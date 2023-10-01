package lotr.client.render.entity;

import lotr.client.model.LOTRModelCrocodile;
import lotr.common.entity.animal.LOTREntityCrocodile;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderCrocodile extends RenderLiving {
	public static ResourceLocation texture = new ResourceLocation("lotr:mob/crocodile.png");

	public LOTRRenderCrocodile() {
		super(new LOTRModelCrocodile(), 0.75f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		float snapTime = ((LOTREntityCrocodile) entity).getSnapTime();
		if (snapTime > 0.0f) {
			snapTime -= f;
		}
		return snapTime / 20.0f;
	}
}
