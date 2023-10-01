package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBDBarrow extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenStructureBase2 ruinsGen = new LOTRWorldGenStoneRuin.STONE(3, 3);

	public LOTRWorldGenBDBarrow(boolean flag) {
		super(flag);
		ruinsGen.restrictions = false;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int i12;
		int j12;
		int k1;
		int radius = 12;
		int height = 7;
		int base = -4;
		setOriginAndRotation(world, i, j, k, rotation, usingPlayer != null ? radius : 0);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -radius; i13 <= radius; ++i13) {
				for (k1 = -radius; k1 <= radius; ++k1) {
					j1 = getTopBlock(world, i13, k1) - 1;
					if (getBlock(world, i13, j1, k1) != Blocks.grass) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 <= maxHeight) {
						continue;
					}
					maxHeight = j1;
				}
			}
			if (maxHeight - minHeight > 5) {
				return false;
			}
		}
		for (i1 = -radius; i1 <= radius; ++i1) {
			for (int j13 = height; j13 >= base; --j13) {
				for (int k12 = -radius; k12 <= radius; ++k12) {
					if (i1 * i1 + (j13 - base) * (j13 - base) + k12 * k12 > radius * radius) {
						continue;
					}
					boolean grass = !isOpaque(world, i1, j13 + 1, k12);
					setBlockAndMetadata(world, i1, j13, k12, grass ? Blocks.grass : Blocks.dirt, 0);
					setGrassToDirt(world, i1, j13 - 1, k12);
				}
			}
		}
		for (i1 = -radius; i1 <= radius; ++i1) {
			for (int k13 = -radius; k13 <= radius; ++k13) {
				int j14 = base - 1;
				while (!isOpaque(world, i1, j14, k13) && getY(j14) >= 0) {
					if (i1 * i1 + k13 * k13 <= radius * radius) {
						setBlockAndMetadata(world, i1, j14, k13, Blocks.dirt, 0);
						setGrassToDirt(world, i1, j14 - 1, k13);
					}
					--j14;
				}
			}
		}
		int innerR = 5;
		int innerH = 5;
		int innerB = -2;
		for (i12 = -innerR - 1; i12 <= innerR + 1; ++i12) {
			for (int k14 = -innerR - 1; k14 <= innerR + 1; ++k14) {
				for (j12 = innerB + 1; j12 <= innerB + innerH + 1; ++j12) {
					int d = i12 * i12 + (j12 - innerB - 1) * (j12 - innerB - 1) + k14 * k14;
					if (d < innerR * innerR) {
						setAir(world, i12, j12, k14);
						if (d <= (innerR - 1) * (innerR - 1) || random.nextInt(3) != 0) {
							continue;
						}
						placeRandomBrick(world, random, i12, j12, k14);
						continue;
					}
					if (d >= (innerR + 1) * (innerR + 1)) {
						continue;
					}
					placeRandomBrick(world, random, i12, j12, k14);
				}
				placeRandomBrick(world, random, i12, innerB, k14);
			}
		}
		placeSpawnerChest(world, random, 0, innerB + 1, 0, LOTRMod.spawnerChestStone, 0, LOTREntityBarrowWight.class, LOTRChestContents.BARROW_DOWNS);
		setBlockAndMetadata(world, 1, innerB + 1, 0, Blocks.stone_stairs, 0);
		setBlockAndMetadata(world, -1, innerB + 1, 0, Blocks.stone_stairs, 1);
		setBlockAndMetadata(world, 0, innerB + 1, -1, Blocks.stone_stairs, 2);
		setBlockAndMetadata(world, 0, innerB + 1, 1, Blocks.stone_stairs, 3);
		for (k1 = -radius + 2; k1 <= -innerR + 1; ++k1) {
			for (int i14 = -1; i14 <= 1; ++i14) {
				placeRandomBrick(world, random, i14, 0, k1);
				for (j12 = 1; j12 <= 3; ++j12) {
					setAir(world, i14, j12, k1);
				}
				placeRandomBrick(world, random, i14, 4, k1);
			}
			for (j1 = 0; j1 <= 4; ++j1) {
				placeRandomBrick(world, random, -2, j1, k1);
				placeRandomBrick(world, random, 2, j1, k1);
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, innerB + 1, -innerR + 1, Blocks.stone_stairs, 3);
			for (j1 = 0; j1 <= 3; ++j1) {
				setAir(world, i12, j1, -innerR + 1);
				setAir(world, i12, j1, -innerR + 2);
			}
			setBlockAndMetadata(world, i12, 0, -innerR, Blocks.stone_stairs, 3);
		}
		placeRandomBrick(world, random, -2, innerB + 1, -innerR + 1);
		placeRandomBrick(world, random, 2, innerB + 1, -innerR + 1);
		for (int i15 : new int[]{-3, 3}) {
			placeRandomBrick(world, random, i15, 1, -radius + 1);
			placeRandomBrick(world, random, i15, 0, -radius + 1);
			placeRandomBrick(world, random, i15, -1, -radius + 1);
			placeRandomBrick(world, random, i15, 2, -radius + 2);
			placeRandomBrick(world, random, i15, 1, -radius + 2);
		}
		for (int i16 = -2; i16 <= 2; ++i16) {
			placeRandomBrick(world, random, i16, 5, -radius + 4);
			if (Math.abs(i16) > 1) {
				continue;
			}
			placeRandomBrick(world, random, i16, 5, -radius + 3);
		}
		for (int j15 = 1; j15 <= 3; ++j15) {
			placeRandomBrick(world, random, -1, j15, -radius + 4);
			placeRandomBrick(world, random, 0, j15, -radius + 6);
			placeRandomBrick(world, random, 1, j15, -radius + 4);
		}
		int rX = 0;
		int rY = height + 1;
		int rZ = 0;
		ruinsGen.generateWithSetRotation(world, random, getX(rX, rZ), getY(rY), getZ(rX, rZ), getRotationMode());
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			setBlockAndMetadata(world, i, j, k, Blocks.stone, 0);
		} else if (random.nextInt(3) > 0) {
			int l = random.nextInt(2);
			if (l == 0) {
				setBlockAndMetadata(world, i, j, k, Blocks.cobblestone, 0);
			}
			if (l == 1) {
				setBlockAndMetadata(world, i, j, k, Blocks.mossy_cobblestone, 0);
			}
		} else {
			int l = random.nextInt(3);
			if (l == 0) {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
			}
			if (l == 1) {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
			}
			if (l == 2) {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
			}
		}
	}
}
