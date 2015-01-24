package net.dimensionshiftinfectus.mod.block;

import java.util.Random;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockInfectedGrass extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockInfectedGrass() {
		super(Material.grass);
		this.setTickRandomly(true);
		this.setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int p_149691_2_) {
		return side == 1 ? this.iconTop : (side == 0 ? DimensionShiftInfectus.blockInfectedDirt.getBlockTextureFromSide(side) : this.blockIcon);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote) {
			if ((world.provider.dimensionId == 9 && world.getBlockLightValue(x, y + 1, z) > 15) || (world.provider.dimensionId != 9 && world.getBlockLightValue(x, y + 1, z) > 6)) {
				world.setBlock(x, y, z, Blocks.dirt);
			} else if (world.getBlockLightValue(x, y + 1, z) <= 6) {
				for (int l = 0; l < 4; ++l) {
					int i1 = x + rand.nextInt(3) - 1;
					int j1 = y + rand.nextInt(5) - 3;
					int k1 = z + rand.nextInt(3) - 1;

					if ((world.getBlock(i1, j1, k1) == Blocks.dirt || world.getBlock(i1, j1, k1) == DimensionShiftInfectus.blockInfectedDirt) && world.getBlockMetadata(i1, j1, k1) == 0 && world.getBlockLightValue(i1, j1 + 1, k1) <= 6 && world.getBlockLightOpacity(i1, j1 + 1, k1) <= 1) {
						world.setBlock(i1, j1, k1, this);
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return DimensionShiftInfectus.blockInfectedDirt.getItemDropped(0, p_149650_2_, p_149650_3_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int side) {
		if (side == 1) {
			return this.iconTop;
		} else if (side == 0) {
			return DimensionShiftInfectus.blockInfectedDirt.getBlockTextureFromSide(side);
		} else {
			return this.blockIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iiconRegister) {
		this.blockIcon = iiconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockInfectedGrass_side");
		this.iconTop = iiconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockInfectedGrass_top");

	}

	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		if (rand.nextInt(10) < 2) {
			world.spawnParticle("townaura", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

}
