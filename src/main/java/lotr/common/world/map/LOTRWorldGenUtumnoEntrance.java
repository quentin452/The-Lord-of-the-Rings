package lotr.common.world.map;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.world.World;

public class LOTRWorldGenUtumnoEntrance extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenUtumnoEntrance() {
		super(false);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j2;
		rotation = 2;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		originY = 0;
		int radius = 8;
		int baseHeight = 40;
		int portalHeight = 10;
		int portalBase = portalHeight - 2 - 1;
		for (int i1 = -radius; i1 <= radius; ++i1) {
			for (int k1 = -radius; k1 <= radius; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				int maxHeight = 100 + random.nextInt(10);
				for (int j12 = baseHeight; j12 <= maxHeight; ++j12) {
					if (i2 == radius || k2 == radius || j12 == baseHeight || j12 >= maxHeight - 10) {
						setBlockAndMetadata(world, i1, j12, k1, LOTRMod.utumnoBrick, 2);
						continue;
					}
					setAir(world, i1, j12, k1);
				}
				if (i2 >= radius || k2 >= radius || random.nextInt(16) != 0) {
					continue;
				}
				int height = 1 + random.nextInt(2);
				for (j1 = baseHeight; j1 <= baseHeight + height; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoBrick, 2);
				}
			}
		}
		for (int l = 0; l < 40; ++l) {
			int i1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
			int k1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
			int width = 1 + random.nextInt(3);
			int height = width * 4 + random.nextInt(4);
			for (int i2 = i1 - width; i2 <= i1 + width; ++i2) {
				for (int k2 = k1 - width; k2 <= k1 + width; ++k2) {
					int base = getTopBlock(world, i2, k2);
					int top = base + height - random.nextInt(3);
					for (j2 = base; j2 < top; ++j2) {
						setBlockAndMetadata(world, i2, j2, k2, LOTRMod.utumnoBrick, 2);
					}
				}
			}
		}
		int entranceX = -radius;
		int entranceZ = -radius;
		int entranceY = 80;
		int entranceSize = 6;
		int entranceSizeExtra = entranceSize + 3;
		for (int i1 = entranceX - entranceSize; i1 <= entranceX + entranceSize; ++i1) {
			for (j1 = entranceY - entranceSize; j1 <= entranceY + entranceSize; ++j1) {
				for (int k1 = entranceZ - entranceSize; k1 <= entranceZ + entranceSize; ++k1) {
					int i2 = i1 - entranceX;
					j2 = j1 - entranceY;
					int k2 = k1 - entranceZ;
					float dist = i2 * i2 + j2 * j2 + k2 * k2;
					if (dist >= entranceSize * entranceSize && (dist >= entranceSizeExtra * entranceSizeExtra || random.nextInt(6) != 0)) {
						continue;
					}
					setAir(world, i1, j1, k1);
				}
			}
		}
		int stairX = entranceX + 1;
		int stairY = entranceY - entranceSize - 1;
		int stairZ = entranceZ + 1;
		int stairDirection = 2;
		do {
			setBlockAndMetadata(world, stairX, stairY, stairZ, LOTRMod.utumnoBrick, 2);
			if (stairY <= baseHeight) {
				break;
			}
			if (stairDirection == 0 && getBlock(world, stairX, --stairY, stairZ + 1).isOpaqueCube()) {
				stairDirection = 1;
			}
			if (stairDirection == 1 && getBlock(world, stairX - 1, stairY, stairZ).isOpaqueCube()) {
				stairDirection = 2;
			}
			if (stairDirection == 2 && getBlock(world, stairX, stairY, stairZ - 1).isOpaqueCube()) {
				stairDirection = 3;
			}
			if (stairDirection == 3 && getBlock(world, stairX + 1, stairY, stairZ).isOpaqueCube()) {
				stairDirection = 0;
			}
			if (stairDirection == 0) {
				++stairZ;
			}
			if (stairDirection == 1) {
				--stairX;
			}
			if (stairDirection == 2) {
				--stairZ;
			}
			if (stairDirection != 3) {
				continue;
			}
			++stairX;
		} while (true);
		for (int i1 = -2; i1 <= 2; ++i1) {
			for (int k1 = -2; k1 <= 2; ++k1) {
				int j13;
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (int j14 = portalBase; j14 <= baseHeight + 1; ++j14) {
					if (i2 < 2 && k2 < 2) {
						if (j14 == portalBase) {
							setBlockAndMetadata(world, i1, j14, k1, LOTRMod.utumnoBrickEntrance, 0);
							continue;
						}
						if (j14 < portalHeight) {
							setAir(world, i1, j14, k1);
							continue;
						}
						if (j14 == portalHeight) {
							setBlockAndMetadata(world, i1, j14, k1, LOTRMod.utumnoPortal, 0);
							continue;
						}
						setAir(world, i1, j14, k1);
						continue;
					}
					setBlockAndMetadata(world, i1, j14, k1, LOTRMod.utumnoBrickEntrance, 0);
				}
				if (i2 != 2 || k2 != 2) {
					continue;
				}
				int min = baseHeight + 2;
				int max = min + 2 + random.nextInt(2);
				for (j13 = min; j13 <= max; ++j13) {
					setBlockAndMetadata(world, i1, j13, k1, LOTRMod.utumnoPillar, 1);
				}
				setBlockAndMetadata(world, i1, max + 1, k1, LOTRMod.utumnoBrick, 2);
				min = max + 2;
				max = min + 2;
				for (j13 = min; j13 <= max; ++j13) {
					setBlockAndMetadata(world, i1, j13, k1, LOTRMod.utumnoPillar, 1);
				}
			}
		}
		return true;
	}
}
