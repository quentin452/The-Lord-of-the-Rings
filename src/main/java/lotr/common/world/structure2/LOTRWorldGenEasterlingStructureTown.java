package lotr.common.world.structure2;

public abstract class LOTRWorldGenEasterlingStructureTown extends LOTRWorldGenEasterlingStructure {
	protected LOTRWorldGenEasterlingStructureTown(boolean flag) {
		super(flag);
	}

	@Override
	public boolean useTownBlocks() {
		return true;
	}
}
