package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRWorldGenHighElfHouse extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block stoneBlock;
	public int stoneMeta;
	public Block stoneSlabBlock;
	public int stoneSlabMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block leafBlock;
	public int leafMeta;
	public Block tableBlock;
	public Block bedBlock;
	public Block barsBlock;
	public Block torchBlock;
	public Block chandelierBlock;
	public int chandelierMeta;
	public Block plateBlock;
	public LOTRItemBanner.BannerType bannerType;
	public LOTRChestContents chestContents;
	public Block trapdoorBlock;

	public LOTRWorldGenHighElfHouse(boolean flag) {
		super(flag);
	}

	public LOTREntityElf createElf(World world) {
		return new LOTREntityHighElf(world);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int k12;
		int i1;
		int j1;
		int j12;
		int i12;
		int i13;
		int j13;
		int j14;
		int i2;
		int k13;
		int i22;
		int meta;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		boolean leafy = random.nextBoolean();
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -4; i14 <= 4; ++i14) {
				for (int k14 = -1; k14 <= 14; ++k14) {
					j12 = getTopBlock(world, i14, k14) - 1;
					if (!isSurface(world, i14, j12, k14)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k13 = 0; k13 <= 13; ++k13) {
				int j15;
				i2 = Math.abs(i12);
				for (j15 = 0; (j15 >= 0 || !isOpaque(world, i12, j15, k13)) && getY(j15) >= 0; --j15) {
					setBlockAndMetadata(world, i12, j15, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j15 - 1, k13);
				}
				for (j15 = 1; j15 <= 12; ++j15) {
					setAir(world, i12, j15, k13);
				}
				if (i2 <= 2 && k13 >= 1 && k13 <= 12) {
					setBlockAndMetadata(world, i12, 0, k13, stoneBlock, stoneMeta);
				}
				if (i2 > 2 || k13 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i12, 0, k13, pillarBlock, pillarMeta);
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			i22 = Math.abs(i12);
			if (i22 % 2 == 1) {
				setBlockAndMetadata(world, i12, 1, 0, pillarBlock, pillarMeta);
				setBlockAndMetadata(world, i12, 2, 0, brickWallBlock, brickWallMeta);
				setBlockAndMetadata(world, i12, 3, 0, brickWallBlock, brickWallMeta);
				setBlockAndMetadata(world, i12, 4, 0, pillarBlock, pillarMeta);
				if (i22 == 1) {
					setBlockAndMetadata(world, i12, 5, 0, pillarBlock, pillarMeta);
				}
			}
			if (i22 == 0) {
				setBlockAndMetadata(world, i12, 6, 0, pillarBlock, pillarMeta);
				for (j1 = 7; j1 <= 9; ++j1) {
					setBlockAndMetadata(world, i12, j1, 0, brickWallBlock, brickWallMeta);
				}
				for (j1 = 10; j1 <= 11; ++j1) {
					setBlockAndMetadata(world, i12, j1, 0, pillarBlock, pillarMeta);
				}
				continue;
			}
			if (i22 > 2) {
				continue;
			}
			setBlockAndMetadata(world, i12, 6, 0, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, -2, 5, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 5, 0, brickStairBlock, 6);
		setBlockAndMetadata(world, 2, 5, 0, brickStairBlock, 5);
		int[] i15 = {-3, 3};
		i22 = i15.length;
		for (j1 = 0; j1 < i22; ++j1) {
			int i16 = i15[j1];
			for (j12 = 1; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i16, j12, 1, pillarBlock, pillarMeta);
			}
			setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i16, 1, 2, brickStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 2, 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 3, 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 4, 2, plankStairBlock, i16 > 0 ? 0 : 1);
			setBlockAndMetadata(world, i16, 1, 3, brickStairBlock, i16 > 0 ? 0 : 1);
			setBlockAndMetadata(world, i16, 3, 3, barsBlock, 0);
			setBlockAndMetadata(world, i16, 4, 3, plankStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 1, 4, brickStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 2, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 3, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 4, 4, plankStairBlock, i16 > 0 ? 0 : 1);
			for (j12 = 1; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i16, j12, 5, pillarBlock, pillarMeta);
				setBlockAndMetadata(world, i16, j12, 8, pillarBlock, pillarMeta);
			}
			setBlockAndMetadata(world, i16, 4, 6, brickStairBlock, 7);
			setBlockAndMetadata(world, i16, 4, 7, brickStairBlock, 6);
			setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 5, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 8, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i16, 1, 9, brickStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 2, 9, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 3, 9, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 4, 9, plankStairBlock, i16 > 0 ? 0 : 1);
			setBlockAndMetadata(world, i16, 1, 10, brickStairBlock, i16 > 0 ? 0 : 1);
			setBlockAndMetadata(world, i16, 3, 10, barsBlock, 0);
			setBlockAndMetadata(world, i16, 4, 10, plankStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 1, 11, brickStairBlock, i16 > 0 ? 4 : 5);
			setBlockAndMetadata(world, i16, 2, 11, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 3, 11, brickBlock, brickMeta);
			setBlockAndMetadata(world, i16, 4, 11, plankStairBlock, i16 > 0 ? 0 : 1);
			for (j12 = 1; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i16, j12, 12, pillarBlock, pillarMeta);
			}
			setBlockAndMetadata(world, i16 + Integer.signum(i16), 4, 12, brickWallBlock, brickWallMeta);
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			i22 = Math.abs(i1);
			switch (i22) {
				case 0:
					for (j1 = 1; j1 <= 6; ++j1) {
						setBlockAndMetadata(world, i1, j1, 13, pillarBlock, pillarMeta);
					}
					for (j1 = 7; j1 <= 9; ++j1) {
						setBlockAndMetadata(world, i1, j1, 13, brickWallBlock, brickWallMeta);
					}
					for (j1 = 10; j1 <= 11; ++j1) {
						setBlockAndMetadata(world, i1, j1, 13, pillarBlock, pillarMeta);
					}
					break;
				case 1:
					setBlockAndMetadata(world, i1, 1, 13, brickStairBlock, 7);
					setBlockAndMetadata(world, i1, 3, 13, barsBlock, 0);
					setBlockAndMetadata(world, i1, 4, 13, plankStairBlock, 7);
					break;
				case 2:
					setBlockAndMetadata(world, i1, 1, 13, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 2, 13, plankBlock, plankMeta);
					setBlockAndMetadata(world, i1, 3, 13, plankBlock, plankMeta);
					setBlockAndMetadata(world, i1, 4, 13, plankStairBlock, i1 > 0 ? 0 : 1);
					break;
				default:
					break;
			}
			if (i22 < 1 || i22 > 2) {
				continue;
			}
			setBlockAndMetadata(world, i1, 5, 13, stoneSlabBlock, stoneSlabMeta | 8);
			setBlockAndMetadata(world, i1, 6, 13, brickWallBlock, brickWallMeta);
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k13 = 1; k13 <= 12; ++k13) {
				setBlockAndMetadata(world, i1, 5, k13, stoneBlock, stoneMeta);
			}
		}
		for (k12 = 0; k12 <= 13; ++k12) {
			Block block = roofBlock;
			meta = roofMeta;
			Block slabBlock = roofSlabBlock;
			int slabMeta = roofSlabMeta;
			Block stairBlock = roofStairBlock;
			if (k12 == 1 || k12 == 12) {
				block = brickBlock;
				meta = brickMeta;
				slabBlock = brickSlabBlock;
				slabMeta = brickSlabMeta;
				stairBlock = brickStairBlock;
			}
			setBlockAndMetadata(world, -4, 5, k12, stairBlock, 1);
			setBlockAndMetadata(world, -3, 5, k12, stairBlock, 4);
			setBlockAndMetadata(world, -3, 6, k12, block, meta);
			setBlockAndMetadata(world, -3, 7, k12, block, meta);
			setBlockAndMetadata(world, -3, 8, k12, stairBlock, 1);
			setBlockAndMetadata(world, -2, 8, k12, stairBlock, 4);
			setBlockAndMetadata(world, -2, 9, k12, block, meta);
			setBlockAndMetadata(world, -2, 10, k12, stairBlock, 1);
			setBlockAndMetadata(world, -1, 10, k12, stairBlock, 4);
			setBlockAndMetadata(world, 4, 5, k12, stairBlock, 0);
			setBlockAndMetadata(world, 3, 5, k12, stairBlock, 5);
			setBlockAndMetadata(world, 3, 6, k12, block, meta);
			setBlockAndMetadata(world, 3, 7, k12, block, meta);
			setBlockAndMetadata(world, 3, 8, k12, stairBlock, 0);
			setBlockAndMetadata(world, 2, 8, k12, stairBlock, 5);
			setBlockAndMetadata(world, 2, 9, k12, block, meta);
			setBlockAndMetadata(world, 2, 10, k12, stairBlock, 0);
			setBlockAndMetadata(world, 1, 10, k12, stairBlock, 5);
			if (k12 <= 1 || k12 >= 12) {
				setBlockAndMetadata(world, -1, 11, k12, block, meta);
				setBlockAndMetadata(world, -1, 12, k12, stairBlock, 1);
				setBlockAndMetadata(world, 1, 11, k12, block, meta);
				setBlockAndMetadata(world, 1, 12, k12, stairBlock, 0);
				continue;
			}
			if (k12 <= 4 || k12 >= 9) {
				setBlockAndMetadata(world, -1, 11, k12, stairBlock, 1);
				setBlockAndMetadata(world, 1, 11, k12, stairBlock, 0);
				continue;
			}
			if (k12 == 5) {
				setBlockAndMetadata(world, -1, 11, 5, stairBlock, 3);
				setBlockAndMetadata(world, 1, 11, 5, stairBlock, 3);
				continue;
			}
			if (k12 == 8) {
				setBlockAndMetadata(world, -1, 11, 8, stairBlock, 2);
				setBlockAndMetadata(world, 1, 11, 8, stairBlock, 2);
				continue;
			}
			setBlockAndMetadata(world, -1, 11, k12, slabBlock, slabMeta);
			setBlockAndMetadata(world, 1, 11, k12, slabBlock, slabMeta);
		}
		for (k12 = 0; k12 <= 13; ++k12) {
			setBlockAndMetadata(world, 0, 11, k12, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 0, 12, -1, brickStairBlock, 6);
		setBlockAndMetadata(world, 0, 13, -1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 14, -1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 0, 12, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 13, 0, brickStairBlock, 3);
		setBlockAndMetadata(world, 0, 12, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 13, 1, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 4, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 5, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 8, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 9, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 10, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 11, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 12, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 13, 12, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 12, 13, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 13, 13, brickStairBlock, 2);
		setBlockAndMetadata(world, 0, 12, 14, brickStairBlock, 7);
		setBlockAndMetadata(world, 0, 13, 14, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 14, 14, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 4, 1, brickStairBlock, 4);
		for (j13 = 1; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, -2, j13, 2, Blocks.bookshelf, 0);
		}
		setBlockAndMetadata(world, -2, 1, 3, brickStairBlock, 4);
		placeFlowerPot(world, -2, 2, 3, getRandomFlower(world, random));
		setBlockAndMetadata(world, -2, 4, 3, stoneSlabBlock, stoneSlabMeta | 8);
		setBlockAndMetadata(world, -2, 1, 4, Blocks.grass, 0);
		setBlockAndMetadata(world, -1, 1, 4, trapdoorBlock, 6);
		setBlockAndMetadata(world, -2, 1, 5, trapdoorBlock, 5);
		setBlockAndMetadata(world, -2, 2, 4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 3, 4, leafBlock, leafMeta);
		setBlockAndMetadata(world, -2, 4, 4, leafBlock, leafMeta);
		setBlockAndMetadata(world, 2, 4, 1, brickStairBlock, 5);
		setBlockAndMetadata(world, 2, 1, 2, brickStairBlock, 5);
		placeMug(world, random, 2, 2, 2, 1, LOTRFoods.ELF_DRINK);
		setBlockAndMetadata(world, 2, 1, 3, tableBlock, 0);
		setBlockAndMetadata(world, 2, 4, 3, stoneSlabBlock, stoneSlabMeta | 8);
		for (j13 = 1; j13 <= 4; ++j13) {
			setBlockAndMetadata(world, 2, j13, 4, Blocks.bookshelf, 0);
		}
		for (i1 = -1; i1 <= 0; ++i1) {
			for (k13 = 2; k13 <= 3; ++k13) {
				setBlockAndMetadata(world, i1, 1, k13, Blocks.carpet, 3);
			}
		}
		int[] i17 = {5, 8};
		k13 = i17.length;
		for (meta = 0; meta < k13; ++meta) {
			int k15 = i17[meta];
			setBlockAndMetadata(world, -2, 4, k15, brickStairBlock, 4);
			for (int i18 = -1; i18 <= 1; ++i18) {
				setBlockAndMetadata(world, i18, 4, k15, brickSlabBlock, brickSlabMeta | 8);
			}
			setBlockAndMetadata(world, 2, 4, k15, brickStairBlock, 5);
			setBlockAndMetadata(world, -2, 3, k15, torchBlock, 2);
			setBlockAndMetadata(world, 2, 3, k15, torchBlock, 1);
		}
		for (i13 = 0; i13 <= 1; ++i13) {
			for (k13 = 7; k13 <= 8; ++k13) {
				setBlockAndMetadata(world, i13, 1, k13, Blocks.carpet, 11);
			}
		}
		setBlockAndMetadata(world, -2, 4, 10, stoneSlabBlock, stoneSlabMeta | 8);
		setBlockAndMetadata(world, 2, 4, 10, stoneSlabBlock, stoneSlabMeta | 8);
		setBlockAndMetadata(world, -2, 4, 12, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 4, 12, brickStairBlock, 5);
		spawnItemFrame(world, -3, 2, 9, 1, getElfFramedItem(random));
		spawnItemFrame(world, 3, 2, 9, 3, getElfFramedItem(random));
		spawnItemFrame(world, -3, 2, 11, 1, getElfFramedItem(random));
		spawnItemFrame(world, 3, 2, 11, 3, getElfFramedItem(random));
		if (leafy) {
			for (i13 = -2; i13 <= 2; ++i13) {
				for (k13 = 6; k13 <= 7; ++k13) {
					if (random.nextInt(3) == 0) {
						continue;
					}
					setBlockAndMetadata(world, i13, 4, k13, leafBlock, leafMeta);
				}
			}
		}
		for (i13 = 0; i13 <= 1; ++i13) {
			for (k13 = 9; k13 <= 11; ++k13) {
				setAir(world, i13, 5, k13);
			}
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			for (k13 = 1; k13 <= 9; ++k13) {
				setBlockAndMetadata(world, i13, 10, k13, brickSlabBlock, brickSlabMeta | 8);
			}
			for (k13 = 10; k13 <= 12; ++k13) {
				setBlockAndMetadata(world, i13, 9, k13, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, 10, k13, brickBlock, brickMeta);
			}
		}
		for (k1 = 9; k1 <= 12; ++k1) {
			for (j14 = 6; j14 <= 8; ++j14) {
				setBlockAndMetadata(world, 2, j14, k1, brickBlock, brickMeta);
			}
		}
		for (k1 = 11; k1 <= 12; ++k1) {
			for (j14 = 6; j14 <= 8; ++j14) {
				setBlockAndMetadata(world, -2, j14, k1, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -2, 6, 1, plankStairBlock, 4);
		setBlockAndMetadata(world, -2, 7, 1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 8, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 9, 1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -1, 10, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 2, 6, 1, plankStairBlock, 5);
		setBlockAndMetadata(world, 2, 7, 1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 2, 8, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 9, 1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 1, 10, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 10, 1, brickStairBlock, 7);
		setBlockAndMetadata(world, -1, 10, 2, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 10, 2, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 10, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 9, 3, chandelierBlock, chandelierMeta);
		setBlockAndMetadata(world, -2, 6, 4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 7, 4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -2, 8, 4, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 6, 4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 2, 7, 4, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 2, 8, 4, brickStairBlock, 5);
		setBlockAndMetadata(world, 0, 10, 6, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 9, 6, chandelierBlock, chandelierMeta);
		setBlockAndMetadata(world, -1, 10, 7, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 10, 7, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 10, 8, brickStairBlock, 6);
		setBlockAndMetadata(world, 1, 10, 8, brickStairBlock, 6);
		setBlockAndMetadata(world, -1, 9, 9, brickStairBlock, 4);
		setBlockAndMetadata(world, -1, 10, 9, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 9, 9, brickStairBlock, 5);
		setBlockAndMetadata(world, 1, 10, 9, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 8, 12, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 8, 12, brickStairBlock, 5);
		for (int j16 = 1; j16 <= 11; ++j16) {
			setBlockAndMetadata(world, 0, j16, 10, pillarBlock, pillarMeta);
		}
		setBlockAndMetadata(world, 0, 8, 10, brickCarvedBlock, brickCarvedMeta);
		placeWallBanner(world, 0, 3, 10, bannerType, 2);
		setBlockAndMetadata(world, -1, 1, 9, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, -1, 1, 10, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -1, 2, 11, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 2, 11, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 1, 3, 11, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 1, 3, 10, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 1, 4, 9, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 0, 4, 9, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -1, 5, 9, brickSlabBlock, brickSlabMeta);
		for (i13 = 0; i13 <= 2; ++i13) {
			setBlockAndMetadata(world, i13, 6, 8, brickWallBlock, brickWallMeta);
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			for (k13 = 1; k13 <= 7; ++k13) {
				i2 = Math.abs(i13);
				int k2 = Math.abs(k13 - 4);
				if (i2 == 0 && k2 == 0) {
					setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 0);
					continue;
				}
				if (i2 == 0 && k2 <= 2 || i2 == 1 && k2 == 0) {
					setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 3);
					continue;
				}
				if ((i2 != 0 || k2 != 3) && (i2 != 1 || k2 != 1)) {
					continue;
				}
				setBlockAndMetadata(world, i13, 6, k13, Blocks.carpet, 11);
			}
		}
		int[] i19 = {-2, 2};
		k13 = i19.length;
		for (i2 = 0; i2 < k13; ++i2) {
			int i110 = i19[i2];
			setBlockAndMetadata(world, i110, 6, 3, bedBlock, 2);
			setBlockAndMetadata(world, i110, 6, 2, bedBlock, 10);
		}
		setBlockAndMetadata(world, -2, 6, 5, plankStairBlock, 4);
		placeMug(world, random, -2, 7, 5, 3, LOTRFoods.ELF_DRINK);
		setBlockAndMetadata(world, 2, 6, 5, plankStairBlock, 7);
		setBlockAndMetadata(world, 2, 6, 6, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 2, 6, 7, plankStairBlock, 6);
		placeChest(world, random, 2, 7, 5, 5, chestContents);
		placePlateWithCertainty(world, random, 2, 7, 6, plateBlock, LOTRFoods.ELF);
		placeBarrel(world, random, 2, 7, 7, 5, LOTRFoods.ELF_DRINK);
		if (leafy) {
			for (int i111 = -4; i111 <= 4; ++i111) {
				for (k13 = 0; k13 <= 13; ++k13) {
					for (j1 = 5; j1 <= 12; ++j1) {
						if (Math.abs(i111) < 3 && j1 < 11 || random.nextInt(4) != 0 || !isAir(world, i111, j1, k13) || !isSolidRoofBlock(world, i111, j1 - 1, k13) && !isSolidRoofBlock(world, i111 - 1, j1, k13) && !isSolidRoofBlock(world, i111 + 1, j1, k13)) {
							continue;
						}
						setBlockAndMetadata(world, i111, j1, k13, leafBlock, leafMeta);
					}
				}
			}
		}
		int elves = 1 + random.nextInt(2);
		for (int l = 0; l < elves; ++l) {
			LOTREntityElf elf = createElf(world);
			spawnNPCAndSetHome(elf, world, 0, 1, 7, 16);
		}
		return true;
	}

	public ItemStack getElfFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetHighElven), new ItemStack(LOTRMod.bodyHighElven), new ItemStack(LOTRMod.legsHighElven), new ItemStack(LOTRMod.bootsHighElven), new ItemStack(LOTRMod.daggerHighElven), new ItemStack(LOTRMod.swordHighElven), new ItemStack(LOTRMod.spearHighElven), new ItemStack(LOTRMod.longspearHighElven), new ItemStack(LOTRMod.highElvenBow), new ItemStack(Items.arrow), new ItemStack(Items.feather), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean isSolidRoofBlock(IBlockAccess world, int i, int j, int k) {
		return getBlock(world, i, j, k).getMaterial().isOpaque();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = LOTRMod.brick3;
		brickMeta = 2;
		brickSlabBlock = LOTRMod.slabSingle5;
		brickSlabMeta = 5;
		brickStairBlock = LOTRMod.stairsHighElvenBrick;
		brickWallBlock = LOTRMod.wall2;
		brickWallMeta = 11;
		brickCarvedBlock = LOTRMod.brick2;
		brickCarvedMeta = 13;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 10;
		stoneBlock = LOTRMod.smoothStoneV;
		stoneMeta = 0;
		stoneSlabBlock = Blocks.stone_slab;
		stoneSlabMeta = 0;
		int randomRoof = random.nextInt(5);
		switch (randomRoof) {
			case 0:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 11;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 3;
				roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
				break;
			case 1:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 3;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
				roofSlabMeta = 3;
				roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
				break;
			case 2:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 9;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 1;
				roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
				break;
			case 3:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 8;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
				roofSlabMeta = 0;
				roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
				break;
			case 4:
				roofBlock = LOTRMod.clayTileDyed;
				roofMeta = 7;
				roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
				roofSlabMeta = 7;
				roofStairBlock = LOTRMod.stairsClayTileDyedGray;
				break;
			default:
				break;
		}
		int randomWood = random.nextInt(4);
		switch (randomWood) {
			case 0:
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				trapdoorBlock = Blocks.trapdoor;
				break;
			case 1:
				plankBlock = Blocks.planks;
				plankMeta = 2;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 2;
				plankStairBlock = Blocks.birch_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 2;
				trapdoorBlock = LOTRMod.trapdoorBirch;
				break;
			case 2:
				plankBlock = LOTRMod.planks;
				plankMeta = 9;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = LOTRMod.stairsBeech;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 9;
				trapdoorBlock = LOTRMod.trapdoorBeech;
				break;
			case 3:
				plankBlock = LOTRMod.planks;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle;
				plankSlabMeta = 4;
				plankStairBlock = LOTRMod.stairsApple;
				fenceBlock = LOTRMod.fence;
				fenceMeta = 4;
				trapdoorBlock = LOTRMod.trapdoorApple;
				break;
			default:
				break;
		}
		int randomLeaf = random.nextInt(3);
		switch (randomLeaf) {
			case 0:
				leafBlock = Blocks.leaves;
				leafMeta = 4;
				break;
			case 1:
				leafBlock = Blocks.leaves;
				leafMeta = 6;
				break;
			case 2:
				leafBlock = LOTRMod.leaves2;
				leafMeta = 5;
				break;
			default:
				break;
		}
		tableBlock = LOTRMod.highElvenTable;
		bedBlock = LOTRMod.highElvenBed;
		barsBlock = LOTRMod.highElfWoodBars;
		torchBlock = LOTRMod.highElvenTorch;
		chandelierBlock = LOTRMod.chandelier;
		chandelierMeta = 10;
		plateBlock = LOTRMod.plateBlock;
		bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
		chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
	}
}
