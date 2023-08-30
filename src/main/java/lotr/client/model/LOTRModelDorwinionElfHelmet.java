package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelDorwinionElfHelmet extends LOTRModelBiped {
	public LOTRModelDorwinionElfHelmet() {
		this(0.0f);
	}

	public LOTRModelDorwinionElfHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(20, 16).addBox(0.0f, -10.0f, 4.0f, 0, 10, 4, 0.0f);
		bipedHeadwear = new ModelRenderer(this, 32, 0);
		bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f + 0.5f);
		ModelRenderer crest = new ModelRenderer(this, 0, 16);
		crest.setRotationPoint(0.0f, -f, 0.0f);
		crest.addBox(-1.0f, -11.0f, -6.0f, 2, 5, 8, 0.0f);
		crest.rotateAngleX = -0.2617993877991494f;
		bipedHead.addChild(crest);
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
