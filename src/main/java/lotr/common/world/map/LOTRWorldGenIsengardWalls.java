package lotr.common.world.map;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenIsengardWalls extends LOTRWorldGenStructureBase2 {
	public static LOTRWorldGenIsengardWalls INSTANCE = new LOTRWorldGenIsengardWalls(false);
	public int centreX;
	public int centreZ;
	public int radius = 400;
	public int radiusSq = radius * radius;
	public double wallThick = 0.03;
	public int wallTop = 100;
	public int gateBottom = 80;
	public int gateTop = wallTop - 3;

	public LOTRWorldGenIsengardWalls(boolean flag) {
		super(flag);
		centreX = LOTRWaypoint.ISENGARD.getXCoord();
		centreZ = LOTRWaypoint.ISENGARD.getZCoord() - LOTRWaypoint.mapToWorldR(3.5);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		if (isPosInWall(i + 8, k + 8) < wallThick * 3.0) {
			for (int i1 = i; i1 <= i + 15; ++i1) {
				block1:
				for (int k1 = k; k1 <= k + 15; ++k1) {
					double circleDist = isPosInWall(i1, k1);
					if (circleDist >= 0.03) {
						continue;
					}
					int dx = i1 - centreX;
					int dz = k1 - centreX;
					float roadNear = LOTRRoads.isRoadNear(i1, k1, 9);
					boolean gate = roadNear >= 0.0f;
					boolean fences = false;
					boolean wallEdge = circleDist > 0.025;
					boolean ladder = wallEdge && Math.abs(dx) == 16 && dz < radius;
					for (int j1 = wallTop; j1 > 0; --j1) {
						if (fences) {
							setBlockAndMetadata(world, i1, j1, k1, LOTRMod.fence, 3);
						} else {
							setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick2, 11);
							if (wallEdge && j1 == wallTop && !ladder) {
								setBlockAndMetadata(world, i1, j1 + 1, k1, LOTRMod.brick2, 11);
								if (IntMath.mod(i1 + k1, 2) == 1) {
									setBlockAndMetadata(world, i1, j1 + 2, k1, LOTRMod.slabSingle5, 3);
								} else if (isTorchAt(i1, k1)) {
									setBlockAndMetadata(world, i1, j1 + 2, k1, LOTRMod.orcTorch, 0);
									setBlockAndMetadata(world, i1, j1 + 3, k1, LOTRMod.orcTorch, 1);
								}
							}
							if (ladder) {
								setBlockAndMetadata(world, i1, j1, k1 - 1, Blocks.ladder, 2);
							}
						}
						Block below = getBlock(world, i1, j1 - 1, k1);
						setGrassToDirt(world, i1, j1 - 1, k1);
						if (below == Blocks.grass || below == Blocks.dirt || below == Blocks.stone) {
							continue block1;
						}
						if (!gate) {
							continue;
						}
						if (fences) {
							if (j1 != gateBottom) {
								continue;
							}
							continue block1;
						}
						int lerpGateTop = gateBottom + Math.round((gateTop - gateBottom) * MathHelper.sqrt_float(1.0f - roadNear));
						if (j1 != lerpGateTop) {
							continue;
						}
						if (circleDist <= 0.025) {
							continue block1;
						}
						fences = true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getX(int x, int z) {
		return x;
	}

	@Override
	public int getY(int y) {
		return y;
	}

	@Override
	public int getZ(int x, int z) {
		return z;
	}

	public double isPosInWall(int i, int k) {
		int dx = i - centreX;
		int dz = k - centreZ;
		int distSq = dx * dx + dz * dz;
		return Math.abs((double) distSq / radiusSq - 1.0);
	}

	public boolean isTorchAt(int i, int k) {
		int torchRange = 12;
		int xmod = IntMath.mod(i, torchRange);
		return IntMath.mod(xmod + IntMath.mod(k, torchRange), torchRange) == 0;
	}

	@Override
	public int rotateMeta(Block block, int meta) {
		return meta;
	}
}
