package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHobbitPicnicBench;
import lotr.common.world.structure2.*;
import net.minecraft.block.BlockFalling;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenWhiteDowns extends LOTRBiomeGenShire {
	public WorldGenerator chalkBoulder = new LOTRWorldGenBoulder(LOTRMod.rock, 5, 1, 3);

	public LOTRBiomeGenWhiteDowns(int i, boolean major) {
		super(i, major);
		fillerBlock = LOTRMod.rock;
		fillerBlockMeta = 5;
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10).setSpawnChance(100);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 1).setConquestOnly();
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
		arrspawnListContainer3[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 3).setConquestThreshold(100.0f);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 10);
		arrspawnListContainer4[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 5);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		npcSpawnList.conquestGainRate = 0.2f;
		clearBiomeVariants();
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		biomeColors.resetGrass();
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 300);
		decorator.addRandomStructure(new LOTRWorldGenHobbitBurrow(false), 150);
		decorator.addRandomStructure(new LOTRWorldGenHobbitHouse(false), 150);
		decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 300);
		decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
		decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 2000);
		decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(80) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				chalkBoulder.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterWhiteDowns;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SHIRE.getSubregion("whiteDowns");
	}

	@Override
	public int spawnCountMultiplier() {
		return 5;
	}
}
