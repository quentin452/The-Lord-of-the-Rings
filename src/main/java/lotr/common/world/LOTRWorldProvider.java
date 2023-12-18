package lotr.common.world;

import com.google.common.math.IntMath;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRCloudRenderer;
import lotr.client.render.LOTRSkyRenderer;
import lotr.client.render.LOTRWeatherRenderer;
import lotr.common.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenOcean;
import lotr.common.world.biome.LOTRBiomeGenTundra;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.ForgeModContainer;

public abstract class LOTRWorldProvider extends WorldProvider {
	public static int MOON_PHASES = 8;
	@SideOnly(Side.CLIENT)
	public IRenderHandler lotrSkyRenderer;
	@SideOnly(Side.CLIENT)
	public IRenderHandler lotrCloudRenderer;
	@SideOnly(Side.CLIENT)
	public IRenderHandler lotrWeatherRenderer;

	public static int getLOTRMoonPhase() {
		int day = LOTRDate.ShireReckoning.currentDay;
		return IntMath.mod(day, MOON_PHASES);
	}

	public static boolean isLunarEclipse() {
		int day = LOTRDate.ShireReckoning.currentDay;
		return getLOTRMoonPhase() == 0 && IntMath.mod(day / MOON_PHASES, 4) == 3;
	}

	@Override
	public float calculateCelestialAngle(long time, float partialTick) {
		float daytime = ((int) (time % LOTRTime.DAY_LENGTH) + partialTick) / LOTRTime.DAY_LENGTH - 0.25f;
		if (daytime < 0.0f) {
			daytime += 1.0f;
		}
		if (daytime > 1.0f) {
			daytime -= 1.0f;
		}
		float angle = 1.0f - (float) ((Math.cos(daytime * 3.141592653589793) + 1.0) / 2.0);
		return daytime + (angle - daytime) / 3.0f;
	}

	@Override
	public boolean canBlockFreeze(int i, int j, int k, boolean isBlockUpdate) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenOcean) {
			return LOTRBiomeGenOcean.isFrozen(i, k) && canFreeze_ignoreTemp(i, j, k, isBlockUpdate);
		}
		return worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
	}

	public boolean canFreeze_ignoreTemp(int i, int j, int k, boolean isBlockUpdate) {
		Block block;
		if (j >= 0 && j < worldObj.getHeight() && worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && ((block = worldObj.getBlock(i, j, k)) == Blocks.water || block == Blocks.flowing_water) && worldObj.getBlockMetadata(i, j, k) == 0) {
			if (!isBlockUpdate) {
				return true;
			}
			boolean surroundWater = worldObj.getBlock(i - 1, j, k).getMaterial() == Material.water;
			if (surroundWater && worldObj.getBlock(i + 1, j, k).getMaterial() != Material.water) {
				surroundWater = false;
			}
			if (surroundWater && worldObj.getBlock(i, j, k - 1).getMaterial() != Material.water) {
				surroundWater = false;
			}
			if (surroundWater && worldObj.getBlock(i, j, k + 1).getMaterial() != Material.water) {
				surroundWater = false;
			}
			return !surroundWater;
		}
		return false;
	}

	public boolean canSnow_ignoreTemp(int i, int j, int k, boolean checkLight) {
		if (!checkLight) {
			return true;
		}
		return j >= 0 && j < worldObj.getHeight() && worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && worldObj.getBlock(i, j, k).getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(worldObj, i, j, k);
	}

	@Override
	public boolean canSnowAt(int i, int j, int k, boolean checkLight) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenOcean) {
			return LOTRBiomeGenOcean.isFrozen(i, k) && canSnow_ignoreTemp(i, j, k, checkLight);
		}
		if (biome instanceof LOTRBiomeGenTundra) {
			boolean flag = worldObj.canSnowAtBody(i, j, k, checkLight);
			return flag && LOTRBiomeGenTundra.isTundraSnowy(i, k);
		}
		if (biome instanceof LOTRBiome) {
			return j >= ((LOTRBiome) biome).getSnowHeight() && worldObj.canSnowAtBody(i, j, k, checkLight);
		}
		return worldObj.canSnowAtBody(i, j, k, checkLight);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int i, int k) {
        int chunkX = i >> 4;
        int chunkZ = k >> 4;

        if (worldObj.getChunkProvider().chunkExists(chunkX, chunkZ)) {
            BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
            return biome instanceof LOTRBiome && ((LOTRBiome) biome).hasFog();
        }
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 drawClouds(float f) {
		Minecraft mc = Minecraft.getMinecraft();
		int i = (int) mc.renderViewEntity.posX;
		int k = (int) mc.renderViewEntity.posZ;
		Vec3 clouds = super.drawClouds(f);
		double cloudsB = 0.0;
		double cloudsG = 0.0;
		double cloudsR = 0.0;
		GameSettings settings = mc.gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
			distance = ranges[settings.renderDistanceChunks];
		}
		int l = 0;
		for (int i1 = -distance; i1 <= distance; ++i1) {
			for (int k1 = -distance; k1 <= distance; ++k1) {
				Vec3 tempClouds = Vec3.createVectorHelper(clouds.xCoord, clouds.yCoord, clouds.zCoord);
				BiomeGenBase biome = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				if (biome instanceof LOTRBiome) {
					((LOTRBiome) biome).getCloudColor(tempClouds);
				}
				cloudsR += tempClouds.xCoord;
				cloudsG += tempClouds.yCoord;
				cloudsB += tempClouds.zCoord;
				++l;
			}
		}
		cloudsR /= l;
		cloudsG /= l;
		cloudsB /= l;
		return Vec3.createVectorHelper(cloudsR, cloudsG, cloudsB);
	}

    private final LOTRDimension dim = getLOTRDimension();
    private final BiomeGenBase[] biomeList = dim.biomeList;

    @Override
    public BiomeGenBase getBiomeGenForCoords(int i, int k) {
        if (!worldObj.blockExists(i, 0, k)) {
            return worldChunkMgr.getBiomeGenAt(i, k);
        }

        BiomeGenBase biome = worldChunkMgr.getBiomeGenAt(i, k);
        int biomeID = biome.biomeID;

        // Early return if the biomeID is out of range
        if (biomeID < 0 || biomeID >= biomeList.length) {
            return worldChunkMgr.getBiomeGenAt(i, k);
        }

        // Lookup override biome
        return getOverrideBiome(biomeID, i, k);
    }


    private BiomeGenBase getOverrideBiome(int biomeID,int i, int k) {
        // Lookup logic
        BiomeGenBase selectedBiome = biomeList[biomeID];
        if (selectedBiome != null) {
            return selectedBiome;
        }

        // If no override biome is found, return default biome
        return worldChunkMgr.getBiomeGenAt(i, k);
    }


    @Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 192.0f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		if (!LOTRModChecker.hasShaders() && LOTRConfig.cloudRange > 0) {
			if (lotrCloudRenderer == null) {
				lotrCloudRenderer = new LOTRCloudRenderer();
			}
			return lotrCloudRenderer;
		}
		return super.getCloudRenderer();
	}

	@Override
	public String getDepartMessage() {
		return StatCollector.translateToLocalFormatted("lotr.dimension.exit", getLOTRDimension().getDimensionName());
	}

	@Override
	public String getDimensionName() {
		return getLOTRDimension().dimensionName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float f, float f1) {
		Minecraft mc = Minecraft.getMinecraft();
		int i = (int) mc.renderViewEntity.posX;
		int k = (int) mc.renderViewEntity.posZ;
		Vec3 fog = super.getFogColor(f, f1);
		double fogB = 0.0;
		double fogG = 0.0;
		double fogR = 0.0;
		GameSettings settings = mc.gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
			distance = ranges[settings.renderDistanceChunks];
		}
		int l = 0;
		for (int i1 = -distance; i1 <= distance; ++i1) {
			for (int k1 = -distance; k1 <= distance; ++k1) {
				Vec3 tempFog = Vec3.createVectorHelper(fog.xCoord, fog.yCoord, fog.zCoord);
				BiomeGenBase biome = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				if (biome instanceof LOTRBiome) {
					((LOTRBiome) biome).getFogColor(tempFog);
				}
				fogR += tempFog.xCoord;
				fogG += tempFog.yCoord;
				fogB += tempFog.zCoord;
				++l;
			}
		}
		fogR /= l;
		fogG /= l;
		fogB /= l;
		return Vec3.createVectorHelper(fogR, fogG, fogB);
	}

	public abstract LOTRDimension getLOTRDimension();

	@Override
	public int getMoonPhase(long time) {
		return getLOTRMoonPhase();
	}

	@Override
	public String getSaveFolder() {
		return getLOTRDimension().dimensionName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		if (!LOTRModChecker.hasShaders() && LOTRConfig.enableLOTRSky) {
			if (lotrSkyRenderer == null) {
				lotrSkyRenderer = new LOTRSkyRenderer(this);
			}
			return lotrSkyRenderer;
		}
		return super.getSkyRenderer();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getWeatherRenderer() {
		if (lotrWeatherRenderer == null) {
			lotrWeatherRenderer = new LOTRWeatherRenderer();
		}
		return lotrWeatherRenderer;
	}

	@Override
	public String getWelcomeMessage() {
		return StatCollector.translateToLocalFormatted("lotr.dimension.enter", getLOTRDimension().getDimensionName());
	}

	public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
		return rgb;
	}

    public float[] modifyFogIntensity(float farPlane, int fogMode) {
        Minecraft mc = Minecraft.getMinecraft();
        int i = (int) mc.renderViewEntity.posX;
        int k = (int) mc.renderViewEntity.posZ;
        float fogStart = 0.0f;
        float fogEnd = 0.0f;
        GameSettings settings = mc.gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;

        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }

        int totalIterations = (2 * distance + 1) * (2 * distance + 1);
        float fogMultiplier = 0.05f;

        if (fogMode >= 0) {
            fogMultiplier = 0.75f;
        }

        for (int i1 = -distance; i1 <= distance; ++i1) {
            for (int k1 = -distance; k1 <= distance; ++k1) {
                boolean foggy = doesXZShowFog(i + i1, k + k1);
                float thisFogStart = foggy ? farPlane * 0.05f : farPlane * fogMultiplier;
                float thisFogEnd = foggy ? Math.min(farPlane, 192.0f) * 0.5f : farPlane;

                fogStart += thisFogStart;
                fogEnd += thisFogEnd;
            }
        }

        return new float[]{fogStart / totalIterations, fogEnd / totalIterations};
    }

	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new LOTRWorldChunkManager(worldObj, getLOTRDimension());
		dimensionId = getLOTRDimension().dimensionID;
	}

	@Override
	public void resetRainAndThunder() {
		super.resetRainAndThunder();
		if (LOTRMod.doDayCycle(worldObj)) {
			LOTRTime.advanceToMorning();
		}
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double y, double z) {
		return false;
	}
}
