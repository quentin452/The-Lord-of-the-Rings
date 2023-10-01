package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelFlamingo extends ModelBase {
	public ModelRenderer head = new ModelRenderer(this, 8, 24);
	public ModelRenderer body;
	public ModelRenderer tail;
	public ModelRenderer wingLeft;
	public ModelRenderer wingRight;
	public ModelRenderer legLeft;
	public ModelRenderer legRight;
	public ModelRenderer head_child;
	public ModelRenderer body_child;
	public ModelRenderer tail_child;
	public ModelRenderer wingLeft_child;
	public ModelRenderer wingRight_child;
	public ModelRenderer legLeft_child;
	public ModelRenderer legRight_child;

	public LOTRModelFlamingo() {
		head.addBox(-2.0f, -17.0f, -2.0f, 4, 4, 4);
		head.setRotationPoint(0.0f, 5.0f, -2.0f);
		head.setTextureOffset(24, 27).addBox(-1.5f, -16.0f, -5.0f, 3, 2, 3);
		head.setTextureOffset(36, 30).addBox(-1.0f, -14.0f, -5.0f, 2, 1, 1);
		head.setTextureOffset(0, 16).addBox(-1.0f, -15.0f, -1.0f, 2, 14, 2);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-3.0f, 0.0f, -4.0f, 6, 7, 8);
		body.setRotationPoint(0.0f, 3.0f, 0.0f);
		tail = new ModelRenderer(this, 42, 23);
		tail.addBox(-2.5f, 0.0f, 0.0f, 5, 3, 6);
		tail.setRotationPoint(0.0f, 4.0f, 3.0f);
		wingLeft = new ModelRenderer(this, 36, 0);
		wingLeft.addBox(-1.0f, 0.0f, -3.0f, 1, 8, 6);
		wingLeft.setRotationPoint(-3.0f, 3.0f, 0.0f);
		wingRight = new ModelRenderer(this, 50, 0);
		wingRight.addBox(0.0f, 0.0f, -3.0f, 1, 8, 6);
		wingRight.setRotationPoint(3.0f, 3.0f, 0.0f);
		legLeft = new ModelRenderer(this, 30, 0);
		legLeft.addBox(-0.5f, 0.0f, -0.5f, 1, 16, 1);
		legLeft.setRotationPoint(-2.0f, 8.0f, 0.0f);
		legLeft.setTextureOffset(30, 17).addBox(-1.5f, 14.9f, -3.5f, 3, 1, 3);
		legRight = new ModelRenderer(this, 30, 0);
		legRight.addBox(-0.5f, 0.0f, -0.5f, 1, 16, 1);
		legRight.setRotationPoint(2.0f, 8.0f, 0.0f);
		legRight.setTextureOffset(30, 17).addBox(-1.5f, 14.9f, -3.5f, 3, 1, 3);
		head_child = new ModelRenderer(this, 0, 24);
		head_child.addBox(-2.0f, -4.0f, -4.0f, 4, 4, 4);
		head_child.setRotationPoint(0.0f, 15.0f, -3.0f);
		head_child.setTextureOffset(16, 28).addBox(-1.0f, -2.0f, -6.0f, 2, 2, 2);
		body_child = new ModelRenderer(this, 0, 0);
		body_child.addBox(-3.0f, 0.0f, -4.0f, 6, 5, 7);
		body_child.setRotationPoint(0.0f, 14.0f, 0.0f);
		tail_child = new ModelRenderer(this, 0, 14);
		tail_child.addBox(-2.0f, 0.0f, 0.0f, 4, 2, 3);
		tail_child.setRotationPoint(0.0f, 14.5f, 3.0f);
		wingLeft_child = new ModelRenderer(this, 40, 0);
		wingLeft_child.addBox(-1.0f, 0.0f, -3.0f, 1, 4, 5);
		wingLeft_child.setRotationPoint(-3.0f, 14.0f, 0.0f);
		wingRight_child = new ModelRenderer(this, 52, 0);
		wingRight_child.addBox(0.0f, 0.0f, -3.0f, 1, 4, 5);
		wingRight_child.setRotationPoint(3.0f, 14.0f, 0.0f);
		legLeft_child = new ModelRenderer(this, 27, 0);
		legLeft_child.addBox(-0.5f, 0.0f, -0.5f, 1, 5, 1);
		legLeft_child.setRotationPoint(-2.0f, 19.0f, 0.0f);
		legLeft_child.setTextureOffset(27, 7).addBox(-1.5f, 3.9f, -3.5f, 3, 1, 3);
		legRight_child = new ModelRenderer(this, 27, 0);
		legRight_child.addBox(-0.5f, 0.0f, -0.5f, 1, 5, 1);
		legRight_child.setRotationPoint(2.0f, 19.0f, 0.0f);
		legRight_child.setTextureOffset(27, 7).addBox(-1.5f, 3.9f, -3.5f, 3, 1, 3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if (isChild) {
			head_child.render(f5);
			body_child.render(f5);
			tail_child.render(f5);
			wingLeft_child.render(f5);
			wingRight_child.render(f5);
			legLeft_child.render(f5);
			legRight_child.render(f5);
		} else {
			head.render(f5);
			body.render(f5);
			tail.render(f5);
			wingLeft.render(f5);
			wingRight.render(f5);
			legLeft.render(f5);
			legRight.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		LOTREntityFlamingo flamingo = (LOTREntityFlamingo) entity;
		if (isChild) {
			head_child.rotateAngleX = f4 / 57.29578f;
			head_child.rotateAngleY = f3 / 57.29578f;
			legLeft_child.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
			legRight_child.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
			wingLeft_child.rotateAngleZ = f2 * 0.4f;
			wingRight_child.rotateAngleZ = -f2 * 0.4f;
			tail_child.rotateAngleX = -0.25f;
		} else {
			head.rotateAngleX = f4 / 57.29578f;
			head.rotateAngleY = f3 / 57.29578f;
			legLeft.rotateAngleX = MathHelper.cos(f * 0.6662f) * 0.9f * f1;
			legRight.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 0.9f * f1;
			wingLeft.rotateAngleZ = f2 * 0.4f;
			wingRight.rotateAngleZ = -f2 * 0.4f;
			tail.rotateAngleX = -0.25f;
			int cur = flamingo.getFishingTickCur();
			int pre = flamingo.getFishingTickPre();
			float fishing = pre + (cur - pre) * LOTRTickHandlerClient.renderTick;
			if (cur > 160 + 20) {
				head.rotateAngleX = 3.1415927f * (200.0f - fishing) / 20.0f;
			} else if (cur > 20) {
				head.rotateAngleX = 3.1415927f;
			} else if (cur > 0) {
				head.rotateAngleX = 3.1415927f * fishing / 20.0f;
			}
		}
	}
}
