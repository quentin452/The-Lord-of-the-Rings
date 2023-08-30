package lotr.common.world;

import java.awt.Color;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRDimension;
import lotr.common.network.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class LOTRWorldProviderUtumno extends LOTRWorldProvider {
	public LOTRWorldProviderUtumno() {
		hasNoSky = true;
	}

	@Override
	public float calculateCelestialAngle(long time, float f) {
		return 0.5f;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new LOTRChunkProviderUtumno(worldObj, worldObj.getSeed());
	}

	@Override
	public void generateLightBrightnessTable() {
		for (int i = 0; i <= 15; ++i) {
			float f = i / 15.0f;
			lightBrightnessTable[i] = (float) Math.pow(f, 4.0);
		}
	}

	@Override
	public int getActualHeight() {
		return getHeight();
	}

	@Override
	public LOTRDimension getLOTRDimension() {
		return LOTRDimension.UTUMNO;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP entityplayer) {
		return LOTRDimension.MIDDLE_EARTH.dimensionID;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getVoidFogYFactor() {
		return 1.0;
	}

	@Override
	public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
		double y = viewer.prevPosY + (viewer.posY - viewer.prevPosY) * tick;
		LOTRUtumnoLevel level = LOTRUtumnoLevel.forY((int) y);
		if (level == LOTRUtumnoLevel.FIRE) {
			int min = LOTRUtumnoLevel.FIRE.getLowestCorridorFloor();
			int max = LOTRUtumnoLevel.FIRE.getHighestCorridorRoof();
			max = (int) (max - (max - min) * 0.3);
			double yFactor = (y - min) / (max - min);
			yFactor = MathHelper.clamp_double(yFactor, 0.0, 1.0);
			yFactor = 1.0 - yFactor;
			Color clr = new Color(rgb[0], rgb[1], rgb[2]);
			float[] hsb = Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), null);
			float br = hsb[2];
			if (br > 0.0) {
				hsb[2] = (float) (br + (1.0f - br) * yFactor);
			}
			rgb = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])).getRGBComponents(null);
		}
		return rgb;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public void setSpawnPoint(int i, int j, int k) {
	}

	public static void doEvaporateFX(World world, int i, int j, int k) {
		if (!world.isRemote) {
			world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
			IMessage pkt = new LOTRPacketBlockFX(LOTRPacketBlockFX.Type.UTUMNO_EVAPORATE, i, j, k);
			LOTRPacketHandler.networkWrapper.sendToAllAround(pkt, new NetworkRegistry.TargetPoint(world.provider.dimensionId, i + 0.5, j + 0.5, k + 0.5, 32.0));
		}
	}

	public interface UtumnoBlock {
	}

}
