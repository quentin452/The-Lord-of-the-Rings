package lotr.client.render.entity;

import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public abstract class LOTRRenderRugBase extends Render {
	public ModelBase rugModel;

	protected LOTRRenderRugBase(ModelBase m) {
		rugModel = m;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		LOTREntityRugBase rug = (LOTREntityRugBase) entity;
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		bindEntityTexture(rug);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		GL11.glRotatef(180.0f - rug.rotationYaw, 0.0f, 1.0f, 0.0f);
		preRenderCallback();
		rugModel.render(rug, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}

	public void preRenderCallback() {
	}
}
