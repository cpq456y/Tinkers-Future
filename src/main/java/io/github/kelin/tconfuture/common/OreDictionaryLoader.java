package io.github.kelin.tconfuture.common;

import io.github.kelin.tconfuture.shared.TinkerMaterials;
import io.github.kelin.tconfuture.world.TinkerWorld;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader {
    public OreDictionaryLoader(FMLPreInitializationEvent event) {

        ItemBlock copperOre = TinkerModule.getItemBlock(TinkerWorld.copperOre);
        OreDictionary.registerOre("oreCopper", new ItemStack(copperOre));
        ItemBlock copperBlock = TinkerModule.getItemBlock(TinkerMaterials.copperBlock);
        OreDictionary.registerOre("blockCopper", new ItemStack(copperBlock));

        OreDictionary.registerOre("ingotCopper", new ItemStack(TinkerMaterials.copperIngot));

        OreDictionary.registerOre("nuggetCopper", new ItemStack(TinkerMaterials.copperNugget));
    }
}
