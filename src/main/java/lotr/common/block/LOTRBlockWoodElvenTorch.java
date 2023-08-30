package lotr.common.block;

import java.util.Random;

public class LOTRBlockWoodElvenTorch extends LOTRBlockTorch {
	@Override
	public LOTRBlockTorch.TorchParticle createTorchParticle(Random random) {
		String s = "leafRed_" + (20 + random.nextInt(30));
		double x = -0.01 + random.nextFloat() * 0.02f;
		double y = -0.01 + random.nextFloat() * 0.02f;
		double z = -0.01 + random.nextFloat() * 0.02f;
		return new TorchParticle(s, 0.0, 0.0, 0.0, x, y, z);
	}
}
