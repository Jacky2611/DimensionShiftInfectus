package net.dimensionshiftinfectus.mod.block;

import java.util.Random;

import net.dimensionshift.mod.DimensionShift;
import net.dimensionshift.mod.api.ICustomItemBlock;
import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.entity.EntityParasiteMaggot;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityGlassJarParasiteMaggot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGlassJarParasiteMaggot extends BlockContainer implements ICustomItemBlock{

	public BlockGlassJarParasiteMaggot(Material material) {
		super(material);
		this.setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
		this.setBlockName("blockGlassJarParasiteMaggot");
		this.setTickRandomly(true);
		
		this.setBlockBounds(1F / 16F * 3F, 0F, 1F / 16F * 3F, 1F - 1F / 16F * 3F, 1F - 1F / 16F * 2F, 1F - 1F / 16F * 3F);

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityGlassJarParasiteMaggot();
	}
	
	
    public void updateTick(World world, int x, int y, int z, Random random) {
    	if(Block.isEqualTo(world.getBlock(x, y-1, z), Blocks.fire)){
    		if(world.getTileEntity(x, y, z) instanceof TileEntityGlassJarParasiteMaggot){
    			TileEntityGlassJarParasiteMaggot tileEntity = (TileEntityGlassJarParasiteMaggot)world.getTileEntity(x, y, z);
    				if(!tileEntity.isDead()){
    				tileEntity.setBurned(true);
    				tileEntity.setDead(true);
    				world.markBlockForUpdate(x, y, z);
    			}
    		}
    	}
    }


	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getStackSize() {
		return 1;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		if(!world.isRemote){
			
			if(world.getTileEntity(x, y, z)!=null && (world.getTileEntity(x, y, z) instanceof TileEntityGlassJarParasiteMaggot)){
				TileEntityGlassJarParasiteMaggot tileentity= (TileEntityGlassJarParasiteMaggot)world.getTileEntity(x, y, z);
				if(!tileentity.isDead()){
				if(player.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.potionitem, 1, 12)) || player.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.potionitem, 1, 4))){
			
					if(player.inventory.getCurrentItem().stackSize>1){
						player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
					} else {
						player.inventory.getCurrentItem().stackSize--;
						player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.glass_bottle));
					}
			
					tileentity.setDead(true);
					return true;
				}
				}
			}
		}
		return false;
    }
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(!world.isRemote){
			
			System.out.println("TILE ENTITY EXISTS: " + world.getTileEntity(x, y, z)!=null);
			System.out.println("TILE ENTITY INSTANCE OF TEFJPM: " + ((world.getTileEntity(x, y, z) instanceof TileEntityGlassJarParasiteMaggot)));

			if(world.getTileEntity(x, y, z)!=null && (world.getTileEntity(x, y, z) instanceof TileEntityGlassJarParasiteMaggot)){
				System.out.println("After first if");
				if(((TileEntityGlassJarParasiteMaggot)world.getTileEntity(x, y, z)).isDead()){
					System.out.println("DROPPING DEAD");
					//DEAD
					if(((TileEntityGlassJarParasiteMaggot)world.getTileEntity(x, y, z)).isBurned()){
						world.spawnEntityInWorld(new EntityItem(world, x+0.5, y+0.1, z+0.5, new ItemStack(DimensionShiftInfectus.itemBurnedParasiteMaggot)));
						
						
					} else {
					
						world.spawnEntityInWorld(new EntityItem(world, x+0.5, y+0.1, z+0.5, new ItemStack(DimensionShiftInfectus.itemDeadParasiteMaggot)));

					}
				} else {
					Entity entity = new EntityParasiteMaggot(world);
					entity.setPosition(x+0.5, y+0.1, z+0.5);
					world.spawnEntityInWorld(entity);
				}
				
			}

		}
	}

	
	@Override
	public Item getItemDropped(int par1, Random random, int par2) {
		return Item.getItemFromBlock(DimensionShift.blockGlassJar);

	}


}
