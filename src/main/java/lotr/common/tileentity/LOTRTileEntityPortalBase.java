package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class LOTRTileEntityPortalBase extends TileEntity {
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			LOTRBlockPortal portalBlock = (LOTRBlockPortal) getBlockType();
			for (int i1 = xCoord - 1; i1 <= xCoord + 1; ++i1) {
				for (int k1 = zCoord - 1; k1 <= zCoord + 1; ++k1) {
					if (!portalBlock.isValidPortalLocation(worldObj, i1, yCoord, k1, true)) {
						continue;
					}
					return;
				}
			}
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
}
