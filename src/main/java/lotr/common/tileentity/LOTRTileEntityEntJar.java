package lotr.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityEntJar extends TileEntity {
	public static int MAX_CAPACITY = 6;
	public int drinkMeta = -1;
	public int drinkAmount;

	public void consume() {
		--drinkAmount;
		if (drinkAmount <= 0) {
			drinkMeta = -1;
		}
		drinkAmount = Math.max(drinkAmount, 0);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	public boolean fillFromBowl(ItemStack itemstack) {
		if (drinkMeta == -1 && drinkAmount == 0) {
			drinkMeta = itemstack.getItemDamage();
			++drinkAmount;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
			return true;
		}
		if (drinkMeta == itemstack.getItemDamage() && drinkAmount < MAX_CAPACITY) {
			++drinkAmount;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
			return true;
		}
		return false;
	}

	public void fillWithWater() {
		if (drinkMeta == -1 && drinkAmount < MAX_CAPACITY) {
			++drinkAmount;
		}
		drinkAmount = Math.min(drinkAmount, MAX_CAPACITY);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readFromNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		drinkMeta = nbt.getInteger("DrinkMeta");
		drinkAmount = nbt.getInteger("DrinkAmount");
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && (worldObj.canLightningStrikeAt(xCoord, yCoord, zCoord) || worldObj.canLightningStrikeAt(xCoord, yCoord + 1, zCoord)) && worldObj.rand.nextInt(2500) == 0) {
			fillWithWater();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("DrinkMeta", drinkMeta);
		nbt.setInteger("DrinkAmount", drinkAmount);
	}
}
