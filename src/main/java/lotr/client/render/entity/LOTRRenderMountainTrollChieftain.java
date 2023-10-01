package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMountainTrollChieftain extends LOTRRenderMountainTroll {
	public static ResourceLocation armorTexture = new ResourceLocation("lotr:mob/troll/mountainTrollChieftain_armor.png");
	public LOTRModelTroll helmetModel = new LOTRModelTroll(1.5f, 2);
	public LOTRModelTroll chestplateModel = new LOTRModelTroll(1.5f, 3);

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain) entity;
		if (troll.addedToChunk) {
			BossStatus.setBossStatus(troll, false);
		}
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		GL11.glTranslatef(0.0f, -((LOTREntityMountainTrollChieftain) entity).getSpawningOffset(f), 0.0f);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		LOTREntityMountainTrollChieftain troll = (LOTREntityMountainTrollChieftain) entity;
		bindTexture(armorTexture);
		if (pass == 2 && troll.getTrollArmorLevel() >= 2) {
			helmetModel.onGround = mainModel.onGround;
			setRenderPassModel(helmetModel);
			return 1;
		}
		if (pass == 3 && troll.getTrollArmorLevel() >= 1) {
			chestplateModel.onGround = mainModel.onGround;
			setRenderPassModel(chestplateModel);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
