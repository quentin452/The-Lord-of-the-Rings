package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.*;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.StringUtils;

public class LOTRPacketBuyUnit implements IMessage {
	public int tradeIndex;
	public String squadron;

	public LOTRPacketBuyUnit() {
	}

	public LOTRPacketBuyUnit(int tr, String s) {
		tradeIndex = tr;
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		tradeIndex = data.readByte();
		int squadronLength = data.readInt();
		if (squadronLength > -1) {
			squadron = data.readBytes(squadronLength).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(tradeIndex);
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeInt(-1);
		} else {
			byte[] squadronBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(squadronBytes.length);
			data.writeBytes(squadronBytes);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketBuyUnit, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBuyUnit packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof LOTRContainerUnitTrade) {
				LOTRContainerUnitTrade tradeContainer = (LOTRContainerUnitTrade) container;
				LOTRHireableBase unitTrader = tradeContainer.theUnitTrader;
				int tradeIndex = packet.tradeIndex;
				LOTRUnitTradeEntry trade = null;
				if (unitTrader instanceof LOTRUnitTradeable) {
					LOTRUnitTradeEntry[] tradeList = ((LOTRUnitTradeable) unitTrader).getUnits().tradeEntries;
					if (tradeIndex >= 0 && tradeIndex < tradeList.length) {
						trade = tradeList[tradeIndex];
					}
				} else if (unitTrader instanceof LOTRMercenary) {
					trade = LOTRMercenaryTradeEntry.createFor((LOTRMercenary) unitTrader);
				}
				String squadron = packet.squadron;
				squadron = LOTRSquadrons.checkAcceptableLength(squadron);
				if (trade != null) {
					trade.hireUnit(entityplayer, unitTrader, squadron);
					if (unitTrader instanceof LOTRMercenary) {
						((EntityPlayer) entityplayer).closeScreen();
					}
				} else {
					LOTRLog.logger.error("LOTR: Error player " + entityplayer.getCommandSenderName() + " trying to hire unit from " + unitTrader.getNPCName() + " - trade is null or bad index!");
				}
			}
			return null;
		}
	}

}
