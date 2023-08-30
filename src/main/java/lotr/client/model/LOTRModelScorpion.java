package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelScorpion extends LOTRModelSpider {
	public ModelRenderer armRight;
	public ModelRenderer armLeft;
	public ModelRenderer tail;

	public LOTRModelScorpion() {
		abdomen.cubeList.clear();
		armRight = new ModelRenderer(this, 36, 16);
		armRight.addBox(-16.0f, -1.0f, 0.0f, 16, 2, 2);
		armRight.setRotationPoint(-3.0f, 18.5f, -4.0f);
		ModelRenderer clawRight = new ModelRenderer(this, 0, 12);
		clawRight.addBox(-13.0f, -2.0f, -16.0f, 4, 3, 5);
		clawRight.addBox(-13.0f, -1.0f, -20.0f, 1, 1, 4);
		clawRight.addBox(-10.0f, -1.0f, -20.0f, 1, 1, 4);
		clawRight.rotateAngleY = 0.8726646259971648f;
		armRight.addChild(clawRight);
		armLeft = new ModelRenderer(this, 36, 16);
		armLeft.mirror = true;
		armLeft.addBox(0.0f, -1.0f, 0.0f, 16, 2, 2);
		armLeft.setRotationPoint(3.0f, 18.5f, -4.0f);
		ModelRenderer clawLeft = new ModelRenderer(this, 0, 12);
		clawLeft.mirror = true;
		clawLeft.addBox(9.0f, -2.0f, -16.0f, 4, 3, 5);
		clawLeft.addBox(12.0f, -1.0f, -20.0f, 1, 1, 4);
		clawLeft.addBox(9.0f, -1.0f, -20.0f, 1, 1, 4);
		clawLeft.rotateAngleY = -0.8726646259971648f;
		armLeft.addChild(clawLeft);
		tail = new ModelRenderer(this, 0, 12);
		tail.addBox(-2.5f, -3.0f, 0.0f, 5, 5, 11);
		tail.setRotationPoint(0.0f, 19.5f, 3.0f);
		ModelRenderer tail1 = new ModelRenderer(this, 0, 12);
		tail1.addBox(-2.0f, -2.0f, 0.0f, 4, 4, 10);
		tail1.setRotationPoint(0.0f, -0.5f, 11.0f);
		tail1.rotateAngleX = 0.6981317007977318f;
		tail.addChild(tail1);
		ModelRenderer tail2 = new ModelRenderer(this, 0, 12);
		tail2.addBox(-1.5f, -2.0f, 0.0f, 3, 4, 10);
		tail2.setRotationPoint(0.0f, 0.0f, 11.0f);
		tail2.rotateAngleX = 0.6981317007977318f;
		tail1.addChild(tail2);
		ModelRenderer sting = new ModelRenderer(this, 0, 12);
		sting.addBox(-1.0f, -0.5f, 0.0f, 2, 3, 5);
		sting.addBox(-0.5f, 0.0f, 5.0f, 1, 1, 3);
		sting.setRotationPoint(0.0f, 0.0f, 9.0f);
		sting.rotateAngleX = 1.5707963267948966f;
		tail2.addChild(sting);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		armRight.render(f5);
		armLeft.render(f5);
		tail.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		armRight.rotateAngleY = -0.8726646259971648f + MathHelper.cos(f * 0.4f) * f1 * 0.4f;
		armRight.rotateAngleY += f2 * -0.6981317007977318f;
		armLeft.rotateAngleY = -armRight.rotateAngleY;
		tail.rotateAngleX = 0.5235987755982988f + MathHelper.cos(f * 0.4f) * f1 * 0.15f;
		tail.rotateAngleX += f2 * 1.5707963267948966f;
	}
}
