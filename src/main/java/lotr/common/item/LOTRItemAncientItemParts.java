package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemAncientItemParts extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	public String[] partNames = {"swordTip", "swordBlade", "swordHilt", "armorPlate"};

	public LOTRItemAncientItemParts() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		if (i >= icons.length) {
			i = 0;
		}
		return icons[i];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 3; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		icons = new IIcon[partNames.length];
		for (int i = 0; i < partNames.length; ++i) {
			icons[i] = iconregister.registerIcon(getIconString() + "_" + partNames[i]);
		}
	}
}
