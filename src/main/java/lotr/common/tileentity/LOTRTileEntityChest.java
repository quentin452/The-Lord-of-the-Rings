package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class LOTRTileEntityChest extends TileEntity implements IInventory {
	public ItemStack[] chestContents = new ItemStack[getSizeInventory()];
	public float lidAngle;
	public float prevLidAngle;
	public String textureName;
	public int numPlayersUsing;
	public int ticksSinceSync;
	public String customName;

	@Override
	public void closeInventory() {
		if (getBlockType() instanceof LOTRBlockChest) {
			--numPlayersUsing;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (chestContents[i] != null) {
			if (chestContents[i].stackSize <= j) {
				ItemStack itemstack = chestContents[i];
				chestContents[i] = null;
				markDirty();
				return itemstack;
			}
			ItemStack itemstack = chestContents[i].splitStack(j);
			if (chestContents[i].stackSize == 0) {
				chestContents[i] = null;
			}
			markDirty();
			return itemstack;
		}
		return null;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? customName : "container.chest";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return chestContents[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (chestContents[i] != null) {
			ItemStack itemstack = chestContents[i];
			chestContents[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		updateContainingBlockInfo();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void openInventory() {
		if (numPlayersUsing < 0) {
			numPlayersUsing = 0;
		}
		++numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList itemTags = nbt.getTagList("Items", 10);
		chestContents = new ItemStack[getSizeInventory()];
		for (int i = 0; i < itemTags.tagCount(); ++i) {
			NBTTagCompound slotData = itemTags.getCompoundTagAt(i);
			int slot = slotData.getByte("Slot") & 0xFF;
			if (slot >= chestContents.length) {
				continue;
			}
			chestContents[slot] = ItemStack.loadItemStackFromNBT(slotData);
		}
		if (nbt.hasKey("CustomName", 8)) {
			customName = nbt.getString("CustomName");
		}
	}

	@Override
	public boolean receiveClientEvent(int i, int j) {
		if (i == 1) {
			numPlayersUsing = j;
			return true;
		}
		return super.receiveClientEvent(i, j);
	}

	public void setCustomName(String s) {
		customName = s;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		chestContents[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		prevLidAngle = lidAngle;
		++ticksSinceSync;
		if (!worldObj.isRemote && numPlayersUsing != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numPlayersUsing = 0;
			float range = 5.0f;
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + 1 + range, yCoord + 1 + range, zCoord + 1 + range));
			for (Object obj : players) {
				EntityPlayer entityplayer = (EntityPlayer) obj;
				if (!(entityplayer.openContainer instanceof ContainerChest) || ((ContainerChest) entityplayer.openContainer).getLowerChestInventory() != this) {
					continue;
				}
				++numPlayersUsing;
			}
		}
		if (numPlayersUsing > 0 && lidAngle == 0.0f) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.chestopen", 0.5f, worldObj.rand.nextFloat() * 0.1f + 0.9f);
		}
		if (numPlayersUsing == 0 && lidAngle > 0.0f || numPlayersUsing > 0 && lidAngle < 1.0f) {
			float pre = lidAngle;
			float incr = 0.1f;
			lidAngle = numPlayersUsing > 0 ? lidAngle + incr : lidAngle - incr;
			lidAngle = Math.min(lidAngle, 1.0f);
			lidAngle = Math.max(lidAngle, 0.0f);
			float thr = 0.5f;
			if (lidAngle < thr && pre >= thr) {
				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.chestclosed", 0.5f, worldObj.rand.nextFloat() * 0.1f + 0.9f);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList itemTags = new NBTTagList();
		for (int i = 0; i < chestContents.length; ++i) {
			if (chestContents[i] == null) {
				continue;
			}
			NBTTagCompound slotData = new NBTTagCompound();
			slotData.setByte("Slot", (byte) i);
			chestContents[i].writeToNBT(slotData);
			itemTags.appendTag(slotData);
		}
		nbt.setTag("Items", itemTags);
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", customName);
		}
	}
}
