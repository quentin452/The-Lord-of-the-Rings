package lotr.client.render.entity;

import lotr.client.model.LOTRModelRabbit;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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
