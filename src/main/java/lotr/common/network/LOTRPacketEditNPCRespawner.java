package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class LOTRPacketEditNPCRespawner implements IMessage {
	public int spawnerID;
	public NBTTagCompound spawnerData;
	public boolean destroy;

	public LOTRPacketEditNPCRespawner() {
	}

	public LOTRPacketEditNPCRespawner(LOTREntityNPCRespawner spawner) {
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
		destroy = data.readBoolean();
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
		data.writeBoolean(destroy);
	}

	public static class Handler implements IMessageHandler<LOTRPacketEditNPCRespawner, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketEditNPCRespawner packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity sEntity = world.getEntityByID(packet.spawnerID);
			if (sEntity instanceof LOTREntityNPCRespawner) {
				LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) sEntity;
				if (entityplayer.capabilities.isCreativeMode) {
					spawner.readSpawnerDataFromNBT(packet.spawnerData);
				}
				if (packet.destroy) {
					spawner.onBreak();
				}
			}
			return null;
		}
	}

}
