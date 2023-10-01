package lotr.common;

import lotr.common.fac.LOTRFaction;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.LOTRWorldProviderMiddleEarth;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

import java.util.*;

public enum LOTRDimension {
	MIDDLE_EARTH("MiddleEarth", 100, LOTRWorldProviderMiddleEarth.class, true, 100, EnumSet.of(DimensionRegion.WEST, DimensionRegion.EAST, DimensionRegion.SOUTH)), UTUMNO("Utumno", 101, LOTRWorldProviderUtumno.class, false, 500, EnumSet.of(DimensionRegion.REG_UTUMNO));

	public String dimensionName;
	public int defaultID;
	public int dimensionID;
	public Class providerClass;
	public boolean loadSpawn;
	public LOTRBiome[] biomeList = new LOTRBiome[256];
	public Map<Integer, Integer> colorsToBiomeIDs = new HashMap<>();
	public List<LOTRBiome> majorBiomes = new ArrayList<>();
	public List<LOTRAchievement.Category> achievementCategories = new ArrayList<>();
	public Collection<LOTRAchievement> allAchievements = new ArrayList<>();
	public Collection<LOTRFaction> factionList = new ArrayList<>();
	public List<DimensionRegion> dimensionRegions = new ArrayList<>();
	public int spawnCap;

	LOTRDimension(String s, int i, Class c, boolean flag, int spawns, Collection<DimensionRegion> regions) {
		dimensionName = s;
		defaultID = i;
		providerClass = c;
		loadSpawn = flag;
		spawnCap = spawns;
		dimensionRegions.addAll(regions);
		for (DimensionRegion r : dimensionRegions) {
			r.setDimension(this);
		}
	}

	public static void configureDimensions(Configuration config, String category) {
		for (LOTRDimension dim : values()) {
			dim.dimensionID = config.get(category, "Dimension ID: " + dim.dimensionName, dim.defaultID).getInt();
		}
	}

	public static LOTRDimension forName(String s) {
		for (LOTRDimension dim : values()) {
			if (!dim.dimensionName.equals(s)) {
				continue;
			}
			return dim;
		}
		return null;
	}

	public static LOTRDimension getCurrentDimension(World world) {
		WorldProvider provider;
		if (world != null && (provider = world.provider) instanceof LOTRWorldProvider) {
			return ((LOTRWorldProvider) provider).getLOTRDimension();
		}
		return null;
	}

	public static LOTRDimension getCurrentDimensionWithFallback(World world) {
		LOTRDimension dim = getCurrentDimension(world);
		if (dim == null) {
			return MIDDLE_EARTH;
		}
		return dim;
	}

	public static void registerDimensions() {
		for (LOTRDimension dim : values()) {
			DimensionManager.registerProviderType(dim.dimensionID, dim.providerClass, dim.loadSpawn);
			DimensionManager.registerDimension(dim.dimensionID, dim.dimensionID);
		}
	}

	public String getDimensionName() {
		return StatCollector.translateToLocal(getUntranslatedDimensionName());
	}

	public String getUntranslatedDimensionName() {
		return "lotr.dimension." + dimensionName;
	}

	public enum DimensionRegion {
		WEST("west"), EAST("east"), SOUTH("south"), REG_UTUMNO("utumno");

		public String regionName;
		public LOTRDimension dimension;
		public List<LOTRFaction> factionList = new ArrayList<>();

		DimensionRegion(String s) {
			regionName = s;
		}

		public static DimensionRegion forID(int ID) {
			for (DimensionRegion r : values()) {
				if (r.ordinal() != ID) {
					continue;
				}
				return r;
			}
			return null;
		}

		public static DimensionRegion forName(String regionName) {
			for (DimensionRegion r : values()) {
				if (!r.codeName().equals(regionName)) {
					continue;
				}
				return r;
			}
			return null;
		}

		public String codeName() {
			return regionName;
		}

		public LOTRDimension getDimension() {
			return dimension;
		}

		public void setDimension(LOTRDimension dim) {
			dimension = dim;
		}

		public String getRegionName() {
			return StatCollector.translateToLocal("lotr.dimension." + dimension.dimensionName + "." + codeName());
		}
	}

}
