package lotr.common.world.structure2;

import lotr.common.LOTRMod;

import java.util.Random;

public class LOTRWorldGenAngmarForgeTent extends LOTRWorldGenAngmarTent {
	public LOTRWorldGenAngmarForgeTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = LOTRMod.brick2;
		tentMeta = 0;
		fenceBlock = LOTRMod.wall2;
		fenceMeta = 0;
		hasOrcForge = true;
	}
}
