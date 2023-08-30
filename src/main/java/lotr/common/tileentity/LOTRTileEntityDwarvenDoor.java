package lotr.common.tileentity;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class LOTRTileEntityDwarvenDoor extends TileEntity {
	public static Map<ChunkCoordinates, Pair<Long, Integer>> replacementGlowTicks = new HashMap<>();
	public static int GLOW_RANGE = 12;
	public LOTRDwarvenGlowLogic glowLogic = new LOTRDwarvenGlowLogic().setPlayerRange(GLOW_RANGE);
	public LOTRBlockGateDwarvenIthildin.DoorSize doorSize;
	public int doorPosX;
	public int doorPosY;
	public int doorBaseX;
	public int doorBaseY;
	public int doorBaseZ;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeDoorToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public int getDoorPosX() {
		if (doorPosX < 0 || doorSize != null && doorPosX >= doorSize.width) {
			doorPosX = 0;
		}
		return doorPosX;
	}

	public int getDoorPosY() {
		if (doorPosY < 0 || doorSize != null && doorPosY >= doorSize.height) {
			doorPosY = 0;
		}
		return doorPosY;
	}

	public LOTRBlockGateDwarvenIthildin.DoorSize getDoorSize() {
		if (doorSize == null) {
			doorSize = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
		}
		return doorSize;
	}

	public float getGlowBrightness(float f) {
		TileEntity te;
		if (doorSize != null && worldObj.blockExists(doorBaseX, doorBaseY, doorBaseZ) && (te = worldObj.getTileEntity(doorPosX, doorBaseY, doorBaseZ)) instanceof LOTRTileEntityDwarvenDoor) {
			LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor) te;
			return otherDoor.glowLogic.getGlowBrightness(worldObj, doorPosX, doorBaseY, doorBaseZ, f);
		}
		return glowLogic.getGlowBrightness(worldObj, xCoord, yCoord, zCoord, f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		double range = GLOW_RANGE + 20;
		return range * range;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (worldObj.isRemote) {
			long time = worldObj.getTotalWorldTime();
			int glow = glowLogic.getGlowTick();
			ChunkCoordinates coords = new ChunkCoordinates(xCoord, yCoord, zCoord);
			replacementGlowTicks.put(coords, Pair.of(time, glow));
		}
	}

	public boolean isSameDoor(LOTRTileEntityDwarvenDoor other) {
		return doorBaseX == other.doorBaseX && doorBaseY == other.doorBaseY && doorBaseZ == other.doorBaseZ;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readDoorFromNBT(data);
	}

	public void readDoorFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("DoorSize")) {
			doorSize = LOTRBlockGateDwarvenIthildin.DoorSize.forName(nbt.getString("DoorSize"));
			doorPosX = nbt.getByte("DoorX");
			doorPosY = nbt.getByte("DoorY");
			doorBaseX = nbt.getInteger("DoorBaseX");
			doorBaseY = nbt.getInteger("DoorBaseY");
			doorBaseZ = nbt.getInteger("DoorBaseZ");
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readDoorFromNBT(nbt);
	}

	public void setDoorBasePos(int i, int j, int k) {
		doorBaseX = i;
		doorBaseY = j;
		doorBaseZ = k;
		glowLogic.resetGlowTick();
		markDirty();
	}

	public void setDoorSizeAndPos(LOTRBlockGateDwarvenIthildin.DoorSize size, int i, int j) {
		if (size == null) {
			size = LOTRBlockGateDwarvenIthildin.DoorSize._1x1;
		}
		doorSize = size;
		doorPosX = i;
		doorPosY = j;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (doorSize != null) {
			glowLogic.update(worldObj, doorBaseX, doorBaseY, doorBaseZ);
		} else {
			glowLogic.update(worldObj, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void validate() {
		super.validate();
		if (worldObj.isRemote) {
			ChunkCoordinates coords = new ChunkCoordinates(xCoord, yCoord, zCoord);
			long time = worldObj.getTotalWorldTime();
			if (replacementGlowTicks.containsKey(coords)) {
				Pair<Long, Integer> stored = replacementGlowTicks.get(coords);
				long storedTime = stored.getLeft();
				int glow = stored.getRight();
				if (time == storedTime) {
					glowLogic.setGlowTick(glow);
				}
				replacementGlowTicks.remove(coords);
			}
		}
	}

	public void writeDoorToNBT(NBTTagCompound nbt) {
		if (doorSize != null) {
			nbt.setString("DoorSize", doorSize.doorName);
			nbt.setByte("DoorX", (byte) doorPosX);
			nbt.setByte("DoorY", (byte) doorPosY);
			nbt.setInteger("DoorBaseX", doorBaseX);
			nbt.setInteger("DoorBaseY", doorBaseY);
			nbt.setInteger("DoorBaseZ", doorBaseZ);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeDoorToNBT(nbt);
	}
}
