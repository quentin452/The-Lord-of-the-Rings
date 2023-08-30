package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelEasterlingHelmet extends LOTRModelBiped {
	public LOTRModelEasterlingHelmet() {
		this(0.0f, false);
	}

	public LOTRModelEasterlingHelmet(float f, boolean kineHorns) {
		super(f);
		if (kineHorns) {
			textureWidth = 64;
			textureHeight = 64;
		} else {
			textureWidth = 64;
			textureHeight = 32;
		}
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, f);
		bipedHead.setTextureOffset(0, 16).addBox(-5.5f, -8.5f - f, -5.5f, 11, 2, 11, 0.0f);
		bipedHead.setTextureOffset(32, 8).addBox(-3.5f, -9.5f - f, -3.5f, 7, 1, 7, 0.0f);
		bipedHead.setTextureOffset(50, 16).addBox(0.0f, -10.5f - f, -4.5f - f, 0, 3, 4, 0.0f);
		ModelRenderer horn = new ModelRenderer(this, 44, 16);
		horn.setRotationPoint(0.0f, 0.0f, 0.0f);
		horn.addBox(-0.5f, -14.0f - f, -2.0f - f, 1, 8, 2, 0.0f);
		horn.rotateAngleX = 0.3490658503988659f;
		bipedHead.addChild(horn);
		bipedHead.setTextureOffset(24, 0).addBox(-1.0f, -8.0f - f, 4.0f + f, 2, 4, 1, 0.0f);
		bipedHead.setTextureOffset(32, 2).addBox(-6.0f, -12.0f - f, 5.0f + f, 12, 4, 0, 0.0f);
		ModelRenderer crest = new ModelRenderer(this, 32, 0);
		crest.setRotationPoint(0.0f, -12.0f - f, 5.0f + f);
		crest.addBox(-6.0f, -2.0f, 0.0f, 12, 2, 0, 0.0f);
		crest.rotateAngleX = 0.5235987755982988f;
		bipedHead.addChild(crest);
		if (kineHorns) {
			ModelRenderer kineHornRight = new ModelRenderer(this, 0, 32);
			kineHornRight.setRotationPoint(-1.0f - f, -8.0f - f, 0.0f);
			kineHornRight.addBox(-7.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
			ModelRenderer kineHornRight1 = new ModelRenderer(this, 0, 38);
			kineHornRight1.setRotationPoint(-7.0f, 0.0f, 0.0f);
			kineHornRight1.addBox(-5.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
			ModelRenderer kineHornRight2 = new ModelRenderer(this, 0, 42);
			kineHornRight2.setRotationPoint(-5.0f, 0.0f, 0.0f);
			kineHornRight2.addBox(-3.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
			ModelRenderer kineHornLeft = new ModelRenderer(this, 0, 32);
			kineHornLeft.mirror = true;
			kineHornLeft.setRotationPoint(1.0f + f, -8.0f - f, 0.0f);
			kineHornLeft.addBox(0.0f, -1.5f, -1.5f, 7, 3, 3, 0.0f);
			ModelRenderer kineHornLeft1 = new ModelRenderer(this, 0, 38);
			kineHornLeft1.mirror = true;
			kineHornLeft1.setRotationPoint(7.0f, 0.0f, 0.0f);
			kineHornLeft1.addBox(-1.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
			ModelRenderer kineHornLeft2 = new ModelRenderer(this, 0, 42);
			kineHornLeft2.mirror = true;
			kineHornLeft2.setRotationPoint(5.0f, 0.0f, 0.0f);
			kineHornLeft2.addBox(-1.0f, -0.5f, -0.5f, 4, 1, 1, 0.0f);
			kineHornRight.rotateAngleZ = 0.6981317007977318f;
			kineHornLeft.rotateAngleZ = -kineHornRight.rotateAngleZ;
			kineHornRight1.rotateAngleZ = -0.5235987755982988f;
			kineHornLeft1.rotateAngleZ = -kineHornRight1.rotateAngleZ;
			kineHornRight2.rotateAngleZ = -0.5235987755982988f;
			kineHornLeft2.rotateAngleZ = -kineHornRight2.rotateAngleZ;
			bipedHead.addChild(kineHornRight);
			kineHornRight.addChild(kineHornRight1);
			kineHornRight1.addChild(kineHornRight2);
			bipedHead.addChild(kineHornLeft);
			kineHornLeft.addChild(kineHornLeft1);
			kineHornLeft1.addChild(kineHornLeft2);
		}
		bipedHeadwear.cubeList.clear();
		bipedBody.cubeList.clear();
		bipedRightArm.cubeList.clear();
		bipedLeftArm.cubeList.clear();
		bipedRightLeg.cubeList.clear();
		bipedLeftLeg.cubeList.clear();
	}
}
