package io.github.kelin.tconfuture.smeltery.data;

import io.github.kelin.tconfuture.fluids.data.FluidValues;
import io.github.kelin.tconfuture.library.smeltery.CustomMeltingRecipe;
import io.github.kelin.tconfuture.shared.TinkerCommon;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.shared.TinkerFluids;

public class SmelteryRecipeProvider {
    public void init(FMLPostInitializationEvent event) {
        TinkerRegistry.registerMelting(new CustomMeltingRecipe(new RecipeMatch.Item(new ItemStack(TinkerCommon.obsidianPane), 1, FluidValues.INGOT * 1/2), TinkerFluids.obsidian, 1300, 480));
    }
}
