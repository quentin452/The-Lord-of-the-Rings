package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelBalrog;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRRenderBalrog extends RenderLiving {
	public static LOTRRandomSkins balrogSkins;
	public static LOTRRandomSkins balrogSkinsBright;
	public static ResourceLocation fireTexture = new ResourceLocation("lotr:mob/balrog/fire.png");

	public LOTRModelBalrog balrogModel;
	public LOTRModelBalrog balrogModelBright;

	public LOTRModelBalrog fireModel;

	public LOTRRenderBalrog() {
		super(new LOTRModelBalrog(), 0.5f);
		balrogModel = (LOTRModelBalrog) mainModel;
		balrogModelBright = new LOTRModelBalrog(0.05f);
		fireModel = new LOTRModelBalrog(0.0f);
		fireModel.setFireModel();
		balrogSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog");
		balrogSkinsBright = LOTRRandomSkins.loadSkinsList("lotr:mob/balrog/balrog_bright");
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityBalrog balrog = (LOTREntityBalrog) entity;
		ItemStack heldItem = balrog.getHeldItem();
		fireModel.heldItemRight = heldItem == null ? 0 : 2;
		balrogModel.heldItemRight = fireModel.heldItemRight;
		doRender(balrog, d, d1, d2, f, f1);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return balrogSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		LOTREntityBalrog balrog = (LOTREntityBalrog) entity;
		float scale = 2.0f;
		GL11.glScalef(scale, scale, scale);
		if (balrog.isBalrogCharging()) {
			float lean = balrog.getInterpolatedChargeLean(f);
			GL11.glRotatef(lean * 35.0f, 1.0f, 0.0f, 0.0f);
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		ItemStack heldItem = entity.getHeldItem();
		if (heldItem != null) {
			GL11.glPushMatrix();
			balrogModel.body.postRender(0.0625f);
			balrogModel.rightArm.postRender(0.0625f);
			GL11.glTranslatef(-0.25f, 1.5f, -0.125f);
			float scale = 1.25f;
			GL11.glScalef(scale, -scale, scale);
			GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
			renderManager.itemRenderer.renderItem(entity, heldItem, 0);
			if (heldItem.getItem().requiresMultipleRenderPasses()) {
				for (int x = 1; x < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++x) {
					renderManager.itemRenderer.renderItem(entity, heldItem, x);
				}
			}
			GL11.glPopMatrix();
		}
	}

	public void setupFullBright() {
		int light = 15728880;
		int lx = light % 65536;
		int ly = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		LOTREntityBalrog balrog = (LOTREntityBalrog) entity;
		if (balrog.isWreathedInFlame()) {
			switch (pass) {
				case 1: {
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
					GL11.glMatrixMode(5890);
					GL11.glLoadIdentity();
					float f1 = balrog.ticksExisted + f;
					float f2 = f1 * 0.01f;
					float f3 = f1 * 0.01f;
					GL11.glTranslatef(f2, f3, 0.0f);
					GL11.glMatrixMode(5888);
					GL11.glAlphaFunc(516, 0.01f);
					GL11.glEnable(3042);
					GL11.glBlendFunc(1, 1);
					float alpha = balrog.isBalrogCharging() ? 0.6f + MathHelper.sin(f1 * 0.1f) * 0.15f : 0.3f + MathHelper.sin(f1 * 0.05f) * 0.15f;
					GL11.glColor4f(alpha, alpha, alpha, 1.0f);
					GL11.glDisable(2896);
					GL11.glDepthMask(false);
					setRenderPassModel(fireModel);
					bindTexture(fireTexture);
					return 1;
				}
				case 2:
					GL11.glMatrixMode(5890);
					GL11.glLoadIdentity();
					GL11.glMatrixMode(5888);
					GL11.glAlphaFunc(516, 0.1f);
					GL11.glDisable(3042);
					GL11.glEnable(2896);
					GL11.glDepthMask(true);
					GL11.glDisable(2896);
					setupFullBright();
					setRenderPassModel(balrogModelBright);
					bindTexture(balrogSkinsBright.getRandomSkin(balrog));
					GL11.glEnable(3042);
					GL11.glBlendFunc(770, 771);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					return 1;
				case 3:
					GL11.glEnable(2896);
					GL11.glDisable(3042);
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					break;
				default:
					break;
			}
		}
		return -1;
	}
}
