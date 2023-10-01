package lotr.client.model;

import lotr.client.render.entity.LOTRRenderPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRModelCompass extends ModelBase {
	public static LOTRModelCompass compassModel = new LOTRModelCompass();
	public static ResourceLocation compassTexture = new ResourceLocation("lotr:misc/compass.png");
	public ModelRenderer compass;
	public ModelBase ringModel = new LOTRModelPortal(false);
	public ModelBase writingModelOuter = new LOTRModelPortal(true);
	public ModelBase writingModelInner = new LOTRModelPortal(true);

	public LOTRModelCompass() {
		textureWidth = 32;
		textureHeight = 32;
		compass = new ModelRenderer(this, 0, 0);
		compass.setRotationPoint(0.0f, 0.0f, 0.0f);
		compass.addBox(-16.0f, 0.0f, -16.0f, 32, 0, 32);
	}

	public void render(float scale, float rotation) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(2884);
		GL11.glNormal3f(0.0f, 0.0f, 0.0f);
		GL11.glEnable(32826);
		GL11.glScalef(1.0f, 1.0f, -1.0f);
		GL11.glRotatef(40.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		texturemanager.bindTexture(compassTexture);
		compass.render(scale * 2.0f);
		texturemanager.bindTexture(LOTRRenderPortal.ringTexture);
		ringModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
		writingModelOuter.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 1.05f);
		texturemanager.bindTexture(LOTRRenderPortal.writingTexture);
		writingModelInner.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale * 0.85f);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
