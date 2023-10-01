package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenCorsairTent extends LOTRWorldGenCorsairStructure {
	public LOTRWorldGenCorsairTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -2; i1 <= 2; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					Block block = getBlock(world, i1, j1, k1);
					if (!isSurface(world, i1, j1, k1) && block != Blocks.stone && block != Blocks.sandstone) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -2; i1 <= 2; ++i1) {
			for (int k1 = -3; k1 <= 3; ++k1) {
				int j1;
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					int randomGround = random.nextInt(3);
					switch (randomGround) {
						case 0:
							if (j1 == 0) {
								setBiomeTop(world, i1, 0, k1);
							} else {
								setBiomeFiller(world, i1, j1, k1);
							}
							break;
						case 1:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 1);
							break;
						case 2:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.sand, 0);
							break;
						default:
							break;
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (int k1 = -3; k1 <= 3; ++k1) {
			for (int i1 : new int[]{-2, 2}) {
				for (int j1 = 1; j1 <= 2; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
				}
				setGrassToDirt(world, i1, 0, k1);
			}
			setBlockAndMetadata(world, -1, 3, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
			setBlockAndMetadata(world, 1, 3, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
			setBlockAndMetadata(world, 0, 4, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
			if (Math.abs(k1) != 3) {
				continue;
			}
			setBlockAndMetadata(world, 0, 5, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
		}
		for (int j1 = 1; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, 0, j1, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, j1, 3, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
		if (random.nextBoolean()) {
			placeChest(world, random, -1, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
		} else {
			placeChest(world, random, 1, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
		}
		return true;
	}
}
