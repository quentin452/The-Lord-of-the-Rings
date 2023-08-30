package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRPacketUtumnoKill implements IMessage {
	public int entityID;
	public int blockX;
	public int blockY;
	public int blockZ;

	public LOTRPacketUtumnoKill() {
	}

	public LOTRPacketUtumnoKill(int id, int i, int j, int k) {
		entityID = id;
		blockX = i;
		blockY = j;
		blockZ = k;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		blockX = data.readInt();
		blockY = data.readInt();
		blockZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(blockX);
		data.writeInt(blockY);
		data.writeInt(blockZ);
	}

	public static class Handler implements IMessageHandler<LOTRPacketUtumnoKill, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketUtumnoKill packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity != null) {
				double d1;
				double d4;
				double d;
				int l;
				double d2;
				double d3;
				double d5;
				int i1 = packet.blockX;
				int j1 = packet.blockY;
				int k1 = packet.blockZ;
				Block block = world.getBlock(i1, j1, k1);
				block.setBlockBoundsBasedOnState(world, i1, j1, k1);
				double blockTop = block.getBlockBoundsMaxY();
				for (l = 0; l < 8; ++l) {
					d = i1 + MathHelper.getRandomDoubleInRange(world.rand, 0.25, 0.75);
					d1 = j1 + 0.1;
					d2 = k1 + MathHelper.getRandomDoubleInRange(world.rand, 0.25, 0.75);
					d3 = 0.0;
					d4 = MathHelper.getRandomDoubleInRange(world.rand, 0.1, 0.2);
					d5 = 0.0;
					LOTRMod.proxy.spawnParticle("utumnoKill", d, d1, d2, d3, d4, d5);
				}
				for (l = 0; l < 12; ++l) {
					d = entity.posX + world.rand.nextGaussian() * 0.8;
					d1 = entity.boundingBox.minY + entity.height * 0.7 + world.rand.nextGaussian() * 0.2;
					d2 = entity.posZ + world.rand.nextGaussian() * 0.8;
					d3 = i1 + 0.5 - d;
					d4 = j1 + blockTop - d1;
					d5 = k1 + 0.5 - d2;
					double v = 0.05;
					LOTRMod.proxy.spawnParticle("utumnoKill", d, d1, d2, d3 * v, d4 * v, d5 * v);
				}
			}
			return null;
		}
	}

}
