package net.dimensionshiftinfectus.mod.render;

import net.dimensionshiftinfectus.mod.model.ModelParasiteMaggot;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderParasiteMaggot extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("dimensionshiftinfectus:textures/entity/entityParasiteMaggot.png"); // refers
																																				// to:assets/yourmod/textures/entity/yourtexture.png

	public RenderParasiteMaggot(ModelParasiteMaggot par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return texture;
	}
}