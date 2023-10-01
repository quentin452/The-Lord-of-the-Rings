package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelCrocodile extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 18, 83).setTextureSize(128, 128);
	public ModelRenderer tail1;
	public ModelRenderer tail2;
	public ModelRenderer tail3;
	public ModelRenderer jaw;
	public ModelRenderer head;
	public ModelRenderer legFrontLeft;
	public ModelRenderer legBackLeft;
	public ModelRenderer legFrontRight;
	public ModelRenderer legBackRight;
	public ModelRenderer spines;

	public LOTRModelCrocodile() {
		body.addBox(-8.0f, -5.0f, 0.0f, 16, 9, 36);
		body.setRotationPoint(0.0f, 17.0f, -16.0f);
		tail1 = new ModelRenderer(this, 0, 28).setTextureSize(128, 128);
		tail1.addBox(-7.0f, 0.0f, 0.0f, 14, 7, 19);
		tail1.setRotationPoint(0.0f, 13.0f, 18.0f);
		tail2 = new ModelRenderer(this, 0, 55).setTextureSize(128, 128);
		tail2.addBox(-6.0f, 1.5f, 17.0f, 12, 5, 16);
		tail2.setRotationPoint(0.0f, 13.0f, 18.0f);
		tail3 = new ModelRenderer(this, 0, 77).setTextureSize(128, 128);
		tail3.addBox(-5.0f, 3.0f, 31.0f, 10, 3, 14);
		tail3.setRotationPoint(0.0f, 13.0f, 18.0f);
		jaw = new ModelRenderer(this, 58, 18).setTextureSize(128, 128);
		jaw.addBox(-6.5f, 0.3f, -19.0f, 13, 4, 19);
		jaw.setRotationPoint(0.0f, 17.0f, -16.0f);
		head = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		head.addBox(-7.5f, -6.0f, -21.0f, 15, 6, 21);
		head.setRotationPoint(0.0f, 18.5f, -16.0f);
		legFrontLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legFrontLeft.addBox(0.0f, 0.0f, -3.0f, 16, 3, 6);
		legFrontLeft.setRotationPoint(6.0f, 15.0f, -11.0f);
		legBackLeft = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legBackLeft.addBox(0.0f, 0.0f, -3.0f, 16, 3, 6);
		legBackLeft.setRotationPoint(6.0f, 15.0f, 15.0f);
		legFrontRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legFrontRight.mirror = true;
		legFrontRight.addBox(-16.0f, 0.0f, -3.0f, 16, 3, 6);
		legFrontRight.setRotationPoint(-6.0f, 15.0f, -11.0f);
		legBackRight = new ModelRenderer(this, 2, 104).setTextureSize(128, 128);
		legBackRight.mirror = true;
		legBackRight.addBox(-16.0f, 0.0f, -3.0f, 16, 3, 6);
		legBackRight.setRotationPoint(-6.0f, 15.0f, 15.0f);
		spines = new ModelRenderer(this, 46, 45).setTextureSize(128, 128);
		spines.addBox(-5.0f, 0.0f, 0.0f, 10, 4, 32);
		spines.setRotationPoint(0.0f, 9.5f, -14.0f);
		legBackLeft.rotateAngleZ = 0.43633232f;
		legBackRight.rotateAngleZ = -0.43633232f;
		legFrontLeft.rotateAngleZ = 0.43633232f;
		legFrontRight.rotateAngleZ = -0.43633232f;
		spines.rotateAngleX = -0.034906585f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tail1.render(f5);
		tail2.render(f5);
		tail3.render(f5);
		jaw.render(f5);
		head.render(f5);
		legFrontLeft.render(f5);
		legBackLeft.render(f5);
		legFrontRight.render(f5);
		legBackRight.render(f5);
		spines.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f2 * 3.1415927f * -0.3f;
		legBackRight.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
		legBackLeft.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
		legFrontRight.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
		legFrontLeft.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1;
		tail1.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.5f;
		tail2.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.5625f;
		tail3.rotateAngleY = MathHelper.cos(f * 0.6662f) * f1 * 0.59375f;
	}
}
