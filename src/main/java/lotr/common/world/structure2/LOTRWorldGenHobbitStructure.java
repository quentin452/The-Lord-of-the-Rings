package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenHobbitStructure extends LOTRWorldGenStructureBase2 {
	public boolean isWealthy;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block beamBlock;
	public int beamMeta;
	public Block doorBlock;
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block fence2Block;
	public int fence2Meta;
	public Block fenceGate2Block;
	public Block floorBlock;
	public int floorMeta;
	public Block floorStairBlock;
	public Block brickBlock;
	public int brickMeta;
	public Block brickStairBlock;
	public Block wallBlock;
	public int wallMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block carpetBlock;
	public int carpetMeta;
	public Block chandelierBlock;
	public int chandelierMeta;
	public Block barsBlock;
	public Block outFenceBlock;
	public int outFenceMeta;
	public Block outFenceGateBlock;
	public Block pathBlock;
	public int pathMeta;
	public Block pathSlabBlock;
	public int pathSlabMeta;
	public Block hedgeBlock;
	public int hedgeMeta;
	public Block tileSlabBlock;
	public int tileSlabMeta;
	public Block bedBlock;
	public Block tableBlock;
	public Block gateBlock;
	public Block plateBlock;
	public String maleName;
	public String femaleName;
	public String homeName1;
	public String homeName2;

	protected LOTRWorldGenHobbitStructure(boolean flag) {
		super(flag);
	}

	public LOTREntityHobbit createHobbit(World world) {
		return new LOTREntityHobbit(world);
	}

	public String[] getHobbitCoupleAndHomeNames(Random random) {
		return LOTRNames.getHobbitCoupleAndHomeNames(random);
	}

	public ItemStack getRandomHobbitDecoration(World world, Random random) {
		if (random.nextInt(3) == 0) {
			return getRandomFlower(world, random);
		}
		ItemStack[] items = {new ItemStack(LOTRMod.rollingPin), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather), new ItemStack(Items.clock), new ItemStack(LOTRMod.leatherHat), new ItemStack(LOTRMod.hobbitPipe), new ItemStack(Blocks.brown_mushroom), new ItemStack(Blocks.red_mushroom)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean makeWealthy(Random random) {
		return random.nextInt(5) == 0;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomChandelier;
		int randomWood2;
		isWealthy = makeWealthy(random);
		int randomWood = random.nextInt(5);
		switch (randomWood) {
			case 0:
				plankBlock = LOTRMod.planks;
				plankMeta = 0;
				plankSlabBlock = LOTRMod.woodSlabSingle;
				plankSlabMeta = 0;
				plankStairBlock = LOTRMod.stairsShirePine;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 0;
				fenceGateBlock = LOTRMod.fenceGateShirePine;
				beamBlock = LOTRMod.woodBeam1;
				beamMeta = 0;
				doorBlock = Blocks.wooden_door;
				break;
			case 1:
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				beamBlock = LOTRMod.woodBeamV1;
				beamMeta = 0;
				doorBlock = Blocks.wooden_door;
				break;
			case 2:
				plankBlock = Blocks.planks;
				plankMeta = 2;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 2;
				plankStairBlock = Blocks.birch_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 2;
				fenceGateBlock = LOTRMod.fenceGateBirch;
				beamBlock = LOTRMod.woodBeamV1;
				beamMeta = 0;
				doorBlock = LOTRMod.doorBirch;
				break;
			case 3:
				plankBlock = LOTRMod.planks2;
				plankMeta = 0;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 0;
				plankStairBlock = LOTRMod.stairsChestnut;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 0;
				fenceGateBlock = LOTRMod.fenceGateChestnut;
				beamBlock = LOTRMod.woodBeam4;
				beamMeta = 0;
				doorBlock = LOTRMod.doorChestnut;
				break;
			case 4:
				plankBlock = LOTRMod.planks;
				plankMeta = 9;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = LOTRMod.stairsBeech;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 9;
				fenceGateBlock = LOTRMod.fenceGateBeech;
				beamBlock = LOTRMod.woodBeam2;
				beamMeta = 1;
				doorBlock = LOTRMod.doorBeech;
				break;
			default:
				break;
		}
		if (random.nextBoolean()) {
			doorBlock = LOTRMod.doorShirePine;
		}
		if (isWealthy) {
			randomWood2 = random.nextInt(3);
			switch (randomWood2) {
				case 0:
					plank2Block = LOTRMod.planks;
					plank2Meta = 4;
					plank2SlabBlock = LOTRMod.woodSlabSingle;
					plank2SlabMeta = 4;
					plank2StairBlock = LOTRMod.stairsApple;
					fence2Block = LOTRMod.fence;
					fence2Meta = 4;
					fenceGate2Block = LOTRMod.fenceGateApple;
					break;
				case 1:
					plank2Block = LOTRMod.planks;
					plank2Meta = 5;
					plank2SlabBlock = LOTRMod.woodSlabSingle;
					plank2SlabMeta = 5;
					plank2StairBlock = LOTRMod.stairsPear;
					fence2Block = LOTRMod.fence;
					fence2Meta = 5;
					fenceGate2Block = LOTRMod.fenceGatePear;
					break;
				case 2:
					plank2Block = LOTRMod.planks;
					plank2Meta = 6;
					plank2SlabBlock = LOTRMod.woodSlabSingle;
					plank2SlabMeta = 6;
					plank2StairBlock = LOTRMod.stairsCherry;
					fence2Block = LOTRMod.fence;
					fence2Meta = 6;
					fenceGate2Block = LOTRMod.fenceGateCherry;
					break;
				default:
					break;
			}
		} else {
			randomWood2 = random.nextInt(3);
			switch (randomWood2) {
				case 0:
					plank2Block = Blocks.planks;
					plank2Meta = 0;
					plank2SlabBlock = Blocks.wooden_slab;
					plank2SlabMeta = 0;
					plank2StairBlock = Blocks.oak_stairs;
					fence2Block = Blocks.fence;
					fence2Meta = 0;
					fenceGate2Block = Blocks.fence_gate;
					break;
				case 1:
					plank2Block = Blocks.planks;
					plank2Meta = 1;
					plank2SlabBlock = Blocks.wooden_slab;
					plank2SlabMeta = 1;
					plank2StairBlock = Blocks.spruce_stairs;
					fence2Block = Blocks.fence;
					fence2Meta = 1;
					fenceGate2Block = LOTRMod.fenceGateSpruce;
					break;
				case 2:
					plank2Block = LOTRMod.planks2;
					plank2Meta = 9;
					plank2SlabBlock = LOTRMod.woodSlabSingle4;
					plank2SlabMeta = 1;
					plank2StairBlock = LOTRMod.stairsWillow;
					fence2Block = LOTRMod.fence2;
					fence2Meta = 9;
					fenceGate2Block = LOTRMod.fenceGateWillow;
					break;
				default:
					break;
			}
		}
		int randomFloor = random.nextInt(3);
		switch (randomFloor) {
			case 0:
				floorBlock = Blocks.brick_block;
				floorMeta = 0;
				floorStairBlock = Blocks.brick_stairs;
				break;
			case 1:
				floorBlock = Blocks.cobblestone;
				floorMeta = 0;
				floorStairBlock = Blocks.stone_stairs;
				break;
			case 2:
				floorBlock = Blocks.stonebrick;
				floorMeta = 0;
				floorStairBlock = Blocks.stone_brick_stairs;
				break;
			default:
				break;
		}
		brickBlock = Blocks.brick_block;
		brickMeta = 0;
		brickStairBlock = Blocks.brick_stairs;
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
		int randomCarpet = random.nextInt(5);
		switch (randomCarpet) {
			case 0:
				carpetBlock = Blocks.carpet;
				carpetMeta = 15;
				break;
			case 1:
				carpetBlock = Blocks.carpet;
				carpetMeta = 14;
				break;
			case 2:
				carpetBlock = Blocks.carpet;
				carpetMeta = 13;
				break;
			case 3:
				carpetBlock = Blocks.carpet;
				carpetMeta = 12;
				break;
			case 4:
				carpetBlock = Blocks.carpet;
				carpetMeta = 7;
				break;
			default:
				break;
		}
		if (isWealthy) {
			randomChandelier = random.nextInt(2);
			if (randomChandelier == 0) {
				chandelierBlock = LOTRMod.chandelier;
				chandelierMeta = 2;
			} else {
				chandelierBlock = LOTRMod.chandelier;
				chandelierMeta = 3;
			}
		} else {
			randomChandelier = random.nextInt(2);
			if (randomChandelier == 0) {
				chandelierBlock = LOTRMod.chandelier;
				chandelierMeta = 0;
			} else {
				chandelierBlock = LOTRMod.chandelier;
				chandelierMeta = 1;
			}
		}
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : LOTRMod.bronzeBars;
		if (random.nextInt(3) == 0) {
			outFenceBlock = Blocks.fence;
		} else {
			outFenceBlock = Blocks.cobblestone_wall;
		}
		outFenceMeta = 0;
		outFenceGateBlock = Blocks.fence_gate;
		if (random.nextInt(3) == 0) {
			pathBlock = LOTRMod.dirtPath;
			pathMeta = 0;
			pathSlabBlock = LOTRMod.slabSingleDirt;
			pathSlabMeta = 1;
		} else {
			pathBlock = Blocks.gravel;
			pathMeta = 0;
			pathSlabBlock = LOTRMod.slabSingleGravel;
			pathSlabMeta = 0;
		}
		hedgeBlock = Blocks.leaves;
		hedgeMeta = 4;
		if (random.nextBoolean()) {
			tileSlabBlock = LOTRMod.slabClayTileDyedSingle2;
			tileSlabMeta = 4;
		} else {
			tileSlabBlock = LOTRMod.slabClayTileDyedSingle2;
			tileSlabMeta = 5;
		}
		bedBlock = Blocks.bed;
		tableBlock = LOTRMod.hobbitTable;
		int randomGate = random.nextInt(4);
		switch (randomGate) {
			case 0:
				gateBlock = LOTRMod.gateHobbitGreen;
				break;
			case 1:
				gateBlock = LOTRMod.gateHobbitBlue;
				break;
			case 2:
				gateBlock = LOTRMod.gateHobbitRed;
				break;
			case 3:
				gateBlock = LOTRMod.gateHobbitYellow;
				break;
			default:
				break;
		}
		plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.plateBlock;
		String[] hobbitNames = getHobbitCoupleAndHomeNames(random);
		maleName = hobbitNames[0];
		femaleName = hobbitNames[1];
		homeName1 = hobbitNames[2];
		homeName2 = hobbitNames[3];
	}

	public void spawnHobbitCouple(World world, int i, int j, int k, int homeRange) {
		LOTREntityHobbit hobbitMale = createHobbit(world);
		hobbitMale.familyInfo.setName(maleName);
		hobbitMale.familyInfo.setMale(true);
		spawnNPCAndSetHome(hobbitMale, world, i, j, k, homeRange);
		LOTREntityHobbit hobbitFemale = createHobbit(world);
		hobbitFemale.familyInfo.setName(femaleName);
		hobbitFemale.familyInfo.setMale(false);
		spawnNPCAndSetHome(hobbitFemale, world, i, j, k, homeRange);
		int maxChildren = hobbitMale.familyInfo.getRandomMaxChildren();
		hobbitMale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.hobbitRing));
		hobbitMale.familyInfo.spouseUniqueID = hobbitFemale.getUniqueID();
		hobbitMale.familyInfo.setMaxBreedingDelay();
		hobbitMale.familyInfo.maxChildren = maxChildren;
		hobbitFemale.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.hobbitRing));
		hobbitFemale.familyInfo.spouseUniqueID = hobbitMale.getUniqueID();
		hobbitFemale.familyInfo.setMaxBreedingDelay();
		hobbitFemale.familyInfo.maxChildren = maxChildren;
	}

	public static Block getRandomCakeBlock(Random random) {
		int i = random.nextInt(5);
		switch (i) {
			case 0:
				return Blocks.cake;
			case 1:
				return LOTRMod.appleCrumble;
			case 2:
				return LOTRMod.cherryPie;
			case 3:
				return LOTRMod.berryPie;
			case 4:
				return LOTRMod.marzipanBlock;
			default:
				break;
		}
		return Blocks.cake;
	}
}
