package lotr.common;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.world.World;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class LOTRConfig {
	public static Configuration config;
	public static List<ConfigCategory> allCategories = new ArrayList<>();
	public static String CATEGORY_DIMENSION;
	public static String CATEGORY_GAMEPLAY;
	public static String CATEGORY_GUI;
	public static String CATEGORY_ENVIRONMENT;
	public static String CATEGORY_MISC;
	public static boolean allowBannerProtection;
	public static boolean allowSelfProtectingBanners;
	public static boolean allowMiniquests;
	public static boolean allowBountyQuests;
	public static boolean enableTitles;
	public static boolean enableFastTravel;
	public static boolean enableFellowshipCreation;
	public static int fellowshipMaxSize;
	public static boolean alignmentDrain;
	public static boolean enableConquest;
	public static boolean removeGoldenAppleRecipes;
	public static boolean enablePortals;
	public static boolean enableOrcSkirmish;
	public static boolean enchantingVanilla;
	public static boolean enchantingLOTR;
	public static boolean enchantingAutoRemoveVanilla;
	public static boolean enablePotionBrewing;
	public static int bannerWarningCooldown;
	public static boolean dropMutton;
	public static boolean drunkMessages;
	public static boolean middleEarthRespawning;
	public static int MERBedRespawnThreshold;
	public static int MERWorldRespawnThreshold;
	public static int MERMinRespawn;
	public static int MERMaxRespawn;
	public static boolean generateMapFeatures;
	public static boolean generateFixedSettlements;
	public static boolean changedHunger;
	public static boolean canAlwaysEat;
	public static int forceMapLocations;
	public static boolean enableBandits;
	public static boolean enableInvasions;
	public static boolean enableUnitLevelling;
	public static boolean removeDiamondArmorRecipes;
	public static boolean disableEnderChestsUtumno;
	public static int preventTraderKidnap;
	public static boolean disableLightningGrief;
	public static boolean disableFireSpread;
	public static boolean enableVillagerTrading;
	public static boolean strictFactionTitleRequirements;
	public static boolean invasionProgressReverts;
	public static boolean hiredUnitKillsCountForBane;
	public static int customWaypointMinY;
	public static boolean alwaysShowAlignment;
	public static int alignmentXOffset;
	public static int alignmentYOffset;
	public static boolean displayAlignmentAboveHead;
	public static boolean enableSepiaMap;
	public static boolean osrsMap;
	public static boolean enableOnscreenCompass;
	public static boolean compassExtraInfo;
	public static boolean hiredUnitHealthBars;
	public static boolean hiredUnitIcons;
	public static boolean elvenBladeGlow;
	public static boolean immersiveSpeech;
	public static boolean immersiveSpeechChatLog;
	public static boolean meleeAttackMeter;
	public static boolean mapLabels;
	public static boolean mapLabelsConquest;
	public static boolean enableQuestTracker;
	public static boolean trackingQuestRight;
	public static boolean customMainMenu;
	public static boolean fellowPlayerHealthBars;
	public static boolean displayCoinCounts;
	public static boolean balrogWings;
	public static boolean showPermittedBannerSilhouettes;
	public static boolean enableLOTRSky;
	public static boolean enableMistyMountainsMist;
	public static boolean enableAmbience;
	public static boolean enableSunFlare;
	public static int cloudRange;
	public static boolean newWeather;
	public static boolean snowyStone;
	public static boolean aurora;
	public static boolean naturalBlocks;
	public static boolean updateLangFiles;
	public static boolean checkUpdates;
	public static boolean strTimelapse;
	public static int strTimelapseInterval;
	public static boolean protectHobbitKillers;
	public static boolean fixMobSpawning;
	public static int mobSpawnInterval;
	public static int musicIntervalMin;
	public static int musicIntervalMax;
	public static boolean displayMusicTrack;
	public static int musicIntervalMenuMin;
	public static int musicIntervalMenuMax;
	public static boolean fixRenderDistance;
	public static boolean preventMessageExploit;
	public static boolean cwpLog;
	public static int playerDataClearingInterval;

    public static boolean enableAttackCooldown;
	public static int MIN_PLAYER_DATA_CLEARING_INTERVAL = 600;

	public static boolean areStrictFactionTitleRequirementsEnabled(World world) {
		if (!world.isRemote) {
			return strictFactionTitleRequirements;
		}
		return LOTRLevelData.clientside_thisServer_strictFactionTitleRequirements;
	}

	public static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<>();
		for (ConfigCategory category : allCategories) {
			IConfigElement categoryElement = new ConfigElement(category);
			list.add(categoryElement);
		}
		return list;
	}

	public static int getCustomWaypointMinY(World world) {
		if (!world.isRemote) {
			return customWaypointMinY;
		}
		return LOTRLevelData.clientside_thisServer_customWaypointMinY;
	}

	public static int getFellowshipMaxSize(World world) {
		if (!world.isRemote) {
			return fellowshipMaxSize;
		}
		return LOTRLevelData.clientside_thisServer_fellowshipMaxSize;
	}

	public static boolean isEnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingVanilla;
		}
		return LOTRLevelData.clientside_thisServer_enchanting;
	}

	public static boolean isFellowshipCreationEnabled(World world) {
		if (!world.isRemote) {
			return enableFellowshipCreation;
		}
		return LOTRLevelData.clientside_thisServer_fellowshipCreation;
	}

	public static boolean isLOTREnchantingEnabled(World world) {
		if (!world.isRemote) {
			return enchantingLOTR;
		}
		return LOTRLevelData.clientside_thisServer_enchantingLOTR;
	}

	public static void load() {
		LOTRDimension.configureDimensions(config, CATEGORY_DIMENSION);
        enableAttackCooldown = config.get(CATEGORY_GAMEPLAY, "Enable Attack Cooldown?", false).getBoolean();
		allowBannerProtection = config.get(CATEGORY_GAMEPLAY, "Allow Banner Protection", true).getBoolean();
		allowSelfProtectingBanners = config.get(CATEGORY_GAMEPLAY, "Allow Self-Protecting Banners", true).getBoolean();
		allowMiniquests = config.get(CATEGORY_GAMEPLAY, "NPCs give mini-quests", true).getBoolean();
		allowBountyQuests = config.get(CATEGORY_GAMEPLAY, "NPCs give bounty mini-quests", true, "Allow NPCs to generate mini-quests to kill enemy players").getBoolean();
		enableTitles = config.get(CATEGORY_GAMEPLAY, "Enable Titles", true).getBoolean();
		enableFastTravel = config.get(CATEGORY_GAMEPLAY, "Enable Fast Travel", true).getBoolean();
		enableFellowshipCreation = config.get(CATEGORY_GAMEPLAY, "Enable Fellowship creation", true, "If disabled, admins can still create Fellowships using the command").getBoolean();
		fellowshipMaxSize = config.get(CATEGORY_GAMEPLAY, "Fellowship maximum size", -1, "Maximum player count for Fellowships. Negative = no limit").getInt();
		alignmentDrain = config.get(CATEGORY_GAMEPLAY, "Enable alignment drain", true, "Factions dislike if a player has + alignment with enemy factions").getBoolean();
		enableConquest = config.get(CATEGORY_GAMEPLAY, "Enable Conquest", true).getBoolean();
		removeGoldenAppleRecipes = config.get(CATEGORY_GAMEPLAY, "Remove Golden Apple recipes", true).getBoolean();
		enablePortals = config.get(CATEGORY_GAMEPLAY, "Enable Middle-earth Portals", true, "Enable or disable the buildable Middle-earth portals (excluding the Ring Portal). If disabled, portals can still be made, but will not function").getBoolean();
		enableOrcSkirmish = config.get(CATEGORY_GAMEPLAY, "Enable Orc Skirmishes", true).getBoolean();
		enchantingVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Vanilla System", false, "Enable the vanilla enchanting system: if disabled, prevents players from enchanting items, but does not affect existing enchanted items").getBoolean();
		enchantingLOTR = config.get(CATEGORY_GAMEPLAY, "Enchanting: LOTR System", true, "Enable the LOTR enchanting system: if disabled, prevents newly crafted items, loot chest items, etc. from having modifiers applied, but does not affect existing modified items").getBoolean();
		enchantingAutoRemoveVanilla = config.get(CATEGORY_GAMEPLAY, "Enchanting: Auto-remove vanilla enchants", false, "Intended for servers. If enabled, enchantments will be automatically removed from items").getBoolean();
		enablePotionBrewing = config.get(CATEGORY_GAMEPLAY, "Enable Potion Brewing", true, "Mainly intended for servers. Disable the vanilla potion brewing system, as it is not 'lore-friendly'").getBoolean();
		bannerWarningCooldown = config.get(CATEGORY_GAMEPLAY, "Protection Warning Cooldown", 20, "Cooldown time (in ticks) between appearances of the warning message for banner-public land").getInt();
		dropMutton = config.get(CATEGORY_GAMEPLAY, "Mutton Drops", true, "Enable or disable sheep dropping the mod's mutton items").getBoolean();
		drunkMessages = config.get(CATEGORY_GAMEPLAY, "Enable Drunken Messages", true).getBoolean();
		middleEarthRespawning = config.get(CATEGORY_GAMEPLAY, "Middle-earth Respawning: Enable", true, "If enabled, when a player dies in Middle-earth far from their spawn point, they will respawn somewhere near their death point instead").getBoolean();
		MERBedRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Middle-earth Respawning: Bed Threshold", 5000, "Threshold distance from spawn for applying Middle-earth Respawning when the player's spawn point is a bed").getInt();
		MERWorldRespawnThreshold = config.get(CATEGORY_GAMEPLAY, "Middle-earth Respawning: World Threshold", 2000, "Threshold distance from spawn for applying Middle-earth respawning when the player's spawn point is the world spawn (no bed)").getInt();
		MERMinRespawn = config.get(CATEGORY_GAMEPLAY, "Middle-earth Respawning: Min Respawn Range", 500, "Minimum possible range to place the player from their death point").getInt();
		MERMaxRespawn = config.get(CATEGORY_GAMEPLAY, "Middle-earth Respawning: Max Respawn Range", 1500, "Maximum possible range to place the player from their death point").getInt();
		generateMapFeatures = config.get(CATEGORY_GAMEPLAY, "Generate map features", true, "Roads; fixed hills and mountains; fixed structures, such as the Utumno entrance").getBoolean();
		generateFixedSettlements = config.get(CATEGORY_GAMEPLAY, "Generate fixed settlements", true, "Villages in fixed locations, such as Bree").getBoolean();
		changedHunger = config.get(CATEGORY_GAMEPLAY, "Hunger changes", true, "Food meter decreases more slowly").getBoolean();
		canAlwaysEat = config.get(CATEGORY_GAMEPLAY, "Feast Mode", true, "Food can always be eaten regardless of hunger").getBoolean();
		forceMapLocations = config.get(CATEGORY_GAMEPLAY, "Force Hide/Show Map Locations", 0, "Force hide or show players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show").getInt();
		enableBandits = config.get(CATEGORY_GAMEPLAY, "Enable Bandits", true).getBoolean();
		enableInvasions = config.get(CATEGORY_GAMEPLAY, "Enable Invasions", true).getBoolean();
		enableUnitLevelling = config.get(CATEGORY_GAMEPLAY, "Enable hired unit levelling", true).getBoolean();
		removeDiamondArmorRecipes = config.get(CATEGORY_GAMEPLAY, "Remove diamond armour recipes", false).getBoolean();
		disableEnderChestsUtumno = config.get(CATEGORY_GAMEPLAY, "Disable ender chests in Utumno", false).getBoolean();
		preventTraderKidnap = config.get(CATEGORY_GAMEPLAY, "Prevent trader transport range", 0, "Prevent transport of structure-bound traders beyond this distance outside their initial home range (0 = disabled)").getInt();
		disableLightningGrief = config.get(CATEGORY_GAMEPLAY, "Disable lightning grief", false, "Prevent lightning from placing fire blocks").getBoolean();
		disableFireSpread = config.get(CATEGORY_GAMEPLAY, "Disable fire spread", false, "Activate instead of gamerule doFireTick for finer control over fire behaviour. Fire will still die out and burn blocks, but will not spread").getBoolean();
		enableVillagerTrading = config.get(CATEGORY_GAMEPLAY, "Enable Villager trading", true, "Intended for servers. Enable or disable vanilla villager trading").getBoolean();
		strictFactionTitleRequirements = config.get(CATEGORY_GAMEPLAY, "Strict faction title requirements", false, "Require a pledge to bear faction titles of alignment level equal to the faction's pledge level - not just those titles higher than pledge level").getBoolean();
		invasionProgressReverts = config.get(CATEGORY_GAMEPLAY, "Invasion progress reverts", false, "Invasion progress slowly reverts if left alone for too long").getBoolean();
		hiredUnitKillsCountForBane = config.get(CATEGORY_GAMEPLAY, "Hired units killed count towards x-bane modifiers", true, "").getBoolean();
		customWaypointMinY = config.get(CATEGORY_GAMEPLAY, "Custom waypoint minimum y-level", -1, "Minimum y-coordinate at which a player can create a custom waypoint. Negative value = no limit").getInt();
		alwaysShowAlignment = config.get(CATEGORY_GUI, "Always show alignment", false, "If set to false, the alignment bar will only be shown in Middle-earth. If set to true, it will be shown in all dimensions").getBoolean();
		alignmentXOffset = config.get(CATEGORY_GUI, "Alignment x-offset", 0, "Configure the x-position of the alignment bar on-screen. Negative values move it left, positive values right").getInt();
		alignmentYOffset = config.get(CATEGORY_GUI, "Alignment y-offset", 0, "Configure the y-position of the alignment bar on-screen. Negative values move it up, positive values down").getInt();
		displayAlignmentAboveHead = config.get(CATEGORY_GUI, "Display alignment above head", true, "Enable or disable the rendering of other players' alignment values above their heads").getBoolean();
		enableSepiaMap = config.get(CATEGORY_GUI, "Sepia Map", false, "Display the Middle-earth map in sepia colours").getBoolean();
		osrsMap = config.get(CATEGORY_GUI, "OSRS Map", false, "It's throwback time. (Requires game restart)").getBoolean();
		enableOnscreenCompass = config.get(CATEGORY_GUI, "On-screen Compass", true).getBoolean();
		compassExtraInfo = config.get(CATEGORY_GUI, "On-screen Compass Extra Info", true, "Display co-ordinates and biome below compass").getBoolean();
		hiredUnitHealthBars = config.get(CATEGORY_GUI, "Hired NPC Health Bars", true).getBoolean();
		hiredUnitIcons = config.get(CATEGORY_GUI, "Hired NPC Icons", true).getBoolean();
		elvenBladeGlow = config.get(CATEGORY_GUI, "Animated Elven blade glow", true).getBoolean();
		immersiveSpeech = config.get(CATEGORY_GUI, "Immersive Speech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box").getBoolean();
		immersiveSpeechChatLog = config.get(CATEGORY_GUI, "Immersive Speech Chat Logs", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled").getBoolean();
		meleeAttackMeter = config.get(CATEGORY_GUI, "Melee attack meter", true).getBoolean();
		mapLabels = config.get(CATEGORY_GUI, "Map Labels", true).getBoolean();
		mapLabelsConquest = config.get(CATEGORY_GUI, "Map Labels - Conquest", true).getBoolean();
		enableQuestTracker = config.get(CATEGORY_GUI, "Enable quest tracker", true).getBoolean();
		trackingQuestRight = config.get(CATEGORY_GUI, "Flip quest tracker", false, "Display the quest tracker on the right-hand side of the screen instead of the left").getBoolean();
		customMainMenu = config.get(CATEGORY_GUI, "Custom main menu", true, "Use the mod's custom main menu screen").getBoolean();
		fellowPlayerHealthBars = config.get(CATEGORY_GUI, "Fellow Player Health Bars", true).getBoolean();
		displayCoinCounts = config.get(CATEGORY_GUI, "Inventory coin counts", true).getBoolean();
		balrogWings = config.get(CATEGORY_GUI, "Balrog Wings", true, "Choose your side in the legendary debate...").getBoolean();
		showPermittedBannerSilhouettes = config.get(CATEGORY_GUI, "Show permitted banner silhouettes", true, "In the debug screen, render any protection banners for which you have permission as a solid green shape, visible through blocks").getBoolean();
		enableLOTRSky = config.get(CATEGORY_ENVIRONMENT, "Middle-earth sky", true, "Toggle the new Middle-earth sky").getBoolean();
		enableMistyMountainsMist = config.get(CATEGORY_ENVIRONMENT, "Misty Misty Mountains", true, "Toggle mist overlay in the Misty Mountains").getBoolean();
		enableAmbience = config.get(CATEGORY_ENVIRONMENT, "Ambience", true).getBoolean();
		enableSunFlare = config.get(CATEGORY_ENVIRONMENT, "Sun flare", true).getBoolean();
		cloudRange = config.get(CATEGORY_ENVIRONMENT, "Cloud range", 1024, "Middle-earth cloud rendering range. To use vanilla clouds, set this to a non-positive value").getInt();
		newWeather = config.get(CATEGORY_ENVIRONMENT, "New weather", true, "New rain textures and sounds; mist during rain; wind sounds; new weather types").getBoolean();
		snowyStone = config.get(CATEGORY_ENVIRONMENT, "Snowy stone", true, "Snowy texture on the sides of snow-capped stone blocks").getBoolean();
		aurora = config.get(CATEGORY_ENVIRONMENT, "Aurora", true, "The Aurora, or Northern Lights! May be a slightly performance-intensive feature.").getBoolean();
		naturalBlocks = config.get(CATEGORY_ENVIRONMENT, "Natural blocks", true, "Randomly rotate textures on some blocks - grass, dirt, sand, etc. - for a more natural appearance").getBoolean();
		updateLangFiles = config.get(CATEGORY_MISC, "Run language update helper", true, "Run the mod's language file update helper on launch - see .minecraft/mods/LOTR_UpdatedLangFiles/readme.txt").getBoolean();
		checkUpdates = config.get(CATEGORY_MISC, "Check for updates", true, "Disable this if you will be playing offline").getBoolean();
		strTimelapse = config.get(CATEGORY_MISC, "Structure Timelapse", false, "Structure spawners generate as a timelapse instead of instantly. WARNING: May be buggy. See also the command /strTimelapse").getBoolean();
		strTimelapseInterval = config.get(CATEGORY_MISC, "Structure Timelapse Interval", 5, "Structure timelapse interval (in ms) between each block placement").getInt();
		if (strTimelapseInterval < 0) {
			setStructureTimelapseInterval(0);
		}
		protectHobbitKillers = config.get(CATEGORY_MISC, "Protect Hobbit Killers", false, "For servers: Disable broadcasting of the 'Hobbit Slayer' achievement, to protect new evil players from being persecuted").getBoolean();
		fixMobSpawning = config.get(CATEGORY_MISC, "Fix mob spawning lag", true, "Fix a major source of server lag caused by the vanilla mob spawning system").getBoolean();
		mobSpawnInterval = config.get(CATEGORY_MISC, "Mob spawn interval", 0, "Tick interval between mob spawn cycles (which are then run multiple times to compensate). Higher values may reduce server lag").getInt();
		musicIntervalMin = config.get(CATEGORY_MISC, "Music Interval: Min.", 30, "Minimum time (seconds) between LOTR music tracks").getInt();
		musicIntervalMax = config.get(CATEGORY_MISC, "Music Interval: Max.", 150, "Maximum time (seconds) between LOTR music tracks").getInt();
		displayMusicTrack = config.get(CATEGORY_MISC, "Display music track", false, "Display the name of a LOTR music track when it begins playing").getBoolean();
		musicIntervalMenuMin = config.get(CATEGORY_MISC, "Menu Music Interval: Min.", 10, "Minimum time (seconds) between LOTR menu music tracks").getInt();
		musicIntervalMenuMax = config.get(CATEGORY_MISC, "Menu Music Interval: Max.", 20, "Maximum time (seconds) between LOTR menu music tracks").getInt();
		fixRenderDistance = config.get(CATEGORY_MISC, "Fix render distance", true, "Fix a vanilla crash caused by having render distance > 16 in the options.txt. NOTE: This will not run if Optifine is installed").getBoolean();
		preventMessageExploit = config.get(CATEGORY_MISC, "Fix /msg exploit", true, "Disable usage of @a, @r, etc. in the /msg command, to prevent exploiting it as a player locator").getBoolean();
		cwpLog = config.get(CATEGORY_MISC, "Custom Waypoint logging", false).getBoolean();
		playerDataClearingInterval = config.get(CATEGORY_MISC, "Playerdata clearing interval", 600, "Tick interval between clearing offline LOTR-playerdata from the cache. Offline players' data is typically loaded to serve features like fellowships and their shared custom waypoints. Higher values may reduce server lag, as data will have to be reloaded from disk less often, but will result in higher RAM usage to some extent").getInt();
		if (LOTRModChecker.isCauldronServer()) {
			FMLLog.info("LOTR: Successfully detected Cauldron server and disabled: nothing! (Thanks, ASM!)");
		}
		if (config.hasChanged()) {
			config.save();
		}
	}

	public static String makeCategory(String name) {
		ConfigCategory category = config.getCategory(name);
		category.setLanguageKey(LOTRMod.getModContainer().getModId() + ".config." + name);
		allCategories.add(category);
		return name;
	}

	public static void setStructureTimelapse(boolean flag) {
		strTimelapse = flag;
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse").set(strTimelapse);
		config.save();
	}

	public static void setStructureTimelapseInterval(int i) {
		strTimelapseInterval = Math.max(i, 0);
		config.getCategory(CATEGORY_MISC).get("Structure Timelapse Interval").set(strTimelapseInterval);
		config.save();
	}

	public static void setupAndLoad(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		setupCategories();
		load();
	}

	public static void setupCategories() {
		CATEGORY_DIMENSION = makeCategory("dimension");
		CATEGORY_GAMEPLAY = makeCategory("gameplay");
		CATEGORY_GUI = makeCategory("gui");
		CATEGORY_ENVIRONMENT = makeCategory("environment");
		CATEGORY_MISC = makeCategory("misc");
	}

	public static void toggleMapLabels() {
		mapLabels = !mapLabels;
		config.getCategory(CATEGORY_GUI).get("Map Labels").set(mapLabels);
		config.save();
	}

	public static void toggleMapLabelsConquest() {
		mapLabelsConquest = !mapLabelsConquest;
		config.getCategory(CATEGORY_GUI).get("Map Labels - Conquest").set(mapLabelsConquest);
		config.save();
	}

	public static void toggleSepia() {
		enableSepiaMap = !enableSepiaMap;
		config.getCategory(CATEGORY_GUI).get("Sepia Map").set(enableSepiaMap);
		config.save();
	}
}
