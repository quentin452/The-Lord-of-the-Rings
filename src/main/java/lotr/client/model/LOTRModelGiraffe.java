package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelGiraffe extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
	public ModelRenderer neck;
	public ModelRenderer head;
	public ModelRenderer tail;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelGiraffe(float f) {
		body.addBox(-6.0f, -8.0f, -13.0f, 12, 16, 26, f);
		body.setRotationPoint(0.0f, -11.0f, 0.0f);
		neck = new ModelRenderer(this, 0, 44).setTextureSize(128, 64);
		neck.addBox(-4.5f, -13.0f, -4.5f, 9, 11, 9, f);
		neck.setTextureOffset(78, 0).addBox(-3.0f, -37.0f, -3.0f, 6, 40, 6, f);
		neck.setRotationPoint(0.0f, -14.0f, -7.0f);
		head = new ModelRenderer(this, 96, 48).setTextureSize(128, 64);
		head.addBox(-3.0f, -43.0f, -6.0f, 6, 6, 10, f);
		head.setTextureOffset(10, 0).addBox(-4.0f, -45.0f, 1.5f, 1, 3, 2, f);
		head.setTextureOffset(17, 0).addBox(3.0f, -45.0f, 1.5f, 1, 3, 2, f);
		head.setTextureOffset(0, 0).addBox(-2.5f, -47.0f, 0.0f, 1, 4, 1, f);
		head.setTextureOffset(5, 0).addBox(1.5f, -47.0f, 0.0f, 1, 4, 1, f);
		head.setTextureOffset(76, 56).addBox(-2.0f, -41.0f, -11.0f, 4, 3, 5, f);
		head.setRotationPoint(0.0f, -14.0f, -7.0f);
		tail = new ModelRenderer(this, 104, 0).setTextureSize(128, 64);
		tail.addBox(-0.5f, 0.0f, 0.0f, 1, 24, 1, f);
		tail.setRotationPoint(0.0f, -12.0f, 13.0f);
		leg1 = new ModelRenderer(this, 112, 0).setTextureSize(128, 64);
		leg1.addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		leg1.setRotationPoint(-3.9f, -3.0f, 8.0f);
		leg2 = new ModelRenderer(this, 112, 0).setTextureSize(128, 64);
		leg2.addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		leg2.setRotationPoint(3.9f, -3.0f, 8.0f);
		leg2.mirror = true;
		leg3 = new ModelRenderer(this, 112, 0).setTextureSize(128, 64);
		leg3.addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		leg3.setRotationPoint(-3.9f, -3.0f, -7.0f);
		leg4 = new ModelRenderer(this, 112, 0).setTextureSize(128, 64);
		leg4.addBox(-2.0f, 0.0f, -2.0f, 4, 27, 4, f);
		leg4.setRotationPoint(3.9f, -3.0f, -7.0f);
		leg4.mirror = true;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (entity.riddenByEntity instanceof EntityPlayer) {
			setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
		} else {
			setDefaultHeadNeckRotation(f, f1, f2, f3, f4, f5);
		}
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 8.0f * f5, 4.0f * f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			head.render(f5);
			body.render(f5);
			neck.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			body.render(f5);
			neck.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
		}
	}

	public void setDefaultHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
		head.setRotationPoint(0.0f, -14.0f, -7.0f);
		neck.rotateAngleX = 0.17453294f + f4 / 57.29578f;
		head.rotateAngleX = 0.17453294f + f4 / 57.29578f;
		neck.rotateAngleY = f3 / 57.29578f;
		head.rotateAngleY = f3 / 57.29578f;
	}

	public void setRiddenHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
		head.setRotationPoint(0.0f, 25.0f, -48.0f);
		neck.rotateAngleX = 1.5707964f;
		neck.rotateAngleY = 0.0f;
		head.rotateAngleX = 0.0f;
		head.rotateAngleY = 0.0f;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		leg1.rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leg2.rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg3.rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg4.rotateAngleX = 0.5f * MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		tail.rotateAngleZ = 0.2f * MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
	}
}
