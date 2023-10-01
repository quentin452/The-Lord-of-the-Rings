package lotr.client.render.tileentity;

import lotr.client.render.LOTRRenderBlocks;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDwarvenDoor extends TileEntitySpecialRenderer {
	public RenderBlocks renderBlocks;

	@Override
	public void func_147496_a(World world) {
		renderBlocks = new RenderBlocks(world);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		if (renderBlocks == null) {
			renderBlocks = new RenderBlocks(tileentity.getWorldObj());
		}
		LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor) tileentity;
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		float alphaFunc = LOTRRenderDwarvenGlow.setupGlow(door.getGlowBrightness(f));
		bindTexture(TextureMap.locationBlocksTexture);
		Block block = door.getBlockType();
		if (block instanceof LOTRBlockGateDwarvenIthildin) {
			LOTRRenderBlocks.renderDwarvenDoorGlow((LOTRBlockGateDwarvenIthildin) block, renderBlocks, door.xCoord, door.yCoord, door.zCoord);
		}
		LOTRRenderDwarvenGlow.endGlow(alphaFunc);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}
}
