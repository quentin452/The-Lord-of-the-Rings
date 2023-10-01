package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCIsEating implements IMessage {
	public int entityID;
	public boolean isEating;

	public LOTRPacketNPCIsEating() {
	}

	public LOTRPacketNPCIsEating(int id, boolean flag) {
		entityID = id;
		isEating = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		isEating = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(isEating);
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCIsEating, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCIsEating packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).clientIsEating = packet.isEating;
			}
			return null;
		}
	}

}
