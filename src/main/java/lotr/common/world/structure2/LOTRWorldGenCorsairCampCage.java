package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityHaradSlave;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenCorsairCampCage extends LOTRWorldGenCorsairStructure {
	public LOTRWorldGenCorsairCampCage(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -1; i1 <= 2; ++i1) {
				for (k1 = -1; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -1; i1 <= 2; ++i1) {
			for (k1 = -1; k1 <= 2; ++k1) {
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("corsair_camp_cage");
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		generateStrScan(world, random, 0, 0, 0);
		int slaves = 1 + random.nextInt(3);
		for (int l = 0; l < slaves; ++l) {
			LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
			spawnNPCAndSetHome(slave, world, 0, 1, 0, 4);
		}
		return true;
	}
}
