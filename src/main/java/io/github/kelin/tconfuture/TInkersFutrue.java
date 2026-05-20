package io.github.kelin.tconfuture;

import io.github.kelin.tconfuture.shared.TinkerCommons;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class TInkersFutrue {

    public static final TinkerCommons COMMONS = new TinkerCommons();
}
