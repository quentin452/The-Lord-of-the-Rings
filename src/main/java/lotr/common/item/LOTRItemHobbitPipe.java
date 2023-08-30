package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemHobbitPipe extends Item {
	public static int MAGIC_COLOR = 16;

	public LOTRItemHobbitPipe() {
		setMaxDamage(300);
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int color = getSmokeColor(itemstack);
		list.add(StatCollector.translateToLocal(getUnlocalizedName() + ".subtitle." + color));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 40;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 16; ++i) {
			ItemStack itemstack = new ItemStack(this);
			setSmokeColor(itemstack, i);
			list.add(itemstack);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.inventory.hasItem(LOTRMod.pipeweed) || entityplayer.capabilities.isCreativeMode) {
			itemstack.damageItem(1, entityplayer);
			if (!entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.consumeInventoryItem(LOTRMod.pipeweed);
			}
			if (entityplayer.canEat(false)) {
				entityplayer.getFoodStats().addStats(2, 0.3f);
			}
			if (!world.isRemote) {
				LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(world, entityplayer);
				int color = getSmokeColor(itemstack);
				smoke.setSmokeColour(color);
				world.spawnEntityInWorld(smoke);
				if (color == 16) {
					LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMagicPipe);
				}
			}
			world.playSoundAtEntity(entityplayer, "lotr:item.puff", 1.0f, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2f + 1.0f);
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.inventory.hasItem(LOTRMod.pipeweed) || entityplayer.capabilities.isCreativeMode) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	public static int getSmokeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SmokeColour")) {
			return itemstack.getTagCompound().getInteger("SmokeColour");
		}
		return 0;
	}

	public static boolean isPipeDyed(ItemStack itemstack) {
		int color = getSmokeColor(itemstack);
		return color != 0 && color != 16;
	}

	public static void removePipeDye(ItemStack itemstack) {
		setSmokeColor(itemstack, 0);
	}

	public static void setSmokeColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("SmokeColour", i);
	}
}
