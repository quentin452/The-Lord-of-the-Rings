package lotr.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelFish extends ModelBase {
	public ModelRenderer body = new ModelRenderer(this, 0, 0);
	public ModelRenderer finTop;
	public ModelRenderer finRight;
	public ModelRenderer finLeft;
	public ModelRenderer finBack;

	public LOTRModelFish() {
		body.setRotationPoint(0.0f, 22.0f, -1.0f);
		body.addBox(-0.5f, -2.0f, -3.0f, 1, 3, 6);
		finTop = new ModelRenderer(this, 14, 0);
		finTop.setRotationPoint(0.0f, 0.0f, -1.5f);
		finTop.addBox(0.0f, -2.0f, 0.0f, 0, 2, 4);
		body.addChild(finTop);
		finRight = new ModelRenderer(this, 22, 0);
		finRight.setRotationPoint(0.0f, 0.0f, -1.0f);
		finRight.addBox(-0.5f, -1.0f, 0.0f, 0, 2, 3);
		body.addChild(finRight);
		finLeft = new ModelRenderer(this, 22, 0);
		finLeft.setRotationPoint(0.0f, 0.0f, -1.0f);
		finLeft.addBox(0.5f, -1.0f, 0.0f, 0, 2, 3);
		body.addChild(finLeft);
		finBack = new ModelRenderer(this, 0, 9);
		finBack.setRotationPoint(0.0f, -0.5f, 1.5f);
		finBack.addBox(0.0f, -5.0f, 0.0f, 0, 5, 5);
		body.addChild(finBack);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		body.render(f5);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		finTop.rotateAngleX = 0.47123889803846897f;
		finRight.rotateAngleX = -0.2617993877991494f;
		finRight.rotateAngleY = -0.5235987755982988f;
		finRight.rotateAngleY += MathHelper.cos(f2 * 0.5f + 3.1415927f) * 0.17453292519943295f;
		finLeft.rotateAngleX = finRight.rotateAngleX;
		finLeft.rotateAngleY = -finRight.rotateAngleY;
		finBack.rotateAngleX = -0.7853981633974483f;
	}
}
