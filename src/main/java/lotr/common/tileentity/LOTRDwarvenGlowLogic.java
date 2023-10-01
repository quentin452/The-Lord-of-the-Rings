package lotr.common.tileentity;

import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class LOTRDwarvenGlowLogic {
	public static float[] lightValueSqrts = new float[16];

	static {
		for (int i = 0; i <= 15; ++i) {
			lightValueSqrts[i] = MathHelper.sqrt_float(i / 15.0f);
		}
	}

	public int glowTick;
	public int prevGlowTick;
	public int maxGlowTick = 120;
	public int playerRange = 8;

	public float fullGlow = 0.7f;

	public float getGlowBrightness(World world, int i, int j, int k, float tick) {
		float glow = (prevGlowTick + (glowTick - prevGlowTick) * tick) / maxGlowTick;
		glow *= fullGlow;
		float sun = world.getSunBrightness(tick);
		float sunNorml = (sun - 0.2F) / 0.8F;
		float night = 1.0F - sunNorml;
		night -= 0.5F;
		if (night < 0.0F) {
			night = 0.0F;
		}
		night *= 2.0F;
		float skylight = lightValueSqrts[world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k)];
		return glow * night * skylight;
	}

	public int getGlowTick() {
		return glowTick;
	}

	public void setGlowTick(int i) {
		glowTick = prevGlowTick = i;
	}

	public void resetGlowTick() {
		prevGlowTick = 0;
		glowTick = 0;
	}

	public LOTRDwarvenGlowLogic setFullGlow(float f) {
		fullGlow = f;
		return this;
	}

	public LOTRDwarvenGlowLogic setGlowTime(int i) {
		maxGlowTick = i;
		return this;
	}

	public LOTRDwarvenGlowLogic setPlayerRange(int i) {
		playerRange = i;
		return this;
	}

	public void update(World world, int i, int j, int k) {
		prevGlowTick = glowTick;
		if (world.isRemote) {
			boolean playersNearby = world.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, playerRange) != null;
			if (playersNearby && glowTick < maxGlowTick) {
				++glowTick;
			} else if (!playersNearby && glowTick > 0) {
				--glowTick;
			}
		}
	}
}
