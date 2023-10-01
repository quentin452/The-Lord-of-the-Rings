package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronTower extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronTower(boolean flag) {
		super(flag);
	}

	public LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
		return random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				for (j1 = 1; j1 <= 15; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("southron_tower");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeChest(world, random, -1, 1, -1, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
		placeMug(world, random, -1, 2, 1, 0, LOTRFoods.SOUTHRON_DRINK);
		placeBarrel(world, random, 1, 2, 1, 2, LOTRFoods.SOUTHRON_DRINK);
		placeWeaponRack(world, -1, 8, 0, 5, getRandomHaradWeapon(random));
		placeWeaponRack(world, 1, 8, 0, 7, getRandomHaradWeapon(random));
		placeWallBanner(world, 0, 14, -3, bannerType, 2);
		placeWallBanner(world, -3, 14, 0, bannerType, 3);
		placeWallBanner(world, 0, 14, 3, bannerType, 0);
		placeWallBanner(world, 3, 14, 0, bannerType, 1);
		int warriors = 2;
		for (int l = 0; l < warriors; ++l) {
			LOTREntityNearHaradrimBase warrior = createWarrior(world, random);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 15, 0, 8);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		setSpawnClasses(respawner);
		respawner.setCheckRanges(8, -4, 20, 8);
		respawner.setSpawnRanges(2, -1, 16, 8);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void setSpawnClasses(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
	}
}
