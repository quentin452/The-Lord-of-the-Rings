package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanVillage extends LOTRWorldGenStructureBase2 {
	public static int VILLAGE_SIZE = 16;

	public LOTRWorldGenAngmarHillmanVillage(boolean flag) {
		super(flag);
	}

	public void attemptHouseSpawn(LOTRWorldGenStructureBase2 structure, World world, Random random) {
		structure.restrictions = restrictions;
		structure.usingPlayer = usingPlayer;
		for (int l = 0; l < 16; ++l) {
			int x = MathHelper.getRandomIntegerInRange(random, -VILLAGE_SIZE, VILLAGE_SIZE);
			int z = MathHelper.getRandomIntegerInRange(random, -VILLAGE_SIZE, VILLAGE_SIZE);
			int spawnX = getX(x, z);
			int spawnZ = getZ(x, z);
			int spawnY = getY(getTopBlock(world, x, z));
			if (!structure.generateWithSetRotation(world, random, spawnX, spawnY, spawnZ, random.nextInt(4))) {
				continue;
			}
			return;
		}
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		LOTRWorldGenStructureBase2 structure;
		int l;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		if (restrictions) {
			boolean suitableSpawn = true;
			world.getBiomeGenForCoords(originX, originZ);
			if (!suitableSpawn) {
				return false;
			}
		}
		int houses = MathHelper.getRandomIntegerInRange(random, 3, 6);
		int chiefainHouses = MathHelper.getRandomIntegerInRange(random, 0, 1);
		for (l = 0; l < chiefainHouses; ++l) {
			structure = new LOTRWorldGenAngmarHillmanChieftainHouse(notifyChanges);
			attemptHouseSpawn(structure, world, random);
		}
		for (l = 0; l < houses; ++l) {
			structure = new LOTRWorldGenAngmarHillmanHouse(notifyChanges);
			attemptHouseSpawn(structure, world, random);
		}
		return true;
	}
}
