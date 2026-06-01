package io.github.kelin.tconfuture.shared;

import io.github.kelin.tconfuture.common.TinkerModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public class TinkerMaterials {
    public static final CreativeTab tabMaterials = new CreativeTab("materials", new ItemStack(Items.IRON_INGOT)) {
        @Override
        public String getTranslationKey() {
            return "itemGroup.tconfuture.materials";
        }
    };

    public static Block copperBlock;
    public static final Item copperIngot = TinkerModule.registerItem("copper_ingot", new Item().setCreativeTab(tabMaterials));
    public static final Item copperNugget = TinkerModule.registerItem("copper_nugget", new Item().setCreativeTab(tabMaterials));

    static {
        copperBlock = TinkerModule.registerBlock("copper_block", new Block(Material.IRON).setHardness(5.0f).setResistance(6.0f).setCreativeTab(tabMaterials));
        copperBlock.setHarvestLevel("pickaxe", 1);
    }
}
