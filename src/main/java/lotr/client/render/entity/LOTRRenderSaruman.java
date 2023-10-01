package lotr.client.render.entity;

import lotr.client.model.LOTRModelHuman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

public class LOTRRenderSaruman extends LOTRRenderBiped {
	public static ResourceLocation skin = new ResourceLocation("lotr:mob/char/saruman.png");
	public Random rand = new Random();
	public boolean twitch;

	public LOTRRenderSaruman() {
		super(new LOTRModelHuman(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return skin;
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		super.preRenderCallback(entity, f);
		if (entity.ticksExisted % 60 == 0) {
			twitch = !twitch;
		}
		if (twitch) {
			GL11.glRotatef(rand.nextFloat() * 40.0f, 0.0f, 0.0f, 1.0f);
			GL11.glRotatef(rand.nextFloat() * 40.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(rand.nextFloat() * 40.0f, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(rand.nextFloat() * 0.5f, rand.nextFloat() * 0.5f, rand.nextFloat() * 0.5f);
		}
		int i = entity.ticksExisted % 360;
		float hue = i / 360.0f;
		Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
		float r = color.getRed() / 255.0f;
		float g = color.getGreen() / 255.0f;
		float b = color.getBlue() / 255.0f;
		GL11.glColor3f(r, g, b);
	}
}
