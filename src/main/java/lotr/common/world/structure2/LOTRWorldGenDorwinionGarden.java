package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class LOTRWorldGenDorwinionGarden extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenDorwinionGarden(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 7);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			int maxEdgeHeight = 0;
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
					int j1 = getTopBlock(world, i1, k1);
					Block block = getBlock(world, i1, j1 - 1, k1);
					if (block != Blocks.grass) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight > 4) {
						return false;
					}
					if (Math.abs(i1) != 7 && Math.abs(k1) != 7 || j1 <= maxEdgeHeight) {
						continue;
					}
					maxEdgeHeight = j1;
				}
			}
			originY = getY(maxEdgeHeight);
		}
		int r = 25;
		int h = 4;
		int gardenR = 6;
		int leafR = 7;
		for (int i1 = -r; i1 <= r; ++i1) {
			for (int k1 = -r; k1 <= r; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				boolean within = false;
				for (j1 = 0; j1 >= -h; --j1) {
					int j2 = j1 + r - 2;
					int d = i2 * i2 + j2 * j2 + k2 * k2;
					if (d >= r * r) {
						continue;
					}
					boolean grass = !isOpaque(world, i1, j1 + 1, k1);
					setBlockAndMetadata(world, i1, j1, k1, grass ? Blocks.grass : Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					within = true;
				}
				if (!within) {
					continue;
				}
				j1 = -h - 1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				int dh = i2 * i2 + k2 * k2;
				if (dh < gardenR * gardenR) {
					setBlockAndMetadata(world, i1, 0, k1, LOTRMod.brick5, 2);
				} else if (dh < leafR * leafR) {
					setBlockAndMetadata(world, i1, 1, k1, LOTRMod.leaves6, 6);
				} else if (random.nextInt(5) == 0) {
					BiomeGenBase biome = getBiome(world, i1, k1);
					int j12 = getTopBlock(world, i1, k1);
					biome.plantFlower(world, random, getX(i1, k1), getY(j12), getZ(i1, k1));
				}
				if (i2 == 6 && k2 == 6) {
					setGrassToDirt(world, i1, 0, k1);
					setBlockAndMetadata(world, i1, 1, k1, LOTRMod.brick5, 2);
					setBlockAndMetadata(world, i1, 2, k1, LOTRMod.brick5, 2);
					setBlockAndMetadata(world, i1, 3, k1, LOTRMod.wall3, 10);
					setBlockAndMetadata(world, i1, 4, k1, Blocks.torch, 5);
				}
				if (i2 == gardenR && k2 <= 1 || k2 == gardenR && i2 <= 1) {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.stained_hardened_clay, 0);
					setAir(world, i1, 1, k1);
				}
				if (i2 == gardenR - 1 && k2 == 2 || k2 == gardenR - 1 && i2 == 2) {
					setGrassToDirt(world, i1, 0, k1);
					setBlockAndMetadata(world, i1, 1, k1, LOTRMod.brick5, 2);
					setBlockAndMetadata(world, i1, 2, k1, LOTRMod.slabSingle9, 7);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.stained_hardened_clay, 0);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i1, 1, k1, LOTRMod.slabSingle9, 7);
				}
				if (i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1) {
					for (int j13 = -1; j13 <= 1; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, LOTRMod.brick5, 2);
					}
				}
				if (i2 > 1 || k2 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, -2, k1, LOTRMod.brick5, 2);
				for (int j14 = -1; j14 <= 1; ++j14) {
					setBlockAndMetadata(world, i1, j14, k1, Blocks.water, 0);
				}
			}
		}
		return true;
	}
}
