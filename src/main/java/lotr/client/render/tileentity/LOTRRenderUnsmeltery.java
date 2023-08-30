package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelUnsmeltery;
import lotr.common.block.LOTRBlockForgeBase;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderUnsmeltery extends TileEntitySpecialRenderer {
	public ModelBase unsmelteryModel = new LOTRModelUnsmeltery();
	public ResourceLocation idleTexture = new ResourceLocation("lotr:item/unsmeltery/idle.png");
	public ResourceLocation activeTexture = new ResourceLocation("lotr:item/unsmeltery/active.png");

	public void renderInvUnsmeltery() {
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		renderTileEntityAt(null, 0.0, 0.0, 0.0, 0.0f);
		GL11.glEnable(32826);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		boolean useActiveTexture;
		LOTRTileEntityUnsmeltery unsmeltery = (LOTRTileEntityUnsmeltery) tileentity;
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glDisable(2884);
		GL11.glTranslatef((float) d + 0.5f, (float) d1 + 1.5f, (float) d2 + 0.5f);
		GL11.glScalef(1.0f, -1.0f, -1.0f);
		float rotation = 0.0f;
		float rocking = 0.0f;
		if (unsmeltery != null) {
			switch (unsmeltery.getBlockMetadata() & 7) {
				case 2: {
					rotation = 180.0f;
					break;
				}
				case 3: {
					rotation = 0.0f;
					break;
				}
				case 4: {
					rotation = 90.0f;
					break;
				}
				case 5: {
					rotation = 270.0f;
				}
			}
			rocking = unsmeltery.getRockingAmount(f);
		}
		GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
		useActiveTexture = unsmeltery != null && LOTRBlockForgeBase.isForgeActive(unsmeltery.getWorldObj(), unsmeltery.xCoord, unsmeltery.yCoord, unsmeltery.zCoord);
		if (useActiveTexture) {
			bindTexture(activeTexture);
		} else {
			bindTexture(idleTexture);
		}
		unsmelteryModel.render(null, rocking, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}
}
