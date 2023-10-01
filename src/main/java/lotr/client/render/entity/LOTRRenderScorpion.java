package lotr.client.render.entity;

import lotr.client.model.LOTRModelScorpion;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.entity.animal.LOTREntityScorpion;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderScorpion extends RenderLiving {
	public static ResourceLocation jungleTexture = new ResourceLocation("lotr:mob/scorpion/jungle.png");
	public static ResourceLocation desertTexture = new ResourceLocation("lotr:mob/scorpion/desert.png");

	public LOTRRenderScorpion() {
		super(new LOTRModelScorpion(), 1.0f);
		setRenderPassModel(new LOTRModelScorpion());
	}

	@Override
	public float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0f;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof LOTREntityDesertScorpion) {
			return desertTexture;
		}
		return jungleTexture;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float strikeTime = ((LOTREntityScorpion) entity).getStrikeTime();
		if (strikeTime > 0.0f) {
			strikeTime -= f;
		}
		return strikeTime / 20.0f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = ((LOTREntityScorpion) entity).getScorpionScaleAmount();
		GL11.glScalef(scale, scale, scale);
	}
}
