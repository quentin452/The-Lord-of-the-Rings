package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMoredainLionHelmet extends LOTRModelBiped {
	public ModelRenderer panelRight;
	public ModelRenderer panelLeft;
	public ModelRenderer panelBack;
	public ModelRenderer panelTop;

	public LOTRModelMoredainLionHelmet() {
		this(0.0f);
	}

	public LOTRModelMoredainLionHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.setTextureOffset(34, 16).addBox(-4.5f, -9.0f, -2.5f, 9, 2, 5, f);
		bipedHead.setTextureOffset(0, 17).addBox(-2.5f, -10.0f, -7.0f, 5, 3, 12, f);
		bipedHead.setTextureOffset(34, 23).addBox(-1.0f, -10.4f, -7.2f, 2, 2, 7, f);
		bipedHead.setTextureOffset(0, 0).addBox(-2.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
		bipedHead.mirror = true;
		bipedHead.addBox(1.0f, -8.0f, -6.8f - f, 1, 3, 1, 0.0f);
		panelRight = new ModelRenderer(this, 32, 0);
		panelRight.addBox(-5.0f - f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
		panelRight.rotateAngleZ = 0.06981317007977318f;
		panelLeft = new ModelRenderer(this, 32, 0);
		panelLeft.mirror = true;
		panelLeft.addBox(5.0f + f, -8.0f, -3.0f, 0, 8, 8, 0.0f);
		panelLeft.rotateAngleZ = -0.06981317007977318f;
		panelBack = new ModelRenderer(this, 44, 0);
		panelBack.addBox(-4.0f, -8.0f, 4.8f + f, 8, 10, 0, 0.0f);
		panelBack.rotateAngleX = 0.06981317007977318f;
		panelTop = new ModelRenderer(this, 52, 25);
		panelTop.addBox(-2.5f, -16.0f - f, -2.0f, 5, 7, 0, 0.0f);
		panelTop.rotateAngleX = -0.17453292519943295f;
		bipedHead.addChild(panelRight);
		bipedHead.addChild(panelLeft);
		bipedHead.addChild(panelBack);
		bipedHead.addChild(panelTop);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
