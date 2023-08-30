package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutTrader extends LOTRWorldGenMoredainHut {
	public LOTRWorldGenMoredainHutTrader(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int k1;
		int i2;
		int i1;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				layFoundation(world, i1, k1);
				for (int j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 3 || k2 == 3) {
					setBlockAndMetadata(world, i1, 1, k1, clayBlock, clayMeta);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 2, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 3, k1, stainedClayBlock, stainedClayMeta);
					setBlockAndMetadata(world, i1, 4, k1, stainedClayBlock, stainedClayMeta);
				}
				if (i2 != 3 || k2 != 3) {
					continue;
				}
				for (int j1 = 2; j1 <= 3; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 4, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 == 3 && k2 == 3) {
					setBlockAndMetadata(world, i1, 4, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 == 4 && k2 == 0 || k2 == 4 && i2 == 0) {
					setBlockAndMetadata(world, i1, 4, k1, thatchBlock, thatchMeta);
					setBlockAndMetadata(world, i1, 5, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
					if (i1 != 0 || k1 != -4) {
						setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
						dropFence(world, i1, 1, k1);
					}
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 5, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 == 3 && k2 == 0 || k2 == 3 && i2 == 0) {
					setBlockAndMetadata(world, i1, 5, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 5, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 + k2 == 2) {
					setBlockAndMetadata(world, i1, 6, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 + k2 == 1) {
					setBlockAndMetadata(world, i1, 6, k1, thatchBlock, thatchMeta);
				}
				if (i2 <= 2 && k2 <= 2 && i2 + k2 >= 3) {
					setBlockAndMetadata(world, i1, 5, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i1, 5, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 != 2 || k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 4, k1, fenceBlock, fenceMeta);
			}
		}
		setAir(world, 0, 1, -3);
		setAir(world, 0, 2, -3);
		setBlockAndMetadata(world, 0, 3, -3, plankSlabBlock, plankSlabMeta | 8);
		layFoundation(world, -2, -4);
		layFoundation(world, 2, -4);
		setBlockAndMetadata(world, -2, 1, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 1, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 1, -2, LOTRMod.chestBasket, 4);
		setBlockAndMetadata(world, -2, 1, -1, stainedClayBlock, stainedClayMeta);
		setBlockAndMetadata(world, -2, 1, 0, stainedClayBlock, stainedClayMeta);
		setBlockAndMetadata(world, -2, 1, 1, LOTRMod.chestBasket, 4);
		setBlockAndMetadata(world, 2, 1, -2, LOTRMod.chestBasket, 5);
		setBlockAndMetadata(world, 2, 1, -1, stainedClayBlock, stainedClayMeta);
		setBlockAndMetadata(world, 2, 1, 0, stainedClayBlock, stainedClayMeta);
		setBlockAndMetadata(world, 2, 1, 1, LOTRMod.chestBasket, 5);
		for (int f : new int[]{-1, 1}) {
			setBlockAndMetadata(world, 2 * f, 1, 2, plankBlock, plankMeta);
			setBlockAndMetadata(world, f, 1, 2, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 0, 1, 2, LOTRMod.moredainTable, 0);
			setBlockAndMetadata(world, 2 * f, 2, 2, plankBlock, plankMeta);
			setBlockAndMetadata(world, f, 2, 2, LOTRMod.chestBasket, 2);
			setBlockAndMetadata(world, 0, 2, 2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2 * f, 3, 2, plankBlock, plankMeta);
			setBlockAndMetadata(world, f, 3, 2, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 0, 3, 2, plankSlabBlock, plankSlabMeta);
		}
		setBlockAndMetadata(world, -2, 2, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 4, -2, Blocks.torch, 3);
		LOTREntityMoredainVillageTrader trader = random.nextBoolean() ? new LOTREntityMoredainHuntsman(world) : new LOTREntityMoredainHutmaker(world);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
		return true;
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
