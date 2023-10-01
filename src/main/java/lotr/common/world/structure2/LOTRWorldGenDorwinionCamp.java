package lotr.common.world.structure2;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDorwinionCamp extends LOTRWorldGenDorwinionTent {
	public LOTRWorldGenDorwinionCamp(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		LOTRWorldGenDorwinionCaptainTent captainTent = new LOTRWorldGenDorwinionCaptainTent(notifyChanges);
		captainTent.restrictions = true;
		int i1 = 0;
		int k1 = -7;
		int j1 = getTopBlock(world, i1, k1);
		int r = 0;
		if (!captainTent.generateWithSetRotation(world, random, getX(i1, k1), getY(j1), getZ(i1, k1), (getRotationMode() + r) % 4)) {
			return false;
		}
		int xMin = 8;
		int xMax = 12;
		int zMin = -5;
		int zMax = 5;
		for (int k2 : new int[]{-9, 0, 9}) {
			tryGenerateTent(world, random, new int[]{-xMax, -xMin}, new int[]{k2 + zMin, k2 + zMax}, 3);
			tryGenerateTent(world, random, new int[]{xMin, xMax}, new int[]{k2 + zMin, k2 + zMax}, 1);
		}
		return true;
	}

	public void tryGenerateTent(World world, Random random, int[] i, int[] k, int r) {
		LOTRWorldGenDorwinionTent tent = new LOTRWorldGenDorwinionTent(notifyChanges);
		tent.restrictions = true;
		int attempts = 1;
		for (int l = 0; l < attempts; ++l) {
			int i1 = MathHelper.getRandomIntegerInRange(random, i[0], i[1]);
			int k1 = MathHelper.getRandomIntegerInRange(random, k[0], k[1]);
			int j1 = getTopBlock(world, i1, k1);
			if (!tent.generateWithSetRotation(world, random, getX(i1, k1), getY(j1), getZ(i1, k1), (getRotationMode() + r) % 4)) {
				continue;
			}
			return;
		}
	}
}
