package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockPipeweedPlant extends LOTRBlockFlower {
	public LOTRBlockPipeweedPlant() {
		setFlowerBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(4) == 0) {
			double d = i + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			double d1 = j + MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
			double d2 = k + MathHelper.randomFloatClamp(random, 0.1f, 0.9f);
			world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
		}
	}
}
