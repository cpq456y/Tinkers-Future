package io.github.kelin.tconfuture;

import io.github.kelin.tconfuture.client.BuiltinResourcePackInstaller;
import io.github.kelin.tconfuture.common.OreDictionaryLoader;
import io.github.kelin.tconfuture.common.TinkerModule;
import io.github.kelin.tconfuture.common.data.CopperSmeltingProvider;
import io.github.kelin.tconfuture.fluids.TinkerFluid;
import io.github.kelin.tconfuture.shared.TinkerCommon;
import io.github.kelin.tconfuture.shared.TinkerMaterials;
import io.github.kelin.tconfuture.smeltery.data.*;
import io.github.kelin.tconfuture.tools.data.ModifierRecipeProvider;
import io.github.kelin.tconfuture.world.TinkerWorld;
import io.github.kelin.tconfuture.world.TinkerWorldGen;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = "required-after:mantle@[1.12-1.3.3.55,);required-after:tconstruct@[1.12.2-2.13.0.183,)")
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class TinkersFuture {

    public static final TinkerWorld WORLD = new TinkerWorld();
    public static final TinkerCommon COMMONS = new TinkerCommon();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new OreDictionaryLoader(event);
        TinkerFluid.init();
        GameRegistry.registerWorldGenerator(new TinkerWorldGen(), 0);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            BuiltinResourcePackInstaller.install();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("tconstruct")) {
            new CastingRecipeProvider().init(event);
            new SmelteryRecipeProvider().init(event);
            new CastRecipeProvider().init(event);
            new ModifierRecipeProvider().init(event);
            new IngotCastingProvider().init(event);
            new BlockCastingProvider().init(event);
            new GemCastingProvider().init(event);
        }
        new CopperSmeltingProvider(event);
    }
}
