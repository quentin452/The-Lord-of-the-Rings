package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.entity.npc.LOTREntityDunlendingArcher;
import lotr.common.entity.npc.LOTREntityDunlendingWarlord;
import lotr.common.entity.npc.LOTREntityDunlendingWarrior;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDunlandHillFort extends LOTRWorldGenDunlandStructure {
	public LOTRWorldGenDunlandHillFort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -12; i12 <= 12; ++i12) {
				for (int k1 = -12; k1 <= 12; ++k1) {
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
		for (i1 = -11; i1 <= 11; ++i1) {
			for (int k1 = -11; k1 <= 11; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 <= 8 && k2 <= 8 || i2 <= 1 && k1 < 0) {
					int randomGround = random.nextInt(3);
					switch (randomGround) {
						case 0:
							setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
							break;
						case 1:
							setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
							break;
						case 2:
							setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
							break;
						default:
							break;
					}
					if ((i2 > 3 || k1 < -3 || k1 > 2) && random.nextInt(5) == 0) {
						setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
					}
				} else {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.cobblestone, 0);
				}
				setGrassToDirt(world, i1, -1, k1);
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		loadStrScan("dunland_fort");
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("BARS", barsBlock, barsMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, 8, 1, 5, bedBlock, 9);
		setBlockAndMetadata(world, 7, 1, 5, bedBlock, 1);
		setBlockAndMetadata(world, 7, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, 7, 1, 8, bedBlock, 8);
		setBlockAndMetadata(world, 5, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, 5, 1, 8, bedBlock, 8);
		placeChest(world, random, 5, 1, 5, LOTRMod.chestBasket, 3, LOTRChestContents.DUNLENDING_HOUSE);
		placeChest(world, random, -4, 1, 8, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
		placeChest(world, random, 6, 1, -8, Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
		placeChest(world, random, 5, 1, -8, Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
		for (i1 = -6; i1 <= -5; ++i1) {
			int j12 = 1;
			int k1 = 8;
			if (random.nextBoolean()) {
				placeArmorStand(world, i1, j12, k1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetDunlending), new ItemStack(LOTRMod.bodyDunlending), new ItemStack(LOTRMod.legsDunlending), new ItemStack(LOTRMod.bootsDunlending)});
				continue;
			}
			placeArmorStand(world, i1, j12, k1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.bootsFur)});
		}
		placeWeaponRack(world, -7, 2, -3, 5, getRandomDunlandWeapon(random));
		placeBarrel(world, random, 8, 2, 7, 2, LOTRFoods.DUNLENDING_DRINK);
		placeSkull(world, random, -2, 7, -11);
		placeSkull(world, random, 2, 7, -11);
		placeSkull(world, random, -11, 7, 2);
		placeSkull(world, random, 3, 7, 8);
		placeSkull(world, random, 11, 8, -3);
		placeAnimalJar(world, 8, 2, -6, LOTRMod.birdCageWood, 0, new LOTREntityCrebain(world));
		setBlockAndMetadata(world, 6, 1, -3, LOTRMod.commandTable, 0);
		placeWallBanner(world, -2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
		placeWallBanner(world, 2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
		placeWallBanner(world, -8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 1);
		placeWallBanner(world, 8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 3);
		LOTREntityBearRug rug = new LOTREntityBearRug(world);
		LOTREntityBear.BearType[] bearTypes = {LOTREntityBear.BearType.LIGHT, LOTREntityBear.BearType.DARK, LOTREntityBear.BearType.BLACK};
		rug.setRugType(bearTypes[random.nextInt(bearTypes.length)]);
		placeRug(rug, world, -5, 1, -4, -45.0f);
		LOTREntityDunlendingWarlord warlord = new LOTREntityDunlendingWarlord(world);
		spawnNPCAndSetHome(warlord, world, 0, 1, 2, 8);
		int warriors = 6;
		for (int l = 0; l < warriors; ++l) {
			LOTREntityDunlendingWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityDunlendingArcher(world) : new LOTREntityDunlendingWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 1, 2, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClasses(LOTREntityDunlendingWarrior.class, LOTREntityDunlendingArcher.class);
		respawner.setCheckRanges(20, -8, 12, 12);
		respawner.setSpawnRanges(6, -1, 4, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}
