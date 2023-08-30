package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelGemsbok extends ModelBase {
	public ModelRenderer head = new ModelRenderer(this, 28, 0).setTextureSize(128, 64);
	public ModelRenderer tail;
	public ModelRenderer earLeft;
	public ModelRenderer earRight;
	public ModelRenderer neck;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leftHorn;
	public ModelRenderer rightHorn;

	public LOTRModelGemsbok() {
		head.addBox(-3.0f, -10.0f, -6.0f, 6, 7, 12);
		head.setRotationPoint(0.0f, 4.0f, -9.0f);
		tail = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		tail.addBox(0.0f, 0.0f, 0.0f, 2, 12, 2);
		tail.setRotationPoint(-1.0f, 3.0f, 11.0f);
		earLeft = new ModelRenderer(this, 28, 19).setTextureSize(128, 64);
		earLeft.addBox(-3.8f, -12.0f, 3.0f, 1, 3, 2);
		earLeft.setRotationPoint(0.0f, 4.0f, -9.0f);
		earRight = new ModelRenderer(this, 34, 19).setTextureSize(128, 64);
		earRight.addBox(2.8f, -12.0f, 3.0f, 1, 3, 2);
		earRight.setRotationPoint(0.0f, 4.0f, -9.0f);
		neck = new ModelRenderer(this, 0, 14).setTextureSize(128, 64);
		neck.addBox(-2.5f, -6.0f, -5.0f, 5, 8, 9);
		neck.setRotationPoint(0.0f, 4.0f, -9.0f);
		body = new ModelRenderer(this, 0, 31).setTextureSize(128, 64);
		body.addBox(-7.0f, -10.0f, -7.0f, 13, 10, 23);
		body.setRotationPoint(0.5f, 12.0f, -3.0f);
		leg1 = new ModelRenderer(this, 0, 38).setTextureSize(128, 64);
		leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4);
		leg1.setRotationPoint(-4.0f, 12.0f, 10.0f);
		leg2 = new ModelRenderer(this, 0, 38).setTextureSize(128, 64);
		leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4);
		leg2.setRotationPoint(4.0f, 12.0f, 10.0f);
		leg3 = new ModelRenderer(this, 0, 38).setTextureSize(128, 64);
		leg3.addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4);
		leg3.setRotationPoint(-4.0f, 12.0f, -7.0f);
		leg4 = new ModelRenderer(this, 0, 38).setTextureSize(128, 64);
		leg4.addBox(-2.0f, 0.0f, -3.0f, 4, 12, 4);
		leg4.setRotationPoint(4.0f, 12.0f, -7.0f);
		leftHorn = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		leftHorn.addBox(-2.8f, -9.5f, 5.8f, 1, 1, 13);
		leftHorn.setRotationPoint(0.0f, 4.0f, -9.0f);
		rightHorn = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
		rightHorn.addBox(1.8f, -9.5f, 5.8f, 1, 1, 13);
		rightHorn.setRotationPoint(0.0f, 4.0f, -9.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			head.render(f5);
			earLeft.render(f5);
			earRight.render(f5);
			neck.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			neck.render(f5);
			leftHorn.render(f5);
			rightHorn.render(f5);
			earLeft.render(f5);
			earRight.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = (float) Math.toRadians(f4) + 0.4014257f;
		head.rotateAngleY = (float) Math.toRadians(f3);
		neck.rotateAngleX = -1.064650843716541f;
		neck.rotateAngleY = head.rotateAngleY * 0.7f;
		rightHorn.rotateAngleX = head.rotateAngleX;
		rightHorn.rotateAngleY = head.rotateAngleY;
		leftHorn.rotateAngleX = head.rotateAngleX;
		leftHorn.rotateAngleY = head.rotateAngleY;
		earLeft.rotateAngleX = head.rotateAngleX;
		earLeft.rotateAngleY = head.rotateAngleY;
		earRight.rotateAngleX = head.rotateAngleX;
		earRight.rotateAngleY = head.rotateAngleY;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		tail.rotateAngleX = 0.29670597283903605f;
	}
}
