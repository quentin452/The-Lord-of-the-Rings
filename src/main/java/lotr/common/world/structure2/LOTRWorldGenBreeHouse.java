package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeHouse extends LOTRWorldGenBreeStructure {
	public LOTRWorldGenBreeHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k1;
		int j2;
		int j1;
		int i12;
		int randPath;
		int j12;
		int i13;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i13 = -7; i13 <= 8; ++i13) {
				for (k12 = -8; k12 <= 5; ++k12) {
					j12 = getTopBlock(world, i13, k12) - 1;
					if (isSurface(world, i13, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i13 = -3; i13 <= 8; ++i13) {
			for (k12 = -5; k12 <= 3; ++k12) {
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k12);
				}
			}
		}
		for (i13 = -2; i13 <= 2; ++i13) {
			for (k12 = -8; k12 <= -6; ++k12) {
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k12);
				}
			}
		}
		for (i13 = 2; i13 <= 7; ++i13) {
			for (k12 = 3; k12 <= 5; ++k12) {
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k12);
				}
			}
		}
		for (i13 = -7; i13 <= -3; ++i13) {
			for (k12 = -4; k12 <= 2; ++k12) {
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k12);
				}
			}
		}
		loadStrScan("bree_house");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		addBlockMetaAliasOption("PATH", 5, Blocks.grass, 0);
		addBlockMetaAliasOption("PATH", 5, Blocks.dirt, 1);
		addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
		addBlockMetaAliasOption("PATH", 5, Blocks.cobblestone, 0);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		for (i1 = 3; i1 <= 6; ++i1) {
			for (int step = 0; step < 12 && !isOpaque(world, i1, j1 = -1 - step, k1 = 6 + step); ++step) {
				randPath = random.nextInt(4);
				switch (randPath) {
					case 0:
						setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
						break;
					case 1:
						setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 1);
						break;
					case 2:
						setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
						break;
					case 3:
						setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
						break;
					default:
						break;
				}
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (int step = 0; step < 12 && !isOpaque(world, i12 = -5, j1 = -step, k1 = -5 - step); ++step) {
			randPath = random.nextInt(4);
			switch (randPath) {
				case 0:
					setBlockAndMetadata(world, i12, j1, k1, Blocks.grass, 0);
					break;
				case 1:
					setBlockAndMetadata(world, i12, j1, k1, Blocks.dirt, 1);
					break;
				case 2:
					setBlockAndMetadata(world, i12, j1, k1, LOTRMod.dirtPath, 0);
					break;
				case 3:
					setBlockAndMetadata(world, i12, j1, k1, Blocks.cobblestone, 0);
					break;
				default:
					break;
			}
			setGrassToDirt(world, i12, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i12, j2, k1, Blocks.dirt, 0);
				setGrassToDirt(world, i12, j2 - 1, k1);
				--j2;
			}
		}
		for (i1 = -6; i1 <= -3; ++i1) {
			for (int k13 = -3; k13 <= 1; ++k13) {
				j1 = 1;
				if (getBlock(world, i1, 0, k13) != Blocks.grass || random.nextInt(4) != 0) {
					continue;
				}
				plantFlower(world, random, i1, j1, k13);
			}
		}
		placeRandomFlowerPot(world, random, 6, 1, 3);
		placeRandomFlowerPot(world, random, 3, 1, 3);
		placeRandomFlowerPot(world, random, -1, 5, -1);
		placeRandomFlowerPot(world, random, 2, 5, 1);
		plantFlower(world, random, 0, 2, 3);
		plantFlower(world, random, 8, 6, -1);
		placeChest(world, random, -1, 1, 1, 4, LOTRChestContents.BREE_HOUSE);
		placeChest(world, random, 1, 5, 1, 2, LOTRChestContents.BREE_HOUSE);
		placeMug(world, random, 3, 2, -2, 3, LOTRFoods.BREE_DRINK);
		placePlateWithCertainty(world, random, 3, 2, -3, LOTRMod.plateBlock, LOTRFoods.BREE);
		setBlockAndMetadata(world, 0, 5, 0, bedBlock, 3);
		setBlockAndMetadata(world, -1, 5, 0, bedBlock, 11);
		if (random.nextBoolean()) {
			spawnItemFrame(world, 2, 3, 0, 3, new ItemStack(Items.clock));
		}
		String[] breeNames = LOTRNames.getBreeCoupleAndHomeNames(random);
		LOTREntityBreeMan man = new LOTREntityBreeMan(world);
		man.familyInfo.setMale(true);
		man.familyInfo.setName(breeNames[0]);
		spawnNPCAndSetHome(man, world, 0, 1, 0, 16);
		LOTREntityBreeMan woman = new LOTREntityBreeMan(world);
		woman.familyInfo.setMale(false);
		woman.familyInfo.setName(breeNames[1]);
		spawnNPCAndSetHome(woman, world, 0, 1, 0, 16);
		placeSign(world, 2, 2, -8, Blocks.standing_sign, 9, new String[]{"", breeNames[2], breeNames[3], ""});
		return true;
	}
}
