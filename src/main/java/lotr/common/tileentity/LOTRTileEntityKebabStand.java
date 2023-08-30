package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.*;

public class LOTRTileEntityKebabStand extends TileEntity implements IInventory {
	public static int MEAT_SLOTS = 8;
	public ItemStack[] inventory = new ItemStack[8];
	public boolean[] cooked = new boolean[8];
	public int cookTime;
	public int fuelTime;
	public boolean cookedClient;
	public boolean cookingClient;
	public int meatAmountClient;
	public float kebabSpin;
	public float prevKebabSpin;

	public void addFuel(int i) {
		fuelTime += i;
	}

	public boolean addMeat(ItemStack meat) {
		ItemStack copyMeat = meat.copy();
		copyMeat.stackSize = 1;
		boolean added = false;
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null) {
				continue;
			}
			setInventorySlotContents(i, copyMeat);
			cooked[i] = false;
			added = true;
			break;
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
		return added;
	}

	public boolean canCook() {
		return !isFullyCooked() && getMeatAmount() > 0;
	}

	@Override
	public void closeInventory() {
	}

	public void cookFirstMeat() {
		cookTime = 0;
		fuelTime -= 200;
		for (int i = getSizeInventory() - 1; i >= 0; --i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack == null || cooked[i]) {
				continue;
			}
			setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
			cooked[i] = true;
			break;
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

	public void generateCookedKebab(int kebab) {
		for (int i = 0; i < kebab && i < getSizeInventory(); ++i) {
			setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
			cooked[i] = true;
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("Cooked", isCooked());
		data.setBoolean("Cooking", isCooking());
		data.setByte("Meats", (byte) getMeatAmount());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@Override
	public String getInventoryName() {
		return "KebabStand";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public float getKebabSpin(float f) {
		return prevKebabSpin + (kebabSpin - prevKebabSpin) * f;
	}

	public int getMeatAmount() {
		if (worldObj != null && worldObj.isRemote) {
			return meatAmountClient;
		}
		int meats = 0;
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			++meats;
		}
		return meats;
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

	public String getStandTextureName() {
		Block block = getBlockType();
		if (block instanceof LOTRBlockKebabStand) {
			return ((LOTRBlockKebabStand) block).getStandTextureName();
		}
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	public boolean hasEmptyMeatSlot() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack != null) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean isCooked() {
		if (worldObj != null && worldObj.isRemote) {
			return cookedClient;
		}
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (!cooked[i]) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean isCooking() {
		if (worldObj != null && worldObj.isRemote) {
			return cookingClient;
		}
		return fuelTime > 0;
	}

	public boolean isFullyCooked() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);
			if (itemstack == null || cooked[i]) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return false;
	}

	public boolean isMeat(ItemStack meat) {
		if (meat == null) {
			return false;
		}
		Item item = meat.getItem();
		if (item instanceof ItemFood && ((ItemFood) item).isWolfsFavoriteMeat()) {
			ItemStack cookedFood = FurnaceRecipes.smelting().getSmeltingResult(meat);
			return cookedFood != null;
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
		cookedClient = data.getBoolean("Cooked");
		cookingClient = data.getBoolean("Cooking");
		meatAmountClient = data.getByte("Meats");
	}

	public void onReplaced() {
		stopCooking();
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readKebabStandFromNBT(nbt);
	}

	public void readKebabStandFromNBT(NBTTagCompound nbt) {
		NBTTagList items = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		cooked = new boolean[inventory.length];
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte slot = itemData.getByte("Slot");
			if (slot < 0 || slot >= inventory.length) {
				continue;
			}
			boolean slotItem = itemData.getBoolean("SlotItem");
			if (slotItem) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
			}
			cooked[i] = itemData.getBoolean("SlotCooked");
		}
		cookTime = nbt.getShort("CookTime");
		fuelTime = nbt.getShort("FuelTime");
	}

	public ItemStack removeFirstMeat() {
		ItemStack itemstack;
		int i;
		ItemStack meat = null;
		for (i = getSizeInventory() - 1; i >= 0; --i) {
			itemstack = getStackInSlot(i);
			if (itemstack == null || !cooked[i]) {
				continue;
			}
			meat = itemstack;
			setInventorySlotContents(i, null);
			cooked[i] = false;
			break;
		}
		if (meat == null) {
			for (i = getSizeInventory() - 1; i >= 0; --i) {
				itemstack = getStackInSlot(i);
				if (itemstack == null || cooked[i]) {
					continue;
				}
				meat = itemstack;
				setInventorySlotContents(i, null);
				break;
			}
		}
		if (isCooking() && getMeatAmount() == 0) {
			stopCooking();
		}
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
		return meat;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean shouldSaveBlockData() {
		return getMeatAmount() > 0;
	}

	public void startCooking(int i) {
		cookTime = 0;
		fuelTime = i;
	}

	public void stopCooking() {
		cookTime = 0;
		fuelTime = 0;
	}

	public int takeFuelFromBelow() {
		TileEntity belowTE = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		if (belowTE instanceof IInventory) {
			IInventory inv = (IInventory) belowTE;
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				if (itemstack == null || !TileEntityFurnace.isItemFuel(itemstack)) {
					continue;
				}
				int fuel = TileEntityFurnace.getItemBurnTime(itemstack);
				--itemstack.stackSize;
				if (itemstack.stackSize <= 0) {
					inv.setInventorySlotContents(i, null);
				} else {
					inv.setInventorySlotContents(i, itemstack);
				}
				return fuel;
			}
		}
		return 0;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			int fuel;
			boolean prevCooking = isCooking();
			boolean prevCooked = isCooked();
			if (isCooking()) {
				if (canCook()) {
					++cookTime;
					if (cookTime > fuelTime) {
						int fuel2 = takeFuelFromBelow();
						if (fuel2 > 0) {
							addFuel(fuel2);
						} else {
							stopCooking();
						}
					} else if (cookTime >= 200) {
						cookFirstMeat();
					}
				} else {
					stopCooking();
				}
			} else if (canCook() && (fuel = takeFuelFromBelow()) > 0) {
				startCooking(fuel);
			}
			if (isCooking() != prevCooking || isCooked() != prevCooked) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				markDirty();
			}
		} else if (isCooking()) {
			prevKebabSpin = kebabSpin;
			kebabSpin += 4.0f;
			if (worldObj.rand.nextInt(4) == 0) {
				double d = xCoord + worldObj.rand.nextFloat();
				double d1 = yCoord + worldObj.rand.nextFloat() * 0.2f;
				double d2 = zCoord + worldObj.rand.nextFloat();
				worldObj.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
				worldObj.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
			}
		} else if (kebabSpin > 0.0f) {
			prevKebabSpin = kebabSpin;
			kebabSpin += 20.0f;
			if ((float) Math.ceil(kebabSpin / 360.0f) > (float) Math.ceil(prevKebabSpin / 360.0f)) {
				float ds = kebabSpin - prevKebabSpin;
				kebabSpin = 0.0f;
				prevKebabSpin = kebabSpin - ds;
			}
		} else {
			kebabSpin = 0.0f;
			prevKebabSpin = 0.0f;
		}
	}

	public void writeKebabStandToNBT(NBTTagCompound nbt) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i) {
			NBTTagCompound itemData = new NBTTagCompound();
			itemData.setByte("Slot", (byte) i);
			ItemStack slotItem = inventory[i];
			boolean slotCooked = cooked[i];
			itemData.setBoolean("SlotItem", slotItem != null);
			if (slotItem != null) {
				slotItem.writeToNBT(itemData);
			}
			itemData.setBoolean("SlotCooked", slotCooked);
			items.appendTag(itemData);
		}
		nbt.setTag("Items", items);
		nbt.setShort("CookTime", (short) cookTime);
		nbt.setShort("FuelTime", (short) fuelTime);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeKebabStandToNBT(nbt);
	}
}
