package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelWarg extends ModelBase implements LOTRGlowingEyes.Model {
	public ModelRenderer body = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
	public ModelRenderer tail;
	public ModelRenderer head;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelWarg() {
		this(0.0f);
	}

	public LOTRModelWarg(float f) {
		body.addBox(-8.0f, -2.0f, -14.0f, 16, 14, 14, f);
		body.setRotationPoint(0.0f, 2.0f, 1.0f);
		body.setTextureOffset(0, 28).addBox(-6.5f, 0.0f, 0.0f, 13, 11, 18, f);
		tail = new ModelRenderer(this, 98, 55).setTextureSize(128, 64);
		tail.addBox(-1.0f, -1.0f, 0.0f, 2, 1, 8, f);
		tail.setRotationPoint(0.0f, 4.0f, 18.0f);
		head = new ModelRenderer(this, 92, 0).setTextureSize(128, 64);
		head.addBox(-5.0f, -5.0f, -8.0f, 10, 10, 8, f);
		head.setRotationPoint(0.0f, 8.0f, -13.0f);
		head.setTextureOffset(108, 18).addBox(-3.0f, -1.0f, -12.0f, 6, 5, 4, f);
		head.setTextureOffset(102, 18).addBox(-4.0f, -7.8f, -3.0f, 2, 3, 1, f);
		head.setTextureOffset(102, 18).addBox(2.0f, -7.8f, -3.0f, 2, 3, 1, f);
		leg1 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
		leg1.mirror = true;
		leg1.addBox(-6.0f, -1.0f, -2.5f, 6, 9, 8, f);
		leg1.setRotationPoint(-4.0f, 6.0f, 12.0f);
		leg1.setTextureOffset(66, 17).addBox(-5.5f, 8.0f, -1.0f, 5, 10, 5, f);
		leg2 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
		leg2.addBox(0.0f, -1.0f, -2.5f, 6, 9, 8, f);
		leg2.setRotationPoint(4.0f, 6.0f, 12.0f);
		leg2.setTextureOffset(66, 17).addBox(0.5f, 8.0f, -1.0f, 5, 10, 5, f);
		leg3 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
		leg3.mirror = true;
		leg3.addBox(-6.0f, -1.0f, -2.5f, 6, 9, 8, f);
		leg3.setRotationPoint(-6.0f, 5.0f, -8.0f);
		leg3.setTextureOffset(66, 17).addBox(-5.5f, 8.0f, -1.0f, 5, 11, 5, f);
		leg4 = new ModelRenderer(this, 62, 0).setTextureSize(128, 64);
		leg4.addBox(0.0f, -1.0f, -2.5f, 6, 9, 8, f);
		leg4.setRotationPoint(6.0f, 5.0f, -8.0f);
		leg4.setTextureOffset(66, 17).addBox(0.5f, 8.0f, -1.0f, 5, 11, 5, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		tail.render(f5);
		head.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
	}

	@Override
	public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / 57.295776f;
		head.rotateAngleY = f3 / 57.295776f;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
		tail.rotateAngleX = ((LOTREntityWarg) entity).getTailRotation();
	}
}
