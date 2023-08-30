package lotr.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.world.biome.LOTRBiome;

public class LOTRBiomeInvasionSpawns {
	public LOTRBiome theBiome;
	public Map<LOTREventSpawner.EventChance, List<LOTRInvasions>> invasionsByChance = new EnumMap<>(LOTREventSpawner.EventChance.class);
	public Collection<LOTRInvasions> registeredInvasions = new ArrayList<>();

	public LOTRBiomeInvasionSpawns(LOTRBiome biome) {
		theBiome = biome;
	}

	public void addInvasion(LOTRInvasions invasion, LOTREventSpawner.EventChance chance) {
		List<LOTRInvasions> chanceList = getInvasionsForChance(chance);
		if (chanceList.contains(invasion) || registeredInvasions.contains(invasion)) {
			FMLLog.warning("LOTR biome %s already has invasion %s registered", theBiome.biomeName, invasion.codeName());
		} else {
			chanceList.add(invasion);
			registeredInvasions.add(invasion);
		}
	}

	public void clearInvasions() {
		invasionsByChance.clear();
		registeredInvasions.clear();
	}

	public List<LOTRInvasions> getInvasionsForChance(LOTREventSpawner.EventChance chance) {
		List<LOTRInvasions> chanceList = invasionsByChance.get(chance);
		if (chanceList == null) {
			chanceList = new ArrayList<>();
		}
		invasionsByChance.put(chance, chanceList);
		return chanceList;
	}
}
