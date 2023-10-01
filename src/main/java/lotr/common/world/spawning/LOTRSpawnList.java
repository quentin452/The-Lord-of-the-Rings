package lotr.common.world.spawning;

import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTRSpawnList {
	public static LOTRSpawnList HOBBITS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHobbit.class, 40, 1, 4), new LOTRSpawnEntry(LOTREntityHobbitBounder.class, 1, 1, 3));
	public static LOTRSpawnList HOBBITS_ORCHARD = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHobbitOrcharder.class, 10, 1, 1));
	public static LOTRSpawnList DARK_HUORNS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDarkHuorn.class, 10, 4, 4));
	public static LOTRSpawnList BARROW_WIGHTS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityBarrowWight.class, 10, 1, 1));
	public static LOTRSpawnList BREE_MEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityBreeMan.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityBreeHobbit.class, 3, 4, 6));
	public static LOTRSpawnList BREE_GUARDS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityBreeGuard.class, 10, 2, 4));
	public static LOTRSpawnList RUFFIANS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRuffianSpy.class, 10, 1, 4), new LOTRSpawnEntry(LOTREntityRuffianBrute.class, 5, 1, 4)).factionOverride(LOTRFaction.ISENGARD);
	public static LOTRSpawnList BLUE_DWARVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityBlueDwarf.class, 100, 4, 4), new LOTRSpawnEntry(LOTREntityBlueDwarfMiner.class, 15, 1, 3), new LOTRSpawnEntry(LOTREntityBlueDwarfWarrior.class, 20, 4, 4), new LOTRSpawnEntry(LOTREntityBlueDwarfAxeThrower.class, 10, 4, 4));
	public static LOTRSpawnList DUNEDAIN_NORTH = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDunedain.class, 10, 2, 4));
	public static LOTRSpawnList RANGERS_NORTH = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRangerNorth.class, 10, 1, 4));
	public static LOTRSpawnList LINDON_ELVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHighElf.class, 10, 4, 6));
	public static LOTRSpawnList LINDON_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHighElfWarrior.class, 10, 4, 4));
	public static LOTRSpawnList RIVENDELL_ELVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRivendellElf.class, 10, 4, 6));
	public static LOTRSpawnList RIVENDELL_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRivendellWarrior.class, 10, 4, 4));
	public static LOTRSpawnList GUNDABAD_ORCS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGundabadOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityGundabadOrcArcher.class, 10, 4, 6));
	public static LOTRSpawnList GUNDABAD_URUKS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGundabadUruk.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityGundabadUrukArcher.class, 10, 2, 4));
	public static LOTRSpawnList GUNDABAD_WARGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGundabadWarg.class, 10, 4, 4));
	public static LOTRSpawnList ANGMAR_ORCS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityAngmarOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityAngmarOrcArcher.class, 10, 4, 6));
	public static LOTRSpawnList ANGMAR_BOMBARDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityAngmarOrcBombardier.class, 10, 1, 2));
	public static LOTRSpawnList ANGMAR_WARGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityAngmarWarg.class, 10, 4, 4));
	public static LOTRSpawnList ANGMAR_HILLMEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityAngmarHillman.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityAngmarHillmanWarrior.class, 5, 4, 6), new LOTRSpawnEntry(LOTREntityAngmarHillmanAxeThrower.class, 5, 4, 6));
	public static LOTRSpawnList TROLLS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityTroll.class, 10, 1, 3));
	public static LOTRSpawnList HILL_TROLLS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMountainTroll.class, 10, 1, 3));
	public static LOTRSpawnList SNOW_TROLLS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntitySnowTroll.class, 10, 1, 3));
	public static LOTRSpawnList WOOD_ELVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityWoodElf.class, 10, 4, 6));
	public static LOTRSpawnList WOOD_ELF_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityWoodElfScout.class, 10, 4, 4), new LOTRSpawnEntry(LOTREntityWoodElfWarrior.class, 5, 4, 4));
	public static LOTRSpawnList MIRKWOOD_SPIDERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMirkwoodSpider.class, 10, 4, 6));
	public static LOTRSpawnList DOL_GULDUR_ORCS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDolGuldurOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityDolGuldurOrcArcher.class, 10, 4, 6));
	public static LOTRSpawnList MIRK_TROLLS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMirkTroll.class, 10, 1, 3));
	public static LOTRSpawnList DALE_MEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDaleMan.class, 10, 2, 4));
	public static LOTRSpawnList DALE_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDaleLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityDaleSoldier.class, 10, 2, 8), new LOTRSpawnEntry(LOTREntityDaleArcher.class, 5, 2, 8));
	public static LOTRSpawnList DWARVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDwarf.class, 100, 4, 4), new LOTRSpawnEntry(LOTREntityDwarfMiner.class, 15, 1, 3), new LOTRSpawnEntry(LOTREntityDwarfWarrior.class, 20, 4, 4), new LOTRSpawnEntry(LOTREntityDwarfAxeThrower.class, 10, 4, 4));
	public static LOTRSpawnList GALADHRIM = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGaladhrimElf.class, 10, 4, 6));
	public static LOTRSpawnList GALADHRIM_WARDENS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGaladhrimWarden.class, 10, 4, 4));
	public static LOTRSpawnList GALADHRIM_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGaladhrimWarrior.class, 10, 4, 4));
	public static LOTRSpawnList DUNLENDINGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDunlending.class, 30, 4, 6));
	public static LOTRSpawnList DUNLENDING_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDunlendingWarrior.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityDunlendingArcher.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityDunlendingAxeThrower.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityDunlendingBerserker.class, 5, 1, 2));
	public static LOTRSpawnList ENTS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityEnt.class, 10, 4, 4));
	public static LOTRSpawnList HUORNS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHuorn.class, 10, 4, 4));
	public static LOTRSpawnList ISENGARD_SNAGA = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityIsengardSnaga.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityIsengardSnagaArcher.class, 5, 4, 6));
	public static LOTRSpawnList URUK_HAI = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUrukHai.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityUrukHaiCrossbower.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityUrukHaiSapper.class, 3, 1, 2), new LOTRSpawnEntry(LOTREntityUrukHaiBerserker.class, 5, 4, 6));
	public static LOTRSpawnList URUK_WARGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUrukWarg.class, 10, 4, 4));
	public static LOTRSpawnList ROHIRRIM = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRohanMan.class, 10, 4, 6));
	public static LOTRSpawnList ROHIRRIM_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRohirrimWarrior.class, 20, 3, 8), new LOTRSpawnEntry(LOTREntityRohirrimArcher.class, 10, 3, 8));
	public static LOTRSpawnList GONDOR_MEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorMan.class, 10, 4, 6));
	public static LOTRSpawnList GONDOR_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityGondorSoldier.class, 10, 2, 8), new LOTRSpawnEntry(LOTREntityGondorArcher.class, 5, 2, 8));
	public static LOTRSpawnList DOL_AMROTH_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDolAmrothSoldier.class, 20, 2, 6), new LOTRSpawnEntry(LOTREntityDolAmrothArcher.class, 10, 2, 6), new LOTRSpawnEntry(LOTREntitySwanKnight.class, 5, 2, 4));
	public static LOTRSpawnList LOSSARNACH_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityLossarnachAxeman.class, 20, 2, 6));
	public static LOTRSpawnList LEBENNIN_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityLebenninLevyman.class, 40, 2, 4), new LOTRSpawnEntry(LOTREntityGondorSoldier.class, 10, 2, 8), new LOTRSpawnEntry(LOTREntityGondorArcher.class, 5, 2, 8));
	public static LOTRSpawnList PELARGIR_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityLebenninLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityPelargirMarine.class, 15, 2, 8));
	public static LOTRSpawnList LAMEDON_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorLevyman.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityLamedonSoldier.class, 10, 2, 6), new LOTRSpawnEntry(LOTREntityLamedonArcher.class, 5, 2, 6));
	public static LOTRSpawnList LAMEDON_HILLMEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityLamedonHillman.class, 20, 4, 6));
	public static LOTRSpawnList BLACKROOT_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityBlackrootSoldier.class, 5, 2, 8), new LOTRSpawnEntry(LOTREntityBlackrootArcher.class, 5, 15, 8));
	public static LOTRSpawnList PINNATH_GELIN_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityPinnathGelinSoldier.class, 15, 2, 6));
	public static LOTRSpawnList RANGERS_ITHILIEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityRangerIthilien.class, 10, 1, 4));
	public static LOTRSpawnList MORDOR_ORCS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMordorOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityMordorOrcArcher.class, 10, 4, 6));
	public static LOTRSpawnList MORDOR_BOMBARDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMordorOrcBombardier.class, 10, 1, 2));
	public static LOTRSpawnList BLACK_URUKS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityBlackUruk.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityBlackUrukArcher.class, 10, 4, 6));
	public static LOTRSpawnList MORDOR_WARGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMordorWarg.class, 10, 4, 4));
	public static LOTRSpawnList MORDOR_SPIDERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMordorSpider.class, 10, 4, 4));
	public static LOTRSpawnList OLOG_HAI = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityOlogHai.class, 10, 1, 3));
	public static LOTRSpawnList WICKED_DWARVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityWickedDwarf.class, 10, 1, 3));
	public static LOTRSpawnList DORWINION_MEN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDorwinionMan.class, 10, 4, 6));
	public static LOTRSpawnList DORWINION_GUARDS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDorwinionGuard.class, 20, 1, 3), new LOTRSpawnEntry(LOTREntityDorwinionCrossbower.class, 10, 1, 3));
	public static LOTRSpawnList DORWINION_ELVES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDorwinionElf.class, 10, 4, 6));
	public static LOTRSpawnList DORWINION_ELF_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDorwinionElfWarrior.class, 20, 1, 3), new LOTRSpawnEntry(LOTREntityDorwinionElfArcher.class, 10, 1, 3));
	public static LOTRSpawnList DORWINION_VINEYARDS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityDorwinionVinehand.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityDorwinionVinekeeper.class, 2, 1, 1));
	public static LOTRSpawnList EASTERLINGS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityEasterling.class, 20, 2, 4));
	public static LOTRSpawnList EASTERLING_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityEasterlingLevyman.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityEasterlingWarrior.class, 10, 3, 6), new LOTRSpawnEntry(LOTREntityEasterlingArcher.class, 5, 3, 6), new LOTRSpawnEntry(LOTREntityEasterlingFireThrower.class, 2, 1, 3));
	public static LOTRSpawnList EASTERLING_GOLD_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityEasterlingGoldWarrior.class, 10, 2, 4));
	public static LOTRSpawnList HARNEDHRIM = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHarnedhrim.class, 10, 2, 4));
	public static LOTRSpawnList HARNEDOR_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHarnedorWarrior.class, 20, 3, 6), new LOTRSpawnEntry(LOTREntityHarnedorArcher.class, 10, 3, 6));
	public static LOTRSpawnList COAST_SOUTHRONS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityNearHaradrim.class, 10, 2, 4));
	public static LOTRSpawnList SOUTHRON_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityNearHaradrimWarrior.class, 20, 3, 6), new LOTRSpawnEntry(LOTREntityNearHaradrimArcher.class, 10, 3, 6), new LOTRSpawnEntry(LOTREntitySouthronChampion.class, 4, 1, 2));
	public static LOTRSpawnList UMBARIANS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUmbarian.class, 10, 2, 4));
	public static LOTRSpawnList UMBAR_SOLDIERS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUmbarWarrior.class, 10, 3, 6), new LOTRSpawnEntry(LOTREntityUmbarArcher.class, 5, 3, 6));
	public static LOTRSpawnList CORSAIRS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityCorsair.class, 10, 2, 6));
	public static LOTRSpawnList GONDOR_RENEGADES = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGondorRenegade.class, 10, 1, 4));
	public static LOTRSpawnList NOMADS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityNomad.class, 10, 4, 6));
	public static LOTRSpawnList NOMAD_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityNomadWarrior.class, 20, 2, 4), new LOTRSpawnEntry(LOTREntityNomadArcher.class, 10, 2, 4));
	public static LOTRSpawnList GULF_HARADRIM = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGulfHaradrim.class, 10, 2, 4));
	public static LOTRSpawnList GULF_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityGulfHaradWarrior.class, 20, 3, 6), new LOTRSpawnEntry(LOTREntityGulfHaradArcher.class, 10, 3, 6));
	public static LOTRSpawnList MORWAITH = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMoredain.class, 10, 4, 6));
	public static LOTRSpawnList MORWAITH_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityMoredainWarrior.class, 10, 4, 6));
	public static LOTRSpawnList TAURETHRIM = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityTauredain.class, 10, 4, 6));
	public static LOTRSpawnList TAURETHRIM_WARRIORS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityTauredainWarrior.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityTauredainBlowgunner.class, 20, 4, 6));
	public static LOTRSpawnList HALF_TROLLS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityHalfTroll.class, 60, 2, 4), new LOTRSpawnEntry(LOTREntityHalfTrollWarrior.class, 35, 2, 4), new LOTRSpawnEntry(LOTREntityHalfTrollScavenger.class, 5, 1, 1));
	public static LOTRSpawnList UTUMNO_ICE = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUtumnoOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoOrcArcher.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityUtumnoIceWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityUtumnoIceSpider.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityUtumnoSnowTroll.class, 5, 1, 3));
	public static LOTRSpawnList UTUMNO_OBSIDIAN = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUtumnoOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoOrcArcher.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityUtumnoObsidianWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityTormentedElf.class, 10, 2, 6), new LOTRSpawnEntry(LOTREntityUtumnoTroll.class, 5, 2, 4));
	public static LOTRSpawnList UTUMNO_FIRE = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityUtumnoOrc.class, 20, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoOrcArcher.class, 10, 4, 6), new LOTRSpawnEntry(LOTREntityUtumnoWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityUtumnoFireWarg.class, 10, 2, 4), new LOTRSpawnEntry(LOTREntityTormentedElf.class, 15, 2, 6), new LOTRSpawnEntry(LOTREntityUtumnoTroll.class, 15, 2, 4), new LOTRSpawnEntry(LOTREntityBalrog.class, 2, 1, 1));
	public static LOTRSpawnList UTUMNO_GUESTS = new LOTRSpawnList(new LOTRSpawnEntry(LOTREntityScrapTrader.class, 10, 1, 1));
	public List<LOTRSpawnEntry> spawnList;
	public LOTRFaction discoveredFaction;

	public LOTRSpawnList(LOTRSpawnEntry... entries) {
		spawnList = Arrays.asList(entries);
	}

	public LOTRSpawnList factionOverride(LOTRFaction fac) {
		discoveredFaction = fac;
		return this;
	}

	public LOTRFaction getListCommonFaction(World world) {
		if (discoveredFaction != null) {
			return discoveredFaction;
		}
		LOTRFaction commonFaction = null;
		for (LOTRSpawnEntry entry : spawnList) {
			Class entityClass = entry.entityClass;
			if (LOTREntityNPC.class.isAssignableFrom(entityClass)) {
				try {
					LOTREntityNPC npc = (LOTREntityNPC) LOTREntities.createEntityByClass(entityClass, world);
					LOTRFaction fac = npc.getFaction();
					if (commonFaction == null) {
						commonFaction = fac;
						continue;
					}
					if (commonFaction == fac) {
						continue;
					}
					throw new IllegalArgumentException("Spawn lists must contain only one faction! Mismatched entity class: " + entityClass.getName());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			throw new IllegalArgumentException("Spawn list must contain only NPCs - invalid " + entityClass.getName());
		}
		if (commonFaction != null) {
			discoveredFaction = commonFaction;
			return discoveredFaction;
		}
		throw new IllegalArgumentException("Failed to discover faction for spawn list");
	}

	public LOTRSpawnEntry getRandomSpawnEntry(Random rand) {
		return (LOTRSpawnEntry) WeightedRandom.getRandomItem(rand, spawnList);
	}

	public List<LOTRSpawnEntry> getReadOnlyList() {
		return new ArrayList<>(spawnList);
	}
}
