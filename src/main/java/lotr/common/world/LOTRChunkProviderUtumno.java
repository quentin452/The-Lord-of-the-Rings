package lotr.common.world;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.mapgen.LOTRMapGenCaves;
import lotr.common.world.mapgen.LOTRMapGenCavesUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTRChunkProviderUtumno implements IChunkProvider {
	public World worldObj;
	public Random rand;
	public BiomeGenBase[] biomesForGeneration;
	public LOTRBiomeVariant[] variantsForGeneration;
	public LOTRMapGenCaves caveGenerator = new LOTRMapGenCavesUtumno();

	public LOTRChunkProviderUtumno(World world, long l) {
		worldObj = world;
		rand = new Random(l);
		LOTRUtumnoLevel.setupLevels();
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public boolean chunkExists(int i, int j) {
		return true;
	}

	@Override
	public ChunkPosition func_147416_a(World world, String type, int i, int j, int k) {
		return null;
	}

	public void generateTerrain(int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
		Arrays.fill(blocks, Blocks.air);
		LOTRUtumnoLevel.generateTerrain(worldObj, rand, chunkX, chunkZ, blocks, metadata);
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int i, int j, int k) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		return biome == null ? null : biome.getSpawnableList(creatureType);
	}

	@Override
	public Chunk loadChunk(int i, int j) {
		return provideChunk(i, j);
	}

	@Override
	public String makeString() {
		return "UtumnoLevelSource";
	}

	@Override
	public void populate(IChunkProvider ichunkprovider, int chunkX, int chunkZ) {
		BlockFalling.fallInstantly = true;
		int i = chunkX * 16;
		int k = chunkZ * 16;
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(i + 16, k + 16);
		if (!(biomegenbase instanceof LOTRBiome)) {
			return;
		}
		LOTRBiome biome = (LOTRBiome) biomegenbase;
		rand.setSeed(worldObj.getSeed());
		long l1 = rand.nextLong() / 2L * 2L + 1L;
		long l2 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(chunkX * l1 + chunkZ * l2 ^ worldObj.getSeed());
		biome.decorate(worldObj, rand, i, k);
		BlockFalling.fallInstantly = false;
	}

	@Override
	public Chunk provideChunk(int i, int k) {
		rand.setSeed(i * 341873128712L + k * 132897987541L);
		LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager) worldObj.getWorldChunkManager();
		Block[] blocks = new Block[65536];
		byte[] metadata = new byte[65536];
		generateTerrain(i, k, blocks, metadata);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, i * 16, k * 16, 16, 16);
		variantsForGeneration = chunkManager.getBiomeVariants(variantsForGeneration, i * 16, k * 16, 16, 16);
		caveGenerator.func_151539_a(this, worldObj, i, k, blocks);
		Chunk chunk = new Chunk(worldObj, i, k);
		ExtendedBlockStorage[] blockStorage = chunk.getBlockStorageArray();
		for (int i1 = 0; i1 < 16; ++i1) {
			for (int k1 = 0; k1 < 16; ++k1) {
				for (int j1 = 0; j1 < 256; ++j1) {
					int blockIndex = i1 << 12 | k1 << 8 | j1;
					Block block = blocks[blockIndex];
					if (block == null || block == Blocks.air) {
						continue;
					}
					byte meta = metadata[blockIndex];
					int j2 = j1 >> 4;
					if (blockStorage[j2] == null) {
						blockStorage[j2] = new ExtendedBlockStorage(j2 << 4, true);
					}
					blockStorage[j2].func_150818_a(i1, j1 & 0xF, k1, block);
					blockStorage[j2].setExtBlockMetadata(i1, j1 & 0xF, k1, meta & 0xF);
				}
			}
		}
		byte[] biomes = chunk.getBiomeArray();
		for (int l = 0; l < biomes.length; ++l) {
			biomes[l] = (byte) biomesForGeneration[l].biomeID;
		}
		byte[] variants = new byte[256];
		for (int l = 0; l < variants.length; ++l) {
			variants[l] = (byte) variantsForGeneration[l].variantID;
		}
		LOTRBiomeVariantStorage.setChunkBiomeVariants(worldObj, chunk, variants);
		chunk.resetRelightChecks();
		return chunk;
	}

	@Override
	public void recreateStructures(int i, int j) {
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate update) {
		return true;
	}

	@Override
	public void saveExtraData() {
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}
}
