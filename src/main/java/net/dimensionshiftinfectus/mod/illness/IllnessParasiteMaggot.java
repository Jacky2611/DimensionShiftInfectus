package net.dimensionshiftinfectus.mod.illness;

import scala.util.Random;
import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class IllnessParasiteMaggot {

	public static void living(EntityLivingBase entity) {
		int time = 0;
		boolean isPlayer = (entity instanceof EntityPlayer);
		Random rand = new Random();
		
		int timeState1 = 				750;
		int timeState2 = timeState1+	750;
		int timeState3 = timeState2+	750;
		int timeState4 = timeState3+	750;
		
		if(!entity.getEntityData().hasKey("dimensionshift.infectus.illness.parasitemaggot.time")){
			entity.getEntityData().setInteger("dimensionshift.infectus.illness.parasitemaggot.time", 0);
		} else {
			time = entity.getEntityData().getInteger("dimensionshift.infectus.illness.parasitemaggot.time");
		}
		
		
		if(entity.isPotionActive(Potion.regeneration)){
			time -= (rand.nextInt(3)+1);
		}
		if(entity.isPotionActive(Potion.heal)){
			time -= timeState1/2;
		}
		
		entity.getEntityData().setInteger("dimensionshift.infectus.illness.parasitemaggot.time", time+1);
		
		System.out.println("infected time: " + time);
		//Achievement
		if(isPlayer && time>timeState1){
			((EntityPlayer)entity).addStat(DimensionShiftInfectus.achievementInfectionParasiteMaggot, 1);
		}
		
		/*
		if(rand.nextInt(1000)<2){
			entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 180));
			System.out.println("POTION!");
		}
		
		*/
		
		if(time <= timeState1){
			
			
			if(rand.nextInt(1200)<2){
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 180));
				System.out.println("POTION!");
			}
			
			
		} else if(time <= timeState2){
			
			
			if(rand.nextInt(1000)<2){
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
				System.out.println("POTION!");
			}
			if(rand.nextInt(1000)<2){
				entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 50));
				System.out.println("POTION!");
			}
			
			
		} else if(time <= timeState3){
			
				
		} else if(time <= timeState4){
			
			
		} else {
			//entity.setHealth(0);
			
		}

	}

}
