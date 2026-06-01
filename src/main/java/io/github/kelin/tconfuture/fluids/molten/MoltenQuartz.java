package io.github.kelin.tconfuture.fluids.molten;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class MoltenQuartz {
    private MoltenQuartz() {}

    public static FluidNonColoredMolten init() {
        FluidNonColoredMolten fluid = new FluidNonColoredMolten(
                "molten_quartz",
                FluidTextureRegistry.MOLTEN_QUARTZ_STILL,
                FluidTextureRegistry.MOLTEN_QUARTZ_FLOWING
        );
        fluid.setUnlocalizedName("tconfuture.molten_quartz");

        fluid.setTemperature(937);
        fluid.setLuminosity(6);

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        if (Loader.isModLoaded("tconstruct")) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("fluid", fluid.getName());
            tag.setString("ore", "gemQuartz");
            FMLInterModComms.sendMessage("tconstruct", "getFluid", tag);
        }
        return fluid;
    }
}
