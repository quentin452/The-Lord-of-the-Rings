package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class LOTRWorldGenRohanStructure extends LOTRWorldGenStructureBase2 {
	public Block rockBlock;
	public int rockMeta;
	public Block rockSlabBlock;
	public int rockSlabMeta;
	public Block rockSlabDoubleBlock;
	public int rockSlabDoubleMeta;
	public Block rockStairBlock;
	public Block rockWallBlock;
	public int rockWallMeta;
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
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block cobbleSlabBlock;
	public int cobbleSlabMeta;
	public Block cobbleStairBlock;
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
	public Block log2Block;
	public int log2Meta;
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block fence2Block;
	public int fence2Meta;
	public Block fenceGate2Block;
	public Block woodBeam2Block;
	public int woodBeam2Meta;
	public Block woodBeamRohanBlock;
	public int woodBeamRohanMeta;
	public Block woodBeamRohanGoldBlock;
	public int woodBeamRohanGoldMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block tableBlock;
	public Block bedBlock;
	public Block gateBlock;
	public Block plateBlock;
	public Block carpetBlock;
	public int carpetMeta;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public LOTRItemBanner.BannerType bannerType;
	public LOTRChestContents chestContents;

	protected LOTRWorldGenRohanStructure(boolean flag) {
		super(flag);
	}

	public Block getRandomCakeBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				return LOTRMod.appleCrumble;
			case 1:
				return LOTRMod.cherryPie;
			case 2:
				return LOTRMod.berryPie;
			default:
				break;
		}
		return null;
	}

	public ItemStack getRandomRohanWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordRohan), new ItemStack(LOTRMod.battleaxeRohan), new ItemStack(LOTRMod.daggerRohan), new ItemStack(LOTRMod.spearRohan)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack[] getRohanArmourItems() {
		return new ItemStack[]{new ItemStack(LOTRMod.helmetRohan), new ItemStack(LOTRMod.bodyRohan), new ItemStack(LOTRMod.legsRohan), new ItemStack(LOTRMod.bootsRohan)};
	}

	public ItemStack getRohanFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetRohan), new ItemStack(LOTRMod.bodyRohan), new ItemStack(LOTRMod.legsRohan), new ItemStack(LOTRMod.bootsRohan), new ItemStack(LOTRMod.swordRohan), new ItemStack(LOTRMod.battleaxeRohan), new ItemStack(LOTRMod.daggerRohan), new ItemStack(Items.wooden_sword), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.rohanBow), new ItemStack(Items.arrow), new ItemStack(LOTRMod.horn)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean oneWoodType() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		rockBlock = LOTRMod.rock;
		rockMeta = 2;
		rockSlabBlock = LOTRMod.slabSingle2;
		rockSlabMeta = 1;
		rockSlabDoubleBlock = LOTRMod.slabDouble2;
		rockSlabDoubleMeta = 1;
		rockStairBlock = LOTRMod.stairsRohanRock;
		rockWallBlock = LOTRMod.wall;
		rockWallMeta = 8;
		brickBlock = LOTRMod.brick;
		brickMeta = 4;
		brickSlabBlock = LOTRMod.slabSingle;
		brickSlabMeta = 6;
		brickStairBlock = LOTRMod.stairsRohanBrick;
		brickWallBlock = LOTRMod.wall;
		brickWallMeta = 6;
		brickCarvedBlock = LOTRMod.brick5;
		brickCarvedMeta = 3;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 8;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		int randomWood = random.nextInt(6);
		switch (randomWood) {
			case 0:
			case 1:
			case 2:
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
				break;
			case 3:
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
				break;
			case 4:
				logBlock = LOTRMod.fruitWood;
				logMeta = 0;
				plankBlock = LOTRMod.planks;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsApple;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 4;
				fenceGateBlock = LOTRMod.fenceGateApple;
				woodBeamBlock = LOTRMod.woodBeamFruit;
				woodBeamMeta = 0;
				doorBlock = LOTRMod.doorApple;
				break;
			case 5:
				logBlock = LOTRMod.wood5;
				logMeta = 0;
				plankBlock = LOTRMod.planks2;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsPine;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 4;
				fenceGateBlock = LOTRMod.fenceGatePine;
				woodBeamBlock = LOTRMod.woodBeam5;
				woodBeamMeta = 0;
				doorBlock = LOTRMod.doorPine;
				break;
			default:
				break;
		}
		int randomWood2 = random.nextInt(4);
		if (randomWood2 == 0 || randomWood2 == 1 || randomWood2 == 2) {
			log2Block = Blocks.log;
			log2Meta = 1;
			plank2Block = Blocks.planks;
			plank2Meta = 1;
			plank2SlabBlock = Blocks.wooden_slab;
			plank2SlabMeta = 1;
			plank2StairBlock = Blocks.spruce_stairs;
			fence2Block = Blocks.fence;
			fence2Meta = 1;
			fenceGate2Block = LOTRMod.fenceGateSpruce;
			woodBeam2Block = LOTRMod.woodBeamV1;
		} else {
			log2Block = LOTRMod.wood3;
			log2Meta = 1;
			plank2Block = LOTRMod.planks;
			plank2Meta = 13;
			plank2SlabBlock = LOTRMod.woodSlabSingle2;
			plank2SlabMeta = 5;
			plank2StairBlock = LOTRMod.stairsLarch;
			fence2Block = LOTRMod.fence;
			fence2Meta = 13;
			fenceGate2Block = LOTRMod.fenceGateLarch;
			woodBeam2Block = LOTRMod.woodBeam3;
		}
		woodBeam2Meta = 1;
		if (oneWoodType() && random.nextInt(3) == 0) {
			logBlock = log2Block;
			logMeta = log2Meta;
			plankBlock = plank2Block;
			plankMeta = plank2Meta;
			plankSlabBlock = plank2SlabBlock;
			plankSlabMeta = plank2SlabMeta;
			plankStairBlock = plank2StairBlock;
			fenceBlock = fence2Block;
			fenceMeta = fence2Meta;
			fenceGateBlock = fenceGate2Block;
			woodBeamBlock = woodBeam2Block;
			woodBeamMeta = woodBeam2Meta;
		}
		woodBeamRohanBlock = LOTRMod.woodBeamS;
		woodBeamRohanMeta = 0;
		woodBeamRohanGoldBlock = LOTRMod.woodBeamS;
		woodBeamRohanGoldMeta = 1;
		roofBlock = LOTRMod.thatch;
		roofMeta = 0;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = LOTRMod.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
		tableBlock = LOTRMod.rohirricTable;
		bedBlock = LOTRMod.strawBed;
		gateBlock = LOTRMod.gateWooden;
		plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
		carpetBlock = Blocks.carpet;
		carpetMeta = 13;
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
		bannerType = LOTRItemBanner.BannerType.ROHAN;
		chestContents = LOTRChestContents.ROHAN_HOUSE;
	}
}
