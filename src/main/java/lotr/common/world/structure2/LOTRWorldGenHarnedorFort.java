package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorFort extends LOTRWorldGenHarnedorStructure {
	public LOTRWorldGenHarnedorFort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 12);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -15; i12 <= 15; ++i12) {
				for (int k1 = -15; k1 <= 15; ++k1) {
					int j12 = getTopBlock(world, i12, k1) - 1;
					if (!isSurface(world, i12, j12, k1)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -15; i1 <= 15; ++i1) {
			for (int k1 = -15; k1 <= 15; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				int bedRegion = i2 <= 3 && k1 >= 5 && k1 <= 9 || i2 <= 2 && k1 == 4 || i2 <= 1 && k1 == 3 ? 1 : 0;
				int airHeight = 7;
				for (j1 = 0; j1 <= airHeight; ++j1) {
					setAir(world, i1, j1, k1);
				}
				for (j1 = 0; (j1 >= -1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (bedRegion != 0 && j1 == 0) {
						continue;
					}
					if (j1 == 0) {
						int randomGround;
						if (i2 <= 11 && k2 <= 11 && random.nextBoolean()) {
							setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
						} else {
							randomGround = random.nextInt(3);
							switch (randomGround) {
								case 0:
									setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
									break;
								case 1:
									setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
									break;
								case 2:
									setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 0);
									break;
								default:
									break;
							}
						}
					} else {
						setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (bedRegion != 0 || i2 > 10 || k2 > 10 || random.nextInt(5) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
			}
		}
		loadStrScan("harnedor_fort");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("PLANK2_SLAB", plank2SlabBlock, plank2SlabMeta);
		associateBlockMetaAlias("PLANK2_SLAB_INV", plank2SlabBlock, plank2SlabMeta | 8);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, 2, 0, 8, bedBlock, 0);
		setBlockAndMetadata(world, 2, 0, 9, bedBlock, 8);
		setBlockAndMetadata(world, 10, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, 10, 1, 8, bedBlock, 8);
		setBlockAndMetadata(world, 7, 1, 10, bedBlock, 1);
		setBlockAndMetadata(world, 8, 1, 10, bedBlock, 9);
		setBlockAndMetadata(world, -10, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, -10, 1, 8, bedBlock, 8);
		setBlockAndMetadata(world, -7, 1, 10, bedBlock, 3);
		setBlockAndMetadata(world, -8, 1, 10, bedBlock, 11);
		placeChest(world, random, 0, 0, 7, LOTRMod.chestBasket, 3, LOTRChestContents.HARNENNOR_HOUSE);
		placeChest(world, random, -9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
		placeChest(world, random, 9, 1, 9, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
		for (i1 = -2; i1 <= 0; ++i1) {
			int j13 = 1;
			int k1 = 9;
			if (random.nextBoolean()) {
				placePlate(world, random, i1, j13, k1, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
				continue;
			}
			placeMug(world, random, i1, j13, k1, 0, LOTRFoods.HARNEDOR_DRINK);
		}
		placeWeaponRack(world, 4, 2, -1, 6, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, 5, 2, 0, 5, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, 4, 2, 1, 4, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, 3, 2, 0, 7, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, -4, 2, -1, 6, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, -3, 2, 0, 5, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, -4, 2, 1, 4, getRandomHarnedorWeapon(random));
		placeWeaponRack(world, -5, 2, 0, 7, getRandomHarnedorWeapon(random));
		placeHarnedorArmor(world, random, 9, 1, -3, 1);
		placeHarnedorArmor(world, random, 9, 1, 0, 1);
		placeHarnedorArmor(world, random, 9, 1, 3, 1);
		placeSkull(world, random, -8, 3, -4);
		placeSkull(world, random, -8, 3, 2);
		placeSkull(world, random, -10, 6, 0);
		placeSkull(world, random, 10, 6, 0);
		placeSkull(world, random, -13, 8, -15);
		placeSkull(world, random, 13, 8, -15);
		placeSkull(world, random, -13, 8, 15);
		placeSkull(world, random, 13, 8, 15);
		placeWallBanner(world, 0, 5, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
		placeWallBanner(world, -3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
		placeWallBanner(world, 3, 4, -13, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
		placeWallBanner(world, 0, 6, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
		setBlockAndMetadata(world, 7, 1, 5, LOTRMod.commandTable, 0);
		LOTREntityHarnedorWarlord warlord = new LOTREntityHarnedorWarlord(world);
		warlord.spawnRidingHorse = false;
		spawnNPCAndSetHome(warlord, world, 0, 3, 7, 4);
		int warriors = 4 + random.nextInt(4);
		for (int l = 0; l < warriors; ++l) {
			LOTREntityHarnedorWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityHarnedorArcher(world) : new LOTREntityHarnedorWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 1, 0, 16);
		}
		for (int i13 : new int[]{-4, 4}) {
			j1 = 1;
			int k1 = -6;
			LOTREntityHorse horse = new LOTREntityHorse(world);
			spawnNPCAndSetHome(horse, world, i13, j1, k1, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
			leashEntityTo(horse, world, i13, j1, k1);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
		respawner.setCheckRanges(16, -8, 12, 12);
		respawner.setSpawnRanges(8, -2, 2, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void placeHarnedorArmor(World world, Random random, int i, int j, int k, int meta) {
		ItemStack[] armor = random.nextInt(3) != 0 ? new ItemStack[]{null, null, null, null} : new ItemStack[]{new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor)};
		placeArmorStand(world, i, j, k, meta, armor);
	}
}
