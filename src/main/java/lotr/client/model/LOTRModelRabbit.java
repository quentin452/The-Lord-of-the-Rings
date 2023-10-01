package lotr.client.model;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRabbit extends ModelBase {
	public ModelRenderer head = new ModelRenderer(this, 0, 0);
	public ModelRenderer body;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;

	public LOTRModelRabbit() {
		head.addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4);
		head.setRotationPoint(0.0f, -7.0f, 0.0f);
		head.setTextureOffset(0, 8).addBox(-1.5f, 0.0f, -3.0f, 3, 2, 2);
		ModelRenderer rightEar = new ModelRenderer(this, 16, 0);
		rightEar.addBox(-1.2f, -4.5f, -0.5f, 2, 5, 1);
		rightEar.setRotationPoint(-1.0f, -1.5f, 0.0f);
		rightEar.rotateAngleX = -0.3490658503988659f;
		ModelRenderer leftEar = new ModelRenderer(this, 16, 0);
		leftEar.mirror = true;
		leftEar.addBox(-0.8f, -4.5f, -0.5f, 2, 5, 1);
		leftEar.setRotationPoint(1.0f, -1.5f, 0.0f);
		leftEar.rotateAngleX = -0.3490658503988659f;
		head.addChild(rightEar);
		head.addChild(leftEar);
		body = new ModelRenderer(this, 0, 19);
		body.addBox(-2.5f, -4.0f, -2.5f, 5, 8, 5);
		body.setRotationPoint(0.0f, 18.5f, 0.0f);
		body.setTextureOffset(0, 14).addBox(-1.5f, -6.0f, -1.5f, 3, 2, 3);
		ModelRenderer tail = new ModelRenderer(this, 32, 30);
		tail.addBox(-0.5f, -0.5f, -0.5f, 1, 1, 1);
		tail.setRotationPoint(0.0f, 4.5f, 2.5f);
		tail.rotateAngleX = -0.7853981633974483f;
		body.addChild(head);
		body.addChild(tail);
		rightArm = new ModelRenderer(this, 32, 0);
		rightArm.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
		rightArm.setRotationPoint(-1.5f, -2.0f, -2.5f);
		leftArm = new ModelRenderer(this, 32, 0);
		leftArm.mirror = true;
		leftArm.addBox(-0.5f, -0.5f, -0.5f, 1, 4, 1);
		leftArm.setRotationPoint(1.5f, -2.0f, -2.5f);
		body.addChild(rightArm);
		body.addChild(leftArm);
		rightLeg = new ModelRenderer(this, 32, 8);
		rightLeg.addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
		rightLeg.setRotationPoint(-3.0f, 21.5f, 1.0f);
		ModelRenderer rightFoot = new ModelRenderer(this, 32, 16);
		rightFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
		rightFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
		rightFoot.rotateAngleX = -0.2617993877991494f;
		rightLeg.addChild(rightFoot);
		leftLeg = new ModelRenderer(this, 32, 8);
		leftLeg.mirror = true;
		leftLeg.addBox(-1.0f, -2.0f, -2.0f, 2, 4, 4);
		leftLeg.setRotationPoint(3.0f, 21.5f, 1.0f);
		ModelRenderer leftFoot = new ModelRenderer(this, 32, 16);
		leftFoot.mirror = true;
		leftFoot.addBox(-1.0f, -0.5f, -2.5f, 2, 1, 3);
		leftFoot.setRotationPoint(0.0f, 2.0f, -1.0f);
		leftFoot.rotateAngleX = -0.2617993877991494f;
		leftLeg.addChild(leftFoot);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		body.rotateAngleX = 0.7853981633974483f;
		head.rotateAngleX = -0.7853981633974483f;
		rightArm.rotateAngleX = -0.9599310885968813f;
		leftArm.rotateAngleX = -0.9599310885968813f;
		float f6 = 0.7853981633974483f;
		f6 = LOTRMod.isAprilFools() ? f6 * f2 : f6 * f1;
		body.rotateAngleX += f6;
		head.rotateAngleX -= f6;
		rightArm.rotateAngleX -= f6;
		leftArm.rotateAngleX -= f6;
		if (((LOTREntityRabbit) entity).isRabbitEating()) {
			float f7 = 0.5235987755982988f;
			body.rotateAngleX += f7;
			rightArm.rotateAngleX += f7;
			leftArm.rotateAngleX += f7;
			head.rotateAngleX += f7 * 2.0f;
		} else {
			head.rotateAngleX += f4 / 57.295776f;
			head.rotateAngleY = MathHelper.cos(head.rotateAngleX) * f3 / 57.295776f;
			head.rotateAngleZ = MathHelper.sin(head.rotateAngleX) * f3 / 57.295776f;
		}
		rightArm.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * f1;
		leftArm.rotateAngleX += MathHelper.cos(f * 0.6662f) * f1;
		body.rotateAngleZ = MathHelper.cos(f * 0.6662f) * f1 * 0.3f;
		rightLeg.rotateAngleX = 0.2617993877991494f;
		leftLeg.rotateAngleX = 0.2617993877991494f;
		rightLeg.rotateAngleX += MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leftLeg.rotateAngleX += MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
	}
}
