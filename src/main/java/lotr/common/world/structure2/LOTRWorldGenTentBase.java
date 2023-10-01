package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public abstract class LOTRWorldGenTentBase extends LOTRWorldGenStructureBase2 {
	public Block tentBlock;
	public int tentMeta;
	public Block fenceBlock;
	public int fenceMeta;
	public Block tableBlock;
	public LOTRChestContents chestContents;
	public boolean hasOrcForge;
	public boolean hasOrcTorches;

	protected LOTRWorldGenTentBase(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		BiomeGenBase biome;
		int k1;
		int j1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					int randomGround;
					biome = getBiome(world, i1, k1);
					if (biome instanceof LOTRBiomeGenMordor) {
						randomGround = random.nextInt(3);
						switch (randomGround) {
							case 0:
								setBlockAndMetadata(world, i1, j1, k1, LOTRMod.rock, 0);
								break;
							case 1:
								setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorDirt, 0);
								break;
							case 2:
								setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorGravel, 0);
								break;
							default:
								break;
						}
					} else {
						randomGround = random.nextInt(3);
						switch (randomGround) {
							case 0:
								if (j1 == 0) {
									setBiomeTop(world, i1, 0, k1);
								} else {
									setBiomeFiller(world, i1, j1, k1);
								}
								break;
							case 1:
								setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
								break;
							case 2:
								setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
								break;
							default:
								break;
						}
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (int k12 = -3; k12 <= 3; ++k12) {
			for (int i12 : new int[]{-2, 2}) {
				for (int j12 = 1; j12 <= 2; ++j12) {
					setBlockAndMetadata(world, i12, j12, k12, tentBlock, tentMeta);
				}
				setGrassToDirt(world, i12, 0, k12);
			}
			setBlockAndMetadata(world, -1, 3, k12, tentBlock, tentMeta);
			setBlockAndMetadata(world, 1, 3, k12, tentBlock, tentMeta);
			setBlockAndMetadata(world, 0, 4, k12, tentBlock, tentMeta);
		}
		for (int j13 = 1; j13 <= 3; ++j13) {
			setBlockAndMetadata(world, 0, j13, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, j13, 3, fenceBlock, fenceMeta);
		}
		if (hasOrcTorches) {
			placeOrcTorch(world, -1, 1, -3);
			placeOrcTorch(world, 1, 1, -3);
			placeOrcTorch(world, -1, 1, 3);
			placeOrcTorch(world, 1, 1, 3);
		} else {
			setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
			setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
			setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
			setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
		}
		if (random.nextBoolean()) {
			if (hasOrcForge) {
				setBlockAndMetadata(world, -1, 1, 0, LOTRMod.orcForge, 4);
				setGrassToDirt(world, -1, 0, 0);
				setBlockAndMetadata(world, -1, 1, -1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, -1, 1, 1, fenceBlock, fenceMeta);
			} else {
				placeChest(world, random, -1, 1, 0, 4, chestContents);
				setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
				setGrassToDirt(world, -1, 0, -1);
				setBlockAndMetadata(world, -1, 1, 1, tableBlock, 0);
				setGrassToDirt(world, -1, 0, 1);
			}
		} else if (hasOrcForge) {
			setBlockAndMetadata(world, 1, 1, 0, LOTRMod.orcForge, 5);
			setGrassToDirt(world, 1, 0, 0);
			setBlockAndMetadata(world, 1, 1, -1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, 1, 1, fenceBlock, fenceMeta);
		} else {
			placeChest(world, random, 1, 1, 0, 5, chestContents);
			setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
			setGrassToDirt(world, 1, 0, -1);
			setBlockAndMetadata(world, 1, 1, 1, tableBlock, 0);
			setGrassToDirt(world, 1, 0, 1);
		}
		return true;
	}

	public boolean isOrcTent() {
		return true;
	}
}
