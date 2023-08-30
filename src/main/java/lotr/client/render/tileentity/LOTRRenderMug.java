package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.*;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRRenderMug extends TileEntitySpecialRenderer {
	public static ResourceLocation mugTexture = new ResourceLocation("lotr:item/mug.png");
	public static ResourceLocation mugClayTexture = new ResourceLocation("lotr:item/mugClay.png");
	public static ResourceLocation gobletGoldTexture = new ResourceLocation("lotr:item/gobletGold.png");
	public static ResourceLocation gobletSilverTexture = new ResourceLocation("lotr:item/gobletSilver.png");
	public static ResourceLocation gobletCopperTexture = new ResourceLocation("lotr:item/gobletCopper.png");
	public static ResourceLocation gobletWoodTexture = new ResourceLocation("lotr:item/gobletWood.png");
	public static ResourceLocation skullTexture = new ResourceLocation("lotr:item/skullCup.png");
	public static ResourceLocation glassTexture = new ResourceLocation("lotr:item/wineGlass.png");
	public static ResourceLocation bottleTexture = new ResourceLocation("lotr:item/glassBottle.png");
	public static ResourceLocation hornTexture = new ResourceLocation("lotr:item/aleHorn.png");
	public static ResourceLocation hornGoldTexture = new ResourceLocation("lotr:item/aleHornGold.png");
	public static ModelBase mugModel = new LOTRModelMug();
	public static ModelBase gobletModel = new LOTRModelGoblet();
	public static ModelBase skullModel = new LOTRModelSkullCup();
	public static ModelBase glassModel = new LOTRModelWineGlass();
	public static ModelBase bottleModel = new LOTRModelGlassBottle();
	public static LOTRModelAleHorn hornModel = new LOTRModelAleHorn();
	public static RenderBlocks renderBlocks = new RenderBlocks();

	public void renderLiquid(IIcon icon, int uvMin, int uvMax, double yMin, double yMax, float scale) {
		double edge = 0.001;
		double xzMin = (double) uvMin * scale;
		double xzMax = (double) uvMax * scale;
		float dxz = 0.5f - (uvMin + uvMax) / 2.0f * scale;
		yMin = 16.0 - yMin;
		yMax = 16.0 - yMax;
		yMin *= scale;
		yMax *= scale;
		GL11.glPushMatrix();
		GL11.glTranslatef(dxz, -0.5f, dxz);
		renderBlocks.setOverrideBlockTexture(icon);
		LOTRRenderBlocks.renderStandardInvBlock(renderBlocks, LOTRMod.mugBlock, xzMin += edge, yMax - edge, xzMin, xzMax -= edge, yMin + edge, xzMax);
		renderBlocks.clearOverrideBlockTexture();
		GL11.glPopMatrix();
	}

	public void renderMeniscus(IIcon icon, int uvMin, int uvMax, double width, double height, float scale) {
		float minU = icon.getInterpolatedU(uvMin);
		float maxU = icon.getInterpolatedU(uvMax);
		float minV = icon.getInterpolatedV(uvMin);
		float maxV = icon.getInterpolatedV(uvMax);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-(width *= scale), -(height *= scale), width, minU, maxV);
		tessellator.addVertexWithUV(width, -height, width, maxU, maxV);
		tessellator.addVertexWithUV(width, -height, -width, maxU, minV);
		tessellator.addVertexWithUV(-width, -height, -width, minU, minV);
		tessellator.draw();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityMug mug = (LOTRTileEntityMug) tileentity;
		ItemStack mugItemstack = mug.getMugItemForRender();
		Item mugItem = mugItemstack.getItem();
		boolean full = !mug.isEmpty();
		LOTRItemMug.Vessel vessel = mug.getVessel();
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5f, (float) d1, (float) d2 + 0.5f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float mugScale = 0.75f;
		GL11.glScalef(mugScale, mugScale, mugScale);
		float scale = 0.0625f;
		switch (mug.getBlockMetadata()) {
			case 0: {
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 1: {
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 2: {
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 3: {
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
			}
		}
		if (vessel == LOTRItemMug.Vessel.SKULL || vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
			GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
		}
		if (full) {
			GL11.glDisable(2896);
			GL11.glPushMatrix();
			bindTexture(TextureMap.locationItemsTexture);
			IIcon liquidIcon = mugItem.getIconFromDamage(-1);
			if (vessel == LOTRItemMug.Vessel.MUG || vessel == LOTRItemMug.Vessel.MUG_CLAY) {
				renderMeniscus(liquidIcon, 6, 10, 2.0, 7.0, scale);
			} else if (vessel == LOTRItemMug.Vessel.GOBLET_GOLD || vessel == LOTRItemMug.Vessel.GOBLET_SILVER || vessel == LOTRItemMug.Vessel.GOBLET_COPPER || vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
				renderMeniscus(liquidIcon, 6, 9, 1.5, 8.0, scale);
			} else if (vessel == LOTRItemMug.Vessel.SKULL) {
				renderMeniscus(liquidIcon, 5, 11, 3.0, 9.0, scale);
			} else if (vessel == LOTRItemMug.Vessel.GLASS) {
				renderLiquid(liquidIcon, 6, 9, 6.0, 9.0, scale);
			} else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
				renderLiquid(liquidIcon, 6, 10, 1.0, 5.0, scale);
			} else if (vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
				hornModel.prepareLiquid(scale);
				renderMeniscus(liquidIcon, 6, 9, -1.5, 5.0, scale);
			}
			GL11.glPopMatrix();
			GL11.glEnable(2896);
		}
		GL11.glPushMatrix();
		ModelBase model = null;
		if (vessel == LOTRItemMug.Vessel.MUG) {
			bindTexture(mugTexture);
			model = mugModel;
		} else if (vessel == LOTRItemMug.Vessel.MUG_CLAY) {
			bindTexture(mugClayTexture);
			model = mugModel;
		} else if (vessel == LOTRItemMug.Vessel.GOBLET_GOLD) {
			bindTexture(gobletGoldTexture);
			model = gobletModel;
		} else if (vessel == LOTRItemMug.Vessel.GOBLET_SILVER) {
			bindTexture(gobletSilverTexture);
			model = gobletModel;
		} else if (vessel == LOTRItemMug.Vessel.GOBLET_COPPER) {
			bindTexture(gobletCopperTexture);
			model = gobletModel;
		} else if (vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
			bindTexture(gobletWoodTexture);
			model = gobletModel;
		} else if (vessel == LOTRItemMug.Vessel.SKULL) {
			bindTexture(skullTexture);
			model = skullModel;
		} else if (vessel == LOTRItemMug.Vessel.GLASS) {
			bindTexture(glassTexture);
			model = glassModel;
			GL11.glEnable(2884);
		} else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
			bindTexture(bottleTexture);
			model = bottleModel;
			GL11.glEnable(2884);
		} else if (vessel == LOTRItemMug.Vessel.HORN) {
			bindTexture(hornTexture);
			model = hornModel;
		} else if (vessel == LOTRItemMug.Vessel.HORN_GOLD) {
			bindTexture(hornGoldTexture);
			model = hornModel;
		}
		if (model != null) {
			model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glDisable(3042);
		GL11.glEnable(2884);
		GL11.glDisable(32826);

	}
}
