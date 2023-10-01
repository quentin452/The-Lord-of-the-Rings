package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderWhiteOryx extends LOTRRenderGemsbok {
	public LOTRRandomSkins oryxSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/whiteOryx");

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return oryxSkins.getRandomSkin((LOTRRandomSkinEntity) entity);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = 0.9f;
		GL11.glScalef(scale, scale, scale);
	}
}
