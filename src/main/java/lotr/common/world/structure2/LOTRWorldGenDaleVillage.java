package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillage extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenDaleVillage(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		LOTRWorldGenDaleVillageTower tower = new LOTRWorldGenDaleVillageTower(notifyChanges);
		tower.restrictions = true;
		int i1 = 0;
		int k1 = -3;
		int j1 = getTopBlock(world, i1, k1);
		int r = 0;
		if (!tower.generateWithSetRotation(world, random, getX(i1, k1), getY(j1), getZ(i1, k1), (getRotationMode() + r) % 4)) {
			return false;
		}
		int smithies = MathHelper.getRandomIntegerInRange(random, 0, 1);
		for (int l = 0; l < smithies; ++l) {
			LOTRWorldGenDaleSmithy smithy = new LOTRWorldGenDaleSmithy(notifyChanges);
			smithy.restrictions = true;
			tryGenerateHouse(world, random, smithy);
		}
		int bakeries = MathHelper.getRandomIntegerInRange(random, 0, 1);
		for (int l = 0; l < bakeries; ++l) {
			LOTRWorldGenDaleBakery bakery = new LOTRWorldGenDaleBakery(notifyChanges);
			bakery.restrictions = true;
			tryGenerateHouse(world, random, bakery);
		}
		int houses = MathHelper.getRandomIntegerInRange(random, 2, 5);
		for (int l = 0; l < houses; ++l) {
			LOTRWorldGenDaleHouse house = new LOTRWorldGenDaleHouse(notifyChanges);
			house.restrictions = true;
			tryGenerateHouse(world, random, house);
		}
		return true;
	}

	public void tryGenerateHouse(World world, Random random, LOTRWorldGenStructureBase2 structure) {
		int attempts = 8;
		for (int l = 0; l < attempts; ++l) {
			int i1 = 0;
			int k1 = 0;
			int r = random.nextInt(4);
			switch (r) {
				case 0:
					i1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
					k1 = MathHelper.getRandomIntegerInRange(random, 7, 10);
					break;
				case 1:
					k1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
					i1 = -MathHelper.getRandomIntegerInRange(random, 7, 10);
					break;
				case 2:
					i1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
					k1 = -MathHelper.getRandomIntegerInRange(random, 7, 10);
					break;
				case 3:
					k1 = MathHelper.getRandomIntegerInRange(random, -16, 16);
					i1 = MathHelper.getRandomIntegerInRange(random, 7, 10);
					break;
				default:
					break;
			}
			int j1 = getTopBlock(world, i1, k1);
			if (!structure.generateWithSetRotation(world, random, getX(i1, k1), getY(j1), getZ(i1, k1), (getRotationMode() + r) % 4)) {
				continue;
			}
			return;
		}
	}
}
