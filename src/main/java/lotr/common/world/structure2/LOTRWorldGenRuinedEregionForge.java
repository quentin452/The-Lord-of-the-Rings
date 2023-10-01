package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRuinedEregionForge extends LOTRWorldGenHighElvenForge {
	public LOTRWorldGenRuinedEregionForge(boolean flag) {
		super(flag);
		ruined = true;
		roofBlock = LOTRMod.clayTileDyed;
		roofMeta = 8;
		roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
	}

	@Override
	public LOTREntityElf getElf(World world) {
		return null;
	}

	@Override
	public void placeBrick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 2);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 3);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 4);
			}
		}
	}

	@Override
	public void placePillar(World world, int i, int j, int k, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		if (random.nextInt(3) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 11);
		} else {
			setBlockAndMetadata(world, i, j, k, LOTRMod.pillar, 10);
		}
	}

	@Override
	public void placeRoof(World world, int i, int j, int k, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		super.placeRoof(world, i, j, k, random);
	}

	@Override
	public void placeRoofStairs(World world, int i, int j, int k, int meta, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		super.placeRoofStairs(world, i, j, k, meta, random);
	}

	@Override
	public void placeSlab(World world, int i, int j, int k, boolean flag, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 5 | (flag ? 8 : 0));
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 6 | (flag ? 8 : 0));
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 7 | (flag ? 8 : 0));
			}
		}
	}

	@Override
	public void placeStairs(World world, int i, int j, int k, int meta, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrick, meta);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickMossy, meta);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.stairsHighElvenBrickCracked, meta);
			}
		}
	}

	@Override
	public void placeWall(World world, int i, int j, int k, Random random) {
		if (random.nextInt(12) == 0) {
			return;
		}
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 11);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 12);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, LOTRMod.wall2, 13);
			}
		}
	}
}
