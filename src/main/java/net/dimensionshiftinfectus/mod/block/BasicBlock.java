package net.dimensionshiftinfectus.mod.block;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block {

	public BasicBlock(Material material, String name) {
		super(material);
		this.setBlockName(name);
		this.textureName = DimensionShiftInfectus.MODID + ":" + name;
		this.setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
	}

}
