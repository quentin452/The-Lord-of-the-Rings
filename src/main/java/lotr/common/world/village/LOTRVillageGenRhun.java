package lotr.common.world.village;

import java.util.Random;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.world.World;

public class LOTRVillageGenRhun extends LOTRVillageGen {
	public boolean enableTowns;

	public LOTRVillageGenRhun(LOTRBiome biome, float f, boolean flag) {
		super(biome);
		gridScale = 14;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
		enableTowns = flag;
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends LOTRVillageGen.AbstractInstance<LOTRVillageGenRhun> {
		public VillageType villageType;
		public String[] villageName;
		public boolean enableTowns;

		public Instance(LOTRVillageGenRhun village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
			enableTowns = village.enableTowns;
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

		public LOTRWorldGenStructureBase2 getOtherVillageStructure(Random random) {
			if (random.nextBoolean()) {
				return new LOTRWorldGenEasterlingStables(false);
			}
			return new LOTRWorldGenEasterlingSmithy(false);
		}

		@Override
		public LOTRRoadType getPath(Random random, int i, int k) {
			int innerOut;
			int outerOut;
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				int dSq = i * i + k * k;
				int imn = 15 + random.nextInt(4);
				if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
					return LOTRRoadType.PATH;
				}
			}
			if (villageType == VillageType.TOWN) {
				innerOut = 18;
				if (i1 <= innerOut && k1 <= innerOut && (i1 >= 12 || k1 >= 12)) {
					return LOTRRoadType.RHUN;
				}
				if (i1 <= 3 && k1 >= innerOut && k1 <= 86 || k1 <= 3 && i1 >= innerOut && i1 <= 86) {
					return LOTRRoadType.RHUN;
				}
				outerOut = 66;
				if (i1 <= outerOut && k1 <= outerOut && (i1 >= 60 || k1 >= 60)) {
					return LOTRRoadType.RHUN;
				}
			}
			if (villageType == VillageType.FORT) {
				innerOut = 24;
				if (i1 <= innerOut && k1 <= innerOut && (i1 >= 20 || k1 >= 20)) {
					return LOTRRoadType.RHUN;
				}
				if (k >= 14 && k <= 54 && i1 <= 2) {
					return LOTRRoadType.RHUN;
				}
				outerOut = 52;
				if (i1 <= outerOut && k1 <= outerOut && (i1 >= 48 || k1 >= 48)) {
					return LOTRRoadType.RHUN;
				}
			}
			return null;
		}

		public LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
			return new LOTRWorldGenEasterlingHouse(false);
		}

		public LOTRWorldGenStructureBase2 getRandomVillageFarm(Random random) {
			if (random.nextBoolean()) {
				return new LOTRWorldGenEasterlingVillageFarm.Animals(false);
			}
			return new LOTRWorldGenEasterlingVillageFarm.Crops(false);
		}

		@Override
		public boolean isFlat() {
			return villageType == VillageType.TOWN;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupFort(Random random) {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityEasterling.class);
					spawner.setCheckRanges(50, -12, 12, 16);
					spawner.setSpawnRanges(30, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-48, 48}) {
				for (int k1 : new int[]{-48, 48}) {
					addStructure(new LOTRWorldGenNPCRespawner(false) {

						@Override
						public void setupRespawner(LOTREntityNPCRespawner spawner) {
							spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
							spawner.setCheckRanges(32, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(40);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new LOTRWorldGenEasterlingFortress(false), 0, 13, 2, true);
			int stableX = 26;
			int stableZ = 0;
			addStructure(new LOTRWorldGenEasterlingStables(false), -stableX, stableZ, 1, true);
			addStructure(new LOTRWorldGenEasterlingStables(false), stableX, stableZ, 3, true);
			int wellZ = 18;
			addStructure(new LOTRWorldGenEasterlingWell(false), -stableX, wellZ, 1, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), stableX, wellZ, 3, true);
			int farmZ = 27;
			for (int l = -3; l <= 3; ++l) {
				int farmX = l * 10;
				if (random.nextInt(3) == 0) {
					addStructure(new LOTRWorldGenHayBales(false), farmX, -farmZ - 5, 2);
					continue;
				}
				addStructure(getRandomVillageFarm(random), farmX, -farmZ, 2);
			}
			int statueX = 6;
			int statueZ = 36;
			addStructure(new LOTRWorldGenEasterlingStatue(false), -statueX, statueZ, 1, true);
			addStructure(new LOTRWorldGenEasterlingStatue(false), statueX, statueZ, 3, true);
			addStructure(new LOTRWorldGenEasterlingGatehouse(false).disableSigns(), 0, 62, 2, true);
			int towerX = 58;
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -towerX, 0);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), towerX, 0, 1);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -towerX, 0, 3);
			for (int l = 0; l <= 5; ++l) {
				int wallX = 11 + l * 8;
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -towerX, 0);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -towerX, 0);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), towerX, wallX, 1);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), towerX, -wallX, 1);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, towerX, 2);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, towerX, 2);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -towerX, -wallX, 3);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -towerX, wallX, 3);
			}
			int lampX = 17;
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
			lampX = 45;
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
			lampX = 7;
			int lampZ = 64;
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
		}

		public void setupTown(Random random) {
			int marketZ;
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityEasterling.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int spawnerX = 60;
			for (int i1 : new int[]{-spawnerX, spawnerX}) {
				for (int k1 : new int[]{-spawnerX, spawnerX}) {
					addStructure(new LOTRWorldGenNPCRespawner(false) {

						@Override
						public void setupRespawner(LOTREntityNPCRespawner spawner) {
							spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
							spawner.setCheckRanges(50, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			if (random.nextBoolean()) {
				addStructure(new LOTRWorldGenEasterlingGarden(false), 0, 10, 2, true);
			} else {
				addStructure(new LOTRWorldGenEasterlingStatue(false), 0, 6, 2, true);
			}
			int mansionX = 12;
			int mansionZ = 20;
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, -mansionZ, 2, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, -mansionZ, 2, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, mansionZ, 0, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, mansionZ, 0, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, -mansionX, 1, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, mansionX, 1, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, -mansionX, 3, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, mansionX, 3, true);
			for (int l = 0; l <= 3; ++l) {
				int houseX = 10 + 14 * l;
				int houseZ1 = 58;
				int houseZ2 = 68;
				if (l <= 2) {
					if (l >= 1) {
						if (l == 1) {
							addStructure(new LOTRWorldGenEasterlingTavernTown(false), -houseX - 7, -houseZ1, 0, true);
						}
					} else {
						addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ1, 0, true);
					}
					addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, -houseZ1, 0, true);
					if (l >= 1) {
						addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ1, 2, true);
						addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ1, 2, true);
					}
					addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, -houseX, 3, true);
					addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, houseX, 3, true);
					addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, -houseX, 1, true);
					addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, houseX, 1, true);
				}
				if (l == 1) {
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, -houseZ2, 2, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, -houseZ2, 2, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, houseZ2, 0, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, houseZ2, 0, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, -houseX, 1, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, houseX, 1, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, -houseX, 3, true);
					addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, houseX, 3, true);
					continue;
				}
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ2, 2, true);
				addStructure(l == 3 ? new LOTRWorldGenEasterlingSmithy(false) : new LOTRWorldGenEasterlingTownHouse(false), houseX, -houseZ2, 2, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ2, 0, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ2, 0, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, -houseX, 1, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, houseX, 1, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, -houseX, 3, true);
				addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, houseX, 3, true);
			}
			int marketX = 4;
			for (int l = 0; l <= 2; ++l) {
				marketZ = 56 - l * 7;
				addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, marketZ, 1, true);
				addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, marketZ, 3, true);
			}
			marketX = 14;
			marketZ = 59;
			addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, marketZ, 2, true);
			addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, marketZ, 2, true);
			int gardenX = 58;
			addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -gardenX + 5, -gardenX, 0, true);
			addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), gardenX - 5, -gardenX, 0, true);
			addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -gardenX + 5, gardenX, 2, true);
			addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), gardenX - 5, gardenX, 2, true);
			int wellX = 69;
			int wellZ = 63;
			addStructure(new LOTRWorldGenEasterlingWell(false), -wellX, -wellZ, 1, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, -wellX, 2, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), wellX, -wellZ, 3, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, -wellX, 2, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), -wellX, wellZ, 1, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, wellX, 0, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), wellX, wellZ, 3, true);
			addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, wellX, 0, true);
			addStructure(new LOTRWorldGenEasterlingGatehouse(false).setSignText(villageName), 0, 94, 2, true);
			int towerX = 90;
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			addStructure(new LOTRWorldGenEasterlingTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -towerX, 0);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), towerX, 0, 1);
			addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -towerX, 0, 3);
			for (int l = 0; l <= 9; ++l) {
				int wallX = 11 + l * 8;
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -towerX, 0);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -towerX, 0);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), towerX, wallX, 1);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), towerX, -wallX, 1);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, towerX, 2);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, towerX, 2);
				addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -towerX, -wallX, 3);
				addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -towerX, wallX, 3);
			}
			int lampX = 7;
			int lampZ = 96;
			addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
			addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
		}

		public void setupVillage(Random random) {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityEasterling.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			addStructure(new LOTRWorldGenEasterlingWell(false), 0, -2, 0, true);
			int signX = 12;
			addStructure(new LOTRWorldGenEasterlingVillageSign(false).setSignText(villageName), -signX, 0, 1, true);
			addStructure(new LOTRWorldGenEasterlingVillageSign(false).setSignText(villageName), signX, 0, 3, true);
			addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), 0, -centreSide, 2, true);
			if (random.nextBoolean()) {
				addStructure(new LOTRWorldGenEasterlingTavern(false), -pathEnd, 0, 1, true);
				addStructure(getOtherVillageStructure(random), pathEnd, 0, 3, true);
			} else {
				addStructure(getOtherVillageStructure(random), -pathEnd, 0, 1, true);
				addStructure(new LOTRWorldGenEasterlingTavern(false), pathEnd, 0, 3, true);
			}
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					addStructure(getRandomHouse(random), i1, -k1, 2);
				}
				addStructure(getRandomHouse(random), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					if (random.nextInt(3) == 0) {
						addStructure(getRandomVillageFarm(random), i1, -k2, 2);
					} else {
						addStructure(new LOTRWorldGenHayBales(false), i1, -k2, 2);
					}
				}
				if (random.nextInt(3) == 0) {
					addStructure(getRandomVillageFarm(random), i1, k2, 0);
					continue;
				}
				addStructure(new LOTRWorldGenHayBales(false), i1, k2, 0);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageName = LOTRNames.getRhunVillageName(random);
			villageType = random.nextInt(4) == 0 ? VillageType.FORT : enableTowns && random.nextInt(4) == 0 ? VillageType.TOWN : VillageType.VILLAGE;
		}

	}

	public enum VillageType {
		VILLAGE, TOWN, FORT

	}

}
