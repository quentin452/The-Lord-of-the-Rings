package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTower extends LOTRWorldGenHarnedorStructure {
	public LOTRWorldGenHarnedorTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k1 = -3; k1 <= 3; ++k1) {
				for (int j1 = 6; j1 <= 16; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("harnedor_tower");
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		placeSkull(world, random, -3, 5, -3);
		placeSkull(world, random, 3, 6, -3);
		placeSkull(world, random, 3, 6, 3);
		placeSkull(world, random, -3, 7, -2);
		placeSkull(world, random, -3, 7, 2);
		placeSkull(world, random, 0, 8, 3);
		placeSkull(world, random, -3, 10, 3);
		placeSkull(world, random, -3, 12, -3);
		placeSkull(world, random, 3, 13, 2);
		placeChest(world, random, -2, 11, 2, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
		int warriors = 1 + random.nextInt(2);
		for (int l = 0; l < warriors; ++l) {
			LOTREntityHarnedorWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 13, 0, 8);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
		respawner.setCheckRanges(6, -16, 4, 4);
		respawner.setSpawnRanges(2, -1, 1, 8);
		placeNPCRespawner(respawner, world, 0, 13, 0);
		return true;
	}
}
