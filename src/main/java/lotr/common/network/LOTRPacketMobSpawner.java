package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class LOTRPacketMobSpawner implements IMessage {
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int dimension;
	public int active;
	public boolean spawnsPersistentNPCs;
	public int minSpawnDelay;
	public int maxSpawnDelay;
	public int nearbyMobLimit;
	public int nearbyMobCheckRange;
	public int requiredPlayerRange;
	public int maxSpawnCount;
	public int maxSpawnRange;
	public int maxSpawnRangeVertical;
	public int maxHealth;
	public int navigatorRange;
	public float moveSpeed;
	public float attackDamage;

	public LOTRPacketMobSpawner() {
	}

	public LOTRPacketMobSpawner(int x, int y, int z, int dim) {
		xCoord = x;
		yCoord = y;
		zCoord = z;
		dimension = dim;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		xCoord = data.readInt();
		yCoord = data.readInt();
		zCoord = data.readInt();
		dimension = data.readByte();
		active = data.readByte();
		spawnsPersistentNPCs = data.readBoolean();
		minSpawnDelay = data.readInt();
		maxSpawnDelay = data.readInt();
		nearbyMobLimit = data.readByte();
		nearbyMobCheckRange = data.readByte();
		requiredPlayerRange = data.readByte();
		maxSpawnCount = data.readByte();
		maxSpawnRange = data.readByte();
		maxSpawnRangeVertical = data.readByte();
		maxHealth = data.readShort();
		navigatorRange = data.readByte();
		moveSpeed = data.readFloat();
		attackDamage = data.readFloat();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(xCoord);
		data.writeInt(yCoord);
		data.writeInt(zCoord);
		data.writeByte(dimension);
		data.writeByte(active);
		data.writeBoolean(spawnsPersistentNPCs);
		data.writeInt(minSpawnDelay);
		data.writeInt(maxSpawnDelay);
		data.writeByte(nearbyMobLimit);
		data.writeByte(nearbyMobCheckRange);
		data.writeByte(requiredPlayerRange);
		data.writeByte(maxSpawnCount);
		data.writeByte(maxSpawnRange);
		data.writeByte(maxSpawnRangeVertical);
		data.writeShort(maxHealth);
		data.writeByte(navigatorRange);
		data.writeFloat(moveSpeed);
		data.writeFloat(attackDamage);
	}

	public static class Handler implements IMessageHandler<LOTRPacketMobSpawner, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketMobSpawner packet, MessageContext context) {
			int k;
			int i;
			TileEntity tileentity;
			int j;
			WorldServer world = DimensionManager.getWorld(packet.dimension);
			if (world != null && (tileentity = world.getTileEntity(i = packet.xCoord, j = packet.yCoord, k = packet.zCoord)) instanceof LOTRTileEntityMobSpawner) {
				LOTRTileEntityMobSpawner mobSpawner = (LOTRTileEntityMobSpawner) tileentity;
				mobSpawner.active = packet.active;
				mobSpawner.spawnsPersistentNPCs = packet.spawnsPersistentNPCs;
				mobSpawner.minSpawnDelay = packet.minSpawnDelay;
				mobSpawner.maxSpawnDelay = packet.maxSpawnDelay;
				mobSpawner.nearbyMobLimit = packet.nearbyMobLimit;
				mobSpawner.nearbyMobCheckRange = packet.nearbyMobCheckRange;
				mobSpawner.requiredPlayerRange = packet.requiredPlayerRange;
				mobSpawner.maxSpawnCount = packet.maxSpawnCount;
				mobSpawner.maxSpawnRange = packet.maxSpawnRange;
				mobSpawner.maxSpawnRangeVertical = packet.maxSpawnRangeVertical;
				mobSpawner.maxHealth = packet.maxHealth;
				mobSpawner.navigatorRange = packet.navigatorRange;
				mobSpawner.moveSpeed = packet.moveSpeed;
				mobSpawner.attackDamage = packet.attackDamage;
				world.markBlockForUpdate(i, j, k);
				mobSpawner.delay = -1;
				((World) world).addBlockEvent(i, j, k, mobSpawner.getBlockType(), 2, 0);
			}
			return null;
		}
	}

}
