package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class LOTRWorldGenEasterlingStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block brickFloweryBlock;
	public int brickFloweryMeta;
	public Block brickFlowerySlabBlock;
	public int brickFlowerySlabMeta;
	public Block brickGoldBlock;
	public int brickGoldMeta;
	public Block brickRedBlock;
	public int brickRedMeta;
	public Block brickRedSlabBlock;
	public int brickRedSlabMeta;
	public Block brickRedStairBlock;
	public Block brickRedWallBlock;
	public int brickRedWallMeta;
	public Block brickRedCarvedBlock;
	public int brickRedCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block pillarRedBlock;
	public int pillarRedMeta;
	public Block logBlock;
	public int logMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block doorBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block roofWallBlock;
	public int roofWallMeta;
	public Block barsBlock;
	public Block tableBlock;
	public Block gateBlock;
	public Block bedBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public Block trapdoorBlock;
	public LOTRItemBanner.BannerType bannerType;
	public LOTRChestContents chestContents;

	protected LOTRWorldGenEasterlingStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getEasterlingFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetRhun), new ItemStack(LOTRMod.bodyRhun), new ItemStack(LOTRMod.legsRhun), new ItemStack(LOTRMod.bootsRhun), new ItemStack(LOTRMod.helmetRhunGold), new ItemStack(LOTRMod.bodyRhunGold), new ItemStack(LOTRMod.legsRhunGold), new ItemStack(LOTRMod.bootsRhunGold), new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.rhunBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.goldRing)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getEasterlingWeaponItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.daggerRhunPoisoned), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(LOTRMod.polearmRhun), new ItemStack(LOTRMod.pikeRhun)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = LOTRMod.brick5;
		brickMeta = 11;
		brickSlabBlock = LOTRMod.slabSingle12;
		brickSlabMeta = 0;
		brickStairBlock = LOTRMod.stairsRhunBrick;
		brickWallBlock = LOTRMod.wall3;
		brickWallMeta = 15;
		brickCarvedBlock = LOTRMod.brick5;
		brickCarvedMeta = 12;
		brickFloweryBlock = LOTRMod.brick5;
		brickFloweryMeta = 15;
		brickFlowerySlabBlock = LOTRMod.slabSingle12;
		brickFlowerySlabMeta = 3;
		brickGoldBlock = LOTRMod.brick6;
		brickGoldMeta = 0;
		brickRedBlock = LOTRMod.brick6;
		brickRedMeta = 1;
		brickRedSlabBlock = LOTRMod.slabSingle12;
		brickRedSlabMeta = 5;
		brickRedStairBlock = LOTRMod.stairsRhunBrickRed;
		brickRedWallBlock = LOTRMod.wall4;
		brickRedWallMeta = 13;
		brickRedCarvedBlock = LOTRMod.brick6;
		brickRedCarvedMeta = 2;
		pillarBlock = LOTRMod.pillar2;
		pillarMeta = 8;
		pillarRedBlock = LOTRMod.pillar2;
		pillarRedMeta = 9;
		if (random.nextBoolean()) {
			logBlock = LOTRMod.wood8;
			logMeta = 1;
			plankBlock = LOTRMod.planks3;
			plankMeta = 1;
			plankSlabBlock = LOTRMod.woodSlabSingle5;
			plankSlabMeta = 1;
			plankStairBlock = LOTRMod.stairsRedwood;
			fenceBlock = LOTRMod.fence3;
			fenceMeta = 1;
			fenceGateBlock = LOTRMod.fenceGateRedwood;
			woodBeamBlock = LOTRMod.woodBeam8;
			woodBeamMeta = 1;
			doorBlock = LOTRMod.doorRedwood;
			trapdoorBlock = LOTRMod.trapdoorRedwood;
		} else {
			int randomWood = random.nextInt(4);
			switch (randomWood) {
				case 0:
					logBlock = Blocks.log;
					logMeta = 0;
					plankBlock = Blocks.planks;
					plankMeta = 0;
					plankSlabBlock = Blocks.wooden_slab;
					plankSlabMeta = 0;
					plankStairBlock = Blocks.oak_stairs;
					fenceBlock = Blocks.fence;
					fenceMeta = 0;
					fenceGateBlock = Blocks.fence_gate;
					woodBeamBlock = LOTRMod.woodBeamV1;
					woodBeamMeta = 0;
					doorBlock = Blocks.wooden_door;
					trapdoorBlock = Blocks.trapdoor;
					break;
				case 1:
					logBlock = LOTRMod.wood2;
					logMeta = 1;
					plankBlock = LOTRMod.planks;
					plankMeta = 9;
					plankSlabBlock = LOTRMod.woodSlabSingle2;
					plankSlabMeta = 1;
					plankStairBlock = LOTRMod.stairsBeech;
					fenceBlock = LOTRMod.fence;
					fenceMeta = 9;
					fenceGateBlock = LOTRMod.fenceGateBeech;
					woodBeamBlock = LOTRMod.woodBeam2;
					woodBeamMeta = 1;
					doorBlock = LOTRMod.doorBeech;
					trapdoorBlock = LOTRMod.trapdoorBeech;
					break;
				case 2:
					logBlock = LOTRMod.wood6;
					logMeta = 2;
					plankBlock = LOTRMod.planks2;
					plankMeta = 10;
					plankSlabBlock = LOTRMod.woodSlabSingle4;
					plankSlabMeta = 2;
					plankStairBlock = LOTRMod.stairsCypress;
					fenceBlock = LOTRMod.fence2;
					fenceMeta = 10;
					fenceGateBlock = LOTRMod.fenceGateCypress;
					woodBeamBlock = LOTRMod.woodBeam6;
					woodBeamMeta = 2;
					doorBlock = LOTRMod.doorCypress;
					trapdoorBlock = LOTRMod.trapdoorCypress;
					break;
				case 3:
					logBlock = LOTRMod.wood6;
					logMeta = 3;
					plankBlock = LOTRMod.planks2;
					plankMeta = 11;
					plankSlabBlock = LOTRMod.woodSlabSingle4;
					plankSlabMeta = 3;
					plankStairBlock = LOTRMod.stairsOlive;
					fenceBlock = LOTRMod.fence2;
					fenceMeta = 11;
					fenceGateBlock = LOTRMod.fenceGateOlive;
					woodBeamBlock = LOTRMod.woodBeam6;
					woodBeamMeta = 3;
					doorBlock = LOTRMod.doorOlive;
					trapdoorBlock = LOTRMod.trapdoorOlive;
					break;
				default:
					break;
			}
		}
		if (useTownBlocks()) {
			if (random.nextBoolean()) {
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 14;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 6;
				roofStairBlock = LOTRMod.stairsClayTileDyedRed;
				roofWallBlock = LOTRMod.wallClayTileDyed;
				roofWallMeta = 14;
			} else {
				int randomClay = random.nextInt(2);
				if (randomClay == 0) {
					roofBlock = LOTRMod.clayTileDyed;
					roofMeta = 12;
					roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
					roofSlabMeta = 4;
					roofStairBlock = LOTRMod.stairsClayTileDyedBrown;
					roofWallBlock = LOTRMod.wallClayTileDyed;
					roofWallMeta = 12;
				} else {
					roofBlock = LOTRMod.clayTileDyed;
					roofMeta = 1;
					roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
					roofSlabMeta = 1;
					roofStairBlock = LOTRMod.stairsClayTileDyedOrange;
					roofWallBlock = LOTRMod.wallClayTileDyed;
					roofWallMeta = 1;
				}
			}
		} else {
			roofBlock = LOTRMod.thatch;
			roofMeta = 0;
			roofSlabBlock = LOTRMod.slabSingleThatch;
			roofSlabMeta = 0;
			roofStairBlock = LOTRMod.stairsThatch;
			roofWallBlock = fenceBlock;
			roofWallMeta = fenceMeta;
		}
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
		tableBlock = LOTRMod.rhunTable;
		gateBlock = LOTRMod.gateRhun;
		bedBlock = useTownBlocks() ? Blocks.bed : LOTRMod.strawBed;
		plateBlock = useTownBlocks() ? LOTRMod.ceramicPlateBlock : random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
			cropMeta = 7;
			seedItem = Items.wheat_seeds;
		} else {
			int randomCrop = random.nextInt(5);
			switch (randomCrop) {
				case 0:
					cropBlock = Blocks.carrots;
					cropMeta = 7;
					seedItem = Items.carrot;
					break;
				case 1:
					cropBlock = Blocks.potatoes;
					cropMeta = 7;
					seedItem = Items.potato;
					break;
				case 2:
					cropBlock = LOTRMod.lettuceCrop;
					cropMeta = 7;
					seedItem = LOTRMod.lettuce;
					break;
				case 3:
					cropBlock = LOTRMod.leekCrop;
					cropMeta = 7;
					seedItem = LOTRMod.leek;
					break;
				case 4:
					cropBlock = LOTRMod.turnipCrop;
					cropMeta = 7;
					seedItem = LOTRMod.turnip;
					break;
				default:
					break;
			}
		}
		bannerType = LOTRItemBanner.BannerType.RHUN;
		chestContents = LOTRChestContents.EASTERLING_HOUSE;
	}

	public boolean useTownBlocks() {
		return false;
	}
}
