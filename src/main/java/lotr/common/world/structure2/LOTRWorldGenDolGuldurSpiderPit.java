package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDolGuldurSpiderPit extends LOTRWorldGenWargPitBase {
	public LOTRWorldGenDolGuldurSpiderPit(boolean flag) {
		super(flag);
	}

	@Override
	public void associateGroundBlocks() {
		super.associateGroundBlocks();
		clearScanAlias("GROUND_COVER");
		addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
		setBlockAliasChance("GROUND_COVER", 0.04f);
	}

	@Override
	public LOTREntityNPC getOrc(World world) {
		return new LOTREntityDolGuldurOrc(world);
	}

	@Override
	public LOTREntityNPC getWarg(World world) {
		return new LOTREntityMirkwoodSpider(world);
	}

	@Override
	public void setOrcSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityDolGuldurOrc.class);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.brick2;
		brickMeta = 8;
		brickSlabBlock = LOTRMod.slabSingle4;
		brickSlabMeta = 5;
		brickStairBlock = LOTRMod.stairsDolGuldurBrick;
		brickWallBlock = LOTRMod.wall2;
		brickWallMeta = 8;
		pillarBlock = beamBlock;
		pillarMeta = beamMeta;
		woolBlock = Blocks.wool;
		woolMeta = 15;
		carpetBlock = Blocks.carpet;
		carpetMeta = 15;
		gateMetalBlock = LOTRMod.gateIronBars;
		tableBlock = LOTRMod.dolGuldurTable;
		banner = LOTRItemBanner.BannerType.DOL_GULDUR;
		chestContents = LOTRChestContents.DOL_GULDUR_TENT;
	}

	@Override
	public void setWargSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityMirkwoodSpider.class);
	}
}
