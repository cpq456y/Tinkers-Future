package io.github.kelin.tconfuture.fluids;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.fluids.client.FluidStateMapper;
import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class FluidClientEvents {
    private  FluidClientEvents() {
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.getMap();
        map.registerSprite(FluidTextureRegistry.METAL_STILL);
        map.registerSprite(FluidTextureRegistry.METAL_FLOWING);

        map.registerSprite(FluidTextureRegistry.EARTH_SLIME_STILL);
        map.registerSprite(FluidTextureRegistry.EARTH_SLIME_FLOWING);

        map.registerSprite(FluidTextureRegistry.BLAZING_BLOOD_STILL);
        map.registerSprite(FluidTextureRegistry.BLAZING_BLOOD_FLOWING);

        map.registerSprite(FluidTextureRegistry.MOLTEN_QUARTZ_STILL);
        map.registerSprite(FluidTextureRegistry.MOLTEN_QUARTZ_FLOWING);

        map.registerSprite(FluidTextureRegistry.MOLTEN_DIAMOND_STILL);
        map.registerSprite(FluidTextureRegistry.MOLTEN_DIAMOND_FLOWING);

        map.registerSprite(FluidTextureRegistry.MOLTEN_SLIMESTEEL_STILL);
        map.registerSprite(FluidTextureRegistry.MOLTEN_SLIMESTEEL_FLOWING);
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        registerFluidModels(TinkerFluid.earthSlime);

        registerFluidModels(TinkerFluid.blazingBlood);

        registerFluidModels(TinkerFluid.moltenQuartz);
        registerFluidModels(TinkerFluid.moltenDiamond);

        registerFluidModels(TinkerFluid.moltenSlimesteel);
    }

    private static void registerFluidModels(Fluid fluid) {
        if (fluid == null || fluid.getBlock() == null)  {
            return;
        }

        Block b = fluid.getBlock();
        Item item = Item.getItemFromBlock(b);
        FluidStateMapper  mapper = new FluidStateMapper(fluid);

        if (item != Items.AIR) {
            ModelBakery.registerItemVariants(item);
            ModelLoader.setCustomMeshDefinition(item, mapper);
        }

        ModelLoader.setCustomStateMapper(b, mapper);
    }
}
