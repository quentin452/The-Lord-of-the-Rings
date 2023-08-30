package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSnowTroll extends LOTRRenderTroll {
	public static LOTRRandomSkins snowTrollSkins;

	public LOTRRenderSnowTroll() {
		snowTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/snowTroll");
	}

	@Override
	public void bindTrollOutfitTexture(EntityLivingBase entity) {
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return snowTrollSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void renderTrollWeapon(EntityLivingBase entity, float f) {
		LOTREntitySnowTroll troll = (LOTREntitySnowTroll) entity;
		if (!troll.isThrowingSnow()) {
			((LOTRModelTroll) mainModel).renderWoodenClubWithSpikes(0.0625f);
		}
	}
}
