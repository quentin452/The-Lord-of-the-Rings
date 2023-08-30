package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelLion extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer mane;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer tail;

	public LOTRModelLion() {
		textureWidth = 128;
		textureHeight = 64;
		head = new ModelRenderer(this, 48, 0);
		head.setRotationPoint(0.0f, 3.0f, -10.0f);
		head.addBox(-5.0f, -6.0f, -10.0f, 10, 10, 10);
		head.setTextureOffset(78, 0).addBox(-3.0f, -1.0f, -14.0f, 6, 5, 4);
		head.setTextureOffset(98, 0).addBox(-1.0f, -2.0f, -14.2f, 2, 2, 5);
		head.setTextureOffset(0, 0);
		head.addBox(-4.0f, -9.0f, -7.5f, 3, 3, 1);
		head.mirror = true;
		head.addBox(1.0f, -9.0f, -7.5f, 3, 3, 1);
		mane = new ModelRenderer(this, 0, 0);
		mane.setRotationPoint(0.0f, 3.0f, -10.0f);
		mane.addBox(-8.0f, -10.0f, -6.0f, 16, 16, 8);
		body = new ModelRenderer(this, 0, 24);
		body.setRotationPoint(0.0f, 6.0f, 1.0f);
		body.addBox(-7.0f, -6.5f, -11.0f, 14, 14, 24);
		leg1 = new ModelRenderer(this, 52, 24);
		leg1.setRotationPoint(-4.0f, 4.0f, 11.0f);
		leg1.addBox(-6.0f, -2.0f, -3.5f, 6, 10, 8);
		leg1.setTextureOffset(106, 24).addBox(-5.5f, 8.0f, -2.5f, 5, 12, 5);
		leg2 = new ModelRenderer(this, 52, 24);
		leg2.setRotationPoint(4.0f, 4.0f, 11.0f);
		leg2.mirror = true;
		leg2.addBox(0.0f, -2.0f, -3.5f, 6, 10, 8);
		leg2.setTextureOffset(106, 24).addBox(0.5f, 8.0f, -2.5f, 5, 12, 5);
		leg3 = new ModelRenderer(this, 80, 24);
		leg3.setRotationPoint(-4.0f, 5.0f, -5.0f);
		leg3.addBox(-6.0f, -2.0f, -3.5f, 6, 9, 7);
		leg3.setTextureOffset(106, 24).addBox(-5.5f, 7.0f, -2.5f, 5, 12, 5);
		leg4 = new ModelRenderer(this, 80, 24);
		leg4.setRotationPoint(4.0f, 5.0f, -5.0f);
		leg4.mirror = true;
		leg4.addBox(0.0f, -2.0f, -3.5f, 6, 9, 7);
		leg4.setTextureOffset(106, 24).addBox(0.5f, 7.0f, -2.5f, 5, 12, 5);
		tail = new ModelRenderer(this, 100, 50);
		tail.setRotationPoint(0.0f, 4.0f, 13.0f);
		tail.addBox(-1.0f, -1.0f, 0.0f, 2, 2, 12);
		tail.setTextureOffset(86, 57).addBox(-1.5f, -1.5f, 12.0f, 3, 3, 4);
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
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			mane.render(f5);
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
		head.rotateAngleX = (float) Math.toRadians(f4);
		head.rotateAngleY = (float) Math.toRadians(f3);
		mane.rotateAngleX = head.rotateAngleX;
		mane.rotateAngleY = head.rotateAngleY;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.0f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.0f * f1;
		tail.rotateAngleX = -1.0471975511965976f;
		tail.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.5f * f1;
	}
}
