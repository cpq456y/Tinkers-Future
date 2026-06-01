package io.github.kelin.tconfuture.asm;

import io.github.kelin.tconfuture.Tags;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Map;

public class FluidTextureTransformer implements IClassTransformer {
    private static final Map<String, String[]> FLUID_TEXTURES = new HashMap<>();

    static {
        FLUID_TEXTURES.put("obsidian", new String[] {"fluid/molten/stone/obsidian/still", "fluid/molten/stone/obsidian/flowing"});
        FLUID_TEXTURES.put("iron", new String[] {"fluid/molten/ore/iron/still", "fluid/molten/ore/iron/flowing"});
        FLUID_TEXTURES.put("gold", new String[] {"fluid/molten/ore/gold/still", "fluid/molten/ore/gold/still"});
        FLUID_TEXTURES.put("pigiron", new String[] {"fluid/molten/alloy/pig_iron/still",  "fluid/molten/alloy/pig_iron/flowing"});
        FLUID_TEXTURES.put("cobalt", new String[] {"fluid/molten/ore/cobalt/still",  "fluid/molten/ore/cobalt/flowing"});
        FLUID_TEXTURES.put("manyullyn", new String[] {"fluid/molten/alloy/manyullyn/still",  "fluid/molten/alloy/manyullyn/flowing"});
        FLUID_TEXTURES.put("emerald", new String[] {"fluid/molten/ore/emerald/still",  "fluid/molten/ore/emerald/flowing"});
        FLUID_TEXTURES.put("glass", new String[] {"fluid/molten/glass/still", "fluid/molten/glass/flowing"});
        FLUID_TEXTURES.put("blueslime", new String[] {"fluid/slime/sky/still", "fluid/slime/sky/flowing"});
        FLUID_TEXTURES.put("purpleslime", new String[] {"fluid/slime/ender/still", "fluid/slime/ender/flowing"});
        FLUID_TEXTURES.put("copper", new String[] {"fluid/molten/ore/copper/still",  "fluid/molten/ore/copper/flowing"});
    }

    private static final String MOD_ID = Tags.MOD_ID;

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;
        
        // 拦截 Forge 的 Fluid 基类
        if (transformedName.equals("net.minecraftforge.fluids.Fluid")) {
            System.out.println("[TinkerFutureASM] Patching Fluid base class for custom textures...");
            return patchFluid(basicClass);
        }
        return basicClass;
    }

    private byte[] patchFluid(byte[] basicClass) {
        ClassReader cr = new ClassReader(basicClass);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        // 获取混淆后的方法名
        String getNameMethod = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
                "net/minecraftforge/fluids/Fluid", "getName", "()Ljava/lang/String;");
        String getStillMethod = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
                "net/minecraftforge/fluids/Fluid", "getStill", "()Lnet/minecraft/util/ResourceLocation;");
        String getFlowingMethod = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
                "net/minecraftforge/fluids/Fluid", "getFlowing", "()Lnet/minecraft/util/ResourceLocation;");
        // 获取 getColor 方法名
        String getColorMethod = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
                "net/minecraftforge/fluids/Fluid", "getColor", "()I");

        System.out.println("[TinkerFutureASM] Methods: getStill=" + getStillMethod + ", getColor=" + getColorMethod);

        for (MethodNode method : cn.methods) {
            if (method.name.equals(getStillMethod)) {
                insertFluidChecks(method, getNameMethod, FLUID_TEXTURES, "getStill");
                System.out.println("[TinkerFutureASM] patched getStill for " + FLUID_TEXTURES.size() + " fluids.");
            }
            else if (method.name.equals(getFlowingMethod)) {
                insertFluidChecks(method, getNameMethod, FLUID_TEXTURES, "getFlowing");
                System.out.println("[TinkerFutureASM] patched getFlowing for " + FLUID_TEXTURES.size() + " fluids.");
            }
            // 拦截 getColor，返回白色以移除色值滤镜
            else if (method.name.equals(getColorMethod)) {
                insertColorOverride(method, getNameMethod);
                System.out.println("[TinkerFutureASM] patched getColor to remove tint.");
            }
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();
    }

    private void insertFluidChecks(MethodNode method, String getNameMethod, Map<String, String[]> textures, String methodNameSuffix) {
        InsnList insertions = new InsnList();
        
        for (Map.Entry<String, String[]> entry : textures.entrySet()) {
            String fluidName = entry.getKey();
            String path = methodNameSuffix.equals("getStill") ? entry.getValue()[0] : entry.getValue()[1];

            LabelNode skipLabel = new LabelNode(new Label());

            insertions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insertions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/fluids/Fluid", getNameMethod, "()Ljava/lang/String;", false));

            insertions.add(new LdcInsnNode(fluidName));
            insertions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));

            insertions.add(new JumpInsnNode(Opcodes.IFEQ, skipLabel));

            insertions.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/util/ResourceLocation"));
            insertions.add(new InsnNode(Opcodes.DUP));
            insertions.add(new LdcInsnNode(MOD_ID));
            insertions.add(new LdcInsnNode(path));
            insertions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/util/ResourceLocation", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false));
            insertions.add(new InsnNode(Opcodes.ARETURN));

            insertions.add(skipLabel);
        }
        
        method.instructions.insertBefore(method.instructions.getFirst(), insertions);
    }

    private void insertColorOverride(MethodNode method, String getNameMethod) {
        InsnList insertions = new InsnList();

        for (String fluidName : FLUID_TEXTURES.keySet()) {
            LabelNode skipLabel = new LabelNode(new Label());

            // this.getName()
            insertions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            insertions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/fluids/Fluid", getNameMethod, "()Ljava/lang/String;", false));

            // equals(fluidName)
            insertions.add(new LdcInsnNode(fluidName));
            insertions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false));

            // if (!equals) skip
            insertions.add(new JumpInsnNode(Opcodes.IFEQ, skipLabel));

            // return 0xFFFFFFFF (white, fully opaque, no tint)
            insertions.add(new LdcInsnNode(-1)); // -1 is 0xFFFFFFFF in integer
            insertions.add(new InsnNode(Opcodes.IRETURN));

            insertions.add(skipLabel);
        }
        
        method.instructions.insertBefore(method.instructions.getFirst(), insertions);
    }
}
