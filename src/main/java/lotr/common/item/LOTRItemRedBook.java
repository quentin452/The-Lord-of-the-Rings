package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

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
