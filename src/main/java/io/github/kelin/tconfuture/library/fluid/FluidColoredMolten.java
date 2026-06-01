package io.github.kelin.tconfuture.library.fluid;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.fluid.FluidColored;

public class FluidColoredMolten extends FluidColored {

    public FluidColoredMolten(String fluidName, int color) {this(fluidName, color, FluidTextureRegistry.METAL_STILL, FluidTextureRegistry.METAL_FLOWING);}

    public FluidColoredMolten(String fluidName, int color, ResourceLocation still, ResourceLocation flow) {
        super(fluidName, color, still, flow);
        this.setDensity(2000);
        this.setViscosity(10000);
        this.setTemperature(1000);
        this.setLuminosity(10);
        this.setRarity(EnumRarity.UNCOMMON);
    }
}
