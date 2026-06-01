package io.github.kelin.tconfuture.fluids.molten;

import io.github.kelin.tconfuture.fluids.data.FluidTextureRegistry;
import io.github.kelin.tconfuture.fluids.data.FluidValues;
import io.github.kelin.tconfuture.library.fluid.FluidNonColoredMolten;
import io.github.kelin.tconfuture.library.smeltery.CustomMeltingRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;

public final class MoltenDiamond {
    private MoltenDiamond() {}

    public static FluidNonColoredMolten init() {
        FluidNonColoredMolten fluid = new FluidNonColoredMolten(
                "molten_diamond",
                FluidTextureRegistry.MOLTEN_DIAMOND_STILL,
                FluidTextureRegistry.MOLTEN_DIAMOND_FLOWING
        );
        fluid.setUnlocalizedName("tconfuture.molten_diamond");

        fluid.setTemperature(1750);
        fluid.setLuminosity(13);

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Oredict("gemDiamond", 1, FluidValues.GEM), fluid, 1750, 380));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Oredict("oreDiamond", 1, FluidValues.GEM * 2), fluid, 1750, 980));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Oredict("blockDiamond", 1, FluidValues.GEM * 9), fluid, 1750,1180));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_SWORD), 1, FluidValues.GEM * 2), fluid, 1750, 560));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_SHOVEL), 1, FluidValues.GEM), fluid, 1750, 380));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_PICKAXE), 1, FluidValues.GEM * 3), fluid, 1750, 780));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_AXE), 1, FluidValues.GEM * 3), fluid, 1750, 780));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_HOE), 1, FluidValues.GEM * 2), fluid, 1750, 560));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_HELMET), 1, FluidValues.GEM * 5), fluid, 1750, 880));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_CHESTPLATE), 1, FluidValues.GEM * 8), fluid, 1750, 1100));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_LEGGINGS), 1, FluidValues.GEM * 7), fluid, 1750, 1040));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_BOOTS), 1, FluidValues.GEM * 4), fluid, 1750, 780));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.DIAMOND_HORSE_ARMOR), 1, FluidValues.GEM * 7), fluid, 1750, 1100));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Blocks.ENCHANTING_TABLE), 1, FluidValues.GEM * 2), fluid, 1750, 580));
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(Blocks.JUKEBOX), 1, FluidValues.GEM), fluid, 1750, 400));


        if (Loader.isModLoaded("tconstruct")) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("fluid", fluid.getName());
            tag.setString("ore", "gemDiamond");
            FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
        }
        return fluid;
    }
}
