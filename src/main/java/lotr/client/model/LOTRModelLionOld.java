package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelLionOld extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer headwear;
	public ModelRenderer mane;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelLionOld() {
		textureWidth = 64;
		textureHeight = 96;
		head = new ModelRenderer(this, 0, 0);
		head.setRotationPoint(0.0f, 4.0f, -9.0f);
		head.addBox(-4.0f, -4.0f, -7.0f, 8, 8, 8, 0.0f);
		head.setTextureOffset(52, 34).addBox(-2.0f, 0.0f, -9.0f, 4, 4, 2);
		headwear = new ModelRenderer(this, 32, 0);
		headwear.setRotationPoint(0.0f, 4.0f, -9.0f);
		headwear.addBox(-4.0f, -4.0f, -7.0f, 8, 8, 8, 0.5f);
		mane = new ModelRenderer(this, 0, 36);
		mane.setRotationPoint(0.0f, 4.0f, -9.0f);
		mane.addBox(-7.0f, -7.0f, -5.0f, 14, 14, 9, 0.0f);
		body = new ModelRenderer(this, 0, 68);
		body.addBox(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.0f);
		body.setRotationPoint(0.0f, 5.0f, 2.0f);
		leg1 = new ModelRenderer(this, 0, 19);
		leg1.setRotationPoint(-4.0f, 12.0f, 7.0f);
		leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
		leg2 = new ModelRenderer(this, 0, 19);
		leg2.mirror = true;
		leg2.setRotationPoint(4.0f, 12.0f, 7.0f);
		leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
		leg3 = new ModelRenderer(this, 0, 19);
		leg3.setRotationPoint(-4.0f, 12.0f, -5.0f);
		leg3.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
		leg4 = new ModelRenderer(this, 0, 19);
		leg4.mirror = true;
		leg4.setRotationPoint(4.0f, 12.0f, -5.0f);
		leg4.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
			head.render(f5);
			headwear.render(f5);
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
			headwear.render(f5);
			mane.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = (float) Math.toRadians(f4);
		head.rotateAngleY = (float) Math.toRadians(f3);
		headwear.rotateAngleX = head.rotateAngleX;
		headwear.rotateAngleY = head.rotateAngleY;
		mane.rotateAngleX = head.rotateAngleX;
		mane.rotateAngleY = head.rotateAngleY;
		body.rotateAngleX = 1.5707964f;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
	}
}
