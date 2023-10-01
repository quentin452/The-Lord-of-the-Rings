package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityBreeMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBreeMan extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public static LOTRRandomSkins headwearFemale;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderBreeMan() {
		super(new LOTRModelHuman(), 0.5f);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/bree_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/bree_female");
		headwearFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/bree/headwear_female");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityBreeMan man = (LOTREntityBreeMan) entity;
		if (man.familyInfo.isMale()) {
			return skinsMale.getRandomSkin(man);
		}
		return skinsFemale.getRandomSkin(man);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityBreeMan man = (LOTREntityBreeMan) entity;
		if (pass == 0 && man.getEquipmentInSlot(4) == null && !man.familyInfo.isMale() && LOTRRandomSkins.nextInt(man, 4) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(headwearFemale.getRandomSkin(man));
			return 1;
		}
		return super.shouldRenderPass(man, pass, f);
	}
}
