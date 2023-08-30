package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemAncientItem extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	public String[] itemNames = {"sword", "dagger", "helmet", "body", "legs", "boots"};

	public LOTRItemAncientItem() {
		setMaxStackSize(1);
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
		for (int i = 0; i <= 5; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote) {
			ItemStack ancientItem = getRandomItem(itemstack);
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAncientItem);
			world.playSoundAtEntity(entityplayer, "random.pop", 0.2f, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
			return ancientItem;
		}
		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		icons = new IIcon[itemNames.length];
		for (int i = 0; i < itemNames.length; ++i) {
			icons[i] = iconregister.registerIcon(getIconString() + "_" + itemNames[i]);
		}
	}

	public static ItemStack getRandomItem(ItemStack itemstack) {
		ItemStack randomItem;
		IInventory randomItemInv = new InventoryBasic("ancientItem", true, 1);
		LOTRChestContents itemPool = null;
		if (itemstack.getItemDamage() == 0) {
			itemPool = LOTRChestContents.ANCIENT_SWORD;
		} else if (itemstack.getItemDamage() == 1) {
			itemPool = LOTRChestContents.ANCIENT_DAGGER;
		} else if (itemstack.getItemDamage() == 2) {
			itemPool = LOTRChestContents.ANCIENT_HELMET;
		} else if (itemstack.getItemDamage() == 3) {
			itemPool = LOTRChestContents.ANCIENT_BODY;
		} else if (itemstack.getItemDamage() == 4) {
			itemPool = LOTRChestContents.ANCIENT_LEGS;
		} else if (itemstack.getItemDamage() == 5) {
			itemPool = LOTRChestContents.ANCIENT_BOOTS;
		}
		LOTRChestContents.fillInventory(randomItemInv, itemRand, itemPool, 1);
		randomItem = randomItemInv.getStackInSlot(0);
		if (randomItem != null) {
			LOTREnchantment wraithbane;
			if (LOTRConfig.enchantingLOTR && (wraithbane = LOTREnchantment.baneWraith).canApply(randomItem, false) && itemRand.nextInt(4) == 0) {
				LOTREnchantmentHelper.setHasEnchant(randomItem, wraithbane);
			}
			return randomItem;
		}
		return itemstack;
	}
}
