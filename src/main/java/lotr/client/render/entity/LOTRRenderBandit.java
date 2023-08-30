package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBandit extends LOTRRenderBiped {
	public LOTRRandomSkins banditSkins;

	public LOTRRenderBandit(String s) {
		super(new LOTRModelHuman(), 0.5f);
		banditSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/bandit/" + s);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return banditSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}
}
