package lotr.client.render;

import lotr.client.render.entity.LOTRRandomSkins;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class LOTRSkyRenderer extends IRenderHandler {
	public static ResourceLocation moonTexture = new ResourceLocation("lotr:sky/moon.png");
	public static ResourceLocation sunTexture = new ResourceLocation("lotr:sky/sun.png");
	public static ResourceLocation earendilTexture = new ResourceLocation("lotr:sky/earendil.png");
	public LOTRRandomSkins skyTextures;
	public ResourceLocation currentSkyTexture;
	public int glSkyList;
	public int glSkyList2;

	public LOTRSkyRenderer(LOTRWorldProvider provider) {
		skyTextures = LOTRRandomSkins.loadSkinsList("lotr:sky/night");
		Tessellator tessellator = Tessellator.instance;
		glSkyList = GLAllocation.generateDisplayLists(3);
		GL11.glNewList(glSkyList, 4864);
		byte b2 = 64;
		int i = 256 / b2 + 2;
		float f = 16.0F;
		int j;
		for (j = -b2 * i; j <= b2 * i; j += b2) {
			int k;
			for (k = -b2 * i; k <= b2 * i; k += b2) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j, f, k);
				tessellator.addVertex(j + b2, f, k);
				tessellator.addVertex(j + b2, f, k + b2);
				tessellator.addVertex(j, f, k + b2);
				tessellator.draw();
			}
		}
		GL11.glEndList();
		glSkyList2 = glSkyList + 1;
		GL11.glNewList(glSkyList2, 4864);
		f = -16.0F;
		tessellator.startDrawingQuads();
		for (j = -b2 * i; j <= b2 * i; j += b2) {
			int k;
			for (k = -b2 * i; k <= b2 * i; k += b2) {
				tessellator.addVertex(j + b2, f, k);
				tessellator.addVertex(j, f, k);
				tessellator.addVertex(j, f, k + b2);
				tessellator.addVertex(j + b2, f, k + b2);
			}
		}
		tessellator.draw();
		GL11.glEndList();
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		world.theProfiler.startSection("lotrSky");
		boolean renderSkyFeatures = world.provider.isSurfaceWorld();
		int i = MathHelper.floor_double(mc.renderViewEntity.posX);
		int k = MathHelper.floor_double(mc.renderViewEntity.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiome && renderSkyFeatures) {
			renderSkyFeatures = ((LOTRBiome) biome).hasSky();
		}
		GL11.glDisable(3553);
		Vec3 skyColor = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float skyR = (float) skyColor.xCoord;
		float skyG = (float) skyColor.yCoord;
		float skyB = (float) skyColor.zCoord;
		if (mc.gameSettings.anaglyph) {
			float newSkyR = (skyR * 30.0F + skyG * 59.0F + skyB * 11.0F) / 100.0F;
			float newSkyG = (skyR * 30.0F + skyG * 70.0F) / 100.0F;
			float newSkyB = (skyR * 30.0F + skyB * 70.0F) / 100.0F;
			skyR = newSkyR;
			skyG = newSkyG;
			skyB = newSkyB;
		}
		GL11.glColor3f(skyR, skyG, skyB);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(2912);
		GL11.glColor3f(skyR, skyG, skyB);
		GL11.glCallList(glSkyList);
		GL11.glDisable(2912);
		GL11.glDisable(3008);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		RenderHelper.disableStandardItemLighting();
		float[] sunrise = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		if (sunrise != null) {
			GL11.glDisable(3553);
			GL11.glShadeModel(7425);
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			float r = sunrise[0];
			float g = sunrise[1];
			g *= 1.2F;
			float b = sunrise[2];
			if (mc.gameSettings.anaglyph) {
				float r1 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
				float g1 = (r * 30.0F + g * 70.0F) / 100.0F;
				float b1 = (r * 30.0F + b * 70.0F) / 100.0F;
				r = r1;
				g = g1;
				b = b1;
			}
			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(r, g, b, sunrise[3]);
			tessellator.addVertex(0.0D, 100.0D, 0.0D);
			tessellator.setColorRGBA_F(sunrise[0], sunrise[1], sunrise[2], 0.0F);
			int passes = 16;
			for (int l = 0; l <= passes; l++) {
				float angle = l * 3.1415927F * 2.0F / passes;
				float sin = MathHelper.sin(angle);
				float cos = MathHelper.cos(angle);
				tessellator.addVertex(sin * 120.0F, cos * 120.0F, -cos * 40.0F * sunrise[3]);
			}
			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(7424);
		}
		GL11.glPushMatrix();
		if (renderSkyFeatures) {
			GL11.glEnable(3553);
			GL11.glBlendFunc(770, 1);
			float rainBrightness = 1.0F - world.getRainStrength(partialTicks);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
			float x = 0.0F;
			float y = 0.0F;
			float z = 0.0F;
			GL11.glTranslatef(x, y, z);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			float starBrightness = world.getStarBrightness(partialTicks) * rainBrightness;
			if (starBrightness > 0.0F) {
				if (currentSkyTexture == null) {
					currentSkyTexture = skyTextures.getRandomSkin();
				}
				mc.renderEngine.bindTexture(currentSkyTexture);
				GL11.glPushMatrix();
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightness);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 4);
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				renderSkyboxSide(tessellator, 1);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				renderSkyboxSide(tessellator, 0);
				GL11.glPopMatrix();
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 5);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 2);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 3);
				GL11.glPopMatrix();
			} else {
				currentSkyTexture = null;
			}
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glBlendFunc(770, 771);
			mc.renderEngine.bindTexture(sunTexture);
			float rSun = 10.0F;
			for (int pass = 0; pass <= 1; pass++) {
				if (pass == 0) {
					GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
				} else {
					if (sunrise == null) {
						continue;
					}
					float sunriseBlend = sunrise[3];
					sunriseBlend *= 0.5F;
					GL11.glColor4f(1.0F, 0.9F, 0.2F, sunriseBlend * rainBrightness);
				}
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-rSun, 100.0D, -rSun, 0.0D, 0.0D);
				tessellator.addVertexWithUV(rSun, 100.0D, -rSun, 1.0D, 0.0D);
				tessellator.addVertexWithUV(rSun, 100.0D, rSun, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-rSun, 100.0D, rSun, 0.0D, 1.0D);
				tessellator.draw();
			}
			GL11.glBlendFunc(770, 1);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
			int phases = LOTRWorldProvider.MOON_PHASES;
			int moonPhase = LOTRWorldProvider.getLOTRMoonPhase();
			boolean lunarEclipse = LOTRWorldProvider.isLunarEclipse();
			if (lunarEclipse) {
				GL11.glColor3f(1.0F, 0.6F, 0.4F);
			}
			mc.renderEngine.bindTexture(moonTexture);
			float rMoon = 10.0F;
			float f14 = (float) moonPhase / phases;
			float f15 = 0.0F;
			float f16 = (float) (moonPhase + 1) / phases;
			float f17 = 1.0F;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-rMoon, -100.0D, rMoon, f16, f17);
			tessellator.addVertexWithUV(rMoon, -100.0D, rMoon, f14, f17);
			tessellator.addVertexWithUV(rMoon, -100.0D, -rMoon, f14, f15);
			tessellator.addVertexWithUV(-rMoon, -100.0D, -rMoon, f16, f15);
			tessellator.draw();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			float celestialAngle = world.getCelestialAngle(partialTicks);
			float f0 = celestialAngle - 0.5F;
			float f1 = Math.abs(f0);
			float eMin = 0.15F;
			float eMax = 0.3F;
			if (f1 >= eMin && f1 <= eMax) {
				float eMid = (eMin + eMax) / 2.0F;
				float eHalfWidth = eMax - eMid;
				float eBright = MathHelper.cos((f1 - eMid) / eHalfWidth * 3.1415927F / 2.0F);
				eBright *= eBright;
				float eAngle = Math.signum(f0) * 18.0F;
				GL11.glPushMatrix();
				GL11.glRotatef(eAngle, 1.0F, 0.0F, 0.0F);
				GL11.glBlendFunc(770, 1);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, eBright * rainBrightness);
				mc.renderEngine.bindTexture(earendilTexture);
				float rEarendil = 1.5F;
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-rEarendil, 100.0D, -rEarendil, 0.0D, 0.0D);
				tessellator.addVertexWithUV(rEarendil, 100.0D, -rEarendil, 1.0D, 0.0D);
				tessellator.addVertexWithUV(rEarendil, 100.0D, rEarendil, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-rEarendil, 100.0D, rEarendil, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glPopMatrix();
			}
			GL11.glDisable(3553);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);
		GL11.glEnable(3008);
		GL11.glEnable(2912);
		GL11.glPopMatrix();
		GL11.glDisable(3553);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();
		if (d0 < 0.0D) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(glSkyList2);
			GL11.glPopMatrix();
			float f8 = 1.0F;
			float f9 = -((float) (d0 + 65.0D));
			float f10 = -f8;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.draw();
		}
		if (world.provider.isSkyColored()) {
			GL11.glColor3f(skyR * 0.2F + 0.04F, skyG * 0.2F + 0.04F, skyB * 0.6F + 0.1F);
		} else {
			GL11.glColor3f(skyR, skyG, skyB);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
		GL11.glCallList(glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDepthMask(true);
		world.theProfiler.endSection();
	}

	public void renderSkyboxSide(Tessellator tessellator, int side) {
		double u = side % 3 / 3.0D;
		double v = (double) side / 3 / 2.0D;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, u, v);
		tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, u, v + 0.5D);
		tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, u + 0.3333333333333333D, v + 0.5D);
		tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, u + 0.3333333333333333D, v);
		tessellator.draw();
	}
}