package lotr.common.world.structure2;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenSouthronTownFlowers extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronTownFlowers(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		ItemStack flower = getRandomFlower(world, random);
		Block flowerBlock = Block.getBlockFromItem(flower.getItem());
		int flowerMeta = flower.getItemDamage();
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = 0; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = 0; k1 <= 3; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				//noinspection BadOddness
				if ((k1 == 0 || k1 == 3) && i2 % 2 == 1) {
					setBlockAndMetadata(world, i1, 1, k1, brickSlabBlock, brickSlabMeta);
				}
				if (k1 < 1 || k1 > 2 || i2 > 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
				setBlockAndMetadata(world, i1, 1, k1, flowerBlock, flowerMeta);
			}
		}
		return true;
	}
}
