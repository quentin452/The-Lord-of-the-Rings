package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class LOTRPacketFactionData implements IMessage {
	public LOTRFaction faction;
	public NBTTagCompound factionNBT;

	public LOTRPacketFactionData() {
	}

	public LOTRPacketFactionData(LOTRFaction f, NBTTagCompound nbt) {
		faction = f;
		factionNBT = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte factionID = data.readByte();
		faction = LOTRFaction.forID(factionID);
		try {
			factionNBT = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading faction data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(faction.ordinal());
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(factionNBT);
		} catch (IOException e) {
			FMLLog.severe("Error writing faction data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketFactionData, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFactionData packet, MessageContext context) {
			if (!LOTRMod.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
				LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
				LOTRFaction faction = packet.faction;
				if (faction != null) {
					LOTRFactionData factionData = pd.getFactionData(faction);
					factionData.load(packet.factionNBT);
				}
			}
			return null;
		}
	}

}
