package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCCombatStance implements IMessage {
	public int entityID;
	public boolean combatStance;

	public LOTRPacketNPCCombatStance() {
	}

	public LOTRPacketNPCCombatStance(int id, boolean flag) {
		entityID = id;
		combatStance = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		combatStance = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(combatStance);
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCCombatStance, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCCombatStance packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).clientCombatStance = packet.combatStance;
			}
			return null;
		}
	}

}
