package net.dimensionshiftinfectus.mod.item;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.entity.EntityArtificialMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebugItem extends Item {

	public ItemDebugItem(int maxStackSize, String name) {
		setMaxStackSize(maxStackSize);
		setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
		setUnlocalizedName(name);
		setTextureName(DimensionShiftInfectus.MODID + ":" + name);

	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		if (!world.isRemote) {
			Entity entity = new EntityArtificialMob(world);
			entity.setPosition(x+0.5, y + 1, z+0.5);

			world.spawnEntityInWorld(entity);

		}
		return true;
	}

}