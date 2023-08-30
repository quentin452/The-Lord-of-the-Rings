package lotr.client.render.entity;

import lotr.client.model.LOTRModelDeer;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDeer extends RenderLiving {
	public static LOTRRandomSkins deerSkins;

	public LOTRRenderDeer() {
		super(new LOTRModelDeer(), 0.5f);
		deerSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/deer");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity deer = (LOTRRandomSkinEntity) entity;
		return deerSkins.getRandomSkin(deer);
	}
}
