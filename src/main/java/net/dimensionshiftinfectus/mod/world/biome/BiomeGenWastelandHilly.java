package net.dimensionshiftinfectus.mod.world.biome;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenWastelandHilly extends BiomeGenBase {

	public BiomeGenWastelandHilly(int id) {
		super(id);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();

		this.biomeName = "Hilly Wasteland";
		this.enableSnow = false;
		this.enableRain = false;

		this.topBlock = DimensionShiftInfectus.blockInfectedGrass;
		this.fillerBlock = DimensionShiftInfectus.blockInfectedDirt;

		this.rootHeight = 0.8F; // Base Height
		this.heightVariation = 0.8F; // height Variation
		// TODO Auto-generated constructor stub

		this.addFlower(Blocks.deadbush, 1, 1);

	}

}
