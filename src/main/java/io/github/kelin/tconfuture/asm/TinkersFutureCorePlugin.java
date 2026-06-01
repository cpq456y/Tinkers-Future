package io.github.kelin.tconfuture.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("TinkersFutureCore")
// 不需要依赖其他核心模组
public class TinkersFutureCorePlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
            "io.github.kelin.tconfuture.asm.FluidTextureTransformer",
            "io.github.kelin.tconfuture.asm.MeltingPatchTransformer"
        };
    }

    @Override public String getModContainerClass() { return null; }
    @Nullable @Override public String getSetupClass() { return null; }
    @Override public void injectData(Map<String, Object> data) {}
    @Override public String getAccessTransformerClass() { return null; }
}