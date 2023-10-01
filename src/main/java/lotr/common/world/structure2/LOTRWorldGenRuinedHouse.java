package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRuinedHouse extends LOTRWorldGenStructureBase2 {
	public Block woodBlock = Blocks.log;
	public int woodMeta;
	public Block plankBlock = Blocks.planks;
	public int plankMeta;
	public Block fenceBlock = Blocks.fence;
	public int fenceMeta;
	public Block stairBlock = Blocks.oak_stairs;
	public Block stoneBlock = Blocks.cobblestone;
	public int stoneMeta;
	public Block stoneVariantBlock = Blocks.mossy_cobblestone;
	public int stoneVariantMeta;

	public LOTRWorldGenRuinedHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int width = 4 + random.nextInt(3);
		setOriginAndRotation(world, i, j, k, rotation, width + 1);
		if (restrictions) {
			int minHeight = 1;
			int maxHeight = 1;
			for (int i12 = -width; i12 <= width; ++i12) {
				for (int k1 = -width; k1 <= width; ++k1) {
					int j1 = getTopBlock(world, i12, k1);
					Block block = getBlock(world, i12, j1 - 1, k1);
					if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
						return false;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (j1 >= minHeight) {
						continue;
					}
					minHeight = j1;
				}
			}
			if (Math.abs(maxHeight - minHeight) > 5) {
				return false;
			}
		}
		for (i1 = -width; i1 <= width; ++i1) {
			for (int k1 = -width; k1 <= width; ++k1) {
				int j1;
				for (j1 = 0; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
				j1 = 0;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					placeRandomGroundBlock(world, random, i1, j1, k1);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		for (int k1 = -width; k1 <= width; ++k1) {
			placeRandomWallOrStone(world, random, -width, 1, -k1);
			placeRandomWallOrStone(world, random, width, 1, k1);
			placeRandomWall(world, random, -width, 2, k1, true);
			placeRandomWall(world, random, width, 2, k1, true);
			placeRandomWall(world, random, -width, 3, k1, true);
			placeRandomWall(world, random, width, 3, k1, true);
		}
		for (i1 = -width; i1 <= width; ++i1) {
			placeRandomWallOrStone(world, random, i1, 1, width);
			if (random.nextInt(3) == 0) {
				placeRandomWallOrStone(world, random, i1, 2, width - 1);
			}
			placeRandomWall(world, random, i1, 2, width, false);
			placeRandomWall(world, random, i1, 3, width, false);
		}
		for (i1 = -width + 1; i1 <= -1 && random.nextInt(4) != 0; ++i1) {
			placeRandomWallOrStone(world, random, i1, 1, -width);
		}
		for (i1 = width - 1; i1 >= 1 && random.nextInt(4) != 0; --i1) {
			placeRandomWallOrStone(world, random, i1, 1, -width);
		}
		setBlockAndMetadata(world, -width + 1, 2, -width, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, width - 1, 2, -width, fenceBlock, fenceMeta);
		placeWoodPillar(world, random, -width, 1, -width);
		placeWoodPillar(world, random, width, 1, -width);
		placeWoodPillar(world, random, -width, 1, width);
		placeWoodPillar(world, random, width, 1, width);
		if (random.nextBoolean()) {
			setBlockAndMetadata(world, width - 1, 1, -width + 1, stoneBlock, stoneMeta);
			setBlockAndMetadata(world, width - 1, 1, -width + 2, Blocks.furnace, 0);
		} else {
			setBlockAndMetadata(world, -width + 1, 1, -width + 1, stoneBlock, stoneMeta);
			setBlockAndMetadata(world, -width + 1, 1, -width + 2, Blocks.furnace, 0);
		}
		if (random.nextBoolean()) {
			placeChest(world, random, width - 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
		} else {
			placeChest(world, random, -width + 1, 1, width - 2, 0, LOTRChestContents.RUINED_HOUSE);
		}
		return true;
	}

	public void placeRandomGroundBlock(World world, Random random, int i, int j, int k) {
		int l = random.nextInt(4);
		switch (l) {
			case 0:
				setBlockAndMetadata(world, i, j, k, Blocks.dirt, 1);
				break;
			case 1:
				setBlockAndMetadata(world, i, j, k, Blocks.gravel, 0);
				break;
			case 2:
				setBlockAndMetadata(world, i, j, k, stoneBlock, stoneMeta);
				break;
			case 3:
				setBlockAndMetadata(world, i, j, k, stoneVariantBlock, stoneVariantMeta);
				break;
			default:
				break;
		}
	}

	public void placeRandomWall(World world, Random random, int i, int j, int k, boolean northToSouth) {
		if (random.nextInt(12) == 0 || isAir(world, i, j - 1, k)) {
			return;
		}
		int l = random.nextInt(4);
		switch (l) {
			case 0:
				setBlockAndMetadata(world, i, j, k, fenceBlock, fenceMeta);
				break;
			case 1:
				setBlockAndMetadata(world, i, j, k, plankBlock, plankMeta);
				break;
			case 2:
				setBlockAndMetadata(world, i, j, k, woodBlock, woodMeta | (northToSouth ? 8 : 4));
				break;
			case 3: {
				int upsideDown;
				upsideDown = random.nextBoolean() ? 4 : 0;
				if (northToSouth) {
					setBlockAndMetadata(world, i, j, k, stairBlock, random.nextInt(2) | upsideDown);
				} else {
					setBlockAndMetadata(world, i, j, k, stairBlock, 2 + random.nextInt(2) | upsideDown);
				}
				break;
			}
			default:
				break;
		}
	}

	public void placeRandomWallOrStone(World world, Random random, int i, int j, int k) {
		if (random.nextInt(12) == 0 || isAir(world, i, j - 1, k)) {
			return;
		}
		int l = random.nextInt(4);
		switch (l) {
			case 0:
				setBlockAndMetadata(world, i, j, k, fenceBlock, fenceMeta);
				break;
			case 1:
				setBlockAndMetadata(world, i, j, k, plankBlock, plankMeta);
				break;
			case 2:
				setBlockAndMetadata(world, i, j, k, stoneBlock, stoneMeta);
				break;
			case 3:
				setBlockAndMetadata(world, i, j, k, stoneVariantBlock, stoneVariantMeta);
				break;
			default:
				break;
		}
	}

	public void placeWoodPillar(World world, Random random, int i, int j, int k) {
		for (int j1 = j; j1 <= j + 4; ++j1) {
			setBlockAndMetadata(world, i, j1, k, woodBlock, woodMeta);
			if (random.nextInt(4) == 0 && j1 >= j + 2) {
				break;
			}
		}
	}
}
