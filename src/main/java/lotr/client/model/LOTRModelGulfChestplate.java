package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGulfChestplate extends LOTRModelBiped {
	public LOTRModelGulfChestplate() {
		this(0.0f);
	}

	public LOTRModelGulfChestplate(float f) {
		super(f);
		bipedBody = new ModelRenderer(this, 16, 16);
		bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
		bipedBody.setTextureOffset(16, 0);
		bipedBody.addBox(-4.0f, 0.0f, -3.0f - f, 8, 3, 1, 0.0f);
		ModelRenderer chestHorn1 = new ModelRenderer(this, 0, 0);
		chestHorn1.setRotationPoint(-2.5f - f, 2.5f, -3.0f - f);
		chestHorn1.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
		chestHorn1.rotateAngleX = -0.4363323129985824f;
		chestHorn1.rotateAngleZ = 0.4363323129985824f;
		bipedBody.addChild(chestHorn1);
		ModelRenderer chestHorn2 = new ModelRenderer(this, 0, 0);
		chestHorn2.setRotationPoint(0.0f, 3.0f, -3.0f - f);
		chestHorn2.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
		chestHorn2.rotateAngleX = -0.4363323129985824f;
		chestHorn2.rotateAngleZ = 0.0f;
		bipedBody.addChild(chestHorn2);
		ModelRenderer chestHorn3 = new ModelRenderer(this, 0, 0);
		chestHorn3.setRotationPoint(2.5f + f, 2.5f, -3.0f - f);
		chestHorn3.addBox(-0.5f, 0.0f, -0.5f, 1, 2, 1, 0.0f);
		chestHorn3.rotateAngleX = -0.4363323129985824f;
		chestHorn3.rotateAngleZ = -0.4363323129985824f;
		bipedBody.addChild(chestHorn3);
		bipedRightArm = new ModelRenderer(this, 40, 16);
		bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
		bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
		bipedRightArm.setTextureOffset(40, 0);
		bipedRightArm.addBox(-4.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
		ModelRenderer rightHorn1 = new ModelRenderer(this, 4, 0);
		rightHorn1.setRotationPoint(-2.5f, -2.0f - f, 0.0f);
		rightHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
		rightHorn1.rotateAngleZ = -0.17453292519943295f;
		bipedRightArm.addChild(rightHorn1);
		ModelRenderer rightHorn2 = new ModelRenderer(this, 8, 0);
		rightHorn2.setRotationPoint(-0.5f, -2.0f - f, 0.0f);
		rightHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
		rightHorn2.rotateAngleZ = -0.17453292519943295f;
		bipedRightArm.addChild(rightHorn2);
		bipedLeftArm = new ModelRenderer(this, 40, 16);
		bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
		bipedLeftArm.setTextureOffset(40, 0);
		bipedLeftArm.addBox(-1.0f, -2.0f - f, -2.5f, 5, 1, 5, 0.0f);
		ModelRenderer leftHorn1 = new ModelRenderer(this, 4, 0);
		leftHorn1.setRotationPoint(2.5f, -2.0f - f, 0.0f);
		leftHorn1.mirror = true;
		leftHorn1.addBox(-0.5f, -2.0f, -0.5f, 1, 2, 1, 0.0f);
		leftHorn1.rotateAngleZ = 0.17453292519943295f;
		bipedLeftArm.addChild(leftHorn1);
		ModelRenderer leftHorn2 = new ModelRenderer(this, 8, 0);
		leftHorn2.setRotationPoint(0.5f, -2.0f - f, 0.0f);
		leftHorn2.mirror = true;
		leftHorn2.addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1, 0.0f);
		leftHorn2.rotateAngleZ = 0.17453292519943295f;
		bipedLeftArm.addChild(leftHorn2);
		bipedHead.cubeList.clear();
		bipedHeadwear.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
