package lotr.common.world.structure2;

import net.minecraft.init.Blocks;

import java.util.Random;

public class LOTRWorldGenGundabadForgeTent extends LOTRWorldGenGundabadTent {
	public LOTRWorldGenGundabadForgeTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = Blocks.cobblestone;
		tentMeta = 0;
		fenceBlock = Blocks.cobblestone_wall;
		fenceMeta = 0;
		hasOrcForge = true;
	}
}
