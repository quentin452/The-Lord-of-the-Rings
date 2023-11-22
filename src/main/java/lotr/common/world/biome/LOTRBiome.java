package lotr.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfigBiomeID;
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
        river = (new LOTRBiomeGenRiver(LOTRConfigBiomeID.riverid, false)).setMinMaxHeight(-0.5F, 0.0F).setColor(3570869).setBiomeName("river");
        rohan = (new LOTRBiomeGenRohan(LOTRConfigBiomeID.rohanid, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.15F).setColor(7384389).setBiomeName("rohan");
        mistyMountains = (new LOTRBiomeGenMistyMountains(LOTRConfigBiomeID.mistyMountainsid, true)).setTemperatureRainfall(0.2F, 0.5F).setMinMaxHeight(2.0F, 2.0F).setColor(15263713).setBiomeName("mistyMountains");
        shire = (new LOTRBiomeGenShire(LOTRConfigBiomeID.shireid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.15F, 0.3F).setColor(6794549).setBiomeName("shire");
        shireWoodlands = (new LOTRBiomeGenShireWoodlands(LOTRConfigBiomeID.shireWoodlandsid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.3F, 0.5F).setColor(4486966).setBiomeName("shireWoodlands");
        mordor = (new LOTRBiomeGenMordor(LOTRConfigBiomeID.mordorid, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.3F, 0.5F).setColor(1118222).setBiomeName("mordor");
        mordorMountains = (new LOTRBiomeGenMordorMountains(LOTRConfigBiomeID.mordorMountainsid, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(2.0F, 3.0F).setColor(5328200).setBiomeName("mordorMountains");
        gondor = (new LOTRBiomeGenGondor(LOTRConfigBiomeID.gondorid, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.1F, 0.15F).setColor(8959045).setBiomeName("gondor");
        whiteMountains = (new LOTRBiomeGenWhiteMountains(LOTRConfigBiomeID.whiteMountainsid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(1.5F, 2.0F).setColor(15066600).setBiomeName("whiteMountains");
        lothlorien = (new LOTRBiomeGenLothlorien(LOTRConfigBiomeID.lothlorienid, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.3F).setColor(16504895).setBiomeName("lothlorien");
        celebrant = (new LOTRBiomeGenCelebrant(LOTRConfigBiomeID.celebrantid, true)).setTemperatureRainfall(1.1F, 1.1F).setMinMaxHeight(0.1F, 0.05F).setColor(7647046).setBiomeName("celebrant");
        ironHills = (new LOTRBiomeGenIronHills(LOTRConfigBiomeID.ironHillsid, true)).setTemperatureRainfall(0.27F, 0.4F).setMinMaxHeight(0.3F, 1.4F).setColor(9142093).setBiomeName("ironHills");
        deadMarshes = (new LOTRBiomeGenDeadMarshes(LOTRConfigBiomeID.deadMarshesid, true)).setTemperatureRainfall(0.4F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(7303999).setBiomeName("deadMarshes");
        trollshaws = (new LOTRBiomeGenTrollshaws(LOTRConfigBiomeID.trollshawsid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.15F, 1.0F).setColor(5798959).setBiomeName("trollshaws");
        woodlandRealm = (new LOTRBiomeGenWoodlandRealm(LOTRConfigBiomeID.woodlandRealmid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.3F).setColor(4089126).setBiomeName("woodlandRealm");
        mirkwoodCorrupted = (new LOTRBiomeGenMirkwoodCorrupted(LOTRConfigBiomeID.mirkwoodCorruptedid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.4F).setColor(3032091).setBiomeName("mirkwoodCorrupted");
        rohanUrukHighlands = (new LOTRBiomeGenRohanUruk(LOTRConfigBiomeID.rohanUrukHighlandsid, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.8F, 0.3F).setColor(8295258).setBiomeName("rohanUrukHighlands");
        emynMuil = (new LOTRBiomeGenEmynMuil(LOTRConfigBiomeID.emynMuilid, true)).setTemperatureRainfall(0.5F, 0.9F).setMinMaxHeight(0.2F, 0.8F).setColor(9866354).setBiomeName("emynMuil");
        ithilien = (new LOTRBiomeGenIthilien(LOTRConfigBiomeID.ithilienid, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.15F, 0.5F).setColor(7710516).setBiomeName("ithilien");
        pelargir = (new LOTRBiomeGenPelargir(LOTRConfigBiomeID.pelargirid, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.08F, 0.2F).setColor(11256145).setBiomeName("pelargir");
        loneLands = (new LOTRBiomeGenLoneLands(LOTRConfigBiomeID.loneLandsid, true)).setTemperatureRainfall(0.6F, 0.5F).setMinMaxHeight(0.15F, 0.4F).setColor(8562762).setBiomeName("loneLands");
        loneLandsHills = (new LOTRBiomeGenLoneLandsHills(LOTRConfigBiomeID.loneLandsHillsid, false)).setTemperatureRainfall(0.6F, 0.5F).setMinMaxHeight(0.6F, 0.8F).setColor(8687182).setBiomeName("loneLandsHills");
        dunland = (new LOTRBiomeGenDunland(LOTRConfigBiomeID.dunlandid, true)).setTemperatureRainfall(0.4F, 0.7F).setMinMaxHeight(0.3F, 0.5F).setColor(6920524).setBiomeName("dunland");
        fangorn = (new LOTRBiomeGenFangorn(LOTRConfigBiomeID.fangornid, true)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.4F).setColor(4355353).setBiomeName("fangorn");
        angle = (new LOTRBiomeGenAngle(LOTRConfigBiomeID.angleid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.15F, 0.3F).setColor(9416527).setBiomeName("angle");
        ettenmoors = (new LOTRBiomeGenEttenmoors(LOTRConfigBiomeID.ettenmoorsid, true)).setTemperatureRainfall(0.2F, 0.6F).setMinMaxHeight(0.5F, 0.6F).setColor(8161626).setBiomeName("ettenmoors");
        oldForest = (new LOTRBiomeGenOldForest(LOTRConfigBiomeID.oldForestid, true)).setTemperatureRainfall(0.5F, 1.0F).setMinMaxHeight(0.2F, 0.3F).setColor(4551995).setBiomeName("oldForest");
        harondor = (new LOTRBiomeGenHarondor(LOTRConfigBiomeID.harondorid, true)).setTemperatureRainfall(1.0F, 0.6F).setMinMaxHeight(0.2F, 0.3F).setColor(10663238).setBiomeName("harondor");
        eriador = (new LOTRBiomeGenEriador(LOTRConfigBiomeID.eriadorid, true)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.1F, 0.4F).setColor(7054916).setBiomeName("eriador");
        eriadorDowns = (new LOTRBiomeGenEriadorDowns(LOTRConfigBiomeID.eriadorDownsid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.5F, 0.5F).setColor(7638087).setBiomeName("eriadorDowns");
        erynVorn = (new LOTRBiomeGenErynVorn(LOTRConfigBiomeID.erynVornid, false)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.1F, 0.4F).setColor(4357965).setBiomeName("erynVorn");
        greyMountains = (new LOTRBiomeGenGreyMountains(LOTRConfigBiomeID.greyMountainsid, true)).setTemperatureRainfall(0.28F, 0.2F).setMinMaxHeight(1.8F, 2.0F).setColor(13290689).setBiomeName("greyMountains");
        midgewater = (new LOTRBiomeGenMidgewater(LOTRConfigBiomeID.midgewaterid, true)).setTemperatureRainfall(0.6F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(6001495).setBiomeName("midgewater");
        brownLands = (new LOTRBiomeGenBrownLands(LOTRConfigBiomeID.brownLandsid, true)).setTemperatureRainfall(1.0F, 0.2F).setMinMaxHeight(0.2F, 0.2F).setColor(8552016).setBiomeName("brownLands");
        ocean = (new LOTRBiomeGenOcean(LOTRConfigBiomeID.oceanid, false)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-1.0F, 0.3F).setColor(153997).setBiomeName("ocean");
        anduinHills = (new LOTRBiomeGenAnduin(LOTRConfigBiomeID.anduinHillsid, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.6F, 0.4F).setColor(7058012).setBiomeName("anduinHills");
        meneltarma = (new LOTRBiomeGenMeneltarma(LOTRConfigBiomeID.meneltarmaid, false)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.1F, 0.2F).setColor(9549658).setBiomeName("meneltarma");
        gladdenFields = (new LOTRBiomeGenGladdenFields(LOTRConfigBiomeID.gladdenFieldsid, true)).setTemperatureRainfall(0.6F, 1.2F).setMinMaxHeight(0.0F, 0.1F).setColor(5020505).setBiomeName("gladdenFields");
        lothlorienEdge = (new LOTRBiomeGenLothlorienEdge(LOTRConfigBiomeID.lothlorienEdgeid, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.2F).setColor(13944387).setBiomeName("lothlorienEdge");
        forodwaith = (new LOTRBiomeGenForodwaith(LOTRConfigBiomeID.forodwaithid, true)).setTemperatureRainfall(0.0F, 0.2F).setMinMaxHeight(0.1F, 0.1F).setColor(14211282).setBiomeName("forodwaith");
        enedwaith = (new LOTRBiomeGenEnedwaith(LOTRConfigBiomeID.enedwaithid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.3F).setColor(8038479).setBiomeName("enedwaith");
        angmar = (new LOTRBiomeGenAngmar(LOTRConfigBiomeID.angmarid, true)).setTemperatureRainfall(0.2F, 0.2F).setMinMaxHeight(0.2F, 0.6F).setColor(5523247).setBiomeName("angmar");
        eregion = (new LOTRBiomeGenEregion(LOTRConfigBiomeID.eregionid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.2F, 0.3F).setColor(6656072).setBiomeName("eregion");
        lindon = (new LOTRBiomeGenLindon(LOTRConfigBiomeID.lindonid, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.15F, 0.2F).setColor(7646533).setBiomeName("lindon");
        lindonWoodlands = (new LOTRBiomeGenLindonWoodlands(LOTRConfigBiomeID.lindonWoodlandsid, false)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.2F, 0.5F).setColor(1996591).setBiomeName("lindonWoodlands");
        eastBight = (new LOTRBiomeGenEastBight(LOTRConfigBiomeID.eastBightid, true)).setTemperatureRainfall(0.8F, 0.3F).setMinMaxHeight(0.15F, 0.05F).setColor(9082205).setBiomeName("eastBight");
        blueMountains = (new LOTRBiomeGenBlueMountains(LOTRConfigBiomeID.blueMountainsid, true)).setTemperatureRainfall(0.22F, 0.8F).setMinMaxHeight(1.0F, 2.5F).setColor(13228770).setBiomeName("blueMountains");
        mirkwoodMountains = (new LOTRBiomeGenMirkwoodMountains(LOTRConfigBiomeID.mirkwoodMountainsid, true)).setTemperatureRainfall(0.28F, 0.9F).setMinMaxHeight(1.2F, 1.5F).setColor(2632989).setBiomeName("mirkwoodMountains");
        wilderland = (new LOTRBiomeGenWilderland(LOTRConfigBiomeID.wilderlandid, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.2F, 0.4F).setColor(9612368).setBiomeName("wilderland");
        dagorlad = (new LOTRBiomeGenDagorlad(LOTRConfigBiomeID.dagorladid, true)).setTemperatureRainfall(1.0F, 0.2F).setMinMaxHeight(0.1F, 0.05F).setColor(7036741).setBiomeName("dagorlad");
        nurn = (new LOTRBiomeGenNurn(LOTRConfigBiomeID.nurnid, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.1F, 0.2F).setColor(2630683).setBiomeName("nurn");
        nurnen = (new LOTRBiomeGenNurnen(LOTRConfigBiomeID.nurnenid, false)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(-1.0F, 0.3F).setColor(931414).setBiomeName("nurnen");
        nurnMarshes = (new LOTRBiomeGenNurnMarshes(LOTRConfigBiomeID.nurnMarshesid, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.0F, 0.1F).setColor(4012843).setBiomeName("nurnMarshes");
        adornland = (new LOTRBiomeGenAdornland(LOTRConfigBiomeID.adornlandid, true)).setTemperatureRainfall(0.7F, 0.6F).setMinMaxHeight(0.2F, 0.2F).setColor(7838543).setBiomeName("adornland");
        angmarMountains = (new LOTRBiomeGenAngmarMountains(LOTRConfigBiomeID.angmarMountainsid, true)).setTemperatureRainfall(0.25F, 0.1F).setMinMaxHeight(1.6F, 1.5F).setColor(13619147).setBiomeName("angmarMountains");
        anduinMouth = (new LOTRBiomeGenAnduinMouth(LOTRConfigBiomeID.anduinMouthid, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5089363).setBiomeName("anduinMouth");
        entwashMouth = (new LOTRBiomeGenEntwashMouth(LOTRConfigBiomeID.entwashMouthid, true)).setTemperatureRainfall(0.5F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5612358).setBiomeName("entwashMouth");
        dorEnErnil = (new LOTRBiomeGenDorEnErnil(LOTRConfigBiomeID.dorEnErnilid, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.07F, 0.2F).setColor(9355077).setBiomeName("dorEnErnil");
        dorEnErnilHills = (new LOTRBiomeGenDorEnErnilHills(LOTRConfigBiomeID.dorEnErnilHillsid, false)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.5F, 0.5F).setColor(8560707).setBiomeName("dorEnErnilHills");
        fangornWasteland = (new LOTRBiomeGenFangornWasteland(LOTRConfigBiomeID.fangornWastelandid, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.2F, 0.4F).setColor(6782028).setBiomeName("fangornWasteland");
        rohanWoodlands = (new LOTRBiomeGenRohanWoodlands(LOTRConfigBiomeID.rohanWoodlandsid, false)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(5736246).setBiomeName("rohanWoodlands");
        gondorWoodlands = (new LOTRBiomeGenGondorWoodlands(LOTRConfigBiomeID.gondorWoodlandsid, false)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.2F).setColor(5867307).setBiomeName("gondorWoodlands");
        lake = (new LOTRBiomeGenLake(LOTRConfigBiomeID.lakeid, false)).setColor(3433630).setBiomeName("lake");
        lindonCoast = (new LOTRBiomeGenLindonCoast(LOTRConfigBiomeID.lindonCoastid, false)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.0F, 0.5F).setColor(9278870).setBiomeName("lindonCoast");
        barrowDowns = (new LOTRBiomeGenBarrowDowns(LOTRConfigBiomeID.barrowDownsid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.3F, 0.4F).setColor(8097362).setBiomeName("barrowDowns");
        longMarshes = (new LOTRBiomeGenLongMarshes(LOTRConfigBiomeID.longMarshesid, true)).setTemperatureRainfall(0.6F, 0.9F).setMinMaxHeight(0.0F, 0.1F).setColor(7178054).setBiomeName("longMarshes");
        fangornClearing = (new LOTRBiomeGenFangornClearing(LOTRConfigBiomeID.fangornClearingid, false)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.1F).setColor(5877050).setBiomeName("fangornClearing");
        ithilienHills = (new LOTRBiomeGenIthilienHills(LOTRConfigBiomeID.ithilienHillsid, false)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.6F, 0.6F).setColor(6985792).setBiomeName("ithilienHills");
        ithilienWasteland = (new LOTRBiomeGenIthilienWasteland(LOTRConfigBiomeID.ithilienWastelandid, true)).setTemperatureRainfall(0.6F, 0.6F).setMinMaxHeight(0.15F, 0.2F).setColor(8030031).setBiomeName("ithilienWasteland");
        nindalf = (new LOTRBiomeGenNindalf(LOTRConfigBiomeID.nindalfid, true)).setTemperatureRainfall(0.4F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(7111750).setBiomeName("nindalf");
        coldfells = (new LOTRBiomeGenColdfells(LOTRConfigBiomeID.coldfellsid, true)).setTemperatureRainfall(0.25F, 0.8F).setMinMaxHeight(0.4F, 0.8F).setColor(8296018).setBiomeName("coldfells");
        nanCurunir = (new LOTRBiomeGenNanCurunir(LOTRConfigBiomeID.nanCurunirid, true)).setTemperatureRainfall(0.6F, 0.4F).setMinMaxHeight(0.2F, 0.1F).setColor(7109714).setBiomeName("nanCurunir");
        whiteDowns = (new LOTRBiomeGenWhiteDowns(LOTRConfigBiomeID.whiteDownsid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.6F, 0.6F).setColor(10210937).setBiomeName("whiteDowns");
        swanfleet = (new LOTRBiomeGenSwanfleet(LOTRConfigBiomeID.swanfleetid, true)).setTemperatureRainfall(0.8F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(6265945).setBiomeName("swanfleet");
        pelennor = (new LOTRBiomeGenPelennor(LOTRConfigBiomeID.pelennorid, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.1F, 0.02F).setColor(11258955).setBiomeName("pelennor");
        minhiriath = (new LOTRBiomeGenMinhiriath(LOTRConfigBiomeID.minhiriathid, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.1F, 0.2F).setColor(7380550).setBiomeName("minhiriath");
        erebor = (new LOTRBiomeGenErebor(LOTRConfigBiomeID.ereborid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.4F, 0.6F).setColor(7499093).setBiomeName("erebor");
        mirkwoodNorth = (new LOTRBiomeGenMirkwoodNorth(LOTRConfigBiomeID.mirkwoodNorthid, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.2F, 0.4F).setColor(3822115).setBiomeName("mirkwoodNorth");
        woodlandRealmHills = (new LOTRBiomeGenWoodlandRealmHills(LOTRConfigBiomeID.woodlandRealmHillsid, false)).setTemperatureRainfall(0.8F, 0.6F).setMinMaxHeight(0.9F, 0.7F).setColor(3624991).setBiomeName("woodlandRealmHills");
        nanUngol = (new LOTRBiomeGenNanUngol(LOTRConfigBiomeID.nanUngolid, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.1F, 0.4F).setColor(656641).setBiomeName("nanUngol");
        pinnathGelin = (new LOTRBiomeGenPinnathGelin(LOTRConfigBiomeID.pinnathGelinid, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.5F, 0.5F).setColor(9946693).setBiomeName("pinnathGelin");
        island = (new LOTRBiomeGenOcean(LOTRConfigBiomeID.islandid, false)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.0F, 0.3F).setColor(10138963).setBiomeName("island");
        forodwaithMountains = (new LOTRBiomeGenForodwaithMountains(LOTRConfigBiomeID.forodwaithMountainsid, true)).setTemperatureRainfall(0.0F, 0.2F).setMinMaxHeight(2.0F, 2.0F).setColor(15592942).setBiomeName("forodwaithMountains");
        mistyMountainsFoothills = (new LOTRBiomeGenMistyMountainsFoothills(LOTRConfigBiomeID.mistyMountainsFoothillsid, true)).setTemperatureRainfall(0.25F, 0.6F).setMinMaxHeight(0.7F, 0.9F).setColor(12501430).setBiomeName("mistyMountainsFoothills");
        greyMountainsFoothills = (new LOTRBiomeGenGreyMountainsFoothills(LOTRConfigBiomeID.greyMountainsFoothillsid, true)).setTemperatureRainfall(0.5F, 0.7F).setMinMaxHeight(0.5F, 0.9F).setColor(9148000).setBiomeName("greyMountainsFoothills");
        blueMountainsFoothills = (new LOTRBiomeGenBlueMountainsFoothills(LOTRConfigBiomeID.blueMountainsFoothillsid, true)).setTemperatureRainfall(0.5F, 0.8F).setMinMaxHeight(0.5F, 0.9F).setColor(11253170).setBiomeName("blueMountainsFoothills");
        tundra = (new LOTRBiomeGenTundra(LOTRConfigBiomeID.tundraid, true)).setTemperatureRainfall(0.1F, 0.3F).setMinMaxHeight(0.1F, 0.2F).setColor(12366486).setBiomeName("tundra");
        taiga = (new LOTRBiomeGenTaiga(LOTRConfigBiomeID.taigaid, true)).setTemperatureRainfall(0.1F, 0.7F).setMinMaxHeight(0.1F, 0.5F).setColor(6526543).setBiomeName("taiga");
        breeland = (new LOTRBiomeGenBreeland(LOTRConfigBiomeID.breelandid, true)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.1F, 0.2F).setColor(6861625).setBiomeName("breeland");
        chetwood = (new LOTRBiomeGenChetwood(LOTRConfigBiomeID.chetwoodid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(4424477).setBiomeName("chetwood");
        forodwaithGlacier = (new LOTRBiomeGenForodwaithGlacier(LOTRConfigBiomeID.forodwaithGlacierid, true)).setTemperatureRainfall(0.0F, 0.1F).setMinMaxHeight(1.0F, 0.1F).setColor(9424096).setBiomeName("forodwaithGlacier");
        whiteMountainsFoothills = (new LOTRBiomeGenWhiteMountainsFoothills(LOTRConfigBiomeID.whiteMountainsFoothillsid, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.5F, 0.9F).setColor(12635575).setBiomeName("whiteMountainsFoothills");
        beach = (new LOTRBiomeGenBeach(LOTRConfigBiomeID.beachid, false)).setBeachBlock(Blocks.sand, 0).setColor(14404247).setBiomeName("beach");
        beachGravel = (new LOTRBiomeGenBeach(LOTRConfigBiomeID.beachGravelid, false)).setBeachBlock(Blocks.gravel, 0).setColor(9868704).setBiomeName("beachGravel");
        nearHarad = (new LOTRBiomeGenNearHarad(LOTRConfigBiomeID.nearHaradid, true)).setTemperatureRainfall(1.5F, 0.1F).setMinMaxHeight(0.2F, 0.1F).setColor(14205815).setBiomeName("nearHarad");
        farHarad = (new LOTRBiomeGenFarHaradSavannah(LOTRConfigBiomeID.farHaradid, true)).setTemperatureRainfall(1.2F, 0.2F).setMinMaxHeight(0.1F, 0.1F).setColor(9740353).setBiomeName("farHarad");
        haradMountains = (new LOTRBiomeGenHaradMountains(LOTRConfigBiomeID.haradMountainsid, true)).setTemperatureRainfall(0.9F, 0.5F).setMinMaxHeight(1.8F, 2.0F).setColor(9867381).setBiomeName("haradMountains");
        umbar = (new LOTRBiomeGenUmbar(LOTRConfigBiomeID.umbarid, true)).setTemperatureRainfall(0.9F, 0.6F).setMinMaxHeight(0.1F, 0.2F).setColor(9542740).setBiomeName("umbar");
        farHaradJungle = (new LOTRBiomeGenFarHaradJungle(LOTRConfigBiomeID.farHaradJungleid, true)).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(4944931).setBiomeName("farHaradJungle");
        umbarHills = (new LOTRBiomeGenUmbar(LOTRConfigBiomeID.umbarHillsid, false)).setTemperatureRainfall(0.8F, 0.5F).setMinMaxHeight(1.2F, 0.8F).setColor(8226378).setBiomeName("umbarHills");
        nearHaradHills = (new LOTRBiomeGenNearHaradHills(LOTRConfigBiomeID.nearHaradHillsid, false)).setTemperatureRainfall(1.2F, 0.3F).setMinMaxHeight(0.5F, 0.8F).setColor(12167010).setBiomeName("nearHaradHills");
        farHaradJungleLake = (new LOTRBiomeGenFarHaradJungleLake(LOTRConfigBiomeID.farHaradJungleLakeid, false)).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(-0.5F, 0.2F).setColor(2271948).setBiomeName("farHaradJungleLake");
        lostladen = (new LOTRBiomeGenLostladen(LOTRConfigBiomeID.lostladenid, true)).setTemperatureRainfall(1.2F, 0.2F).setMinMaxHeight(0.2F, 0.1F).setColor(10658661).setBiomeName("lostladen");
        farHaradForest = (new LOTRBiomeGenFarHaradForest(LOTRConfigBiomeID.farHaradForestid, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.3F, 0.4F).setColor(3703325).setBiomeName("farHaradForest");
        nearHaradFertile = (new LOTRBiomeGenNearHaradFertile(LOTRConfigBiomeID.nearHaradFertileid, true)).setTemperatureRainfall(1.2F, 0.7F).setMinMaxHeight(0.2F, 0.1F).setColor(10398286).setBiomeName("nearHaradFertile");
        pertorogwaith = (new LOTRBiomeGenPertorogwaith(LOTRConfigBiomeID.pertorogwaithid, true)).setTemperatureRainfall(0.7F, 0.1F).setMinMaxHeight(0.2F, 0.5F).setColor(8879706).setBiomeName("pertorogwaith");
        umbarForest = (new LOTRBiomeGenUmbarForest(LOTRConfigBiomeID.umbarForestid, false)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.3F).setColor(7178042).setBiomeName("umbarForest");
        farHaradJungleEdge = (new LOTRBiomeGenFarHaradJungleEdge(LOTRConfigBiomeID.farHaradJungleEdgeid, true)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(7440430).setBiomeName("farHaradJungleEdge");
        tauredainClearing = (new LOTRBiomeGenTauredainClearing(LOTRConfigBiomeID.tauredainClearingid, true)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(10796101).setBiomeName("tauredainClearing");
        gulfHarad = (new LOTRBiomeGenGulfHarad(LOTRConfigBiomeID.gulfHaradid, true)).setTemperatureRainfall(1.0F, 0.5F).setMinMaxHeight(0.15F, 0.1F).setColor(9152592).setBiomeName("gulfHarad");
        dorwinionHills = (new LOTRBiomeGenDorwinionHills(LOTRConfigBiomeID.dorwinionHillsid, true)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.8F, 0.8F).setColor(13357993).setBiomeName("dorwinionHills");
        tolfalas = (new LOTRBiomeGenTolfalas(LOTRConfigBiomeID.tolfalasid, true)).setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.3F, 1.0F).setColor(10199149).setBiomeName("tolfalas");
        lebennin = (new LOTRBiomeGenLebennin(LOTRConfigBiomeID.lebenninid, true)).setTemperatureRainfall(1.0F, 0.9F).setMinMaxHeight(0.1F, 0.3F).setColor(7845418).setBiomeName("lebennin");
        rhun = (new LOTRBiomeGenRhun(LOTRConfigBiomeID.rhunid, true)).setTemperatureRainfall(0.9F, 0.3F).setMinMaxHeight(0.3F, 0.0F).setColor(10465880).setBiomeName("rhun");
        rhunForest = (new LOTRBiomeGenRhunForest(LOTRConfigBiomeID.rhunForestid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.3F, 0.5F).setColor(7505723).setBiomeName("rhunForest");
        redMountains = (new LOTRBiomeGenRedMountains(LOTRConfigBiomeID.redMountainsid, true)).setTemperatureRainfall(0.3F, 0.4F).setMinMaxHeight(1.5F, 2.0F).setColor(9662796).setBiomeName("redMountains");
        redMountainsFoothills = (new LOTRBiomeGenRedMountainsFoothills(LOTRConfigBiomeID.redMountainsFoothillsid, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.5F, 0.9F).setColor(10064978).setBiomeName("redMountainsFoothills");
        dolGuldur = (new LOTRBiomeGenDolGuldur(LOTRConfigBiomeID.dolGuldurid, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.5F).setColor(2371343).setBiomeName("dolGuldur");
        nearHaradSemiDesert = (new LOTRBiomeGenNearHaradSemiDesert(LOTRConfigBiomeID.nearHaradSemiDesertid, true)).setTemperatureRainfall(1.5F, 0.2F).setMinMaxHeight(0.2F, 0.1F).setColor(12434282).setBiomeName("nearHaradSemiDesert");
        farHaradArid = (new LOTRBiomeGenFarHaradArid(LOTRConfigBiomeID.farHaradAridid, true)).setTemperatureRainfall(1.5F, 0.3F).setMinMaxHeight(0.2F, 0.15F).setColor(11185749).setBiomeName("farHaradArid");
        farHaradAridHills = (new LOTRBiomeGenFarHaradArid(LOTRConfigBiomeID.farHaradAridHillsid, false)).setTemperatureRainfall(1.5F, 0.3F).setMinMaxHeight(1.0F, 0.6F).setColor(10063195).setBiomeName("farHaradAridHills");
        farHaradSwamp = (new LOTRBiomeGenFarHaradSwamp(LOTRConfigBiomeID.farHaradSwampid, true)).setTemperatureRainfall(0.8F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5608267).setBiomeName("farHaradSwamp");
        farHaradCloudForest = (new LOTRBiomeGenFarHaradCloudForest(LOTRConfigBiomeID.farHaradCloudForestid, true)).setTemperatureRainfall(1.2F, 1.2F).setMinMaxHeight(0.7F, 0.4F).setColor(3046208).setBiomeName("farHaradCloudForest");
        farHaradBushland = (new LOTRBiomeGenFarHaradBushland(LOTRConfigBiomeID.farHaradBushlandid, true)).setTemperatureRainfall(1.0F, 0.4F).setMinMaxHeight(0.2F, 0.1F).setColor(10064190).setBiomeName("farHaradBushland");
        farHaradBushlandHills = (new LOTRBiomeGenFarHaradBushland(LOTRConfigBiomeID.farHaradBushlandHillsid, false)).setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.8F, 0.8F).setColor(8354100).setBiomeName("farHaradBushlandHills");
        farHaradMangrove = (new LOTRBiomeGenFarHaradMangrove(LOTRConfigBiomeID.farHaradMangroveid, true)).setTemperatureRainfall(1.0F, 0.9F).setMinMaxHeight(-0.05F, 0.05F).setColor(8883789).setBiomeName("farHaradMangrove");
        nearHaradFertileForest = (new LOTRBiomeGenNearHaradFertileForest(LOTRConfigBiomeID.nearHaradFertileForestid, false)).setTemperatureRainfall(1.2F, 1.0F).setMinMaxHeight(0.2F, 0.4F).setColor(6915122).setBiomeName("nearHaradFertileForest");
        anduinVale = (new LOTRBiomeGenAnduinVale(LOTRConfigBiomeID.anduinValeid, true)).setTemperatureRainfall(0.9F, 1.1F).setMinMaxHeight(0.05F, 0.05F).setColor(7447880).setBiomeName("anduinVale");
        wold = (new LOTRBiomeGenWold(LOTRConfigBiomeID.woldid, true)).setTemperatureRainfall(0.9F, 0.1F).setMinMaxHeight(0.4F, 0.3F).setColor(9483599).setBiomeName("wold");
        shireMoors = (new LOTRBiomeGenShireMoors(LOTRConfigBiomeID.shireMoorsid, true)).setTemperatureRainfall(0.6F, 1.6F).setMinMaxHeight(0.4F, 0.6F).setColor(6921036).setBiomeName("shireMoors");
        shireMarshes = (new LOTRBiomeGenShireMarshes(LOTRConfigBiomeID.shireMarshesid, true)).setTemperatureRainfall(0.8F, 1.2F).setMinMaxHeight(0.0F, 0.1F).setColor(4038751).setBiomeName("shireMarshes");
        nearHaradRedDesert = (new LOTRBiomeGenNearHaradRed(LOTRConfigBiomeID.nearHaradRedDesertid, true)).setTemperatureRainfall(1.5F, 0.1F).setMinMaxHeight(0.2F, 0.0F).setColor(13210447).setBiomeName("nearHaradRedDesert");
        farHaradVolcano = (new LOTRBiomeGenFarHaradVolcano(LOTRConfigBiomeID.farHaradVolcanoid, true)).setTemperatureRainfall(1.5F, 0.0F).setMinMaxHeight(0.6F, 1.2F).setColor(6838068).setBiomeName("farHaradVolcano");
        udun = (new LOTRBiomeGenUdun(LOTRConfigBiomeID.udunid, true)).setTemperatureRainfall(1.5F, 0.0F).setMinMaxHeight(0.2F, 0.7F).setColor(65536).setBiomeName("udun");
        gorgoroth = (new LOTRBiomeGenGorgoroth(LOTRConfigBiomeID.gorgorothid, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.6F, 0.2F).setColor(2170141).setBiomeName("gorgoroth");
        morgulVale = (new LOTRBiomeGenMorgulVale(LOTRConfigBiomeID.morgulValeid, true)).setTemperatureRainfall(1.0F, 0.0F).setMinMaxHeight(0.2F, 0.1F).setColor(1387801).setBiomeName("morgulVale");
        easternDesolation = (new LOTRBiomeGenEasternDesolation(LOTRConfigBiomeID.easternDesolationid, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.2F, 0.2F).setColor(6052935).setBiomeName("easternDesolation");
        dale = (new LOTRBiomeGenDale(LOTRConfigBiomeID.daleid, true)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.1F, 0.2F).setColor(8233807).setBiomeName("dale");
        dorwinion = (new LOTRBiomeGenDorwinion(LOTRConfigBiomeID.dorwinionid, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.1F, 0.3F).setColor(7120197).setBiomeName("dorwinion");
        towerHills = (new LOTRBiomeGenTowerHills(LOTRConfigBiomeID.towerHillsid, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.5F, 0.5F).setColor(6854209).setBiomeName("towerHills");
        gulfHaradForest = (new LOTRBiomeGenGulfHaradForest(LOTRConfigBiomeID.gulfHaradForestid, false)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.2F, 0.4F).setColor(5868590).setBiomeName("gulfHaradForest");
        wilderlandNorth = (new LOTRBiomeGenWilderlandNorth(LOTRConfigBiomeID.wilderlandNorthid, true)).setTemperatureRainfall(0.6F, 0.6F).setMinMaxHeight(0.2F, 0.5F).setColor(9676396).setBiomeName("wilderlandNorth");
        forodwaithCoast = (new LOTRBiomeGenForodwaithCoast(LOTRConfigBiomeID.forodwaithCoastid, false)).setTemperatureRainfall(0.0F, 0.4F).setMinMaxHeight(0.0F, 0.5F).setColor(9214637).setBiomeName("forodwaithCoast");
        farHaradCoast = (new LOTRBiomeGenFarHaradCoast(LOTRConfigBiomeID.farHaradCoastid, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.0F, 0.5F).setColor(8356472).setBiomeName("farHaradCoast");
        nearHaradRiverbank = (new LOTRBiomeGenNearHaradRiverbank(LOTRConfigBiomeID.nearHaradRiverbankid, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.1F, 0.1F).setColor(7183952).setBiomeName("nearHaradRiverbank");
        lossarnach = (new LOTRBiomeGenLossarnach(LOTRConfigBiomeID.lossarnachid, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.1F, 0.2F).setColor(8439086).setBiomeName("lossarnach");
        imlothMelui = (new LOTRBiomeGenImlothMelui(LOTRConfigBiomeID.imlothMeluiid, true)).setTemperatureRainfall(1.0F, 1.2F).setMinMaxHeight(0.1F, 0.2F).setColor(14517608).setBiomeName("imlothMelui");
        nearHaradOasis = (new LOTRBiomeGenNearHaradOasis(LOTRConfigBiomeID.nearHaradOasisid, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.1F, 0.1F).setColor(832768).setBiomeName("nearHaradOasis");
        beachWhite = (new LOTRBiomeGenBeach(LOTRConfigBiomeID.beachWhiteid, false)).setBeachBlock(LOTRMod.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
        harnedor = (new LOTRBiomeGenHarnedor(LOTRConfigBiomeID.harnedorid, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.1F, 0.3F).setColor(11449173).setBiomeName("harnedor");
        lamedon = (new LOTRBiomeGenLamedon(LOTRConfigBiomeID.lamedonid, true)).setTemperatureRainfall(0.9F, 0.5F).setMinMaxHeight(0.2F, 0.2F).setColor(10927460).setBiomeName("lamedon");
        lamedonHills = (new LOTRBiomeGenLamedonHills(LOTRConfigBiomeID.lamedonHillsid, true)).setTemperatureRainfall(0.6F, 0.4F).setMinMaxHeight(0.6F, 0.9F).setColor(13555369).setBiomeName("lamedonHills");
        blackrootVale = (new LOTRBiomeGenBlackrootVale(LOTRConfigBiomeID.blackrootValeid, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.12F).setColor(7183921).setBiomeName("blackrootVale");
        andrast = (new LOTRBiomeGenAndrast(LOTRConfigBiomeID.andrastid, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(8885856).setBiomeName("andrast");
        pukel = (new LOTRBiomeGenPukel(LOTRConfigBiomeID.pukelid, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.2F, 0.4F).setColor(5667394).setBiomeName("pukel");
        rhunLand = (new LOTRBiomeGenRhunLand(LOTRConfigBiomeID.rhunLandid, true)).setTemperatureRainfall(1.0F, 0.8F).setMinMaxHeight(0.1F, 0.3F).setColor(11381583).setBiomeName("rhunLand");
        rhunLandSteppe = (new LOTRBiomeGenRhunLandSteppe(LOTRConfigBiomeID.rhunLandSteppeid, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.2F, 0.05F).setColor(11712354).setBiomeName("rhunLandSteppe");
        rhunLandHills = (new LOTRBiomeGenRhunLandHills(LOTRConfigBiomeID.rhunLandHillsid, true)).setTemperatureRainfall(1.0F, 0.5F).setMinMaxHeight(0.6F, 0.8F).setColor(9342286).setBiomeName("rhunLandHills");
        rhunRedForest = (new LOTRBiomeGenRhunRedForest(LOTRConfigBiomeID.rhunRedForestid, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.3F).setColor(9530430).setBiomeName("rhunRedForest");
        rhunIsland = (new LOTRBiomeGenRhunIsland(LOTRConfigBiomeID.rhunIslandid, false)).setTemperatureRainfall(1.0F, 0.8F).setMinMaxHeight(0.1F, 0.4F).setColor(10858839).setBiomeName("rhunIsland");
        rhunIslandForest = (new LOTRBiomeGenRhunIslandForest(LOTRConfigBiomeID.rhunIslandForestid, false)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.4F).setColor(9533758).setBiomeName("rhunIslandForest");
        lastDesert = (new LOTRBiomeGenLastDesert(LOTRConfigBiomeID.lastDesertid, true)).setTemperatureRainfall(0.7F, 0.0F).setMinMaxHeight(0.2F, 0.05F).setColor(13878151).setBiomeName("lastDesert");
        windMountains = (new LOTRBiomeGenWindMountains(LOTRConfigBiomeID.windMountainsid, true)).setTemperatureRainfall(0.28F, 0.2F).setMinMaxHeight(2.0F, 2.0F).setColor(13882323).setBiomeName("windMountains");
        windMountainsFoothills = (new LOTRBiomeGenWindMountainsFoothills(LOTRConfigBiomeID.windMountainsFoothillsid, true)).setTemperatureRainfall(0.4F, 0.6F).setMinMaxHeight(0.5F, 0.6F).setColor(10133354).setBiomeName("windMountainsFoothills");
        rivendell = (new LOTRBiomeGenRivendell(LOTRConfigBiomeID.rivendellid, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.15F, 0.3F).setColor(8828714).setBiomeName("rivendell");
        rivendellHills = (new LOTRBiomeGenRivendellHills(LOTRConfigBiomeID.rivendellHillsid, true)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(2.0F, 0.5F).setColor(14210481).setBiomeName("rivendellHills");
        farHaradJungleMountains = (new LOTRBiomeGenFarHaradJungleMountains(LOTRConfigBiomeID.farHaradJungleMountainsid, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(1.8F, 1.5F).setColor(6511174).setBiomeName("farHaradJungleMountains");
        halfTrollForest = (new LOTRBiomeGenHalfTrollForest(LOTRConfigBiomeID.halfTrollForestid, true)).setTemperatureRainfall(0.8F, 0.2F).setMinMaxHeight(0.3F, 0.4F).setColor(5992500).setBiomeName("halfTrollForest");
        farHaradKanuka = (new LOTRBiomeGenKanuka(LOTRConfigBiomeID.farHaradKanukaid, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.3F, 0.5F).setColor(5142552).setBiomeName("farHaradKanuka");
        utumno = (new LOTRBiomeGenUtumno(LOTRConfigBiomeID.utumnoid)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.0F, 0.0F).setColor(0).setBiomeName("utumno");
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
