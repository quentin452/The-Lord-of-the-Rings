package lotr.common.world.mapgen;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.LOTRChunkProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class LOTRMapGenCaves extends MapGenBase {
	public LOTRChunkProvider.ChunkFlags chunkFlags;

	public int caveRarity() {
		return 10;
	}

	public void digBlock(Block[] blockArray, int index, int xzIndex, int i, int j, int k, int chunkX, int chunkZ, LOTRBiome biome, boolean cutSurface) {
		int j1;
		int roadDepth;
		boolean dig;
		Block block = blockArray[index];
		boolean isTop = false;
		boolean belowVillageOrRoad = false;
		int topCheckDepth = 1;
		if (j >= 59 - topCheckDepth) {
			isTop = true;
			int checkAboveMax = 5;
			for (int j12 = topCheckDepth + 1; j12 <= topCheckDepth + checkAboveMax && j + j12 <= 255; ++j12) {
				if (!blockArray[index + j12].isOpaqueCube()) {
					continue;
				}
				isTop = false;
				break;
			}
		}
		if ((chunkFlags.isVillage || chunkFlags.roadFlags[xzIndex]) && j >= 59 - (roadDepth = 4)) {
			belowVillageOrRoad = true;
			int checkAboveMax = 5;
			for (j1 = roadDepth + 1; j1 <= roadDepth + checkAboveMax && j + j1 <= 255; ++j1) {
				if (!blockArray[index + j1].isOpaqueCube()) {
					continue;
				}
				belowVillageOrRoad = false;
				break;
			}
		}
		dig = isTerrainBlock(block, biome) || block.getMaterial().isLiquid();
		if (belowVillageOrRoad) {
			dig = false;
		}
		if (isTop && (!cutSurface || chunkFlags.isVillage)) {
			dig = false;
		}
		if (dig) {
			if (j < 10) {
				blockArray[index] = Blocks.lava;
			} else {
				blockArray[index] = Blocks.air;
				if (isTop) {
					int grassCheckMax = 5;
					for (j1 = 1; j1 <= grassCheckMax && j - j1 > 0; ++j1) {
						if (blockArray[index - j1] != biome.fillerBlock) {
							continue;
						}
						blockArray[index - j1] = biome.topBlock;
						break;
					}
				}
			}
		}
	}

	@Override
	public void func_151538_a(World world, int i, int k, int chunkX, int chunkZ, Block[] blocks) {
		int caves = rand.nextInt(rand.nextInt(rand.nextInt(40) + 1) + 1);
		if (rand.nextInt(caveRarity()) != 0) {
			caves = 0;
		}
		for (int l = 0; l < caves; ++l) {
			int i1 = i * 16 + rand.nextInt(16);
			int j1 = getCaveGenerationHeight();
			int k1 = k * 16 + rand.nextInt(16);
			boolean cutSurface = rand.nextInt(5) == 0;
			int nodes = 1;
			if (rand.nextInt(4) == 0) {
				generateLargeCaveNode(rand.nextLong(), chunkX, chunkZ, blocks, i1, j1, k1, cutSurface);
				nodes += rand.nextInt(4);
			}
			for (int n = 0; n < nodes; ++n) {
				float angle = rand.nextFloat() * 3.1415927f * 2.0f;
				float var18 = (rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
				float size = rand.nextFloat() * 2.0f + rand.nextFloat();
				if (rand.nextInt(10) == 0) {
					size *= rand.nextFloat() * rand.nextFloat() * 3.0f + 1.0f;
				}
				generateCaveNode(rand.nextLong(), chunkX, chunkZ, blocks, i1, j1, k1, size, angle, var18, 0, 0, 1.0, cutSurface);
			}
		}
	}

	public void generateCaveNode(long seed, int par3, int par4, Block[] blockArray, double par6, double par8, double par10, float par12, float angle, float par14, int par15, int par16, double par17, boolean cutSurface) {
		boolean var28;
		double var19 = par3 * 16 + 8;
		double var21 = par4 * 16 + 8;
		float var23 = 0.0f;
		float var24 = 0.0f;
		Random caveRand = new Random(seed);
		if (par16 <= 0) {
			int var26 = range * 16 - 16;
			par16 = var26 - caveRand.nextInt(var26 / 4);
		}
		boolean var54 = false;
		if (par15 == -1) {
			par15 = par16 / 2;
			var54 = true;
		}
		int var27 = caveRand.nextInt(par16 / 2) + par16 / 4;
		var28 = caveRand.nextInt(6) == 0;
		while (par15 < par16) {
			double var29 = 1.5 + MathHelper.sin(par15 * 3.1415927f / par16) * par12 * 1.0f;
			double var31 = var29 * par17;
			float var33 = MathHelper.cos(par14);
			float var34 = MathHelper.sin(par14);
			par6 += MathHelper.cos(angle) * var33;
			par8 += var34;
			par10 += MathHelper.sin(angle) * var33;
			par14 = var28 ? par14 * 0.92f : par14 * 0.7f;
			par14 += var24 * 0.1f;
			angle += var23 * 0.1f;
			var24 *= 0.9f;
			var23 *= 0.75f;
			var24 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 2.0f;
			var23 += (caveRand.nextFloat() - caveRand.nextFloat()) * caveRand.nextFloat() * 4.0f;
			if (!var54 && par15 == var27 && par12 > 1.0f && par16 > 0) {
				generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5f + 0.5f, angle - 1.5707964f, par14 / 3.0f, par15, par16, 1.0, cutSurface);
				generateCaveNode(caveRand.nextLong(), par3, par4, blockArray, par6, par8, par10, caveRand.nextFloat() * 0.5f + 0.5f, angle + 1.5707964f, par14 / 3.0f, par15, par16, 1.0, cutSurface);
				return;
			}
			if (var54 || caveRand.nextInt(4) != 0) {
				double var35 = par6 - var19;
				double var37 = par10 - var21;
				double var39 = par16 - par15;
				double var41 = par12 + 2.0f + 16.0f;
				if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
					return;
				}
				if (par6 >= var19 - 16.0 - var29 * 2.0 && par10 >= var21 - 16.0 - var29 * 2.0 && par6 <= var19 + 16.0 + var29 * 2.0 && par10 <= var21 + 16.0 + var29 * 2.0) {
					int var45;
					int var42;
					int var55 = MathHelper.floor_double(par6 - var29) - par3 * 16 - 1;
					int var36 = MathHelper.floor_double(par6 + var29) - par3 * 16 + 1;
					int var57 = MathHelper.floor_double(par8 - var31) - 1;
					int var38 = MathHelper.floor_double(par8 + var31) + 1;
					int var56 = MathHelper.floor_double(par10 - var29) - par4 * 16 - 1;
					int var40 = MathHelper.floor_double(par10 + var29) - par4 * 16 + 1;
					var55 = Math.max(var55, 0);
					var36 = Math.min(var36, 16);
					var57 = Math.max(var57, 1);
					var38 = Math.min(var38, 248);
					var56 = Math.max(var56, 0);
					var40 = Math.min(var40, 16);
					boolean anyWater = false;
					for (var42 = var55; !anyWater && var42 < var36; ++var42) {
						for (int var43 = var56; !anyWater && var43 < var40; ++var43) {
							for (int var44 = var38 + 1; !anyWater && var44 >= var57 - 1; --var44) {
								var45 = (var42 * 16 + var43) * 256 + var44;
								if (var44 >= 256) {
									continue;
								}
								if (blockArray[var45] == Blocks.flowing_water || blockArray[var45] == Blocks.water) {
									anyWater = true;
								}
								if (var44 == var57 - 1 || var42 == var55 || var42 == var36 - 1 || var43 == var56 || var43 == var40 - 1) {
									continue;
								}
								var44 = var57;
							}
						}
					}
					if (!anyWater) {
						for (var42 = var55; var42 < var36; ++var42) {
							double var59 = (var42 + par3 * 16 + 0.5 - par6) / var29;
							for (var45 = var56; var45 < var40; ++var45) {
								double var46 = (var45 + par4 * 16 + 0.5 - par10) / var29;
								int xzIndex = var42 * 16 + var45;
								int blockIndex = xzIndex * 256 + var57 + 1;
								if (var59 * var59 + var46 * var46 >= 1.0) {
									continue;
								}
								for (int var50 = var57; var50 <= var38 - 1; ++var50) {
									double var51 = (var50 + 0.5 - par8) / var31;
									if (var51 > -0.7 && var59 * var59 + var51 * var51 + var46 * var46 < 1.0) {
										LOTRBiome biome = (LOTRBiome) worldObj.getBiomeGenForCoords(var42 + par3 * 16, var45 + par4 * 16);
										digBlock(blockArray, blockIndex, xzIndex, var42, var50, var45, par3, par4, biome, cutSurface);
									}
									++blockIndex;
								}
							}
						}
						if (var54) {
							break;
						}
					}
				}
			}
			++par15;
		}
	}

	public void generateLargeCaveNode(long seed, int par3, int par4, Block[] blockArray, double par6, double par8, double par10, boolean cutSurface) {
		generateCaveNode(seed, par3, par4, blockArray, par6, par8, par10, 1.0f + rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5, cutSurface);
	}

	public int getCaveGenerationHeight() {
		return rand.nextInt(rand.nextInt(120) + 8);
	}

	public static boolean isTerrainBlock(Block block, BiomeGenBase biome) {
		if (block == biome.topBlock || block == biome.fillerBlock) {
			return true;
		}
		if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.sand || block == LOTRMod.whiteSand || block == Blocks.gravel || block == LOTRMod.mudGrass || block == LOTRMod.mud) {
			return true;
		}
		if (block == LOTRMod.dirtPath) {
			return true;
		}
		if (block == Blocks.stone || block == LOTRMod.rock || block == Blocks.sandstone || block == LOTRMod.redSandstone || block == LOTRMod.whiteSandstone) {
			return true;
		}
		return block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
	}
}
