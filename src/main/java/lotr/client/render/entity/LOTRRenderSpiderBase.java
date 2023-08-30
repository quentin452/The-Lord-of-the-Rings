package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNPC;
import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelSpider;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public abstract class LOTRRenderSpiderBase extends RenderLiving {
	public LOTRGlowingEyes.Model eyesModel = new LOTRModelSpider(0.55f);

	protected LOTRRenderSpiderBase() {
		super(new LOTRModelSpider(0.5f), 1.0f);
		setRenderPassModel(new LOTRModelSpider(0.5f));
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && ((LOTREntityNPC) entity).hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 0.5, d2);
			LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5, d2);
		}
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = ((LOTREntitySpiderBase) entity).getSpiderScaleAmount();
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		ResourceLocation eyes1 = LOTRTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{39, 10}, {42, 11}, {44, 11}, {47, 10}}, 2, 2);
		ResourceLocation eyes2 = LOTRTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{41, 8}, {42, 9}, {45, 9}, {46, 8}}, 1, 1);
		LOTRGlowingEyes.renderGlowingEyes(entity, eyes1, eyesModel, f, f1, f2, f3, f4, f5);
		LOTRGlowingEyes.renderGlowingEyes(entity, eyes2, eyesModel, f, f1, f2, f3, f4, f5);
	}
}
