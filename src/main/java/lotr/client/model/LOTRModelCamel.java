package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelCamel extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer humps;
	public ModelRenderer tail;
	public ModelRenderer head;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer chest;

	public LOTRModelCamel() {
		this(0.0f);
	}

	public LOTRModelCamel(float f) {
		textureWidth = 64;
		textureHeight = 64;
		body = new ModelRenderer(this, 0, 16);
		body.setRotationPoint(0.0f, 10.0f, 0.0f);
		body.addBox(-4.5f, -5.0f, -10.0f, 9, 10, 22, f);
		humps = new ModelRenderer(this, 34, 0);
		humps.setRotationPoint(0.0f, 10.0f, 0.0f);
		humps.addBox(-3.0f, -9.0f, -8.0f, 6, 4, 6, f);
		humps.addBox(-3.0f, -9.0f, 5.0f, 6, 4, 6, f);
		tail = new ModelRenderer(this, 54, 52);
		tail.setRotationPoint(0.0f, 7.0f, 12.0f);
		tail.addBox(-1.0f, -1.0f, 0.0f, 2, 10, 2);
		head = new ModelRenderer(this, 0, 0);
		head.setRotationPoint(0.0f, 6.0f, -10.0f);
		head.addBox(-3.0f, -13.0f, -10.5f, 6, 5, 11, f);
		head.addBox(-2.5f, -15.0f, -1.0f, 2, 2, 1, f);
		head.mirror = true;
		head.addBox(0.5f, -15.0f, -1.0f, 2, 2, 1, f);
		head.mirror = false;
		head.setTextureOffset(0, 16).addBox(-2.5f, -9.0f, -5.0f, 5, 14, 5, f);
		leg1 = new ModelRenderer(this, 0, 52);
		leg1.setRotationPoint(-4.5f, 7.0f, 8.0f);
		leg1.addBox(-4.0f, -1.0f, -2.5f, 4, 7, 5, f);
		leg1.setTextureOffset(18, 53).addBox(-3.5f, 6.0f, -1.5f, 3, 8, 3, f);
		leg1.setTextureOffset(30, 57).addBox(-4.0f, 14.0f, -2.0f, 4, 3, 4, f);
		leg2 = new ModelRenderer(this, 0, 52);
		leg2.mirror = true;
		leg2.setRotationPoint(4.5f, 7.0f, 8.0f);
		leg2.addBox(0.0f, -1.0f, -2.5f, 4, 7, 5, f);
		leg2.setTextureOffset(18, 53).addBox(0.5f, 6.0f, -1.5f, 3, 8, 3, f);
		leg2.setTextureOffset(30, 57).addBox(0.0f, 14.0f, -2.0f, 4, 3, 4, f);
		leg3 = new ModelRenderer(this, 0, 52);
		leg3.setRotationPoint(-4.5f, 7.0f, -5.0f);
		leg3.addBox(-4.0f, -1.0f, -2.5f, 4, 7, 5, f);
		leg3.setTextureOffset(18, 53).addBox(-3.5f, 6.0f, -1.5f, 3, 8, 3, f);
		leg3.setTextureOffset(30, 57).addBox(-4.0f, 14.0f, -2.0f, 4, 3, 4, f);
		leg4 = new ModelRenderer(this, 0, 52);
		leg4.mirror = true;
		leg4.setRotationPoint(4.5f, 7.0f, -5.0f);
		leg4.addBox(0.0f, -1.0f, -2.5f, 4, 7, 5, f);
		leg4.setTextureOffset(18, 53).addBox(0.5f, 6.0f, -1.5f, 3, 8, 3, f);
		leg4.setTextureOffset(30, 57).addBox(0.0f, 14.0f, -2.0f, 4, 3, 4, f);
		chest = new ModelRenderer(this, 40, 22);
		chest.setRotationPoint(0.0f, 10.0f, 0.0f);
		chest.addBox(-7.5f, -4.5f, -2.5f, 3, 8, 8, f);
		chest.mirror = true;
		chest.addBox(4.5f, -4.5f, -2.5f, 3, 8, 8, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityCamel camel = (LOTREntityCamel) entity;
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			body.render(f5);
			tail.render(f5);
			head.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			GL11.glPopMatrix();
		} else {
			body.render(f5);
			humps.render(f5);
			tail.render(f5);
			head.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			if (camel.isChested()) {
				chest.render(f5);
			}
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / 57.29577951308232f;
		head.rotateAngleY = f3 / 57.29577951308232f;
		head.rotateAngleX += MathHelper.cos(f * 0.3331f) * 0.1f * f1;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.8f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.8f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.8f * f1;
		tail.rotateAngleZ = 0.1f * MathHelper.cos(f * 0.3331f + 3.1415927f) * f1;
	}
}
