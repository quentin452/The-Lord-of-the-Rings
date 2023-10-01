package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuorn;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHuorn extends RenderLiving {
	public static LOTRRandomSkins faceSkins;

	public LOTRRenderHuorn() {
		super(new LOTRModelHuorn(), 0.0f);
		faceSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/huorn/face");
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityHuornBase huorn = (LOTREntityHuornBase) entity;
		if (huorn.ignoringFrustumForRender) {
			huorn.ignoringFrustumForRender = false;
			huorn.ignoreFrustumCheck = false;
		}
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && huorn.hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 3.5, d2);
			LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 3.5, d2);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity huorn = (LOTRRandomSkinEntity) entity;
		return faceSkins.getRandomSkin(huorn);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		return f;
	}

	@Override
	public void renderLivingAt(EntityLivingBase entity, double d, double d1, double d2) {
		LOTREntityHuornBase huorn = (LOTREntityHuornBase) entity;
		if (!huorn.isHuornActive()) {
			int i = MathHelper.floor_double(huorn.posX);
			int j = MathHelper.floor_double(huorn.posY);
			int k = MathHelper.floor_double(huorn.posZ);
			d = i + 0.5 - RenderManager.renderPosX;
			d1 = j - RenderManager.renderPosY;
			d2 = k + 0.5 - RenderManager.renderPosZ;
		}
		super.renderLivingAt(entity, d, d1 - 0.0078125, d2);
		huorn.hurtTime = 0;
	}

	@Override
	public void rotateCorpse(EntityLivingBase entity, float f, float f1, float f2) {
		LOTREntityHuornBase huorn = (LOTREntityHuornBase) entity;
		if (!huorn.isHuornActive()) {
			f1 = 0.0f;
		}
		super.rotateCorpse(entity, f, f1, f2);
	}
}
