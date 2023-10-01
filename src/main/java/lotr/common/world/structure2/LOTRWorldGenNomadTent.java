package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenNomadTent extends LOTRWorldGenNomadStructure {
	public LOTRWorldGenNomadTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 7);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -6; i1 <= 6; ++i1) {
				for (int k1 = -6; k1 <= 6; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -6; i1 <= 6; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				if (i2 + Math.abs(k1) > 9) {
					continue;
				}
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_tent");
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -3, 1, -2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, -2, bedBlock, 11);
		setBlockAndMetadata(world, -3, 1, 2, bedBlock, 3);
		setBlockAndMetadata(world, -4, 1, 2, bedBlock, 11);
		placeWeaponRack(world, 0, 3, 5, 6, getRandomNomadWeapon(random));
		placeChest(world, random, 0, 1, 5, LOTRMod.chestBasket, 2, LOTRChestContents.NOMAD_TENT);
		int nomads = 1 + random.nextInt(2);
		for (int l = 0; l < nomads; ++l) {
			LOTREntityNomad nomad = new LOTREntityNomad(world);
			spawnNPCAndSetHome(nomad, world, 0, 1, -1, 16);
		}
		return true;
	}
}
