package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelElk extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer head;
	public ModelRenderer nose;
	public ModelRenderer antlersRight_1;
	public ModelRenderer antlersRight_2;
	public ModelRenderer antlersRight_3;
	public ModelRenderer antlersRight_4;
	public ModelRenderer antlersLeft_1;
	public ModelRenderer antlersLeft_2;
	public ModelRenderer antlersLeft_3;
	public ModelRenderer antlersLeft_4;

	public LOTRModelElk() {
		this(0.0f);
	}

	public LOTRModelElk(float f) {
		textureWidth = 128;
		textureHeight = 64;
		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(0.0f, 4.0f, 9.0f);
		body.addBox(-6.0f, -4.0f, -21.0f, 12, 11, 26, f);
		ModelRenderer tail = new ModelRenderer(this, 0, 54);
		tail.addBox(-1.0f, -5.0f, 2.0f, 2, 2, 8, f);
		tail.rotateAngleX = -1.0471975511965976f;
		body.addChild(tail);
		leg1 = new ModelRenderer(this, 42, 37);
		leg1.setRotationPoint(-4.0f, 3.0f, 8.0f);
		leg1.addBox(-5.5f, 0.0f, -3.0f, 7, 11, 8, f);
		leg1.setTextureOffset(26, 37).addBox(-4.0f, 11.0f, -1.0f, 4, 10, 4, f);
		leg2 = new ModelRenderer(this, 42, 37);
		leg2.mirror = true;
		leg2.setRotationPoint(4.0f, 3.0f, 8.0f);
		leg2.addBox(-1.5f, 0.0f, -3.0f, 7, 11, 8, f);
		leg2.setTextureOffset(26, 37).addBox(0.0f, 11.0f, -1.0f, 4, 10, 4, f);
		leg3 = new ModelRenderer(this, 0, 37);
		leg3.setRotationPoint(-4.0f, 4.0f, -6.0f);
		leg3.addBox(-4.5f, 0.0f, -3.0f, 6, 10, 7, f);
		leg3.setTextureOffset(26, 37).addBox(-3.5f, 10.0f, -2.0f, 4, 10, 4, f);
		leg4 = new ModelRenderer(this, 0, 37);
		leg4.mirror = true;
		leg4.setRotationPoint(4.0f, 4.0f, -6.0f);
		leg4.addBox(-1.5f, 0.0f, -3.0f, 6, 10, 7, f);
		leg4.setTextureOffset(26, 37).addBox(-0.5f, 10.0f, -2.0f, 4, 10, 4, f);
		head = new ModelRenderer(this, 50, 0);
		head.setRotationPoint(0.0f, 4.0f, -10.0f);
		head.addBox(-2.0f, -10.0f, -4.0f, 4, 12, 8, f);
		head.setTextureOffset(74, 0).addBox(-3.0f, -16.0f, -8.0f, 6, 6, 13, f);
		head.setTextureOffset(50, 20);
		head.addBox(-2.0f, -18.0f, 3.0f, 1, 2, 1, f);
		head.mirror = true;
		head.addBox(1.0f, -18.0f, 3.0f, 1, 2, 1, f);
		nose = new ModelRenderer(this, 56, 20);
		nose.addBox(-1.0f, -14.5f, -9.0f, 2, 2, 1, f);
		antlersRight_1 = new ModelRenderer(this, 0, 0);
		antlersRight_1.addBox(10.0f, -19.0f, 2.5f, 1, 12, 1, f);
		antlersRight_1.rotateAngleZ = -1.1344640137963142f;
		antlersRight_2 = new ModelRenderer(this, 4, 0);
		antlersRight_2.addBox(-3.0f, -23.6f, 2.5f, 1, 8, 1, f);
		antlersRight_2.rotateAngleZ = -0.2617993877991494f;
		antlersRight_3 = new ModelRenderer(this, 8, 0);
		antlersRight_3.addBox(-8.0f, -36.0f, 2.5f, 1, 16, 1, f);
		antlersRight_3.rotateAngleZ = -0.2617993877991494f;
		antlersRight_4 = new ModelRenderer(this, 12, 0);
		antlersRight_4.addBox(7.5f, -35.0f, 2.5f, 1, 10, 1, f);
		antlersRight_4.rotateAngleZ = -0.8726646259971648f;
		head.addChild(antlersRight_1);
		head.addChild(antlersRight_2);
		head.addChild(antlersRight_3);
		head.addChild(antlersRight_4);
		antlersLeft_1 = new ModelRenderer(this, 0, 0);
		antlersLeft_1.mirror = true;
		antlersLeft_1.addBox(-11.0f, -19.0f, 2.5f, 1, 12, 1, f);
		antlersLeft_1.rotateAngleZ = 1.1344640137963142f;
		antlersLeft_2 = new ModelRenderer(this, 4, 0);
		antlersLeft_2.mirror = true;
		antlersLeft_2.addBox(2.0f, -23.6f, 2.5f, 1, 8, 1, f);
		antlersLeft_2.rotateAngleZ = 0.2617993877991494f;
		antlersLeft_3 = new ModelRenderer(this, 8, 0);
		antlersLeft_3.mirror = true;
		antlersLeft_3.addBox(7.0f, -36.0f, 2.5f, 1, 16, 1, f);
		antlersLeft_3.rotateAngleZ = 0.2617993877991494f;
		antlersLeft_4 = new ModelRenderer(this, 12, 0);
		antlersLeft_4.mirror = true;
		antlersLeft_4.addBox(-8.5f, -35.0f, 2.5f, 1, 10, 1, f);
		antlersLeft_4.rotateAngleZ = 0.8726646259971648f;
		head.addChild(antlersLeft_1);
		head.addChild(antlersLeft_2);
		head.addChild(antlersLeft_3);
		head.addChild(antlersLeft_4);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		boolean showAntlers;
		LOTREntityElk elk = (LOTREntityElk) entity;
		setRotationAngles(f, f1, f2, f3, f4, f5, elk);
		GL11.glPushMatrix();
		float scale = elk.getHorseSize();
		GL11.glTranslatef(0.0f, 24.0f * (1.0f - scale) * f5, 0.0f);
		GL11.glScalef(scale, scale, scale);
		antlersRight_1.showModel = showAntlers = scale > 0.75f;
		antlersRight_2.showModel = showAntlers;
		antlersRight_3.showModel = showAntlers;
		antlersRight_4.showModel = showAntlers;
		antlersLeft_1.showModel = showAntlers;
		antlersLeft_2.showModel = showAntlers;
		antlersLeft_3.showModel = showAntlers;
		antlersLeft_4.showModel = showAntlers;
		body.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		head.render(f5);
		if (LOTRMod.isChristmas()) {
			GL11.glColor3f(1.0f, 0.0f, 0.0f);
		}
		nose.render(f5);
		if (LOTRMod.isChristmas()) {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		LOTREntityElk elk = (LOTREntityElk) entity;
		float renderTick = LOTRTickHandlerClient.renderTick;
		float rearAmount = elk.getRearingAmount(renderTick);
		float antiRearAmount = 1.0f - rearAmount;
		head.rotationPointY = 4.0f;
		head.rotationPointZ = -10.0f;
		head.rotateAngleX = 0.3490658503988659f;
		head.rotateAngleY = 0.0f;
		head.rotationPointY = rearAmount * -6.0f + antiRearAmount * head.rotationPointY;
		head.rotationPointZ = rearAmount * -1.0f + antiRearAmount * head.rotationPointZ;
		head.rotateAngleX = (float) (head.rotateAngleX + Math.toRadians(f4));
		head.rotateAngleY = (float) (head.rotateAngleY + Math.toRadians(f3));
		head.rotateAngleX = antiRearAmount * head.rotateAngleX;
		head.rotateAngleY = antiRearAmount * head.rotateAngleY;
		if (f1 > 0.2f) {
			head.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.1f * f1;
		}
		nose.setRotationPoint(head.rotationPointX, head.rotationPointY, head.rotationPointZ);
		nose.rotateAngleX = head.rotateAngleX;
		nose.rotateAngleY = head.rotateAngleY;
		nose.rotateAngleZ = head.rotateAngleZ;
		body.rotateAngleX = 0.0f;
		body.rotateAngleX = rearAmount * -0.7853982f + antiRearAmount * body.rotateAngleX;
		float legRotation = MathHelper.cos(f * 0.4f + 3.1415927f) * f1;
		float f17 = -1.0471976f;
		float f18 = 0.2617994f * rearAmount;
		float f19 = MathHelper.cos(f2 * 0.4f + 3.1415927f);
		leg4.rotationPointY = -2.0f * rearAmount + 4.0f * antiRearAmount;
		leg4.rotationPointZ = -2.0f * rearAmount + -6.0f * antiRearAmount;
		leg3.rotationPointY = leg4.rotationPointY;
		leg3.rotationPointZ = leg4.rotationPointZ;
		leg1.rotateAngleX = f18 + legRotation * antiRearAmount;
		leg2.rotateAngleX = f18 + -legRotation * antiRearAmount;
		leg3.rotateAngleX = (f17 - f19) * rearAmount + -legRotation * 0.8f * antiRearAmount;
		leg4.rotateAngleX = (f17 + f19) * rearAmount + legRotation * 0.8f * antiRearAmount;
	}
}
