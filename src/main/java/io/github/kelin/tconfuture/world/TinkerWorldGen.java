package io.github.kelin.tconfuture.world;

import io.github.kelin.tconfuture.common.TinkerModule;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TinkerWorldGen implements IWorldGenerator {

    private static final WorldGenMinable COPPER_ORE = new WorldGenMinable(
            TinkerModule.getBlockState(TinkerWorld.copperOre),
            9,
            net.minecraft.block.state.pattern.BlockMatcher.forBlock(net.minecraft.init.Blocks.STONE)
    );

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0: generateOre(COPPER_ORE, world, random, chunkX * 16, chunkZ * 16, 0, 64, 30);break;
            case -1: break;
            case 1: break;
        }
    }

    private void generateOre(WorldGenMinable generator, World world, Random random, int x, int z, int minY, int maxY, int veinCount) {
        for (int i = 0; i < veinCount; i++) {
            int posX = x + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = z + random.nextInt(16);
            generator.generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}
