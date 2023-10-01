package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class LOTRPacketDeleteMiniquest implements IMessage {
	public UUID questUUID;
	public boolean completed;

	public LOTRPacketDeleteMiniquest() {
	}

	public LOTRPacketDeleteMiniquest(LOTRMiniQuest quest) {
		questUUID = quest.questUUID;
		completed = quest.isCompleted();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		questUUID = new UUID(data.readLong(), data.readLong());
		completed = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(questUUID.getMostSignificantBits());
		data.writeLong(questUUID.getLeastSignificantBits());
		data.writeBoolean(completed);
	}

	public static class Handler implements IMessageHandler<LOTRPacketDeleteMiniquest, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketDeleteMiniquest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.completed);
			if (removeQuest != null) {
				pd.removeMiniQuest(removeQuest, packet.completed);
			} else {
				FMLLog.warning("Tried to remove a LOTR miniquest that doesn't exist");
			}
			return null;
		}
	}

}
