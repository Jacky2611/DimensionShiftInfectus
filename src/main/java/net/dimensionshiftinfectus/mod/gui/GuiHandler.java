package net.dimensionshiftinfectus.mod.gui;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.container.ContainerItemWasher;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityItemWasher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null) {
			switch (ID) {
				case DimensionShiftInfectus.guiIdItemWasher:
					if (entity instanceof TileEntityItemWasher) {
						return new ContainerItemWasher(player.inventory, (TileEntityItemWasher) entity);
					}
			}

		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null) {
			switch (ID) {
				case DimensionShiftInfectus.guiIdItemWasher:
					if (entity instanceof TileEntityItemWasher) {
						return new GuiItemWasher(player.inventory, (TileEntityItemWasher) entity);
					}
			}

		}

		return null;
	}

}
