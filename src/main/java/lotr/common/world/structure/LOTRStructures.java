package lotr.common.world.structure;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRConfig;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.mapgen.dwarvenmine.LOTRMapGenDwarvenMine;
import lotr.common.world.mapgen.tpyr.LOTRMapGenTauredainPyramid;
import lotr.common.world.structure2.*;
import lotr.common.world.village.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LOTRStructures {
	public static HashMap<Integer, IStructureProvider> idToClassMapping = new HashMap<>();
	public static HashMap<Integer, String> idToStringMapping = new HashMap<>();
	public static HashMap<Integer, StructureColorInfo> structureItemSpawners = new LinkedHashMap<>();

	public static String getNameFromID(int ID) {
		return idToStringMapping.get(ID);
	}

	public static int getRotationFromPlayer(EntityPlayer entityplayer) {
		return MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
	}

	public static IStructureProvider getStructureForID(int ID) {
		return idToClassMapping.get(ID);
	}

	public static void registerStructure(int id, Class<? extends WorldGenerator> strClass, String name, int colorBG, int colorFG) {
		registerStructure(id, strClass, name, colorBG, colorFG, false);
	}

	public static void registerStructure(int id, Class<? extends WorldGenerator> strClass, String name, int colorBG, int colorFG, boolean hide) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				WorldGenerator generator = null;
				try {
					generator = strClass.getConstructor(Boolean.TYPE).newInstance(true);
				} catch (Exception e) {
					FMLLog.warning("Failed to build LOTR structure " + strClass.getName());
					e.printStackTrace();
				}
				if (generator != null) {
					boolean timelapse = LOTRConfig.strTimelapse;
					if (generator instanceof LOTRWorldGenStructureBase2) {
						LOTRWorldGenStructureBase2 strGen = (LOTRWorldGenStructureBase2) generator;
						strGen.restrictions = false;
						strGen.usingPlayer = entityplayer;
						if (timelapse) {
							LOTRStructureTimelapse.start(strGen, world, i, j, k);
							return true;
						}
						return strGen.generateWithSetRotation(world, world.rand, i, j, k, strGen.usingPlayerRotation());
					}
					if (generator instanceof LOTRWorldGenStructureBase) {
						LOTRWorldGenStructureBase strGen = (LOTRWorldGenStructureBase) generator;
						strGen.restrictions = false;
						strGen.usingPlayer = entityplayer;
						if (timelapse) {
							LOTRStructureTimelapse.start(strGen, world, i, j, k);
							return true;
						}
						return strGen.generate(world, world.rand, i, j, k);
					}
				}
				return false;
			}

			@Override
			public boolean isVillage() {
				return false;
			}
		};
		registerStructure(id, strProvider, name, colorBG, colorFG, hide);
	}

	public static void registerStructure(int id, IStructureProvider str, String name, int colorBG, int colorFG, boolean hide) {
		if (idToClassMapping.containsKey(id)) {
			throw new IllegalArgumentException("Structure ID " + id + " is already registered to " + name + "!");
		}
		idToClassMapping.put(id, str);
		idToStringMapping.put(id, name);
		structureItemSpawners.put(id, new StructureColorInfo(id, colorBG, colorFG, str.isVillage(), hide));
	}

	public static void registerStructures() {
		registerStructure(1, LOTRWorldGenHobbitHole.class, "HobbitHole", 2727977, 8997164);
		registerStructure(2, LOTRWorldGenHobbitTavern.class, "HobbitTavern", 9324081, 15975807);
		registerStructure(3, LOTRWorldGenHobbitPicnicBench.class, "HobbitPicnicBench", 7032622, 13882323);
		registerStructure(4, LOTRWorldGenHobbitWindmill.class, "HobbitWindmill", 9324081, 15975807);
		registerStructure(5, LOTRWorldGenHobbitFarm.class, "HobbitFarm", 9324081, 15975807);
		registerStructure(6, LOTRWorldGenHayBales.class, "HayBale", 14863437, 11499334);
		registerStructure(7, LOTRWorldGenHobbitHouse.class, "HobbitHouse", 9324081, 15975807);
		registerStructure(8, LOTRWorldGenHobbitBurrow.class, "HobbitBurrow", 9324081, 15975807);
		registerStructure(20, LOTRWorldGenBreeHouse.class, "BreeHouse", 7366748, 13547379);
		registerStructure(21, LOTRWorldGenBreeOffice.class, "BreeOffice", 7366748, 13547379);
		registerStructure(22, LOTRWorldGenBreeSmithy.class, "BreeSmithy", 7895160, 13547379);
		registerStructure(23, LOTRWorldGenBreeInn.class, "BreeInn", 7366748, 13547379);
		registerStructure(24, LOTRWorldGenBreeWell.class, "BreeWell", 7366748, 13547379);
		registerStructure(25, LOTRWorldGenBreeLampPost.class, "BreeLampPost", 7366748, 13547379);
		registerStructure(26, LOTRWorldGenBreeBarn.class, "BreeBarn", 7366748, 13547379);
		registerStructure(27, LOTRWorldGenBreeRuffianHouse.class, "BreeRuffianHouse", 7366748, 13547379);
		registerStructure(28, LOTRWorldGenBreeStable.class, "BreeStables", 7366748, 13547379);
		registerStructure(29, LOTRWorldGenBreeGarden.class, "BreeGarden", 3056942, 9012349);
		registerStructure(30, LOTRWorldGenBreeHobbitBurrow.class, "BreeHobbitBurrow", 7366748, 13547379);
		registerStructure(31, LOTRWorldGenBreeMarketStall.Baker.class, "BreeMarketBaker", 16246393, 13547379);
		registerStructure(32, LOTRWorldGenBreeMarketStall.Butcher.class, "BreeMarketButcher", 14173509, 13547379);
		registerStructure(33, LOTRWorldGenBreeMarketStall.Brewer.class, "BreeMarketBrewer", 11368000, 13547379);
		registerStructure(34, LOTRWorldGenBreeMarketStall.Mason.class, "BreeMarketMason", 8948105, 13547379);
		registerStructure(35, LOTRWorldGenBreeMarketStall.Lumber.class, "BreeMarketLumber", 7160619, 13547379);
		registerStructure(36, LOTRWorldGenBreeMarketStall.Smith.class, "BreeMarketSmith", 5658198, 13547379);
		registerStructure(37, LOTRWorldGenBreeMarketStall.Florist.class, "BreeMarketFlorist", 10966702, 13547379);
		registerStructure(38, LOTRWorldGenBreeMarketStall.Farmer.class, "BreeMarketFarmer", 5137960, 13547379);
		registerStructure(39, LOTRWorldGenBreeMarket.class, "BreeMarket", 7366748, 13547379);
		registerVillage(40, new LOTRVillageGenBree(LOTRBiome.breeland, 1.0f), "BreeHamlet", 7366748, 13547379, (IVillageProperties<LOTRVillageGenBree.Instance>) instance -> instance.villageType = LOTRVillageGenBree.VillageType.HAMLET);
		registerVillage(41, new LOTRVillageGenBree(LOTRBiome.breeland, 1.0f), "BreeVillage", 7366748, 13547379, (IVillageProperties<LOTRVillageGenBree.Instance>) instance -> instance.villageType = LOTRVillageGenBree.VillageType.VILLAGE);
		registerStructure(42, LOTRWorldGenBreeGate.class, "BreeGate", 7366748, 13547379);
		registerStructure(43, LOTRWorldGenBreeGatehouse.class, "BreeGatehouse", 7366748, 13547379);
		registerStructure(50, LOTRWorldGenBlueMountainsHouse.class, "BlueMountainsHouse", 10397380, 7633815);
		registerStructure(51, LOTRWorldGenBlueMountainsStronghold.class, "BlueMountainsStronghold", 10397380, 7633815);
		registerStructure(52, LOTRWorldGenBlueMountainsSmithy.class, "BlueMountainsSmithy", 10397380, 7633815);
		registerStructure(60, LOTRWorldGenHighElvenTurret.class, "HighElvenTurret", 13419962, 11380637);
		registerStructure(61, LOTRWorldGenRuinedHighElvenTurret.class, "RuinedHighElvenTurret", 13419962, 11380637);
		registerStructure(62, LOTRWorldGenHighElvenHall.class, "HighElvenHall", 13419962, 11380637);
		registerStructure(63, LOTRWorldGenUnderwaterElvenRuin.class, "UnderwaterElvenRuin", 13419962, 11380637);
		registerStructure(64, LOTRWorldGenHighElvenForge.class, "HighElvenForge", 13419962, 11380637);
		registerStructure(65, LOTRWorldGenRuinedEregionForge.class, "RuinedEregionForge", 13419962, 11380637);
		registerStructure(66, LOTRWorldGenHighElvenTower.class, "HighElvenTower", 13419962, 11380637);
		registerStructure(67, LOTRWorldGenTowerHillsTower.class, "TowerHillsTower", 16250346, 14211019);
		registerStructure(68, LOTRWorldGenHighElfHouse.class, "HighElfHouse", 13419962, 11380637);
		registerStructure(69, LOTRWorldGenRivendellHouse.class, "RivendellHouse", 13419962, 11380637);
		registerStructure(70, LOTRWorldGenRivendellHall.class, "RivendellHall", 13419962, 11380637);
		registerStructure(71, LOTRWorldGenRivendellForge.class, "RivendellForge", 13419962, 11380637);
		registerStructure(80, LOTRWorldGenRuinedDunedainTower.class, "RuinedDunedainTower", 8947848, 6052956);
		registerStructure(81, LOTRWorldGenRuinedHouse.class, "RuinedHouse", 8355197, 6838845);
		registerStructure(82, LOTRWorldGenRangerTent.class, "RangerTent", 3755037, 4142111);
		registerStructure(83, LOTRWorldGenNumenorRuin.class, "NumenorRuin", 8947848, 6052956);
		registerStructure(84, LOTRWorldGenBDBarrow.class, "BDBarrow", 6586202, 6505786);
		registerStructure(85, LOTRWorldGenRangerWatchtower.class, "RangerWatchtower", 5982252, 13411436);
		registerStructure(86, LOTRWorldGenBurntHouse.class, "BurntHouse", 1117449, 3288357);
		registerStructure(87, LOTRWorldGenRottenHouse.class, "RottenHouse", 3026204, 5854007);
		registerStructure(88, LOTRWorldGenRangerHouse.class, "RangerHouse", 5982252, 13411436);
		registerStructure(89, LOTRWorldGenRangerLodge.class, "RangerLodge", 5982252, 13411436);
		registerStructure(90, LOTRWorldGenRangerStables.class, "RangerStables", 5982252, 13411436);
		registerStructure(91, LOTRWorldGenRangerSmithy.class, "RangerSmithy", 5982252, 13411436);
		registerStructure(92, LOTRWorldGenRangerWell.class, "RangerWell", 5982252, 13411436);
		registerStructure(93, LOTRWorldGenRangerVillageLight.class, "RangerVillageLight", 5982252, 13411436);
		registerVillage(94, new LOTRVillageGenDunedain(LOTRBiome.angle, 1.0f), "DunedainVillage", 5982252, 13411436, (IVillageProperties<LOTRVillageGenDunedain.Instance>) instance -> instance.villageType = LOTRVillageGenDunedain.VillageType.VILLAGE);
		registerStructure(95, LOTRWorldGenRangerCamp.class, "RangerCamp", 3755037, 4142111);
		registerStructure(120, LOTRWorldGenOrcDungeon.class, "OrcDungeon", 8947848, 6052956);
		registerStructure(121, LOTRWorldGenGundabadTent.class, "GundabadTent", 2301210, 131586);
		registerStructure(122, LOTRWorldGenGundabadForgeTent.class, "GundabadForgeTent", 2301210, 131586);
		registerStructure(123, LOTRWorldGenGundabadCamp.class, "GundabadCamp", 2301210, 131586);
		registerStructure(140, LOTRWorldGenAngmarTower.class, "AngmarTower", 3815994, 1644825);
		registerStructure(141, LOTRWorldGenAngmarShrine.class, "AngmarShrine", 3815994, 1644825);
		registerStructure(142, LOTRWorldGenAngmarWargPit.class, "AngmarWargPit", 3815994, 1644825);
		registerStructure(143, LOTRWorldGenAngmarTent.class, "AngmarTent", 2301210, 131586);
		registerStructure(144, LOTRWorldGenAngmarForgeTent.class, "AngmarForgeTent", 3815994, 1644825);
		registerStructure(145, LOTRWorldGenAngmarCamp.class, "AngmarCamp", 2301210, 131586);
		registerStructure(160, LOTRWorldGenAngmarHillmanHouse.class, "AngmarHillmanHouse", 6705465, 3813154);
		registerStructure(161, LOTRWorldGenAngmarHillmanChieftainHouse.class, "AngmarHillmanChieftainHouse", 6705465, 3813154);
		registerStructure(162, LOTRWorldGenRhudaurCastle.class, "RhudaurCastle", 3815994, 1644825);
		registerStructure(200, LOTRWorldGenWoodElfPlatform.class, "WoodElfLookoutPlatform", 2498840, 4932405);
		registerStructure(201, LOTRWorldGenWoodElfHouse.class, "WoodElfHouse", 2498840, 1004574);
		registerStructure(202, LOTRWorldGenWoodElfTower.class, "WoodElfTower", 12692892, 9733494);
		registerStructure(203, LOTRWorldGenRuinedWoodElfTower.class, "RuinedWoodElfTower", 12692892, 9733494);
		registerStructure(204, LOTRWorldGenWoodElvenForge.class, "WoodElvenForge", 12692892, 9733494);
		registerStructure(220, LOTRWorldGenDolGuldurAltar.class, "DolGuldurAltar", 4408654, 2040101);
		registerStructure(221, LOTRWorldGenDolGuldurTower.class, "DolGuldurTower", 4408654, 2040101);
		registerStructure(222, LOTRWorldGenDolGuldurSpiderPit.class, "DolGuldurSpiderPit", 4408654, 2040101);
		registerStructure(223, LOTRWorldGenDolGuldurTent.class, "DolGuldurTent", 2301210, 131586);
		registerStructure(224, LOTRWorldGenDolGuldurForgeTent.class, "DolGuldurForgeTent", 4408654, 2040101);
		registerStructure(225, LOTRWorldGenDolGuldurCamp.class, "DolGuldurCamp", 2301210, 131586);
		registerStructure(240, LOTRWorldGenDaleWatchtower.class, "DaleWatchtower", 13278568, 6836795);
		registerStructure(241, LOTRWorldGenDaleFortress.class, "DaleFortress", 13278568, 6836795);
		registerStructure(242, LOTRWorldGenDaleHouse.class, "DaleHouse", 13278568, 6836795);
		registerStructure(243, LOTRWorldGenDaleSmithy.class, "DaleSmithy", 13278568, 6836795);
		registerStructure(244, LOTRWorldGenDaleVillageTower.class, "DaleVillageTower", 13278568, 6836795);
		registerStructure(245, LOTRWorldGenDaleBakery.class, "DaleBakery", 13278568, 6836795);
		registerStructure(260, LOTRWorldGenDwarvenMineEntrance.class, "DwarvenMineEntrance", 4935761, 2961971);
		registerStructure(261, LOTRWorldGenDwarvenTower.class, "DwarvenTower", 4935761, 2961971);
		registerStructure(262, LOTRWorldGenDwarfHouse.class, "DwarfHouse", 4935761, 2961971);
		registerStructure(263, LOTRWorldGenDwarvenMineEntranceRuined.class, "DwarvenMineEntranceRuined", 4935761, 2961971);
		registerStructure(264, LOTRWorldGenDwarfSmithy.class, "DwarfSmithy", 4935761, 2961971);
		registerStructure(265, LOTRWorldGenRuinedDwarvenTower.class, "DwarvenTowerRuined", 4935761, 2961971);
		registerStructure(280, LOTRWorldGenElfHouse.class, "ElfHouse", 15325615, 2315809);
		registerStructure(281, LOTRWorldGenElfLordHouse.class, "ElfLordHouse", 15325615, 2315809);
		registerStructure(282, LOTRWorldGenGaladhrimForge.class, "GaladhrimForge", 14407118, 10854552);
		registerStructure(300, LOTRWorldGenMeadHall.class, "RohanMeadHall", 5982252, 13411436);
		registerStructure(301, LOTRWorldGenRohanWatchtower.class, "RohanWatchtower", 5982252, 13411436);
		registerStructure(302, LOTRWorldGenRohanBarrow.class, "RohanBarrow", 9016133, 16775901);
		registerStructure(303, LOTRWorldGenRohanFortress.class, "RohanFortress", 5982252, 13411436);
		registerStructure(304, LOTRWorldGenRohanHouse.class, "RohanHouse", 5982252, 13411436);
		registerStructure(305, LOTRWorldGenRohanSmithy.class, "RohanSmithy", 5982252, 13411436);
		registerStructure(306, LOTRWorldGenRohanVillageFarm.class, "RohanVillageFarm", 7648578, 8546111);
		registerStructure(307, LOTRWorldGenRohanStables.class, "RohanStables", 5982252, 13411436);
		registerStructure(308, LOTRWorldGenRohanBarn.class, "RohanBarn", 5982252, 13411436);
		registerStructure(309, LOTRWorldGenRohanWell.class, "RohanWell", 5982252, 13411436);
		registerStructure(310, LOTRWorldGenRohanVillageGarden.class, "RohanVillageGarden", 7648578, 8546111);
		registerStructure(311, LOTRWorldGenRohanMarketStall.Blacksmith.class, "RohanMarketBlacksmith", 2960684, 13411436);
		registerStructure(312, LOTRWorldGenRohanMarketStall.Farmer.class, "RohanMarketFarmer", 15066597, 13411436);
		registerStructure(313, LOTRWorldGenRohanMarketStall.Lumber.class, "RohanMarketLumber", 5981994, 13411436);
		registerStructure(314, LOTRWorldGenRohanMarketStall.Builder.class, "RohanMarketBuilder", 7693401, 13411436);
		registerStructure(315, LOTRWorldGenRohanMarketStall.Brewer.class, "RohanMarketBrewer", 13874218, 13411436);
		registerStructure(316, LOTRWorldGenRohanMarketStall.Butcher.class, "RohanMarketButcher", 16358066, 13411436);
		registerStructure(317, LOTRWorldGenRohanMarketStall.Fish.class, "RohanMarketFish", 9882879, 13411436);
		registerStructure(318, LOTRWorldGenRohanMarketStall.Baker.class, "RohanMarketBaker", 14725995, 13411436);
		registerStructure(319, LOTRWorldGenRohanMarketStall.Orcharder.class, "RohanMarketOrcharder", 9161006, 13411436);
		registerStructure(320, LOTRWorldGenRohanVillagePasture.class, "RohanVillagePasture", 7648578, 8546111);
		registerStructure(321, LOTRWorldGenRohanVillageSign.class, "RohanVillageSign", 5982252, 13411436);
		registerStructure(322, LOTRWorldGenRohanGatehouse.class, "RohanGatehouse", 5982252, 13411436);
		registerVillage(323, new LOTRVillageGenRohan(LOTRBiome.rohan, 1.0f), "RohanVillage", 5982252, 13411436, (IVillageProperties<LOTRVillageGenRohan.Instance>) instance -> instance.villageType = LOTRVillageGenRohan.VillageType.VILLAGE);
		registerVillage(324, new LOTRVillageGenRohan(LOTRBiome.rohan, 1.0f), "RohanFortVillage", 5982252, 13411436, (IVillageProperties<LOTRVillageGenRohan.Instance>) instance -> instance.villageType = LOTRVillageGenRohan.VillageType.FORT);
		registerStructure(350, LOTRWorldGenUrukTent.class, "UrukTent", 2301210, 131586);
		registerStructure(351, LOTRWorldGenRuinedRohanWatchtower.class, "RuinedRohanWatchtower", 1117449, 3288357);
		registerStructure(352, LOTRWorldGenUrukForgeTent.class, "UrukForgeTent", 3682596, 2038547);
		registerStructure(353, LOTRWorldGenUrukWargPit.class, "UrukWargPit", 3682596, 2038547);
		registerStructure(354, LOTRWorldGenUrukCamp.class, "UrukCamp", 2301210, 131586);
		registerStructure(380, LOTRWorldGenDunlendingHouse.class, "DunlendingHouse", 6705465, 3813154);
		registerStructure(381, LOTRWorldGenDunlendingTavern.class, "DunlendingTavern", 6705465, 3813154);
		registerStructure(382, LOTRWorldGenDunlendingCampfire.class, "DunlendingCampfire", 9539472, 6837299);
		registerStructure(383, LOTRWorldGenDunlandHillFort.class, "DunlandHillFort", 6705465, 3813154);
		registerStructure(400, LOTRWorldGenBeaconTower.class, "BeaconTower", 14869218, 11513775);
		registerStructure(401, LOTRWorldGenGondorWatchfort.class, "GondorWatchfort", 14869218, 2367263);
		registerStructure(402, LOTRWorldGenGondorSmithy.class, "GondorSmithy", 14869218, 2367263);
		registerStructure(403, LOTRWorldGenGondorTurret.class, "GondorTurret", 14869218, 11513775);
		registerStructure(404, LOTRWorldGenIthilienHideout.class, "IthilienHideout", 8882055, 7365464);
		registerStructure(405, LOTRWorldGenGondorHouse.class, "GondorHouse", 14869218, 9861961);
		registerStructure(406, LOTRWorldGenGondorCottage.class, "GondorCottage", 14869218, 9861961);
		registerStructure(407, LOTRWorldGenGondorStoneHouse.class, "GondorStoneHouse", 14869218, 2367263);
		registerStructure(408, LOTRWorldGenGondorWatchtower.class, "GondorWatchtower", 14869218, 11513775);
		registerStructure(409, LOTRWorldGenGondorStables.class, "GondorStables", 14869218, 9861961);
		registerStructure(410, LOTRWorldGenGondorBarn.class, "GondorBarn", 14869218, 9861961);
		registerStructure(411, LOTRWorldGenGondorFortress.class, "GondorFortress", 14869218, 2367263);
		registerStructure(412, LOTRWorldGenGondorTavern.class, "GondorTavern", 14869218, 9861961);
		registerStructure(413, LOTRWorldGenGondorWell.class, "GondorWell", 14869218, 11513775);
		registerStructure(414, LOTRWorldGenGondorVillageFarm.Crops.class, "GondorFarmCrops", 7047232, 15066597);
		registerStructure(415, LOTRWorldGenGondorVillageFarm.Animals.class, "GondorFarmAnimals", 7047232, 15066597);
		registerStructure(416, LOTRWorldGenGondorVillageFarm.Tree.class, "GondorFarmTree", 7047232, 15066597);
		registerStructure(417, LOTRWorldGenGondorMarketStall.Greengrocer.class, "GondorMarketGreengrocer", 8567851, 9861961);
		registerStructure(418, LOTRWorldGenGondorMarketStall.Lumber.class, "GondorMarketLumber", 5981994, 9861961);
		registerStructure(419, LOTRWorldGenGondorMarketStall.Mason.class, "GondorMarketMason", 10526621, 9861961);
		registerStructure(420, LOTRWorldGenGondorMarketStall.Brewer.class, "GondorMarketBrewer", 13874218, 9861961);
		registerStructure(421, LOTRWorldGenGondorMarketStall.Flowers.class, "GondorMarketFlowers", 16243515, 9861961);
		registerStructure(422, LOTRWorldGenGondorMarketStall.Butcher.class, "GondorMarketButcher", 14521508, 9861961);
		registerStructure(423, LOTRWorldGenGondorMarketStall.Fish.class, "GondorMarketFish", 6862591, 9861961);
		registerStructure(424, LOTRWorldGenGondorMarketStall.Farmer.class, "GondorMarketFarmer", 14401433, 9861961);
		registerStructure(425, LOTRWorldGenGondorMarketStall.Blacksmith.class, "GondorMarketBlacksmith", 2960684, 9861961);
		registerStructure(426, LOTRWorldGenGondorMarketStall.Baker.class, "GondorMarketBaker", 13543009, 9861961);
		registerStructure(427, LOTRWorldGenGondorVillageSign.class, "GondorVillageSign", 5982252, 13411436);
		registerStructure(428, LOTRWorldGenGondorBath.class, "GondorBath", 14869218, 2367263);
		registerStructure(429, LOTRWorldGenGondorGatehouse.class, "GondorGatehouse", 14869218, 2367263);
		registerStructure(430, LOTRWorldGenGondorLampPost.class, "GondorLampPost", 14869218, 11513775);
		registerStructure(431, LOTRWorldGenGondorTownGarden.class, "GondorTownGarden", 7047232, 15066597);
		registerStructure(432, LOTRWorldGenGondorTownTrees.class, "GondorTownTrees", 7047232, 15066597);
		registerStructure(433, LOTRWorldGenGondorTownBench.class, "GondorTownBench", 14869218, 11513775);
		registerVillage(434, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorVillage", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(435, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorTown", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(436, new LOTRVillageGenGondor(LOTRBiome.gondor, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f), "GondorFortVillage", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(450, LOTRWorldGenRuinedBeaconTower.class, "RuinedBeaconTower", 14869218, 11513775);
		registerStructure(451, LOTRWorldGenRuinedGondorTower.class, "RuinedGondorTower", 14869218, 11513775);
		registerStructure(452, LOTRWorldGenGondorObelisk.class, "GondorObelisk", 14869218, 11513775);
		registerStructure(453, LOTRWorldGenGondorRuin.class, "GondorRuin", 14869218, 11513775);
		registerStructure(500, LOTRWorldGenDolAmrothStables.class, "DolAmrothStables", 15002613, 2709918);
		registerStructure(501, LOTRWorldGenDolAmrothWatchtower.class, "DolAmrothWatchtower", 14869218, 11513775);
		registerStructure(502, LOTRWorldGenDolAmrothWatchfort.class, "DolAmrothWatchfort", 15002613, 2709918);
		registerVillage(503, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothVillage", 15002613, 2709918, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(504, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothTown", 15002613, 2709918, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(505, new LOTRVillageGenGondor(LOTRBiome.dorEnErnil, LOTRWorldGenGondorStructure.GondorFiefdom.DOL_AMROTH, 1.0f), "DolAmrothFortVillage", 15002613, 2709918, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(510, LOTRWorldGenLossarnachFortress.class, "LossarnachFortress", 14869218, 15138816);
		registerStructure(511, LOTRWorldGenLossarnachWatchtower.class, "LossarnachWatchtower", 14869218, 11513775);
		registerStructure(512, LOTRWorldGenLossarnachWatchfort.class, "LossarnachWatchfort", 14869218, 15138816);
		registerVillage(513, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachVillage", 14869218, 15138816, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(514, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachTown", 14869218, 15138816, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(515, new LOTRVillageGenGondor(LOTRBiome.lossarnach, LOTRWorldGenGondorStructure.GondorFiefdom.LOSSARNACH, 1.0f), "LossarnachFortVillage", 14869218, 15138816, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(520, LOTRWorldGenLebenninFortress.class, "LebenninFortress", 14869218, 621750);
		registerStructure(521, LOTRWorldGenLebenninWatchtower.class, "LebenninWatchtower", 14869218, 11513775);
		registerStructure(522, LOTRWorldGenLebenninWatchfort.class, "LebenninWatchfort", 14869218, 621750);
		registerVillage(523, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninVillage", 14869218, 621750, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(524, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninTown", 14869218, 621750, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(525, new LOTRVillageGenGondor(LOTRBiome.lebennin, LOTRWorldGenGondorStructure.GondorFiefdom.LEBENNIN, 1.0f), "LebenninFortVillage", 14869218, 621750, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(530, LOTRWorldGenPelargirFortress.class, "PelargirFortress", 14869218, 2917253);
		registerStructure(531, LOTRWorldGenPelargirWatchtower.class, "PelargirWatchtower", 14869218, 11513775);
		registerStructure(532, LOTRWorldGenPelargirWatchfort.class, "PelargirWatchfort", 14869218, 2917253);
		registerVillage(533, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirVillage", 14869218, 2917253, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(534, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirTown", 14869218, 2917253, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(535, new LOTRVillageGenGondor(LOTRBiome.pelargir, LOTRWorldGenGondorStructure.GondorFiefdom.PELARGIR, 1.0f), "PelargirFortVillage", 14869218, 2917253, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(540, LOTRWorldGenPinnathGelinFortress.class, "PinnathGelinFortress", 14869218, 1401651);
		registerStructure(541, LOTRWorldGenPinnathGelinWatchtower.class, "PinnathGelinWatchtower", 14869218, 11513775);
		registerStructure(542, LOTRWorldGenPinnathGelinWatchfort.class, "PinnathGelinWatchfort", 14869218, 1401651);
		registerVillage(543, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinVillage", 14869218, 1401651, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(544, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinTown", 14869218, 1401651, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(545, new LOTRVillageGenGondor(LOTRBiome.pinnathGelin, LOTRWorldGenGondorStructure.GondorFiefdom.PINNATH_GELIN, 1.0f), "PinnathGelinFortVillage", 14869218, 1401651, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(550, LOTRWorldGenBlackrootFortress.class, "BlackrootFortress", 14869218, 2367263);
		registerStructure(551, LOTRWorldGenBlackrootWatchtower.class, "BlackrootWatchtower", 14869218, 11513775);
		registerStructure(552, LOTRWorldGenBlackrootWatchfort.class, "BlackrootWatchfort", 14869218, 2367263);
		registerVillage(553, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootVillage", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(554, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootTown", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(555, new LOTRVillageGenGondor(LOTRBiome.blackrootVale, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 1.0f), "BlackrootFortVillage", 14869218, 2367263, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(560, LOTRWorldGenLamedonFortress.class, "LamedonFortress", 14869218, 1784649);
		registerStructure(561, LOTRWorldGenLamedonWatchtower.class, "LamedonWatchtower", 14869218, 11513775);
		registerStructure(562, LOTRWorldGenLamedonWatchfort.class, "LamedonWatchfort", 14869218, 1784649);
		registerVillage(563, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonVillage", 14869218, 1784649, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.VILLAGE);
		registerVillage(564, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonTown", 14869218, 1784649, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.TOWN);
		registerVillage(565, new LOTRVillageGenGondor(LOTRBiome.lamedon, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 1.0f), "LamedonFortVillage", 14869218, 1784649, (IVillageProperties<LOTRVillageGenGondor.Instance>) instance -> instance.villageType = LOTRVillageGenGondor.VillageType.FORT);
		registerStructure(600, LOTRWorldGenMordorTower.class, "MordorTower", 2631720, 328965);
		registerStructure(601, LOTRWorldGenMordorTent.class, "MordorTent", 2301210, 131586);
		registerStructure(602, LOTRWorldGenMordorForgeTent.class, "MordorForgeTent", 2631720, 328965);
		registerStructure(603, LOTRWorldGenMordorWargPit.class, "MordorWargPit", 2631720, 328965);
		registerStructure(604, LOTRWorldGenMordorCamp.class, "MordorCamp", 2301210, 131586);
		registerStructure(605, LOTRWorldGenBlackUrukFort.class, "BlackUrukFort", 2631720, 328965);
		registerStructure(650, LOTRWorldGenNurnWheatFarm.class, "NurnWheatFarm", 4469796, 328965);
		registerStructure(651, LOTRWorldGenOrcSlaverTower.class, "OrcSlaverTower", 1117449, 3288357);
		registerStructure(670, LOTRWorldGenMordorSpiderPit.class, "MordorSpiderPit", 1511181, 12917534);
		registerStructure(700, LOTRWorldGenDorwinionGarden.class, "DorwinionGarden", 16572875, 13418417);
		registerStructure(701, LOTRWorldGenDorwinionTent.class, "DorwinionTent", 6706573, 15058766);
		registerStructure(702, LOTRWorldGenDorwinionCaptainTent.class, "DorwinionCaptainTent", 6706573, 15058766);
		registerStructure(703, LOTRWorldGenDorwinionHouse.class, "DorwinionHouse", 7167128, 15390149);
		registerStructure(704, LOTRWorldGenDorwinionBrewery.class, "DorwinionBrewery", 7167128, 15390149);
		registerStructure(705, LOTRWorldGenDorwinionElfHouse.class, "DorwinionElfHouse", 7167128, 15390149);
		registerStructure(706, LOTRWorldGenDorwinionBath.class, "DorwinionBath", 7167128, 15390149);
		registerStructure(750, LOTRWorldGenEasterlingHouse.class, "EasterlingHouse", 12693373, 7689786);
		registerStructure(751, LOTRWorldGenEasterlingStables.class, "EasterlingStables", 12693373, 7689786);
		registerStructure(752, LOTRWorldGenEasterlingTownHouse.class, "EasterlingTownHouse", 6304287, 12693373);
		registerStructure(753, LOTRWorldGenEasterlingLargeTownHouse.class, "EasterlingLargeTownHouse", 6304287, 12693373);
		registerStructure(754, LOTRWorldGenEasterlingFortress.class, "EasterlingFortress", 6304287, 12693373);
		registerStructure(755, LOTRWorldGenEasterlingTower.class, "EasterlingTower", 6304287, 12693373);
		registerStructure(756, LOTRWorldGenEasterlingSmithy.class, "EasterlingSmithy", 6304287, 12693373);
		registerStructure(757, LOTRWorldGenEasterlingMarketStall.Blacksmith.class, "EasterlingMarketBlacksmith", 2960684, 12693373);
		registerStructure(758, LOTRWorldGenEasterlingMarketStall.Lumber.class, "EasterlingMarketLumber", 5981994, 12693373);
		registerStructure(759, LOTRWorldGenEasterlingMarketStall.Mason.class, "EasterlingMarketMason", 7039594, 12693373);
		registerStructure(760, LOTRWorldGenEasterlingMarketStall.Butcher.class, "EasterlingMarketButcher", 12544103, 12693373);
		registerStructure(761, LOTRWorldGenEasterlingMarketStall.Brewer.class, "EasterlingMarketBrewer", 11891243, 12693373);
		registerStructure(762, LOTRWorldGenEasterlingMarketStall.Fish.class, "EasterlingMarketFish", 4882395, 12693373);
		registerStructure(763, LOTRWorldGenEasterlingMarketStall.Baker.class, "EasterlingMarketBaker", 14725995, 12693373);
		registerStructure(764, LOTRWorldGenEasterlingMarketStall.Hunter.class, "EasterlingMarketHunter", 4471854, 12693373);
		registerStructure(765, LOTRWorldGenEasterlingMarketStall.Farmer.class, "EasterlingMarketFarmer", 8893759, 12693373);
		registerStructure(766, LOTRWorldGenEasterlingMarketStall.Gold.class, "EasterlingMarketGold", 16237060, 12693373);
		registerStructure(767, LOTRWorldGenEasterlingTavern.class, "EasterlingTavern", 12693373, 7689786);
		registerStructure(768, LOTRWorldGenEasterlingTavernTown.class, "EasterlingTavernTown", 6304287, 12693373);
		registerStructure(769, LOTRWorldGenEasterlingStatue.class, "EasterlingStatue", 12693373, 7689786);
		registerStructure(770, LOTRWorldGenEasterlingGarden.class, "EasterlingGarden", 4030994, 12693373);
		registerStructure(771, LOTRWorldGenEasterlingVillageSign.class, "EasterlingVillageSign", 12693373, 7689786);
		registerStructure(772, LOTRWorldGenEasterlingWell.class, "EasterlingWell", 12693373, 7689786);
		registerStructure(773, LOTRWorldGenEasterlingVillageFarm.Crops.class, "EasterlingFarmCrops", 4030994, 12693373);
		registerStructure(774, LOTRWorldGenEasterlingVillageFarm.Animals.class, "EasterlingFarmAnimals", 4030994, 12693373);
		registerStructure(775, LOTRWorldGenEasterlingVillageFarm.Tree.class, "EasterlingFarmTree", 4030994, 12693373);
		registerStructure(776, LOTRWorldGenEasterlingGatehouse.class, "EasterlingGatehouse", 6304287, 12693373);
		registerStructure(777, LOTRWorldGenEasterlingLamp.class, "EasterlingLamp", 6304287, 12693373);
		registerVillage(778, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingVillage", 6304287, 12693373, (IVillageProperties<LOTRVillageGenRhun.Instance>) instance -> instance.villageType = LOTRVillageGenRhun.VillageType.VILLAGE);
		registerVillage(779, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingTown", 6304287, 12693373, (IVillageProperties<LOTRVillageGenRhun.Instance>) instance -> instance.villageType = LOTRVillageGenRhun.VillageType.TOWN);
		registerVillage(780, new LOTRVillageGenRhun(LOTRBiome.rhunLand, 1.0f, true), "EasterlingFortVillage", 6304287, 12693373, (IVillageProperties<LOTRVillageGenRhun.Instance>) instance -> instance.villageType = LOTRVillageGenRhun.VillageType.FORT);
		registerStructure(1000, LOTRWorldGenHaradObelisk.class, "HaradObelisk", 10854007, 15590575);
		registerStructure(1001, LOTRWorldGenHaradPyramid.class, "HaradPyramid", 10854007, 15590575);
		registerStructure(1002, LOTRWorldGenMumakSkeleton.class, "MumakSkeleton", 14737111, 16250349);
		registerStructure(1003, LOTRWorldGenHaradRuinedFort.class, "HaradRuinedFort", 10854007, 15590575);
		registerStructure(1050, LOTRWorldGenHarnedorHouse.class, "HarnedorHouse", 4994339, 12814421);
		registerStructure(1051, LOTRWorldGenHarnedorSmithy.class, "HarnedorSmithy", 4994339, 12814421);
		registerStructure(1052, LOTRWorldGenHarnedorTavern.class, "HarnedorTavern", 4994339, 12814421);
		registerStructure(1053, LOTRWorldGenHarnedorMarket.class, "HarnedorMarket", 4994339, 12814421);
		registerStructure(1054, LOTRWorldGenHarnedorTower.class, "HarnedorTower", 4994339, 12814421);
		registerStructure(1055, LOTRWorldGenHarnedorFort.class, "HarnedorFort", 4994339, 12814421);
		registerStructure(1056, LOTRWorldGenNearHaradTent.class, "NearHaradTent", 13519170, 1775897);
		registerStructure(1057, LOTRWorldGenHarnedorFarm.class, "HarnedorFarm", 10073953, 12814421);
		registerStructure(1058, LOTRWorldGenHarnedorPasture.class, "HarnedorPasture", 10073953, 12814421);
		registerVillage(1059, new LOTRVillageGenHarnedor(LOTRBiome.harnedor, 1.0f), "HarnedorVillage", 4994339, 12814421, (IVillageProperties<LOTRVillageGenHarnedor.Instance>) instance -> instance.villageType = LOTRVillageGenHarnedor.VillageType.VILLAGE);
		registerStructure(1060, LOTRWorldGenHarnedorStables.class, "HarnedorStables", 4994339, 12814421);
		registerStructure(1061, LOTRWorldGenHarnedorVillageSign.class, "HarnedorVillageSign", 4994339, 12814421);
		registerVillage(1062, new LOTRVillageGenHarnedor(LOTRBiome.harnedor, 1.0f), "HarnedorFortVillage", 4994339, 12814421, (IVillageProperties<LOTRVillageGenHarnedor.Instance>) instance -> instance.villageType = LOTRVillageGenHarnedor.VillageType.FORTRESS);
		registerStructure(1080, LOTRWorldGenHarnedorHouseRuined.class, "HarnedorHouseRuined", 5519919, 10059372);
		registerStructure(1081, LOTRWorldGenHarnedorTavernRuined.class, "HarnedorTavernRuined", 5519919, 10059372);
		registerVillage(1082, new LOTRVillageGenHarnedor(LOTRBiome.harondor, 1.0f).setRuined(), "HarnedorVillageRuined", 5519919, 10059372, (IVillageProperties<LOTRVillageGenHarnedor.Instance>) instance -> instance.villageType = LOTRVillageGenHarnedor.VillageType.VILLAGE);
		registerStructure(1100, LOTRWorldGenSouthronHouse.class, "SouthronHouse", 15063989, 10052655);
		registerStructure(1101, LOTRWorldGenSouthronTavern.class, "SouthronTavern", 15063989, 10052655);
		registerStructure(1102, LOTRWorldGenSouthronSmithy.class, "SouthronSmithy", 15063989, 10052655);
		registerStructure(1103, LOTRWorldGenSouthronTower.class, "SouthronTower", 15063989, 10052655);
		registerStructure(1104, LOTRWorldGenSouthronMansion.class, "SouthronMansion", 15063989, 10052655);
		registerStructure(1105, LOTRWorldGenSouthronStables.class, "SouthronStables", 15063989, 10052655);
		registerStructure(1106, LOTRWorldGenSouthronFarm.class, "SouthronFarm", 9547581, 10052655);
		registerStructure(1107, LOTRWorldGenSouthronFortress.class, "SouthronFortress", 15063989, 10052655);
		registerStructure(1108, LOTRWorldGenSouthronWell.class, "SouthronWell", 15063989, 10052655);
		registerStructure(1109, LOTRWorldGenSouthronBazaar.class, "SouthronBazaar", 15063989, 10052655);
		registerStructure(1110, LOTRWorldGenSouthronPasture.class, "SouthronPasture", 9547581, 10052655);
		registerStructure(1111, LOTRWorldGenSouthronVillageSign.class, "SouthronVillageSign", 15063989, 10052655);
		registerVillage(1112, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronVillage", 15063989, 10052655, (IVillageProperties<LOTRVillageGenSouthron.Instance>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.VILLAGE);
		registerStructure(1113, LOTRWorldGenSouthronStatue.class, "SouthronStatue", 15063989, 10052655);
		registerStructure(1114, LOTRWorldGenSouthronBarracks.class, "SouthronBarracks", 15063989, 10052655);
		registerStructure(1115, LOTRWorldGenSouthronTraining.class, "SouthronTraining", 15063989, 10052655);
		registerStructure(1116, LOTRWorldGenSouthronFortGate.class, "SouthronFortGate", 15063989, 10052655);
		registerVillage(1117, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronFortVillage", 15063989, 10052655, (IVillageProperties<LOTRVillageGenSouthron.Instance>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.FORT);
		registerStructure(1118, LOTRWorldGenSouthronLamp.class, "SouthronLamp", 15063989, 10052655);
		registerStructure(1119, LOTRWorldGenSouthronTownTree.class, "SouthronTownTree", 9547581, 10052655);
		registerStructure(1120, LOTRWorldGenSouthronTownFlowers.class, "SouthronTownFlowers", 9547581, 10052655);
		registerVillage(1121, new LOTRVillageGenSouthron(LOTRBiome.nearHaradFertile, 1.0f), "SouthronTown", 15063989, 10052655, (IVillageProperties<LOTRVillageGenSouthron.Instance>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.TOWN);
		registerStructure(1122, LOTRWorldGenSouthronTownGate.class, "SouthronTownGate", 15063989, 10052655);
		registerStructure(1123, LOTRWorldGenSouthronTownCorner.class, "SouthronTownCorner", 15063989, 10052655);
		registerStructure(1140, LOTRWorldGenMoredainMercTent.class, "MoredainMercTent", 12845056, 2949120);
		registerStructure(1141, LOTRWorldGenMoredainMercCamp.class, "MoredainMercCamp", 12845056, 2949120);
		registerStructure(1150, LOTRWorldGenUmbarHouse.class, "UmbarHouse", 14407104, 3354926);
		registerStructure(1151, LOTRWorldGenUmbarTavern.class, "UmbarTavern", 14407104, 3354926);
		registerStructure(1152, LOTRWorldGenUmbarSmithy.class, "UmbarSmithy", 14407104, 3354926);
		registerStructure(1153, LOTRWorldGenUmbarTower.class, "UmbarTower", 14407104, 3354926);
		registerStructure(1154, LOTRWorldGenUmbarMansion.class, "UmbarMansion", 14407104, 3354926);
		registerStructure(1155, LOTRWorldGenUmbarStables.class, "UmbarStables", 14407104, 3354926);
		registerStructure(1156, LOTRWorldGenUmbarFarm.class, "UmbarFarm", 9547581, 3354926);
		registerStructure(1157, LOTRWorldGenUmbarFortress.class, "UmbarFortress", 14407104, 3354926);
		registerStructure(1158, LOTRWorldGenUmbarWell.class, "UmbarWell", 14407104, 3354926);
		registerStructure(1159, LOTRWorldGenUmbarBazaar.class, "UmbarBazaar", 14407104, 3354926);
		registerStructure(1160, LOTRWorldGenUmbarPasture.class, "UmbarPasture", 9547581, 3354926);
		registerStructure(1161, LOTRWorldGenUmbarVillageSign.class, "UmbarVillageSign", 14407104, 3354926);
		registerVillage(1162, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarVillage", 14407104, 3354926, (IVillageProperties<LOTRVillageGenUmbar.InstanceUmbar>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.VILLAGE);
		registerStructure(1163, LOTRWorldGenUmbarStatue.class, "UmbarStatue", 14407104, 3354926);
		registerStructure(1164, LOTRWorldGenUmbarBarracks.class, "UmbarBarracks", 14407104, 3354926);
		registerStructure(1165, LOTRWorldGenUmbarTraining.class, "UmbarTraining", 14407104, 3354926);
		registerStructure(1166, LOTRWorldGenUmbarFortGate.class, "UmbarFortGate", 14407104, 3354926);
		registerVillage(1167, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarFortVillage", 14407104, 3354926, (IVillageProperties<LOTRVillageGenSouthron.Instance>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.FORT);
		registerStructure(1168, LOTRWorldGenUmbarLamp.class, "UmbarLamp", 14407104, 3354926);
		registerStructure(1169, LOTRWorldGenUmbarTownTree.class, "UmbarTownTree", 9547581, 3354926);
		registerStructure(1170, LOTRWorldGenUmbarTownFlowers.class, "UmbarTownFlowers", 9547581, 3354926);
		registerVillage(1171, new LOTRVillageGenUmbar(LOTRBiome.umbar, 1.0f), "UmbarTown", 14407104, 3354926, (IVillageProperties<LOTRVillageGenSouthron.Instance>) instance -> instance.villageType = LOTRVillageGenSouthron.VillageType.TOWN);
		registerStructure(1172, LOTRWorldGenUmbarTownGate.class, "UmbarTownGate", 14407104, 3354926);
		registerStructure(1173, LOTRWorldGenUmbarTownCorner.class, "UmbarTownCorner", 14407104, 3354926);
		registerStructure(1180, LOTRWorldGenCorsairCove.class, "CorsairCove", 8355711, 1644825);
		registerStructure(1181, LOTRWorldGenCorsairTent.class, "CorsairTent", 5658198, 657930);
		registerStructure(1182, LOTRWorldGenCorsairCamp.class, "CorsairCamp", 5658198, 657930);
		registerStructure(1200, LOTRWorldGenNomadTent.class, "NomadTent", 16775927, 8345150);
		registerStructure(1201, LOTRWorldGenNomadTentLarge.class, "NomadTentLarge", 16775927, 8345150);
		registerStructure(1202, LOTRWorldGenNomadChieftainTent.class, "NomadChieftainTent", 16775927, 8345150);
		registerStructure(1203, LOTRWorldGenNomadWell.class, "NomadWell", 5478114, 15391151);
		registerVillage(1204, new LOTRVillageGenHaradNomad(LOTRBiome.nearHaradSemiDesert, 1.0f), "NomadVillageSmall", 16775927, 8345150, (IVillageProperties<LOTRVillageGenHaradNomad.Instance>) instance -> instance.villageType = LOTRVillageGenHaradNomad.VillageType.SMALL);
		registerVillage(1205, new LOTRVillageGenHaradNomad(LOTRBiome.nearHaradSemiDesert, 1.0f), "NomadVillageBig", 16775927, 8345150, (IVillageProperties<LOTRVillageGenHaradNomad.Instance>) instance -> instance.villageType = LOTRVillageGenHaradNomad.VillageType.BIG);
		registerStructure(1206, LOTRWorldGenNomadBazaarTent.class, "NomadBazaarTent", 16775927, 8345150);
		registerStructure(1250, LOTRWorldGenGulfWarCamp.class, "GulfWarCamp", 12849937, 4275226);
		registerStructure(1251, LOTRWorldGenGulfHouse.class, "GulfHouse", 9335899, 5654831);
		registerStructure(1252, LOTRWorldGenGulfAltar.class, "GulfAltar", 12849937, 4275226);
		registerStructure(1253, LOTRWorldGenGulfSmithy.class, "GulfSmithy", 9335899, 5654831);
		registerStructure(1254, LOTRWorldGenGulfBazaar.class, "GulfBazaar", 9335899, 5654831);
		registerStructure(1255, LOTRWorldGenGulfTotem.class, "GulfTotem", 12849937, 4275226);
		registerStructure(1256, LOTRWorldGenGulfPyramid.class, "GulfPyramid", 15721151, 12873038);
		registerStructure(1257, LOTRWorldGenGulfFarm.class, "GulfFarm", 9547581, 12849937);
		registerStructure(1258, LOTRWorldGenGulfTower.class, "GulfTower", 12849937, 4275226);
		registerStructure(1259, LOTRWorldGenGulfTavern.class, "GulfTavern", 9335899, 5654831);
		registerStructure(1260, LOTRWorldGenGulfVillageSign.class, "GulfVillageSign", 14737111, 16250349);
		registerStructure(1261, LOTRWorldGenGulfVillageLight.class, "GulfVillageLight", 14737111, 16250349);
		registerVillage(1262, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfVillage", 9335899, 5654831, (IVillageProperties<LOTRVillageGenGulfHarad.Instance>) instance -> instance.villageType = LOTRVillageGenGulfHarad.VillageType.VILLAGE);
		registerStructure(1263, LOTRWorldGenGulfPasture.class, "GulfPasture", 9547581, 12849937);
		registerVillage(1264, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfTown", 15721151, 12873038, (IVillageProperties<LOTRVillageGenGulfHarad.Instance>) instance -> instance.villageType = LOTRVillageGenGulfHarad.VillageType.TOWN);
		registerVillage(1265, new LOTRVillageGenGulfHarad(LOTRBiome.gulfHarad, 1.0f), "GulfFortVillage", 12849937, 4275226, (IVillageProperties<LOTRVillageGenGulfHarad.Instance>) instance -> instance.villageType = LOTRVillageGenGulfHarad.VillageType.FORT);
		registerStructure(1500, LOTRWorldGenMoredainHutVillage.class, "MoredainHutVillage", 8873812, 12891279);
		registerStructure(1501, LOTRWorldGenMoredainHutChieftain.class, "MoredainHutChieftain", 8873812, 12891279);
		registerStructure(1502, LOTRWorldGenMoredainHutTrader.class, "MoredainHutTrader", 8873812, 12891279);
		registerStructure(1503, LOTRWorldGenMoredainHutHunter.class, "MoredainHutHunter", 8873812, 12891279);
		registerStructure(1550, LOTRWorldGenTauredainPyramid.class, "TauredainPyramid", 6513746, 4803646);
		registerStructure(1551, LOTRWorldGenTauredainHouseSimple.class, "TauredainHouseSimple", 4796447, 8021303);
		registerStructure(1552, LOTRWorldGenTauredainHouseStilts.class, "TauredainHouseStilts", 4796447, 8021303);
		registerStructure(1553, LOTRWorldGenTauredainWatchtower.class, "TauredainWatchtower", 4796447, 8021303);
		registerStructure(1554, LOTRWorldGenTauredainHouseLarge.class, "TauredainHouseLarge", 4796447, 14593598);
		registerStructure(1555, LOTRWorldGenTauredainChieftainPyramid.class, "TauredainChieftainPyramid", 6513746, 4803646);
		registerStructure(1556, LOTRWorldGenTauredainVillageTree.class, "TauredainVillageTree", 9285414, 4796447);
		registerStructure(1557, LOTRWorldGenTauredainVillageFarm.class, "TauredainVillageFarm", 9285414, 4796447);
		registerVillage(1558, new LOTRVillageGenTauredain(LOTRBiome.tauredainClearing, 1.0f), "TauredainVillage", 6840658, 5979708, (IVillageProperties<LOTRVillageGenTauredain.Instance>) instance -> {
		});
		registerStructure(1559, LOTRWorldGenTauredainSmithy.class, "TauredainSmithy", 4796447, 8021303);
		registerStructure(1700, LOTRWorldGenHalfTrollHouse.class, "HalfTrollHouse", 10058344, 5325111);
		registerStructure(1701, LOTRWorldGenHalfTrollWarlordHouse.class, "HalfTrollWarlordHouse", 10058344, 5325111);
		registerStructure(1994, LOTRWorldGenTicketBooth.class, "TicketBooth", 15313961, 1118481, true);
		LOTRMapGenDwarvenMine.register();
		LOTRMapGenTauredainPyramid.register();
	}

	public static void registerVillage(int id, LOTRVillageGen village, String name, int colorBG, int colorFG, IVillageProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				LOTRVillageGen.AbstractInstance<?> instance = village.createAndSetupVillageInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				village.generateCompleteVillageInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isVillage() {
				return true;
			}
		};
		registerStructure(id, strProvider, name, colorBG, colorFG, false);
	}

	public interface IStructureProvider {
		boolean generateStructure(World var1, EntityPlayer var2, int var3, int var4, int var5);

		boolean isVillage();
	}

	public interface IVillageProperties<V> {
		void apply(V var1);
	}

	public static class StructureColorInfo {
		public int spawnedID;
		public int colorBackground;
		public int colorForeground;
		public boolean isVillage;
		public boolean isHidden;

		public StructureColorInfo(int i, int colorBG, int colorFG, boolean vill, boolean hide) {
			spawnedID = i;
			colorBackground = colorBG;
			colorForeground = colorFG;
			isVillage = vill;
			isHidden = hide;
		}
	}

}
