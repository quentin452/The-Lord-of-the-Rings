package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGemsbokHelmet extends LOTRModelBiped {
	public ModelRenderer hornRight;
	public ModelRenderer hornLeft;

	public LOTRModelGemsbokHelmet() {
		this(0.0f);
	}

	public LOTRModelGemsbokHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		hornRight = new ModelRenderer(this, 32, 0);
		hornRight.addBox(-4.9f, -7.0f, 7.5f, 1, 1, 13);
		hornLeft = new ModelRenderer(this, 32, 0);
		hornLeft.mirror = true;
		hornLeft.addBox(3.9f, -7.0f, 7.5f, 1, 1, 13);
		hornRight.rotateAngleX = hornLeft.rotateAngleX = 0.3490658503988659f;
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
