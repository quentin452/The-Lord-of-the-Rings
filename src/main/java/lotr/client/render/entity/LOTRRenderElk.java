package lotr.client.render.entity;

import lotr.client.model.LOTRModelElk;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElk extends RenderLiving {
	public static LOTRRandomSkins elkSkins;
	public static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/elk/saddle.png");

	public LOTRRenderElk() {
		super(new LOTRModelElk(), 0.5f);
		setRenderPassModel(new LOTRModelElk(0.5f));
		elkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elk/elk");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityElk elk = (LOTREntityElk) entity;
		ResourceLocation elkSkin = elkSkins.getRandomSkin(elk);
		return LOTRRenderHorse.getLayeredMountTexture(elk, elkSkin);
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
