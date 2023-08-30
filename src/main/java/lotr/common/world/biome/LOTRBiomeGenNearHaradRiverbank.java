package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.*;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenNearHaradRiverbank extends LOTRBiomeGenNearHaradFertile {
	public LOTRBiomeGenNearHaradRiverbank(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 20, 4, 4));
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 30).setSpawnChance(1000);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 10).setSpawnChance(1000);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		variantChance = 0.3f;
		decorator.treesPerChunk = 0;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 3;
		decorator.flowersPerChunk = 1;
		decorator.doubleFlowersPerChunk = 1;
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
		decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
		decorator.clearVillages();
		decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.25f));
		clearTravellingTraders();
		registerTravellingTrader(LOTREntityNomadMerchant.class);
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterNearHarad;
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.HARAD_DESERT;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}
}
