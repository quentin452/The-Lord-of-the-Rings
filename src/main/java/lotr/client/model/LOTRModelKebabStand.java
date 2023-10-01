package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelKebabStand extends ModelBase {
	public ModelRenderer stand;
	public ModelRenderer[] kebab;

	public LOTRModelKebabStand() {
		textureWidth = 64;
		textureHeight = 64;
		stand = new ModelRenderer(this, 0, 0);
		stand.setRotationPoint(0.0f, 24.0f, 0.0f);
		stand.addBox(-7.0f, -1.0f, -7.0f, 14, 1, 14);
		stand.setTextureOffset(0, 15).addBox(-4.0f, -16.0f, 6.0f, 8, 15, 1);
		stand.setTextureOffset(0, 31).addBox(-4.0f, -16.0f, -2.0f, 8, 1, 8);
		stand.setTextureOffset(0, 40).addBox(-0.5f, -15.0f, -0.5f, 1, 14, 1);
		ModelRenderer panelRight = new ModelRenderer(this, 18, 15);
		panelRight.setRotationPoint(-4.0f, 0.0f, 6.0f);
		panelRight.addBox(-4.0f, -16.0f, 0.0f, 4, 15, 1);
		panelRight.rotateAngleY = -0.7853981633974483f;
		stand.addChild(panelRight);
		ModelRenderer panelLeft = new ModelRenderer(this, 18, 15);
		panelLeft.setRotationPoint(4.0f, 0.0f, 6.0f);
		panelLeft.addBox(0.0f, -16.0f, 0.0f, 4, 15, 1);
		panelLeft.rotateAngleY = 0.7853981633974483f;
		stand.addChild(panelLeft);
		textureWidth = 32;
		textureHeight = 32;
		kebab = new ModelRenderer[9];
		for (int i = 0; i < kebab.length; ++i) {
			ModelRenderer kb = new ModelRenderer(this, 0, 0);
			kb.setRotationPoint(0.0f, 10.0f, 0.0f);
			if (i > 0) {
				int width = i + 1;
				kb.addBox(-((float) width) / 2.0f, 0.0f, -((float) width) / 2.0f, width, 11, width);
			}
			kebab[i] = kb;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		stand.render(f5);
	}

	public void renderKebab(float scale, int size, float spin) {
		if (size < 0 || size >= kebab.length) {
			size = 0;
		}
		kebab[size].rotateAngleY = (float) Math.toRadians(spin);
		kebab[size].render(scale);
	}
}
