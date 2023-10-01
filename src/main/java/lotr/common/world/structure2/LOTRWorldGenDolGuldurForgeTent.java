package lotr.common.world.structure2;

import lotr.common.LOTRMod;

import java.util.Random;

public class LOTRWorldGenDolGuldurForgeTent extends LOTRWorldGenDolGuldurTent {
	public LOTRWorldGenDolGuldurForgeTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = LOTRMod.brick2;
		tentMeta = 8;
		fenceBlock = LOTRMod.wall2;
		fenceMeta = 8;
		hasOrcForge = true;
	}
}
