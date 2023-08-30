package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenSouthronStructure extends LOTRWorldGenStructureBase2 {
	public Block stoneBlock;
	public int stoneMeta;
	public Block stoneStairBlock;
	public Block stoneWallBlock;
	public int stoneWallMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block brick2CarvedBlock;
	public int brick2CarvedMeta;
	public Block pillar2Block;
	public int pillar2Meta;
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
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public int woodBeamMeta4;
	public int woodBeamMeta8;
	public Block doorBlock;
	public Block plank2Block;
	public int plank2Meta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block gateMetalBlock;
	public Block bedBlock;
	public Block tableBlock;
	public Block trapdoorBlock;
	public Block cropBlock;
	public LOTRItemBanner.BannerType bannerType;

	protected LOTRWorldGenSouthronStructure(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBricks() {
		return true;
	}

	public LOTREntityNearHaradrimBase createHaradrim(World world) {
		if (isUmbar()) {
			return new LOTREntityUmbarian(world);
		}
		return new LOTREntityNearHaradrim(world);
	}

	public boolean forceCedarWood() {
		return false;
	}

	public ItemStack getRandomHaradItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.gobletCopper), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing), new ItemStack(LOTRMod.doubleFlower, 1, 2), new ItemStack(LOTRMod.doubleFlower, 1, 3), new ItemStack(LOTRMod.gemsbokHorn), new ItemStack(LOTRMod.lionFur)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomHaradWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean isUmbar() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		stoneBlock = Blocks.sandstone;
		stoneMeta = 0;
		stoneStairBlock = Blocks.sandstone_stairs;
		stoneWallBlock = LOTRMod.wallStoneV;
		stoneWallMeta = 4;
		if (canUseRedBricks() && random.nextInt(4) == 0) {
			brickBlock = LOTRMod.brick3;
			brickMeta = 13;
			brickSlabBlock = LOTRMod.slabSingle7;
			brickSlabMeta = 2;
			brickStairBlock = LOTRMod.stairsNearHaradBrickRed;
			brickWallBlock = LOTRMod.wall3;
			brickWallMeta = 4;
			pillarBlock = LOTRMod.pillar;
			pillarMeta = 15;
		} else {
			brickBlock = LOTRMod.brick;
			brickMeta = 15;
			brickSlabBlock = LOTRMod.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = LOTRMod.stairsNearHaradBrick;
			brickWallBlock = LOTRMod.wall;
			brickWallMeta = 15;
			pillarBlock = LOTRMod.pillar;
			pillarMeta = 5;
		}
		brick2Block = LOTRMod.brick3;
		brick2Meta = 13;
		brick2SlabBlock = LOTRMod.slabSingle7;
		brick2SlabMeta = 2;
		brick2StairBlock = LOTRMod.stairsNearHaradBrickRed;
		brick2WallBlock = LOTRMod.wall3;
		brick2WallMeta = 4;
		brick2CarvedBlock = LOTRMod.brick3;
		brick2CarvedMeta = 15;
		pillar2Block = LOTRMod.pillar;
		pillar2Meta = 15;
		roofBlock = LOTRMod.thatch;
		roofMeta = 1;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = LOTRMod.stairsReed;
		if (random.nextBoolean() || forceCedarWood()) {
			woodBlock = LOTRMod.wood4;
			woodMeta = 2;
			plankBlock = LOTRMod.planks2;
			plankMeta = 2;
			plankSlabBlock = LOTRMod.woodSlabSingle3;
			plankSlabMeta = 2;
			plankStairBlock = LOTRMod.stairsCedar;
			fenceBlock = LOTRMod.fence2;
			fenceMeta = 2;
			fenceGateBlock = LOTRMod.fenceGateCedar;
			woodBeamBlock = LOTRMod.woodBeam4;
			woodBeamMeta = 2;
			doorBlock = LOTRMod.doorCedar;
			trapdoorBlock = LOTRMod.trapdoorCedar;
		} else {
			int randomWood = random.nextInt(3);
			switch (randomWood) {
				case 0:
					woodBlock = LOTRMod.wood6;
					woodMeta = 3;
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
				case 1:
					woodBlock = LOTRMod.wood3;
					woodMeta = 2;
					plankBlock = LOTRMod.planks;
					plankMeta = 14;
					plankSlabBlock = LOTRMod.woodSlabSingle2;
					plankSlabMeta = 6;
					plankStairBlock = LOTRMod.stairsDatePalm;
					fenceBlock = LOTRMod.fence;
					fenceMeta = 14;
					fenceGateBlock = LOTRMod.fenceGateDatePalm;
					woodBeamBlock = LOTRMod.woodBeam3;
					woodBeamMeta = 2;
					doorBlock = LOTRMod.doorDatePalm;
					trapdoorBlock = LOTRMod.trapdoorDatePalm;
					break;
				case 2:
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
					woodBeamBlock = LOTRMod.woodBeam8;
					woodBeamMeta = 3;
					doorBlock = LOTRMod.doorPalm;
					trapdoorBlock = LOTRMod.trapdoorPalm;
					break;
				default:
					break;
			}
		}
		woodBeamMeta4 = woodBeamMeta | 4;
		woodBeamMeta8 = woodBeamMeta | 8;
		plank2Block = LOTRMod.planks2;
		plank2Meta = 11;
		gateMetalBlock = LOTRMod.gateBronzeBars;
		bedBlock = LOTRMod.strawBed;
		tableBlock = LOTRMod.nearHaradTable;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
		} else {
			int randomCrop = random.nextInt(3);
			switch (randomCrop) {
				case 0:
				case 1:
					cropBlock = Blocks.carrots;
					break;
				case 2:
					cropBlock = LOTRMod.lettuceCrop;
					break;
				default:
					break;
			}
		}
		bannerType = LOTRItemBanner.BannerType.NEAR_HARAD;
		if (isUmbar()) {
			stoneBlock = LOTRMod.brick2;
			stoneMeta = 11;
			stoneStairBlock = LOTRMod.stairsBlackGondorBrick;
			stoneWallBlock = LOTRMod.wall2;
			stoneWallMeta = 10;
			brickBlock = LOTRMod.brick2;
			brickMeta = 11;
			brickSlabBlock = LOTRMod.slabSingle5;
			brickSlabMeta = 3;
			brickStairBlock = LOTRMod.stairsBlackGondorBrick;
			brickWallBlock = LOTRMod.wall2;
			brickWallMeta = 10;
			pillarBlock = LOTRMod.pillar;
			pillarMeta = 9;
			int randomRoof = random.nextInt(4);
			switch (randomRoof) {
				case 0:
					roofBlock = LOTRMod.clayTileDyed;
					roofMeta = 15;
					roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
					roofSlabMeta = 7;
					roofStairBlock = LOTRMod.stairsClayTileDyedBlack;
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
					roofMeta = 7;
					roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
					roofSlabMeta = 7;
					roofStairBlock = LOTRMod.stairsClayTileDyedGray;
					break;
				default:
					break;
			}
			brick2Block = roofBlock;
			brick2Meta = roofMeta;
			brick2SlabBlock = roofSlabBlock;
			brick2SlabMeta = roofSlabMeta;
			brick2StairBlock = roofStairBlock;
			plankBlock = LOTRMod.brick6;
			plankMeta = 6;
			plankSlabBlock = LOTRMod.slabSingle13;
			plankSlabMeta = 2;
			plankStairBlock = LOTRMod.stairsUmbarBrick;
			woodBeamBlock = LOTRMod.pillar2;
			woodBeamMeta4 = woodBeamMeta = 10;
			woodBeamMeta8 = woodBeamMeta;
			if (random.nextBoolean() && !forceCedarWood()) {
				fenceBlock = LOTRMod.fence3;
				fenceMeta = 3;
				fenceGateBlock = LOTRMod.fenceGatePalm;
				doorBlock = LOTRMod.doorPalm;
			}
			gateMetalBlock = LOTRMod.gateIronBars;
			tableBlock = LOTRMod.umbarTable;
			bannerType = LOTRItemBanner.BannerType.UMBAR;
		}
	}
}
