package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNomadChieftainTent extends LOTRWorldGenNomadStructure {
	public LOTRWorldGenNomadChieftainTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -12; i1 <= 12; ++i1) {
				for (int k1 = -8; k1 <= 8; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -12; i1 <= 12; ++i1) {
			for (int k1 = -8; k1 <= 8; ++k1) {
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_tent_chieftain");
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("CARPET2", carpet2Block, carpet2Meta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -6, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -6, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, -5, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -5, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, 5, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, 5, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, 6, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, 6, 1, 5, bedBlock, 8);
		placeChest(world, random, -11, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.NOMAD_TENT);
		placeChest(world, random, 11, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.NOMAD_TENT);
		placeWeaponRack(world, -5, 3, -5, 4, getRandomUmbarWeapon(random));
		placeWeaponRack(world, 5, 3, -5, 4, getRandomUmbarWeapon(random));
		placeMug(world, random, -4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
		placePlateWithCertainty(world, random, -6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
		placePlateWithCertainty(world, random, 6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
		placeMug(world, random, 4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
		placeWallBanner(world, 0, 3, 7, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
		placeWallBanner(world, -5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
		placeWallBanner(world, 5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
		placeWallBanner(world, -12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 1);
		placeWallBanner(world, 12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 3);
		placeWallBanner(world, 0, 5, -8, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
		setBlockAndMetadata(world, -1, 4, -9, Blocks.skull, 2);
		setBlockAndMetadata(world, 1, 4, -9, Blocks.skull, 2);
		LOTREntityNomadChieftain chief = new LOTREntityNomadChieftain(world);
		spawnNPCAndSetHome(chief, world, 0, 1, 0, 8);
		int warriors = 2 + random.nextInt(2);
		for (int l = 0; l < warriors; ++l) {
			LOTREntityNomadWarrior warrior = new LOTREntityNomadWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, random.nextBoolean() ? -6 : 6, 1, 0, 8);
		}
		for (int i1 : new int[]{-5, 5}) {
			int j12 = 1;
			int k1 = -8;
			if (!isOpaque(world, i1, 0, k1) || !isAir(world, i1, j12, k1)) {
				continue;
			}
			setBlockAndMetadata(world, i1, j12, k1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i1, j12 + 1, k1, fenceBlock, fenceMeta);
			LOTREntityCamel camel = new LOTREntityCamel(world);
			spawnNPCAndSetHome(camel, world, i1, j12, k1, 0);
			camel.saddleMountForWorldGen();
			camel.detachHome();
			camel.setNomadChestAndCarpet();
			leashEntityTo(camel, world, i1, j12, k1);
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = LOTRMod.lionBed;
	}
}
