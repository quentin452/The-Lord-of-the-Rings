package lotr.common.world.structure2;

public abstract class LOTRWorldGenUmbarFortWall extends LOTRWorldGenSouthronFortWall {
	protected LOTRWorldGenUmbarFortWall(boolean flag) {
		super(flag);
	}

	public static class Long extends LOTRWorldGenSouthronFortWall.Long {
		public Long(boolean flag) {
			super(flag);
		}

		@Override
		public boolean isUmbar() {
			return true;
		}
	}

	public static class Short extends LOTRWorldGenSouthronFortWall.Short {
		public Short(boolean flag) {
			super(flag);
		}

		@Override
		public boolean isUmbar() {
			return true;
		}
	}

}
