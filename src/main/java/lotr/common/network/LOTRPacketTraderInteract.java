package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketTraderInteract implements IMessage {
	public int traderID;
	public int traderAction;

	public LOTRPacketTraderInteract() {
	}

	public LOTRPacketTraderInteract(int idt, int a) {
		traderID = idt;
		traderAction = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		traderID = data.readInt();
		traderAction = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(traderID);
		data.writeByte(traderAction);
	}

	public static class Handler implements IMessageHandler<LOTRPacketTraderInteract, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketTraderInteract packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity trader = world.getEntityByID(packet.traderID);
			if (trader instanceof LOTRTradeable) {
				LOTRTradeable tradeableTrader = (LOTRTradeable) trader;
				LOTREntityNPC livingTrader = (LOTREntityNPC) trader;
				int action = packet.traderAction;
				boolean closeScreen = false;
				if (action == 0) {
					livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
					closeScreen = livingTrader.interactFirst(entityplayer);
				} else if (action == 1 && tradeableTrader.canTradeWith(entityplayer)) {
					entityplayer.openGui(LOTRMod.instance, 3, world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 2 && tradeableTrader.canTradeWith(entityplayer)) {
					entityplayer.openGui(LOTRMod.instance, 35, world, livingTrader.getEntityId(), 0, 0);
				} else if (action == 3 && tradeableTrader.canTradeWith(entityplayer) && tradeableTrader instanceof LOTRTradeable.Smith) {
					entityplayer.openGui(LOTRMod.instance, 54, world, livingTrader.getEntityId(), 0, 0);
				}
				if (closeScreen) {
					entityplayer.closeScreen();
				}
			}
			return null;
		}
	}

}
