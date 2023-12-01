package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBlastedLand;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenAngmarShrine;
import lotr.common.world.structure.LOTRWorldGenAngmarTower;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenAngmar extends LOTRBiome {
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);

	public LOTRBiomeGenAngmar(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 10, 4, 8));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 2, 4, 8));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
		spawnableWaterCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[7];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_BOMBARDIERS, 5);
		arrspawnListContainer[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 30);
		arrspawnListContainer[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 30);
		arrspawnListContainer[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 20);
		arrspawnListContainer[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 5);
		arrspawnListContainer[6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 20);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
		npcSpawnList.newFactionList(2, 0.0f).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer4 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer4[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer4);
		npcSpawnList.conquestGainRate = 0.5f;
		addBiomeVariant(LOTRBiomeVariant.FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.STEPPE);
		addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 1.0f);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0f, 0, 64);
		decorator.addOre(new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0f, 0, 32);
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 1;
		decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
		decorator.addTree(LOTRTreeType.SPRUCE, 200);
		decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 150);
		decorator.addTree(LOTRTreeType.CHARRED, 150);
		decorator.addTree(LOTRTreeType.FIR, 100);
		decorator.addTree(LOTRTreeType.PINE, 200);
		biomeColors.setGrass(7896151);
		biomeColors.setSky(5324595);
		biomeColors.setClouds(1644825);
		biomeColors.setFog(1644825);
		decorator.generateOrcDungeon = true;
		decorator.generateTrollHoard = true;
		decorator.addRandomStructure(new LOTRWorldGenAngmarTower(false), 300);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 40);
		decorator.addRandomStructure(new LOTRWorldGenBlastedLand(false), 40);
		decorator.addRandomStructure(new LOTRWorldGenAngmarShrine(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenAngmarWargPit(false), 200);
		decorator.addRandomStructure(new LOTRWorldGenAngmarCamp(false), 50);
		decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 400);
		decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
		invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_LINDON, LOTREventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.RARE);
	}

	@Override
	public boolean canSpawnHostilesInDay() {
		return true;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		for (int l = 0; l < 4; ++l) {
			int k1;
			int i1 = i + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8);
			if (j1 <= 80) {
				continue;
			}
			decorator.genTree(world, random, i1, j1, k1);
		}
		if (random.nextInt(6) == 0) {
			int i1 = i + random.nextInt(16) + 8;
			int k1 = k + random.nextInt(16) + 8;
			boulderGen.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
		if (d1 + biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4) > 0.5) {
			topBlock = Blocks.stone;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterAngmar;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.ANGMAR.getSubregion("angmar");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.ANGMAR;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.25f;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
