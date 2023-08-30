package lotr.common.world.structure;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitPicnicBench extends LOTRWorldGenStructureBase {
	public Block baseBlock;
	public int baseMeta;
	public Block stairBlock;
	public Block halfBlock;
	public int halfMeta;
	public Block plateBlock;

	public LOTRWorldGenHobbitPicnicBench(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		if (restrictions && !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenShire)) {
			return false;
		}
		int randomWood = random.nextInt(4);
		switch (randomWood) {
			case 0: {
				baseBlock = Blocks.planks;
				baseMeta = 0;
				stairBlock = Blocks.oak_stairs;
				halfBlock = Blocks.wooden_slab;
				halfMeta = 0;
				break;
			}
			case 1: {
				baseBlock = Blocks.planks;
				baseMeta = 1;
				stairBlock = Blocks.spruce_stairs;
				halfBlock = Blocks.wooden_slab;
				halfMeta = 1;
				break;
			}
			case 2: {
				baseBlock = Blocks.planks;
				baseMeta = 2;
				stairBlock = Blocks.birch_stairs;
				halfBlock = Blocks.wooden_slab;
				halfMeta = 2;
				break;
			}
			case 3: {
				baseBlock = LOTRMod.planks;
				baseMeta = 0;
				stairBlock = LOTRMod.stairsShirePine;
				halfBlock = LOTRMod.woodSlabSingle;
				halfMeta = 0;
			}
		}
		plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
		int rotation = random.nextInt(4);
		if (!restrictions && usingPlayer != null) {
			rotation = usingPlayerRotation();
		}
		switch (rotation) {
			case 0: {
				return generateFacingSouth(world, random, i, j, k);
			}
			case 1: {
				return generateFacingWest(world, random, i, j, k);
			}
			case 2: {
				return generateFacingNorth(world, random, i, j, k);
			}
			case 3: {
				return generateFacingEast(world, random, i, j, k);
			}
		}
		return false;
	}

	public boolean generateFacingEast(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		if (restrictions) {
			for (i1 = i; i1 <= i + 5; ++i1) {
				for (k1 = k - 2; k1 <= k + 3; ++k1) {
					if (world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i; i1 <= i + 5; ++i1) {
			for (k1 = k - 2; k1 <= k + 3; ++k1) {
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
			}
		}
		for (i1 = i; i1 <= i + 5; ++i1) {
			for (k1 = k; k1 <= k + 1; ++k1) {
				if (i1 == i || i1 == i + 5) {
					setBlockAndNotifyAdequately(world, i1, j, k1, baseBlock, baseMeta);
				} else {
					setBlockAndNotifyAdequately(world, i1, j, k1, halfBlock, halfMeta | 8);
				}
				placePlate(world, random, i1, j + 1, k1, plateBlock, LOTRFoods.HOBBIT);
			}
			setBlockAndNotifyAdequately(world, i1, j, k - 2, stairBlock, 3);
			setBlockAndNotifyAdequately(world, i1, j, k + 3, stairBlock, 2);
		}
		int hobbits = 2 + random.nextInt(3);
		for (int i12 = 0; i12 < hobbits; ++i12) {
			LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
			int hobbitX = i + random.nextInt(6);
			int hobbitZ = k - 1 + random.nextInt(2) * 3;
			hobbit.setLocationAndAngles(hobbitX + 0.5, j, hobbitZ + 0.5, 0.0f, 0.0f);
			hobbit.setHomeArea(hobbitX, j, hobbitZ, 16);
			hobbit.onSpawnWithEgg(null);
			hobbit.isNPCPersistent = true;
			world.spawnEntityInWorld(hobbit);
		}
		return true;
	}

	public boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		if (restrictions) {
			for (k1 = k; k1 >= k - 5; --k1) {
				for (i1 = i - 2; i1 <= i + 3; ++i1) {
					if (world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (k1 = k; k1 >= k - 5; --k1) {
			for (i1 = i - 2; i1 <= i + 3; ++i1) {
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
			}
		}
		for (k1 = k; k1 >= k - 5; --k1) {
			for (i1 = i; i1 <= i + 1; ++i1) {
				if (k1 == k || k1 == k - 5) {
					setBlockAndNotifyAdequately(world, i1, j, k1, baseBlock, baseMeta);
				} else {
					setBlockAndNotifyAdequately(world, i1, j, k1, halfBlock, halfMeta | 8);
				}
				placePlate(world, random, i1, j + 1, k1, plateBlock, LOTRFoods.HOBBIT);
			}
			setBlockAndNotifyAdequately(world, i - 2, j, k1, stairBlock, 1);
			setBlockAndNotifyAdequately(world, i + 3, j, k1, stairBlock, 0);
		}
		int hobbits = 2 + random.nextInt(3);
		for (i1 = 0; i1 < hobbits; ++i1) {
			LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
			int hobbitX = i - 1 + random.nextInt(2) * 3;
			int hobbitZ = k - random.nextInt(6);
			hobbit.setLocationAndAngles(hobbitX + 0.5, j, hobbitZ + 0.5, 0.0f, 0.0f);
			hobbit.setHomeArea(hobbitX, j, hobbitZ, 16);
			hobbit.onSpawnWithEgg(null);
			hobbit.isNPCPersistent = true;
			world.spawnEntityInWorld(hobbit);
		}
		return true;
	}

	public boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		if (restrictions) {
			for (k1 = k; k1 <= k + 5; ++k1) {
				for (i1 = i + 2; i1 >= i - 3; --i1) {
					if (world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (k1 = k; k1 <= k + 5; ++k1) {
			for (i1 = i + 2; i1 >= i - 3; --i1) {
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
			}
		}
		for (k1 = k; k1 <= k + 5; ++k1) {
			for (i1 = i; i1 >= i - 1; --i1) {
				if (k1 == k || k1 == k + 5) {
					setBlockAndNotifyAdequately(world, i1, j, k1, baseBlock, baseMeta);
				} else {
					setBlockAndNotifyAdequately(world, i1, j, k1, halfBlock, halfMeta | 8);
				}
				placePlate(world, random, i1, j + 1, k1, plateBlock, LOTRFoods.HOBBIT);
			}
			setBlockAndNotifyAdequately(world, i - 3, j, k1, stairBlock, 1);
			setBlockAndNotifyAdequately(world, i + 2, j, k1, stairBlock, 0);
		}
		int hobbits = 2 + random.nextInt(3);
		for (i1 = 0; i1 < hobbits; ++i1) {
			LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
			int hobbitX = i + 1 - random.nextInt(2) * 3;
			int hobbitZ = k + random.nextInt(6);
			hobbit.setLocationAndAngles(hobbitX + 0.5, j, hobbitZ + 0.5, 0.0f, 0.0f);
			hobbit.setHomeArea(hobbitX, j, hobbitZ, 16);
			hobbit.onSpawnWithEgg(null);
			hobbit.isNPCPersistent = true;
			world.spawnEntityInWorld(hobbit);
		}
		return true;
	}

	public boolean generateFacingWest(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		if (restrictions) {
			for (i1 = i; i1 >= i - 5; --i1) {
				for (k1 = k + 2; k1 >= k - 3; --k1) {
					if (world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = i; i1 >= i - 5; --i1) {
			for (k1 = k + 2; k1 >= k - 3; --k1) {
				setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
				setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
			}
		}
		for (i1 = i; i1 >= i - 5; --i1) {
			for (k1 = k; k1 >= k - 1; --k1) {
				if (i1 == i || i1 == i - 5) {
					setBlockAndNotifyAdequately(world, i1, j, k1, baseBlock, baseMeta);
				} else {
					setBlockAndNotifyAdequately(world, i1, j, k1, halfBlock, halfMeta | 8);
				}
				placePlate(world, random, i1, j + 1, k1, plateBlock, LOTRFoods.HOBBIT);
			}
			setBlockAndNotifyAdequately(world, i1, j, k - 3, stairBlock, 3);
			setBlockAndNotifyAdequately(world, i1, j, k + 2, stairBlock, 2);
		}
		int hobbits = 2 + random.nextInt(3);
		for (int i12 = 0; i12 < hobbits; ++i12) {
			LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
			int hobbitX = i - random.nextInt(6);
			int hobbitZ = k + 1 - random.nextInt(2) * 3;
			hobbit.setLocationAndAngles(hobbitX + 0.5, j, hobbitZ + 0.5, 0.0f, 0.0f);
			hobbit.setHomeArea(hobbitX, j, hobbitZ, 16);
			hobbit.onSpawnWithEgg(null);
			hobbit.isNPCPersistent = true;
			world.spawnEntityInWorld(hobbit);
		}
		return true;
	}
}
