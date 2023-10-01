package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRTileEntitySignCarvedIthildin extends LOTRTileEntitySignCarved {
	public LOTRDwarvenGlowLogic glowLogic = new LOTRDwarvenGlowLogic().setPlayerRange(8);

	public float getGlowBrightness(float f) {
		if (isFakeGuiSign) {
			return 1.0f;
		}
		return glowLogic.getGlowBrightness(worldObj, xCoord, yCoord, zCoord, f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		return 1024.0;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		glowLogic.update(worldObj, xCoord, yCoord, zCoord);
	}
}
