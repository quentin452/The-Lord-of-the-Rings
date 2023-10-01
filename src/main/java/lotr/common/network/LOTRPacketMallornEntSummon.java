package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.entity.npc.LOTREntityTree;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketMallornEntSummon implements IMessage {
	public int mallornEntID;
	public int summonedID;

	public LOTRPacketMallornEntSummon() {
	}

	public LOTRPacketMallornEntSummon(int id1, int id2) {
		mallornEntID = id1;
		summonedID = id1;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		mallornEntID = data.readInt();
		summonedID = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(mallornEntID);
		data.writeInt(summonedID);
	}

	public static class Handler implements IMessageHandler<LOTRPacketMallornEntSummon, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMallornEntSummon packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.mallornEntID);
			if (entity instanceof LOTREntityMallornEnt) {
				LOTREntityMallornEnt ent = (LOTREntityMallornEnt) entity;
				Entity summoned = world.getEntityByID(packet.summonedID);
				if (summoned instanceof LOTREntityTree) {
					ent.spawnEntSummonParticles((LOTREntityTree) summoned);
				}
			}
			return null;
		}
	}

}
