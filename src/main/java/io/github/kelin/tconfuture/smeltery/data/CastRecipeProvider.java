package io.github.kelin.tconfuture.smeltery.data;

import io.github.kelin.tconfuture.fluids.data.FluidValues;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class CastRecipeProvider {
    public void init(FMLPostInitializationEvent event) {
        // Golden Cast
        TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGem, new RecipeMatch.Oredict("gemDiamond", 1), TinkerFluids.gold, FluidValues.INGOT * 2, true, false));
        TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGem, new RecipeMatch.Oredict("gemLapis", 1), TinkerFluids.gold, FluidValues.INGOT * 2, true, false));
        TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGem, new RecipeMatch.Oredict("gemQuartz", 1), TinkerFluids.gold, FluidValues.INGOT * 2, true, false));
        // Clay Cast

    }
}
