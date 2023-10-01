package lotr.common.world.feature;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public enum LOTRTreeType {
	OAK((flag, rand) -> {
		if (rand.nextInt(4) == 0) {
			return new LOTRWorldGenGnarledOak(flag);
		}
		return new LOTRWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, Blocks.leaves, 0);
	}), OAK_TALL((flag, rand) -> {
		if (rand.nextInt(4) == 0) {
			return new LOTRWorldGenGnarledOak(flag).setMinMaxHeight(6, 10);
		}
		return new LOTRWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, Blocks.leaves, 0);
	}), OAK_TALLER((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 12, 16, Blocks.log, 0, Blocks.leaves, 0)), OAK_ITHILIEN_HIDEOUT((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 6, 6, Blocks.log, 0, Blocks.leaves, 0)), OAK_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), OAK_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(Blocks.log, 0, Blocks.leaves, 0)), OAK_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), OAK_FANGORN_DEAD((flag, rand) -> new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0).setNoLeaves()), OAK_SWAMP((flag, rand) -> new WorldGenSwamp()), OAK_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(Blocks.log, 0)), OAK_DESERT((flag, rand) -> new LOTRWorldGenDesertTrees(flag, Blocks.log, 0, Blocks.leaves, 0)), OAK_SHRUB((flag, rand) -> new LOTRWorldGenShrub(Blocks.log, 0, Blocks.leaves, 0)), BIRCH((flag, rand) -> {
		if (rand.nextInt(3) != 0) {
			return new LOTRWorldGenAspen(flag).setBlocks(Blocks.log, 2, Blocks.leaves, 2).setMinMaxHeight(8, 16);
		}
		return new LOTRWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, Blocks.leaves, 2);
	}), BIRCH_TALL((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, Blocks.leaves, 2)), BIRCH_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, Blocks.log, 2, Blocks.leaves, 2)), BIRCH_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(Blocks.log, 2, Blocks.leaves, 2)), BIRCH_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, Blocks.log, 2, Blocks.leaves, 2)), BIRCH_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(Blocks.log, 2)), SPRUCE((flag, rand) -> new WorldGenTaiga2(flag)), SPRUCE_THIN((flag, rand) -> new WorldGenTaiga1()), SPRUCE_MEGA((flag, rand) -> new WorldGenMegaPineTree(flag, true)), SPRUCE_MEGA_THIN((flag, rand) -> new WorldGenMegaPineTree(flag, false)), SPRUCE_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(Blocks.log, 1)), JUNGLE((flag, rand) -> new WorldGenTrees(flag, 7, 3, 3, true)), JUNGLE_LARGE((flag, rand) -> new WorldGenMegaJungle(flag, 10, 20, 3, 3)), JUNGLE_CLOUD((flag, rand) -> new WorldGenMegaJungle(flag, 30, 30, 3, 3)), JUNGLE_SHRUB((flag, rand) -> new LOTRWorldGenShrub(Blocks.log, 3, Blocks.leaves, 3)), JUNGLE_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, Blocks.log, 3, Blocks.leaves, 3).setHeightFactor(1.5f)), ACACIA((flag, rand) -> new WorldGenSavannaTree(flag)), ACACIA_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(Blocks.log2, 0)), DARK_OAK((flag, rand) -> new WorldGenCanopyTree(flag)), DARK_OAK_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(Blocks.log2, 1, Blocks.leaves2, 1)), SHIRE_PINE((flag, rand) -> new LOTRWorldGenShirePine(flag)), MALLORN((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 6, 9, LOTRMod.wood, 1, LOTRMod.leaves, 1)), MALLORN_BOUGHS((flag, rand) -> new LOTRWorldGenMallorn(flag)), MALLORN_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.wood, 1, LOTRMod.leaves, 1)), MALLORN_EXTREME((flag, rand) -> new LOTRWorldGenMallornExtreme(flag)), MALLORN_EXTREME_SAPLING((flag, rand) -> new LOTRWorldGenMallornExtreme(flag).setSaplingGrowth()), MIRK_OAK((flag, rand) -> new LOTRWorldGenMirkOak(flag, 4, 7, 0, true)), MIRK_OAK_LARGE((flag, rand) -> new LOTRWorldGenMirkOak(flag, 12, 16, 1, true)), MIRK_OAK_DEAD((flag, rand) -> new LOTRWorldGenMirkOak(flag, 4, 7, 0, true).setDead()), RED_OAK((flag, rand) -> new LOTRWorldGenMirkOak(flag, 6, 9, 0, false).setRedOak()), RED_OAK_LARGE((flag, rand) -> new LOTRWorldGenMirkOak(flag, 12, 17, 1, false).setRedOak()), RED_OAK_WEIRWOOD((flag, rand) -> new LOTRWorldGenMirkOak(flag, 12, 20, 1, false).setBlocks(LOTRMod.wood9, 0, LOTRMod.leaves, 3)), CHARRED((flag, rand) -> new LOTRWorldGenCharredTrees()), CHARRED_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, LOTRMod.wood, 3, Blocks.air, 0).setNoLeaves()), APPLE((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 0, LOTRMod.fruitLeaves, 0)), PEAR((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 4, 5, LOTRMod.fruitWood, 1, LOTRMod.fruitLeaves, 1)), CHERRY((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2)), CHERRY_MORDOR((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2).disableRestrictions()), MANGO((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
		}
		return new LOTRWorldGenDesertTrees(flag, LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
	}), LEBETHRON((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 0, LOTRMod.leaves2, 0)), LEBETHRON_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 0, LOTRMod.leaves2, 0)), LEBETHRON_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.wood2, 0, LOTRMod.leaves2, 0)), LEBETHRON_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(LOTRMod.wood2, 0)), BEECH((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 1, LOTRMod.leaves2, 1)), BEECH_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1)), BEECH_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.wood2, 1, LOTRMod.leaves2, 1)), BEECH_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1)), BEECH_FANGORN_DEAD((flag, rand) -> new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1).setNoLeaves()), BEECH_DEAD((flag, rand) -> new LOTRWorldGenDeadTrees(LOTRMod.wood2, 1)), HOLLY((flag, rand) -> new LOTRWorldGenHolly(flag)), HOLLY_LARGE((flag, rand) -> new LOTRWorldGenHolly(flag).setLarge()), BANANA((flag, rand) -> new LOTRWorldGenBanana(flag)), MAPLE((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.wood3, 0, LOTRMod.leaves3, 0)), MAPLE_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, LOTRMod.wood3, 0, LOTRMod.leaves3, 0)), MAPLE_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.wood3, 0, LOTRMod.leaves3, 0)), LARCH((flag, rand) -> new LOTRWorldGenLarch(flag)), DATE_PALM((flag, rand) -> new LOTRWorldGenPalm(flag, LOTRMod.wood3, 2, LOTRMod.leaves3, 2).setMinMaxHeight(5, 8).setDates()), MANGROVE((flag, rand) -> new LOTRWorldGenMangrove(flag)), CHESTNUT((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 5, 7, LOTRMod.wood4, 0, LOTRMod.leaves4, 0)), CHESTNUT_LARGE((flag, rand) -> new LOTRWorldGenBigTrees(flag, LOTRMod.wood4, 0, LOTRMod.leaves4, 0)), CHESTNUT_PARTY((flag, rand) -> new LOTRWorldGenPartyTrees(LOTRMod.wood4, 0, LOTRMod.leaves4, 0)), BAOBAB((flag, rand) -> new LOTRWorldGenBaobab(flag)), CEDAR((flag, rand) -> new LOTRWorldGenCedar(flag)), CEDAR_LARGE((flag, rand) -> new LOTRWorldGenCedar(flag).setMinMaxHeight(15, 30)), FIR((flag, rand) -> new LOTRWorldGenFir(flag)), PINE((flag, rand) -> new LOTRWorldGenPine(flag)), PINE_SHRUB((flag, rand) -> new LOTRWorldGenShrub(LOTRMod.wood5, 0, LOTRMod.leaves5, 0)), LEMON((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
		}
		return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
	}), ORANGE((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
		}
		return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
	}), LIME((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
		}
		return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
	}), MAHOGANY((flag, rand) -> new LOTRWorldGenCedar(flag).setBlocks(LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHangingLeaves()), MAHOGANY_FANGORN((flag, rand) -> new LOTRWorldGenFangornTrees(flag, LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHeightFactor(1.5f)), WILLOW((flag, rand) -> new LOTRWorldGenWillow(flag)), WILLOW_WATER((flag, rand) -> new LOTRWorldGenWillow(flag).setNeedsWater()), CYPRESS((flag, rand) -> new LOTRWorldGenCypress(flag)), CYPRESS_LARGE((flag, rand) -> new LOTRWorldGenCypress(flag).setLarge()), OLIVE((flag, rand) -> new LOTRWorldGenOlive(flag)), OLIVE_LARGE((flag, rand) -> new LOTRWorldGenOlive(flag).setMinMaxHeight(5, 8).setExtraTrunkWidth(1)), ASPEN((flag, rand) -> new LOTRWorldGenAspen(flag)), ASPEN_LARGE((flag, rand) -> new LOTRWorldGenAspen(flag).setExtraTrunkWidth(1).setMinMaxHeight(14, 25)), GREEN_OAK((flag, rand) -> new LOTRWorldGenMirkOak(flag, 4, 7, 0, false).setGreenOak()), GREEN_OAK_LARGE((flag, rand) -> new LOTRWorldGenMirkOak(flag, 12, 16, 1, false).setGreenOak()), GREEN_OAK_EXTREME((flag, rand) -> new LOTRWorldGenMirkOak(flag, 25, 45, 2, false).setGreenOak()), LAIRELOSSE((flag, rand) -> new LOTRWorldGenLairelosse(flag)), LAIRELOSSE_LARGE((flag, rand) -> new LOTRWorldGenLairelosse(flag).setExtraTrunkWidth(1).setMinMaxHeight(8, 12)), ALMOND((flag, rand) -> new LOTRWorldGenAlmond(flag)), PLUM((flag, rand) -> new LOTRWorldGenSimpleTrees(flag, 4, 6, LOTRMod.wood8, 0, LOTRMod.leaves8, 0)), REDWOOD((flag, rand) -> new LOTRWorldGenRedwood(flag)), REDWOOD_2((flag, rand) -> new LOTRWorldGenRedwood(flag).setExtraTrunkWidth(1)), REDWOOD_3((flag, rand) -> new LOTRWorldGenRedwood(flag).setTrunkWidth(1)), REDWOOD_4((flag, rand) -> new LOTRWorldGenRedwood(flag).setTrunkWidth(1).setExtraTrunkWidth(1)), REDWOOD_5((flag, rand) -> new LOTRWorldGenRedwood(flag).setTrunkWidth(2)), POMEGRANATE((flag, rand) -> {
		if (rand.nextInt(3) == 0) {
			return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
		}
		return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
	}), PALM((flag, rand) -> new LOTRWorldGenPalm(flag, LOTRMod.wood8, 3, LOTRMod.leaves8, 3).setMinMaxHeight(6, 11)), DRAGONBLOOD((flag, rand) -> new LOTRWorldGenDragonblood(flag, 3, 7, 0)), DRAGONBLOOD_LARGE((flag, rand) -> new LOTRWorldGenDragonblood(flag, 6, 10, 1)), DRAGONBLOOD_HUGE((flag, rand) -> new LOTRWorldGenDragonblood(flag, 8, 16, 2)), KANUKA((flag, rand) -> new LOTRWorldGenKanuka(flag)), NULL(null);

	public ITreeFactory treeFactory;

	LOTRTreeType(ITreeFactory factory) {
		treeFactory = factory;
	}

	public WorldGenAbstractTree create(boolean flag, Random rand) {
		return treeFactory.createTree(flag, rand);
	}

	public interface ITreeFactory {
		WorldGenAbstractTree createTree(boolean var1, Random var2);
	}

	public static class WeightedTreeType extends WeightedRandom.Item {
		public LOTRTreeType treeType;

		public WeightedTreeType(LOTRTreeType tree, int i) {
			super(i);
			treeType = tree;
		}
	}

}
