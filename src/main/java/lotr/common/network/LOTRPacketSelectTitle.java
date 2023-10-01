package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

public class LOTRPacketSelectTitle implements IMessage {
	public LOTRTitle.PlayerTitle playerTitle;

	public LOTRPacketSelectTitle() {
	}

	public LOTRPacketSelectTitle(LOTRTitle.PlayerTitle t) {
		playerTitle = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		short titleID = data.readShort();
		LOTRTitle title = LOTRTitle.forID(titleID);
		byte colorID = data.readByte();
		EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorID);
		if (title != null && color != null) {
			playerTitle = new LOTRTitle.PlayerTitle(title, color);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (playerTitle == null) {
			data.writeShort(-1);
			data.writeByte(-1);
		} else {
			data.writeShort(playerTitle.getTitle().titleID);
			data.writeByte(playerTitle.getColor().getFormattingCode());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketSelectTitle, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSelectTitle packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRTitle.PlayerTitle title = packet.playerTitle;
			if (title == null) {
				pd.setPlayerTitle(null);
			} else if (title.getTitle().canPlayerUse(entityplayer)) {
				pd.setPlayerTitle(title);
			}
			return null;
		}
	}

}
