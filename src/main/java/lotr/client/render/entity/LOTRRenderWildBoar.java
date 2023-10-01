package lotr.client.render.entity;

import lotr.client.model.LOTRModelBoar;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWildBoar extends RenderLiving {
	public static ResourceLocation boarSkin = new ResourceLocation("lotr:mob/boar/boar.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/boar/saddle.png");

	public LOTRRenderWildBoar() {
		super(new LOTRModelBoar(), 0.7f);
		setRenderPassModel(new LOTRModelBoar(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRNPCMount boar = (LOTRNPCMount) entity;
		return LOTRRenderHorse.getLayeredMountTexture(boar, boarSkin);
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
