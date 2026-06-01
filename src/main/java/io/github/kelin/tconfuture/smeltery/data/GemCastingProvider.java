package io.github.kelin.tconfuture.smeltery.data;

import io.github.kelin.tconfuture.fluids.TinkerFluid;
import io.github.kelin.tconfuture.fluids.data.FluidValues;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class GemCastingProvider {
    public void init(FMLPostInitializationEvent event) {

        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Items.DIAMOND), RecipeMatch.ofNBT(TinkerSmeltery.castGem), TinkerFluid.moltenDiamond, FluidValues.GEM));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(Blocks.DIAMOND_BLOCK), null, TinkerFluid.moltenDiamond, FluidValues.GEM * 9));
    }
}
