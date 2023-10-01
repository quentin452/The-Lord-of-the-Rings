package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelElf extends LOTRModelBiped implements LOTRGlowingEyes.Model {
	public ModelRenderer earRight = new ModelRenderer(this, 0, 0);
	public ModelRenderer earLeft;
	public ModelRenderer bipedChest;

	public LOTRModelElf() {
		this(0.0f);
	}

	public LOTRModelElf(float f) {
		this(f, 64, f == 0.0f ? 64 : 32);
	}

	public LOTRModelElf(float f, int width, int height) {
		super(f, 0.0f, width, height);
		earRight.addBox(-4.0f, -6.5f, -1.0f, 1, 4, 2);
		earRight.setRotationPoint(0.0f, 0.0f, 0.0f);
		earRight.rotateAngleZ = -0.2617994f;
		earLeft = new ModelRenderer(this, 0, 0);
		earLeft.mirror = true;
		earLeft.addBox(3.0f, -6.5f, -1.0f, 1, 4, 2);
		earLeft.setRotationPoint(0.0f, 0.0f, 0.0f);
		earLeft.rotateAngleZ = 0.2617994f;
		bipedHead.addChild(earRight);
		bipedHead.addChild(earLeft);
		bipedChest = new ModelRenderer(this, 24, 0);
		bipedChest.addBox(-3.0f, 2.0f, -4.0f, 6, 3, 2, f);
		bipedChest.setRotationPoint(0.0f, 0.0f, 0.0f);
		bipedBody.addChild(bipedChest);
		if (height == 64) {
			bipedHeadwear = new ModelRenderer(this, 0, 32);
			bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 16, 8, 0.5f + f);
			bipedHeadwear.setRotationPoint(0.0f, 0.0f, 0.0f);
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
			bipedLeftLeg.render(f5);
			GL11.glPopMatrix();
		} else {
			bipedHead.render(f5);
			bipedHeadwear.render(f5);
			bipedBody.render(f5);
			bipedRightArm.render(f5);
			bipedLeftArm.render(f5);
			bipedRightLeg.render(f5);
			bipedLeftLeg.render(f5);
		}
	}

	@Override
	public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bipedHead.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		LOTREntityElf elf;
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (entity instanceof LOTREntityElf && (elf = (LOTREntityElf) entity).isJazz() && elf.isSolo()) {
			bipedRightArm.rotateAngleY = -0.7853981633974483f;
			bipedLeftArm.rotateAngleY = -bipedRightArm.rotateAngleY;
			bipedLeftArm.rotateAngleX = bipedRightArm.rotateAngleX = -0.8726646259971648f;
		}
	}
}
