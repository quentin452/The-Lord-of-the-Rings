package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDunedain extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderDunedain() {
		super(new LOTRModelHuman(), 0.5f);
		setRenderPassModel(outfitModel);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/ranger/ranger_female");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityDunedain dunedain = (LOTREntityDunedain) entity;
		if (dunedain.familyInfo.isMale()) {
			return skinsMale.getRandomSkin(dunedain);
		}
		return skinsFemale.getRandomSkin(dunedain);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityDunedain dunedain = (LOTREntityDunedain) entity;
		return super.shouldRenderPass(dunedain, pass, f);
	}
}
