package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderAngmarHillman extends LOTRRenderBiped {
	public static LOTRRandomSkins hillmanSkinsMale;
	public static LOTRRandomSkins hillmanSkinsFemale;
	public static LOTRRandomSkins hillmanOutfits;
	public ModelBiped outfitModel = new LOTRModelHuman(0.6f, false);
	public boolean useOutfits;

	public LOTRRenderAngmarHillman(boolean outfit) {
		super(new LOTRModelHuman(), 0.5f);
		useOutfits = outfit;
		setRenderPassModel(outfitModel);
		hillmanSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_male");
		hillmanSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/hillman_female");
		hillmanOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/hillman/outfit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman) entity;
		if (hillman.familyInfo.isMale()) {
			return hillmanSkinsMale.getRandomSkin(hillman);
		}
		return hillmanSkinsFemale.getRandomSkin(hillman);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityAngmarHillman hillman = (LOTREntityAngmarHillman) entity;
		if (useOutfits && pass == 1 && hillman.getEquipmentInSlot(3) == null) {
			setRenderPassModel(outfitModel);
			bindTexture(hillmanOutfits.getRandomSkin(hillman));
			return 1;
		}
		return super.shouldRenderPass(hillman, pass, f);
	}
}
