package lotr.common;

import com.google.common.primitives.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

public class LOTRNetHandlerPlayServer extends NetHandlerPlayServer {
	public MinecraftServer theServer;
	public double defaultReach = -1.0;
	public int lastAttackTime;
	public double lastX;
	public double lastY;
	public double lastZ;
	public int floatingMountTick;

	public LOTRNetHandlerPlayServer(MinecraftServer server, NetworkManager nm, EntityPlayerMP entityplayer) {
		super(server, nm, entityplayer);
		theServer = server;
	}

	@Override
	public void processInput(C0CPacketInput packet) {
		super.processInput(packet);
		float forward = packet.func_149616_d();
		float strafing = packet.func_149620_c();
		boolean jump = packet.func_149618_e();
		if (forward != 0.0f || strafing != 0.0f || jump) {
			LOTRLevelData.getData(playerEntity).cancelFastTravel();
		}
	}

	public void processMountControl(LOTRPacketMountControl packet) {
		double x = packet.posX;
		double y = packet.posY;
		double z = packet.posZ;
		float yaw = packet.rotationYaw;
		float pitch = packet.rotationPitch;
		if (!Doubles.isFinite(x) || !Doubles.isFinite(y) || !Doubles.isFinite(z) || !Floats.isFinite(yaw) || !Floats.isFinite(pitch)) {
			playerEntity.playerNetServerHandler.kickPlayerFromServer("Invalid mount movement");
			return;
		}
		Entity mount = playerEntity.ridingEntity;
		if (mount != null && mount != playerEntity && mount.riddenByEntity == playerEntity && LOTRMountFunctions.isMountControllable(mount)) {
			WorldServer world = playerEntity.getServerForPlayer();
			MinecraftServer server = world.func_73046_m();
			double d0 = mount.posX;
			double dx = x - d0;
			double d1 = mount.posY;
			double dy = y - d1;
			double d2 = mount.posZ;
			double dz = z - d2;
			double distSq = dx * dx + dy * dy + dz * dz;
			double speedSq = mount.motionX * mount.motionX + mount.motionY * mount.motionY + mount.motionZ * mount.motionZ;
			if (distSq - speedSq > 150.0 && (!server.isSinglePlayer() || !server.getServerOwner().equals(playerEntity.getCommandSenderName()))) {
				IMessage pktClient = new LOTRPacketMountControlServerEnforce(mount);
				LOTRPacketHandler.networkWrapper.sendTo(pktClient, playerEntity);
				return;
			}
			double check = 0.0625;
			boolean noCollideBeforeMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
			dx = x - d0;
			dy = y - d1 - 1.0E-6;
			dz = z - d2;
			mount.moveEntity(dx, dy, dz);
			double movedY = dy;
			dx = x - mount.posX;
			dy = y - mount.posY;
			dz = z - mount.posZ;
			if (dy > -0.5 || dy < 0.5) {
				dy = 0.0;
			}
			distSq = dx * dx + dy * dy + dz * dz;
			boolean clientServerConflict = distSq > 10.0;
			mount.setPositionAndRotation(x, y, z, yaw, pitch);
			playerEntity.setPositionAndRotation(x, y, z, yaw, pitch);
			boolean noCollideAfterMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
			if (noCollideBeforeMove && (clientServerConflict || !noCollideAfterMove)) {
				mount.setPositionAndRotation(d0, d1, d2, yaw, pitch);
				playerEntity.setPositionAndRotation(d0, d1, d2, yaw, pitch);
				IMessage pktClient = new LOTRPacketMountControlServerEnforce(mount);
				LOTRPacketHandler.networkWrapper.sendTo(pktClient, playerEntity);
				return;
			}
			AxisAlignedBB flyCheckBox = mount.boundingBox.copy().expand(check, check, check).addCoord(0.0, -0.55, 0.0);
			if (!server.isFlightAllowed() && !world.checkBlockCollision(flyCheckBox)) {
				if (movedY >= -0.03125) {
					++floatingMountTick;
					if (floatingMountTick > 80) {
						FMLLog.warning(playerEntity.getCommandSenderName() + " was kicked for floating too long on mount " + mount.getCommandSenderName() + "!");
						kickPlayerFromServer("Flying is not enabled on this server");
						return;
					}
				}
			} else {
				floatingMountTick = 0;
			}
			server.getConfigurationManager().updatePlayerPertinentChunks(playerEntity);
			playerEntity.addMovementStat(playerEntity.posX - d0, playerEntity.posY - d1, playerEntity.posZ - d2);
		}
	}

	@Override
	public void processPlayer(C03PacketPlayer packet) {
		super.processPlayer(packet);
		if (!playerEntity.isRiding() && packet.func_149466_j()) {
			double newX = packet.func_149464_c();
			double newY = packet.func_149467_d();
			double newZ = packet.func_149472_e();
			if (newX != lastX || newY != lastY || newZ != lastZ) {
				LOTRLevelData.getData(playerEntity).cancelFastTravel();
			}
		}
		lastX = playerEntity.posX;
		lastY = playerEntity.posY;
		lastZ = playerEntity.posZ;
	}

	@Override
	public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packet) {
		setBlockReach();
		super.processPlayerBlockPlacement(packet);
	}

	@Override
	public void processPlayerDigging(C07PacketPlayerDigging packet) {
		setBlockReach();
		super.processPlayerDigging(packet);
	}

	@Override
	public void processUseEntity(C02PacketUseEntity packet) {
		WorldServer world = theServer.worldServerForDimension(playerEntity.dimension);
		Entity target = packet.func_149564_a(world);
		playerEntity.func_143004_u();
		if (target != null) {
			ItemStack itemstack = playerEntity.getHeldItem();
			double reach = LOTRWeaponStats.getMeleeReachDistance(playerEntity);
			reach += LOTRWeaponStats.getMeleeExtraLookWidth();
			reach += target.getCollisionBorderSize();
			int attackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
			if (playerEntity.getDistanceSqToEntity(target) < reach * reach) {
				if (packet.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
					playerEntity.interactWith(target);
				} else if (packet.func_149565_c() == C02PacketUseEntity.Action.ATTACK && (lastAttackTime <= 0 || !(target instanceof EntityLivingBase))) {
					if (target instanceof EntityItem || target instanceof EntityXPOrb || target instanceof EntityArrow || target == playerEntity) {
						kickPlayerFromServer("Attempting to attack an invalid entity");
						theServer.logWarning("Player " + playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
						return;
					}
					playerEntity.attackTargetEntityWithCurrentItem(target);
					lastAttackTime = attackTime;
				}
			}
		}
	}

	public void setBlockReach() {
		if (defaultReach == -1.0) {
			defaultReach = playerEntity.theItemInWorldManager.getBlockReachDistance();
		}
		double reach = defaultReach;
		playerEntity.theItemInWorldManager.setBlockReachDistance(reach * LOTRWeaponStats.getMeleeReachFactor(playerEntity.getHeldItem()));
	}

	public void update() {
		updateAttackTime();
	}

	public void updateAttackTime() {
		if (lastAttackTime > 0) {
			--lastAttackTime;
		}
	}
}
