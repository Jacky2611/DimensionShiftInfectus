package net.dimensionshiftinfectus.mod;

import net.dimensionshift.mod.DimensionShift;
import net.dimensionshift.mod.item.CustomItemBlock;
import net.dimensionshiftinfectus.mod.block.BasicBlock;
import net.dimensionshiftinfectus.mod.block.BlockAncientStoneBricks;
import net.dimensionshiftinfectus.mod.block.BlockGlassJarParasiteMaggot;
import net.dimensionshiftinfectus.mod.block.BlockInfectedGrass;
import net.dimensionshiftinfectus.mod.block.BlockItemWasher;
import net.dimensionshiftinfectus.mod.entity.EntityArtificialMob;
import net.dimensionshiftinfectus.mod.entity.EntityParasiteMaggot;
import net.dimensionshiftinfectus.mod.event.EventHandlerDimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.generation.OreGenerationDimensionShiftInfectus;
import net.dimensionshiftinfectus.mod.gui.GuiHandler;
import net.dimensionshiftinfectus.mod.item.ItemBasic;
import net.dimensionshiftinfectus.mod.item.ItemCustomPotion;
import net.dimensionshiftinfectus.mod.item.ItemDebugItem;
import net.dimensionshiftinfectus.mod.proxies.DimensionShiftInfectusCommonProxy;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityGlassJarParasiteMaggot;
import net.dimensionshiftinfectus.mod.tileentity.TileEntityItemWasher;
import net.dimensionshiftinfectus.mod.world.biome.BiomeGenWasteland;
import net.dimensionshiftinfectus.mod.world.biome.BiomeGenWastelandHilly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = DimensionShiftInfectus.MODID, name = "DimensionShiftInfectusAddon", version = DimensionShiftInfectus.VERSION, dependencies = "required-after:dimensionshift")
public class DimensionShiftInfectus {

	public static final String MODID = "dimensionshiftinfectus"; // setting
																	// MODID
	public static final String VERSION = "Experimental v0.001"; // setting
																// MODVersion

	@Instance(MODID)
	public static DimensionShiftInfectus instance;

	@SidedProxy(clientSide = "net.dimensionshiftinfectus.mod.proxies.DimensionShiftInfectusClientProxy", serverSide = "net.dimensionshiftinfectus.mod.proxies.DimensionShiftInfectusCommonProxy")
	public static DimensionShiftInfectusCommonProxy proxy;

	// BLOCKS
	public static Block blockInfectedGrass;
	public static Block blockInfectedDirt;

	public static Block blockAncientStoneBricks;

	public static Block blockPortal;
	public static Block blockPortalInactive;

	public static Block blockItemWasherIdle;
	public static Block blockItemWasherActive;
	
	public static Block blockGlassJarParasiteMaggot;

	// Items
	public static Item itemDebugItem;

	public static Item itemDeadParasiteMaggot;
	
	public static Item itemBurnedParasiteMaggot;

	public static Item itemParasiteMaggotsAsh;

	public static Item itemPotionLiquefiedParasiteMaggot;
	
	// BIOMES
	public static BiomeGenBase biomeWasteland;
	public static BiomeGenBase biomeWastelandHilly;

	// DIMENSIONS
	public static int dimensionInfectusId = 9;

	// ENTITIES
	public static int entityParasiteMaggotId = 0;
	public static int entityArtificialMobId = 1;

	// GUI
	public static final int guiIdItemWasher = 0;

	// Achievements
	public static Achievement achievementInfectusDimension;
	public static Achievement achievementInfectionParasiteMaggot;

	// CreativeTab
	public static CreativeTabs tabDimensionShiftInfectus = new CreativeTabs("tabDimensionshiftInfectus") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getIconItemStack() {
			return new ItemStack(DimensionShiftInfectus.blockInfectedGrass, 1, 0);
		}

		@Override
		public Item getTabIconItem() {
			return null;
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		event.getModMetadata().parent = "dimensionshift";

		// CONFIG
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		// loading the configuration from its file
		config.load();

		dimensionInfectusId = config.get(Configuration.CATEGORY_GENERAL, "InfectusDimensionId", 9).getInt();

		// saving the configuration to its file
		config.save();

		// BLOCKS
		blockInfectedGrass = new BlockInfectedGrass().setStepSound(Block.soundTypeGrass).setBlockName("blockInfectedGrass");
		GameRegistry.registerBlock(blockInfectedGrass, "blockInfectedGrass");
		blockInfectedDirt = new BasicBlock(Material.ground, "blockInfectedDirt").setStepSound(Block.soundTypeGrass).setResistance(5F);
		GameRegistry.registerBlock(blockInfectedDirt, "blockInfectedDirt");

		blockItemWasherIdle = new BlockItemWasher(false).setHardness(3.5F).setStepSound(Block.soundTypeMetal).setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus).setBlockName("blockItemWasher");
		blockItemWasherActive = new BlockItemWasher(true).setHardness(3.5F).setStepSound(Block.soundTypeMetal).setLightLevel(0.7F).setBlockName("blockItemWasherActive");
		GameRegistry.registerBlock(blockItemWasherIdle, "blockItemWasher");
		GameRegistry.registerBlock(blockItemWasherActive, "blockItemWasherActive");
		GameRegistry.registerTileEntity(TileEntityItemWasher.class, "tileEntityItemWasher");

		blockAncientStoneBricks = new BlockAncientStoneBricks(Material.ground, "blockAncientStoneBricks").setStepSound(Block.soundTypeStone).setResistance(10F);
		GameRegistry.registerBlock(blockAncientStoneBricks, "blockAncientStoneBricks");
		
		blockGlassJarParasiteMaggot = new BlockGlassJarParasiteMaggot(Material.glass).setStepSound(Block.soundTypeWood).setBlockName("blockGlassJarParasiteMaggot");
		GameRegistry.registerBlock(blockGlassJarParasiteMaggot, CustomItemBlock.class, "blockGlassJarParasiteMaggot");
		GameRegistry.registerTileEntity(TileEntityGlassJarParasiteMaggot.class, "tileEntityGlassJarParasiteMaggot");

		// ITEMS
		itemDebugItem = new ItemDebugItem(64, "itemDebugItem");
		GameRegistry.registerItem(itemDebugItem, "itemDebugItem");

		itemDeadParasiteMaggot = new ItemBasic(16, "itemDeadParasiteMaggot");
		GameRegistry.registerItem(itemDeadParasiteMaggot, "itemDeadParasiteMaggot");
		
		itemBurnedParasiteMaggot = new ItemBasic(16, "itemBurnedParasiteMaggot");
		GameRegistry.registerItem(itemBurnedParasiteMaggot, "itemBurnedParasiteMaggot");
		
		itemParasiteMaggotsAsh = new ItemBasic(16, "itemParasiteMaggotsAsh");
		GameRegistry.registerItem(itemParasiteMaggotsAsh, "itemParasiteMaggotsAsh");
		
		
		
		itemPotionLiquefiedParasiteMaggot = new ItemCustomPotion(1, "itemPotionLiquefiedParasiteMaggot", new PotionEffect[]{new PotionEffect(17, 1200)}, 0xB5CD3E);
		GameRegistry.registerItem(itemPotionLiquefiedParasiteMaggot, "itemPotionLiquefiedParasiteMaggot");

		
		
		// ENTITIES
		EntityRegistry.registerModEntity(EntityParasiteMaggot.class, "entityParasiteMaggot", entityParasiteMaggotId, this, 80, 1, true);
		EntityRegistry.registerModEntity(EntityArtificialMob.class, "entityArtificialMob", entityArtificialMobId, this, 80, 1, true);
		
		
		// BIOMES
		biomeWasteland = new BiomeGenWasteland(140);
		biomeWastelandHilly = new BiomeGenWastelandHilly(141);

		// DIMENSIONS
		DimensionManager.registerProviderType(DimensionShiftInfectus.dimensionInfectusId, net.dimensionshiftinfectus.mod.world.dimensions.WorldProviderInfectus.class, true);
		DimensionManager.registerDimension(DimensionShiftInfectus.dimensionInfectusId, DimensionShiftInfectus.dimensionInfectusId);

		// GUIHANDLER
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		
		
		// RECIPES
		GameRegistry.addRecipe(new ItemStack(blockItemWasherIdle), "xyx", "xzx", "xxx", // machine
																						// block
				'x', new ItemStack(DimensionShift.blockMachineBlock), 'y', new ItemStack(DimensionShift.blockGlassJar), 'z', new ItemStack(Blocks.furnace));

		GameRegistry.addShapelessRecipe(new ItemStack(itemParasiteMaggotsAsh), new ItemStack(itemBurnedParasiteMaggot), new ItemStack(itemBurnedParasiteMaggot));

		GameRegistry.addShapelessRecipe(new ItemStack(itemPotionLiquefiedParasiteMaggot), new ItemStack(itemParasiteMaggotsAsh), new ItemStack(Items.potionitem, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(itemPotionLiquefiedParasiteMaggot), new ItemStack(itemParasiteMaggotsAsh), new ItemStack(Items.potionitem, 1, 8227));
		GameRegistry.addShapelessRecipe(new ItemStack(itemPotionLiquefiedParasiteMaggot), new ItemStack(itemParasiteMaggotsAsh), new ItemStack(Items.potionitem, 1, 8195));
		GameRegistry.addShapelessRecipe(new ItemStack(itemPotionLiquefiedParasiteMaggot), new ItemStack(itemParasiteMaggotsAsh), new ItemStack(Items.potionitem, 1, 8259));

		
		
		
		GameRegistry.addSmelting(itemDeadParasiteMaggot, new ItemStack(itemBurnedParasiteMaggot), 0.1f);
	
	
	
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {
		// ACHIEVEMENTS
		achievementInfectusDimension = new Achievement("achievementInfectusDimension", "infectusDimension", 0, 3, DimensionShiftInfectus.blockInfectedGrass, DimensionShift.achievementEnderDust);
		DimensionShift.achievementPageDimensionShift.getAchievements().add(achievementInfectusDimension);

		achievementInfectionParasiteMaggot = new Achievement("achievementInfectionParasiteMaggot", "infectionParasiteMaggot", -4, 0, Items.bread, DimensionShift.achievementEnderDust);
		DimensionShift.achievementPageDimensionShift.getAchievements().add(achievementInfectionParasiteMaggot);

	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent e) {

		// EVENT HANDLER
		MinecraftForge.EVENT_BUS.register(new EventHandlerDimensionShiftInfectus());

		// PROXY
		proxy.registerProxies();

		// GENERATION
		GameRegistry.registerWorldGenerator(new OreGenerationDimensionShiftInfectus(), 0);

	}

}
