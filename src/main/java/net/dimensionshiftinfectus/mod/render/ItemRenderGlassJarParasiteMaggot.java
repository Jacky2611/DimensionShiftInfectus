package net.dimensionshiftinfectus.mod.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemRenderGlassJarParasiteMaggot implements IItemRenderer {

	TileEntitySpecialRenderer render;
	private TileEntity tileentity;

	public ItemRenderGlassJarParasiteMaggot(TileEntitySpecialRenderer render, TileEntity tileentity) {
		this.render = render;
		this.tileentity = tileentity;

	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		GL11.glScalef(1.4F, 1.4F, 1.4F);
		//Enables Transparency
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		this.render.renderTileEntityAt(tileentity, 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

	}
}	
