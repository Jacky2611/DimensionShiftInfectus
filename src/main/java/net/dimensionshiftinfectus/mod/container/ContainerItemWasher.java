package net.dimensionshiftinfectus.mod.container;

import net.dimensionshiftinfectus.mod.recipe.WashingRecipes;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityItemWasher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerItemWasher extends Container {
	private TileEntityItemWasher itemwasher;

	public int lastWater;
	public int lastBurnTime;
	public int lastWashTime;

	public ContainerItemWasher(InventoryPlayer par1InventoryPlayer, TileEntityItemWasher slotnumberTileEntityItemWasher) {
		this.itemwasher = slotnumberTileEntityItemWasher;

		this.addSlotToContainer(new Slot(slotnumberTileEntityItemWasher, 0, 34, 53));
		this.addSlotToContainer(new Slot(slotnumberTileEntityItemWasher, 1, 56, 53));
		this.addSlotToContainer(new Slot(slotnumberTileEntityItemWasher, 2, 56, 17));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, slotnumberTileEntityItemWasher, 3, 116, 35));
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	/*
	 * public void addCraftingToCrafters(ICrafting par1ICrafting) {
	 * super.addCraftingToCrafters(par1ICrafting);
	 * par1ICrafting.sendProgressBarUpdate(this, 0,
	 * this.tileFurnace.furnaceCookTime);
	 * par1ICrafting.sendProgressBarUpdate(this, 1,
	 * this.tileFurnace.furnaceBurnTime);
	 * par1ICrafting.sendProgressBarUpdate(this, 2,
	 * this.tileFurnace.currentItemBurnTime); }
	 */
	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastWashTime != this.itemwasher.washTime) {
				icrafting.sendProgressBarUpdate(this, 0, this.itemwasher.washTime);
			}

			if (this.lastBurnTime != this.itemwasher.burnTime) {
				icrafting.sendProgressBarUpdate(this, 1, this.itemwasher.burnTime);
			}

			if (this.lastWater != this.itemwasher.water) {
				icrafting.sendProgressBarUpdate(this, 2, this.itemwasher.water);
			}
		}

		this.lastWashTime = this.itemwasher.washTime;
		this.lastBurnTime = this.itemwasher.burnTime;
		this.lastWater = this.itemwasher.water;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int slotnumber) {
		if (par1 == 0) {
			this.itemwasher.washTime = slotnumber;
		}

		if (par1 == 1) {
			this.itemwasher.burnTime = slotnumber;
		}

		if (par1 == 2) {
			this.itemwasher.water = slotnumber;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.itemwasher.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotnumber) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotnumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotnumber == 2) {
				if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotnumber != 1 && slotnumber != 0) {
				if (WashingRecipes.washing().getWashingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
						return null;
					}
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (itemstack1.isItemEqual(new ItemStack(Items.water_bucket))) {

					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (slotnumber >= 4 && slotnumber < 31) {
					if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
						return null;
					}
				} else if (slotnumber >= 31 && slotnumber < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}