package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRanger extends LOTRRenderDunedain {
	public static LOTRRandomSkins ithilienSkins;

	public LOTRRenderRanger() {
		ithilienSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/ranger");
	}

	public void doRangerInvisibility() {
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glAlphaFunc(516, 0.001f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity ranger = (LOTRRandomSkinEntity) entity;
		if (ranger instanceof LOTREntityRangerIthilien) {
			return ithilienSkins.getRandomSkin(ranger);
		}
		return super.getEntityTexture(entity);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		if (((LOTREntityRanger) entity).isRangerSneaking()) {
			doRangerInvisibility();
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		if (((LOTREntityRanger) entity).isRangerSneaking()) {
			doRangerInvisibility();
		}
		super.renderEquippedItems(entity, f);
		if (((LOTREntityRanger) entity).isRangerSneaking()) {
			undoRangerInvisibility();
		}
	}

	@Override
	public void renderNPCCape(LOTREntityNPC entity) {
		if (((LOTREntityRanger) entity).isRangerSneaking()) {
			doRangerInvisibility();
		}
		super.renderNPCCape(entity);
		if (((LOTREntityRanger) entity).isRangerSneaking()) {
			undoRangerInvisibility();
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		int i = super.shouldRenderPass(entity, pass, f);
		if (i > 0 && ((LOTREntityRanger) entity).isRangerSneaking()) {
			doRangerInvisibility();
		}
		return i;
	}

	public void undoRangerInvisibility() {
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glAlphaFunc(516, 0.1f);
	}
}
