package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBanner extends ModelBase {
	public ModelRenderer stand;
	public ModelRenderer post;
	public ModelRenderer lowerPost;
	public ModelRenderer bannerFront;
	public ModelRenderer bannerBack;

	public LOTRModelBanner() {
		textureWidth = 64;
		textureHeight = 64;
		stand = new ModelRenderer(this, 0, 0);
		stand.setRotationPoint(0.0f, 24.0f, 0.0f);
		stand.addBox(-6.0f, -2.0f, -6.0f, 12, 2, 12);
		post = new ModelRenderer(this, 0, 14);
		post.setRotationPoint(0.0f, 24.0f, 0.0f);
		post.addBox(-0.5f, -48.0f, -0.5f, 1, 47, 1);
		post.setTextureOffset(4, 14).addBox(-8.0f, -43.0f, -1.5f, 16, 1, 3);
		lowerPost = new ModelRenderer(this, 0, 14);
		lowerPost.setRotationPoint(0.0f, 24.0f, 0.0f);
		lowerPost.addBox(-0.5f, -1.0f, -0.5f, 1, 24, 1);
		bannerFront = new ModelRenderer(this, 0, 0);
		bannerFront.setRotationPoint(0.0f, -18.0f, 0.0f);
		bannerFront.addBox(-8.0f, 0.0f, -1.0f, 16, 32, 0);
		bannerBack = new ModelRenderer(this, 0, 0);
		bannerBack.setRotationPoint(0.0f, -18.0f, 0.0f);
		bannerBack.addBox(-8.0f, 0.0f, -1.0f, 16, 32, 0);
		bannerBack.rotateAngleY = 3.1415927f;
	}

	public void renderBanner(float f) {
		bannerFront.render(f);
		bannerBack.render(f);
	}

	public void renderLowerPost(float f) {
		lowerPost.render(f);
	}

	public void renderPost(float f) {
		post.render(f);
	}

	public void renderStand(float f) {
		stand.render(f);
	}
}
