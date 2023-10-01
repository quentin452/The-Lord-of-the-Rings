package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelKebabStand;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class LOTRRenderKebabStand extends TileEntitySpecialRenderer {
	public static LOTRModelKebabStand standModel = new LOTRModelKebabStand();
	public static Map<String, ResourceLocation> standTextures = new HashMap<>();
	public static ResourceLocation rawTexture = new ResourceLocation("lotr:item/kebab/raw.png");
	public static ResourceLocation cookedTexture = new ResourceLocation("lotr:item/kebab/cooked.png");

	public static ResourceLocation getStandTexture(LOTRTileEntityKebabStand kebabStand) {
		ResourceLocation r;
		String s = kebabStand.getStandTextureName();
		if (!StringUtils.isNullOrEmpty(s)) {
			s = "_" + s;
		}
		r = standTextures.get(s = "stand" + s);
		if (r == null) {
			r = new ResourceLocation("lotr:item/kebab/" + s + ".png");
			standTextures.put(s, r);
		}
		return r;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand) tileentity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		int meta = kebabStand.getBlockMetadata();
		switch (meta) {
			case 2: {
				GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 5: {
				GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 3: {
				GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
				break;
			}
			case 4: {
				GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			}
		}
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float scale = 0.0625f;
		bindTexture(getStandTexture(kebabStand));
		standModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
		int meatAmount = kebabStand.getMeatAmount();
		if (meatAmount > 0) {
			boolean cooked = kebabStand.isCooked();
			if (cooked) {
				bindTexture(cookedTexture);
			} else {
				bindTexture(rawTexture);
			}
			float spin = kebabStand.getKebabSpin(f);
			standModel.renderKebab(scale, meatAmount, spin);
		}
		GL11.glEnable(2884);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
