package lotr.client.render.entity;

import lotr.client.model.LOTRModelBanner;
import lotr.common.LOTRConfig;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;
import java.util.Map;

public class LOTRRenderBanner extends Render {
	public static Map<LOTRItemBanner.BannerType, ResourceLocation> bannerTextures = new EnumMap<>(LOTRItemBanner.BannerType.class);
	public static ResourceLocation standTexture = new ResourceLocation("lotr:item/banner/stand.png");
	public static LOTRModelBanner model = new LOTRModelBanner();
	public static ICamera bannerFrustum = new Frustrum();

	public static ResourceLocation getBannerTexture(LOTRItemBanner.BannerType type) {
		ResourceLocation r = bannerTextures.get(type);
		if (r == null) {
			r = new ResourceLocation("lotr:item/banner/banner_" + type.bannerName + ".png");
			bannerTextures.put(type, r);
		}
		return r;
	}

	public static ResourceLocation getStandTexture(LOTRItemBanner.BannerType type) {
		return standTexture;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		int ly;
		int lx;
		int light;
		LOTREntityBanner banner = (LOTREntityBanner) entity;
		Minecraft mc = Minecraft.getMinecraft();
		boolean debug = mc.gameSettings.showDebugInfo;
		boolean protecting = banner.isProtectingTerritory();
		boolean renderBox = debug && protecting;
		boolean seeThroughWalls = renderBox && LOTRConfig.showPermittedBannerSilhouettes && (mc.thePlayer.capabilities.isCreativeMode || banner.clientside_playerHasPermissionInSurvival());
		int protectColor = 65280;
		bannerFrustum.setPosition(d + RenderManager.renderPosX, d1 + RenderManager.renderPosY, d2 + RenderManager.renderPosZ);
		if (bannerFrustum.isBoundingBoxInFrustum(banner.boundingBox)) {
			GL11.glPushMatrix();
			GL11.glDisable(2884);
			GL11.glTranslatef((float) d, (float) d1 + 1.5f, (float) d2);
			GL11.glScalef(-1.0f, -1.0f, 1.0f);
			GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(0.0f, 0.01f, 0.0f);
			if (seeThroughWalls) {
				GL11.glDisable(2929);
				GL11.glDisable(3553);
				GL11.glDisable(2896);
				light = 15728880;
				lx = light % 65536;
				ly = light / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
				GL11.glColor4f((0) / 255.0f, (protectColor >> 8 & 0xFF) / 255.0f, (0) / 255.0f, 1.0f);
			}
			bindTexture(getStandTexture(entity));
			model.renderStand(0.0625f);
			model.renderPost(0.0625f);
			bindTexture(getBannerTexture(entity));
			model.renderBanner(0.0625f);
			if (seeThroughWalls) {
				GL11.glEnable(2929);
				GL11.glEnable(3553);
				GL11.glEnable(2896);
			}
			GL11.glEnable(2884);
			GL11.glPopMatrix();
		}
		if (renderBox) {
			GL11.glPushMatrix();
			GL11.glDepthMask(false);
			GL11.glDisable(3553);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			light = 15728880;
			lx = light % 65536;
			ly = light / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(2896);
			AxisAlignedBB aabb = banner.createProtectionCube().offset(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			RenderGlobal.drawOutlinedBoundingBox(aabb, protectColor);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glEnable(2896);
			GL11.glEnable(3553);
			GL11.glEnable(2884);
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}

	public ResourceLocation getBannerTexture(Entity entity) {
		LOTREntityBanner banner = (LOTREntityBanner) entity;
		return getBannerTexture(banner.getBannerType());
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return getStandTexture(entity);
	}

	public ResourceLocation getStandTexture(Entity entity) {
		LOTREntityBanner banner = (LOTREntityBanner) entity;
		return getStandTexture(banner.getBannerType());
	}
}
