package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelMug extends ModelBase {
	public ModelRenderer[] mugParts = new ModelRenderer[5];
	public ModelRenderer[] handleParts = new ModelRenderer[3];

	public LOTRModelMug() {
		mugParts[0] = new ModelRenderer(this, 0, 0);
		mugParts[0].addBox(-3.0f, -8.0f, -2.0f, 1, 8, 4);
		mugParts[1] = new ModelRenderer(this, 10, 3);
		mugParts[1].addBox(-3.0f, -8.0f, -3.0f, 6, 8, 1);
		mugParts[2] = new ModelRenderer(this, 24, 0);
		mugParts[2].addBox(2.0f, -8.0f, -2.0f, 1, 8, 4);
		mugParts[3] = new ModelRenderer(this, 34, 3);
		mugParts[3].addBox(-3.0f, -8.0f, 2.0f, 6, 8, 1);
		mugParts[4] = new ModelRenderer(this, 0, 12);
		mugParts[4].addBox(-2.0f, -1.0f, -2.0f, 4, 1, 4);
		handleParts[0] = new ModelRenderer(this, 0, 17);
		handleParts[0].addBox(3.0f, -7.0f, -0.5f, 2, 1, 1);
		handleParts[1] = new ModelRenderer(this, 0, 19);
		handleParts[1].addBox(4.0f, -6.0f, -0.5f, 1, 4, 1);
		handleParts[2] = new ModelRenderer(this, 0, 24);
		handleParts[2].addBox(3.0f, -2.0f, -0.5f, 2, 1, 1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		for (ModelRenderer part : mugParts) {
			part.render(f5);
		}
		for (ModelRenderer part : handleParts) {
			part.render(f5);
		}
	}
}
