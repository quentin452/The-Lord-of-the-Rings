package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTraining extends LOTRWorldGenSouthronStructure {
	public LOTRWorldGenSouthronTraining(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -7; i1 <= 7; ++i1) {
			for (int k1 = -5; k1 <= 5; ++k1) {
				for (int j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("southron_training");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		addBlockMetaAliasOption("GROUND", 5, Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 1, Blocks.sand, 1);
		generateStrScan(world, random, 0, 0, 0);
		placeWeaponRack(world, -5, 2, -4, 2, getRandomHaradWeapon(random));
		placeWeaponRack(world, 5, 2, -4, 2, getRandomHaradWeapon(random));
		placeSkull(world, 0, 3, 2, 0);
		placeSkull(world, -5, 3, 0, 12);
		placeSkull(world, 5, 3, 0, 4);
		return true;
	}
}
