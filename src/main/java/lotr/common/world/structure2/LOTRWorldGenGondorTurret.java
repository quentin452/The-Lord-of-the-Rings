package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorArcher;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenGondorTurret extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenGondorTurret(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int j12;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					j12 = getTopBlock(world, i1, k1);
					Block block = getBlock(world, i1, j12 - 1, k1);
					if (block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, i1, 0, k1, LOTRMod.slabDouble, 2);
				j12 = -1;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, LOTRMod.slabDouble, 2);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
			}
		}
		for (j1 = 1; j1 <= 4; ++j1) {
			for (int i12 = -1; i12 <= 1; ++i12) {
				for (int k12 = -1; k12 <= 1; ++k12) {
					if (Math.abs(i12) == 1 && Math.abs(k12) == 1) {
						setBlockAndMetadata(world, i12, j1, k12, LOTRMod.brick, 5);
						continue;
					}
					setBlockAndMetadata(world, i12, j1, k12, LOTRMod.rock, 1);
				}
			}
		}
		setBlockAndMetadata(world, -2, 1, -2, LOTRMod.slabDouble, 2);
		setBlockAndMetadata(world, -2, 1, 2, LOTRMod.slabDouble, 2);
		setBlockAndMetadata(world, 2, 1, -2, LOTRMod.slabDouble, 2);
		setBlockAndMetadata(world, 2, 1, 2, LOTRMod.slabDouble, 2);
		for (j1 = 2; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, -2, j1, -2, LOTRMod.wall, 2);
			setBlockAndMetadata(world, -2, j1, 2, LOTRMod.wall, 2);
			setBlockAndMetadata(world, 2, j1, -2, LOTRMod.wall, 2);
			setBlockAndMetadata(world, 2, j1, 2, LOTRMod.wall, 2);
		}
		setBlockAndMetadata(world, -2, 5, -2, Blocks.log, 0);
		setBlockAndMetadata(world, -2, 5, 2, Blocks.log, 0);
		setBlockAndMetadata(world, 2, 5, -2, Blocks.log, 0);
		setBlockAndMetadata(world, 2, 5, 2, Blocks.log, 0);
		for (int k13 = -1; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, -2, 1, k13, LOTRMod.stairsGondorBrick, 1);
			setBlockAndMetadata(world, 2, 1, k13, LOTRMod.stairsGondorBrick, 0);
			setBlockAndMetadata(world, -2, 3, k13, LOTRMod.slabSingle, 10);
			setBlockAndMetadata(world, -2, 4, k13, Blocks.log, 0);
			setBlockAndMetadata(world, -2, 5, k13, Blocks.log, 0);
			setBlockAndMetadata(world, 2, 3, k13, LOTRMod.slabSingle, 10);
			setBlockAndMetadata(world, 2, 4, k13, Blocks.log, 0);
			setBlockAndMetadata(world, 2, 5, k13, Blocks.log, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, 2, LOTRMod.stairsGondorBrick, 3);
			setBlockAndMetadata(world, i1, 3, -2, LOTRMod.slabSingle, 10);
			setBlockAndMetadata(world, i1, 4, -2, Blocks.log, 0);
			setBlockAndMetadata(world, i1, 5, -2, Blocks.log, 0);
			setBlockAndMetadata(world, i1, 3, 2, LOTRMod.slabSingle, 10);
			setBlockAndMetadata(world, i1, 4, 2, Blocks.log, 0);
			setBlockAndMetadata(world, i1, 5, 2, Blocks.log, 0);
		}
		for (j1 = 1; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 1, -1, LOTRMod.doorLebethron, 1);
		setBlockAndMetadata(world, 0, 2, -1, LOTRMod.doorLebethron, 8);
		setBlockAndMetadata(world, 0, 5, 0, Blocks.trapdoor, 0);
		setBlockAndMetadata(world, 0, 5, 1, LOTRMod.slabSingle, 2);
		placeChest(world, random, 1, 5, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				if (Math.abs(i1) != 2 && Math.abs(k1) != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 6, k1, LOTRMod.wall, 2);
				if (Math.abs(i1) != 2 || Math.abs(k1) != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 7, k1, Blocks.torch, 5);
			}
		}
		int soldiers = 1 + random.nextInt(2);
		for (int l = 0; l < soldiers; ++l) {
			LOTREntityGondorSoldier soldier = random.nextBoolean() ? new LOTREntityGondorSoldier(world) : new LOTREntityGondorArcher(world);
			soldier.spawnRidingHorse = false;
			spawnNPCAndSetHome(soldier, world, 0, 6, 0, 8);
		}
		return true;
	}
}
