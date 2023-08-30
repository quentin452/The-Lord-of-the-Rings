package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHobbit extends LOTRModelBiped {
	public ModelRenderer bipedChest;

	public LOTRModelHobbit() {
		this(0.0f);
	}

	public LOTRModelHobbit(float f) {
		this(f, 64, f == 0.0f ? 64 : 32);
	}

	public LOTRModelHobbit(float f, int width, int height) {
		super(f, 0.0f, width, height);
		boolean isArmor = height == 32;
		bipedChest = new ModelRenderer(this, 24, 0);
		bipedChest.addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
		bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.addChild(bipedChest);
		if (!isArmor) {
			bipedHeadwear = new ModelRenderer(this, 0, 32);
			bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, 0.5f + f);
			bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
		}
		if (!isArmor) {
			ModelRenderer rightFoot = new ModelRenderer(this, 40, 32);
			rightFoot.addBox(-2.0f, 10.0f, -5.0f, 4, 2, 3, f);
			rightFoot.rotateAngleY = 0.17453292519943295f;
			bipedRightLeg.addChild(rightFoot);
			ModelRenderer leftFoot = new ModelRenderer(this, 40, 32);
			leftFoot.addBox(-2.0f, 10.0f, -5.0f, 4, 2, 3, f);
			leftFoot.rotateAngleY = -0.17453292519943295f;
			bipedLeftLeg.addChild(leftFoot);
		}
		bipedHead.rotationPointY += 4.0f;
		bipedHeadwear.rotationPointY += 4.0f;
		bipedBody.rotationPointY += 4.8f;
		bipedRightArm.rotationPointY += 4.8f;
		bipedLeftArm.rotationPointY += 4.8f;
		bipedRightLeg.rotationPointY += 4.8f;
		bipedLeftLeg.rotationPointY += 4.8f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
		float f6 = 2.0f;
		if (isChild) {
			GL11.glPushMatrix();
			GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
			GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
			GL11.glTranslatef(0.0f, -1.0f * f5, 0.0f);
		} else {
			GL11.glPushMatrix();
		}
		bipedHead.render(f5);
		bipedHeadwear.render(f5);
		if (isChild) {
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
		}
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, 0.8333333f, 1.0f);
		bipedBody.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, 0.8333333f, 1.0f);
		bipedRightArm.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, 0.8333333f, 1.0f);
		bipedLeftArm.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, 0.8333333f, 1.0f);
		bipedRightLeg.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(1.0f, 0.8333333f, 1.0f);
		bipedLeftLeg.render(f5);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
