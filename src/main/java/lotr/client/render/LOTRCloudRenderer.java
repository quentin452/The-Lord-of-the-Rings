package lotr.client.render;

import java.util.Random;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.Project;

import com.google.common.math.IntMath;

import lotr.client.LOTRReflectionClient;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraftforge.client.IRenderHandler;

public class LOTRCloudRenderer extends IRenderHandler {
	public static ResourceLocation cloudTexture = new ResourceLocation("lotr:sky/clouds.png");
	public static Random cloudRand = new Random();
	public static CloudProperty cloudOpacity = new CloudProperty(233591206262L, 0.1F, 1.0F, 0.001F);
	public static CloudProperty cloudSpeed = new CloudProperty(6283905602629L, 0.0F, 0.5F, 0.001F);
	public static CloudProperty cloudAngle = new CloudProperty(360360635650636L, 0.0F, 6.2831855F, 0.01F);
	public static double cloudPosXPre;
	public static double cloudPosX;
	public static double cloudPosZPre;
	public static double cloudPosZ;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		world.theProfiler.startSection("lotrClouds");
		if (world.provider.isSurfaceWorld()) {
			Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(world, mc.renderViewEntity, partialTicks);
			if (block.getMaterial() == Material.water) {
				return;
			}
			int cloudRange = LOTRConfig.cloudRange;
			GL11.glMatrixMode(5889);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			float fov = LOTRReflectionClient.getFOVModifier(mc.entityRenderer, partialTicks, true);
			Project.gluPerspective(fov, (float) mc.displayWidth / mc.displayHeight, 0.05F, cloudRange);
			GL11.glMatrixMode(5888);
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glDepthMask(false);
			GL11.glEnable(3008);
			float alphaFunc = GL11.glGetFloat(3010);
			GL11.glAlphaFunc(516, 0.01F);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glFogi(2917, 9729);
			GL11.glFogf(2915, cloudRange * 0.9F);
			GL11.glFogf(2916, cloudRange);
			if (GLContext.getCapabilities().GL_NV_fog_distance) {
				GL11.glFogi(34138, 34139);
			}
			Tessellator tessellator = Tessellator.instance;
			mc.renderEngine.bindTexture(cloudTexture);
			Vec3 cloudColor = world.getCloudColour(partialTicks);
			float r = (float) cloudColor.xCoord;
			float g = (float) cloudColor.yCoord;
			float b = (float) cloudColor.zCoord;
			if (mc.gameSettings.anaglyph) {
				float r1 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
				float g1 = (r * 30.0F + g * 70.0F) / 100.0F;
				float b1 = (r * 30.0F + b * 70.0F) / 100.0F;
				r = r1;
				g = g1;
				b = b1;
			}
			Vec3 pos = mc.renderViewEntity.getPosition(partialTicks);
			for (int pass = 0; pass < 2; pass++) {
				int scale = 4096 * IntMath.pow(2, pass);
				double invScaleD = 1.0D / scale;
				double posX = pos.xCoord;
				double posZ = pos.zCoord;
				double posY = pos.yCoord;
				double cloudPosXAdd = cloudPosXPre + (cloudPosX - cloudPosXPre) * partialTicks;
				double cloudPosZAdd = cloudPosZPre + (cloudPosZ - cloudPosZPre) * partialTicks;
				cloudPosXAdd /= pass + 1;
				cloudPosZAdd /= pass + 1;
				posX += cloudPosXAdd;
				posZ += cloudPosZAdd;
				int x = MathHelper.floor_double(posX / scale);
				int z = MathHelper.floor_double(posZ / scale);
				double cloudX = posX - x * scale;
				double cloudZ = posZ - z * scale;
				double cloudY = world.provider.getCloudHeight() - posY + 0.33000001311302185D + pass * 50.0F;
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(r, g, b, (0.8F - pass * 0.5F) * cloudOpacity.getValue(partialTicks));
				int i;
				for (i = -cloudRange; i < cloudRange; i += cloudRange) {
					int k;
					for (k = -cloudRange; k < cloudRange; k += cloudRange) {
						int xMax = i + cloudRange;
						int zMax = k + cloudRange;
						double uMin = (i + cloudX) * invScaleD;
						double uMax = (xMax + cloudX) * invScaleD;
						double vMin = (k + cloudZ) * invScaleD;
						double vMax = (zMax + cloudZ) * invScaleD;
						tessellator.addVertexWithUV(i, cloudY, zMax, uMin, vMax);
						tessellator.addVertexWithUV(xMax, cloudY, zMax, uMax, vMax);
						tessellator.addVertexWithUV(xMax, cloudY, k, uMax, vMin);
						tessellator.addVertexWithUV(i, cloudY, k, uMin, vMin);
					}
				}
				tessellator.draw();
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(2884);
			GL11.glDepthMask(true);
			GL11.glAlphaFunc(516, alphaFunc);
			GL11.glDisable(3042);
			GL11.glMatrixMode(5889);
			GL11.glPopMatrix();
			GL11.glMatrixMode(5888);
			GL11.glPopMatrix();
		}
		world.theProfiler.endSection();
	}

	public static void resetClouds() {
		cloudOpacity.reset();
		cloudSpeed.reset();
		cloudAngle.reset();
	}

	public static void updateClouds(WorldClient world) {
		cloudOpacity.update(world);
		cloudSpeed.update(world);
		cloudAngle.update(world);
		float angle = cloudAngle.getValue(1.0F);
		float speed = cloudSpeed.getValue(1.0F);
		cloudPosXPre = cloudPosX;
		cloudPosX += MathHelper.cos(angle) * speed;
		cloudPosZPre = cloudPosZ;
		cloudPosZ += MathHelper.sin(angle) * speed;
	}

	public static class CloudProperty {
		public long baseSeed;
		public float value;
		public float prevValue;
		public float minValue;
		public float maxValue;
		public float interval;

		public CloudProperty(long l, float min, float max, float i) {
			baseSeed = l;
			value = -1.0F;
			minValue = min;
			maxValue = max;
			interval = i;
		}

		public float getCurrentDayValue(WorldClient world) {
			int day = LOTRDate.ShireReckoning.currentDay;
			long seed = day * baseSeed + day + 83025820626792L;
			cloudRand.setSeed(seed);
			return MathHelper.randomFloatClamp(cloudRand, minValue, maxValue);
		}

		public float getValue(float f) {
			return prevValue + (value - prevValue) * f;
		}

		public void reset() {
			value = -1.0F;
		}

		public void update(WorldClient world) {
			float currentDayValue = getCurrentDayValue(world);
			if (value == -1.0F) {
				prevValue = value = currentDayValue;
			} else {
				prevValue = value;
				if (value > currentDayValue) {
					value -= interval;
					value = Math.max(value, currentDayValue);
				} else if (value < currentDayValue) {
					value += interval;
					value = Math.min(value, currentDayValue);
				}
			}
		}
	}
}