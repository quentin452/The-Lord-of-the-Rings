package lotr.client.model;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelGollum extends ModelBase {
	public ModelRenderer head = new ModelRenderer(this, 0, 0);
	public ModelRenderer body;
	public ModelRenderer rightShoulder;
	public ModelRenderer rightArm;
	public ModelRenderer leftShoulder;
	public ModelRenderer leftArm;
	public ModelRenderer rightThigh;
	public ModelRenderer rightLeg;
	public ModelRenderer leftThigh;
	public ModelRenderer leftLeg;

	public LOTRModelGollum() {
		head.addBox(-3.5f, -6.5f, -6.5f, 7, 7, 7);
		head.setRotationPoint(0.0f, 5.0f, -5.5f);
		head.addBox(3.5f, -4.0f, -4.0f, 1, 2, 2);
		head.mirror = true;
		head.addBox(-4.5f, -4.0f, -4.0f, 1, 2, 2);
		body = new ModelRenderer(this, 20, 17);
		body.addBox(-5.0f, -12.0f, -2.0f, 10, 12, 3);
		body.setRotationPoint(0.0f, 11.0f, 5.0f);
		body.setTextureOffset(32, 0).addBox(-5.5f, -2.0f, -3.5f, 11, 4, 5);
		body.rotateAngleX = 1.0471976f;
		rightShoulder = new ModelRenderer(this, 0, 23);
		rightShoulder.addBox(-0.5f, -1.0f, -2.0f, 3, 6, 3);
		rightShoulder.setRotationPoint(5.0f, 6.0f, -4.5f);
		rightShoulder.rotateAngleX = 0.5235988f;
		rightArm = new ModelRenderer(this, 12, 22);
		rightArm.addBox(0.0f, 4.0f, 0.5f, 2, 8, 2);
		rightArm.setRotationPoint(5.0f, 6.0f, -4.5f);
		leftShoulder = new ModelRenderer(this, 0, 23);
		leftShoulder.mirror = true;
		leftShoulder.addBox(-1.5f, -1.0f, -2.0f, 3, 6, 3);
		leftShoulder.setRotationPoint(-5.0f, 6.0f, -4.5f);
		leftShoulder.rotateAngleX = 0.5235988f;
		leftArm = new ModelRenderer(this, 12, 22);
		leftArm.mirror = true;
		leftArm.addBox(-1.0f, 4.0f, 0.5f, 2, 8, 2);
		leftArm.setRotationPoint(-5.0f, 6.0f, -4.5f);
		rightThigh = new ModelRenderer(this, 0, 23);
		rightThigh.addBox(-0.5f, -1.0f, -1.0f, 3, 6, 3);
		rightThigh.setRotationPoint(2.0f, 12.0f, 4.0f);
		rightThigh.rotateAngleX = -0.43633232f;
		rightLeg = new ModelRenderer(this, 12, 22);
		rightLeg.addBox(0.0f, 4.0f, -2.5f, 2, 8, 2);
		rightLeg.setRotationPoint(2.0f, 12.0f, 4.0f);
		leftThigh = new ModelRenderer(this, 0, 23);
		leftThigh.addBox(-2.5f, -1.0f, -1.0f, 3, 6, 3);
		leftThigh.setRotationPoint(-2.0f, 12.0f, 4.0f);
		leftThigh.rotateAngleX = -0.43633232f;
		leftLeg = new ModelRenderer(this, 12, 22);
		leftLeg.addBox(-2.0f, 4.0f, -2.5f, 2, 8, 2);
		leftLeg.setRotationPoint(-2.0f, 12.0f, 4.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		rightShoulder.render(f5);
		rightArm.render(f5);
		leftShoulder.render(f5);
		leftArm.render(f5);
		rightThigh.render(f5);
		rightLeg.render(f5);
		leftThigh.render(f5);
		leftLeg.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / 57.295776f;
		head.rotateAngleX = f4 / 57.295776f;
		rightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
		leftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
		rightArm.rotateAngleZ = 0.0f;
		leftArm.rotateAngleZ = 0.0f;
		rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		rightLeg.rotateAngleY = 0.0f;
		leftLeg.rotateAngleY = 0.0f;
		rightArm.rotateAngleY = 0.0f;
		leftArm.rotateAngleY = 0.0f;
		rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		rightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
		leftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
		if (((LOTREntityGollum) entity).isGollumSitting()) {
			float f6 = f2 / 20.0f;
			rightArm.rotateAngleX = MathHelper.sin(f6 *= 6.2831855f) * 3.0f;
			leftArm.rotateAngleX = MathHelper.sin(f6) * -3.0f;
			rightLeg.rotateAngleX = MathHelper.sin(f6) * 0.5f;
			leftLeg.rotateAngleX = MathHelper.sin(f6) * -0.5f;
		} else if (((LOTREntityGollum) entity).isGollumFleeing()) {
			rightArm.rotateAngleX += 3.1415927f;
			leftArm.rotateAngleX += 3.1415927f;
		}
		body.rotateAngleZ = MathHelper.cos(f * 0.6662f) * 0.25f * f1;
		syncRotationAngles(rightArm, rightShoulder, 30.0f);
		syncRotationAngles(leftArm, leftShoulder, 30.0f);
		syncRotationAngles(rightLeg, rightThigh, -25.0f);
		syncRotationAngles(leftLeg, leftThigh, -25.0f);
	}

	public void syncRotationAngles(ModelRenderer source, ModelRenderer target, float additionalAngle) {
		target.rotationPointX = source.rotationPointX;
		target.rotationPointY = source.rotationPointY;
		target.rotationPointZ = source.rotationPointZ;
		target.rotateAngleX = source.rotateAngleX + 0.017453292f * additionalAngle;
		target.rotateAngleY = source.rotateAngleY;
		target.rotateAngleZ = source.rotateAngleZ;
	}
}
