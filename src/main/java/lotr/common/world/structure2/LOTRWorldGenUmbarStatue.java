package lotr.common.world.structure2;

import java.util.Random;

public class LOTRWorldGenUmbarStatue extends LOTRWorldGenSouthronStatue {
	public LOTRWorldGenUmbarStatue(boolean flag) {
		super(flag);
	}

	@Override
	public String getRandomStatueStrscan(Random random) {
		String[] statues = {"pillar", "snake", "pharazon"};
		return "umbar_statue_" + statues[random.nextInt(statues.length)];
	}

	@Override
	public boolean isUmbar() {
		return true;
	}
}
