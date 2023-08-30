package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelSpider extends ModelBase implements LOTRGlowingEyes.Model {
	public ModelRenderer head = new ModelRenderer(this, 32, 0);
	public ModelRenderer thorax;
	public ModelRenderer abdomen;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leg5;
	public ModelRenderer leg6;
	public ModelRenderer leg7;
	public ModelRenderer leg8;

	public LOTRModelSpider() {
		this(0.0f);
	}

	public LOTRModelSpider(float f) {
		head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, f);
		head.setRotationPoint(0.0f, 17.0f, -3.0f);
		thorax = new ModelRenderer(this, 0, 0);
		thorax.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, f);
		thorax.setRotationPoint(0.0f, 17.0f, 0.0f);
		abdomen = new ModelRenderer(this, 0, 12);
		abdomen.addBox(-5.0f, -4.0f, -0.5f, 10, 8, 12, f);
		abdomen.setRotationPoint(0.0f, 17.0f, 3.0f);
		leg1 = new ModelRenderer(this, 36, 16);
		leg1.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg1.setRotationPoint(-4.0f, 17.0f, 2.0f);
		leg1.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg2 = new ModelRenderer(this, 36, 16);
		leg2.mirror = true;
		leg2.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg2.setRotationPoint(4.0f, 17.0f, 2.0f);
		leg2.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg3 = new ModelRenderer(this, 36, 16);
		leg3.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg3.setRotationPoint(-4.0f, 17.0f, 1.0f);
		leg3.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg4 = new ModelRenderer(this, 36, 16);
		leg4.mirror = true;
		leg4.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg4.setRotationPoint(4.0f, 17.0f, 1.0f);
		leg4.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg5 = new ModelRenderer(this, 36, 16);
		leg5.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg5.setRotationPoint(-4.0f, 17.0f, 0.0f);
		leg5.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg6 = new ModelRenderer(this, 36, 16);
		leg6.mirror = true;
		leg6.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg6.setRotationPoint(4.0f, 17.0f, 0.0f);
		leg6.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg7 = new ModelRenderer(this, 36, 16);
		leg7.addBox(-11.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg7.setRotationPoint(-4.0f, 17.0f, -1.0f);
		leg7.setTextureOffset(60, 20).addBox(-10.5f, 0.0f, -0.5f, 1, 10, 1, f);
		leg8 = new ModelRenderer(this, 36, 16);
		leg8.mirror = true;
		leg8.addBox(-1.0f, -1.0f, -1.0f, 12, 2, 2, f);
		leg8.setRotationPoint(4.0f, 17.0f, -1.0f);
		leg8.setTextureOffset(60, 20).addBox(9.5f, 0.0f, -0.5f, 1, 10, 1, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		thorax.render(f5);
		abdomen.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		leg6.render(f5);
		leg7.render(f5);
		leg8.render(f5);
	}

	@Override
	public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / 57.295776f;
		head.rotateAngleX = f4 / 57.295776f;
		abdomen.rotateAngleY = MathHelper.cos(f * 0.6662f) * 0.5f * f1;
		float f6 = -0.51460177f;
		leg1.rotateAngleZ = -f6;
		leg2.rotateAngleZ = f6;
		leg3.rotateAngleZ = -f6 * 0.74f;
		leg4.rotateAngleZ = f6 * 0.74f;
		leg5.rotateAngleZ = -f6 * 0.74f;
		leg6.rotateAngleZ = f6 * 0.74f;
		leg7.rotateAngleZ = -f6;
		leg8.rotateAngleZ = f6;
		float f7 = -0.0f;
		float f8 = 0.3926991f;
		leg1.rotateAngleY = f8 * 2.0f + f7;
		leg2.rotateAngleY = -f8 * 2.0f - f7;
		leg3.rotateAngleY = f8 + f7;
		leg4.rotateAngleY = -f8 - f7;
		leg5.rotateAngleY = -f8 + f7;
		leg6.rotateAngleY = f8 - f7;
		leg7.rotateAngleY = -f8 * 2.0f + f7;
		leg8.rotateAngleY = f8 * 2.0f - f7;
		float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f1;
		float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
		float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
		float f12 = -(MathHelper.cos(f * 0.6662f * 2.0f + 4.712389f) * 0.4f) * f1;
		float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
		float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
		float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
		float f16 = Math.abs(MathHelper.sin(f * 0.6662f + 4.712389f) * 0.4f) * f1;
		leg1.rotateAngleY += f9;
		leg2.rotateAngleY -= f9;
		leg3.rotateAngleY += f10;
		leg4.rotateAngleY -= f10;
		leg5.rotateAngleY += f11;
		leg6.rotateAngleY -= f11;
		leg7.rotateAngleY += f12;
		leg8.rotateAngleY -= f12;
		leg1.rotateAngleZ += f13;
		leg2.rotateAngleZ -= f13;
		leg3.rotateAngleZ += f14;
		leg4.rotateAngleZ -= f14;
		leg5.rotateAngleZ += f15;
		leg6.rotateAngleZ -= f15;
		leg7.rotateAngleZ += f16;
		leg8.rotateAngleZ -= f16;
	}
}
