package lotr.client.render.entity;

import lotr.client.model.LOTRModelBird;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class LOTRRenderBird extends RenderLiving {
	public static Map<String, LOTRRandomSkins> birdTypeSkins = new HashMap<>();
	public static boolean renderStolenItem = true;

	public LOTRRenderBird() {
		super(new LOTRModelBird(), 0.2f);
	}

	public LOTRRandomSkins getBirdSkins(String s) {
		LOTRRandomSkins skins = birdTypeSkins.get(s);
		if (skins == null) {
			skins = LOTRRandomSkins.loadSkinsList("lotr:mob/bird/" + s);
			birdTypeSkins.put(s, skins);
		}
		return skins;
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTREntityBird bird = (LOTREntityBird) entity;
		String type = bird.getBirdTextureDir();
		LOTRRandomSkins skins = getBirdSkins(type);
		return skins.getRandomSkin(bird);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entity, float f) {
		LOTREntityBird bird = (LOTREntityBird) entity;
		if (bird.isBirdStill() && bird.flapTime > 0) {
			return bird.flapTime - f;
		}
		return super.handleRotationFloat(entity, f);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		if (entity instanceof LOTREntityCrebain) {
			float scale = LOTREntityCrebain.CREBAIN_SCALE;
			GL11.glScalef(scale, scale, scale);
		} else if (entity instanceof LOTREntityGorcrow) {
			float scale = LOTREntityGorcrow.GORCROW_SCALE;
			GL11.glScalef(scale, scale, scale);
		} else if (entity instanceof LOTREntitySeagull) {
			float scale = LOTREntitySeagull.SEAGULL_SCALE;
			GL11.glScalef(scale, scale, scale);
		}
	}

	@Override
	public void renderEquippedItems(EntityLivingBase entity, float f) {
		LOTREntityBird bird = (LOTREntityBird) entity;
		if (renderStolenItem) {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			ItemStack stolenItem = bird.getStolenItem();
			if (stolenItem != null) {
				GL11.glPushMatrix();
				((LOTRModelBird) mainModel).head.postRender(0.0625f);
				GL11.glTranslatef(0.05f, 1.4f, -0.1f);
				float scale = 0.25f;
				GL11.glScalef(scale, scale, scale);
				renderManager.itemRenderer.renderItem(entity, stolenItem, 0);
				GL11.glPopMatrix();
			}
		}
	}
}
