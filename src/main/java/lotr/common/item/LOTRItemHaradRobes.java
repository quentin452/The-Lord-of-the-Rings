package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRItemHaradRobes extends LOTRItemArmor {
	public static int ROBES_WHITE = 16777215;

	public LOTRItemHaradRobes(int slot) {
		this(LOTRMaterial.HARAD_ROBES, slot);
	}

	public LOTRItemHaradRobes(LOTRMaterial material, int slot) {
		super(material, slot);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (areRobesDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.lotr.haradRobes.dyed"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		return getRobesColor(itemstack);
	}

	public static boolean areRobesDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static int getRobesColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return ROBES_WHITE;
	}

	public static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("RobesColor")) {
			return itemstack.getTagCompound().getInteger("RobesColor");
		}
		return -1;
	}

	public static void removeRobeDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("RobesColor");
		}
	}

	public static void setRobesColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("RobesColor", i);
	}
}
