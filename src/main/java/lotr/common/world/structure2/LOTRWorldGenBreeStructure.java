package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRWorldGenBreeStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block floorBlock;
	public int floorMeta;
	public Block stoneWallBlock;
	public int stoneWallMeta;
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
	public Block trapdoorBlock;
	public Block beamBlock;
	public int beamMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block carpetBlock;
	public int carpetMeta;
	public Block bedBlock;
	public Block tableBlock;

	protected LOTRWorldGenBreeStructure(boolean flag) {
		super(flag);
	}

	public static Block getRandomPieBlock(Random random) {
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
		return LOTRMod.appleCrumble;
	}

	public ItemStack getRandomBreeWeapon(Random random) {
		ItemStack[] items = {new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.rollingPin)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomTavernItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.rollingPin), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.wooden_axe), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather)};
		return items[random.nextInt(items.length)].copy();
	}

	public void placeRandomFloor(World world, Random random, int i, int j, int k) {
		float randFloor = random.nextFloat();
		if (randFloor < 0.25f) {
			setBlockAndMetadata(world, i, j, k, Blocks.grass, 0);
		} else if (randFloor < 0.5f) {
			setBlockAndMetadata(world, i, j, k, Blocks.dirt, 1);
		} else if (randFloor < 0.75f) {
			setBlockAndMetadata(world, i, j, k, Blocks.gravel, 0);
		} else {
			setBlockAndMetadata(world, i, j, k, LOTRMod.dirtPath, 0);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.cobblebrick;
		brickMeta = 0;
		brick2Block = Blocks.stonebrick;
		brick2Meta = 0;
		brick2SlabBlock = Blocks.stone_slab;
		brick2SlabMeta = 5;
		brick2StairBlock = Blocks.stone_brick_stairs;
		brick2WallBlock = LOTRMod.wallStoneV;
		brick2WallMeta = 1;
		floorBlock = Blocks.cobblestone;
		floorMeta = 0;
		stoneWallBlock = Blocks.cobblestone_wall;
		stoneWallMeta = 0;
		int randomWood = random.nextInt(7);
		switch (randomWood) {
			case 0:
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
				break;
			case 1:
				plankBlock = LOTRMod.planks;
				plankMeta = 9;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = LOTRMod.stairsBeech;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 9;
				fenceGateBlock = LOTRMod.fenceGateBeech;
				beamBlock = LOTRMod.woodBeam2;
				beamMeta = 1;
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
				beamMeta = 2;
				break;
			case 3:
				plankBlock = Blocks.planks;
				plankMeta = 1;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 1;
				plankStairBlock = Blocks.spruce_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 1;
				fenceGateBlock = LOTRMod.fenceGateSpruce;
				beamBlock = LOTRMod.woodBeamV1;
				beamMeta = 1;
				break;
			case 4:
				woodBlock = LOTRMod.wood4;
				woodMeta = 0;
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
				break;
			case 5:
				woodBlock = LOTRMod.wood3;
				woodMeta = 0;
				plankBlock = LOTRMod.planks;
				plankMeta = 12;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsMaple;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 12;
				fenceGateBlock = LOTRMod.fenceGateMaple;
				beamBlock = LOTRMod.woodBeam3;
				beamMeta = 0;
				break;
			case 6:
				woodBlock = LOTRMod.wood7;
				woodMeta = 0;
				plankBlock = LOTRMod.planks2;
				plankMeta = 12;
				plankSlabBlock = LOTRMod.woodSlabSingle4;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsAspen;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 12;
				fenceGateBlock = LOTRMod.fenceGateAspen;
				beamBlock = LOTRMod.woodBeam7;
				beamMeta = 0;
				break;
			default:
				break;
		}
		doorBlock = LOTRMod.doorBeech;
		trapdoorBlock = LOTRMod.trapdoorBeech;
		roofBlock = LOTRMod.thatch;
		roofMeta = 0;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = LOTRMod.stairsThatch;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		bedBlock = LOTRMod.strawBed;
		tableBlock = LOTRMod.breeTable;
	}
}
