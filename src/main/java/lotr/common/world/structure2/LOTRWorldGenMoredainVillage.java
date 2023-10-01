package lotr.common.world.structure2;

import lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class LOTRWorldGenMoredainVillage extends LOTRWorldGenStructureBase2 {
	public static int VILLAGE_SIZE = 16;

	public LOTRWorldGenMoredainVillage(boolean flag) {
		super(flag);
	}

	public void attemptHutSpawn(LOTRWorldGenStructureBase2 structure, World world, Random random) {
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
		LOTRWorldGenMoredainHut structure;
		int l;
		setOriginAndRotation(world, i, j, k, rotation, usingPlayer != null ? VILLAGE_SIZE + 1 : 0);
		if (restrictions) {
			boolean suitableSpawn = false;
			BiomeGenBase biome = world.getBiomeGenForCoords(originX, originZ);
			if (biome instanceof LOTRBiomeGenFarHaradSavannah) {
				suitableSpawn = LOTRBiomeGenFarHaradSavannah.isBiomePopulated(originX, originY, originZ);
			}
			if (!suitableSpawn) {
				return false;
			}
		}
		int huts = MathHelper.getRandomIntegerInRange(random, 3, 6);
		int traderHuts = MathHelper.getRandomIntegerInRange(random, 0, 2);
		int chieftainHuts = MathHelper.getRandomIntegerInRange(random, 0, 1);
		for (l = 0; l < chieftainHuts; ++l) {
			structure = new LOTRWorldGenMoredainHutChieftain(notifyChanges);
			attemptHutSpawn(structure, world, random);
		}
		for (l = 0; l < huts; ++l) {
			structure = new LOTRWorldGenMoredainHutVillage(notifyChanges);
			attemptHutSpawn(structure, world, random);
		}
		for (l = 0; l < traderHuts; ++l) {
			structure = new LOTRWorldGenMoredainHutTrader(notifyChanges);
			attemptHutSpawn(structure, world, random);
		}
		return true;
	}
}
