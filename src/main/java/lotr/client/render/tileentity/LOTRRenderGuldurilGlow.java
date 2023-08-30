package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LOTRRenderGuldurilGlow extends TileEntitySpecialRenderer {
	public static ResourceLocation texture = new ResourceLocation("lotr:misc/gulduril_glow.png");

	public boolean guldurilBlockAt(TileEntity te, int dx, int dy, int dz) {
		if (te == null) {
			return false;
		}
		World world = te.getWorldObj();
		TileEntity otherTE = world.getTileEntity(te.xCoord + dx, te.yCoord + dy, te.zCoord + dz);
		return otherTE instanceof LOTRTileEntityGulduril;
	}

	public void renderFace(PositionTextureVertex v0, PositionTextureVertex v1, PositionTextureVertex v2, PositionTextureVertex v3) {
		float uMin = 0.0f;
		float uMax = 0.25f;
		float vMin = 0.0f;
		float vMax = 0.25f;
		v0.texturePositionX = uMin;
		v0.texturePositionY = vMax;
		v1.texturePositionX = uMax;
		v1.texturePositionY = vMax;
		v2.texturePositionX = uMax;
		v2.texturePositionY = vMin;
		v3.texturePositionX = uMin;
		v3.texturePositionY = vMin;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		for (PositionTextureVertex v : new PositionTextureVertex[]{v0, v1, v2, v3}) {
			tess.addVertexWithUV(v.vector3D.xCoord, v.vector3D.yCoord, v.vector3D.zCoord, v.texturePositionX, v.texturePositionY);
		}
		tess.draw();
	}

	public void renderGlowAt(double d, double d1, double d2, int tick, float f, TileEntity te) {
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GL11.glPushMatrix();
		float glowTick = tick + f;
		bindTexture(texture);
		GL11.glMatrixMode(5890);
		GL11.glLoadIdentity();
		float glowX = glowTick * 0.008f;
		float glowY = glowTick * 0.008f;
		GL11.glTranslatef(glowX, glowY, 0.0f);
		GL11.glMatrixMode(5888);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.5f, (float) d2 + 0.5f);
		float alpha = 0.6f;
		GL11.glColor4f(alpha, alpha, alpha, 1.0f);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glEnable(3042);
		GL11.glBlendFunc(768, 1);
		boolean glowWest = guldurilBlockAt(te, -1, 0, 0);
		boolean glowEast = guldurilBlockAt(te, 1, 0, 0);
		boolean glowBelow = guldurilBlockAt(te, 0, -1, 0);
		boolean glowAbove = guldurilBlockAt(te, 0, 1, 0);
		boolean glowNorth = guldurilBlockAt(te, 0, 0, -1);
		boolean glowSouth = guldurilBlockAt(te, 0, 0, 1);
		float xMin = -(glowWest ? 8.0f : 8.5f) / 16.0f;
		float xMax = (glowEast ? 8.0f : 8.5f) / 16.0f;
		float yMin = -(glowBelow ? 8.0f : 8.5f) / 16.0f;
		float yMax = (glowAbove ? 8.0f : 8.5f) / 16.0f;
		float zMin = -(glowNorth ? 8.0f : 8.5f) / 16.0f;
		float zMax = (glowSouth ? 8.0f : 8.5f) / 16.0f;
		PositionTextureVertex xyz = new PositionTextureVertex(xMin, yMin, zMin, 0.0f, 0.0f);
		PositionTextureVertex Xyz = new PositionTextureVertex(xMax, yMin, zMin, 0.0f, 8.0f);
		PositionTextureVertex XYz = new PositionTextureVertex(xMax, yMax, zMin, 8.0f, 8.0f);
		PositionTextureVertex xYz = new PositionTextureVertex(xMin, yMax, zMin, 8.0f, 0.0f);
		PositionTextureVertex xyZ = new PositionTextureVertex(xMin, yMin, zMax, 0.0f, 0.0f);
		PositionTextureVertex XyZ = new PositionTextureVertex(xMax, yMin, zMax, 0.0f, 8.0f);
		PositionTextureVertex XYZ = new PositionTextureVertex(xMax, yMax, zMax, 8.0f, 8.0f);
		PositionTextureVertex xYZ = new PositionTextureVertex(xMin, yMax, zMax, 8.0f, 0.0f);
		if (!glowEast) {
			renderFace(XyZ, Xyz, XYz, XYZ);
		}
		if (!glowWest) {
			renderFace(xyz, xyZ, xYZ, xYz);
		}
		if (!glowBelow) {
			renderFace(XyZ, xyZ, xyz, Xyz);
		}
		if (!glowAbove) {
			renderFace(XYz, xYz, xYZ, XYZ);
		}
		if (!glowNorth) {
			renderFace(Xyz, xyz, xYz, XYz);
		}
		if (!glowSouth) {
			renderFace(xyZ, XyZ, XYZ, xYZ);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glMatrixMode(5890);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(5888);
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glEnable(2896);
		GL11.glPopMatrix();
	}

	public void renderInvGlow() {
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		renderGlowAt(0.0, 0.0, 0.0, LOTRTickHandlerClient.clientTick, LOTRTickHandlerClient.renderTick, null);
		GL11.glEnable(32826);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityGulduril glow = (LOTRTileEntityGulduril) tileentity;
		renderGlowAt(d, d1, d2, glow.ticksExisted, f, glow);
	}
}
