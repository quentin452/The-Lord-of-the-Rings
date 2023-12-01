package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenMordorSpiderPit;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBiomeGenNanUngol extends LOTRBiomeGenMordor {
	public LOTRBiomeGenNanUngol(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[3];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 2);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 100);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		npcSpawnList.conquestGainRate = 0.5f;
		clearBiomeVariants();
		decorator.generateCobwebs = false;
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenMordorSpiderPit(false), 40);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		for (int l = 0; l < 4; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			new LOTRWorldGenWebOfUngoliant(false, 64).generate(world, random, i1, j1, k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterNanUngol;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.MORDOR.getSubregion("nanUngol");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.NAN_UNGOL;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.75f;
	}
}
