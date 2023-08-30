package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketOptions implements IMessage {
	public int option;
	public boolean enable;

	public LOTRPacketOptions() {
	}

	public LOTRPacketOptions(int i, boolean flag) {
		option = i;
		enable = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		option = data.readByte();
		enable = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(option);
		data.writeBoolean(enable);
	}

	public static class Handler implements IMessageHandler<LOTRPacketOptions, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketOptions packet, MessageContext context) {
			if (!LOTRMod.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
				int option = packet.option;
				boolean enable = packet.enable;
				switch (option) {
					case 0:
						LOTRLevelData.getData(entityplayer).setFriendlyFire(enable);
						break;
					case 1:
						LOTRLevelData.getData(entityplayer).setEnableHiredDeathMessages(enable);
						break;
					case 3:
						LOTRLevelData.getData(entityplayer).setHideMapLocation(enable);
						break;
					case 4:
						LOTRLevelData.getData(entityplayer).setFemRankOverride(enable);
						break;
					case 5:
						LOTRLevelData.getData(entityplayer).setEnableConquestKills(enable);
						break;
					default:
						break;
				}
			}
			return null;
		}
	}

}
