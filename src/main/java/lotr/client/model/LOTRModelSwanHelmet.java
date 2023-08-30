package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSwanHelmet extends LOTRModelBiped {
	public ModelRenderer wingRight;
	public ModelRenderer wingLeft;

	public LOTRModelSwanHelmet() {
		this(0.0f);
	}

	public LOTRModelSwanHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(32, 0).addBox(-0.5f, -9.0f, -3.5f, 1, 1, 7, f);
		wingRight = new ModelRenderer(this, 0, 16);
		wingRight.addBox(-4.0f - f, -6.0f, 1.0f + f, 1, 1, 9, 0.0f);
		wingRight.setTextureOffset(20, 16).addBox(-3.5f - f, -5.0f, 1.9f + f, 0, 6, 8, 0.0f);
		wingLeft = new ModelRenderer(this, 0, 16);
		wingLeft.mirror = true;
		wingLeft.addBox(3.0f + f, -6.0f, 1.0f + f, 1, 1, 9, 0.0f);
		wingLeft.setTextureOffset(20, 16).addBox(3.5f + f, -5.0f, 1.9f + f, 0, 6, 8, 0.0f);
		bipedHead.addChild(wingRight);
		bipedHead.addChild(wingLeft);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float wingYaw = -0.4363323129985824f;
		float wingPitch = 0.3490658503988659f;
		wingRight.rotateAngleY = wingYaw;
		wingLeft.rotateAngleY = -wingYaw;
		wingRight.rotateAngleX = wingPitch;
		wingLeft.rotateAngleX = wingPitch;
	}
}
