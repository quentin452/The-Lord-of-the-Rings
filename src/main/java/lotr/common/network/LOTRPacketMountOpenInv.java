package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketMountOpenInv implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<LOTRPacketMountOpenInv, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMountOpenInv packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			if (entityplayer.ridingEntity instanceof LOTREntityNPCRideable) {
				((LOTREntityNPCRideable) entityplayer.ridingEntity).openGUI(entityplayer);
			}
			return null;
		}
	}

}
