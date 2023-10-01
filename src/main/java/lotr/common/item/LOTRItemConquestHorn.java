package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.*;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class LOTRItemConquestHorn extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon baseIcon;
	@SideOnly(Side.CLIENT)
	public IIcon overlayIcon;

	public LOTRItemConquestHorn() {
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabCombat);
	}

	public static ItemStack createHorn(LOTRInvasions type) {
		ItemStack itemstack = new ItemStack(LOTRMod.conquestHorn);
		setInvasionType(itemstack, type);
		return itemstack;
	}

	public static LOTRInvasions getInvasionType(ItemStack itemstack) {
		String s;
		LOTRInvasions invasionType = null;
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("InvasionType")) {
			s = itemstack.getTagCompound().getString("InvasionType");
			invasionType = LOTRInvasions.forName(s);
		}
		if (invasionType == null && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HornFaction")) {
			s = itemstack.getTagCompound().getString("HornFaction");
			invasionType = LOTRInvasions.forName(s);
		}
		if (invasionType == null) {
			invasionType = LOTRInvasions.HOBBIT;
		}
		return invasionType;
	}

	public static void setInvasionType(ItemStack itemstack, LOTRInvasions type) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("InvasionType", type.codeName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		LOTRInvasions type = getInvasionType(itemstack);
		list.add(type.invasionName());
	}

	public boolean canUseHorn(ItemStack itemstack, World world, EntityPlayer entityplayer, boolean sendMessage) {
		if (LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO) {
			if (sendMessage && !world.isRemote) {
				entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.conquestHornProtected"));
			}
			return false;
		}
		LOTRInvasions invasionType = getInvasionType(itemstack);
		LOTRFaction invasionFaction = invasionType.invasionFaction;
		float alignmentRequired = 1500.0f;
		if (LOTRLevelData.getData(entityplayer).getAlignment(invasionFaction) >= alignmentRequired) {
			boolean blocked = LOTRBannerProtection.isProtected(world, entityplayer, LOTRBannerProtection.forFaction(invasionFaction), false);
			if (LOTREntityNPCRespawner.isSpawnBlocked(entityplayer, invasionFaction)) {
				blocked = true;
			}
			if (blocked) {
				if (sendMessage && !world.isRemote) {
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.conquestHornProtected", invasionFaction.factionName()));
				}
				return false;
			}
			return true;
		}
		if (sendMessage && !world.isRemote) {
			LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, alignmentRequired, invasionType.invasionFaction);
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 0) {
			LOTRFaction faction = getInvasionType(itemstack).invasionFaction;
			return faction.getFactionColor();
		}
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		return pass > 0 ? overlayIcon : baseIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		LOTRInvasions type = getInvasionType(itemstack);
		if (type != null) {
			return StatCollector.translateToLocal(type.codeNameHorn());
		}
		return super.getItemStackDisplayName(itemstack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (LOTRInvasions type : LOTRInvasions.values()) {
			ItemStack itemstack = new ItemStack(item);
			setInvasionType(itemstack, type);
			list.add(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		LOTRInvasions invasionType = getInvasionType(itemstack);
		if (canUseHorn(itemstack, world, entityplayer, true)) {
			if (!world.isRemote) {
				LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
				invasion.setInvasionType(invasionType);
				invasion.isWarhorn = true;
				invasion.spawnsPersistent = true;
				invasion.setLocationAndAngles(entityplayer.posX, entityplayer.posY + 3.0, entityplayer.posZ, 0.0f, 0.0f);
				world.spawnEntityInWorld(invasion);
				invasion.startInvasion(entityplayer);
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		canUseHorn(itemstack, world, entityplayer, false);
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		baseIcon = iconregister.registerIcon(getIconString() + "_base");
		overlayIcon = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
