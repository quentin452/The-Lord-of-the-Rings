package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityTrollTotem extends TileEntity {
	public int prevJawRotation;
	public int jawRotation;
	public boolean prevCanSummon;
	public boolean clientCanSummon;

	public boolean canSummon() {
		if (worldObj.isRemote) {
			return clientCanSummon;
		}
		if (worldObj.isDaytime() || !worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
			return false;
		}
		if (getBlockType() == LOTRMod.trollTotem && (getBlockMetadata() & 3) == 0) {
			int rotation = (getBlockMetadata() & 0xC) >> 2;
			return worldObj.getBlock(xCoord, yCoord - 1, zCoord) == LOTRMod.trollTotem && worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord) == (1 | rotation << 2) && worldObj.getBlock(xCoord, yCoord - 2, zCoord) == LOTRMod.trollTotem && worldObj.getBlockMetadata(xCoord, yCoord - 2, zCoord) == (2 | rotation << 2);
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("CanSummon", canSummon());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public float getJawRotation(float f) {
		float rotation = prevJawRotation + (jawRotation - prevJawRotation) * f;
		return rotation / 60.0f * -35.0f;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		clientCanSummon = data.getBoolean("CanSummon");
	}

	public void summon() {
		if (!worldObj.isRemote) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
			worldObj.setBlockToAir(xCoord, yCoord - 2, zCoord);
			LOTREntityMountainTrollChieftain troll = new LOTREntityMountainTrollChieftain(worldObj);
			troll.setLocationAndAngles(xCoord + 0.5, yCoord - 2, zCoord + 0.5, worldObj.rand.nextFloat() * 360.0f, 0.0f);
			troll.onSpawnWithEgg(null);
			worldObj.spawnEntityInWorld(troll);
		}
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote) {
			prevJawRotation = jawRotation;
			if (jawRotation < 60 && canSummon()) {
				++jawRotation;
			} else if (jawRotation > 0 && !canSummon()) {
				--jawRotation;
			}
		} else {
			boolean flag = canSummon();
			if (flag != prevCanSummon) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			prevCanSummon = flag;
		}
	}
}
