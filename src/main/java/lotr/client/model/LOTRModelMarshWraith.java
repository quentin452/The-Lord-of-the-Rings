package lotr.client.model;

import lotr.common.entity.npc.LOTREntityMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelMarshWraith extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer headwear;
	public ModelRenderer body;
	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public ModelRenderer cape;

	public LOTRModelMarshWraith() {
		textureHeight = 64;
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
		head.setRotationPoint(0.0f, 0.0f, 0.0f);
		headwear = new ModelRenderer(this, 32, 0);
		headwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, 0.5f);
		headwear.setRotationPoint(0.0f, 0.0f, 0.0f);
		body = new ModelRenderer(this, 0, 16);
		body.addBox(-4.0f, 0.0f, -2.0f, 8, 24, 4);
		body.setRotationPoint(0.0f, 0.0f, 0.0f);
		rightArm = new ModelRenderer(this, 46, 16);
		rightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4);
		rightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
		leftArm = new ModelRenderer(this, 46, 16);
		leftArm.mirror = true;
		leftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4);
		leftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
		cape = new ModelRenderer(this, 24, 16);
		cape.addBox(-5.0f, 1.0f, 3.0f, 10, 16, 1);
		cape.setRotationPoint(0.0f, 0.0f, 0.0f);
		cape.rotateAngleX = 0.1f;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		head.render(f5);
		headwear.render(f5);
		body.render(f5);
		rightArm.render(f5);
		leftArm.render(f5);
		cape.render(f5);
		if (entity instanceof LOTREntityMarshWraith) {
			GL11.glDisable(3042);
			GL11.glDisable(32826);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleY = f3 / 57.295776f;
		head.rotateAngleX = f4 / 57.295776f;
		headwear.rotateAngleY = head.rotateAngleY;
		headwear.rotateAngleX = head.rotateAngleX;
	}
}
