package lotr.common.world;

import com.google.common.primitives.Ints;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public enum LOTRUtumnoLevel {
	ICE(13819887, 180, 240, 4, 4), OBSIDIAN(2104109, 92, 180, 6, 5), FIRE(6295040, 0, 92, 8, 7);

	public static boolean initSpawnLists;
	public static Random lightRand = new Random();
	public static NoiseGeneratorPerlin noiseGenXZ = new NoiseGeneratorPerlin(new Random(5628506078940526L), 1);
	public static NoiseGeneratorPerlin noiseGenY = new NoiseGeneratorPerlin(new Random(1820268708369704034L), 1);
	public static NoiseGeneratorPerlin corridorNoiseY = new NoiseGeneratorPerlin(new Random(89230369345425L), 1);
	public static NoiseGeneratorPerlin corridorNoiseX = new NoiseGeneratorPerlin(new Random(824595069307073L), 1);
	public static NoiseGeneratorPerlin corridorNoiseZ = new NoiseGeneratorPerlin(new Random(759206035530266067L), 1);

	public int fogColor;
	public int baseLevel;
	public int topLevel;
	public int corridorWidth;
	public int corridorWidthStart;
	public int corridorWidthEnd;
	public int corridorHeight;
	public int[] corridorBaseLevels;
	public LOTRBiomeSpawnList npcSpawnList = new LOTRBiomeSpawnList("UtumnoLevel_" + name());
	public Block brickBlock;
	public int brickMeta;
	public Block brickStairBlock;
	public Block brickGlowBlock;
	public int brickGlowMeta;
	public Block tileBlock;
	public int tileMeta;
	public Block pillarBlock;

	public int pillarMeta;

	LOTRUtumnoLevel(int fog, int base, int top, int cWidth, int cHeight) {
		fogColor = fog;
		baseLevel = base;
		topLevel = top;
		corridorWidth = cWidth;
		corridorWidthStart = 8 - cWidth / 2;
		corridorWidthEnd = corridorWidthStart + cWidth;
		corridorHeight = cHeight;
		Collection<Integer> baseLevels = new ArrayList<>();
		int y = baseLevel;
		while ((y += corridorHeight * 2) < top - 5) {
			baseLevels.add(y);
		}
		corridorBaseLevels = Ints.toArray(baseLevels);
	}

	public static LOTRUtumnoLevel forY(int y) {
		for (LOTRUtumnoLevel level : values()) {
			if (y < level.baseLevel) {
				continue;
			}
			return level;
		}
		return FIRE;
	}

	public static void generateTerrain(World world, Random rand, int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
		boolean hugeHoleChunk = rand.nextInt(16) == 0;
		boolean hugeRavineChunk = rand.nextInt(16) == 0;
		long seed = world.getSeed();
		lightRand.setSeed(seed * (chunkX / 2 * 67839703L + chunkZ / 2 * 368093693L));
		boolean chunkHasGlowing = lightRand.nextInt(4) > 0;
		for (int i = 0; i < 16; ++i) {
			for (int k = 0; k < 16; ++k) {
				int blockX = chunkX * 16 + i;
				int blockZ = chunkZ * 16 + k;
				double genNoiseXZHere = noiseGenXZ.func_151601_a(blockX * 0.2, blockZ * 0.2);
				double corridorNoiseYHere = corridorNoiseY.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseY.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
				double corridorNoiseXHere = corridorNoiseX.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseX.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
				double corridorNoiseZHere = corridorNoiseZ.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseZ.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
				for (int j = 255; j >= 0; --j) {
					LOTRUtumnoLevel utumnoLevel = forY(j);
					int blockIndex = (k * 16 + i) * 256 + j;
					if (j <= rand.nextInt(5) || j >= 255 - rand.nextInt(3)) {
						blocks[blockIndex] = Blocks.bedrock;
					} else {
						double genNoiseYHere = noiseGenY.func_151601_a(j * 0.4, 0.0);
						double genNoise = (genNoiseXZHere + genNoiseYHere * 0.5) / 1.5;
						if (genNoise > -0.2) {
							blocks[blockIndex] = utumnoLevel.brickBlock;
							metadata[blockIndex] = (byte) utumnoLevel.brickMeta;
							if (chunkHasGlowing) {
								boolean glowing = false;
								if (utumnoLevel == ICE && rand.nextInt(16) == 0 || utumnoLevel == OBSIDIAN && rand.nextInt(12) == 0) {
									glowing = true;
								} else if (utumnoLevel == FIRE && rand.nextInt(8) == 0) {
									glowing = true;
								}
								if (glowing) {
									blocks[blockIndex] = utumnoLevel.brickGlowBlock;
									metadata[blockIndex] = (byte) utumnoLevel.brickGlowMeta;
								}
							}
						} else if (utumnoLevel == ICE) {
							if (genNoise < -0.5) {
								blocks[blockIndex] = Blocks.stone;
							} else {
								blocks[blockIndex] = Blocks.packed_ice;
							}
							metadata[blockIndex] = 0;
						} else if (utumnoLevel == OBSIDIAN) {
							if (genNoise < -0.5) {
								blocks[blockIndex] = Blocks.stained_hardened_clay;
								metadata[blockIndex] = 15;
							} else {
								blocks[blockIndex] = Blocks.obsidian;
								metadata[blockIndex] = 0;
							}
						} else if (utumnoLevel == FIRE) {
							blocks[blockIndex] = Blocks.obsidian;
							metadata[blockIndex] = 0;
						}
						int levelFuzz = 2;
						if (j <= utumnoLevel.getLowestCorridorFloor() - levelFuzz || j >= utumnoLevel.getHighestCorridorRoof() + levelFuzz) {
							blocks[blockIndex] = utumnoLevel.brickBlock;
							metadata[blockIndex] = (byte) utumnoLevel.brickMeta;
						}
						if (genNoise < 0.5) {
							for (int corridorBase : utumnoLevel.corridorBaseLevels) {
								if (j != corridorBase - 1) {
									continue;
								}
								blocks[blockIndex] = utumnoLevel.tileBlock;
								metadata[blockIndex] = (byte) utumnoLevel.tileMeta;
							}
						}
					}
					int actingY = j;
					actingY += (int) Math.round(corridorNoiseYHere * 1.15);
					actingY = MathHelper.clamp_int(actingY, 0, 255);
					int actingXInChunk = blockX + (int) Math.round(corridorNoiseXHere * 1.7) & 0xF;
					int actingZInChunk = blockZ + (int) Math.round(corridorNoiseZHere * 1.7) & 0xF;
					boolean carveHugeHole = hugeHoleChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
					boolean carveHugeRavine = hugeRavineChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
					boolean carveCorridor = false;
					for (int corridorBase : utumnoLevel.corridorBaseLevels) {
						carveCorridor = actingY >= corridorBase && actingY < corridorBase + utumnoLevel.corridorHeight;
						if (carveCorridor) {
							break;
						}
					}
					if (carveHugeHole && chunkX % 2 == 0 && chunkZ % 2 == 0) {
						if (i >= utumnoLevel.corridorWidthStart + 1 && i <= utumnoLevel.corridorWidthEnd - 1 && k >= utumnoLevel.corridorWidthStart + 1 && k <= utumnoLevel.corridorWidthEnd - 1) {
							blocks[blockIndex] = Blocks.air;
							metadata[blockIndex] = 0;
						} else if (i >= utumnoLevel.corridorWidthStart && i <= utumnoLevel.corridorWidthEnd && k >= utumnoLevel.corridorWidthStart && k <= utumnoLevel.corridorWidthEnd) {
							blocks[blockIndex] = utumnoLevel.brickGlowBlock;
							metadata[blockIndex] = (byte) utumnoLevel.brickGlowMeta;
						}
					}
					if (chunkX % 2 == 0) {
						if (carveCorridor && actingZInChunk >= utumnoLevel.corridorWidthStart && actingZInChunk <= utumnoLevel.corridorWidthEnd) {
							blocks[blockIndex] = Blocks.air;
							metadata[blockIndex] = 0;
						}
						if (carveHugeRavine && actingXInChunk >= utumnoLevel.corridorWidthStart + 1 && actingXInChunk <= utumnoLevel.corridorWidthEnd - 1) {
							blocks[blockIndex] = Blocks.air;
							metadata[blockIndex] = 0;
						}
					}
					if (chunkZ % 2 != 0) {
						continue;
					}
					if (carveCorridor && actingXInChunk >= utumnoLevel.corridorWidthStart && actingXInChunk <= utumnoLevel.corridorWidthEnd) {
						blocks[blockIndex] = Blocks.air;
						metadata[blockIndex] = 0;
					}
					if (!carveHugeRavine || actingZInChunk < utumnoLevel.corridorWidthStart + 1 || actingZInChunk > utumnoLevel.corridorWidthEnd - 1) {
						continue;
					}
					blocks[blockIndex] = Blocks.air;
					metadata[blockIndex] = 0;
				}
			}
		}
	}

	public static void setupLevels() {
		if (initSpawnLists) {
			return;
		}
		ICE.brickBlock = LOTRMod.utumnoBrick;
		ICE.brickMeta = 2;
		ICE.brickStairBlock = LOTRMod.stairsUtumnoBrickIce;
		ICE.brickGlowBlock = LOTRMod.utumnoBrick;
		ICE.brickGlowMeta = 3;
		ICE.tileBlock = LOTRMod.utumnoBrick;
		ICE.tileMeta = 6;
		ICE.pillarBlock = LOTRMod.utumnoPillar;
		ICE.pillarMeta = 1;
		OBSIDIAN.brickBlock = LOTRMod.utumnoBrick;
		OBSIDIAN.brickMeta = 4;
		OBSIDIAN.brickStairBlock = LOTRMod.stairsUtumnoBrickObsidian;
		OBSIDIAN.brickGlowBlock = LOTRMod.utumnoBrick;
		OBSIDIAN.brickGlowMeta = 5;
		OBSIDIAN.tileBlock = LOTRMod.utumnoBrick;
		OBSIDIAN.tileMeta = 7;
		OBSIDIAN.pillarBlock = LOTRMod.utumnoPillar;
		OBSIDIAN.pillarMeta = 2;
		FIRE.brickBlock = LOTRMod.utumnoBrick;
		FIRE.brickMeta = 0;
		FIRE.brickStairBlock = LOTRMod.stairsUtumnoBrickFire;
		FIRE.brickGlowBlock = LOTRMod.utumnoBrick;
		FIRE.brickGlowMeta = 1;
		FIRE.tileBlock = LOTRMod.utumnoBrick;
		FIRE.tileMeta = 8;
		FIRE.pillarBlock = LOTRMod.utumnoPillar;
		FIRE.pillarMeta = 0;
		ICE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_ICE, 10));
		OBSIDIAN.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_OBSIDIAN, 10));
		FIRE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_FIRE, 10));
		initSpawnLists = true;
	}

	public int getHighestCorridorRoof() {
		return corridorBaseLevels[corridorBaseLevels.length - 1] + corridorHeight;
	}

	public int getLowestCorridorFloor() {
		return corridorBaseLevels[0] - 1;
	}

	public LOTRBiomeSpawnList getNPCSpawnList() {
		return npcSpawnList;
	}
}
