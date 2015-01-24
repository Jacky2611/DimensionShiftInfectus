package net.dimensionshiftinfectus.mod.item;

import net.dimensionshiftinfectus.mod.DimensionShiftInfectus;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomPotion extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon iconOverlay;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottle;
	private int color;
	private PotionEffect[] potion;

	public ItemCustomPotion(int maxStackSize, String name, PotionEffect[] potion, int color) {
		this.setMaxStackSize(maxStackSize);
		this.setCreativeTab(DimensionShiftInfectus.tabDimensionShiftInfectus);
		this.setUnlocalizedName(name);
		this.potion = potion;
		this.color=color;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--itemStack.stackSize;
		}

		if (!world.isRemote) {
			for (int i = 0; i < potion.length; i++) {
				if (potion[i] != null && potion[i].getPotionID() > 0 && !world.isRemote) {
					player.addPotionEffect(new PotionEffect(potion[i]));
				}
			}
		}

		if (!player.capabilities.isCreativeMode) {
			if (itemStack.stackSize <= 0) {
				return new ItemStack(Items.glass_bottle);
			}

			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
		}

		return itemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}
	
	@Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        return false;
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {

            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            return itemStack;

    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_, int pass) {
		if (pass == 0) {
			return true;
		} else {
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int pass) {
		if (pass == 0) {
			return this.color;
		} else {
			return 99999999;
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int damage, int pass) {

		if (pass == 0) {
			return this.iconOverlay;

		} else {
			return this.iconBottle;
		}

	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {

		this.itemIcon = Items.apple.getIconFromDamage(0);
		if (ItemPotion.func_94589_d("bottle_drinkable") != null && ItemPotion.func_94589_d("overlay") != null) {
			this.iconBottle = ItemPotion.func_94589_d("bottle_drinkable");
			this.iconOverlay = ItemPotion.func_94589_d("overlay");
		}

	}

}