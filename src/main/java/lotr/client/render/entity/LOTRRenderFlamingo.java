package lotr.client.render.entity;

import lotr.client.model.LOTRModelFlamingo;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class LOTRRenderFlamingo extends RenderLiving {
	public static ResourceLocation texture = new ResourceLocation("lotr:mob/flamingo/flamingo.png");
	public static ResourceLocation textureChick = new ResourceLocation("lotr:mob/flamingo/chick.png");

	public LOTRRenderFlamingo() {
		super(new LOTRModelFlamingo(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return ((EntityLivingBase) entity).isChild() ? textureChick : texture;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		LOTREntityFlamingo flamingo = (LOTREntityFlamingo) entity;
		float f1 = flamingo.field_756_e + (flamingo.field_752_b - flamingo.field_756_e) * f;
		float f2 = flamingo.field_757_d + (flamingo.destPos - flamingo.field_757_d) * f;
		return (MathHelper.sin(f1) + 1.0f) * f2;
	}
}
