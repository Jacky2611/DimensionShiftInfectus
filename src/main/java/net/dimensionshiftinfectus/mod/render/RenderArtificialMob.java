package net.dimensionshiftinfectus.mod.render;

import net.dimensionshift.mod.api.DimensionShiftModelBase;
import net.dimensionshift.mod.api.IMobAnimation;
import net.dimensionshiftinfectus.mod.entity.EntityArtificialMob;
import net.dimensionshiftinfectus.mod.model.ModelArtificialMob;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderArtificialMob extends RenderEntity {
	
	private static ResourceLocation texture;// = new ResourceLocation("dimensionshiftinfectus:textures/entity/entityParasiteMaggot.png"); // refers to:assets/yourmod/textures/entity/yourtexture.png
	private DimensionShiftModelBase model;
	private double scalex;
	private double scaley;
	private double scalez;

	
	public RenderArtificialMob(ModelArtificialMob model, ResourceLocation texture, float shadowSize, double scalex, double scaley, double scalez) {
		this.shadowSize=shadowSize;
		this.texture=texture;
		this.model = model;
		this.scalex = scalex;
		this.scaley = scaley;
		this.scalez = scalez;
		
	}
	
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
    {
        GL11.glPushMatrix();
        	GL11.glTranslated(x, y+1.5F*this.scaley, z);
			GL11.glRotatef(180, 0, 0, 1);

			GL11.glScaled(this.scalex, this.scaley, this.scalez);
        	
			if(entity instanceof IMobAnimation){
				((IMobAnimation) entity).animate();
			}
			
        	
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
			this.model.render(0.0626F);
			
			
			if(entity instanceof EntityArtificialMob){
				EntityArtificialMob aMob = (EntityArtificialMob) entity;
				
			}
        
        
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return texture;
    }
	
	/*
	private static final ResourceLocation texture = new ResourceLocation("dimensionshiftinfectus:textures/entity/entityParasiteMaggot.png"); // refers to:assets/yourmod/textures/entity/yourtexture.png
	

	public RenderArtificialMob(ModelArtificialMob par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return texture;
	}
	*/
}
