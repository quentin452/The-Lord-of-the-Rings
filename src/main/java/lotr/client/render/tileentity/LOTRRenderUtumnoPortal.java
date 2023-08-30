package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class LOTRRenderUtumnoPortal extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(false);
		int passes = 60;
		for (int i = 0; i < passes; ++i) {
			GL11.glPushMatrix();
			GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.0f + i * 0.5f, (float) d2 + 0.5f);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, (float) (passes - i) / passes);
			double width = 0.5;
			tessellator.addVertexWithUV(width, 0.0, width, 0.0, 0.0);
			tessellator.addVertexWithUV(width, 0.0, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, 0.0, -width, 0.0, 0.0);
			tessellator.addVertexWithUV(-width, 0.0, width, 0.0, 0.0);
			tessellator.draw();
			GL11.glPopMatrix();
		}
		GL11.glDepthMask(true);
		GL11.glEnable(3553);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
