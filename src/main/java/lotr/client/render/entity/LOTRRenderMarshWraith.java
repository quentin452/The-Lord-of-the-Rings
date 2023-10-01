package lotr.client.render.entity;

import lotr.client.model.LOTRModelMarshWraith;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMarshWraith extends RenderLiving {
	public static ResourceLocation skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");

	public LOTRRenderMarshWraith() {
		super(new LOTRModelMarshWraith(), 0.5f);
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return skin;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		float f1 = 0.9375f;
		GL11.glScalef(f1, f1, f1);
		LOTREntityMarshWraith wraith = (LOTREntityMarshWraith) entity;
		if (wraith.getSpawnFadeTime() < 30) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getSpawnFadeTime() / 30.0f);
		} else if (wraith.getDeathFadeTime() > 0) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, wraith.getDeathFadeTime() / 30.0f);
		}
	}
}
