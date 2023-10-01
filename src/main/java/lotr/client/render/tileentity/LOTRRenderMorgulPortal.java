package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMorgulPortal extends TileEntitySpecialRenderer {
	public static ResourceLocation portalTexture = new ResourceLocation("lotr:misc/gulduril_glow.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		float f1 = LOTRTickHandlerClient.clientTick + f;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GL11.glPushMatrix();
		bindTexture(portalTexture);
		GL11.glMatrixMode(5890);
		GL11.glLoadIdentity();
		float f2 = f1 * 0.01f;
		GL11.glTranslatef(f2, 0.0f, 0.0f);
		GL11.glMatrixMode(5888);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.5f + 0.25f * MathHelper.sin(f1 / 40.0f), (float) d2 + 0.5f);
		float f4 = 0.9f;
		GL11.glColor4f(f4, f4, f4, 1.0f);
		GL11.glDisable(2896);
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.5, 0.0, 0.5, 0.0, 0.0);
		tessellator.addVertexWithUV(0.5, 0.0, -0.5, 0.0, 0.0);
		tessellator.addVertexWithUV(-0.5, 0.0, -0.5, 0.0, 0.0);
		tessellator.addVertexWithUV(-0.5, 0.0, 0.5, 0.0, 0.0);
		tessellator.draw();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glMatrixMode(5890);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(5888);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glPopMatrix();
	}
}
