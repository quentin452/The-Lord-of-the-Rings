package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCIsOfferingQuest implements IMessage {
	public int entityID;
	public boolean offering;
	public int offerColor;

	public LOTRPacketNPCIsOfferingQuest() {
	}

	public LOTRPacketNPCIsOfferingQuest(int id, boolean flag, int color) {
		entityID = id;
		offering = flag;
		offerColor = color;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		offering = data.readBoolean();
		offerColor = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(offering);
		data.writeInt(offerColor);
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCIsOfferingQuest, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCIsOfferingQuest packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).questInfo.receiveData(packet);
			}
			return null;
		}
	}

}
