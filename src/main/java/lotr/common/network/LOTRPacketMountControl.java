package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRNetHandlerPlayServer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class LOTRPacketMountControl implements IMessage {
	public double posX;
	public double posY;
	public double posZ;
	public float rotationYaw;
	public float rotationPitch;

	public LOTRPacketMountControl() {
	}

	public LOTRPacketMountControl(Entity entity) {
		posX = entity.posX;
		posY = entity.posY;
		posZ = entity.posZ;
		rotationYaw = entity.rotationYaw;
		rotationPitch = entity.rotationPitch;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
		rotationYaw = data.readFloat();
		rotationPitch = data.readFloat();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
		data.writeFloat(rotationYaw);
		data.writeFloat(rotationPitch);
	}

	public static class Handler implements IMessageHandler<LOTRPacketMountControl, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMountControl packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			NetHandlerPlayServer handler = entityplayer.playerNetServerHandler;
			if (handler instanceof LOTRNetHandlerPlayServer) {
				((LOTRNetHandlerPlayServer) handler).processMountControl(packet);
			}
			return null;
		}
	}

}
