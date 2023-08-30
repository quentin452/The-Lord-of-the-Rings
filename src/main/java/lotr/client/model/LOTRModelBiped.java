package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBiped extends ModelBiped {
	public boolean setup;
	public float base_bodyRotateX;
	public float base_armX;
	public float base_armY;
	public float base_armZ;
	public float base_legY;
	public float base_legZ;
	public float base_headY;
	public float base_headZ;
	public float base_bodyY;
	public float base_bodyZ;

	public LOTRModelBiped() {
	}

	public LOTRModelBiped(float f) {
		super(f);
	}

	public LOTRModelBiped(float f, float f1, int width, int height) {
		super(f, f1, width, height);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		LOTREntityNPC npc;
		float f6;
		float f7;
		if (!setup) {
			setupModelBiped();
			setup = true;
		}
		bipedHead.rotateAngleY = f3 / 57.295776f;
		bipedHead.rotateAngleX = f4 / 57.295776f;
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
		bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
		bipedRightArm.rotateAngleZ = 0.0f;
		bipedLeftArm.rotateAngleZ = 0.0f;
		bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		if (entity instanceof LOTREntityNPC) {
			bipedRightLeg.rotateAngleY = 0.08726646259971647f;
			bipedLeftLeg.rotateAngleY = -0.08726646259971647f;
		}
		if (isRiding) {
			bipedRightArm.rotateAngleX -= 0.62831855f;
			bipedLeftArm.rotateAngleX -= 0.62831855f;
			bipedRightLeg.rotateAngleX = -1.2566371f;
			bipedLeftLeg.rotateAngleX = -1.2566371f;
			bipedRightLeg.rotateAngleY = 0.31415927f;
			bipedLeftLeg.rotateAngleY = -0.31415927f;
		}
		if (heldItemLeft != 0) {
			bipedLeftArm.rotateAngleX = bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * heldItemLeft;
		}
		if (heldItemRight != 0) {
			bipedRightArm.rotateAngleX = bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * heldItemRight;
		}
		bipedRightArm.rotateAngleY = 0.0f;
		bipedLeftArm.rotateAngleY = 0.0f;
		if (onGround > -9990.0f) {
			f6 = onGround;
			bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f * 2.0f) * 0.2f;
			bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * base_armX;
			bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * base_armX;
			bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * base_armX;
			bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * base_armX;
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
			bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
			f6 = 1.0f - onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0f - f6;
			f7 = MathHelper.sin(f6 * 3.1415927f);
			float f8 = MathHelper.sin(onGround * 3.1415927f) * -(bipedHead.rotateAngleX - 0.7f) * 0.75f;
			bipedRightArm.rotateAngleX = (float) (bipedRightArm.rotateAngleX - (f7 * 1.2 + f8));
			bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0f;
			bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.1415927f) * -0.4f;
		}
		if (isSneak) {
			bipedBody.rotateAngleX = base_bodyRotateX + 0.5f;
			bipedRightArm.rotateAngleX += 0.4f;
			bipedLeftArm.rotateAngleX += 0.4f;
			bipedRightLeg.rotationPointZ = base_legZ + 4.0f;
			bipedLeftLeg.rotationPointZ = base_legZ + 4.0f;
			bipedRightLeg.rotationPointY = base_legY - 3.0f;
			bipedLeftLeg.rotationPointY = base_legY - 3.0f;
			bipedHead.rotationPointY = base_headY + 1.0f;
			bipedHeadwear.rotationPointY = base_headY + 1.0f;
		} else {
			bipedBody.rotateAngleX = base_bodyRotateX;
			bipedRightLeg.rotationPointZ = base_legZ + 0.1f;
			bipedLeftLeg.rotationPointZ = base_legZ + 0.1f;
			bipedRightLeg.rotationPointY = base_legY;
			bipedLeftLeg.rotationPointY = base_legY;
			bipedHead.rotationPointY = base_headY;
			bipedHeadwear.rotationPointY = base_headY;
		}
		bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
		bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
		bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
		if (aimedBow) {
			f6 = 0.0f;
			f7 = 0.0f;
			bipedRightArm.rotateAngleZ = 0.0f;
			bipedLeftArm.rotateAngleZ = 0.0f;
			bipedRightArm.rotateAngleY = -(0.1f - f6 * 0.6f) + bipedHead.rotateAngleY;
			bipedLeftArm.rotateAngleY = 0.1f - f6 * 0.6f + bipedHead.rotateAngleY + 0.4f;
			bipedRightArm.rotateAngleX = -1.5707964f + bipedHead.rotateAngleX;
			bipedLeftArm.rotateAngleX = -1.5707964f + bipedHead.rotateAngleX;
			bipedRightArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
			bipedLeftArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
			bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
			bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
			bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
			bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
		}
		if (entity instanceof LOTREntityNPC && (npc = (LOTREntityNPC) entity).isDrunkard()) {
			float f62 = f2 / 80.0f;
			float f72 = (f2 + 40.0f) / 80.0f;
			float f8 = MathHelper.sin(f62 * 6.2831855f) * 0.5f;
			float f9 = MathHelper.sin(f72 * 6.2831855f) * 0.5f;
			bipedHead.rotateAngleX += f8;
			bipedHead.rotateAngleY += f9;
			bipedHeadwear.rotateAngleX += f8;
			bipedHeadwear.rotateAngleY += f9;
			if (npc.getHeldItem() != null) {
				bipedRightArm.rotateAngleX = -1.0471976f;
			}
		}
		boolean bowing = false;
		float bowAmount = 0.0f;
		if (entity instanceof LOTREntityElf) {
			bowAmount = ((LOTREntityElf) entity).getBowingAmount(LOTRTickHandlerClient.renderTick);
			bowing = bowAmount != 0.0f;
		}
		if (bowing) {
			float bowAmountRad = (float) Math.toRadians(bowAmount * 30.0f);
			float bowCos = MathHelper.cos(bowAmountRad);
			float bowSin = MathHelper.sin(bowAmountRad);
			bipedHead.rotationPointY = base_headY + 12.0f * (1.0f - bowCos);
			bipedHead.rotationPointZ = base_headY - 12.0f * bowSin;
			bipedHeadwear.rotationPointY = bipedHead.rotationPointY;
			bipedHeadwear.rotationPointZ = bipedHead.rotationPointZ;
			bipedBody.rotationPointY = base_bodyY + 12.0f * (1.0f - bowCos);
			bipedBody.rotationPointZ = base_bodyZ - 12.0f * bowSin;
			bipedRightArm.rotationPointY = base_armY + 10.0f * (1.0f - bowCos);
			bipedRightArm.rotationPointZ = base_armY - 12.0f * bowSin;
			bipedLeftArm.rotationPointY = bipedRightArm.rotationPointY;
			bipedLeftArm.rotationPointZ = bipedRightArm.rotationPointZ;
			bipedHead.rotateAngleX = bowAmountRad;
			bipedHeadwear.rotateAngleX = bowAmountRad;
			bipedBody.rotateAngleX = bowAmountRad;
			bipedRightArm.rotateAngleX = bowAmountRad;
			bipedLeftArm.rotateAngleX = bowAmountRad;
		} else {
			if (!isSneak) {
				bipedHead.rotationPointY = base_headY;
				bipedHead.rotationPointZ = base_headZ;
				bipedHeadwear.rotationPointY = base_headY;
				bipedHeadwear.rotationPointZ = base_headZ;
			}
			bipedBody.rotationPointY = base_bodyY;
			bipedBody.rotationPointZ = base_bodyZ;
			bipedRightArm.rotationPointY = base_armY;
			bipedRightArm.rotationPointZ = base_armZ;
			bipedLeftArm.rotationPointY = base_armY;
			bipedLeftArm.rotationPointZ = base_armZ;
		}
	}

	public void setupModelBiped() {
		base_bodyRotateX = bipedBody.rotateAngleX;
		base_armX = Math.abs(bipedRightArm.rotationPointX);
		base_armY = bipedRightArm.rotationPointY;
		base_armZ = bipedRightArm.rotationPointZ;
		base_legY = bipedRightLeg.rotationPointY;
		base_legZ = bipedRightLeg.rotationPointZ;
		base_headY = bipedHead.rotationPointY;
		base_headZ = bipedHead.rotationPointZ;
		base_bodyY = bipedBody.rotationPointY;
		base_bodyZ = bipedBody.rotationPointZ;
	}
}
