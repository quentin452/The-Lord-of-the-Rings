package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketAchievementRemove implements IMessage {
	public LOTRAchievement achievement;

	public LOTRPacketAchievementRemove() {
	}

	public LOTRPacketAchievementRemove(LOTRAchievement ach) {
		achievement = ach;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte catID = data.readByte();
		short achID = data.readShort();
		LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
		achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(achievement.category.ordinal());
		data.writeShort(achievement.ID);
	}

	public static class Handler implements IMessageHandler<LOTRPacketAchievementRemove, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketAchievementRemove packet, MessageContext context) {
			LOTRAchievement achievement = packet.achievement;
			if (achievement != null && !LOTRMod.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
				LOTRLevelData.getData(entityplayer).removeAchievement(achievement);
			}
			return null;
		}
	}

}
