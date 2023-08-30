package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUrukHelmet extends LOTRModelBiped {
	public ModelRenderer crest;
	public ModelRenderer jaw;

	public LOTRModelUrukHelmet() {
		this(0.0f);
	}

	public LOTRModelUrukHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		crest = new ModelRenderer(this, 0, 22);
		crest.addBox(-10.0f, -16.0f, -1.0f, 20, 10, 0, 0.0f);
		crest.rotateAngleX = -0.17453292519943295f;
		bipedHead.addChild(crest);
		jaw = new ModelRenderer(this, 0, 16);
		jaw.addBox(-6.0f, 2.0f, -4.0f, 12, 6, 0, 0.0f);
		jaw.rotateAngleX = -1.0471975511965976f;
		bipedHead.addChild(jaw);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
