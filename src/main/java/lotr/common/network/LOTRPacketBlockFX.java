package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRPacketBlockFX implements IMessage {
	public Type type;
	public int blockX;
	public int blockY;
	public int blockZ;

	public LOTRPacketBlockFX() {
	}

	public LOTRPacketBlockFX(Type t, int i, int j, int k) {
		type = t;
		blockX = i;
		blockY = j;
		blockZ = k;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = Type.values()[typeID];
		blockX = data.readInt();
		blockY = data.readInt();
		blockZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
		data.writeInt(blockX);
		data.writeInt(blockY);
		data.writeInt(blockZ);
	}

	public enum Type {
		UTUMNO_EVAPORATE

	}

	public static class Handler implements IMessageHandler<LOTRPacketBlockFX, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBlockFX packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			int i = packet.blockX;
			int j = packet.blockY;
			int k = packet.blockZ;
			Random rand = world.rand;
			if (packet.type == Type.UTUMNO_EVAPORATE) {
				for (int l = 0; l < 8; ++l) {
					world.spawnParticle("largesmoke", i + rand.nextFloat(), j + rand.nextFloat(), k + rand.nextFloat(), 0.0, 0.0, 0.0);
				}
			}
			return null;
		}
	}

}
