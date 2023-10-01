package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;

import java.util.Random;

public abstract class LOTRWorldGenMordorStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block smoothBlock;
	public int smoothMeta;
	public Block smoothSlabBlock;
	public int smoothSlabMeta;
	public Block tileBlock;
	public int tileMeta;
	public Block tileSlabBlock;
	public int tileSlabMeta;
	public Block tileStairBlock;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block beamBlock;
	public int beamMeta;
	public Block bedBlock;
	public Block gateIronBlock;
	public Block gateOrcBlock;
	public Block barsBlock;
	public Block chandelierBlock;
	public int chandelierMeta;
	public Block trapdoorBlock;

	protected LOTRWorldGenMordorStructure(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.brick;
		brickMeta = 0;
		brickSlabBlock = LOTRMod.slabSingle;
		brickSlabMeta = 1;
		brickStairBlock = LOTRMod.stairsMordorBrick;
		brickWallBlock = LOTRMod.wall;
		brickWallMeta = 1;
		brickCarvedBlock = LOTRMod.brick2;
		brickCarvedMeta = 10;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 7;
		smoothBlock = LOTRMod.smoothStone;
		smoothMeta = 0;
		smoothSlabBlock = LOTRMod.slabSingle;
		smoothSlabMeta = 0;
		tileBlock = LOTRMod.clayTileDyed;
		tileMeta = 15;
		tileSlabBlock = LOTRMod.slabClayTileDyedSingle2;
		tileSlabMeta = 7;
		tileStairBlock = LOTRMod.stairsClayTileDyedBlack;
		plankBlock = LOTRMod.planks;
		plankMeta = 3;
		plankSlabBlock = LOTRMod.woodSlabSingle;
		plankSlabMeta = 3;
		plankStairBlock = LOTRMod.stairsCharred;
		fenceBlock = LOTRMod.fence;
		fenceMeta = 3;
		beamBlock = LOTRMod.woodBeam1;
		beamMeta = 3;
		bedBlock = LOTRMod.orcBed;
		gateIronBlock = LOTRMod.gateIronBars;
		gateOrcBlock = LOTRMod.gateOrc;
		barsBlock = LOTRMod.orcSteelBars;
		chandelierBlock = LOTRMod.chandelier;
		chandelierMeta = 7;
		trapdoorBlock = LOTRMod.trapdoorCharred;
	}
}
