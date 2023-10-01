package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBlock extends ModelBase {
	public ModelRenderer block = new ModelRenderer(this, 0, 0);

	public LOTRModelBlock(float f) {
		block.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16, f);
		block.setRotationPoint(0.0f, 0.0f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		block.render(f5);
	}
}
