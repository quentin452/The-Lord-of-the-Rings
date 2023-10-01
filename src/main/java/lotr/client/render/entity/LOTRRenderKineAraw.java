package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderKineAraw extends LOTRRenderAurochs {
	public static LOTRRandomSkins kineSkins;

	public LOTRRenderKineAraw() {
		kineSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/kineAraw");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity kine = (LOTRRandomSkinEntity) entity;
		return kineSkins.getRandomSkin(kine);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		float scale = LOTREntityKineAraw.KINE_SCALE;
		GL11.glScalef(scale, scale, scale);
	}
}
