package lotr.client.render.entity;

import lotr.client.model.LOTRModelFish;
import lotr.common.entity.animal.LOTREntityFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class LOTRRenderFish extends RenderLiving {
	public static Map<String, LOTRRandomSkins> fishTypeSkins = new HashMap<>();

	public LOTRRenderFish() {
		super(new LOTRModelFish(), 0.0f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityFish fish = (LOTREntityFish) entity;
		String type = fish.getFishTextureDir();
		LOTRRandomSkins skins = getFishSkins(type);
		return skins.getRandomSkin(fish);
	}

	public LOTRRandomSkins getFishSkins(String s) {
		LOTRRandomSkins skins = fishTypeSkins.get(s);
		if (skins == null) {
			skins = LOTRRandomSkins.loadSkinsList("lotr:mob/fish/" + s);
			fishTypeSkins.put(s, skins);
		}
		return skins;
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		if (!entity.isInWater()) {
			GL11.glTranslatef(0.0f, -0.05f, 0.0f);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
		}
	}
}
