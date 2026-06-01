package io.github.kelin.tconfuture.shared;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.common.TinkerModule;
import io.github.kelin.tconfuture.shared.item.CheeseBlockItem;
import io.github.kelin.tconfuture.shared.item.CheeseItem;
import io.github.kelin.tconfuture.shared.item.JeweledAppleItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public class TinkerCommon{
    public static final CreativeTab tabGeneral = new CreativeTab("general", new ItemStack(Items.AIR)) {
        @Override
        public String getTranslationKey() {
            return "itemGroup." + Tags.MOD_ID + ".general";
        }
    };

    public static final Block obsidianPane;static {obsidianPane = TinkerModule.registerBlock("obsidian_pane", new BlockPane(Material.ROCK, true) {@Override public boolean isOpaqueCube(IBlockState state) {return false;}}.setHardness(25.0f).setResistance(400.0f).setCreativeTab(tabGeneral));obsidianPane.setHarvestLevel("pickaxe", 3);}
    public static final Block goldBars;static {goldBars = TinkerModule.registerBlock("gold_bars", new BlockPane(Material.ROCK, true) {@Override public boolean isOpaqueCube(IBlockState state) {return false;}}.setHardness(3.0f).setResistance(5.0f).setCreativeTab(tabGeneral));goldBars.setHarvestLevel("pickaxe", 2);}

    public static final Item jeweledApple = TinkerModule.registerItem("jeweled_apple", new JeweledAppleItem() {{setCreativeTab(tabGeneral);}});
    public static final Item cheeseIngot = TinkerModule.registerItem("cheese_ingot", new CheeseItem() {{setCreativeTab(tabGeneral);}});
    public static final Item cheeseBlock = TinkerModule.registerItem("cheese_block", new CheeseBlockItem() {{setCreativeTab(tabGeneral);}});
}