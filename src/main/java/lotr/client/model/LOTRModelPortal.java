package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class LOTRModelPortal extends ModelBase {
	public boolean isScript;
	public ModelRenderer[] ringParts = new ModelRenderer[60];
	public Vec3[][] scriptParts = new Vec3[60][4];

	public LOTRModelPortal(boolean flag) {
		isScript = flag;
		if (isScript) {
			double depth = 38.0;
			double halfX = 2.0;
			double halfY = 2.5;
			Vec3[] parts = {Vec3.createVectorHelper(halfX, -halfY, -depth), Vec3.createVectorHelper(-halfX, -halfY, -depth), Vec3.createVectorHelper(-halfX, halfY, -depth), Vec3.createVectorHelper(halfX, halfY, -depth)};
			for (int i = 0; i < 60; ++i) {
				float rotate = i / 60.0f * 3.1415927f * 2.0f;
				for (int j = 0; j < parts.length; ++j) {
					Vec3 srcPart = parts[j];
					Vec3 rotatedPart = Vec3.createVectorHelper(srcPart.xCoord, srcPart.yCoord, srcPart.zCoord);
					rotatedPart.rotateAroundY(-rotate);
					scriptParts[i][j] = rotatedPart;
				}
			}
		} else {
			for (int i = 0; i < 60; ++i) {
				ModelRenderer part = new ModelRenderer(this, 0, 0).setTextureSize(64, 32);
				part.addBox(-2.0f, -3.5f, -38.0f, 4, 7, 3);
				part.setRotationPoint(0.0f, 0.0f, 0.0f);
				part.rotateAngleY = i / 60.0f * 3.1415927f * 2.0f;
				ringParts[i] = part;
			}
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (isScript) {
			GL11.glPushMatrix();
			GL11.glScalef(-1.0f, 1.0f, 1.0f);
			Tessellator tess = Tessellator.instance;
			for (int i = 0; i < 60; ++i) {
				Vec3[] parts = scriptParts[i];
				float uMin = i / 60.0f;
				float uMax = (i + 1) / 60.0f;
				float vMin = 0.0f;
				float vMax = 1.0f;
				tess.startDrawingQuads();
				tess.addVertexWithUV(parts[0].xCoord * f5, parts[0].yCoord * f5, parts[0].zCoord * f5, uMax, vMin);
				tess.addVertexWithUV(parts[1].xCoord * f5, parts[1].yCoord * f5, parts[1].zCoord * f5, uMin, vMin);
				tess.addVertexWithUV(parts[2].xCoord * f5, parts[2].yCoord * f5, parts[2].zCoord * f5, uMin, vMax);
				tess.addVertexWithUV(parts[3].xCoord * f5, parts[3].yCoord * f5, parts[3].zCoord * f5, uMax, vMax);
				tess.draw();
			}
			GL11.glPopMatrix();
		} else {
			for (ModelRenderer ringPart : ringParts) {
				ringPart.render(f5);
			}
		}
	}
}
