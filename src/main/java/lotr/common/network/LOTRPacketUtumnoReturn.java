package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class LOTRPacketUtumnoReturn implements IMessage {
	public double posX;
	public double posZ;

	public LOTRPacketUtumnoReturn() {
	}

	public LOTRPacketUtumnoReturn(double x, double z) {
		posX = x;
		posZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readDouble();
		posZ = data.readDouble();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeDouble(posX);
		data.writeDouble(posZ);
	}

	public static class Handler implements IMessageHandler<LOTRPacketUtumnoReturn, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketUtumnoReturn packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			entityplayer.setPosition(packet.posX, entityplayer.posY, packet.posZ);
			LOTRMod.proxy.setUtumnoReturnPortalCoords(entityplayer, MathHelper.floor_double(packet.posX), MathHelper.floor_double(packet.posZ));
			return null;
		}
	}

}
