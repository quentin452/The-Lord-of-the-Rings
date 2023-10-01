package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRWorldGenElvenForge extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block slabBlock;
	public int slabMeta;
	public Block carvedBrickBlock;
	public int carvedBrickMeta;
	public Block wallBlock;
	public int wallMeta;
	public Block stairBlock;
	public Block torchBlock;
	public Block tableBlock;
	public Block barsBlock;
	public Block woodBarsBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block roofStairBlock;
	public Block chestBlock = Blocks.chest;
	public boolean ruined;

	protected LOTRWorldGenElvenForge(boolean flag) {
		super(flag);
	}

	public void buildPillar(World world, int i, int k, Random random) {
		for (int j = 1; j <= 6; ++j) {
			placePillar(world, i, j, k, random);
		}
	}

	public void buildWall(World world, int i, int k, Random random) {
		placePillar(world, i, 2, k, random);
		placeWall(world, i, 3, k, random);
		placePillar(world, i, 6, k, random);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		LOTREntityElf elf;
		int k1;
		int i1;
		int j1;
		int i2;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -6; i12 <= 6; ++i12) {
				for (k12 = -6; k12 <= 6; ++k12) {
					j1 = getTopBlock(world, i12, k12);
					Block block = getBlock(world, i12, j1 - 1, k12);
					if (block != Blocks.grass) {
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
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				layFoundation(world, i1, k1, random);
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			layFoundation(world, i1, -5, random);
			layFoundation(world, i1, 5, random);
		}
		for (int k13 = -2; k13 <= 2; ++k13) {
			layFoundation(world, -5, k13, random);
			layFoundation(world, 5, k13, random);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			layFoundation(world, i1, -6, random);
		}
		placeStairs(world, -1, 1, -6, 1, random);
		placeStairs(world, 0, 1, -6, 2, random);
		placeStairs(world, 1, 1, -6, 0, random);
		for (int l = 0; l <= 3; ++l) {
			int width = 4 - l;
			int j12 = 7 + l;
			for (int i13 = -width; i13 <= width; ++i13) {
				placeRoofStairs(world, i13, j12, -width, 2, random);
				placeRoofStairs(world, i13, j12, width, 3, random);
				placeRoofStairs(world, i13, j12 - 1, -width, 7, random);
				placeRoofStairs(world, i13, j12 - 1, width, 6, random);
			}
			for (k12 = -width + 1; k12 <= width - 1; ++k12) {
				placeRoofStairs(world, -width, j12, k12, 1, random);
				placeRoofStairs(world, width, j12, k12, 0, random);
				placeRoofStairs(world, -width, j12 - 1, k12, 4, random);
				placeRoofStairs(world, width, j12 - 1, k12, 5, random);
			}
			if (l == 3) {
				continue;
			}
			int width2 = 2 - l;
			for (int i14 = -width2; i14 <= width2; ++i14) {
				placeRoofStairs(world, i14, j12, -width - 1, 2, random);
				placeRoofStairs(world, i14, j12, width + 1, 3, random);
				placeRoof(world, i14, j12, -width, random);
				placeRoof(world, i14, j12, width, random);
			}
			for (int k14 = -width2; k14 <= width2; ++k14) {
				placeRoofStairs(world, -width - 1, j12, k14, 1, random);
				placeRoofStairs(world, width + 1, j12, k14, 0, random);
				placeRoof(world, -width, j12, k14, random);
				placeRoof(world, width, j12, k14, random);
			}
			if (width2 <= 0) {
				continue;
			}
			for (int l1 = 0; l1 <= 1; ++l1) {
				for (int l2 = 0; l2 <= 1; ++l2) {
					int l3 = IntMath.pow(-1, l2);
					placeRoofStairs(world, -width2, j12, l3 * (width + l1), 1, random);
					placeRoofStairs(world, width2, j12, l3 * (width + l1), 0, random);
					placeRoofStairs(world, l3 * (width + l1), j12, -width2, 2, random);
					placeRoofStairs(world, l3 * (width + l1), j12, width2, 3, random);
				}
			}
		}
		setBlockAndMetadata(world, 0, 10, -1, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, 0, 10, 1, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, -1, 10, 0, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, 1, 10, 0, carvedBrickBlock, carvedBrickMeta);
		placeRoofStairs(world, 0, 11, -1, 2, random);
		placeRoofStairs(world, 0, 11, 1, 3, random);
		placeRoofStairs(world, -1, 11, 0, 1, random);
		placeRoofStairs(world, 1, 11, 0, 0, random);
		buildPillar(world, -5, -2, random);
		buildPillar(world, -5, 2, random);
		buildPillar(world, 5, -2, random);
		buildPillar(world, 5, 2, random);
		buildPillar(world, -2, -5, random);
		buildPillar(world, 2, -5, random);
		buildPillar(world, -2, 5, random);
		buildPillar(world, 2, 5, random);
		buildPillar(world, -4, -4, random);
		buildPillar(world, -4, 4, random);
		buildPillar(world, 4, -4, random);
		buildPillar(world, 4, 4, random);
		buildWall(world, 2, -4, random);
		buildWall(world, 3, -4, random);
		buildWall(world, 4, -3, random);
		buildWall(world, 4, -2, random);
		buildWall(world, 5, -1, random);
		buildWall(world, 5, 0, random);
		buildWall(world, 5, 1, random);
		buildWall(world, 4, 2, random);
		buildWall(world, 4, 3, random);
		buildWall(world, 3, 4, random);
		buildWall(world, 2, 4, random);
		buildWall(world, 1, 5, random);
		buildWall(world, 0, 5, random);
		buildWall(world, -1, 5, random);
		buildWall(world, -2, 4, random);
		buildWall(world, -3, 4, random);
		buildWall(world, -4, 3, random);
		buildWall(world, -4, 2, random);
		buildWall(world, -5, 1, random);
		buildWall(world, -5, 0, random);
		buildWall(world, -5, -1, random);
		buildWall(world, -4, -2, random);
		buildWall(world, -4, -3, random);
		buildWall(world, -3, -4, random);
		buildWall(world, -2, -4, random);
		placeStairs(world, -1, 2, -5, 0, random);
		placeStairs(world, 1, 2, -5, 1, random);
		if (!ruined) {
			setBlockAndMetadata(world, -1, 5, -5, woodBarsBlock, 0);
			setBlockAndMetadata(world, 0, 5, -5, woodBarsBlock, 0);
			setBlockAndMetadata(world, 1, 5, -5, woodBarsBlock, 0);
			setBlockAndMetadata(world, 2, 5, -4, woodBarsBlock, 0);
			setBlockAndMetadata(world, 3, 5, -4, woodBarsBlock, 0);
			setBlockAndMetadata(world, 4, 5, -3, woodBarsBlock, 0);
			setBlockAndMetadata(world, 4, 5, -2, woodBarsBlock, 0);
			setBlockAndMetadata(world, 5, 5, -1, woodBarsBlock, 0);
			setBlockAndMetadata(world, 5, 5, 0, woodBarsBlock, 0);
			setBlockAndMetadata(world, 5, 5, 1, woodBarsBlock, 0);
			setBlockAndMetadata(world, 4, 5, 2, woodBarsBlock, 0);
			setBlockAndMetadata(world, 4, 5, 3, woodBarsBlock, 0);
			setBlockAndMetadata(world, 3, 5, 4, woodBarsBlock, 0);
			setBlockAndMetadata(world, 2, 5, 4, woodBarsBlock, 0);
			setBlockAndMetadata(world, 1, 5, 5, woodBarsBlock, 0);
			setBlockAndMetadata(world, 0, 5, 5, woodBarsBlock, 0);
			setBlockAndMetadata(world, -1, 5, 5, woodBarsBlock, 0);
			setBlockAndMetadata(world, -2, 5, 4, woodBarsBlock, 0);
			setBlockAndMetadata(world, -3, 5, 4, woodBarsBlock, 0);
			setBlockAndMetadata(world, -4, 5, 3, woodBarsBlock, 0);
			setBlockAndMetadata(world, -4, 5, 2, woodBarsBlock, 0);
			setBlockAndMetadata(world, -5, 5, 1, woodBarsBlock, 0);
			setBlockAndMetadata(world, -5, 5, 0, woodBarsBlock, 0);
			setBlockAndMetadata(world, -5, 5, -1, woodBarsBlock, 0);
			setBlockAndMetadata(world, -4, 5, -2, woodBarsBlock, 0);
			setBlockAndMetadata(world, -4, 5, -3, woodBarsBlock, 0);
			setBlockAndMetadata(world, -3, 5, -4, woodBarsBlock, 0);
			setBlockAndMetadata(world, -2, 5, -4, woodBarsBlock, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			placePillar(world, i1, 6, -5, random);
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if ((i2 > 2 || k2 > 2) && i2 != 0 && k2 != 0) {
					continue;
				}
				placePillar(world, i1, 1, k1, random);
			}
		}
		if (!ruined) {
			for (i1 = -4; i1 <= 4; i1 += 8) {
				setBlockAndMetadata(world, i1, 2, -1, Blocks.anvil, 0);
				setBlockAndMetadata(world, i1, 2, 1, tableBlock, 0);
			}
		}
		setBlockAndMetadata(world, -4, 2, 0, LOTRMod.elvenForge, 4);
		setBlockAndMetadata(world, 4, 2, 0, LOTRMod.elvenForge, 5);
		if (!ruined) {
			placeChest(world, random, -1, 2, 4, chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
			setBlockAndMetadata(world, 0, 2, 4, Blocks.crafting_table, 0);
			placeChest(world, random, 1, 2, 4, chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
		}
		setBlockAndMetadata(world, 0, 1, -2, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, 0, 1, 2, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, -2, 1, 0, carvedBrickBlock, carvedBrickMeta);
		setBlockAndMetadata(world, 2, 1, 0, carvedBrickBlock, carvedBrickMeta);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 1 && k2 == 1) {
					placePillar(world, i1, 2, k1, random);
					placePillar(world, i1, 3, k1, random);
					placeSlab(world, i1, 4, k1, false, random);
				}
				if (i2 + k2 != 1) {
					continue;
				}
				for (j1 = 2; j1 <= 9; ++j1) {
					placeBrick(world, i1, j1, k1, random);
				}
			}
		}
		if (!ruined) {
			setBlockAndMetadata(world, 0, 2, 0, Blocks.lava, 0);
		}
		setBlockAndMetadata(world, 0, 2, -1, LOTRMod.elvenForge, 2);
		setBlockAndMetadata(world, 0, 3, -1, barsBlock, 0);
		if (!ruined) {
			setBlockAndMetadata(world, -1, 5, -1, getTorchBlock(random), 1);
			setBlockAndMetadata(world, 1, 5, -1, getTorchBlock(random), 2);
			setBlockAndMetadata(world, -1, 5, 1, getTorchBlock(random), 1);
			setBlockAndMetadata(world, 1, 5, 1, getTorchBlock(random), 2);
		}
		elf = getElf(world);
		if (elf != null) {
			spawnNPCAndSetHome(elf, world, 0, 2, -2, 8);
		}
		return true;
	}

	public abstract LOTREntityElf getElf(World var1);

	public Block getTorchBlock(Random random) {
		return torchBlock;
	}

	public void layFoundation(World world, int i, int k, Random random) {
		int j = 0;
		while (!isOpaque(world, i, j, k) && getY(j) >= 0) {
			placeBrick(world, i, j, k, random);
			setGrassToDirt(world, i, j - 1, k);
			--j;
		}
		placeBrick(world, i, 1, k, random);
		for (j = 2; j <= 6; ++j) {
			setAir(world, i, j, k);
		}
	}

	public void placeBrick(World world, int i, int j, int k, Random random) {
		setBlockAndMetadata(world, i, j, k, brickBlock, brickMeta);
	}

	public void placePillar(World world, int i, int j, int k, Random random) {
		setBlockAndMetadata(world, i, j, k, pillarBlock, pillarMeta);
	}

	public void placeRoof(World world, int i, int j, int k, Random random) {
		setBlockAndMetadata(world, i, j, k, roofBlock, roofMeta);
	}

	public void placeRoofStairs(World world, int i, int j, int k, int meta, Random random) {
		setBlockAndMetadata(world, i, j, k, roofStairBlock, meta);
	}

	public void placeSlab(World world, int i, int j, int k, boolean flag, Random random) {
		setBlockAndMetadata(world, i, j, k, slabBlock, flag ? slabMeta | 8 : slabMeta);
	}

	public void placeStairs(World world, int i, int j, int k, int meta, Random random) {
		setBlockAndMetadata(world, i, j, k, stairBlock, meta);
	}

	public void placeWall(World world, int i, int j, int k, Random random) {
		setBlockAndMetadata(world, i, j, k, wallBlock, wallMeta);
	}
}
