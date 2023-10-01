package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;

import java.util.Random;

public class LOTRWorldGenAngmarTent extends LOTRWorldGenTentBase {
	public LOTRWorldGenAngmarTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		int randomWool = random.nextInt(3);
		switch (randomWool) {
			case 0:
				tentBlock = Blocks.wool;
				tentMeta = 15;
				break;
			case 1:
				tentBlock = Blocks.wool;
				tentMeta = 12;
				break;
			case 2:
				tentBlock = Blocks.wool;
				tentMeta = 7;
				break;
			default:
				break;
		}
		fenceBlock = LOTRMod.fence;
		fenceMeta = 3;
		tableBlock = LOTRMod.angmarTable;
		chestContents = LOTRChestContents.ANGMAR_TENT;
		hasOrcTorches = true;
	}
}
