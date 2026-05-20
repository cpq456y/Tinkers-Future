package io.github.kelin.tconfuture.shared;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.common.TinkerModule;
import io.github.kelin.tconfuture.shared.item.CheeseBlockItem;
import io.github.kelin.tconfuture.shared.item.CheeseItem;
import io.github.kelin.tconfuture.shared.item.JeweledAppleItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public class TinkerCommons extends TinkerModule {
    public static final CreativeTab tabGeneral = new CreativeTab("general", new ItemStack(Items.AIR)) {
        @Override
        public String getTranslationKey() {
            return "itemGroup." + Tags.MOD_ID + ".general";
        }
    };

    public static final Item jeweledApple = TinkerModule.registerItem("jeweled_apple",new JeweledAppleItem() {{setCreativeTab(tabGeneral);}});
    public static final Item cheeseIngot = TinkerModule.registerItem("cheese_ingot", new CheeseItem() {{setCreativeTab(tabGeneral);}});
    public static final Item cheeseBlock = TinkerModule.registerItem("cheese_block", new CheeseBlockItem() {{setCreativeTab(tabGeneral);}});
}