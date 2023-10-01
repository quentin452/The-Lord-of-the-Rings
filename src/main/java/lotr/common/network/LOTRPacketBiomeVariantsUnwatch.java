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

public class LOTRPacketBiomeVariantsUnwatch implements IMessage {
	public int chunkX;
	public int chunkZ;

	public LOTRPacketBiomeVariantsUnwatch() {
	}

	public LOTRPacketBiomeVariantsUnwatch(int x, int z) {
		chunkX = x;
		chunkZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		chunkX = data.readInt();
		chunkZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(chunkX);
		data.writeInt(chunkZ);
	}

	public static class Handler implements IMessageHandler<LOTRPacketBiomeVariantsUnwatch, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBiomeVariantsUnwatch packet, MessageContext context) {
			int chunkX;
			int chunkZ;
			World world = LOTRMod.proxy.getClientWorld();
			if (world.blockExists((chunkX = packet.chunkX) << 4, 0, (chunkZ = packet.chunkZ) << 4)) {
				LOTRBiomeVariantStorage.clearChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ));
			} else {
				FMLLog.severe("Client received LOTR biome variant unwatch packet for nonexistent chunk at %d, %d", chunkX << 4, chunkZ << 4);
			}
			return null;
		}
	}

}
