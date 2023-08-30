package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMirkTroll extends LOTRRenderTroll {
	public static LOTRRandomSkins mirkSkins;
	public static LOTRRandomSkins mirkArmorSkins;

	public LOTRRenderMirkTroll() {
		mirkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll");
		mirkArmorSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mirkTroll_armor");
	}

	@Override
	public void bindTrollOutfitTexture(EntityLivingBase entity) {
		bindTexture(mirkArmorSkins.getRandomSkin((LOTRRandomSkinEntity) entity));
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return mirkSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void renderTrollWeapon(EntityLivingBase entity, float f) {
		((LOTRModelTroll) mainModel).renderBattleaxe(0.0625f);
	}
}
