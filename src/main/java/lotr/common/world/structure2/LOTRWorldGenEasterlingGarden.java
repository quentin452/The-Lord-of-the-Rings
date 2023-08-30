package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingGarden extends LOTRWorldGenEasterlingStructure {
	public Block leafBlock;
	public int leafMeta;

	public LOTRWorldGenEasterlingGarden(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -10; i1 <= 10; ++i1) {
				for (int k1 = -10; k1 <= 10; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -10; i1 <= 10; ++i1) {
			for (int k1 = -10; k1 <= 10; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (j1 == 0) {
						setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
					} else {
						setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 9; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if ((i2 == 9 && k2 >= 2 && k2 <= 8 || k2 == 9 && i2 >= 2 && i2 <= 8)) {
					setBlockAndMetadata(world, i1, 0, k1, brickBlock, brickMeta);
					if (i2 == 9 && k2 == 2 || k2 == 9 && i2 == 2) {
						for (j1 = 1; j1 <= 6; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
						}
					} else {
						for (j1 = 1; j1 <= 2; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
						}
						if (i2 == 9 && k2 % 2 == 0 || k2 == 9 && i2 % 2 == 0) {
							for (j1 = 3; j1 <= 6; ++j1) {
								setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
							}
						}
					}
				}
				if (i2 >= 2 && i2 <= 8 && k2 >= 2 && k2 <= 8) {
					if (i2 == 2 && k2 >= 5 || k2 == 2 && i2 >= 5) {
						int hedgeHeight;
						if (i2 == 2) {
							hedgeHeight = k2 - 4;
						} else {
							hedgeHeight = i2 - 4;
						}
						for (int j12 = 1; j12 <= hedgeHeight; ++j12) {
							setBlockAndMetadata(world, i1, j12, k1, leafBlock, leafMeta | 4);
						}
					} else if (i2 == 3 && k2 == 3) {
						for (j1 = 1; j1 <= 3; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
						}
					} else {
						int sum = i2 + k2;
						if (sum <= 7) {
							if (random.nextBoolean()) {
								plantFlower(world, random, i1, 1, k1);
							}
						} else if (sum <= 9) {
							setBlockAndMetadata(world, i1, 1, k1, Blocks.grass, 0);
							setGrassToDirt(world, i1, 0, k1);
							if (random.nextBoolean()) {
								plantFlower(world, random, i1, 2, k1);
							}
						} else {
							setBlockAndMetadata(world, i1, 2, k1, Blocks.grass, 0);
							setBlockAndMetadata(world, i1, 1, k1, Blocks.dirt, 0);
							setGrassToDirt(world, i1, 0, k1);
							if (sum >= 12 && i2 <= 7 && k2 <= 7) {
								setBlockAndMetadata(world, i1, 2, k1, Blocks.water, 0);
							} else if (random.nextBoolean()) {
								plantFlower(world, random, i1, 3, k1);
							}
						}
					}
				}
				if (k2 == 10 && i2 <= 9) {
					setBlockAndMetadata(world, i1, 7, k1, brickStairBlock, k1 > 0 ? 7 : 6);
					setBlockAndMetadata(world, i1, 8, k1, brickBlock, brickMeta);
				}
				if (i2 == 10 && k2 <= 9) {
					setBlockAndMetadata(world, i1, 7, k1, brickStairBlock, i1 > 0 ? 4 : 5);
					setBlockAndMetadata(world, i1, 8, k1, brickBlock, brickMeta);
				}
				if (k2 == 8 && i2 <= 7) {
					setBlockAndMetadata(world, i1, 7, k1, brickStairBlock, k1 > 0 ? 6 : 7);
					setBlockAndMetadata(world, i1, 8, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 8 && k2 <= 7) {
					setBlockAndMetadata(world, i1, 7, k1, brickStairBlock, i1 > 0 ? 5 : 4);
					setBlockAndMetadata(world, i1, 8, k1, brickWallBlock, brickWallMeta);
				}
				if (i2 == 9 && k2 == 9) {
					setBlockAndMetadata(world, i1, 7, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 8, k1, brickBlock, brickMeta);
				}
				if (i2 == 8 && k2 == 8) {
					setBlockAndMetadata(world, i1, 7, k1, brickStairBlock, k1 > 0 ? 6 : 7);
				}
				if (k2 == 9 && i2 <= 8 || i2 == 9 && k2 <= 8) {
					setBlockAndMetadata(world, i1, 7, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 8, k1, Blocks.water, 0);
				}
				if (i2 <= 1 && k2 <= 1) {
					if (i2 == 0 && k2 == 0) {
						setBlockAndMetadata(world, i1, 0, k1, brickCarvedBlock, brickCarvedMeta);
					} else if (i2 + k2 == 1) {
						setBlockAndMetadata(world, i1, 0, k1, brickFloweryBlock, brickFloweryMeta);
					} else {
						setBlockAndMetadata(world, i1, 0, k1, brickRedBlock, brickRedMeta);
					}
				}
				if (i2 <= 1 && k2 >= 2 && k2 <= 9) {
					if (i2 == 0) {
						setBlockAndMetadata(world, i1, 0, k1, brickRedBlock, brickRedMeta);
					} else {
						setBlockAndMetadata(world, i1, 0, k1, brickBlock, brickMeta);
					}
				}
				if (k2 <= 1 && i2 >= 2 && i2 <= 9) {
					if (k2 == 0) {
						setBlockAndMetadata(world, i1, 0, k1, brickRedBlock, brickRedMeta);
					} else {
						setBlockAndMetadata(world, i1, 0, k1, brickBlock, brickMeta);
					}
				}
				if ((k2 != 8 || i2 < 7 || i2 > 8) && (i2 != 8 || k2 < 7 || k2 > 8)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 8, k1, Blocks.water, 0);
			}
		}
		int domeRadius = 4;
		int domeRadiusSq = domeRadius * domeRadius;
		int domeX = 0;
		int domeY = 4;
		int domeZ = 0;
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k1 = -3; k1 <= 3; ++k1) {
				for (int j13 = 4; j13 <= 8; ++j13) {
					int dx = i1 - domeX;
					int dy = j13 - domeY;
					int dz = k1 - domeZ;
					float dSq = dx * dx + dy * dy + dz * dz;
					if (Math.abs(dSq - domeRadiusSq) > 3.0) {
						continue;
					}
					setBlockAndMetadata(world, i1, j13, k1, leafBlock, leafMeta | 4);
				}
			}
		}
		setBlockAndMetadata(world, -9, 7, -8, brickStairBlock, 6);
		setBlockAndMetadata(world, -8, 7, -8, brickStairBlock, 6);
		setBlockAndMetadata(world, 8, 7, -8, brickStairBlock, 6);
		setBlockAndMetadata(world, 9, 7, -8, brickStairBlock, 6);
		setBlockAndMetadata(world, -9, 7, 8, brickStairBlock, 7);
		setBlockAndMetadata(world, -8, 7, 8, brickStairBlock, 7);
		setBlockAndMetadata(world, 8, 7, 8, brickStairBlock, 7);
		setBlockAndMetadata(world, 9, 7, 8, brickStairBlock, 7);
		for (int k1 : new int[]{-9, 9}) {
			setBlockAndMetadata(world, -1, 5, k1, brickStairBlock, 4);
			setBlockAndMetadata(world, 1, 5, k1, brickStairBlock, 5);
			setBlockAndMetadata(world, -1, 6, k1, brickStairBlock, 0);
			setBlockAndMetadata(world, 0, 6, k1, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, 1, 6, k1, brickStairBlock, 1);
		}
		for (int i1 : new int[]{-9, 9}) {
			setBlockAndMetadata(world, i1, 5, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i1, 5, 1, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 6, -1, brickStairBlock, 3);
			setBlockAndMetadata(world, i1, 6, 0, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i1, 6, 1, brickStairBlock, 2);
		}
		for (int i1 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i1, 6, -8, Blocks.torch, 3);
			setBlockAndMetadata(world, i1, 6, 8, Blocks.torch, 4);
		}
		for (int k1 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, -8, 6, k1, Blocks.torch, 2);
			setBlockAndMetadata(world, 8, 6, k1, Blocks.torch, 1);
		}
		for (int i1 = -8; i1 <= 8; ++i1) {
			int i2 = Math.abs(i1);
			if (i2 == 0) {
				setBlockAndMetadata(world, i1, 8, -10, brickRedCarvedBlock, brickRedCarvedMeta);
				setBlockAndMetadata(world, i1, 9, -10, brickRedStairBlock, 3);
				setBlockAndMetadata(world, i1, 8, 10, brickRedCarvedBlock, brickRedCarvedMeta);
				setBlockAndMetadata(world, i1, 9, 10, brickRedStairBlock, 2);
			}
			if (i2 != 3 && i2 != 7) {
				continue;
			}
			for (int k1 : new int[]{-10, 10}) {
				setBlockAndMetadata(world, i1 - 1, 9, k1, brickStairBlock, 1);
				setBlockAndMetadata(world, i1, 9, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1 + 1, 9, k1, brickStairBlock, 0);
			}
		}
		for (int k1 = -8; k1 <= 8; ++k1) {
			int k2 = Math.abs(k1);
			if (k2 == 0) {
				setBlockAndMetadata(world, -10, 8, k1, brickRedCarvedBlock, brickRedCarvedMeta);
				setBlockAndMetadata(world, -10, 9, k1, brickRedStairBlock, 0);
				setBlockAndMetadata(world, 10, 8, k1, brickRedCarvedBlock, brickRedCarvedMeta);
				setBlockAndMetadata(world, 10, 9, k1, brickRedStairBlock, 1);
			}
			if (k2 != 3 && k2 != 7) {
				continue;
			}
			for (int i1 : new int[]{-10, 10}) {
				setBlockAndMetadata(world, i1, 9, k1 - 1, brickStairBlock, 2);
				setBlockAndMetadata(world, i1, 9, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 9, k1 + 1, brickStairBlock, 3);
			}
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		leafBlock = LOTRMod.leaves6;
		leafMeta = 2;
	}
}
