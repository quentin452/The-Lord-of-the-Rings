package lotr.client.render.tileentity;

import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class LOTRRenderSpawnerChest extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		LOTRTileEntitySpawnerChest chest = (LOTRTileEntitySpawnerChest) tileentity;
		Block block = chest.getBlockType();
		if (block instanceof LOTRBlockSpawnerChest) {
			LOTRBlockSpawnerChest scBlock = (LOTRBlockSpawnerChest) block;
			Block model = scBlock.chestModel;
			if (model instanceof ITileEntityProvider) {
				ITileEntityProvider itep = (ITileEntityProvider) model;
				TileEntity modelTE = itep.createNewTileEntity(chest.getWorldObj(), 0);
				modelTE.setWorldObj(chest.getWorldObj());
				modelTE.xCoord = chest.xCoord;
				modelTE.yCoord = chest.yCoord;
				modelTE.zCoord = chest.zCoord;
				TileEntityRendererDispatcher.instance.getSpecialRenderer(modelTE).renderTileEntityAt(modelTE, d, d1, d2, f);
			}
		}
	}
}
