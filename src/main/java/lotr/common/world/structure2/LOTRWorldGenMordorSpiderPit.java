package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrcSpiderKeeper;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenMordorSpiderPit extends LOTRWorldGenMordorWargPit {
	public LOTRWorldGenMordorSpiderPit(boolean flag) {
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
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			LOTREntityMordorOrcSpiderKeeper spiderKeeper = new LOTREntityMordorOrcSpiderKeeper(world);
			spawnNPCAndSetHome(spiderKeeper, world, 0, 1, 0, 8);
			return true;
		}
		return false;
	}

	@Override
	public LOTREntityNPC getWarg(World world) {
		return new LOTREntityMordorSpider(world);
	}

	@Override
	public void setWargSpawner(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClass(LOTREntityMordorSpider.class);
	}
}
