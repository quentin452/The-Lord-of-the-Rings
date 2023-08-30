package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketNPCSquadron implements IMessage {
	public int npcID;
	public String squadron;

	public LOTRPacketNPCSquadron() {
	}

	public LOTRPacketNPCSquadron(LOTREntityNPC npc, String s) {
		npcID = npc.getEntityId();
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		npcID = data.readInt();
		int length = data.readInt();
		if (length > -1) {
			squadron = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(npcID);
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeInt(-1);
		} else {
			byte[] sqBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(sqBytes.length);
			data.writeBytes(sqBytes);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCSquadron, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCSquadron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.npcID);
			if (npc instanceof LOTREntityNPC) {
				LOTREntityNPC hiredNPC = (LOTREntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					String squadron = packet.squadron;
					if (StringUtils.isNullOrEmpty(squadron)) {
						hiredNPC.hiredNPCInfo.setSquadron("");
					} else {
						squadron = LOTRSquadrons.checkAcceptableLength(squadron);
						hiredNPC.hiredNPCInfo.setSquadron(squadron);
					}
				}
			}
			return null;
		}
	}

}
