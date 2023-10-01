package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class LOTRPacketDate implements IMessage {
	public NBTTagCompound dateData;
	public boolean doUpdate;

	public LOTRPacketDate() {
	}

	public LOTRPacketDate(NBTTagCompound nbt, boolean flag) {
		dateData = nbt;
		doUpdate = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			dateData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading LOTR date");
			e.printStackTrace();
		}
		doUpdate = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(dateData);
		} catch (IOException e) {
			FMLLog.severe("Error writing LOTR date");
			e.printStackTrace();
		}
		data.writeBoolean(doUpdate);
	}

	public static class Handler implements IMessageHandler<LOTRPacketDate, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketDate packet, MessageContext context) {
			LOTRDate.loadDates(packet.dateData);
			if (packet.doUpdate) {
				LOTRMod.proxy.displayNewDate();
			}
			return null;
		}
	}

}
