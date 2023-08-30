package lotr.common.world.spawning;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemConquestHorn;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;

public enum LOTRInvasions {
	HOBBIT(LOTRFaction.HOBBIT), BREE(LOTRFaction.BREE), RANGER_NORTH(LOTRFaction.RANGER_NORTH), BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS), HIGH_ELF_LINDON(LOTRFaction.HIGH_ELF, "lindon"), HIGH_ELF_RIVENDELL(LOTRFaction.HIGH_ELF, "rivendell"), GUNDABAD(LOTRFaction.GUNDABAD), GUNDABAD_WARG(LOTRFaction.GUNDABAD, "warg"), ANGMAR(LOTRFaction.ANGMAR), ANGMAR_HILLMEN(LOTRFaction.ANGMAR, "hillmen"), ANGMAR_WARG(LOTRFaction.ANGMAR, "warg"), WOOD_ELF(LOTRFaction.WOOD_ELF), DOL_GULDUR(LOTRFaction.DOL_GULDUR), DALE(LOTRFaction.DALE), DWARF(LOTRFaction.DURINS_FOLK), GALADHRIM(LOTRFaction.LOTHLORIEN), DUNLAND(LOTRFaction.DUNLAND), URUK_HAI(LOTRFaction.ISENGARD), FANGORN(LOTRFaction.FANGORN), ROHAN(LOTRFaction.ROHAN), GONDOR(LOTRFaction.GONDOR), GONDOR_ITHILIEN(LOTRFaction.GONDOR, "ithilien"), GONDOR_DOL_AMROTH(LOTRFaction.GONDOR, "dolAmroth"), GONDOR_LOSSARNACH(LOTRFaction.GONDOR, "lossarnach"), GONDOR_PELARGIR(LOTRFaction.GONDOR, "pelargir"), GONDOR_PINNATH_GELIN(LOTRFaction.GONDOR, "pinnathGelin"), GONDOR_BLACKROOT(LOTRFaction.GONDOR, "blackroot"), GONDOR_LEBENNIN(LOTRFaction.GONDOR, "lebennin"), GONDOR_LAMEDON(LOTRFaction.GONDOR, "lamedon"), MORDOR(LOTRFaction.MORDOR), MORDOR_BLACK_URUK(LOTRFaction.MORDOR, "blackUruk"), MORDOR_NAN_UNGOL(LOTRFaction.MORDOR, "nanUngol"), MORDOR_WARG(LOTRFaction.MORDOR, "warg"), DORWINION(LOTRFaction.DORWINION), DORWINION_ELF(LOTRFaction.DORWINION, "elf"), RHUN(LOTRFaction.RHUDEL), NEAR_HARAD_HARNEDOR(LOTRFaction.NEAR_HARAD, "harnedor"), NEAR_HARAD_COAST(LOTRFaction.NEAR_HARAD, "coast"), NEAR_HARAD_UMBAR(LOTRFaction.NEAR_HARAD, "umbar"), NEAR_HARAD_CORSAIR(LOTRFaction.NEAR_HARAD, "corsair"), NEAR_HARAD_NOMAD(LOTRFaction.NEAR_HARAD, "nomad"), NEAR_HARAD_GULF(LOTRFaction.NEAR_HARAD, "gulf"), MOREDAIN(LOTRFaction.MORWAITH), TAUREDAIN(LOTRFaction.TAURETHRIM), HALF_TROLL(LOTRFaction.HALF_TROLL);

	public LOTRFaction invasionFaction;
	public String subfaction;
	public Collection<InvasionSpawnEntry> invasionMobs = new ArrayList<>();
	public Item invasionIcon;

	LOTRInvasions(LOTRFaction f) {
		this(f, null);
	}

	LOTRInvasions(LOTRFaction f, String s) {
		invasionFaction = f;
		subfaction = s;
	}

	public String codeName() {
		StringBuilder s = new StringBuilder().append(invasionFaction.codeName());
		if (subfaction != null) {
			s.append("_").append(subfaction);
		}
		return s.toString();
	}

	public List<String> codeNameAndAliases() {
		List<String> aliases = new ArrayList<>();
		if (subfaction != null) {
			String subfactionAdd = "_" + subfaction;
			aliases.add(invasionFaction.codeName() + subfactionAdd);
			for (String al : invasionFaction.listAliases()) {
				aliases.add(al + subfactionAdd);
			}
		} else {
			aliases.add(invasionFaction.codeName());
			aliases.addAll(invasionFaction.listAliases());
		}
		return aliases;
	}

	public String codeNameHorn() {
		return "lotr.invasion." + codeName() + ".horn";
	}

	public ItemStack createConquestHorn() {
		ItemStack horn = new ItemStack(LOTRMod.conquestHorn);
		LOTRItemConquestHorn.setInvasionType(horn, this);
		return horn;
	}

	public ItemStack getInvasionIcon() {
		Item sword = invasionIcon;
		if (sword == null) {
			sword = Items.iron_sword;
		}
		return new ItemStack(sword);
	}

	public String invasionName() {
		if (subfaction == null) {
			return invasionFaction.factionName();
		}
		return StatCollector.translateToLocal("lotr.invasion." + codeName());
	}

	public static void createMobLists() {
		HOBBIT.invasionIcon = LOTRMod.hobbitPipe;
		HOBBIT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHobbitBounder.class, 15));
		BREE.invasionIcon = LOTRMod.pikeIron;
		BREE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBreeGuard.class, 15));
		BREE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBreeBannerBearer.class, 2));
		RANGER_NORTH.invasionIcon = LOTRMod.rangerBow;
		RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorth.class, 15));
		RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorthBannerBearer.class, 2));
		BLUE_MOUNTAINS.invasionIcon = LOTRMod.hammerBlueDwarven;
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfWarrior.class, 10));
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfAxeThrower.class, 5));
		BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfBannerBearer.class, 2));
		HIGH_ELF_LINDON.invasionIcon = LOTRMod.swordHighElven;
		HIGH_ELF_LINDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfWarrior.class, 15));
		HIGH_ELF_LINDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfBannerBearer.class, 2));
		HIGH_ELF_RIVENDELL.invasionIcon = LOTRMod.swordRivendell;
		HIGH_ELF_RIVENDELL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRivendellWarrior.class, 15));
		HIGH_ELF_RIVENDELL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRivendellBannerBearer.class, 2));
		GUNDABAD.invasionIcon = LOTRMod.swordGundabadUruk;
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrc.class, 20));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrcArcher.class, 10));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadWarg.class, 20));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadBannerBearer.class, 5));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadUruk.class, 5));
		GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadUrukArcher.class, 2));
		GUNDABAD_WARG.invasionIcon = LOTRMod.wargBone;
		GUNDABAD_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadWarg.class, 10));
		ANGMAR.invasionIcon = LOTRMod.swordAngmar;
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrc.class, 10));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcArcher.class, 5));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcBombardier.class, 3));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarBannerBearer.class, 2));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
		ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWargBombardier.class, 1));
		ANGMAR_HILLMEN.invasionIcon = LOTRMod.swordBronze;
		ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillman.class, 10));
		ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanWarrior.class, 5));
		ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanAxeThrower.class, 5));
		ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanBannerBearer.class, 2));
		ANGMAR_WARG.invasionIcon = LOTRMod.wargBone;
		ANGMAR_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
		WOOD_ELF.invasionIcon = LOTRMod.swordWoodElven;
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfWarrior.class, 10));
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfScout.class, 5));
		WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfBannerBearer.class, 2));
		DOL_GULDUR.invasionIcon = LOTRMod.swordDolGuldur;
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkwoodSpider.class, 15));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrc.class, 10));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrcArcher.class, 5));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurBannerBearer.class, 2));
		DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkTroll.class, 3));
		DALE.invasionIcon = LOTRMod.swordDale;
		DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleLevyman.class, 5));
		DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleSoldier.class, 10));
		DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleArcher.class, 5));
		DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleBannerBearer.class, 1));
		DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEsgarothBannerBearer.class, 1));
		DWARF.invasionIcon = LOTRMod.hammerDwarven;
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfWarrior.class, 10));
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfAxeThrower.class, 5));
		DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfBannerBearer.class, 2));
		GALADHRIM.invasionIcon = LOTRMod.swordElven;
		GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimWarrior.class, 15));
		GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimBannerBearer.class, 2));
		DUNLAND.invasionIcon = LOTRMod.dunlendingClub;
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlending.class, 10));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingWarrior.class, 5));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingArcher.class, 3));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingAxeThrower.class, 3));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingBerserker.class, 2));
		DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingBannerBearer.class, 2));
		URUK_HAI.invasionIcon = LOTRMod.scimitarUruk;
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityIsengardSnaga.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityIsengardSnagaArcher.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHai.class, 10));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiCrossbower.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBerserker.class, 5));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiSapper.class, 3));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBannerBearer.class, 2));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWarg.class, 10));
		URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWargBombardier.class, 1));
		FANGORN.invasionIcon = Items.stick;
		FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEnt.class, 10));
		FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHuorn.class, 20));
		ROHAN.invasionIcon = LOTRMod.swordRohan;
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrimWarrior.class, 10));
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrimArcher.class, 5));
		ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohanBannerBearer.class, 2));
		GONDOR.invasionIcon = LOTRMod.swordGondor;
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
		GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorBannerBearer.class, 2));
		GONDOR_ITHILIEN.invasionIcon = LOTRMod.gondorBow;
		GONDOR_ITHILIEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerIthilien.class, 15));
		GONDOR_ITHILIEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerIthilienBannerBearer.class, 2));
		GONDOR_DOL_AMROTH.invasionIcon = LOTRMod.swordDolAmroth;
		GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothSoldier.class, 10));
		GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothArcher.class, 5));
		GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntitySwanKnight.class, 5));
		GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothBannerBearer.class, 2));
		GONDOR_LOSSARNACH.invasionIcon = LOTRMod.battleaxeLossarnach;
		GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
		GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLossarnachAxeman.class, 15));
		GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLossarnachBannerBearer.class, 2));
		GONDOR_PELARGIR.invasionIcon = LOTRMod.tridentPelargir;
		GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 5));
		GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPelargirMarine.class, 15));
		GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPelargirBannerBearer.class, 2));
		GONDOR_PINNATH_GELIN.invasionIcon = LOTRMod.swordGondor;
		GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
		GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPinnathGelinSoldier.class, 15));
		GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPinnathGelinBannerBearer.class, 2));
		GONDOR_BLACKROOT.invasionIcon = LOTRMod.blackrootBow;
		GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
		GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootSoldier.class, 10));
		GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootArcher.class, 5));
		GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootBannerBearer.class, 2));
		GONDOR_LEBENNIN.invasionIcon = LOTRMod.swordGondor;
		GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 10));
		GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
		GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
		GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninBannerBearer.class, 2));
		GONDOR_LAMEDON.invasionIcon = LOTRMod.hammerGondor;
		GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonHillman.class, 5));
		GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonSoldier.class, 10));
		GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonArcher.class, 5));
		GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonBannerBearer.class, 2));
		MORDOR.invasionIcon = LOTRMod.scimitarOrc;
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrc.class, 10));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 5));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcBombardier.class, 2));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorBannerBearer.class, 2));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMinasMorgulBannerBearer.class, 1));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWargBombardier.class, 1));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUruk.class, 2));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 1));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 1));
		MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityOlogHai.class, 3));
		MORDOR_BLACK_URUK.invasionIcon = LOTRMod.scimitarBlackUruk;
		MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUruk.class, 10));
		MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 5));
		MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 2));
		MORDOR_NAN_UNGOL.invasionIcon = LOTRMod.scimitarOrc;
		MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrc.class, 20));
		MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 10));
		MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNanUngolBannerBearer.class, 5));
		MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorSpider.class, 30));
		MORDOR_WARG.invasionIcon = LOTRMod.wargBone;
		MORDOR_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
		DORWINION.invasionIcon = LOTRMod.mugRedWine;
		DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionGuard.class, 10));
		DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionCrossbower.class, 5));
		DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionBannerBearer.class, 2));
		DORWINION_ELF.invasionIcon = LOTRMod.spearBladorthin;
		DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfWarrior.class, 10));
		DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfArcher.class, 5));
		DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfBannerBearer.class, 2));
		RHUN.invasionIcon = LOTRMod.swordRhun;
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingLevyman.class, 5));
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingWarrior.class, 10));
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingArcher.class, 5));
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingGoldWarrior.class, 5));
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingBannerBearer.class, 5));
		RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingFireThrower.class, 3));
		NEAR_HARAD_HARNEDOR.invasionIcon = LOTRMod.swordHarad;
		NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorWarrior.class, 10));
		NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorArcher.class, 5));
		NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorBannerBearer.class, 2));
		NEAR_HARAD_COAST.invasionIcon = LOTRMod.scimitarNearHarad;
		NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimWarrior.class, 8));
		NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimArcher.class, 5));
		NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradBannerBearer.class, 2));
		NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntitySouthronChampion.class, 2));
		NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 5));
		NEAR_HARAD_UMBAR.invasionIcon = LOTRMod.scimitarNearHarad;
		NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarWarrior.class, 100));
		NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarArcher.class, 50));
		NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarBannerBearer.class, 20));
		NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 30));
		NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorRenegade.class, 4));
		NEAR_HARAD_CORSAIR.invasionIcon = LOTRMod.swordCorsair;
		NEAR_HARAD_CORSAIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityCorsair.class, 10));
		NEAR_HARAD_NOMAD.invasionIcon = LOTRMod.swordHarad;
		NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadWarrior.class, 10));
		NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadArcher.class, 5));
		NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadBannerBearer.class, 2));
		NEAR_HARAD_GULF.invasionIcon = LOTRMod.swordGulfHarad;
		NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradWarrior.class, 10));
		NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradArcher.class, 5));
		NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradBannerBearer.class, 2));
		MOREDAIN.invasionIcon = LOTRMod.spearMoredain;
		MOREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainWarrior.class, 15));
		MOREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainBannerBearer.class, 2));
		TAUREDAIN.invasionIcon = LOTRMod.swordTauredain;
		TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainWarrior.class, 10));
		TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainBlowgunner.class, 5));
		TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainBannerBearer.class, 2));
		HALF_TROLL.invasionIcon = LOTRMod.scimitarHalfTroll;
		HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTroll.class, 10));
		HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTrollWarrior.class, 10));
		HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTrollBannerBearer.class, 2));
	}

	public static LOTRInvasions forID(int ID) {
		for (LOTRInvasions i : values()) {
			if (i.ordinal() != ID) {
				continue;
			}
			return i;
		}
		return null;
	}

	public static LOTRInvasions forName(String name) {
		for (LOTRInvasions i : values()) {
			List<String> aliases = i.codeNameAndAliases();
			for (String al : aliases) {
				if (!al.equals(name)) {
					continue;
				}
				return i;
			}
		}
		return null;
	}

	public static String[] listInvasionNames() {
		String[] names = new String[values().length];
		for (int i = 0; i < names.length; ++i) {
			names[i] = values()[i].codeName();
		}
		return names;
	}

	public static class InvasionSpawnEntry extends WeightedRandom.Item {
		public Class entityClass;

		public InvasionSpawnEntry(Class<? extends LOTREntityNPC> c, int chance) {
			super(chance);
			entityClass = c;
		}

		public Class getEntityClass() {
			return entityClass;
		}
	}

}
