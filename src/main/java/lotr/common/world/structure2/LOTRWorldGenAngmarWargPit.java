package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenAngmarWargPit extends LOTRWorldGenWargPitBase {
	public LOTRWorldGenAngmarWargPit(boolean flag) {
		super(flag);
	}

	@Override
	public void associateGroundBlocks() {
		super.associateGroundBlocks();
		clearScanAlias("GROUND_COVER");
		addBlockMetaAliasOption("GROUND_COVER", 1, Blocks.snow_layer, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
	}

	@Override
	public LOTREntityNPC getOrc(World world) {
		return new LOTREntityAngmarOrc(world);
	}

	@Override
	public LOTREntityNPC getWarg(World world) {
		return new LOTREntityAngmarWarg(world);
	}

	@Override
	public void setOrcSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityAngmarOrc.class);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = LOTRMod.brick2;
		brickMeta = 0;
		brickSlabBlock = LOTRMod.slabSingle3;
		brickSlabMeta = 3;
		brickStairBlock = LOTRMod.stairsAngmarBrick;
		brickWallBlock = LOTRMod.wall2;
		brickWallMeta = 0;
		pillarBlock = LOTRMod.pillar2;
		pillarMeta = 4;
		woolBlock = Blocks.wool;
		woolMeta = 15;
		carpetBlock = Blocks.carpet;
		carpetMeta = 15;
		tableBlock = LOTRMod.angmarTable;
		banner = LOTRItemBanner.BannerType.ANGMAR;
		chestContents = LOTRChestContents.ANGMAR_TENT;
	}

	@Override
	public void setWargSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityAngmarWarg.class);
	}
}
