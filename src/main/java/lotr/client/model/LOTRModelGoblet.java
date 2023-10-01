package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelGoblet extends ModelBase {
	public ModelRenderer base = new ModelRenderer(this, 0, 0);
	public ModelRenderer cup;

	public LOTRModelGoblet() {
		base.setRotationPoint(0.0f, -1.0f, 0.0f);
		base.addBox(-2.5f, 0.0f, -2.5f, 5, 1, 5);
		base.setTextureOffset(0, 6).addBox(-0.5f, -3.0f, -0.5f, 1, 3, 1);
		cup = new ModelRenderer(this, 0, 12);
		cup.setRotationPoint(0.0f, -5.0f, 0.0f);
		cup.addBox(-2.5f, 0.0f, -2.5f, 5, 1, 5);
		cup.setTextureOffset(0, 18).addBox(-2.5f, -4.0f, -2.5f, 1, 4, 5);
		cup.setTextureOffset(12, 22).addBox(-1.5f, -4.0f, -2.5f, 3, 4, 1);
		cup.setTextureOffset(20, 18).addBox(1.5f, -4.0f, -2.5f, 1, 4, 5);
		cup.setTextureOffset(32, 22).addBox(-1.5f, -4.0f, 1.5f, 3, 4, 1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		base.render(f5);
		cup.render(f5);
	}
}
