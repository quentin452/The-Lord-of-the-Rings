package lotr.client.render;

import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import lotr.common.LOTRTime;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;
import java.util.List;
import java.util.*;

public class LOTRRenderNorthernLights {
	public static int nlTick;
	public static int currentNightNum;
	public static float brightnessTonight;
	public static float maxNorthTonight;
	public static float minNorthTonight;
	public static int rainingTick;
	public static int rainingTickPrev;
	public static boolean atUtumno = false;
	public static int utumnoChange = 0;
	public static Random rand = new Random();
	public static Random dateRand = new Random();
	public static float[] colorTopCurrent;
	public static float[] colorMidCurrent;
	public static float[] colorBottomCurrent;
	public static float[] colorTopNext;
	public static float[] colorMidNext;
	public static float[] colorBottomNext;
	public static int colorChangeTime;
	public static int colorChangeTick;
	public static int timeUntilColorChange;
	public static int utumnoCheckTime;
	public static AuroraCycle wave0 = new AuroraCycle(4.0f, 0.01f, 0.9f);
	public static List<AuroraCycle> waveOscillations = new ArrayList<>();
	public static List<AuroraCycle> glowOscillations = new ArrayList<>();
	public static AuroraCycle glow0 = new AuroraCycle(20.0f, 0.02f, 0.6f);

	public static Color[] generateColorSet() {
		float h1 = MathHelper.randomFloatClamp(rand, 0.22f, 0.48f);
		float h2 = MathHelper.randomFloatClamp(rand, 0.22f, 0.48f);
		float h3 = MathHelper.randomFloatClamp(rand, 0.22f, 0.48f);
		if (rand.nextInt(3) == 0) {
			h1 = MathHelper.randomFloatClamp(rand, 0.78f, 1.08f);
		}
		if (rand.nextInt(5) == 0) {
			h1 = MathHelper.randomFloatClamp(rand, 0.78f, 1.08f);
			h2 = MathHelper.randomFloatClamp(rand, 0.85f, 1.08f);
		}
		if (rand.nextInt(50) == 0) {
			h1 = MathHelper.randomFloatClamp(rand, 0.7f, 1.08f);
			h2 = MathHelper.randomFloatClamp(rand, 0.54f, 0.77f);
			h3 = MathHelper.randomFloatClamp(rand, 0.48f, 0.7f);
		}
		Color topColor = new Color(Color.HSBtoRGB(h1, 1.0f, 1.0f));
		Color midColor = new Color(Color.HSBtoRGB(h2, 1.0f, 1.0f));
		Color bottomColor = new Color(Color.HSBtoRGB(h3, 1.0f, 1.0f));
		return new Color[]{topColor, midColor, bottomColor};
	}

	public static float getNorthernness(EntityLivingBase entity) {
		float minNorth = minNorthTonight;
		float maxNorth = maxNorthTonight;
		float northernness = ((float) entity.posZ - minNorth) / (maxNorth - minNorth);
		return MathHelper.clamp_float(northernness, 0.0f, 1.0f);
	}

	public static float glowEquation(Minecraft mc, float t, float tick, float renderTick) {
		float f = 0.0f;
		f += glow0.calc(t, tick);
		if (mc.gameSettings.fancyGraphics) {
			for (AuroraCycle c : glowOscillations) {
				f += c.calc(t, tick);
			}
		}
		return f;
	}

	public static boolean isRainLayerAt(EntityLivingBase entity) {
		World world = entity.worldObj;
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.boundingBox.minY);
		int k = MathHelper.floor_double(entity.posZ);
		if (!world.isRaining()) {
			return false;
		}
		BiomeGenBase biomegenbase = world.getBiomeGenForCoords(i, k);
		if (biomegenbase.getEnableSnow()) {
			return false;
		}
		return biomegenbase.getFloatTemperature(i, j, k) >= 0.15f && biomegenbase.canSpawnLightningBolt();
	}

	public static void render(Minecraft mc, WorldClient world, float tick) {
		float maxDaylight;
		if (mc.gameSettings.renderDistanceChunks < 6) {
			return;
		}
		float minSun = 0.2f;
		float daylight = (world.getSunBrightness(tick) - minSun) / (1.0f - minSun);
		float nlBrightness = (1.0f - daylight - (1.0f - (maxDaylight = 0.3f))) / maxDaylight;
		if (nlBrightness <= 0.0f) {
			return;
		}
		float tonight = brightnessTonight;
		float utumno = utumnoChange / 200.0f;
		tonight += (1.0f - tonight) * utumno;
		if (tonight <= 0.0f) {
			return;
		}
		nlBrightness *= tonight;
		float northernness = getNorthernness(mc.renderViewEntity);
		if (northernness <= 0.0f) {
			return;
		}
		nlBrightness *= northernness;
		float raininess = (rainingTickPrev + (rainingTick - rainingTickPrev) * tick) / 80.0f;
		if (raininess >= 1.0f) {
			return;
		}
		nlBrightness *= 1.0f - raininess;
		world.theProfiler.startSection("aurora");
		float nlScale = 2000.0f;
		float nlDistance = (1.0f - northernness) * nlScale * 2.0f;
		float nlHeight = nlScale * 0.5f;
		GL11.glMatrixMode(5889);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		float fov = LOTRReflectionClient.getFOVModifier(mc.entityRenderer, tick, true);
		Project.gluPerspective(fov, (float) mc.displayWidth / mc.displayHeight, 0.05f, nlScale * 5.0f);
		GL11.glMatrixMode(5888);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, nlHeight, -nlDistance);
		GL11.glScalef(1.0f, 1.0f, 1.0f);
		GL11.glDisable(3553);
		GL11.glDisable(3008);
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		float alphaFunc = GL11.glGetFloat(3010);
		GL11.glAlphaFunc(516, 0.01f);
		GL11.glShadeModel(7425);
		GL11.glDisable(2884);
		world.theProfiler.startSection("sheet");
		renderSheet(mc, world, nlScale * -0.5f, (nlBrightness *= 0.3f + (1.0f - world.getRainStrength(tick)) * 0.7f) * 0.8f, nlScale, nlScale * 0.25f, 0.25502f, tick);
		renderSheet(mc, world, 0.0f, nlBrightness, nlScale * 1.5f, nlScale * 0.3f, 0.15696f, tick);
		renderSheet(mc, world, nlScale * 0.5f, nlBrightness * 0.8f, nlScale, nlScale * 0.25f, 0.67596f, tick);
		world.theProfiler.endSection();
		GL11.glEnable(2884);
		GL11.glShadeModel(7424);
		GL11.glAlphaFunc(516, alphaFunc);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glEnable(3008);
		GL11.glEnable(3553);
		GL11.glMatrixMode(5889);
		GL11.glPopMatrix();
		GL11.glMatrixMode(5888);
		GL11.glPopMatrix();
		world.theProfiler.endSection();
	}

	public static void renderSheet(Minecraft mc, World world, float nlDistance, float nlBrightness, double halfWidth, double halfHeight, float tickExtra, float tick) {
		float r1 = colorTopCurrent[0];
		float g1 = colorTopCurrent[1];
		float b1 = colorTopCurrent[2];
		float r2 = colorMidCurrent[0];
		float g2 = colorMidCurrent[1];
		float b2 = colorMidCurrent[2];
		float r3 = colorBottomCurrent[0];
		float g3 = colorBottomCurrent[1];
		float b3 = colorBottomCurrent[2];
		if (colorChangeTime > 0) {
			float t = (float) colorChangeTick / colorChangeTime;
			t = 1.0f - t;
			r1 = colorTopCurrent[0] + (colorTopNext[0] - colorTopCurrent[0]) * t;
			g1 = colorTopCurrent[1] + (colorTopNext[1] - colorTopCurrent[1]) * t;
			b1 = colorTopCurrent[2] + (colorTopNext[2] - colorTopCurrent[2]) * t;
			r2 = colorMidCurrent[0] + (colorMidNext[0] - colorMidCurrent[0]) * t;
			g2 = colorMidCurrent[1] + (colorMidNext[1] - colorMidCurrent[1]) * t;
			b2 = colorMidCurrent[2] + (colorMidNext[2] - colorMidCurrent[2]) * t;
			r3 = colorBottomCurrent[0] + (colorBottomNext[0] - colorBottomCurrent[0]) * t;
			g3 = colorBottomCurrent[1] + (colorBottomNext[1] - colorBottomCurrent[1]) * t;
			b3 = colorBottomCurrent[2] + (colorBottomNext[2] - colorBottomCurrent[2]) * t;
		}
		float a1 = 0.0f;
		float a2 = 0.4f;
		float a3 = 0.8f;
		a1 *= nlBrightness;
		a2 *= nlBrightness;
		a3 *= nlBrightness;
		float fullTick = nlTick + tick + tickExtra;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setBrightness(15728880);
		world.theProfiler.startSection("vertexLoop");
		int strips = 500;
		if (!mc.gameSettings.fancyGraphics) {
			strips = 80;
		}
		for (int l = 0; l < strips; ++l) {
			float t = (float) l / strips;
			float t1 = (float) (l + 1) / strips;
			float a1_here = a1;
			float a2_here = a2;
			float a3_here = a3;
			float fadeEdge = 0.3f;
			float fadePos = Math.min(t, 1.0f - t);
			if (fadePos < fadeEdge) {
				float fade = fadePos / fadeEdge;
				a1_here *= fade;
				a2_here *= fade;
				a3_here *= fade;
			}
			float randomFade = 0.5f + glowEquation(mc, t, fullTick, tick) * 0.5f;
			double x0 = -halfWidth + halfWidth * 2.0 * t;
			double x1 = x0 + halfWidth * 2.0 / strips;
			double yMin = -halfHeight;
			double yMid = -halfHeight * 0.4;
			double z0 = nlDistance;
			double z1 = nlDistance;
			double extra = halfHeight * 0.15;
			tess.setColorRGBA_F(r3, g3, b3, 0.0f);
			tess.addVertex(x0, yMin - extra, z0 += waveEquation(mc, t, fullTick, tick) * (halfWidth * 0.15));
			tess.addVertex(x1, yMin - extra, z1 += waveEquation(mc, t1, fullTick, tick) * (halfWidth * 0.15));
			tess.setColorRGBA_F(r3, g3, b3, a3_here *= randomFade);
			tess.addVertex(x1, yMin, z1);
			tess.addVertex(x0, yMin, z0);
			tess.setColorRGBA_F(r3, g3, b3, a3_here);
			tess.addVertex(x0, yMin, z0);
			tess.addVertex(x1, yMin, z1);
			tess.setColorRGBA_F(r2, g2, b2, a2_here *= randomFade);
			tess.addVertex(x1, yMid, z1);
			tess.addVertex(x0, yMid, z0);
			tess.setColorRGBA_F(r2, g2, b2, a2_here);
			tess.addVertex(x0, yMid, z0);
			tess.addVertex(x1, yMid, z1);
			tess.setColorRGBA_F(r1, g1, b1, a1_here * randomFade);
			tess.addVertex(x1, halfHeight, z1);
			tess.addVertex(x0, halfHeight, z0);
		}
		world.theProfiler.endSection();
		world.theProfiler.startSection("draw");
		tess.draw();
		world.theProfiler.endSection();
	}

	public static void update(EntityLivingBase viewer) {
		AuroraCycle cycle;
		float amp;
		float speed;
		++nlTick;
		World world = viewer.worldObj;
		int effectiveDay = LOTRDate.ShireReckoning.currentDay;
		float daytime = world.getWorldTime() % LOTRTime.DAY_LENGTH;
		if (daytime < 0.25f) {
			--effectiveDay;
		}
		if (effectiveDay != currentNightNum) {
			currentNightNum = effectiveDay;
			dateRand.setSeed(currentNightNum * 35920558925051L + currentNightNum + 83025820626792L);
			LOTRDate.ShireReckoning.Month month = LOTRDate.ShireReckoning.getShireDate(currentNightNum).month;
			boolean isYule = month == LOTRDate.ShireReckoning.Month.YULE_1 || month == LOTRDate.ShireReckoning.Month.YULE_2;
			maxNorthTonight = -35000.0f;
			minNorthTonight = MathHelper.randomFloatClamp(dateRand, -20000.0f, -15000.0f);
			float goSouth = dateRand.nextFloat();
			if (isYule || goSouth < 0.01f) {
				minNorthTonight += 15000.0f;
			} else if (goSouth < 0.1f) {
				minNorthTonight += 10000.0f;
			} else if (goSouth < 0.5f) {
				minNorthTonight += 5000.0f;
			}
			if (LOTRMod.isChristmas()) {
				minNorthTonight = 1000000.0f;
			}
			float appearChance = 0.5f;
			brightnessTonight = isYule || dateRand.nextFloat() < appearChance ? MathHelper.randomFloatClamp(dateRand, 0.4f, 1.0f) : 0.0f;
		}
		rainingTickPrev = rainingTick;
		boolean raining = isRainLayerAt(viewer);
		if (raining) {
			if (rainingTick < 80) {
				++rainingTick;
			}
		} else if (rainingTick > 0) {
			--rainingTick;
		}
		if (colorTopCurrent == null) {
			Color[] cs = generateColorSet();
			colorTopCurrent = cs[0].getColorComponents(null);
			colorMidCurrent = cs[1].getColorComponents(null);
			colorBottomCurrent = cs[2].getColorComponents(null);
		}
		if (timeUntilColorChange > 0) {
			--timeUntilColorChange;
		} else if (rand.nextInt(1200) == 0) {
			Color[] cs = generateColorSet();
			colorTopNext = cs[0].getColorComponents(null);
			colorMidNext = cs[1].getColorComponents(null);
			colorBottomNext = cs[2].getColorComponents(null);
			colorChangeTick = colorChangeTime = MathHelper.getRandomIntegerInRange(rand, 100, 200);
			utumnoCheckTime = 0;
		}
		if (colorChangeTick > 0 && --colorChangeTick <= 0) {
			colorChangeTime = 0;
			colorTopCurrent = colorTopNext;
			colorMidCurrent = colorMidNext;
			colorBottomCurrent = colorBottomNext;
			colorTopNext = null;
			colorMidNext = null;
			colorBottomNext = null;
			timeUntilColorChange = MathHelper.getRandomIntegerInRange(rand, 1200, 2400);
		}
		if (utumnoCheckTime > 0) {
			--utumnoCheckTime;
		} else {
			double range = 256.0;
			if (LOTRFixedStructures.UTUMNO_ENTRANCE.distanceSqTo(viewer) <= range * range) {
				atUtumno = true;
				timeUntilColorChange = 0;
				colorTopNext = new float[]{1.0f, 0.4f, 0.0f};
				colorMidNext = new float[]{1.0f, 0.0f, 0.0f};
				colorBottomNext = new float[]{1.0f, 0.0f, 0.3f};
				colorChangeTick = colorChangeTime = MathHelper.getRandomIntegerInRange(rand, 100, 200);
			} else {
				atUtumno = false;
			}
			utumnoCheckTime = 200;
		}
		if (atUtumno) {
			if (utumnoChange < 200) {
				++utumnoChange;
			}
		} else if (utumnoChange > 0) {
			--utumnoChange;
		}
		if (rand.nextInt(50) == 0) {
			float freq = MathHelper.randomFloatClamp(rand, 8.0f, 100.0f);
			speed = freq * 5.0E-4f;
			amp = MathHelper.randomFloatClamp(rand, 0.05f, 0.3f);
			cycle = new AuroraCycle(freq, speed, amp);
			cycle.age = cycle.maxAge = MathHelper.getRandomIntegerInRange(rand, 100, 400);
			waveOscillations.add(cycle);
		}
		if (!waveOscillations.isEmpty()) {
			Collection<AuroraCycle> removes = new HashSet<>();
			for (AuroraCycle c : waveOscillations) {
				c.update();
				if (c.age > 0) {
					continue;
				}
				removes.add(c);
			}
			waveOscillations.removeAll(removes);
		}
		if (rand.nextInt(120) == 0) {
			float freq = MathHelper.randomFloatClamp(rand, 30.0f, 150.0f);
			speed = freq * 0.002f;
			amp = MathHelper.randomFloatClamp(rand, 0.05f, 0.5f);
			cycle = new AuroraCycle(freq, speed, amp);
			cycle.age = cycle.maxAge = MathHelper.getRandomIntegerInRange(rand, 100, 400);
			glowOscillations.add(cycle);
		}
		if (rand.nextInt(300) == 0) {
			float freq = MathHelper.randomFloatClamp(rand, 400.0f, 500.0f);
			speed = freq * 0.004f;
			amp = MathHelper.randomFloatClamp(rand, 0.1f, 0.2f);
			cycle = new AuroraCycle(freq, speed, amp);
			cycle.age = cycle.maxAge = MathHelper.getRandomIntegerInRange(rand, 100, 200);
			glowOscillations.add(cycle);
		}
		if (!glowOscillations.isEmpty()) {
			Collection<AuroraCycle> removes = new HashSet<>();
			for (AuroraCycle c : glowOscillations) {
				c.update();
				if (c.age > 0) {
					continue;
				}
				removes.add(c);
			}
			glowOscillations.removeAll(removes);
		}
	}

	public static float waveEquation(Minecraft mc, float t, float tick, float renderTick) {
		float f = 0.0f;
		f += wave0.calc(t, tick);
		for (AuroraCycle c : waveOscillations) {
			f += c.calc(t, tick);
		}
		return f;
	}

	public static class AuroraCycle {
		public float freq;
		public float tickMultiplier;
		public float amp;
		public int age;
		public int maxAge = -1;
		public float ampModifier = 1.0f;

		public AuroraCycle(float f, float t, float a) {
			freq = f;
			tickMultiplier = t;
			amp = a;
		}

		public float calc(float t, float tick) {
			return MathHelper.cos(t * freq + tick * tickMultiplier) * amp * ampModifier;
		}

		public void update() {
			if (age >= 0) {
				--age;
				float a = (float) (maxAge - age) / maxAge;
				ampModifier = Math.min(a, 1.0f - a);
			}
		}
	}

}
