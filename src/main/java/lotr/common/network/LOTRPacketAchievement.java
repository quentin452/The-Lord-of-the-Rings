package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketAchievement implements IMessage {
	public LOTRAchievement achievement;
	public boolean display;

	public LOTRPacketAchievement() {
	}

	public LOTRPacketAchievement(LOTRAchievement ach, boolean disp) {
		achievement = ach;
		display = disp;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte catID = data.readByte();
		short achID = data.readShort();
		LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
		achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
		display = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(achievement.category.ordinal());
		data.writeShort(achievement.ID);
		data.writeBoolean(display);
	}

	public static class Handler implements IMessageHandler<LOTRPacketAchievement, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAchievement packet, MessageContext context) {
			LOTRAchievement achievement = packet.achievement;
			if (achievement != null) {
				if (!LOTRMod.proxy.isSingleplayer()) {
					EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
					LOTRLevelData.getData(entityplayer).addAchievement(achievement);
				}
				if (packet.display) {
					LOTRMod.proxy.queueAchievement(achievement);
				}
			}
			return null;
		}
	}

}
