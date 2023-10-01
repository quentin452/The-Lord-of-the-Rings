package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelAleHorn extends ModelBase {
	public ModelRenderer horn = new ModelRenderer(this, 28, 16);
	public ModelRenderer horn1;
	public ModelRenderer horn2;
	public ModelRenderer horn3;
	public ModelRenderer stand;

	public LOTRModelAleHorn() {
		horn.setRotationPoint(-4.0f, -5.0f, 0.0f);
		horn.addBox(-1.0f, -1.0f, -1.0f, 2, 6, 2);
		horn1 = new ModelRenderer(this, 16, 16);
		horn1.setRotationPoint(0.0f, 0.0f, 0.0f);
		horn1.addBox(-1.5f, -6.0f, -1.5f, 3, 6, 3);
		horn.addChild(horn1);
		horn2 = new ModelRenderer(this, 0, 16);
		horn2.setRotationPoint(0.0f, -5.0f, 0.0f);
		horn2.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 4);
		horn1.addChild(horn2);
		horn3 = new ModelRenderer(this, 0, 0);
		horn3.setRotationPoint(0.0f, -5.0f, 0.0f);
		horn3.addBox(-2.5f, -1.0f, -2.5f, 5, 1, 5);
		horn3.setTextureOffset(0, 6).addBox(-2.5f, -6.0f, -1.5f, 1, 5, 3);
		horn3.setTextureOffset(8, 8).addBox(-2.5f, -6.0f, -2.5f, 5, 5, 1);
		horn3.setTextureOffset(20, 6).addBox(1.5f, -6.0f, -1.5f, 1, 5, 3);
		horn3.setTextureOffset(28, 8).addBox(-2.5f, -6.0f, 1.5f, 5, 5, 1);
		horn2.addChild(horn3);
		horn.rotateAngleZ = 1.5707963267948966f;
		horn1.rotateAngleZ = -0.3490658503988659f;
		horn2.rotateAngleZ = -0.3490658503988659f;
		horn3.rotateAngleZ = -0.3490658503988659f;
		stand = new ModelRenderer(this, 40, 16);
		stand.setRotationPoint(0.0f, -1.0f, 0.0f);
		stand.addBox(1.5f, -8.0f, -2.5f, 1, 9, 1);
		stand.addBox(1.5f, -8.0f, 1.5f, 1, 9, 1);
		stand.setTextureOffset(44, 16).addBox(-2.5f, -6.0f, -0.5f, 1, 7, 1);
	}

	public void prepareLiquid(float f) {
		horn.postRender(f);
		horn1.postRender(f);
		horn2.postRender(f);
		horn3.postRender(f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		horn.render(f5);
		stand.render(f5);
	}
}
