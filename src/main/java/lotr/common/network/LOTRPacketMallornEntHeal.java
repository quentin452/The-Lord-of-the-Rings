package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class LOTRPacketMallornEntHeal implements IMessage {
	public int entityID;
	public NBTTagCompound healingData;

	public LOTRPacketMallornEntHeal() {
	}

	public LOTRPacketMallornEntHeal(int id, NBTTagCompound nbt) {
		entityID = id;
		healingData = nbt;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		try {
			healingData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading MEnt healing data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(healingData);
		} catch (IOException e) {
			FMLLog.severe("Error writing MEnt healing data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketMallornEntHeal, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMallornEntHeal packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityMallornEnt) {
				LOTREntityMallornEnt ent = (LOTREntityMallornEnt) entity;
				ent.receiveClientHealing(packet.healingData);
			}
			return null;
		}
	}

}
