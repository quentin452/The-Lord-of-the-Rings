package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketMiniquestOffer implements IMessage {
	public int entityID;
	public NBTTagCompound miniquestData;

	public LOTRPacketMiniquestOffer() {
	}

	public LOTRPacketMiniquestOffer(int id, NBTTagCompound nbt) {
		entityID = id;
		miniquestData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		try {
			miniquestData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading miniquest data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(miniquestData);
		} catch (IOException e) {
			FMLLog.severe("Error writing miniquest data");
			e.printStackTrace();
		}
	}

	public static void sendClosePacket(EntityPlayer entityplayer, LOTREntityNPC npc, boolean accept) {
		if (entityplayer == null) {
			FMLLog.warning("LOTR Warning: Tried to send miniquest offer close packet, but player == null");
			return;
		}
		IMessage packet = new LOTRPacketMiniquestOfferClose(npc.getEntityId(), accept);
		LOTRPacketHandler.networkWrapper.sendToServer(packet);
	}

	public static class Handler implements IMessageHandler<LOTRPacketMiniquestOffer, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMiniquestOffer packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				LOTREntityNPC npc = (LOTREntityNPC) entity;
				LOTRMiniQuest quest = LOTRMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
				if (quest != null) {
					LOTRMod.proxy.displayMiniquestOffer(quest, npc);
				} else {
					sendClosePacket(entityplayer, npc, false);
				}
			}
			return null;
		}
	}

}
