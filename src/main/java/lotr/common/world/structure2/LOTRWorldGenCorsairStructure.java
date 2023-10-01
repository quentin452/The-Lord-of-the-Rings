package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class LOTRWorldGenCorsairStructure extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block pillarSlabBlock;
	public int pillarSlabMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;

	protected LOTRWorldGenCorsairStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomCorsairWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.nearHaradBow)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.brick6;
		brickMeta = 6;
		brickSlabBlock = LOTRMod.slabSingle13;
		brickSlabMeta = 2;
		brickStairBlock = LOTRMod.stairsUmbarBrick;
		brickWallBlock = LOTRMod.wall5;
		brickWallMeta = 0;
		pillarBlock = LOTRMod.pillar2;
		pillarMeta = 10;
		pillarSlabBlock = LOTRMod.slabSingle13;
		pillarSlabMeta = 4;
		int randomWood = random.nextInt(2);
		if (randomWood == 0) {
			plankBlock = LOTRMod.planks3;
			plankMeta = 3;
			plankSlabBlock = LOTRMod.woodSlabSingle5;
			plankSlabMeta = 3;
			plankStairBlock = LOTRMod.stairsPalm;
			fenceBlock = LOTRMod.fence3;
			fenceMeta = 3;
			fenceGateBlock = LOTRMod.fenceGatePalm;
		} else {
			plankBlock = LOTRMod.planks2;
			plankMeta = 2;
			plankSlabBlock = LOTRMod.woodSlabSingle3;
			plankSlabMeta = 2;
			plankStairBlock = LOTRMod.stairsCedar;
			fenceBlock = LOTRMod.fence2;
			fenceMeta = 2;
			fenceGateBlock = LOTRMod.fenceGateCedar;
		}
	}
}
