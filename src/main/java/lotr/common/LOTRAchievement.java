package lotr.common;

import java.awt.Color;
import java.util.*;

import lotr.common.entity.npc.LOTREntityWickedDwarf;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemManFlesh;
import lotr.common.quest.LOTRMiniQuestPickpocket;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

public class LOTRAchievement {
	public static LOTRAchievement enterMiddleEarth;
	public static LOTRAchievement doGreyQuest;
	public static LOTRAchievement killOrc;
	public static LOTRAchievement mineMithril;
	public static LOTRAchievement rideWarg;
	public static LOTRAchievement killWarg;
	public static LOTRAchievement useSpearFromFar;
	public static LOTRAchievement wearFullMithril;
	public static LOTRAchievement gainHighAlcoholTolerance;
	public static LOTRAchievement craftSaddle;
	public static LOTRAchievement drinkOrcDraught;
	public static LOTRAchievement getPouch;
	public static LOTRAchievement craftBronze;
	public static LOTRAchievement killUsingOnlyPlates;
	public static LOTRAchievement wearFullFur;
	public static LOTRAchievement brewDrinkInBarrel;
	public static LOTRAchievement findAthelas;
	public static LOTRAchievement drinkAthelasBrew;
	public static LOTRAchievement killLargeMobWithSlingshot;
	public static LOTRAchievement eatMaggotyBread;
	public static LOTRAchievement killWhileDrunk;
	public static LOTRAchievement collectCraftingTables;
	public static LOTRAchievement hitByOrcSpear;
	public static LOTRAchievement killBombardier;
	public static LOTRAchievement earnManyCoins;
	public static LOTRAchievement craftAppleCrumble;
	public static LOTRAchievement killButterfly;
	public static LOTRAchievement fishRing;
	public static LOTRAchievement useCrossbow;
	public static LOTRAchievement collectCrossbowBolts;
	public static LOTRAchievement travel10;
	public static LOTRAchievement travel20;
	public static LOTRAchievement travel30;
	public static LOTRAchievement attackRabbit;
	public static LOTRAchievement craftRabbitStew;
	public static LOTRAchievement drinkSkull;
	public static LOTRAchievement travel40;
	public static LOTRAchievement travel50;
	public static LOTRAchievement killThievingBandit;
	public static LOTRAchievement hundreds;
	public static LOTRAchievement alloyBronze;
	public static LOTRAchievement bannerProtect;
	public static LOTRAchievement catchButterfly;
	public static LOTRAchievement unsmelt;
	public static LOTRAchievement tradeScrapTrader;
	public static LOTRAchievement cookDeer;
	public static LOTRAchievement eatManFlesh;
	public static LOTRAchievement craftSaltedFlesh;
	public static LOTRAchievement enchantBaneElf;
	public static LOTRAchievement enchantBaneOrc;
	public static LOTRAchievement enchantBaneDwarf;
	public static LOTRAchievement enchantBaneWarg;
	public static LOTRAchievement enchantBaneTroll;
	public static LOTRAchievement enchantBaneSpider;
	public static LOTRAchievement enchantBaneWight;
	public static LOTRAchievement getDrunk;
	public static LOTRAchievement reforge;
	public static LOTRAchievement doMiniquestHunter;
	public static LOTRAchievement doMiniquestHunter5;
	public static LOTRAchievement killHuntingPlayer;
	public static LOTRAchievement pledgeService;
	public static LOTRAchievement factionConquest;
	public static LOTRAchievement defeatInvasion;
	public static LOTRAchievement pickpocket;
	public static LOTRAchievement combineSmithScrolls;
	public static LOTRAchievement engraveOwnership;
	public static LOTRAchievement killHobbit;
	public static LOTRAchievement sellPipeweedLeaf;
	public static LOTRAchievement marryHobbit;
	public static LOTRAchievement findFourLeafClover;
	public static LOTRAchievement useMagicPipe;
	public static LOTRAchievement rideShirePony;
	public static LOTRAchievement tradeBartender;
	public static LOTRAchievement speakToDrunkard;
	public static LOTRAchievement tradeHobbitShirriff;
	public static LOTRAchievement killDarkHuorn;
	public static LOTRAchievement enterOldForest;
	public static LOTRAchievement buyOrcharderFood;
	public static LOTRAchievement rideGiraffeShire;
	public static LOTRAchievement buyPotatoHobbitFarmer;
	public static LOTRAchievement doMiniquestHobbit;
	public static LOTRAchievement enterWhiteDowns;
	public static LOTRAchievement useHobbitTable;
	public static LOTRAchievement hireHobbitFarmer;
	public static LOTRAchievement enterBlueMountains;
	public static LOTRAchievement smeltBlueDwarfSteel;
	public static LOTRAchievement killBlueDwarf;
	public static LOTRAchievement wearFullBlueDwarven;
	public static LOTRAchievement useBlueDwarvenTable;
	public static LOTRAchievement tradeBlueDwarfMiner;
	public static LOTRAchievement tradeBlueDwarfCommander;
	public static LOTRAchievement tradeBlueDwarfMerchant;
	public static LOTRAchievement marryBlueDwarf;
	public static LOTRAchievement doMiniquestBlueMountains;
	public static LOTRAchievement tradeBlueDwarfSmith;
	public static LOTRAchievement enterLindon;
	public static LOTRAchievement doMiniquestHighElf;
	public static LOTRAchievement killHighElf;
	public static LOTRAchievement tradeHighElfLord;
	public static LOTRAchievement useHighElvenTable;
	public static LOTRAchievement wearFullHighElven;
	public static LOTRAchievement smeltElfSteel;
	public static LOTRAchievement tradeHighElfSmith;
	public static LOTRAchievement wearFullGondolin;
	public static LOTRAchievement enterTowerHills;
	public static LOTRAchievement wearFullGalvorn;
	public static LOTRAchievement enterBreeland;
	public static LOTRAchievement enterChetwood;
	public static LOTRAchievement killBreelander;
	public static LOTRAchievement doMiniquestBree;
	public static LOTRAchievement tradeBreeCaptain;
	public static LOTRAchievement useBreeTable;
	public static LOTRAchievement tradeBreeBlacksmith;
	public static LOTRAchievement tradeBreeInnkeeper;
	public static LOTRAchievement killBreeHobbit;
	public static LOTRAchievement killRuffianSpy;
	public static LOTRAchievement killRuffianBrute;
	public static LOTRAchievement doMiniquestRuffianSpy;
	public static LOTRAchievement doMiniquestRuffianBrute;
	public static LOTRAchievement tradeBreeMarketTrader;
	public static LOTRAchievement buyAppleBreeFarmer;
	public static LOTRAchievement hireBreeFarmer;
	public static LOTRAchievement killRangerNorth;
	public static LOTRAchievement wearFullRanger;
	public static LOTRAchievement killTroll;
	public static LOTRAchievement getTrollStatue;
	public static LOTRAchievement makeTrollSneeze;
	public static LOTRAchievement killMountainTroll;
	public static LOTRAchievement killTrollFleeingSun;
	public static LOTRAchievement killMountainTrollChieftain;
	public static LOTRAchievement shootDownMidges;
	public static LOTRAchievement enterTrollshaws;
	public static LOTRAchievement enterMidgewater;
	public static LOTRAchievement enterLoneLands;
	public static LOTRAchievement enterEttenmoors;
	public static LOTRAchievement enterEriador;
	public static LOTRAchievement enterColdfells;
	public static LOTRAchievement enterSwanfleet;
	public static LOTRAchievement enterMinhiriath;
	public static LOTRAchievement tradeGundabadCaptain;
	public static LOTRAchievement tradeRangerNorthCaptain;
	public static LOTRAchievement enterBarrowDowns;
	public static LOTRAchievement useRangerTable;
	public static LOTRAchievement useGundabadTable;
	public static LOTRAchievement doMiniquestRanger;
	public static LOTRAchievement doMiniquestGundabad;
	public static LOTRAchievement killBarrowWight;
	public static LOTRAchievement killGundabadOrc;
	public static LOTRAchievement killGundabadUruk;
	public static LOTRAchievement wearFullGundabadUruk;
	public static LOTRAchievement killDunedain;
	public static LOTRAchievement enterAngle;
	public static LOTRAchievement wearFullArnor;
	public static LOTRAchievement tradeDunedainBlacksmith;
	public static LOTRAchievement enterRivendell;
	public static LOTRAchievement useRivendellTable;
	public static LOTRAchievement wearFullRivendell;
	public static LOTRAchievement tradeRivendellSmith;
	public static LOTRAchievement tradeRivendellLord;
	public static LOTRAchievement tradeRivendellTrader;
	public static LOTRAchievement doMiniquestRivendell;
	public static LOTRAchievement killRivendellElf;
	public static LOTRAchievement tradeGundabadTrader;
	public static LOTRAchievement tradeAngmarCaptain;
	public static LOTRAchievement killAngmarOrc;
	public static LOTRAchievement enterAngmar;
	public static LOTRAchievement useAngmarTable;
	public static LOTRAchievement wearFullAngmar;
	public static LOTRAchievement doMiniquestAngmar;
	public static LOTRAchievement tradeAngmarTrader;
	public static LOTRAchievement killAngmarHillman;
	public static LOTRAchievement tradeAngmarHillmanChieftain;
	public static LOTRAchievement killSnowTroll;
	public static LOTRAchievement enterEregion;
	public static LOTRAchievement enterEnedwaith;
	public static LOTRAchievement enterNanCurunir;
	public static LOTRAchievement enterPukel;
	public static LOTRAchievement killDunlending;
	public static LOTRAchievement wearFullDunlending;
	public static LOTRAchievement useDunlendingTable;
	public static LOTRAchievement tradeDunlendingWarlord;
	public static LOTRAchievement useDunlendingTrident;
	public static LOTRAchievement tradeDunlendingBartender;
	public static LOTRAchievement enterDunland;
	public static LOTRAchievement doMiniquestDunland;
	public static LOTRAchievement climbMistyMountains;
	public static LOTRAchievement enterMistyMountains;
	public static LOTRAchievement tameGollum;
	public static LOTRAchievement enterForodwaith;
	public static LOTRAchievement enterValesOfAnduin;
	public static LOTRAchievement enterGreyMountains;
	public static LOTRAchievement enterGladdenFields;
	public static LOTRAchievement enterEmynMuil;
	public static LOTRAchievement enterBrownLands;
	public static LOTRAchievement enterWilderland;
	public static LOTRAchievement enterDagorlad;
	public static LOTRAchievement enterCelebrant;
	public static LOTRAchievement enterLongMarshes;
	public static LOTRAchievement enterEastBight;
	public static LOTRAchievement killMirkwoodSpider;
	public static LOTRAchievement killWoodElf;
	public static LOTRAchievement useWoodElvenTable;
	public static LOTRAchievement wearFullWoodElvenScout;
	public static LOTRAchievement tradeWoodElfCaptain;
	public static LOTRAchievement rideBarrelMirkwood;
	public static LOTRAchievement enterMirkwood;
	public static LOTRAchievement enterWoodlandRealm;
	public static LOTRAchievement wearFullWoodElven;
	public static LOTRAchievement enterDolGuldur;
	public static LOTRAchievement killDolGuldurOrc;
	public static LOTRAchievement useDolGuldurTable;
	public static LOTRAchievement tradeDolGuldurCaptain;
	public static LOTRAchievement killMirkTroll;
	public static LOTRAchievement wearFullDolGuldur;
	public static LOTRAchievement doMiniquestWoodElf;
	public static LOTRAchievement doMiniquestDolGuldur;
	public static LOTRAchievement tradeDolGuldurTrader;
	public static LOTRAchievement tradeWoodElfSmith;
	public static LOTRAchievement enterDale;
	public static LOTRAchievement doMiniquestDale;
	public static LOTRAchievement useDaleTable;
	public static LOTRAchievement wearFullDale;
	public static LOTRAchievement killDalish;
	public static LOTRAchievement tradeDaleCaptain;
	public static LOTRAchievement tradeDaleBlacksmith;
	public static LOTRAchievement tradeDaleBaker;
	public static LOTRAchievement tradeDaleMerchant;
	public static LOTRAchievement enterErebor;
	public static LOTRAchievement openDaleCracker;
	public static LOTRAchievement killDwarf;
	public static LOTRAchievement wearFullDwarven;
	public static LOTRAchievement useDwarvenThrowingAxe;
	public static LOTRAchievement useDwarvenTable;
	public static LOTRAchievement tradeDwarfMiner;
	public static LOTRAchievement tradeDwarfCommander;
	public static LOTRAchievement mineGlowstone;
	public static LOTRAchievement smeltDwarfSteel;
	public static LOTRAchievement drinkDwarvenTonic;
	public static LOTRAchievement craftMithrilDwarvenBrick;
	public static LOTRAchievement talkDwarfWoman;
	public static LOTRAchievement enterIronHills;
	public static LOTRAchievement useDwarvenDoor;
	public static LOTRAchievement marryDwarf;
	public static LOTRAchievement doMiniquestDwarf;
	public static LOTRAchievement tradeIronHillsMerchant;
	public static LOTRAchievement tradeDwarfSmith;
	public static LOTRAchievement killElf;
	public static LOTRAchievement useElvenPortal;
	public static LOTRAchievement wearFullElven;
	public static LOTRAchievement useElvenTable;
	public static LOTRAchievement tradeElfLord;
	public static LOTRAchievement mineQuendite;
	public static LOTRAchievement takeMallornWood;
	public static LOTRAchievement enterLothlorien;
	public static LOTRAchievement tradeElvenTrader;
	public static LOTRAchievement doMiniquestGaladhrim;
	public static LOTRAchievement tradeGaladhrimSmith;
	public static LOTRAchievement wearFullHithlain;
	public static LOTRAchievement killEnt;
	public static LOTRAchievement drinkEntDraught;
	public static LOTRAchievement killHuorn;
	public static LOTRAchievement talkEnt;
	public static LOTRAchievement enterFangorn;
	public static LOTRAchievement summonHuorn;
	public static LOTRAchievement killMallornEnt;
	public static LOTRAchievement raidUrukCamp;
	public static LOTRAchievement useUrukTable;
	public static LOTRAchievement tradeUrukTrader;
	public static LOTRAchievement tradeUrukCaptain;
	public static LOTRAchievement useRohirricTable;
	public static LOTRAchievement smeltUrukSteel;
	public static LOTRAchievement wearFullUruk;
	public static LOTRAchievement hireWargBombardier;
	public static LOTRAchievement killRohirrim;
	public static LOTRAchievement tradeRohirrimMarshal;
	public static LOTRAchievement wearFullRohirric;
	public static LOTRAchievement tradeRohanBlacksmith;
	public static LOTRAchievement buyRohanMead;
	public static LOTRAchievement enterRohan;
	public static LOTRAchievement enterRohanUrukHighlands;
	public static LOTRAchievement doMiniquestRohan;
	public static LOTRAchievement doMiniquestIsengard;
	public static LOTRAchievement killUrukHai;
	public static LOTRAchievement enterEntwashMouth;
	public static LOTRAchievement wearFullRohirricMarshal;
	public static LOTRAchievement killIsengardSnaga;
	public static LOTRAchievement doMiniquestRohanShieldmaiden;
	public static LOTRAchievement tradeRohanFarmer;
	public static LOTRAchievement hireRohanFarmer;
	public static LOTRAchievement tradeRohanMarketTrader;
	public static LOTRAchievement tradeRohanStablemaster;
	public static LOTRAchievement enterAdornland;
	public static LOTRAchievement killGondorian;
	public static LOTRAchievement lightGondorBeacon;
	public static LOTRAchievement useGondorianTable;
	public static LOTRAchievement tradeGondorBlacksmith;
	public static LOTRAchievement tradeGondorianCaptain;
	public static LOTRAchievement wearFullGondorian;
	public static LOTRAchievement killRangerIthilien;
	public static LOTRAchievement enterGondor;
	public static LOTRAchievement enterIthilien;
	public static LOTRAchievement enterWhiteMountains;
	public static LOTRAchievement enterTolfalas;
	public static LOTRAchievement enterLebennin;
	public static LOTRAchievement doMiniquestGondor;
	public static LOTRAchievement tradeRangerIthilienCaptain;
	public static LOTRAchievement useDolAmrothTable;
	public static LOTRAchievement wearFullDolAmroth;
	public static LOTRAchievement killSwanKnight;
	public static LOTRAchievement enterDorEnErnil;
	public static LOTRAchievement tradeDolAmrothCaptain;
	public static LOTRAchievement enterAnduinMouth;
	public static LOTRAchievement enterPelennor;
	public static LOTRAchievement wearFullRangerIthilien;
	public static LOTRAchievement enterLossarnach;
	public static LOTRAchievement enterImlothMelui;
	public static LOTRAchievement wearFullLossarnach;
	public static LOTRAchievement wearFullPelargir;
	public static LOTRAchievement wearFullPinnathGelin;
	public static LOTRAchievement wearFullBlackroot;
	public static LOTRAchievement hireGondorFarmer;
	public static LOTRAchievement buyPipeweedGondorFarmer;
	public static LOTRAchievement tradeGondorBartender;
	public static LOTRAchievement tradeGondorMarketTrader;
	public static LOTRAchievement tradeLossarnachCaptain;
	public static LOTRAchievement tradePelargirCaptain;
	public static LOTRAchievement tradePinnathGelinCaptain;
	public static LOTRAchievement tradeBlackrootCaptain;
	public static LOTRAchievement tradeLebenninCaptain;
	public static LOTRAchievement enterPelargir;
	public static LOTRAchievement wearFullLamedon;
	public static LOTRAchievement tradeLamedonCaptain;
	public static LOTRAchievement enterLamedon;
	public static LOTRAchievement enterBlackrootVale;
	public static LOTRAchievement enterPinnathGelin;
	public static LOTRAchievement enterAndrast;
	public static LOTRAchievement doMiniquestGondorKillRenegade;
	public static LOTRAchievement mineRemains;
	public static LOTRAchievement craftAncientItem;
	public static LOTRAchievement enterDeadMarshes;
	public static LOTRAchievement enterNindalf;
	public static LOTRAchievement killMarshWraith;
	public static LOTRAchievement killOlogHai;
	public static LOTRAchievement useMorgulTable;
	public static LOTRAchievement smeltOrcSteel;
	public static LOTRAchievement wearFullOrc;
	public static LOTRAchievement tradeOrcTrader;
	public static LOTRAchievement tradeOrcCaptain;
	public static LOTRAchievement mineNaurite;
	public static LOTRAchievement eatMorgulShroom;
	public static LOTRAchievement craftOrcBomb;
	public static LOTRAchievement hireOlogHai;
	public static LOTRAchievement mineGulduril;
	public static LOTRAchievement useMorgulPortal;
	public static LOTRAchievement wearFullMorgul;
	public static LOTRAchievement enterMordor;
	public static LOTRAchievement enterNurn;
	public static LOTRAchievement enterNanUngol;
	public static LOTRAchievement killMordorSpider;
	public static LOTRAchievement tradeOrcSpiderKeeper;
	public static LOTRAchievement killMordorOrc;
	public static LOTRAchievement doMiniquestMordor;
	public static LOTRAchievement smeltBlackUrukSteel;
	public static LOTRAchievement wearFullBlackUruk;
	public static LOTRAchievement killBlackUruk;
	public static LOTRAchievement hireNurnSlave;
	public static LOTRAchievement enterMorgulVale;
	public static LOTRAchievement tradeBlackUrukCaptain;
	public static LOTRAchievement killWickedDwarf;
	public static LOTRAchievement tradeWickedDwarf;
	public static LOTRAchievement enterDorwinion;
	public static LOTRAchievement doMiniquestDorwinion;
	public static LOTRAchievement useDorwinionTable;
	public static LOTRAchievement wearFullDorwinion;
	public static LOTRAchievement wearFullDorwinionElf;
	public static LOTRAchievement killDorwinion;
	public static LOTRAchievement tradeDorwinionCaptain;
	public static LOTRAchievement killDorwinionElf;
	public static LOTRAchievement tradeDorwinionElfCaptain;
	public static LOTRAchievement drinkWine;
	public static LOTRAchievement harvestGrapes;
	public static LOTRAchievement buyWineVintner;
	public static LOTRAchievement hireDorwinionVinekeeper;
	public static LOTRAchievement stealDorwinionGrapes;
	public static LOTRAchievement tradeDorwinionMerchant;
	public static LOTRAchievement enterDorwinionHills;
	public static LOTRAchievement enterRhun;
	public static LOTRAchievement useRhunTable;
	public static LOTRAchievement killEasterling;
	public static LOTRAchievement doMiniquestRhun;
	public static LOTRAchievement wearFullRhun;
	public static LOTRAchievement tradeRhunBlacksmith;
	public static LOTRAchievement tradeRhunCaptain;
	public static LOTRAchievement hitBirdFirePot;
	public static LOTRAchievement getKineArawHorn;
	public static LOTRAchievement wearFullRhunGold;
	public static LOTRAchievement enterLastDesert;
	public static LOTRAchievement enterMountainsWind;
	public static LOTRAchievement tradeRhunMarketTrader;
	public static LOTRAchievement tradeRhunBartender;
	public static LOTRAchievement hireRhunFarmer;
	public static LOTRAchievement enterRhunLand;
	public static LOTRAchievement enterRhunRedwood;
	public static LOTRAchievement enterRhunIsland;
	public static LOTRAchievement enterRedMountains;
	public static LOTRAchievement enterHarondor;
	public static LOTRAchievement enterNearHarad;
	public static LOTRAchievement killNearHaradrim;
	public static LOTRAchievement useNearHaradTable;
	public static LOTRAchievement wearFullNearHarad;
	public static LOTRAchievement tradeNearHaradWarlord;
	public static LOTRAchievement rideCamel;
	public static LOTRAchievement tradeBazaarTrader;
	public static LOTRAchievement tradeNearHaradMerchant;
	public static LOTRAchievement doMiniquestNearHarad;
	public static LOTRAchievement cookKebab;
	public static LOTRAchievement enterNearHaradOasis;
	public static LOTRAchievement tradeNearHaradBlacksmith;
	public static LOTRAchievement enterHarnedor;
	public static LOTRAchievement enterSouthronCoasts;
	public static LOTRAchievement enterUmbar;
	public static LOTRAchievement enterLostladen;
	public static LOTRAchievement enterGulfHarad;
	public static LOTRAchievement useUmbarTable;
	public static LOTRAchievement useGulfTable;
	public static LOTRAchievement wearFullGulfHarad;
	public static LOTRAchievement wearFullCorsair;
	public static LOTRAchievement wearFullUmbar;
	public static LOTRAchievement wearFullHarnedor;
	public static LOTRAchievement wearFullNomad;
	public static LOTRAchievement tradeHarnedorWarlord;
	public static LOTRAchievement tradeUmbarCaptain;
	public static LOTRAchievement tradeCorsairCaptain;
	public static LOTRAchievement tradeNomadWarlord;
	public static LOTRAchievement tradeGulfWarlord;
	public static LOTRAchievement hireHaradSlave;
	public static LOTRAchievement hireMoredainMercenary;
	public static LOTRAchievement tradeHarnedorBlacksmith;
	public static LOTRAchievement tradeUmbarBlacksmith;
	public static LOTRAchievement tradeGulfBlacksmith;
	public static LOTRAchievement doMiniquestGondorRenegade;
	public static LOTRAchievement tradeNomadMerchant;
	public static LOTRAchievement tradeHaradBartender;
	public static LOTRAchievement tradeNomadArmourer;
	public static LOTRAchievement hireHarnedorFarmer;
	public static LOTRAchievement tradeHaradFarmer;
	public static LOTRAchievement wearFullBlackNumenorean;
	public static LOTRAchievement enterFarHaradSavannah;
	public static LOTRAchievement pickBanana;
	public static LOTRAchievement growBaobab;
	public static LOTRAchievement enterFarHaradVolcano;
	public static LOTRAchievement killMoredain;
	public static LOTRAchievement useMoredainTable;
	public static LOTRAchievement wearFullMoredain;
	public static LOTRAchievement tradeMoredainChieftain;
	public static LOTRAchievement doMiniquestMoredain;
	public static LOTRAchievement tradeMoredainVillager;
	public static LOTRAchievement enterCorsairCoasts;
	public static LOTRAchievement enterFarHaradJungle;
	public static LOTRAchievement drinkMangoJuice;
	public static LOTRAchievement doMiniquestTauredain;
	public static LOTRAchievement useTauredainTable;
	public static LOTRAchievement wearFullTauredain;
	public static LOTRAchievement killTauredain;
	public static LOTRAchievement tradeTauredainChieftain;
	public static LOTRAchievement tradeTauredainShaman;
	public static LOTRAchievement smeltObsidianShard;
	public static LOTRAchievement tradeTauredainFarmer;
	public static LOTRAchievement hireTauredainFarmer;
	public static LOTRAchievement tradeTauredainSmith;
	public static LOTRAchievement wearFullTaurethrimGold;
	public static LOTRAchievement enterPertorogwaith;
	public static LOTRAchievement killHalfTroll;
	public static LOTRAchievement wearFullHalfTroll;
	public static LOTRAchievement tradeHalfTrollWarlord;
	public static LOTRAchievement useHalfTrollTable;
	public static LOTRAchievement doMiniquestHalfTroll;
	public static LOTRAchievement tradeHalfTrollScavenger;
	public static LOTRAchievement enterHalfTrollForest;
	public static LOTRAchievement enterOcean;
	public static LOTRAchievement enterMeneltarma;
	public static LOTRAchievement enterUtumnoIce;
	public static LOTRAchievement enterUtumnoObsidian;
	public static LOTRAchievement enterUtumnoFire;
	public static LOTRAchievement wearFullUtumno;
	public static LOTRAchievement killUtumnoOrc;
	public static LOTRAchievement killUtumnoWarg;
	public static LOTRAchievement killBalrog;
	public static LOTRAchievement killTormentedElf;
	public static LOTRAchievement killUtumnoTroll;
	public static LOTRAchievement craftUtumnoKey;
	public static LOTRAchievement leaveUtumno;
	public Category category;
	public int ID;
	public ItemStack icon;
	public String name;
	public boolean isBiomeAchievement;
	public boolean isSpecial;
	public LOTRTitle achievementTitle;
	public Collection<LOTRFaction> enemyFactions = new ArrayList<>();
	public Collection<LOTRFaction> allyFactions = new ArrayList<>();

	public LOTRAchievement(Category c, int i, Block block, String s) {
		this(c, i, new ItemStack(block), s);
	}

	public LOTRAchievement(Category c, int i, Item item, String s) {
		this(c, i, new ItemStack(item), s);
	}

	public LOTRAchievement(Category c, int i, ItemStack itemstack, String s) {
		category = c;
		ID = i;
		icon = itemstack;
		name = s;
		for (LOTRAchievement achievement : category.list) {
			if (achievement.ID != ID) {
				continue;
			}
			throw new IllegalArgumentException("Duplicate ID " + ID + " for LOTR achievement category " + category.name());
		}
		category.list.add(this);
		getDimension().allAchievements.add(this);
	}

	public void broadcastEarning(EntityPlayer entityplayer) {
		if (LOTRConfig.protectHobbitKillers && this == killHobbit) {
			return;
		}
		ChatComponentTranslation dimName = new ChatComponentTranslation(getDimension().getUntranslatedDimensionName());
		IChatComponent earnName = getChatComponentForEarn(entityplayer);
		IChatComponent msg = new ChatComponentTranslation("chat.lotr.achievement", entityplayer.func_145748_c_(), dimName, earnName);
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg);
	}

	public boolean canPlayerEarn(EntityPlayer entityplayer) {
		float alignment;
		LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
		if (!enemyFactions.isEmpty()) {
			boolean anyEnemies = false;
			for (LOTRFaction f : enemyFactions) {
				alignment = playerData.getAlignment(f);
				if (alignment > 0.0f) {
					continue;
				}
				anyEnemies = true;
			}
			if (!anyEnemies) {
				return false;
			}
		}
		if (!allyFactions.isEmpty()) {
			boolean anyAllies = false;
			for (LOTRFaction f : allyFactions) {
				alignment = playerData.getAlignment(f);
				if (alignment < 0.0f) {
					continue;
				}
				anyAllies = true;
			}
			return anyAllies;
		}
		return true;
	}

	public LOTRAchievement createTitle() {
		return createTitle(null);
	}

	public LOTRAchievement createTitle(String s) {
		if (achievementTitle != null) {
			throw new IllegalArgumentException("LOTR achievement " + name + " already has an associated title!");
		}
		achievementTitle = new LOTRTitle(s, this);
		return this;
	}

	public IChatComponent getAchievementChatComponent(EntityPlayer entityplayer) {
		ChatComponentTranslation component = new ChatComponentTranslation(getUntranslatedTitle(entityplayer)).createCopy();
		component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		component.getChatStyle().setChatHoverEvent(new HoverEvent(LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT, new ChatComponentText(category.name() + "$" + ID)));
		return component;
	}

	public LOTRTitle getAchievementTitle() {
		return achievementTitle;
	}

	public IChatComponent getChatComponentForEarn(EntityPlayer entityplayer) {
		IChatComponent base = getAchievementChatComponent(entityplayer);
		IChatComponent component = new ChatComponentText("[").appendSibling(base).appendText("]");
		component.setChatStyle(base.getChatStyle());
		return component;
	}

	public String getCodeName() {
		return name;
	}

	public String getDescription(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal("lotr.achievement." + name + ".desc");
	}

	public LOTRDimension getDimension() {
		return category.dimension;
	}

	public String getTitle(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal(getUntranslatedTitle(entityplayer));
	}

	public String getUntranslatedTitle(EntityPlayer entityplayer) {
		return "lotr.achievement." + name + ".title";
	}

	public LOTRAchievement setBiomeAchievement() {
		isBiomeAchievement = true;
		return this;
	}

	public LOTRAchievement setRequiresAlly(LOTRFaction... f) {
		allyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public LOTRAchievement setRequiresAnyAlly(List<LOTRFaction> f) {
		return setRequiresAlly(f.toArray(new LOTRFaction[0]));
	}

	public LOTRAchievement setRequiresAnyEnemy(List<LOTRFaction> f) {
		return setRequiresEnemy(f.toArray(new LOTRFaction[0]));
	}

	public LOTRAchievement setRequiresEnemy(LOTRFaction... f) {
		enemyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public LOTRAchievement setSpecial() {
		isSpecial = true;
		return this;
	}

	public static LOTRAchievement achievementForCategoryAndID(Category category, int ID) {
		if (category == null) {
			return null;
		}
		for (LOTRAchievement achievement : category.list) {
			if (achievement.ID != ID) {
				continue;
			}
			return achievement;
		}
		return null;
	}

	public static Category categoryForName(String name) {
		for (Category category : Category.values()) {
			if (!category.name().equals(name)) {
				continue;
			}
			return category;
		}
		return null;
	}

	public static void createAchievements() {
		enterMiddleEarth = new LOTRAchievement(Category.GENERAL, 1, LOTRMod.redBook, "enterMiddleEarth").setSpecial();
		doGreyQuest = new LOTRAchievement(Category.GENERAL, 2, LOTRMod.gandalfStaffGrey, "doGreyQuest");
		killOrc = new LOTRAchievement(Category.GENERAL, 14, LOTRMod.orcBone, "killOrc").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		mineMithril = new LOTRAchievement(Category.GENERAL, 15, LOTRMod.oreMithril, "mineMithril").createTitle();
		rideWarg = new LOTRAchievement(Category.GENERAL, 16, Items.saddle, "rideWarg").setRequiresAnyAlly(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		killWarg = new LOTRAchievement(Category.GENERAL, 17, LOTRMod.wargBone, "killWarg").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		useSpearFromFar = new LOTRAchievement(Category.GENERAL, 18, LOTRMod.spearIron, "useSpearFromFar");
		wearFullMithril = new LOTRAchievement(Category.GENERAL, 19, LOTRMod.bodyMithril, "wearFullMithril");
		gainHighAlcoholTolerance = new LOTRAchievement(Category.GENERAL, 20, LOTRMod.mugRum, "gainHighAlcoholTolerance").createTitle("alcoholic");
		craftSaddle = new LOTRAchievement(Category.GENERAL, 21, Items.saddle, "craftSaddle");
		craftBronze = new LOTRAchievement(Category.GENERAL, 22, LOTRMod.bronze, "craftBronze");
		drinkOrcDraught = new LOTRAchievement(Category.GENERAL, 23, LOTRMod.mugOrcDraught, "drinkOrcDraught");
		getPouch = new LOTRAchievement(Category.GENERAL, 24, LOTRMod.pouch, "getPouch");
		killUsingOnlyPlates = new LOTRAchievement(Category.GENERAL, 25, LOTRMod.plate, "killUsingOnlyPlates").createTitle();
		wearFullFur = new LOTRAchievement(Category.GENERAL, 26, LOTRMod.bodyFur, "wearFullFur");
		brewDrinkInBarrel = new LOTRAchievement(Category.GENERAL, 27, LOTRMod.barrel, "brewDrinkInBarrel").createTitle("brewDrink");
		findAthelas = new LOTRAchievement(Category.GENERAL, 28, LOTRMod.athelas, "findAthelas");
		drinkAthelasBrew = new LOTRAchievement(Category.GENERAL, 29, LOTRMod.mugAthelasBrew, "drinkAthelasBrew");
		killLargeMobWithSlingshot = new LOTRAchievement(Category.GENERAL, 30, LOTRMod.sling, "killLargeMobWithSlingshot");
		eatMaggotyBread = new LOTRAchievement(Category.GENERAL, 31, LOTRMod.maggotyBread, "eatMaggotyBread");
		killWhileDrunk = new LOTRAchievement(Category.GENERAL, 32, LOTRMod.mugAle, "killWhileDrunk").createTitle();
		collectCraftingTables = new LOTRAchievement(Category.GENERAL, 33, Blocks.crafting_table, "collectCraftingTables").createTitle();
		hitByOrcSpear = new LOTRAchievement(Category.GENERAL, 34, LOTRMod.spearOrc, "hitByOrcSpear").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC));
		killBombardier = new LOTRAchievement(Category.GENERAL, 35, LOTRMod.orcBomb, "killBombardier").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC));
		earnManyCoins = new LOTRAchievement(Category.GENERAL, 36, LOTRMod.silverCoin, "earnManyCoins").createTitle();
		craftAppleCrumble = new LOTRAchievement(Category.GENERAL, 37, LOTRMod.appleCrumbleItem, "craftAppleCrumble");
		killButterfly = new LOTRAchievement(Category.GENERAL, 38, Items.iron_sword, "killButterfly").createTitle();
		fishRing = new LOTRAchievement(Category.GENERAL, 39, Items.fishing_rod, "fishRing").createTitle("fishRing");
		useCrossbow = new LOTRAchievement(Category.GENERAL, 40, LOTRMod.ironCrossbow, "useCrossbow");
		collectCrossbowBolts = new LOTRAchievement(Category.GENERAL, 41, LOTRMod.crossbowBolt, "collectCrossbowBolts");
		travel10 = new LOTRAchievement(Category.GENERAL, 42, Items.leather_boots, "travel10").setSpecial();
		travel20 = new LOTRAchievement(Category.GENERAL, 43, Items.compass, "travel20").setSpecial();
		travel30 = new LOTRAchievement(Category.GENERAL, 44, Items.map, "travel30").setSpecial();
		attackRabbit = new LOTRAchievement(Category.GENERAL, 45, Items.wheat_seeds, "attackRabbit");
		craftRabbitStew = new LOTRAchievement(Category.GENERAL, 46, LOTRMod.rabbitStew, "craftRabbitStew");
		drinkSkull = new LOTRAchievement(Category.GENERAL, 47, LOTRMod.skullCup, "drinkSkull").createTitle("drinkSkull");
		travel40 = new LOTRAchievement(Category.GENERAL, 48, Items.map, "travel40").setSpecial();
		travel50 = new LOTRAchievement(Category.GENERAL, 49, Items.map, "travel50").setSpecial().createTitle("explore50Biomes");
		killThievingBandit = new LOTRAchievement(Category.GENERAL, 50, LOTRMod.leatherHat, "killThievingBandit");
		hundreds = new LOTRAchievement(Category.GENERAL, 51, Items.iron_sword, "hundreds");
		alloyBronze = new LOTRAchievement(Category.GENERAL, 52, LOTRMod.alloyForge, "alloyBronze");
		bannerProtect = new LOTRAchievement(Category.GENERAL, 53, LOTRMod.banner, "bannerProtect").createTitle();
		catchButterfly = new LOTRAchievement(Category.GENERAL, 54, LOTRMod.butterflyJar, "catchButterfly");
		unsmelt = new LOTRAchievement(Category.GENERAL, 55, LOTRMod.unsmeltery, "unsmelt");
		tradeScrapTrader = new LOTRAchievement(Category.GENERAL, 56, LOTRMod.silverCoin, "tradeScrapTrader");
		cookDeer = new LOTRAchievement(Category.GENERAL, 57, LOTRMod.deerCooked, "cookDeer");
		eatManFlesh = new LOTRAchievement(Category.GENERAL, 58, LOTRMod.manFlesh, "eatManFlesh").setRequiresAnyAlly(LOTRItemManFlesh.getManFleshFactions()).createTitle("manFlesh");
		craftSaltedFlesh = new LOTRAchievement(Category.GENERAL, 59, LOTRMod.saltedFlesh, "craftSaltedFlesh").createTitle("saltedFlesh");
		enchantBaneElf = new LOTRAchievement(Category.GENERAL, 60, LOTRMod.scimitarOrc, "enchantBaneElf").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ELF)).createTitle();
		enchantBaneOrc = new LOTRAchievement(Category.GENERAL, 61, LOTRMod.swordHighElven, "enchantBaneOrc").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		enchantBaneDwarf = new LOTRAchievement(Category.GENERAL, 62, LOTRMod.battleaxeGundabadUruk, "enchantBaneDwarf").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_DWARF)).createTitle();
		enchantBaneWarg = new LOTRAchievement(Category.GENERAL, 63, LOTRMod.swordRohan, "enchantBaneWarg").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		enchantBaneTroll = new LOTRAchievement(Category.GENERAL, 64, LOTRMod.swordArnor, "enchantBaneTroll").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_TROLL)).createTitle();
		enchantBaneSpider = new LOTRAchievement(Category.GENERAL, 65, LOTRMod.sting, "enchantBaneSpider").setRequiresAnyEnemy(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC)).createTitle();
		enchantBaneWight = new LOTRAchievement(Category.GENERAL, 66, LOTRMod.daggerBarrow, "enchantBaneWight").createTitle();
		getDrunk = new LOTRAchievement(Category.GENERAL, 67, LOTRMod.mugAle, "getDrunk").createTitle();
		reforge = new LOTRAchievement(Category.GENERAL, 68, Blocks.anvil, "reforge").createTitle();
		doMiniquestHunter = new LOTRAchievement(Category.GENERAL, 69, LOTRMod.redBook, "doMiniquestHunter").createTitle();
		doMiniquestHunter5 = new LOTRAchievement(Category.GENERAL, 70, LOTRMod.bountyTrophy, "doMiniquestHunter5").createTitle();
		killHuntingPlayer = new LOTRAchievement(Category.GENERAL, 71, Items.iron_sword, "killHuntingPlayer").createTitle();
		pledgeService = new LOTRAchievement(Category.GENERAL, 72, LOTRMod.goldRing, "pledgeService");
		factionConquest = new LOTRAchievement(Category.GENERAL, 73, LOTRMod.commandTable, "factionConquest");
		defeatInvasion = new LOTRAchievement(Category.GENERAL, 74, Items.iron_sword, "defeatInvasion");
		pickpocket = new LOTRAchievement(Category.GENERAL, 75, LOTRMiniQuestPickpocket.createPickpocketIcon(), "pickpocket").createTitle("pickpocket");
		combineSmithScrolls = new LOTRAchievement(Category.GENERAL, 76, LOTRMod.modTemplate, "combineSmithScrolls");
		engraveOwnership = new LOTRAchievement(Category.GENERAL, 77, LOTRMod.swordGondolin, "engraveOwnership");
		killHobbit = new LOTRAchievement(Category.SHIRE, 0, LOTRMod.hobbitBone, "killHobbit").setRequiresEnemy(LOTRFaction.HOBBIT).createTitle();
		sellPipeweedLeaf = new LOTRAchievement(Category.SHIRE, 1, LOTRMod.pipeweedLeaf, "sellPipeweedLeaf").setRequiresAlly(LOTRFaction.HOBBIT);
		marryHobbit = new LOTRAchievement(Category.SHIRE, 2, LOTRMod.hobbitRing, "marryHobbit").setRequiresAlly(LOTRFaction.HOBBIT);
		findFourLeafClover = new LOTRAchievement(Category.SHIRE, 3, new ItemStack(LOTRMod.clover, 1, 1), "findFourLeafClover").createTitle("fourLeafClover");
		useMagicPipe = new LOTRAchievement(Category.SHIRE, 4, LOTRMod.hobbitPipe, "useMagicPipe").createTitle();
		rideShirePony = new LOTRAchievement(Category.SHIRE, 5, Items.saddle, "rideShirePony");
		tradeBartender = new LOTRAchievement(Category.SHIRE, 6, LOTRMod.silverCoin, "tradeBartender").setRequiresAlly(LOTRFaction.HOBBIT);
		speakToDrunkard = new LOTRAchievement(Category.SHIRE, 7, LOTRMod.mugAle, "speakToDrunkard");
		tradeHobbitShirriff = new LOTRAchievement(Category.SHIRE, 8, LOTRMod.silverCoin, "tradeHobbitShirriffChief").setRequiresAlly(LOTRFaction.HOBBIT);
		killDarkHuorn = new LOTRAchievement(Category.SHIRE, 9, Blocks.log, "killDarkHuorn");
		enterOldForest = new LOTRAchievement(Category.SHIRE, 10, Blocks.log, "enterOldForest").setBiomeAchievement();
		buyOrcharderFood = new LOTRAchievement(Category.SHIRE, 11, Items.apple, "buyOrcharderFood").setRequiresAlly(LOTRFaction.HOBBIT);
		rideGiraffeShire = new LOTRAchievement(Category.SHIRE, 15, Items.saddle, "rideGiraffeShire").createTitle("zookeeper");
		buyPotatoHobbitFarmer = new LOTRAchievement(Category.SHIRE, 16, Items.potato, "buyPotatoHobbitFarmer").setRequiresAlly(LOTRFaction.HOBBIT);
		doMiniquestHobbit = new LOTRAchievement(Category.SHIRE, 17, LOTRMod.redBook, "doMiniquestHobbit").setRequiresAlly(LOTRFaction.HOBBIT);
		enterWhiteDowns = new LOTRAchievement(Category.SHIRE, 18, new ItemStack(LOTRMod.rock, 1, 5), "enterWhiteDowns").setBiomeAchievement();
		useHobbitTable = new LOTRAchievement(Category.SHIRE, 19, LOTRMod.hobbitTable, "useHobbitTable").setRequiresAlly(LOTRFaction.HOBBIT);
		hireHobbitFarmer = new LOTRAchievement(Category.SHIRE, 20, Items.iron_hoe, "hireHobbitFarmer").setRequiresAlly(LOTRFaction.HOBBIT);
		enterBlueMountains = new LOTRAchievement(Category.BLUE_MOUNTAINS, 0, new ItemStack(LOTRMod.rock, 1, 3), "enterBlueMountains").setBiomeAchievement();
		smeltBlueDwarfSteel = new LOTRAchievement(Category.BLUE_MOUNTAINS, 4, LOTRMod.blueDwarfSteel, "smeltBlueDwarfSteel");
		killBlueDwarf = new LOTRAchievement(Category.BLUE_MOUNTAINS, 5, LOTRMod.dwarfBone, "killBlueDwarf").setRequiresEnemy(LOTRFaction.BLUE_MOUNTAINS).createTitle();
		wearFullBlueDwarven = new LOTRAchievement(Category.BLUE_MOUNTAINS, 6, LOTRMod.bodyBlueDwarven, "wearFullBlueDwarven");
		useBlueDwarvenTable = new LOTRAchievement(Category.BLUE_MOUNTAINS, 7, LOTRMod.blueDwarvenTable, "useBlueDwarvenTable").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		tradeBlueDwarfMiner = new LOTRAchievement(Category.BLUE_MOUNTAINS, 8, LOTRMod.silverCoin, "tradeBlueDwarfMiner").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		tradeBlueDwarfCommander = new LOTRAchievement(Category.BLUE_MOUNTAINS, 9, LOTRMod.silverCoin, "tradeBlueDwarfCommander").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		tradeBlueDwarfMerchant = new LOTRAchievement(Category.BLUE_MOUNTAINS, 10, LOTRMod.silverCoin, "tradeBlueDwarfMerchant").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		marryBlueDwarf = new LOTRAchievement(Category.BLUE_MOUNTAINS, 11, LOTRMod.dwarvenRing, "marryBlueDwarf").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		doMiniquestBlueMountains = new LOTRAchievement(Category.BLUE_MOUNTAINS, 12, LOTRMod.redBook, "doMiniquestBlueMountains").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		tradeBlueDwarfSmith = new LOTRAchievement(Category.BLUE_MOUNTAINS, 13, LOTRMod.silverCoin, "tradeBlueDwarfSmith").setRequiresAlly(LOTRFaction.BLUE_MOUNTAINS);
		enterLindon = new LOTRAchievement(Category.LINDON, 0, new ItemStack(LOTRMod.brick3, 1, 2), "enterLindon").setBiomeAchievement();
		doMiniquestHighElf = new LOTRAchievement(Category.LINDON, 1, LOTRMod.redBook, "doMiniquestHighElf").setRequiresAlly(LOTRFaction.HIGH_ELF);
		killHighElf = new LOTRAchievement(Category.LINDON, 5, LOTRMod.elfBone, "killHighElf").setRequiresEnemy(LOTRFaction.HIGH_ELF).createTitle();
		tradeHighElfLord = new LOTRAchievement(Category.LINDON, 6, LOTRMod.silverCoin, "tradeHighElfLord").setRequiresAlly(LOTRFaction.HIGH_ELF);
		useHighElvenTable = new LOTRAchievement(Category.LINDON, 7, LOTRMod.highElvenTable, "useHighElvenTable").setRequiresAlly(LOTRFaction.HIGH_ELF);
		wearFullHighElven = new LOTRAchievement(Category.LINDON, 8, LOTRMod.bodyHighElven, "wearFullHighElven");
		smeltElfSteel = new LOTRAchievement(Category.LINDON, 9, LOTRMod.elfSteel, "smeltElfSteel");
		tradeHighElfSmith = new LOTRAchievement(Category.LINDON, 10, LOTRMod.silverCoin, "tradeHighElfSmith").setRequiresAlly(LOTRFaction.HIGH_ELF);
		wearFullGondolin = new LOTRAchievement(Category.LINDON, 11, LOTRMod.bodyGondolin, "wearFullGondolin").createTitle("wearFullGondolin");
		enterTowerHills = new LOTRAchievement(Category.LINDON, 12, new ItemStack(LOTRMod.brick4, 1, 15), "enterTowerHills").setBiomeAchievement();
		wearFullGalvorn = new LOTRAchievement(Category.LINDON, 13, LOTRMod.bodyGalvorn, "wearFullGalvorn");
		enterBreeland = new LOTRAchievement(Category.BREE_LAND, 0, new ItemStack(LOTRMod.cobblebrick, 1, 0), "enterBreeland").setBiomeAchievement();
		enterChetwood = new LOTRAchievement(Category.BREE_LAND, 1, Blocks.sapling, "enterChetwood").setBiomeAchievement();
		killBreelander = new LOTRAchievement(Category.BREE_LAND, 2, Items.bone, "killBreelander").setRequiresEnemy(LOTRFaction.BREE).createTitle();
		doMiniquestBree = new LOTRAchievement(Category.BREE_LAND, 3, LOTRMod.redBook, "doMiniquestBree").setRequiresAlly(LOTRFaction.BREE);
		tradeBreeCaptain = new LOTRAchievement(Category.BREE_LAND, 4, LOTRMod.silverCoin, "tradeBreeCaptain").setRequiresAlly(LOTRFaction.BREE);
		useBreeTable = new LOTRAchievement(Category.BREE_LAND, 5, LOTRMod.breeTable, "useBreeTable").setRequiresAlly(LOTRFaction.BREE);
		tradeBreeBlacksmith = new LOTRAchievement(Category.BREE_LAND, 6, LOTRMod.silverCoin, "tradeBreeBlacksmith").setRequiresAlly(LOTRFaction.BREE);
		tradeBreeInnkeeper = new LOTRAchievement(Category.BREE_LAND, 7, LOTRMod.silverCoin, "tradeBreeInnkeeper").setRequiresAlly(LOTRFaction.BREE);
		killBreeHobbit = new LOTRAchievement(Category.BREE_LAND, 8, LOTRMod.hobbitBone, "killBreeHobbit").setRequiresEnemy(LOTRFaction.BREE).createTitle();
		killRuffianSpy = new LOTRAchievement(Category.BREE_LAND, 9, LOTRMod.leatherHat, "killRuffianSpy").createTitle();
		killRuffianBrute = new LOTRAchievement(Category.BREE_LAND, 10, LOTRMod.battleaxeIron, "killRuffianBrute");
		doMiniquestRuffianSpy = new LOTRAchievement(Category.BREE_LAND, 11, LOTRMod.redBook, "doMiniquestRuffianSpy");
		doMiniquestRuffianBrute = new LOTRAchievement(Category.BREE_LAND, 12, LOTRMod.redBook, "doMiniquestRuffianBrute");
		tradeBreeMarketTrader = new LOTRAchievement(Category.BREE_LAND, 13, LOTRMod.silverCoin, "tradeBreeMarketTrader").setRequiresAlly(LOTRFaction.BREE);
		buyAppleBreeFarmer = new LOTRAchievement(Category.BREE_LAND, 14, LOTRMod.appleGreen, "buyAppleBreeFarmer").setRequiresAlly(LOTRFaction.BREE);
		hireBreeFarmer = new LOTRAchievement(Category.BREE_LAND, 15, Items.iron_hoe, "hireBreeFarmer").setRequiresAlly(LOTRFaction.BREE);
		killRangerNorth = new LOTRAchievement(Category.ERIADOR, 0, LOTRMod.rangerBow, "killRangerNorth").setRequiresEnemy(LOTRFaction.RANGER_NORTH).createTitle();
		wearFullRanger = new LOTRAchievement(Category.ERIADOR, 1, LOTRMod.bodyRanger, "wearFullRanger");
		killTroll = new LOTRAchievement(Category.ERIADOR, 2, LOTRMod.trollBone, "killTroll").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		getTrollStatue = new LOTRAchievement(Category.ERIADOR, 3, LOTRMod.trollStatue, "getTrollStatue").createTitle();
		makeTrollSneeze = new LOTRAchievement(Category.ERIADOR, 4, Items.slime_ball, "makeTrollSneeze").setRequiresAlly(LOTRFaction.ANGMAR);
		killMountainTroll = new LOTRAchievement(Category.ERIADOR, 5, LOTRMod.trollBone, "killMountainTroll").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		killTrollFleeingSun = new LOTRAchievement(Category.ERIADOR, 6, Items.feather, "killTrollFleeingSun").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		killMountainTrollChieftain = new LOTRAchievement(Category.ERIADOR, 7, new ItemStack(LOTRMod.bossTrophy, 1, 0), "killMountainTrollChieftain").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle("trollSlayer");
		shootDownMidges = new LOTRAchievement(Category.ERIADOR, 8, Items.arrow, "shootDownMidges");
		enterTrollshaws = new LOTRAchievement(Category.ERIADOR, 9, LOTRMod.muttonCooked, "enterTrollshaws").setBiomeAchievement();
		enterMidgewater = new LOTRAchievement(Category.ERIADOR, 10, LOTRMod.quagmire, "enterMidgewater").setBiomeAchievement();
		enterLoneLands = new LOTRAchievement(Category.ERIADOR, 11, new ItemStack(Blocks.stonebrick, 1, 2), "enterLoneLands").setBiomeAchievement();
		enterEttenmoors = new LOTRAchievement(Category.ERIADOR, 12, new ItemStack(Blocks.sapling, 1, 1), "enterEttenmoors").setBiomeAchievement();
		enterEriador = new LOTRAchievement(Category.ERIADOR, 13, Blocks.grass, "enterEriador").setBiomeAchievement();
		enterColdfells = new LOTRAchievement(Category.ERIADOR, 14, new ItemStack(Blocks.sapling, 1, 0), "enterColdfells").setBiomeAchievement();
		enterSwanfleet = new LOTRAchievement(Category.ERIADOR, 15, Blocks.waterlily, "enterSwanfleet").setBiomeAchievement();
		enterMinhiriath = new LOTRAchievement(Category.ERIADOR, 16, Blocks.grass, "enterMinhiriath").setBiomeAchievement();
		tradeGundabadCaptain = new LOTRAchievement(Category.ERIADOR, 17, LOTRMod.silverCoin, "tradeGundabadCaptain").setRequiresAlly(LOTRFaction.GUNDABAD);
		tradeRangerNorthCaptain = new LOTRAchievement(Category.ERIADOR, 18, LOTRMod.silverCoin, "tradeRangerNorthCaptain").setRequiresAlly(LOTRFaction.RANGER_NORTH);
		enterBarrowDowns = new LOTRAchievement(Category.ERIADOR, 25, Items.bone, "enterBarrowDowns").setBiomeAchievement();
		useRangerTable = new LOTRAchievement(Category.ERIADOR, 26, LOTRMod.rangerTable, "useRangerTable").setRequiresAlly(LOTRFaction.RANGER_NORTH);
		useGundabadTable = new LOTRAchievement(Category.ERIADOR, 27, LOTRMod.gundabadTable, "useGundabadTable").setRequiresAlly(LOTRFaction.GUNDABAD);
		doMiniquestRanger = new LOTRAchievement(Category.ERIADOR, 28, LOTRMod.redBook, "doMiniquestRanger").setRequiresAlly(LOTRFaction.RANGER_NORTH);
		doMiniquestGundabad = new LOTRAchievement(Category.ERIADOR, 29, LOTRMod.redBook, "doMiniquestGundabad").setRequiresAlly(LOTRFaction.GUNDABAD);
		killBarrowWight = new LOTRAchievement(Category.ERIADOR, 30, Items.bone, "killBarrowWight").createTitle("killBarrowWight");
		killGundabadOrc = new LOTRAchievement(Category.ERIADOR, 31, LOTRMod.orcBone, "killGundabadOrc").setRequiresEnemy(LOTRFaction.GUNDABAD).createTitle();
		killGundabadUruk = new LOTRAchievement(Category.ERIADOR, 32, LOTRMod.helmetGundabadUruk, "killGundabadUruk").setRequiresEnemy(LOTRFaction.GUNDABAD);
		wearFullGundabadUruk = new LOTRAchievement(Category.ERIADOR, 33, LOTRMod.bodyGundabadUruk, "wearFullGundabadUruk");
		killDunedain = new LOTRAchievement(Category.ERIADOR, 34, Items.bone, "killDunedain").setRequiresEnemy(LOTRFaction.RANGER_NORTH).createTitle();
		enterAngle = new LOTRAchievement(Category.ERIADOR, 35, new ItemStack(Blocks.wool, 1, 13), "enterAngle").setBiomeAchievement();
		wearFullArnor = new LOTRAchievement(Category.ERIADOR, 36, LOTRMod.bodyArnor, "wearFullArnor");
		tradeDunedainBlacksmith = new LOTRAchievement(Category.ERIADOR, 37, LOTRMod.silverCoin, "tradeDunedainBlacksmith").setRequiresAlly(LOTRFaction.RANGER_NORTH);
		enterRivendell = new LOTRAchievement(Category.ERIADOR, 38, new ItemStack(LOTRMod.brick3, 1, 2), "enterRivendell").setBiomeAchievement();
		useRivendellTable = new LOTRAchievement(Category.ERIADOR, 39, LOTRMod.rivendellTable, "useRivendellTable").setRequiresAlly(LOTRFaction.HIGH_ELF);
		wearFullRivendell = new LOTRAchievement(Category.ERIADOR, 40, LOTRMod.bodyRivendell, "wearFullRivendell");
		tradeRivendellSmith = new LOTRAchievement(Category.ERIADOR, 41, LOTRMod.silverCoin, "tradeRivendellSmith").setRequiresAlly(LOTRFaction.HIGH_ELF);
		tradeRivendellLord = new LOTRAchievement(Category.ERIADOR, 42, LOTRMod.silverCoin, "tradeRivendellLord").setRequiresAlly(LOTRFaction.HIGH_ELF);
		tradeRivendellTrader = new LOTRAchievement(Category.ERIADOR, 43, LOTRMod.silverCoin, "tradeRivendellTrader").setRequiresAlly(LOTRFaction.HIGH_ELF);
		doMiniquestRivendell = new LOTRAchievement(Category.ERIADOR, 44, LOTRMod.redBook, "doMiniquestRivendell").setRequiresAlly(LOTRFaction.HIGH_ELF);
		killRivendellElf = new LOTRAchievement(Category.ERIADOR, 45, LOTRMod.elfBone, "killRivendellElf").setRequiresEnemy(LOTRFaction.HIGH_ELF);
		tradeGundabadTrader = new LOTRAchievement(Category.ERIADOR, 46, LOTRMod.silverCoin, "tradeGundabadTrader").setRequiresAlly(LOTRFaction.GUNDABAD);
		tradeAngmarCaptain = new LOTRAchievement(Category.ANGMAR, 0, LOTRMod.silverCoin, "tradeAngmarCaptain").setRequiresAlly(LOTRFaction.ANGMAR);
		killAngmarOrc = new LOTRAchievement(Category.ANGMAR, 1, LOTRMod.orcBone, "killAngmarOrc").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		enterAngmar = new LOTRAchievement(Category.ANGMAR, 2, new ItemStack(LOTRMod.brick2, 1, 0), "enterAngmar").setBiomeAchievement();
		useAngmarTable = new LOTRAchievement(Category.ANGMAR, 3, LOTRMod.angmarTable, "useAngmarTable").setRequiresAlly(LOTRFaction.ANGMAR);
		wearFullAngmar = new LOTRAchievement(Category.ANGMAR, 4, LOTRMod.bodyAngmar, "wearFullAngmar");
		doMiniquestAngmar = new LOTRAchievement(Category.ANGMAR, 8, LOTRMod.redBook, "doMiniquestAngmar").setRequiresAlly(LOTRFaction.ANGMAR);
		tradeAngmarTrader = new LOTRAchievement(Category.ANGMAR, 9, LOTRMod.silverCoin, "tradeAngmarTrader").setRequiresAlly(LOTRFaction.ANGMAR);
		killAngmarHillman = new LOTRAchievement(Category.ANGMAR, 10, Items.bone, "killAngmarHillman").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		tradeAngmarHillmanChieftain = new LOTRAchievement(Category.ANGMAR, 11, LOTRMod.silverCoin, "tradeAngmarHillmanChieftain").setRequiresAlly(LOTRFaction.ANGMAR);
		killSnowTroll = new LOTRAchievement(Category.ANGMAR, 12, LOTRMod.trollBone, "killSnowTroll").setRequiresEnemy(LOTRFaction.ANGMAR).createTitle();
		enterEregion = new LOTRAchievement(Category.EREGION, 0, new ItemStack(LOTRMod.sapling2, 1, 2), "enterEregion").setBiomeAchievement();
		enterEnedwaith = new LOTRAchievement(Category.ENEDWAITH, 0, Blocks.grass, "enterEnedwaith").setBiomeAchievement();
		enterNanCurunir = new LOTRAchievement(Category.ENEDWAITH, 1, LOTRMod.scimitarUruk, "enterNanCurunir").setBiomeAchievement();
		enterPukel = new LOTRAchievement(Category.ENEDWAITH, 2, new ItemStack(Blocks.sapling, 1, 5), "enterPukel").setBiomeAchievement();
		killDunlending = new LOTRAchievement(Category.DUNLAND, 0, LOTRMod.dunlendingClub, "killDunlending").setRequiresEnemy(LOTRFaction.DUNLAND).createTitle();
		wearFullDunlending = new LOTRAchievement(Category.DUNLAND, 1, LOTRMod.bodyDunlending, "wearFullDunlending");
		useDunlendingTable = new LOTRAchievement(Category.DUNLAND, 2, LOTRMod.dunlendingTable, "useDunlendingTable").setRequiresAlly(LOTRFaction.DUNLAND);
		tradeDunlendingWarlord = new LOTRAchievement(Category.DUNLAND, 3, LOTRMod.silverCoin, "tradeDunlendingWarlord").setRequiresAlly(LOTRFaction.DUNLAND);
		useDunlendingTrident = new LOTRAchievement(Category.DUNLAND, 4, LOTRMod.dunlendingTrident, "useDunlendingTrident");
		tradeDunlendingBartender = new LOTRAchievement(Category.DUNLAND, 5, LOTRMod.silverCoin, "tradeDunlendingBartender").setRequiresAlly(LOTRFaction.DUNLAND);
		enterDunland = new LOTRAchievement(Category.DUNLAND, 6, Items.stone_sword, "enterDunland").setBiomeAchievement();
		doMiniquestDunland = new LOTRAchievement(Category.DUNLAND, 10, LOTRMod.redBook, "doMiniquestDunland").setRequiresAlly(LOTRFaction.DUNLAND);
		climbMistyMountains = new LOTRAchievement(Category.MISTY_MOUNTAINS, 0, Blocks.snow, "climbMistyMountains");
		enterMistyMountains = new LOTRAchievement(Category.MISTY_MOUNTAINS, 1, new ItemStack(Blocks.stone, 1, 1000), "enterMistyMountains").setBiomeAchievement();
		tameGollum = new LOTRAchievement(Category.MISTY_MOUNTAINS, 2, Items.fish, "tameGollum");
		enterForodwaith = new LOTRAchievement(Category.FORODWAITH, 0, Blocks.ice, "enterForodwaith").setBiomeAchievement();
		enterValesOfAnduin = new LOTRAchievement(Category.RHOVANION, 0, Blocks.sapling, "enterValesOfAnduin").setBiomeAchievement();
		enterGreyMountains = new LOTRAchievement(Category.RHOVANION, 1, Blocks.stone, "enterGreyMountains").setBiomeAchievement();
		enterGladdenFields = new LOTRAchievement(Category.RHOVANION, 2, new ItemStack(LOTRMod.doubleFlower, 1, 1), "enterGladdenFields").setBiomeAchievement();
		enterEmynMuil = new LOTRAchievement(Category.RHOVANION, 3, Blocks.stone, "enterEmynMuil").setBiomeAchievement();
		enterBrownLands = new LOTRAchievement(Category.RHOVANION, 4, Blocks.dirt, "enterBrownLands").setBiomeAchievement();
		enterWilderland = new LOTRAchievement(Category.RHOVANION, 5, Blocks.grass, "enterWilderland").setBiomeAchievement();
		enterDagorlad = new LOTRAchievement(Category.RHOVANION, 6, LOTRMod.mordorGravel, "enterDagorlad").setBiomeAchievement();
		enterCelebrant = new LOTRAchievement(Category.RHOVANION, 8, new ItemStack(Blocks.red_flower, 1, 3), "enterCelebrant").setBiomeAchievement();
		enterLongMarshes = new LOTRAchievement(Category.RHOVANION, 9, new ItemStack(LOTRMod.reeds, 1, 0), "enterLongMarshes").setBiomeAchievement();
		enterEastBight = new LOTRAchievement(Category.RHOVANION, 10, new ItemStack(Blocks.log, 1, 0), "enterEastBight").setBiomeAchievement();
		killMirkwoodSpider = new LOTRAchievement(Category.MIRKWOOD, 0, Items.string, "killMirkwoodSpider").setRequiresEnemy(LOTRFaction.DOL_GULDUR).createTitle();
		killWoodElf = new LOTRAchievement(Category.MIRKWOOD, 1, LOTRMod.elfBone, "killWoodElf").setRequiresEnemy(LOTRFaction.WOOD_ELF).createTitle();
		useWoodElvenTable = new LOTRAchievement(Category.MIRKWOOD, 2, LOTRMod.woodElvenTable, "useWoodElvenTable").setRequiresAlly(LOTRFaction.WOOD_ELF);
		wearFullWoodElvenScout = new LOTRAchievement(Category.MIRKWOOD, 4, LOTRMod.bodyWoodElvenScout, "wearFullWoodElvenScout");
		tradeWoodElfCaptain = new LOTRAchievement(Category.MIRKWOOD, 5, LOTRMod.silverCoin, "tradeWoodElfCaptain").setRequiresAlly(LOTRFaction.WOOD_ELF);
		rideBarrelMirkwood = new LOTRAchievement(Category.MIRKWOOD, 6, LOTRMod.barrel, "rideBarrelMirkwood").createTitle("rideBarrel");
		enterMirkwood = new LOTRAchievement(Category.MIRKWOOD, 7, LOTRMod.webUngoliant, "enterMirkwood").setBiomeAchievement();
		enterWoodlandRealm = new LOTRAchievement(Category.MIRKWOOD, 8, LOTRMod.mirkwoodBow, "enterWoodlandRealm").setBiomeAchievement();
		wearFullWoodElven = new LOTRAchievement(Category.MIRKWOOD, 9, LOTRMod.bodyWoodElven, "wearFullWoodElven");
		enterDolGuldur = new LOTRAchievement(Category.MIRKWOOD, 17, new ItemStack(LOTRMod.brick2, 1, 8), "enterDolGuldur").setBiomeAchievement();
		killDolGuldurOrc = new LOTRAchievement(Category.MIRKWOOD, 18, LOTRMod.orcBone, "killDolGuldurOrc").setRequiresEnemy(LOTRFaction.DOL_GULDUR).createTitle();
		useDolGuldurTable = new LOTRAchievement(Category.MIRKWOOD, 19, LOTRMod.dolGuldurTable, "useDolGuldurTable").setRequiresAlly(LOTRFaction.DOL_GULDUR);
		tradeDolGuldurCaptain = new LOTRAchievement(Category.MIRKWOOD, 20, LOTRMod.silverCoin, "tradeDolGuldurCaptain").setRequiresAlly(LOTRFaction.DOL_GULDUR);
		killMirkTroll = new LOTRAchievement(Category.MIRKWOOD, 21, LOTRMod.trollBone, "killMirkTroll").setRequiresEnemy(LOTRFaction.DOL_GULDUR).createTitle();
		wearFullDolGuldur = new LOTRAchievement(Category.MIRKWOOD, 22, LOTRMod.bodyDolGuldur, "wearFullDolGuldur");
		doMiniquestWoodElf = new LOTRAchievement(Category.MIRKWOOD, 23, LOTRMod.redBook, "doMiniquestWoodElf").setRequiresAlly(LOTRFaction.WOOD_ELF);
		doMiniquestDolGuldur = new LOTRAchievement(Category.MIRKWOOD, 24, LOTRMod.redBook, "doMiniquestDolGuldur").setRequiresAlly(LOTRFaction.DOL_GULDUR);
		tradeDolGuldurTrader = new LOTRAchievement(Category.MIRKWOOD, 25, LOTRMod.silverCoin, "tradeDolGuldurTrader").setRequiresAlly(LOTRFaction.DOL_GULDUR);
		tradeWoodElfSmith = new LOTRAchievement(Category.MIRKWOOD, 26, LOTRMod.silverCoin, "tradeWoodElfSmith").setRequiresAlly(LOTRFaction.WOOD_ELF);
		enterDale = new LOTRAchievement(Category.DALE, 0, LOTRMod.dalishPastryItem, "enterDale").setBiomeAchievement();
		doMiniquestDale = new LOTRAchievement(Category.DALE, 4, LOTRMod.redBook, "doMiniquestDale").setRequiresAlly(LOTRFaction.DALE);
		useDaleTable = new LOTRAchievement(Category.DALE, 5, LOTRMod.daleTable, "useDaleTable").setRequiresAlly(LOTRFaction.DALE);
		wearFullDale = new LOTRAchievement(Category.DALE, 6, LOTRMod.bodyDale, "wearFullDale");
		killDalish = new LOTRAchievement(Category.DALE, 7, Items.bone, "killDalish").setRequiresEnemy(LOTRFaction.DALE).createTitle();
		tradeDaleCaptain = new LOTRAchievement(Category.DALE, 8, LOTRMod.silverCoin, "tradeDaleCaptain").setRequiresAlly(LOTRFaction.DALE);
		tradeDaleBlacksmith = new LOTRAchievement(Category.DALE, 9, LOTRMod.silverCoin, "tradeDaleBlacksmith").setRequiresAlly(LOTRFaction.DALE);
		tradeDaleBaker = new LOTRAchievement(Category.DALE, 10, LOTRMod.silverCoin, "tradeDaleBaker").setRequiresAlly(LOTRFaction.DALE);
		tradeDaleMerchant = new LOTRAchievement(Category.DALE, 11, LOTRMod.silverCoin, "tradeDaleMerchant").setRequiresAlly(LOTRFaction.DALE);
		enterErebor = new LOTRAchievement(Category.DALE, 12, new ItemStack(LOTRMod.brick, 1, 6), "enterErebor").setBiomeAchievement();
		openDaleCracker = new LOTRAchievement(Category.DALE, 13, new ItemStack(LOTRMod.daleCracker, 1, 0), "openDaleCracker");
		killDwarf = new LOTRAchievement(Category.IRON_HILLS, 0, LOTRMod.dwarfBone, "killDwarf").setRequiresEnemy(LOTRFaction.DURINS_FOLK).createTitle();
		wearFullDwarven = new LOTRAchievement(Category.IRON_HILLS, 1, LOTRMod.bodyDwarven, "wearFullDwarven");
		useDwarvenThrowingAxe = new LOTRAchievement(Category.IRON_HILLS, 2, LOTRMod.throwingAxeDwarven, "useDwarvenThrowingAxe");
		useDwarvenTable = new LOTRAchievement(Category.IRON_HILLS, 3, LOTRMod.dwarvenTable, "useDwarvenTable").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		tradeDwarfMiner = new LOTRAchievement(Category.IRON_HILLS, 4, LOTRMod.silverCoin, "tradeDwarfMiner").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		tradeDwarfCommander = new LOTRAchievement(Category.IRON_HILLS, 5, LOTRMod.silverCoin, "tradeDwarfCommander").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		mineGlowstone = new LOTRAchievement(Category.IRON_HILLS, 6, LOTRMod.oreGlowstone, "mineGlowstone");
		smeltDwarfSteel = new LOTRAchievement(Category.IRON_HILLS, 7, LOTRMod.dwarfSteel, "smeltDwarfSteel");
		drinkDwarvenTonic = new LOTRAchievement(Category.IRON_HILLS, 8, LOTRMod.mugDwarvenTonic, "drinkDwarvenTonic");
		craftMithrilDwarvenBrick = new LOTRAchievement(Category.IRON_HILLS, 9, new ItemStack(LOTRMod.brick, 1, 10), "craftMithrilDwarvenBrick");
		talkDwarfWoman = new LOTRAchievement(Category.IRON_HILLS, 10, LOTRMod.mugDwarvenAle, "talkDwarfWoman").setRequiresAnyAlly(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_DWARF));
		enterIronHills = new LOTRAchievement(Category.IRON_HILLS, 11, LOTRMod.pickaxeDwarven, "enterIronHills").setBiomeAchievement();
		useDwarvenDoor = new LOTRAchievement(Category.IRON_HILLS, 12, LOTRMod.dwarvenDoor, "useDwarvenDoor");
		marryDwarf = new LOTRAchievement(Category.IRON_HILLS, 16, LOTRMod.dwarvenRing, "marryDwarf").setRequiresAnyAlly(LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_DWARF));
		doMiniquestDwarf = new LOTRAchievement(Category.IRON_HILLS, 17, LOTRMod.redBook, "doMiniquestDwarf").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		tradeIronHillsMerchant = new LOTRAchievement(Category.IRON_HILLS, 18, LOTRMod.silverCoin, "tradeIronHillsMerchant").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		tradeDwarfSmith = new LOTRAchievement(Category.IRON_HILLS, 19, LOTRMod.silverCoin, "tradeDwarfSmith").setRequiresAlly(LOTRFaction.DURINS_FOLK);
		killElf = new LOTRAchievement(Category.LOTHLORIEN, 0, LOTRMod.elfBone, "killElf").setRequiresEnemy(LOTRFaction.LOTHLORIEN).createTitle();
		useElvenPortal = new LOTRAchievement(Category.LOTHLORIEN, 1, LOTRMod.quenditeGrass, "useElvenPortal").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		wearFullElven = new LOTRAchievement(Category.LOTHLORIEN, 2, LOTRMod.bodyElven, "wearFullElven");
		useElvenTable = new LOTRAchievement(Category.LOTHLORIEN, 3, LOTRMod.elvenTable, "useElvenTable").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		tradeElfLord = new LOTRAchievement(Category.LOTHLORIEN, 4, LOTRMod.silverCoin, "tradeElfLord").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		mineQuendite = new LOTRAchievement(Category.LOTHLORIEN, 5, LOTRMod.oreQuendite, "mineQuendite");
		takeMallornWood = new LOTRAchievement(Category.LOTHLORIEN, 6, new ItemStack(LOTRMod.wood, 1, 1), "takeMallornWood").setRequiresEnemy(LOTRFaction.LOTHLORIEN);
		enterLothlorien = new LOTRAchievement(Category.LOTHLORIEN, 8, new ItemStack(LOTRMod.sapling, 1, 1), "enterLothlorien").setBiomeAchievement();
		tradeElvenTrader = new LOTRAchievement(Category.LOTHLORIEN, 12, LOTRMod.silverCoin, "tradeElvenTrader").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		doMiniquestGaladhrim = new LOTRAchievement(Category.LOTHLORIEN, 13, LOTRMod.redBook, "doMiniquestGaladhrim").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		tradeGaladhrimSmith = new LOTRAchievement(Category.LOTHLORIEN, 14, LOTRMod.silverCoin, "tradeGaladhrimSmith").setRequiresAlly(LOTRFaction.LOTHLORIEN);
		wearFullHithlain = new LOTRAchievement(Category.LOTHLORIEN, 15, LOTRMod.bodyHithlain, "wearFullHithlain");
		killEnt = new LOTRAchievement(Category.FANGORN, 0, Blocks.log, "killEnt").setRequiresEnemy(LOTRFaction.FANGORN).createTitle("killEnt");
		drinkEntDraught = new LOTRAchievement(Category.FANGORN, 1, LOTRMod.entDraught, "drinkEntDraught").setRequiresAlly(LOTRFaction.FANGORN);
		killHuorn = new LOTRAchievement(Category.FANGORN, 2, Blocks.log, "killHuorn").setRequiresEnemy(LOTRFaction.FANGORN).createTitle();
		talkEnt = new LOTRAchievement(Category.FANGORN, 3, Blocks.log, "talkEnt");
		enterFangorn = new LOTRAchievement(Category.FANGORN, 4, Blocks.leaves, "enterFangorn").setBiomeAchievement();
		summonHuorn = new LOTRAchievement(Category.FANGORN, 8, new ItemStack(LOTRMod.entDraught, 1, 2), "summonHuorn").setRequiresAlly(LOTRFaction.FANGORN);
		killMallornEnt = new LOTRAchievement(Category.FANGORN, 9, new ItemStack(LOTRMod.bossTrophy, 1, 1), "killMallornEnt").setRequiresEnemy(LOTRFaction.FANGORN).createTitle("entSlayer");
		raidUrukCamp = new LOTRAchievement(Category.ROHAN, 0, Items.skull, "raidUrukCamp").setRequiresEnemy(LOTRFaction.ISENGARD);
		useUrukTable = new LOTRAchievement(Category.ROHAN, 1, LOTRMod.urukTable, "useUrukTable").setRequiresAlly(LOTRFaction.ISENGARD);
		tradeUrukTrader = new LOTRAchievement(Category.ROHAN, 2, LOTRMod.silverCoin, "tradeUrukTrader").setRequiresAlly(LOTRFaction.ISENGARD);
		tradeUrukCaptain = new LOTRAchievement(Category.ROHAN, 3, LOTRMod.silverCoin, "tradeUrukCaptain").setRequiresAlly(LOTRFaction.ISENGARD);
		useRohirricTable = new LOTRAchievement(Category.ROHAN, 4, LOTRMod.rohirricTable, "useRohirricTable").setRequiresAlly(LOTRFaction.ROHAN);
		smeltUrukSteel = new LOTRAchievement(Category.ROHAN, 5, LOTRMod.urukSteel, "smeltUrukSteel");
		wearFullUruk = new LOTRAchievement(Category.ROHAN, 6, LOTRMod.bodyUruk, "wearFullUruk");
		hireWargBombardier = new LOTRAchievement(Category.ROHAN, 7, LOTRMod.fur, "hireWargBombardier").setRequiresAlly(LOTRFaction.ISENGARD);
		killRohirrim = new LOTRAchievement(Category.ROHAN, 8, LOTRMod.swordRohan, "killRohirrim").setRequiresEnemy(LOTRFaction.ROHAN).createTitle();
		tradeRohirrimMarshal = new LOTRAchievement(Category.ROHAN, 9, LOTRMod.silverCoin, "tradeRohirrimMarshal").setRequiresAlly(LOTRFaction.ROHAN);
		wearFullRohirric = new LOTRAchievement(Category.ROHAN, 10, LOTRMod.bodyRohan, "wearFullRohirric");
		tradeRohanBlacksmith = new LOTRAchievement(Category.ROHAN, 11, LOTRMod.silverCoin, "tradeRohanBlacksmith").setRequiresAlly(LOTRFaction.ROHAN);
		buyRohanMead = new LOTRAchievement(Category.ROHAN, 12, LOTRMod.mugMead, "buyRohanMead").setRequiresAlly(LOTRFaction.ROHAN);
		enterRohan = new LOTRAchievement(Category.ROHAN, 13, LOTRMod.spearRohan, "enterRohan").setBiomeAchievement();
		enterRohanUrukHighlands = new LOTRAchievement(Category.ROHAN, 14, LOTRMod.helmetUruk, "enterRohanUrukHighlands").setBiomeAchievement();
		doMiniquestRohan = new LOTRAchievement(Category.ROHAN, 21, LOTRMod.redBook, "doMiniquestRohan").setRequiresAlly(LOTRFaction.ROHAN);
		doMiniquestIsengard = new LOTRAchievement(Category.ROHAN, 22, LOTRMod.redBook, "doMiniquestIsengard").setRequiresAlly(LOTRFaction.ISENGARD);
		killUrukHai = new LOTRAchievement(Category.ROHAN, 23, LOTRMod.orcBone, "killUrukHai").setRequiresEnemy(LOTRFaction.ISENGARD).createTitle();
		enterEntwashMouth = new LOTRAchievement(Category.ROHAN, 24, new ItemStack(Blocks.tallgrass, 1, 2), "enterEntwashMouth").setBiomeAchievement();
		wearFullRohirricMarshal = new LOTRAchievement(Category.ROHAN, 25, LOTRMod.bodyRohanMarshal, "wearFullRohirricMarshal");
		killIsengardSnaga = new LOTRAchievement(Category.ROHAN, 26, LOTRMod.orcBone, "killIsengardSnaga").setRequiresEnemy(LOTRFaction.ISENGARD).createTitle();
		doMiniquestRohanShieldmaiden = new LOTRAchievement(Category.ROHAN, 27, LOTRMod.redBook, "doMiniquestRohanShieldmaiden").setRequiresAlly(LOTRFaction.ROHAN);
		tradeRohanFarmer = new LOTRAchievement(Category.ROHAN, 28, LOTRMod.silverCoin, "tradeRohanFarmer").setRequiresAlly(LOTRFaction.ROHAN);
		hireRohanFarmer = new LOTRAchievement(Category.ROHAN, 29, Items.iron_hoe, "hireRohanFarmer").setRequiresAlly(LOTRFaction.ROHAN);
		tradeRohanMarketTrader = new LOTRAchievement(Category.ROHAN, 30, LOTRMod.silverCoin, "tradeRohanMarketTrader").setRequiresAlly(LOTRFaction.ROHAN);
		tradeRohanStablemaster = new LOTRAchievement(Category.ROHAN, 31, LOTRMod.silverCoin, "tradeRohanStablemaster").setRequiresAlly(LOTRFaction.ROHAN);
		enterAdornland = new LOTRAchievement(Category.ROHAN, 32, Items.skull, "enterAdornland").setBiomeAchievement();
		killGondorian = new LOTRAchievement(Category.GONDOR, 0, LOTRMod.swordGondor, "killGondorian").setRequiresEnemy(LOTRFaction.GONDOR).createTitle();
		lightGondorBeacon = new LOTRAchievement(Category.GONDOR, 1, LOTRMod.beacon, "lightGondorBeacon");
		useGondorianTable = new LOTRAchievement(Category.GONDOR, 2, LOTRMod.gondorianTable, "useGondorianTable").setRequiresAlly(LOTRFaction.GONDOR);
		tradeGondorBlacksmith = new LOTRAchievement(Category.GONDOR, 3, LOTRMod.silverCoin, "tradeGondorBlacksmith").setRequiresAlly(LOTRFaction.GONDOR);
		tradeGondorianCaptain = new LOTRAchievement(Category.GONDOR, 4, LOTRMod.silverCoin, "tradeGondorianCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		wearFullGondorian = new LOTRAchievement(Category.GONDOR, 5, LOTRMod.bodyGondor, "wearFullGondorian");
		killRangerIthilien = new LOTRAchievement(Category.GONDOR, 6, LOTRMod.gondorBow, "killRangerIthilien").setRequiresEnemy(LOTRFaction.GONDOR).createTitle();
		enterGondor = new LOTRAchievement(Category.GONDOR, 7, LOTRMod.spearGondor, "enterGondor").setBiomeAchievement();
		enterIthilien = new LOTRAchievement(Category.GONDOR, 8, LOTRMod.gondorBow, "enterIthilien").setBiomeAchievement();
		enterWhiteMountains = new LOTRAchievement(Category.GONDOR, 9, new ItemStack(LOTRMod.rock, 1, 1), "enterWhiteMountains").setBiomeAchievement();
		enterTolfalas = new LOTRAchievement(Category.GONDOR, 13, Blocks.stone, "enterTolfalas").setBiomeAchievement();
		enterLebennin = new LOTRAchievement(Category.GONDOR, 14, new ItemStack(Blocks.sapling, 1, 2), "enterLebennin").setBiomeAchievement();
		doMiniquestGondor = new LOTRAchievement(Category.GONDOR, 15, LOTRMod.redBook, "doMiniquestGondor").setRequiresAlly(LOTRFaction.GONDOR);
		tradeRangerIthilienCaptain = new LOTRAchievement(Category.GONDOR, 16, LOTRMod.silverCoin, "tradeRangerIthilienCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		useDolAmrothTable = new LOTRAchievement(Category.GONDOR, 17, LOTRMod.dolAmrothTable, "useDolAmrothTable").setRequiresAlly(LOTRFaction.GONDOR);
		wearFullDolAmroth = new LOTRAchievement(Category.GONDOR, 18, LOTRMod.bodyDolAmroth, "wearFullDolAmroth");
		killSwanKnight = new LOTRAchievement(Category.GONDOR, 19, LOTRMod.swordDolAmroth, "killSwanKnight").setRequiresEnemy(LOTRFaction.GONDOR).createTitle();
		enterDorEnErnil = new LOTRAchievement(Category.GONDOR, 20, Items.feather, "enterDorEnErnil").setBiomeAchievement();
		tradeDolAmrothCaptain = new LOTRAchievement(Category.GONDOR, 21, LOTRMod.silverCoin, "tradeDolAmrothCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		enterAnduinMouth = new LOTRAchievement(Category.GONDOR, 22, Blocks.waterlily, "enterAnduinMouth").setBiomeAchievement();
		enterPelennor = new LOTRAchievement(Category.GONDOR, 23, new ItemStack(LOTRMod.brick, 1, 1), "enterPelennor").setBiomeAchievement();
		wearFullRangerIthilien = new LOTRAchievement(Category.GONDOR, 24, LOTRMod.bodyRangerIthilien, "wearFullRangerIthilien");
		enterLossarnach = new LOTRAchievement(Category.GONDOR, 25, new ItemStack(LOTRMod.fruitSapling, 1, 0), "enterLossarnach").setBiomeAchievement();
		enterImlothMelui = new LOTRAchievement(Category.GONDOR, 26, new ItemStack(Blocks.double_plant, 1, 4), "enterImlothMelui").setBiomeAchievement();
		wearFullLossarnach = new LOTRAchievement(Category.GONDOR, 27, LOTRMod.bodyLossarnach, "wearFullLossarnach");
		wearFullPelargir = new LOTRAchievement(Category.GONDOR, 28, LOTRMod.bodyPelargir, "wearFullPelargir");
		wearFullPinnathGelin = new LOTRAchievement(Category.GONDOR, 29, LOTRMod.bodyPinnathGelin, "wearFullPinnathGelin");
		wearFullBlackroot = new LOTRAchievement(Category.GONDOR, 30, LOTRMod.bodyBlackroot, "wearFullBlackroot");
		hireGondorFarmer = new LOTRAchievement(Category.GONDOR, 31, Items.iron_hoe, "hireGondorFarmer").setRequiresAlly(LOTRFaction.GONDOR);
		buyPipeweedGondorFarmer = new LOTRAchievement(Category.GONDOR, 32, LOTRMod.pipeweedPlant, "buyPipeweedGondorFarmer").setRequiresAlly(LOTRFaction.GONDOR);
		tradeGondorBartender = new LOTRAchievement(Category.GONDOR, 33, LOTRMod.silverCoin, "tradeGondorBartender").setRequiresAlly(LOTRFaction.GONDOR);
		tradeGondorMarketTrader = new LOTRAchievement(Category.GONDOR, 34, LOTRMod.silverCoin, "tradeGondorMarketTrader").setRequiresAlly(LOTRFaction.GONDOR);
		tradeLossarnachCaptain = new LOTRAchievement(Category.GONDOR, 35, LOTRMod.silverCoin, "tradeLossarnachCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		tradePelargirCaptain = new LOTRAchievement(Category.GONDOR, 36, LOTRMod.silverCoin, "tradePelargirCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		tradePinnathGelinCaptain = new LOTRAchievement(Category.GONDOR, 37, LOTRMod.silverCoin, "tradePinnathGelinCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		tradeBlackrootCaptain = new LOTRAchievement(Category.GONDOR, 38, LOTRMod.silverCoin, "tradeBlackrootCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		tradeLebenninCaptain = new LOTRAchievement(Category.GONDOR, 39, LOTRMod.silverCoin, "tradeLebenninCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		enterPelargir = new LOTRAchievement(Category.GONDOR, 40, LOTRMod.tridentPelargir, "enterPelargir").setBiomeAchievement();
		wearFullLamedon = new LOTRAchievement(Category.GONDOR, 41, LOTRMod.bodyLamedon, "wearFullLamedon");
		tradeLamedonCaptain = new LOTRAchievement(Category.GONDOR, 42, LOTRMod.silverCoin, "tradeLamedonCaptain").setRequiresAlly(LOTRFaction.GONDOR);
		enterLamedon = new LOTRAchievement(Category.GONDOR, 43, LOTRMod.axeBronze, "enterLamedon").setBiomeAchievement();
		enterBlackrootVale = new LOTRAchievement(Category.GONDOR, 44, LOTRMod.blackroot, "enterBlackrootVale").setBiomeAchievement();
		enterPinnathGelin = new LOTRAchievement(Category.GONDOR, 45, Blocks.grass, "enterPinnathGelin").setBiomeAchievement();
		enterAndrast = new LOTRAchievement(Category.GONDOR, 46, Blocks.stone, "enterAndrast").setBiomeAchievement();
		doMiniquestGondorKillRenegade = new LOTRAchievement(Category.GONDOR, 47, LOTRMod.redBook, "doMiniquestGondorKillRenegade").setRequiresAlly(LOTRFaction.GONDOR).createTitle("killGondorRenegade");
		mineRemains = new LOTRAchievement(Category.NINDALF, 0, LOTRMod.remains, "mineRemains");
		craftAncientItem = new LOTRAchievement(Category.NINDALF, 1, LOTRMod.ancientItem, "craftAncientItem");
		enterDeadMarshes = new LOTRAchievement(Category.NINDALF, 2, LOTRMod.deadPlant, "enterDeadMarshes").setBiomeAchievement();
		enterNindalf = new LOTRAchievement(Category.NINDALF, 3, new ItemStack(Blocks.tallgrass, 1, 2), "enterNindalf").setBiomeAchievement();
		killMarshWraith = new LOTRAchievement(Category.NINDALF, 4, Items.skull, "killMarshWraith").createTitle();
		killOlogHai = new LOTRAchievement(Category.MORDOR, 0, LOTRMod.trollBone, "killOlogHai").setRequiresEnemy(LOTRFaction.MORDOR).createTitle();
		useMorgulTable = new LOTRAchievement(Category.MORDOR, 1, LOTRMod.morgulTable, "useMorgulTable").setRequiresAlly(LOTRFaction.MORDOR);
		smeltOrcSteel = new LOTRAchievement(Category.MORDOR, 2, LOTRMod.orcSteel, "smeltOrcSteel");
		wearFullOrc = new LOTRAchievement(Category.MORDOR, 3, LOTRMod.bodyOrc, "wearFullOrc");
		tradeOrcTrader = new LOTRAchievement(Category.MORDOR, 4, LOTRMod.silverCoin, "tradeOrcTrader").setRequiresAlly(LOTRFaction.MORDOR);
		tradeOrcCaptain = new LOTRAchievement(Category.MORDOR, 5, LOTRMod.silverCoin, "tradeOrcCaptain").setRequiresAlly(LOTRFaction.MORDOR);
		mineNaurite = new LOTRAchievement(Category.MORDOR, 6, LOTRMod.oreNaurite, "mineNaurite");
		eatMorgulShroom = new LOTRAchievement(Category.MORDOR, 7, LOTRMod.morgulShroom, "eatMorgulShroom");
		craftOrcBomb = new LOTRAchievement(Category.MORDOR, 8, LOTRMod.orcBomb, "craftOrcBomb").setRequiresAlly(LOTRFaction.MORDOR);
		hireOlogHai = new LOTRAchievement(Category.MORDOR, 9, LOTRMod.hammerOrc, "hireOlogHai").setRequiresAlly(LOTRFaction.MORDOR);
		mineGulduril = new LOTRAchievement(Category.MORDOR, 10, new ItemStack(LOTRMod.oreGulduril, 1, 1), "mineGulduril");
		useMorgulPortal = new LOTRAchievement(Category.MORDOR, 11, LOTRMod.guldurilCrystal, "useMorgulPortal").setRequiresAlly(LOTRFaction.MORDOR);
		wearFullMorgul = new LOTRAchievement(Category.MORDOR, 12, LOTRMod.bodyMorgul, "wearFullMorgul");
		enterMordor = new LOTRAchievement(Category.MORDOR, 13, new ItemStack(LOTRMod.rock, 1, 0), "enterMordor").setBiomeAchievement();
		enterNurn = new LOTRAchievement(Category.MORDOR, 14, LOTRMod.hoeOrc, "enterNurn").setBiomeAchievement();
		enterNanUngol = new LOTRAchievement(Category.MORDOR, 15, LOTRMod.webUngoliant, "enterNanUngol").setBiomeAchievement();
		killMordorSpider = new LOTRAchievement(Category.MORDOR, 16, Items.string, "killMordorSpider").setRequiresEnemy(LOTRFaction.MORDOR).createTitle();
		tradeOrcSpiderKeeper = new LOTRAchievement(Category.MORDOR, 17, LOTRMod.silverCoin, "tradeOrcSpiderKeeper").setRequiresAlly(LOTRFaction.MORDOR);
		killMordorOrc = new LOTRAchievement(Category.MORDOR, 18, LOTRMod.orcBone, "killMordorOrc").setRequiresEnemy(LOTRFaction.MORDOR).createTitle();
		doMiniquestMordor = new LOTRAchievement(Category.MORDOR, 22, LOTRMod.redBook, "doMiniquestMordor").setRequiresAlly(LOTRFaction.MORDOR);
		smeltBlackUrukSteel = new LOTRAchievement(Category.MORDOR, 23, LOTRMod.blackUrukSteel, "smeltBlackUrukSteel");
		wearFullBlackUruk = new LOTRAchievement(Category.MORDOR, 24, LOTRMod.bodyBlackUruk, "wearFullBlackUruk");
		killBlackUruk = new LOTRAchievement(Category.MORDOR, 25, LOTRMod.helmetBlackUruk, "killBlackUruk").setRequiresEnemy(LOTRFaction.MORDOR).createTitle();
		hireNurnSlave = new LOTRAchievement(Category.MORDOR, 26, LOTRMod.brandingIron, "hireNurnSlave").setRequiresAlly(LOTRFaction.MORDOR);
		enterMorgulVale = new LOTRAchievement(Category.MORDOR, 27, Items.skull, "enterMorgulVale").setBiomeAchievement();
		tradeBlackUrukCaptain = new LOTRAchievement(Category.MORDOR, 28, LOTRMod.silverCoin, "tradeBlackUrukCaptain").setRequiresAlly(LOTRFaction.MORDOR);
		killWickedDwarf = new LOTRAchievement(Category.MORDOR, 29, LOTRMod.pickaxeDwarven, "killWickedDwarf").setRequiresEnemy(LOTRFaction.MORDOR).createTitle();
		tradeWickedDwarf = new LOTRAchievement(Category.MORDOR, 30, LOTRMod.silverCoin, "tradeWickedDwarf").setRequiresAlly(LOTREntityWickedDwarf.getTradeFactions());
		enterDorwinion = new LOTRAchievement(Category.DORWINION, 0, LOTRMod.mugWhiteWine, "enterDorwinion").setBiomeAchievement();
		doMiniquestDorwinion = new LOTRAchievement(Category.DORWINION, 4, LOTRMod.redBook, "doMiniquestDorwinion").setRequiresAlly(LOTRFaction.DORWINION);
		useDorwinionTable = new LOTRAchievement(Category.DORWINION, 5, LOTRMod.dorwinionTable, "useDorwinionTable").setRequiresAlly(LOTRFaction.DORWINION);
		wearFullDorwinion = new LOTRAchievement(Category.DORWINION, 6, LOTRMod.bodyDorwinion, "wearFullDorwinion");
		wearFullDorwinionElf = new LOTRAchievement(Category.DORWINION, 7, LOTRMod.bodyDorwinionElf, "wearFullDorwinionElf");
		killDorwinion = new LOTRAchievement(Category.DORWINION, 8, Items.bone, "killDorwinion").setRequiresEnemy(LOTRFaction.DORWINION).createTitle();
		tradeDorwinionCaptain = new LOTRAchievement(Category.DORWINION, 9, LOTRMod.silverCoin, "tradeDorwinionCaptain").setRequiresAlly(LOTRFaction.DORWINION);
		killDorwinionElf = new LOTRAchievement(Category.DORWINION, 10, LOTRMod.elfBone, "killDorwinionElf").setRequiresEnemy(LOTRFaction.DORWINION).createTitle();
		tradeDorwinionElfCaptain = new LOTRAchievement(Category.DORWINION, 11, LOTRMod.silverCoin, "tradeDorwinionElfCaptain").setRequiresAlly(LOTRFaction.DORWINION);
		drinkWine = new LOTRAchievement(Category.DORWINION, 12, LOTRMod.mugRedWine, "drinkWine");
		harvestGrapes = new LOTRAchievement(Category.DORWINION, 13, LOTRMod.grapeWhite, "harvestGrapes");
		buyWineVintner = new LOTRAchievement(Category.DORWINION, 14, LOTRMod.silverCoin, "buyWineVintner").setRequiresAlly(LOTRFaction.DORWINION);
		hireDorwinionVinekeeper = new LOTRAchievement(Category.DORWINION, 15, LOTRMod.seedsGrapeRed, "hireDorwinionVinekeeper").setRequiresAlly(LOTRFaction.DORWINION);
		stealDorwinionGrapes = new LOTRAchievement(Category.DORWINION, 16, LOTRMod.grapeRed, "stealDorwinionGrapes").createTitle("stealGrapes");
		tradeDorwinionMerchant = new LOTRAchievement(Category.DORWINION, 17, LOTRMod.silverCoin, "tradeDorwinionMerchant").setRequiresAlly(LOTRFaction.DORWINION);
		enterDorwinionHills = new LOTRAchievement(Category.DORWINION, 18, new ItemStack(LOTRMod.rock, 1, 5), "enterDorwinionHills").setBiomeAchievement();
		enterRhun = new LOTRAchievement(Category.RHUN, 0, Blocks.grass, "enterRhun").setBiomeAchievement();
		useRhunTable = new LOTRAchievement(Category.RHUN, 4, LOTRMod.rhunTable, "useRhunTable").setRequiresAlly(LOTRFaction.RHUDEL);
		killEasterling = new LOTRAchievement(Category.RHUN, 5, Items.bone, "killEasterling").setRequiresEnemy(LOTRFaction.RHUDEL).createTitle();
		doMiniquestRhun = new LOTRAchievement(Category.RHUN, 6, LOTRMod.redBook, "doMiniquestRhun").setRequiresAlly(LOTRFaction.RHUDEL);
		wearFullRhun = new LOTRAchievement(Category.RHUN, 7, LOTRMod.bodyRhun, "wearFullRhun");
		tradeRhunBlacksmith = new LOTRAchievement(Category.RHUN, 8, LOTRMod.silverCoin, "tradeRhunBlacksmith").setRequiresAlly(LOTRFaction.RHUDEL);
		tradeRhunCaptain = new LOTRAchievement(Category.RHUN, 9, LOTRMod.silverCoin, "tradeRhunCaptain").setRequiresAlly(LOTRFaction.RHUDEL);
		hitBirdFirePot = new LOTRAchievement(Category.RHUN, 10, LOTRMod.rhunFirePot, "hitBirdFirePot");
		getKineArawHorn = new LOTRAchievement(Category.RHUN, 11, LOTRMod.kineArawHorn, "getKineArawHorn");
		wearFullRhunGold = new LOTRAchievement(Category.RHUN, 12, LOTRMod.bodyRhunGold, "wearFullRhunGold");
		enterLastDesert = new LOTRAchievement(Category.RHUN, 13, Blocks.sand, "enterLastDesert").setBiomeAchievement();
		enterMountainsWind = new LOTRAchievement(Category.RHUN, 14, new ItemStack(Blocks.stone, 1, 1000), "enterMountainsWind").setBiomeAchievement();
		tradeRhunMarketTrader = new LOTRAchievement(Category.RHUN, 15, LOTRMod.silverCoin, "tradeRhunMarketTrader").setRequiresAlly(LOTRFaction.RHUDEL);
		tradeRhunBartender = new LOTRAchievement(Category.RHUN, 16, LOTRMod.silverCoin, "tradeRhunBartender").setRequiresAlly(LOTRFaction.RHUDEL);
		hireRhunFarmer = new LOTRAchievement(Category.RHUN, 17, LOTRMod.hoeBronze, "hireRhunFarmer").setRequiresAlly(LOTRFaction.RHUDEL);
		enterRhunLand = new LOTRAchievement(Category.RHUN, 18, LOTRMod.helmetRhunGold, "enterRhunLand").setBiomeAchievement();
		enterRhunRedwood = new LOTRAchievement(Category.RHUN, 19, new ItemStack(LOTRMod.sapling8, 1, 1), "enterRhunRedwood").setBiomeAchievement();
		enterRhunIsland = new LOTRAchievement(Category.RHUN, 20, Items.boat, "enterRhunIsland").setBiomeAchievement();
		enterRedMountains = new LOTRAchievement(Category.OROCARNI, 0, new ItemStack(LOTRMod.rock, 1, 4), "enterRedMountains").setBiomeAchievement();
		enterHarondor = new LOTRAchievement(Category.NEAR_HARAD, 0, Blocks.dirt, "enterHarondor").setBiomeAchievement();
		enterNearHarad = new LOTRAchievement(Category.NEAR_HARAD, 1, Blocks.sand, "enterNearHarad").setBiomeAchievement();
		killNearHaradrim = new LOTRAchievement(Category.NEAR_HARAD, 2, Items.bone, "killNearHaradrim").setRequiresEnemy(LOTRFaction.NEAR_HARAD).createTitle();
		useNearHaradTable = new LOTRAchievement(Category.NEAR_HARAD, 6, LOTRMod.nearHaradTable, "useNearHaradTable").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		wearFullNearHarad = new LOTRAchievement(Category.NEAR_HARAD, 7, LOTRMod.bodyNearHarad, "wearFullNearHarad");
		tradeNearHaradWarlord = new LOTRAchievement(Category.NEAR_HARAD, 8, LOTRMod.silverCoin, "tradeNearHaradWarlord").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		rideCamel = new LOTRAchievement(Category.NEAR_HARAD, 9, Items.saddle, "rideCamel");
		tradeBazaarTrader = new LOTRAchievement(Category.NEAR_HARAD, 10, LOTRMod.silverCoin, "tradeBazaarTrader").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeNearHaradMerchant = new LOTRAchievement(Category.NEAR_HARAD, 11, LOTRMod.silverCoin, "tradeNearHaradMerchant").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		doMiniquestNearHarad = new LOTRAchievement(Category.NEAR_HARAD, 12, LOTRMod.redBook, "doMiniquestNearHarad").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		cookKebab = new LOTRAchievement(Category.NEAR_HARAD, 13, LOTRMod.kebab, "cookKebab").createTitle("cookKebab");
		enterNearHaradOasis = new LOTRAchievement(Category.NEAR_HARAD, 14, new ItemStack(LOTRMod.sapling3, 1, 2), "enterNearHaradOasis").setBiomeAchievement();
		tradeNearHaradBlacksmith = new LOTRAchievement(Category.NEAR_HARAD, 15, LOTRMod.silverCoin, "tradeNearHaradBlacksmith").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		enterHarnedor = new LOTRAchievement(Category.NEAR_HARAD, 16, LOTRMod.swordHarad, "enterHarnedor").setBiomeAchievement();
		enterSouthronCoasts = new LOTRAchievement(Category.NEAR_HARAD, 17, new ItemStack(LOTRMod.sapling3, 1, 2), "enterSouthronCoasts").setBiomeAchievement();
		enterUmbar = new LOTRAchievement(Category.NEAR_HARAD, 18, LOTRMod.scimitarNearHarad, "enterUmbar").setBiomeAchievement();
		enterLostladen = new LOTRAchievement(Category.NEAR_HARAD, 19, Blocks.stone, "enterLostladen").setBiomeAchievement();
		enterGulfHarad = new LOTRAchievement(Category.NEAR_HARAD, 20, new ItemStack(LOTRMod.doubleFlower, 1, 3), "enterGulfHarad").setBiomeAchievement();
		useUmbarTable = new LOTRAchievement(Category.NEAR_HARAD, 21, LOTRMod.umbarTable, "useUmbarTable").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		useGulfTable = new LOTRAchievement(Category.NEAR_HARAD, 22, LOTRMod.gulfTable, "useGulfTable").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		wearFullGulfHarad = new LOTRAchievement(Category.NEAR_HARAD, 23, LOTRMod.bodyGulfHarad, "wearFullGulfHarad");
		wearFullCorsair = new LOTRAchievement(Category.NEAR_HARAD, 24, LOTRMod.bodyCorsair, "wearFullCorsair");
		wearFullUmbar = new LOTRAchievement(Category.NEAR_HARAD, 25, LOTRMod.bodyUmbar, "wearFullUmbar");
		wearFullHarnedor = new LOTRAchievement(Category.NEAR_HARAD, 26, LOTRMod.bodyHarnedor, "wearFullHarnedor");
		wearFullNomad = new LOTRAchievement(Category.NEAR_HARAD, 27, LOTRMod.bodyNomad, "wearFullNomad");
		tradeHarnedorWarlord = new LOTRAchievement(Category.NEAR_HARAD, 28, LOTRMod.silverCoin, "tradeHarnedorWarlord").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeUmbarCaptain = new LOTRAchievement(Category.NEAR_HARAD, 29, LOTRMod.silverCoin, "tradeUmbarCaptain").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeCorsairCaptain = new LOTRAchievement(Category.NEAR_HARAD, 30, LOTRMod.silverCoin, "tradeCorsairCaptain").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeNomadWarlord = new LOTRAchievement(Category.NEAR_HARAD, 31, LOTRMod.silverCoin, "tradeNomadWarlord").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeGulfWarlord = new LOTRAchievement(Category.NEAR_HARAD, 32, LOTRMod.silverCoin, "tradeGulfWarlord").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		hireHaradSlave = new LOTRAchievement(Category.NEAR_HARAD, 33, LOTRMod.brandingIron, "hireHaradSlave").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		hireMoredainMercenary = new LOTRAchievement(Category.NEAR_HARAD, 34, LOTRMod.silverCoin, "hireMoredainMercenary").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeHarnedorBlacksmith = new LOTRAchievement(Category.NEAR_HARAD, 35, LOTRMod.silverCoin, "tradeHarnedorBlacksmith").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeUmbarBlacksmith = new LOTRAchievement(Category.NEAR_HARAD, 36, LOTRMod.silverCoin, "tradeUmbarBlacksmith").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeGulfBlacksmith = new LOTRAchievement(Category.NEAR_HARAD, 37, LOTRMod.silverCoin, "tradeGulfBlacksmith").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		doMiniquestGondorRenegade = new LOTRAchievement(Category.NEAR_HARAD, 38, LOTRMod.redBook, "doMiniquestGondorRenegade").setRequiresAlly(LOTRFaction.NEAR_HARAD).createTitle("gondorRenegade");
		tradeNomadMerchant = new LOTRAchievement(Category.NEAR_HARAD, 39, LOTRMod.silverCoin, "tradeNomadMerchant").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeHaradBartender = new LOTRAchievement(Category.NEAR_HARAD, 40, LOTRMod.silverCoin, "tradeHaradBartender").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeNomadArmourer = new LOTRAchievement(Category.NEAR_HARAD, 41, LOTRMod.silverCoin, "tradeNomadArmourer").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		hireHarnedorFarmer = new LOTRAchievement(Category.NEAR_HARAD, 42, LOTRMod.hoeBronze, "hireHarnedorFarmer").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		tradeHaradFarmer = new LOTRAchievement(Category.NEAR_HARAD, 43, LOTRMod.silverCoin, "tradeHaradFarmer").setRequiresAlly(LOTRFaction.NEAR_HARAD);
		wearFullBlackNumenorean = new LOTRAchievement(Category.NEAR_HARAD, 44, LOTRMod.bodyBlackNumenorean, "wearFullBlackNumenorean");
		enterFarHaradSavannah = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 0, LOTRMod.lionFur, "enterFarHaradSavannah").setBiomeAchievement();
		pickBanana = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 1, LOTRMod.banana, "pickBanana");
		growBaobab = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 5, new ItemStack(LOTRMod.sapling4, 1, 1), "growBaobab");
		enterFarHaradVolcano = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 6, Blocks.lava, "enterFarHaradVolcano").setBiomeAchievement();
		killMoredain = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 7, Items.bone, "killMoredain").setRequiresEnemy(LOTRFaction.MORWAITH).createTitle();
		useMoredainTable = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 8, LOTRMod.moredainTable, "useMoredainTable").setRequiresAlly(LOTRFaction.MORWAITH);
		wearFullMoredain = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 9, LOTRMod.bodyMoredain, "wearFullMoredain");
		tradeMoredainChieftain = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 10, LOTRMod.silverCoin, "tradeMoredainChieftain").setRequiresAlly(LOTRFaction.MORWAITH);
		doMiniquestMoredain = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 11, LOTRMod.redBook, "doMiniquestMoredain").setRequiresAlly(LOTRFaction.MORWAITH);
		tradeMoredainVillager = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 12, LOTRMod.silverCoin, "tradeMoredainVillager").setRequiresAlly(LOTRFaction.MORWAITH);
		enterCorsairCoasts = new LOTRAchievement(Category.FAR_HARAD_SAVANNAH, 13, LOTRMod.swordCorsair, "enterCorsairCoasts").setBiomeAchievement();
		enterFarHaradJungle = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 0, new ItemStack(Blocks.sapling, 1, 3), "enterFarHaradJungle").setBiomeAchievement();
		drinkMangoJuice = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 1, LOTRMod.mugMangoJuice, "drinkMangoJuice");
		doMiniquestTauredain = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 5, LOTRMod.redBook, "doMiniquestTauredain").setRequiresAlly(LOTRFaction.TAURETHRIM);
		useTauredainTable = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 6, LOTRMod.tauredainTable, "useTauredainTable").setRequiresAlly(LOTRFaction.TAURETHRIM);
		wearFullTauredain = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 7, LOTRMod.bodyTauredain, "wearFullTauredain");
		killTauredain = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 8, Items.bone, "killTauredain").setRequiresEnemy(LOTRFaction.TAURETHRIM).createTitle();
		tradeTauredainChieftain = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 9, LOTRMod.silverCoin, "tradeTauredainChieftain").setRequiresAlly(LOTRFaction.TAURETHRIM);
		tradeTauredainShaman = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 10, LOTRMod.silverCoin, "tradeTauredainShaman").setRequiresAlly(LOTRFaction.TAURETHRIM);
		smeltObsidianShard = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 11, LOTRMod.obsidianShard, "smeltObsidianShard");
		tradeTauredainFarmer = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 12, LOTRMod.silverCoin, "tradeTauredainFarmer").setRequiresAlly(LOTRFaction.TAURETHRIM);
		hireTauredainFarmer = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 13, LOTRMod.hoeTauredain, "hireTauredainFarmer").setRequiresAlly(LOTRFaction.TAURETHRIM);
		tradeTauredainSmith = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 14, LOTRMod.silverCoin, "tradeTauredainSmith").setRequiresAlly(LOTRFaction.TAURETHRIM);
		wearFullTaurethrimGold = new LOTRAchievement(Category.FAR_HARAD_JUNGLE, 15, LOTRMod.bodyTauredainGold, "wearFullTaurethrimGold");
		enterPertorogwaith = new LOTRAchievement(Category.PERDOROGWAITH, 0, Blocks.stone, "enterPertorogwaith").setBiomeAchievement();
		killHalfTroll = new LOTRAchievement(Category.PERDOROGWAITH, 4, LOTRMod.trollBone, "killHalfTroll").setRequiresEnemy(LOTRFaction.HALF_TROLL).createTitle();
		wearFullHalfTroll = new LOTRAchievement(Category.PERDOROGWAITH, 5, LOTRMod.bodyHalfTroll, "wearFullHalfTroll");
		tradeHalfTrollWarlord = new LOTRAchievement(Category.PERDOROGWAITH, 6, LOTRMod.silverCoin, "tradeHalfTrollWarlord").setRequiresAlly(LOTRFaction.HALF_TROLL);
		useHalfTrollTable = new LOTRAchievement(Category.PERDOROGWAITH, 7, LOTRMod.halfTrollTable, "useHalfTrollTable").setRequiresAlly(LOTRFaction.HALF_TROLL);
		doMiniquestHalfTroll = new LOTRAchievement(Category.PERDOROGWAITH, 8, LOTRMod.redBook, "doMiniquestHalfTroll").setRequiresAlly(LOTRFaction.HALF_TROLL);
		tradeHalfTrollScavenger = new LOTRAchievement(Category.PERDOROGWAITH, 9, LOTRMod.silverCoin, "tradeHalfTrollScavenger").setRequiresAlly(LOTRFaction.HALF_TROLL);
		enterHalfTrollForest = new LOTRAchievement(Category.PERDOROGWAITH, 10, new ItemStack(LOTRMod.brick4, 1, 2), "enterHalfTrollForest").setBiomeAchievement();
		enterOcean = new LOTRAchievement(Category.OCEAN, 0, Items.boat, "enterOcean").setBiomeAchievement().createTitle("visitOcean");
		enterMeneltarma = new LOTRAchievement(Category.OCEAN, 1, LOTRMod.athelas, "enterMeneltarma").setBiomeAchievement().createTitle("enterMeneltarma");
		enterUtumnoIce = new LOTRAchievement(Category.UTUMNO, 0, new ItemStack(LOTRMod.utumnoBrick, 1, 3), "enterUtumnoIce").setSpecial().createTitle("enterUtumno");
		enterUtumnoObsidian = new LOTRAchievement(Category.UTUMNO, 1, new ItemStack(LOTRMod.utumnoBrick, 1, 5), "enterUtumnoObsidian").setSpecial();
		enterUtumnoFire = new LOTRAchievement(Category.UTUMNO, 2, new ItemStack(LOTRMod.utumnoBrick, 1, 1), "enterUtumnoFire").setSpecial().createTitle("enterUtumnoFire");
		wearFullUtumno = new LOTRAchievement(Category.UTUMNO, 3, LOTRMod.bodyUtumno, "wearFullUtumno");
		killUtumnoOrc = new LOTRAchievement(Category.UTUMNO, 4, LOTRMod.orcBone, "killUtumnoOrc");
		killUtumnoWarg = new LOTRAchievement(Category.UTUMNO, 5, LOTRMod.wargBone, "killUtumnoWarg");
		killBalrog = new LOTRAchievement(Category.UTUMNO, 6, LOTRMod.balrogWhip, "killBalrog").createTitle();
		killTormentedElf = new LOTRAchievement(Category.UTUMNO, 7, LOTRMod.elfBone, "killTormentedElf");
		killUtumnoTroll = new LOTRAchievement(Category.UTUMNO, 8, LOTRMod.trollBone, "killUtumnoTroll");
		craftUtumnoKey = new LOTRAchievement(Category.UTUMNO, 9, LOTRMod.utumnoKey, "craftUtumnoKey");
		leaveUtumno = new LOTRAchievement(Category.UTUMNO, 10, LOTRMod.swordUtumno, "leaveUtumno").createTitle();
	}

	public static LOTRAchievement findByName(String name) {
		for (Category category : Category.values()) {
			for (LOTRAchievement achievement : category.list) {
				if (!achievement.name.equalsIgnoreCase(name)) {
					continue;
				}
				return achievement;
			}
		}
		return null;
	}

	public static List<LOTRAchievement> getAllAchievements() {
		List<LOTRAchievement> list = new ArrayList<>();
		for (Category category : Category.values()) {
			list.addAll(category.list);
		}
		return list;
	}

	public static Comparator<LOTRAchievement> sortForDisplay(EntityPlayer entityplayer) {
		return (ach1, ach2) -> {
			if (ach1.isSpecial) {
				if (!ach2.isSpecial) {
					return -1;
				}
				return Integer.compare(ach1.ID, ach2.ID);
			} else if (ach2.isSpecial) {
				return 1;
			}
			if (ach1.isBiomeAchievement) {
				if (ach2.isBiomeAchievement) {
					return ach1.getTitle(entityplayer).compareTo(ach2.getTitle(entityplayer));
				}
				return -1;
			}
			if (!ach2.isBiomeAchievement) {
				return ach1.getTitle(entityplayer).compareTo(ach2.getTitle(entityplayer));
			}
			return 1;
		};
	}

	public enum Category {
		GENERAL(15916746), SHIRE(LOTRFaction.HOBBIT), BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS), LINDON(LOTRBiome.lindon), ERIADOR(LOTRBiome.eriador), BREE_LAND(LOTRBiome.breeland), ANGMAR(LOTRBiome.angmar), EREGION(LOTRBiome.eregion), ENEDWAITH(LOTRBiome.enedwaith), DUNLAND(LOTRFaction.DUNLAND), MISTY_MOUNTAINS(LOTRBiome.mistyMountains), FORODWAITH(LOTRBiome.forodwaith), RHOVANION(LOTRBiome.wilderland), MIRKWOOD(LOTRBiome.woodlandRealm), DALE(LOTRFaction.DALE), IRON_HILLS(LOTRFaction.DURINS_FOLK), LOTHLORIEN(LOTRFaction.LOTHLORIEN), FANGORN(LOTRFaction.FANGORN), ROHAN(LOTRFaction.ROHAN), GONDOR(LOTRFaction.GONDOR), NINDALF(LOTRBiome.nindalf), MORDOR(LOTRFaction.MORDOR), DORWINION(LOTRFaction.DORWINION), RHUN(LOTRBiome.rhun), OROCARNI(LOTRBiome.redMountains), NEAR_HARAD(LOTRBiome.nearHarad), FAR_HARAD_SAVANNAH(LOTRBiome.farHarad), FAR_HARAD_JUNGLE(LOTRBiome.farHaradJungle), PERDOROGWAITH(LOTRBiome.pertorogwaith), OCEAN(LOTRBiome.ocean), UTUMNO(LOTRDimension.UTUMNO, LOTRFaction.UTUMNO.getFactionColor());

		public String codeName;
		public float[] categoryColors;
		public LOTRDimension dimension;
		public Collection<LOTRAchievement> list = new ArrayList<>();
		public int nextRankAchID = 1000;

		Category(int color) {
			this(LOTRDimension.MIDDLE_EARTH, color);
		}

		Category(LOTRBiome biome) {
			this(biome.color);
		}

		Category(LOTRDimension dim, int color) {
			codeName = name();
			categoryColors = new Color(color).getColorComponents(null);
			dimension = dim;
			dimension.achievementCategories.add(this);
		}

		Category(LOTRFaction faction) {
			this(faction.getFactionColor());
		}

		public String codeName() {
			return codeName;
		}

		public float[] getCategoryRGB() {
			return categoryColors;
		}

		public String getDisplayName() {
			return StatCollector.translateToLocal("lotr.achievement.category." + codeName());
		}

		public int getNextRankAchID() {
			++nextRankAchID;
			return nextRankAchID;
		}
	}

}
