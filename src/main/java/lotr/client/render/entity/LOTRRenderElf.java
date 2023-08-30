package lotr.client.render.entity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTextures;
import lotr.client.model.LOTRModelElf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemRing;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class LOTRRenderElf extends LOTRRenderBiped {
	public static LOTRRandomSkins galadhrimSkinsMale;
	public static LOTRRandomSkins galadhrimSkinsFemale;
	public static LOTRRandomSkins woodElfSkinsMale;
	public static LOTRRandomSkins woodElfSkinsFemale;
	public static LOTRRandomSkins highElfSkinsMale;
	public static LOTRRandomSkins highElfSkinsFemale;
	public static LOTRRandomSkins dorwinionSkinsMale;
	public static LOTRRandomSkins dorwinionSkinsFemale;
	public static LOTRRandomSkins tormentedElfSkins;
	public static LOTRRandomSkins jazzSkinsMale;
	public static LOTRRandomSkins jazzSkinsFemale;
	public static LOTRRandomSkins jazzOutfits;
	public LOTRGlowingEyes.Model eyesModel = new LOTRModelElf(0.05f, 64, 64);
	public LOTRModelElf outfitModel = new LOTRModelElf(0.6f, 64, 64);

	public LOTRRenderElf() {
		super(new LOTRModelElf(), 0.5f);
		galadhrimSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_male");
		galadhrimSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/galadhrim_female");
		woodElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_male");
		woodElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/woodElf_female");
		highElfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_male");
		highElfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/highElf_female");
		dorwinionSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_male");
		dorwinionSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/dorwinion_female");
		tormentedElfSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/tormented");
		jazzSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_male");
		jazzSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_female");
		jazzOutfits = LOTRRandomSkins.loadSkinsList("lotr:mob/elf/jazz_outfit");
	}

	@Override
	public void func_82421_b() {
		field_82423_g = new LOTRModelElf(1.0f);
		field_82425_h = new LOTRModelElf(0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityElf elf = (LOTREntityElf) entity;
		boolean male = elf.familyInfo.isMale();
		if (elf.isJazz()) {
			if (male) {
				return jazzSkinsMale.getRandomSkin(elf);
			}
			return jazzSkinsFemale.getRandomSkin(elf);
		}
		if (elf instanceof LOTREntityTormentedElf) {
			return tormentedElfSkins.getRandomSkin(elf);
		}
		if (elf instanceof LOTREntityDorwinionElf) {
			if (male) {
				return dorwinionSkinsMale.getRandomSkin(elf);
			}
			return dorwinionSkinsFemale.getRandomSkin(elf);
		}
		if (elf instanceof LOTREntityHighElf || elf instanceof LOTREntityRivendellElf) {
			if (male) {
				return highElfSkinsMale.getRandomSkin(elf);
			}
			return highElfSkinsFemale.getRandomSkin(elf);
		}
		if (elf instanceof LOTREntityWoodElf) {
			if (male) {
				return woodElfSkinsMale.getRandomSkin(elf);
			}
			return woodElfSkinsFemale.getRandomSkin(elf);
		}
		if (male) {
			return galadhrimSkinsMale.getRandomSkin(elf);
		}
		return galadhrimSkinsFemale.getRandomSkin(elf);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		LOTREntityElf elf = (LOTREntityElf) entity;
		if (LOTRMod.isAprilFools()) {
			GL11.glScalef(0.25f, 0.25f, 0.25f);
		}
		if (elf.isJazz() && elf.isSolo()) {
			float hue = (elf.ticksExisted + f) / 20.0f;
			float sat = 0.5f;
			Color color = Color.getHSBColor(hue % 360.0f, sat, 1.0f);
			float r = color.getRed() / 255.0f;
			float g = color.getGreen() / 255.0f;
			float b = color.getBlue() / 255.0f;
			GL11.glColor3f(r, g, b);
			float soloSpin = elf.getSoloSpin(f);
			GL11.glRotatef(soloSpin, 0.0f, 1.0f, 0.0f);
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		super.renderEquippedItems(entity, f);
		LOTREntityElf elf = (LOTREntityElf) entity;
		if (elf.isJazz() && elf.isSolo()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 0.75f, 0.1f);
			GL11.glScalef(1.0f, -1.0f, 1.0f);
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
			TextureManager texturemanager = renderManager.renderEngine;
			texturemanager.bindTexture(TextureMap.locationItemsTexture);
			TextureUtil.func_152777_a(false, false, 1.0f);
			Tessellator tessellator = Tessellator.instance;
			IIcon icon = LOTRItemRing.saxIcon;
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			GL11.glEnable(32826);
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
			GL11.glDisable(32826);
			texturemanager.bindTexture(TextureMap.locationItemsTexture);
			TextureUtil.func_147945_b();
			GL11.glPopMatrix();
		}
	}

	@Override
	public void renderModel(EntityLivingBase entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderModel(entity, f, f1, f2, f3, f4, f5);
		if (entity instanceof LOTREntityTormentedElf) {
			ResourceLocation eyes = LOTRTextures.getEyesTexture(getEntityTexture(entity), new int[][]{{9, 12}, {13, 12}}, 2, 1);
			LOTRGlowingEyes.renderGlowingEyes(entity, eyes, eyesModel, f, f1, f2, f3, f4, f5);
		}
	}

	@Override
	public int shouldRenderPass(EntityLiving entity, int pass, float f) {
		LOTREntityElf elf = (LOTREntityElf) entity;
		if (elf.isJazz() && pass == 0 && elf.getEquipmentInSlot(4) == null && LOTRRandomSkins.nextInt(elf, 2) == 0) {
			setRenderPassModel(outfitModel);
			bindTexture(jazzOutfits.getRandomSkin(elf));
			return 1;
		}
		return super.shouldRenderPass(elf, pass, f);
	}
}
