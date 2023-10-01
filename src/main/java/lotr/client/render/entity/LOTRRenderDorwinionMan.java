package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDorwinionMan extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public static LOTRRandomSkins outfits;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderDorwinionMan() {
		super(new LOTRModelHuman(), 0.5f);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/dorwinion_female");
		outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/dorwinion/outfit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityDorwinionMan man = (LOTREntityDorwinionMan) entity;
		if (man.familyInfo.isMale()) {
			return skinsMale.getRandomSkin(man);
		}
		return skinsFemale.getRandomSkin(man);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityDorwinionMan man = (LOTREntityDorwinionMan) entity;
		if (pass == 1 && man.getEquipmentInSlot(3) == null && LOTRRandomSkins.nextInt(man, 2) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(outfits.getRandomSkin(man));
			return 1;
		}
		return super.shouldRenderPass(man, pass, f);
	}
}
