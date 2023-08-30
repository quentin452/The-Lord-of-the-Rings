package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRInventoryHiredReplacedItems extends LOTRInventoryNPC {
	public static int HELMET;
	public static int BODY = 1;
	public static int LEGS = 2;
	public static int BOOTS = 3;
	public static int MELEE = 4;
	public static int BOMB = 5;
	public static int RANGED = 6;
	public boolean[] hasReplacedEquipment = new boolean[7];
	public boolean replacedMeleeWeapons;

	public LOTRInventoryHiredReplacedItems(LOTREntityNPC npc) {
		super("HiredReplacedItems", npc, 7);
	}

	public void dropAllReplacedItems() {
		for (int i = 0; i < 7; ++i) {
			ItemStack itemstack;
			if (!hasReplacedEquipment(i) || (itemstack = getEquippedReplacement(i)) == null) {
				continue;
			}
			theNPC.npcDropItem(itemstack, 0.0f, false, true);
			equipReplacement(i, getReplacedEquipment(i));
			setReplacedEquipment(i, null, false);
		}
	}

	public void equipReplacement(int i, ItemStack itemstack) {
		switch (i) {
			case 4: {
				boolean idleMelee = ItemStack.areItemStacksEqual(theNPC.npcItemsInv.getMeleeWeapon(), theNPC.npcItemsInv.getIdleItem());
				theNPC.npcItemsInv.setMeleeWeapon(itemstack);
				if (!replacedMeleeWeapons) {
					theNPC.npcItemsInv.setReplacedIdleItem(theNPC.npcItemsInv.getIdleItem());
					theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(theNPC.npcItemsInv.getMeleeWeaponMounted());
					theNPC.npcItemsInv.setReplacedIdleItemMounted(theNPC.npcItemsInv.getIdleItemMounted());
					replacedMeleeWeapons = true;
				}
				theNPC.npcItemsInv.setMeleeWeaponMounted(itemstack);
				if (idleMelee) {
					theNPC.npcItemsInv.setIdleItem(itemstack);
					theNPC.npcItemsInv.setIdleItemMounted(itemstack);
				}
				updateHeldItem();
				break;
			}
			case 6:
				theNPC.npcItemsInv.setRangedWeapon(itemstack);
				updateHeldItem();
				break;
			case 5:
				theNPC.npcItemsInv.setBomb(itemstack);
				updateHeldItem();
				break;
			default:
				theNPC.setCurrentItemOrArmor(getNPCArmorSlot(i), itemstack);
				break;
		}
	}

	public ItemStack getEquippedReplacement(int i) {
		switch (i) {
			case 4:
				return theNPC.npcItemsInv.getMeleeWeapon();
			case 6:
				return theNPC.npcItemsInv.getRangedWeapon();
			case 5:
				return theNPC.npcItemsInv.getBomb();
			default:
				break;
		}
		return theNPC.getEquipmentInSlot(getNPCArmorSlot(i));
	}

	public int getNPCArmorSlot(int i) {
		return 4 - i;
	}

	public ItemStack getReplacedEquipment(int i) {
		ItemStack item = getStackInSlot(i);
		return item == null ? null : item.copy();
	}

	public boolean hasReplacedEquipment(int i) {
		return hasReplacedEquipment[i];
	}

	public void onEquipmentChanged(int i, ItemStack newItem) {
		if (newItem == null) {
			if (hasReplacedEquipment(i)) {
				ItemStack itemstack = getReplacedEquipment(i);
				equipReplacement(i, itemstack);
				setReplacedEquipment(i, null, false);
			}
		} else {
			if (!hasReplacedEquipment(i)) {
				ItemStack itemstack = getEquippedReplacement(i);
				setReplacedEquipment(i, itemstack, true);
			}
			equipReplacement(i, newItem.copy());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for (int i = 0; i < hasReplacedEquipment.length; ++i) {
			hasReplacedEquipment[i] = nbt.getBoolean("ReplacedFlag_" + i);
		}
		replacedMeleeWeapons = nbt.getBoolean("ReplacedMelee");
	}

	public void setReplacedEquipment(int i, ItemStack item, boolean flag) {
		setInventorySlotContents(i, item);
		hasReplacedEquipment[i] = flag;
		if (!flag && i == 4) {
			if (replacedMeleeWeapons) {
				theNPC.npcItemsInv.setIdleItem(theNPC.npcItemsInv.getReplacedIdleItem());
				theNPC.npcItemsInv.setMeleeWeaponMounted(theNPC.npcItemsInv.getReplacedMeleeWeaponMounted());
				theNPC.npcItemsInv.setIdleItemMounted(theNPC.npcItemsInv.getReplacedIdleItemMounted());
				theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(null);
				theNPC.npcItemsInv.setReplacedIdleItem(null);
				theNPC.npcItemsInv.setReplacedIdleItemMounted(null);
				replacedMeleeWeapons = false;
			}
			updateHeldItem();
		}
	}

	public void updateHeldItem() {
		if (!theNPC.npcItemsInv.getIsEating()) {
			theNPC.refreshCurrentAttackMode();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for (int i = 0; i < hasReplacedEquipment.length; ++i) {
			boolean flag = hasReplacedEquipment[i];
			nbt.setBoolean("ReplacedFlag_" + i, flag);
		}
		nbt.setBoolean("ReplacedMelee", replacedMeleeWeapons);
	}
}
