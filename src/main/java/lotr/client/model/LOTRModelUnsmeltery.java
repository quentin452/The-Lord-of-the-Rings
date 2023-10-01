package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelUnsmeltery extends ModelBase {
	public ModelRenderer base;
	public ModelRenderer body;
	public ModelRenderer standRight;
	public ModelRenderer standLeft;

	public LOTRModelUnsmeltery() {
		textureWidth = 64;
		textureHeight = 64;
		base = new ModelRenderer(this, 0, 0);
		base.setRotationPoint(0.0f, 21.0f, 0.0f);
		base.addBox(-7.0f, 0.0f, -7.0f, 14, 3, 14);
		body = new ModelRenderer(this, 0, 17);
		body.setRotationPoint(0.0f, 12.0f, 0.0f);
		body.addBox(-7.0f, -2.0f, -7.0f, 14, 10, 14);
		body.setTextureOffset(0, 41).addBox(-7.0f, -4.0f, -7.0f, 14, 2, 1);
		body.addBox(-7.0f, -4.0f, 6.0f, 14, 2, 1);
		body.setTextureOffset(0, 44).addBox(-7.0f, -4.0f, -6.0f, 1, 2, 12);
		body.addBox(6.0f, -4.0f, -6.0f, 1, 2, 12);
		standRight = new ModelRenderer(this, 56, 6);
		standRight.setRotationPoint(-7.0f, 23.0f, 0.0f);
		standRight.addBox(-0.9f, -12.0f, -1.0f, 1, 12, 2);
		ModelRenderer panelRight = new ModelRenderer(this, 56, 0);
		panelRight.setRotationPoint(0.0f, -11.0f, 0.0f);
		panelRight.addBox(-1.0f, -2.0f, -1.0f, 1, 3, 3);
		panelRight.rotateAngleX = 0.7853981633974483f;
		standRight.addChild(panelRight);
		standLeft = new ModelRenderer(this, 56, 6);
		standLeft.setRotationPoint(7.0f, 23.0f, 0.0f);
		standLeft.mirror = true;
		standLeft.addBox(-0.1f, -12.0f, -1.0f, 1, 12, 2);
		ModelRenderer panelLeft = new ModelRenderer(this, 56, 0);
		panelLeft.setRotationPoint(0.0f, -11.0f, 0.0f);
		panelLeft.mirror = true;
		panelLeft.addBox(0.0f, -2.0f, -1.0f, 1, 3, 3);
		panelLeft.rotateAngleX = 0.7853981633974483f;
		standLeft.addChild(panelLeft);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		body.rotateAngleX = f * 0.3490658503988659f;
		base.render(f5);
		body.render(f5);
		standRight.render(f5);
		standLeft.render(f5);
	}
}
