package lotr.client.model;

import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelButterfly extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 0, 0);
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;

	public LOTRModelButterfly() {
		body.addBox(-1.0f, -6.0f, -1.0f, 2, 12, 2);
		rightWing = new ModelRenderer(this, 10, 0);
		rightWing.addBox(-12.0f, -10.5f, 0.0f, 12, 21, 0);
		leftWing = new ModelRenderer(this, 10, 0);
		leftWing.mirror = true;
		leftWing.addBox(0.0f, -10.5f, 0.0f, 12, 21, 0);
		body.addChild(rightWing);
		body.addChild(leftWing);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityButterfly butterfly = (LOTREntityButterfly) entity;
		if (butterfly.isButterflyStill()) {
			body.setRotationPoint(0.0f, 24.0f, 0.0f);
			body.rotateAngleX = 1.5707964f;
			rightWing.rotateAngleY = butterfly.flapTime > 0 ? MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f : 0.31415927f;
		} else {
			body.setRotationPoint(0.0f, 8.0f, 0.0f);
			body.rotateAngleX = 0.7853982f + MathHelper.cos(f2 * 0.1f) * 0.15f;
			rightWing.rotateAngleY = MathHelper.cos(f2 * 1.3f) * 3.1415927f * 0.25f;
		}
		leftWing.rotateAngleY = -rightWing.rotateAngleY;
		body.render(f5);
	}
}
