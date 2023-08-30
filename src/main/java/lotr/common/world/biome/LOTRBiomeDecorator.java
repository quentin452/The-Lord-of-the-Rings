package lotr.common.world.biome;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.world.*;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.*;
import lotr.common.world.village.LOTRVillageGen;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class LOTRBiomeDecorator {
	public World worldObj;
	public Random rand;
	public int chunkX;
	public int chunkZ;
	public LOTRBiome biome;
	public Collection<OreGenerant> biomeSoils = new ArrayList<>();
	public Collection<OreGenerant> biomeOres = new ArrayList<>();
	public Collection<OreGenerant> biomeGems = new ArrayList<>();
	public float biomeOreFactor = 1.0f;
	public float biomeGemFactor = 0.5f;
	public WorldGenerator clayGen = new LOTRWorldGenSand(Blocks.clay, 5, 1);
	public WorldGenerator sandGen = new LOTRWorldGenSand(Blocks.sand, 7, 2);
	public WorldGenerator whiteSandGen = new LOTRWorldGenSand(LOTRMod.whiteSand, 7, 2);
	public WorldGenerator quagmireGen = new LOTRWorldGenSand(LOTRMod.quagmire, 7, 2);
	public WorldGenerator surfaceGravelGen = new LOTRWorldGenSurfaceGravel();
	public WorldGenerator flowerGen = new LOTRWorldGenBiomeFlowers();
	public WorldGenerator logGen = new LOTRWorldGenLogs();
	public WorldGenerator mushroomBrownGen = new WorldGenFlowers(Blocks.brown_mushroom);
	public WorldGenerator mushroomRedGen = new WorldGenFlowers(Blocks.red_mushroom);
	public WorldGenerator caneGen = new WorldGenReed();
	public WorldGenerator reedGen = new LOTRWorldGenReeds(LOTRMod.reeds);
	public WorldGenerator dryReedGen = new LOTRWorldGenReeds(LOTRMod.driedReeds);
	public WorldGenerator cornGen = new LOTRWorldGenCorn();
	public WorldGenerator pumpkinGen = new WorldGenPumpkin();
	public WorldGenerator waterlilyGen = new WorldGenWaterlily();
	public WorldGenerator cobwebGen = new LOTRWorldGenCaveCobwebs();
	public WorldGenerator stalactiteGen = new LOTRWorldGenStalactites();
	public WorldGenerator vinesGen = new WorldGenVines();
	public WorldGenerator cactusGen = new WorldGenCactus();
	public WorldGenerator melonGen = new WorldGenMelon();
	public int sandPerChunk = 4;
	public int clayPerChunk = 3;
	public int quagmirePerChunk;
	public int treesPerChunk;
	public int willowPerChunk;
	public int logsPerChunk;
	public int vinesPerChunk;
	public int flowersPerChunk = 2;
	public int doubleFlowersPerChunk;
	public int grassPerChunk = 1;
	public int doubleGrassPerChunk;
	public boolean enableFern;
	public boolean enableSpecialGrasses = true;
	public int deadBushPerChunk;
	public int waterlilyPerChunk;
	public int mushroomsPerChunk;
	public boolean enableRandomMushroom = true;
	public int canePerChunk;
	public int reedPerChunk = 1;
	public float dryReedChance = 0.1f;
	public int cornPerChunk;
	public int cactiPerChunk;
	public float melonPerChunk;
	public boolean generateWater = true;
	public boolean generateLava = true;
	public boolean generateCobwebs = true;
	public boolean generateAthelas;
	public boolean whiteSand;
	public int treeClusterSize;
	public int treeClusterChance = -1;
	public WorldGenerator orcDungeonGen = new LOTRWorldGenOrcDungeon(false);
	public WorldGenerator trollHoardGen = new LOTRWorldGenTrollHoard();
	public boolean generateOrcDungeon;
	public boolean generateTrollHoard;
	public Collection<LOTRTreeType.WeightedTreeType> treeTypes = new ArrayList<>();
	public Random structureRand = new Random();
	public Collection<RandomStructure> randomStructures = new ArrayList<>();
	public Collection<LOTRVillageGen> villages = new ArrayList<>();

	public LOTRBiomeDecorator(LOTRBiome lotrbiome) {
		biome = lotrbiome;
		addDefaultOres();
	}

	public void addDefaultOres() {
		addSoil(new WorldGenMinable(Blocks.dirt, 32), 40.0f, 0, 256);
		addSoil(new WorldGenMinable(Blocks.gravel, 32), 20.0f, 0, 256);
		addOre(new WorldGenMinable(Blocks.coal_ore, 16), 40.0f, 0, 128);
		addOre(new WorldGenMinable(LOTRMod.oreCopper, 8), 16.0f, 0, 128);
		addOre(new WorldGenMinable(LOTRMod.oreTin, 8), 16.0f, 0, 128);
		addOre(new WorldGenMinable(Blocks.iron_ore, 8), 20.0f, 0, 64);
		addOre(new WorldGenMinable(LOTRMod.oreSulfur, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(LOTRMod.oreSaltpeter, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(LOTRMod.oreSalt, 12), 2.0f, 0, 64);
		addOre(new WorldGenMinable(Blocks.gold_ore, 8), 2.0f, 0, 32);
		addOre(new WorldGenMinable(LOTRMod.oreSilver, 8), 3.0f, 0, 32);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 1, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 0, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 4, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 6, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 2, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 3, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 7, 4, Blocks.stone), 0.75f, 0, 24);
		addGem(new WorldGenMinable(LOTRMod.oreGem, 5, 4, Blocks.stone), 0.5f, 0, 16);
	}

	public void addGem(WorldGenerator gen, float f, int min, int max) {
		biomeGems.add(new OreGenerant(gen, f, min, max));
	}

	public void addOre(WorldGenerator gen, float f, int min, int max) {
		biomeOres.add(new OreGenerant(gen, f, min, max));
	}

	public void addRandomStructure(WorldGenerator structure, int chunkChance) {
		randomStructures.add(new RandomStructure(structure, chunkChance));
	}

	public void addSoil(WorldGenerator gen, float f, int min, int max) {
		biomeSoils.add(new OreGenerant(gen, f, min, max));
	}

	public void addTree(LOTRTreeType type, int weight) {
		treeTypes.add(new LOTRTreeType.WeightedTreeType(type, weight));
	}

	public void addVillage(LOTRVillageGen village) {
		villages.add(village);
	}

	public boolean anyFixedVillagesAt(World world, int i, int k) {
		for (LOTRVillageGen village : villages) {
			if (!village.anyFixedVillagesAt(world, i, k)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public void checkForVillages(World world, int i, int k, LOTRChunkProvider.ChunkFlags chunkFlags) {
		chunkFlags.isVillage = false;
		chunkFlags.isFlatVillage = false;
		for (LOTRVillageGen village : villages) {
			List<LOTRVillageGen.AbstractInstance<?>> instances = village.getNearbyVillagesAtPosition(world, i, k);
			if (instances.isEmpty()) {
				continue;
			}
			chunkFlags.isVillage = true;
			for (LOTRVillageGen.AbstractInstance<?> inst : instances) {
				if (!inst.isFlat()) {
					continue;
				}
				chunkFlags.isFlatVillage = true;
			}
		}
	}

	public void clearOres() {
		biomeSoils.clear();
		biomeOres.clear();
		biomeGems.clear();
	}

	public void clearRandomStructures() {
		randomStructures.clear();
	}

	public void clearTrees() {
		treeTypes.clear();
	}

	public void clearVillages() {
		villages.clear();
	}

	public void decorate() {
		int l;
		int j;
		int l2;
		int i;
		int l3;
		int i2;
		int j2;
		int k;
		int l4;
		int j3;
		int l5;
		WorldGenerator house;
		int k2;
		int k3;
		int j4;
		int k4;
		int k5;
		int cluster;
		int k6;
		int i3;
		int l6;
		int i4;
		int l7;
		int j5;
		int i5;
		int k7;
		LOTRBiomeVariant biomeVariant = ((LOTRWorldChunkManager) worldObj.getWorldChunkManager()).getBiomeVariantAt(chunkX + 8, chunkZ + 8);
		generateOres();
		biomeVariant.decorateVariant(worldObj, rand, chunkX, chunkZ, biome);
		if (rand.nextBoolean() && generateCobwebs) {
			i3 = chunkX + rand.nextInt(16) + 8;
			int j6 = rand.nextInt(60);
			k3 = chunkZ + rand.nextInt(16) + 8;
			cobwebGen.generate(worldObj, rand, i3, j6, k3);
		}
		for (l5 = 0; l5 < 3; ++l5) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j5 = rand.nextInt(60);
			int k8 = chunkZ + rand.nextInt(16) + 8;
			stalactiteGen.generate(worldObj, rand, i5, j5, k8);
		}
		for (l5 = 0; l5 < quagmirePerChunk; ++l5) {
			i5 = chunkX + rand.nextInt(16) + 8;
			k3 = chunkZ + rand.nextInt(16) + 8;
			quagmireGen.generate(worldObj, rand, i5, worldObj.getTopSolidOrLiquidBlock(i5, k3), k3);
		}
		for (l5 = 0; l5 < sandPerChunk; ++l5) {
			i5 = chunkX + rand.nextInt(16) + 8;
			k3 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator biomeSandGenerator = sandGen;
			if (whiteSand) {
				biomeSandGenerator = whiteSandGen;
			}
			biomeSandGenerator.generate(worldObj, rand, i5, worldObj.getTopSolidOrLiquidBlock(i5, k3), k3);
		}
		for (l5 = 0; l5 < clayPerChunk; ++l5) {
			i5 = chunkX + rand.nextInt(16) + 8;
			k3 = chunkZ + rand.nextInt(16) + 8;
			clayGen.generate(worldObj, rand, i5, worldObj.getTopSolidOrLiquidBlock(i5, k3), k3);
		}
		if (rand.nextInt(60) == 0) {
			i3 = chunkX + rand.nextInt(16) + 8;
			k6 = chunkZ + rand.nextInt(16) + 8;
			surfaceGravelGen.generate(worldObj, rand, i3, 0, k6);
		}
		if (!biomeVariant.disableStructures && Math.abs(chunkX) > 32 && Math.abs(chunkZ) > 32) {
			boolean roadNear;
			long seed = chunkX * 1879267L ^ chunkZ * 67209689L;
			seed = seed * seed * 5829687L + seed * 2876L;
			structureRand.setSeed(seed);
			roadNear = LOTRRoads.isRoadNear(chunkX + 8, chunkZ + 8, 16) >= 0.0f;
			if (!roadNear) {
				for (RandomStructure randomstructure : randomStructures) {
					if (structureRand.nextInt(randomstructure.chunkChance) != 0) {
						continue;
					}
					int i6 = chunkX + rand.nextInt(16) + 8;
					k = chunkZ + rand.nextInt(16) + 8;
					j4 = worldObj.getTopSolidOrLiquidBlock(i6, k);
					randomstructure.structureGen.generate(worldObj, rand, i6, j4, k);
				}
			}
			for (LOTRVillageGen village : villages) {
				village.generateInChunk(worldObj, chunkX, chunkZ);
			}
		}
		if (LOTRWorldGenMarshHut.generatesAt(worldObj, chunkX, chunkZ)) {
			i2 = chunkX + 8;
			k = chunkZ + 8;
			j4 = worldObj.getTopSolidOrLiquidBlock(i2, k);
			house = new LOTRWorldGenMarshHut();
			((LOTRWorldGenStructureBase) house).restrictions = false;
			house.generate(worldObj, rand, i2, j4, k);
		}
		if (LOTRWorldGenGrukHouse.generatesAt(worldObj, chunkX, chunkZ)) {
			i2 = chunkX + 8;
			k = chunkZ + 8;
			j4 = worldObj.getTopSolidOrLiquidBlock(i2, k);
			house = new LOTRWorldGenGrukHouse(false);
			((LOTRWorldGenStructureBase2) house).restrictions = false;
			((LOTRWorldGenStructureBase2) house).generateWithSetRotation(worldObj, rand, i2, j4, k, 2);
		}
		if (LOTRWorldGenTicketBooth.generatesAt(worldObj, chunkX, chunkZ)) {
			i2 = chunkX + 8;
			k = chunkZ + 8;
			j4 = worldObj.getTopSolidOrLiquidBlock(i2, k);
			LOTRWorldGenTicketBooth booth = new LOTRWorldGenTicketBooth(false);
			booth.restrictions = false;
			((LOTRWorldGenStructureBase2) booth).generateWithSetRotation(worldObj, rand, i2, j4, k, 3);
		}
		int trees = getVariantTreesPerChunk(biomeVariant);
		if (rand.nextFloat() < biome.getTreeIncreaseChance() * biomeVariant.treeFactor) {
			++trees;
		}
		cluster = Math.round(treeClusterChance * (1.0f / Math.max(biomeVariant.treeFactor, 0.001f)));
		if (cluster > 0) {
			Random chunkRand = new Random();
			long seed = chunkX / treeClusterSize * 3129871L ^ chunkZ / treeClusterSize * 116129781L;
			seed = seed * seed * 42317861L + seed * 11L;
			chunkRand.setSeed(seed);
			if (chunkRand.nextInt(cluster) == 0) {
				trees += 6 + rand.nextInt(5);
			}
		}
		for (l6 = 0; l6 < trees; ++l6) {
			int i7 = chunkX + rand.nextInt(16) + 8;
			k7 = chunkZ + rand.nextInt(16) + 8;
			WorldGenAbstractTree treeGen = getRandomTreeForVariant(rand, biomeVariant).create(false, rand);
			treeGen.generate(worldObj, rand, i7, worldObj.getHeightValue(i7, k7), k7);
		}
		for (l6 = 0; l6 < willowPerChunk; ++l6) {
			int i8 = chunkX + rand.nextInt(16) + 8;
			k7 = chunkZ + rand.nextInt(16) + 8;
			WorldGenAbstractTree treeGen = LOTRTreeType.WILLOW_WATER.create(false, rand);
			treeGen.generate(worldObj, rand, i8, worldObj.getHeightValue(i8, k7), k7);
		}
		if (trees > 0) {
			float fallenLeaves = trees / 2.0f;
			int fallenLeavesI = (int) fallenLeaves;
			float fallenLeavesR = fallenLeaves - fallenLeavesI;
			if (rand.nextFloat() < fallenLeavesR) {
				++fallenLeavesI;
			}
			l4 = 0;
			while (l4 < fallenLeaves) {
				i4 = chunkX + rand.nextInt(16) + 8;
				k5 = chunkZ + rand.nextInt(16) + 8;
				new LOTRWorldGenFallenLeaves().generate(worldObj, rand, i4, worldObj.getTopSolidOrLiquidBlock(i4, k5), k5);
				++l4;
			}
		}
		if (trees > 0) {
			float bushes = trees / 3.0f;
			int bushesI = (int) bushes;
			float bushesR = bushes - bushesI;
			if (rand.nextFloat() < bushesR) {
				++bushesI;
			}
			l4 = 0;
			while (l4 < bushes) {
				i4 = chunkX + rand.nextInt(16) + 8;
				k5 = chunkZ + rand.nextInt(16) + 8;
				new LOTRWorldGenBushes().generate(worldObj, rand, i4, worldObj.getTopSolidOrLiquidBlock(i4, k5), k5);
				++l4;
			}
		}
		for (l = 0; l < logsPerChunk; ++l) {
			int i9 = chunkX + rand.nextInt(16) + 8;
			int k9 = chunkZ + rand.nextInt(16) + 8;
			logGen.generate(worldObj, rand, i9, worldObj.getHeightValue(i9, k9), k9);
		}
		for (l = 0; l < vinesPerChunk; ++l) {
			int i10 = chunkX + rand.nextInt(16) + 8;
			int j7 = 64;
			k = chunkZ + rand.nextInt(16) + 8;
			vinesGen.generate(worldObj, rand, i10, j7, k);
		}
		int flowers = flowersPerChunk;
		flowers = Math.round(flowers * biomeVariant.flowerFactor);
		for (int l8 = 0; l8 < flowers; ++l8) {
			int i11 = chunkX + rand.nextInt(16) + 8;
			int j8 = rand.nextInt(128);
			int k10 = chunkZ + rand.nextInt(16) + 8;
			flowerGen.generate(worldObj, rand, i11, j8, k10);
		}
		int doubleFlowers = doubleFlowersPerChunk;
		doubleFlowers = Math.round(doubleFlowers * biomeVariant.flowerFactor);
		for (int l9 = 0; l9 < doubleFlowers; ++l9) {
			int i12 = chunkX + rand.nextInt(16) + 8;
			j4 = rand.nextInt(128);
			k5 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator doubleFlowerGen = biome.getRandomWorldGenForDoubleFlower(rand);
			doubleFlowerGen.generate(worldObj, rand, i12, j4, k5);
		}
		int grasses = grassPerChunk;
		grasses = Math.round(grasses * biomeVariant.grassFactor);
		for (l4 = 0; l4 < grasses; ++l4) {
			i4 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k11 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForGrass(rand);
			grassGen.generate(worldObj, rand, i4, j2, k11);
		}
		int doubleGrasses = doubleGrassPerChunk;
		doubleGrasses = Math.round(doubleGrasses * biomeVariant.grassFactor);
		for (l3 = 0; l3 < doubleGrasses; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			int j9 = rand.nextInt(128);
			int k12 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForDoubleGrass(rand);
			grassGen.generate(worldObj, rand, i2, j9, k12);
		}
		for (l3 = 0; l3 < deadBushPerChunk; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			int j10 = rand.nextInt(128);
			int k13 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenDeadBush(Blocks.deadbush).generate(worldObj, rand, i2, j10, k13);
		}
		for (l3 = 0; l3 < waterlilyPerChunk; ++l3) {
			int j11;
			i2 = chunkX + rand.nextInt(16) + 8;
			int k14 = chunkZ + rand.nextInt(16) + 8;
			//noinspection StatementWithEmptyBody
			for (j11 = rand.nextInt(128); j11 > 0 && worldObj.getBlock(i2, j11 - 1, k14) == Blocks.air; --j11) {
			}
			waterlilyGen.generate(worldObj, rand, i2, j11, k14);
		}
		for (l3 = 0; l3 < mushroomsPerChunk; ++l3) {
			if (rand.nextInt(4) == 0) {
				i2 = chunkX + rand.nextInt(16) + 8;
				int k15 = chunkZ + rand.nextInt(16) + 8;
				int j12 = worldObj.getHeightValue(i2, k15);
				mushroomBrownGen.generate(worldObj, rand, i2, j12, k15);
			}
			if (rand.nextInt(8) != 0) {
				continue;
			}
			i2 = chunkX + rand.nextInt(16) + 8;
			k4 = chunkZ + rand.nextInt(16) + 8;
			j = worldObj.getHeightValue(i2, k4);
			mushroomRedGen.generate(worldObj, rand, i2, j, k4);
		}
		if (enableRandomMushroom) {
			if (rand.nextInt(4) == 0) {
				i4 = chunkX + rand.nextInt(16) + 8;
				j2 = rand.nextInt(128);
				int k16 = chunkZ + rand.nextInt(16) + 8;
				mushroomBrownGen.generate(worldObj, rand, i4, j2, k16);
			}
			if (rand.nextInt(8) == 0) {
				i4 = chunkX + rand.nextInt(16) + 8;
				j2 = rand.nextInt(128);
				k4 = chunkZ + rand.nextInt(16) + 8;
				mushroomRedGen.generate(worldObj, rand, i4, j2, k4);
			}
		}
		for (l3 = 0; l3 < canePerChunk; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k17 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i2, j3, k17);
		}
		for (l3 = 0; l3 < 10; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k18 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i2, j3, k18);
		}
		for (l3 = 0; l3 < reedPerChunk; ++l3) {
			int j13;
			i2 = chunkX + rand.nextInt(16) + 8;
			k4 = chunkZ + rand.nextInt(16) + 8;
			//noinspection StatementWithEmptyBody
			for (j13 = rand.nextInt(128); j13 > 0 && worldObj.getBlock(i2, j13 - 1, k4) == Blocks.air; --j13) {
			}
			if (rand.nextFloat() < dryReedChance) {
				dryReedGen.generate(worldObj, rand, i2, j13, k4);
				continue;
			}
			reedGen.generate(worldObj, rand, i2, j13, k4);
		}
		for (l3 = 0; l3 < cornPerChunk; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k19 = chunkZ + rand.nextInt(16) + 8;
			cornGen.generate(worldObj, rand, i2, j3, k19);
		}
		for (l3 = 0; l3 < cactiPerChunk; ++l3) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k20 = chunkZ + rand.nextInt(16) + 8;
			cactusGen.generate(worldObj, rand, i2, j3, k20);
		}
		if (melonPerChunk > 0.0f) {
			int melonInt = MathHelper.floor_double(melonPerChunk);
			float melonF = melonPerChunk - melonInt;
			for (l2 = 0; l2 < melonInt; ++l2) {
				int i13 = chunkX + rand.nextInt(16) + 8;
				int k21 = chunkZ + rand.nextInt(16) + 8;
				int j14 = worldObj.getHeightValue(i13, k21);
				melonGen.generate(worldObj, rand, i13, j14, k21);
			}
			if (rand.nextFloat() < melonF) {
				i = chunkX + rand.nextInt(16) + 8;
				int k22 = chunkZ + rand.nextInt(16) + 8;
				int j15 = worldObj.getHeightValue(i, k22);
				melonGen.generate(worldObj, rand, i, j15, k22);
			}
		}
		if (flowersPerChunk > 0 && rand.nextInt(32) == 0) {
			i4 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			pumpkinGen.generate(worldObj, rand, i4, j2, k4);
		}
		if (flowersPerChunk > 0 && rand.nextInt(4) == 0) {
			i4 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			new LOTRWorldGenBerryBush().generate(worldObj, rand, i4, j2, k4);
		}
		if (generateAthelas && rand.nextInt(30) == 0) {
			i4 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenFlowers(LOTRMod.athelas).generate(worldObj, rand, i4, j2, k4);
		}
		if (generateWater) {
			int k23;
			LOTRWorldGenStreams waterGen = new LOTRWorldGenStreams(Blocks.flowing_water);
			for (l7 = 0; l7 < 50; ++l7) {
				i = chunkX + rand.nextInt(16) + 8;
				j = rand.nextInt(rand.nextInt(120) + 8);
				k23 = chunkZ + rand.nextInt(16) + 8;
				waterGen.generate(worldObj, rand, i, j, k23);
			}
			if (biome.rootHeight > 1.0f) {
				for (l7 = 0; l7 < 50; ++l7) {
					i = chunkX + rand.nextInt(16) + 8;
					j = 100 + rand.nextInt(150);
					k23 = chunkZ + rand.nextInt(16) + 8;
					waterGen.generate(worldObj, rand, i, j, k23);
				}
			}
		}
		if (generateLava) {
			LOTRWorldGenStreams lavaGen = new LOTRWorldGenStreams(Blocks.flowing_lava);
			int lava = 20;
			if (biome instanceof LOTRBiomeGenMordor) {
				lava = 50;
			}
			for (l2 = 0; l2 < lava; ++l2) {
				int i14 = chunkX + rand.nextInt(16) + 8;
				int j16 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
				int k24 = chunkZ + rand.nextInt(16) + 8;
				lavaGen.generate(worldObj, rand, i14, j16, k24);
			}
		}
		if (generateOrcDungeon) {
			for (l3 = 0; l3 < 6; ++l3) {
				i2 = chunkX + rand.nextInt(16) + 8;
				j3 = rand.nextInt(128);
				k2 = chunkZ + rand.nextInt(16) + 8;
				orcDungeonGen.generate(worldObj, rand, i2, j3, k2);
			}
		}
		if (generateTrollHoard) {
			for (l3 = 0; l3 < 2; ++l3) {
				i2 = chunkX + rand.nextInt(16) + 8;
				j3 = MathHelper.getRandomIntegerInRange(rand, 36, 90);
				k2 = chunkZ + rand.nextInt(16) + 8;
				trollHoardGen.generate(worldObj, rand, i2, j3, k2);
			}
		}
		if (biomeVariant.boulderGen != null && rand.nextInt(biomeVariant.boulderChance) == 0) {
			int boulders = MathHelper.getRandomIntegerInRange(rand, 1, biomeVariant.boulderMax);
			for (l7 = 0; l7 < boulders; ++l7) {
				i = chunkX + rand.nextInt(16) + 8;
				k2 = chunkZ + rand.nextInt(16) + 8;
				biomeVariant.boulderGen.generate(worldObj, rand, i, worldObj.getHeightValue(i, k2), k2);
			}
		}
	}

	public void decorate(World world, Random random, int i, int k) {
		worldObj = world;
		rand = random;
		chunkX = i;
		chunkZ = k;
		decorate();
	}

	public void generateOres() {
		float f;
		for (OreGenerant soil : biomeSoils) {
			genStandardOre(soil.oreChance, soil.oreGen, soil.minHeight, soil.maxHeight);
		}
		for (OreGenerant ore : biomeOres) {
			f = ore.oreChance * biomeOreFactor;
			genStandardOre(f, ore.oreGen, ore.minHeight, ore.maxHeight);
		}
		for (OreGenerant gem : biomeGems) {
			f = gem.oreChance * biomeGemFactor;
			genStandardOre(f, gem.oreGen, gem.minHeight, gem.maxHeight);
		}
	}

	public void genStandardOre(float ores, WorldGenerator oreGen, int minHeight, int maxHeight) {
		while (ores > 0.0f) {
			boolean generate = ores >= 1.0f || rand.nextFloat() < ores;
			ores -= 1.0f;
			if (!generate) {
				continue;
			}
			int i = chunkX + rand.nextInt(16);
			int j = MathHelper.getRandomIntegerInRange(rand, minHeight, maxHeight);
			int k = chunkZ + rand.nextInt(16);
			oreGen.generate(worldObj, rand, i, j, k);
		}
	}

	public void genTree(World world, Random random, int i, int j, int k) {
		WorldGenAbstractTree treeGen = biome.getTreeGen(world, random, i, j, k);
		treeGen.generate(world, random, i, j, k);
	}

	public LOTRTreeType getRandomTree(Random random) {
		if (treeTypes.isEmpty()) {
			return LOTRTreeType.OAK;
		}
		WeightedRandom.Item item = WeightedRandom.getRandomItem(random, treeTypes);
		return ((LOTRTreeType.WeightedTreeType) item).treeType;
	}

	public LOTRTreeType getRandomTreeForVariant(Random random, LOTRBiomeVariant variant) {
		if (variant.treeTypes.isEmpty()) {
			return getRandomTree(random);
		}
		float f = variant.variantTreeChance;
		if (random.nextFloat() < f) {
			return variant.getRandomTree(random);
		}
		return getRandomTree(random);
	}

	public int getVariantTreesPerChunk(LOTRBiomeVariant variant) {
		int trees = treesPerChunk;
		if (variant.treeFactor > 1.0f) {
			trees = Math.max(trees, 1);
		}
		return Math.round(trees * variant.treeFactor);
	}

	public void resetTreeCluster() {
		setTreeCluster(0, -1);
	}

	public void setTreeCluster(int size, int chance) {
		treeClusterSize = size;
		treeClusterChance = chance;
	}

	public static class OreGenerant {
		public WorldGenerator oreGen;
		public float oreChance;
		public int minHeight;
		public int maxHeight;

		public OreGenerant(WorldGenerator gen, float f, int min, int max) {
			oreGen = gen;
			oreChance = f;
			minHeight = min;
			maxHeight = max;
		}
	}

	public static class RandomStructure {
		public WorldGenerator structureGen;
		public int chunkChance;

		public RandomStructure(WorldGenerator w, int i) {
			structureGen = w;
			chunkChance = i;
		}
	}

}
