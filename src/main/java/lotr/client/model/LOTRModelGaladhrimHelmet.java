package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGaladhrimHelmet extends LOTRModelBiped {
	public LOTRModelGaladhrimHelmet() {
		this(0.0f);
	}

	public LOTRModelGaladhrimHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		ModelRenderer horn = new ModelRenderer(this, 32, 0);
		horn.addBox(-0.5f, -9.0f - f, 2.0f - f, 1, 3, 3, 0.0f);
		horn.setTextureOffset(32, 6).addBox(-0.5f, -10.0f - f, 3.5f - f, 1, 1, 3, 0.0f);
		horn.setTextureOffset(32, 10).addBox(-0.5f, -11.0f - f, 5.5f - f, 1, 1, 4, 0.0f);
		horn.rotateAngleX = 0.7853981633974483f;
		bipedHead.addChild(horn);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
