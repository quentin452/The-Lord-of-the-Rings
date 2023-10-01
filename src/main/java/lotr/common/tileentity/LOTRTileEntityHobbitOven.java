package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockHobbitOven;
import lotr.common.inventory.LOTRSlotStackSize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collections;

public class LOTRTileEntityHobbitOven extends TileEntity implements ISidedInventory {
	public ItemStack[] inventory = new ItemStack[19];
	public int ovenCookTime;
	public int currentItemFuelValue;
	public int currentCookTime;
	public String specialOvenName;
	public int[] inputSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	public int[] outputSlots = {9, 10, 11, 12, 13, 14, 15, 16, 17};
	public int fuelSlot = 18;

	public static boolean isCookResultAcceptable(ItemStack result) {
		if (result == null) {
			return false;
		}
		Item item = result.getItem();
		return item instanceof ItemFood || item == LOTRMod.pipeweed || item == Item.getItemFromBlock(LOTRMod.driedReeds);
	}

	public boolean canCook(int i) {
		if (inventory[i] == null) {
			return false;
		}
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inventory[i]);
		if (!isCookResultAcceptable(result)) {
			return false;
		}
		if (inventory[i + 9] == null) {
			return true;
		}
		if (!inventory[i + 9].isItemEqual(result)) {
			return false;
		}
		int resultSize = inventory[i + 9].stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
	}

	public boolean canCookAnyItem() {
		for (int i = 0; i < 9; ++i) {
			if (!canCook(i)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if (side == 0 && slot == fuelSlot) {
			return itemstack.getItem() == Items.bucket;
		}
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public void closeInventory() {
	}

	public void cookItem(int i) {
		if (canCook(i)) {
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(inventory[i]);
			if (inventory[i + 9] == null) {
				inventory[i + 9] = itemstack.copy();
			} else if (inventory[i + 9].isItemEqual(itemstack)) {
				inventory[i + 9].stackSize += itemstack.stackSize;
			}
			--inventory[i].stackSize;
			if (inventory[i].stackSize <= 0) {
				inventory[i] = null;
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack;
		}
		return null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == 0) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int i : outputSlots) {
				list.add(i);
			}
			list.add(fuelSlot);
			int[] temp = new int[list.size()];
			for (int i = 0; i < temp.length; ++i) {
				temp[i] = list.get(i);
			}
			return temp;
		}
		if (side == 1) {
			ArrayList<LOTRSlotStackSize> list = new ArrayList<>();
			for (int slot : inputSlots) {
				int size = getStackInSlot(slot) == null ? 0 : getStackInSlot(slot).stackSize;
				list.add(new LOTRSlotStackSize(slot, size));
			}
			Collections.sort(list);
			int[] temp = new int[inputSlots.length];
			for (int i = 0; i < temp.length; ++i) {
				LOTRSlotStackSize obj = list.get(i);
				temp[i] = obj.slot;
			}
			return temp;
		}
		return new int[]{fuelSlot};
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int i) {
		return currentCookTime * i / 400;
	}

	@SideOnly(Side.CLIENT)
	public int getCookTimeRemainingScaled(int i) {
		if (currentItemFuelValue == 0) {
			currentItemFuelValue = 400;
		}
		return ovenCookTime * i / currentItemFuelValue;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? specialOvenName : StatCollector.translateToLocal("container.lotr.hobbitOven");
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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

	@Override
	public boolean hasCustomInventoryName() {
		return specialOvenName != null && !specialOvenName.isEmpty();
	}

	public boolean isCooking() {
		return ovenCookTime > 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (slot < 9) {
			return itemstack != null && isCookResultAcceptable(FurnaceRecipes.smelting().getSmeltingResult(itemstack));
		}
		if (slot < 18) {
			return false;
		}
		return TileEntityFurnace.isItemFuel(itemstack);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
		if (packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
			specialOvenName = packet.func_148857_g().getString("CustomName");
		}
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList items = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte byte0 = itemData.getByte("Slot");
			if (byte0 < 0 || byte0 >= inventory.length) {
				continue;
			}
			inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
		}
		ovenCookTime = nbt.getShort("BurnTime");
		currentCookTime = nbt.getShort("CookTime");
		currentItemFuelValue = TileEntityFurnace.getItemBurnTime(inventory[18]);
		if (nbt.hasKey("CustomName")) {
			specialOvenName = nbt.getString("CustomName");
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void setOvenName(String s) {
		specialOvenName = s;
	}

	@Override
	public void updateEntity() {
		boolean cooking = ovenCookTime > 0;
		boolean needUpdate = false;
		if (ovenCookTime > 0) {
			--ovenCookTime;
		}
		if (!worldObj.isRemote) {
			if (ovenCookTime == 0 && canCookAnyItem()) {
				currentItemFuelValue = ovenCookTime = TileEntityFurnace.getItemBurnTime(inventory[18]);
				if (ovenCookTime > 0) {
					needUpdate = true;
					if (inventory[18] != null) {
						--inventory[18].stackSize;
						if (inventory[18].stackSize == 0) {
							inventory[18] = inventory[18].getItem().getContainerItem(inventory[18]);
						}
					}
				}
			}
			if (isCooking() && canCookAnyItem()) {
				++currentCookTime;
				if (currentCookTime == 400) {
					currentCookTime = 0;
					for (int i = 0; i < 9; ++i) {
						cookItem(i);
					}
					needUpdate = true;
				}
			} else {
				currentCookTime = 0;
			}
			if (cooking != ovenCookTime > 0) {
				needUpdate = true;
				LOTRBlockHobbitOven.setOvenActive(worldObj, xCoord, yCoord, zCoord);
			}
		}
		if (needUpdate) {
			markDirty();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
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
		nbt.setShort("BurnTime", (short) ovenCookTime);
		nbt.setShort("CookTime", (short) currentCookTime);
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", specialOvenName);
		}
	}
}
