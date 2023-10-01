package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityBreeHobbit;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBreeHobbitBurrow extends LOTRWorldGenHobbitBurrow {
	public LOTRWorldGenBreeHobbitBurrow(boolean flag) {
		super(flag);
	}

	@Override
	public LOTREntityHobbit createHobbit(World world) {
		return new LOTREntityBreeHobbit(world);
	}

	@Override
	public String[] getHobbitCoupleAndHomeNames(Random random) {
		return LOTRNames.getBreeHobbitCoupleAndHomeNames(random);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		LOTRWorldGenBreeHouse breeBlockProxy = new LOTRWorldGenBreeHouse(false);
		breeBlockProxy.setupRandomBlocks(random);
		brickBlock = breeBlockProxy.brickBlock;
		brickMeta = breeBlockProxy.brickMeta;
		floorBlock = Blocks.cobblestone;
		floorMeta = 0;
		plankBlock = breeBlockProxy.plankBlock;
		plankMeta = breeBlockProxy.plankMeta;
		plankSlabBlock = breeBlockProxy.plankSlabBlock;
		plankSlabMeta = breeBlockProxy.plankSlabMeta;
		plankStairBlock = breeBlockProxy.plankStairBlock;
		fenceBlock = breeBlockProxy.fenceBlock;
		fenceMeta = breeBlockProxy.fenceMeta;
		fenceGateBlock = breeBlockProxy.fenceGateBlock;
		doorBlock = breeBlockProxy.doorBlock;
		beamBlock = breeBlockProxy.beamBlock;
		beamMeta = breeBlockProxy.beamMeta;
		tableBlock = breeBlockProxy.tableBlock;
		burrowLoot = LOTRChestContents.BREE_HOUSE;
		foodPool = LOTRFoods.BREE;
	}
}
