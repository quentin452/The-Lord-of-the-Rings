package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LOTRModelSwanChestplate extends LOTRModelBiped {
	public ModelRenderer[] wingsRight;
	public ModelRenderer[] wingsLeft;

	public LOTRModelSwanChestplate() {
		this(0.0f);
	}

	public LOTRModelSwanChestplate(float f) {
		super(f);
		ModelRenderer wing;
		int i;
		bipedBody = new ModelRenderer(this, 0, 0);
		bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
		int wings = 12;
		wingsRight = new ModelRenderer[wings];
		for (i = 0; i < wingsRight.length; ++i) {
			wing = new ModelRenderer(this, 0, 16);
			wing.setRotationPoint(-2.0f, 0.0f, 0.0f);
			wing.addBox(-2.0f, 0.0f, 0.0f, 2, 1, 1, 0.0f);
			wing.setTextureOffset(6, 16).addBox(-2.0f, 1.0f, 0.5f, 2, 10, 0, 0.0f);
			wingsRight[i] = wing;
		}
		for (i = 0; i < wingsRight.length - 1; ++i) {
			wingsRight[i].addChild(wingsRight[i + 1]);
		}
		wingsRight[0].setRotationPoint(-2.0f, 1.0f, 1.0f);
		bipedBody.addChild(wingsRight[0]);
		wingsLeft = new ModelRenderer[wings];
		for (i = 0; i < wingsLeft.length; ++i) {
			wing = new ModelRenderer(this, 0, 16);
			wing.setRotationPoint(2.0f, 0.0f, 0.0f);
			wing.mirror = true;
			wing.addBox(0.0f, 0.0f, 0.0f, 2, 1, 1, 0.0f);
			wing.setTextureOffset(6, 16).addBox(0.0f, 1.0f, 0.5f, 2, 10, 0, 0.0f);
			wingsLeft[i] = wing;
		}
		for (i = 0; i < wingsLeft.length - 1; ++i) {
			wingsLeft[i].addChild(wingsLeft[i + 1]);
		}
		wingsLeft[0].setRotationPoint(2.0f, 1.0f, 1.0f);
		bipedBody.addChild(wingsLeft[0]);
		bipedRightArm = new ModelRenderer(this, 24, 0);
		bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
		bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
		bipedLeftArm = new ModelRenderer(this, 24, 0);
		bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
		bipedHead.cubeList.clear();
		bipedHeadwear.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float motion = f1;
		float motionPhase = f;
		if (entity != null && entity.ridingEntity instanceof EntityLivingBase) {
			EntityLivingBase mount = (EntityLivingBase) entity.ridingEntity;
			float tick = LOTRTickHandlerClient.renderTick;
			motion = mount.prevLimbSwingAmount + (mount.limbSwingAmount - mount.prevLimbSwingAmount) * tick;
			motionPhase = mount.limbSwing - mount.limbSwingAmount * (1.0f - tick);
			motion *= 1.5f;
			motionPhase *= 2.0f;
		}
		float wingAngleBase = 0.17453292519943295f;
		wingAngleBase += MathHelper.sin(f2 * 0.02f) * 0.01f;
		wingAngleBase += MathHelper.sin(motionPhase * 0.2f) * 0.03f * motion;
		float wingYaw = 0.8726646259971648f;
		wingYaw += MathHelper.sin(f2 * 0.03f) * 0.05f;
		wingYaw += MathHelper.sin(motionPhase * 0.25f) * 0.12f * motion;
		for (int i = 0; i < wingsRight.length; ++i) {
			float wingAngle;
			float factor = i + 1;
			wingsRight[i].rotateAngleZ = wingAngle = wingAngleBase / (factor / 3.4f);
			wingsLeft[i].rotateAngleZ = -wingAngle;
		}
		wingsRight[0].rotateAngleY = MathHelper.sin(wingYaw);
		wingsRight[0].rotateAngleX = MathHelper.cos(wingYaw);
		wingsLeft[0].rotateAngleY = MathHelper.sin(-wingYaw);
		wingsLeft[0].rotateAngleX = MathHelper.cos(-wingYaw);
	}
}
