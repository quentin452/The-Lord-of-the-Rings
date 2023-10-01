package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGaladhrimWarden extends LOTRRenderElf {
	public void doElfInvisibility() {
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glAlphaFunc(516, 0.001f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.05f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		if (((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			doElfInvisibility();
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		if (((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			doElfInvisibility();
			return;
		}
		super.renderEquippedItems(entity, f);
		if (((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			undoElfInvisibility();
		}
	}

	@Override
	public void renderNPCCape(LOTREntityNPC entity) {
		if (((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			doElfInvisibility();
		}
		super.renderNPCCape(entity);
		if (((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			undoElfInvisibility();
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		int j = super.shouldRenderPass(entity, pass, f);
		if (j > 0 && ((LOTREntityGaladhrimWarden) entity).isElfSneaking()) {
			doElfInvisibility();
		}
		return j;
	}

	public void undoElfInvisibility() {
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glAlphaFunc(516, 0.1f);
	}
}
