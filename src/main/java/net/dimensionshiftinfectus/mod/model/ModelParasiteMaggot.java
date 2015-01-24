package net.dimensionshiftinfectus.mod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelParasiteMaggot extends ModelBase {
	ModelRenderer Head;
	ModelRenderer Body;
	ModelRenderer Tail;
	ModelRenderer TailEnd;

	public ModelParasiteMaggot() {
		this.textureHeight = 32;
		this.textureWidth = 32;

		this.Head = new ModelRenderer(this, 0, 11);
		this.Head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2);
		this.Head.setRotationPoint(0.0F, 23.0F, -2.0F);

		this.Body = new ModelRenderer(this, 0, 0);
		this.Body.addBox(-1.5F, -2.0F, 0.0F, 3, 3, 4);
		this.Body.setRotationPoint(0.0F, 23.0F, -2.0F);

		this.Tail = new ModelRenderer(this, 0, 7);
		this.Tail.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2);
		this.Tail.setRotationPoint(0.0F, 23.0F, 2.0F);

		this.TailEnd = new ModelRenderer(this, 8, 7);
		this.TailEnd.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1);
		this.TailEnd.setRotationPoint(0.0F, 23.0F, 4.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glTranslatef(0, 0.45F, 0);
		GL11.glBlendFunc(770, 771);

		GL11.glScalef(0.7F, 0.7F, 0.7F + f1 * 2.0F);
		this.Head.render(f5);
		this.Body.render(f5);
		this.Tail.render(f5);
		this.TailEnd.render(f5);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}
	
	
	public void renderModel(float f){

		this.Head.render(f);
		this.Body.render(f);
		this.Tail.render(f);
		this.TailEnd.render(f);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	}
}