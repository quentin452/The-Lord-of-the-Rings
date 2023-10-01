package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelLionRug extends ModelBase {
	public LOTRModelLion lionModel = new LOTRModelLion();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles();
		GL11.glTranslatef(0.0f, -0.4f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		lionModel.body.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		lionModel.tail.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.1f, 0.1f);
		lionModel.head.render(f5);
		lionModel.mane.render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.15f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.4f, 0.0f, 0.0f);
		lionModel.leg1.render(f5);
		lionModel.leg3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.4f, 0.0f, 0.0f);
		lionModel.leg2.render(f5);
		lionModel.leg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		lionModel.leg1.rotateAngleX = 0.5235987755982988f;
		lionModel.leg1.rotateAngleZ = 1.5707963267948966f;
		lionModel.leg2.rotateAngleX = 0.5235987755982988f;
		lionModel.leg2.rotateAngleZ = -1.5707963267948966f;
		lionModel.leg3.rotateAngleX = -0.3490658503988659f;
		lionModel.leg3.rotateAngleZ = 1.5707963267948966f;
		lionModel.leg4.rotateAngleX = -0.3490658503988659f;
		lionModel.leg4.rotateAngleZ = -1.5707963267948966f;
	}
}
