package lotr.client.fx;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityMallornEntSummonFX extends EntityDiggingFX {
	public Entity summoner;
	public Entity summoned;
	public float arcParam;

	public LOTREntityMallornEntSummonFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int summonerID, int summonedID, float t, Block block, int meta, int color) {
		super(world, d, d1, d2, d3, d4, d5, block, meta);
		motionX = d3;
		motionY = d4;
		motionZ = d5;
		summoner = worldObj.getEntityByID(summonerID);
		summoned = worldObj.getEntityByID(summonedID);
		arcParam = t;
		particleMaxAge = (int) (40.0f * arcParam);
		particleRed *= (color >> 16 & 0xFF) / 255.0f;
		particleGreen *= (color >> 8 & 0xFF) / 255.0f;
		particleBlue *= (color & 0xFF) / 255.0f;
		particleScale *= 2.0f;
		particleGravity = 0.0f;
		renderDistanceWeight = 10.0;
		noClip = true;
		updateArcPos();
	}

	public double[] lerp(double[] a, double[] b, float t) {
		double[] ab = new double[a.length];
		for (int i = 0; i < ab.length; ++i) {
			ab[i] = a[i] + (b[i] - a[i]) * t;
		}
		return ab;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		updateArcPos();
	}

	public void updateArcPos() {
		if (summoner == null || summoned == null || !summoner.isEntityAlive() || !summoned.isEntityAlive()) {
			setDead();
			return;
		}
		double[] posA = {summoner.posX, summoner.boundingBox.minY + summoner.height * 0.7, summoner.posZ};
		double[] posC = {summoned.posX, summoned.boundingBox.minY + summoned.height * 0.7, summoned.posZ};
		double[] posB = {(posA[0] + posC[0]) / 2.0, (posA[1] + posC[1]) / 2.0 + 20.0, (posA[2] + posC[2]) / 2.0};
		double[] ab = lerp(posA, posB, arcParam);
		double[] bc = lerp(posB, posC, arcParam);
		double[] abbc = lerp(ab, bc, arcParam);
		posX = abbc[0];
		posY = abbc[1];
		posZ = abbc[2];
	}
}
