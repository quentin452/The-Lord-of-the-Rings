package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.model.LOTRModelHuman;
import lotr.client.model.LOTRModelWizardHat;
import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGandalf extends LOTRRenderBiped {
	public static ResourceLocation skin = new ResourceLocation("lotr:mob/char/gandalf.png");
	public static ResourceLocation hat = new ResourceLocation("lotr:mob/char/gandalf_hat.png");
	public static ResourceLocation cloak = new ResourceLocation("lotr:mob/char/gandalf_cloak.png");
	public static ResourceLocation skin_santa = new ResourceLocation("lotr:mob/char/santa.png");
	public static ResourceLocation hat_santa = new ResourceLocation("lotr:mob/char/santa_hat.png");
	public static ResourceLocation cloak_santa = new ResourceLocation("lotr:mob/char/santa_cloak.png");
	public ModelBiped hatModel = new LOTRModelWizardHat(1.0f);
	public ModelBiped cloakModel = new LOTRModelHuman(0.6f, false);

	public LOTRRenderGandalf() {
		super(new LOTRModelHuman(), 0.5f);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityGandalf gandalf = (LOTREntityGandalf) entity;
		super.doRender(gandalf, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && !LOTRSpeechClient.hasSpeech(gandalf)) {
			func_147906_a(gandalf, gandalf.getCommandSenderName(), d, d1 + 0.75, d2, 64);
		}
	}

	@Override
	public ResourceLocation getCapeToRender(LOTREntityNPC entity) {
		if (LOTRMod.isChristmas()) {
			return LOTRCapes.GANDALF_SANTA;
		}
		return super.getCapeToRender(entity);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		if (LOTRMod.isChristmas()) {
			return skin_santa;
		}
		return skin;
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityGandalf gandalf = (LOTREntityGandalf) entity;
		if (pass == 0 && gandalf.getEquipmentInSlot(4) == null) {
			setRenderPassModel(hatModel);
			if (LOTRMod.isChristmas()) {
				bindTexture(hat_santa);
			} else {
				bindTexture(hat);
			}
			return 1;
		}
		if (pass == 1 && gandalf.getEquipmentInSlot(3) == null) {
			setRenderPassModel(cloakModel);
			if (LOTRMod.isChristmas()) {
				bindTexture(cloak_santa);
			} else {
				bindTexture(cloak);
			}
			return 1;
		}
		return super.shouldRenderPass(gandalf, pass, f);
	}
}
