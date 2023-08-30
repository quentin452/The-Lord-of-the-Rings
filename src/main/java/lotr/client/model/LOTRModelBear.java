package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBear extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer nose;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelBear() {
		textureWidth = 128;
		textureHeight = 64;
		head = new ModelRenderer(this, 32, 0);
		head.setRotationPoint(0.0f, 8.0f, -9.0f);
		head.addBox(-4.0f, -5.0f, -4.0f, 8, 9, 6);
		head.setTextureOffset(0, 0).addBox(-4.5f, -5.5f, -11.0f, 9, 10, 7);
		nose = new ModelRenderer(this, 0, 17);
		nose.setRotationPoint(0.0f, 0.0f, 0.0f);
		nose.addBox(-2.5f, -2.0f, -17.0f, 5, 6, 6);
		nose.setTextureOffset(0, 29).addBox(-1.5f, -2.5f, -17.5f, 3, 3, 7);
		head.addChild(nose);
		ModelRenderer earRight = new ModelRenderer(this, 23, 17);
		earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
		earRight.addBox(-4.0f, -8.0f, -6.0f, 3, 3, 1);
		earRight.rotateAngleZ = -0.2617993877991494f;
		head.addChild(earRight);
		ModelRenderer earLeft = new ModelRenderer(this, 23, 17);
		earLeft.mirror = true;
		earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
		earLeft.addBox(1.0f, -8.0f, -6.0f, 3, 3, 1);
		earLeft.rotateAngleZ = 0.2617993877991494f;
		head.addChild(earLeft);
		body = new ModelRenderer(this, 40, 0);
		body.setRotationPoint(0.0f, 10.0f, -2.0f);
		body.addBox(-6.0f, -8.0f, -9.0f, 12, 14, 28);
		body.setTextureOffset(92, 0).addBox(-2.5f, -6.0f, 19.0f, 5, 5, 2);
		leg1 = new ModelRenderer(this, 56, 44);
		leg1.setRotationPoint(-4.0f, 6.0f, 10.0f);
		leg1.addBox(-6.0f, -2.0f, -3.5f, 6, 9, 9);
		leg1.setTextureOffset(86, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 11, 5);
		leg2 = new ModelRenderer(this, 56, 44);
		leg2.mirror = true;
		leg2.setRotationPoint(4.0f, 6.0f, 10.0f);
		leg2.addBox(0.0f, -2.0f, -3.5f, 6, 9, 9);
		leg2.setTextureOffset(86, 44).addBox(0.5f, 7.0f, -1.5f, 5, 11, 5);
		leg3 = new ModelRenderer(this, 0, 44);
		leg3.setRotationPoint(-3.0f, 6.0f, -5.0f);
		leg3.addBox(-6.0f, -2.0f, -3.0f, 6, 9, 8);
		leg3.setTextureOffset(28, 44).addBox(-5.5f, 7.0f, -1.5f, 5, 12, 5);
		leg4 = new ModelRenderer(this, 0, 44);
		leg4.mirror = true;
		leg4.setRotationPoint(3.0f, 6.0f, -5.0f);
		leg4.addBox(0.0f, -2.0f, -3.0f, 6, 9, 8);
		leg4.setTextureOffset(28, 44).addBox(0.5f, 7.0f, -1.5f, 5, 12, 5);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
			head.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = 0.17453292519943295f;
		head.rotateAngleY = 0.0f;
		head.rotateAngleX += (float) Math.toRadians(f4);
		head.rotateAngleY += (float) Math.toRadians(f3);
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		nose.rotationPointZ = isChild ? 3.0f : 0.0f;
	}
}
