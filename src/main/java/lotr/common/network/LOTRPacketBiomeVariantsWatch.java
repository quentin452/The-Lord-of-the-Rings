package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class LOTRPacketBiomeVariantsWatch implements IMessage {
	public int chunkX;
	public int chunkZ;
	public byte[] variants;

	public LOTRPacketBiomeVariantsWatch() {
	}

	public LOTRPacketBiomeVariantsWatch(int x, int z, byte[] v) {
		chunkX = x;
		chunkZ = z;
		variants = v;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		chunkX = data.readInt();
		chunkZ = data.readInt();
		int length = data.readInt();
		variants = data.readBytes(length).array();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(chunkX);
		data.writeInt(chunkZ);
		data.writeInt(variants.length);
		data.writeBytes(variants);
	}

	public static class Handler implements IMessageHandler<LOTRPacketBiomeVariantsWatch, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBiomeVariantsWatch packet, MessageContext context) {
			int chunkX;
			int chunkZ;
			World world = LOTRMod.proxy.getClientWorld();
			if (world.blockExists((chunkX = packet.chunkX) << 4, 0, (chunkZ = packet.chunkZ) << 4)) {
				LOTRBiomeVariantStorage.setChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ), packet.variants);
			} else {
				FMLLog.severe("Client received LOTR biome variant data for nonexistent chunk at %d, %d", chunkX << 4, chunkZ << 4);
			}
			return null;
		}
	}

}
