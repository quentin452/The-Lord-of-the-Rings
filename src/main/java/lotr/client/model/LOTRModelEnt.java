package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelEnt extends ModelBase implements LOTRGlowingEyes.Model {
	public ModelRenderer trunk;
	public ModelRenderer browRight;
	public ModelRenderer browLeft;
	public ModelRenderer eyeRight;
	public ModelRenderer eyeLeft;
	public ModelRenderer nose;
	public ModelRenderer beard;
	public ModelRenderer trophyBottomPanel;
	public ModelRenderer rightArm;
	public ModelRenderer rightHand;
	public ModelRenderer leftArm;
	public ModelRenderer leftHand;
	public ModelRenderer rightLeg;
	public ModelRenderer rightFoot;
	public ModelRenderer leftLeg;
	public ModelRenderer leftFoot;
	public ModelRenderer branches;

	public LOTRModelEnt() {
		this(0.0f);
	}

	public LOTRModelEnt(float f) {
		textureWidth = 128;
		textureHeight = 128;
		trunk = new ModelRenderer(this, 0, 0);
		trunk.addBox(-8.0f, -48.0f, -6.0f, 16, 48, 12, f);
		trunk.setRotationPoint(0.0f, -10.0f, 0.0f);
		browRight = new ModelRenderer(this, 56, 26);
		browRight.addBox(-6.5f, 0.0f, -8.0f, 5, 1, 2, f);
		browRight.setRotationPoint(0.0f, -39.0f, 0.0f);
		browRight.rotateAngleZ = 0.17453292519943295f;
		trunk.addChild(browRight);
		browLeft = new ModelRenderer(this, 56, 26);
		browLeft.mirror = true;
		browLeft.addBox(1.5f, 0.0f, -8.0f, 5, 1, 2, f);
		browLeft.setRotationPoint(0.0f, -39.0f, 0.0f);
		browLeft.rotateAngleZ = -0.17453292519943295f;
		trunk.addChild(browLeft);
		eyeRight = new ModelRenderer(this, 56, 29);
		eyeRight.addBox(-1.5f, -2.0f, -7.0f, 3, 3, 1, f + 0.2f);
		eyeRight.setRotationPoint(-3.5f, -36.0f, 0.0f);
		trunk.addChild(eyeRight);
		eyeLeft = new ModelRenderer(this, 56, 29);
		eyeLeft.mirror = true;
		eyeLeft.addBox(-1.5f, -2.0f, -7.0f, 3, 3, 1, f + 0.2f);
		eyeLeft.setRotationPoint(3.5f, -36.0f, 0.0f);
		trunk.addChild(eyeLeft);
		nose = new ModelRenderer(this, 56, 33);
		nose.addBox(-1.5f, -2.0f, -9.0f, 3, 6, 3, f);
		nose.setRotationPoint(0.0f, -36.0f, 0.0f);
		trunk.addChild(nose);
		beard = new ModelRenderer(this, 56, 0);
		beard.addBox(-5.0f, 0.0f, -8.0f, 10, 24, 2, f);
		beard.setRotationPoint(0.0f, -31.0f, 0.0f);
		trunk.addChild(beard);
		trophyBottomPanel = new ModelRenderer(this, 72, 116);
		trophyBottomPanel.setRotationPoint(0.0f, -24.0f, 0.0f);
		trophyBottomPanel.addBox(-8.0f, 0.0f, -6.0f, 16, 0, 12, f);
		trunk.addChild(trophyBottomPanel);
		trophyBottomPanel.showModel = false;
		rightArm = new ModelRenderer(this, 96, 28);
		rightArm.addBox(-8.0f, 0.0f, -4.0f, 8, 12, 8, f);
		rightArm.setTextureOffset(112, 48).addBox(-7.0f, 12.0f, -2.0f, 4, 16, 4, f);
		rightArm.setRotationPoint(-8.0f, -38.0f, 0.0f);
		trunk.addChild(rightArm);
		rightHand = new ModelRenderer(this, 102, 68);
		rightHand.addBox(-2.5f, 0.0f, -4.0f, 5, 16, 8, f);
		rightHand.setTextureOffset(102, 92).addBox(-2.0f, 16.0f, -4.0f, 3, 10, 2, f);
		rightHand.setTextureOffset(112, 92).addBox(-2.0f, 16.0f, -1.0f, 2, 8, 2, f);
		rightHand.setTextureOffset(120, 92).addBox(-2.0f, 16.0f, 2.0f, 2, 6, 2, f);
		rightHand.setRotationPoint(-5.0f, 28.0f, 0.0f);
		rightArm.addChild(rightHand);
		leftArm = new ModelRenderer(this, 96, 28);
		leftArm.mirror = true;
		leftArm.addBox(0.0f, 0.0f, -4.0f, 8, 12, 8, f);
		leftArm.setTextureOffset(112, 48).addBox(3.0f, 12.0f, -2.0f, 4, 16, 4, f);
		leftArm.setRotationPoint(8.0f, -38.0f, 0.0f);
		trunk.addChild(leftArm);
		leftHand = new ModelRenderer(this, 102, 68);
		leftHand.mirror = true;
		leftHand.addBox(-2.5f, 0.0f, -4.0f, 5, 16, 8, f);
		leftHand.setTextureOffset(102, 92).addBox(-1.0f, 16.0f, -4.0f, 3, 10, 2, f);
		leftHand.setTextureOffset(112, 92).addBox(0.0f, 16.0f, -1.0f, 2, 8, 2, f);
		leftHand.setTextureOffset(120, 92).addBox(0.0f, 16.0f, 2.0f, 2, 6, 2, f);
		leftHand.setRotationPoint(5.0f, 28.0f, 0.0f);
		leftArm.addChild(leftHand);
		rightLeg = new ModelRenderer(this, 0, 60);
		rightLeg.addBox(-7.0f, -4.0f, -4.0f, 6, 22, 8, f);
		rightLeg.setRotationPoint(-4.0f, -12.0f, 0.0f);
		rightFoot = new ModelRenderer(this, 28, 60);
		rightFoot.addBox(-4.0f, 0.0f, -5.0f, 8, 12, 10, f);
		rightFoot.setTextureOffset(0, 90).addBox(-5.0f, 12.0f, -7.0f, 10, 6, 15, f);
		rightFoot.setTextureOffset(0, 111).addBox(2.0f, 13.0f, -16.0f, 3, 5, 9, f);
		rightFoot.setTextureOffset(24, 113).addBox(-2.0f, 14.0f, -15.0f, 3, 4, 8, f);
		rightFoot.setTextureOffset(46, 115).addBox(-5.0f, 15.0f, -14.0f, 2, 3, 7, f);
		rightFoot.setRotationPoint(-4.0f, 18.0f, 0.0f);
		rightLeg.addChild(rightFoot);
		leftLeg = new ModelRenderer(this, 0, 60);
		leftLeg.mirror = true;
		leftLeg.addBox(1.0f, -4.0f, -4.0f, 6, 22, 8, f);
		leftLeg.setRotationPoint(4.0f, -12.0f, 0.0f);
		leftFoot = new ModelRenderer(this, 28, 60);
		leftFoot.mirror = true;
		leftFoot.addBox(-4.0f, 0.0f, -5.0f, 8, 12, 10, f);
		leftFoot.setTextureOffset(0, 90).addBox(-5.0f, 12.0f, -7.0f, 10, 6, 15, f);
		leftFoot.setTextureOffset(0, 111).addBox(-5.0f, 13.0f, -16.0f, 3, 5, 9, f);
		leftFoot.setTextureOffset(24, 113).addBox(-1.0f, 14.0f, -15.0f, 3, 4, 8, f);
		leftFoot.setTextureOffset(46, 115).addBox(3.0f, 15.0f, -14.0f, 2, 3, 7, f);
		leftFoot.setRotationPoint(4.0f, 18.0f, 0.0f);
		leftLeg.addChild(leftFoot);
		branches = new ModelRenderer(this, 0, 0);
		branches.setRotationPoint(0.0f, -48.0f, 0.0f);
		ModelRenderer branch1 = new ModelRenderer(this, 80, 16);
		branch1.addBox(-1.5f, -28.0f, -1.5f, 3, 32, 3, f);
		branch1.setTextureOffset(80, 0).addBox(-3.5f, -32.0f, -3.5f, 7, 7, 7, f);
		branch1.setRotationPoint(-1.0f, 0.0f, 0.0f);
		setRotation(branch1, -7.0f, 17.0f, 0.0f);
		branches.addChild(branch1);
		ModelRenderer branch1twig1 = new ModelRenderer(this, 80, 16);
		branch1twig1.addBox(-7.5f, -22.0f, -1.5f, 1, 12, 1, f);
		branch1twig1.setTextureOffset(80, 0).addBox(-8.5f, -23.0f, -2.5f, 3, 3, 3, f);
		branch1twig1.setRotationPoint(1.0f, -5.0f, -7.0f);
		setRotation(branch1twig1, -50.0f, 25.0f, 15.0f);
		branches.addChild(branch1twig1);
		ModelRenderer branch1twig2 = new ModelRenderer(this, 80, 16);
		branch1twig2.addBox(-14.0f, -26.0f, -5.5f, 2, 12, 2, f);
		branch1twig2.setTextureOffset(80, 0).addBox(-15.5f, -28.0f, -7.0f, 5, 5, 5, f);
		branch1twig2.setRotationPoint(-2.0f, 1.0f, 7.0f);
		setRotation(branch1twig2, 10.0f, 10.0f, 50.0f);
		branches.addChild(branch1twig2);
		ModelRenderer branch1twig3 = new ModelRenderer(this, 80, 16);
		branch1twig3.addBox(-7.5f, -24.0f, -3.5f, 1, 12, 1, f);
		branch1twig3.setTextureOffset(80, 0).addBox(-8.5f, -25.0f, -4.5f, 3, 3, 3, f);
		branch1twig3.setRotationPoint(8.0f, -6.0f, 9.0f);
		setRotation(branch1twig3, 15.0f, -20.0f, -30.0f);
		branches.addChild(branch1twig3);
		ModelRenderer branch2 = new ModelRenderer(this, 80, 16);
		branch2.addBox(-0.5f, -10.0f, -0.5f, 1, 14, 1, f);
		branch2.setTextureOffset(80, 0).addBox(-1.5f, -12.0f, -1.5f, 3, 3, 3, f);
		branch2.setRotationPoint(6.0f, 0.0f, 2.0f);
		setRotation(branch2, -20.0f, 42.0f, 0.0f);
		branches.addChild(branch2);
		ModelRenderer branch3 = new ModelRenderer(this, 80, 16);
		branch3.addBox(-1.0f, -16.0f, -1.0f, 2, 20, 2, f);
		branch3.setTextureOffset(80, 0).addBox(-2.5f, -18.0f, -2.5f, 5, 5, 5, f);
		branch3.setRotationPoint(3.0f, 0.0f, -3.0f);
		setRotation(branch3, 26.0f, -27.0f, 0.0f);
		branches.addChild(branch3);
		ModelRenderer branch4 = new ModelRenderer(this, 80, 16);
		branch4.addBox(-1.0f, -18.0f, -1.0f, 2, 22, 2, f);
		branch4.setTextureOffset(80, 0).addBox(-2.5f, -20.0f, -2.5f, 5, 5, 5, f);
		branch4.setRotationPoint(-5.0f, 0.0f, -4.0f);
		setRotation(branch4, 17.0f, 60.0f, 0.0f);
		branches.addChild(branch4);
		ModelRenderer branch4twig1 = new ModelRenderer(this, 80, 16);
		branch4twig1.addBox(8.5f, -21.0f, -7.5f, 1, 12, 1, f);
		branch4twig1.setTextureOffset(80, 0).addBox(7.0f, -22.0f, -9.0f, 4, 4, 4, f);
		branch4twig1.setRotationPoint(-12.0f, -6.0f, 8.0f);
		setRotation(branch4twig1, 50.0f, 15.0f, -10.0f);
		branches.addChild(branch4twig1);
		ModelRenderer branch5 = new ModelRenderer(this, 80, 16);
		branch5.addBox(-1.0f, -24.0f, -1.0f, 2, 28, 2, f);
		branch5.setTextureOffset(80, 0).addBox(-2.0f, -25.0f, -2.0f, 4, 4, 4, f);
		branch5.setRotationPoint(-5.0f, 0.0f, 3.0f);
		setRotation(branch5, -20.0f, -36.0f, 0.0f);
		branches.addChild(branch5);
		trunk.addChild(branches);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityEnt ent = (LOTREntityEnt) entity;
		setRotationAngles(f, f1, f2, f3, f4, f5, ent);
		trunk.render(f5);
		rightLeg.render(f5);
		leftLeg.render(f5);
		if (ent != null) {
			int numBranches = ent.getExtraHeadBranches();
			for (int l = 0; l < numBranches; ++l) {
				GL11.glPushMatrix();
				trunk.postRender(f5);
				float angle = 90.0f + (float) l / numBranches * 360.0f;
				GL11.glTranslatef(0.0f, -2.7f, 0.0f);
				GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-60.0f, 1.0f, 0.0f, 0.0f);
				GL11.glTranslatef(0.0f, 2.6f, 0.0f);
				branches.render(f5);
				GL11.glPopMatrix();
			}
		}
	}

	@Override
	public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		trunk.render(f5);
	}

	public void setRotation(ModelRenderer part, float x, float y, float z) {
		part.rotateAngleX = (float) Math.toRadians(x);
		part.rotateAngleY = (float) Math.toRadians(y);
		part.rotateAngleZ = (float) Math.toRadians(z);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		boolean healing;
		LOTREntityEnt ent = (LOTREntityEnt) entity;
		trunk.rotateAngleX = 0.0f;
		healing = ent != null && ent.isHealingSapling();
		if (healing) {
			trunk.rotateAngleX = 0.3f + MathHelper.sin(f2 * 0.08f) * 0.1f;
		}
		eyeRight.showModel = ent != null && ent.eyesClosed > 0;
		eyeLeft.showModel = ent != null && ent.eyesClosed > 0;
		if (ent != null && ent.hurtTime > 0) {
			browRight.rotateAngleZ = 0.5235987755982988f;
			browLeft.rotateAngleZ = -browRight.rotateAngleZ;
			browLeft.rotationPointY = -40.0f;
			browRight.rotationPointY = -40.0f;
		} else {
			browRight.rotateAngleZ = 0.17453292519943295f;
			browLeft.rotateAngleZ = -browRight.rotateAngleZ;
			browLeft.rotationPointY = -39.0f;
			browRight.rotationPointY = -39.0f;
		}
		rightArm.rotateAngleX = 0.0f;
		rightHand.rotateAngleX = 0.0f;
		leftArm.rotateAngleX = 0.0f;
		leftHand.rotateAngleX = 0.0f;
		rightArm.rotateAngleZ = 0.0f;
		leftArm.rotateAngleZ = 0.0f;
		if (onGround > -9990.0f) {
			float f6 = 1.0f - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0f - f6;
			float f8 = MathHelper.sin(f6 * 3.1415927f);
			float f9 = MathHelper.sin(onGround * 3.1415927f) * -(trunk.rotateAngleX - 0.7f) * 0.75f;
			rightArm.rotateAngleX -= f8 * 1.2f + f9;
			leftArm.rotateAngleX -= f8 * 1.2f + f9;
		}
		rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		rightArm.rotateAngleX += MathHelper.cos(f * 0.3f + 3.1415927f) * 0.8f * f1;
		leftArm.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.8f * f1;
		if (healing) {
			float armHealing = -0.5f + MathHelper.sin(f2 * 0.2f) * 0.4f;
			rightArm.rotateAngleX += armHealing;
			leftArm.rotateAngleX += armHealing;
		}
		if (rightArm.rotateAngleX < 0.0f) {
			rightHand.rotateAngleX = rightArm.rotateAngleX / 3.1415927f * 2.5f;
		}
		if (leftArm.rotateAngleX < 0.0f) {
			leftHand.rotateAngleX = leftArm.rotateAngleX / 3.1415927f * 2.5f;
		}
		rightLeg.rotateAngleX = 0.0f;
		rightFoot.rotateAngleX = 0.0f;
		leftLeg.rotateAngleX = 0.0f;
		leftFoot.rotateAngleX = 0.0f;
		rightLeg.rotateAngleX += MathHelper.cos(f * 0.3f + 3.1415927f) * 1.2f * f1;
		leftLeg.rotateAngleX += MathHelper.cos(f * 0.3f) * 1.2f * f1;
		if (rightLeg.rotateAngleX < 0.0f) {
			rightFoot.rotateAngleX = -(rightLeg.rotateAngleX / 3.1415927f) * 2.5f;
		}
		if (leftLeg.rotateAngleX < 0.0f) {
			leftFoot.rotateAngleX = -(leftLeg.rotateAngleX / 3.1415927f) * 2.5f;
		}
	}
}
