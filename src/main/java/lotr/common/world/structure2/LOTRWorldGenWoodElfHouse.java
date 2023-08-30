package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityWoodElf;
import lotr.common.world.feature.LOTRWorldGenMirkOak;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWoodElfHouse extends LOTRWorldGenStructureBase2 {
	public WorldGenerator treeGen = new LOTRWorldGenMirkOak(true, 3, 4, 0, false).setGreenOak().disableRestrictions().disableRoots();
	public Block plank1Block;
	public int plank1Meta;
	public Block wood1Block;
	public int wood1Meta;
	public Block fence1Block;
	public int fence1Meta;
	public Block doorBlock;
	public Block plank2Block;
	public int plank2Meta;
	public Block fence2Block;
	public int fence2Meta;
	public Block stair2Block;
	public Block barsBlock;
	public Block plateBlock;

	public LOTRWorldGenWoodElfHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int k2;
		int i1;
		int k12;
		int k13;
		int i12;
		int j1;
		int i13;
		int i2;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i13 = -6; i13 <= 6; ++i13) {
				for (k12 = -6; k12 <= 6; ++k12) {
					j1 = getTopBlock(world, i13, k12);
					Block block = getBlock(world, i13, j1 - 1, k12);
					if (block != Blocks.grass) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 3) {
						continue;
					}
					return false;
				}
			}
		}
		if (random.nextInt(2) == 0) {
			plank1Block = LOTRMod.planks2;
			plank1Meta = 13;
			wood1Block = LOTRMod.wood7;
			wood1Meta = 1;
			fence1Block = LOTRMod.fence2;
			fence1Meta = 13;
			doorBlock = LOTRMod.doorGreenOak;
		} else {
			plank1Block = LOTRMod.planks;
			plank1Meta = 9;
			wood1Block = LOTRMod.wood2;
			wood1Meta = 1;
			fence1Block = LOTRMod.fence;
			fence1Meta = 9;
			doorBlock = LOTRMod.doorBeech;
		}
		int randomWood2 = random.nextInt(2);
		if (randomWood2 == 0) {
			plank2Block = LOTRMod.planks2;
			plank2Meta = 13;
			fence2Block = LOTRMod.fence2;
			fence2Meta = 13;
			stair2Block = LOTRMod.stairsGreenOak;
		} else {
			plank2Block = LOTRMod.planks;
			plank2Meta = 9;
			fence2Block = LOTRMod.fence;
			fence2Meta = 9;
			stair2Block = LOTRMod.stairsBeech;
		}
		barsBlock = LOTRMod.woodElfWoodBars;
		plateBlock = LOTRMod.woodPlateBlock;
		for (i13 = -6; i13 <= 6; ++i13) {
			for (k12 = -6; k12 <= 6; ++k12) {
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i13, j1, k12);
				}
				for (j1 = 0; (j1 == 0 || !isOpaque(world, i13, j1, k12)) && getY(j1) >= 0; --j1) {
					if (getBlock(world, i13, j1 + 1, k12).isOpaqueCube()) {
						setBlockAndMetadata(world, i13, j1, k12, Blocks.dirt, 0);
					} else {
						setBlockAndMetadata(world, i13, j1, k12, Blocks.grass, 0);
					}
					setGrassToDirt(world, i13, j1 - 1, k12);
				}
			}
		}
		for (i13 = -4; i13 <= 4; ++i13) {
			for (k12 = -4; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, i13, 0, k12, LOTRMod.brick3, 5);
			}
		}
		for (i13 = -4; i13 <= 4; ++i13) {
			for (k12 = -4; k12 <= 4; ++k12) {
				i2 = Math.abs(i13);
				k2 = Math.abs(k12);
				for (int j12 = 1; j12 <= 3; ++j12) {
					if (i2 == 4 && k2 == 4) {
						setBlockAndMetadata(world, i13, j12, k12, wood1Block, wood1Meta);
						continue;
					}
					if (i2 == 4 || k2 == 4) {
						setBlockAndMetadata(world, i13, j12, k12, plank1Block, plank1Meta);
						continue;
					}
					setAir(world, i13, j12, k12);
				}
				if (i2 != 4 && k2 != 4) {
					continue;
				}
				setBlockAndMetadata(world, i13, 4, k12, plank2Block, plank2Meta);
			}
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			setBlockAndMetadata(world, i13, 4, -5, stair2Block, 6);
			setBlockAndMetadata(world, i13, 4, 5, stair2Block, 7);
			for (int k14 : new int[]{-5, 5}) {
				if (getBlock(world, i13, 1, k14).isOpaqueCube()) {
					continue;
				}
				setBlockAndMetadata(world, i13, 1, k14, LOTRMod.leaves7, 5);
			}
		}
		for (k1 = -4; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -5, 4, k1, stair2Block, 5);
			setBlockAndMetadata(world, 5, 4, k1, stair2Block, 4);
			for (int i14 : new int[]{-5, 5}) {
				if (getBlock(world, i14, 1, k1).isOpaqueCube()) {
					continue;
				}
				setBlockAndMetadata(world, i14, 1, k1, LOTRMod.leaves7, 5);
			}
		}
		for (i13 = -3; i13 <= 3; ++i13) {
			setBlockAndMetadata(world, i13, 4, -3, stair2Block, 7);
			setBlockAndMetadata(world, i13, 4, 3, stair2Block, 6);
		}
		for (k1 = -2; k1 <= 2; ++k1) {
			setBlockAndMetadata(world, -3, 4, k1, stair2Block, 4);
			setBlockAndMetadata(world, 3, 4, k1, stair2Block, 5);
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			for (int k15 = -5; k15 <= 5; ++k15) {
				i2 = Math.abs(i13);
				k2 = Math.abs(k15);
				if (i2 == 5 || k2 == 5) {
					setBlockAndMetadata(world, i13, 5, k15, fence1Block, fence1Meta);
				}
				if (i2 == 5 && k2 == 5) {
					setBlockAndMetadata(world, i13, 6, k15, LOTRMod.woodElvenTorch, 5);
				}
				if ((i2 != 5 || k2 != 0) && (k2 != 5 || i2 != 0)) {
					continue;
				}
				setBlockAndMetadata(world, i13, 6, k15, fence1Block, fence1Meta);
				setBlockAndMetadata(world, i13, 7, k15, LOTRMod.woodElvenTorch, 5);
			}
		}
		setBlockAndMetadata(world, -3, 2, -1, LOTRMod.woodElvenTorch, 2);
		setBlockAndMetadata(world, -3, 2, 1, LOTRMod.woodElvenTorch, 2);
		setBlockAndMetadata(world, 3, 2, -1, LOTRMod.woodElvenTorch, 1);
		setBlockAndMetadata(world, 3, 2, 1, LOTRMod.woodElvenTorch, 1);
		setBlockAndMetadata(world, -1, 2, -3, LOTRMod.woodElvenTorch, 3);
		setBlockAndMetadata(world, 1, 2, -3, LOTRMod.woodElvenTorch, 3);
		setBlockAndMetadata(world, -1, 2, 3, LOTRMod.woodElvenTorch, 4);
		setBlockAndMetadata(world, 1, 2, 3, LOTRMod.woodElvenTorch, 4);
		int[] carpets = {12, 13, 14, 15};
		int carpetType = carpets[random.nextInt(carpets.length)];
		for (i1 = -4; i1 <= 4; ++i1) {
			for (int k16 = -4; k16 <= 4; ++k16) {
				int i22 = Math.abs(i1);
				int k22 = Math.abs(k16);
				setBlockAndMetadata(world, i1, -5, k16, LOTRMod.brick3, 5);
				for (int j13 = -4; j13 <= -1; ++j13) {
					if (i22 == 4 || k22 == 4) {
						if (j13 >= -3 && j13 <= -2) {
							setBlockAndMetadata(world, i1, j13, k16, Blocks.stonebrick, 0);
							continue;
						}
						setBlockAndMetadata(world, i1, j13, k16, plank1Block, plank1Meta);
						continue;
					}
					setAir(world, i1, j13, k16);
				}
				if (i22 > 2 || k22 > 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, -4, k16, Blocks.carpet, carpetType);
			}
		}
		for (j1 = -3; j1 <= -2; ++j1) {
			setBlockAndMetadata(world, -2, j1, -4, wood1Block, wood1Meta);
			setBlockAndMetadata(world, -1, j1, -4, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, 0, j1, -4, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, 1, j1, -4, Blocks.bookshelf, 0);
			setBlockAndMetadata(world, 1, j1, -4, wood1Block, wood1Meta);
		}
		for (k13 = 2; k13 <= 3; ++k13) {
			for (i12 = -2; i12 <= 2; ++i12) {
				setAir(world, i12, 0, k13);
				int stairHeight = i12 + 2;
				for (int j14 = -4; j14 < -4 + stairHeight; ++j14) {
					setBlockAndMetadata(world, i12, j14, k13, LOTRMod.brick3, 5);
				}
				setBlockAndMetadata(world, i12, -4 + stairHeight, k13, LOTRMod.stairsWoodElvenBrick, 1);
			}
			for (int j15 = -4; j15 <= -1; ++j15) {
				setBlockAndMetadata(world, 3, j15, k13, LOTRMod.brick3, 5);
			}
		}
		setBlockAndMetadata(world, -3, -2, -3, LOTRMod.woodElvenTorch, 3);
		setBlockAndMetadata(world, 3, -2, -3, LOTRMod.woodElvenTorch, 3);
		setBlockAndMetadata(world, -3, -2, 3, LOTRMod.woodElvenTorch, 4);
		setBlockAndMetadata(world, 3, -2, 1, LOTRMod.woodElvenTorch, 4);
		setBlockAndMetadata(world, 3, -4, 0, LOTRMod.woodElvenBed, 0);
		setBlockAndMetadata(world, 3, -4, 1, LOTRMod.woodElvenBed, 8);
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, -1, -3, barsBlock, 0);
		}
		for (k13 = -2; k13 <= 3; ++k13) {
			setBlockAndMetadata(world, -3, -1, k13, barsBlock, 0);
		}
		for (k13 = -2; k13 <= 1; ++k13) {
			setBlockAndMetadata(world, 3, -1, k13, barsBlock, 0);
		}
		for (j1 = 1; j1 <= 3; ++j1) {
			for (i12 = -1; i12 <= 1; ++i12) {
				setBlockAndMetadata(world, i12, j1, -4, wood1Block, wood1Meta);
			}
		}
		setBlockAndMetadata(world, 0, 0, -2, LOTRMod.brick2, 14);
		setBlockAndMetadata(world, -2, 0, 0, LOTRMod.brick2, 14);
		setBlockAndMetadata(world, 2, 0, 0, LOTRMod.brick2, 14);
		setBlockAndMetadata(world, 3, 0, 3, LOTRMod.brick2, 14);
		setAir(world, 0, 1, -5);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, -1, 2, -5, LOTRMod.woodElvenTorch, 4);
		setBlockAndMetadata(world, 1, 2, -5, LOTRMod.woodElvenTorch, 4);
		setBlockAndMetadata(world, -3, 2, -4, barsBlock, 0);
		setBlockAndMetadata(world, -2, 2, -4, barsBlock, 0);
		setBlockAndMetadata(world, 2, 2, -4, barsBlock, 0);
		setBlockAndMetadata(world, 3, 2, -4, barsBlock, 0);
		setBlockAndMetadata(world, 3, 1, -3, plank2Block, plank2Meta);
		setBlockAndMetadata(world, 2, 1, -3, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 1, -2, LOTRMod.woodElvenTable, 0);
		setBlockAndMetadata(world, -3, 1, -3, plank2Block, plank2Meta);
		placeMug(world, random, -3, 2, -3, random.nextInt(4), LOTRFoods.WOOD_ELF_DRINK);
		setBlockAndMetadata(world, -2, 1, -3, plank2Block, plank2Meta);
		placePlate(world, random, -2, 2, -3, plateBlock, LOTRFoods.ELF);
		placeChest(world, random, -3, 1, -2, 0, LOTRChestContents.WOOD_ELF_HOUSE);
		placeWoodElfItemFrame(world, -4, 2, 0, 1, random);
		placeWoodElfItemFrame(world, 4, 2, 0, 3, random);
		for (j1 = 1; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, 3, j1, 3, Blocks.ladder, 2);
		}
		for (j1 = -4; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, LOTRMod.wood7, 1);
		}
		treeGen.generate(world, random, getX(0, 0), getY(5), getZ(0, 0));
		LOTREntityWoodElf elf = new LOTREntityWoodElf(world);
		spawnNPCAndSetHome(elf, world, 1, 1, 1, 8);
		return true;
	}

	public void placeWoodElfItemFrame(World world, int i, int j, int k, int direction, Random random) {
		ItemStack item = null;
		int l = random.nextInt(3);
		switch (l) {
			case 0: {
				item = new ItemStack(LOTRMod.mirkwoodBow);
				break;
			}
			case 1: {
				item = new ItemStack(Items.arrow);
				break;
			}
			case 2: {
				item = new ItemStack(LOTRMod.sapling7, 1, 1);
				break;
			}
			case 3: {
				item = new ItemStack(Blocks.red_flower);
				break;
			}
			case 4: {
				item = new ItemStack(Blocks.yellow_flower);
				break;
			}
			case 5: {
				item = new ItemStack(Items.book);
			}
		}
		spawnItemFrame(world, i, j, k, direction, item);
	}
}
