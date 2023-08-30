package lotr.client.model;

import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHalfTroll extends LOTRModelBiped {
	public ModelRenderer mohawk;
	public ModelRenderer hornRight1;
	public ModelRenderer hornRight2;
	public ModelRenderer hornLeft1;
	public ModelRenderer hornLeft2;

	public LOTRModelHalfTroll() {
		this(0.0f);
	}

	public LOTRModelHalfTroll(float f) {
		super(f);
		textureWidth = 64;
		textureHeight = 64;
		bipedHead = new ModelRenderer(this, 0, 0);
		bipedHead.setRotationPoint(0.0f, -8.0f, 0.0f);
		bipedHead.addBox(-5.0f, -10.0f, -5.0f, 10, 10, 10, f);
		bipedHead.setTextureOffset(40, 5).addBox(-4.0f, -3.0f, -7.0f, 8, 3, 2, f);
		ModelRenderer nose = new ModelRenderer(this, 30, 0);
		nose.addBox(-1.0f, -4.5f, -8.0f, 2, 3, 3, f);
		nose.rotateAngleX = -0.3490658503988659f;
		bipedHead.addChild(nose);
		ModelRenderer teeth = new ModelRenderer(this, 60, 7);
		teeth.addBox(-3.5f, -7.5f, -5.0f, 1, 2, 1, f);
		teeth.mirror = true;
		teeth.addBox(2.5f, -7.5f, -5.0f, 1, 2, 1, f);
		teeth.rotateAngleX = 0.5235987755982988f;
		bipedHead.addChild(teeth);
		ModelRenderer earRight = new ModelRenderer(this, 0, 0);
		earRight.addBox(-5.0f, -6.0f, -2.0f, 1, 3, 3, f);
		earRight.rotateAngleY = -0.6108652381980153f;
		bipedHead.addChild(earRight);
		ModelRenderer earLeft = new ModelRenderer(this, 0, 0);
		earLeft.mirror = true;
		earLeft.addBox(4.0f, -6.0f, -2.0f, 1, 3, 3, f);
		earLeft.rotateAngleY = 0.6108652381980153f;
		bipedHead.addChild(earLeft);
		mohawk = new ModelRenderer(this, 40, 10);
		mohawk.addBox(-1.0f, -12.5f, -1.5f, 2, 10, 8, f);
		bipedHead.addChild(mohawk);
		hornRight1 = new ModelRenderer(this, 40, 0);
		hornRight1.addBox(-10.0f, -8.0f, 1.0f, 3, 2, 2, f);
		hornRight1.rotateAngleZ = 0.3490658503988659f;
		bipedHead.addChild(hornRight1);
		hornRight2 = new ModelRenderer(this, 50, 2);
		hornRight2.addBox(-14.5f, -4.0f, 1.5f, 3, 1, 1, f);
		hornRight2.rotateAngleZ = 0.6981317007977318f;
		bipedHead.addChild(hornRight2);
		hornLeft1 = new ModelRenderer(this, 40, 0);
		hornLeft1.mirror = true;
		hornLeft1.addBox(7.0f, -8.0f, 1.0f, 3, 2, 2, f);
		hornLeft1.rotateAngleZ = -0.3490658503988659f;
		bipedHead.addChild(hornLeft1);
		hornLeft2 = new ModelRenderer(this, 50, 2);
		hornLeft2.mirror = true;
		hornLeft2.addBox(11.5f, -4.0f, 1.5f, 3, 1, 1, f);
		hornLeft2.rotateAngleZ = -0.6981317007977318f;
		bipedHead.addChild(hornLeft2);
		bipedBody = new ModelRenderer(this, 0, 20);
		bipedBody.setRotationPoint(0.0f, -8.0f, 0.0f);
		bipedBody.addBox(-6.0f, 0.0f, -4.0f, 12, 16, 8, f);
		bipedRightArm = new ModelRenderer(this, 20, 50);
		bipedRightArm.setRotationPoint(-8.5f, -6.0f, 0.0f);
		bipedRightArm.addBox(-3.5f, -2.0f, -3.0f, 6, 8, 6, f);
		bipedRightArm.setTextureOffset(0, 49).addBox(-3.0f, 6.0f, -2.5f, 5, 10, 5, f);
		bipedLeftArm = new ModelRenderer(this, 20, 50);
		bipedLeftArm.setRotationPoint(8.5f, -6.0f, 0.0f);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-2.5f, -2.0f, -3.0f, 6, 8, 6, f);
		bipedLeftArm.setTextureOffset(0, 49).addBox(-2.0f, 6.0f, -2.5f, 5, 10, 5, f);
		bipedRightLeg = new ModelRenderer(this, 40, 28);
		bipedRightLeg.setRotationPoint(-3.2f, 8.0f, 0.0f);
		bipedRightLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
		bipedLeftLeg = new ModelRenderer(this, 40, 28);
		bipedLeftLeg.setRotationPoint(3.2f, 8.0f, 0.0f);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-3.0f, 0.0f, -3.0f, 6, 16, 6, f);
		bipedHeadwear.isHidden = true;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll) entity;
		mohawk.showModel = halfTroll.hasMohawk();
		hornRight1.showModel = hornLeft1.showModel = halfTroll.hasHorns();
		hornRight2.showModel = hornLeft2.showModel = halfTroll.hasFullHorns();
		super.render(entity, f, f1, f2, f3, f4, f5);
	}
}
