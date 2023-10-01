package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRShields;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketSelectShield implements IMessage {
	public LOTRShields shield;

	public LOTRPacketSelectShield() {
	}

	public LOTRPacketSelectShield(LOTRShields s) {
		shield = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte shieldID = data.readByte();
		if (shieldID == -1) {
			shield = null;
		} else {
			byte shieldTypeID = data.readByte();
			if (shieldTypeID < 0 || shieldTypeID >= LOTRShields.ShieldType.values().length) {
				FMLLog.severe("Failed to update LOTR shield on server side: There is no shieldtype with ID " + shieldTypeID);
			} else {
				LOTRShields.ShieldType shieldType = LOTRShields.ShieldType.values()[shieldTypeID];
				if (shieldID < 0 || shieldID >= shieldType.list.size()) {
					FMLLog.severe("Failed to update LOTR shield on server side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID);
				} else {
					shield = shieldType.list.get(shieldID);
				}
			}
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (shield == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(shield.getShieldId());
			data.writeByte(shield.getShieldType().ordinal());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketSelectShield, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSelectShield packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRShields shield = packet.shield;
			if (shield == null || shield.canPlayerWear(entityplayer)) {
				LOTRLevelData.getData(entityplayer).setShield(shield);
				LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			} else {
				FMLLog.severe("Failed to update LOTR shield on server side: Player " + entityplayer.getCommandSenderName() + " cannot wear shield " + shield.name());
			}
			return null;
		}
	}

}
