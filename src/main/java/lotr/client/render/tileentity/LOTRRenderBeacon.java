package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelBeacon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBeacon extends TileEntitySpecialRenderer {
	public ModelBase beaconModel = new LOTRModelBeacon();
	public ResourceLocation beaconTexture = new ResourceLocation("lotr:item/beacon.png");

	public void renderInvBeacon() {
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		renderTileEntityAt(null, 0.0, 0.0, 0.0, 0.0f);
		GL11.glEnable(32826);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		bindTexture(beaconTexture);
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		GL11.glScalef(1.0f, -1.0f, 1.0f);
		beaconModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}
}
