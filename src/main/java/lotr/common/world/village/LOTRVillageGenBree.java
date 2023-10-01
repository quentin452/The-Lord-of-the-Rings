package lotr.common.world.village;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.entity.npc.LOTREntityBreeHobbit;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.structure2.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class LOTRVillageGenBree extends LOTRVillageGen {
	public LOTRVillageGenBree(LOTRBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 4;
		fixedVillageChunkRadius = 13;
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public enum VillageType {
		HAMLET, VILLAGE

	}

	public static class Instance extends LOTRVillageGen.AbstractInstance<LOTRVillageGenBree> {
		public static boolean[][] hobbitPathLookup;
		public static int[] hobbitBurrowPathPoints;
		public static int[] hobbitBurrowEndPoints;
		public VillageType villageType;
		public int innerSize;
		public boolean hamletHedge;

		public Instance(LOTRVillageGenBree village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
			if (hobbitPathLookup == null) {
				int l;
				int size = 361;
				hobbitPathLookup = new boolean[size][size];
				int numBurrows = 20;
				ArrayList<Integer> burrowCoords = new ArrayList<>();
				ArrayList<Integer> burrowEndCoords = new ArrayList<>();
				int samples = 300;
				int burrowInterval = samples / numBurrows;
				float[] pathPointsX = new float[samples];
				float[] pathPointsZ = new float[samples];
				int zStart = 50;
				int zEnd = 150;
				float cycles = 1.0f;
				int amp = 80;
				int endpointInterval = samples / MathHelper.floor_double(cycles * 4.0f);
				for (l = 0; l < samples; ++l) {
					float x;
					float t = (float) l / samples;
					float z = zStart + (zEnd - zStart) * t;
					pathPointsX[l] = x = MathHelper.sin((z - zStart) / (zEnd - zStart) * cycles * 3.1415927f * 2.0f) * amp;
					pathPointsZ[l] = z;
					if (l % burrowInterval == 0 && Math.abs(x) <= amp * 0.8f) {
						burrowCoords.add(MathHelper.floor_double(x));
						burrowCoords.add(MathHelper.floor_double(z));
					}
					//noinspection BadOddness
					if (l % endpointInterval != 0 || l / endpointInterval % 2 != 1) {
						continue;
					}
					burrowEndCoords.add(MathHelper.floor_double(x));
					burrowEndCoords.add(MathHelper.floor_double(z));
				}
				hobbitBurrowPathPoints = new int[burrowCoords.size()];
				for (l = 0; l < burrowCoords.size(); ++l) {
					hobbitBurrowPathPoints[l] = burrowCoords.get(l);
				}
				hobbitBurrowEndPoints = new int[burrowEndCoords.size()];
				for (l = 0; l < burrowEndCoords.size(); ++l) {
					hobbitBurrowEndPoints[l] = burrowEndCoords.get(l);
				}
				int pathWidth = 3;
				for (int z = -180; z <= 180; ++z) {
					block4:
					for (int x = -180; x <= 180; ++x) {
						int xi = x + 180;
						int zi = z + 180;
						hobbitPathLookup[zi][xi] = false;
						float xMid = x + 0.5f;
						float zMid = z + 0.5f;
						for (int l2 = 0; l2 < samples; ++l2) {
							float pathX = pathPointsX[l2];
							float dx = xMid - pathX;
							float pathZ = pathPointsZ[l2];
							float dz = zMid - pathZ;
							if (dx * dx + dz * dz > pathWidth * pathWidth) {
								continue;
							}
							hobbitPathLookup[zi][xi] = true;
							continue block4;
						}
					}
				}
			}
		}

		@Override
		public void addVillageStructures(Random random) {
			if (villageType == VillageType.HAMLET) {
				setupHamlet(random);
			} else if (villageType == VillageType.VILLAGE) {
				setupVillage(random);
			}
		}

		public LOTRWorldGenStructureBase2 getHamletHouse(Random random) {
			if (random.nextInt(3) == 0) {
				return new LOTRWorldGenBreeHobbitBurrow(false);
			}
			if (random.nextInt(8) == 0) {
				return new LOTRWorldGenBreeRuffianHouse(false);
			}
			return new LOTRWorldGenBreeHouse(false);
		}

		public LOTRWorldGenStructureBase2 getHamletHouseOrOther(Random random) {
			if (random.nextInt(3) == 0) {
				float f = random.nextFloat();
				if (f < 0.08f) {
					return new LOTRWorldGenBreeBarn(false);
				}
				if (f < 0.16f) {
					return new LOTRWorldGenBreeStable(false);
				}
				if (f < 0.4f) {
					return new LOTRWorldGenBreeSmithy(false);
				}
				if (f < 0.7f) {
					return new LOTRWorldGenBreeOffice(false);
				}
				return new LOTRWorldGenBreeInn(false);
			}
			return getHamletHouse(random);
		}

		@Override
		public LOTRRoadType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.HAMLET) {
				int dSq = i * i + k * k;
				int imn = innerSize + random.nextInt(3);
				if (dSq < imn * imn) {
					return LOTRRoadType.PATH;
				}
				if (hamletHedge && k < 0 && k > -(innerSize + 32 + 2) && i1 <= 2 + random.nextInt(3)) {
					return LOTRRoadType.PATH;
				}
			}
			if (villageType == VillageType.VILLAGE) {
				if (i1 <= 192 && k1 <= 3) {
					return LOTRRoadType.PAVED_PATH;
				}
				if (k >= -192 && k <= 50 && i1 <= 3) {
					return LOTRRoadType.PAVED_PATH;
				}
				if (i1 <= 70 && Math.abs(k + 100) <= 3) {
					return LOTRRoadType.PAVED_PATH;
				}
				if (i >= -180 && i <= 180 && k >= -180 && k <= 180 && hobbitPathLookup[k + 180][i + 180]) {
					return LOTRRoadType.PAVED_PATH;
				}
			}
			return null;
		}

		@Override
		public boolean isFlat() {
			return false;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupHamlet(Random random) {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeMan.class);
					spawner.setCheckRanges(40, -12, 12, 20);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeHobbit.class);
					spawner.setCheckRanges(40, -12, 12, 6);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeGuard.class);
					spawner.setCheckRanges(40, -12, 12, 8);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenBreeWell(false), 0, -2, 0, true);
			int lampX = 9;
			for (int i : new int[]{-lampX, lampX}) {
				for (int k : new int[]{-lampX, lampX}) {
					addStructure(new LOTRWorldGenBreeLampPost(false), i, k, 0);
				}
			}
			int rHouse = innerSize + 3;
			if (hamletHedge) {
				addStructure(getHamletHouseOrOther(random), -rHouse, 0, 1);
				addStructure(getHamletHouseOrOther(random), rHouse, 0, 3);
				addStructure(getHamletHouseOrOther(random), 0, rHouse, 0);
				int pathHouseX = 8;
				int pathHouseZ = innerSize + 16;
				addStructure(getHamletHouse(random), -pathHouseX, -pathHouseZ, 1);
				addStructure(getHamletHouse(random), pathHouseX, -pathHouseZ, 3);
			} else {
				addStructure(getHamletHouseOrOther(random), -rHouse, 0, 1);
				addStructure(getHamletHouseOrOther(random), rHouse, 0, 3);
				addStructure(getHamletHouseOrOther(random), 0, rHouse, 0);
				addStructure(getHamletHouseOrOther(random), 0, -rHouse, 2);
			}
			int hayX = innerSize + 16;
			for (int i : new int[]{-hayX, hayX}) {
				for (int k : new int[]{-hayX, hayX}) {
					if (!random.nextBoolean()) {
						continue;
					}
					addStructure(new LOTRWorldGenHayBales(false), i, k, 0);
				}
			}
			LOTRWorldGenBreeMarketStall[] stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, false, 4);
			int stallX = innerSize + 1;
			if (random.nextInt(6) == 0) {
				addStructure(stalls[0], -stallX + 3, -stallX, 1);
			}
			if (random.nextInt(6) == 0) {
				addStructure(stalls[1], stallX, -stallX + 3, 2);
			}
			if (random.nextInt(6) == 0) {
				addStructure(stalls[2], stallX - 3, stallX, 3);
			}
			if (random.nextInt(6) == 0) {
				addStructure(stalls[3], -stallX, stallX - 3, 0);
			}
			if (hamletHedge) {
				int rHedge = innerSize + 32;
				int rSq = rHedge * rHedge;
				int rMax = rHedge + 2;
				int rSqMax = rMax * rMax;
				for (int i = -rMax; i <= rMax; ++i) {
					for (int k = -rMax; k <= rMax; ++k) {
						int dSq;
						if (Math.abs(i) <= 5 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
							continue;
						}
						addStructure(new LOTRWorldGenBreeHedgePart(false), i, k, 0);
					}
				}
			}
		}

		public void setupVillage(Random random) {
			int hobbitZ;
			int l;
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeMan.class);
					spawner.setCheckRanges(64, -24, 24, 32);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeMan.class);
					spawner.setCheckRanges(64, -24, 24, 32);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, -120, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeMan.class);
					spawner.setCheckRanges(64, -24, 24, 32);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 120, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeHobbit.class);
					spawner.setCheckRanges(64, -24, 24, 40);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 80, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeGuard.class);
					spawner.setCheckRanges(64, -24, 24, 8);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeGuard.class);
					spawner.setCheckRanges(64, -24, 24, 8);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, -120, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeGuard.class);
					spawner.setCheckRanges(64, -24, 24, 8);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 120, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityBreeGuard.class);
					spawner.setCheckRanges(64, -24, 24, 8);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, -120, 0);
			LOTRWorldGenBreeInn inn = new LOTRWorldGenBreeInn(false);
			if (locationInfo.getAssociatedWaypoint() == LOTRWaypoint.BREE) {
				inn.setPresets(new String[]{"The Prancing", "Pony"}, "Barliman Butterbur", true, false);
			}
			addStructure(inn, 15, 8, 0, true);
			addStructure(new LOTRWorldGenBreeOffice(false), -15, 8, 0, true);
			int houses = 9;
			for (int i1 = -houses; i1 <= houses; ++i1) {
				int houseX = i1 * 18;
				int houseZ = 5;
				LOTRWorldGenBreeStructure house1 = new LOTRWorldGenBreeHouse(false);
				LOTRWorldGenBreeStructure house2 = new LOTRWorldGenBreeHouse(false);
				boolean forceHouse1 = false;
				boolean forceHouse2 = false;
				if (i1 <= -houses + 2 || i1 == houses) {
					house1 = new LOTRWorldGenBreeRuffianHouse(false);
					house2 = new LOTRWorldGenBreeRuffianHouse(false);
					if (locationInfo.getAssociatedWaypoint() == LOTRWaypoint.BREE && i1 == -houses) {
						house1 = new LOTRWorldGenBreeRuffianHouse(false).setRuffianName("Bill Ferny");
						forceHouse1 = true;
					}
				}
				if (Math.abs(i1) < 2) {
					continue;
				}
				addStructure(house1, houseX, houseZ, 0, forceHouse1);
				if (Math.abs(i1) == 4) {
					addStructure(new LOTRWorldGenBreeSmithy(false), houseX, -houseZ, 2);
				} else {
					addStructure(house2, houseX, -houseZ, 2, forceHouse2);
				}
				int lampX = houseX - Integer.signum(i1) * 9;
				int lampZ = houseZ - 1;
				addStructure(new LOTRWorldGenBreeLampPost(false), lampX, lampZ, 0);
				addStructure(new LOTRWorldGenBreeLampPost(false), lampX, -lampZ, 2);
			}
			LOTRWorldGenBreeMarketStall[] stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, false, 8);
			LOTRWorldGenBreeMarket market1 = new LOTRWorldGenBreeMarket(false).setFrontStepsOnly(true);
			LOTRWorldGenBreeMarket market2 = new LOTRWorldGenBreeMarket(false).setFrontStepsOnly(true);
			market1.setStalls(stalls[0], stalls[1], stalls[2], stalls[3]);
			market2.setStalls(stalls[4], stalls[5], stalls[6], stalls[7]);
			addStructure(market1, -15, -3, 2, true);
			addStructure(market2, 15, -3, 2, true);
			addStructure(new LOTRWorldGenBreeWell(false), 5, -32, 3, true);
			addStructure(new LOTRWorldGenBreeWell(false), -5, -32, 1, true);
			addStructure(new LOTRWorldGenBreeGarden(false), 6, -42, 3, true);
			addStructure(new LOTRWorldGenBreeGarden(false), -6, -42, 1, true);
			for (int i1 = 0; i1 <= 5; ++i1) {
				int houseX = 5;
				int houseZ = -64 - i1 * 18;
				if (i1 != 2) {
					addStructure(new LOTRWorldGenBreeHouse(false), houseX, houseZ, 3);
					addStructure(new LOTRWorldGenBreeHouse(false), -houseX, houseZ, 1);
				}
				int lampX = houseX - 1;
				int lampZ = houseZ - 9;
				addStructure(new LOTRWorldGenBreeLampPost(false), lampX, lampZ, 3);
				addStructure(new LOTRWorldGenBreeLampPost(false), -lampX, lampZ, 1);
			}
			addStructure(new LOTRWorldGenBreeBarn(false), -72, -100, 1, true);
			addStructure(new LOTRWorldGenBreeBarn(false), 72, -100, 3, true);
			addStructure(new LOTRWorldGenBreeStable(false), -40, -106, 2, true);
			addStructure(new LOTRWorldGenBreeStable(false), 40, -106, 2, true);
			addStructure(new LOTRWorldGenBreeWell(false), -40, -94, 0, true);
			addStructure(new LOTRWorldGenBreeWell(false), 40, -94, 0, true);
			addStructure(new LOTRWorldGenBreeWell(false), 5, 28, 3, true);
			addStructure(new LOTRWorldGenBreeWell(false), -5, 28, 1, true);
			addStructure(new LOTRWorldGenBreeGarden(false), 6, 38, 3, true);
			addStructure(new LOTRWorldGenBreeGarden(false), -6, 38, 1, true);
			for (l = 0; l < hobbitBurrowPathPoints.length; l += 2) {
				int hobbitX = hobbitBurrowPathPoints[l];
				hobbitZ = hobbitBurrowPathPoints[l + 1];
				addStructure(new LOTRWorldGenBreeHobbitBurrow(false), hobbitX, hobbitZ + 6, 0, true);
			}
			for (l = 0; l < hobbitBurrowEndPoints.length; l += 2) {
				int hobbitX = hobbitBurrowEndPoints[l];
				hobbitZ = hobbitBurrowEndPoints[l + 1];
				if (Integer.signum(hobbitX) == -1) {
					addStructure(new LOTRWorldGenBreeHobbitBurrow(false), hobbitX - 6, hobbitZ, 1, true);
					continue;
				}
				addStructure(new LOTRWorldGenBreeHobbitBurrow(false), hobbitX + 6, hobbitZ, 3, true);
			}
			addStructure(new LOTRWorldGenBreeGate(false), -182, 0, 3, true);
			addStructure(new LOTRWorldGenBreeGate(false), 182, 0, 1, true);
			addStructure(new LOTRWorldGenBreeGatehouse(false).setName(locationInfo.name), 0, -182, 0, true);
			int rHedge = 180;
			int rHedgeSq = rHedge * rHedge;
			int rHedgeMax = rHedge + 3;
			int rHedgeSqMax = rHedgeMax * rHedgeMax;
			for (int i = -rHedgeMax; i <= rHedgeMax; ++i) {
				for (int k = -rHedgeMax; k <= rHedgeMax; ++k) {
					int dSq = i * i + k * k;
					if (dSq < rHedgeSq || dSq >= rHedgeSqMax) {
						continue;
					}
					int i1 = Math.abs(i);
					int k1 = Math.abs(k);
					if (i1 <= 192 && k1 <= 4 || k >= -192 && k <= 50 && i1 <= 4) {
						continue;
					}
					addStructure(new LOTRWorldGenBreeHedgePart(false), i, k, 0);
				}
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (locationInfo.isFixedLocation()) {
				villageType = VillageType.VILLAGE;
			} else {
				villageType = VillageType.HAMLET;
				innerSize = MathHelper.getRandomIntegerInRange(random, 12, 14);
				hamletHedge = random.nextBoolean();
			}
		}

	}

}
