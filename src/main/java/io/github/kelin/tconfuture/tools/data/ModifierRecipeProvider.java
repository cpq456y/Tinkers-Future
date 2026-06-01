package io.github.kelin.tconfuture.tools.data;

import io.github.kelin.tconfuture.fluids.TinkerFluid;
import io.github.kelin.tconfuture.fluids.data.FluidValues;
import io.github.kelin.tconfuture.shared.TinkerCommon;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

public class ModifierRecipeProvider {
    public void init(FMLPostInitializationEvent event) {
        // jeweled apple
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(TinkerCommon.jeweledApple), RecipeMatch.ofNBT(new ItemStack(Items.APPLE)), TinkerFluid.moltenDiamond, FluidValues.GEM * 2, true, false));
    }
}
