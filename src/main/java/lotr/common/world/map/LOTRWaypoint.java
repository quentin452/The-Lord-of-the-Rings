package lotr.common.world.map;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.*;

public enum LOTRWaypoint implements LOTRAbstractWaypoint {
	HIMLING(Region.OCEAN, LOTRFaction.UNALIGNED, 485.0, 523.0), TOL_FUIN(Region.OCEAN, LOTRFaction.UNALIGNED, 357.0, 542.0), TOL_MORWEN(Region.OCEAN, LOTRFaction.UNALIGNED, 87.0, 698.0), MENELTARMA_SUMMIT(Region.MENELTARMA, LOTRFaction.UNALIGNED, 64.0, 1733.0, true), HOBBITON(Region.SHIRE, LOTRFaction.HOBBIT, 815.0, 727.0), BRANDYWINE_BRIDGE(Region.SHIRE, LOTRFaction.UNALIGNED, 853.0, 725.0), SARN_FORD(Region.SHIRE, LOTRFaction.UNALIGNED, 883.0, 802.0), LONGBOTTOM(Region.SHIRE, LOTRFaction.HOBBIT, 820.0, 765.0), MICHEL_DELVING(Region.SHIRE, LOTRFaction.HOBBIT, 796.0, 739.0), WILLOWBOTTOM(Region.SHIRE, LOTRFaction.HOBBIT, 845.0, 752.0), BUCKLEBURY(Region.SHIRE, LOTRFaction.HOBBIT, 857.0, 734.0), WHITFURROWS(Region.SHIRE, LOTRFaction.HOBBIT, 843.0, 727.0), FROGMORTON(Region.SHIRE, LOTRFaction.HOBBIT, 831.0, 728.0), OATBARTON(Region.SHIRE, LOTRFaction.HOBBIT, 822.0, 701.0), SCARY(Region.SHIRE, LOTRFaction.HOBBIT, 840.0, 713.0), NEEDLEHOLE(Region.SHIRE, LOTRFaction.HOBBIT, 806.0, 708.0), LITTLE_DELVING(Region.SHIRE, LOTRFaction.HOBBIT, 785.0, 718.0), WAYMEET(Region.SHIRE, LOTRFaction.UNALIGNED, 807.0, 733.0), TUCKBOROUGH(Region.SHIRE, LOTRFaction.HOBBIT, 815.0, 741.0), NOBOTTLE(Region.SHIRE, LOTRFaction.HOBBIT, 797.0, 710.0), TIGHFIELD(Region.SHIRE, LOTRFaction.HOBBIT, 778.0, 712.0), BROCKENBORINGS(Region.SHIRE, LOTRFaction.HOBBIT, 833.0, 710.0), DEEPHALLOW(Region.SHIRE, LOTRFaction.HOBBIT, 850.0, 749.0), STOCK(Region.SHIRE, LOTRFaction.HOBBIT, 849.0, 737.0), BYWATER(Region.SHIRE, LOTRFaction.HOBBIT, 820.0, 730.0), OVERHILL(Region.SHIRE, LOTRFaction.HOBBIT, 817.0, 720.0), HAYSEND(Region.SHIRE, LOTRFaction.HOBBIT, 858.0, 747.0), HAY_GATE(Region.SHIRE, LOTRFaction.HOBBIT, 856.0, 728.0), GREENHOLM(Region.SHIRE, LOTRFaction.HOBBIT, 762.0, 745.0), WITHYWINDLE_VALLEY(Region.OLD_FOREST, LOTRFaction.UNALIGNED, 881.0, 749.0), FORLOND(Region.LINDON, LOTRFaction.HIGH_ELF, 526.0, 718.0), HARLOND(Region.LINDON, LOTRFaction.HIGH_ELF, 605.0, 783.0), MITHLOND_NORTH(Region.LINDON, LOTRFaction.HIGH_ELF, 669.0, 717.0), MITHLOND_SOUTH(Region.LINDON, LOTRFaction.HIGH_ELF, 679.0, 729.0), FORLINDON(Region.LINDON, LOTRFaction.HIGH_ELF, 532.0, 638.0), HARLINDON(Region.LINDON, LOTRFaction.HIGH_ELF, 611.0, 878.0), TOWER_HILLS(Region.LINDON, LOTRFaction.HIGH_ELF, 710.0, 742.0), AMON_EREB(Region.LINDON, LOTRFaction.HIGH_ELF, 500.0, 708.0), BELEGOST(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 622.0, 600.0), NOGROD(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 626.0, 636.0), MOUNT_RERIR(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 592.0, 525.0), MOUNT_DOLMED(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 599.0, 627.0), THORIN_HALLS(Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 641.0, 671.0), ARVEDUI_MINES(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 663.0, 489.0), THRAIN_HALLS(Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 669.0, 793.0), NORTH_DOWNS(Region.ERIADOR, LOTRFaction.UNALIGNED, 930.0, 626.0), SOUTH_DOWNS(Region.ERIADOR, LOTRFaction.UNALIGNED, 960.0, 768.0), ERYN_VORN(Region.ERIADOR, LOTRFaction.UNALIGNED, 747.0, 957.0), THARBAD(Region.ERIADOR, LOTRFaction.UNALIGNED, 979.0, 878.0), FORNOST(Region.ERIADOR, LOTRFaction.UNALIGNED, 897.0, 652.0), ANNUMINAS(Region.ERIADOR, LOTRFaction.UNALIGNED, 814.0, 661.0), GREENWAY_CROSSROADS(Region.ERIADOR, LOTRFaction.UNALIGNED, 920.0, 810.0), BREE(Region.BREE_LAND, LOTRFaction.BREE, 915.0, 734.0), STADDLE(Region.BREE_LAND, LOTRFaction.UNALIGNED, 924.0, 734.0), COMBE(Region.BREE_LAND, LOTRFaction.UNALIGNED, 927.0, 731.0), ARCHET(Region.BREE_LAND, LOTRFaction.UNALIGNED, 928.0, 728.0), FORSAKEN_INN(Region.BREE_LAND, LOTRFaction.UNALIGNED, 950.0, 743.0), WEATHERTOP(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 998.0, 723.0), LAST_BRIDGE(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1088.0, 714.0), OLD_ELF_WAY(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1028.0, 847.0), RIVENDELL(Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1173.0, 721.0), FORD_BRUINEN(Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1163.0, 723.0), THE_TROLLSHAWS(Region.TROLLSHAWS, LOTRFaction.UNALIGNED, 1130.0, 703.0), CARN_DUM(Region.ANGMAR, LOTRFaction.ANGMAR, 1010.0, 503.0), WEST_GATE(Region.EREGION, LOTRFaction.UNALIGNED, 1134.0, 873.0), OST_IN_EDHIL(Region.EREGION, LOTRFaction.UNALIGNED, 1112.0, 870.0), NORTH_DUNLAND(Region.DUNLAND, LOTRFaction.DUNLAND, 1073.0, 946.0), SOUTH_DUNLAND(Region.DUNLAND, LOTRFaction.DUNLAND, 1070.0, 1027.0), FORDS_OF_ISEN(Region.DUNLAND, LOTRFaction.UNALIGNED, 1102.0, 1087.0), DWARROWVALE(Region.DUNLAND, LOTRFaction.UNALIGNED, 1080.0, 990.0), WULFBURG(Region.DUNLAND, LOTRFaction.UNALIGNED, 1077.0, 1098.0), LOND_DAER(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 867.0, 1004.0), ENEDWAITH_ROAD(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 1025.0, 1050.0), MOUTHS_ISEN(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 871.0, 1127.0), ISENGARD(Region.NAN_CURUNIR, LOTRFaction.ISENGARD, 1102.0, 1061.5), CAPE_OF_FOROCHEL(Region.FORODWAITH, LOTRFaction.UNALIGNED, 786.0, 390.0), SOUTH_FOROCHEL(Region.FORODWAITH, LOTRFaction.UNALIGNED, 825.0, 459.0), WITHERED_HEATH(Region.FORODWAITH, LOTRFaction.UNALIGNED, 1441.0, 556.0), MOUNT_GUNDABAD(Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1195.0, 592.0), MOUNT_GRAM(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1106.0, 589.0), HIGH_PASS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1222.0, 706.0), MOUNT_CARADHRAS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1166.0, 845.0), MOUNT_CELEBDIL(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1158.0, 864.0), MOUNT_FANUIDHOL(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1191.0, 854.0), MOUNT_METHEDRAS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1111.0, 1031.0), GOBLIN_TOWN(Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1220.0, 696.0), EAGLES_EYRIE(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1246.0, 685.0), DAINS_HALLS(Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1262.0, 554.0), SCATHA(Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1335.0, 562.0), CARROCK(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281.0, 681.0), OLD_FORD(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1284.0, 702.0), GLADDEN_FIELDS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1294.0, 790.0), DIMRILL_DALE(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1177.0, 864.0), FIELD_OF_CELEBRANT(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281.0, 960.0), RAUROS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1345.0, 1130.0), BEORN(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1302.0, 680.0), FOREST_GATE(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1303.0, 655.0), FRAMSBURG(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1251.0, 590.0), ANDUIN_CROSSROADS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1285.0, 905.0), EAST_RHOVANION_ROAD(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1354.0, 966.0), THRANDUIL_HALLS(Region.WOODLAND_REALM, LOTRFaction.WOOD_ELF, 1420.0, 633.0), DOL_GULDUR(Region.MIRKWOOD, LOTRFaction.DOL_GULDUR, 1339.0, 894.0), MIRKWOOD_MOUNTAINS(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1430.0, 672.0), RHOSGOBEL(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1343.0, 762.0), ENCHANTED_RIVER(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1396.0, 650.0), RIVER_GATE(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1474.0, 696.0), EAST_BIGHT(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1437.0, 824.0), OLD_RHOVANION(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1524.0, 870.0), DORWINION_CROSSROADS(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1680.0, 882.0), EREBOR(Region.DALE, LOTRFaction.DURINS_FOLK, 1463.0, 609.0), DALE_CITY(Region.DALE, LOTRFaction.DALE, 1464.0, 615.0), LONG_LAKE(Region.DALE, LOTRFaction.DALE, 1461.0, 632.0), RUNNING_FORD(Region.DALE, LOTRFaction.UNALIGNED, 1534.0, 749.0), REDWATER_FORD(Region.DALE, LOTRFaction.UNALIGNED, 1651.0, 690.0), DALE_CROSSROADS(Region.DALE, LOTRFaction.UNALIGNED, 1567.0, 680.0), DALE_PORT(Region.DALE, LOTRFaction.UNALIGNED, 1657.0, 768.0), WEST_PEAK(Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1588.0, 608.0), EAST_PEAK(Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1729.0, 610.0), CARAS_GALADHON(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1242.0, 902.0), CERIN_AMROTH(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1230.0, 897.0), NIMRODEL(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1198.0, 894.0), DERNDINGLE(Region.FANGORN, LOTRFaction.FANGORN, 1163.0, 1030.0), WELLINGHALL(Region.FANGORN, LOTRFaction.FANGORN, 1153.0, 1014.0), TREEBEARD_HILL(Region.FANGORN, LOTRFaction.FANGORN, 1200.0, 1030.0), WOLD(Region.ROHAN, LOTRFaction.UNALIGNED, 1285.0, 1015.0), EDORAS(Region.ROHAN, LOTRFaction.ROHAN, 1190.0, 1148.0), HELMS_DEEP(Region.ROHAN, LOTRFaction.UNALIGNED, 1128.0, 1115.0), HELMS_CROSSROADS(Region.ROHAN, LOTRFaction.UNALIGNED, 1136.0, 1108.0), URUK_HIGHLANDS(Region.ROHAN, LOTRFaction.ISENGARD, 1131.0, 1057.0), MERING_STREAM(Region.ROHAN, LOTRFaction.UNALIGNED, 1299.0, 1202.0), ENTWADE(Region.ROHAN, LOTRFaction.UNALIGNED, 1239.0, 1104.0), EASTMARK(Region.ROHAN, LOTRFaction.UNALIGNED, 1286.0, 1130.0), ALDBURG(Region.ROHAN, LOTRFaction.UNALIGNED, 1223.0, 1178.0), GRIMSLADE(Region.ROHAN, LOTRFaction.UNALIGNED, 1153.0, 1122.0), CORSAIRS_LANDING(Region.ROHAN, LOTRFaction.UNALIGNED, 992.0, 1113.0), FRECA_HOLD(Region.ROHAN, LOTRFaction.UNALIGNED, 1099.0, 1144.0), DUNHARROW(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1175.0, 1154.0), TARLANG(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1205.0, 1213.0), RAS_MORTHIL(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 845.0, 1332.0), MINAS_TIRITH(Region.GONDOR, LOTRFaction.GONDOR, 1419.0, 1247.0), CAIR_ANDROS(Region.GONDOR, LOTRFaction.GONDOR, 1427.0, 1207.0), HALIFIRIEN(Region.GONDOR, LOTRFaction.UNALIGNED, 1309.0, 1205.0), CALENHAD(Region.GONDOR, LOTRFaction.UNALIGNED, 1330.0, 1212.0), MINRIMMON(Region.GONDOR, LOTRFaction.UNALIGNED, 1350.0, 1219.0), ERELAS(Region.GONDOR, LOTRFaction.UNALIGNED, 1367.0, 1222.0), NARDOL(Region.GONDOR, LOTRFaction.UNALIGNED, 1384.0, 1228.0), EILENACH(Region.GONDOR, LOTRFaction.UNALIGNED, 1402.0, 1228.0), AMON_DIN(Region.GONDOR, LOTRFaction.UNALIGNED, 1416.0, 1231.0),
	OSGILIATH_WEST(Region.GONDOR, LOTRFaction.UNALIGNED, 1428.0, 1246.0), OSGILIATH_EAST(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1435.0, 1246.0), EMYN_ARNEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1437.0, 1267.0), HENNETH_ANNUN(Region.ITHILIEN, LOTRFaction.GONDOR, 1443.0, 1192.0), CROSSROADS_ITHILIEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1450.0, 1236.0), NORTH_ITHILIEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1447.0, 1151.0), CROSSINGS_OF_POROS(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1442.0, 1370.0), PELARGIR(Region.LEBENNIN, LOTRFaction.GONDOR, 1390.0, 1348.0), LINHIR(Region.LEBENNIN, LOTRFaction.UNALIGNED, 1292.0, 1342.0), ANDUIN_MOUTH(Region.LEBENNIN, LOTRFaction.UNALIGNED, 1273.0, 1369.0), IMLOTH_MELUI(Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1397.0, 1254.0), CROSSINGS_ERUI(Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1412.0, 1272.0), CALEMBEL(Region.LAMEDON, LOTRFaction.GONDOR, 1235.0, 1248.0), ETHRING(Region.LAMEDON, LOTRFaction.UNALIGNED, 1256.0, 1259.0), ERECH(Region.BLACKROOT_VALE, LOTRFaction.GONDOR, 1186.0, 1205.0), GREEN_HILLS(Region.PINNATH_GELIN, LOTRFaction.UNALIGNED, 1045.0, 1273.0), DOL_AMROTH(Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1158.0, 1333.0), EDHELLOND(Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1189.0, 1293.0), TARNOST(Region.DOR_EN_ERNIL, LOTRFaction.UNALIGNED, 1241.0, 1300.0), TOLFALAS_ISLAND(Region.TOLFALAS, LOTRFaction.UNALIGNED, 1240.0, 1414.0), AMON_HEN(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1335.0, 1131.0), AMON_LHAW(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1372.0, 1120.0), ARGONATH(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1347.0, 1112.0), NORTH_UNDEEP(Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1319.0, 988.0), SOUTH_UNDEEP(Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1335.0, 1024.0), MORANNON(Region.DAGORLAD, LOTRFaction.UNALIGNED, 1470.0, 1131.0), UDUN(Region.MORDOR, LOTRFaction.MORDOR, 1470.0, 1145.0), MOUNT_DOOM(Region.MORDOR, LOTRFaction.MORDOR, 1533.0, 1204.0), BARAD_DUR(Region.MORDOR, LOTRFaction.MORDOR, 1573.0, 1196.0), MINAS_MORGUL(Region.MORDOR, LOTRFaction.MORDOR, 1461.0, 1239.0), DURTHANG(Region.MORDOR, LOTRFaction.MORDOR, 1464.0, 1159.0), CARACH_ANGREN(Region.MORDOR, LOTRFaction.MORDOR, 1493.0, 1166.0), CIRITH_UNGOL(Region.MORDOR, LOTRFaction.MORDOR, 1479.0, 1225.0), MORIGOST(Region.MORDOR, LOTRFaction.MORDOR, 1558.0, 1286.0), NARGROTH(Region.MORDOR, LOTRFaction.MORDOR, 1640.0, 1248.0), AMON_ANGREN(Region.MORDOR, LOTRFaction.MORDOR, 1663.0, 1245.0), SEREGOST(Region.MORDOR, LOTRFaction.MORDOR, 1682.0, 1214.0), FELLBEASTS(Region.MORDOR, LOTRFaction.MORDOR, 1754.0, 1164.0), EASTERN_GUARD(Region.MORDOR, LOTRFaction.MORDOR, 1840.0, 1137.0), NURNEN_NORTHERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1696.0, 1324.0), NURNEN_SOUTHERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1718.0, 1369.0), NURNEN_WESTERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1650.0, 1363.0), NURNEN_EASTERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1758.0, 1316.0), THAURBAND(Region.NURN, LOTRFaction.MORDOR, 1643.0, 1354.0), VALLEY_OF_SPIDERS(Region.NAN_UNGOL, LOTRFaction.MORDOR, 1512.0, 1400.0), DORWINION_PORT(Region.DORWINION, LOTRFaction.UNALIGNED, 1784.0, 863.0), DORWINION_COURT(Region.DORWINION, LOTRFaction.DORWINION, 1758.0, 939.0), DORWINION_FORD(Region.DORWINION, LOTRFaction.UNALIGNED, 1776.0, 986.0), DORWINION_HILLS(Region.DORWINION, LOTRFaction.UNALIGNED, 1733.0, 950.0), RHUN_ROAD_WAY(Region.RHUN, LOTRFaction.UNALIGNED, 2228.0, 835.0), BALCARAS(Region.RHUN, LOTRFaction.UNALIGNED, 2231.0, 1058.0), KHAND_NORTH_ROAD(Region.RHUN, LOTRFaction.UNALIGNED, 1932.0, 1331.0), RHUN_CAPITAL(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1867.0, 984.0), KHAMUL_TOWER(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1888.0, 878.0), MORDOR_FORD(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1834.0, 1112.0), RHUN_NORTH_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1903.0, 914.0), BAZYLAN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1921.0, 889.0), BORDER_TOWN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1794.0, 979.0), RHUN_SEA_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1837.0, 956.0), RHUN_NORTH_FORD(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1942.0, 811.0), RHUN_NORTHEAST(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 2045.0, 815.0), RHUN_SOUTH_PASS(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1869.0, 1055.0), RHUN_EAST_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 2010.0, 962.0), RHUN_EAST_TOWN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1983.0, 936.0), RHUN_SOUTHEAST(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1900.0, 1141.0), BARAZ_DUM(Region.RED_MOUNTAINS, LOTRFaction.UNALIGNED, 2326.0, 800.0), CROSSINGS_OF_HARAD(Region.HARONDOR, LOTRFaction.UNALIGNED, 1503.0, 1544.0), HARNEN_SEA_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1343.0, 1561.0), HARNEN_ROAD_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1518.0, 1563.0), HARNEN_BLACK_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1566.0, 1482.0), CROSSINGS_OF_LITHNEN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1539.0, 1545.0), HARNEN_RIVER_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1447.0, 1558.0), KHAND_FORD(Region.LOSTLADEN, LOTRFaction.UNALIGNED, 1778.0, 1432.0), UMBAR_CITY(Region.UMBAR, LOTRFaction.NEAR_HARAD, 1214.0, 1689.0), UMBAR_GATE(Region.UMBAR, LOTRFaction.UNALIGNED, 1252.0, 1698.0), GATE_HERUMOR(Region.UMBAR, LOTRFaction.NEAR_HARAD, 1097.0, 1721.0), CEDAR_ROAD(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1034.0, 1848.0), FERTILE_VALLEY(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1169.0, 1821.0), GARDENS_BERUTHIEL(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245.0, 1781.0), AIN_AL_HARAD(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1265.0, 1737.0), GATE_FUINUR(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1218.0, 1631.0), COAST_FORTRESS(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245.0, 1582.0), SANDHILL_TOWN(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1277.0, 1600.0), COAST_RIVER_TOWN(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1080.0, 1837.0), COAST_CITY(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1165.0, 1742.0), DESERT_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1563.0, 1611.0), SOUTH_DESERT_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1141.0, 1976.0), DESERT_RIVER_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1191.0, 1984.0), GULF_OF_HARAD(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1724.0, 1982.0), GULF_CITY(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1640.0, 1922.0), GULF_FORD(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1686.0, 2032.0), GULF_TRADE_TOWN(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1692.0, 2001.0), GULF_NORTH_TOWN(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1626.0, 1874.0), GULF_EAST_BAY(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 2036.0, 2081.0), GULF_EAST_PORT(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1847.0, 2049.0), MOUNT_SAND(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 959.0, 1899.0), MOUNT_GREEN(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 884.0, 2372.0), MOUNT_THUNDER(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 1019.0, 2590.0), GREAT_PLAINS_NORTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1308.0, 2067.0), GREAT_PLAINS_SOUTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1462.0, 2452.0), GREAT_PLAINS_WEST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1048.0, 2215.0), GREAT_PLAINS_EAST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1637.0, 2176.0), GREEN_VALLEY(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1557.0, 2622.0), HARAD_LAKES(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1774.0, 2310.0), LAKE_HARAD(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1100.0, 2592.0), HARADUIN_MOUTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1846.0, 2838.0), ISLE_MIST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1533.0, 3573.0), TAURELONDE(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 901.0, 2613.0), HARAD_HORN(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1105.0, 3778.0), TOWN_BONES(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1832.0, 2188.0), HARADUIN_BRIDGE(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1621.0, 2673.0), JUNGLE_CITY_TRADE(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 952.0, 2656.0), JUNGLE_CITY_OLD(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1084.0, 2670.0), JUNGLE_CITY_NORTH(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1419.0, 2604.0), JUNGLE_CITY_EAST(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1594.0, 2766.0), JUNGLE_CITY_CAPITAL(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1380.0, 2861.0), JUNGLE_CITY_DEEP(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1184.0, 3237.0), JUNGLE_CITY_WATCH(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1590.0, 2940.0), JUNGLE_CITY_CAVES(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1257.0, 3054.0), JUNGLE_CITY_STONE(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1236.0, 2787.0), JUNGLE_LAKES(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1550.0, 2856.0), TROLL_ISLAND(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1966.0, 2342.0), BLACK_COAST(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1936.0, 2496.0), BLOOD_RIVER(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1897.0, 2605.0), SHADOW_POINT(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1952.0, 2863.0), OLD_JUNGLE_RUIN(Region.PERTOROGWAITH_FOREST, LOTRFaction.UNALIGNED, 1834.0, 2523.0);

	public Region region;
	public LOTRFaction faction;
	public double imgX;
	public double imgY;
	public int xCoord;
	public int zCoord;
	public boolean isHidden;

	LOTRWaypoint(Region r, LOTRFaction f, double x, double y) {
		this(r, f, x, y, false);
	}

	LOTRWaypoint(Region r, LOTRFaction f, double x, double y, boolean hide) {
		region = r;
		region.waypoints.add(this);
		faction = f;
		imgX = x;
		imgY = y;
		xCoord = mapToWorldX(x);
		zCoord = mapToWorldZ(y);
		isHidden = hide;
	}

	public static List<LOTRAbstractWaypoint> listAllWaypoints() {
		return new ArrayList<>(Arrays.asList(values()));
	}

	public static int mapToWorldR(double r) {
		return (int) Math.round(r * LOTRGenLayerWorld.scale);
	}

	public static int mapToWorldX(double x) {
		return (int) Math.round((x - 810.0 + 0.5) * LOTRGenLayerWorld.scale);
	}

	public static int mapToWorldZ(double z) {
		return (int) Math.round((z - 730.0 + 0.5) * LOTRGenLayerWorld.scale);
	}

	public static Region regionForID(int id) {
		for (Region region : Region.values()) {
			if (region.ordinal() != id) {
				continue;
			}
			return region;
		}
		return null;
	}

	public static Region regionForName(String name) {
		for (Region region : Region.values()) {
			if (!region.name().equals(name)) {
				continue;
			}
			return region;
		}
		return null;
	}

	public static LOTRWaypoint waypointForName(String name) {
		for (LOTRWaypoint wp : values()) {
			if (!wp.getCodeName().equals(name)) {
				continue;
			}
			return wp;
		}
		return null;
	}

	public static int worldToMapR(double r) {
		return (int) Math.round(r / LOTRGenLayerWorld.scale);
	}

	public static int worldToMapX(double x) {
		return (int) Math.round(x / LOTRGenLayerWorld.scale - 0.5 + 810.0);
	}

	public static int worldToMapZ(double z) {
		return (int) Math.round(z / LOTRGenLayerWorld.scale - 0.5 + 730.0);
	}

	@Override
	public String getCodeName() {
		return name();
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("lotr.waypoint." + getCodeName());
	}

	@Override
	public int getID() {
		return ordinal();
	}

	@Override
	public LOTRAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
		if (hasPlayerUnlocked(entityplayer)) {
			return isUnlockedByConquest(entityplayer) ? LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED_CONQUEST : LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
		}
		return LOTRAbstractWaypoint.WaypointLockState.STANDARD_LOCKED;
	}

	@Override
	public String getLoreText(EntityPlayer entityplayer) {
		StringBuilder key = new StringBuilder("lotr.waypoint.").append(getCodeName()).append(".info");
		if (this == FERTILE_VALLEY && LOTRLevelData.clientside_thisServer_commemorateEmpressShamiir) {
			key.append(".shamiir");
		}
		return StatCollector.translateToLocal(key.toString());
	}

	@Override
	public double getX() {
		return imgX;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public double getY() {
		return imgY;
	}

	@Override
	public int getYCoord(World world, int i, int k) {
		return LOTRMod.getTrueTopBlock(world, i, k);
	}

	@Override
	public int getYCoordSaved() {
		return 64;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		if (pd.isFTRegionUnlocked(region)) {
			if (isCompatibleAlignment(entityplayer)) {
				return true;
			}
			if (isConquestUnlockable(entityplayer)) {
				return isConquered(entityplayer);
			}
		}
		return false;
	}

    @Override
    public boolean hasPlayerUnlockedProxy(EntityPlayer entityplayer, Map<LOTRFaction, List<LOTRConquestZone>> facConquestGrids) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (pd.isFTRegionUnlocked(region)) {
            if (isCompatibleAlignment(entityplayer)) {
                return true;
            }
            if (isConquestUnlockable(entityplayer)) {
                return isConqueredProxy(entityplayer, facConquestGrids);
            }
        }
        return false;
    }

	public boolean isCompatibleAlignment(EntityPlayer entityplayer) {
		if (faction == LOTRFaction.UNALIGNED) {
			return true;
		}
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		return pd.getAlignment(faction) >= 0.0f;
	}

    public boolean isConqueredProxy(EntityPlayer entityplayer, Map<LOTRFaction, List<LOTRConquestZone>> facConquestGrids) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        World world = entityplayer.worldObj;
        LOTRFaction pledgeFac = pd.getPledgeFaction();
        if (pledgeFac == null) return false;
        List<LOTRConquestZone> allZones = facConquestGrids.get(pledgeFac);
        if (allZones == null) {
            allZones = new ArrayList<>();
        }
        // Note: LOTRConquestGrid.getZoneByWorldCoords always returns a zone with conquest strength of 0.0f
        LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(xCoord, zCoord);
        for (LOTRConquestZone allOneZone : allZones) {
            if (allOneZone.gridX == zone.gridX && allOneZone.gridZ == zone.gridZ) {
                float strength = allOneZone.getConquestStrength(pledgeFac, world.getTotalWorldTime());
                if (strength >= 500.0f) return true;
            }
        }
        return false;
    }


    public boolean isConquered(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(xCoord, zCoord);
		LOTRFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && zone.getConquestStrength(pledgeFac, world) >= 500.0f;
	}

	public boolean isConquestUnlockable(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(xCoord, zCoord);
		LOTRFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && pledgeFac.isBadRelation(faction) && LOTRConquestGrid.getConquestEffectIn(world, zone, pledgeFac) == LOTRConquestGrid.ConquestEffective.EFFECTIVE;
	}

	@Override
	public boolean isHidden() {
		return isHidden;
	}

	public boolean isUnlockedByConquest(EntityPlayer entityplayer) {
		return !isCompatibleAlignment(entityplayer) && isConquestUnlockable(entityplayer) && isConquered(entityplayer);
	}

	public enum Region {
		OCEAN, MENELTARMA, SHIRE, OLD_FOREST, LINDON, BLUE_MOUNTAINS, ERIADOR, BREE_LAND, MIDGEWATER, LONE_LANDS, RIVENDELL_VALE, TROLLSHAWS, COLDFELLS, ETTENMOORS, ANGMAR, EREGION, DUNLAND, ENEDWAITH, NAN_CURUNIR, FORODWAITH, MISTY_MOUNTAINS, GREY_MOUNTAINS, VALES_OF_ANDUIN, WOODLAND_REALM, MIRKWOOD, WILDERLAND, DALE, IRON_HILLS, LOTHLORIEN, FANGORN, ROHAN, WHITE_MOUNTAINS, PUKEL, GONDOR, ITHILIEN, LEBENNIN, LOSSARNACH, LAMEDON, BLACKROOT_VALE, PINNATH_GELIN, DOR_EN_ERNIL, TOLFALAS, EMYN_MUIL, NINDALF, BROWN_LANDS, DAGORLAD, MORDOR, NURN, NAN_UNGOL, DORWINION, RHUN, RHUN_KHAGANATE, TOL_RHUNAER, RED_MOUNTAINS, HARONDOR, HARNEDOR, LOSTLADEN, UMBAR, SOUTHRON_COASTS, HARAD_DESERT, GULF_HARAD, HARAD_MOUNTAINS, FAR_HARAD, FAR_HARAD_JUNGLE, PERTOROGWAITH, PERTOROGWAITH_FOREST;

		public Collection<LOTRWaypoint> waypoints = new ArrayList<>();
	}

}
