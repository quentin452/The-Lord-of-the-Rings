package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.animal.LOTREntitySwan;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelSwan extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer tail;
	public ModelRenderer neck;
	public ModelRenderer head;
	public ModelRenderer wingRight;
	public ModelRenderer wingLeft;
	public ModelRenderer legRight;
	public ModelRenderer legLeft;

	public LOTRModelSwan() {
		textureWidth = 64;
		textureHeight = 64;
		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(0.0f, 18.0f, 0.0f);
		body.addBox(-4.0f, -3.0f, -7.0f, 8, 6, 14);
		tail = new ModelRenderer(this, 24, 20);
		tail.setRotationPoint(0.0f, -2.0f, 7.0f);
		tail.addBox(-3.0f, -1.5f, -1.0f, 6, 4, 4);
		tail.setTextureOffset(24, 28).addBox(-2.0f, -1.0f, 3.0f, 4, 2, 3);
		body.addChild(tail);
		neck = new ModelRenderer(this, 44, 11);
		neck.setRotationPoint(0.0f, 0.0f, -5.5f);
		neck.addBox(-1.0f, -11.0f, -3.0f, 2, 13, 2);
		body.addChild(neck);
		head = new ModelRenderer(this, 44, 0);
		head.setRotationPoint(0.0f, -10.0f, -2.0f);
		head.addBox(-1.5f, -2.0f, -4.0f, 3, 3, 4);
		head.setTextureOffset(44, 7).addBox(-1.0f, -0.5f, -7.0f, 2, 1, 3);
		neck.addChild(head);
		wingRight = new ModelRenderer(this, 0, 20);
		wingRight.setRotationPoint(-4.0f, 18.0f, -5.0f);
		wingRight.addBox(-1.0f, -3.5f, -1.0f, 1, 7, 8);
		wingRight.setTextureOffset(0, 35).addBox(-1.0f, -4.5f, 7.0f, 1, 6, 3);
		wingRight.setTextureOffset(8, 35).addBox(-1.0f, -5.5f, 10.0f, 1, 5, 3);
		wingLeft = new ModelRenderer(this, 0, 20);
		wingLeft.mirror = true;
		wingLeft.setRotationPoint(4.0f, 18.0f, -5.0f);
		wingLeft.addBox(0.0f, -3.5f, -1.0f, 1, 7, 8);
		wingLeft.setTextureOffset(0, 35).addBox(0.0f, -4.5f, 7.0f, 1, 6, 3);
		wingLeft.setTextureOffset(8, 35).addBox(0.0f, -5.5f, 10.0f, 1, 5, 3);
		legRight = new ModelRenderer(this, 24, 33);
		legRight.setRotationPoint(-2.0f, 21.0f, 1.0f);
		legRight.addBox(-1.5f, 0.0f, -3.0f, 3, 3, 3);
		legLeft = new ModelRenderer(this, 24, 33);
		legLeft.mirror = true;
		legLeft.setRotationPoint(2.0f, 21.0f, 1.0f);
		legLeft.addBox(-1.5f, 0.0f, -3.0f, 3, 3, 3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
		wingRight.render(f5);
		wingLeft.render(f5);
		legRight.render(f5);
		legLeft.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		float tick = LOTRTickHandlerClient.renderTick;
		LOTREntitySwan swan = (LOTREntitySwan) entity;
		float f6 = swan.prevFlapPhase + (swan.flapPhase - swan.prevFlapPhase) * tick;
		float f7 = swan.prevFlapPower + (swan.flapPower - swan.prevFlapPower) * tick;
		float flapping = (MathHelper.sin(f6) + 1.0f) * f7;
		neck.rotateAngleX = -0.20943951023931956f;
		neck.rotateAngleX += f4 / 57.295776f * 0.4f;
		neck.rotateAngleX += swan.getPeckAngle(tick);
		neck.rotateAngleY = f3 / 57.295776f;
		head.rotateAngleX = -neck.rotateAngleX;
		tail.rotateAngleX = 0.3490658503988659f;
		tail.rotateAngleX += MathHelper.cos(f * 0.4f) * f1 * 0.5f;
		tail.rotateAngleX += MathHelper.cos(f2 * 0.1f) * 0.1f;
		float wingX = 0.17453292519943295f;
		float wingY = (1.0f + MathHelper.cos(f * 0.4f + 3.1415927f)) * f1 * 0.5f;
		wingY += (1.0f + MathHelper.cos(f2 * 0.15f)) * 0.1f;
		float wingZ = MathHelper.cos(f * 0.4f + 3.1415927f) * f1 * 0.2f;
		wingRight.rotateAngleX = wingX;
		wingLeft.rotateAngleX = wingX;
		wingRight.rotateAngleY = -(wingY += flapping * 0.2f);
		wingLeft.rotateAngleY = wingY;
		wingRight.rotateAngleZ = wingZ += flapping * 0.5f;
		wingLeft.rotateAngleZ = -wingZ;
		legRight.rotateAngleX = MathHelper.cos(f * 0.7f + 3.1415927f) * f1 * 1.0f;
		legLeft.rotateAngleX = MathHelper.cos(f * 0.7f) * f1 * 1.0f;
	}
}
