package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTRWorldGenSouthronBazaar extends LOTRWorldGenSouthronStructure {
	public static Class[] stalls = {Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class};

	public LOTRWorldGenSouthronBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -13; i1 <= 13; ++i1) {
				for (int k1 = -9; k1 <= 9; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -13; i1 <= 13; ++i1) {
			for (int k1 = -9; k1 <= 9; ++k1) {
				int j1;
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		loadStrScan("southron_bazaar");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeArmorStand(world, -4, 1, -2, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHaradWarlord), null, null, null});
		placeAnimalJar(world, -3, 1, -7, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
		placeAnimalJar(world, 11, 1, -1, LOTRMod.birdCageWood, 0, null);
		placeAnimalJar(world, 3, 1, 7, LOTRMod.birdCage, 0, new LOTREntityBird(world));
		placeAnimalJar(world, -9, 3, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeAnimalJar(world, 4, 3, 3, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		List<Class> stallClasses = new ArrayList<>(Arrays.asList(getStallClasses()));
		while (stallClasses.size() > 6) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall4 = (LOTRWorldGenStructureBase2) stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall5 = (LOTRWorldGenStructureBase2) stallClasses.get(5).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -8, 1, -4, 2);
			generateSubstructure(stall1, world, random, 0, 1, -4, 2);
			generateSubstructure(stall2, world, random, 8, 1, -4, 2);
			generateSubstructure(stall3, world, random, -8, 1, 4, 0);
			generateSubstructure(stall4, world, random, 0, 1, 4, 0);
			generateSubstructure(stall5, world, random, 8, 1, 4, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public Class[] getStallClasses() {
		return stalls;
	}

	public static class Baker extends LOTRWorldGenStructureBase2 {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
			placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, getRandomFlower(world, random));
			LOTREntitySouthronBaker trader = new LOTREntitySouthronBaker(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Blacksmith extends LOTRWorldGenStructureBase2 {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
			placeArmorStand(world, 1, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null});
			placeWeaponRack(world, -2, 2, 0, 1, new LOTRWorldGenSouthronBazaar(false).getRandomHaradWeapon(random));
			placeWeaponRack(world, 2, 2, 0, 3, new LOTRWorldGenSouthronBazaar(false).getRandomHaradWeapon(random));
			LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Brewer extends LOTRWorldGenStructureBase2 {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
			placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
			placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
			LOTREntitySouthronBrewer trader = new LOTREntitySouthronBrewer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Butcher extends LOTRWorldGenStructureBase2 {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
			LOTREntitySouthronButcher trader = new LOTREntitySouthronButcher(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Farmer extends LOTRWorldGenStructureBase2 {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
			LOTREntitySouthronFarmer trader = new LOTREntitySouthronFarmer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Fish extends LOTRWorldGenStructureBase2 {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			LOTREntitySouthronFishmonger trader = new LOTREntitySouthronFishmonger(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Florist extends LOTRWorldGenStructureBase2 {
		public Florist(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, -2, 2, 0, getRandomFlower(world, random));
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			setBlockAndMetadata(world, -1, 0, 1, Blocks.grass, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.grass, 0);
			plantFlower(world, random, 1, 2, 1);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
			LOTREntitySouthronFlorist trader = new LOTREntitySouthronFlorist(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Goldsmith extends LOTRWorldGenStructureBase2 {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
			LOTREntitySouthronGoldsmith trader = new LOTREntitySouthronGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Lumber extends LOTRWorldGenStructureBase2 {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
			placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
			placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
			LOTREntitySouthronLumberman trader = new LOTREntitySouthronLumberman(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Mason extends LOTRWorldGenStructureBase2 {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick, 15);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle4, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick3, 13);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntitySouthronMason trader = new LOTREntitySouthronMason(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Miner extends LOTRWorldGenStructureBase2 {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreCopper, 0);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntitySouthronMiner trader = new LOTREntitySouthronMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
