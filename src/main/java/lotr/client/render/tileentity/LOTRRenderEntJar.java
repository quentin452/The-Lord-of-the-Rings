package lotr.client.render.tileentity;

import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class LOTRRenderEntJar extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar) tileentity;
		if (jar.drinkAmount <= 0) {
			return;
		}
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d + 0.5f, (float) d1, (float) d2 + 0.5f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		float transparency = 0.5f;
		Tessellator tessellator = Tessellator.instance;
		IIcon icon;
		float minU;
		float maxU;
		float minV;
		float maxV;
		int color = 16777215;
		if (jar.drinkMeta >= 0) {
			bindTexture(TextureMap.locationItemsTexture);
			ItemStack drink = new ItemStack(LOTRMod.entDraught, 1, jar.drinkMeta);
			icon = drink.getIconIndex();
			minU = icon.getInterpolatedU(7.0);
			maxU = icon.getInterpolatedU(8.0);
			minV = icon.getInterpolatedV(7.0);
			maxV = icon.getInterpolatedV(8.0);
		} else {
			bindTexture(TextureMap.locationBlocksTexture);
			icon = Blocks.water.getBlockTextureFromSide(1);
			minU = icon.getInterpolatedU(0.0);
			maxU = icon.getInterpolatedU(6.0);
			minV = icon.getInterpolatedV(0.0);
			maxV = icon.getInterpolatedV(6.0);
			color = Blocks.water.colorMultiplier(jar.getWorldObj(), jar.xCoord, jar.yCoord, jar.zCoord);
		}
		double d3 = 0.1875;
		double d4 = -0.0625 - 0.75 * jar.drinkAmount / LOTRTileEntityEntJar.MAX_CAPACITY;
		tessellator.startDrawingQuads();
		float[] colors = new Color(color).getColorComponents(null);
		tessellator.setColorRGBA_F(colors[0], colors[1], colors[2], transparency);
		tessellator.addVertexWithUV(-d3, d4, d3, minU, maxV);
		tessellator.addVertexWithUV(d3, d4, d3, maxU, maxV);
		tessellator.addVertexWithUV(d3, d4, -d3, maxU, minV);
		tessellator.addVertexWithUV(-d3, d4, -d3, minU, minV);
		tessellator.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
