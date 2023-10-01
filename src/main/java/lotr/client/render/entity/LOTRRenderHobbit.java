package lotr.client.render.entity;

import lotr.client.model.LOTRModelHobbit;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderHobbit extends LOTRRenderBiped {
	public static LOTRRandomSkins hobbitSkinsMale;
	public static LOTRRandomSkins hobbitSkinsFemale;
	public static LOTRRandomSkins hobbitSkinsMaleChild;
	public static LOTRRandomSkins hobbitSkinsFemaleChild;
	public static ResourceLocation ringTexture = new ResourceLocation("lotr:mob/hobbit/ring.png");

	public ModelBiped outfitModel = new LOTRModelHobbit(0.5f, 64, 64);

	public LOTRRenderHobbit() {
		super(new LOTRModelHobbit(), 0.5f);
		setRenderPassModel(outfitModel);
		hobbitSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_male");
		hobbitSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_female");
		hobbitSkinsMaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_male");
		hobbitSkinsFemaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_female");
	}

	@Override
	public void func_82421_b() {
		field_82423_g = new LOTRModelHobbit(1.0f);
		field_82425_h = new LOTRModelHobbit(0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityHobbit hobbit = (LOTREntityHobbit) entity;
		boolean child = hobbit.isChild();
		if (hobbit.familyInfo.isMale()) {
			if (child) {
				return hobbitSkinsMaleChild.getRandomSkin(hobbit);
			}
			return hobbitSkinsMale.getRandomSkin(hobbit);
		}
		if (child) {
			return hobbitSkinsFemaleChild.getRandomSkin(hobbit);
		}
		return hobbitSkinsFemale.getRandomSkin(hobbit);
	}

	@Override
	public float getHeldItemYTranslation() {
		return 0.075f;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		if (LOTRMod.isAprilFools()) {
			GL11.glScalef(2.0f, 2.0f, 2.0f);
		} else {
			GL11.glScalef(0.75f, 0.75f, 0.75f);
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityHobbit hobbit = (LOTREntityHobbit) entity;
		outfitModel.bipedRightArm.showModel = true;
		if (pass == 1 && hobbit.getClass() == hobbit.familyInfo.marriageEntityClass && hobbit.getEquipmentInSlot(4) != null && hobbit.getEquipmentInSlot(4).getItem() == hobbit.familyInfo.marriageRing) {
			bindTexture(ringTexture);
			outfitModel.bipedRightArm.showModel = false;
			setRenderPassModel(outfitModel);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
