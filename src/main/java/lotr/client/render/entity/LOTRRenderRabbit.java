package lotr.client.render.entity;

import lotr.common.entity.LOTRRandomSkinEntity;
import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRabbit extends RenderLiving {
	public static LOTRRandomSkins rabbitSkins;

	public LOTRRenderRabbit() {
		super(new LOTRModelRabbit(), 0.3f);
		rabbitSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/rabbit");
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		LOTRRandomSkinEntity rabbit = (LOTRRandomSkinEntity) entity;
		return rabbitSkins.getRandomSkin(rabbit);
	}

	@Override
	public void preRenderCallback(EntityLivingBase entity, float f) {
		GL11.glScalef(0.75f, 0.75f, 0.75f);
	}
}
