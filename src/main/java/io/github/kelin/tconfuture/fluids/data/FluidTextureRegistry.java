package io.github.kelin.tconfuture.fluids.data;

import io.github.kelin.tconfuture.Tags;
import net.minecraft.util.ResourceLocation;

public final class FluidTextureRegistry {
    private FluidTextureRegistry(){}

    public static final ResourceLocation METAL_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/molten/still");
    public static final ResourceLocation METAL_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/molten/flowing");

    public static final ResourceLocation EARTH_SLIME_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/slime/earth/still");
    public static final ResourceLocation EARTH_SLIME_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/slime/earth/flowing");

    public static final ResourceLocation BLAZING_BLOOD_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/molten/blaze/still");
    public static final ResourceLocation BLAZING_BLOOD_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/molten/blaze/flowing");

    public static final ResourceLocation MOLTEN_QUARTZ_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/molten/ore/quartz/still");
    public static final ResourceLocation MOLTEN_QUARTZ_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/molten/ore/quartz/flowing");

    public static final ResourceLocation MOLTEN_DIAMOND_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/molten/ore/diamond/still");
    public static final ResourceLocation MOLTEN_DIAMOND_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/molten/ore/diamond/flowing");

    public static final ResourceLocation MOLTEN_SLIMESTEEL_STILL = new ResourceLocation(Tags.MOD_ID, "fluid/molten/alloy/slimesteel/still");
    public static final ResourceLocation MOLTEN_SLIMESTEEL_FLOWING = new ResourceLocation(Tags.MOD_ID, "fluid/molten/alloy/slimesteel/flowing");
}
