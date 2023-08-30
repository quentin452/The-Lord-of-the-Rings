package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRWorldGenWoodElfPlatform;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMirkOak extends WorldGenAbstractTree {
	public int minHeight;
	public int maxHeight;
	public int trunkWidth;
	public boolean isMirky;
	public boolean restrictions = true;
	public boolean isDead;
	public boolean hasRoots = true;
	public Block woodBlock = LOTRMod.wood;
	public int woodMeta = 2;
	public Block leafBlock = LOTRMod.leaves;
	public int leafMeta = 2;

	public LOTRWorldGenMirkOak(boolean flag, int i, int j, int k, boolean mirk) {
		super(flag);
		minHeight = i;
		maxHeight = j;
		trunkWidth = k;
		isMirky = mirk;
	}

	public LOTRWorldGenMirkOak disableRestrictions() {
		restrictions = false;
		return this;
	}

	public LOTRWorldGenMirkOak disableRoots() {
		hasRoots = false;
		return this;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (!restrictions || j >= 1 && j + height + 5 <= 256) {
			int i1;
			int j1;
			int k1;
			int i12;
			if (restrictions) {
				for (j1 = j; j1 <= j + height + 5; ++j1) {
					int range = trunkWidth + 1;
					if (j1 == j) {
						range = trunkWidth;
					}
					if (j1 >= j + height + 2) {
						range = trunkWidth + 2;
					}
					for (i1 = i - range; i1 <= i + range && flag; ++i1) {
						for (int k12 = k - range; k12 <= k + range && flag; ++k12) {
							if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k12)) {
								continue;
							}
							flag = false;
						}
					}
				}
				for (i12 = i - trunkWidth; i12 <= i + trunkWidth && flag; ++i12) {
					for (k1 = k - trunkWidth; k1 <= k + trunkWidth && flag; ++k1) {
						Block block = world.getBlock(i12, j - 1, k1);
						boolean isSoil = block.canSustainPlant(world, i12, j - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling);
						if (isSoil) {
							continue;
						}
						flag = false;
					}
				}
				if (!flag) {
					return false;
				}
			}
			if (restrictions) {
				for (i12 = i - trunkWidth; i12 <= i + trunkWidth; ++i12) {
					for (k1 = k - trunkWidth; k1 <= k + trunkWidth; ++k1) {
						world.getBlock(i12, j - 1, k1).onPlantGrow(world, i12, j - 1, k1, i12, j, k1);
					}
				}
			}
			for (j1 = 0; j1 < height; ++j1) {
				for (int i13 = i - trunkWidth; i13 <= i + trunkWidth; ++i13) {
					for (int k13 = k - trunkWidth; k13 <= k + trunkWidth; ++k13) {
						setBlockAndNotifyAdequately(world, i13, j + j1, k13, woodBlock, woodMeta);
					}
				}
			}
			if (trunkWidth >= 1) {
				int deg = 0;
				while (deg < 360) {
					float angle = (float) Math.toRadians(deg += (40 + random.nextInt(30)) / trunkWidth);
					float cos = MathHelper.cos(angle);
					float sin = MathHelper.sin(angle);
					float angleY = random.nextFloat() * 0.6981317007977318f;
					float sinY = MathHelper.sin(angleY);
					int length = 3 + random.nextInt(6);
					int i14 = i;
					int k14 = k;
					int j12 = j + height - 1 - random.nextInt(5);
					for (int l = 0; l < (length *= 1 + random.nextInt(trunkWidth)); ++l) {
						Block block;
						if (Math.floor(cos * l) != Math.floor(cos * (l - 1))) {
							i14 = (int) (i14 + Math.signum(cos));
						}
						if (Math.floor(sin * l) != Math.floor(sin * (l - 1))) {
							k14 = (int) (k14 + Math.signum(sin));
						}
						if (Math.floor(sinY * l) != Math.floor(sinY * (l - 1))) {
							j12 = (int) (j12 + Math.signum(sinY));
						}
						if (!(block = world.getBlock(i14, j12, k14)).isReplaceable(world, i14, j12, k14) && !block.isWood(world, i14, j12, k14) && !block.isLeaves(world, i14, j12, k14)) {
							break;
						}
						setBlockAndNotifyAdequately(world, i14, j12, k14, woodBlock, woodMeta);
					}
					growLeafCanopy(world, random, i14, j12, k14);
				}
				if (trunkWidth == 2) {
					int platforms = 0;
					if (random.nextInt(3) != 0) {
						platforms = random.nextBoolean() ? 1 + random.nextInt(3) : 4 + random.nextInt(5);
					}
					for (int l = 0; l < platforms; ++l) {
						int j13 = j + MathHelper.getRandomIntegerInRange(random, 10, height);
						new LOTRWorldGenWoodElfPlatform(false).generate(world, random, i, j13, k);
					}
				}
			} else {
				growLeafCanopy(world, random, i, j + height - 1, k);
			}
			if (hasRoots) {
				int roots = 4 + random.nextInt(5 * trunkWidth + 1);
				for (int l = 0; l < roots; ++l) {
					i1 = i;
					int j14 = j + 1 + random.nextInt(trunkWidth * 2 + 1);
					int k15 = k;
					int xDirection = 0;
					int zDirection = 0;
					int rootLength = 1 + random.nextInt(4);
					if (random.nextBoolean()) {
						if (random.nextBoolean()) {
							i1 -= trunkWidth + 1;
							xDirection = -1;
						} else {
							i1 += trunkWidth + 1;
							xDirection = 1;
						}
						k15 -= trunkWidth + 1;
						k15 += random.nextInt(trunkWidth * 2 + 2);
					} else {
						if (random.nextBoolean()) {
							k15 -= trunkWidth + 1;
							zDirection = -1;
						} else {
							k15 += trunkWidth + 1;
							zDirection = 1;
						}
						i1 -= trunkWidth + 1;
						i1 += random.nextInt(trunkWidth * 2 + 2);
					}
					for (int l1 = 0; l1 < rootLength; ++l1) {
						int rootBlocks = 0;
						int j2 = j14;
						while (!world.getBlock(i1, j2, k15).isOpaqueCube()) {
							setBlockAndNotifyAdequately(world, i1, j2, k15, woodBlock, woodMeta | 0xC);
							world.getBlock(i1, j2 - 1, k15).onPlantGrow(world, i1, j2 - 1, k15, i1, j2, k15);
							rootBlocks++;
							if (rootBlocks > 5) {
								break;
							}
							--j2;
						}
						--j14;
						if (!random.nextBoolean()) {
							continue;
						}
						if (xDirection == -1) {
							--i1;
							continue;
						}
						if (xDirection == 1) {
							++i1;
							continue;
						}
						if (zDirection == -1) {
							--k15;
							continue;
						}
						++k15;
					}
				}
			}
			return true;
		}
		return false;
	}

	public void growLeafCanopy(World world, Random random, int i, int j, int k) {
		int j1;
		int leafStart = j + 2;
		int leafTop = j + 5;
		int maxRange = 3;
		if (!isDead) {
			for (j1 = leafStart; j1 <= leafTop; ++j1) {
				int j2 = j1 - leafTop;
				int leafRange = maxRange - j2;
				int leafRangeSq = leafRange * leafRange;
				for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
					for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
						int k2;
						boolean grow;
						int i2 = Math.abs(i1 - i);
						int dist = i2 * i2 + (k2 = Math.abs(k1 - k)) * k2;
						grow = dist < leafRangeSq;
						if (i2 == leafRange - 1 || k2 == leafRange - 1) {
							grow = grow && random.nextInt(4) > 0;
						}
						if (!grow) {
							continue;
						}
						int below = 0;
						if (isMirky && j1 == leafStart && i2 <= 3 && k2 <= 3 && random.nextInt(3) == 0) {
							++below;
						}
						for (int j3 = j1; j3 >= j1 - below; --j3) {
							Block block = world.getBlock(i1, j3, k1);
							if (!block.isReplaceable(world, i1, j3, k1) && !block.isLeaves(world, i1, j3, k1)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i1, j3, k1, leafBlock, leafMeta);
							if (!isMirky) {
								continue;
							}
							if (random.nextInt(20) == 0 && world.isAirBlock(i1 - 1, j3, k1)) {
								growVines(world, random, i1 - 1, j3, k1, 8);
							}
							if (random.nextInt(20) == 0 && world.isAirBlock(i1 + 1, j3, k1)) {
								growVines(world, random, i1 + 1, j3, k1, 2);
							}
							if (random.nextInt(20) == 0 && world.isAirBlock(i1, j3, k1 - 1)) {
								growVines(world, random, i1, j3, k1 - 1, 1);
							}
							if (random.nextInt(20) != 0 || !world.isAirBlock(i1, j3, k1 + 1)) {
								continue;
							}
							growVines(world, random, i1, j3, k1 + 1, 4);
						}
					}
				}
			}
		}
		for (j1 = j; j1 <= j + 2; ++j1) {
			for (int i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
				for (int k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
					Block block;
					int i2 = Math.abs(i1 - i);
					int k2 = Math.abs(k1 - k);
					int j2 = j1 - j;
					if ((i2 != 0 || k2 != 0) && (i2 != k2 || i2 != j2) && (i2 != 0 && k2 != 0 || i2 != j2 + 1 && k2 != j2 + 1) || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
				}
			}
		}
	}

	public void growVines(World world, Random random, int i, int j, int k, int meta) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mirkVines, meta);
		int length = 4 + random.nextInt(8);
		while (world.isAirBlock(i, --j, k) && length > 0) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mirkVines, meta);
			--length;
		}
	}

	public LOTRWorldGenMirkOak setBlocks(Block b1, int m1, Block b2, int m2) {
		woodBlock = b1;
		woodMeta = m1;
		leafBlock = b2;
		leafMeta = m2;
		return this;
	}

	public LOTRWorldGenMirkOak setDead() {
		isDead = true;
		return this;
	}

	public LOTRWorldGenMirkOak setGreenOak() {
		return setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves7, 1);
	}

	public LOTRWorldGenMirkOak setRedOak() {
		return setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves, 3);
	}
}
