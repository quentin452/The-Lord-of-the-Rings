package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class LOTRBlockTrapdoor extends BlockTrapDoor {
	public LOTRBlockTrapdoor() {
		this(Material.wood);
		setStepSound(Block.soundTypeWood);
		setHardness(3.0f);
	}

	public LOTRBlockTrapdoor(Material material) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
	}
}
