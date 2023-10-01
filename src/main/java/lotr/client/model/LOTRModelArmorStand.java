package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelArmorStand extends ModelBase {
	public ModelRenderer base = new ModelRenderer(this, 0, 0);
	public ModelRenderer head;
	public ModelRenderer spine;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightHip;
	public ModelRenderer leftHip;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightFoot;
	public ModelRenderer leftFoot;

	public LOTRModelArmorStand() {
		base.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 2);
		base.setRotationPoint(0.0f, 30.0f, 0.0f);
		base.rotateAngleX = -1.5707964f;
		head = new ModelRenderer(this, 40, 0);
		head.addBox(-3.0f, 0.0f, -2.0f, 6, 8, 4);
		head.setRotationPoint(0.0f, -11.0f, 0.0f);
		spine = new ModelRenderer(this, 60, 0);
		spine.addBox(-0.5f, 0.0f, -0.5f, 1, 25, 1);
		spine.setRotationPoint(0.0f, -3.0f, 0.0f);
		rightArm = new ModelRenderer(this, 44, 12);
		rightArm.addBox(-7.5f, 0.0f, -0.5f, 7, 1, 1);
		rightArm.setRotationPoint(0.0f, -2.0f, 0.0f);
		leftArm = new ModelRenderer(this, 44, 12);
		leftArm.mirror = true;
		leftArm.addBox(0.5f, 0.0f, -0.5f, 7, 1, 1);
		leftArm.setRotationPoint(0.0f, -2.0f, 0.0f);
		rightHip = new ModelRenderer(this, 42, 30);
		rightHip.addBox(-3.5f, 0.0f, -0.5f, 3, 1, 1);
		rightHip.setRotationPoint(0.0f, 9.0f, 0.0f);
		leftHip = new ModelRenderer(this, 42, 30);
		leftHip.mirror = true;
		leftHip.addBox(0.5f, 0.0f, -0.5f, 3, 1, 1);
		leftHip.setRotationPoint(0.0f, 9.0f, 0.0f);
		rightLeg = new ModelRenderer(this, 50, 23);
		rightLeg.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
		rightLeg.setRotationPoint(-2.0f, 10.0f, 0.0f);
		leftLeg = new ModelRenderer(this, 50, 23);
		leftLeg.mirror = true;
		leftLeg.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1);
		leftLeg.setRotationPoint(2.0f, 10.0f, 0.0f);
		rightFoot = new ModelRenderer(this, 54, 27);
		rightFoot.addBox(-2.0f, 0.0f, -1.0f, 3, 3, 2);
		rightFoot.setRotationPoint(-2.0f, 18.0f, 0.0f);
		leftFoot = new ModelRenderer(this, 54, 27);
		leftFoot.mirror = true;
		leftFoot.addBox(-1.0f, 0.0f, -1.0f, 3, 3, 2);
		leftFoot.setRotationPoint(2.0f, 18.0f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		base.render(f5);
		head.render(f5);
		spine.render(f5);
		rightArm.render(f5);
		leftArm.render(f5);
		rightHip.render(f5);
		leftHip.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
		rightFoot.render(f5);
		leftFoot.render(f5);
	}
}
