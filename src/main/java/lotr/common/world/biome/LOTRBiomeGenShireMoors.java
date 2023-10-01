package lotr.common.world.biome;

import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LOTRBiomeGenShireMoors extends LOTRBiomeGenShire {
	public WorldGenerator boulderSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
	public WorldGenerator boulderLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 3, 5);

	public LOTRBiomeGenShireMoors(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		variantChance = 0.2f;
		addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
		decorator.treesPerChunk = 0;
		decorator.flowersPerChunk = 16;
		decorator.doubleFlowersPerChunk = 0;
		decorator.grassPerChunk = 16;
		decorator.doubleGrassPerChunk = 1;
		decorator.addTree(LOTRTreeType.OAK_LARGE, 8000);
		decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 2000);
		addFlower(LOTRMod.shireHeather, 0, 100);
		biomeColors.resetGrass();
		decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 500);
		decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 1000);
		decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 200);
		setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int i1;
		int l;
		super.decorate(world, random, i, k);
		if (random.nextInt(8) == 0) {
			for (l = 0; l < 4; ++l) {
				i1 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				boulderSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
		if (random.nextInt(30) == 0) {
			for (l = 0; l < 4; ++l) {
				i1 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				boulderLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}

	@Override
	public LOTRMusicRegion.Sub getBiomeMusic() {
		return LOTRMusicRegion.SHIRE.getSubregion("moors");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.1f;
	}

	@Override
	public int spawnCountMultiplier() {
		return super.spawnCountMultiplier() * 2;
	}
}
