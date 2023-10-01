package lotr.common.world.village;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.*;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRVillageGenTauredain extends LOTRVillageGen {
	public LOTRVillageGenTauredain(LOTRBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends LOTRVillageGen.AbstractInstance<LOTRVillageGenTauredain> {
		public Instance(LOTRVillageGenTauredain village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			int smithyPos = random.nextInt(4);
			addStructure(new LOTRWorldGenTauredainChieftainPyramid(false), 0, -11, 0, true);
			addStructure(new LOTRWorldGenTauredainVillageTree(false), 0, -16, 2);
			addStructure(new LOTRWorldGenTauredainVillageFarm(false), -16, -19, 2);
			addStructure(new LOTRWorldGenTauredainVillageFarm(false), 16, -19, 2);
			addStructure(new LOTRWorldGenTauredainHouseStilts(false), 0, 15, 0);
			addStructure(new LOTRWorldGenTauredainVillageFarm(false), -16, 19, 0);
			addStructure(new LOTRWorldGenTauredainVillageFarm(false), 16, 19, 0);
			addStructure(new LOTRWorldGenTauredainHouseLarge(false), -20, 0, 1);
			addStructure(new LOTRWorldGenTauredainHouseLarge(false), 20, 1, 3);
			addStructure(new LOTRWorldGenTauredainHouseSimple(false), -15, -36, 0);
			addStructure(new LOTRWorldGenTauredainHouseSimple(false), 15, -36, 0);
			if (smithyPos == 0) {
				addStructure(new LOTRWorldGenTauredainSmithy(false), -22, -13, 1);
			} else {
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, -22, 3);
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, -12, 3);
			}
			if (smithyPos == 1) {
				addStructure(new LOTRWorldGenTauredainSmithy(false), -22, 14, 1);
			} else {
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, 13, 3);
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), -32, 23, 3);
			}
			if (smithyPos == 2) {
				addStructure(new LOTRWorldGenTauredainSmithy(false), 22, -13, 3);
			} else {
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, -22, 1);
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, -12, 1);
			}
			if (smithyPos == 3) {
				addStructure(new LOTRWorldGenTauredainSmithy(false), 22, 14, 3);
			} else {
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, 13, 1);
				addStructure(new LOTRWorldGenTauredainHouseSimple(false), 32, 23, 1);
			}
			addStructure(new LOTRWorldGenTauredainHouseSimple(false), -15, 36, 2);
			addStructure(new LOTRWorldGenTauredainHouseSimple(false), 0, 37, 2);
			addStructure(new LOTRWorldGenTauredainHouseSimple(false), 15, 36, 2);
			addStructure(new LOTRWorldGenTauredainWatchtower(false), -26, -36, 0);
			addStructure(new LOTRWorldGenTauredainWatchtower(false), 26, -36, 0);
			addStructure(new LOTRWorldGenTauredainWatchtower(false), -26, 37, 2);
			addStructure(new LOTRWorldGenTauredainWatchtower(false), 26, 37, 2);
		}

		@Override
		public LOTRRoadType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isFlat() {
			return false;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}

}
