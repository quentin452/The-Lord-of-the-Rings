package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRWorldGenNurnFarmBase extends LOTRWorldGenStructureBase {
	protected LOTRWorldGenNurnFarmBase(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j1;
		int k1;
		int i1;
		if (restrictions && (world.getBlock(i, j - 1, k) != Blocks.grass || !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenNurn))) {
			return false;
		}
		--j;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				k += 8;
				break;
			}
			case 1: {
				i -= 8;
				break;
			}
			case 2: {
				k -= 8;
				break;
			}
			case 3: {
				i += 8;
			}
		}
		if (restrictions) {
			for (i1 = i - 8; i1 <= i + 8; ++i1) {
				for (k1 = k - 8; k1 <= k + 8; ++k1) {
					j1 = world.getHeightValue(i1, k1) - 1;
					if (Math.abs(j1 - j) > 4) {
						return false;
					}
					Block l = world.getBlock(i1, j1, k1);
					if (l == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i - 7; i1 <= i + 7; ++i1) {
			for (k1 = k - 7; k1 <= k + 7; ++k1) {
				for (j1 = j + 1; j1 <= j + 4; ++j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
				}
				for (j1 = j; (j1 == j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, LOTRMod.brick, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (Math.abs(i1 - i) == 7 || Math.abs(k1 - k) == 7) {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, LOTRMod.brick, 0);
					setBlockAndNotifyAdequately(world, i1, j + 2, k1, LOTRMod.wall, 1);
				} else {
					setBlockAndNotifyAdequately(world, i1, j + 1, k1, LOTRMod.slabSingle, 1);
				}
				if (Math.abs(i1 - i) != 7 || Math.abs(k1 - k) != 7) {
					continue;
				}
				placeOrcTorch(world, i1, j + 3, k1);
			}
		}
		switch (rotation) {
			case 0:
				setBlockAndNotifyAdequately(world, i, j + 1, k - 7, LOTRMod.slabSingle, 1);
				setBlockAndNotifyAdequately(world, i, j + 2, k - 7, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 1, j + 3, k - 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 1, j + 3, k - 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 1, j + 4, k - 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i, j + 4, k - 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 1, j + 4, k - 7, LOTRMod.wall, 1);
				break;
			case 1:
				setBlockAndNotifyAdequately(world, i + 7, j + 1, k, LOTRMod.slabSingle, 1);
				setBlockAndNotifyAdequately(world, i + 7, j + 2, k, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i + 7, j + 3, k - 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 7, j + 3, k + 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 7, j + 4, k - 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 7, j + 4, k, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 7, j + 4, k + 1, LOTRMod.wall, 1);
				break;
			case 2:
				setBlockAndNotifyAdequately(world, i, j + 1, k + 7, LOTRMod.slabSingle, 1);
				setBlockAndNotifyAdequately(world, i, j + 2, k + 7, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 1, j + 3, k + 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 1, j + 3, k + 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 1, j + 4, k + 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i, j + 4, k + 7, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i + 1, j + 4, k + 7, LOTRMod.wall, 1);
				break;
			case 3:
				setBlockAndNotifyAdequately(world, i - 7, j + 1, k, LOTRMod.slabSingle, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 2, k, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i - 7, j + 3, k - 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 3, k + 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 4, k - 1, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 4, k, LOTRMod.wall, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 4, k + 1, LOTRMod.wall, 1);
				break;
			default:
				break;
		}
		generateCrops(world, random, i, j, k);
		int slaves = 2 + random.nextInt(4);
		for (int l = 0; l < slaves; ++l) {
			LOTREntityNurnSlave slave = new LOTREntityNurnSlave(world);
			slave.setLocationAndAngles(i + 0.5, j + 2, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
			slave.onSpawnWithEgg(null);
			slave.setHomeArea(i, j, k, 8);
			slave.isNPCPersistent = true;
			world.spawnEntityInWorld(slave);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityNurnSlave.class);
		respawner.setCheckRanges(12, -8, 8, 8);
		respawner.setSpawnRanges(6, -2, 2, 8);
		placeNPCRespawner(respawner, world, i, j, k);
		return true;
	}

	public abstract void generateCrops(World var1, Random var2, int var3, int var4, int var5);
}
