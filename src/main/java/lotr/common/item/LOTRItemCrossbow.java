package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemCrossbow extends ItemBow {
	public double boltDamageFactor;
	public Item.ToolMaterial crossbowMaterial;
	@SideOnly(Side.CLIENT)
	public IIcon[] crossbowPullIcons;

	public LOTRItemCrossbow(Item.ToolMaterial material) {
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		crossbowMaterial = material;
		setMaxDamage((int) (crossbowMaterial.getMaxUses() * 1.25f));
		setMaxStackSize(1);
		boltDamageFactor = 1.0f + Math.max(0.0f, (crossbowMaterial.getDamageVsEntity() - 2.0f) * 0.1f);
	}

	public LOTRItemCrossbow(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		ItemStack ammo = getLoaded(itemstack);
		if (ammo != null) {
			String ammoName = ammo.getDisplayName();
			list.add(StatCollector.translateToLocalFormatted("item.lotr.crossbow.loadedItem", ammoName));
		}
	}

	public Item.ToolMaterial getCrossbowMaterial() {
		return crossbowMaterial;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		return getIconIndex(itemstack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int renderPass, EntityPlayer entityplayer, ItemStack usingItem, int useRemaining) {
		if (isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		if (usingItem != null && usingItem.getItem() == this) {
			int ticksInUse = usingItem.getMaxItemUseDuration() - useRemaining;
			double useAmount = (double) ticksInUse / getMaxDrawTime();
			if (useAmount >= 1.0) {
				return crossbowPullIcons[2];
			}
			if (useAmount > 0.5) {
				return crossbowPullIcons[1];
			}
			if (useAmount > 0.0) {
				return crossbowPullIcons[0];
			}
		}
		return itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		if (isLoaded(itemstack)) {
			return crossbowPullIcons[2];
		}
		return itemIcon;
	}

	public int getInvBoltSlot(EntityPlayer entityplayer) {
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			ItemStack invItem = entityplayer.inventory.mainInventory[slot];
			if (invItem == null || !(invItem.getItem() instanceof LOTRItemCrossbowBolt)) {
				continue;
			}
			return slot;
		}
		return -1;
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		if (LOTRRecipes.checkItemEquals(crossbowMaterial.getRepairItemStack(), repairItem)) {
			return true;
		}
		return super.getIsRepairable(itemstack, repairItem);
	}

	@Override
	public int getItemEnchantability() {
		return 1 + crossbowMaterial.getEnchantability() / 5;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String name = super.getItemStackDisplayName(itemstack);
		if (isLoaded(itemstack)) {
			name = StatCollector.translateToLocalFormatted("item.lotr.crossbow.loaded", name);
		}
		return name;
	}

	public int getMaxDrawTime() {
		return 50;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (isLoaded(itemstack)) {
			ItemStack boltItem = getLoaded(itemstack);
			if (boltItem != null) {
				float charge = 1.0f;
				ItemStack shotBolt = boltItem.copy();
				shotBolt.stackSize = 1;
				LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, entityplayer, shotBolt, charge * 2.0f * getCrossbowLaunchSpeedFactor(itemstack));
				if (bolt.boltDamageFactor < 1.0) {
					bolt.boltDamageFactor = 1.0;
				}
				bolt.setIsCritical(true);
				applyCrossbowModifiers(bolt, itemstack);
				if (!shouldConsumeBolt(itemstack, entityplayer)) {
					bolt.canBePickedUp = 2;
				}
				if (!world.isRemote) {
					world.spawnEntityInWorld(bolt);
				}
				world.playSoundAtEntity(entityplayer, "lotr:item.crossbow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + charge * 0.5f);
				itemstack.damageItem(1, entityplayer);
				if (!world.isRemote) {
					setLoaded(itemstack, null);
				}
			}
		} else if (!shouldConsumeBolt(itemstack, entityplayer) || getInvBoltSlot(entityplayer) >= 0) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick) {
		int ticksInUse = getMaxItemUseDuration(itemstack) - useTick;
		if (ticksInUse >= getMaxDrawTime() && !isLoaded(itemstack)) {
			ItemStack boltItem = null;
			int boltSlot = getInvBoltSlot(entityplayer);
			if (boltSlot >= 0) {
				boltItem = entityplayer.inventory.mainInventory[boltSlot];
			}
			boolean shouldConsume = shouldConsumeBolt(itemstack, entityplayer);
			if (boltItem == null && !shouldConsume) {
				boltItem = new ItemStack(LOTRMod.crossbowBolt);
			}
			if (boltItem != null) {
				if (shouldConsume) {
					--boltItem.stackSize;
					if (boltItem.stackSize <= 0) {
						entityplayer.inventory.mainInventory[boltSlot] = null;
					}
				}
				if (!world.isRemote) {
					setLoaded(itemstack, boltItem.copy());
				}
			}
			entityplayer.clearItemInUse();
		}
	}

	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
		World world = entityplayer.worldObj;
		if (!world.isRemote && !isLoaded(itemstack) && getMaxItemUseDuration(itemstack) - count == getMaxDrawTime()) {
			world.playSoundAtEntity(entityplayer, "lotr:item.crossbowLoad", 1.0f, 1.5f + world.rand.nextFloat() * 0.2f);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		itemIcon = iconregister.registerIcon(getIconString());
		crossbowPullIcons = new IIcon[3];
		crossbowPullIcons[0] = iconregister.registerIcon(getIconString() + "_" + LOTRItemBow.BowState.PULL_0.iconName);
		crossbowPullIcons[1] = iconregister.registerIcon(getIconString() + "_" + LOTRItemBow.BowState.PULL_1.iconName);
		crossbowPullIcons[2] = iconregister.registerIcon(getIconString() + "_" + LOTRItemBow.BowState.PULL_2.iconName);
	}

	public void setLoaded(ItemStack itemstack, ItemStack ammo) {
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt == null) {
				nbt = new NBTTagCompound();
				itemstack.setTagCompound(nbt);
			}
			if (ammo != null) {
				NBTTagCompound ammoData = new NBTTagCompound();
				ammo.writeToNBT(ammoData);
				nbt.setTag("LOTRCrossbowAmmo", ammoData);
			} else {
				nbt.removeTag("LOTRCrossbowAmmo");
			}
			if (nbt.hasKey("LOTRCrossbowLoaded")) {
				nbt.removeTag("LOTRCrossbowLoaded");
			}
		}
	}

	public boolean shouldConsumeBolt(ItemStack itemstack, EntityPlayer entityplayer) {
		return !entityplayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0;
	}

	public static void applyCrossbowModifiers(LOTREntityCrossbowBolt bolt, ItemStack itemstack) {
		int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
		if (power > 0) {
			bolt.boltDamageFactor += power * 0.5 + 0.5;
		}
		int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
		punch += LOTREnchantmentHelper.calcRangedKnockback(itemstack);
		if (punch > 0) {
			bolt.knockbackStrength = punch;
		}
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) + LOTREnchantmentHelper.calcFireAspect(itemstack) > 0) {
			bolt.setFire(100);
		}
		for (LOTREnchantment ench : LOTREnchantment.allEnchantments) {
			if (!ench.applyToProjectile() || !LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
				continue;
			}
			LOTREnchantmentHelper.setProjectileEnchantment(bolt, ench);
		}
	}

	public static float getCrossbowLaunchSpeedFactor(ItemStack itemstack) {
		float f = 1.0f;
		if (itemstack != null) {
			if (itemstack.getItem() instanceof LOTRItemCrossbow) {
				f = (float) (f * ((LOTRItemCrossbow) itemstack.getItem()).boltDamageFactor);
			}
			f *= LOTREnchantmentHelper.calcRangedDamageFactor(itemstack);
		}
		return f;
	}

	public static ItemStack getLoaded(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if (nbt == null) {
				return null;
			}
			if (nbt.hasKey("LOTRCrossbowAmmo")) {
				NBTTagCompound ammoData = nbt.getCompoundTag("LOTRCrossbowAmmo");
				return ItemStack.loadItemStackFromNBT(ammoData);
			}
			if (nbt.hasKey("LOTRCrossbowLoaded")) {
				return new ItemStack(LOTRMod.crossbowBolt);
			}
		}
		return null;
	}

	public static boolean isLoaded(ItemStack itemstack) {
		return getLoaded(itemstack) != null;
	}
}
