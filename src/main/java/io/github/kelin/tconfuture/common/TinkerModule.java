package io.github.kelin.tconfuture.common;

import io.github.kelin.tconfuture.Tags;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class TinkerModule {

    // gameplay singleton
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Fluid> FLUIDS = new ArrayList<>();
    public static final List<MobEffects> MOB_EFFECTS = new ArrayList<>();
    public static final List<CreativeTabs>  CREATIVE_TABS = new ArrayList<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));}
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));}

    public static Block registerBlock(String name, Block block, Item item) {
        block.setRegistryName(Tags.MOD_ID + ":" + name);
        block.setTranslationKey(Tags.MOD_ID + "." + name);
        BLOCKS.add(block);

        item.setRegistryName(Tags.MOD_ID + ":" + name);
        item.setTranslationKey(Tags.MOD_ID + "." + name);
        ITEMS.add(item);
        return block;
    }

    public static Item registerItem(String name, Item item) {
        item.setRegistryName(Tags.MOD_ID + ":" + name);
        item.setTranslationKey(Tags.MOD_ID + "." + name);
        ITEMS.add(item);
        return item;}

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ITEMS) {
            if (item instanceof ItemBlock) continue;
            String name = item.getRegistryName().getPath();
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(Tags.MOD_ID + ":" + name,
                            "inventory"));}
        }
}
