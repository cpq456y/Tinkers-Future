package io.github.kelin.tconfuture.library.fluid;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidNonColoredMolten extends Fluid{
    public FluidNonColoredMolten(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
        this.setDensity(2000);
        this.setViscosity(10000);
        this.setTemperature(1000);
        this.setLuminosity(10);
        this.setRarity(EnumRarity.UNCOMMON);
    }
}
