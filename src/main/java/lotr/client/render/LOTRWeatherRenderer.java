package lotr.client.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.*;
import lotr.common.world.biome.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;

public class LOTRWeatherRenderer extends IRenderHandler {
	public static ResourceLocation rainTexture = new ResourceLocation("lotr:weather/rain.png");
	public static ResourceLocation snowTexture = new ResourceLocation("lotr:weather/snow.png");
	public static ResourceLocation ashTexture = new ResourceLocation("lotr:weather/ash.png");
	public static ResourceLocation sandstormTexture = new ResourceLocation("lotr:weather/sandstorm.png");
	public static ResourceLocation rainTexture_def = new ResourceLocation("textures/environment/rain.png");
	public static ResourceLocation snowTexture_def = new ResourceLocation("textures/environment/snow.png");
	public Random rand = new Random();
	public float[] rainXCoords;
	public float[] rainYCoords;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		EntityRenderer er = mc.entityRenderer;
		int rendererUpdateCount = LOTRTickHandlerClient.clientTick;
		float rainStrength = world.getRainStrength(partialTicks);
		if (rainStrength > 0.0f) {
			er.enableLightmap(partialTicks);
			if (rainXCoords == null) {
				rainXCoords = new float[1024];
				rainYCoords = new float[1024];
				for (int i = 0; i < 32; ++i) {
					for (int j = 0; j < 32; ++j) {
						float f2 = j - 16;
						float f3 = i - 16;
						float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
						rainXCoords[i << 5 | j] = -f3 / f4;
						rainYCoords[i << 5 | j] = f2 / f4;
					}
				}
			}
			EntityLivingBase entitylivingbase = mc.renderViewEntity;
			int k2 = MathHelper.floor_double(entitylivingbase.posX);
			int l2 = MathHelper.floor_double(entitylivingbase.posY);
			int i3 = MathHelper.floor_double(entitylivingbase.posZ);
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(2884);
			GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glAlphaFunc(516, 0.1f);
			double d0 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * partialTicks;
			double d1 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * partialTicks;
			double d2 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * partialTicks;
			int k = MathHelper.floor_double(d1);
			int b0 = 5;
			if (mc.gameSettings.fancyGraphics) {
				b0 = 10;
			}
			int b1 = -1;
			float f5 = rendererUpdateCount + partialTicks;
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			boolean isChristmas = LOTRMod.isChristmas();
			for (int l = i3 - b0; l <= i3 + b0; ++l) {
				for (int i1 = k2 - b0; i1 <= k2 + b0; ++i1) {
					float f11;
					float f15;
					float f14;
					double d4;
					float f10;
					double d5;
					float f16;
					int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
					float f6 = rainXCoords[j1] * 0.5f;
					float f7 = rainYCoords[j1] * 0.5f;
					BiomeGenBase biomegenbase = world.getBiomeGenForCoords(i1, l);
					boolean rainy = biomegenbase.canSpawnLightningBolt();
					boolean snowy = biomegenbase.getEnableSnow();
					boolean ashy = biomegenbase instanceof LOTRBiomeGenMordor && !(biomegenbase instanceof LOTRBiomeGenNurn) && !(biomegenbase instanceof LOTRBiomeGenMorgulVale) || biomegenbase instanceof LOTRBiomeGenFarHaradVolcano || biomegenbase instanceof LOTRBiomeGenDagorlad;
					boolean sandy = isSandstormBiome(biomegenbase);
					if (isChristmas) {
						ashy = false;
						sandy = false;
					}
					if (!rainy && !snowy && !ashy && !sandy) {
						continue;
					}
					int k1 = world.getPrecipitationHeight(i1, l);
					int l1 = l2 - b0;
					int i2 = l2 + b0;
					if (l1 < k1) {
						l1 = k1;
					}
					if (i2 < k1) {
						i2 = k1;
					}
					float f8 = 1.0f;
					int j2 = Math.max(k1, k);
					if (l1 == i2) {
						continue;
					}
					rand.setSeed((long) i1 * i1 * 3121 + i1 * 45238971L ^ (long) l * l * 418711 + l * 13761L);
					float f9 = biomegenbase.getFloatTemperature(i1, l1, l);
					if (ashy) {
						if (b1 != 1) {
							if (b1 == 0) {
								tessellator.draw();
							}
							b1 = 1;
							mc.getTextureManager().bindTexture(ashTexture);
							tessellator.startDrawingQuads();
						}
						f10 = ((rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
						f16 = rand.nextFloat() * 0.3f + f5 * 0.003f * (float) rand.nextGaussian();
						f11 = rand.nextFloat() + f5 * (float) rand.nextGaussian() * 0.001f;
						d4 = i1 + 0.5f - entitylivingbase.posX;
						d5 = l + 0.5f - entitylivingbase.posZ;
						f14 = MathHelper.sqrt_double(d4 * d4 + d5 * d5) / b0;
						f15 = 1.0f;
						tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
						tessellator.setColorRGBA_F(f15, f15, f15, ((1.0f - f14 * f14) * 0.3f + 0.5f) * rainStrength);
						tessellator.setTranslation(-d0, -d1, -d2);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, l1, l - f7 + 0.5, 0.0f * f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, l1, l + f7 + 0.5, f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, i2, l + f7 + 0.5, f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, i2, l - f7 + 0.5, 0.0f * f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.setTranslation(0.0, 0.0, 0.0);
						continue;
					}
					if (sandy) {
						if (b1 != 1) {
							if (b1 == 0) {
								tessellator.draw();
							}
							b1 = 1;
							mc.getTextureManager().bindTexture(sandstormTexture);
							tessellator.startDrawingQuads();
						}
						f10 = ((rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
						f16 = f5 * (0.07f + (float) rand.nextGaussian() * 0.01f);
						f11 = rand.nextFloat() + f5 * (float) rand.nextGaussian() * 0.001f;
						d4 = i1 + 0.5f - entitylivingbase.posX;
						d5 = l + 0.5f - entitylivingbase.posZ;
						f14 = MathHelper.sqrt_double(d4 * d4 + d5 * d5) / b0;
						f15 = 1.0f;
						tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
						tessellator.setColorRGBA_F(f15, f15, f15, ((1.0f - f14 * f14) * 0.3f + 0.5f) * rainStrength);
						tessellator.setTranslation(-d0, -d1, -d2);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, l1, l - f7 + 0.5, 0.0f * f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, l1, l + f7 + 0.5, f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, i2, l + f7 + 0.5, f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, i2, l - f7 + 0.5, 0.0f * f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
						tessellator.setTranslation(0.0, 0.0, 0.0);
						continue;
					}
					if (world.getWorldChunkManager().getTemperatureAtHeight(f9, k1) >= 0.15f) {
						if (b1 != 0) {
							if (b1 >= 0) {
								tessellator.draw();
							}
							b1 = 0;
							if (LOTRConfig.newWeather) {
								mc.getTextureManager().bindTexture(rainTexture);
							} else {
								mc.getTextureManager().bindTexture(rainTexture_def);
							}
							tessellator.startDrawingQuads();
						}
						f10 = ((rendererUpdateCount + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 0x1F) + partialTicks) / 32.0f * (3.0f + rand.nextFloat());
						double d3 = i1 + 0.5f - entitylivingbase.posX;
						d4 = l + 0.5f - entitylivingbase.posZ;
						float f12 = MathHelper.sqrt_double(d3 * d3 + d4 * d4) / b0;
						float f13 = 1.0f;
						tessellator.setBrightness(world.getLightBrightnessForSkyBlocks(i1, j2, l, 0));
						tessellator.setColorRGBA_F(f13, f13, f13, ((1.0f - f12 * f12) * 0.5f + 0.5f) * rainStrength);
						tessellator.setTranslation(-d0, -d1, -d2);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, l1, l - f7 + 0.5, 0.0f * f8, l1 * f8 / 4.0f + f10 * f8);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, l1, l + f7 + 0.5, f8, l1 * f8 / 4.0f + f10 * f8);
						tessellator.addVertexWithUV(i1 + f6 + 0.5, i2, l + f7 + 0.5, f8, i2 * f8 / 4.0f + f10 * f8);
						tessellator.addVertexWithUV(i1 - f6 + 0.5, i2, l - f7 + 0.5, 0.0f * f8, i2 * f8 / 4.0f + f10 * f8);
						tessellator.setTranslation(0.0, 0.0, 0.0);
						continue;
					}
					if (b1 != 1) {
						if (b1 == 0) {
							tessellator.draw();
						}
						b1 = 1;
						if (LOTRConfig.newWeather) {
							mc.getTextureManager().bindTexture(snowTexture);
						} else {
							mc.getTextureManager().bindTexture(snowTexture_def);
						}
						tessellator.startDrawingQuads();
					}
					f10 = ((rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
					f16 = rand.nextFloat() + f5 * 0.01f * (float) rand.nextGaussian();
					f11 = rand.nextFloat() + f5 * (float) rand.nextGaussian() * 0.001f;
					d4 = i1 + 0.5f - entitylivingbase.posX;
					d5 = l + 0.5f - entitylivingbase.posZ;
					f14 = MathHelper.sqrt_double(d4 * d4 + d5 * d5) / b0;
					f15 = 1.0f;
					tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
					tessellator.setColorRGBA_F(f15, f15, f15, ((1.0f - f14 * f14) * 0.3f + 0.5f) * rainStrength);
					tessellator.setTranslation(-d0, -d1, -d2);
					tessellator.addVertexWithUV(i1 - f6 + 0.5, l1, l - f7 + 0.5, 0.0f * f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
					tessellator.addVertexWithUV(i1 + f6 + 0.5, l1, l + f7 + 0.5, f8 + f16, l1 * f8 / 4.0f + f10 * f8 + f11);
					tessellator.addVertexWithUV(i1 + f6 + 0.5, i2, l + f7 + 0.5, f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
					tessellator.addVertexWithUV(i1 - f6 + 0.5, i2, l - f7 + 0.5, 0.0f * f8 + f16, i2 * f8 / 4.0f + f10 * f8 + f11);
					tessellator.setTranslation(0.0, 0.0, 0.0);
				}
			}
			if (b1 >= 0) {
				tessellator.draw();
			}
			GL11.glEnable(2884);
			GL11.glDisable(3042);
			GL11.glAlphaFunc(516, 0.1f);
			er.disableLightmap(partialTicks);
		}
	}

	public static boolean isSandstormBiome(BiomeGenBase biome) {
		return !biome.canSpawnLightningBolt() && biome.topBlock.getMaterial() == Material.sand;
	}
}
