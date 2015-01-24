package net.dimensionshiftinfectus.mod.world.biome;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenWasteland extends BiomeGenBase {

	public BiomeGenWasteland(int id) {
		super(id);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();

		this.biomeName = "Wasteland";
		this.enableSnow = false;
		this.enableRain = false;

		this.topBlock = DimensionShiftInfectus.blockInfectedGrass;
		this.fillerBlock = DimensionShiftInfectus.blockInfectedDirt;

		this.rootHeight = 0.8F; // Base Height
		this.heightVariation = 0.001F; // height Variation
		// TODO Auto-generated constructor stub

		this.addFlower(Blocks.deadbush, 1, 1);

	}

}
