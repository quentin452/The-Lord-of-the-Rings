package lotr.client.sound;

import lotr.client.LOTRClientProxy;
import lotr.common.LOTRConfig;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class LOTRMusicTicker {
	public static LOTRMusicTrack currentTrack;
	public static boolean wasPlayingMenu = true;
	public static int timing = 100;

	public static LOTRMusicCategory getCurrentCategory(Minecraft mc, Random rand) {
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (world != null && entityplayer != null) {
			int i = MathHelper.floor_double(entityplayer.posX);
			if (LOTRMusicCategory.isCave(world, i, MathHelper.floor_double(entityplayer.boundingBox.minY), MathHelper.floor_double(entityplayer.posZ))) {
				return LOTRMusicCategory.CAVE;
			}
			if (LOTRMusicCategory.isDay(world)) {
				return LOTRMusicCategory.DAY;
			}
			return LOTRMusicCategory.NIGHT;
		}
		return null;
	}

	public static LOTRMusicRegion.Sub getCurrentRegion(Minecraft mc, Random rand) {
		int k;
		int i;
		BiomeGenBase biome;
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (LOTRMusic.isMenuMusic()) {
			return LOTRMusicRegion.MENU.getWithoutSub();
		}
		if (LOTRMusic.isLOTRDimension() && LOTRClientProxy.doesClientChunkExist(world, i = MathHelper.floor_double(entityplayer.posX), k = MathHelper.floor_double(entityplayer.posZ)) && (biome = world.getBiomeGenForCoords(i, k)) instanceof LOTRBiome) {
			LOTRBiome lotrbiome = (LOTRBiome) biome;
			return lotrbiome.getBiomeMusic();
		}
		return null;
	}

	public static LOTRMusicTrack getNewTrack(Minecraft mc, Random rand) {
		LOTRMusicRegion.Sub regionSub = getCurrentRegion(mc, rand);
		LOTRMusicCategory category = getCurrentCategory(mc, rand);
		if (regionSub != null) {
			LOTRMusicRegion region = regionSub.region;
			String sub = regionSub.subregion;
			LOTRTrackSorter.Filter filter = category != null ? LOTRTrackSorter.forRegionAndCategory(region, category) : LOTRTrackSorter.forAny();
			LOTRRegionTrackPool trackPool = LOTRMusic.getTracksForRegion(region, sub);
			return trackPool.getRandomTrack(rand, filter);
		}
		return null;
	}

	public static void resetTiming(Random rand) {
		timing = LOTRMusic.isMenuMusic() ? MathHelper.getRandomIntegerInRange(rand, LOTRConfig.musicIntervalMenuMin * 20, LOTRConfig.musicIntervalMenuMax * 20) : MathHelper.getRandomIntegerInRange(rand, LOTRConfig.musicIntervalMin * 20, LOTRConfig.musicIntervalMax * 20);
	}

	public static void update(Random rand) {
		Minecraft mc = Minecraft.getMinecraft();
		boolean noMusic = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC) <= 0.0f;
		boolean menu = LOTRMusic.isMenuMusic();
		if (wasPlayingMenu != menu) {
			if (currentTrack != null) {
				mc.getSoundHandler().stopSound(currentTrack);
				currentTrack = null;
			}
			wasPlayingMenu = menu;
			timing = 100;
		}
		if (currentTrack != null) {
			if (noMusic) {
				mc.getSoundHandler().stopSound(currentTrack);
			}
			if (!mc.getSoundHandler().isSoundPlaying(currentTrack)) {
				currentTrack = null;
				resetTiming(rand);
			}
		}
		if (!noMusic) {
			boolean update;
			if (menu) {
				update = true;
			} else {
				update = LOTRMusic.isLOTRDimension() && !Minecraft.getMinecraft().isGamePaused();
			}
			if (update && currentTrack == null && --timing <= 0) {
				currentTrack = getNewTrack(mc, rand);
				if (currentTrack != null) {
					wasPlayingMenu = menu;
					mc.getSoundHandler().playSound(currentTrack);
					timing = Integer.MAX_VALUE;
				} else {
					timing = 400;
				}
			}
		}
	}
}
