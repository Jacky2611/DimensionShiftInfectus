package net.dimensionshiftinfectus.mod.block;

import java.util.Random;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAncientStoneBricks extends Block {

	public BlockAncientStoneBricks(Material material, String name) {
		super(material);
		this.setBlockName(name);
		this.textureName = DimensionShiftInfectus.MODID + ":" + name;
		this.setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/*
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(int side, int p_149691_2_) {
	 * return Blocks.acacia_stairs.getBlockTextureFromSide(1); }
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.thePlayer;

		if (player.getDistance(x, y, z) < 8) {
			this.blockIcon = Blocks.acacia_stairs.getBlockTextureFromSide(1);
			minecraft.renderGlobal.markBlockForUpdate(x, y, z);
		} else {
			this.blockIcon = Blocks.air.getBlockTextureFromSide(1);
			minecraft.renderGlobal.markBlockForUpdate(x, y, z);
		}
	}

}
