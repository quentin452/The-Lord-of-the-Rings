package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercCamp extends LOTRWorldGenCampBase {
	public LOTRWorldGenMoredainMercCamp(boolean flag) {
		super(flag);
	}

	@Override
	public LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
		return new LOTRWorldGenMoredainMercTent(false);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		if (super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			int dummies = 1 + random.nextInt(3);
			for (int l = 0; l < dummies; ++l) {
				int r;
				int k1;
				int i1;
				float ang;
				//noinspection StatementWithEmptyBody
				for (int att = 0; att < 8 && !generateSubstructureWithRestrictionFlag(new LOTRWorldGenMoredainMercDummy(notifyChanges), world, random, i1 = (int) ((r = MathHelper.getRandomIntegerInRange(random, 8, 15)) * MathHelper.cos(ang = random.nextFloat() * 3.1415927f * 2.0f)), getTopBlock(world, i1, k1 = (int) (r * MathHelper.sin(ang))), k1, random.nextInt(4), true); ++att) {
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public LOTREntityNPC getCampCaptain(World world, Random random) {
		return null;
	}

	@Override
	public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityMoredainMercenary.class);
		respawner.setCheckRanges(24, -12, 12, 10);
		respawner.setSpawnRanges(8, -4, 4, 16);
		placeNPCRespawner(respawner, world, i, j, k);
		int mercs = 2 + random.nextInt(5);
		for (int l = 0; l < mercs; ++l) {
			LOTREntityMoredainMercenary merc = new LOTREntityMoredainMercenary(world);
			spawnNPCAndSetHome(merc, world, 0, 1, 0, 16);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tableBlock = LOTRMod.commandTable;
		brickBlock = LOTRMod.planks2;
		brickMeta = 2;
		brickSlabBlock = LOTRMod.woodSlabSingle3;
		brickSlabMeta = 2;
		fenceBlock = LOTRMod.fence2;
		fenceMeta = 2;
		fenceGateBlock = LOTRMod.fenceGateCedar;
	}
}
