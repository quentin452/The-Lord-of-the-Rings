package lotr.client.render.entity;

import lotr.client.model.LOTRModelDikDik;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDikDik extends RenderLiving {
	public static LOTRRandomSkins skins;

	public LOTRRenderDikDik() {
		super(new LOTRModelDikDik(), 0.8f);
		skins = LOTRRandomSkins.loadSkinsList("lotr:mob/dikdik");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity dikdik = (LOTRRandomSkinEntity) entity;
		return skins.getRandomSkin(dikdik);
	}
}
