package net.dimensionshiftinfectus.mod.block;

import java.util.Random;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityItemWasher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockItemWasher extends BlockContainer {

	private final boolean isActive;
	private static boolean keepInventory;

	private Random random = new Random();

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	private IIcon iconTopRotate1;
	private IIcon iconTopRotate2;
	private IIcon iconTopRotate3;
	private IIcon iconTopRotate4;

	public BlockItemWasher(boolean isActive) {
		super(Material.rock);

		this.isActive = isActive;

	}

	@Override
	@SideOnly(Side.CLIENT)
	// textures
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockItemWasher/side");
		this.iconFront = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + (this.isActive ? "blockItemWasher/front_active" : "blockItemWasher/front_idle"));
		this.iconTopRotate1 = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockItemWasher/top_rotate1");
		this.iconTopRotate2 = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockItemWasher/top_rotate2");
		this.iconTopRotate3 = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockItemWasher/top_rotate3");
		this.iconTopRotate4 = iconRegister.registerIcon(DimensionShiftInfectus.MODID + ":" + "blockItemWasher/top_rotate4");

	}

	@Override
	@SideOnly(Side.CLIENT)
	// texture direction facing
	public IIcon getIcon(int side, int metadata) {

		/*
		 * return side == 1 ? this.iconTopRotate1:(side == metadata ?
		 * this.iconFront : this.blockIcon);
		 */
		return side == 1 ? (metadata == 3 ? this.iconTopRotate1 : (metadata == 4 ? this.iconTopRotate2 : (metadata == 5 ? this.iconTopRotate4 : this.iconTopRotate3))) : (side == metadata ? this.iconFront : this.blockIcon);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (this.isActive) {
			int l = world.getBlockMetadata(x, y, z);
			float f = x + 0.5F;
			float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;

			if (l == 4) {
				world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if (l == 5) {
				world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if (l == 2) {
				world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
			} else if (l == 3) {
				world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	// always dropping not active version
	@Override
	public Item getItemDropped(int par1, Random random, int par2) {
		return Item.getItemFromBlock(DimensionShiftInfectus.blockItemWasherIdle);

	}

	// getting coordinates when added
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMetadata) {
		if (!keepInventory) {// if block is not broken by update status function

			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity != null) {
				TileEntityItemWasher itemWasher = (TileEntityItemWasher) world.getTileEntity(x, y, z);
				for (int i = 0; i < itemWasher.getSizeInventory(); i++) {
					ItemStack itemstack = itemWasher.getStackInSlot(i);// get
																		// itemstack
																		// from
																		// slot
					if (itemstack != null) {// if there is an itemstack

						float f = this.random.nextFloat() * 0.8F + 0.1F;// random
																		// position
																		// for
																		// item
																		// drop....
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;// random
																			// position
																			// for
																			// item
																			// drop....
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;// random
																			// position
																			// for
																			// item
																			// drop....

						while (itemstack.stackSize > 0) {
							int j = this.random.nextInt(21) + 10; // How many
																	// item it
																	// throws
																	// out a
																	// tick

							if (j > itemstack.stackSize) { // can't throw out
															// more than max
															// available
															// items...
								j = itemstack.stackSize;
							}

							itemstack.stackSize -= j; // removing item from gui

							// creating item
							EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

							// nbt
							if (itemstack.hasTagCompound()) {
								item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							// adding motion to item...
							float f3 = 0.05F;
							item.motionX = (float) this.random.nextGaussian() * f3;
							item.motionY = (float) this.random.nextGaussian() * f3 + 0.2F;
							item.motionZ = (float) this.random.nextGaussian() * f3;

							// spawning item
							world.spawnEntityInWorld(item);
						}

					}
				}
				world.func_147453_f(x, y, z, oldBlock);

			}

		}
		super.breakBlock(world, x, y, z, oldBlock, oldMetadata);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			// getting ids of blocks surround
			Block a = world.getBlock(x, y, z - 1);
			Block b = world.getBlock(x, y, z + 1);
			Block c = world.getBlock(x - 1, y, z);
			Block d = world.getBlock(x + 1, y, z);

			byte b0 = 3;

			// checking if blocks to your front/right/left/back are opaqued
			if (a.isOpaqueCube() && !b.isOpaqueCube()) {
				b0 = 3;
			}
			if (b.isOpaqueCube() && !a.isOpaqueCube()) {
				b0 = 2;
			}
			if (c.isOpaqueCube() && !d.isOpaqueCube()) {
				b0 = 5;
			}
			if (d.isOpaqueCube() && !c.isOpaqueCube()) {
				b0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);

		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		} else {
			player.openGui(DimensionShiftInfectus.instance, DimensionShiftInfectus.guiIdItemWasher, world, x, y, z);

		}

		return true;

	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor Block
	 */

	// creating TileEntity
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityItemWasher();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemstack) {
		int l = MathHelper.floor_double(entityLivingBase.rotationYaw * 4F / 360F + 0.5D) & 3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		if (itemstack.hasDisplayName()) {
			((TileEntityItemWasher) world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
		}

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityItemWasher();
	}

	public static void updateBlockType(boolean active, World world, int x, int y, int z) {
		int i = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);

		keepInventory = true;

		if (active) {
			world.setBlock(x, y, z, DimensionShiftInfectus.blockItemWasherActive);
		} else {
			world.setBlock(x, y, z, DimensionShiftInfectus.blockItemWasherIdle);

		}

		keepInventory = false;

		world.setBlockMetadataWithNotify(x, y, z, i, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}

	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
	}

	// idpicked???????

}
