package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.entity.npc.LOTREntityDunlendingBartender;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunlending extends LOTRRenderDunlendingBase {
	public static LOTRRandomSkins dunlendingOutfits;
	public static ResourceLocation outfitApron = new ResourceLocation("lotr:mob/dunland/bartender_apron.png");

	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderDunlending() {
		setRenderPassModel(outfitModel);
		dunlendingOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dunland/outfit");
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityDunlending dunlending = (LOTREntityDunlending) entity;
		if (pass == 1 && dunlending.getEquipmentInSlot(3) == null) {
			setRenderPassModel(outfitModel);
			if (dunlending instanceof LOTREntityDunlendingBartender) {
				bindTexture(outfitApron);
			} else {
				bindTexture(dunlendingOutfits.getRandomSkin(dunlending));
			}
			return 1;
		}
		return super.shouldRenderPass(dunlending, pass, f);
	}
}
