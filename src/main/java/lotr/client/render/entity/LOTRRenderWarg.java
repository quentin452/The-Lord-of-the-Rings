package lotr.client.render.entity;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelWarg;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class LOTRRenderWarg extends RenderLiving {
	public static Map wargSkins = new HashMap();
	public static ResourceLocation wargSaddle = new ResourceLocation("lotr:mob/warg/saddle.png");
	public LOTRModelWarg saddleModel = new LOTRModelWarg(0.5f);
	public LOTRGlowingEyes.Model eyesModel = new LOTRModelWarg(0.05f);

	public LOTRRenderWarg() {
		super(new LOTRModelWarg(), 0.5f);
	}

	public static ResourceLocation getWargSkin(LOTREntityWarg.WargType type) {
		String s = type.textureName();
		ResourceLocation wargSkin = (ResourceLocation) wargSkins.get(s);
		if (wargSkin == null) {
			wargSkin = new ResourceLocation("lotr:mob/warg/" + s + ".png");
			wargSkins.put(s, wargSkin);
		}
		return wargSkin;
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		if (entity instanceof LOTREntityWargBombardier) {
			GL11.glEnable(32826);
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d, (float) d1 + 1.7f, (float) d2);
			GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
			int i = entity.getBrightnessForRender(f1);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			LOTRRenderOrcBomb bombRenderer = (LOTRRenderOrcBomb) RenderManager.instance.getEntityClassRenderObject(LOTREntityOrcBomb.class);
			bombRenderer.renderBomb(entity, 0.0, 0.0, 0.0, f1, ((LOTREntityWargBombardier) entity).getBombFuse(), ((LOTREntityWargBombardier) entity).getBombStrengthLevel(), 0.75f, 1.0f);
			GL11.glPopMatrix();
			GL11.glDisable(32826);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && ((LOTREntityNPC) entity).hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 0.5, d2);
			LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5, d2);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityWarg warg = (LOTREntityWarg) entity;
		ResourceLocation skin = getWargSkin(warg.getWargType());
		return LOTRRenderHorse.getLayeredMountTexture(warg, skin);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		if (LOTRMod.isAprilFools()) {
			GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
		}
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		LOTREntityWarg warg = (LOTREntityWarg) entity;
		ResourceLocation eyes = LOTRTextures.getEyesTexture(getWargSkin(warg.getWargType()), new int[][]{{100, 12}, {108, 12}}, 2, 1);
		LOTRGlowingEyes.renderGlowingEyes(entity, eyes, eyesModel, f, f1, f2, f3, f4, f5);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		LOTRNPCMount warg = (LOTRNPCMount) entity;
		if (pass == 0 && warg.isMountSaddled()) {
			bindTexture(wargSaddle);
			setRenderPassModel(saddleModel);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
