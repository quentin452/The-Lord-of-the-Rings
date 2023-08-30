package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelAurochs extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer tail;
	public ModelRenderer head;
	public ModelRenderer horns;
	public ModelRenderer hornLeft1;
	public ModelRenderer hornLeft2;
	public ModelRenderer hornRight1;
	public ModelRenderer hornRight2;

	public LOTRModelAurochs() {
		textureWidth = 128;
		textureHeight = 64;
		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(0.0f, 2.0f, -1.0f);
		body.addBox(-8.0f, -6.0f, -11.0f, 16, 16, 26);
		body.setTextureOffset(28, 42).addBox(-8.0f, -8.0f, -8.0f, 16, 2, 10);
		body.setTextureOffset(84, 31).addBox(-3.0f, 10.0f, 4.0f, 6, 1, 6);
		leg1 = new ModelRenderer(this, 0, 42);
		leg1.setRotationPoint(-5.0f, 12.0f, 9.0f);
		leg1.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
		leg2 = new ModelRenderer(this, 0, 42);
		leg2.setRotationPoint(5.0f, 12.0f, 9.0f);
		leg2.mirror = true;
		leg2.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
		leg3 = new ModelRenderer(this, 0, 42);
		leg3.setRotationPoint(-5.0f, 12.0f, -7.0f);
		leg3.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
		leg4 = new ModelRenderer(this, 0, 42);
		leg4.setRotationPoint(5.0f, 12.0f, -7.0f);
		leg4.mirror = true;
		leg4.addBox(-2.5f, 0.0f, -2.5f, 5, 12, 5);
		tail = new ModelRenderer(this, 20, 42);
		tail.setRotationPoint(0.0f, 1.0f, 14.0f);
		tail.addBox(-1.0f, -1.0f, 0.0f, 2, 12, 1);
		head = new ModelRenderer(this, 58, 0);
		head.setRotationPoint(0.0f, -1.0f, -10.0f);
		head.addBox(-5.0f, -4.0f, -12.0f, 10, 10, 11);
		head.setTextureOffset(89, 0).addBox(-3.0f, 1.0f, -15.0f, 6, 4, 4);
		head.setTextureOffset(105, 0);
		head.addBox(-8.0f, -2.5f, -7.0f, 3, 2, 1);
		head.mirror = true;
		head.addBox(5.0f, -2.5f, -7.0f, 3, 2, 1);
		head.mirror = false;
		horns = new ModelRenderer(this, 98, 21);
		horns.setRotationPoint(0.0f, -3.5f, -5.0f);
		horns.addBox(-6.0f, -1.5f, -1.5f, 12, 3, 3);
		head.addChild(horns);
		hornLeft1 = new ModelRenderer(this, 112, 27);
		hornLeft1.setRotationPoint(-6.0f, 0.0f, 0.0f);
		hornLeft1.addBox(-5.0f, -1.0f, -1.0f, 6, 2, 2);
		hornLeft2 = new ModelRenderer(this, 114, 31);
		hornLeft2.setRotationPoint(-5.0f, 0.0f, 0.0f);
		hornLeft2.addBox(-5.0f, -0.5f, -0.5f, 6, 1, 1);
		hornLeft1.addChild(hornLeft2);
		horns.addChild(hornLeft1);
		hornRight1 = new ModelRenderer(this, 112, 27);
		hornRight1.mirror = true;
		hornRight1.setRotationPoint(6.0f, 0.0f, 0.0f);
		hornRight1.addBox(-1.0f, -1.0f, -1.0f, 6, 2, 2);
		hornRight2 = new ModelRenderer(this, 114, 31);
		hornRight2.mirror = true;
		hornRight2.setRotationPoint(5.0f, 0.0f, 0.0f);
		hornRight2.addBox(-1.0f, -0.5f, -0.5f, 6, 1, 1);
		hornRight1.addChild(hornRight2);
		horns.addChild(hornRight1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		horns.showModel = !isChild;
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			float f7 = 8.0f;
			float f8 = 6.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, f7 * f5, f8 * f5);
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
		LOTREntityAurochs aurochs = (LOTREntityAurochs) entity;
		head.rotateAngleX = 0.0f;
		head.rotateAngleY = 0.0f;
		head.rotateAngleZ = 0.0f;
		head.rotateAngleX += (float) Math.toRadians(f4);
		head.rotateAngleY += (float) Math.toRadians(f3);
		if (aurochs.isAurochsEnraged()) {
			head.rotateAngleX += 0.2617993877991494f;
		}
		head.rotateAngleX += MathHelper.cos(f * 0.2f) * f1 * 0.4f;
		hornLeft1.rotateAngleZ = 0.4363323129985824f;
		hornLeft2.rotateAngleZ = 0.2617993877991494f;
		hornRight1.rotateAngleZ = -hornLeft1.rotateAngleZ;
		hornRight2.rotateAngleZ = -hornLeft2.rotateAngleZ;
		hornLeft1.rotateAngleY = -0.4363323129985824f;
		hornRight1.rotateAngleY = -hornLeft1.rotateAngleY;
		hornRight1.rotateAngleX = hornLeft1.rotateAngleX = 0.6108652381980153f;
		leg1.rotateAngleX = MathHelper.cos(f * 0.4f) * f1 * 0.8f;
		leg2.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.8f;
		leg3.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.8f;
		leg4.rotateAngleX = MathHelper.cos(f * 0.4f) * f1 * 0.8f;
	}
}
