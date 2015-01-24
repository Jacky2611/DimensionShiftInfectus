package net.dimensionshiftinfectus.mod.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class GenerationPortalShrine {

	// Starts the creation of the 5x5 shrine
	public static boolean generate(Random rand, int x, int z) {

		boolean canCreate = true;

		if (DimensionManager.getWorld(0) == null || DimensionManager.getWorld(DimensionShiftInfectus.dimensionInfectusId) == null) {

			return false;
		}

		MinecraftServer mServer = MinecraftServer.getServer();
		World worldSurface = mServer.worldServerForDimension(0);
		World worldInfectus = mServer.worldServerForDimension(DimensionShiftInfectus.dimensionInfectusId);

		if (!checkIfFlat(worldSurface, x, z)) {
			canCreate = false;
		}
		if (!checkIfFlat(worldInfectus, x, z)) {
			canCreate = false;
		}

		if (canCreate) {
			create(worldSurface, x, z);
			create(worldInfectus, x, z);
		}

		return canCreate;
	}

	private static void create(World world, int x, int z) {
		int y = world.getTopSolidOrLiquidBlock(x, z) - 1;
		// System.out.println("LOCATIONS x:" + x +" y:" + y +" z:" +z);

		// Generation starts at the lowest blocks
		for (int x1 = x - 2; x1 >= x + 2; x1++) {
			for (int z1 = z - 2; z1 >= z + 2; z1++) {
				if (world.getTopSolidOrLiquidBlock(x1, z1) - 1 < y) {
					y = world.getTopSolidOrLiquidBlock(x1, z1) - 1;
				}
			}
		}

		// generate here

		// Setting box
		for (int x1 = x - 2; x1 <= x + 2; x1++) {
			for (int z1 = z - 2; z1 <= z + 2; z1++) {
				for (int y1 = y; y1 <= y + 4; y1++) {
					world.setBlock(x1, y1, z1, DimensionShiftInfectus.blockAncientStoneBricks);
				}
			}
		}

		// making room
		for (int x1 = x - 1; x1 <= x + 1; x1++) {
			for (int z1 = z - 1; z1 <= z + 1; z1++) {
				for (int y1 = y + 1; y1 <= y + 3; y1++) {
					world.setBlock(x1, y1, z1, Blocks.air);
				}
			}
		}

		// Window in Roof
		world.setBlock(x, y + 4, z, Blocks.air);

	}

	public static boolean checkIfFlat(World world, int x, int z) {
		List<Integer> height = new ArrayList<Integer>();
		height.add(world.getTopSolidOrLiquidBlock(x, z));;

		for (int x1 = x - 2; x1 <= x + 2; x1++) {
			for (int z1 = z - 2; z1 <= z + 2; z1++) {
				// Ending if its no normal cube
				Block topBlock = world.getTopBlock(x1, z1);
				if ((!topBlock.isNormalCube()) || Block.isEqualTo(topBlock, Blocks.ice)) {
					return false;
				}
				int heightValue = world.getTopSolidOrLiquidBlock(x1, z1);

				// Checking if that height is already in the List
				boolean isNewHeight = true;

				for (int i = 0; i < height.size(); i++) {
					if (height.get(i) == heightValue) {
						isNewHeight = false;
					}
				}
				if (isNewHeight) {
					height.add(heightValue);
				}

			}
		}

		if (height.size() < 3) {
			return true;
		} else {
			return false;
		}
	}

}
