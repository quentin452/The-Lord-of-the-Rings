package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanShieldmaiden;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRohirrim extends LOTRRenderBiped {
	public static LOTRRandomSkins skinsMale;
	public static LOTRRandomSkins skinsFemale;
	public static LOTRRandomSkins skinsSoldier;
	public static LOTRRandomSkins skinsShieldmaiden;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderRohirrim() {
		super(new LOTRModelHuman(), 0.5f);
		setRenderPassModel(outfitModel);
		skinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_male");
		skinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/rohan_female");
		skinsSoldier = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/warrior");
		skinsShieldmaiden = LOTRRandomSkins.loadSkinsList("lotr:mob/rohan/shieldmaiden");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityRohanMan rohirrim = (LOTREntityRohanMan) entity;
		if (rohirrim.familyInfo.isMale()) {
			if (rohirrim instanceof LOTREntityRohirrimWarrior) {
				return skinsSoldier.getRandomSkin(rohirrim);
			}
			return skinsMale.getRandomSkin(rohirrim);
		}
		if (rohirrim instanceof LOTREntityRohanShieldmaiden) {
			return skinsShieldmaiden.getRandomSkin(rohirrim);
		}
		return skinsFemale.getRandomSkin(rohirrim);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityRohanMan rohirrim = (LOTREntityRohanMan) entity;
		return super.shouldRenderPass(rohirrim, pass, f);
	}
}
