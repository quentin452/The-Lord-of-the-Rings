package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnEntry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Random;

public class LOTRWorldGenOrcDungeon extends LOTRWorldGenStructureBase {
	public LOTRWorldGenOrcDungeon(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int k1;
		int i1;
		int j1;
		int xSize = random.nextInt(3) + 2;
		int ySize = 3;
		int zSize = random.nextInt(3) + 2;
		int height = 0;
		if (!restrictions && usingPlayer != null) {
			int rotation = usingPlayerRotation();
			switch (rotation) {
				case 0: {
					k += zSize + 2;
					break;
				}
				case 1: {
					i -= xSize + 2;
					break;
				}
				case 2: {
					k -= zSize + 2;
					break;
				}
				case 3: {
					i += xSize + 2;
				}
			}
		}
		if (restrictions) {
			for (i1 = i - xSize - 1; i1 <= i + xSize + 1; ++i1) {
				for (j1 = j - 1; j1 <= j + ySize + 1; ++j1) {
					for (k1 = k - zSize - 1; k1 <= k + zSize + 1; ++k1) {
						Material material = world.getBlock(i1, j1, k1).getMaterial();
						if (j1 == j - 1 && !material.isSolid() || j1 == j + ySize + 1 && !material.isSolid()) {
							return false;
						}
						if (i1 != i - xSize - 1 && i1 != i + xSize + 1 && k1 != k - zSize - 1 && k1 != k + zSize + 1 || j1 != j || !world.isAirBlock(i1, j1, k1) || !world.isAirBlock(i1, j1 + 1, k1)) {
							continue;
						}
						++height;
					}
				}
			}
		} else {
			height = 3;
		}
		if (height >= 1 && height <= 5) {
			for (i1 = i - xSize - 1; i1 <= i + xSize + 1; ++i1) {
				for (j1 = j + ySize; j1 >= j - 1; --j1) {
					for (k1 = k - zSize - 1; k1 <= k + zSize + 1; ++k1) {
						if (i1 != i - xSize - 1 && j1 != j - 1 && k1 != k - zSize - 1 && i1 != i + xSize + 1 && j1 != j + ySize + 1 && k1 != k + zSize + 1) {
							setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.air, 0);
							continue;
						}
						if (restrictions && !world.getBlock(i1, j1, k1).getMaterial().isSolid()) {
							continue;
						}
						if (random.nextInt(4) != 0) {
							setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.stonebrick, 1 + random.nextInt(2));
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j1, k1, Blocks.stonebrick, 0);
					}
				}
			}
			block12:
			for (int chestAttempts = 0; chestAttempts < 2; ++chestAttempts) {
				for (int thisChestAttempts = 0; thisChestAttempts < 3; ++thisChestAttempts) {
					int k12;
					int i12 = i + random.nextInt(xSize * 2 + 1) - xSize;
					if (!world.isAirBlock(i12, j, k12 = k + random.nextInt(zSize * 2 + 1) - zSize)) {
						continue;
					}
					boolean flag = world.getBlock(i12 - 1, j, k12).getMaterial().isSolid();
					if (world.getBlock(i12 + 1, j, k12).getMaterial().isSolid()) {
						flag = true;
					}
					if (world.getBlock(i12, j, k12 - 1).getMaterial().isSolid()) {
						flag = true;
					}
					if (world.getBlock(i12, j, k12 + 1).getMaterial().isSolid()) {
						flag = true;
					}
					if (!flag) {
						continue;
					}
					setBlockAndNotifyAdequately(world, i12, j, k12, LOTRMod.chestStone, 0);
					LOTRChestContents.fillChest(world, random, i12, j, k12, LOTRChestContents.ORC_DUNGEON);
					continue block12;
				}
			}
			Class<LOTREntityGundabadOrc> backupClass = LOTREntityGundabadOrc.class;
			ArrayList<Class> biomeClasses = new ArrayList<>();
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			if (biome instanceof LOTRBiome) {
				LOTRBiomeVariant variant = ((LOTRWorldChunkManager) world.provider.worldChunkMgr).getBiomeVariantAt(i, k);
				LOTRBiomeSpawnList biomeSpawns = ((LOTRBiome) biome).getNPCSpawnList(world, random, i, j, k, variant);
				for (LOTRSpawnEntry spawnEntry : biomeSpawns.getAllSpawnEntries(world)) {
					Class spawnClass = spawnEntry.entityClass;
					if (!LOTREntityOrc.class.isAssignableFrom(spawnClass)) {
						continue;
					}
					biomeClasses.add(spawnClass);
				}
			}
			int orcs = MathHelper.getRandomIntegerInRange(random, 3, 6);
			while (orcs > 0) {
				LOTREntityOrc orc;
				Class orcClass = backupClass;
				if (!biomeClasses.isEmpty()) {
					orcClass = biomeClasses.get(random.nextInt(biomeClasses.size()));
				}
				if ((orc = (LOTREntityOrc) EntityList.createEntityByName(LOTREntities.getStringFromClass(orcClass), world)).isOrcBombardier()) {
					continue;
				}
				orc.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
				orc.setHomeArea(i, j + 1, k, 4);
				orc.onSpawnWithEgg(null);
				orc.isNPCPersistent = true;
				world.spawnEntityInWorld(orc);
				--orcs;
			}
			int pillars = random.nextInt(6);
			block16:
			for (int l = 0; l < pillars; ++l) {
				int j12;
				int i13 = i + random.nextInt(xSize * 2 + 1) - xSize;
				int k13 = k + random.nextInt(zSize * 2 + 1) - zSize;
				if (i13 == i && k13 == k) {
					continue;
				}
				for (j12 = j + ySize; j12 >= j; --j12) {
					if (!world.isAirBlock(i13, j12, k13)) {
						continue block16;
					}
				}
				for (j12 = j + ySize; j12 >= j; --j12) {
					if (random.nextInt(4) != 0) {
						setBlockAndNotifyAdequately(world, i13, j12, k13, Blocks.stonebrick, 1 + random.nextInt(2));
						continue;
					}
					setBlockAndNotifyAdequately(world, i13, j12, k13, Blocks.stonebrick, 0);
				}
			}
			return true;
		}
		return false;
	}
}
