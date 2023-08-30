package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBreeRuffian;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBreeRuffian extends LOTRRenderBreeMan {
	public static LOTRRandomSkins skinsRuffian;
	public static LOTRRandomSkins hoodsRuffian;

	public LOTRRenderBreeRuffian() {
		skinsRuffian = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/ruffian");
		hoodsRuffian = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/ruffian_hood");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity ruffian = (LOTRRandomSkinEntity) entity;
		return skinsRuffian.getRandomSkin(ruffian);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityBreeRuffian ruffian = (LOTREntityBreeRuffian) entity;
		if (pass == 0 && ruffian.getEquipmentInSlot(4) == null && LOTRRandomSkins.nextInt(ruffian, 3) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(hoodsRuffian.getRandomSkin(ruffian));
			return 1;
		}
		return super.shouldRenderPass(ruffian, pass, f);
	}
}
