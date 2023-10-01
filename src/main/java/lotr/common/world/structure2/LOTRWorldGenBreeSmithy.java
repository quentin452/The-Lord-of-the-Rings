package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeBlacksmith;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeSmithy extends LOTRWorldGenBreeStructure {
	public LOTRWorldGenBreeSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 11);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -5; i1 <= 5; ++i1) {
				for (k1 = -11; k1 <= 5; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -10; k1 <= -6; ++k1) {
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("bree_smithy");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockAlias("BRICK2_STAIR", brick2StairBlock);
		associateBlockMetaAlias("BRICK2_WALL", brick2WallBlock, brick2WallMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
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
		addBlockMetaAliasOption("PATH", 5, Blocks.dirt, 1);
		addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
		addBlockMetaAliasOption("PATH", 5, Blocks.cobblestone, 0);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, -2, 1, 3, bedBlock, 3);
		setBlockAndMetadata(world, -3, 1, 3, bedBlock, 11);
		placeChest(world, random, 3, 1, 0, 5, LOTRChestContents.BREE_HOUSE);
		placeArmorStand(world, 3, 1, -8, 1, getDisplayArmorOrNull(world, random));
		placeArmorStand(world, 3, 1, -6, 1, getDisplayArmorOrNull(world, random));
		placeArmorStand(world, 1, 1, 1, 3, getDisplayArmorOrNull(world, random));
		placeWeaponRack(world, 1, 2, 3, 2, getDisplayWeaponOrNull(random));
		placeWeaponRack(world, 3, 2, 3, 2, getDisplayWeaponOrNull(random));
		placeWeaponRack(world, 0, 3, -5, 6, getRandomBreeWeapon(random));
		placeWeaponRack(world, -2, 3, -2, 4, getDisplayWeaponOrNull(random));
		placeMug(world, random, -2, 2, 1, 2, LOTRFoods.BREE_DRINK);
		placeBarrel(world, random, -3, 2, 1, 3, LOTRFoods.BREE_DRINK);
		placePlateWithCertainty(world, random, 0, 2, -3, LOTRMod.ceramicPlateBlock, LOTRFoods.BREE);
		LOTREntityBreeBlacksmith blacksmith = new LOTREntityBreeBlacksmith(world);
		spawnNPCAndSetHome(blacksmith, world, 0, 1, -1, 8);
		return true;
	}

	public ItemStack[] getDisplayArmorOrNull(World world, Random random) {
		if (random.nextBoolean()) {
			LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
			armorGuard.onSpawnWithEgg(null);
			return new ItemStack[]{armorGuard.getEquipmentInSlot(4), armorGuard.getEquipmentInSlot(3), null, null};
		}
		return null;
	}

	public ItemStack getDisplayWeaponOrNull(Random random) {
		return random.nextBoolean() ? getRandomBreeWeapon(random) : null;
	}
}
