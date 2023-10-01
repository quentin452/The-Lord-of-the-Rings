package lotr.common.world.structure2;

import lotr.common.LOTRMod;

import java.util.Random;

public class LOTRWorldGenHarnedorPalisadeRuined extends LOTRWorldGenHarnedorPalisade {
	public LOTRWorldGenHarnedorPalisadeRuined(boolean flag) {
		super(flag);
	}

	@Override
	public boolean isRuined() {
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (random.nextBoolean()) {
			woodBlock = LOTRMod.wood;
			woodMeta = 3;
		}
	}
}
