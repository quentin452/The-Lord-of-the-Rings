package lotr.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class LOTRModelWargskinRug extends ModelBase {
	public LOTRModelWarg wargModel = new LOTRModelWarg();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		GL11.glTranslatef(0.0f, -0.3f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		wargModel.body.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		wargModel.tail.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.5f, 0.1f);
		wargModel.head.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.3f, 0.0f, 0.0f);
		wargModel.leg1.render(f5);
		wargModel.leg3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.3f, 0.0f, 0.0f);
		wargModel.leg2.render(f5);
		wargModel.leg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		wargModel.leg1.rotateAngleX = 0.5235987755982988f;
		wargModel.leg1.rotateAngleZ = 1.5707963267948966f;
		wargModel.leg2.rotateAngleX = 0.5235987755982988f;
		wargModel.leg2.rotateAngleZ = -1.5707963267948966f;
		wargModel.leg3.rotateAngleX = -0.3490658503988659f;
		wargModel.leg3.rotateAngleZ = 1.5707963267948966f;
		wargModel.leg4.rotateAngleX = -0.3490658503988659f;
		wargModel.leg4.rotateAngleZ = -1.5707963267948966f;
	}
}
