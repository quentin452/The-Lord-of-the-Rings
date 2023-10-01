package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRWorldGenPalm extends WorldGenAbstractTree {
	public Block woodBlock;
	public int woodMeta;
	public Block leafBlock;
	public int leafMeta;
	public boolean hasDates;
	public int minHeight = 5;
	public int maxHeight = 8;

	public LOTRWorldGenPalm(boolean flag, Block b, int m, Block b1, int m1) {
		super(flag);
		woodBlock = b;
		woodMeta = m;
		leafBlock = b1;
		leafMeta = m1;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		if (j < 1 || j + height + 2 > 256 || !isReplaceable(world, i, j, k)) {
			return false;
		}
		Block below = world.getBlock(i, j - 1, k);
		if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			return false;
		}
		for (int l = 1; l < height + 2; ++l) {
			for (int i1 = i - 1; i1 <= i + 1; ++i1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					if (isReplaceable(world, i1, j + l, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		float trunkAngle = 6.2831855f * random.nextFloat();
		float trunkSin = MathHelper.sin(trunkAngle);
		float trunkCos = MathHelper.cos(trunkAngle);
		int trunkX = i;
		int trunkZ = k;
		int trunkSwitches = 0;
		int trunkSwitchesMax = MathHelper.getRandomIntegerInRange(random, 0, 3);
		for (int l = 0; l < height; ++l) {
			setBlockAndNotifyAdequately(world, trunkX, j + l, trunkZ, woodBlock, woodMeta);
			if (hasDates && l == height - 3) {
				for (int d = 0; d < 4; ++d) {
					ForgeDirection dir = ForgeDirection.getOrientation(d + 2);
					setBlockAndNotifyAdequately(world, trunkX + dir.getOpposite().offsetX, j + l, trunkZ + dir.getOpposite().offsetZ, LOTRMod.dateBlock, d);
				}
			}
			if (l <= height / 3 || l >= height - 1 || trunkSwitches >= trunkSwitchesMax || !random.nextBoolean()) {
				continue;
			}
			++trunkSwitches;
			if (Math.abs(trunkCos) >= MathHelper.getRandomDoubleInRange(random, 0.25, 0.5)) {
				trunkX = (int) (trunkX + Math.signum(trunkCos));
			}
			if (Math.abs(trunkSin) < MathHelper.getRandomDoubleInRange(random, 0.25, 0.5)) {
				continue;
			}
			trunkZ = (int) (trunkZ + Math.signum(trunkSin));
		}
		int leafAngle = 0;
		block5:
		while (leafAngle < 360) {
			float angleR = (float) Math.toRadians(leafAngle += 15 + random.nextInt(15));
			float sin = MathHelper.sin(angleR);
			float cos = MathHelper.cos(angleR);
			float angleY = random.nextFloat() * 0.5235987755982988f;
			float sinY = MathHelper.sin(angleY);
			int i1 = trunkX;
			int j1 = j + height - 1;
			int k1 = trunkZ;
			int branchLength = 5;
			for (int l = 1; l <= branchLength; ++l) {
				if (Math.floor(sinY * l) == Math.floor(sinY * (l - 1))) {
					boolean cosOrSin;
					double dCos = Math.floor(Math.abs(cos * l)) - Math.floor(Math.abs(cos * (l - 1)));
					double dSin = Math.floor(Math.abs(sin * l)) - Math.floor(Math.abs(sin * (l - 1)));
					cosOrSin = (dCos = Math.abs(dCos)) == (dSin = Math.abs(dSin)) ? random.nextBoolean() : dCos > dSin;
					if (cosOrSin) {
						i1 = (int) (i1 + Math.signum(cos));
					} else {
						k1 = (int) (k1 + Math.signum(sin));
					}
				} else {
					j1 = (int) (j1 + Math.signum(sinY));
				}
				boolean wood = l == 1;
				Block block = world.getBlock(i1, j1, k1);
				boolean replacingWood = block.isWood(world, i1, j1, k1);
				if (!block.isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1) && !replacingWood) {
					continue block5;
				}
				if (wood) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta);
				} else if (!replacingWood) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
				}
				if (l == 5) {
					continue block5;
				}
			}
		}
		world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
		return true;
	}

	public LOTRWorldGenPalm setDates() {
		hasDates = true;
		return this;
	}

	public LOTRWorldGenPalm setMinMaxHeight(int min, int max) {
		minHeight = min;
		maxHeight = max;
		return this;
	}
}
