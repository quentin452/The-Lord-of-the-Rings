package lotr.client.render.entity;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelButterfly;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderButterfly extends RenderLiving {
	public static Map<LOTREntityButterfly.ButterflyType, LOTRRandomSkins> textures = new EnumMap<>(LOTREntityButterfly.ButterflyType.class);

	public LOTRRenderButterfly() {
		super(new LOTRModelButterfly(), 0.2f);
		for (LOTREntityButterfly.ButterflyType t : LOTREntityButterfly.ButterflyType.values()) {
			textures.put(t, LOTRRandomSkins.loadSkinsList("lotr:mob/butterfly/" + t.textureDir));
		}
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityButterfly butterfly = (LOTREntityButterfly) entity;
		if (butterfly.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(2896);
		}
		super.doRender(entity, d, d1, d2, f, f1);
		GL11.glEnable(2896);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityButterfly butterfly = (LOTREntityButterfly) entity;
		LOTRRandomSkins skins = textures.get(butterfly.getButterflyType());
		return skins.getRandomSkin(butterfly);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		LOTREntityButterfly butterfly = (LOTREntityButterfly) entity;
		if (butterfly.isButterflyStill() && butterfly.flapTime > 0) {
			return butterfly.flapTime - f;
		}
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		GL11.glScalef(0.3f, 0.3f, 0.3f);
	}
}
