package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanFarmhand;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageFarm extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanVillageFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 <= 3 && k2 <= 4) {
					setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
				}
				if (i2 == 0 && k2 == 0) {
					setBlockAndMetadata(world, i1, -1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, -2, k1);
					setBlockAndMetadata(world, i1, 0, k1, Blocks.water, 0);
					setBlockAndMetadata(world, i1, 1, k1, logBlock, logMeta);
					setBlockAndMetadata(world, i1, 2, k1, Blocks.hay_block, 0);
					setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i1, 4, k1, Blocks.hay_block, 0);
					setBlockAndMetadata(world, i1, 5, k1, Blocks.pumpkin, 2);
					continue;
				}
				if (i2 == 3 && k2 == 4) {
					for (j1 = 1; j1 <= 2; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, logBlock, logMeta);
					}
					setBlockAndMetadata(world, i1, 3, k1, Blocks.torch, 5);
					continue;
				}
				if (i2 == 3 && k2 <= 3) {
					setBlockAndMetadata(world, i1, 1, k1, fenceBlock, fenceMeta);
					continue;
				}
				if (i2 > 2 || i2 % 2 != 0) {
					continue;
				}
				if (k2 <= 3) {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.farmland, 7);
					setBlockAndMetadata(world, i1, 1, k1, cropBlock, cropMeta);
				}
				if (k2 != 4) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, fenceBlock, fenceMeta);
			}
		}
		int farmhands = 1 + random.nextInt(2);
		for (int l = 0; l < farmhands; ++l) {
			LOTREntityRohanFarmhand farmhand = new LOTREntityRohanFarmhand(world);
			spawnNPCAndSetHome(farmhand, world, random.nextBoolean() ? -1 : 1, 1, 0, 8);
			farmhand.seedsItem = seedItem;
		}
		return true;
	}
}
