package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.material.Material;

public class LOTRBlockGravel extends Block {
	public LOTRBlockGravel() {
        super(Material.rock);
        setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(0.6f);
		setStepSound(Block.soundTypeGravel);
	}
}
