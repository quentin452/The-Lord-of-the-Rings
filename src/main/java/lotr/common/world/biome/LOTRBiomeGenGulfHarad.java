package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.structure2.LOTRWorldGenMoredainMercCamp;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenGulfHarad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenGulfHarad extends LOTRBiome {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	public static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	public static NoiseGeneratorPerlin noiseRedSand = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);

	public LOTRBiomeGenGulfHarad(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityWildBoar.class, 10, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 8, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityAurochs.class, 6, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityWhiteOryx.class, 12, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityCamel.class, 2, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityHorse.class, 10, 4, 4));
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[2];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_HARADRIM, 20).setSpawnChance(100);
		arrspawnListContainer[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 5).setSpawnChance(100);
		npcSpawnList.newFactionList(100, 0.0f).add(arrspawnListContainer);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer2 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer2[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer2);
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer3 = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer3[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
		npcSpawnList.newFactionList(0).add(arrspawnListContainer3);
		addBiomeVariant(LOTRBiomeVariant.FLOWERS);
		addBiomeVariant(LOTRBiomeVariant.FOREST);
		addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(LOTRBiomeVariant.STEPPE);
		addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
		addBiomeVariant(LOTRBiomeVariant.HILLS);
		addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
		addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
		addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND_SAND);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.flowersPerChunk = 1;
		decorator.doubleFlowersPerChunk = 1;
		decorator.deadBushPerChunk = 1;
		decorator.cactiPerChunk = 1;
		decorator.addTree(LOTRTreeType.PALM, 500);
		decorator.addTree(LOTRTreeType.ACACIA, 300);
		decorator.addTree(LOTRTreeType.OAK_DESERT, 400);
		decorator.addTree(LOTRTreeType.DRAGONBLOOD, 200);
		decorator.addTree(LOTRTreeType.DRAGONBLOOD_LARGE, 10);
		decorator.addTree(LOTRTreeType.DATE_PALM, 50);
		decorator.addTree(LOTRTreeType.LEMON, 5);
		decorator.addTree(LOTRTreeType.ORANGE, 5);
		decorator.addTree(LOTRTreeType.LIME, 5);
		decorator.addTree(LOTRTreeType.OLIVE, 5);
		decorator.addTree(LOTRTreeType.OLIVE_LARGE, 10);
		decorator.addTree(LOTRTreeType.ALMOND, 5);
		decorator.addTree(LOTRTreeType.PLUM, 5);
		registerHaradFlowers();
		decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 3), 500);
		decorator.addRandomStructure(new LOTRWorldGenMoredainMercCamp(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 3000);
		decorator.addVillage(new LOTRVillageGenGulfHarad(this, 0.75f));
		registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
		registerTravellingTrader(LOTREntityNomadMerchant.class);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
		setBanditEntityClass(LOTREntityBanditHarad.class);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		super.decorate(world, random, i, k);
		if (random.nextInt(20) == 0) {
			int boulders = 1 + random.nextInt(3);
			for (int l = 0; l < boulders; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				int j1 = world.getHeightValue(i1, k1);
				boulderGen.generate(world, random, i1, j1, k1);
			}
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		if (hasMixedHaradSoils()) {
			double d1 = noiseDirt.func_151601_a(i * 0.002, k * 0.002);
			double d2 = noiseDirt.func_151601_a(i * 0.07, k * 0.07);
			double d3 = noiseDirt.func_151601_a(i * 0.25, k * 0.25);
			double d4 = noiseSand.func_151601_a(i * 0.002, k * 0.002);
			double d5 = noiseSand.func_151601_a(i * 0.07, k * 0.07);
			double d6 = noiseSand.func_151601_a(i * 0.25, k * 0.25);
			double d7 = noiseRedSand.func_151601_a(i * 0.002, k * 0.002);
			if (d7 + noiseRedSand.func_151601_a(i * 0.07, k * 0.07) + noiseRedSand.func_151601_a(i * 0.25, k * 0.25) > 0.9) {
				topBlock = Blocks.sand;
				topBlockMeta = 1;
				fillerBlock = topBlock;
				fillerBlockMeta = topBlockMeta;
			} else if (d4 + d5 + d6 > 1.2) {
				topBlock = Blocks.sand;
				topBlockMeta = 0;
				fillerBlock = topBlock;
				fillerBlockMeta = topBlockMeta;
			} else if (d1 + d2 + d3 > 0.4) {
				topBlock = Blocks.dirt;
				topBlockMeta = 1;
			}
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterGulfHarad;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.NEAR_HARAD.getSubregion("gulf");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.GULF_HARAD;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
		if (random.nextInt(3) == 0) {
			doubleFlowerGen.setFlowerType(2);
		} else {
			doubleFlowerGen.setFlowerType(3);
		}
		return doubleFlowerGen;
	}

	@Override
	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.GULF_HARAD;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.2f;
	}

	public boolean hasMixedHaradSoils() {
		return true;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}
