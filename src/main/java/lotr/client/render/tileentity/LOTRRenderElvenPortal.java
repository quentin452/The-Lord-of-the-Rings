package lotr.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.Random;

public class LOTRRenderElvenPortal extends TileEntitySpecialRenderer {
	public FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer(16);
	public ResourceLocation portalTexture0 = new ResourceLocation("lotr:misc/elvenportal_0.png");
	public ResourceLocation portalTexture1 = new ResourceLocation("lotr:misc/elvenportal_1.png");

	public FloatBuffer getFloatBuffer(float f, float f1, float f2, float f3) {
		floatBuffer.clear();
		floatBuffer.put(f).put(f1).put(f2).put(f3);
		floatBuffer.flip();
		return floatBuffer;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		float f0 = tileentity.getWorldObj().getTotalWorldTime() % 16L + f;
		float f1 = (float) TileEntityRendererDispatcher.staticPlayerX + f0 * 0.25f;
		float f2 = (float) TileEntityRendererDispatcher.staticPlayerY;
		float f3 = (float) TileEntityRendererDispatcher.staticPlayerZ + f0 * 0.25f;
		GL11.glDisable(2896);
		GL11.glColor3f(0.2f, 0.6f, 1.0f);
		Random random = new Random(31100L);
		float f4 = 0.75f;
		for (int i = 0; i < 16; ++i) {
			GL11.glPushMatrix();
			float f5 = 16 - i;
			float f6 = 0.0625f;
			float f7 = 1.0f / (f5 + 1.0f);
			if (i == 0) {
				bindTexture(portalTexture0);
				f7 = 0.1f;
				f5 = 65.0f;
				f6 = 0.125f;
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
			}
			if (i == 1) {
				bindTexture(portalTexture1);
				GL11.glEnable(3042);
				GL11.glBlendFunc(1, 1);
				f6 = 0.5f;
			}
			float f8 = (float) -(d1 + f4);
			float f9 = f8 + ActiveRenderInfo.objectY;
			float f10 = f8 + f5 + ActiveRenderInfo.objectY;
			float f11 = f9 / f10;
			GL11.glTranslatef(f1, f11 + (float) (d1 + f4), f3);
			GL11.glTexGeni(8192, 9472, 9217);
			GL11.glTexGeni(8193, 9472, 9217);
			GL11.glTexGeni(8194, 9472, 9217);
			GL11.glTexGeni(8195, 9472, 9216);
			GL11.glTexGen(8192, 9473, getFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
			GL11.glTexGen(8193, 9473, getFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
			GL11.glTexGen(8194, 9473, getFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
			GL11.glTexGen(8195, 9474, getFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
			GL11.glEnable(3168);
			GL11.glEnable(3169);
			GL11.glEnable(3170);
			GL11.glEnable(3171);
			GL11.glPopMatrix();
			GL11.glMatrixMode(5890);
			GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
			GL11.glScalef(f6, f6, f6);
			GL11.glTranslatef(0.5f, 0.5f, 0.0f);
			GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
			GL11.glTranslatef(-f1, -f3, -f2);
			f9 = f8 + ActiveRenderInfo.objectY;
			GL11.glTranslatef(ActiveRenderInfo.objectX * f5 / f9, ActiveRenderInfo.objectZ * f5 / f9, -f2);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			f11 = random.nextFloat() * 0.5f + 0.1f;
			float f12 = random.nextFloat() * 0.5f + 0.4f;
			float f13 = random.nextFloat() * 0.5f + 0.5f;
			if (i == 0) {
				f13 = 1.0f;
				f12 = 1.0f;
				f11 = 1.0f;
			}
			tessellator.setColorRGBA_F(f11 * f7, f12 * f7, f13 * f7, 1.0f);
			tessellator.addVertex(d, d1 + f4, d2);
			tessellator.addVertex(d, d1 + f4, d2 + 1.0);
			tessellator.addVertex(d + 1.0, d1 + f4, d2 + 1.0);
			tessellator.addVertex(d + 1.0, d1 + f4, d2);
			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glMatrixMode(5888);
		}
		GL11.glDisable(3042);
		GL11.glDisable(3168);
		GL11.glDisable(3169);
		GL11.glDisable(3170);
		GL11.glDisable(3171);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glEnable(2896);
	}
}
