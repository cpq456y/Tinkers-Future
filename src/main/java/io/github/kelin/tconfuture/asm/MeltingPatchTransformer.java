package io.github.kelin.tconfuture.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Patch TIC2的熔融逻辑，让CustomMeltingRecipe支持独立的温度和时间设置
 */
public class MeltingPatchTransformer implements IClassTransformer {

    private static final String TILE_SMELTERY = "slimeknights.tconstruct.smeltery.tileentity.TileSmeltery";
    private static final String TILE_HEATING_STRUCTURE = "slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructure";
    private static final String MELTING_HOOKS = "io/github/kelin/tconfuture/asm/MeltingHooks";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        if (name.equals(TILE_SMELTERY) || transformedName.equals(TILE_SMELTERY)) {
            return patchTileSmeltery(basicClass);
        }
        
        if (name.equals(TILE_HEATING_STRUCTURE) || transformedName.equals(TILE_HEATING_STRUCTURE)) {
            return patchTileHeatingStructure(basicClass);
        }

        return basicClass;
    }

    private byte[] patchTileSmeltery(byte[] basicClass) {
        ClassReader cr = new ClassReader(basicClass);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        for (MethodNode method : cn.methods) {
            if (method.name.equals("updateHeatRequired") && method.desc.equals("(I)V")) {
                replaceUpdateHeatRequired(method);
            }
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();
    }

    private byte[] patchTileHeatingStructure(byte[] basicClass) {
        ClassReader cr = new ClassReader(basicClass);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        for (MethodNode method : cn.methods) {
            if (method.name.equals("canHeat") && method.desc.equals("(I)Z")) {
                replaceCanHeat(method);
            }
            if (method.name.equals("heatSlot") && method.desc.equals("(I)I")) {
                replaceHeatSlot(method);
            }
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();
    }

    /**
     * 替换 updateHeatRequired 方法体
     * 使用 CustomMeltingRecipe 的 temperature 作为 heat 值（确保温度门槛正确）
     */
    private void replaceUpdateHeatRequired(MethodNode method) {
        method.instructions.clear();
        
        InsnList code = new InsnList();
        LabelNode end = new LabelNode();
        LabelNode setZero = new LabelNode();
        
        // stack = this.getStackInSlot(index)
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "getStackInSlot",
            "(I)Lnet/minecraft/item/ItemStack;",
            false
        ));
        code.add(new VarInsnNode(Opcodes.ASTORE, 2));
        
        // if (stack.isEmpty()) goto setZero
        code.add(new VarInsnNode(Opcodes.ALOAD, 2));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "net/minecraft/item/ItemStack",
            "isEmpty",
            "()Z",
            false
        ));
        code.add(new JumpInsnNode(Opcodes.IFNE, setZero));
        
        // recipe = TinkerRegistry.getMelting(stack)
        code.add(new VarInsnNode(Opcodes.ALOAD, 2));
        code.add(new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            "slimeknights/tconstruct/library/TinkerRegistry",
            "getMelting",
            "(Lnet/minecraft/item/ItemStack;)Lslimeknights/tconstruct/library/smeltery/MeltingRecipe;",
            false
        ));
        code.add(new VarInsnNode(Opcodes.ASTORE, 3));
        
        // if (recipe == null) goto setZero
        code.add(new VarInsnNode(Opcodes.ALOAD, 3));
        code.add(new JumpInsnNode(Opcodes.IFNULL, setZero));
        
        // heat = MeltingHooks.getHeatForRecipe(this, index, recipe.getUsableTemperature())
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new VarInsnNode(Opcodes.ALOAD, 3));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/library/smeltery/MeltingRecipe",
            "getUsableTemperature",
            "()I",
            false
        ));
        code.add(new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            MELTING_HOOKS,
            "getHeatForRecipe",
            "(Lslimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure;II)I",
            false
        ));
        code.add(new VarInsnNode(Opcodes.ISTORE, 4));
        
        // this.setHeatRequiredForSlot(index, heat)
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new VarInsnNode(Opcodes.ILOAD, 4));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "setHeatRequiredForSlot",
            "(II)V",
            false
        ));
        
        // if (!this.hasFuel()) this.consumeFuel()
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructureFuelTank",
            "hasFuel",
            "()Z",
            false
        ));
        code.add(new JumpInsnNode(Opcodes.IFNE, end));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructureFuelTank",
            "consumeFuel",
            "()V",
            false
        ));
        code.add(new InsnNode(Opcodes.RETURN));
        
        // setZero: this.setHeatRequiredForSlot(index, 0)
        code.add(setZero);
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "setHeatRequiredForSlot",
            "(II)V",
            false
        ));
        
        code.add(end);
        code.add(new InsnNode(Opcodes.RETURN));
        
        method.instructions.add(code);
    }

    /**
     * 替换 canHeat 方法体
     * 对 CustomMeltingRecipe 使用自定义温度检查
     */
    private void replaceCanHeat(MethodNode method) {
        method.instructions.clear();
        
        InsnList code = new InsnList();
        LabelNode normalCheck = new LabelNode();
        LabelNode retTrue = new LabelNode();
        
        // if (MeltingHooks.shouldCheckCustomTemp(this, index)) return true
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            MELTING_HOOKS,
            "shouldCheckCustomTemp",
            "(Lslimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure;I)Z",
            false
        ));
        code.add(new JumpInsnNode(Opcodes.IFEQ, normalCheck));
        code.add(new InsnNode(Opcodes.ICONST_1));
        code.add(new InsnNode(Opcodes.IRETURN));
        
        // normalCheck: return temperature >= getHeatRequiredForSlot(index)
        code.add(normalCheck);
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(
            Opcodes.GETFIELD,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "temperature",
            "I"
        ));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new MethodInsnNode(
            Opcodes.INVOKEVIRTUAL,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "getHeatRequiredForSlot",
            "(I)I",
            false
        ));
        code.add(new JumpInsnNode(Opcodes.IF_ICMPGE, retTrue));
        code.add(new InsnNode(Opcodes.ICONST_0));
        code.add(new InsnNode(Opcodes.IRETURN));
        code.add(retTrue);
        code.add(new InsnNode(Opcodes.ICONST_1));
        code.add(new InsnNode(Opcodes.IRETURN));
        
        method.instructions.add(code);
    }

    /**
     * 替换 heatSlot 方法体
     * 原逻辑: return temperature / 100;
     * 对 CustomMeltingRecipe 返回固定进度值
     */
    private void replaceHeatSlot(MethodNode method) {
        method.instructions.clear();
        
        InsnList code = new InsnList();
        LabelNode normalReturn = new LabelNode();
        
        // customProgress = MeltingHooks.getCustomProgressPerTick(this, i, temperature / 100)
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new VarInsnNode(Opcodes.ILOAD, 1));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(
            Opcodes.GETFIELD,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "temperature",
            "I"
        ));
        code.add(new MethodInsnNode(
            Opcodes.INVOKESTATIC,
            MELTING_HOOKS,
            "getCustomProgressPerTick",
            "(Lslimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure;II)I",
            false
        ));
        
        // if (customProgress <= 0) goto normalReturn
        code.add(new VarInsnNode(Opcodes.ISTORE, 2));
        code.add(new VarInsnNode(Opcodes.ILOAD, 2));
        code.add(new JumpInsnNode(Opcodes.IFLE, normalReturn));
        
        // return customProgress
        code.add(new VarInsnNode(Opcodes.ILOAD, 2));
        code.add(new InsnNode(Opcodes.IRETURN));
        
        // normalReturn: return temperature / 100
        code.add(normalReturn);
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new FieldInsnNode(
            Opcodes.GETFIELD,
            "slimeknights/tconstruct/smeltery/tileentity/TileHeatingStructure",
            "temperature",
            "I"
        ));
        code.add(new IntInsnNode(Opcodes.BIPUSH, 100));
        code.add(new InsnNode(Opcodes.IDIV));
        code.add(new InsnNode(Opcodes.IRETURN));
        
        method.instructions.add(code);
    }
}
