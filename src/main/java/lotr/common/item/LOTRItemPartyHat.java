package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class LOTRItemPartyHat extends LOTRItemArmor {
	public static int HAT_WHITE = 16777215;

	public LOTRItemPartyHat() {
		super(LOTRMaterial.COSMETIC, 0);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	public static ItemStack createDyedHat(int i) {
		return setHatColor(new ItemStack(LOTRMod.partyHat), i);
	}

	public static int getHatColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 16777215;
	}

	public static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HatColor")) {
			return itemstack.getTagCompound().getInteger("HatColor");
		}
		return -1;
	}

	public static boolean isHatDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static void removeHatDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("HatColor");
		}
	}

	public static ItemStack setHatColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("HatColor", i);
		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isHatDyed(itemstack) && getHatColor(itemstack) != 16777215) {
			list.add(StatCollector.translateToLocal("item.lotr.hat.dyed"));
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "lotr:armor/partyhat.png";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		return getHatColor(itemstack);
	}
}
