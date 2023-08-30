package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRRenderUtumnoReturnPortal extends TileEntitySpecialRenderer {
	public static ResourceLocation lightCircle = new ResourceLocation("lotr:misc/utumnoPortal_lightCircle.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityUtumnoReturnPortal portal = (LOTRTileEntityUtumnoReturnPortal) tileentity;
		World world = portal.getWorldObj();
		world.theProfiler.startSection("utumnoReturnPortal");
		float renderTime = portal.ticksExisted + f;
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glAlphaFunc(516, 0.01f);
		GL11.glBlendFunc(770, 771);
		GL11.glTranslatef((float) d + 0.5f, (float) d1, (float) d2 + 0.5f);
		float alpha = 0.2f + 0.15f * MathHelper.sin(renderTime * 0.1f);
		int passes = 12;
		for (int i = 0; i < passes; ++i) {
			GL11.glPushMatrix();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, alpha);
			double width = (float) (i + 1) / passes * 0.99f;
			double bottom = 0.0;
			double top = bottom + LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP;
			tessellator.addVertexWithUV(width /= 2.0, bottom, width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, top, width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, top, width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, bottom, width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, bottom, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, top, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, top, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, bottom, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, bottom, width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, top, width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, top, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, bottom, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, bottom, width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, top, width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, top, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, bottom, -width, 0.0, 0.0);
			tessellator.draw();
			GL11.glPopMatrix();
		}
		GL11.glEnable(3553);
		bindTexture(lightCircle);
		int circles = 12;
		for (int i = 0; i < circles; ++i) {
			GL11.glPushMatrix();
			float rotation = renderTime * i * 0.2f;
			GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
			alpha = 0.15f + 0.1f * MathHelper.sin(renderTime * 0.1f + i);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			double width = 1.5f + 1.5f * MathHelper.sin(renderTime * 0.05f + i);
			double height = 0.01 + i * 0.01;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(width, height, width, 1.0, 1.0);
			tessellator.addVertexWithUV(width, height, -width, 1.0, 0.0);
			tessellator.addVertexWithUV(-width, height, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, height, width, 0.0, 1.0);
			tessellator.draw();
			GL11.glPopMatrix();
		}
		GL11.glAlphaFunc(516, 0.1f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
		world.theProfiler.endSection();
	}
}
