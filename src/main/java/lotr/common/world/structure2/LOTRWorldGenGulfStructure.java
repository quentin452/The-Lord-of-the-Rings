package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public abstract class LOTRWorldGenGulfStructure extends LOTRWorldGenStructureBase2 {
	public Block trapdoorBlock;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block woodBlock;
	public int woodMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block doorBlock;
	public Block beamBlock;
	public int beamMeta;
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block beam2Block;
	public int beam2Meta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block flagBlock;
	public int flagMeta;
	public Block boneBlock;
	public int boneMeta;
	public Block boneWallBlock;
	public int boneWallMeta;
	public Block bedBlock;

	protected LOTRWorldGenGulfStructure(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBrick() {
		return true;
	}

	public ItemStack getRandomGulfWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordGulfHarad), new ItemStack(LOTRMod.swordGulfHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (canUseRedBrick() && random.nextInt(3) == 0) {
			brickBlock = LOTRMod.brick3;
			brickMeta = 13;
			brickSlabBlock = LOTRMod.slabSingle7;
			brickSlabMeta = 2;
			brickStairBlock = LOTRMod.stairsNearHaradBrickRed;
			brickWallBlock = LOTRMod.wall3;
			brickWallMeta = 4;
		} else {
			brickBlock = LOTRMod.brick;
			brickMeta = 15;
			brickSlabBlock = LOTRMod.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = LOTRMod.stairsNearHaradBrick;
			brickWallBlock = LOTRMod.wall;
			brickWallMeta = 15;
		}
		brick2Block = LOTRMod.brick3;
		brick2Meta = 13;
		if (random.nextInt(5) == 0) {
			woodBlock = LOTRMod.wood9;
			woodMeta = 0;
			plankBlock = LOTRMod.planks3;
			plankMeta = 4;
			plankSlabBlock = LOTRMod.woodSlabSingle5;
			plankSlabMeta = 4;
			plankStairBlock = LOTRMod.stairsDragon;
			fenceBlock = LOTRMod.fence3;
			fenceMeta = 4;
			fenceGateBlock = LOTRMod.fenceGateDragon;
			doorBlock = LOTRMod.doorDragon;
			beamBlock = LOTRMod.woodBeam9;
			beamMeta = 0;
			trapdoorBlock = LOTRMod.trapdoorDragon;
		} else {
			woodBlock = LOTRMod.wood8;
			woodMeta = 3;
			plankBlock = LOTRMod.planks3;
			plankMeta = 3;
			plankSlabBlock = LOTRMod.woodSlabSingle5;
			plankSlabMeta = 3;
			plankStairBlock = LOTRMod.stairsPalm;
			fenceBlock = LOTRMod.fence3;
			fenceMeta = 3;
			fenceGateBlock = LOTRMod.fenceGatePalm;
			doorBlock = LOTRMod.doorPalm;
			beamBlock = LOTRMod.woodBeam8;
			beamMeta = 3;
			trapdoorBlock = LOTRMod.trapdoorPalm;
		}
		int randomWood2 = random.nextInt(3);
		switch (randomWood2) {
			case 0:
				plank2Block = Blocks.planks;
				plank2Meta = 4;
				plank2SlabBlock = Blocks.wooden_slab;
				plank2SlabMeta = 4;
				plank2StairBlock = Blocks.acacia_stairs;
				beam2Block = LOTRMod.woodBeamV2;
				beam2Meta = 0;
				break;
			case 1:
				plank2Block = LOTRMod.planks;
				plank2Meta = 14;
				plank2SlabBlock = LOTRMod.woodSlabSingle2;
				plank2SlabMeta = 6;
				plank2StairBlock = LOTRMod.stairsDatePalm;
				beam2Block = LOTRMod.woodBeam4;
				beam2Meta = 2;
				break;
			case 2:
				plank2Block = LOTRMod.planks3;
				plank2Meta = 4;
				plank2SlabBlock = LOTRMod.woodSlabSingle5;
				plank2SlabMeta = 4;
				plank2StairBlock = LOTRMod.stairsDragon;
				beam2Block = LOTRMod.woodBeam9;
				beam2Meta = 0;
				break;
			default:
				break;
		}
		roofBlock = LOTRMod.thatch;
		roofMeta = 1;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = LOTRMod.stairsReed;
		flagBlock = Blocks.wool;
		flagMeta = 14;
		boneBlock = LOTRMod.boneBlock;
		boneMeta = 0;
		boneWallBlock = LOTRMod.wallBone;
		boneWallMeta = 0;
		bedBlock = LOTRMod.strawBed;
	}
}
