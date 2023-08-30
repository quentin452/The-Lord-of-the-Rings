package lotr.common.world.village;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class LOTRVillageGenSouthron extends LOTRVillageGen {
	public LOTRVillageGenSouthron(LOTRBiome biome, float f) {
		super(biome);
		gridScale = 14;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 5;
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends LOTRVillageGen.AbstractInstance<LOTRVillageGenSouthron> {
		public VillageType villageType;
		public String[] villageName;

		public Instance(LOTRVillageGenSouthron village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			if (villageType == VillageType.VILLAGE) {
				setupVillage(random);
			} else if (villageType == VillageType.TOWN) {
				setupTown(random);
			} else if (villageType == VillageType.FORT) {
				setupFort(random);
			}
		}

		public LOTRWorldGenStructureBase2 getBarracks(Random random) {
			return new LOTRWorldGenSouthronBarracks(false);
		}

		public LOTRWorldGenStructureBase2 getBazaar(Random random) {
			return new LOTRWorldGenSouthronBazaar(false);
		}

		public LOTRWorldGenStructureBase2 getFlowers(Random random) {
			return new LOTRWorldGenSouthronTownFlowers(false);
		}

		public LOTRWorldGenStructureBase2 getFortCorner(Random random) {
			return new LOTRWorldGenSouthronFortCorner(false);
		}

		public LOTRWorldGenStructureBase2 getFortGate(Random random) {
			return new LOTRWorldGenSouthronFortGate(false);
		}

		public LOTRWorldGenStructureBase2 getFortress(Random random) {
			return new LOTRWorldGenSouthronFortress(false);
		}

		public LOTRWorldGenStructureBase2 getFortWallLong(Random random) {
			return new LOTRWorldGenSouthronFortWall.Long(false);
		}

		public LOTRWorldGenStructureBase2 getFortWallShort(Random random) {
			return new LOTRWorldGenSouthronFortWall.Short(false);
		}

		public LOTRWorldGenStructureBase2 getHouse(Random random) {
			return new LOTRWorldGenSouthronHouse(false);
		}

		public LOTRWorldGenStructureBase2 getLamp(Random random) {
			return new LOTRWorldGenSouthronLamp(false);
		}

		public LOTRWorldGenStructureBase2 getMansion(Random random) {
			return new LOTRWorldGenSouthronMansion(false);
		}

		@Override
		public LOTRRoadType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				int imn = 2;
				int imx = 14 + random.nextInt(3);
				int kmn = 2;
				int kmx = 14 + random.nextInt(3);
				if (i1 <= imx && k1 <= kmx && (i1 > imn || k1 > kmn)) {
					return LOTRRoadType.PATH;
				}
				imn = 45 - random.nextInt(3);
				imx = 50 + random.nextInt(3);
				kmn = 45 - random.nextInt(3);
				kmx = 50 + random.nextInt(3);
				if (i1 <= imx && k1 <= kmx && (i1 > imn || k1 > kmn) && (k < 0 || i1 > 7)) {
					return LOTRRoadType.PATH;
				}
				if (k < 0) {
					imn = 14;
					imx = 45;
					if (i1 + k1 >= imn + imn && i1 + k1 <= imx + imx && Math.abs(i1 - k1) <= (int) (2.5f + random.nextInt(3) * 2.0f)) {
						return LOTRRoadType.PATH;
					}
				}
				if (k > 0) {
					imn = 10;
					imx = imn + 5 + random.nextInt(3);
					kmn = 14;
					kmx = 45;
					if (k1 >= kmn && k1 <= kmx && i1 >= imn - random.nextInt(3) && i1 <= imx) {
						return LOTRRoadType.PATH;
					}
				}
			}
			if (villageType == VillageType.TOWN && i1 <= 72 && k1 <= 42) {
				return LOTRRoadType.HARAD_TOWN;
			}
			if (villageType == VillageType.FORT) {
				if (i1 <= 3 && k >= -45 && k <= -15) {
					return LOTRRoadType.PATH;
				}
				if (i1 <= 36 && k >= -27 && k <= -20) {
					return LOTRRoadType.PATH;
				}
				if (i1 >= 29 && i1 <= 36 && k >= -27 && k <= 39 && (k < -7 || k > 7)) {
					return LOTRRoadType.PATH;
				}
				if (i1 <= 36 && k >= 20 && k <= 27) {
					return LOTRRoadType.PATH;
				}
			}
			return null;
		}

		public LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new LOTRWorldGenSouthronFarm(false);
			}
			return new LOTRWorldGenSouthronPasture(false);
		}

		public LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
			if (random.nextInt(6) == 0) {
				return new LOTRWorldGenSouthronSmithy(false);
			}
			if (random.nextInt(6) == 0) {
				return new LOTRWorldGenSouthronStables(false);
			}
			return new LOTRWorldGenSouthronHouse(false);
		}

		public LOTRWorldGenSouthronVillageSign getSignpost(Random random) {
			return new LOTRWorldGenSouthronVillageSign(false);
		}

		public LOTRWorldGenStructureBase2 getSmithy(Random random) {
			return new LOTRWorldGenSouthronSmithy(false);
		}

		public LOTRWorldGenStructureBase2 getStables(Random random) {
			return new LOTRWorldGenSouthronStables(false);
		}

		public LOTRWorldGenStructureBase2 getStatue(Random random) {
			return new LOTRWorldGenSouthronStatue(false);
		}

		public LOTRWorldGenStructureBase2 getTavern(Random random) {
			return new LOTRWorldGenSouthronTavern(false);
		}

		public LOTRWorldGenStructureBase2 getTower(Random random) {
			return new LOTRWorldGenSouthronTower(false);
		}

		public LOTRWorldGenSouthronTownGate getTownGate(Random random) {
			return new LOTRWorldGenSouthronTownGate(false);
		}

		public LOTRWorldGenStructureBase2 getTownWallCorner(Random random) {
			return new LOTRWorldGenSouthronTownCorner(false);
		}

		public LOTRWorldGenStructureBase2 getTownWallExtra(Random random) {
			return new LOTRWorldGenSouthronTownWall.Extra(false);
		}

		public LOTRWorldGenStructureBase2 getTownWallLong(Random random) {
			return new LOTRWorldGenSouthronTownWall.Long(false);
		}

		public LOTRWorldGenStructureBase2 getTownWallShort(Random random) {
			return new LOTRWorldGenSouthronTownWall.Short(false);
		}

		public LOTRWorldGenStructureBase2 getTownWallSideMid(Random random) {
			return new LOTRWorldGenSouthronTownWall.SideMid(false);
		}

		public LOTRWorldGenStructureBase2 getTraining(Random random) {
			return new LOTRWorldGenSouthronTraining(false);
		}

		public LOTRWorldGenStructureBase2 getTree(Random random) {
			return new LOTRWorldGenSouthronTownTree(false);
		}

		public LOTRWorldGenStructureBase2 getWell(Random random) {
			return new LOTRWorldGenSouthronWell(false);
		}

		@Override
		public boolean isFlat() {
			return false;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			if (villageType == VillageType.TOWN) {
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				return block == LOTRMod.brick && meta == 15 || block == LOTRMod.brick3 && meta == 11 || block == LOTRMod.pillar && meta == 5;
			}
			return false;
		}

		public void placeChampionRespawner() {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntitySouthronChampion.class);
					spawner.setCheckRanges(60, -12, 12, 4);
					spawner.setSpawnRanges(24, -6, 6, 32);
				}
			}, 0, 0, 0);
		}

		public void setCivilianSpawnClass(LOTREntityNPCRespawner spawner) {
			spawner.setSpawnClass(LOTREntityNearHaradrim.class);
		}

		public void setupFort(Random random) {
			int i;
			int r;
			int k;
			int l;
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(60, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-25, 25}) {
				for (int k1 : new int[]{-25, 25}) {
					addStructure(new LOTRWorldGenNPCRespawner(false) {

						@Override
						public void setupRespawner(LOTREntityNPCRespawner spawner) {
							setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(35, -12, 12, 16);
							spawner.setSpawnRanges(15, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(35);
						}
					}, i1, k1, 0);
				}
			}
			placeChampionRespawner();
			addStructure(getFortress(random), 0, -15, 0, true);
			addStructure(getBarracks(random), -33, -8, 0, true);
			addStructure(getBarracks(random), 32, -8, 0, true);
			addStructure(getTower(random), -43, -36, 2, true);
			addStructure(getTower(random), 43, -36, 2, true);
			addStructure(getTower(random), -43, 36, 0, true);
			addStructure(getTower(random), 43, 36, 0, true);
			for (l = 0; l <= 2; ++l) {
				i = 10 + l * 11;
				k = -28;
				r = 2;
				addStructure(getRandomFarm(random), i, k, r);
				addStructure(getRandomFarm(random), -i, k, r);
			}
			addStructure(getTraining(random), 0, 27, 0, true);
			addStructure(getStables(random), -29, 33, 3, true);
			addStructure(getStables(random), 29, 37, 1, true);
			addStructure(getFortGate(random), 0, -47, 0, true);
			for (l = 0; l <= 9; ++l) {
				i = 8 + l * 4;
				k = -46;
				r = 0;
				addStructure(getFortWallLong(random), -i, k, r, true);
				addStructure(getFortWallLong(random), i, k, r, true);
			}
			for (l = -11; l <= 11; ++l) {
				i = l * 4;
				k = 46;
				r = 2;
				addStructure(getFortWallLong(random), i, k, r, true);
			}
			for (l = -10; l <= 10; ++l) {
				i = -50;
				k = l * 4;
				r = 3;
				addStructure(getFortWallLong(random), i, k, r, true);
				r = 1;
				addStructure(getFortWallLong(random), -i, k, r, true);
			}
			addStructure(getFortCorner(random), -50, -46, 0, true);
			addStructure(getFortCorner(random), 50, -46, 1, true);
			addStructure(getFortCorner(random), -50, 46, 3, true);
			addStructure(getFortCorner(random), 50, 46, 2, true);
		}

		public void setupTown(Random random) {
			int i;
			int r;
			int k;
			int l;
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(40, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-30, 30}) {
				for (int k1 : new int[]{-30, 30}) {
					addStructure(new LOTRWorldGenNPCRespawner(false) {

						@Override
						public void setupRespawner(LOTREntityNPCRespawner spawner) {
							setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(getBazaar(random), 1, -2, 0, true);
			addStructure(getLamp(random), 15, -2, 0, true);
			addStructure(getLamp(random), -13, -2, 0, true);
			addStructure(getLamp(random), 15, 18, 0, true);
			addStructure(getLamp(random), -13, 18, 0, true);
			addStructure(getWell(random), -16, 12, 1, true);
			addStructure(getWell(random), -16, 4, 1, true);
			addStructure(getFlowers(random), 18, 13, 3, true);
			addStructure(getFlowers(random), 18, 3, 3, true);
			for (l = 0; l <= 3; ++l) {
				i = -41 + l * 19;
				k = -7;
				r = 2;
				addStructure(getMansion(random), i, k, r, true);
				addStructure(getLamp(random), i + 6, k - 1, r, true);
				i = 24 - l * 19;
				k = 23;
				r = 0;
				addStructure(getMansion(random), i, k, r, true);
				addStructure(getLamp(random), i - 6, k + 1, r, true);
			}
			addStructure(getSmithy(random), -25, 9, 1, true);
			addStructure(getHouse(random), -25, 18, 1, true);
			addStructure(getHouse(random), -25, -2, 1, true);
			addStructure(getTree(random), -45, 8, 1, true);
			addStructure(getHouse(random), -50, 18, 3, true);
			addStructure(getHouse(random), -50, -2, 3, true);
			addStructure(getWell(random), -51, -14, 2, true);
			addStructure(getTree(random), -46, -29, 2, true);
			addStructure(getFlowers(random), -42, -32, 3, true);
			addStructure(getTree(random), -50, 30, 0, true);
			for (l = -3; l <= 3; ++l) {
				i = -56;
				k = -2 + l * 10;
				r = 1;
				addStructure(getHouse(random), i, k, r, true);
			}
			addStructure(getStatue(random), 26, 8, 3, true);
			addStructure(getHouse(random), 26, 18, 3, true);
			addStructure(getHouse(random), 26, -2, 3, true);
			for (l = -3; l <= 2; ++l) {
				i = 52;
				k = 8 + l * 10;
				r = 1;
				addStructure(getHouse(random), i, k, r, true);
			}
			addStructure(getSmithy(random), 41, -33, 3, true);
			for (l = -2; l <= 2; ++l) {
				i = 65;
				k = 3 + l * 14;
				r = 2;
				addStructure(getHouse(random), i, k, r, true);
			}
			addStructure(getWell(random), 57, -19, 2, true);
			addStructure(getLamp(random), 57, -16, 2, true);
			addStructure(getLamp(random), 57, -8, 2, true);
			addStructure(getTree(random), 57, 1, 2, true);
			addStructure(getLamp(random), 57, 4, 2, true);
			addStructure(getLamp(random), 57, 12, 2, true);
			addStructure(getTree(random), 57, 21, 2, true);
			addStructure(getLamp(random), 57, 24, 2, true);
			addStructure(getLamp(random), 57, 32, 2, true);
			for (l = 0; l <= 3; ++l) {
				i = 41 + l * 8;
				k = 34;
				r = 0;
				addStructure(getFlowers(random), i, k, r, true);
			}
			addStructure(getTree(random), 34, 25, 0, true);
			addStructure(getStables(random), -20, -30, 1, true);
			addStructure(getTavern(random), 17, -32, 1, true);
			addStructure(getLamp(random), 19, -28, 1, true);
			addStructure(getLamp(random), 19, -36, 1, true);
			addStructure(getLamp(random), -16, -32, 3, true);
			addStructure(getFlowers(random), 25, -32, 3, true);
			addStructure(getTree(random), 34, -29, 2, true);
			addStructure(getLamp(random), 34, -26, 2, true);
			addStructure(getLamp(random), 34, -18, 2, true);
			addStructure(getTree(random), 34, -9, 2, true);
			addStructure(getTownGate(random).setSignText(villageName), 34, -47, 0, true);
			addStructure(getTownWallCorner(random), 73, -47, 0, true);
			addStructure(getTownWallCorner(random), -77, -43, 3, true);
			addStructure(getTownWallCorner(random), -73, 47, 2, true);
			addStructure(getTownWallCorner(random), 77, 43, 1, true);
			for (l = 0; l <= 6; ++l) {
				i = 68 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				addStructure(getTownWallLong(random), i, k, r, true);
			}
			addStructure(getTownWallExtra(random), 24, -44, 0, true);
			for (l = 0; l <= 22; ++l) {
				i = 20 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				addStructure(getTownWallLong(random), i, k, r, true);
			}
			addStructure(getTownWallSideMid(random), 74, 0, 1, true);
			addStructure(getTownWallSideMid(random), -74, 0, 3, true);
			for (l = 1; l <= 9; ++l) {
				i = 74;
				k = 2 + l * 4;
				if (l % 2 == 1) {
					addStructure(getTownWallShort(random), i, k, 1, true);
					addStructure(getTownWallShort(random), i, -k, 1, true);
					addStructure(getTownWallShort(random), -i, k, 3, true);
					addStructure(getTownWallShort(random), -i, -k, 3, true);
					continue;
				}
				addStructure(getTownWallLong(random), i, k, 1, true);
				addStructure(getTownWallLong(random), i, -k, 1, true);
				addStructure(getTownWallLong(random), -i, k, 3, true);
				addStructure(getTownWallLong(random), -i, -k, 3, true);
			}
			for (l = -17; l <= 17; ++l) {
				i = l * 4;
				k = 44;
				r = 2;
				if (IntMath.mod(l, 2) == 1) {
					addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				addStructure(getTownWallLong(random), i, k, r, true);
			}
		}

		public void setupVillage(Random random) {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					setWarriorSpawnClasses(spawner);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(getWell(random), 0, -2, 0, true);
			addStructure(getSignpost(random).setSignText(villageName), 0, -8, 0, true);
			int rSquareEdge = 17;
			addStructure(getTavern(random), 0, rSquareEdge, 0, true);
			addStructure(getMansion(random), -3, -rSquareEdge, 2, true);
			addStructure(getMansion(random), -rSquareEdge, 3, 1, true);
			addStructure(getMansion(random), rSquareEdge, -3, 3, true);
			int backFenceX = 0;
			int backFenceZ = rSquareEdge + 19;
			int backFenceWidth = 12;
			int sideFenceX = 13;
			int sideFenceZ = rSquareEdge + 11;
			int sideFenceWidth = 8;
			int frontPostZ = sideFenceZ - sideFenceWidth - 1;
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceX, -backFenceZ, 0);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceX, -sideFenceZ, 1);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceX, -sideFenceZ, 3);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -sideFenceX, -frontPostZ, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), sideFenceX, -frontPostZ, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -sideFenceX, -backFenceZ, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), sideFenceX, -backFenceZ, 0);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), -backFenceZ, backFenceX, 1);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceZ, sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), -sideFenceZ, -sideFenceX, 2);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -frontPostZ, sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -frontPostZ, -sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -backFenceZ, sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), -backFenceZ, -sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceZ, backFenceX, 3);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), sideFenceZ, -sideFenceX, 2);
			addStructure(new LOTRWorldGenSouthronVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceZ, sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), frontPostZ, -sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), frontPostZ, sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), backFenceZ, -sideFenceX, 0);
			addStructure(new LOTRWorldGenSouthronVillagePost(false), backFenceZ, sideFenceX, 0);
			int farmRange = 3;
			int farmStep = 14;
			int farmX = 55;
			for (int l = -farmRange; l <= farmRange; ++l) {
				int k = l * farmStep;
				int i = -farmX;
				int r = 1;
				if (random.nextInt(3) == 0) {
					addStructure(new LOTRWorldGenHayBales(false), i, k, r);
				} else {
					addStructure(getRandomFarm(random), i, k, r);
				}
				i = farmX;
				r = 3;
				if (random.nextInt(3) == 0) {
					addStructure(new LOTRWorldGenHayBales(false), i, k, r);
					continue;
				}
				addStructure(getRandomFarm(random), i, k, r);
			}
			int houseRange = 3;
			int houseStep = 17;
			int houseZ = 55;
			for (int l = -houseRange; l <= houseRange; ++l) {
				int i = l * houseStep;
				int k = -houseZ;
				int r = 2;
				addStructure(getRandomHouse(random), i, k, r);
				k = houseZ;
				r = 0;
				if (Math.abs(i) < 7) {
					continue;
				}
				addStructure(getRandomHouse(random), i, k, r);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = random.nextInt(4) == 0 ? VillageType.FORT : random.nextInt(3) == 0 ? VillageType.TOWN : VillageType.VILLAGE;
			villageName = LOTRNames.getHaradVillageName(random);
		}

		public void setWarriorSpawnClasses(LOTREntityNPCRespawner spawner) {
			spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
		}

	}

	public enum VillageType {
		VILLAGE, TOWN, FORT

	}

}
