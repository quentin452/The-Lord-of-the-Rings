package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class LOTRWorldGenRangerStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
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
	public Block wallBlock;
	public int wallMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block tableBlock;
	public Block bedBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public Block trapdoorBlock;
	public LOTRItemBanner.BannerType bannerType;
	public LOTRChestContents chestContentsHouse;
	public LOTRChestContents chestContentsRanger;

	protected LOTRWorldGenRangerStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getRangerFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.rangerBow), new ItemStack(Items.bow), new ItemStack(Items.arrow)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		if (random.nextInt(3) == 0) {
			brickBlock = LOTRMod.brick2;
			brickMeta = 3;
			brickSlabBlock = LOTRMod.slabSingle4;
			brickSlabMeta = 1;
			brickStairBlock = LOTRMod.stairsArnorBrick;
			brickWallBlock = LOTRMod.wall2;
			brickWallMeta = 4;
			brickCarvedBlock = LOTRMod.brick2;
			brickCarvedMeta = 6;
		} else {
			brickBlock = Blocks.stonebrick;
			brickMeta = 0;
			brickSlabBlock = Blocks.stone_slab;
			brickSlabMeta = 5;
			brickStairBlock = Blocks.stone_brick_stairs;
			brickWallBlock = LOTRMod.wallStoneV;
			brickWallMeta = 1;
			brickCarvedBlock = Blocks.stonebrick;
			brickCarvedMeta = 3;
		}
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		int randomWood = random.nextInt(7);
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
				trapdoorBlock = Blocks.trapdoor;
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
				trapdoorBlock = LOTRMod.trapdoorBeech;
				break;
			case 4:
				logBlock = Blocks.log;
				logMeta = 1;
				plankBlock = Blocks.planks;
				plankMeta = 1;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 1;
				plankStairBlock = Blocks.spruce_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 1;
				fenceGateBlock = LOTRMod.fenceGateSpruce;
				woodBeamBlock = LOTRMod.woodBeamV1;
				woodBeamMeta = 1;
				doorBlock = LOTRMod.doorSpruce;
				trapdoorBlock = LOTRMod.trapdoorSpruce;
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
				trapdoorBlock = LOTRMod.trapdoorPine;
				break;
			case 6:
				logBlock = LOTRMod.wood4;
				logMeta = 0;
				plankBlock = LOTRMod.planks2;
				plankMeta = 0;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 0;
				plankStairBlock = LOTRMod.stairsChestnut;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 0;
				fenceGateBlock = LOTRMod.fenceGateChestnut;
				woodBeamBlock = LOTRMod.woodBeam4;
				woodBeamMeta = 0;
				doorBlock = LOTRMod.doorChestnut;
				trapdoorBlock = LOTRMod.trapdoorChestnut;
				break;
			default:
				break;
		}
		if (random.nextBoolean()) {
			wallBlock = LOTRMod.daub;
			wallMeta = 0;
		} else {
			wallBlock = plankBlock;
			wallMeta = plankMeta;
		}
		roofBlock = LOTRMod.thatch;
		roofMeta = 0;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = LOTRMod.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
		tableBlock = LOTRMod.rangerTable;
		bedBlock = LOTRMod.strawBed;
		plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
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
		bannerType = LOTRItemBanner.BannerType.RANGER_NORTH;
		chestContentsHouse = LOTRChestContents.RANGER_HOUSE;
		chestContentsRanger = LOTRChestContents.RANGER_TENT;
	}
}
