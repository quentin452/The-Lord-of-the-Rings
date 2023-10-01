package lotr.client.model;

import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelDeer extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leg1Foot;
	public ModelRenderer leg2Foot;
	public ModelRenderer leg3Foot;
	public ModelRenderer leg4Foot;
	public ModelRenderer tail;
	public ModelRenderer head;
	public ModelRenderer antlers;

	public LOTRModelDeer() {
		this(0.0f);
	}

	public LOTRModelDeer(float f) {
		textureWidth = 64;
		textureHeight = 64;
		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(0.0f, 14.0f, 0.0f);
		body.addBox(-3.5f, -4.0f, -7.0f, 7, 7, 15, f);
		leg1 = new ModelRenderer(this, 12, 46);
		leg1.setRotationPoint(-4.0f, 14.0f, 5.0f);
		leg1.addBox(-1.0f, -2.0f, -2.0f, 3, 6, 4, f);
		leg1Foot = new ModelRenderer(this, 12, 56);
		leg1Foot.setRotationPoint(0.5f, 4.0f, 0.0f);
		leg1Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
		leg1.addChild(leg1Foot);
		leg2 = new ModelRenderer(this, 12, 46);
		leg2.setRotationPoint(4.0f, 14.0f, 5.0f);
		leg2.mirror = true;
		leg2.addBox(-2.0f, -2.0f, -2.0f, 3, 6, 4, f);
		leg2Foot = new ModelRenderer(this, 12, 56);
		leg2Foot.setRotationPoint(-0.5f, 4.0f, 0.0f);
		leg2Foot.mirror = true;
		leg2Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
		leg2.addChild(leg2Foot);
		leg3 = new ModelRenderer(this, 0, 47);
		leg3.setRotationPoint(-3.0f, 14.0f, -4.0f);
		leg3.addBox(-1.5f, -2.0f, -1.5f, 3, 6, 3, f);
		leg3Foot = new ModelRenderer(this, 0, 56);
		leg3Foot.setRotationPoint(0.0f, 4.0f, 0.0f);
		leg3Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
		leg3.addChild(leg3Foot);
		leg4 = new ModelRenderer(this, 0, 47);
		leg4.setRotationPoint(3.0f, 14.0f, -4.0f);
		leg4.mirror = true;
		leg4.addBox(-1.5f, -2.0f, -1.5f, 3, 6, 3, f);
		leg4Foot = new ModelRenderer(this, 0, 56);
		leg4Foot.setRotationPoint(0.0f, 4.0f, 0.0f);
		leg4Foot.mirror = true;
		leg4Foot.addBox(-1.0f, 0.0f, -1.0f, 2, 6, 2, f);
		leg4.addChild(leg4Foot);
		tail = new ModelRenderer(this, 20, 58);
		tail.setRotationPoint(0.0f, 14.0f, 0.0f);
		tail.addBox(-1.5f, -8.0f, 3.0f, 3, 2, 4, f);
		head = new ModelRenderer(this, 0, 22);
		head.setRotationPoint(0.0f, 11.0f, -5.0f);
		head.addBox(-2.5f, -8.0f, -6.0f, 5, 4, 7, f);
		head.setTextureOffset(24, 22).addBox(-2.0f, -4.0f, -4.0f, 4, 7, 4, f);
		ModelRenderer earRight = new ModelRenderer(this, 0, 22);
		earRight.setRotationPoint(-2.0f, -8.0f, 0.0f);
		earRight.addBox(-1.0f, -2.0f, -0.5f, 2, 3, 1, f);
		earRight.rotateAngleY = 0.5235987755982988f;
		earRight.rotateAngleZ = -0.8726646259971648f;
		head.addChild(earRight);
		ModelRenderer earLeft = new ModelRenderer(this, 0, 22);
		earLeft.setRotationPoint(2.0f, -8.0f, 0.0f);
		earLeft.mirror = true;
		earLeft.addBox(-1.0f, -2.0f, -0.5f, 2, 3, 1, f);
		earLeft.rotateAngleY = -0.5235987755982988f;
		earLeft.rotateAngleZ = 0.8726646259971648f;
		head.addChild(earLeft);
		antlers = new ModelRenderer(this, 0, 0);
		antlers.setRotationPoint(0.0f, 0.0f, 0.0f);
		head.addChild(antlers);
		ModelRenderer antlerRight1 = new ModelRenderer(this, 0, 33);
		antlerRight1.setRotationPoint(-2.0f, -7.0f, 1.0f);
		antlerRight1.addBox(-0.5f, -8.0f, -0.5f, 1, 9, 1, f);
		antlerRight1.rotateAngleX = -0.6981317007977318f;
		antlerRight1.rotateAngleZ = -0.6108652381980153f;
		antlers.addChild(antlerRight1);
		ModelRenderer antlerRight2 = new ModelRenderer(this, 4, 33);
		antlerRight2.setRotationPoint(-2.0f, -6.0f, 0.0f);
		antlerRight2.addBox(-0.5f, -6.0f, -0.5f, 1, 6, 1, f);
		antlerRight2.rotateAngleX = -1.0471975511965976f;
		antlerRight2.rotateAngleY = -0.8726646259971648f;
		antlerRight2.rotateAngleZ = -0.3490658503988659f;
		antlers.addChild(antlerRight2);
		ModelRenderer antlerLeft1 = new ModelRenderer(this, 0, 33);
		antlerLeft1.setRotationPoint(2.0f, -7.0f, 1.0f);
		antlerLeft1.mirror = true;
		antlerLeft1.addBox(-0.5f, -8.0f, -0.5f, 1, 9, 1, f);
		antlerLeft1.rotateAngleX = -0.6981317007977318f;
		antlerLeft1.rotateAngleZ = 0.6108652381980153f;
		antlers.addChild(antlerLeft1);
		ModelRenderer antlerLeft2 = new ModelRenderer(this, 4, 33);
		antlerLeft2.setRotationPoint(2.0f, -6.0f, 0.0f);
		antlerLeft2.mirror = true;
		antlerLeft2.addBox(-0.5f, -6.0f, -0.5f, 1, 6, 1, f);
		antlerLeft2.rotateAngleX = -1.0471975511965976f;
		antlerLeft2.rotateAngleY = 0.8726646259971648f;
		antlerLeft2.rotateAngleZ = 0.3490658503988659f;
		antlers.addChild(antlerLeft2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		LOTREntityDeer deer = (LOTREntityDeer) entity;
		antlers.showModel = deer.isMale() && !isChild;
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f);
			head.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			body.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			tail.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = 0.5235987755982988f;
		head.rotateAngleY = 0.0f;
		head.rotateAngleX += (float) Math.toRadians(f4);
		head.rotateAngleY += (float) Math.toRadians(f3);
		leg1.rotateAngleX = MathHelper.cos(f * 0.8f) * f1 * 1.4f;
		leg2.rotateAngleX = MathHelper.cos(f * 0.8f + 3.1415927f) * f1 * 1.4f;
		leg3.rotateAngleX = MathHelper.cos(f * 0.8f + 3.1415927f) * f1 * 1.4f;
		leg4.rotateAngleX = MathHelper.cos(f * 0.8f) * f1 * 1.4f;
		leg1Foot.rotateAngleX = leg1.rotateAngleX * -0.6f;
		leg2Foot.rotateAngleX = leg2.rotateAngleX * -0.6f;
		leg3Foot.rotateAngleX = leg3.rotateAngleX * -0.6f;
		leg4Foot.rotateAngleX = leg4.rotateAngleX * -0.6f;
		tail.rotateAngleX = -0.8726646259971648f;
	}
}
