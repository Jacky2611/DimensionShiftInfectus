package net.dimensionshiftinfectus.mod.item;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.item.Item;

public class ItemBasic extends Item {

	public ItemBasic(int maxStackSize, String name) {
		setMaxStackSize(maxStackSize);
		setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
		setUnlocalizedName(name);
		setTextureName(DimensionShiftInfectus.MODID + ":" + name);

	}

}