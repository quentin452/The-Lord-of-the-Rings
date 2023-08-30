package lotr.common.world.feature;

import java.util.*;

import lotr.common.LOTRMod;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenKanuka extends WorldGenAbstractTree {
	public int minHeight;
	public int maxHeight;
	public int trunkWidth;
	public Block woodBlock = LOTRMod.wood9;
	public int woodMeta = 1;
	public Block leafBlock = LOTRMod.leaves9;
	public int leafMeta = 1;

	public LOTRWorldGenKanuka(boolean flag) {
		this(flag, 5, 12, 0);
	}

	public LOTRWorldGenKanuka(boolean flag, int i, int j, int k) {
		super(flag);
		minHeight = i;
		maxHeight = j;
		trunkWidth = k;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		float trunkAngleY = (float) Math.toRadians(90.0f - MathHelper.randomFloatClamp(random, 0.0f, 35.0f));
		float trunkAngle = random.nextFloat() * 3.1415927f * 2.0f;
		float trunkYCos = MathHelper.cos(trunkAngleY);
		float trunkYSin = MathHelper.sin(trunkAngleY);
		float trunkCos = MathHelper.cos(trunkAngle) * trunkYCos;
		float trunkSin = MathHelper.sin(trunkAngle) * trunkYCos;
		boolean flag = true;
		if (j >= 1 && j + height + 3 <= 256) {
			for (int j1 = j; j1 <= j + height + 3; ++j1) {
				int range = trunkWidth + 1;
				if (j1 == j) {
					range = trunkWidth;
				}
				if (j1 >= j + height + 2) {
					range = trunkWidth + 2;
				}
				for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
					for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k1)) {
							continue;
						}
						flag = false;
					}
				}
			}
			for (int i1 = i - trunkWidth; i1 <= i + trunkWidth && flag; ++i1) {
				for (int k1 = k - trunkWidth; k1 <= k + trunkWidth && flag; ++k1) {
					Block block = world.getBlock(i1, j - 1, k1);
					boolean isSoil = block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable) Blocks.sapling);
					if (isSoil) {
						continue;
					}
					flag = false;
				}
			}
			if (!flag) {
				return false;
			}
			for (int pass = 0; pass <= 1; ++pass) {
				if (pass == 1) {
					for (int i1 = i - trunkWidth; i1 <= i + trunkWidth; ++i1) {
						for (int k1 = k - trunkWidth; k1 <= k + trunkWidth; ++k1) {
							world.getBlock(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
						}
					}
				}
				int trunkX = i;
				int trunkZ = k;
				int trunkY = j;
				ArrayList<int[]> trunkCoords = new ArrayList<>();
				for (int l = 0; l < height; ++l) {
					if (l > 0) {
						if (Math.floor(trunkCos * l) != Math.floor(trunkCos * (l + 1))) {
							trunkX = (int) (trunkX + Math.signum(trunkCos));
						}
						if (Math.floor(trunkSin * l) != Math.floor(trunkSin * (l + 1))) {
							trunkZ = (int) (trunkZ + Math.signum(trunkSin));
						}
						if (Math.floor(trunkYSin * l) != Math.floor(trunkYSin * (l + 1))) {
							trunkY = (int) (trunkY + Math.signum(trunkYSin));
						}
					}
					int j1 = trunkY;
					for (int i1 = trunkX - trunkWidth; i1 <= trunkX + trunkWidth; ++i1) {
						for (int k1 = trunkZ - trunkWidth; k1 <= trunkZ + trunkWidth; ++k1) {
							if (pass == 0 && !isReplaceable(world, i1, j1, k1)) {
								return false;
							}
							if (pass != 1) {
								continue;
							}
							setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 0xC);
							trunkCoords.add(new int[]{i1, j1, k1});
						}
					}
				}
				if (pass != 1) {
					continue;
				}
				int branchHeight = (int) (height * 0.67);
				int deg = 0;
				while (deg < 360) {
					int degIncr = MathHelper.getRandomIntegerInRange(random, 70, 150);
					degIncr -= trunkWidth * 10;
					degIncr = Math.max(degIncr, 20);
					float angle = (float) Math.toRadians(deg += degIncr);
					float cos = MathHelper.cos(angle);
					float sin = MathHelper.sin(angle);
					float angleY = (float) Math.toRadians(70.0f - random.nextFloat() * 20.0f);
					float cosY = MathHelper.cos(angleY);
					float sinY = MathHelper.sin(angleY);
					cos *= cosY;
					sin *= cosY;
					int length = branchHeight + MathHelper.getRandomIntegerInRange(random, -3, 3);
					length = Math.max(length, 3);
					int trunkIndex = MathHelper.getRandomIntegerInRange(random, (int) ((trunkCoords.size() - 1) * 0.5), trunkCoords.size() - 1);
					int[] oneTrunkCoord = trunkCoords.get(trunkIndex);
					int i1 = oneTrunkCoord[0];
					int j1 = oneTrunkCoord[1];
					int k1 = oneTrunkCoord[2];
					for (int l = 0; l < (length += trunkWidth); ++l) {
						Block block;
						if (Math.floor(cos * l) != Math.floor(cos * (l + 1))) {
							i1 = (int) (i1 + Math.signum(cos));
						}
						if (Math.floor(sin * l) != Math.floor(sin * (l + 1))) {
							k1 = (int) (k1 + Math.signum(sin));
						}
						if (Math.floor(sinY * l) != Math.floor(sinY * (l + 1))) {
							j1 = (int) (j1 + Math.signum(sinY));
						}
						if (!(block = world.getBlock(i1, j1, k1)).isReplaceable(world, i1, j1, k1) && !block.isWood(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
							break;
						}
						setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 0xC);
					}
					growLeafCanopy(world, random, i1, j1, k1);
				}
			}
			return true;
		}
		return false;
	}

	public void growLeafCanopy(World world, Random random, int i, int j, int k) {
		int leafHeight = 2;
		int maxRange = 1 + random.nextInt(3);
		for (int j1 = 0; j1 < leafHeight; ++j1) {
			int j2 = j + j1;
			int leafRange = maxRange - j1;
			for (int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
				for (int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
					Block block;
					int i2 = Math.abs(i1 - i);
					int dist = i2 + Math.abs(k1 - k);
					if (dist > leafRange || !(block = world.getBlock(i1, j2, k1)).isReplaceable(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i1, j2, k1, leafBlock, leafMeta);
				}
			}
		}
		Block block = world.getBlock(i, j, k);
		if (block.isReplaceable(world, i, j, k) || block.isLeaves(world, i, j, k)) {
			setBlockAndNotifyAdequately(world, i, j, k, woodBlock, woodMeta | 0xC);
		}
	}
}
