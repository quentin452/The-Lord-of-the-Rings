package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDwarvenMineEntrance extends LOTRWorldGenStructureBase2 {
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block logBlock;
	public int logMeta;
	public Block fenceBlock;
	public int fenceMeta;
	public boolean isRuined;

	public LOTRWorldGenDwarvenMineEntrance(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int i12;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, usingPlayer != null ? 5 : 0);
		setupRandomBlocks(random);
		int coordDepth = 40;
		if (usingPlayer != null) {
			coordDepth = Math.max(getY(-30), 5);
		}
		int relDepth = coordDepth - originY;
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = -4; k1 <= 4; ++k1) {
				int j12;
				int i2 = Math.abs(i12);
				int k2 = Math.abs(k1);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i12, j12, k1);
				}
				if (!isRuined) {
					setBlockAndMetadata(world, i12, 0, k1, plankBlock, plankMeta);
					if (i2 == 4 && k2 >= 2 || k2 == 4 && i2 >= 2) {
						setBlockAndMetadata(world, i12, 1, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
						for (j12 = 2; j12 <= 3; ++j12) {
							setBlockAndMetadata(world, i12, j12, k1, fenceBlock, fenceMeta);
						}
					}
					if (i2 == 4 || k2 == 4) {
						setBlockAndMetadata(world, i12, 4, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 0 || k2 == 0) {
						setBlockAndMetadata(world, i12, 4, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 0 && k2 == 0) {
						for (j12 = 1; j12 <= 3; ++j12) {
							setBlockAndMetadata(world, i12, j12, k1, fenceBlock, fenceMeta);
						}
					}
					if (i2 == 4 || k2 == 4 || i2 == 0 || k2 == 0 || i2 + k2 <= 2) {
						setBlockAndMetadata(world, i12, 5, k1, plankSlabBlock, plankSlabMeta);
					}
				} else if (i2 == 4 || k2 == 4) {
					setBlockAndMetadata(world, i12, 0, k1, LOTRMod.pillar, 0);
				} else {
					setAir(world, i12, 0, k1);
				}
				if (i2 != 4 || k2 != 4) {
					continue;
				}
				for (j12 = 1; j12 <= 3; ++j12) {
					setBlockAndMetadata(world, i12, j12, k1, LOTRMod.pillar, 0);
				}
				if (isRuined) {
					continue;
				}
				setBlockAndMetadata(world, i12, 4, k1, LOTRMod.brick3, 12);
				setBlockAndMetadata(world, i12, 5, k1, LOTRMod.pillar, 0);
			}
		}
		for (j1 = -1; j1 > relDepth && getY(j1) >= 0; --j1) {
			for (i1 = -4; i1 <= 4; ++i1) {
				for (int k12 = -4; k12 <= 4; ++k12) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k12);
					if (i2 == 4 || k2 == 4) {
						if (isRuined && random.nextInt(20) == 0) {
							setAir(world, i1, j1, k12);
							continue;
						}
						if (isRuined && random.nextInt(4) == 0) {
							setBlockAndMetadata(world, i1, j1, k12, LOTRMod.brick4, 5);
							continue;
						}
						setBlockAndMetadata(world, i1, j1, k12, LOTRMod.brick, 6);
						continue;
					}
					setAir(world, i1, j1, k12);
				}
			}
			setBlockAndMetadata(world, -3, j1, -3, LOTRMod.pillar, 0);
			setBlockAndMetadata(world, -3, j1, 3, LOTRMod.pillar, 0);
			setBlockAndMetadata(world, 3, j1, -3, LOTRMod.pillar, 0);
			setBlockAndMetadata(world, 3, j1, 3, LOTRMod.pillar, 0);
			if (isRuined || IntMath.mod(j1, 6) != 3) {
				continue;
			}
			setBlockAndMetadata(world, -3, j1, -3, LOTRMod.brick3, 12);
			setBlockAndMetadata(world, -3, j1, 3, LOTRMod.brick3, 12);
			setBlockAndMetadata(world, 3, j1, -3, LOTRMod.brick3, 12);
			setBlockAndMetadata(world, 3, j1, 3, LOTRMod.brick3, 12);
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				if (isOpaque(world, i12, relDepth, k1)) {
					continue;
				}
				setBlockAndMetadata(world, i12, relDepth, k1, Blocks.stone, 0);
			}
		}
		if (isRuined) {
			for (i12 = -2; i12 <= 2; ++i12) {
				for (k1 = -2; k1 <= 2; ++k1) {
					int h = 0;
					if (random.nextInt(5) == 0) {
						h += 1 + random.nextInt(1);
					}
					for (int j13 = 0; j13 <= h; ++j13) {
						if (random.nextBoolean()) {
							setBlockAndMetadata(world, i12, relDepth + h, k1, LOTRMod.pillar, 0);
							continue;
						}
						setBlockAndMetadata(world, i12, relDepth + h, k1, Blocks.stone, 0);
					}
				}
			}
		} else {
			for (i12 = -2; i12 <= 2; ++i12) {
				for (k1 = -2; k1 <= 2; ++k1) {
					setBlockAndMetadata(world, i12, relDepth, k1, LOTRMod.pillar, 0);
				}
			}
		}
		if (!isRuined) {
			for (j1 = 1; j1 > relDepth && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, 0, j1, 0, logBlock, logMeta);
				setBlockAndMetadata(world, 0, j1, -1, Blocks.ladder, 2);
				setBlockAndMetadata(world, 0, j1, 1, Blocks.ladder, 3);
				setBlockAndMetadata(world, -1, j1, 0, Blocks.ladder, 5);
				setBlockAndMetadata(world, 1, j1, 0, Blocks.ladder, 4);
			}
		}
		for (j1 = relDepth + 1; j1 <= relDepth + 3; ++j1) {
			for (i1 = -1; i1 <= 1; ++i1) {
				setAir(world, i1, j1, -4);
				setAir(world, i1, j1, 4);
			}
			for (k1 = -1; k1 <= 1; ++k1) {
				setAir(world, -4, j1, k1);
				setAir(world, 4, j1, k1);
			}
		}
		for (int k13 = -1; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, -4, relDepth + 1, k13, LOTRMod.slabSingle, 15);
			setBlockAndMetadata(world, 4, relDepth + 1, k13, LOTRMod.slabSingle, 15);
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, relDepth + 1, -4, LOTRMod.slabSingle, 15);
		}
		if (!isRuined || random.nextInt(3) == 0) {
			setBlockAndMetadata(world, -4, relDepth + 1, 0, LOTRMod.dwarvenForge, 4);
		}
		if (!isRuined || random.nextInt(3) == 0) {
			setBlockAndMetadata(world, 4, relDepth + 1, 0, LOTRMod.dwarvenForge, 5);
		}
		if (!isRuined || random.nextInt(3) == 0) {
			setBlockAndMetadata(world, 0, relDepth + 1, -4, LOTRMod.dwarvenForge, 3);
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		plankBlock = Blocks.planks;
		plankMeta = 1;
		plankSlabBlock = Blocks.wooden_slab;
		plankSlabMeta = 1;
		logBlock = Blocks.log;
		logMeta = 1;
		fenceBlock = Blocks.fence;
		fenceMeta = 1;
	}
}
