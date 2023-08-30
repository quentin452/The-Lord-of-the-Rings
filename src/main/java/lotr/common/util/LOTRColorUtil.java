package lotr.common.util;

import java.awt.Color;
import java.util.Random;

import net.minecraft.item.*;
import net.minecraft.util.MathHelper;

public class LOTRColorUtil {
	public static ItemStack dyeLeather(ItemStack itemstack, int color) {
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
	}

	public static ItemStack dyeLeather(ItemStack itemstack, int[] colors, Random rand) {
		int color = colors[rand.nextInt(colors.length)];
		return dyeLeather(itemstack, color);
	}

	public static float[] lerpColors(float[] rgb0, int color1, float f) {
		float[] rgb1 = new Color(color1).getColorComponents(null);
		float r0 = rgb0[0];
		float g0 = rgb0[1];
		float b0 = rgb0[2];
		float r1 = rgb1[0];
		float g1 = rgb1[1];
		float b1 = rgb1[2];
		float r = r0 + (r1 - r0) * f;
		float g = g0 + (g1 - g0) * f;
		float b = b0 + (b1 - b0) * f;
		r = MathHelper.clamp_float(r, 0.0f, 1.0f);
		g = MathHelper.clamp_float(g, 0.0f, 1.0f);
		b = MathHelper.clamp_float(b, 0.0f, 1.0f);
		return new float[]{r, g, b};
	}

	public static float[] lerpColors(int color0, int color1, float f) {
		float[] rgb0 = new Color(color0).getColorComponents(null);
		return lerpColors(rgb0, color1, f);
	}

	public static int lerpColors_I(float[] rgb0, int color1, float f) {
		float[] rgb = lerpColors(rgb0, color1, f);
		return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
	}

	public static int lerpColors_I(int color0, int color1, float f) {
		float[] rgb0 = new Color(color0).getColorComponents(null);
		return lerpColors_I(rgb0, color1, f);
	}
}
