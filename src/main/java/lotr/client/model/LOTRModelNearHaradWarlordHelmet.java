package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelNearHaradWarlordHelmet extends LOTRModelBiped {
	public ModelRenderer stickRight;
	public ModelRenderer stickCentre;
	public ModelRenderer stickLeft;

	public LOTRModelNearHaradWarlordHelmet() {
		this(0.0f);
	}

	public LOTRModelNearHaradWarlordHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.setTextureOffset(6, 24).addBox(-2.5f, -3.0f, 4.1f, 5, 3, 2, 0.0f);
		bipedHead.setTextureOffset(0, 16).addBox(-9.0f, -16.0f, 5.5f, 18, 8, 0, 0.0f);
		stickRight = new ModelRenderer(this, 36, 0);
		stickRight.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
		stickRight.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
		stickRight.rotateAngleZ = -0.4886921905584123f;
		bipedHead.addChild(stickRight);
		stickCentre = new ModelRenderer(this, 36, 0);
		stickCentre.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
		stickCentre.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
		stickCentre.rotateAngleZ = 0.0f;
		bipedHead.addChild(stickCentre);
		stickLeft = new ModelRenderer(this, 36, 0);
		stickLeft.addBox(-0.5f, -19.0f, 5.0f, 1, 18, 1, 0.0f);
		stickLeft.setTextureOffset(0, 24).addBox(-1.5f, -24.0f, 5.5f, 3, 5, 0, 0.0f);
		stickLeft.rotateAngleZ = 0.4886921905584123f;
		bipedHead.addChild(stickLeft);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
