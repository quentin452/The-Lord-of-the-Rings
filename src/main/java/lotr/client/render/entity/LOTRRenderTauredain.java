package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderTauredain extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public static LOTRRandomSkins outfits;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderTauredain() {
		super(new LOTRModelHuman(), 0.5f);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/tauredain_female");
		outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/outfit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityTauredain tauredain = (LOTREntityTauredain) entity;
		if (tauredain.familyInfo.isMale()) {
			return skinsMale.getRandomSkin(tauredain);
		}
		return skinsFemale.getRandomSkin(tauredain);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityTauredain tauredain = (LOTREntityTauredain) entity;
		if (pass == 1 && tauredain.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(tauredain, 3) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(outfits.getRandomSkin(tauredain));
			return 1;
		}
		return super.shouldRenderPass(tauredain, pass, f);
	}
}
