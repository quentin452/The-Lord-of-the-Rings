package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackUrukHelmet extends LOTRModelBiped {
	public ModelRenderer crest;

	public LOTRModelBlackUrukHelmet() {
		this(0.0f);
	}

	public LOTRModelBlackUrukHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		crest = new ModelRenderer(this, 32, 0);
		crest.addBox(-8.0f, -16.0f, -3.0f, 16, 10, 0, 0.0f);
		crest.rotateAngleX = -0.3490658503988659f;
		bipedHead.addChild(crest);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
