package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemRedBook extends Item {
	@SideOnly(Side.CLIENT)
	public static IIcon questOfferIcon;

	public LOTRItemRedBook() {
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
		int activeQuests = playerData.getActiveMiniQuests().size();
		list.add(StatCollector.translateToLocalFormatted("item.lotr.redBook.activeQuests", activeQuests));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.openGui(LOTRMod.instance, 32, world, 0, 0, 0);
		if (!world.isRemote) {
			LOTRLevelData.getData(entityplayer).distributeMQEvent(new LOTRMiniQuestEvent.OpenRedBook());
		}
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		questOfferIcon = iconregister.registerIcon("lotr:questOffer");
	}
}
