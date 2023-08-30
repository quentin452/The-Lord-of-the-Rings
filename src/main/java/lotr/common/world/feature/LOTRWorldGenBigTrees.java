package lotr.common.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenBigTrees extends WorldGenAbstractTree {
	public static byte[] otherCoordPairs = {2, 0, 0, 1, 2, 1};
	public Random rand = new Random();
	public World worldObj;
	public int[] basePos = {0, 0, 0};
	public int heightLimit;
	public int height;
	public double heightAttenuation = 0.618;
	public double branchSlope = 0.381;
	public double scaleWidth = 1.0;
	public double leafDensity = 1.0;
	public int heightLimitLimit = 12;
	public int leafDistanceLimit = 4;
	public int[][] leafNodes;
	public Block woodBlock;
	public int woodMeta;
	public Block leafBlock;
	public int leafMeta;

	public LOTRWorldGenBigTrees(boolean flag, Block block, int i, Block block1, int j) {
		super(flag);
		woodBlock = block;
		woodMeta = i;
		leafBlock = block1;
		leafMeta = i;
	}

	public int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
		int i;
		int[] aint2 = {0, 0, 0};
		int b1 = 0;
		for (int b0 = 0; b0 < 3; b0 = (byte) (b0 + 1)) {
			aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
			if (Math.abs(aint2[b0]) <= Math.abs(aint2[b1])) {
				continue;
			}
			b1 = b0;
		}
		if (aint2[b1] == 0) {
			return -1;
		}
		byte b2 = otherCoordPairs[b1];
		byte b3 = otherCoordPairs[b1 + 3];
		int b4 = aint2[b1] > 0 ? 1 : -1;
		double d0 = (double) aint2[b2] / aint2[b1];
		double d1 = (double) aint2[b3] / aint2[b1];
		int[] aint3 = {0, 0, 0};
		int j = aint2[b1] + b4;
		for (i = 0; i != j; i += b4) {
			aint3[b1] = par1ArrayOfInteger[b1] + i;
			aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + i * d0);
			aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + i * d1);
			Block block = worldObj.getBlock(aint3[0], aint3[1], aint3[2]);
			if (block.getMaterial() != Material.air && !block.isLeaves(worldObj, aint3[0], aint3[1], aint3[2])) {
				break;
			}
		}
		return i == j ? -1 : Math.abs(i);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		worldObj = world;
		long l = random.nextLong();
		rand.setSeed(l);
		basePos[0] = i;
		basePos[1] = j;
		basePos[2] = k;
		if (heightLimit == 0) {
			heightLimit = 5 + rand.nextInt(heightLimitLimit);
		}
		if (!validTreeLocation()) {
			return false;
		}
		generateLeafNodeList();
		generateLeaves();
		generateTrunk();
		generateLeafNodeBases();
		return true;
	}

	public void generateLeafNode(int i, int j, int k) {
		int j2 = j + leafDistanceLimit;
		for (int j1 = j; j1 < j2; ++j1) {
			float f = leafSize(j1 - j);
			genTreeLayer(i, j1, k, f, (byte) 1, leafBlock, leafMeta);
		}
	}

	public void generateLeafNodeBases() {
		int j = leafNodes.length;
		int[] aint = {basePos[0], basePos[1], basePos[2]};
		for (int[] aint1 : leafNodes) {
			int[] aint2 = {aint1[0], aint1[1], aint1[2]};
			aint[1] = aint1[3];
			int k = aint[1] - basePos[1];
			if (!leafNodeNeedsBase(k)) {
				continue;
			}
			placeBlockLine(aint, aint2, woodBlock, woodMeta);
		}
	}

	public void generateLeafNodeList() {
		int i;
		height = (int) (heightLimit * heightAttenuation);
		if (height >= heightLimit) {
			height = heightLimit - 1;
		}
		i = (int) (1.382 + Math.pow(leafDensity * heightLimit / 13.0, 2.0));
		if (i < 1) {
			i = 1;
		}
		int[][] aint = new int[i * heightLimit][4];
		int j = basePos[1] + heightLimit - leafDistanceLimit;
		int k = 1;
		int l = basePos[1] + height;
		int i1 = j - basePos[1];
		aint[0][0] = basePos[0];
		aint[0][1] = j;
		j--;
		aint[0][2] = basePos[2];
		aint[0][3] = l;
		while (i1 >= 0) {
			float f = layerSize(i1);
			if (f < 0.0f) {
				--j;
				--i1;
				continue;
			}
			double d0 = 0.5;
			for (int j1 = 0; j1 < i; ++j1) {
				double d1 = scaleWidth * f * (rand.nextFloat() + 0.328);
				double d2 = rand.nextFloat() * 2.0 * 3.141592653589793;
				int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + basePos[0] + d0);
				int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + basePos[2] + d0);
				int[] aint1 = {k1, j, l1};
				int[] aint2 = {k1, j + leafDistanceLimit, l1};
				if (checkBlockLine(aint1, aint2) != -1) {
					continue;
				}
				int[] aint3 = {basePos[0], basePos[1], basePos[2]};
				double d3 = Math.sqrt(Math.pow(Math.abs(basePos[0] - aint1[0]), 2.0) + Math.pow(Math.abs(basePos[2] - aint1[2]), 2.0));
				double d4 = d3 * branchSlope;
				aint3[1] = aint1[1] - d4 > l ? l : (int) (aint1[1] - d4);
				if (checkBlockLine(aint3, aint1) != -1) {
					continue;
				}
				aint[k][0] = k1;
				aint[k][1] = j;
				aint[k][2] = l1;
				aint[k][3] = aint3[1];
				++k;
			}
			--j;
			--i1;
		}
		leafNodes = new int[k][4];
		System.arraycopy(aint, 0, leafNodes, 0, k);
	}

	public void generateLeaves() {
		int j = leafNodes.length;
		for (int[] leafNode : leafNodes) {
			int k = leafNode[0];
			int l = leafNode[1];
			int i1 = leafNode[2];
			generateLeafNode(k, l, i1);
		}
	}

	public void generateTrunk() {
		int i = basePos[0];
		int j = basePos[1];
		int j1 = basePos[1] + height;
		int k = basePos[2];
		int[] aint = {i, j, k};
		int[] aint1 = {i, j1, k};
		placeBlockLine(aint, aint1, woodBlock, woodMeta);
		worldObj.getBlock(i, j - 1, k).onPlantGrow(worldObj, i, j - 1, k, i, j, k);
	}

	public void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block par6, int meta) {
		int i1 = (int) (par4 + 0.618);
		byte b1 = otherCoordPairs[par5];
		byte b2 = otherCoordPairs[par5 + 3];
		int[] aint = {par1, par2, par3};
		int[] aint1 = {0, 0, 0};
		int k1;
		aint1[par5] = aint[par5];
		for (int j1 = -i1; j1 <= i1; ++j1) {
			aint1[b1] = aint[b1] + j1;
			k1 = -i1;
			while (k1 <= i1) {
				double d0 = Math.pow(Math.abs(j1) + 0.5, 2.0) + Math.pow(Math.abs(k1) + 0.5, 2.0);
				if (d0 > par4 * par4) {
					++k1;
					continue;
				}
				aint1[b2] = aint[b2] + k1;
				Block block = worldObj.getBlock(aint1[0], aint1[1], aint1[2]);
				if (block.getMaterial() != Material.air && !block.isLeaves(worldObj, aint1[0], aint1[1], aint1[2])) {
					++k1;
					continue;
				}
				setBlockAndNotifyAdequately(worldObj, aint1[0], aint1[1], aint1[2], par6, meta);
				++k1;
			}
		}
	}

	public float layerSize(int par1) {
		if (par1 < heightLimit * 0.3) {
			return -1.618f;
		}
		float f = heightLimit / 2.0f;
		float f1 = heightLimit / 2.0f - par1;
		float f2 = f1 == 0.0f ? f : Math.abs(f1) >= f ? 0.0f : (float) Math.sqrt(Math.pow(Math.abs(f), 2.0) - Math.pow(Math.abs(f1), 2.0));
		return f2 * 0.5f;
	}

	public boolean leafNodeNeedsBase(int par1) {
		return par1 >= heightLimit * 0.2;
	}

	public float leafSize(int par1) {
		return par1 >= 0 && par1 < leafDistanceLimit ? par1 != 0 && par1 != leafDistanceLimit - 1 ? 3.0f : 2.0f : -1.0f;
	}

	public void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block block, int meta) {
		int[] aint2 = {0, 0, 0};
		int b1 = 0;
		for (int b0 = 0; b0 < 3; b0 = (byte) (b0 + 1)) {
			aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
			if (Math.abs(aint2[b0]) <= Math.abs(aint2[b1])) {
				continue;
			}
			b1 = b0;
		}
		if (aint2[b1] != 0) {
			byte b2 = otherCoordPairs[b1];
			byte b3 = otherCoordPairs[b1 + 3];
			int b4 = aint2[b1] > 0 ? 1 : -1;
			double d0 = (double) aint2[b2] / aint2[b1];
			double d1 = (double) aint2[b3] / aint2[b1];
			int[] aint3 = {0, 0, 0};
			int k = aint2[b1] + b4;
			for (int j = 0; j != k; j += b4) {
				int i1;
				aint3[b1] = MathHelper.floor_double(par1ArrayOfInteger[b1] + j + 0.5);
				aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + j * d0 + 0.5);
				aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + j * d1 + 0.5);
				int b5 = 0;
				int l = Math.abs(aint3[0] - par1ArrayOfInteger[0]);
				int j1 = Math.max(l, Math.abs(aint3[2] - par1ArrayOfInteger[2]));
				if (j1 > 0) {
					if (l == j1) {
						b5 = 4;
					} else {
						b5 = 8;
					}
				}
				setBlockAndNotifyAdequately(worldObj, aint3[0], aint3[1], aint3[2], block, meta | b5);
			}
		}
	}

	public boolean validTreeLocation() {
		int[] aint = {basePos[0], basePos[1], basePos[2]};
		int[] aint1 = {basePos[0], basePos[1] + heightLimit - 1, basePos[2]};
		Block block = worldObj.getBlock(basePos[0], basePos[1] - 1, basePos[2]);
		if (!block.canSustainPlant(worldObj, basePos[0], basePos[1] - 1, basePos[2], ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			return false;
		}
		int j = checkBlockLine(aint, aint1);
		if (j == -1) {
			return true;
		}
		if (j < 6) {
			return false;
		}
		heightLimit = j;
		return true;
	}
}
