package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSwanKnight extends LOTRRenderBiped {
	public static LOTRRandomSkins dolAmrothSkins;

	public LOTRRenderSwanKnight() {
		super(new LOTRModelHuman(), 0.5f);
		dolAmrothSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/gondor/swanKnight");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return dolAmrothSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}
}
