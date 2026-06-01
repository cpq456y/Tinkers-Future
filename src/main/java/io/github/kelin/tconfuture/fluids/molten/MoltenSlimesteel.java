package io.github.kelin.tconfuture.fluids.molten;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;
import net.minecraftforge.fluids.FluidRegistry;

public final class MoltenSlimesteel {
    private MoltenSlimesteel() {}

    public static FluidNonColoredMolten init() {
        FluidNonColoredMolten fluid = new FluidNonColoredMolten(
                "molten_slimesteel",
                FluidTextureRegistry.MOLTEN_SLIMESTEEL_STILL,
                FluidTextureRegistry.MOLTEN_SLIMESTEEL_FLOWING
        );
        fluid.setUnlocalizedName("tconfuture.molten_slimesteel");

        fluid.setTemperature(1200);
        fluid.setLuminosity(10);

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        return fluid;
    }
}
