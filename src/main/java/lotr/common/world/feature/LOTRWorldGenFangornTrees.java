package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenFangornTrees extends WorldGenAbstractTree {
	public Block woodID;
	public int woodMeta;
	public Block leafID;
	public int leafMeta;
	public boolean generateLeaves = true;
	public boolean restrictions = true;
	public float heightFactor = 1.0f;

	public LOTRWorldGenFangornTrees(boolean flag, Block i, int j, Block k, int l) {
		super(flag);
		woodID = i;
		woodMeta = j;
		leafID = k;
		leafMeta = l;
	}

	public LOTRWorldGenFangornTrees disableRestrictions() {
		restrictions = false;
		return this;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		if (restrictions && !world.getBlock(i, j - 1, k).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			return false;
		}
		float f = 0.5f + random.nextFloat() * 0.5f;
		int height = (int) (f * 40.0f * heightFactor);
		int trunkRadiusMin = (int) (f * 5.0f);
		int trunkRadiusMax = trunkRadiusMin + 4;
		int xSlope = 4 + random.nextInt(7);
		if (random.nextBoolean()) {
			xSlope *= -1;
		}
		int zSlope = 4 + random.nextInt(7);
		if (random.nextBoolean()) {
			zSlope *= -1;
		}
		if (restrictions) {
			boolean flag = true;
			if (j < 1 || j + height + 5 > 256) {
				return false;
			}
			for (int i1 = i - 1; i1 <= i + 1; ++i1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					for (int j1 = j; j1 <= j + height; ++j1) {
						for (int i2 = i1 - trunkRadiusMax; i2 <= i1 + trunkRadiusMax && flag; ++i2) {
							for (int k2 = k1 - trunkRadiusMax; k2 <= k1 + trunkRadiusMax && flag; ++k2) {
								if (j1 >= 0 && j1 < 256 && isReplaceable(world, i2, j1, k2)) {
									continue;
								}
								flag = false;
							}
						}
					}
				}
			}
			if (!flag) {
				return false;
			}
		}
		for (int j1 = 0; j1 < height; ++j1) {
			int width = trunkRadiusMax - (int) ((float) j1 / height * (trunkRadiusMax - trunkRadiusMin));
			for (int i1 = i - width; i1 <= i + width; ++i1) {
				for (int k1 = k - width; k1 <= k + width; ++k1) {
					int i2 = i1 - i;
					int k2 = k1 - k;
					if (i2 * i2 + k2 * k2 >= width * width) {
						continue;
					}
					Block block = world.getBlock(i1, j + j1, k1);
					if (block == Blocks.air || block.isLeaves(world, i1, j + j1, k1)) {
						setBlockAndNotifyAdequately(world, i1, j + j1, k1, woodID, woodMeta);
					}
					if (j1 != 0) {
						continue;
					}
					world.getBlock(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
					for (int j2 = j - 1; !LOTRMod.isOpaque(world, i1, j2, k1) && j2 >= 0 && Math.abs(j2 - j) <= 6 + random.nextInt(5); --j2) {
						setBlockAndNotifyAdequately(world, i1, j2, k1, woodID, woodMeta);
						world.getBlock(i1, j2 - 1, k1).onPlantGrow(world, i1, j2 - 1, k1, i1, j2, k1);
					}
				}
			}
			if (j1 % xSlope == 0) {
				if (xSlope > 0) {
					++i;
				} else {
					--i;
				}
			}
			if (j1 % zSlope != 0) {
				continue;
			}
			if (zSlope > 0) {
				++k;
				continue;
			}
			--k;
		}
		int angle = 0;
		while (angle < 360) {
			float angleR = (angle += 10 + random.nextInt(20)) / 180.0f * 3.1415927f;
			float sin = MathHelper.sin(angleR);
			float cos = MathHelper.cos(angleR);
			int boughLength = 12 + random.nextInt(10);
			int boughThickness = Math.round(boughLength / 25.0f * 1.5f);
			int boughBaseHeight = j + MathHelper.floor_double(height * (0.9f + random.nextFloat() * 0.1f));
			int boughHeight = 3 + random.nextInt(4);
			for (int l = 0; l < boughLength; ++l) {
				int i1 = i + Math.round(sin * l);
				int k1 = k + Math.round(cos * l);
				int j1 = boughBaseHeight + Math.round((float) l / boughLength * boughHeight);
				int range = boughThickness - Math.round((float) l / boughLength * boughThickness * 0.5f);
				for (int i2 = i1 - range; i2 <= i1 + range; ++i2) {
					for (int j2 = j1 - range; j2 <= j1 + range; ++j2) {
						for (int k2 = k1 - range; k2 <= k1 + range; ++k2) {
							Block block = world.getBlock(i2, j2, k2);
							if (!block.isReplaceable(world, i2, j2, k2) && !block.isLeaves(world, i2, j2, k2)) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i2, j2, k2, woodID, woodMeta | 0xC);
						}
					}
				}
				int branch_angle = angle + random.nextInt(360);
				float branch_angleR = branch_angle / 180.0f * 3.1415927f;
				float branch_sin = MathHelper.sin(branch_angleR);
				float branch_cos = MathHelper.cos(branch_angleR);
				int branchLength = 7 + random.nextInt(6);
				int branchHeight = random.nextInt(6);
				int leafRange = 3;
				for (int l1 = 0; l1 < branchLength; ++l1) {
					int j2;
					int i2 = i1 + Math.round(branch_sin * l1);
					int k2 = k1 + Math.round(branch_cos * l1);
					for (int j3 = j2 = j1 + Math.round((float) l1 / branchLength * branchHeight); j3 >= j2 - 1; --j3) {
						Block block = world.getBlock(i2, j3, k2);
						if (!block.isReplaceable(world, i2, j3, k2) && !block.isLeaves(world, i2, j3, k2)) {
							continue;
						}
						setBlockAndNotifyAdequately(world, i2, j3, k2, woodID, woodMeta | 0xC);
					}
					if (!generateLeaves || l1 != branchLength - 1) {
						continue;
					}
					for (int i3 = i2 - leafRange; i3 <= i2 + leafRange; ++i3) {
						for (int j3 = j2 - leafRange; j3 <= j2 + leafRange; ++j3) {
							for (int k3 = k2 - leafRange; k3 <= k2 + leafRange; ++k3) {
								Block block2;
								int i4 = i3 - i2;
								int j4 = j3 - j2;
								int k4 = k3 - k2;
								int dist = i4 * i4 + j4 * j4 + k4 * k4;
								if (dist >= (leafRange - 1) * (leafRange - 1) && (dist >= leafRange * leafRange || random.nextInt(3) == 0) || (block2 = world.getBlock(i3, j3, k3)).getMaterial() != Material.air && !block2.isLeaves(world, i3, j3, k3)) {
									continue;
								}
								setBlockAndNotifyAdequately(world, i3, j3, k3, leafID, leafMeta);
								if (random.nextInt(40) == 0 && world.isAirBlock(i3 - 1, j3, k3)) {
									growVines(world, random, i3 - 1, j3, k3, 8);
								}
								if (random.nextInt(40) == 0 && world.isAirBlock(i3 + 1, j3, k3)) {
									growVines(world, random, i3 + 1, j3, k3, 2);
								}
								if (random.nextInt(40) == 0 && world.isAirBlock(i3, j3, k3 - 1)) {
									growVines(world, random, i3, j3, k3 - 1, 1);
								}
								if (random.nextInt(40) != 0 || !world.isAirBlock(i3, j3, k3 + 1)) {
									continue;
								}
								growVines(world, random, i3, j3, k3 + 1, 4);
							}
						}
					}
				}
			}
		}
		return true;
	}

	public void growVines(World world, Random random, int i, int j, int k, int meta) {
		setBlockAndNotifyAdequately(world, i, j, k, Blocks.vine, meta);
		int length = 4 + random.nextInt(12);
		while (world.isAirBlock(i, --j, k) && length > 0) {
			setBlockAndNotifyAdequately(world, i, j, k, Blocks.vine, meta);
			--length;
		}
	}

	public LOTRWorldGenFangornTrees setHeightFactor(float f) {
		heightFactor = f;
		return this;
	}

	public LOTRWorldGenFangornTrees setNoLeaves() {
		generateLeaves = false;
		return this;
	}
}
