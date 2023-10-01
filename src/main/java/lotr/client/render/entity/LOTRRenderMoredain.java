package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityMoredain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderMoredain extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public static LOTRRandomSkins outfits;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderMoredain() {
		super(new LOTRModelHuman(), 0.5f);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/moredain_female");
		outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/moredain/outfit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityMoredain moredain = (LOTREntityMoredain) entity;
		if (moredain.familyInfo.isMale()) {
			return skinsMale.getRandomSkin(moredain);
		}
		return skinsFemale.getRandomSkin(moredain);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityMoredain moredain = (LOTREntityMoredain) entity;
		if (pass == 1 && moredain.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(moredain, 3) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(outfits.getRandomSkin(moredain));
			return 1;
		}
		return super.shouldRenderPass(moredain, pass, f);
	}
}
