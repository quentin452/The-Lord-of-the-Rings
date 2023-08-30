package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class LOTRItemLeatherHat extends LOTRItemArmor {
	public static int HAT_LEATHER = 6834742;
	public static int HAT_SHIRRIFF_CHIEF = 2301981;
	public static int HAT_BLACK;
	public static int FEATHER_WHITE = 16777215;
	public static int FEATHER_SHIRRIFF_CHIEF = 3381529;
	public static int FEATHER_BREE_CAPTAIN = 40960;
	@SideOnly(Side.CLIENT)
	public IIcon featherIcon;

	public LOTRItemLeatherHat() {
		super(LOTRMaterial.COSMETIC, 0);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isHatDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.lotr.hat.dyed"));
		}
		if (hasFeather(itemstack)) {
			list.add(StatCollector.translateToLocal("item.lotr.hat.feathered"));
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "lotr:armor/hat.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 1 && hasFeather(itemstack)) {
			return getFeatherColor(itemstack);
		}
		return getHatColor(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass == 1 && hasFeather(itemstack)) {
			return featherIcon;
		}
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		featherIcon = iconregister.registerIcon(getIconString() + "_feather");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static int getFeatherColor(ItemStack itemstack) {
		int i = -1;
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("FeatherColor")) {
			i = itemstack.getTagCompound().getInteger("FeatherColor");
		}
		return i;
	}

	public static int getHatColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 6834742;
	}

	public static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HatColor")) {
			return itemstack.getTagCompound().getInteger("HatColor");
		}
		return -1;
	}

	public static boolean hasFeather(ItemStack itemstack) {
		return getFeatherColor(itemstack) != -1;
	}

	public static boolean isFeatherDyed(ItemStack itemstack) {
		return hasFeather(itemstack) && getFeatherColor(itemstack) != 16777215;
	}

	public static boolean isHatDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static void removeHatAndFeatherDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("HatColor");
		}
		if (hasFeather(itemstack) && isFeatherDyed(itemstack)) {
			setFeatherColor(itemstack, 16777215);
		}
	}

	public static ItemStack setFeatherColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("FeatherColor", i);
		return itemstack;
	}

	public static ItemStack setHatColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("HatColor", i);
		return itemstack;
	}
}
