package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelMidge extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 0, 0);
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;

	public LOTRModelMidge() {
		body.addBox(-0.5f, -1.5f, -0.5f, 1, 5, 1);
		rightWing = new ModelRenderer(this, 0, 6);
		rightWing.addBox(-5.0f, -2.5f, 0.0f, 5, 5, 1);
		leftWing = new ModelRenderer(this, 0, 6);
		leftWing.mirror = true;
		leftWing.addBox(0.0f, -2.5f, 0.0f, 5, 5, 1);
		body.addChild(rightWing);
		body.addChild(leftWing);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.setRotationPoint(0.0f, 8.0f, 0.0f);
		body.rotateAngleX = 0.7853982f + MathHelper.cos(f2 * 0.1f) * 0.15f;
		rightWing.rotateAngleY = MathHelper.cos(f2 * 4.0f) * 3.1415927f * 0.25f;
		leftWing.rotateAngleY = -rightWing.rotateAngleY;
		body.render(f5);
	}
}
