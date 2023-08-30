package lotr.client.render.entity;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelBear;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBear extends RenderLiving {
	public static Map bearSkins = new HashMap();

	public LOTRRenderBear() {
		super(new LOTRModelBear(), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityBear bear = (LOTREntityBear) entity;
		return getBearSkin(bear.getBearType());
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		scaleBearModel();
	}

	public static ResourceLocation getBearSkin(LOTREntityBear.BearType type) {
		String s = type.textureName();
		ResourceLocation skin = (ResourceLocation) bearSkins.get(s);
		if (skin == null) {
			skin = new ResourceLocation("lotr:mob/bear/" + s + ".png");
			bearSkins.put(s, skin);
		}
		return skin;
	}

	public static void scaleBearModel() {
		float scale = 1.2f;
		GL11.glScalef(scale, scale, scale);
	}
}
