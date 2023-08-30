package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGundabadUrukHelmet extends LOTRModelBiped {
	public LOTRModelGundabadUrukHelmet() {
		this(0.0f);
	}

	public LOTRModelGundabadUrukHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		ModelRenderer hornRight = new ModelRenderer(this, 32, 0);
		hornRight.setRotationPoint(-f, -f, -f);
		hornRight.addBox(-7.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
		hornRight.rotateAngleZ = 0.10471975511965978f;
		ModelRenderer hornLeft = new ModelRenderer(this, 32, 0);
		hornLeft.setRotationPoint(f, -f, -f);
		hornLeft.mirror = true;
		hornLeft.addBox(4.0f, -12.0f, 0.5f, 3, 8, 0, 0.0f);
		hornLeft.rotateAngleZ = -0.10471975511965978f;
		bipedHead.addChild(hornRight);
		bipedHead.addChild(hornLeft);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
