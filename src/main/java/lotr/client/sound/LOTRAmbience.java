package lotr.client.sound;

import java.util.*;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.render.LOTRWeatherRenderer;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;

public class LOTRAmbience {
	public static ResourceLocation jazzMusicPath = new ResourceLocation("lotr:music.jazzelf");
	public int ticksSinceWight;
	public Collection<ISound> playingWindSounds = new ArrayList<>();
	public Collection<ISound> playingSeaSounds = new ArrayList<>();
	public ISound playingJazzMusic;
	public int jazzPlayerID;

	public LOTRAmbience() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public ISound getJazzMusic(Entity entity) {
		return PositionedSoundRecord.func_147675_a(jazzMusicPath, (float) entity.posX, (float) entity.posY, (float) entity.posZ);
	}

	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent17 event) {
		String name = event.name;
		ISound sound = event.sound;
		if (LOTRConfig.newWeather && sound instanceof PositionedSound) {
			WorldClient world = Minecraft.getMinecraft().theWorld;
			if (world != null && world.provider instanceof LOTRWorldProvider) {
				if ("ambient.weather.rain".equals(name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.rain"), sound.getVolume(), sound.getPitch(), sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
				} else if ("ambient.weather.thunder".equals(name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation("lotr:ambient.weather.thunder"), sound.getVolume(), sound.getPitch(), sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
				}
			}
		}
		if (playingJazzMusic != null && event.category == SoundCategory.MUSIC) {
			event.result = null;
		}
	}

	public void updateAmbience(World world, EntityPlayer entityplayer) {
		world.theProfiler.startSection("lotrAmbience");
		Minecraft mc = Minecraft.getMinecraft();
		boolean enableAmbience = LOTRConfig.enableAmbience;
		boolean enableWeather = LOTRConfig.newWeather;
		double x = entityplayer.posX;
		double y = entityplayer.boundingBox.minY;
		double z = entityplayer.posZ;
		int i = MathHelper.floor_double(x);
		int j = MathHelper.floor_double(y);
		int k = MathHelper.floor_double(z);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		Random rand = world.rand;
		if (enableAmbience) {
			if (ticksSinceWight > 0) {
				ticksSinceWight--;
			} else {
				boolean wights = LOTRTickHandlerClient.anyWightsViewed && rand.nextInt(20) == 0 || biome instanceof LOTRBiomeGenBarrowDowns && rand.nextInt(3000) == 0;
				if (wights) {
					world.playSound(x, y, z, "lotr:wight.ambience", 1.0F, 0.8F + rand.nextFloat() * 0.4F, false);
					ticksSinceWight = 300;
				}
			}
			boolean spookyBiomeNoise = false;
			float spookyPitch = 1.0F;
			if (biome instanceof LOTRBiomeGenDolGuldur) {
				spookyBiomeNoise = rand.nextInt(1000) == 0;
				spookyPitch = 0.85F;
			} else if (biome instanceof LOTRBiomeGenDeadMarshes) {
				spookyBiomeNoise = rand.nextInt(2400) == 0;
			} else if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
				spookyBiomeNoise = rand.nextInt(3000) == 0;
			} else if (biome instanceof LOTRBiomeGenOldForest) {
				spookyBiomeNoise = rand.nextInt(6000) == 0;
			} else if (biome instanceof LOTRBiomeGenUtumno) {
				spookyBiomeNoise = rand.nextInt(1000) == 0;
				spookyPitch = 0.75F;
			}
			if (spookyBiomeNoise) {
				world.playSound(x, y, z, "lotr:wight.ambience", 1.0F, (0.8F + rand.nextFloat() * 0.4F) * spookyPitch, false);
			}
			if (biome instanceof LOTRBiomeGenUtumno && world.rand.nextInt(500) == 0) {
				world.playSound(x, y, z, "ambient.cave.cave", 1.0F, 0.8F + rand.nextFloat() * 0.2F, false);
			}
		}
		if (enableWeather && world.provider instanceof lotr.common.world.LOTRWorldProvider) {
			if (playingWindSounds.size() < 4) {
				int xzRange = 16;
				int minWindHeight = 100;
				int fullWindHeight = 180;
				if (rand.nextInt(20) == 0) {
					minWindHeight -= 10;
					if (rand.nextInt(10) == 0) {
						minWindHeight -= 10;
					}
				}
				if (world.isRaining()) {
					minWindHeight = 80;
					fullWindHeight = 120;
					if (rand.nextInt(20) == 0) {
						minWindHeight -= 20;
					}
					if (LOTRWeatherRenderer.isSandstormBiome(biome)) {
						minWindHeight = 60;
						fullWindHeight = 80;
					}
				}
				for (int l = 0; l < 2; l++) {
					int i1 = i + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
					int k1 = k + MathHelper.getRandomIntegerInRange(rand, -xzRange, xzRange);
					int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 16);
					if (j1 >= minWindHeight && world.canBlockSeeTheSky(i1, j1, k1)) {
						float windiness = (float) (j1 - minWindHeight) / (fullWindHeight - minWindHeight);
						windiness = MathHelper.clamp_float(windiness, 0.0F, 1.0F);
						if (windiness >= rand.nextFloat()) {
							float x1 = i1 + 0.5F;
							float y1 = j1 + 0.5F;
							float z1 = k1 + 0.5F;
							float vol = Math.max(0.25F, windiness);
							float pitch = 0.8F + rand.nextFloat() * 0.4F;
							AmbientSoundNoAttentuation ambientSoundNoAttentuation = new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.weather.wind"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
							mc.getSoundHandler().playSound(ambientSoundNoAttentuation);
							playingWindSounds.add(ambientSoundNoAttentuation);
							break;
						}
					}
				}
			} else {
				Collection<ISound> removes = new HashSet<>();
				for (ISound wind : playingWindSounds) {
					if (!mc.getSoundHandler().isSoundPlaying(wind)) {
						removes.add(wind);
					}
				}
				playingWindSounds.removeAll(removes);
			}
		}
		if (enableAmbience) {
			if (playingSeaSounds.size() < 3) {
				if (biome instanceof LOTRBiomeGenOcean || biome instanceof LOTRBiomeGenLindonCoast || biome instanceof LOTRBiomeGenFarHaradCoast) {
					int xzRange = 64;
					float[] rangeChecks = {0.25F, 0.5F, 0.75F, 1.0F};
					for (float fr : rangeChecks) {
						int range = (int) (xzRange * fr);
						for (int l = 0; l < 8; l++) {
							int i1 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
							int k1 = k + MathHelper.getRandomIntegerInRange(rand, -range, range);
							int j1 = j + MathHelper.getRandomIntegerInRange(rand, -16, 8);
							Block block = world.getBlock(i1, j1, k1);
							if (block.getMaterial() == Material.water && j1 >= world.getTopSolidOrLiquidBlock(i1, k1)) {
								float x1 = i1 + 0.5F;
								float y1 = j1 + 0.5F;
								float z1 = k1 + 0.5F;
								float vol = 1.0F;
								float pitch = 0.8F + rand.nextFloat() * 0.4F;
								AmbientSoundNoAttentuation ambientSoundNoAttentuation = new AmbientSoundNoAttentuation(new ResourceLocation("lotr:ambient.terrain.sea"), vol, pitch, x1, y1, z1).calcAmbientVolume(entityplayer, xzRange);
								mc.getSoundHandler().playSound(ambientSoundNoAttentuation);
								playingSeaSounds.add(ambientSoundNoAttentuation);
								int j2 = world.getHeightValue(i1, k1) - 1;
								if (world.getBlock(i1, j2, k1).getMaterial() == Material.water) {
									double dx = i1 + 0.5D - entityplayer.posX;
									double dz = k1 + 0.5D - entityplayer.posZ;
									float angle = (float) Math.atan2(dz, dx);
									float cos = MathHelper.cos(angle);
									float sin = MathHelper.sin(angle);
									float angle90 = angle - 1.5707963267948966f;
									float cos90 = MathHelper.cos(angle90);
									float sin90 = MathHelper.sin(angle90);
									float waveSpeed = MathHelper.randomFloatClamp(rand, 0.3F, 0.5F);
									int waveR = 40 + rand.nextInt(100);
									for (int w = -waveR; w <= waveR; w++) {
										float f = w / 8.0F;
										double d0 = i1 + 0.5D;
										double d1 = j2 + 1.0D + MathHelper.randomFloatClamp(rand, 0.02F, 0.1F);
										double d2 = k1 + 0.5D;
										d0 += f * cos90;
										d2 += f * sin90;
										if (world.getBlock(MathHelper.floor_double(d0), MathHelper.floor_double(d1) - 1, MathHelper.floor_double(d2)).getMaterial() == Material.water) {
											double d3 = waveSpeed * -cos;
											double d4 = 0.0D;
											double d5 = waveSpeed * -sin;
											LOTRMod.proxy.spawnParticle("wave", d0, d1, d2, d3, d4, d5);
										}
									}
								}
							}
						}
					}
				}
			} else {
				Collection<ISound> removes = new HashSet<>();
				for (ISound sea : playingSeaSounds) {
					if (!mc.getSoundHandler().isSoundPlaying(sea)) {
						removes.add(sea);
					}
				}
				playingSeaSounds.removeAll(removes);
			}
		}
		if (playingJazzMusic == null) {
			if (entityplayer.ticksExisted % 20 == 0) {
				double range = 16.0D;
				List elves = world.getEntitiesWithinAABB(LOTREntityElf.class, entityplayer.boundingBox.expand(range, range, range));
				LOTREntityElf playingElf = null;
				for (Object obj : elves) {
					LOTREntityElf elf = (LOTREntityElf) obj;
					if (elf.isEntityAlive() && elf.isJazz() && elf.isSolo()) {
						playingElf = elf;
						break;
					}
				}
				if (playingElf != null) {
					mc.getSoundHandler().stopSounds();
					jazzPlayerID = playingElf.getEntityId();
					ISound music = getJazzMusic(playingElf);
					mc.getSoundHandler().playSound(music);
					playingJazzMusic = music;
					mc.ingameGUI.setRecordPlayingMessage("The Galadhon Groovers - Funky Villagers");
				}
			}
		} else {
			Entity player = world.getEntityByID(jazzPlayerID);
			if (player == null || !player.isEntityAlive()) {
				mc.getSoundHandler().stopSound(playingJazzMusic);
				playingJazzMusic = null;
			}
			if (!mc.getSoundHandler().isSoundPlaying(playingJazzMusic)) {
				playingJazzMusic = null;
			}
		}
		world.theProfiler.endSection();
	}

	public static class AmbientSoundNoAttentuation extends PositionedSoundRecord {
		public AmbientSoundNoAttentuation(ResourceLocation sound, float vol, float pitch, float x, float y, float z) {
			super(sound, vol, pitch, x, y, z);
			field_147666_i = ISound.AttenuationType.NONE;
		}

		public AmbientSoundNoAttentuation calcAmbientVolume(EntityPlayer entityplayer, int maxRange) {
			float distFr = (float) entityplayer.getDistance(xPosF, yPosF, zPosF);
			distFr /= maxRange;
			distFr = Math.min(distFr, 1.0f);
			distFr = 1.0f - distFr;
			distFr *= 1.5f;
			distFr = MathHelper.clamp_float(distFr, 0.1f, 1.0f);
			volume *= distFr;
			return this;
		}
	}

}
