package io.github.kelin.tconfuture.fluids.molten;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerRegistry;

public final class BlazingBlood{
    private BlazingBlood() {}

    public static FluidNonColoredMolten init() {
        FluidNonColoredMolten fluid = new FluidNonColoredMolten(
                "blazing_blood",
                FluidTextureRegistry.BLAZING_BLOOD_STILL,
                FluidTextureRegistry.BLAZING_BLOOD_FLOWING
        );
        fluid.setUnlocalizedName("tconfuture.blazing_blood");

        fluid.setTemperature(1800);
        fluid.setDensity(3500);
        fluid.setLuminosity(14);

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        TinkerRegistry.registerSmelteryFuel(new FluidStack(fluid, 50), 150);
        return fluid;
    }
}
