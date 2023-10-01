package lotr.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityWickedDwarf;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantList;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeInvasionSpawns;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.EnumHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class LOTRBiome extends BiomeGenBase {
	public static Class[][] correctCreatureTypeParams = {{EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE}};
	public static EnumCreatureType creatureType_LOTRAmbient = EnumHelper.addEnum(correctCreatureTypeParams, EnumCreatureType.class, "LOTRAmbient", LOTRAmbientCreature.class, 45, Material.air, true, false);
	public static LOTRBiome river;
	public static LOTRBiome rohan;
	public static LOTRBiome mistyMountains;
	public static LOTRBiome shire;
	public static LOTRBiome shireWoodlands;
	public static LOTRBiome mordor;
	public static LOTRBiome mordorMountains;
	public static LOTRBiome gondor;
	public static LOTRBiome whiteMountains;
	public static LOTRBiome lothlorien;
	public static LOTRBiome celebrant;
	public static LOTRBiome ironHills;
	public static LOTRBiome deadMarshes;
	public static LOTRBiome trollshaws;
	public static LOTRBiome woodlandRealm;
	public static LOTRBiome mirkwoodCorrupted;
	public static LOTRBiome rohanUrukHighlands;
	public static LOTRBiome emynMuil;
	public static LOTRBiome ithilien;
	public static LOTRBiome pelargir;
	public static LOTRBiome loneLands;
	public static LOTRBiome loneLandsHills;
	public static LOTRBiome dunland;
	public static LOTRBiome fangorn;
	public static LOTRBiome angle;
	public static LOTRBiome ettenmoors;
	public static LOTRBiome oldForest;
	public static LOTRBiome harondor;
	public static LOTRBiome eriador;
	public static LOTRBiome eriadorDowns;
	public static LOTRBiome erynVorn;
	public static LOTRBiome greyMountains;
	public static LOTRBiome midgewater;
	public static LOTRBiome brownLands;
	public static LOTRBiome ocean;
	public static LOTRBiome anduinHills;
	public static LOTRBiome meneltarma;
	public static LOTRBiome gladdenFields;
	public static LOTRBiome lothlorienEdge;
	public static LOTRBiome forodwaith;
	public static LOTRBiome enedwaith;
	public static LOTRBiome angmar;
	public static LOTRBiome eregion;
	public static LOTRBiome lindon;
	public static LOTRBiome lindonWoodlands;
	public static LOTRBiome eastBight;
	public static LOTRBiome blueMountains;
	public static LOTRBiome mirkwoodMountains;
	public static LOTRBiome wilderland;
	public static LOTRBiome dagorlad;
	public static LOTRBiome nurn;
	public static LOTRBiome nurnen;
	public static LOTRBiome nurnMarshes;
	public static LOTRBiome angmarMountains;
	public static LOTRBiome anduinMouth;
	public static LOTRBiome entwashMouth;
	public static LOTRBiome dorEnErnil;
	public static LOTRBiome dorEnErnilHills;
	public static LOTRBiome fangornWasteland;
	public static LOTRBiome rohanWoodlands;
	public static LOTRBiome gondorWoodlands;
	public static LOTRBiome lake;
	public static LOTRBiome lindonCoast;
	public static LOTRBiome barrowDowns;
	public static LOTRBiome longMarshes;
	public static LOTRBiome fangornClearing;
	public static LOTRBiome ithilienHills;
	public static LOTRBiome ithilienWasteland;
	public static LOTRBiome nindalf;
	public static LOTRBiome coldfells;
	public static LOTRBiome nanCurunir;
	public static LOTRBiome adornland;
	public static LOTRBiome whiteDowns;
	public static LOTRBiome swanfleet;
	public static LOTRBiome pelennor;
	public static LOTRBiome minhiriath;
	public static LOTRBiome erebor;
	public static LOTRBiome mirkwoodNorth;
	public static LOTRBiome woodlandRealmHills;
	public static LOTRBiome nanUngol;
	public static LOTRBiome pinnathGelin;
	public static LOTRBiome island;
	public static LOTRBiome forodwaithMountains;
	public static LOTRBiome mistyMountainsFoothills;
	public static LOTRBiome greyMountainsFoothills;
	public static LOTRBiome blueMountainsFoothills;
	public static LOTRBiome tundra;
	public static LOTRBiome taiga;
	public static LOTRBiome breeland;
	public static LOTRBiome chetwood;
	public static LOTRBiome forodwaithGlacier;
	public static LOTRBiome whiteMountainsFoothills;
	public static LOTRBiome beach;
	public static LOTRBiome beachGravel;
	public static LOTRBiome nearHarad;
	public static LOTRBiome farHarad;
	public static LOTRBiome haradMountains;
	public static LOTRBiome umbar;
	public static LOTRBiome farHaradJungle;
	public static LOTRBiome umbarHills;
	public static LOTRBiome nearHaradHills;
	public static LOTRBiome farHaradJungleLake;
	public static LOTRBiome lostladen;
	public static LOTRBiome farHaradForest;
	public static LOTRBiome nearHaradFertile;
	public static LOTRBiome pertorogwaith;
	public static LOTRBiome umbarForest;
	public static LOTRBiome farHaradJungleEdge;
	public static LOTRBiome tauredainClearing;
	public static LOTRBiome gulfHarad;
	public static LOTRBiome dorwinionHills;
	public static LOTRBiome tolfalas;
	public static LOTRBiome lebennin;
	public static LOTRBiome rhun;
	public static LOTRBiome rhunForest;
	public static LOTRBiome redMountains;
	public static LOTRBiome redMountainsFoothills;
	public static LOTRBiome dolGuldur;
	public static LOTRBiome nearHaradSemiDesert;
	public static LOTRBiome farHaradArid;
	public static LOTRBiome farHaradAridHills;
	public static LOTRBiome farHaradSwamp;
	public static LOTRBiome farHaradCloudForest;
	public static LOTRBiome farHaradBushland;
	public static LOTRBiome farHaradBushlandHills;
	public static LOTRBiome farHaradMangrove;
	public static LOTRBiome nearHaradFertileForest;
	public static LOTRBiome anduinVale;
	public static LOTRBiome wold;
	public static LOTRBiome shireMoors;
	public static LOTRBiome shireMarshes;
	public static LOTRBiome nearHaradRedDesert;
	public static LOTRBiome farHaradVolcano;
	public static LOTRBiome udun;
	public static LOTRBiome gorgoroth;
	public static LOTRBiome morgulVale;
	public static LOTRBiome easternDesolation;
	public static LOTRBiome dale;
	public static LOTRBiome dorwinion;
	public static LOTRBiome towerHills;
	public static LOTRBiome gulfHaradForest;
	public static LOTRBiome wilderlandNorth;
	public static LOTRBiome forodwaithCoast;
	public static LOTRBiome farHaradCoast;
	public static LOTRBiome nearHaradRiverbank;
	public static LOTRBiome lossarnach;
	public static LOTRBiome imlothMelui;
	public static LOTRBiome nearHaradOasis;
	public static LOTRBiome beachWhite;
	public static LOTRBiome harnedor;
	public static LOTRBiome lamedon;
	public static LOTRBiome lamedonHills;
	public static LOTRBiome blackrootVale;
	public static LOTRBiome andrast;
	public static LOTRBiome pukel;
	public static LOTRBiome rhunLand;
	public static LOTRBiome rhunLandSteppe;
	public static LOTRBiome rhunLandHills;
	public static LOTRBiome rhunRedForest;
	public static LOTRBiome rhunIsland;
	public static LOTRBiome rhunIslandForest;
	public static LOTRBiome lastDesert;
	public static LOTRBiome windMountains;
	public static LOTRBiome windMountainsFoothills;
	public static LOTRBiome rivendell;
	public static LOTRBiome rivendellHills;
	public static LOTRBiome farHaradJungleMountains;
	public static LOTRBiome halfTrollForest;
	public static LOTRBiome farHaradKanuka;
	public static LOTRBiome utumno;
	public static NoiseGeneratorPerlin biomeTerrainNoise = new NoiseGeneratorPerlin(new Random(1955L), 1);
	public static Random terrainRand = new Random();
	public static Color waterColorNorth = new Color(602979);
	public static Color waterColorSouth = new Color(4973293);
	public static int waterLimitNorth = -40000;
	public static int waterLimitSouth = 160000;

	public LOTRDimension biomeDimension;
	public LOTRBiomeDecorator decorator;
	public int topBlockMeta;
	public int fillerBlockMeta;
	public float heightBaseParameter;
	public boolean enablePodzol = true;
	public boolean enableRocky = true;
	public LOTRBiomeVariantList biomeVariantsSmall = new LOTRBiomeVariantList();
	public LOTRBiomeVariantList biomeVariantsLarge = new LOTRBiomeVariantList();
	public float variantChance = 0.4f;
	public LOTRBiomeSpawnList npcSpawnList = new LOTRBiomeSpawnList(this);
	public List spawnableLOTRAmbientList = new ArrayList();
	public Collection spawnableTraders = new ArrayList();
	public LOTREventSpawner.EventChance banditChance;
	public Class<? extends LOTREntityBandit> banditEntityClass;
	public LOTRBiomeInvasionSpawns invasionSpawns;
	public BiomeColors biomeColors = new BiomeColors(this);
	public BiomeTerrain biomeTerrain = new BiomeTerrain(this);
	public boolean initDwarven;

	public boolean isDwarven;

	protected LOTRBiome(int i, boolean major) {
		this(i, major, LOTRDimension.MIDDLE_EARTH);
	}

	protected LOTRBiome(int i, boolean major, LOTRDimension dim) {
		super(i, false);
		biomeDimension = dim;
		if (biomeDimension.biomeList[i] != null) {
			throw new IllegalArgumentException("LOTR biome already exists at index " + i + " for dimension " + biomeDimension.dimensionName + "!");
		}
		biomeDimension.biomeList[i] = this;
		if (major) {
			biomeDimension.majorBiomes.add(this);
		}
		waterColorMultiplier = BiomeColors.DEFAULT_WATER;
		decorator = new LOTRBiomeDecorator(this);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableCaveCreatureList.clear();
		if (hasDomesticAnimals()) {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 5, 4, 4));
		} else {
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityWildBoar.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 8, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityDeer.class, 10, 4, 4));
			spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityAurochs.class, 6, 4, 4));
		}
		spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityFish.class, 10, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityButterfly.class, 8, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityRabbit.class, 8, 4, 4));
		spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
		spawnableCaveCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
		banditChance = LOTREventSpawner.EventChance.NEVER;
		invasionSpawns = new LOTRBiomeInvasionSpawns(this);
	}

	public static void initBiomes() {
		river = new LOTRBiomeGenRiver(0, false).setMinMaxHeight(-0.5f, 0.0f).setColor(3570869).setBiomeName("river");
		rohan = new LOTRBiomeGenRohan(1, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.15f).setColor(7384389).setBiomeName("rohan");
		mistyMountains = new LOTRBiomeGenMistyMountains(2, true).setTemperatureRainfall(0.2f, 0.5f).setMinMaxHeight(2.0f, 2.0f).setColor(15263713).setBiomeName("mistyMountains");
		shire = new LOTRBiomeGenShire(3, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.15f, 0.3f).setColor(6794549).setBiomeName("shire");
		shireWoodlands = new LOTRBiomeGenShireWoodlands(4, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.3f, 0.5f).setColor(4486966).setBiomeName("shireWoodlands");
		mordor = new LOTRBiomeGenMordor(5, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.3f, 0.5f).setColor(1118222).setBiomeName("mordor");
		mordorMountains = new LOTRBiomeGenMordorMountains(6, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(2.0f, 3.0f).setColor(5328200).setBiomeName("mordorMountains");
		gondor = new LOTRBiomeGenGondor(7, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.1f, 0.15f).setColor(8959045).setBiomeName("gondor");
		whiteMountains = new LOTRBiomeGenWhiteMountains(8, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(1.5f, 2.0f).setColor(15066600).setBiomeName("whiteMountains");
		lothlorien = new LOTRBiomeGenLothlorien(9, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.3f).setColor(16504895).setBiomeName("lothlorien");
		celebrant = new LOTRBiomeGenCelebrant(10, true).setTemperatureRainfall(1.1f, 1.1f).setMinMaxHeight(0.1f, 0.05f).setColor(7647046).setBiomeName("celebrant");
		ironHills = new LOTRBiomeGenIronHills(11, true).setTemperatureRainfall(0.27f, 0.4f).setMinMaxHeight(0.3f, 1.4f).setColor(9142093).setBiomeName("ironHills");
		deadMarshes = new LOTRBiomeGenDeadMarshes(12, true).setTemperatureRainfall(0.4f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(7303999).setBiomeName("deadMarshes");
		trollshaws = new LOTRBiomeGenTrollshaws(13, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.15f, 1.0f).setColor(5798959).setBiomeName("trollshaws");
		woodlandRealm = new LOTRBiomeGenWoodlandRealm(14, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.3f).setColor(4089126).setBiomeName("woodlandRealm");
		mirkwoodCorrupted = new LOTRBiomeGenMirkwoodCorrupted(15, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.4f).setColor(3032091).setBiomeName("mirkwoodCorrupted");
		rohanUrukHighlands = new LOTRBiomeGenRohanUruk(16, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.8f, 0.3f).setColor(8295258).setBiomeName("rohanUrukHighlands");
		emynMuil = new LOTRBiomeGenEmynMuil(17, true).setTemperatureRainfall(0.5f, 0.9f).setMinMaxHeight(0.2f, 0.8f).setColor(9866354).setBiomeName("emynMuil");
		ithilien = new LOTRBiomeGenIthilien(18, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.15f, 0.5f).setColor(7710516).setBiomeName("ithilien");
		pelargir = new LOTRBiomeGenPelargir(19, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.08f, 0.2f).setColor(11256145).setBiomeName("pelargir");
		loneLands = new LOTRBiomeGenLoneLands(21, true).setTemperatureRainfall(0.6f, 0.5f).setMinMaxHeight(0.15f, 0.4f).setColor(8562762).setBiomeName("loneLands");
		loneLandsHills = new LOTRBiomeGenLoneLandsHills(22, false).setTemperatureRainfall(0.6f, 0.5f).setMinMaxHeight(0.6f, 0.8f).setColor(8687182).setBiomeName("loneLandsHills");
		dunland = new LOTRBiomeGenDunland(23, true).setTemperatureRainfall(0.4f, 0.7f).setMinMaxHeight(0.3f, 0.5f).setColor(6920524).setBiomeName("dunland");
		fangorn = new LOTRBiomeGenFangorn(24, true).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(0.2f, 0.4f).setColor(4355353).setBiomeName("fangorn");
		angle = new LOTRBiomeGenAngle(25, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.15f, 0.3f).setColor(9416527).setBiomeName("angle");
		ettenmoors = new LOTRBiomeGenEttenmoors(26, true).setTemperatureRainfall(0.2f, 0.6f).setMinMaxHeight(0.5f, 0.6f).setColor(8161626).setBiomeName("ettenmoors");
		oldForest = new LOTRBiomeGenOldForest(27, true).setTemperatureRainfall(0.5f, 1.0f).setMinMaxHeight(0.2f, 0.3f).setColor(4551995).setBiomeName("oldForest");
		harondor = new LOTRBiomeGenHarondor(28, true).setTemperatureRainfall(1.0f, 0.6f).setMinMaxHeight(0.2f, 0.3f).setColor(10663238).setBiomeName("harondor");
		eriador = new LOTRBiomeGenEriador(29, true).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.1f, 0.4f).setColor(7054916).setBiomeName("eriador");
		eriadorDowns = new LOTRBiomeGenEriadorDowns(30, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.5f, 0.5f).setColor(7638087).setBiomeName("eriadorDowns");
		erynVorn = new LOTRBiomeGenErynVorn(31, false).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.1f, 0.4f).setColor(4357965).setBiomeName("erynVorn");
		greyMountains = new LOTRBiomeGenGreyMountains(32, true).setTemperatureRainfall(0.28f, 0.2f).setMinMaxHeight(1.8f, 2.0f).setColor(13290689).setBiomeName("greyMountains");
		midgewater = new LOTRBiomeGenMidgewater(33, true).setTemperatureRainfall(0.6f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(6001495).setBiomeName("midgewater");
		brownLands = new LOTRBiomeGenBrownLands(34, true).setTemperatureRainfall(1.0f, 0.2f).setMinMaxHeight(0.2f, 0.2f).setColor(8552016).setBiomeName("brownLands");
		ocean = new LOTRBiomeGenOcean(35, false).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(-1.0f, 0.3f).setColor(153997).setBiomeName("ocean");
		anduinHills = new LOTRBiomeGenAnduin(36, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.6f, 0.4f).setColor(7058012).setBiomeName("anduinHills");
		meneltarma = new LOTRBiomeGenMeneltarma(37, false).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.1f, 0.2f).setColor(9549658).setBiomeName("meneltarma");
		gladdenFields = new LOTRBiomeGenGladdenFields(38, true).setTemperatureRainfall(0.6f, 1.2f).setMinMaxHeight(0.0f, 0.1f).setColor(5020505).setBiomeName("gladdenFields");
		lothlorienEdge = new LOTRBiomeGenLothlorienEdge(39, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.2f).setColor(13944387).setBiomeName("lothlorienEdge");
		forodwaith = new LOTRBiomeGenForodwaith(40, true).setTemperatureRainfall(0.0f, 0.2f).setMinMaxHeight(0.1f, 0.1f).setColor(14211282).setBiomeName("forodwaith");
		enedwaith = new LOTRBiomeGenEnedwaith(41, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.3f).setColor(8038479).setBiomeName("enedwaith");
		angmar = new LOTRBiomeGenAngmar(42, true).setTemperatureRainfall(0.2f, 0.2f).setMinMaxHeight(0.2f, 0.6f).setColor(5523247).setBiomeName("angmar");
		eregion = new LOTRBiomeGenEregion(43, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.2f, 0.3f).setColor(6656072).setBiomeName("eregion");
		lindon = new LOTRBiomeGenLindon(44, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.15f, 0.2f).setColor(7646533).setBiomeName("lindon");
		lindonWoodlands = new LOTRBiomeGenLindonWoodlands(45, false).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.2f, 0.5f).setColor(1996591).setBiomeName("lindonWoodlands");
		eastBight = new LOTRBiomeGenEastBight(46, true).setTemperatureRainfall(0.8f, 0.3f).setMinMaxHeight(0.15f, 0.05f).setColor(9082205).setBiomeName("eastBight");
		blueMountains = new LOTRBiomeGenBlueMountains(47, true).setTemperatureRainfall(0.22f, 0.8f).setMinMaxHeight(1.0f, 2.5f).setColor(13228770).setBiomeName("blueMountains");
		mirkwoodMountains = new LOTRBiomeGenMirkwoodMountains(48, true).setTemperatureRainfall(0.28f, 0.9f).setMinMaxHeight(1.2f, 1.5f).setColor(2632989).setBiomeName("mirkwoodMountains");
		wilderland = new LOTRBiomeGenWilderland(49, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.2f, 0.4f).setColor(9612368).setBiomeName("wilderland");
		dagorlad = new LOTRBiomeGenDagorlad(50, true).setTemperatureRainfall(1.0f, 0.2f).setMinMaxHeight(0.1f, 0.05f).setColor(7036741).setBiomeName("dagorlad");
		nurn = new LOTRBiomeGenNurn(51, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.1f, 0.2f).setColor(2630683).setBiomeName("nurn");
		nurnen = new LOTRBiomeGenNurnen(52, false).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(-1.0f, 0.3f).setColor(931414).setBiomeName("nurnen");
		nurnMarshes = new LOTRBiomeGenNurnMarshes(53, true).setTemperatureRainfall(0.9f, 0.4f).setMinMaxHeight(0.0f, 0.1f).setColor(4012843).setBiomeName("nurnMarshes");
		adornland = new LOTRBiomeGenAdornland(54, true).setTemperatureRainfall(0.7f, 0.6f).setMinMaxHeight(0.2f, 0.2f).setColor(7838543).setBiomeName("adornland");
		angmarMountains = new LOTRBiomeGenAngmarMountains(55, true).setTemperatureRainfall(0.25f, 0.1f).setMinMaxHeight(1.6f, 1.5f).setColor(13619147).setBiomeName("angmarMountains");
		anduinMouth = new LOTRBiomeGenAnduinMouth(56, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(5089363).setBiomeName("anduinMouth");
		entwashMouth = new LOTRBiomeGenEntwashMouth(57, true).setTemperatureRainfall(0.5f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(5612358).setBiomeName("entwashMouth");
		dorEnErnil = new LOTRBiomeGenDorEnErnil(58, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.07f, 0.2f).setColor(9355077).setBiomeName("dorEnErnil");
		dorEnErnilHills = new LOTRBiomeGenDorEnErnilHills(59, false).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.5f, 0.5f).setColor(8560707).setBiomeName("dorEnErnilHills");
		fangornWasteland = new LOTRBiomeGenFangornWasteland(60, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.2f, 0.4f).setColor(6782028).setBiomeName("fangornWasteland");
		rohanWoodlands = new LOTRBiomeGenRohanWoodlands(61, false).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(5736246).setBiomeName("rohanWoodlands");
		gondorWoodlands = new LOTRBiomeGenGondorWoodlands(62, false).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.2f).setColor(5867307).setBiomeName("gondorWoodlands");
		lake = new LOTRBiomeGenLake(63, false).setColor(3433630).setBiomeName("lake");
		lindonCoast = new LOTRBiomeGenLindonCoast(64, false).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.0f, 0.5f).setColor(9278870).setBiomeName("lindonCoast");
		barrowDowns = new LOTRBiomeGenBarrowDowns(65, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.3f, 0.4f).setColor(8097362).setBiomeName("barrowDowns");
		longMarshes = new LOTRBiomeGenLongMarshes(66, true).setTemperatureRainfall(0.6f, 0.9f).setMinMaxHeight(0.0f, 0.1f).setColor(7178054).setBiomeName("longMarshes");
		fangornClearing = new LOTRBiomeGenFangornClearing(67, false).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(0.2f, 0.1f).setColor(5877050).setBiomeName("fangornClearing");
		ithilienHills = new LOTRBiomeGenIthilienHills(68, false).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.6f, 0.6f).setColor(6985792).setBiomeName("ithilienHills");
		ithilienWasteland = new LOTRBiomeGenIthilienWasteland(69, true).setTemperatureRainfall(0.6f, 0.6f).setMinMaxHeight(0.15f, 0.2f).setColor(8030031).setBiomeName("ithilienWasteland");
		nindalf = new LOTRBiomeGenNindalf(70, true).setTemperatureRainfall(0.4f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(7111750).setBiomeName("nindalf");
		coldfells = new LOTRBiomeGenColdfells(71, true).setTemperatureRainfall(0.25f, 0.8f).setMinMaxHeight(0.4f, 0.8f).setColor(8296018).setBiomeName("coldfells");
		nanCurunir = new LOTRBiomeGenNanCurunir(72, true).setTemperatureRainfall(0.6f, 0.4f).setMinMaxHeight(0.2f, 0.1f).setColor(7109714).setBiomeName("nanCurunir");
		whiteDowns = new LOTRBiomeGenWhiteDowns(74, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.6f, 0.6f).setColor(10210937).setBiomeName("whiteDowns");
		swanfleet = new LOTRBiomeGenSwanfleet(75, true).setTemperatureRainfall(0.8f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(6265945).setBiomeName("swanfleet");
		pelennor = new LOTRBiomeGenPelennor(76, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.1f, 0.02f).setColor(11258955).setBiomeName("pelennor");
		minhiriath = new LOTRBiomeGenMinhiriath(77, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.1f, 0.2f).setColor(7380550).setBiomeName("minhiriath");
		erebor = new LOTRBiomeGenErebor(78, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.4f, 0.6f).setColor(7499093).setBiomeName("erebor");
		mirkwoodNorth = new LOTRBiomeGenMirkwoodNorth(79, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.2f, 0.4f).setColor(3822115).setBiomeName("mirkwoodNorth");
		woodlandRealmHills = new LOTRBiomeGenWoodlandRealmHills(80, false).setTemperatureRainfall(0.8f, 0.6f).setMinMaxHeight(0.9f, 0.7f).setColor(3624991).setBiomeName("woodlandRealmHills");
		nanUngol = new LOTRBiomeGenNanUngol(81, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.1f, 0.4f).setColor(656641).setBiomeName("nanUngol");
		pinnathGelin = new LOTRBiomeGenPinnathGelin(82, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.5f, 0.5f).setColor(9946693).setBiomeName("pinnathGelin");
		island = new LOTRBiomeGenOcean(83, false).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.0f, 0.3f).setColor(10138963).setBiomeName("island");
		forodwaithMountains = new LOTRBiomeGenForodwaithMountains(84, true).setTemperatureRainfall(0.0f, 0.2f).setMinMaxHeight(2.0f, 2.0f).setColor(15592942).setBiomeName("forodwaithMountains");
		mistyMountainsFoothills = new LOTRBiomeGenMistyMountainsFoothills(85, true).setTemperatureRainfall(0.25f, 0.6f).setMinMaxHeight(0.7f, 0.9f).setColor(12501430).setBiomeName("mistyMountainsFoothills");
		greyMountainsFoothills = new LOTRBiomeGenGreyMountainsFoothills(86, true).setTemperatureRainfall(0.5f, 0.7f).setMinMaxHeight(0.5f, 0.9f).setColor(9148000).setBiomeName("greyMountainsFoothills");
		blueMountainsFoothills = new LOTRBiomeGenBlueMountainsFoothills(87, true).setTemperatureRainfall(0.5f, 0.8f).setMinMaxHeight(0.5f, 0.9f).setColor(11253170).setBiomeName("blueMountainsFoothills");
		tundra = new LOTRBiomeGenTundra(88, true).setTemperatureRainfall(0.1f, 0.3f).setMinMaxHeight(0.1f, 0.2f).setColor(12366486).setBiomeName("tundra");
		taiga = new LOTRBiomeGenTaiga(89, true).setTemperatureRainfall(0.1f, 0.7f).setMinMaxHeight(0.1f, 0.5f).setColor(6526543).setBiomeName("taiga");
		breeland = new LOTRBiomeGenBreeland(90, true).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.1f, 0.2f).setColor(6861625).setBiomeName("breeland");
		chetwood = new LOTRBiomeGenChetwood(91, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(4424477).setBiomeName("chetwood");
		forodwaithGlacier = new LOTRBiomeGenForodwaithGlacier(92, true).setTemperatureRainfall(0.0f, 0.1f).setMinMaxHeight(1.0f, 0.1f).setColor(9424096).setBiomeName("forodwaithGlacier");
		whiteMountainsFoothills = new LOTRBiomeGenWhiteMountainsFoothills(93, true).setTemperatureRainfall(0.6f, 0.7f).setMinMaxHeight(0.5f, 0.9f).setColor(12635575).setBiomeName("whiteMountainsFoothills");
		beach = new LOTRBiomeGenBeach(94, false).setBeachBlock(Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
		beachGravel = new LOTRBiomeGenBeach(95, false).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
		nearHarad = new LOTRBiomeGenNearHarad(96, true).setTemperatureRainfall(1.5f, 0.1f).setMinMaxHeight(0.2f, 0.1f).setColor(14205815).setBiomeName("nearHarad");
		farHarad = new LOTRBiomeGenFarHaradSavannah(97, true).setTemperatureRainfall(1.2f, 0.2f).setMinMaxHeight(0.1f, 0.1f).setColor(9740353).setBiomeName("farHarad");
		haradMountains = new LOTRBiomeGenHaradMountains(98, true).setTemperatureRainfall(0.9f, 0.5f).setMinMaxHeight(1.8f, 2.0f).setColor(9867381).setBiomeName("haradMountains");
		umbar = new LOTRBiomeGenUmbar(99, true).setTemperatureRainfall(0.9f, 0.6f).setMinMaxHeight(0.1f, 0.2f).setColor(9542740).setBiomeName("umbar");
		farHaradJungle = new LOTRBiomeGenFarHaradJungle(100, true).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(0.2f, 0.4f).setColor(4944931).setBiomeName("farHaradJungle");
		umbarHills = new LOTRBiomeGenUmbar(101, false).setTemperatureRainfall(0.8f, 0.5f).setMinMaxHeight(1.2f, 0.8f).setColor(8226378).setBiomeName("umbarHills");
		nearHaradHills = new LOTRBiomeGenNearHaradHills(102, false).setTemperatureRainfall(1.2f, 0.3f).setMinMaxHeight(0.5f, 0.8f).setColor(12167010).setBiomeName("nearHaradHills");
		farHaradJungleLake = new LOTRBiomeGenFarHaradJungleLake(103, false).setTemperatureRainfall(1.2f, 0.9f).setMinMaxHeight(-0.5f, 0.2f).setColor(2271948).setBiomeName("farHaradJungleLake");
		lostladen = new LOTRBiomeGenLostladen(104, true).setTemperatureRainfall(1.2f, 0.2f).setMinMaxHeight(0.2f, 0.1f).setColor(10658661).setBiomeName("lostladen");
		farHaradForest = new LOTRBiomeGenFarHaradForest(105, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.3f, 0.4f).setColor(3703325).setBiomeName("farHaradForest");
		nearHaradFertile = new LOTRBiomeGenNearHaradFertile(106, true).setTemperatureRainfall(1.2f, 0.7f).setMinMaxHeight(0.2f, 0.1f).setColor(10398286).setBiomeName("nearHaradFertile");
		pertorogwaith = new LOTRBiomeGenPertorogwaith(107, true).setTemperatureRainfall(0.7f, 0.1f).setMinMaxHeight(0.2f, 0.5f).setColor(8879706).setBiomeName("pertorogwaith");
		umbarForest = new LOTRBiomeGenUmbarForest(108, false).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.3f).setColor(7178042).setBiomeName("umbarForest");
		farHaradJungleEdge = new LOTRBiomeGenFarHaradJungleEdge(109, true).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(7440430).setBiomeName("farHaradJungleEdge");
		tauredainClearing = new LOTRBiomeGenTauredainClearing(110, true).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(10796101).setBiomeName("tauredainClearing");
		gulfHarad = new LOTRBiomeGenGulfHarad(111, true).setTemperatureRainfall(1.0f, 0.5f).setMinMaxHeight(0.15f, 0.1f).setColor(9152592).setBiomeName("gulfHarad");
		dorwinionHills = new LOTRBiomeGenDorwinionHills(112, true).setTemperatureRainfall(0.9f, 0.8f).setMinMaxHeight(0.8f, 0.8f).setColor(13357993).setBiomeName("dorwinionHills");
		tolfalas = new LOTRBiomeGenTolfalas(113, true).setTemperatureRainfall(0.8f, 0.4f).setMinMaxHeight(0.3f, 1.0f).setColor(10199149).setBiomeName("tolfalas");
		lebennin = new LOTRBiomeGenLebennin(114, true).setTemperatureRainfall(1.0f, 0.9f).setMinMaxHeight(0.1f, 0.3f).setColor(7845418).setBiomeName("lebennin");
		rhun = new LOTRBiomeGenRhun(115, true).setTemperatureRainfall(0.9f, 0.3f).setMinMaxHeight(0.3f, 0.0f).setColor(10465880).setBiomeName("rhun");
		rhunForest = new LOTRBiomeGenRhunForest(116, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.3f, 0.5f).setColor(7505723).setBiomeName("rhunForest");
		redMountains = new LOTRBiomeGenRedMountains(117, true).setTemperatureRainfall(0.3f, 0.4f).setMinMaxHeight(1.5f, 2.0f).setColor(9662796).setBiomeName("redMountains");
		redMountainsFoothills = new LOTRBiomeGenRedMountainsFoothills(118, true).setTemperatureRainfall(0.7f, 0.4f).setMinMaxHeight(0.5f, 0.9f).setColor(10064978).setBiomeName("redMountainsFoothills");
		dolGuldur = new LOTRBiomeGenDolGuldur(119, true).setTemperatureRainfall(0.6f, 0.8f).setMinMaxHeight(0.2f, 0.5f).setColor(2371343).setBiomeName("dolGuldur");
		nearHaradSemiDesert = new LOTRBiomeGenNearHaradSemiDesert(120, true).setTemperatureRainfall(1.5f, 0.2f).setMinMaxHeight(0.2f, 0.1f).setColor(12434282).setBiomeName("nearHaradSemiDesert");
		farHaradArid = new LOTRBiomeGenFarHaradArid(121, true).setTemperatureRainfall(1.5f, 0.3f).setMinMaxHeight(0.2f, 0.15f).setColor(11185749).setBiomeName("farHaradArid");
		farHaradAridHills = new LOTRBiomeGenFarHaradArid(122, false).setTemperatureRainfall(1.5f, 0.3f).setMinMaxHeight(1.0f, 0.6f).setColor(10063195).setBiomeName("farHaradAridHills");
		farHaradSwamp = new LOTRBiomeGenFarHaradSwamp(123, true).setTemperatureRainfall(0.8f, 1.0f).setMinMaxHeight(0.0f, 0.1f).setColor(5608267).setBiomeName("farHaradSwamp");
		farHaradCloudForest = new LOTRBiomeGenFarHaradCloudForest(124, true).setTemperatureRainfall(1.2f, 1.2f).setMinMaxHeight(0.7f, 0.4f).setColor(3046208).setBiomeName("farHaradCloudForest");
		farHaradBushland = new LOTRBiomeGenFarHaradBushland(125, true).setTemperatureRainfall(1.0f, 0.4f).setMinMaxHeight(0.2f, 0.1f).setColor(10064190).setBiomeName("farHaradBushland");
		farHaradBushlandHills = new LOTRBiomeGenFarHaradBushland(126, false).setTemperatureRainfall(0.8f, 0.4f).setMinMaxHeight(0.8f, 0.8f).setColor(8354100).setBiomeName("farHaradBushlandHills");
		farHaradMangrove = new LOTRBiomeGenFarHaradMangrove(127, true).setTemperatureRainfall(1.0f, 0.9f).setMinMaxHeight(-0.05f, 0.05f).setColor(8883789).setBiomeName("farHaradMangrove");
		nearHaradFertileForest = new LOTRBiomeGenNearHaradFertileForest(128, false).setTemperatureRainfall(1.2f, 1.0f).setMinMaxHeight(0.2f, 0.4f).setColor(6915122).setBiomeName("nearHaradFertileForest");
		anduinVale = new LOTRBiomeGenAnduinVale(129, true).setTemperatureRainfall(0.9f, 1.1f).setMinMaxHeight(0.05f, 0.05f).setColor(7447880).setBiomeName("anduinVale");
		wold = new LOTRBiomeGenWold(130, true).setTemperatureRainfall(0.9f, 0.1f).setMinMaxHeight(0.4f, 0.3f).setColor(9483599).setBiomeName("wold");
		shireMoors = new LOTRBiomeGenShireMoors(131, true).setTemperatureRainfall(0.6f, 1.6f).setMinMaxHeight(0.4f, 0.6f).setColor(6921036).setBiomeName("shireMoors");
		shireMarshes = new LOTRBiomeGenShireMarshes(132, true).setTemperatureRainfall(0.8f, 1.2f).setMinMaxHeight(0.0f, 0.1f).setColor(4038751).setBiomeName("shireMarshes");
		nearHaradRedDesert = new LOTRBiomeGenNearHaradRed(133, true).setTemperatureRainfall(1.5f, 0.1f).setMinMaxHeight(0.2f, 0.0f).setColor(13210447).setBiomeName("nearHaradRedDesert");
		farHaradVolcano = new LOTRBiomeGenFarHaradVolcano(134, true).setTemperatureRainfall(1.5f, 0.0f).setMinMaxHeight(0.6f, 1.2f).setColor(6838068).setBiomeName("farHaradVolcano");
		udun = new LOTRBiomeGenUdun(135, true).setTemperatureRainfall(1.5f, 0.0f).setMinMaxHeight(0.2f, 0.7f).setColor(65536).setBiomeName("udun");
		gorgoroth = new LOTRBiomeGenGorgoroth(136, true).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.6f, 0.2f).setColor(2170141).setBiomeName("gorgoroth");
		morgulVale = new LOTRBiomeGenMorgulVale(137, true).setTemperatureRainfall(1.0f, 0.0f).setMinMaxHeight(0.2f, 0.1f).setColor(1387801).setBiomeName("morgulVale");
		easternDesolation = new LOTRBiomeGenEasternDesolation(138, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.2f, 0.2f).setColor(6052935).setBiomeName("easternDesolation");
		dale = new LOTRBiomeGenDale(139, true).setTemperatureRainfall(0.8f, 0.7f).setMinMaxHeight(0.1f, 0.2f).setColor(8233807).setBiomeName("dale");
		dorwinion = new LOTRBiomeGenDorwinion(140, true).setTemperatureRainfall(0.9f, 0.9f).setMinMaxHeight(0.1f, 0.3f).setColor(7120197).setBiomeName("dorwinion");
		towerHills = new LOTRBiomeGenTowerHills(141, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.5f, 0.5f).setColor(6854209).setBiomeName("towerHills");
		gulfHaradForest = new LOTRBiomeGenGulfHaradForest(142, false).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.2f, 0.4f).setColor(5868590).setBiomeName("gulfHaradForest");
		wilderlandNorth = new LOTRBiomeGenWilderlandNorth(143, true).setTemperatureRainfall(0.6f, 0.6f).setMinMaxHeight(0.2f, 0.5f).setColor(9676396).setBiomeName("wilderlandNorth");
		forodwaithCoast = new LOTRBiomeGenForodwaithCoast(144, false).setTemperatureRainfall(0.0f, 0.4f).setMinMaxHeight(0.0f, 0.5f).setColor(9214637).setBiomeName("forodwaithCoast");
		farHaradCoast = new LOTRBiomeGenFarHaradCoast(145, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.0f, 0.5f).setColor(8356472).setBiomeName("farHaradCoast");
		nearHaradRiverbank = new LOTRBiomeGenNearHaradRiverbank(146, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.1f, 0.1f).setColor(7183952).setBiomeName("nearHaradRiverbank");
		lossarnach = new LOTRBiomeGenLossarnach(147, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.1f, 0.2f).setColor(8439086).setBiomeName("lossarnach");
		imlothMelui = new LOTRBiomeGenImlothMelui(148, true).setTemperatureRainfall(1.0f, 1.2f).setMinMaxHeight(0.1f, 0.2f).setColor(14517608).setBiomeName("imlothMelui");
		nearHaradOasis = new LOTRBiomeGenNearHaradOasis(149, false).setTemperatureRainfall(1.2f, 0.8f).setMinMaxHeight(0.1f, 0.1f).setColor(832768).setBiomeName("nearHaradOasis");
		beachWhite = new LOTRBiomeGenBeach(150, false).setBeachBlock(LOTRMod.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
		harnedor = new LOTRBiomeGenHarnedor(151, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.1f, 0.3f).setColor(11449173).setBiomeName("harnedor");
		lamedon = new LOTRBiomeGenLamedon(152, true).setTemperatureRainfall(0.9f, 0.5f).setMinMaxHeight(0.2f, 0.2f).setColor(10927460).setBiomeName("lamedon");
		lamedonHills = new LOTRBiomeGenLamedonHills(153, true).setTemperatureRainfall(0.6f, 0.4f).setMinMaxHeight(0.6f, 0.9f).setColor(13555369).setBiomeName("lamedonHills");
		blackrootVale = new LOTRBiomeGenBlackrootVale(154, true).setTemperatureRainfall(0.8f, 0.9f).setMinMaxHeight(0.2f, 0.12f).setColor(7183921).setBiomeName("blackrootVale");
		andrast = new LOTRBiomeGenAndrast(155, true).setTemperatureRainfall(0.8f, 0.8f).setMinMaxHeight(0.2f, 0.2f).setColor(8885856).setBiomeName("andrast");
		pukel = new LOTRBiomeGenPukel(156, true).setTemperatureRainfall(0.7f, 0.7f).setMinMaxHeight(0.2f, 0.4f).setColor(5667394).setBiomeName("pukel");
		rhunLand = new LOTRBiomeGenRhunLand(157, true).setTemperatureRainfall(1.0f, 0.8f).setMinMaxHeight(0.1f, 0.3f).setColor(11381583).setBiomeName("rhunLand");
		rhunLandSteppe = new LOTRBiomeGenRhunLandSteppe(158, true).setTemperatureRainfall(1.0f, 0.3f).setMinMaxHeight(0.2f, 0.05f).setColor(11712354).setBiomeName("rhunLandSteppe");
		rhunLandHills = new LOTRBiomeGenRhunLandHills(159, true).setTemperatureRainfall(1.0f, 0.5f).setMinMaxHeight(0.6f, 0.8f).setColor(9342286).setBiomeName("rhunLandHills");
		rhunRedForest = new LOTRBiomeGenRhunRedForest(160, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.3f).setColor(9530430).setBiomeName("rhunRedForest");
		rhunIsland = new LOTRBiomeGenRhunIsland(161, false).setTemperatureRainfall(1.0f, 0.8f).setMinMaxHeight(0.1f, 0.4f).setColor(10858839).setBiomeName("rhunIsland");
		rhunIslandForest = new LOTRBiomeGenRhunIslandForest(162, false).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.1f, 0.4f).setColor(9533758).setBiomeName("rhunIslandForest");
		lastDesert = new LOTRBiomeGenLastDesert(163, true).setTemperatureRainfall(0.7f, 0.0f).setMinMaxHeight(0.2f, 0.05f).setColor(13878151).setBiomeName("lastDesert");
		windMountains = new LOTRBiomeGenWindMountains(164, true).setTemperatureRainfall(0.28f, 0.2f).setMinMaxHeight(2.0f, 2.0f).setColor(13882323).setBiomeName("windMountains");
		windMountainsFoothills = new LOTRBiomeGenWindMountainsFoothills(165, true).setTemperatureRainfall(0.4f, 0.6f).setMinMaxHeight(0.5f, 0.6f).setColor(10133354).setBiomeName("windMountainsFoothills");
		rivendell = new LOTRBiomeGenRivendell(166, true).setTemperatureRainfall(0.9f, 1.0f).setMinMaxHeight(0.15f, 0.3f).setColor(8828714).setBiomeName("rivendell");
		rivendellHills = new LOTRBiomeGenRivendellHills(167, true).setTemperatureRainfall(0.7f, 0.8f).setMinMaxHeight(2.0f, 0.5f).setColor(14210481).setBiomeName("rivendellHills");
		farHaradJungleMountains = new LOTRBiomeGenFarHaradJungleMountains(168, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(1.8f, 1.5f).setColor(6511174).setBiomeName("farHaradJungleMountains");
		halfTrollForest = new LOTRBiomeGenHalfTrollForest(169, true).setTemperatureRainfall(0.8f, 0.2f).setMinMaxHeight(0.3f, 0.4f).setColor(5992500).setBiomeName("halfTrollForest");
		farHaradKanuka = new LOTRBiomeGenKanuka(170, true).setTemperatureRainfall(1.0f, 1.0f).setMinMaxHeight(0.3f, 0.5f).setColor(5142552).setBiomeName("farHaradKanuka");
		utumno = new LOTRBiomeGenUtumno(0).setTemperatureRainfall(2.0f, 0.0f).setMinMaxHeight(0.0f, 0.0f).setColor(0).setBiomeName("utumno");
	}

	public static void updateWaterColor(int i, int j, int k) {
		int min = 0;
		int max = waterLimitSouth - waterLimitNorth;
		float latitude = (float) MathHelper.clamp_int(k - waterLimitNorth, min, max) / max;
		float[] northColors = waterColorNorth.getColorComponents(null);
		float[] southColors = waterColorSouth.getColorComponents(null);
		float dR = southColors[0] - northColors[0];
		float dG = southColors[1] - northColors[1];
		float dB = southColors[2] - northColors[2];
		float r = dR * latitude;
		float g = dG * latitude;
		float b = dB * latitude;
		Color water = new Color(r + northColors[0], g + northColors[1], b + northColors[2]);
		int waterRGB = water.getRGB();
		for (LOTRDimension dimension : LOTRDimension.values()) {
			for (LOTRBiome biome : dimension.biomeList) {
				if (biome == null || biome.biomeColors.hasCustomWater()) {
					continue;
				}
				biome.biomeColors.updateWater(waterRGB);
			}
		}
	}

	public void addBiomeF3Info(List info, World world, LOTRBiomeVariant variant, int i, int j, int k) {
		int colorRGB = color & 0xFFFFFF;
		StringBuilder colorString = new StringBuilder(Integer.toHexString(colorRGB));
		while (colorString.length() < 6) {
			colorString.insert(0, "0");
		}
		info.add("Middle-earth biome: " + getBiomeDisplayName() + ", ID: " + biomeID + ", c: #" + colorString);
		info.add("Variant: " + variant.variantName + ", loaded: " + LOTRBiomeVariantStorage.getSize(world));
	}

	public void addBiomeVariant(LOTRBiomeVariant v) {
		addBiomeVariant(v, 1.0f);
	}

	public void addBiomeVariant(LOTRBiomeVariant v, float f) {
		if (v.variantScale == LOTRBiomeVariant.VariantScale.ALL) {
			biomeVariantsLarge.add(v, f);
			biomeVariantsSmall.add(v, f);
		} else if (v.variantScale == LOTRBiomeVariant.VariantScale.LARGE) {
			biomeVariantsLarge.add(v, f);
		} else if (v.variantScale == LOTRBiomeVariant.VariantScale.SMALL) {
			biomeVariantsSmall.add(v, f);
		}
	}

	public void addBiomeVariantSet(LOTRBiomeVariant[] set) {
		for (LOTRBiomeVariant v : set) {
			addBiomeVariant(v);
		}
	}

	public boolean canSpawnHostilesInDay() {
		return false;
	}

	@Override
	public boolean canSpawnLightningBolt() {
		return !getEnableSnow() && super.canSpawnLightningBolt();
	}

	public boolean canSpawnTravellingTrader(Class entityClass) {
		return spawnableTraders.contains(entityClass);
	}

	public void clearBiomeVariants() {
		biomeVariantsLarge.clear();
		biomeVariantsSmall.clear();
		variantChance = 0.4f;
	}

	public void clearTravellingTraders() {
		spawnableTraders.clear();
	}

	@Override
	public BiomeGenBase createMutation() {
		return this;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		decorator.decorate(world, random, i, k);
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random random) {
		LOTRTreeType tree = decorator.getRandomTree(random);
		return tree.create(false, random);
	}

	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		int seaLevel = 63;
		double stoneNoiseFiller = modifyStoneNoiseForFiller(stoneNoise);
		int fillerDepthBase = (int) (stoneNoiseFiller / 4.0 + 5.0 + random.nextDouble() * 0.25);
		int fillerDepth = -1;
		Block top = topBlock;
		byte topMeta = (byte) topBlockMeta;
		Block filler = fillerBlock;
		byte fillerMeta = (byte) fillerBlockMeta;
		if (enableRocky && height >= 90) {
			float hFactor = (height - 90) / 10.0f;
			float thresh = 1.2f - hFactor * 0.2f;
			thresh = Math.max(thresh, 0.0f);
			double d12 = biomeTerrainNoise.func_151601_a(i * 0.03, k * 0.03);
			if (d12 + biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3) > thresh) {
				if (random.nextInt(5) == 0) {
					top = Blocks.gravel;
				} else {
					top = Blocks.stone;
				}
				topMeta = 0;
				filler = Blocks.stone;
				fillerMeta = 0;
				int prevHeight = height;
				height++;
				random.nextInt(20);// empty if block
				for (int j = height; j >= prevHeight; --j) {
					int index = xzIndex * ySize + j;
					blocks[index] = Blocks.stone;
					meta[index] = 0;
				}
			}
		}
		if (enablePodzol) {
			boolean podzol = false;
			if (topBlock == Blocks.grass) {
				float trees = decorator.treesPerChunk + getTreeIncreaseChance();
				trees = Math.max(trees, variant.treeFactor * 0.5f);
				if (trees >= 1.0f) {
					float thresh = 0.8f;
					thresh -= trees * 0.15f;
					thresh = Math.max(thresh, 0.0f);
					double d = 0.06;
					double randNoise = biomeTerrainNoise.func_151601_a(i * d, k * d);
					if (randNoise > thresh) {
						podzol = true;
					}
				}
			}
			if (podzol) {
				terrainRand.setSeed(world.getSeed());
				terrainRand.setSeed(terrainRand.nextLong() + i * 4668095025L + k * 1387590552L ^ world.getSeed());
				float pdzRand = terrainRand.nextFloat();
				if (pdzRand < 0.35f) {
					top = Blocks.dirt;
					topMeta = 2;
				} else if (pdzRand < 0.5f) {
					top = Blocks.dirt;
					topMeta = 1;
				} else if (pdzRand < 0.51f) {
					top = Blocks.gravel;
					topMeta = 0;
				}
			}
		}
		if (variant.hasMarsh && LOTRBiomeVariant.marshNoise.func_151601_a(i * 0.1, k * 0.1) > -0.1) {
			for (int j = ySize - 1; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				if (blocks[index] != null && blocks[index].getMaterial() == Material.air) {
					continue;
				}
				if (j != seaLevel - 1 || blocks[index] == Blocks.water) {
					break;
				}
				blocks[index] = Blocks.water;
				break;
			}
		}
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			if (j <= random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
				continue;
			}
			Block block = blocks[index];
			if (block == Blocks.air) {
				fillerDepth = -1;
				continue;
			}
			if (block != Blocks.stone) {
				continue;
			}
			if (fillerDepth == -1) {
				if (fillerDepthBase <= 0) {
					top = Blocks.air;
					topMeta = 0;
					filler = Blocks.stone;
					fillerMeta = 0;
				} else if (j >= seaLevel - 4 && j <= seaLevel + 1) {
					top = topBlock;
					topMeta = (byte) topBlockMeta;
					filler = fillerBlock;
					fillerMeta = (byte) fillerBlockMeta;
				}
				if (j < seaLevel && top == Blocks.air) {
					top = Blocks.water;
					topMeta = 0;
				}
				fillerDepth = fillerDepthBase;
				if (j >= seaLevel - 1) {
					blocks[index] = top;
					meta[index] = topMeta;
					continue;
				}
				blocks[index] = filler;
				meta[index] = fillerMeta;
				continue;
			}
			if (fillerDepth <= 0) {
				continue;
			}
			blocks[index] = filler;
			meta[index] = fillerMeta;
			if (--fillerDepth == 0) {
				boolean sand = false;
				if (filler == Blocks.sand) {
					if (fillerMeta == 1) {
						filler = LOTRMod.redSandstone;
					} else {
						filler = Blocks.sandstone;
					}
					fillerMeta = 0;
					sand = true;
				}
				if (filler == LOTRMod.whiteSand) {
					filler = LOTRMod.whiteSandstone;
					fillerMeta = 0;
					sand = true;
				}
				if (sand) {
					fillerDepth = 10 + random.nextInt(4);
				}
			}
			if ((this instanceof LOTRBiomeGenGondor || this instanceof LOTRBiomeGenIthilien || this instanceof LOTRBiomeGenDorEnErnil) && fillerDepth == 0 && filler == fillerBlock) {
				fillerDepth = 8 + random.nextInt(3);
				filler = LOTRMod.rock;
				fillerMeta = 1;
				continue;
			}
			if ((this instanceof LOTRBiomeGenRohan || this instanceof LOTRBiomeGenAdornland) && fillerDepth == 0 && filler == fillerBlock) {
				fillerDepth = 8 + random.nextInt(3);
				filler = LOTRMod.rock;
				fillerMeta = 2;
				continue;
			}
			if (!(this instanceof LOTRBiomeGenDorwinion) || fillerDepth != 0 || fillerBlock == LOTRMod.rock || filler != fillerBlock) {
				continue;
			}
			fillerDepth = 6 + random.nextInt(3);
			filler = LOTRMod.rock;
			fillerMeta = 5;
		}
		int rockDepth = (int) (stoneNoise * 6.0 + 2.0 + random.nextDouble() * 0.25);
		generateMountainTerrain(world, random, blocks, meta, i, k, xzIndex, ySize, height, rockDepth, variant);
		variant.generateVariantTerrain(world, random, blocks, meta, i, k, height, this);
	}

	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
	}

	public LOTREventSpawner.EventChance getBanditChance() {
		return banditChance;
	}

	public void setBanditChance(LOTREventSpawner.EventChance c) {
		banditChance = c;
	}

	public Class<? extends LOTREntityBandit> getBanditEntityClass() {
		if (banditEntityClass == null) {
			return LOTREntityBandit.class;
		}
		return banditEntityClass;
	}

	public void setBanditEntityClass(Class<? extends LOTREntityBandit> c) {
		banditEntityClass = c;
	}

	@SideOnly(Side.CLIENT)
	public int getBaseFoliageColor(int i, int j, int k) {
		LOTRBiomeVariant variant = ((LOTRWorldChunkManager) LOTRMod.proxy.getClientWorld().getWorldChunkManager()).getBiomeVariantAt(i, k);
		float temp = getFloatTemperature(i, j, k) + variant.tempBoost;
		float rain = rainfall + variant.rainBoost;
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerFoliage.getFoliageColor(temp, rain);
	}

	@SideOnly(Side.CLIENT)
	public int getBaseGrassColor(int i, int j, int k) {
		float temp = getFloatTemperature(i, j, k);
		float rain = rainfall;
		WorldChunkManager wcMgr = LOTRMod.proxy.getClientWorld().getWorldChunkManager();
		if (wcMgr instanceof LOTRWorldChunkManager) {
			LOTRBiomeVariant variant = ((LOTRWorldChunkManager) wcMgr).getBiomeVariantAt(i, k);
			temp += variant.tempBoost;
			rain += variant.rainBoost;
		}
		temp = MathHelper.clamp_float(temp, 0.0f, 1.0f);
		rain = MathHelper.clamp_float(rain, 0.0f, 1.0f);
		return ColorizerGrass.getGrassColor(temp, rain);
	}

	@SideOnly(Side.CLIENT)
	public int getBaseSkyColorByTemp(int i, int j, int k) {
		return super.getSkyColorByTemp(getFloatTemperature(i, j, k));
	}

	public LOTRAchievement getBiomeAchievement() {
		return null;
	}

	public String getBiomeDisplayName() {
		return StatCollector.translateToLocal("lotr.biome." + biomeName + ".name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int i, int j, int k) {
		return biomeColors.foliage != null ? biomeColors.foliage.getRGB() : getBaseFoliageColor(i, j, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int i, int j, int k) {
		return biomeColors.grass != null ? biomeColors.grass.getRGB() : getBaseGrassColor(i, j, k);
	}

	public abstract LOTRMusicRegion.Sub getBiomeMusic();

	public LOTRBiomeVariantList getBiomeVariantsLarge() {
		return biomeVariantsLarge;
	}

	public LOTRBiomeVariantList getBiomeVariantsSmall() {
		return biomeVariantsSmall;
	}

	public LOTRWaypoint.Region getBiomeWaypoints() {
		return null;
	}

	public LOTRRoadType.BridgeType getBridgeBlock() {
		return LOTRRoadType.BridgeType.DEFAULT;
	}

	public float getChanceToSpawnAnimals() {
		return 1.0f;
	}

	public void getCloudColor(Vec3 clouds) {
		if (biomeColors.clouds != null) {
			float[] colors = biomeColors.clouds.getColorComponents(null);
			clouds.xCoord *= colors[0];
			clouds.yCoord *= colors[1];
			clouds.zCoord *= colors[2];
		}
	}

	public boolean getEnableRain() {
		return enableRain;
	}

	public boolean getEnableRiver() {
		return true;
	}

	@Override
	public boolean getEnableSnow() {
		if (LOTRMod.isChristmas() && LOTRMod.proxy.isClient()) {
			return true;
		}
		return super.getEnableSnow();
	}

	public void getFogColor(Vec3 fog) {
		if (biomeColors.fog != null) {
			float[] colors = biomeColors.fog.getColorComponents(null);
			fog.xCoord *= colors[0];
			fog.yCoord *= colors[1];
			fog.zCoord *= colors[2];
		}
	}

	public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
		return npcSpawnList;
	}

	public BiomeGenBase.FlowerEntry getRandomFlower(World world, Random rand, int i, int j, int k) {
		return (BiomeGenBase.FlowerEntry) WeightedRandom.getRandomItem(rand, flowers);
	}

	public GrassBlockAndMeta getRandomGrass(Random random) {
		boolean fern = decorator.enableFern;
		boolean special = decorator.enableSpecialGrasses;
		if (fern && random.nextInt(3) == 0) {
			return new GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		if (special && random.nextInt(500) == 0) {
			return new GrassBlockAndMeta(LOTRMod.flaxPlant, 0);
		}
		if (random.nextInt(4) > 0) {
			if (special) {
				if (random.nextInt(200) == 0) {
					return new GrassBlockAndMeta(LOTRMod.tallGrass, 3);
				}
				if (random.nextInt(16) == 0) {
					return new GrassBlockAndMeta(LOTRMod.tallGrass, 1);
				}
				if (random.nextInt(10) == 0) {
					return new GrassBlockAndMeta(LOTRMod.tallGrass, 2);
				}
			}
			if (random.nextInt(80) == 0) {
				return new GrassBlockAndMeta(LOTRMod.tallGrass, 4);
			}
			return new GrassBlockAndMeta(LOTRMod.tallGrass, 0);
		}
		if (random.nextInt(3) == 0) {
			return new GrassBlockAndMeta(LOTRMod.clover, 0);
		}
		return new GrassBlockAndMeta(Blocks.tallgrass, 1);
	}

	public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
		WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
		int i = random.nextInt(3);
		switch (i) {
			case 0: {
				doubleFlowerGen.func_150548_a(1);
				break;
			}
			case 1: {
				doubleFlowerGen.func_150548_a(4);
				break;
			}
			case 2: {
				doubleFlowerGen.func_150548_a(5);
			}
		}
		return doubleFlowerGen;
	}

	public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		if (decorator.enableFern && random.nextInt(4) == 0) {
			generator.func_150548_a(3);
		} else {
			generator.func_150548_a(2);
		}
		return generator;
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		GrassBlockAndMeta obj = getRandomGrass(random);
		return new WorldGenTallGrass(obj.block, obj.meta);
	}

	public LOTRRoadType getRoadBlock() {
		return LOTRRoadType.PATH;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float f) {
		if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
			return 0;
		}
		if (biomeColors.sky != null) {
			return biomeColors.sky.getRGB();
		}
		return super.getSkyColorByTemp(f);
	}

	public int getSnowHeight() {
		return 0;
	}

	@Override
	public List getSpawnableList(EnumCreatureType creatureType) {
		if (creatureType == creatureType_LOTRAmbient) {
			return spawnableLOTRAmbientList;
		}
		return super.getSpawnableList(creatureType);
	}

	public WorldGenAbstractTree getTreeGen(World world, Random random, int i, int j, int k) {
		LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager) world.getWorldChunkManager();
		LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
		LOTRTreeType tree = decorator.getRandomTreeForVariant(random, variant);
		return tree.create(false, random);
	}

	public float getTreeIncreaseChance() {
		return 0.1f;
	}

	public boolean hasDomesticAnimals() {
		return false;
	}

	public boolean hasFog() {
		return biomeColors.foggy;
	}

	public boolean hasSeasonalGrass() {
		return temperature > 0.3f && temperature < 1.0f;
	}

	public boolean hasSky() {
		return true;
	}

	public boolean isDwarvenBiome(World world) {
		if (initDwarven) {
			return isDwarven;
		}
		initDwarven = true;
		isDwarven = npcSpawnList.containsEntityClassByDefault(LOTREntityDwarf.class, world) && !npcSpawnList.containsEntityClassByDefault(LOTREntityWickedDwarf.class, world);
		return isDwarven;
	}

	public boolean isHiddenBiome() {
		return false;
	}

	public boolean isRiver() {
		return false;
	}

	public boolean isWateryBiome() {
		return heightBaseParameter < 0.0f;
	}

	public double modifyStoneNoiseForFiller(double stoneNoise) {
		return stoneNoise;
	}

	@Override
	public void plantFlower(World world, Random rand, int x, int y, int z) {
		BiomeGenBase.FlowerEntry flower = getRandomFlower(world, rand, x, y, z);
		if (flower == null || flower.block == null || !flower.block.canBlockStay(world, x, y, z)) {
			return;
		}
		world.setBlock(x, y, z, flower.block, flower.metadata, 3);
	}

	public void registerForestFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(LOTRMod.bluebell, 0, 5);
		addFlower(LOTRMod.marigold, 0, 10);
	}

	public void registerHaradFlowers() {
		flowers.clear();
		addFlower(LOTRMod.haradFlower, 0, 10);
		addFlower(LOTRMod.haradFlower, 1, 10);
		addFlower(LOTRMod.haradFlower, 2, 5);
		addFlower(LOTRMod.haradFlower, 3, 5);
	}

	public void registerJungleFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(LOTRMod.haradFlower, 2, 20);
		addFlower(LOTRMod.haradFlower, 3, 20);
	}

	public void registerMountainsFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(Blocks.red_flower, 1, 10);
		addFlower(LOTRMod.bluebell, 0, 5);
	}

	public void registerPlainsFlowers() {
		flowers.clear();
		addFlower(Blocks.red_flower, 4, 3);
		addFlower(Blocks.red_flower, 5, 3);
		addFlower(Blocks.red_flower, 6, 3);
		addFlower(Blocks.red_flower, 7, 3);
		addFlower(Blocks.red_flower, 0, 20);
		addFlower(Blocks.red_flower, 3, 20);
		addFlower(Blocks.red_flower, 8, 20);
		addFlower(Blocks.yellow_flower, 0, 30);
		addFlower(LOTRMod.bluebell, 0, 5);
		addFlower(LOTRMod.marigold, 0, 10);
		addFlower(LOTRMod.lavender, 0, 5);
	}

	public void registerRhunForestFlowers() {
		registerForestFlowers();
		addFlower(LOTRMod.marigold, 0, 10);
		addFlower(LOTRMod.rhunFlower, 0, 10);
		addFlower(LOTRMod.rhunFlower, 1, 10);
		addFlower(LOTRMod.rhunFlower, 2, 10);
		addFlower(LOTRMod.rhunFlower, 3, 10);
		addFlower(LOTRMod.rhunFlower, 4, 10);
	}

	public void registerRhunPlainsFlowers() {
		registerPlainsFlowers();
		addFlower(LOTRMod.marigold, 0, 10);
		addFlower(LOTRMod.rhunFlower, 0, 10);
		addFlower(LOTRMod.rhunFlower, 1, 10);
		addFlower(LOTRMod.rhunFlower, 2, 10);
		addFlower(LOTRMod.rhunFlower, 3, 10);
		addFlower(LOTRMod.rhunFlower, 4, 10);
	}

	public void registerSavannaFlowers() {
		flowers.clear();
		addDefaultFlowers();
	}

	public void registerSwampFlowers() {
		flowers.clear();
		addDefaultFlowers();
	}

	public void registerTaigaFlowers() {
		flowers.clear();
		addDefaultFlowers();
		addFlower(Blocks.red_flower, 1, 10);
		addFlower(LOTRMod.bluebell, 0, 5);
	}

	public void registerTravellingTrader(Class entityClass) {
		spawnableTraders.add(entityClass);
		LOTREventSpawner.createTraderSpawner(entityClass);
	}

	@Override
	public LOTRBiome setBiomeName(String s) {
		return (LOTRBiome) super.setBiomeName(s);
	}

	@Override
	public LOTRBiome setColor(int color) {
		Integer existingBiomeID = biomeDimension.colorsToBiomeIDs.get(color |= 0xFF000000);
		if (existingBiomeID != null) {
			throw new RuntimeException("LOTR biome (ID " + biomeID + ") is duplicating the color of another LOTR biome (ID " + existingBiomeID + ")");
		}
		biomeDimension.colorsToBiomeIDs.put(color, biomeID);
		return (LOTRBiome) super.setColor(color);
	}

	public LOTRBiome setMinMaxHeight(float f, float f1) {
		heightBaseParameter = f;
		f -= 2.0f;
		rootHeight = f + 0.2f;
		heightVariation = f1 / 2.0f;
		return this;
	}

	@Override
	public LOTRBiome setTemperatureRainfall(float f, float f1) {
		super.setTemperatureRainfall(f, f1);
		return this;
	}

	public int spawnCountMultiplier() {
		return 1;
	}

	public static class BiomeColors {
		public static int DEFAULT_WATER = 7186907;
		public LOTRBiome theBiome;
		public Color grass;
		public Color foliage;
		public Color sky;
		public Color clouds;
		public Color fog;
		public boolean foggy;
		public boolean hasCustomWater;

		public BiomeColors(LOTRBiome biome) {
			theBiome = biome;
		}

		public boolean hasCustomWater() {
			return hasCustomWater;
		}

		public void resetClouds() {
			clouds = null;
		}

		public void resetFog() {
			fog = null;
		}

		public void resetFoliage() {
			foliage = null;
		}

		public void resetGrass() {
			grass = null;
		}

		public void resetSky() {
			sky = null;
		}

		public void resetWater() {
			setWater(DEFAULT_WATER);
		}

		public void setClouds(int rgb) {
			clouds = new Color(rgb);
		}

		public void setFog(int rgb) {
			fog = new Color(rgb);
		}

		public void setFoggy(boolean flag) {
			foggy = flag;
		}

		public void setFoliage(int rgb) {
			foliage = new Color(rgb);
		}

		public void setGrass(int rgb) {
			grass = new Color(rgb);
		}

		public void setSky(int rgb) {
			sky = new Color(rgb);
		}

		public void setWater(int rgb) {
			theBiome.waterColorMultiplier = rgb;
			if (rgb != DEFAULT_WATER) {
				hasCustomWater = true;
			}
		}

		public void updateWater(int rgb) {
			theBiome.waterColorMultiplier = rgb;
		}
	}

	public static class BiomeTerrain {
		public double xzScale = -1.0;
		public double heightStretchFactor = -1.0;

		public BiomeTerrain(LOTRBiome biome) {
		}

		public double getHeightStretchFactor() {
			return heightStretchFactor;
		}

		public void setHeightStretchFactor(double d) {
			heightStretchFactor = d;
		}

		public double getXZScale() {
			return xzScale;
		}

		public void setXZScale(double d) {
			xzScale = d;
		}

		public boolean hasHeightStretchFactor() {
			return heightStretchFactor != -1.0;
		}

		public boolean hasXZScale() {
			return xzScale != -1.0;
		}

		public void resetHeightStretchFactor() {
			heightStretchFactor = -1.0;
		}

		public void resetXZScale() {
			xzScale = -1.0;
		}
	}

	public static class GrassBlockAndMeta {
		public Block block;
		public int meta;

		public GrassBlockAndMeta(Block b, int i) {
			block = b;
			meta = i;
		}
	}

}
