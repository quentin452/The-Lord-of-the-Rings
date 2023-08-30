package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNomadStructure extends LOTRWorldGenStructureBase2 {
	public Block tentBlock;
	public int tentMeta;
	public Block tent2Block;
	public int tent2Meta;
	public Block carpetBlock;
	public int carpetMeta;
	public Block carpet2Block;
	public int carpet2Meta;
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
	public Block bedBlock;
	public Block trapdoorBlock;

	protected LOTRWorldGenNomadStructure(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomNomadWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomUmbarWeapon(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad)};
		return items[random.nextInt(items.length)].copy();
	}

	public void laySandBase(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, Blocks.sand, 0);
		int j1 = j - 1;
		while (getY(j1) >= 0 && !isOpaque(world, i, j1, k)) {
			if (isOpaque(world, i, j1 - 1, k)) {
				setBlockAndMetadata(world, i, j1, k, Blocks.sandstone, 0);
			} else {
				setBlockAndMetadata(world, i, j1, k, Blocks.sand, 0);
			}
			setGrassToDirt(world, i, j1 - 1, k);
			--j1;
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = Blocks.wool;
		tentMeta = 0;
		tent2Block = Blocks.wool;
		tent2Meta = 12;
		carpetBlock = Blocks.carpet;
		carpetMeta = 0;
		carpet2Block = Blocks.carpet;
		carpet2Meta = 12;
		int randomWood = random.nextInt(3);
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
				trapdoorBlock = Blocks.trapdoor;
				break;
			case 1:
				plankBlock = LOTRMod.planks2;
				plankMeta = 2;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 2;
				plankStairBlock = LOTRMod.stairsCedar;
				fenceBlock = LOTRMod.fence2;
				fenceMeta = 2;
				fenceGateBlock = LOTRMod.fenceGateCedar;
				beamBlock = LOTRMod.woodBeam4;
				beamMeta = 2;
				trapdoorBlock = LOTRMod.trapdoorCedar;
				break;
			case 2:
				plankBlock = LOTRMod.planks;
				plankMeta = 14;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 6;
				plankStairBlock = LOTRMod.stairsDatePalm;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 14;
				fenceGateBlock = LOTRMod.fenceGateDatePalm;
				trapdoorBlock = LOTRMod.trapdoorDatePalm;
				beamBlock = LOTRMod.woodBeam3;
				beamMeta = 2;
				break;
			default:
				break;
		}
		bedBlock = LOTRMod.strawBed;
	}
}
