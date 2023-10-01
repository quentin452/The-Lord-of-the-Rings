package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenMordorWargPit extends LOTRWorldGenWargPitBase {
	public LOTRWorldGenMordorWargPit(boolean flag) {
		super(flag);
	}

	@Override
	public void associateGroundBlocks() {
		addBlockMetaAliasOption("GROUND", 4, LOTRMod.rock, 0);
		addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorDirt, 0);
		addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorGravel, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingle10, 7);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 3);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 1);
		addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.mordorMoss, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
	}

	@Override
	public LOTREntityNPC getOrc(World world) {
		return new LOTREntityMordorOrc(world);
	}

	@Override
	public LOTREntityNPC getWarg(World world) {
		return new LOTREntityMordorWarg(world);
	}

	@Override
	public void setOrcSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityMordorOrc.class);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.brick;
		brickMeta = 0;
		brickSlabBlock = LOTRMod.slabSingle;
		brickSlabMeta = 1;
		brickStairBlock = LOTRMod.stairsMordorBrick;
		brickWallBlock = LOTRMod.wall;
		brickWallMeta = 1;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 7;
		woolBlock = Blocks.wool;
		woolMeta = 12;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		gateMetalBlock = LOTRMod.gateIronBars;
		tableBlock = LOTRMod.morgulTable;
		banner = LOTRItemBanner.BannerType.MORDOR;
		chestContents = LOTRChestContents.ORC_TENT;
	}

	@Override
	public void setWargSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityMordorWarg.class);
	}
}
