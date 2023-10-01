package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class LOTRItemDaleCracker extends Item {
	public static int emptyMeta = 4096;
	public static int CUSTOM_CAPACITY = 3;
	@SideOnly(Side.CLIENT)
	public IIcon[] crackerIcons;
	public String[] crackerNames = {"red", "blue", "green", "silver", "gold"};

	public LOTRItemDaleCracker() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(LOTRCreativeTabs.tabMisc);
	}

	public static int getBaseCrackerMetadata(int i) {
		return i & ~emptyMeta;
	}

	public static String getSealingPlayerName(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SealingPlayer")) {
			return itemstack.getTagCompound().getString("SealingPlayer");
		}
		return null;
	}

	public static boolean isEmpty(ItemStack itemstack) {
		return (itemstack.getItemDamage() & emptyMeta) == emptyMeta;
	}

	public static IInventory loadCustomCrackerContents(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("CustomCracker")) {
			NBTTagCompound invData = itemstack.getTagCompound().getCompoundTag("CustomCracker");
			int size = invData.getInteger("Size");
			IInventory inv = new InventoryBasic("cracker", false, size);
			NBTTagList items = invData.getTagList("Items", 10);
			for (int i = 0; i < items.tagCount(); ++i) {
				NBTTagCompound itemData = items.getCompoundTagAt(i);
				byte slot = itemData.getByte("Slot");
				if (slot < 0 || slot >= inv.getSizeInventory()) {
					continue;
				}
				inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
			}
			return inv;
		}
		return null;
	}

	public static void setCustomCrackerContents(ItemStack itemstack, IInventory inv) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (inv == null) {
			itemstack.getTagCompound().removeTag("CustomCracker");
		} else {
			NBTTagCompound invData = new NBTTagCompound();
			int size = inv.getSizeInventory();
			invData.setInteger("Size", size);
			NBTTagList items = new NBTTagList();
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack invItem = inv.getStackInSlot(i);
				if (invItem == null) {
					continue;
				}
				NBTTagCompound itemData = new NBTTagCompound();
				itemData.setByte("Slot", (byte) i);
				invItem.writeToNBT(itemData);
				items.appendTag(itemData);
			}
			invData.setTag("Items", items);
			itemstack.getTagCompound().setTag("CustomCracker", invData);
		}
	}

	public static ItemStack setEmpty(ItemStack itemstack, boolean flag) {
		int i = itemstack.getItemDamage();
		i = flag ? i | emptyMeta : i & ~emptyMeta;
		itemstack.setItemDamage(i);
		return itemstack;
	}

	public static void setSealingPlayerName(ItemStack itemstack, String name) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (name == null) {
			itemstack.getTagCompound().removeTag("SealingPlayer");
		} else {
			itemstack.getTagCompound().setString("SealingPlayer", name);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (!isEmpty(itemstack)) {
			String name = getSealingPlayerName(itemstack);
			if (name == null) {
				name = StatCollector.translateToLocal("item.lotr.cracker.sealedByDale");
			}
			list.add(StatCollector.translateToLocalFormatted("item.lotr.cracker.sealedBy", name));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		i = getBaseCrackerMetadata(i);
		if (i >= crackerIcons.length) {
			i = 0;
		}
		return crackerIcons[i];
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (isEmpty(itemstack)) {
			String name = super.getItemStackDisplayName(itemstack);
			return StatCollector.translateToLocalFormatted("item.lotr.cracker.empty", name);
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

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < crackerNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
			list.add(setEmpty(new ItemStack(item, 1, i), true));
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!isEmpty(itemstack)) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}
			}
			world.playSoundAtEntity(entityplayer, "fireworks.blast", 1.0f, 0.9f + world.rand.nextFloat() * 0.1f);
			if (!world.isRemote) {
				IInventory crackerItems;
				IInventory customItems = loadCustomCrackerContents(itemstack);
				if (customItems != null) {
					crackerItems = customItems;
				} else {
					int amount = 1;
					if (world.rand.nextInt(3) == 0) {
						++amount;
					}
					if (LOTRMod.isChristmas()) {
						amount += 1 + world.rand.nextInt(4);
					}
					crackerItems = new InventoryBasic("cracker", true, amount);
					LOTRChestContents.fillInventory(crackerItems, world.rand, LOTRChestContents.DALE_CRACKER, amount);
				}
				for (int l = 0; l < crackerItems.getSizeInventory(); ++l) {
					ItemStack loot = crackerItems.getStackInSlot(l);
					if (entityplayer.inventory.addItemStackToInventory(loot)) {
						continue;
					}
					entityplayer.dropPlayerItemWithRandomChoice(loot, false);
				}
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.openDaleCracker);
				return entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem);
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (isEmpty(itemstack)) {
			entityplayer.openGui(LOTRMod.instance, 48, world, 0, 0, 0);
		} else {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		crackerIcons = new IIcon[crackerNames.length];
		for (int i = 0; i < crackerNames.length; ++i) {
			crackerIcons[i] = iconregister.registerIcon(getIconString() + "_" + crackerNames[i]);
		}
	}
}
