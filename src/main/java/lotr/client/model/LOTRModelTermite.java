package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelTermite extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 10, 5);
	public ModelRenderer head;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg5;
	public ModelRenderer leg4;
	public ModelRenderer leg6;
	public ModelRenderer rightfeeler;
	public ModelRenderer leftfeeler;

	public LOTRModelTermite() {
		body.addBox(0.0f, 0.0f, 0.0f, 6, 6, 21);
		body.setRotationPoint(-3.0f, 17.0f, -5.0f);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(0.0f, 0.0f, 0.0f, 8, 8, 7);
		head.setRotationPoint(-4.0f, 14.0f, -10.0f);
		leg1 = new ModelRenderer(this, 34, 0);
		leg1.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
		leg1.setRotationPoint(-2.0f, 19.0f, 1.0f);
		leg2 = new ModelRenderer(this, 34, 0);
		leg2.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
		leg2.setRotationPoint(2.0f, 19.0f, 1.0f);
		leg3 = new ModelRenderer(this, 34, 0);
		leg3.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
		leg3.setRotationPoint(-2.0f, 19.0f, 0.0f);
		leg4 = new ModelRenderer(this, 34, 0);
		leg4.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
		leg4.setRotationPoint(2.0f, 19.0f, 0.0f);
		leg5 = new ModelRenderer(this, 34, 0);
		leg5.addBox(-12.0f, -1.0f, -1.0f, 13, 2, 2);
		leg5.setRotationPoint(-2.0f, 19.0f, -1.0f);
		leg6 = new ModelRenderer(this, 34, 0);
		leg6.addBox(-1.0f, -1.0f, -1.0f, 13, 2, 2);
		leg6.setRotationPoint(2.0f, 19.0f, -1.0f);
		rightfeeler = new ModelRenderer(this, 50, 18);
		rightfeeler.addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
		rightfeeler.setRotationPoint(-3.0f, 15.0f, -8.0f);
		rightfeeler.rotateAngleY = -0.1f;
		leftfeeler = new ModelRenderer(this, 50, 18);
		leftfeeler.addBox(0.0f, 0.0f, -8.0f, 1, 1, 6);
		leftfeeler.setRotationPoint(2.0f, 15.0f, -8.0f);
		leftfeeler.rotateAngleY = -0.1f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		head.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		leg6.render(f5);
		rightfeeler.render(f5);
		leftfeeler.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		float f6;
		leg1.rotateAngleZ = f6 = -0.51460177f;
		leg2.rotateAngleZ = -f6;
		leg3.rotateAngleZ = f6 * 0.74f;
		leg4.rotateAngleZ = -f6 * 0.74f;
		leg5.rotateAngleZ = f6 * 0.74f;
		leg6.rotateAngleZ = -f6 * 0.74f;
		float f7 = -0.0f;
		float f8 = 0.3926991f;
		leg1.rotateAngleY = f8 * 2.0f + f7;
		leg2.rotateAngleY = -f8 * 2.0f - f7;
		leg3.rotateAngleY = f8 + f7;
		leg4.rotateAngleY = -f8 - f7;
		leg5.rotateAngleY = -f8 + f7;
		leg6.rotateAngleY = f8 - f7;
		float f9 = -(MathHelper.cos(f * 0.6662f * 2.0f + 0.0f) * 0.4f) * f1;
		float f10 = -(MathHelper.cos(f * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * f1;
		float f11 = -(MathHelper.cos(f * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * f1;
		float f13 = Math.abs(MathHelper.sin(f * 0.6662f + 0.0f) * 0.4f) * f1;
		float f14 = Math.abs(MathHelper.sin(f * 0.6662f + 3.1415927f) * 0.4f) * f1;
		float f15 = Math.abs(MathHelper.sin(f * 0.6662f + 1.5707964f) * 0.4f) * f1;
		leg1.rotateAngleY += f9;
		leg2.rotateAngleY -= f9;
		leg3.rotateAngleY += f10;
		leg4.rotateAngleY -= f10;
		leg5.rotateAngleY += f11;
		leg6.rotateAngleY -= f11;
		leg1.rotateAngleZ += f13;
		leg2.rotateAngleZ -= f13;
		leg3.rotateAngleZ += f14;
		leg4.rotateAngleZ -= f14;
		leg5.rotateAngleZ += f15;
		leg6.rotateAngleZ -= f15;
	}
}
