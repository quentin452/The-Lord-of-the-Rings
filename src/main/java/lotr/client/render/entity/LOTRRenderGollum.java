package lotr.client.render.entity;

import lotr.client.LOTRSpeechClient;
import lotr.client.model.LOTRModelGollum;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGollum extends RenderLiving {
	public static ResourceLocation skin = new ResourceLocation("lotr:mob/char/gollum.png");

	public LOTRRenderGollum() {
		super(new LOTRModelGollum(), 0.5f);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityGollum gollum = (LOTREntityGollum) entity;
		super.doRender(gollum, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled()) {
			if (!LOTRSpeechClient.hasSpeech(gollum)) {
				func_147906_a(gollum, gollum.getCommandSenderName(), d, d1 + 0.5, d2, 64);
			}
			if (gollum.getGollumOwner() == Minecraft.getMinecraft().thePlayer) {
				LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 0.5, d2);
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return skin;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = 0.85f;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		ItemStack heldItem = entity.getHeldItem();
		if (heldItem != null && heldItem.getItem() == Items.fish) {
			GL11.glPushMatrix();
			((LOTRModelGollum) mainModel).head.postRender(0.0625f);
			GL11.glTranslatef(0.21875f, 0.03125f, -0.375f);
			float f1 = 0.375f;
			GL11.glScalef(f1, f1, f1);
			GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(-50.0f, 1.0f, 0.0f, 0.0f);
			renderManager.itemRenderer.renderItem(entity, heldItem, 0);
			GL11.glPopMatrix();
		}
	}
}
