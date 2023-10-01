package lotr.common.world.village;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRVillageGenHarnedor extends LOTRVillageGen {
	public boolean isRuinedVillage;

	public LOTRVillageGenHarnedor(LOTRBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 4;
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public LOTRVillageGenHarnedor setRuined() {
		isRuinedVillage = true;
		return this;
	}

	public enum VillageType {
		VILLAGE, FORTRESS

	}

	public static class Instance extends LOTRVillageGen.AbstractInstance<LOTRVillageGenHarnedor> {
		public VillageType villageType;
		public boolean isRuined;
		public String[] villageName;
		public int numOuterHouses;
		public boolean palisade;

		public Instance(LOTRVillageGenHarnedor village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
			isRuined = village.isRuinedVillage;
		}

		@Override
		public void addVillageStructures(Random random) {
			if (villageType == VillageType.VILLAGE) {
				setupVillage(random);
			} else {
				setupFortress(random);
			}
		}

		@Override
		public LOTRRoadType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			if (villageType == VillageType.VILLAGE) {
				if (isRuined && random.nextInt(4) == 0) {
					return null;
				}
				int dSq = i * i + k * k;
				int imn = 17 - random.nextInt(3);
				int imx = 22 + random.nextInt(3);
				if (dSq > imn * imn && dSq < imx * imx) {
					return LOTRRoadType.PATH;
				}
				if (palisade && k <= -imx && k >= -66 && i1 < 2 + random.nextInt(3)) {
					return LOTRRoadType.PATH;
				}
			}
			return null;
		}

		public LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
			if (isRuined) {
				return new LOTRWorldGenHarnedorHouseRuined(false);
			}
			if (random.nextInt(5) == 0) {
				return new LOTRWorldGenHarnedorSmithy(false);
			}
			if (random.nextInt(4) == 0) {
				return new LOTRWorldGenHarnedorStables(false);
			}
			return new LOTRWorldGenHarnedorHouse(false);
		}

		@Override
		public boolean isFlat() {
			return false;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupFortress(Random random) {
			addStructure(new LOTRWorldGenNPCRespawner(false) {

				@Override
				public void setupRespawner(LOTREntityNPCRespawner spawner) {
					spawner.setSpawnClass(LOTREntityHarnedhrim.class);
					spawner.setCheckRanges(64, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(50);
				}
			}, 0, 0, 0);
			addStructure(new LOTRWorldGenHarnedorFort(false), 0, -12, 0, true);
			addStructure(new LOTRWorldGenHarnedorTower(false), -24, -24, 0, true);
			addStructure(new LOTRWorldGenHarnedorTower(false), 24, -24, 0, true);
			addStructure(new LOTRWorldGenHarnedorTower(false), -24, 24, 2, true);
			addStructure(new LOTRWorldGenHarnedorTower(false), 24, 24, 2, true);
			for (int l = -1; l <= 1; ++l) {
				int k = l * 10;
				int i = 24;
				addStructure(new LOTRWorldGenNearHaradTent(false), -i, k, 1, true);
				addStructure(new LOTRWorldGenNearHaradTent(false), i, k, 3, true);
			}
			int rSq = 1764;
			int rMax = 43;
			int rSqMax = rMax * rMax;
			for (int i = -42; i <= 42; ++i) {
				for (int k = -42; k <= 42; ++k) {
					int dSq;
					int i1 = Math.abs(i);
					if (i1 <= 4 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
						continue;
					}
					LOTRWorldGenHarnedorPalisade palisade = new LOTRWorldGenHarnedorPalisade(false);
					if (i1 == 5 && k < 0) {
						palisade.setTall();
					}
					addStructure(palisade, i, k, 0);
				}
			}
		}

		public void setupVillage(Random random) {
			if (!isRuined) {
				addStructure(new LOTRWorldGenNPCRespawner(false) {

					@Override
					public void setupRespawner(LOTREntityNPCRespawner spawner) {
						spawner.setSpawnClass(LOTREntityHarnedhrim.class);
						spawner.setCheckRanges(64, -12, 12, 24);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(64);
					}
				}, 0, 0, 0);
				addStructure(new LOTRWorldGenNPCRespawner(false) {

					@Override
					public void setupRespawner(LOTREntityNPCRespawner spawner) {
						spawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
						spawner.setCheckRanges(64, -12, 12, 12);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(64);
					}
				}, 0, 0, 0);
			}
			if (isRuined) {
				addStructure(new LOTRWorldGenHarnedorTavernRuined(false), 3, -7, 0, true);
			} else if (random.nextBoolean()) {
				addStructure(new LOTRWorldGenHarnedorMarket(false), 0, -8, 0, true);
			} else {
				addStructure(new LOTRWorldGenHarnedorTavern(false), 3, -7, 0, true);
			}
			float frac = 1.0f / numOuterHouses;
			float turn = 0.0f;
			while (turn < 1.0f) {
				float turnR = (float) Math.toRadians((turn += frac) * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turn * 8.0f;
				if (turn8 >= 1.0f && turn8 < 3.0f) {
					r = 0;
				} else if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				int l = 25;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				if (palisade && k < 0 && Math.abs(i) < 10) {
					continue;
				}
				addStructure(getRandomHouse(random), i, k, r);
			}
			if (!isRuined) {
				int numFarms = numOuterHouses * 2;
				frac = 1.0f / numFarms;
				turn = 0.0f;
				while (turn < 1.0f) {
					float turnR = (float) Math.toRadians((turn += frac) * 360.0f);
					float sin = MathHelper.sin(turnR);
					float cos = MathHelper.cos(turnR);
					int r = 0;
					float turn8 = turn * 8.0f;
					if (turn8 >= 1.0f && turn8 < 3.0f) {
						r = 0;
					} else if (turn8 >= 3.0f && turn8 < 5.0f) {
						r = 1;
					} else if (turn8 >= 5.0f && turn8 < 7.0f) {
						r = 2;
					} else if (turn8 >= 7.0f || turn8 < 1.0f) {
						r = 3;
					}
					int l = 45;
					int i = Math.round(l * cos);
					int k = Math.round(l * sin);
					if (palisade && k < 0 && Math.abs(i) < 10) {
						continue;
					}
					if (random.nextInt(3) == 0) {
						addStructure(new LOTRWorldGenHayBales(false), i, k, r);
						continue;
					}
					if (random.nextInt(3) == 0) {
						addStructure(new LOTRWorldGenHarnedorPasture(false), i, k, r);
						continue;
					}
					addStructure(new LOTRWorldGenHarnedorFarm(false), i, k, r);
				}
			}
			if (!isRuined) {
				if (palisade) {
					addStructure(new LOTRWorldGenHarnedorVillageSign(false).setSignText(villageName), 5 * (random.nextBoolean() ? 1 : -1), -56, 0, true);
				} else {
					addStructure(new LOTRWorldGenHarnedorVillageSign(false).setSignText(villageName), 0, -16, 0, true);
				}
			}
			if (palisade) {
				int rSq = 3721;
				int rMax = 62;
				int rSqMax = rMax * rMax;
				for (int i = -61; i <= 61; ++i) {
					for (int k = -61; k <= 61; ++k) {
						int dSq;
						LOTRWorldGenHarnedorPalisade palisade;
						int i1 = Math.abs(i);
						if (i1 <= 4 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
							continue;
						}
						if (isRuined) {
							if (random.nextBoolean()) {
								continue;
							}
							palisade = new LOTRWorldGenHarnedorPalisadeRuined(false);
						} else {
							palisade = new LOTRWorldGenHarnedorPalisade(false);
						}
						if (i1 == 5 && k < 0) {
							palisade.setTall();
						}
						addStructure(palisade, i, k, 0);
					}
				}
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = random.nextInt(4) == 0 ? VillageType.FORTRESS : VillageType.VILLAGE;
			villageName = LOTRNames.getHaradVillageName(random);
			numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
			palisade = random.nextInt(3) != 0;
		}

	}

}
