package net.dimensionshiftinfectus.mod.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WashingRecipes {
	private static final WashingRecipes washingBase = new WashingRecipes();
	/** The list of washing results. */
	private Map<ItemStack, ItemStack> washingList = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	public static WashingRecipes washing() {
		return washingBase;
	}

	private WashingRecipes() {
		this.addBlocks(DimensionShiftInfectus.blockInfectedDirt, new ItemStack(Blocks.dirt), 0.1F);
		this.addBlocks(DimensionShiftInfectus.blockInfectedGrass, new ItemStack(Blocks.grass), 0.2F);

	}

	public void addBlocks(Block block, ItemStack itemstack, float xp) {
		this.addItems(Item.getItemFromBlock(block), itemstack, xp);
	}

	public void addItems(Item item, ItemStack itemstack, float xp) {
		this.addToWashingList(new ItemStack(item, 1, 32767), itemstack, xp);
	}

	public void addToWashingList(ItemStack itemstack1, ItemStack itemstack2, float xp) {
		this.washingList.put(itemstack1, itemstack2);
		this.experienceList.put(itemstack2, Float.valueOf(xp));
	}

	/**
	 * Returns the washing result of an item.
	 */
	public ItemStack getWashingResult(ItemStack itemstack) {
		Iterator iterator = this.washingList.entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext()) {
				return null;
			}

			entry = (Entry) iterator.next();
		} while (!this.func_151397_a(itemstack, (ItemStack) entry.getKey()));

		return (ItemStack) entry.getValue();
	}

	private boolean func_151397_a(ItemStack itemstack1, ItemStack itemstack2) {
		return itemstack2.getItem() == itemstack1.getItem() && (itemstack2.getItemDamage() == 32767 || itemstack2.getItemDamage() == itemstack1.getItemDamage());
	}

	public Map getwashingList() {
		return this.washingList;
	}

	// returns xp of an
	public float getXP(ItemStack itemstack) {
		float ret = itemstack.getItem().getSmeltingExperience(itemstack);
		if (ret != -1) return ret;

		Iterator iterator = this.experienceList.entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext()) {
				return 0.0F;
			}

			entry = (Entry) iterator.next();
		} while (!this.func_151397_a(itemstack, (ItemStack) entry.getKey()));

		return ((Float) entry.getValue()).floatValue();
	}
}