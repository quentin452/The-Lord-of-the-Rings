package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipCreate implements IMessage {
	public String fellowshipName;

	public LOTRPacketFellowshipCreate() {
	}

	public LOTRPacketFellowshipCreate(String name) {
		fellowshipName = name;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		fellowshipName = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = fellowshipName.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipCreate, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipCreate packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
			playerData.createFellowship(packet.fellowshipName, true);
			return null;
		}
	}

}
