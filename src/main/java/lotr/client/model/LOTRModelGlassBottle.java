package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelGlassBottle extends ModelBase {
	public ModelRenderer bottle = new ModelRenderer(this, 0, 0);

	public LOTRModelGlassBottle() {
		bottle.setRotationPoint(0.0f, -1.0f, 0.0f);
		bottle.addBox(-2.0f, 0.0f, -2.0f, 4, 1, 4);
		bottle.setTextureOffset(0, 6).addBox(-3.0f, -4.0f, -2.0f, 1, 4, 4);
		bottle.setTextureOffset(10, 9).addBox(-2.0f, -4.0f, -3.0f, 4, 4, 1);
		bottle.setTextureOffset(20, 6).addBox(2.0f, -4.0f, -2.0f, 1, 4, 4);
		bottle.setTextureOffset(30, 9).addBox(-2.0f, -4.0f, 2.0f, 4, 4, 1);
		bottle.setTextureOffset(16, 0).addBox(-2.0f, -5.0f, -2.0f, 4, 1, 4);
		bottle.setTextureOffset(0, 16).addBox(-1.0f, -6.0f, -1.0f, 2, 1, 2);
		bottle.setTextureOffset(0, 19).addBox(-1.5f, -7.0f, -1.5f, 3, 1, 3);
		bottle.setTextureOffset(12, 19).addBox(-1.0f, -8.5f, -1.0f, 2, 2, 2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bottle.render(f5);
	}
}
