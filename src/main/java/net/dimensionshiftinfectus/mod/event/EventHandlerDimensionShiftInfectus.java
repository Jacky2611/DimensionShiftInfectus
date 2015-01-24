package net.dimensionshiftinfectus.mod.event;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.illness.IllnessParasiteMaggot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerDimensionShiftInfectus {

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		if (event.entityLiving.getEntityData().getBoolean("dimensionshift.infectus.illness.parasitemaggot.infected")) IllnessParasiteMaggot.living(event.entityLiving);

		// do something if its the player
		if (event.entity instanceof EntityPlayer) {
			// triggering ender infectus achievement if he is in the Infectus
			if (((EntityPlayer)event.entity).dimension == DimensionShiftInfectus.dimensionInfectusId) {
				((EntityPlayer)event.entity).addStat(DimensionShiftInfectus.achievementInfectusDimension, 1);
			}

			
			
			
		}
	}

}
