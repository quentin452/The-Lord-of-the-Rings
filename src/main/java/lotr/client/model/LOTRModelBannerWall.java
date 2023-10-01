package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBannerWall extends ModelBase {
	public ModelRenderer post;
	public ModelRenderer banner;

	public LOTRModelBannerWall() {
		textureWidth = 64;
		textureHeight = 64;
		post = new ModelRenderer(this, 4, 18);
		post.setRotationPoint(0.0f, -8.0f, 0.0f);
		post.addBox(-8.0f, 0.0f, -0.5f, 16, 1, 1);
		banner = new ModelRenderer(this, 0, 0);
		banner.setRotationPoint(0.0f, -7.0f, 0.0f);
		banner.addBox(-8.0f, 0.0f, 0.0f, 16, 32, 0);
	}

	public void renderBanner(float f) {
		banner.render(f);
	}

	public void renderPost(float f) {
		post.render(f);
	}
}
