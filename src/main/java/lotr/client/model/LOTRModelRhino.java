package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelRhino extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer neck;
	public ModelRenderer horn1;
	public ModelRenderer horn2;
	public ModelRenderer body;
	public ModelRenderer tail;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;

	public LOTRModelRhino() {
		this(0.0f);
	}

	public LOTRModelRhino(float f) {
		textureWidth = 128;
		textureHeight = 128;
		head = new ModelRenderer(this, 0, 0);
		head.setRotationPoint(0.0f, 3.0f, -12.0f);
		head.addBox(-5.0f, -2.0f, -22.0f, 10, 10, 16, f);
		head.addBox(-4.0f, -4.0f, -10.0f, 1, 2, 2, f);
		head.mirror = true;
		head.addBox(3.0f, -4.0f, -10.0f, 1, 2, 2, f);
		neck = new ModelRenderer(this, 52, 0);
		neck.setRotationPoint(0.0f, 3.0f, -12.0f);
		neck.addBox(-7.0f, -4.0f, -7.0f, 14, 13, 8, f);
		horn1 = new ModelRenderer(this, 36, 0);
		horn1.addBox(-1.0f, -14.0f, -20.0f, 2, 8, 2, f);
		horn1.rotateAngleX = 0.2617993877991494f;
		head.addChild(horn1);
		horn2 = new ModelRenderer(this, 44, 0);
		horn2.addBox(-1.0f, -3.0f, -17.0f, 2, 4, 2, f);
		horn2.rotateAngleX = -0.17453292519943295f;
		head.addChild(horn2);
		body = new ModelRenderer(this, 0, 26);
		body.setRotationPoint(0.0f, 5.0f, 0.0f);
		body.addBox(-8.0f, -7.0f, -13.0f, 16, 16, 34, f);
		tail = new ModelRenderer(this, 100, 63);
		tail.setRotationPoint(0.0f, 7.0f, 21.0f);
		tail.addBox(-1.5f, -1.0f, -1.0f, 3, 8, 2, f);
		leg1 = new ModelRenderer(this, 30, 76);
		leg1.setRotationPoint(-8.0f, 3.0f, 14.0f);
		leg1.addBox(-8.0f, -3.0f, -5.0f, 8, 12, 10, f);
		leg1.setTextureOffset(0, 95).addBox(-7.0f, 9.0f, -3.0f, 6, 12, 6, f);
		leg2 = new ModelRenderer(this, 30, 76);
		leg2.setRotationPoint(8.0f, 3.0f, 14.0f);
		leg2.mirror = true;
		leg2.addBox(0.0f, -3.0f, -5.0f, 8, 12, 10, f);
		leg2.setTextureOffset(0, 95).addBox(1.0f, 9.0f, -3.0f, 6, 12, 6, f);
		leg3 = new ModelRenderer(this, 0, 76);
		leg3.setRotationPoint(-8.0f, 4.0f, -6.0f);
		leg3.addBox(-7.0f, -3.0f, -4.0f, 7, 11, 8, f);
		leg3.setTextureOffset(0, 95).addBox(-6.5f, 8.0f, -3.0f, 6, 12, 6, f);
		leg4 = new ModelRenderer(this, 0, 76);
		leg4.setRotationPoint(8.0f, 4.0f, -6.0f);
		leg4.mirror = true;
		leg4.addBox(0.0f, -3.0f, -4.0f, 7, 11, 8, f);
		leg4.setTextureOffset(0, 95).addBox(0.5f, 8.0f, -3.0f, 6, 12, 6, f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		horn2.showModel = !isChild;
		horn1.showModel = horn2.showModel;
		if (isChild) {
			float f6 = 2.0f;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0f, 8.0f * f5, 12.0f * f5);
			head.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0f / f6, 1.0f / f6, 1.0f / f6);
			GL11.glTranslatef(0.0f, 24.0f * f5, 0.0f * f5);
			body.render(f5);
			tail.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
			GL11.glPopMatrix();
		} else {
			head.render(f5);
			neck.render(f5);
			body.render(f5);
			tail.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
			leg4.render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		head.rotateAngleX = 0.20943951023931956f;
		head.rotateAngleY = 0.0f;
		head.rotateAngleX += MathHelper.cos(f * 0.2f) * 0.3f * f1;
		head.rotateAngleX += (float) Math.toRadians(f4);
		head.rotateAngleY += (float) Math.toRadians(f3);
		neck.rotateAngleX = head.rotateAngleX;
		neck.rotateAngleY = head.rotateAngleY;
		neck.rotateAngleZ = head.rotateAngleZ;
		tail.rotateAngleX = 0.6981317007977318f;
		tail.rotateAngleX += MathHelper.cos(f * 0.3f) * 0.5f * f1;
		leg1.rotateAngleX = MathHelper.cos(f * 0.4f) * 1.0f * f1;
		leg2.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * 1.0f * f1;
		leg3.rotateAngleX = MathHelper.cos(f * 0.4f + 3.1415927f) * 1.0f * f1;
		leg4.rotateAngleX = MathHelper.cos(f * 0.4f) * 1.0f * f1;
	}
}
