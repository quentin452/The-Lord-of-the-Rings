package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityDwarf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWickedDwarf extends LOTRRenderDwarf {
	public static LOTRRandomSkins wickedSkinsMale;
	public static ResourceLocation apronTexture = new ResourceLocation("lotr:mob/dwarf/wicked_apron.png");

	public LOTRRenderWickedDwarf() {
		wickedSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/wicked_male");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity dwarf = (LOTRRandomSkinEntity) entity;
		return wickedSkinsMale.getRandomSkin(dwarf);
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityDwarf dwarf = (LOTREntityDwarf) entity;
		if (pass == 1 && dwarf.getEquipmentInSlot(3) == null) {
			setRenderPassModel(standardRenderPassModel);
			bindTexture(apronTexture);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
