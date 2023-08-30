package lotr.common.world.biome.variant;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRDimension;
import lotr.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;

public class LOTRBiomeVariantStorage {
	public static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMap = new EnumMap<>(LOTRDimension.class);
	public static Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> chunkVariantMapClient = new EnumMap<>(LOTRDimension.class);

	public static void clearAllVariants(World world) {
		getDimensionChunkMap(world).clear();
		FMLLog.info("Unloading LOTR biome variants in %s", LOTRDimension.getCurrentDimensionWithFallback(world).dimensionName);
	}

	public static void clearChunkBiomeVariants(World world, Chunk chunk) {
		clearChunkBiomeVariants(world, getChunkKey(chunk));
	}

	public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		getDimensionChunkMap(world).remove(chunk);
	}

	public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
		return getChunkBiomeVariants(world, getChunkKey(chunk));
	}

	public static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
		return getDimensionChunkMap(world).get(chunk);
	}

	public static ChunkCoordIntPair getChunkKey(Chunk chunk) {
		return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
	}

	public static Map<ChunkCoordIntPair, byte[]> getDimensionChunkMap(World world) {
		LOTRDimension dim;
		Map<LOTRDimension, Map<ChunkCoordIntPair, byte[]>> sourcemap = !world.isRemote ? chunkVariantMap : chunkVariantMapClient;
		Map<ChunkCoordIntPair, byte[]> map = sourcemap.get(dim = LOTRDimension.getCurrentDimensionWithFallback(world));
		if (map == null) {
			map = new HashMap<>();
			sourcemap.put(dim, map);
		}
		return map;
	}

	public static int getSize(World world) {
		Map<ChunkCoordIntPair, byte[]> map = getDimensionChunkMap(world);
		return map.size();
	}

	public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		if (getChunkBiomeVariants(world, chunk) == null) {
			byte[] variants = data.hasKey("LOTRBiomeVariants") ? data.getByteArray("LOTRBiomeVariants") : new byte[256];
			setChunkBiomeVariants(world, chunk, variants);
		}
	}

	public static void performCleanup(WorldServer world) {
		Map<ChunkCoordIntPair, byte[]> dimensionMap = getDimensionChunkMap(world);
		System.nanoTime();
		Collection<ChunkCoordIntPair> removalChunks = new ArrayList<>();
		for (ChunkCoordIntPair chunk : dimensionMap.keySet()) {
			if (world.theChunkProviderServer.chunkExists(chunk.chunkXPos, chunk.chunkZPos)) {
				continue;
			}
			removalChunks.add(chunk);
		}
		for (ChunkCoordIntPair chunk : removalChunks) {
			dimensionMap.remove(chunk);
		}
	}

	public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
		byte[] variants = getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			data.setByteArray("LOTRBiomeVariants", variants);
		}
	}

	public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
		byte[] variants = getChunkBiomeVariants(world, chunk);
		if (variants != null) {
			IMessage packet = new LOTRPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition, variants);
			LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		} else {
			String dimName = world.provider.getDimensionName();
			int posX = chunk.xPosition << 4;
			int posZ = chunk.zPosition << 4;
			String playerName = entityplayer.getCommandSenderName();
			FMLLog.severe("Could not find LOTR biome variants for %s chunk at %d, %d; requested by %s", dimName, posX, posZ, playerName);
		}
	}

	public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
		IMessage packet = new LOTRPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
		setChunkBiomeVariants(world, getChunkKey(chunk), variants);
	}

	public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
		getDimensionChunkMap(world).put(chunk, variants);
	}
}
