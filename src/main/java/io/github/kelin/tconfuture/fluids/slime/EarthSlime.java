package io.github.kelin.tconfuture.fluids.slime;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;
import net.minecraftforge.fluids.FluidRegistry;

public class EarthSlime {
    private EarthSlime() {}

    public static FluidNonColoredMolten init() {
        FluidNonColoredMolten fluid = new FluidNonColoredMolten(
            "earth_slime",
                FluidTextureRegistry.EARTH_SLIME_STILL,
                FluidTextureRegistry.EARTH_SLIME_FLOWING
        );
        fluid.setUnlocalizedName("tconfuture.earth_slime");

        fluid.setTemperature(350);

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        return fluid;
    }
}
