package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketSetPlayerRotation implements IMessage {
	public float rotYaw;

	public LOTRPacketSetPlayerRotation() {
	}

	public LOTRPacketSetPlayerRotation(float f) {
		rotYaw = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		rotYaw = data.readFloat();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeFloat(rotYaw);
	}

	public static class Handler implements IMessageHandler<LOTRPacketSetPlayerRotation, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSetPlayerRotation packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			if (entityplayer != null) {
				entityplayer.rotationYaw = packet.rotYaw;
			}
			return null;
		}
	}

}
