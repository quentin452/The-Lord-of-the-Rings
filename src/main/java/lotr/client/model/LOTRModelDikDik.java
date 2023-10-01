package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelDikDik extends ModelBase {
	public ModelRenderer head = new ModelRenderer(this, 42, 23);
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelDikDik() {
		head.addBox(-2.0f, -9.0f, -3.0f, 4, 4, 5);
		head.setRotationPoint(0.0f, 11.0f, -4.5f);
		head.setTextureOffset(18, 28).addBox(-1.0f, -7.3f, -5.0f, 2, 2, 2);
		head.setTextureOffset(0, 27).addBox(-2.8f, -11.0f, 0.5f, 1, 3, 2);
		head.setTextureOffset(8, 27).addBox(1.8f, -11.0f, 0.5f, 1, 3, 2);
		head.setTextureOffset(0, 21).addBox(-1.5f, -11.0f, 0.0f, 1, 2, 1);
		head.setTextureOffset(0, 21).addBox(0.5f, -11.0f, 0.0f, 1, 2, 1);
		head.setTextureOffset(28, 22).addBox(-1.5f, -8.0f, -2.0f, 3, 7, 3);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-3.0f, 0.0f, 0.0f, 6, 6, 14);
		body.setRotationPoint(0.0f, 9.0f, -7.0f);
		leg1 = new ModelRenderer(this, 56, 0);
		leg1.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
		leg1.setRotationPoint(-1.7f, 14.0f, 5.0f);
		leg2 = new ModelRenderer(this, 56, 0);
		leg2.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
		leg2.setRotationPoint(1.7f, 14.0f, 5.0f);
		leg3 = new ModelRenderer(this, 56, 0);
		leg3.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
		leg3.setRotationPoint(-1.7f, 14.0f, -5.0f);
		leg4 = new ModelRenderer(this, 56, 0);
		leg4.addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
		leg4.setRotationPoint(1.7f, 14.0f, -5.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = f4 / 57.29578f;
		head.rotateAngleY = f3 / 57.29578f;
		leg1.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
	}
}
