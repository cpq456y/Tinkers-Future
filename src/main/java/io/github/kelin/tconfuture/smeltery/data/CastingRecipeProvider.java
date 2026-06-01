package io.github.kelin.tconfuture.smeltery.data;

import io.github.kelin.tconfuture.fluids.data.FluidValues;
import io.github.kelin.tconfuture.shared.TinkerCommon;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class CastingRecipeProvider {
    public void init(FMLPostInitializationEvent event) {
        // obsidian
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(TinkerCommon.obsidianPane), null, TinkerFluids.obsidian, FluidValues.INGOT * 1/2, 100));
        // cheese
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(TinkerCommon.cheeseIngot), RecipeMatch.ofNBT(TinkerSmeltery.castIngot), slimeknights.tconstruct.shared.TinkerFluids.milk, FluidValues.BUCKET * 1/4, 2400));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(TinkerCommon.cheeseBlock), null, slimeknights.tconstruct.shared.TinkerFluids.milk, FluidValues.BUCKET, 6000));
        // misc casting - gold
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Items.GOLDEN_APPLE), RecipeMatch.ofNBT(new ItemStack(Items.APPLE)), slimeknights.tconstruct.shared.TinkerFluids.gold, FluidValues.INGOT * 8, true,false));

    }
}
