package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Random;

public abstract class LOTRWorldGenDaleStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block floorBlock;
	public int floorMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block woodBlock;
	public int woodMeta;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block doorBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block plateBlock;
	public Block trapdoorBlock;

	protected LOTRWorldGenDaleStructure(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = LOTRMod.brick5;
		brickMeta = 1;
		brickSlabBlock = LOTRMod.slabSingle9;
		brickSlabMeta = 6;
		brickStairBlock = LOTRMod.stairsDaleBrick;
		brickWallBlock = LOTRMod.wall3;
		brickWallMeta = 9;
		pillarBlock = LOTRMod.pillar2;
		pillarMeta = 5;
		floorBlock = Blocks.cobblestone;
		floorMeta = 0;
		int randomWood = random.nextInt(3);
		switch (randomWood) {
			case 0:
				plankBlock = Blocks.planks;
				plankMeta = 1;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 1;
				plankStairBlock = Blocks.spruce_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				woodBlock = Blocks.log;
				woodMeta = 0;
				woodBeamBlock = LOTRMod.woodBeamV1;
				woodBeamMeta = 0;
				doorBlock = LOTRMod.doorSpruce;
				trapdoorBlock = LOTRMod.trapdoorSpruce;
				break;
			case 1:
				plankBlock = LOTRMod.planks2;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsPine;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 4;
				fenceGateBlock = LOTRMod.fenceGatePine;
				woodBlock = LOTRMod.wood5;
				woodMeta = 0;
				woodBeamBlock = LOTRMod.woodBeam5;
				woodBeamMeta = 0;
				doorBlock = LOTRMod.doorPine;
				trapdoorBlock = LOTRMod.trapdoorPine;
				break;
			case 2:
				plankBlock = LOTRMod.planks2;
				plankMeta = 3;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 3;
				plankStairBlock = LOTRMod.stairsFir;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 3;
				fenceGateBlock = LOTRMod.fenceGateFir;
				woodBlock = LOTRMod.wood4;
				woodMeta = 3;
				woodBeamBlock = LOTRMod.woodBeam4;
				woodBeamMeta = 3;
				doorBlock = LOTRMod.doorFir;
				trapdoorBlock = LOTRMod.trapdoorFir;
				break;
			default:
				break;
		}
		int randomClay = random.nextInt(4);
		switch (randomClay) {
			case 0:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 1;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
				roofSlabMeta = 1;
				roofStairBlock = LOTRMod.stairsClayTileDyedOrange;
				break;
			case 1:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 14;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 6;
				roofStairBlock = LOTRMod.stairsClayTileDyedRed;
				break;
			case 2:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 12;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 4;
				roofStairBlock = LOTRMod.stairsClayTileDyedBrown;
				break;
			case 3:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 11;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 3;
				roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
				break;
			default:
				break;
		}
		barsBlock = random.nextInt(3) == 0 ? Blocks.iron_bars : LOTRMod.bronzeBars;
		plateBlock = random.nextBoolean() ? random.nextBoolean() ? LOTRMod.plateBlock : LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
	}
}
