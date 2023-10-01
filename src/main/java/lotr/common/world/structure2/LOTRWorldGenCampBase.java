package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRWorldGenCampBase extends LOTRWorldGenStructureBase2 {
	public Block tableBlock;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block farmBaseBlock;
	public int farmBaseMeta;
	public Block farmCropBlock;
	public int farmCropMeta;
	public boolean hasOrcTorches;
	public boolean hasSkulls;

	protected LOTRWorldGenCampBase(boolean flag) {
		super(flag);
	}

	public abstract LOTRWorldGenStructureBase2 createTent(boolean var1, Random var2);

	public void generateCentrepiece(World world, Random random, int i, int j, int k) {
		for (int i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				int j1 = j - 1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				setBlockAndMetadata(world, i1, j, k1, brickSlabBlock, brickSlabMeta);
				setGrassToDirt(world, i1, j - 1, k1);
			}
		}
		setBlockAndMetadata(world, i, j, k, tableBlock, 0);
	}

	public boolean generateFarm() {
		return true;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int l;
		int i14;
		if (restrictions && (!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k) || world.getBlock(i, j, k).getMaterial().isLiquid())) {
			return false;
		}
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		int groundRange = 12;
		for (int i12 = -groundRange; i12 <= groundRange; ++i12) {
			for (int k1 = -groundRange; k1 <= groundRange; ++k1) {
				int j1;
				int k2;
				int i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 * i2 + k2 * k2 >= groundRange * groundRange || getBlock(world, i12, (j1 = getTopBlock(world, i12, k1)) - 1, k1) != Blocks.grass || random.nextInt(5) == 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, j1 - 1, k1, Blocks.dirt, 1);
			}
		}
		int highestHeight = 0;
		for (int i13 = -1; i13 <= 1; ++i13) {
			for (int k1 = -1; k1 <= 1; ++k1) {
				int j1 = getTopBlock(world, i13, k1);
				if (j1 <= highestHeight) {
					continue;
				}
				highestHeight = j1;
			}
		}
		originY = getY(highestHeight);
		generateCentrepiece(world, random, 0, 0, 0);
		LOTREntityNPC captain = getCampCaptain(world, random);
		if (captain != null) {
			captain.spawnRidingHorse = false;
			spawnNPCAndSetHome(captain, world, 0, 1, 0, 24);
		}
		for (l = 0; l < 4; ++l) {
			int tentX = MathHelper.getRandomIntegerInRange(random, -3, 3);
			int tentZ = MathHelper.getRandomIntegerInRange(random, 6, 12);
			i14 = 0;
			int k1 = 0;
			switch (l) {
				case 0:
					i14 = tentX;
					k1 = tentZ;
					break;
				case 1:
					i14 = tentZ;
					k1 = -tentX;
					break;
				case 2:
					i14 = -tentX;
					k1 = -tentZ;
					break;
				case 3:
					i14 = -tentZ;
					k1 = tentX;
					break;
				default:
					break;
			}
			int j1 = getTopBlock(world, i14, k1);
			generateSubstructure(createTent(notifyChanges, random), world, random, i14, j1, k1, l);
		}
		if (hasOrcTorches) {
			for (int i141 : new int[]{-2, 2}) {
				for (int k1 : new int[]{-2, 2}) {
					int j1 = getTopBlock(world, i141, k1);
					placeOrcTorch(world, i141, j1, k1);
				}
			}
		}
		if (generateFarm()) {
			int[] farmCoords = null;
			int farmRange = 12;
			int minFarmRange = 5;
			block7:
			for (int l2 = 0; l2 < 32; ++l2) {
				int k1;
				int i15 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange);
				int dSq = i15 * i15 + (k1 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange)) * k1;
				if (dSq <= minFarmRange * minFarmRange) {
					continue;
				}
				for (int i2 = i15 - 2; i2 <= i15 + 2; ++i2) {
					for (int k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
						int j2 = getTopBlock(world, i2, k2) - 1;
						if (!isSurface(world, i2, j2, k2) || !isAir(world, i2, j2 + 1, k2) && !isReplaceable(world, i2, j2 + 1, k2)) {
							continue block7;
						}
					}
				}
				farmCoords = new int[]{i15, k1};
				break;
			}
			if (farmCoords != null) {
				int k2;
				int j2;
				int i2;
				i14 = farmCoords[0];
				int k1 = farmCoords[1];
				int highestFarmHeight = getTopBlock(world, i14, k1);
				for (i2 = i14 - 2; i2 <= i14 + 2; ++i2) {
					for (k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
						j2 = getTopBlock(world, i2, k2);
						if (j2 <= highestFarmHeight) {
							continue;
						}
						highestFarmHeight = j2;
					}
				}
				for (i2 = i14 - 2; i2 <= i14 + 2; ++i2) {
					for (k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
						j2 = highestFarmHeight - 2;
						while (!isOpaque(world, i2, j2, k2) && getY(j2) >= 0) {
							setBiomeFiller(world, i2, j2, k2);
							setGrassToDirt(world, i2, j2 - 1, k2);
							--j2;
						}
						if (Math.abs(i2 - i14) == 2 || Math.abs(k2 - k1) == 2) {
							setBlockAndMetadata(world, i2, highestFarmHeight, k2, fenceBlock, fenceMeta);
							setBiomeTop(world, i2, highestFarmHeight - 1, k2);
							setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
							continue;
						}
						if (i2 == i14 && k2 == k1) {
							setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, Blocks.water, 0);
							continue;
						}
						setBlockAndMetadata(world, i2, highestFarmHeight, k2, farmCropBlock, farmCropMeta);
						setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, farmBaseBlock, farmBaseMeta);
						setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
					}
				}
				int gate = random.nextInt(4);
				switch (gate) {
					case 0:
						setBlockAndMetadata(world, i14, highestFarmHeight, k1 + 2, fenceGateBlock, 0);
						break;
					case 1:
						setBlockAndMetadata(world, i14 - 2, highestFarmHeight, k1, fenceGateBlock, 1);
						break;
					case 2:
						setBlockAndMetadata(world, i14, highestFarmHeight, k1 - 2, fenceGateBlock, 2);
						break;
					case 3:
						setBlockAndMetadata(world, i14 + 2, highestFarmHeight, k1, fenceGateBlock, 3);
						break;
					default:
						break;
				}
				int scarecrowX = i14 + (random.nextBoolean() ? -2 : 2);
				int scarecrowZ = k1 + (random.nextBoolean() ? -2 : 2);
				setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 1, scarecrowZ, fenceBlock, fenceMeta);
				if (hasOrcTorches) {
					setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.wool, 12);
					placeSkull(world, random, scarecrowX, highestFarmHeight + 3, scarecrowZ);
				} else {
					setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.hay_block, 0);
					setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 3, scarecrowZ, Blocks.pumpkin, random.nextInt(4));
				}
			}
		}
		if (hasSkulls) {
			int range;
			int j1;
			int k1;
			int i16;
			for (l = 0; l < 6; ++l) {
				range = 8;
				i16 = MathHelper.getRandomIntegerInRange(random, -range, range);
				k1 = MathHelper.getRandomIntegerInRange(random, -range, range);
				if (i16 * i16 + k1 * k1 <= 20 || !isSurface(world, i16, (j1 = getTopBlock(world, i16, k1)) - 1, k1) || !isReplaceable(world, i16, j1, k1) || !isAir(world, i16, j1 + 1, k1)) {
					continue;
				}
				setBlockAndMetadata(world, i16, j1, k1, fenceBlock, fenceMeta);
				placeSkull(world, random, i16, j1 + 1, k1);
			}
			for (l = 0; l < 6; ++l) {
				range = 12;
				i16 = MathHelper.getRandomIntegerInRange(random, -range, range);
				k1 = MathHelper.getRandomIntegerInRange(random, -range, range);
				if (i16 * i16 + k1 * k1 <= 20 || !isSurface(world, i16, (j1 = getTopBlock(world, i16, k1)) - 1, k1) || !isReplaceable(world, i16, j1, k1) || !isAir(world, i16, j1 + 1, k1)) {
					continue;
				}
				placeSkull(world, random, i16, j1, k1);
			}
		}
		placeNPCRespawner(world, random, 0, 0, 0);
		return true;
	}

	public abstract LOTREntityNPC getCampCaptain(World var1, Random var2);

	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
	}

	@Override
	public void setupRandomBlocks(Random random) {
		tableBlock = Blocks.crafting_table;
		brickBlock = Blocks.cobblestone;
		brickMeta = 0;
		brickSlabBlock = Blocks.stone_slab;
		brickSlabMeta = 3;
		fenceBlock = Blocks.fence;
		fenceMeta = 0;
		fenceGateBlock = Blocks.fence_gate;
		farmBaseBlock = Blocks.farmland;
		farmBaseMeta = 7;
		farmCropBlock = Blocks.wheat;
		farmCropMeta = 7;
	}
}
