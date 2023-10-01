package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelDwarf extends LOTRModelBiped {
	public ModelRenderer bipedChest = new ModelRenderer(this, 24, 0);

	public LOTRModelDwarf() {
		this(0.0f);
	}

	public LOTRModelDwarf(float f) {
		this(f, 64, f == 0.0f ? 64 : 32);
	}

	public LOTRModelDwarf(float f, int width, int height) {
		super(f, 0.0f, width, height);
		bipedChest.addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
		bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.addChild(bipedChest);
		if (height == 64) {
			bipedHeadwear = new ModelRenderer(this, 0, 32);
			bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 12, 8, 0.5f + f);
			bipedHeadwear.setRotationPoint(0.0f, 2.0f, 0.0f);
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedChest.showModel = entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).shouldRenderNPCChest();
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glScalef(1.5f / f6, 1.5f / f6, 1.5f / f6);
			GL11.glTranslatef(0.0f, 16.0f * f5, 0.0f);
			bipedHead.render(f5);
			bipedHeadwear.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			bipedBody.render(f5);
			bipedRightArm.render(f5);
			bipedLeftArm.render(f5);
			bipedRightLeg.render(f5);
		} else {
			bipedHead.render(f5);
			bipedHeadwear.render(f5);
			GL11.glPushMatrix();
			GL11.glScalef(1.25f, 1.0f, 1.0f);
			bipedBody.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(-1.0f * f5, 0.0f, 0.0f);
			bipedRightArm.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(f5, 0.0f, 0.0f);
			bipedLeftArm.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(-0.25f * f5, 0.0f, 0.0f);
			GL11.glScalef(1.25f, 1.0f, 1.0f);
			bipedRightLeg.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslatef(0.25f * f5, 0.0f, 0.0f);
			GL11.glScalef(1.25f, 1.0f, 1.0f);
		}
		bipedLeftLeg.render(f5);
		GL11.glPopMatrix();
	}
}
