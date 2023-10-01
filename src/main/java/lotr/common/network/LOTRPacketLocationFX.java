package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.client.fx.LOTREntitySwordCommandMarker;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRPacketLocationFX implements IMessage {
	public Type type;
	public double posX;
	public double posY;
	public double posZ;

	public LOTRPacketLocationFX() {
	}

	public LOTRPacketLocationFX(Type t, double x, double y, double z) {
		type = t;
		posX = x;
		posY = y;
		posZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = Type.values()[typeID];
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
	}

	public enum Type {
		SWORD_COMMAND

	}

	public static class Handler implements IMessageHandler<LOTRPacketLocationFX, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketLocationFX packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			double x = packet.posX;
			double y = packet.posY;
			double z = packet.posZ;
			if (packet.type == Type.SWORD_COMMAND) {
				LOTREntitySwordCommandMarker marker = new LOTREntitySwordCommandMarker(world, x, y + 6.0, z);
				world.spawnEntityInWorld(marker);
			}
			return null;
		}
	}

}
