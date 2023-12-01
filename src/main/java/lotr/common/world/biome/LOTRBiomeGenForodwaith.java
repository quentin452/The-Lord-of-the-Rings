package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.map.LOTRWorldGenUtumnoEntrance;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenRuinedHouse;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenForodwaith extends LOTRBiome {
	public WorldGenerator boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
	public LOTRWorldGenStalactites stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);

	public LOTRBiomeGenForodwaith(int i, boolean major) {
		super(i, major);
		setEnableSnow();
		topBlock = Blocks.snow;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableLOTRAmbientList.clear();
		LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
		arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 10).setSpawnChance(100000);
		npcSpawnList.newFactionList(100).add(arrspawnListContainer);
		decorator.addSoil(new WorldGenMinable(Blocks.packed_ice, 16), 40.0f, 32, 256);
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 0;
		decorator.generateWater = false;
		biomeColors.setSky(10069160);
		decorator.addRandomStructure(new LOTRWorldGenRuinedHouse(false), 4000);
		decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 5), 4000);
		setBanditChance(LOTREventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
        BlockFalling.fallInstantly = true;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		if (LOTRFixedStructures.UTUMNO_ENTRANCE.isAt(world, i, k)) {
			new LOTRWorldGenUtumnoEntrance().generate(world, random, i, world.getHeightValue(i, k), k);
		}
		if (random.nextInt(32) == 0) {
			int boulders = 1 + random.nextInt(5);
			for (int l = 0; l < boulders; ++l) {
				int i12 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				boulderGen.generate(world, random, i12, world.getHeightValue(i12, k1), k1);
			}
		}
		for (int l = 0; l < 2; ++l) {
			i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(60);
			k1 = k + random.nextInt(16) + 8;
			stalactiteIceGen.generate(world, random, i1, j1, k1);
		}
		if (random.nextInt(20000) == 0) {
			LOTRWorldGenMirkOak tree = ((LOTRWorldGenMirkOak) LOTRTreeType.RED_OAK_WEIRWOOD.create(false, random)).disableRestrictions();
			i1 = i + random.nextInt(16) + 8;
			int k12 = k + random.nextInt(16) + 8;
			int j1 = world.getHeightValue(i1, k12);
			tree.generate(world, random, i1, j1, k12);
		}
        BlockFalling.fallInstantly = false;
	}

	@Override
	public LOTRAchievement getBiomeAchievement() {
		return LOTRAchievement.enterForodwaith;
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.FORODWAITH.getSubregion("forodwaith");
	}

	@Override
	public LOTRWaypoint.Region getBiomeWaypoints() {
		return LOTRWaypoint.Region.FORODWAITH;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.0f;
	}
}
