package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTrollTotem extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer jaw;
	public ModelRenderer body;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer base;

	public LOTRModelTrollTotem() {
		textureWidth = 128;
		textureHeight = 64;
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-6.0f, -10.0f, -10.0f, 12, 10, 12);
		head.setRotationPoint(0.0f, 22.0f, 4.0f);
		head.addBox(-1.0f, -5.0f, -12.0f, 2, 3, 2);
		head.setTextureOffset(40, 0).addBox(-7.0f, -6.0f, -6.0f, 1, 4, 3);
		head.mirror = true;
		head.addBox(6.0f, -6.0f, -6.0f, 1, 4, 3);
		jaw = new ModelRenderer(this, 48, 0);
		jaw.addBox(-6.0f, -2.0f, -6.0f, 12, 2, 12);
		jaw.setRotationPoint(0.0f, 24.0f, 0.0f);
		body = new ModelRenderer(this, 0, 24);
		body.addBox(-5.0f, 0.0f, -5.0f, 10, 16, 10);
		body.setRotationPoint(0.0f, 8.0f, 0.0f);
		rightArm = new ModelRenderer(this, 40, 24);
		rightArm.addBox(-3.0f, 0.0f, -3.0f, 3, 10, 6);
		rightArm.setRotationPoint(-5.0f, 9.0f, 0.0f);
		leftArm = new ModelRenderer(this, 40, 24);
		leftArm.mirror = true;
		leftArm.addBox(0.0f, 0.0f, -3.0f, 3, 10, 6);
		leftArm.setRotationPoint(5.0f, 9.0f, 0.0f);
		rightLeg = new ModelRenderer(this, 0, 50);
		rightLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 7, 6);
		rightLeg.setRotationPoint(-4.0f, 8.0f, 0.0f);
		rightLeg.setTextureOffset(24, 50).addBox(-2.5f, 7.0f, -2.5f, 5, 7, 5);
		leftLeg = new ModelRenderer(this, 0, 50);
		leftLeg.mirror = true;
		leftLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 7, 6);
		leftLeg.setRotationPoint(4.0f, 8.0f, 0.0f);
		leftLeg.setTextureOffset(24, 50).addBox(-2.5f, 7.0f, -2.5f, 5, 7, 5);
		base = new ModelRenderer(this, 48, 46);
		base.addBox(-8.0f, 0.0f, -8.0f, 16, 2, 16);
		base.setRotationPoint(0.0f, 22.0f, 0.0f);
	}

	public void renderBase(float f) {
		rightLeg.render(f);
		leftLeg.render(f);
		base.render(f);
	}

	public void renderBody(float f) {
		body.render(f);
		rightArm.render(f);
		leftArm.render(f);
	}

	public void renderHead(float f, float f1) {
		head.rotateAngleX = f1 / 180.0f * 3.1415927f;
		head.render(f);
		jaw.render(f);
	}
}
