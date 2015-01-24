package net.dimensionshiftinfectus.mod.proxies;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.entity.EntityArtificialMob;
import net.dimensionshiftinfectus.mod.entity.EntityParasiteMaggot;
import net.dimensionshiftinfectus.mod.model.ModelArtificialMob;
import net.dimensionshiftinfectus.mod.model.ModelParasiteMaggot;
import net.dimensionshiftinfectus.mod.render.ItemRenderGlassJarParasiteMaggot;
import net.dimensionshiftinfectus.mod.render.RenderArtificialMob;
import net.dimensionshiftinfectus.mod.render.RenderParasiteMaggot;
import net.dimensionshiftinfectus.mod.render.TileEntityRenderGlassJarParasiteMaggot;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityGlassJarParasiteMaggot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class DimensionShiftInfectusClientProxy extends DimensionShiftInfectusCommonProxy {
	@Override
	public void registerProxies() {
		System.out.println("DSI-CLIENT PROXY LOADED");
		RenderingRegistry.registerEntityRenderingHandler(EntityParasiteMaggot.class, new RenderParasiteMaggot(new ModelParasiteMaggot(), 0.1F));
		// the last argument is the shadowsize
		RenderingRegistry.registerEntityRenderingHandler(EntityArtificialMob.class, new RenderArtificialMob(new ModelArtificialMob(),new ResourceLocation("dimensionshiftinfectus:textures/entity/entityParasiteMaggot.png"), 0.1F, 4, 4, 4));

		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlassJarParasiteMaggot.class, new TileEntityRenderGlassJarParasiteMaggot());

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(DimensionShiftInfectus.blockGlassJarParasiteMaggot), new ItemRenderGlassJarParasiteMaggot(new TileEntityRenderGlassJarParasiteMaggot(), new TileEntityGlassJarParasiteMaggot()));


	}
}
