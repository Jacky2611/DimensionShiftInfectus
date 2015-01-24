package net.dimensionshiftinfectus.mod.generation;

import java.util.Random;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.IWorldGenerator;

public class OreGenerationDimensionShiftInfectus implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		if(DimensionManager.getWorld(0) != null){
		if (world.provider.dimensionId == DimensionShiftInfectus.dimensionInfectusId) {
			generateInfectus(world, random, chunkX * 16, chunkZ * 16);
		} else if (world.provider.dimensionId == 0) {
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
		}
		}
	}

	private void generateInfectus(World world, Random random, int x, int z) {
		/*
		 * generateOre(Blocks.sand, world, random, x, z, x * 16, z * 16, 20, 50,
		 * 0, 128, Blocks.stone); generateOre(Blocks.iron_ore, world, random, x,
		 * z, x * 16, z * 16, 6, 50, 0, 128, Blocks.stone);
		 * generateOre(Blocks.coal_ore, world, random, x, z, x * 16, z * 16, 8,
		 * 30, 50, 256, Blocks.stone); generateOre(Blocks.redstone_ore, world,
		 * random, x, z, x * 16, z * 16, 5, 30, 0, 30, Blocks.stone);
		 */
		// WORKS

		World worldSurface = MinecraftServer.getServer().worldServerForDimension(0);
		if (!worldSurface.checkChunksExist(x / 16, 0, z / 16, x / 16, 999, z / 16) && random.nextInt(100) > 5) {
			GenerationPortalShrine.generate(random, x + random.nextInt(16), z + random.nextInt(16));
		}

	}

	private void generateOverworld(World world, Random random, int x, int z) {

		World worldInfectus = MinecraftServer.getServer().worldServerForDimension(DimensionShiftInfectus.dimensionInfectusId);
		if (!worldInfectus.checkChunksExist(x / 16, 0, z / 16, x / 16, 999, z / 16) && random.nextInt(100) > 5) {
			GenerationPortalShrine.generate(random, x + random.nextInt(16), z + random.nextInt(16));
		}

	}

	public void generateOre(Block block, World world, Random random, int chunk_x, int chunk_z, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY, Block generateIn) {

		int heightRange = maxY - minY;

		WorldGenMinable worldgenminable = new WorldGenMinable(block, 13, generateIn);
		for (int k1 = 0; k1 < chancesToSpawn; ++k1) {
			int xrand = random.nextInt(16);
			int yrand = random.nextInt(heightRange) + minY;
			int zrand = random.nextInt(16);
			worldgenminable.generate(world, random, chunk_x + xrand, yrand, chunk_z + zrand);
		}
	}
}