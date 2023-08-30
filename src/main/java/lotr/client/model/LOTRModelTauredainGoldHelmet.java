package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainGoldHelmet extends LOTRModelBiped {
	public LOTRModelTauredainGoldHelmet() {
		this(0.0f);
	}

	public LOTRModelTauredainGoldHelmet(float f) {
		super(f);
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		ModelRenderer crest = new ModelRenderer(this, 32, 0);
		crest.setRotationPoint(0.0f, -f, 0.0f);
		crest.addBox(-7.0f, -20.0f, 0.0f, 14, 12, 0, 0.0f);
		crest.rotateAngleX = -0.06981317007977318f;
		ModelRenderer tusks1 = new ModelRenderer(this, 0, 16);
		tusks1.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
		tusks1.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
		tusks1.rotateAngleX = 0.3490658503988659f;
		tusks1.rotateAngleY = 0.5235987755982988f;
		ModelRenderer tusks2 = new ModelRenderer(this, 0, 16);
		tusks2.setRotationPoint(-3.5f - f, 0.0f + f, -4.0f - f);
		tusks2.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
		tusks2.rotateAngleX = 0.3490658503988659f;
		tusks2.rotateAngleY = -0.3490658503988659f;
		ModelRenderer tusks3 = new ModelRenderer(this, 0, 16);
		tusks3.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
		tusks3.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
		tusks3.rotateAngleX = 0.3490658503988659f;
		tusks3.rotateAngleY = 0.3490658503988659f;
		ModelRenderer tusks4 = new ModelRenderer(this, 0, 16);
		tusks4.setRotationPoint(3.5f + f, 0.0f + f, -4.0f - f);
		tusks4.addBox(0.0f, -6.0f, -5.0f, 0, 6, 6, 0.0f);
		tusks4.rotateAngleX = 0.3490658503988659f;
		tusks4.rotateAngleY = -0.5235987755982988f;
		bipedHead.addChild(crest);
		bipedHead.addChild(tusks1);
		bipedHead.addChild(tusks2);
		bipedHead.addChild(tusks3);
		bipedHead.addChild(tusks4);
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
