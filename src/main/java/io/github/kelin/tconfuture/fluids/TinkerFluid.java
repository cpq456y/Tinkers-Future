package io.github.kelin.tconfuture.fluids;

import io.github.kelin.tconfuture.fluids.molten.*;
import io.github.kelin.tconfuture.fluids.slime.EarthSlime;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;

public final class TinkerFluid {
    private TinkerFluid() {}

    public static FluidNonColoredMolten earthSlime;

    public static FluidNonColoredMolten blazingBlood;

    public static FluidNonColoredMolten moltenQuartz;
    public static FluidNonColoredMolten moltenDiamond;

    public static FluidNonColoredMolten moltenSlimesteel;

    public static void init() {
        earthSlime = EarthSlime.init();

        blazingBlood = BlazingBlood.init();

        moltenQuartz = MoltenQuartz.init();
        moltenDiamond = MoltenDiamond.init();

        moltenSlimesteel = MoltenSlimesteel.init();
    }
}
