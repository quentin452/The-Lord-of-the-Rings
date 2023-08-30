package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class LOTRBlockUtumnoSlabBase extends LOTRBlockSlabBase implements LOTRWorldProviderUtumno.UtumnoBlock {
	protected LOTRBlockUtumnoSlabBase(boolean flag, int n) {
		super(flag, Material.rock, n);
		setHardness(1.5f);
		setResistance(Float.MAX_VALUE);
		setStepSound(Block.soundTypeStone);
	}
}
