package io.github.kelin.tconfuture.common.data;

import io.github.kelin.tconfuture.common.TinkerModule;
import io.github.kelin.tconfuture.shared.TinkerMaterials;
import io.github.kelin.tconfuture.world.TinkerWorld;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CopperSmeltingProvider {

    public CopperSmeltingProvider(FMLPostInitializationEvent event) {
        ItemBlock oreBlock = TinkerModule.getItemBlock(TinkerWorld.copperOre);
        GameRegistry.addSmelting(new ItemStack(oreBlock), new ItemStack(TinkerMaterials.copperIngot), 0.7f);
    }
}
