package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketMiniquestOfferClose implements IMessage {
	public int npcID;
	public boolean accept;

	public LOTRPacketMiniquestOfferClose() {
	}

	public LOTRPacketMiniquestOfferClose(int id, boolean flag) {
		npcID = id;
		accept = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		npcID = data.readInt();
		accept = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(npcID);
		data.writeBoolean(accept);
	}

	public static class Handler implements IMessageHandler<LOTRPacketMiniquestOfferClose, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMiniquestOfferClose packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npcEntity = world.getEntityByID(packet.npcID);
			if (npcEntity instanceof LOTREntityNPC) {
				LOTREntityNPC npc = (LOTREntityNPC) npcEntity;
				npc.questInfo.receiveOfferResponse(entityplayer, packet.accept);
			}
			return null;
		}
	}

}
