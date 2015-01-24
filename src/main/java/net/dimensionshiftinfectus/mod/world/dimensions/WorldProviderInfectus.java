package net.dimensionshiftinfectus.mod.world.dimensions;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderInfectus extends WorldProvider {

	@Override
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return ("DimenisonShift Infectus");
	}

	@Override
	public void registerWorldChunkManager() {

		this.worldChunkMgr = new WorldChunkManagerInfectus(worldObj.getSeed(), terrainType);
		this.dimensionId = DimensionShiftInfectus.dimensionInfectusId;

	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new net.dimensionshiftinfectus.mod.world.dimensions.ChunkProviderInfectus(worldObj, worldObj.getSeed(), true);
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering the Infectus";
	}

	@Override
	public String getDepartMessage() {

		return "Leaving the Infectus";

	}

	/**
	 * Can player re-spawn here public boolean canRespawnHere() { return false;
	 * }
	 **/

	@Override
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
		float red;
		float green;
		float blue;

		Vec3 normal = worldObj.getSkyColorBody(cameraEntity, partialTicks);
		red = (float) (normal.xCoord * 0.7 + 0.1F);
		green = (float) (normal.yCoord * 0.7);
		blue = (float) (normal.zCoord * 0.7 + 0.13);

		return Vec3.createVectorHelper(red, green, blue);
		//return this.worldObj.getWorldVec3Pool().getVecFromPool(red, green, blue);

	}

	@Override
	public String getSaveFolder() {
		return (dimensionId == 0 ? null : "Infectus" + dimensionId);
	}

}
