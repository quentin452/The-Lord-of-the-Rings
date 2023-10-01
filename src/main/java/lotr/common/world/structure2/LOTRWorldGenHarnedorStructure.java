package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class LOTRWorldGenHarnedorStructure extends LOTRWorldGenStructureBase2 {
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
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block boneBlock;
	public int boneMeta;
	public Block bedBlock;
	public Block trapdoorBlock;

	protected LOTRWorldGenHarnedorStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getHarnedorFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad), new ItemStack(LOTRMod.nearHaradBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(LOTRMod.gobletGold), new ItemStack(LOTRMod.gobletSilver), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.ceramicMug), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing), new ItemStack(LOTRMod.doubleFlower, 1, 2), new ItemStack(LOTRMod.doubleFlower, 1, 3)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomHarnedorWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean isRuined() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
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
		doorBlock = LOTRMod.doorCedar;
		trapdoorBlock = LOTRMod.trapdoorCedar;
		int randomWool = random.nextInt(3);
		switch (randomWool) {
			case 0:
				roofBlock = Blocks.wool;
				roofMeta = 1;
				break;
			case 1:
				roofBlock = Blocks.wool;
				roofMeta = 12;
				break;
			case 2:
				roofBlock = Blocks.wool;
				roofMeta = 14;
				break;
			default:
				break;
		}
		int randomFloorWood = random.nextInt(2);
		if (randomFloorWood == 0) {
			plank2Block = LOTRMod.planks2;
			plank2Meta = 11;
			plank2SlabBlock = LOTRMod.woodSlabSingle4;
			plank2SlabMeta = 3;
			plank2StairBlock = LOTRMod.stairsOlive;
		} else {
			plank2Block = LOTRMod.planks3;
			plank2Meta = 0;
			plank2SlabBlock = LOTRMod.woodSlabSingle5;
			plank2SlabMeta = 0;
			plank2StairBlock = LOTRMod.stairsPlum;
		}
		boneBlock = LOTRMod.boneBlock;
		boneMeta = 0;
		bedBlock = LOTRMod.strawBed;
		if (isRuined()) {
			if (random.nextBoolean()) {
				woodBlock = LOTRMod.wood;
				woodMeta = 3;
				plankBlock = LOTRMod.planks;
				plankMeta = 3;
				plankSlabBlock = LOTRMod.woodSlabSingle;
				plankSlabMeta = 3;
				plankStairBlock = LOTRMod.stairsCharred;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 3;
				fenceGateBlock = LOTRMod.fenceGateCharred;
				doorBlock = LOTRMod.doorCharred;
				trapdoorBlock = LOTRMod.trapdoorCharred;
			}
			if (random.nextBoolean()) {
				plank2Block = LOTRMod.planks;
				plank2Meta = 3;
				plank2SlabBlock = LOTRMod.woodSlabSingle;
				plank2SlabMeta = 3;
				plank2StairBlock = LOTRMod.stairsCharred;
			}
		}
	}
}
