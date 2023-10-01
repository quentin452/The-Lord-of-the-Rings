package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderOlogHai extends LOTRRenderTroll {
	public static LOTRRandomSkins ologSkins;
	public static LOTRRandomSkins ologArmorSkins;

	public LOTRRenderOlogHai() {
		ologSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai");
		ologArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/ologHai_armor");
	}

	@Override
	public void bindTrollOutfitTexture(EntityLivingBase entity) {
		bindTexture(ologArmorSkins.getRandomSkin((LOTRRandomSkinEntity) entity));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return ologSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void renderTrollWeapon(EntityLivingBase entity, float f) {
		((LOTRModelTroll) mainModel).renderWarhammer(0.0625f);
	}
}
