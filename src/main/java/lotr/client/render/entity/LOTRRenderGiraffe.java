package lotr.client.render.entity;

import lotr.client.model.LOTRModelGiraffe;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGiraffe extends RenderLiving {
	public static ResourceLocation texture = new ResourceLocation("lotr:mob/giraffe/giraffe.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/giraffe/saddle.png");

	public LOTRRenderGiraffe() {
		super(new LOTRModelGiraffe(0.0f), 0.5f);
		setRenderPassModel(new LOTRModelGiraffe(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		if (pass == 0 && ((LOTRNPCMount) entity).isMountSaddled()) {
			bindTexture(saddleTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
