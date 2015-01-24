package net.dimensionshiftinfectus.mod.gui;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.container.ContainerItemWasher;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityItemWasher;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiItemWasher extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(DimensionShiftInfectus.MODID, "textures/gui/itemWasher.png");

	private TileEntityItemWasher itemwasher;

	public GuiItemWasher(InventoryPlayer par1InventoryPlayer, TileEntityItemWasher itemwasher) {
		super(new ContainerItemWasher(par1InventoryPlayer, itemwasher));
		this.itemwasher = itemwasher;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String name = this.itemwasher.isInvNameLocalized() ? this.itemwasher.getInvName() : I18n.format(this.itemwasher.getInvName(), DimensionShiftInfectus.instance);
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.itemwasher.isBurning()) {

			i1 = this.itemwasher.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = this.itemwasher.getWashProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);

		i1 = this.itemwasher.getWaterScaled(53);
		this.drawTexturedModalRect(k + 13, l + 70 - i1, 176, 85 - i1, 18, i1);
	}
}