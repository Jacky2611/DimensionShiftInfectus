package net.dimensionshiftinfectus.mod.render;

import net.dimensionshift.mod.DimensionShift;
import net.dimensionshift.mod.model.ModelGlassJar;
import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.model.ModelParasiteMaggot;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityGlassJarParasiteMaggot;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class TileEntityRenderGlassJarParasiteMaggot extends TileEntitySpecialRenderer {

	public static final ResourceLocation textureGlassJar = new ResourceLocation(DimensionShift.MODID, "textures/model/blockGlassJar.png");
	public static final ResourceLocation textureParasiteMaggot = new ResourceLocation(DimensionShiftInfectus.MODID, "textures/entity/entityParasiteMaggot.png");
	public static final ResourceLocation textureParasiteMaggotBurned = new ResourceLocation(DimensionShiftInfectus.MODID, "textures/entity/entityParasiteMaggotBurned.png");

	private ModelGlassJar modelGlassJar;
	private ModelParasiteMaggot modelParasiteMaggot;

	public TileEntityRenderGlassJarParasiteMaggot() {
		this.modelGlassJar = new ModelGlassJar();
		this.modelParasiteMaggot = new ModelParasiteMaggot();

	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float var8) {

		if(tileentity.getWorldObj()!=null){
			tileentity.getWorldObj().scheduleBlockUpdate(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, DimensionShiftInfectus.blockGlassJarParasiteMaggot, 10);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glDisable(GL11.GL_CULL_FACE);
						
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureGlassJar);
			//GL11.glEnable(GL11.GL_BLEND);

			GL11.glPushMatrix();

				this.modelGlassJar.renderModel(0.0626F);// one pixel in float

			GL11.glPopMatrix();
			
			
			//GL11.glDisable(GL11.GL_BLEND);
			
			GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
		
		
		GL11.glPushMatrix();
			//Glass Jar position and rotation
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0, 0, 1);


			GL11.glPushMatrix();

				//Roationg Worm
				GL11.glTranslatef(0, 0.38F, 0);
				GL11.glScalef(0.7F, 0.7F, 0.7F);

				//
			    GL11.glRotated(((TileEntityGlassJarParasiteMaggot)tileentity).rotation, 0.0D, 1.0D, 0.0D);
			    			    
			    FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureParasiteMaggot);

			    //tileentity = tileentity.getWorldObj().getTileEntity((int)Math.round(x), (int)Math.round(y), (int)Math.round(z));
			    if(tileentity !=null && ((TileEntityGlassJarParasiteMaggot)tileentity).isDead()){
			    	
			    		//Burned to death
					    FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureParasiteMaggotBurned);
			    	
			    }
			    this.modelParasiteMaggot.renderModel(0.0626F);

			GL11.glPopMatrix();
		GL11.glPopMatrix();
		



	}

}
