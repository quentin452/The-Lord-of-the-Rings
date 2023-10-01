package lotr.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGlowingEyes {
	public static void renderGlowingEyes(Entity entity, ResourceLocation eyesTexture, Model model, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(1, 1);
		float lastX = OpenGlHelper.lastBrightnessX;
		float lastY = OpenGlHelper.lastBrightnessY;
		int light = 15728880;
		int lx = light % 65536;
		int ly = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(eyesTexture);
		model.renderGlowingEyes(entity, f, f1, f2, f3, f4, f5);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	public interface Model {
		void renderGlowingEyes(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7);
	}

}
