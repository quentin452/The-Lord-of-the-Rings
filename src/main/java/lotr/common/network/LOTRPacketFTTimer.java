package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketFTTimer implements IMessage {
	public int timer;

	public LOTRPacketFTTimer() {
	}

	public LOTRPacketFTTimer(int i) {
		timer = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		timer = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(timer);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFTTimer, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFTTimer packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRLevelData.getData(entityplayer).setTimeSinceFT(packet.timer);
			return null;
		}
	}

}
