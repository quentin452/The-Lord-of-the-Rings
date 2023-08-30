package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorStructure extends LOTRWorldGenStructureBase2 {
	public GondorFiefdom strFief = GondorFiefdom.GONDOR;
	public Block rockBlock;
	public int rockMeta;
	public Block rockSlabBlock;
	public int rockSlabMeta;
	public Block trapdoorBlock;
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
	public Block brickMossyBlock;
	public int brickMossyMeta;
	public Block brickMossySlabBlock;
	public int brickMossySlabMeta;
	public Block brickMossyStairBlock;
	public Block brickMossyWallBlock;
	public int brickMossyWallMeta;
	public Block brickCrackedBlock;
	public int brickCrackedMeta;
	public Block brickCrackedSlabBlock;
	public int brickCrackedSlabMeta;
	public Block brickCrackedStairBlock;
	public Block brickCrackedWallBlock;
	public int brickCrackedWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block pillar2Block;
	public int pillar2Meta;
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block cobbleSlabBlock;
	public int cobbleSlabMeta;
	public Block cobbleStairBlock;
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
	public Block gateBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public LOTRItemBanner.BannerType bannerType;
	public LOTRItemBanner.BannerType bannerType2;
	public LOTRChestContents chestContents;

	protected LOTRWorldGenGondorStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getGondorFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.daggerGondor), new ItemStack(LOTRMod.spearGondor), new ItemStack(LOTRMod.gondorBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.ironCrossbow), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		rockBlock = LOTRMod.rock;
		rockMeta = 1;
		rockSlabBlock = LOTRMod.slabSingle;
		rockSlabMeta = 2;
		rockSlabDoubleBlock = LOTRMod.slabDouble;
		rockSlabDoubleMeta = 2;
		rockStairBlock = LOTRMod.stairsGondorRock;
		rockWallBlock = LOTRMod.wall;
		rockWallMeta = 2;
		if (strFief == GondorFiefdom.GONDOR || strFief == GondorFiefdom.LEBENNIN || strFief == GondorFiefdom.PELARGIR) {
			brickBlock = LOTRMod.brick;
			brickMeta = 1;
			brickSlabBlock = LOTRMod.slabSingle;
			brickSlabMeta = 3;
			brickStairBlock = LOTRMod.stairsGondorBrick;
			brickWallBlock = LOTRMod.wall;
			brickWallMeta = 3;
			brickMossyBlock = LOTRMod.brick;
			brickMossyMeta = 2;
			brickMossySlabBlock = LOTRMod.slabSingle;
			brickMossySlabMeta = 4;
			brickMossyStairBlock = LOTRMod.stairsGondorBrickMossy;
			brickMossyWallBlock = LOTRMod.wall;
			brickMossyWallMeta = 4;
			brickCrackedBlock = LOTRMod.brick;
			brickCrackedMeta = 3;
			brickCrackedSlabBlock = LOTRMod.slabSingle;
			brickCrackedSlabMeta = 5;
			brickCrackedStairBlock = LOTRMod.stairsGondorBrickCracked;
			brickCrackedWallBlock = LOTRMod.wall;
			brickCrackedWallMeta = 5;
		} else if (strFief == GondorFiefdom.DOL_AMROTH) {
			brickBlock = LOTRMod.brick3;
			brickMeta = 9;
			brickSlabBlock = LOTRMod.slabSingle6;
			brickSlabMeta = 7;
			brickStairBlock = LOTRMod.stairsDolAmrothBrick;
			brickWallBlock = LOTRMod.wall2;
			brickWallMeta = 14;
			brickMossyBlock = brickBlock;
			brickMossyMeta = brickMeta;
			brickMossySlabBlock = brickSlabBlock;
			brickMossySlabMeta = brickSlabMeta;
			brickMossyStairBlock = brickStairBlock;
			brickMossyWallBlock = brickWallBlock;
			brickMossyWallMeta = brickWallMeta;
			brickCrackedBlock = brickBlock;
			brickCrackedMeta = brickMeta;
			brickCrackedSlabBlock = brickSlabBlock;
			brickCrackedSlabMeta = brickSlabMeta;
			brickCrackedStairBlock = brickStairBlock;
			brickCrackedWallBlock = brickWallBlock;
			brickCrackedWallMeta = brickWallMeta;
		} else {
			brickBlock = LOTRMod.brick5;
			brickMeta = 8;
			brickSlabBlock = LOTRMod.slabSingle11;
			brickSlabMeta = 0;
			brickStairBlock = LOTRMod.stairsGondorBrickRustic;
			brickWallBlock = LOTRMod.wall4;
			brickWallMeta = 7;
			brickMossyBlock = LOTRMod.brick5;
			brickMossyMeta = 9;
			brickMossySlabBlock = LOTRMod.slabSingle11;
			brickMossySlabMeta = 1;
			brickMossyStairBlock = LOTRMod.stairsGondorBrickRusticMossy;
			brickMossyWallBlock = LOTRMod.wall4;
			brickMossyWallMeta = 8;
			brickCrackedBlock = LOTRMod.brick5;
			brickCrackedMeta = 10;
			brickCrackedSlabBlock = LOTRMod.slabSingle11;
			brickCrackedSlabMeta = 2;
			brickCrackedStairBlock = LOTRMod.stairsGondorBrickRusticCracked;
			brickCrackedWallBlock = LOTRMod.wall4;
			brickCrackedWallMeta = 9;
		}
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 6;
		if (strFief == GondorFiefdom.GONDOR || strFief == GondorFiefdom.BLACKROOT_VALE) {
			brick2Block = LOTRMod.brick2;
			brick2Meta = 11;
			brick2SlabBlock = LOTRMod.slabSingle5;
			brick2SlabMeta = 3;
			brick2StairBlock = LOTRMod.stairsBlackGondorBrick;
			brick2WallBlock = LOTRMod.wall2;
			brick2WallMeta = 10;
			pillar2Block = LOTRMod.pillar;
			pillar2Meta = 9;
		} else if (strFief == GondorFiefdom.PELARGIR) {
			brick2Block = LOTRMod.whiteSandstone;
			brick2Meta = 0;
			brick2SlabBlock = LOTRMod.slabSingle10;
			brick2SlabMeta = 6;
			brick2StairBlock = LOTRMod.stairsWhiteSandstone;
			brick2WallBlock = LOTRMod.wall3;
			brick2WallMeta = 14;
			pillar2Block = LOTRMod.pillar;
			pillar2Meta = 9;
		} else if (strFief == GondorFiefdom.LAMEDON) {
			brick2Block = Blocks.cobblestone;
			brick2Meta = 0;
			brick2SlabBlock = Blocks.stone_slab;
			brick2SlabMeta = 3;
			brick2StairBlock = Blocks.stone_stairs;
			brick2WallBlock = Blocks.cobblestone_wall;
			brick2WallMeta = 0;
			pillar2Block = LOTRMod.pillar2;
			pillar2Meta = 2;
		} else if (strFief == GondorFiefdom.PINNATH_GELIN) {
			brick2Block = LOTRMod.clayTileDyed;
			brick2Meta = 13;
			brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
			brick2SlabMeta = 5;
			brick2StairBlock = LOTRMod.stairsClayTileDyedGreen;
			brick2WallBlock = LOTRMod.wallClayTileDyed;
			brick2WallMeta = 13;
			pillar2Block = LOTRMod.pillar;
			pillar2Meta = 6;
		} else if (strFief == GondorFiefdom.DOL_AMROTH) {
			brick2Block = LOTRMod.clayTileDyed;
			brick2Meta = 11;
			brick2SlabBlock = LOTRMod.slabClayTileDyedSingle2;
			brick2SlabMeta = 3;
			brick2StairBlock = LOTRMod.stairsClayTileDyedBlue;
			brick2WallBlock = LOTRMod.wallClayTileDyed;
			brick2WallMeta = 11;
			pillar2Block = LOTRMod.pillar;
			pillar2Meta = 6;
		} else {
			brick2Block = Blocks.stonebrick;
			brick2Meta = 0;
			brick2SlabBlock = Blocks.stone_slab;
			brick2SlabMeta = 5;
			brick2StairBlock = Blocks.stone_brick_stairs;
			brick2WallBlock = LOTRMod.wallStoneV;
			brick2WallMeta = 1;
			pillar2Block = LOTRMod.pillar2;
			pillar2Meta = 2;
		}
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		if (strFief == GondorFiefdom.BLACKROOT_VALE) {
			plankBlock = Blocks.planks;
			plankMeta = 5;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 5;
			plankStairBlock = Blocks.dark_oak_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 5;
			fenceGateBlock = LOTRMod.fenceGateDarkOak;
			woodBeamBlock = LOTRMod.woodBeamV2;
			woodBeamMeta = 1;
			doorBlock = LOTRMod.doorDarkOak;
			trapdoorBlock = LOTRMod.trapdoorDarkOak;
		} else {
			int randomWood = random.nextInt(7);
			switch (randomWood) {
				case 0:
				case 1:
				case 2:
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
					break;
				case 5:
					plankBlock = LOTRMod.planks;
					plankMeta = 8;
					plankSlabBlock = LOTRMod.woodSlabSingle2;
					plankSlabMeta = 0;
					plankStairBlock = LOTRMod.stairsLebethron;
					fenceBlock = LOTRMod.fence;
					fenceMeta = 8;
					fenceGateBlock = LOTRMod.fenceGateLebethron;
					woodBeamBlock = LOTRMod.woodBeam2;
					woodBeamMeta = 0;
					doorBlock = LOTRMod.doorLebethron;
					trapdoorBlock = LOTRMod.trapdoorLebethron;
					break;
				case 6:
					plankBlock = Blocks.planks;
					plankMeta = 2;
					plankSlabBlock = Blocks.wooden_slab;
					plankSlabMeta = 2;
					plankStairBlock = Blocks.birch_stairs;
					fenceBlock = Blocks.fence;
					fenceMeta = 2;
					fenceGateBlock = LOTRMod.fenceGateBirch;
					woodBeamBlock = LOTRMod.woodBeamV1;
					woodBeamMeta = 2;
					doorBlock = LOTRMod.doorBirch;
					trapdoorBlock = LOTRMod.trapdoorBirch;
					break;
				default:
					break;
			}
		}
		if (strFief == GondorFiefdom.LOSSARNACH) {
			pillarBlock = woodBeamBlock;
			pillarMeta = woodBeamMeta;
			brick2Block = plankBlock;
			brick2Meta = plankMeta;
			brick2SlabBlock = plankSlabBlock;
			brick2SlabMeta = plankSlabMeta;
			brick2StairBlock = plankStairBlock;
			brick2WallBlock = fenceBlock;
			brick2WallMeta = fenceMeta;
			pillar2Block = woodBeamBlock;
			pillar2Meta = woodBeamMeta;
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
		barsBlock = Blocks.iron_bars;
		tableBlock = LOTRMod.gondorianTable;
		bedBlock = LOTRMod.strawBed;
		gateBlock = strFief == GondorFiefdom.PINNATH_GELIN || strFief == GondorFiefdom.LOSSARNACH || strFief == GondorFiefdom.LAMEDON ? LOTRMod.gateWooden : strFief == GondorFiefdom.DOL_AMROTH ? LOTRMod.gateDolAmroth : LOTRMod.gateGondor;
		plateBlock = strFief == GondorFiefdom.LOSSARNACH || strFief == GondorFiefdom.LAMEDON || strFief == GondorFiefdom.BLACKROOT_VALE ? random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock : LOTRMod.ceramicPlateBlock;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
			cropMeta = 7;
			seedItem = Items.wheat_seeds;
		} else {
			int randomCrop = random.nextInt(6);
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
					cropBlock = LOTRMod.cornStalk;
					cropMeta = 0;
					seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
					break;
				case 4:
					cropBlock = LOTRMod.leekCrop;
					cropMeta = 7;
					seedItem = LOTRMod.leek;
					break;
				case 5:
					cropBlock = LOTRMod.turnipCrop;
					cropMeta = 7;
					seedItem = LOTRMod.turnip;
					break;
				default:
					break;
			}
		}
		if (strFief == GondorFiefdom.GONDOR) {
			bannerType = LOTRItemBanner.BannerType.GONDOR;
		} else if (strFief == GondorFiefdom.LOSSARNACH) {
			bannerType = LOTRItemBanner.BannerType.LOSSARNACH;
		} else if (strFief == GondorFiefdom.LEBENNIN) {
			bannerType = LOTRItemBanner.BannerType.LEBENNIN;
		} else if (strFief == GondorFiefdom.PELARGIR) {
			bannerType = LOTRItemBanner.BannerType.PELARGIR;
		} else if (strFief == GondorFiefdom.PINNATH_GELIN) {
			bannerType = LOTRItemBanner.BannerType.PINNATH_GELIN;
		} else if (strFief == GondorFiefdom.BLACKROOT_VALE) {
			bannerType = LOTRItemBanner.BannerType.BLACKROOT_VALE;
		} else if (strFief == GondorFiefdom.LAMEDON) {
			bannerType = LOTRItemBanner.BannerType.LAMEDON;
		} else if (strFief == GondorFiefdom.DOL_AMROTH) {
			bannerType = LOTRItemBanner.BannerType.DOL_AMROTH;
		}
		bannerType2 = strFief == GondorFiefdom.PELARGIR ? LOTRItemBanner.BannerType.LEBENNIN : LOTRItemBanner.BannerType.GONDOR;
		chestContents = LOTRChestContents.GONDOR_HOUSE;
	}

	public enum GondorFiefdom {
		GONDOR, LOSSARNACH, LEBENNIN, PELARGIR, PINNATH_GELIN, BLACKROOT_VALE, LAMEDON, DOL_AMROTH;

		public LOTREntityGondorMan createCaptain(World world) {
			if (this == GONDOR) {
				return new LOTREntityGondorianCaptain(world);
			}
			if (this == LOSSARNACH) {
				return new LOTREntityLossarnachCaptain(world);
			}
			if (this == LEBENNIN) {
				return new LOTREntityLebenninCaptain(world);
			}
			if (this == PELARGIR) {
				return new LOTREntityPelargirCaptain(world);
			}
			if (this == PINNATH_GELIN) {
				return new LOTREntityPinnathGelinCaptain(world);
			}
			if (this == BLACKROOT_VALE) {
				return new LOTREntityBlackrootCaptain(world);
			}
			if (this == LAMEDON) {
				return new LOTREntityLamedonCaptain(world);
			}
			if (this == DOL_AMROTH) {
				return new LOTREntityDolAmrothCaptain(world);
			}
			return null;
		}

		public ItemStack[] getFiefdomArmor() {
			if (this == GONDOR) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
			}
			if (this == LOSSARNACH) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetLossarnach), new ItemStack(LOTRMod.bodyLossarnach), new ItemStack(LOTRMod.legsLossarnach), new ItemStack(LOTRMod.bootsLossarnach)};
			}
			if (this == LEBENNIN) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetGondor), new ItemStack(LOTRMod.bodyGondor), new ItemStack(LOTRMod.legsGondor), new ItemStack(LOTRMod.bootsGondor)};
			}
			if (this == PELARGIR) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetPelargir), new ItemStack(LOTRMod.bodyPelargir), new ItemStack(LOTRMod.legsPelargir), new ItemStack(LOTRMod.bootsPelargir)};
			}
			if (this == PINNATH_GELIN) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetPinnathGelin), new ItemStack(LOTRMod.bodyPinnathGelin), new ItemStack(LOTRMod.legsPinnathGelin), new ItemStack(LOTRMod.bootsPinnathGelin)};
			}
			if (this == BLACKROOT_VALE) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetBlackroot), new ItemStack(LOTRMod.bodyBlackroot), new ItemStack(LOTRMod.legsBlackroot), new ItemStack(LOTRMod.bootsBlackroot)};
			}
			if (this == LAMEDON) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetLamedon), new ItemStack(LOTRMod.bodyLamedon), new ItemStack(LOTRMod.legsLamedon), new ItemStack(LOTRMod.bootsLamedon)};
			}
			if (this == DOL_AMROTH) {
				return new ItemStack[]{new ItemStack(LOTRMod.helmetDolAmroth), new ItemStack(LOTRMod.bodyDolAmroth), new ItemStack(LOTRMod.legsDolAmroth), new ItemStack(LOTRMod.bootsDolAmroth)};
			}
			return null;
		}

		public Class[] getLevyClasses() {
			if (this == GONDOR) {
				return new Class[]{LOTREntityGondorLevyman.class, LOTREntityGondorSoldier.class};
			}
			if (this == LOSSARNACH) {
				return new Class[]{LOTREntityGondorLevyman.class, LOTREntityLossarnachAxeman.class};
			}
			if (this == LEBENNIN) {
				return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
			}
			if (this == PELARGIR) {
				return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityPelargirMarine.class};
			}
			if (this == PINNATH_GELIN) {
				return new Class[]{LOTREntityGondorLevyman.class, LOTREntityPinnathGelinSoldier.class};
			}
			if (this == BLACKROOT_VALE) {
				return new Class[]{LOTREntityGondorLevyman.class, LOTREntityBlackrootArcher.class};
			}
			if (this == LAMEDON) {
				return new Class[]{LOTREntityLamedonHillman.class, LOTREntityLamedonSoldier.class};
			}
			if (this == DOL_AMROTH) {
				return new Class[]{LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class};
			}
			return null;
		}

		public Class[] getSoldierClasses() {
			if (this == GONDOR) {
				return new Class[]{LOTREntityGondorSoldier.class, LOTREntityGondorArcher.class};
			}
			if (this == LOSSARNACH) {
				return new Class[]{LOTREntityLossarnachAxeman.class, LOTREntityLossarnachAxeman.class};
			}
			if (this == LEBENNIN) {
				return new Class[]{LOTREntityLebenninLevyman.class, LOTREntityGondorSoldier.class};
			}
			if (this == PELARGIR) {
				return new Class[]{LOTREntityPelargirMarine.class, LOTREntityPelargirMarine.class};
			}
			if (this == PINNATH_GELIN) {
				return new Class[]{LOTREntityPinnathGelinSoldier.class, LOTREntityPinnathGelinSoldier.class};
			}
			if (this == BLACKROOT_VALE) {
				return new Class[]{LOTREntityBlackrootArcher.class, LOTREntityBlackrootSoldier.class};
			}
			if (this == LAMEDON) {
				return new Class[]{LOTREntityLamedonSoldier.class, LOTREntityLamedonArcher.class};
			}
			if (this == DOL_AMROTH) {
				return new Class[]{LOTREntityDolAmrothSoldier.class, LOTREntitySwanKnight.class};
			}
			return null;
		}
	}

}
