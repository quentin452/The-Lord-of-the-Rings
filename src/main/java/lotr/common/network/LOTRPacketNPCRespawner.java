package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class LOTRPacketNPCRespawner implements IMessage {
	public int spawnerID;
	public NBTTagCompound spawnerData;

	public LOTRPacketNPCRespawner() {
	}

	public LOTRPacketNPCRespawner(LOTREntityNPCRespawner spawner) {
		spawnerID = spawner.getEntityId();
		spawnerData = new NBTTagCompound();
		spawner.writeSpawnerDataToNBT(spawnerData);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		spawnerID = data.readInt();
		try {
			spawnerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading spawner data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(spawnerID);
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(spawnerData);
		} catch (IOException e) {
			FMLLog.severe("Error writing spawner data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCRespawner, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCRespawner packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			Entity entity = world.getEntityByID(packet.spawnerID);
			if (entity instanceof LOTREntityNPCRespawner) {
				LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) entity;
				spawner.readSpawnerDataFromNBT(packet.spawnerData);
				entityplayer.openGui(LOTRMod.instance, 45, world, entity.getEntityId(), 0, 0);
			}
			return null;
		}
	}

}
