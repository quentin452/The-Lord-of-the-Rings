package lotr.client.render.entity;

import lotr.client.LOTRTickHandlerClient;
import lotr.client.model.LOTRModelBarrowWight;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBarrowWight extends LOTRRenderBiped {
	public static LOTRRandomSkins wightSkins;

	public LOTRRenderBarrowWight() {
		super(new LOTRModelBarrowWight(), 0.0f);
		wightSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/barrowWight/wight");
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EntityLivingBase viewer;
		super.doRender(entity, d, d1, d2, f, f1);
		LOTREntityBarrowWight wight = (LOTREntityBarrowWight) entity;
		if (wight.addedToChunk && (viewer = Minecraft.getMinecraft().renderViewEntity) != null && wight.getTargetEntityID() == viewer.getEntityId()) {
			LOTRTickHandlerClient.anyWightsViewed = true;
		}
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity wight = (LOTRRandomSkinEntity) entity;
		return wightSkins.getRandomSkin(wight);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		float hover = MathHelper.sin((entity.ticksExisted + f) * 0.05f) * 0.2f;
		GL11.glTranslatef(0.0f, hover, 0.0f);
		if (entity.deathTime > 0) {
			float death = (entity.deathTime + f - 1.0f) / 20.0f;
			death = Math.max(0.0f, death);
			death = Math.min(1.0f, death);
			float scale = 1.0f + death;
			GL11.glScalef(scale, scale, scale);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - death);
		}
	}
}
