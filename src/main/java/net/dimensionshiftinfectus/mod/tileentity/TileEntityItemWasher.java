package net.dimensionshiftinfectus.mod.tileentity;

import net.dimensionshift.mod.energy.DimensionFuel;
import net.dimensionshiftinfectus.mod.block.BlockItemWasher;
import net.dimensionshiftinfectus.mod.recipe.WashingRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityItemWasher extends TileEntity implements ISidedInventory {

	private String localizedName;

	// What slots can be accesed from which side?
	private static final int[] slots_top = new int[] { 2 };
	// first take out items from slot 1, than from slot number 0
	private static final int[] slots_bottom = new int[] { 0, 1 };
	private static final int[] slots_sides = new int[] { 3 };

	// How many Slots does Block have
	private ItemStack[] slots = new ItemStack[4];

	// stored Water
	public int water;// = 0;

	// full Water tank
	public final int maxWater = 2000;

	// energy that has to be used yet
	public int burnTime = 0;

	public int currentBurnTime = 0;

	// how long its already washing
	public int washTime;

	// needed to check if status has changed...
	public boolean activeLastTick = false;

	@Override
	public void updateEntity() {

		// Will be set to true if anything has been done this round. Otherwise
		// it will be false
		boolean active = burnTime > 0;

		boolean flag1 = false;

		// getting rid of stack with size 0
		if (this.slots[1] != null) {
			if (this.slots[1].stackSize == 0) {
				this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		if (this.slots[0] != null) {
			if (this.slots[0].stackSize == 0) {
				this.slots[0] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		if (this.slots[2] != null) {
			if (this.slots[2].stackSize == 0) {
				this.slots[2] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}

		// reducing fuel
		if (this.burnTime > 0) {
			--this.burnTime;
		}

		// filling up fuel
		if (!this.worldObj.isRemote) {

			if (this.burnTime == 0 && this.canWash() && this.water > 0) {
				this.currentBurnTime = this.burnTime = TileEntityFurnace.getItemBurnTime(this.slots[1]);

				if (this.burnTime > 0) {
					flag1 = true;

					if (this.slots[1] != null) {
						--this.slots[1].stackSize;

						if (this.slots[1].stackSize == 0) {
							this.slots[1] = slots[1].getItem().getContainerItem(slots[1]);
						}
					}
				}
			}
		}

		// washing
		if (this.burnTime > 0 && this.canWash()) {
			++this.washTime;

			if (this.washTime == 200) {
				this.washTime = 0;
				this.washItem();
				this.water -= 30;
				flag1 = true;
			}
		} else {
			this.washTime = 0;
		}

		// filling up water
		if (this.slots[0] != null && this.water != this.maxWater) {

			if (this.slots[0].isItemEqual(new ItemStack(Items.water_bucket))) {
				this.water += 100;
				this.slots[0] = new ItemStack(Items.bucket);
			}
		}

		// checking if status has changed
		if (active != this.activeLastTick) {
			BlockItemWasher.updateBlockType(active, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.activeLastTick = active;
		}
		if (flag1) {
			this.markDirty();
		}

	}

	private void washItem() {
		if (this.canWash()) {
			ItemStack itemstack = WashingRecipes.washing().getWashingResult(this.slots[2]);

			if (this.slots[3] == null) {
				this.slots[3] = itemstack.copy();
			} else if (this.slots[3].getItem() == itemstack.getItem()) {
				this.slots[3].stackSize += itemstack.stackSize; // Forge BugFix:
																// Results may
																// have multiple
																// items
			}

			--this.slots[2].stackSize;

			if (this.slots[2].stackSize <= 0) {
				this.slots[2] = null;
			}
		}

	}

	private boolean canWash() {
		if (this.slots[2] == null) {
			return false;
		} else if (water < 100) {
			return false;
		} else {
			ItemStack itemstack = WashingRecipes.washing().getWashingResult(this.slots[2]);
			if (itemstack == null) return false;

			if (this.slots[3] == null) return true;
			if (!this.slots[3].isItemEqual(itemstack)) return false;
			int result = slots[3].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.slots[3].getMaxStackSize(); // Forge
																									// BugFix:
																									// Make
																									// it
																									// respect
																									// stack
																									// sizes
																									// properly.
		}
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	public boolean isInvNameLocalized() {
		return this.localizedName != null && this.localizedName.length() > 0;
	}

	public String getInvName() {
		return this.isInvNameLocalized() ? this.localizedName : "container.itemWasher";
	}

	public void setGuiDisplayName(String displayName) {
		this.localizedName = displayName;

	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.slots[i] != null) {
			ItemStack itemstack;

			if (this.slots[i].stackSize <= j) {
				itemstack = this.slots[i];

				this.slots[i] = null;
				return itemstack;
			} else {
				itemstack = this.slots[i].splitStack(j);

				if (this.slots[i].stackSize == 0) {
					this.slots[i] = null;
				}
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.slots[i] != null) {
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack item) {

		if (i == 2) { // things to wash
			return true;
		} else if (i == 1) { // fuel
			return DimensionFuel.isItemFuel(item);
		} else if (i == 0) { // fuel
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		// saving energy
		nbt.setShort("water", (short) this.water);
		nbt.setShort("washTime", (short) this.washTime);
		nbt.setShort("currentBurnTime", (short) this.currentBurnTime);
		nbt.setShort("burnTime", (short) this.burnTime);

		// items
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbt.setTag("Items", list);

		if (this.isInvNameLocalized()) {
			nbt.setString("CustomName", this.localizedName);
		}
		nbt.setBoolean("ActiveLastTick", this.activeLastTick);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		// items
		NBTTagList list = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			if (b >= 0 && b < this.slots.length) {
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}

		}

		this.water = nbt.getShort("water");
		this.washTime = nbt.getShort("washTime");
		this.currentBurnTime = nbt.getShort("currentBurnTime");
		this.burnTime = nbt.getShort("burnTime");

		if (nbt.hasKey("CustomName")) {
			this.localizedName = nbt.getString("CustomName");
		}
		this.activeLastTick = nbt.getBoolean("ActiveLastTick");

	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack item, int side) {
		return this.isItemValidForSlot(i, item);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack item, int side) {
		// if extracts from down slot 1(fuel) than true
		return side == 0 || i == 1;
	}

	@SideOnly(Side.CLIENT)
	public int getWaterScaled(int heightWaterBar) {
		return this.water * heightWaterBar / this.maxWater;

	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int p_145955_1_) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = 200;
		}

		return this.burnTime * p_145955_1_ / this.currentBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public int getWashProgressScaled(int p_145953_1_) {
		return this.washTime * p_145953_1_ / 200;
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

}
