package lotr.common.world;

import net.minecraft.world.storage.*;

public class LOTRWorldInfo extends DerivedWorldInfo {
	public long lotrTotalTime;
	public long lotrWorldTime;

	public LOTRWorldInfo(WorldInfo worldinfo) {
		super(worldinfo);
	}

	@Override
	public long getWorldTime() {
		return lotrWorldTime;
	}

	@Override
	public long getWorldTotalTime() {
		return lotrTotalTime;
	}

	public void lotr_setTotalTime(long time) {
		lotrTotalTime = time;
	}

	public void lotr_setWorldTime(long time) {
		lotrWorldTime = time;
	}

}
