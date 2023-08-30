package lotr.common.tileentity;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.*;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityUtumnoReturnPortal extends TileEntity {
	public static int PORTAL_TOP = 250;
	public int beamCheck;
	public int ticksExisted;

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void updateEntity() {
		++ticksExisted;
		if (!worldObj.isRemote) {
			if (beamCheck % 20 == 0) {
				int i = xCoord;
				int k = zCoord;
				for (int j = yCoord + 1; j <= PORTAL_TOP; ++j) {
					worldObj.setBlock(i, j, k, LOTRMod.utumnoReturnLight, 0, 3);
				}
			}
			++beamCheck;
		}
		List nearbyEntities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, PORTAL_TOP, zCoord + 1));
		for (Object obj : nearbyEntities) {
			EntityPlayer entityplayer;
			Entity entity = (Entity) obj;
			if (LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO) {
				continue;
			}
			if (entity instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) entity;
				if (entityplayer.capabilities.isFlying) {
					continue;
				}
			}
			if (!worldObj.isRemote) {
				float accel = 0.2f;
				entity.motionY += accel;
				entity.motionY = Math.max(entity.motionY, 0.0);
				entity.setPosition(xCoord + 0.5, entity.boundingBox.minY, zCoord + 0.5);
				entity.fallDistance = 0.0f;
			}
			if (entity instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) entity;
				LOTRMod.proxy.setInUtumnoReturnPortal(entityplayer);
				if (entityplayer instanceof EntityPlayerMP) {
					EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
					IMessage packet = new LOTRPacketUtumnoReturn(entityplayer.posX, entityplayer.posZ);
					LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayermp);
					entityplayermp.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(entityplayer));
				}
			}
			if (worldObj.isRemote || entity.posY < PORTAL_TOP - 5.0) {
				continue;
			}
			int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
			LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
			if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP entityplayer2 = (EntityPlayerMP) entity;
				MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer2, dimension, teleporter);
				LOTRLevelData.getData(entityplayer2).addAchievement(LOTRAchievement.leaveUtumno);
				continue;
			}
			LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
		}
	}
}
