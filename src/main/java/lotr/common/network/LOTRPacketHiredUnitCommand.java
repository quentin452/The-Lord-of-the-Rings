package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketHiredUnitCommand implements IMessage {
	public int entityID;
	public int page;
	public int action;
	public int value;

	public LOTRPacketHiredUnitCommand() {
	}

	public LOTRPacketHiredUnitCommand(int eid, int p, int a, int v) {
		entityID = eid;
		page = p;
		action = a;
		value = v;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		page = data.readByte();
		action = data.readByte();
		value = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(page);
		data.writeByte(action);
		data.writeByte(value);
	}

	public static class Handler implements IMessageHandler<LOTRPacketHiredUnitCommand, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketHiredUnitCommand packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.entityID);
			if (npc instanceof LOTREntityNPC) {
				LOTREntityNPC hiredNPC = (LOTREntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					int page = packet.page;
					int action = packet.action;
					int value = packet.value;
					if (action == -1) {
						hiredNPC.hiredNPCInfo.isGuiOpen = false;
					} else {
						LOTRHiredNPCInfo.Task task = hiredNPC.hiredNPCInfo.getTask();
						if (task == LOTRHiredNPCInfo.Task.WARRIOR) {
							if (page == 0) {
								entityplayer.openGui(LOTRMod.instance, 46, world, hiredNPC.getEntityId(), 0, 0);
							} else if (page == 1) {
								switch (action) {
									case 0:
										hiredNPC.hiredNPCInfo.teleportAutomatically = !hiredNPC.hiredNPCInfo.teleportAutomatically;
										break;
									case 1:
										hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
										break;
									case 2:
										hiredNPC.hiredNPCInfo.setGuardRange(value);
										break;
									default:
										break;
								}
							}
						} else if (task == LOTRHiredNPCInfo.Task.FARMER) {
							switch (action) {
								case 0:
									hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
									break;
								case 1:
									hiredNPC.hiredNPCInfo.setGuardRange(value);
									break;
								case 2:
									entityplayer.openGui(LOTRMod.instance, 22, world, hiredNPC.getEntityId(), 0, 0);
									break;
								default:
									break;
							}
						}
						hiredNPC.hiredNPCInfo.sendClientPacket(false);
					}
				}
			}
			return null;
		}
	}

}
