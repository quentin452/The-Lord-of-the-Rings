package lotr.common.world.structure2;

import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenHarnedorPasture extends LOTRWorldGenHarnedorStructure {
	public LOTRWorldGenHarnedorPasture(boolean flag) {
		super(flag);
	}

	public static EntityAnimal getRandomAnimal(World world, Random random) {
		int animal = random.nextInt(5);
		switch (animal) {
			case 0:
				return new EntityCow(world);
			case 1:
				return new EntityPig(world);
			case 2:
				return new EntitySheep(world);
			case 3:
				return new EntityChicken(world);
			case 4:
				return new LOTREntityCamel(world);
			default:
				break;
		}
		return null;
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 6; ++k1) {
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
		for (int i1 = -3; i1 <= 3; ++i1) {
			for (int k1 = -3; k1 <= 6; ++k1) {
				int j12 = -1;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setAir(world, i1, j12, k1);
				}
			}
		}
		loadStrScan("harnedor_pasture");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		generateStrScan(world, random, 0, 0, 0);
		block6:
		for (int i1 : new int[]{-2, -1, 1, 2}) {
			j1 = 0;
			for (int step = 0; step < 6; ++step) {
				int j2;
				int k1 = -4 - step;
				if (isOpaque(world, i1, j1 + 1, k1)) {
					setAir(world, i1, j1 + 1, k1);
					setAir(world, i1, j1 + 2, k1);
					setAir(world, i1, j1 + 3, k1);
					setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					j2 = j1 - 1;
					while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
						setGrassToDirt(world, i1, j2 - 1, k1);
						--j2;
					}
					++j1;
					continue;
				}
				if (isOpaque(world, i1, j1, k1)) {
					continue block6;
				}
				setAir(world, i1, j1 + 1, k1);
				setAir(world, i1, j1 + 2, k1);
				setAir(world, i1, j1 + 3, k1);
				setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
				--j1;
			}
		}
		int animals = 2 + random.nextInt(4);
		for (int l = 0; l < animals; ++l) {
			EntityAnimal animal = getRandomAnimal(world, random);
			spawnNPCAndSetHome(animal, world, 0, 1, 0, 0);
			animal.detachHome();
		}
		return true;
	}
}
