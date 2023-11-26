package lotr.common.world.genlayer;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LOTRGenLayerWorld extends LOTRGenLayer {
	public static int scalePower = 7;
	public static byte[] biomeImageData;
	public static int originX = 810;
	public static int originZ = 730;
	public static int scale = IntMath.pow(2, 7);
	public static int imageWidth;
	public static int imageHeight;

	public LOTRGenLayerWorld() {
		super(0L);
		if (!loadedBiomeImage()) {
			try {
				BufferedImage biomeImage = null;
				String imageName = "assets/lotr/map/map.png";
				ModContainer mc = LOTRMod.getModContainer();
				if (mc.getSource().isFile()) {
					ZipFile zip = new ZipFile(mc.getSource());
					Enumeration<? extends ZipEntry> entries = zip.entries();
					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (!entry.getName().equals(imageName)) {
							continue;
						}
						biomeImage = ImageIO.read(zip.getInputStream(entry));
					}
					zip.close();
				} else {
					File file = new File(LOTRMod.class.getResource("/" + imageName).toURI());
					biomeImage = ImageIO.read(Files.newInputStream(file.toPath()));
				}
				if (biomeImage == null) {
					throw new RuntimeException("Could not load LOTR biome map image");
				}
				imageWidth = biomeImage.getWidth();
				imageHeight = biomeImage.getHeight();
				int[] colors = biomeImage.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
				biomeImageData = new byte[imageWidth * imageHeight];
				for (int i = 0; i < colors.length; ++i) {
					int color = colors[i];
					Integer biomeID = LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
					if (biomeID != null) {
						int cleanUP = biomeID;
						biomeImageData[i] = (byte) cleanUP;
						continue;
					}
					FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color));
					biomeImageData[i] = (byte) LOTRBiome.ocean.biomeID;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static LOTRGenLayer[] createWorld(LOTRDimension dim, WorldType worldType) {
		int i;
		if (dim == LOTRDimension.UTUMNO) {
			LOTRGenLayerBiome biomes = new LOTRGenLayerBiome(LOTRBiome.utumno);
			LOTRGenLayerBiomeVariants variants = new LOTRGenLayerBiomeVariants(300L);
			return new LOTRGenLayer[]{biomes, variants, variants, variants, variants};
		}
		LOTRGenLayer rivers = new LOTRGenLayerRiverInit(100L);
		rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 10);
		rivers = new LOTRGenLayerRiver(1L, rivers);
		rivers = new LOTRGenLayerSmooth(1000L, rivers);
		rivers = LOTRGenLayerZoom.magnify(1000L, rivers, 1);
		LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
		biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
		LOTRGenLayer biomes = new LOTRGenLayerWorld();
		if (worldType == LOTRMod.worldTypeMiddleEarthClassic) {
			LOTRGenLayer oceans = new LOTRGenLayerClassicOcean(2012L);
			oceans = LOTRGenLayerZoom.magnify(200L, oceans, 3);
			oceans = new LOTRGenLayerClassicRemoveOcean(400L, oceans);
			biomes = new LOTRGenLayerClassicBiomes(2013L, oceans, dim);
			biomes = LOTRGenLayerZoom.magnify(300L, biomes, 2);
		}
		LOTRGenLayer mapRivers = new LOTRGenLayerExtractMapRivers(5000L, biomes);
		biomes = new LOTRGenLayerRemoveMapRivers(1000L, biomes, dim);
		biomes = new LOTRGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
		biomes = new LOTRGenLayerNearHaradRiverbanks(200L, biomes, mapRivers, dim);
		biomes = new LOTRGenLayerNearHaradOasis(500L, biomes, dim);
		biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 1);
		biomes = new LOTRGenLayerBeach(1000L, biomes, dim, LOTRBiome.ocean);
		biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 2);
		biomes = new LOTRGenLayerOasisLake(600L, biomes, dim);
		biomes = LOTRGenLayerZoom.magnify(1000L, biomes, 2);
		biomes = new LOTRGenLayerSmooth(1000L, biomes);
		LOTRGenLayer variants = new LOTRGenLayerBiomeVariants(200L);
		variants = LOTRGenLayerZoom.magnify(200L, variants, 8);
		LOTRGenLayer variantsSmall = new LOTRGenLayerBiomeVariants(300L);
		variantsSmall = LOTRGenLayerZoom.magnify(300L, variantsSmall, 6);
		LOTRGenLayer lakes = new LOTRGenLayerBiomeVariantsLake(100L, null, 0).setLakeFlags(1);
		for (i = 1; i <= 5; ++i) {
			lakes = new LOTRGenLayerZoom(200L + i, lakes);
			if (i <= 2) {
				lakes = new LOTRGenLayerBiomeVariantsLake(300L + i, lakes, i).setLakeFlags(1);
			}
			if (i != 3) {
				continue;
			}
			lakes = new LOTRGenLayerBiomeVariantsLake(500L, lakes, i).setLakeFlags(2, 4);
		}
		for (i = 0; i < 4; ++i) {
			mapRivers = new LOTRGenLayerMapRiverZoom(4000L + i, mapRivers);
		}
		mapRivers = new LOTRGenLayerNarrowRivers(3000L, mapRivers, 6);
		mapRivers = LOTRGenLayerZoom.magnify(4000L, mapRivers, 1);
		rivers = new LOTRGenLayerIncludeMapRivers(5000L, rivers, mapRivers);
		return new LOTRGenLayer[]{biomes, variants, variantsSmall, lakes, rivers};
	}

	public static int getBiomeImageID(int x, int z) {
		int index = z * imageWidth + x;
		return biomeImageData[index] & 0xFF;
	}

	public static LOTRBiome getBiomeOrOcean(int mapX, int mapZ) {
		int biomeID = mapX >= 0 && mapX < imageWidth && mapZ >= 0 && mapZ < imageHeight ? getBiomeImageID(mapX, mapZ) : LOTRBiome.ocean.biomeID;
		return LOTRDimension.MIDDLE_EARTH.biomeList[biomeID];
	}

	public static boolean loadedBiomeImage() {
		return biomeImageData != null;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void printRiverlessMap(World world, File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOTRDimension dim = LOTRDimension.MIDDLE_EARTH;
		LOTRGenLayer biomeSubtypes = new LOTRGenLayerBiomeSubtypesInit(3000L);
		biomeSubtypes = LOTRGenLayerZoom.magnify(3000L, biomeSubtypes, 2);
		LOTRGenLayer biomes = new LOTRGenLayerWorld();
		biomes = new LOTRGenLayerRemoveMapRivers(1000L, biomes, dim);
		biomes = new LOTRGenLayerBiomeSubtypes(1000L, biomes, biomeSubtypes);
		BufferedImage buf = new BufferedImage(imageWidth, imageHeight, 2);
		for (int i = 0; i < imageWidth; ++i) {
			for (int k = 0; k < imageHeight; ++k) {
				int[] b = biomes.getInts(world, i - 810, k - 730, 1, 1);
				LOTRBiome biome = dim.biomeList[b[0]];
				buf.setRGB(i, k, biome.color | 0xFF000000);
				LOTRIntCache.get(world).resetIntCache();
			}
		}
		try {
			ImageIO.write(buf, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] intArray = LOTRIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int i2 = i + i1 + 810;
				int k2 = k + k1 + 730;
				intArray[i1 + k1 * xSize] = i2 < 0 || i2 >= imageWidth || k2 < 0 || k2 >= imageHeight ? LOTRBiome.ocean.biomeID : getBiomeImageID(i2, k2);
			}
		}
		return intArray;
	}
}
