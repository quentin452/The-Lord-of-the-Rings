package lotr.client.render.entity;

import lotr.client.model.LOTRModelAurochs;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderAurochs extends RenderLiving {
	public static LOTRRandomSkins aurochsSkins;

	public LOTRRenderAurochs() {
		super(new LOTRModelAurochs(), 0.5f);
		aurochsSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/aurochs");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity aurochs = (LOTRRandomSkinEntity) entity;
		return aurochsSkins.getRandomSkin(aurochs);
	}
}
