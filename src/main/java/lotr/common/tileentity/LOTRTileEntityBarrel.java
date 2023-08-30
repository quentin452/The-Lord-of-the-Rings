package lotr.common.tileentity;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import lotr.common.inventory.LOTRSlotStackSize;
import lotr.common.item.*;
import lotr.common.recipe.LOTRBrewingRecipes;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityBarrel extends TileEntity implements ISidedInventory {
	public static int EMPTY;
	public static int BREWING = 1;
	public static int FULL = 2;
	public static int brewTime = 12000;
	public static int brewAnimTime = 32;
	public static int[] INGREDIENT_SLOTS = {0, 1, 2, 3, 4, 5};
	public static int[] BUCKET_SLOTS = {6, 7, 8};
	public static int BARREL_SLOT = 9;
	public ItemStack[] inventory = new ItemStack[10];
	public int barrelMode;
	public int brewingTime;
	public int brewingAnim;
	public int brewingAnimPrev;
	public String specialBarrelName;
	public List players = new ArrayList();

	@Override
	public boolean canExtractItem(int slot, ItemStack extractItem, int side) {
		if (ArrayUtils.contains(BUCKET_SLOTS, slot)) {
			return !isItemValidForSlot(slot, extractItem);
		}
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack insertItem, int side) {
		return isItemValidForSlot(slot, insertItem);
	}

	public boolean canPoisonBarrel() {
		if (barrelMode != 0 && inventory[9] != null) {
			ItemStack itemstack = inventory[9];
			return LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
		}
		return false;
	}

	@Override
	public void closeInventory() {
	}

	public void consumeMugRefill() {
		if (barrelMode == 2 && inventory[9] != null) {
			--inventory[9].stackSize;
			if (inventory[9].stackSize <= 0) {
				inventory[9] = null;
				barrelMode = 0;
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				if (i != 9) {
					updateBrewingRecipe();
				}
				return itemstack;
			}
			ItemStack itemstack = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			if (i != 9) {
				updateBrewingRecipe();
			}
			return itemstack;
		}
		return null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == 0) {
			return BUCKET_SLOTS;
		}
		if (side == 1) {
			ArrayList<LOTRSlotStackSize> slotsWithStackSize = new ArrayList<>();
			for (int slot : INGREDIENT_SLOTS) {
				int size = getStackInSlot(slot) == null ? 0 : getStackInSlot(slot).stackSize;
				slotsWithStackSize.add(new LOTRSlotStackSize(slot, size));
			}
			Collections.sort(slotsWithStackSize);
			int[] sortedSlots = new int[INGREDIENT_SLOTS.length];
			for (int i = 0; i < sortedSlots.length; ++i) {
				LOTRSlotStackSize slotAndStack = slotsWithStackSize.get(i);
				sortedSlots[i] = slotAndStack.slot;
			}
			return sortedSlots;
		}
		return BUCKET_SLOTS;
	}

	public int getBarrelFullAmountScaled(int i) {
		return inventory[9] == null ? 0 : inventory[9].stackSize * i / LOTRBrewingRecipes.BARREL_CAPACITY;
	}

	public int getBrewAnimationProgressScaled(int i) {
		return brewingAnim * i / 32;
	}

	public float getBrewAnimationProgressScaledF(int i, float f) {
		float f1 = (float) brewingAnimPrev * i / 32.0f;
		float f2 = (float) brewingAnim * i / 32.0f;
		return f1 + (f2 - f1) * f;
	}

	public ItemStack getBrewedDrink() {
		if (barrelMode == 2 && inventory[9] != null) {
			return inventory[9].copy();
		}
		return null;
	}

	public int getBrewProgressScaled(int i) {
		return brewingTime * i / 12000;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeBarrelToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? specialBarrelName : StatCollector.translateToLocal("container.lotr.barrel");
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public String getInvSubtitle() {
		ItemStack brewingItem = getStackInSlot(9);
		if (barrelMode == 0) {
			return StatCollector.translateToLocal("container.lotr.barrel.empty");
		}
		if (barrelMode == 1 && brewingItem != null) {
			return StatCollector.translateToLocalFormatted("container.lotr.barrel.brewing", brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem));
		}
		if (barrelMode == 2 && brewingItem != null) {
			return StatCollector.translateToLocalFormatted("container.lotr.barrel.full", brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem), brewingItem.stackSize);
		}
		return "";
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (inventory[i] != null) {
			ItemStack itemstack = inventory[i];
			inventory[i] = null;
			return itemstack;
		}
		return null;
	}

	public void handleBrewingButtonPress() {
		if (barrelMode == 0 && inventory[9] != null) {
			int i;
			barrelMode = 1;
			for (i = 0; i < 9; ++i) {
				if (inventory[i] == null) {
					continue;
				}
				ItemStack containerItem = null;
				if (inventory[i].getItem().hasContainerItem(inventory[i]) && (containerItem = inventory[i].getItem().getContainerItem(inventory[i])).isItemStackDamageable() && containerItem.getItemDamage() > containerItem.getMaxDamage()) {
					containerItem = null;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize > 0) {
					continue;
				}
				inventory[i] = null;
				if (containerItem == null) {
					continue;
				}
				inventory[i] = containerItem;
			}
			if (!worldObj.isRemote) {
				for (i = 0; i < players.size(); ++i) {
					EntityPlayerMP entityplayer = (EntityPlayerMP) players.get(i);
					entityplayer.openContainer.detectAndSendChanges();
					entityplayer.sendContainerToPlayer(entityplayer.openContainer);
				}
			}
		} else if (barrelMode == 1 && inventory[9] != null && inventory[9].getItemDamage() > 0) {
			barrelMode = 2;
			brewingTime = 0;
			ItemStack itemstack = inventory[9].copy();
			itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			inventory[9] = itemstack;
		}
	}

	@Override
	public boolean hasCustomInventoryName() {
		return specialBarrelName != null && !specialBarrelName.isEmpty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (ArrayUtils.contains(INGREDIENT_SLOTS, slot)) {
			return true;
		}
		if (ArrayUtils.contains(BUCKET_SLOTS, slot)) {
			return LOTRBrewingRecipes.isWaterSource(itemstack);
		}
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readBarrelFromNBT(data);
	}

	@Override
	public void openInventory() {
	}

	public void poisonBarrel(EntityPlayer entityplayer) {
		ItemStack itemstack = inventory[9];
		LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
		LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
	}

	public void readBarrelFromNBT(NBTTagCompound nbt) {
		NBTTagList items = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte slot = itemData.getByte("Slot");
			if (slot < 0 || slot >= inventory.length) {
				continue;
			}
			inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
		}
		barrelMode = nbt.getByte("BarrelMode");
		brewingTime = nbt.getInteger("BrewingTime");
		if (nbt.hasKey("CustomName")) {
			specialBarrelName = nbt.getString("CustomName");
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readBarrelFromNBT(nbt);
	}

	public void setBarrelName(String s) {
		specialBarrelName = s;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		if (i != 9) {
			updateBrewingRecipe();
		}
	}

	public void updateBrewingRecipe() {
		if (barrelMode == 0) {
			inventory[9] = LOTRBrewingRecipes.findMatchingRecipe(this);
		}
	}

	@Override
	public void updateEntity() {
		boolean needUpdate = false;
		if (worldObj.isRemote) {
			brewingAnimPrev = brewingAnim++;
			if (barrelMode == 1) {
				if (brewingAnim >= 32) {
					brewingAnimPrev = brewingAnim = 0;
				}
			} else {
				brewingAnimPrev = brewingAnim = 0;
			}
		} else {
			if (barrelMode == 1) {
				if (inventory[9] != null) {
					++brewingTime;
					if (brewingTime >= 12000) {
						brewingTime = 0;
						if (inventory[9].getItemDamage() < 4) {
							inventory[9].setItemDamage(inventory[9].getItemDamage() + 1);
							needUpdate = true;
						} else {
							barrelMode = 2;
						}
					}
				} else {
					barrelMode = 0;
				}
			} else {
				brewingTime = 0;
			}
			if (barrelMode == 2 && inventory[9] == null) {
				barrelMode = 0;
			}
		}
		if (needUpdate) {
			markDirty();
		}
	}

	public void writeBarrelToNBT(NBTTagCompound nbt) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i) {
			if (inventory[i] == null) {
				continue;
			}
			NBTTagCompound itemData = new NBTTagCompound();
			itemData.setByte("Slot", (byte) i);
			inventory[i].writeToNBT(itemData);
			items.appendTag(itemData);
		}
		nbt.setTag("Items", items);
		nbt.setByte("BarrelMode", (byte) barrelMode);
		nbt.setInteger("BrewingTime", brewingTime);
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", specialBarrelName);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeBarrelToNBT(nbt);
	}
}
