package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketSetOption implements IMessage {
	public int option;

	public LOTRPacketSetOption() {
	}

	public LOTRPacketSetOption(int i) {
		option = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		option = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(option);
	}

	public static class Handler implements IMessageHandler<LOTRPacketSetOption, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSetOption packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			switch (packet.option) {
				case 0: {
					boolean flag = pd.getFriendlyFire();
					pd.setFriendlyFire(!flag);
					break;
				}
				case 1: {
					boolean flag = pd.getEnableHiredDeathMessages();
					pd.setEnableHiredDeathMessages(!flag);
					break;
				}
				case 2: {
					boolean flag = pd.getHideAlignment();
					pd.setHideAlignment(!flag);
					break;
				}
				case 3: {
					boolean flag = pd.getHideMapLocation();
					pd.setHideMapLocation(!flag);
					break;
				}
				case 4: {
					boolean flag = pd.getFemRankOverride();
					pd.setFemRankOverride(!flag);
					break;
				}
				case 5: {
					boolean flag = pd.getEnableConquestKills();
					pd.setEnableConquestKills(!flag);
					break;
				}
				default:
					break;
			}
			return null;
		}
	}

}
