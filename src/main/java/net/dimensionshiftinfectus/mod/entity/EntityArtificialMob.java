package net.dimensionshiftinfectus.mod.entity;

import java.util.Random;

import net.dimensionshift.mod.api.IMobAnimation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class EntityArtificialMob extends EntityMob implements IMobAnimation{

	private int animateTime=400;
	private float animationSize=1;
	boolean animationChanged=false;

	public EntityArtificialMob(World world) {
		super(world);
		this.setSize(4F, 4F);
		this.setHealth((float) this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue());

	}

	@Override
	public void onUpdate() {
		super.onUpdate();

	}
	

	@Override
	public void animate() {
		this.animateTime++;
		
		animationChanged = false;
		
		
		double time = 2;
		
		if(this.animateTime>100*time && this.animateTime<110*time){
			this.animationSize-=0.005;
			
			this.animationChanged=true;
		}
		if(this.animateTime>=110*time && this.animateTime<120*time){
			this.animationSize+=0.005;
			this.animationChanged=true;
		}
		
		if(this.animateTime>120*time && this.animateTime<130*time){
			this.animationSize-=0.005;
			
			this.animationChanged=true;
		}
		if(this.animateTime>=130*time && this.animateTime<140*time){
			this.animationSize+=0.005;
			this.animationChanged=true;
		}
		
		System.out.println("animation time is: " + this.animateTime);

		if(this.animationChanged){
			GL11.glTranslated(0, (1-this.animationSize)*1.5, 0);
			GL11.glScalef(this.animationSize, this.animationSize, this.animationSize);
			
			
		}
		
		if(this.animateTime>140*time){
			Random random = new Random();
			this.animateTime=random.nextInt(20);
		}
	}

	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(10D);
	}
	
	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		
	}


}
