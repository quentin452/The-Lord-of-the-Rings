package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;

public abstract class LOTRBlockTorch extends BlockTorch {
	protected LOTRBlockTorch() {
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	public abstract TorchParticle createTorchParticle(Random var1);

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k);
		double d = i + 0.5;
		double d1 = j + 0.7;
		double d2 = k + 0.5;
		double particleY = 0.2;
		double particleX = 0.27;
		TorchParticle particle = createTorchParticle(random);
		if (particle != null) {
			switch (meta) {
				case 1:
					particle.spawn(d - particleX, d1 + particleY, d2);
					break;
				case 2:
					particle.spawn(d + particleX, d1 + particleY, d2);
					break;
				case 3:
					particle.spawn(d, d1 + particleY, d2 - particleX);
					break;
				case 4:
					particle.spawn(d, d1 + particleY, d2 + particleX);
					break;
				default:
					particle.spawn(d, d1, d2);
					break;
			}
		}
	}

	public static class TorchParticle {
		public String name;
		public double posX;
		public double posY;
		public double posZ;
		public double motionX;
		public double motionY;
		public double motionZ;

		public TorchParticle(String s, double x, double y, double z, double mx, double my, double mz) {
			name = s;
			posX = x;
			posY = y;
			posZ = z;
			motionX = mx;
			motionY = my;
			motionZ = mz;
		}

		public void spawn(double x, double y, double z) {
			LOTRMod.proxy.spawnParticle(name, x + posX, y + posY, z + posZ, motionX, motionY, motionZ);
		}
	}

}
