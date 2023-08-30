package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorSmithy extends LOTRWorldGenGondorStructure {
	public LOTRWorldGenGondorSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = 1; k12 <= 11; ++k12) {
					int j1 = getTopBlock(world, i13, k12) - 1;
					if (!isSurface(world, i13, j1, k12)) {
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
		for (k1 = 1; k1 <= 11; ++k1) {
			for (i12 = -4; i12 <= 4; ++i12) {
				int j1;
				boolean pillar;
				pillar = Math.abs(i12) == 4 && (k1 == 1 || k1 == 11);
				if (pillar) {
					for (j1 = 4; (j1 >= 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i12, j1, k1, pillar2Block, pillar2Meta);
						setGrassToDirt(world, i12, j1 - 1, k1);
					}
					continue;
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i12, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i12, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					setGrassToDirt(world, i12, j1 - 1, k1);
				}
				if (Math.abs(i12) == 4 || k1 == 1 || k1 == 11) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
					}
					setBlockAndMetadata(world, i12, 4, k1, brickWallBlock, brickWallMeta);
					continue;
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i12, j1, k1);
				}
			}
		}
		for (k1 = 3; k1 <= 7; k1 += 4) {
			for (i12 = -2; i12 <= 1; i12 += 3) {
				for (int k2 = k1; k2 <= k1 + 2; ++k2) {
					for (int i2 = i12; i2 <= i12 + 1; ++i2) {
						setBlockAndMetadata(world, i2, 0, k2, rockBlock, rockMeta);
					}
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (int j1 = 1; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, i1, j1, 1, rockBlock, rockMeta);
			}
		}
		setBlockAndMetadata(world, 0, 1, 1, fenceGateBlock, 0);
		setAir(world, 0, 2, 1);
		for (k1 = 2; k1 <= 10; ++k1) {
			setBlockAndMetadata(world, -4, 4, k1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 4, 4, k1, brickWallBlock, brickWallMeta);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 4, 1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1, 4, 11, brickWallBlock, brickWallMeta);
		}
		for (k1 = 2; k1 <= 10; ++k1) {
			for (i12 = -3; i12 <= 3; ++i12) {
				setBlockAndMetadata(world, i12, 5, k1, brick2Block, brick2Meta);
			}
		}
		for (k1 = 2; k1 <= 10; ++k1) {
			setBlockAndMetadata(world, -4, 5, k1, brick2StairBlock, 1);
			setBlockAndMetadata(world, 4, 5, k1, brick2StairBlock, 0);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 5, 1, brick2StairBlock, 2);
			setBlockAndMetadata(world, i1, 5, 11, brick2StairBlock, 3);
		}
		setBlockAndMetadata(world, -3, 1, 2, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -3, 1, 3, tableBlock, 0);
		setBlockAndMetadata(world, -3, 1, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 2, 4, brickWallBlock, brickWallMeta);
		for (k1 = 2; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -3, 3, k1, brickStairBlock, 0);
		}
		for (k1 = 2; k1 <= 6; k1 += 2) {
			setBlockAndMetadata(world, 3, 1, k1, Blocks.anvil, 0);
		}
		placeChest(world, random, 3, 1, 8, Blocks.chest, 5, LOTRChestContents.GONDOR_SMITHY);
		placeChest(world, random, 3, 1, 9, Blocks.chest, 5, LOTRChestContents.GONDOR_SMITHY);
		setBlockAndMetadata(world, -1, 2, 2, Blocks.torch, 3);
		setBlockAndMetadata(world, 1, 2, 2, Blocks.torch, 3);
		setBlockAndMetadata(world, -3, 2, 6, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 2, 6, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 1, 8, rockSlabDoubleBlock, rockSlabDoubleMeta);
		setBlockAndMetadata(world, -1, 2, 8, rockSlabDoubleBlock, rockSlabDoubleMeta);
		setBlockAndMetadata(world, -3, 1, 9, Blocks.lava, 0);
		setBlockAndMetadata(world, -2, 1, 9, Blocks.lava, 0);
		setBlockAndMetadata(world, -3, 1, 10, Blocks.lava, 0);
		setBlockAndMetadata(world, -2, 1, 10, Blocks.lava, 0);
		setBlockAndMetadata(world, -3, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -2, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 3, 9, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 3, 10, brickStairBlock, 0);
		setBlockAndMetadata(world, -3, 1, 8, LOTRMod.alloyForge, 2);
		setBlockAndMetadata(world, -2, 1, 8, LOTRMod.alloyForge, 2);
		setBlockAndMetadata(world, -1, 1, 9, LOTRMod.alloyForge, 4);
		setBlockAndMetadata(world, -1, 1, 10, LOTRMod.alloyForge, 4);
		world.setBlockMetadataWithNotify(-3, 1, 8, 2, 3);
		world.setBlockMetadataWithNotify(-2, 1, 8, 2, 3);
		world.setBlockMetadataWithNotify(-1, 1, 9, 5, 3);
		world.setBlockMetadataWithNotify(-1, 1, 10, 5, 3);
		setBlockAndMetadata(world, -3, 2, 8, barsBlock, 0);
		setBlockAndMetadata(world, -2, 2, 8, barsBlock, 0);
		setBlockAndMetadata(world, -1, 2, 9, barsBlock, 0);
		setBlockAndMetadata(world, -1, 2, 10, barsBlock, 0);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (int k13 = -1; k13 <= 1; ++k13) {
				if (i1 == 0 && k13 == 0) {
					continue;
				}
				setBlockAndMetadata(world, -3 + i1, 4, 10 + k13, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setBlockAndMetadata(world, -3 + i1, 5, 10 + k13, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setBlockAndMetadata(world, -3 + i1, 6, 10 + k13, rockSlabBlock, rockSlabMeta);
			}
		}
		setAir(world, -3, 5, 10);
		LOTREntityGondorBlacksmith blacksmith = new LOTREntityGondorBlacksmith(world);
		spawnNPCAndSetHome(blacksmith, world, 0, 1, 6, 4);
		return true;
	}
}
