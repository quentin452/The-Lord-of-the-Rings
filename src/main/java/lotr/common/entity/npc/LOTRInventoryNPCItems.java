package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRInventoryNPCItems extends LOTRInventoryNPC {
	public static int IDLE_ITEM;
	public static int WEAPON_MELEE = 1;
	public static int WEAPON_RANGED = 2;
	public static int SPEAR_BACKUP = 3;
	public static int EATING_BACKUP = 4;
	public static int IDLE_ITEM_MOUNTED = 5;
	public static int WEAPON_MELEE_MOUNTED = 6;
	public static int REPLACED_IDLE = 7;
	public static int REPLACED_MELEE_MOUNTED = 8;
	public static int REPLACED_IDLE_MOUNTED = 9;
	public static int BOMBING_ITEM = 10;
	public static int BOMB = 11;
	public boolean isEating;

	public LOTRInventoryNPCItems(LOTREntityNPC npc) {
		super("NPCItemsInv", npc, 12);
	}

	public ItemStack getBomb() {
		ItemStack item = getStackInSlot(BOMB);
		return item == null ? null : item.copy();
	}

	public ItemStack getBombingItem() {
		ItemStack item = getStackInSlot(BOMBING_ITEM);
		return item == null ? null : item.copy();
	}

	public ItemStack getEatingBackup() {
		ItemStack item = getStackInSlot(EATING_BACKUP);
		return item == null ? null : item.copy();
	}

	public ItemStack getIdleItem() {
		ItemStack item = getStackInSlot(IDLE_ITEM);
		return item == null ? null : item.copy();
	}

	public ItemStack getIdleItemMounted() {
		ItemStack item = getStackInSlot(IDLE_ITEM_MOUNTED);
		return item == null ? null : item.copy();
	}

	public boolean getIsEating() {
		return isEating;
	}

	public ItemStack getMeleeWeapon() {
		ItemStack item = getStackInSlot(WEAPON_MELEE);
		return item == null ? null : item.copy();
	}

	public ItemStack getMeleeWeaponMounted() {
		ItemStack item = getStackInSlot(WEAPON_MELEE_MOUNTED);
		return item == null ? null : item.copy();
	}

	public ItemStack getRangedWeapon() {
		ItemStack item = getStackInSlot(WEAPON_RANGED);
		return item == null ? null : item.copy();
	}

	public ItemStack getReplacedIdleItem() {
		ItemStack item = getStackInSlot(REPLACED_IDLE);
		return item == null ? null : item.copy();
	}

	public ItemStack getReplacedIdleItemMounted() {
		ItemStack item = getStackInSlot(REPLACED_IDLE_MOUNTED);
		return item == null ? null : item.copy();
	}

	public ItemStack getReplacedMeleeWeaponMounted() {
		ItemStack item = getStackInSlot(REPLACED_MELEE_MOUNTED);
		return item == null ? null : item.copy();
	}

	public ItemStack getSpearBackup() {
		ItemStack item = getStackInSlot(SPEAR_BACKUP);
		return item == null ? null : item.copy();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isEating = nbt.getBoolean("NPCEating");
		if (isEating) {
			theNPC.setCurrentItemOrArmor(0, getEatingBackup());
			setEatingBackup(null);
			setIsEating(false);
		}
	}

	public void setBomb(ItemStack item) {
		setInventorySlotContents(BOMB, item);
	}

	public void setBombingItem(ItemStack item) {
		setInventorySlotContents(BOMBING_ITEM, item);
	}

	public void setEatingBackup(ItemStack item) {
		setInventorySlotContents(EATING_BACKUP, item);
	}

	public void setIdleItem(ItemStack item) {
		setInventorySlotContents(IDLE_ITEM, item);
	}

	public void setIdleItemMounted(ItemStack item) {
		setInventorySlotContents(IDLE_ITEM_MOUNTED, item);
	}

	public void setIsEating(boolean flag) {
		isEating = flag;
		theNPC.sendIsEatingToWatchers();
	}

	public void setMeleeWeapon(ItemStack item) {
		setInventorySlotContents(WEAPON_MELEE, item);
	}

	public void setMeleeWeaponMounted(ItemStack item) {
		setInventorySlotContents(WEAPON_MELEE_MOUNTED, item);
	}

	public void setRangedWeapon(ItemStack item) {
		setInventorySlotContents(WEAPON_RANGED, item);
	}

	public void setReplacedIdleItem(ItemStack item) {
		setInventorySlotContents(REPLACED_IDLE, item);
	}

	public void setReplacedIdleItemMounted(ItemStack item) {
		setInventorySlotContents(REPLACED_IDLE_MOUNTED, item);
	}

	public void setReplacedMeleeWeaponMounted(ItemStack item) {
		setInventorySlotContents(REPLACED_MELEE_MOUNTED, item);
	}

	public void setSpearBackup(ItemStack item) {
		setInventorySlotContents(SPEAR_BACKUP, item);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("NPCEating", isEating);
	}
}
