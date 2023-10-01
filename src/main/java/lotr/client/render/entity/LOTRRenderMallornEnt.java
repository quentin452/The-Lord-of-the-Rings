package lotr.client.render.entity;

import lotr.client.model.LOTRModelEnt;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMallornEnt extends LOTRRenderEnt {
	public static ResourceLocation mallornEntSkin = new ResourceLocation("lotr:mob/ent/mallorn.png");
	public static ResourceLocation shieldSkin = new ResourceLocation("lotr:mob/ent/mallornEnt_shield.png");
	public LOTRModelEnt shieldModel = new LOTRModelEnt();

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		LOTREntityMallornEnt ent = (LOTREntityMallornEnt) entity;
		if (ent.addedToChunk) {
			BossStatus.setBossStatus(ent, false);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return mallornEntSkin;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		LOTREntityMallornEnt ent = (LOTREntityMallornEnt) entity;
		float scale = LOTREntityMallornEnt.BOSS_SCALE;
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(0.0f, -ent.getSpawningOffset(f), 0.0f);
		if (ent.isWeaponShieldActive()) {
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		LOTREntityMallornEnt ent = (LOTREntityMallornEnt) entity;
		if (ent.isWeaponShieldActive()) {
			if (pass == 1) {
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f1 = ent.ticksExisted + f;
				float f2 = f1 * 0.01f;
				float f3 = f1 * 0.01f;
				GL11.glTranslatef(f2, f3, 0.0f);
				GL11.glMatrixMode(5888);
				GL11.glAlphaFunc(516, 0.01f);
				GL11.glEnable(3042);
				GL11.glBlendFunc(1, 1);
				float alpha = 0.3f + MathHelper.sin(f1 * 0.05f) * 0.15f;
				GL11.glColor4f(alpha, alpha, alpha, 1.0f);
				GL11.glDisable(2896);
				GL11.glDepthMask(false);
				setRenderPassModel(shieldModel);
				bindTexture(shieldSkin);
				return 1;
			}
			if (pass == 2) {
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(5888);
				GL11.glAlphaFunc(516, 0.1f);
				GL11.glDisable(3042);
				GL11.glEnable(2896);
				GL11.glDepthMask(true);
			}
		}
		return -1;
	}
}
