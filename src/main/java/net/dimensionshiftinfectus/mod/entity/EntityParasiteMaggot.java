package net.dimensionshiftinfectus.mod.entity;

import java.util.Random;

import net.dimensionshift.mod.DimensionShift;
import net.dimensionshift.mod.ai.EntityAIAttackAndInfect;
import net.dimensionshift.mod.ai.EntityAIFood;
import net.dimensionshift.mod.api.IMobContagious;
import net.dimensionshift.mod.api.IMobHungry;
import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityParasiteMaggot extends EntityMob implements IMobHungry, IMobContagious {

	public int range = 20;
	public int hunger = 0;
	public int maxHunger = 1000;
	
	public String illnessName = "parasitemaggot.infected";

	public int breeding = 0;
	public int breedinglayegg = 100;

	private static final IEntitySelector attackEntitySelector = new IEntitySelector() {
		/**
		 * Return whether the specified entity is applicable to this filter.
		 */
		@Override
		public boolean isEntityApplicable(Entity par1Entity) {
			return par1Entity instanceof EntityLivingBase && ((EntityLivingBase) par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
		}
	};

	public EntityParasiteMaggot(World par1World) {
		super(par1World);

		this.tasks.addTask(5, new EntityAIAttackAndInfect(this, EntityPlayer.class, 1.0D, this.illnessName, 60, true));
		this.tasks.addTask(5, new EntityAIAttackAndInfect(this, EntityLiving.class, 1.0D, this.illnessName, 60, true));

		this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));

		this.targetTasks.addTask(2, new EntityAIFood(this, 20, 1.2D, 70, 1, 2));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));

		this.setSize(0.55F, 0.25F);
		this.experienceValue = 1;
		this.setHealth((float) this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue());
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	/*
	 * @Override public boolean attackEntityFrom(DamageSource par1DamageSource,
	 * float par2){ if(par2<0.2){ return true; } else { return false; } }
	 */

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
		if (!this.worldObj.isRemote && this.hunger > 0) {
			this.hunger--;
		}

	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.9D);
	}

	/*
	 * @Override protected String getLivingSound() { return
	 * "yourmod:YourSound";//this refers to:yourmod/sound/YourSound }
	 * 
	 * @Override protected String getHurtSound() { return
	 * "yourmod:optionalFile.YourSound";//this refers
	 * to:yourmod/sound/optionalFile/YourSound }
	 * 
	 * @Override protected String getDeathSound() { return
	 * "yourmod:optionalFile.optionalFile2.YourSound";//etc. }
	 */

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public int getHunger() {
		return this.hunger;
	}

	@Override
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	@Override
	public int getFullHunger() {
		return this.maxHunger;
	}
	
	@Override
    public boolean interact(EntityPlayer player){
		if(player.inventory.getCurrentItem().isItemEqual(new ItemStack(DimensionShift.blockGlassJar))){
	        
			Random random = new Random();
			if(random.nextInt(20)<=0){
				NBTTagCompound nbt;

				if (player.getEntityData() != null) {
					nbt = player.getEntityData();
				} else {
					nbt = new NBTTagCompound();
				}
				nbt.setBoolean("dimensionshift.infectus.illness." + this.illnessName, true);
				this.setDead();
			} else {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(DimensionShiftInfectus.blockGlassJarParasiteMaggot));
			}
	        return true;
		}
		return false;
	}
	
	@Override
    protected void dropFewItems(boolean hitByPlayer, int lootingEnchantmentLevel)
    {
        Item slime_ball = Items.slime_ball;
        Item dead = DimensionShiftInfectus.itemDeadParasiteMaggot;
        Item burned = DimensionShiftInfectus.itemBurnedParasiteMaggot;

            int j = this.rand.nextInt(2);

            if (lootingEnchantmentLevel > 0)
            {
                j += this.rand.nextInt(lootingEnchantmentLevel + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(slime_ball, 1);
            }
            
            j = this.rand.nextInt(1);
            
            if (lootingEnchantmentLevel > 0)
            {
                j += this.rand.nextInt(lootingEnchantmentLevel + 1);
            }
            
            if(j<this.rand.nextInt(3)){
            	
            	if(this.isBurning()){
            		this.dropItem(burned, 1);
            	} else{
            		this.dropItem(dead, 1);
            	}
            }
            
    }

}