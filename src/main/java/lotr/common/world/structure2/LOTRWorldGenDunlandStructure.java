package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenDunlandStructure extends LOTRWorldGenStructureBase2 {
	public Block floorBlock;
	public int floorMeta;
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
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public int barsMeta;
	public Block bedBlock;

	protected LOTRWorldGenDunlandStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomDunlandWeapon(Random random) {
		ItemStack[] items = {new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.daggerIron), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.dunlendingClub), new ItemStack(LOTRMod.dunlendingTrident)};
		return items[random.nextInt(items.length)].copy();
	}

	public void placeDunlandItemFrame(World world, Random random, int i, int j, int k, int direction) {
		ItemStack[] items = {new ItemStack(Items.bone), new ItemStack(LOTRMod.fur), new ItemStack(Items.flint), new ItemStack(Items.iron_sword), new ItemStack(Items.stone_sword), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearStone), new ItemStack(Items.bow), new ItemStack(Items.arrow), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.skullCup)};
		ItemStack item = items[random.nextInt(items.length)].copy();
		spawnItemFrame(world, i, j, k, direction, item);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomFloor = random.nextInt(5);
		switch (randomFloor) {
			case 0:
				floorBlock = Blocks.cobblestone;
				floorMeta = 0;
				break;
			case 1:
				floorBlock = Blocks.hardened_clay;
				floorMeta = 0;
				break;
			case 2:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 7;
				break;
			case 3:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 12;
				break;
			case 4:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 15;
				break;
			default:
				break;
		}
		if (random.nextBoolean()) {
			woodBlock = Blocks.log;
			woodMeta = 1;
			plankBlock = Blocks.planks;
			plankMeta = 1;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 1;
			plankStairBlock = Blocks.spruce_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 1;
			fenceGateBlock = LOTRMod.fenceGateSpruce;
			doorBlock = LOTRMod.doorSpruce;
		} else {
			int randomWood = random.nextInt(2);
			if (randomWood == 0) {
				woodBlock = Blocks.log;
				woodMeta = 0;
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				doorBlock = Blocks.wooden_door;
			} else {
				woodBlock = LOTRMod.wood5;
				woodMeta = 0;
				plankBlock = LOTRMod.planks2;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsPine;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 4;
				fenceGateBlock = LOTRMod.fenceGatePine;
				doorBlock = LOTRMod.doorPine;
			}
		}
		roofBlock = LOTRMod.thatch;
		roofMeta = 0;
		roofSlabBlock = LOTRMod.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = LOTRMod.stairsThatch;
		if (random.nextBoolean()) {
			barsBlock = Blocks.iron_bars;
		} else {
			barsBlock = LOTRMod.bronzeBars;
		}
		barsMeta = 0;
		bedBlock = random.nextBoolean() ? LOTRMod.furBed : LOTRMod.strawBed;
	}
}
