package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class LOTRPacketLoginPlayerData implements IMessage {
	public NBTTagCompound playerData;

	public LOTRPacketLoginPlayerData() {
	}

	public LOTRPacketLoginPlayerData(NBTTagCompound nbt) {
		playerData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			playerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading LOTR login player data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(playerData);
		} catch (IOException e) {
			FMLLog.severe("Error writing LOTR login player data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketLoginPlayerData, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketLoginPlayerData packet, MessageContext context) {
			NBTTagCompound nbt = packet.playerData;
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			if (!LOTRMod.proxy.isSingleplayer()) {
				pd.load(nbt);
			}
			LOTRMod.proxy.setWaypointModes(pd.showWaypoints(), pd.showCustomWaypoints(), pd.showHiddenSharedWaypoints());
			return null;
		}
	}

}
