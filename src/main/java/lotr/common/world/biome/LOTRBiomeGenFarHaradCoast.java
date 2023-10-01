package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenCorsairCamp;
import lotr.common.world.structure2.LOTRWorldGenCorsairCove;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class LOTRBiomeGenFarHaradCoast extends LOTRBiomeGenFarHaradSavannah {
	public static NoiseGeneratorPerlin noiseGrass = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
	public static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
	public static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(267215026920L), 1);

	public LOTRBiomeGenFarHaradCoast(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLion.class, 4, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLioness.class, 4, 2, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityZebra.class, 8, 4, 8));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGemsbok.class, 8, 4, 8));
		npcSpawnList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(5000);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		populatedSpawnList.clear();
		topBlock = Blocks.stone;
		topBlockMeta = 0;
		fillerBlock = topBlock;
		fillerBlockMeta = topBlockMeta;
		biomeTerrain.setXZScale(30.0);
		clearBiomeVariants();
		decorator.addTree(LOTRTreeType.PALM, 4000);
		decorator.treesPerChunk = 1;
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new LOTRWorldGenCorsairCove(false), 10);
		decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 100);
		clearTravellingTraders();
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
		setBanditEntityClass(LOTREntityBanditHarad.class);
		invasionSpawns.clearInvasions();
		invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.COMMON);
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseGrass.func_151601_a(i * 0.06, k * 0.06);
		double d2 = noiseGrass.func_151601_a(i * 0.47, k * 0.47);
		double d3 = noiseDirt.func_151601_a(i * 0.06, k * 0.06);
		double d4 = noiseDirt.func_151601_a(i * 0.47, k * 0.47);
		double d5 = noiseSand.func_151601_a(i * 0.06, k * 0.06);
		double d6 = noiseSand.func_151601_a(i * 0.47, k * 0.47);
		double d7 = noiseSandstone.func_151601_a(i * 0.06, k * 0.06);
		if (d7 + noiseSandstone.func_151601_a(i * 0.47, k * 0.47) > 0.8) {
			topBlock = Blocks.sandstone;
			topBlockMeta = 0;
			fillerBlock = Blocks.sand;
			fillerBlockMeta = 0;
		} else if (d5 + d6 > 0.6) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d3 + d4 > 0.5) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.4) {
			topBlock = Blocks.grass;
			topBlockMeta = 0;
			fillerBlock = Blocks.dirt;
			fillerBlockMeta = 0;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterCorsairCoasts;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}
}
