package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Locale;

public class LOTRRenderTroll extends RenderLiving {
	public static LOTRRandomSkins trollSkins;
	public static ResourceLocation[] trollOutfits = new ResourceLocation[]{new ResourceLocation("lotr:mob/troll/outfit_0.png"), new ResourceLocation("lotr:mob/troll/outfit_1.png"), new ResourceLocation("lotr:mob/troll/outfit_2.png")};
	public static ResourceLocation weaponsTexture = new ResourceLocation("lotr:mob/troll/weapons.png");

	public LOTRModelTroll shirtModel = new LOTRModelTroll(1.0f, 0);
	public LOTRModelTroll trousersModel = new LOTRModelTroll(0.75f, 1);

	public LOTRRenderTroll() {
		super(new LOTRModelTroll(), 0.5f);
		trollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/troll");
	}

	public void bindTrollOutfitTexture(EntityLivingBase entity) {
		int j = ((LOTREntityTroll) entity).getTrollOutfit();
		if (j < 0 || j >= trollOutfits.length) {
			j = 0;
		}
		bindTexture(trollOutfits[j]);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		if (Minecraft.isGuiEnabled() && ((LOTREntityNPC) entity).hiredNPCInfo.getHiringPlayer() == renderManager.livingPlayer) {
			LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 1.0, d2);
			LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 1.0, d2);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return trollSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		LOTREntityTroll troll = (LOTREntityTroll) entity;
		scaleTroll(troll, false);
		if (LOTRMod.isAprilFools() || "shrek".equals(troll.familyInfo.getName().toLowerCase(Locale.ROOT))) {
			GL11.glColor3f(0.0f, 1.0f, 0.0f);
		} else if ("drek".equals(troll.familyInfo.getName().toLowerCase(Locale.ROOT))) {
			GL11.glColor3f(0.2f, 0.4f, 1.0f);
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		super.renderEquippedItems(entity, f);
		GL11.glPushMatrix();
		bindTexture(weaponsTexture);
		renderTrollWeapon(entity, f);
		GL11.glPopMatrix();
	}

	public void renderTrollWeapon(EntityLivingBase entity, float f) {
		((LOTRModelTroll) mainModel).renderWoodenClub(0.0625f);
	}

	@Override
	public void rotateCorpse(EntityLivingBase entity, float f, float f1, float f2) {
		if (((LOTREntityTroll) entity).getSneezingTime() > 0) {
			f1 += (float) (Math.cos(entity.ticksExisted * 3.25) * 3.141592653589793);
		}
		super.rotateCorpse(entity, f, f1, f2);
	}

	public void scaleTroll(LOTREntityTroll troll, boolean inverse) {
		float scale = troll.getTrollScale();
		if (inverse) {
			scale = 1.0f / scale;
		}
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
		bindTrollOutfitTexture(entity);
		if (pass == 0) {
			shirtModel.onGround = mainModel.onGround;
			setRenderPassModel(shirtModel);
			return 1;
		}
		if (pass == 1) {
			setRenderPassModel(trousersModel);
			return 1;
		}
		return super.shouldRenderPass(entity, pass, f);
	}
}
