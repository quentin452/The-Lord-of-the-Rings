package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDorwinionTent extends LOTRWorldGenStructureBase2 {
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block floorBlock;
	public int floorMeta;
	public Block wool1Block;
	public int wool1Meta;
	public Block clay1Block;
	public int clay1Meta;
	public Block clay1SlabBlock;
	public int clay1SlabMeta;
	public Block clay1StairBlock;
	public Block wool2Block;
	public int wool2Meta;
	public Block clay2Block;
	public int clay2Meta;
	public Block clay2SlabBlock;
	public int clay2SlabMeta;
	public Block clay2StairBlock;

	public LOTRWorldGenDorwinionTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 == 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, floorBlock, floorMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 1; j1 <= 2; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
				} else if (i2 == 2) {
					for (j1 = 1; j1 <= 2; ++j1) {
						if (k1 % 2 == 0) {
							setBlockAndMetadata(world, i1, j1, k1, wool1Block, wool1Meta);
							continue;
						}
						setBlockAndMetadata(world, i1, j1, k1, wool2Block, wool2Meta);
					}
				}
				if (i2 != 0 || k2 != 2) {
					continue;
				}
				for (j1 = 1; j1 <= 3; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
				}
			}
		}
		for (int k12 = -2; k12 <= 2; ++k12) {
			if (k12 % 2 == 0) {
				setBlockAndMetadata(world, -2, 3, k12, clay1StairBlock, 1);
				setBlockAndMetadata(world, 2, 3, k12, clay1StairBlock, 0);
				setBlockAndMetadata(world, -1, 3, k12, clay1StairBlock, 4);
				setBlockAndMetadata(world, 1, 3, k12, clay1StairBlock, 5);
				setBlockAndMetadata(world, -1, 4, k12, clay1StairBlock, 1);
				setBlockAndMetadata(world, 1, 4, k12, clay1StairBlock, 0);
				setBlockAndMetadata(world, 0, 4, k12, clay1Block, clay1Meta);
				setBlockAndMetadata(world, 0, 5, k12, clay1SlabBlock, clay1SlabMeta);
				continue;
			}
			setBlockAndMetadata(world, -2, 3, k12, clay2StairBlock, 1);
			setBlockAndMetadata(world, 2, 3, k12, clay2StairBlock, 0);
			setBlockAndMetadata(world, -1, 3, k12, clay2StairBlock, 4);
			setBlockAndMetadata(world, 1, 3, k12, clay2StairBlock, 5);
			setBlockAndMetadata(world, -1, 4, k12, clay2StairBlock, 1);
			setBlockAndMetadata(world, 1, 4, k12, clay2StairBlock, 0);
			setBlockAndMetadata(world, 0, 4, k12, clay2Block, clay2Meta);
			setBlockAndMetadata(world, 0, 5, k12, clay2SlabBlock, clay2SlabMeta);
		}
		if (random.nextBoolean()) {
			placeChest(world, random, -1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
			setBlockAndMetadata(world, 1, 2, 0, Blocks.torch, 1);
		} else {
			placeChest(world, random, 1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
			setBlockAndMetadata(world, -1, 2, 0, Blocks.torch, 2);
		}
		LOTREntityDorwinionGuard guard = new LOTREntityDorwinionGuard(world);
		spawnNPCAndSetHome(guard, world, 0, 1, 0, 16);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomWood = random.nextInt(3);
		switch (randomWood) {
			case 0:
				woodBeamBlock = LOTRMod.woodBeamV1;
				woodBeamMeta = 0;
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				break;
			case 1:
				woodBeamBlock = LOTRMod.woodBeam6;
				woodBeamMeta = 2;
				plankBlock = LOTRMod.planks2;
				plankMeta = 10;
				plankSlabBlock = LOTRMod.woodSlabSingle4;
				plankSlabMeta = 2;
				plankStairBlock = LOTRMod.stairsCypress;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 10;
				break;
			case 2:
				woodBeamBlock = LOTRMod.woodBeam6;
				woodBeamMeta = 3;
				plankBlock = LOTRMod.planks2;
				plankMeta = 11;
				plankSlabBlock = LOTRMod.woodSlabSingle4;
				plankSlabMeta = 3;
				plankStairBlock = LOTRMod.stairsOlive;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 11;
				break;
			default:
				break;
		}
		int randomFloor = random.nextInt(4);
		switch (randomFloor) {
			case 0:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 2;
				break;
			case 1:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 3;
				break;
			case 2:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 14;
				break;
			case 3:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 10;
				break;
			default:
				break;
		}
		int randomWool1 = random.nextInt(3);
		switch (randomWool1) {
			case 0:
				wool1Block = Blocks.wool;
				wool1Meta = 10;
				clay1Block = LOTRMod.clayTileDyed;
				clay1Meta = 10;
				clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
				clay1SlabMeta = 2;
				clay1StairBlock = LOTRMod.stairsClayTileDyedPurple;
				break;
			case 1:
				wool1Block = Blocks.wool;
				wool1Meta = 2;
				clay1Block = LOTRMod.clayTileDyed;
				clay1Meta = 2;
				clay1SlabBlock = LOTRMod.slabClayTileDyedSingle;
				clay1SlabMeta = 2;
				clay1StairBlock = LOTRMod.stairsClayTileDyedMagenta;
				break;
			case 2:
				wool1Block = Blocks.wool;
				wool1Meta = 14;
				clay1Block = LOTRMod.clayTileDyed;
				clay1Meta = 14;
				clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
				clay1SlabMeta = 6;
				clay1StairBlock = LOTRMod.stairsClayTileDyedRed;
				break;
			default:
				break;
		}
		int randomWool2 = random.nextInt(2);
		if (randomWool2 == 0) {
			wool2Block = Blocks.wool;
			wool2Meta = 4;
			clay2Block = LOTRMod.clayTileDyed;
			clay2Meta = 4;
			clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
			clay2SlabMeta = 4;
			clay2StairBlock = LOTRMod.stairsClayTileDyedYellow;
		} else {
			wool2Block = Blocks.wool;
			wool2Meta = 0;
			clay2Block = LOTRMod.clayTileDyed;
			clay2Meta = 0;
			clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
			clay2SlabMeta = 0;
			clay2StairBlock = LOTRMod.stairsClayTileDyedWhite;
		}
	}
}
