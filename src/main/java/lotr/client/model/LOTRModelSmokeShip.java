package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSmokeShip extends ModelBase {
	public ModelRenderer hull = new ModelRenderer(this);
	public ModelRenderer deck;
	public ModelRenderer mast1;
	public ModelRenderer sail1;
	public ModelRenderer mast2;
	public ModelRenderer sail2;
	public ModelRenderer mast3;
	public ModelRenderer sail3;
	public ModelRenderer bow;
	public ModelRenderer stern;

	public LOTRModelSmokeShip() {
		hull.addBox(-3.5f, 1.0f, -8.0f, 7, 5, 16);
		hull.setRotationPoint(0.0f, 0.0f, 0.0f);
		deck = new ModelRenderer(this);
		deck.addBox(-5.0f, 0.0f, -8.0f, 10, 1, 16);
		deck.setRotationPoint(0.0f, 0.0f, 0.0f);
		mast1 = new ModelRenderer(this);
		mast1.addBox(-1.0f, -9.0f, -6.0f, 2, 9, 2);
		mast1.setRotationPoint(0.0f, 0.0f, 0.0f);
		sail1 = new ModelRenderer(this);
		sail1.addBox(-6.0f, -8.0f, -5.5f, 12, 6, 1);
		sail1.setRotationPoint(0.0f, 0.0f, 0.0f);
		mast2 = new ModelRenderer(this);
		mast2.addBox(-1.0f, -12.0f, -1.0f, 2, 12, 2);
		mast2.setRotationPoint(0.0f, 0.0f, 0.0f);
		sail2 = new ModelRenderer(this);
		sail2.addBox(-8.0f, -11.0f, -0.5f, 16, 8, 1);
		sail2.setRotationPoint(0.0f, 0.0f, 0.0f);
		mast3 = new ModelRenderer(this);
		mast3.addBox(-1.0f, -9.0f, 4.0f, 2, 9, 2);
		mast3.setRotationPoint(0.0f, 0.0f, 0.0f);
		sail3 = new ModelRenderer(this);
		sail3.addBox(-6.0f, -8.0f, 4.5f, 12, 6, 1);
		sail3.setRotationPoint(0.0f, 0.0f, 0.0f);
		bow = new ModelRenderer(this);
		bow.addBox(-3.5f, -1.0f, -12.0f, 7, 3, 4);
		bow.setRotationPoint(0.0f, 0.0f, 0.0f);
		stern = new ModelRenderer(this);
		stern.addBox(-3.5f, -1.0f, 8.0f, 7, 3, 4);
		stern.setRotationPoint(0.0f, 0.0f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		hull.render(f5);
		deck.render(f5);
		mast1.render(f5);
		sail1.render(f5);
		mast2.render(f5);
		sail2.render(f5);
		mast3.render(f5);
		sail3.render(f5);
		bow.render(f5);
		stern.render(f5);
	}
}
