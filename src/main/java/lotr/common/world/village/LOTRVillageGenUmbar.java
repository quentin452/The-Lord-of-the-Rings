package lotr.common.world.village;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityUmbarian;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure2.*;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRVillageGenUmbar extends LOTRVillageGenSouthron {
	public LOTRVillageGenUmbar(LOTRBiome biome, float f) {
		super(biome, f);
	}

	@Override
	public LOTRVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new InstanceUmbar(this, world, i, k, random, loc);
	}

	public static class InstanceUmbar extends LOTRVillageGenSouthron.Instance {
		public InstanceUmbar(LOTRVillageGenUmbar village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public LOTRWorldGenStructureBase2 getBarracks(Random random) {
			return new LOTRWorldGenUmbarBarracks(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getBazaar(Random random) {
			return new LOTRWorldGenUmbarBazaar(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFlowers(Random random) {
			return new LOTRWorldGenUmbarTownFlowers(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFortCorner(Random random) {
			return new LOTRWorldGenUmbarFortCorner(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFortGate(Random random) {
			return new LOTRWorldGenUmbarFortGate(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFortress(Random random) {
			return new LOTRWorldGenUmbarFortress(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFortWallLong(Random random) {
			return new LOTRWorldGenUmbarFortWall.Long(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getFortWallShort(Random random) {
			return new LOTRWorldGenUmbarFortWall.Short(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getHouse(Random random) {
			return new LOTRWorldGenUmbarHouse(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getLamp(Random random) {
			return new LOTRWorldGenUmbarLamp(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getMansion(Random random) {
			return new LOTRWorldGenUmbarMansion(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new LOTRWorldGenUmbarFarm(false);
			}
			return new LOTRWorldGenUmbarPasture(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
			if (random.nextInt(6) == 0) {
				return new LOTRWorldGenUmbarSmithy(false);
			}
			if (random.nextInt(6) == 0) {
				return new LOTRWorldGenUmbarStables(false);
			}
			return new LOTRWorldGenUmbarHouse(false);
		}

		@Override
		public LOTRWorldGenSouthronVillageSign getSignpost(Random random) {
			return new LOTRWorldGenUmbarVillageSign(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getSmithy(Random random) {
			return new LOTRWorldGenUmbarSmithy(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getStables(Random random) {
			return new LOTRWorldGenUmbarStables(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getStatue(Random random) {
			return new LOTRWorldGenUmbarStatue(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTavern(Random random) {
			return new LOTRWorldGenUmbarTavern(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTower(Random random) {
			return new LOTRWorldGenUmbarTower(false);
		}

		@Override
		public LOTRWorldGenSouthronTownGate getTownGate(Random random) {
			return new LOTRWorldGenUmbarTownGate(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTownWallCorner(Random random) {
			return new LOTRWorldGenUmbarTownCorner(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTownWallExtra(Random random) {
			return new LOTRWorldGenUmbarTownWall.Extra(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTownWallLong(Random random) {
			return new LOTRWorldGenUmbarTownWall.Long(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTownWallShort(Random random) {
			return new LOTRWorldGenUmbarTownWall.Short(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTownWallSideMid(Random random) {
			return new LOTRWorldGenUmbarTownWall.SideMid(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTraining(Random random) {
			return new LOTRWorldGenUmbarTraining(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getTree(Random random) {
			return new LOTRWorldGenUmbarTownTree(false);
		}

		@Override
		public LOTRWorldGenStructureBase2 getWell(Random random) {
			return new LOTRWorldGenUmbarWell(false);
		}

		@Override
		public void placeChampionRespawner() {
		}

		@Override
		public void setCivilianSpawnClass(LOTREntityNPCRespawner spawner) {
			spawner.setSpawnClass(LOTREntityUmbarian.class);
		}

		@Override
		public void setWarriorSpawnClasses(LOTREntityNPCRespawner spawner) {
			spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
		}
	}

}
