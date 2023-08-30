package lotr.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.fac.LOTRFaction;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class LOTRBiomeSpawnList {
	public String biomeIdentifier;
	public Collection<FactionContainer> factionContainers = new ArrayList<>();
	public Collection<LOTRFaction> presentFactions = new ArrayList<>();
	public float conquestGainRate = 1.0f;

	public LOTRBiomeSpawnList(LOTRBiome biome) {
		this(biome.getClass().getName());
	}

	public LOTRBiomeSpawnList(String s) {
		biomeIdentifier = s;
	}

	public void clear() {
		factionContainers.clear();
		presentFactions.clear();
		conquestGainRate = 1.0f;
	}

	public boolean containsEntityClassByDefault(Class<? extends EntityLivingBase> desiredClass, World world) {
		determineFactions(world);
		for (FactionContainer facCont : factionContainers) {
			if (facCont.isEmpty() || facCont.isConquestFaction()) {
				continue;
			}
			for (SpawnListContainer listCont : facCont.spawnLists) {
				LOTRSpawnList list = listCont.spawnList;
				for (LOTRSpawnEntry e : list.getReadOnlyList()) {
					Class spawnClass = e.entityClass;
					if (!desiredClass.isAssignableFrom(spawnClass)) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	public void determineFactions(World world) {
		if (presentFactions.isEmpty() && !factionContainers.isEmpty()) {
			for (FactionContainer facContainer : factionContainers) {
				facContainer.determineFaction(world);
				LOTRFaction fac = facContainer.theFaction;
				if (presentFactions.contains(fac)) {
					continue;
				}
				presentFactions.add(fac);
			}
		}
	}

	public List<LOTRSpawnEntry> getAllSpawnEntries(World world) {
		determineFactions(world);
		List<LOTRSpawnEntry> spawns = new ArrayList<>();
		for (FactionContainer facCont : factionContainers) {
			if (facCont.isEmpty()) {
				continue;
			}
			for (SpawnListContainer listCont : facCont.spawnLists) {
				LOTRSpawnList list = listCont.spawnList;
				spawns.addAll(list.getReadOnlyList());
			}
		}
		return spawns;
	}

	public LOTRSpawnEntry.Instance getRandomSpawnEntry(Random rand, World world, int i, int j, int k) {
		determineFactions(world);
		LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(i, k);
		int totalWeight = 0;
		HashMap<FactionContainer, Integer> cachedFacWeights = new HashMap<>();
		HashMap<FactionContainer, Float> cachedConqStrengths = new HashMap<>();
		for (FactionContainer cont : factionContainers) {
			int weight;
			float conq;
			if (cont.isEmpty() || (weight = cont.getFactionWeight(conq = cont.getEffectiveConquestStrength(world, zone))) <= 0) {
				continue;
			}
			totalWeight += weight;
			cachedFacWeights.put(cont, weight);
			cachedConqStrengths.put(cont, conq);
		}
		if (totalWeight > 0) {
			FactionContainer chosenFacContainer = null;
			boolean isConquestSpawn = false;
			int w = rand.nextInt(totalWeight);
			for (FactionContainer cont : factionContainers) {
				int facWeight;
				if (cont.isEmpty() || !cachedFacWeights.containsKey(cont) || (w -= facWeight = cachedFacWeights.get(cont)) >= 0) {
					continue;
				}
				chosenFacContainer = cont;
				if (facWeight <= cont.baseWeight) {
					break;
				}
				isConquestSpawn = rand.nextFloat() < (float) (facWeight - cont.baseWeight) / facWeight;
				break;
			}
			if (chosenFacContainer != null) {
				float conq = cachedConqStrengths.get(chosenFacContainer);
				SpawnListContainer spawnList = chosenFacContainer.getRandomSpawnList(rand, conq);
				if (spawnList == null || spawnList.spawnList == null) {
					System.out.println("WARNING NPE in " + biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
					FMLLog.severe("WARNING NPE in " + biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
					LOTRLog.logger.warn("WARNING NPE in " + biomeIdentifier + ", " + chosenFacContainer.theFaction.codeName());
				}
				LOTRSpawnEntry entry = spawnList.spawnList.getRandomSpawnEntry(rand);
				int chance = spawnList.spawnChance;
				return new LOTRSpawnEntry.Instance(entry, chance, isConquestSpawn);
			}
		}
		return null;
	}

	public boolean isFactionPresent(World world, LOTRFaction fac) {
		determineFactions(world);
		return presentFactions.contains(fac);
	}

	public FactionContainer newFactionList(int w) {
		return newFactionList(w, 1.0f);
	}

	public FactionContainer newFactionList(int w, float conq) {
		FactionContainer cont = new FactionContainer(this, w);
		cont.conquestSensitivity = conq;
		factionContainers.add(cont);
		return cont;
	}

	public static SpawnListContainer entry(LOTRSpawnList list) {
		return entry(list, 1);
	}

	public static SpawnListContainer entry(LOTRSpawnList list, int weight) {
		return new SpawnListContainer(list, weight);
	}

	public static class FactionContainer {
		public LOTRBiomeSpawnList parent;
		public LOTRFaction theFaction;
		public Collection<SpawnListContainer> spawnLists = new ArrayList<>();
		public int baseWeight;
		public float conquestSensitivity = 1.0f;

		public FactionContainer(LOTRBiomeSpawnList biomeList, int w) {
			parent = biomeList;
			baseWeight = w;
		}

		public void add(SpawnListContainer... lists) {
			Collections.addAll(spawnLists, lists);
		}

		public void determineFaction(World world) {
			if (theFaction == null) {
				for (SpawnListContainer cont : spawnLists) {
					LOTRSpawnList list = cont.spawnList;
					LOTRFaction fac = list.getListCommonFaction(world);
					if (theFaction == null) {
						theFaction = fac;
						continue;
					}
					if (fac == theFaction) {
						continue;
					}
					throw new IllegalArgumentException("Faction containers must include spawn lists of only one faction! Mismatched faction " + fac.codeName() + " in biome " + parent.biomeIdentifier);
				}
			}
		}

		public float getEffectiveConquestStrength(World world, LOTRConquestZone zone) {
			if (LOTRConquestGrid.conquestEnabled(world) && !zone.isEmpty()) {
				float conqStr = zone.getConquestStrength(theFaction, world);
				for (LOTRFaction allyFac : theFaction.getConquestBoostRelations()) {
					if (parent.isFactionPresent(world, allyFac)) {
						continue;
					}
					conqStr += zone.getConquestStrength(allyFac, world) * 0.333f;
				}
				return conqStr;
			}
			return 0.0f;
		}

		public int getFactionWeight(float conq) {
			if (conq > 0.0f) {
				float conqFactor = conq * 0.2f * conquestSensitivity;
				return baseWeight + Math.round(conqFactor);
			}
			return baseWeight;
		}

		public SpawnListContainer getRandomSpawnList(Random rand, float conq) {
			int totalWeight = 0;
			for (SpawnListContainer cont : spawnLists) {
				if (!cont.canSpawnAtConquestLevel(conq)) {
					continue;
				}
				totalWeight += cont.weight;
			}
			if (totalWeight > 0) {
				SpawnListContainer chosenList = null;
				int w = rand.nextInt(totalWeight);
				for (SpawnListContainer cont : spawnLists) {
					if (!cont.canSpawnAtConquestLevel(conq) || (w -= cont.weight) >= 0) {
						continue;
					}
					chosenList = cont;
					break;
				}
				return chosenList;
			}
			return null;
		}

		public boolean isConquestFaction() {
			return baseWeight <= 0;
		}

		public boolean isEmpty() {
			return spawnLists.isEmpty();
		}
	}

	public static class SpawnListContainer {
		public LOTRSpawnList spawnList;
		public int weight;
		public int spawnChance;
		public float conquestThreshold = -1.0f;

		public SpawnListContainer(LOTRSpawnList list, int w) {
			spawnList = list;
			weight = w;
		}

		public boolean canSpawnAtConquestLevel(float conq) {
			return conq > conquestThreshold;
		}

		public boolean isConquestOnly() {
			return conquestThreshold >= 0.0f;
		}

		public SpawnListContainer setConquestOnly() {
			return setConquestThreshold(0.0f);
		}

		public SpawnListContainer setConquestThreshold(float f) {
			conquestThreshold = f;
			return this;
		}

		public SpawnListContainer setSpawnChance(int i) {
			spawnChance = i;
			return this;
		}
	}

}
