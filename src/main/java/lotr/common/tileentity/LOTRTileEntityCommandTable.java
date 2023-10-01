package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class LOTRTileEntityCommandTable extends TileEntity {
	public int zoomExp;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeTableToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	public int getZoomExp() {
		return zoomExp;
	}

	public void setZoomExp(int i) {
		zoomExp = MathHelper.clamp_int(i, -2, 2);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readTableFromNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readTableFromNBT(nbt);
	}

	public void readTableFromNBT(NBTTagCompound nbt) {
		zoomExp = nbt.getByte("Zoom");
	}

	public void toggleZoomExp() {
		int z = zoomExp;
		z = z <= -2 ? 2 : --z;
		setZoomExp(z);
	}

	public void writeTableToNBT(NBTTagCompound nbt) {
		nbt.setByte("Zoom", (byte) zoomExp);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeTableToNBT(nbt);
	}
}
