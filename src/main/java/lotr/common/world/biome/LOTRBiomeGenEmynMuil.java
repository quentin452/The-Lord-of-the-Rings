package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenGrassPatch;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenEmynMuil extends LOTRBiome {
	public WorldGenerator boulderGenSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
	public WorldGenerator boulderGenLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 5, 8).setHeightCheck(6);
	public WorldGenerator clayBoulderGenSmall = new LOTRWorldGenBoulder(Blocks.hardened_clay, 0, 1, 4);
	public WorldGenerator clayBoulderGenLarge = new LOTRWorldGenBoulder(Blocks.hardened_clay, 0, 5, 10).setHeightCheck(6);
	public WorldGenerator grassPatchGen = new LOTRWorldGenGrassPatch();

	public LOTRBiomeGenEmynMuil(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.stone;
		fillerBlock = Blocks.stone;
		spawnableCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 1);
		arrspawnListContainer2[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		decorator.flowersPerChunk = 1;
		decorator.grassPerChunk = 10;
		decorator.doubleGrassPerChunk = 2;
		registerMountainsFlowers();
		biomeColors.setGrass(9539937);
		biomeColors.setSky(10000788);
		decorator.generateOrcDungeon = true;
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int i1;
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 20; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			if (random.nextInt(5) == 0) {
				clayBoulderGenSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
				continue;
			}
			boulderGenSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
		for (l = 0; l < 20; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			if (random.nextInt(5) == 0) {
				clayBoulderGenLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
				continue;
			}
			boulderGenLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
		for (l = 0; l < 10; ++l) {
			Block block = Blocks.stone;
			if (random.nextInt(5) == 0) {
				block = Blocks.hardened_clay;
			}
			for (int l1 = 0; l1 < 10; ++l1) {
				int j1;
				int k12;
				int i12 = i + random.nextInt(16) + 8;
				if (world.getBlock(i12, (j1 = world.getHeightValue(i12, k12 = k + random.nextInt(16) + 8)) - 1, k12) != block) {
					continue;
				}
				int height = j1 + random.nextInt(4);
				for (int j2 = j1; j2 < height && !LOTRMod.isOpaque(world, i12, j2, k12); ++j2) {
					world.setBlock(i12, j2, k12, block, 0, 3);
				}
			}
		}
		for (l = 0; l < 3; ++l) {
			i1 = i + random.nextInt(16) + 8;
			k1 = k + random.nextInt(16) + 8;
			grassPatchGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterEmynMuil;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.BROWN_LANDS.getSubregion("emynMuil");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.EMYN_MUIL;
	}
}
