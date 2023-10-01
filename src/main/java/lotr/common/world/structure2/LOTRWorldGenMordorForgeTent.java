package lotr.common.world.structure2;

import lotr.common.LOTRMod;

import java.util.Random;

public class LOTRWorldGenMordorForgeTent extends LOTRWorldGenMordorTent {
	public LOTRWorldGenMordorForgeTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = LOTRMod.brick;
		tentMeta = 0;
		fenceBlock = LOTRMod.wall;
		fenceMeta = 1;
		hasOrcForge = true;
	}
}
