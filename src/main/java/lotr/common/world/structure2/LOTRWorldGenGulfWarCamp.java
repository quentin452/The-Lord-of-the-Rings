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

public class LOTRWorldGenGulfWarCamp extends LOTRWorldGenGulfStructure {
	public LOTRWorldGenGulfWarCamp(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 15);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -16; i12 <= 16; ++i12) {
				for (int k1 = -16; k1 <= 16; ++k1) {
					j1 = getTopBlock(world, i12, k1) - 1;
					if (!isSurface(world, i12, j1, k1)) {
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
		for (i1 = -15; i1 <= 15; ++i1) {
			for (int k1 = -15; k1 <= 15; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= -1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (j1 == 0) {
						if (i2 <= 14 && k2 <= 14) {
							if (random.nextBoolean()) {
								setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
							} else {
								int randomGround = random.nextInt(3);
								switch (randomGround) {
									case 0:
										setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
										break;
									case 1:
										setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
										break;
									case 2:
										setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 1);
										break;
									default:
										break;
								}
							}
						} else {
							setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
						}
					} else {
						setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				int airHeight = 6;
				if (i2 <= 4 && k2 <= 4) {
					airHeight = 15;
				}
				for (int j12 = 1; j12 <= airHeight; ++j12) {
					setAir(world, i1, j12, k1);
				}
				if (i2 > 12 || k2 > 12 || random.nextInt(5) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
			}
		}
		loadStrScan("gulf_war_camp");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		for (i1 = -13; i1 <= -9; i1 += 2) {
			setBlockAndMetadata(world, i1, 1, 12, bedBlock, 0);
			setBlockAndMetadata(world, i1, 1, 13, bedBlock, 8);
		}
		for (i1 = 9; i1 <= 13; i1 += 2) {
			setBlockAndMetadata(world, i1, 1, 12, bedBlock, 0);
			setBlockAndMetadata(world, i1, 1, 13, bedBlock, 8);
		}
		placeChest(world, random, -12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
		placeChest(world, random, -10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
		placeChest(world, random, 10, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
		placeChest(world, random, 12, 1, 13, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
		placeChest(world, random, -1, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.GULF_HOUSE);
		placeGulfArmor(world, random, -11, 1, -13, 2);
		placeGulfArmor(world, random, -9, 1, -13, 2);
		placeGulfArmor(world, random, -13, 1, -11, 3);
		placeGulfArmor(world, random, -13, 1, -9, 3);
		placeGulfArmor(world, random, 9, 1, -13, 2);
		placeGulfArmor(world, random, 11, 1, -13, 2);
		placeGulfArmor(world, random, 13, 1, -11, 1);
		placeGulfArmor(world, random, 13, 1, -9, 1);
		placeWeaponRack(world, -8, 2, -9, 6, getRandomGulfWeapon(random));
		placeWeaponRack(world, -9, 2, -8, 7, getRandomGulfWeapon(random));
		placeWeaponRack(world, -7, 2, -8, 5, getRandomGulfWeapon(random));
		placeWeaponRack(world, -8, 2, -7, 4, getRandomGulfWeapon(random));
		placeWeaponRack(world, 8, 2, -9, 6, getRandomGulfWeapon(random));
		placeWeaponRack(world, 7, 2, -8, 7, getRandomGulfWeapon(random));
		placeWeaponRack(world, 9, 2, -8, 5, getRandomGulfWeapon(random));
		placeWeaponRack(world, 8, 2, -7, 4, getRandomGulfWeapon(random));
		placeSkull(world, random, -12, 3, -2);
		placeSkull(world, random, -12, 3, 2);
		placeWeaponRack(world, 11, 2, -4, 7, new ItemStack(LOTRMod.nearHaradBow));
		placeWeaponRack(world, 11, 2, 4, 7, new ItemStack(LOTRMod.nearHaradBow));
		placeBarrel(world, random, -13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
		placeBarrel(world, random, 13, 2, 9, 3, LOTRFoods.GULF_HARAD_DRINK);
		placeWallBanner(world, 0, 6, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, 2, 5, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, 4, 4, -15, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
		placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
		placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
		placeWallBanner(world, -5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
		placeWallBanner(world, -5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 3);
		placeWallBanner(world, 5, 13, -5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
		placeWallBanner(world, 5, 13, 5, LOTRItemBanner.BannerType.HARAD_GULF, 1);
		for (int i13 : new int[]{-2, 2}) {
			j1 = 1;
			int k1 = 12;
			LOTREntityHorse horse = new LOTREntityHorse(world);
			spawnNPCAndSetHome(horse, world, i13, j1, k1, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
			leashEntityTo(horse, world, i13, j1, k1);
		}
		LOTREntityGulfHaradWarlord warlord = new LOTREntityGulfHaradWarlord(world);
		warlord.spawnRidingHorse = false;
		spawnNPCAndSetHome(warlord, world, 0, 9, -3, 6);
		setBlockAndMetadata(world, 0, 9, 3, LOTRMod.commandTable, 0);
		int warriors = 6;
		for (int l = 0; l < warriors; ++l) {
			LOTREntityGulfHaradWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityGulfHaradArcher(world) : new LOTREntityGulfHaradWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 1, -1, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
		respawner.setCheckRanges(32, -8, 12, 24);
		respawner.setSpawnRanges(24, -4, 6, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void placeGulfArmor(World world, Random random, int i, int j, int k, int meta) {
		ItemStack[] armor = random.nextInt(3) != 0 ? new ItemStack[]{null, null, null, null} : new ItemStack[]{new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad)};
		placeArmorStand(world, i, j, k, meta, armor);
	}
}
