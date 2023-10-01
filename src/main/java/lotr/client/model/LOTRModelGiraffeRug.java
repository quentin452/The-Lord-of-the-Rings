package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class LOTRModelGiraffeRug extends ModelBase {
	public LOTRModelGiraffe giraffeModel = new LOTRModelGiraffe(0.0f);

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		giraffeModel.setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
		setRotationAngles();
		GL11.glTranslatef(0.0f, 0.1f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		giraffeModel.body.render(f5);
		giraffeModel.tail.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.6f, -0.2f);
		giraffeModel.head.render(f5);
		giraffeModel.neck.render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.25f, 0.0f, 0.0f);
		giraffeModel.leg1.render(f5);
		giraffeModel.leg3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.25f, 0.0f, 0.0f);
		giraffeModel.leg2.render(f5);
		giraffeModel.leg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		giraffeModel.leg1.rotateAngleX = 0.5235987755982988f;
		giraffeModel.leg1.rotateAngleZ = 1.5707963267948966f;
		giraffeModel.leg2.rotateAngleX = 0.5235987755982988f;
		giraffeModel.leg2.rotateAngleZ = -1.5707963267948966f;
		giraffeModel.leg3.rotateAngleX = -0.3490658503988659f;
		giraffeModel.leg3.rotateAngleZ = 1.5707963267948966f;
		giraffeModel.leg4.rotateAngleX = -0.3490658503988659f;
		giraffeModel.leg4.rotateAngleZ = -1.5707963267948966f;
	}
}
