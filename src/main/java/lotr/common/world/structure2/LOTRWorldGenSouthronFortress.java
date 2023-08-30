package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortress extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronFortress(boolean flag) {
		super(flag);
	}

	public LOTREntityNearHaradrimBase createCaptain(World world, Random random) {
		return new LOTREntityNearHaradrimWarlord(world);
	}

	public LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
		return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 15);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -21; i1 <= 21; ++i1) {
				for (int k1 = -15; k1 <= 15; ++k1) {
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
		for (int i1 = -21; i1 <= 21; ++i1) {
			for (int k1 = -15; k1 <= 15; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 <= 17 && k2 <= 10) {
					for (j1 = 1; j1 <= 8; ++j1) {
						setAir(world, i1, j1, k1);
					}
					continue;
				}
				if (i2 >= 15 && k2 >= 9) {
					for (j1 = 1; j1 <= 9; ++j1) {
						setAir(world, i1, j1, k1);
					}
					continue;
				}
				if (i2 > 2 || k1 > -10) {
					continue;
				}
				for (j1 = 1; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("southron_fort");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockAlias("STONE_STAIR", stoneStairBlock);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		addBlockMetaAliasOption("GROUND", 5, Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 1, Blocks.sand, 1);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -5, 8, -13, bannerType, 2);
		placeWallBanner(world, 5, 8, -13, bannerType, 2);
		for (int k1 : new int[]{4, 6, 8}) {
			setBlockAndMetadata(world, -6, 1, k1, bedBlock, 1);
			setBlockAndMetadata(world, -5, 1, k1, bedBlock, 9);
			setBlockAndMetadata(world, -12, 1, k1, bedBlock, 3);
			setBlockAndMetadata(world, -13, 1, k1, bedBlock, 11);
			setBlockAndMetadata(world, 6, 1, k1, bedBlock, 3);
			setBlockAndMetadata(world, 5, 1, k1, bedBlock, 11);
			setBlockAndMetadata(world, 12, 1, k1, bedBlock, 1);
			setBlockAndMetadata(world, 13, 1, k1, bedBlock, 9);
		}
		setBlockAndMetadata(world, 0, 1, 9, Blocks.bed, 3);
		setBlockAndMetadata(world, -1, 1, 9, Blocks.bed, 11);
		setBlockAndMetadata(world, 0, 1, 10, Blocks.bed, 3);
		setBlockAndMetadata(world, -1, 1, 10, Blocks.bed, 11);
		placeWeaponRack(world, -14, 2, -6, 6, getRandomHaradWeapon(random));
		placeWeaponRack(world, -13, 2, -5, 5, getRandomHaradWeapon(random));
		placeWeaponRack(world, -15, 2, -5, 7, getRandomHaradWeapon(random));
		placeWeaponRack(world, -14, 2, -4, 4, getRandomHaradWeapon(random));
		placeWeaponRack(world, -14, 2, -2, 6, getRandomHaradWeapon(random));
		placeWeaponRack(world, -13, 2, -1, 5, getRandomHaradWeapon(random));
		placeWeaponRack(world, -15, 2, -1, 7, getRandomHaradWeapon(random));
		placeWeaponRack(world, -14, 2, 0, 4, getRandomHaradWeapon(random));
		placeBarrel(world, random, 3, 2, 4, 5, LOTRFoods.SOUTHRON_DRINK);
		placeMug(world, random, 3, 2, 5, 1, LOTRFoods.SOUTHRON_DRINK);
		placeChest(world, random, -1, 1, 8, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
		setBlockAndMetadata(world, -5, 1, 1, LOTRMod.commandTable, 0);
		int warriors = 5 + random.nextInt(5);
		for (int l = 0; l < warriors; ++l) {
			LOTREntityNearHaradrimBase warrior = createWarrior(world, random);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 1, 0, 24);
		}
		LOTREntityNearHaradrimBase captain = createCaptain(world, random);
		captain.spawnRidingHorse = false;
		spawnNPCAndSetHome(captain, world, 0, 1, 4, 8);
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		setSpawnClasses(respawner);
		respawner.setCheckRanges(24, -8, 20, 16);
		respawner.setSpawnRanges(12, -4, 6, 24);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void setSpawnClasses(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
	}
}
