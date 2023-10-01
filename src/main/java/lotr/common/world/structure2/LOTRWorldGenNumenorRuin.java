package lotr.common.world.structure2;

import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenNumenorRuin extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenNumenorRuin(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int width = 3 + random.nextInt(3);
		setOriginAndRotation(world, i, j, k, rotation, width + 1);
		for (i1 = -width; i1 <= width; ++i1) {
			for (k1 = -width; k1 <= width; ++k1) {
				int j1;
				if (Math.abs(i1) == width || Math.abs(k1) == width) {
					j1 = 0;
					while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
						placeRandomBrick(world, random, i1, j1, k1);
						setGrassToDirt(world, i1, j1 - 1, k1);
						--j1;
					}
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		if (random.nextBoolean()) {
			LOTRTreeType.OAK_LARGE.create(notifyChanges, random).generate(world, random, originX, originY + 1, originZ);
		} else {
			LOTRTreeType.BEECH_LARGE.create(notifyChanges, random).generate(world, random, originX, originY + 1, originZ);
		}
		for (i1 = -width; i1 <= width; ++i1) {
			for (k1 = -width; k1 <= width; ++k1) {
				if (Math.abs(i1) != width && Math.abs(k1) != width) {
					continue;
				}
				int height = width * 2 + random.nextInt(8);
				for (int j1 = 1; j1 < height; ++j1) {
					placeRandomBrick(world, random, i1, j1, k1);
				}
			}
		}
		setAir(world, 0, 1, -width);
		setAir(world, 0, 2, -width);
		int ruins = 10 + random.nextInt(20);
		for (int l = 0; l < ruins; ++l) {
			int j1;
			int k12;
			int i12 = -width * 2 + random.nextInt(width * 2 + 1);
			Block block = getBlock(world, i12, (j1 = getTopBlock(world, i12, k12 = -width * 2 + random.nextInt(width * 2 + 1))) - 1, k12);
			if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
				continue;
			}
			int l1 = random.nextInt(3);
			if (l1 == 0) {
				setBlockAndMetadata(world, i12, j1 - 1, k12, Blocks.gravel, 0);
				continue;
			}
			if (l1 == 1) {
				placeRandomBrick(world, random, i12, j1 - 1, k12);
				continue;
			}
			int height = 1 + random.nextInt(3);
			for (int j2 = j1; j2 < j1 + height && !isOpaque(world, i12, j2, k12); ++j2) {
				placeRandomBrick(world, random, i12, j2, k12);
			}
		}
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		int l = random.nextInt(5);
		switch (l) {
			case 0:
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
				break;
			case 1:
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
				break;
			case 2:
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
				break;
			case 3:
				setBlockAndMetadata(world, i, j, k, Blocks.cobblestone, 0);
				break;
			case 4:
				setBlockAndMetadata(world, i, j, k, Blocks.mossy_cobblestone, 0);
				break;
			default:
				break;
		}
	}
}
