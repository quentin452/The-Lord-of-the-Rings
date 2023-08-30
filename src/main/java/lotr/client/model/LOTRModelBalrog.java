package lotr.client.model;

import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBalrog extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer neck;
	public ModelRenderer head;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer tail;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public boolean isFireModel;
	public int heldItemRight;

	public LOTRModelBalrog() {
		this(0.0f);
	}

	public LOTRModelBalrog(float f) {
		textureWidth = 128;
		textureHeight = 256;
		body = new ModelRenderer(this, 0, 38);
		body.setRotationPoint(0.0f, 7.0f, 3.0f);
		body.addBox(-8.0f, -15.0f, -6.0f, 16, 18, 12, f);
		body.setTextureOffset(0, 207);
		body.addBox(-9.0f, -6.5f, -7.0f, 7, 1, 14, f);
		body.addBox(-9.0f, -9.5f, -7.0f, 7, 1, 14, f);
		body.addBox(-9.0f, -12.5f, -7.0f, 7, 1, 14, f);
		body.mirror = true;
		body.addBox(2.0f, -6.5f, -7.0f, 7, 1, 14, f);
		body.addBox(2.0f, -9.5f, -7.0f, 7, 1, 14, f);
		body.addBox(2.0f, -12.5f, -7.0f, 7, 1, 14, f);
		body.mirror = false;
		body.setTextureOffset(0, 0).addBox(-9.0f, -29.0f, -7.0f, 18, 14, 15, f);
		body.setTextureOffset(81, 163).addBox(-2.0f, -21.0f, 5.5f, 4, 25, 2, f);
		neck = new ModelRenderer(this, 76, 0);
		neck.setRotationPoint(0.0f, -25.0f, -3.0f);
		neck.addBox(-6.0f, -5.0f, -10.0f, 12, 12, 14, f);
		body.addChild(neck);
		head = new ModelRenderer(this, 92, 48);
		head.setRotationPoint(0.0f, 0.0f, -10.0f);
		head.addBox(-4.0f, -6.0f, -6.0f, 8, 10, 7, f);
		head.setTextureOffset(57, 58).addBox(-6.0f, -7.0f, -4.0f, 12, 4, 4, f);
		head.rotateAngleX = 0.17453292519943295f;
		neck.addChild(head);
		ModelRenderer rightHorn1 = new ModelRenderer(this, 57, 47);
		rightHorn1.setRotationPoint(-6.0f, -5.0f, -2.0f);
		rightHorn1.addBox(-7.0f, -1.5f, -1.5f, 8, 3, 3, f);
		rightHorn1.rotateAngleY = -0.6108652381980153f;
		head.addChild(rightHorn1);
		ModelRenderer rightHorn2 = new ModelRenderer(this, 57, 35);
		rightHorn2.setRotationPoint(-7.0f, 0.0f, 0.0f);
		rightHorn2.addBox(-1.0f, -1.0f, -6.0f, 2, 2, 6, f);
		rightHorn2.rotateAngleY = 0.7853981633974483f;
		rightHorn1.addChild(rightHorn2);
		ModelRenderer leftHorn1 = new ModelRenderer(this, 57, 47);
		leftHorn1.setRotationPoint(6.0f, -5.0f, -2.0f);
		leftHorn1.mirror = true;
		leftHorn1.addBox(-1.0f, -1.5f, -1.5f, 8, 3, 3, f);
		leftHorn1.rotateAngleY = 0.6108652381980153f;
		head.addChild(leftHorn1);
		ModelRenderer leftHorn2 = new ModelRenderer(this, 57, 35);
		leftHorn2.setRotationPoint(7.0f, 0.0f, 0.0f);
		leftHorn2.mirror = true;
		leftHorn2.addBox(-1.0f, -1.0f, -6.0f, 2, 2, 6, f);
		leftHorn2.rotateAngleY = -0.7853981633974483f;
		leftHorn1.addChild(leftHorn2);
		rightArm = new ModelRenderer(this, 59, 136);
		rightArm.setRotationPoint(-9.0f, -25.0f, 0.0f);
		rightArm.addBox(-7.0f, -2.0f, -4.0f, 7, 10, 8, f);
		rightArm.setTextureOffset(93, 136).addBox(-6.5f, 8.0f, -3.0f, 6, 16, 6, f);
		body.addChild(rightArm);
		leftArm = new ModelRenderer(this, 59, 136);
		leftArm.setRotationPoint(9.0f, -25.0f, 0.0f);
		leftArm.mirror = true;
		leftArm.addBox(0.0f, -2.0f, -4.0f, 7, 10, 8, f);
		leftArm.setTextureOffset(93, 136).addBox(0.5f, 8.0f, -3.0f, 6, 16, 6, f);
		body.addChild(leftArm);
		rightLeg = new ModelRenderer(this, 46, 230);
		rightLeg.setRotationPoint(-6.0f, 6.0f, 3.0f);
		rightLeg.addBox(-7.0f, -2.0f, -4.0f, 7, 9, 8, f);
		rightLeg.setTextureOffset(46, 208).addBox(-6.5f, 2.0f, 4.0f, 6, 13, 5, f);
		ModelRenderer rightFoot = new ModelRenderer(this, 0, 243);
		rightFoot.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightFoot.addBox(-7.0f, 15.0f, -6.0f, 7, 3, 9, f);
		rightFoot.rotateAngleX = 0.4363323129985824f;
		rightLeg.addChild(rightFoot);
		leftLeg = new ModelRenderer(this, 46, 230);
		leftLeg.setRotationPoint(6.0f, 6.0f, 3.0f);
		leftLeg.mirror = true;
		leftLeg.addBox(0.0f, -2.0f, -4.0f, 7, 9, 8, f);
		leftLeg.setTextureOffset(46, 208).addBox(0.5f, 2.0f, 4.0f, 6, 13, 5, f);
		ModelRenderer leftFoot = new ModelRenderer(this, 0, 243);
		leftFoot.setRotationPoint(0.0f, 0.0f, 0.0f);
		leftFoot.mirror = true;
		leftFoot.addBox(0.0f, 15.0f, -6.0f, 7, 3, 9, f);
		leftFoot.rotateAngleX = 0.4363323129985824f;
		leftLeg.addChild(leftFoot);
		tail = new ModelRenderer(this, 79, 200);
		tail.setRotationPoint(0.0f, -3.0f, 3.0f);
		tail.addBox(-3.5f, -3.0f, 2.0f, 7, 7, 10, f);
		tail.setTextureOffset(80, 225).addBox(-2.5f, -2.5f, 11.0f, 5, 5, 14, f);
		tail.setTextureOffset(96, 175).addBox(-1.5f, -2.0f, 24.0f, 3, 3, 12, f);
		body.addChild(tail);
		rightWing = new ModelRenderer(this, 0, 137);
		rightWing.setRotationPoint(-6.0f, -27.0f, 4.0f);
		rightWing.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 25, f);
		rightWing.setTextureOffset(0, 167).addBox(-1.0f, -2.0f, 25.0f, 2, 24, 2, f);
		rightWing.setTextureOffset(0, 30).addBox(-0.5f, -7.0f, 25.5f, 1, 5, 1, f);
		rightWing.setTextureOffset(0, 69).addBox(0.0f, 0.0f, 0.0f, 0, 35, 25, f);
		body.addChild(rightWing);
		leftWing = new ModelRenderer(this, 0, 137);
		leftWing.setRotationPoint(6.0f, -27.0f, 4.0f);
		leftWing.mirror = true;
		leftWing.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 25, f);
		leftWing.setTextureOffset(0, 167).addBox(-1.0f, -2.0f, 25.0f, 2, 24, 2, f);
		leftWing.setTextureOffset(0, 30).addBox(-0.5f, -7.0f, 25.5f, 1, 5, 1, f);
		leftWing.setTextureOffset(0, 69).addBox(0.0f, 0.0f, 0.0f, 0, 35, 25, f);
		body.addChild(leftWing);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityBalrog balrog = (LOTREntityBalrog) entity;
		if (isFireModel) {
			rightWing.showModel = false;
			leftWing.showModel = false;
		} else {
			rightWing.showModel = LOTRConfig.balrogWings && balrog.isWreathedInFlame();
			leftWing.showModel = rightWing.showModel;
		}
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
	}

	public void setFireModel() {
		isFireModel = true;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		neck.rotateAngleX = -0.17453292519943295f;
		neck.rotateAngleY = 0.0f;
		neck.rotateAngleX += f4 / 57.29577951308232f;
		neck.rotateAngleY += f3 / 57.29577951308232f;
		body.rotateAngleX = 0.17453292519943295f;
		body.rotateAngleX += MathHelper.cos(f2 * 0.03f) * 0.15f;
		rightArm.rotateAngleX = 0.0f;
		leftArm.rotateAngleX = 0.0f;
		rightArm.rotateAngleZ = 0.0f;
		leftArm.rotateAngleZ = 0.0f;
		rightArm.rotateAngleX += MathHelper.cos(f * 0.4f + 3.1415927f) * 0.8f * f1;
		leftArm.rotateAngleX += MathHelper.cos(f * 0.4f) * 0.8f * f1;
		rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		if (onGround > -9990.0f) {
			float f6 = 1.0f - onGround;
			rightArm.rotateAngleY += body.rotateAngleY;
			leftArm.rotateAngleY += body.rotateAngleY;
			leftArm.rotateAngleX += body.rotateAngleY;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0f - f6;
			float f7 = MathHelper.sin(f6 * 3.1415927f);
			float f8 = MathHelper.sin(onGround * 3.1415927f) * -(head.rotateAngleX - 0.7f) * 0.75f;
			rightArm.rotateAngleX = (float) (rightArm.rotateAngleX - (f7 * 1.2 + f8));
			rightArm.rotateAngleY += body.rotateAngleY * 2.0f;
			rightArm.rotateAngleZ = MathHelper.sin(onGround * 3.1415927f) * -0.4f;
		}
		if (heldItemRight != 0) {
			rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5f - 0.31415927f * heldItemRight;
		}
		rightLeg.rotateAngleX = -0.4363323129985824f;
		leftLeg.rotateAngleX = -0.4363323129985824f;
		rightLeg.rotateAngleX += MathHelper.sin(f * 0.4f) * 1.2f * f1;
		leftLeg.rotateAngleX += MathHelper.sin(f * 0.4f + 3.1415927f) * 1.2f * f1;
		rightWing.rotateAngleX = 0.6981317007977318f;
		leftWing.rotateAngleX = 0.6981317007977318f;
		rightWing.rotateAngleY = -0.6981317007977318f;
		leftWing.rotateAngleY = 0.6981317007977318f;
		rightWing.rotateAngleY += MathHelper.cos(f2 * 0.04f) * 0.5f;
		leftWing.rotateAngleY -= MathHelper.cos(f2 * 0.04f) * 0.5f;
		tail.rotateAngleX = -0.6981317007977318f;
		tail.rotateAngleY = 0.0f;
		tail.rotateAngleY += MathHelper.cos(f2 * 0.05f) * 0.15f;
		tail.rotateAngleY += MathHelper.sin(f * 0.1f) * 0.6f * f1;
	}
}
