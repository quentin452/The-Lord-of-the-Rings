package lotr.client.render.entity;

import lotr.client.model.LOTRModelRhino;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRhino extends RenderLiving {
	public static ResourceLocation rhinoTexture = new ResourceLocation("lotr:mob/rhino/rhino.png");
	public static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/rhino/saddle.png");

	public LOTRRenderRhino() {
		super(new LOTRModelRhino(), 0.5f);
		setRenderPassModel(new LOTRModelRhino(0.5f));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRNPCMount rhino = (LOTRNPCMount) entity;
		return LOTRRenderHorse.getLayeredMountTexture(rhino, rhinoTexture);
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
