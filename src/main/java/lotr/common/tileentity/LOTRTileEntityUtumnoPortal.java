package lotr.common.tileentity;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRTileEntityUtumnoPortal extends TileEntity {
	public static int WIDTH = 3;
	public static int HEIGHT = 30;
	public static int PORTAL_ABOVE = 2;
	public static int PORTAL_BELOW = 2;
	public static int TARGET_COORDINATE_RANGE = 50000;
	public static int TARGET_FUZZ_RANGE = 32;
	public int targetX;
	public int targetZ;
	public int targetResetTick;

	public LOTRTileEntityUtumnoPortal findActingTargetingPortal() {
		int range;
		for (int i = range = 8; i >= -range; --i) {
			for (int k = range; k >= -range; --k) {
				TileEntity te;
				int i1 = xCoord + i;
				int j1 = yCoord;
				int k1 = zCoord + k;
				if (worldObj.getBlock(i1, j1, k1) != getBlockType() || !((te = worldObj.getTileEntity(i1, j1, k1)) instanceof LOTRTileEntityUtumnoPortal)) {
					continue;
				}
				return (LOTRTileEntityUtumnoPortal) te;
			}
		}
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		double d = 256.0;
		return d * d;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 30, zCoord + 3);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		targetX = nbt.getInteger("TargetX");
		targetZ = nbt.getInteger("TargetZ");
		targetResetTick = nbt.getInteger("TargetReset");
	}

	public void transferEntity(Entity entity) {
		entity.fallDistance = 0.0f;
		if (!worldObj.isRemote) {
			LOTRTileEntityUtumnoPortal actingPortal = findActingTargetingPortal();
			int dimension = LOTRDimension.UTUMNO.dimensionID;
			LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
			teleporter.setTargetCoords(actingPortal.targetX, actingPortal.targetZ);
			if (entity instanceof EntityPlayerMP) {
				MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entity, dimension, teleporter);
			} else {
				LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
			}
			entity.fallDistance = 0.0f;
			actingPortal.targetResetTick = 1200;
		}
	}

	@Override
	public void updateEntity() {
		if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == getBlockType()) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		if (!worldObj.isRemote) {
			if (targetResetTick > 0) {
				--targetResetTick;
			} else {
				targetX = MathHelper.getRandomIntegerInRange(worldObj.rand, -50000, 50000);
				targetZ = MathHelper.getRandomIntegerInRange(worldObj.rand, -50000, 50000);
				targetResetTick = 1200;
			}
		}
		if (!worldObj.isRemote) {
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 8, yCoord, zCoord - 8, xCoord + 9, yCoord + 60, zCoord + 9));
			for (Object obj : players) {
				EntityPlayer entityplayer = (EntityPlayer) obj;
				LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.UTUMNO_WARN);
			}
		}
		if (!worldObj.isRemote && worldObj.rand.nextInt(2000) == 0) {
			String s = "ambient.cave.cave";
			if (worldObj.rand.nextBoolean()) {
				s = "lotr:wight.ambience";
			}
			float volume = 6.0f;
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, s, volume, 0.8f + worldObj.rand.nextFloat() * 0.2f);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("TargetX", targetX);
		nbt.setInteger("TargetZ", targetZ);
		nbt.setInteger("TargetReset", targetResetTick);
	}
}
