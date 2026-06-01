package io.github.kelin.tconfuture.world;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.common.TinkerModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public class TinkerWorld  {
    public static final CreativeTab tabWorld = new CreativeTab("world", new ItemStack(Items.AIR)) {
        @Override
        public String getTranslationKey() {
            return "itemGroup." + Tags.MOD_ID + ".world";
        }
    };

    public static Block copperOre;
    static {
        copperOre = TinkerModule.registerBlock("copper_ore", new Block(Material.ROCK).setHardness(3.0f).setResistance(5.0f).setCreativeTab(tabWorld));
        copperOre.setHarvestLevel("pickaxe", 1);
    }
}
