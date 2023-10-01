package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.inventory.LOTRContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class LOTRPacketTraderInfo implements IMessage {
	public NBTTagCompound traderData;

	public LOTRPacketTraderInfo() {
	}

	public LOTRPacketTraderInfo(NBTTagCompound nbt) {
		traderData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			traderData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading trader data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(traderData);
		} catch (IOException e) {
			FMLLog.severe("Error writing trader data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketTraderInfo, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketTraderInfo packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			Container container = entityplayer.openContainer;
			if (container instanceof LOTRContainerTrade) {
				LOTRContainerTrade containerTrade = (LOTRContainerTrade) container;
				containerTrade.theTraderNPC.traderNPCInfo.receiveClientPacket(packet);
			}
			return null;
		}
	}

}
