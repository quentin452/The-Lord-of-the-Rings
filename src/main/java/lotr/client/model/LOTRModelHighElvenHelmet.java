package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHighElvenHelmet extends LOTRModelBiped {
	public ModelRenderer crest;

	public LOTRModelHighElvenHelmet() {
		this(0.0f);
	}

	public LOTRModelHighElvenHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-0.5f, -11.0f, -2.0f, 1, 3, 1, 0.0f);
		bipedHead.setTextureOffset(0, 4).addBox(-0.5f, -10.0f, 2.0f, 1, 2, 1, 0.0f);
		crest = new ModelRenderer(this, 32, 0);
		crest.addBox(-1.0f, -11.0f, -8.0f, 2, 1, 11, 0.0f);
		crest.setTextureOffset(32, 12).addBox(-1.0f, -10.0f, -8.0f, 2, 1, 1, 0.0f);
		crest.rotateAngleX = -0.2792526803190927f;
		bipedHead.addChild(crest);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
