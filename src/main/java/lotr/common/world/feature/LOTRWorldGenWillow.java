package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class LOTRWorldGenWillow extends WorldGenAbstractTree {
	public Block woodBlock = LOTRMod.wood6;
	public int woodMeta = 1;
	public Block leafBlock = LOTRMod.leaves6;
	public int leafMeta = 1;
	public int minHeight = 8;
	public int maxHeight = 13;
	public boolean needsWater;

	public LOTRWorldGenWillow(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below;
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (j >= 1 && height + 1 <= 256) {
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= j + height - 1) {
					range = 2;
				}
				for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
					for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k1)) {
							continue;
						}
						flag = false;
					}
				}
			}
		} else {
			flag = false;
		}
		if (!(below = world.getBlock(i, j - 1, k)).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			flag = false;
		}
		if (!flag) {
			return false;
		}
        if (needsWater && !hasWaterNearby(world, random, i, j, k)) {
            return false;
        }
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
        Collection<ChunkCoordinates> vineGrows = new ArrayList<>();
        int angle = 0;

        while (angle < 360) {
            float angleR = (float) Math.toRadians(angle += 30 + random.nextInt(30));
            float sin = MathHelper.sin(angleR);
            float cos = MathHelper.cos(angleR);

            int base = j + height - 3 - random.nextInt(3);
            int length = 2 + random.nextInt(4);
            int i1 = i;
            int j1 = base;
            int k1 = k;

            for (int l = 0; l < length; ++l) {
                j1 = updateJ1(j1, random);
                i1 = updateI1(i1, cos, random);
                k1 = updateK1(k1, sin, random);
                setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
            }

            spawnLeafCluster(world, random, i1, j1, k1);
            vineGrows.add(new ChunkCoordinates(i1, j1, k1));
        }

        for (int j1 = 0; j1 < height; ++j1) {
            setBlockAndNotifyAdequately(world, i, j + j1, k, woodBlock, woodMeta);
            if (j1 == height - 1) {
                spawnLeafCluster(world, random, i, j + j1, k);
                vineGrows.add(new ChunkCoordinates(i, j + j1, k));
            }
        }

        for (int i1 = i - 1; i1 <= i + 1; ++i1) {
            for (int k1 = k - 1; k1 <= k + 1; ++k1) {
                if (Math.abs(i1 - i) != Math.abs(k1 - k)) {
                    generateRoots(world, random, i1, k1, j);
                }
            }
        }

        for (ChunkCoordinates coords : vineGrows) {
            spawnVineCluster(world, random, coords.posX, coords.posY, coords.posZ);
        }

        return true;
    }
    private boolean hasWaterNearby(World world, Random random, int i, int j, int k) {
        int attempts = 4;
        for (int l = 0; l < attempts; ++l) {
            int xOffset = random.nextInt(13) - 6;
            int zOffset = random.nextInt(13) - 6;
            int yOffset = random.nextInt(7) - 4;
            int i1 = i + xOffset;
            int j1 = j + yOffset;
            int k1 = k + zOffset;
            int chunkX = i1 >> 4;
            int chunkZ = k1 >> 4;
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            if (chunk != null && chunk.isChunkLoaded) {
                Block block = world.getBlock(i1, j1, k1);
                if (block == Blocks.water || block == Blocks.flowing_water) {
                    return true;
                }
            }
        }
        return false;
    }
    private int updateJ1(int j1, Random random) {
        if (j1 > 0 && (j1 % 4 == 0 || random.nextInt(3) == 0)) {
            return j1 + 1;
        }
        return j1;
    }

    private int updateI1(int i1, float cos, Random random) {
        if (random.nextFloat() < Math.abs(cos)) {
            return (int) (i1 + Math.signum(cos));
        }
        return i1;
    }

    private int updateK1(int k1, float sin, Random random) {
        if (random.nextFloat() < Math.abs(sin)) {
            return (int) (k1 + Math.signum(sin));
        }
        return k1;
    }

    private void generateRoots(World world, Random random, int i1, int k1, int j) {
        int rootY = j + 1 + random.nextInt(2);
        while (world.getBlock(i1, rootY, k1).isReplaceable(world, i1, rootY, k1)) {
            setBlockAndNotifyAdequately(world, i1, rootY, k1, woodBlock, woodMeta | 0xC);
            world.getBlock(i1, rootY - 1, k1).onPlantGrow(world, i1, rootY - 1, k1, i1, rootY, k1);
            rootY--;
            random.nextInt(3);
        }
    }

    public void growVines(World world, Random random, int i, int j, int k, int meta) {
		setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.willowVines, meta);
		int vines = 0;
		while (world.getBlock(i, --j, k).isAir(world, i, j, k) && vines < 2 + random.nextInt(4)) {
			setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.willowVines, meta);
			++vines;
		}
	}

	public LOTRWorldGenWillow setNeedsWater() {
		needsWater = true;
		return this;
	}

	public void spawnLeafCluster(World world, Random random, int i, int j, int k) {
		int leafRange = 3;
		int leafRangeSq = leafRange * leafRange;
		int leafRangeSqLess = (int) ((leafRange - 0.5) * (leafRange - 0.5));
		for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
			for (int j1 = j - leafRange; j1 <= j + leafRange; ++j1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					Block block;
					int i2 = i1 - i;
					int j2 = j1 - j;
					int k2 = k1 - k;
					int dist = i2 * i2 + j2 * j2 + k2 * k2;
					int taxicab = Math.abs(i2) + Math.abs(j2) + Math.abs(k2);
					if (dist >= leafRangeSqLess && (dist >= leafRangeSq || random.nextInt(3) != 0) || taxicab > 4 || !(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
				}
			}
		}
	}

	public void spawnVineCluster(World world, Random random, int i, int j, int k) {
		int leafRange = 3;
		int leafRangeSq = leafRange * leafRange;
		for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
			for (int j1 = j - leafRange; j1 <= j + leafRange; ++j1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					int i2 = i1 - i;
					int j2 = j1 - j;
					int k2 = k1 - k;
					int dist = i2 * i2 + j2 * j2 + k2 * k2;
					if (dist >= leafRangeSq) {
						continue;
					}
					Block block = world.getBlock(i1, j1, k1);
					int meta = world.getBlockMetadata(i1, j1, k1);
					if (block != leafBlock || meta != leafMeta) {
						continue;
					}
					int vineChance = 2;
					if (random.nextInt(vineChance) == 0 && world.getBlock(i1 - 1, j1, k1).isAir(world, i1 - 1, j1, k1)) {
						growVines(world, random, i1 - 1, j1, k1, 8);
					}
					if (random.nextInt(vineChance) == 0 && world.getBlock(i1 + 1, j1, k1).isAir(world, i1 + 1, j1, k1)) {
						growVines(world, random, i1 + 1, j1, k1, 2);
					}
					if (random.nextInt(vineChance) == 0 && world.getBlock(i1, j1, k1 - 1).isAir(world, i1, j1, k1 - 1)) {
						growVines(world, random, i1, j1, k1 - 1, 1);
					}
					if (random.nextInt(vineChance) != 0 || !world.getBlock(i1, j1, k1 + 1).isAir(world, i1, j1, k1 + 1)) {
						continue;
					}
					growVines(world, random, i1, j1, k1 + 1, 4);
				}
			}
		}
	}
}
