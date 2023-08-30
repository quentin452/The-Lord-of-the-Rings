package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.WeightedRandom;

public class LOTRItemModifierTemplate extends Item {
	public LOTRItemModifierTemplate() {
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabMaterials);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		LOTREnchantment mod = getModifier(itemstack);
		if (mod != null) {
			String desc = mod.getNamedFormattedDescription(itemstack);
			list.add(desc);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String s = super.getItemStackDisplayName(itemstack);
		LOTREnchantment mod = getModifier(itemstack);
		if (mod != null) {
			s = String.format(s, mod.getDisplayName());
		}
		return s;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.hasTemplateItem()) {
				continue;
			}
			ItemStack itemstack = new ItemStack(this);
			setModifier(itemstack, ench);
			list.add(itemstack);
		}
	}

	public static LOTREnchantment getModifier(ItemStack itemstack) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt != null) {
			String s = nbt.getString("ScrollModifier");
			return LOTREnchantment.getEnchantmentByName(s);
		}
		return null;
	}

	public static ItemStack getRandomCommonTemplate(Random random) {
		Collection<LOTREnchantmentHelper.WeightedRandomEnchant> applicable = new ArrayList<>();
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.hasTemplateItem()) {
				continue;
			}
			int weight = LOTREnchantmentHelper.getSkilfulWeight(ench);
			LOTREnchantmentHelper.WeightedRandomEnchant wre = new LOTREnchantmentHelper.WeightedRandomEnchant(ench, weight);
			applicable.add(wre);
		}
		LOTREnchantmentHelper.WeightedRandomEnchant chosenWre = (LOTREnchantmentHelper.WeightedRandomEnchant) WeightedRandom.getRandomItem(random, applicable);
		LOTREnchantment chosenEnch = chosenWre.theEnchant;
		ItemStack itemstack = new ItemStack(LOTRMod.modTemplate);
		setModifier(itemstack, chosenEnch);
		return itemstack;
	}

	public static void setModifier(ItemStack itemstack, LOTREnchantment ench) {
		String s = ench.enchantName;
		itemstack.setTagInfo("ScrollModifier", new NBTTagString(s));
	}
}
