package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfBazaar extends LOTRWorldGenGulfStructure {
	public static Class[] stalls = {Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Miner.class, Goldsmith.class, Lumber.class, Hunter.class, Blacksmith.class, Farmer.class};

	public LOTRWorldGenGulfBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -17; i1 <= 17; ++i1) {
				for (int k1 = -12; k1 <= 8; ++k1) {
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
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -17; i1 <= 17; ++i1) {
			for (int k1 = -12; k1 <= 8; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 >= 5 && i2 <= 9 && k2 >= 10) {
					for (j1 = 1; j1 <= 5; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if ((k1 < -6 || k1 > -5 || i2 > 4) && (k1 != -5 || i2 < 10 || i2 > 12) && (k2 != 4 || i2 > 14) && (k2 < 2 || k2 > 3 || i2 > 15) && (k2 > 1 || i2 > 16) && (k1 != 5 || i2 > 12) && (k1 != 6 || i2 > 9) && (k1 != 7 || i2 > 4)) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("gulf_bazaar");
		addBlockMetaAliasOption("BRICK", 6, brickBlock, brickMeta);
		addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
		addBlockMetaAliasOption("BRICK", 8, Blocks.sandstone, 0);
		addBlockAliasOption("BRICK_STAIR", 6, brickStairBlock);
		addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
		addBlockAliasOption("BRICK_STAIR", 8, Blocks.sandstone_stairs);
		addBlockMetaAliasOption("BRICK_WALL", 6, brickWallBlock, brickWallMeta);
		addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
		addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wallStoneV, 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM2", beam2Block, beam2Meta);
		associateBlockMetaAlias("BEAM2|4", beam2Block, beam2Meta | 4);
		associateBlockMetaAlias("BEAM2|8", beam2Block, beam2Meta | 8);
		addBlockMetaAliasOption("GROUND", 10, Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 2, LOTRMod.dirtPath, 0);
		associateBlockMetaAlias("WOOL", Blocks.wool, 14);
		associateBlockMetaAlias("CARPET", Blocks.carpet, 14);
		associateBlockMetaAlias("WOOL2", Blocks.wool, 15);
		associateBlockMetaAlias("CARPET2", Blocks.carpet, 15);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeAnimalJar(world, -5, 4, -2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeAnimalJar(world, -7, 5, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeWallBanner(world, -3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, 3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
		placeWallBanner(world, -8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
		placeWallBanner(world, -6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
		placeWallBanner(world, 7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, 7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
		placeWallBanner(world, 6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
		placeWallBanner(world, 8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
		for (int i1 : new int[]{-7, 7}) {
			j1 = 1;
			int k1 = -11;
			LOTREntityGulfHaradWarrior guard = new LOTREntityGulfHaradWarrior(world);
			guard.spawnRidingHorse = false;
			spawnNPCAndSetHome(guard, world, i1, j1, k1, 4);
		}
		List<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
		while (stallClasses.size() > 5) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			LOTRWorldGenStructureBase2 stall4 = (LOTRWorldGenStructureBase2) stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -9, 1, 0, 3);
			generateSubstructure(stall1, world, random, -5, 1, 5, 1);
			generateSubstructure(stall2, world, random, 0, 1, 6, 1);
			generateSubstructure(stall3, world, random, 8, 1, 2, 3);
			generateSubstructure(stall4, world, random, 11, 1, -2, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static class Baker extends LOTRWorldGenStructureBase2 {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.furnace, 2);
			setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
			placePlate_item(world, random, 1, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			setBlockAndMetadata(world, 3, 2, 2, LOTRMod.bananaCake, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.rollingPin));
			LOTREntityGulfBaker trader = new LOTREntityGulfBaker(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, Blocks.anvil, 3);
			placeArmorStand(world, 1, 1, 2, 0, new ItemStack[]{null, new ItemStack(LOTRMod.bodyGulfHarad), null, null});
			placeWeaponRack(world, 0, 2, 2, 1, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
			placeWeaponRack(world, 3, 2, 2, 3, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
			LOTREntityGulfBlacksmith trader = new LOTREntityGulfBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.barrel, 3);
			placeMug(world, random, 1, 2, 0, 0, LOTRFoods.GULF_HARAD_DRINK);
			placeMug(world, random, 0, 2, 2, 3, LOTRFoods.GULF_HARAD_DRINK);
			placeMug(world, random, 3, 2, 1, 1, LOTRFoods.GULF_HARAD_DRINK);
			placeFlowerPot(world, 2, 2, 3, getRandomFlower(world, random));
			LOTREntityGulfBrewer trader = new LOTREntityGulfBrewer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
			placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3)), true);
			placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3)), true);
			placeSkull(world, random, 2, 2, 3);
			LOTREntityGulfButcher trader = new LOTREntityGulfButcher(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 2, 3, Blocks.hay_block, 0);
			placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			LOTREntityGulfFarmer trader = new LOTREntityGulfFarmer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placeWeaponRack(world, 1, 2, 3, 0, new ItemStack(Items.fishing_rod));
			LOTREntityGulfFishmonger trader = new LOTREntityGulfFishmonger(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 2, 2, LOTRMod.birdCage, 3);
			setBlockAndMetadata(world, 2, 3, 2, LOTRMod.goldBars, 0);
			placeFlowerPot(world, 0, 2, 1, getRandomFlower(world, random));
			LOTREntityGulfGoldsmith trader = new LOTREntityGulfGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	public static class Hunter extends LOTRWorldGenStructureBase2 {
		public Hunter(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
			setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
			placeSkull(world, random, 2, 3, 2);
			placeSkull(world, random, 3, 2, 2);
			spawnItemFrame(world, 2, 2, 2, 2, new ItemStack(LOTRMod.lionFur));
			placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.spearHarad));
			LOTREntityGulfHunter trader = new LOTREntityGulfHunter(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
			setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
			placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.sapling, 1, 4));
			placeFlowerPot(world, 3, 2, 1, new ItemStack(LOTRMod.sapling8, 1, 3));
			LOTREntityGulfLumberman trader = new LOTREntityGulfLumberman(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, LOTRMod.brick, 15);
			setBlockAndMetadata(world, 2, 2, 2, LOTRMod.brick3, 13);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntityGulfMason trader = new LOTREntityGulfMason(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
			setBlockAndMetadata(world, 2, 1, 2, LOTRMod.oreTin, 0);
			setBlockAndMetadata(world, 2, 2, 2, LOTRMod.oreCopper, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.pickaxeBronze));
			LOTREntityGulfMiner trader = new LOTREntityGulfMiner(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

}
