package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockGoblet extends LOTRBlockMug {
	public LOTRBlockGoblet() {
		super(2.5f, 9.0f);
		setStepSound(Block.soundTypeMetal);
	}

	public static class Copper extends LOTRBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return LOTRMod.blockOreStorage.getIcon(i, 0);
		}
	}

	public static class Gold extends LOTRBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.gold_block.getIcon(i, 0);
		}
	}

	public static class Silver extends LOTRBlockGoblet {
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return LOTRMod.blockOreStorage.getIcon(i, 3);
		}
	}

	public static class Wood extends LOTRBlockGoblet {
		public Wood() {
			setStepSound(Block.soundTypeWood);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getIcon(int i, int j) {
			return Blocks.planks.getIcon(i, 0);
		}
	}

}
