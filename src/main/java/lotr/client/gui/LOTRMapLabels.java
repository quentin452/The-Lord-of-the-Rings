package lotr.client.gui;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.StatCollector;

public enum LOTRMapLabels {
	BELEGAER("belegaer", 250, 1100, 16.0f, 0, -6.0f, -1.0f), BELEGAER_SUB("belegaer_sub", 250, 1220, 6.0f, 0, -6.0f, -1.0f), WESTERN_ISLES("westernIsles", 290, 620, 4.0f, -20, -4.5f, 1.5f), FORODWAITH(LOTRBiome.forodwaith, 1680, 380, 15.0f, 2, -5.5f, -0.5f), FOROCHEL("forochel", 780, 420, 3.0f, 10, -2.5f, 1.5f), SHIRE(LOTRBiome.shire, 820, 750, 2.0f, -10, -2.5f, 0.5f), WHITE_DOWNS(LOTRBiome.whiteDowns, 783, 730, 0.5f, -5, -0.5f, 2.5f), NORTH_MOORS("northMoors", 805, 690, 1.0f, -5, -2.5f, 2.5f), OLD_FOREST(LOTRBiome.oldForest, 875, 745, 0.5f, -5, -0.5f, 2.5f), BREE_LAND(LOTRBiome.breeland, 920, 740, 1.0f, 15, -1.5f, 1.5f), BARROW_DOWNS(LOTRBiome.barrowDowns, 900, 760, 0.5f, -10, -0.5f, 2.5f), ERIADOR(LOTRBiome.eriador, 900, 750, 10.0f, -15, -4.5f, -1.5f), MINHIRIATH(LOTRBiome.minhiriath, 850, 900, 3.0f, -40, -2.5f, 1.5f), HILLS_EVENDIM("hillsEvendim", 800, 630, 1.5f, -30, -2.5f, 2.5f), NORTH_DOWNS("northDowns", 930, 620, 1.0f, -25, -2.5f, 2.5f), SOUTH_DOWNS("southDowns", 965, 775, 1.0f, 0, -2.5f, 2.5f), LONE_LANDS(LOTRBiome.loneLands, 1080, 740, 3.0f, -5, -2.5f, 0.5f), THE_ANGLE(LOTRBiome.angle, 1110, 755, 1.0f, -50, -0.5f, 2.5f), WEATHER_HILLS("weatherHills", 985, 695, 1.0f, 30, -2.5f, 2.5f), TROLLSHAWS(LOTRBiome.trollshaws, 1130, 695, 1.0f, -5, -1.5f, 2.5f), ETTENMOORS(LOTRBiome.ettenmoors, 1085, 605, 2.0f, 25, -1.5f, 1.5f), COLDFELLS(LOTRBiome.coldfells, 1180, 640, 1.0f, -5, -1.5f, 2.5f), ANGMAR(LOTRBiome.angmar, 1080, 550, 4.0f, 17, -2.5f, 0.5f), MISTY_MOUNTAINS(LOTRBiome.mistyMountains, 1175, 830, 4.0f, -75, -3.5f, 0.5f), LINDON(LOTRBiome.lindon, 570, 750, 8.0f, 70, -5.5f, -0.5f), FORLINDON("forlindon", 535, 665, 2.0f, -5, -1.5f, 2.5f), HARLINDON("harlindon", 580, 825, 2.0f, 15, -1.5f, 2.5f), GULF_LUNE("gulfLune", 570, 760, 1.5f, -10, -0.5f, 2.5f), TOWER_HILLS(LOTRBiome.towerHills, 710, 750, 0.5f, 10, -0.5f, 2.5f), FAR_DOWNS("farDowns", 755, 738, 0.5f, 0, -0.5f, 2.5f), BLUE_MOUNTAINS(LOTRBiome.blueMountains, 625, 620, 3.0f, 80, -3.5f, 0.5f), EREGION(LOTRBiome.eregion, 1125, 845, 1.0f, -50, -2.5f, 2.5f), ENEDWAITH(LOTRBiome.enedwaith, 950, 1020, 4.0f, -50, -4.5f, 0.5f), DUNLAND(LOTRBiome.dunland, 1055, 990, 1.5f, -70, -1.5f, 1.5f), SWANFLEET(LOTRBiome.swanfleet, 1020, 875, 1.0f, 5, -1.5f, 2.5f), PUKEL(LOTRBiome.pukel, 920, 1180, 1.5f, -30, -2.5f, 1.5f), RHOVANION("rhovanion", 1490, 810, 10.0f, 40, -4.5f, -1.5f), VALES_ANDUIN(LOTRBiome.anduinVale, 1260, 775, 2.3f, -80, -2.5f, 0.5f), GLADDEN_FIELDS(LOTRBiome.gladdenFields, 1290, 775, 1.0f, -3, -0.5f, 2.5f), LOTHLORIEN(LOTRBiome.lothlorien, 1225, 930, 1.0f, 15, -2.5f, 1.5f), MIRKWOOD(LOTRBiome.mirkwoodCorrupted, 1400, 730, 3.0f, -2, -2.5f, 1.5f), WOODLAND_REALM(LOTRBiome.woodlandRealm, 1385, 620, 1.0f, -5, -1.5f, 2.5f), DALE(LOTRBiome.dale, 1560, 700, 4.0f, 10, -3.5f, 0.5f), GREY_MOUNTAINS(LOTRBiome.greyMountains, 1360, 565, 2.0f, 5, -3.5f, 0.5f), IRON_HILLS(LOTRBiome.ironHills, 1660, 600, 2.0f, -2, -3.5f, 0.5f), ROHAN(LOTRBiome.rohan, 1240, 1080, 5.0f, -10, -4.5f, -0.5f), WESTEMNET("westemnet", 1180, 1080, 1.5f, 10, -1.5f, 1.5f), EASTEMNET("eastemnet", 1290, 1100, 1.5f, -15, -1.5f, 1.5f), WOLD(LOTRBiome.wold, 1270, 1000, 1.5f, 10, -1.5f, 1.5f), WESTFOLD("westfold", 1165, 1110, 1.0f, 40, -1.5f, 2.5f), EASTFOLD("eastfold", 1250, 1175, 1.0f, 20, -1.5f, 2.5f), WESTMARCH("westmarch", 1060, 1115, 1.0f, 2, -1.5f, 2.5f), FANGORN(LOTRBiome.fangorn, 1190, 1000, 1.5f, 0, -2.5f, 2.5f), EMYN_MUIL(LOTRBiome.emynMuil, 1360, 1100, 1.5f, -12, -2.5f, 1.5f), NINDALF(LOTRBiome.nindalf, 1395, 1165, 1.0f, 0, -1.5f, 1.5f), DEAD_MARSHES(LOTRBiome.deadMarshes, 1420, 1125, 1.0f, 0, -1.5f, 1.5f), BROWN_LANDS(LOTRBiome.brownLands, 1480, 1050, 3.0f, 15, -2.5f, 0.5f), DAGORLAD(LOTRBiome.dagorlad, 1470, 1110, 1.5f, 5, -1.5f, 1.5f), GONDOR(LOTRBiome.gondor, 1150, 1250, 8.0f, 10, -4.5f, -0.5f), ANORIEN("anorien", 1370, 1205, 1.5f, 15, -2.5f, 2.5f), ITHILIEN(LOTRBiome.ithilien, 1440, 1320, 2.0f, -80, -2.5f, 2.5f), LEBENNIN(LOTRBiome.lebennin, 1360, 1320, 2.0f, -20, -2.5f, 2.5f), LOSSARNACH(LOTRBiome.lossarnach, 1405, 1265, 0.75f, -10, -1.5f, 2.5f), LAMEDON(LOTRBiome.lamedon, 1260, 1240, 1.5f, 5, -1.5f, 2.5f), BLACKROOT_VALE(LOTRBiome.blackrootVale, 1150, 1225, 1.0f, -25, -1.5f, 2.5f), PINNATH_GELIN(LOTRBiome.pinnathGelin, 1035, 1255, 1.5f, -10, -1.5f, 2.5f), DOR_EN_ERNIL(LOTRBiome.dorEnErnil, 1220, 1330, 1.5f, -30, -2.5f, 2.5f), ANFALAS("anfalas", 1045, 1320, 2.0f, -15, -2.5f, 1.5f), ANDRAST(LOTRBiome.andrast, 860, 1365, 1.5f, -25, -2.5f, 1.5f), HARONDOR(LOTRBiome.harondor, 1405, 1470, 4.0f, -5, -2.5f, 1.5f), BAY_BELFALAS("bayBelfalas", 1050, 1450, 4.0f, 0, -4.5f, 1.5f), WHITE_MOUNTAINS(LOTRBiome.whiteMountains, 1125, 1180, 3.0f, 5, -3.5f, 1.5f), MORDOR(LOTRBiome.mordor, 1650, 1280, 8.0f, -20, -4.5f, -0.5f), ASH_MOUNTAINS("ashMountains", 1640, 1160, 2.0f, -5, -2.5f, 0.5f), SHADOW_MOUNTAINS("shadowMountains", 1480, 1285, 2.0f, 85, -2.5f, 0.5f), GORGOROTH(LOTRBiome.gorgoroth, 1580, 1220, 2.0f, -15, -1.5f, 1.5f), NURN(LOTRBiome.nurn, 1660, 1310, 5.0f, -25, -0.5f, 1.5f), NURNEN(LOTRBiome.nurnen, 1710, 1345, 1.0f, -15, -1.5f, 2.5f), DORWINION(LOTRBiome.dorwinion, 1730, 890, 2.0f, -10, -2.5f, 2.5f), RHUN("rhun", 2400, 1150, 20.0f, -5, -3.5f, -2.5f), SEA_RHUN("rhunSea", 1830, 930, 1.5f, -5, -3.5f, 1.5f), PALISOR("palisor", 2120, 1020, 12.0f, 2, -3.5f, -1.0f), RHUN_LAND(LOTRBiome.rhunLand, 1870, 1010, 3.0f, -10, -2.0f, 0.5f), RHUN_RED_FOREST(LOTRBiome.rhunRedForest, 1880, 835, 1.25f, 10, -1.5f, 1.5f), WILD_WOOD("wildWood", 2450, 1120, 6.0f, 60, -2.5f, 0.5f), HARUNNOR("harunnor", 2610, 1760, 10.0f, -5, -3.5f, -0.5f), MOUNTAINS_WIND(LOTRBiome.windMountains, 2490, 1610, 4.0f, -10, -3.5f, 0.5f), LAST_DESERT(LOTRBiome.lastDesert, 2540, 1460, 4.0f, -20, -2.5f, 0.5f), UTTER_EAST("utterEast", 2740, 1000, 10.0f, 60, -3.5f, -1.0f), OROCARNI(LOTRBiome.redMountains, 2420, 870, 6.0f, 60, -3.5f, -0.5f), NEAR_HARAD("nearHarad", 1590, 1685, 8.0f, -2, -4.5f, -0.5f), HARNENNOR(LOTRBiome.harnedor, 1440, 1580, 3.0f, -3, -2.5f, 1.5f), LITHNEN("lithnen", 1615, 1615, 1.5f, -15, -1.5f, 1.5f), UMBAR(LOTRBiome.umbar, 1160, 1685, 4.0f, -15, -3.5f, 1.5f), LOSTLADEN("lostladen", 1685, 1470, 2.0f, -5, -2.5f, 1.5f), SOUTHRON_COASTS(LOTRBiome.nearHaradFertile, 1125, 1790, 3.0f, -20, -2.5f, 0.5f), GULF_HARAD(LOTRBiome.gulfHarad, 1930, 2165, 4.0f, 40, -4.5f, 1.5f), KHAND("khand", 1940, 1420, 4.0f, 5, -3.5f, 0.5f), FAR_HARAD("farHarad", 1350, 2500, 15.0f, 5, -5.5f, -1.5f), FAR_HARAD_HILLS("farHaradHills", 1200, 2050, 3.0f, 25, -2.5f, 0.5f), GREAT_PLAINS("greatPlains", 1370, 2280, 7.5f, 2, -2.5f, -0.5f), FORESTS_SOUTH("forestsSouth", 1300, 3000, 6.0f, 2, -2.5f, -0.5f), HARAD_MOUNTAINS(LOTRBiome.haradMountains, 850, 2280, 5.0f, 80, -2.5f, 0.5f), PERDOROGWAITH(LOTRBiome.pertorogwaith, 1920, 2560, 5.0f, -10, -2.5f, 0.5f), FOREST_TROLLS(LOTRBiome.halfTrollForest, 1740, 2530, 2.0f, 5, -2.0f, 1.5f);

	public int posX;
	public int posY;
	public float scale;
	public int angle;
	public float minZoom;
	public float maxZoom;
	public LOTRBiome biome;
	public String labelName;

	LOTRMapLabels(Object label, int x, int y, float f, int r, float z1, float z2) {
		posX = x;
		posY = y;
		scale = f;
		angle = r;
		minZoom = z1;
		maxZoom = z2;
		if (label instanceof LOTRBiome) {
			biome = (LOTRBiome) label;
		} else {
			labelName = (String) label;
		}
	}

	public String getDisplayName() {
		if (labelName != null) {
			return StatCollector.translateToLocal("lotr.map." + labelName);
		}
		return biome.getBiomeDisplayName();
	}

	public static LOTRMapLabels[] allMapLabels() {
		return values();
	}
}
