package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelWineGlass extends ModelBase {
	public ModelRenderer base = new ModelRenderer(this, 0, 0);
	public ModelRenderer cup;

	public LOTRModelWineGlass() {
		base.setRotationPoint(0.0f, -1.0f, 0.0f);
		base.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
		base.setTextureOffset(0, 5).addBox(-0.5f, -4.0f, -0.5f, 1, 4, 1);
		cup = new ModelRenderer(this, 0, 16);
		cup.setRotationPoint(0.0f, -6.0f, 0.0f);
		cup.addBox(-1.5f, 0.0f, -1.5f, 3, 1, 3);
		cup.setTextureOffset(0, 20).addBox(-2.5f, -4.0f, -1.5f, 1, 4, 3);
		cup.setTextureOffset(8, 22).addBox(-1.5f, -4.0f, -2.5f, 3, 4, 1);
		cup.setTextureOffset(16, 20).addBox(1.5f, -4.0f, -1.5f, 1, 4, 3);
		cup.setTextureOffset(24, 22).addBox(-1.5f, -4.0f, 1.5f, 3, 4, 1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		base.render(f5);
		cup.render(f5);
	}
}
