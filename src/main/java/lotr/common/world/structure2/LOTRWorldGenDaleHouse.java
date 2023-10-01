package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDaleHouse extends LOTRWorldGenDaleStructure {
	public LOTRWorldGenDaleHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int i12;
		int j1;
		int i13;
		int i14;
		int k1;
		int k12;
		int k13;
		int fillBlock22;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i16 = -8; i16 <= 3; ++i16) {
				for (int k14 = -1; k14 <= 9; ++k14) {
					int j12 = getTopBlock(world, i16, k14) - 1;
					Block block = getBlock(world, i16, j12, k14);
					if (block != Blocks.grass) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i17 = -7; i17 <= 2; ++i17) {
			for (k1 = 0; k1 <= 8; ++k1) {
				int j12;
				if (i17 < -2 && k1 > 4) {
					continue;
				}
				for (int j13 = 1; j13 <= 10; ++j13) {
					setAir(world, i17, j13, k1);
				}
				Block fillBlock221;
				int fillMeta;
				if ((i17 == -2 || i17 == 2) && (k1 == 0 || k1 == 4 || k1 == 8)) {
					fillBlock221 = brickBlock;
					fillMeta = brickMeta;
					for (j12 = 1; j12 <= 7; ++j12) {
						setBlockAndMetadata(world, i17, j12, k1, brickBlock, brickMeta);
					}
				} else if ((k1 == 0 || k1 == 4) && i17 >= -6 && i17 <= -4) {
					fillBlock221 = brickBlock;
					fillMeta = brickMeta;
					for (j12 = 1; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i17, j12, k1, brickBlock, brickMeta);
					}
					for (j12 = 5; j12 <= 6; ++j12) {
						setBlockAndMetadata(world, i17, j12, k1, plankBlock, plankMeta);
					}
					setBlockAndMetadata(world, i17, 7, k1, woodBeamBlock, woodBeamMeta | 4);
				} else if (i17 == -7 && k1 >= 1 && k1 <= 3) {
					fillBlock221 = brickBlock;
					fillMeta = brickMeta;
					for (j12 = 1; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, -7, j12, k1, brickBlock, brickMeta);
					}
					for (j12 = 5; j12 <= 6; ++j12) {
						setBlockAndMetadata(world, -7, j12, k1, plankBlock, plankMeta);
					}
					setBlockAndMetadata(world, -7, 7, k1, woodBeamBlock, woodBeamMeta | 8);
				} else if ((k1 == 0 || k1 == 4) && (i17 == -7 || i17 == -3)) {
					fillBlock221 = woodBlock;
					fillMeta = woodMeta;
					for (j12 = 1; j12 <= 7; ++j12) {
						setBlockAndMetadata(world, i17, j12, k1, woodBlock, woodMeta);
					}
				} else {
					fillBlock221 = floorBlock;
					fillMeta = floorMeta;
				}
				for (j12 = 0; (j12 == 0 || !isOpaque(world, i17, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i17, j12, k1, fillBlock221, fillMeta);
					setGrassToDirt(world, i17, j12 - 1, k1);
				}
			}
		}
		for (int[] pos : new int[][]{{-3, -1}, {-7, -1}, {-8, 0}, {-8, 4}, {-7, 5}}) {
			i1 = pos[0];
			int k15 = pos[1];
			for (int j14 = 7; (j14 >= 4 || !isOpaque(world, i1, j14, k15)) && getY(j14) >= 0; --j14) {
				setBlockAndMetadata(world, i1, j14, k15, fenceBlock, fenceMeta);
			}
		}
		for (int k16 : new int[]{0, 4, 8}) {
			setBlockAndMetadata(world, -1, 3, k16, brickStairBlock, 4);
			setBlockAndMetadata(world, 1, 3, k16, brickStairBlock, 5);
		}
		int[] i17 = {-2, 2};
		k1 = i17.length;
		for (fillBlock22 = 0; fillBlock22 < k1; ++fillBlock22) {
			int i132 = i17[fillBlock22];
			setBlockAndMetadata(world, i132, 3, 1, brickStairBlock, 7);
			setBlockAndMetadata(world, i132, 3, 3, brickStairBlock, 6);
			setBlockAndMetadata(world, i132, 3, 5, brickStairBlock, 7);
			setBlockAndMetadata(world, i132, 3, 7, brickStairBlock, 6);
		}
		for (int j15 = 1; j15 <= 2; ++j15) {
			setBlockAndMetadata(world, -2, j15, 1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, -2, j15, 3, brickWallBlock, brickWallMeta);
		}
		for (int k132 = 1; k132 <= 3; ++k132) {
			for (j1 = 1; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, -3, j1, k132, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -3, 1, 2, doorBlock, 0);
		setBlockAndMetadata(world, -3, 2, 2, doorBlock, 8);
		setBlockAndMetadata(world, 0, 1, 7, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 1, 1, 7, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 1, 2, 7, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 1, 1, 6, Blocks.hay_block, 0);
		for (int i142 = -2; i142 <= 2; ++i142) {
			setBlockAndMetadata(world, i142, 4, -1, brickStairBlock, 6);
			if (IntMath.mod(i142, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, i142, 5, -1, brickWallBlock, brickWallMeta);
		}
		for (k13 = -1; k13 <= 9; ++k13) {
			setBlockAndMetadata(world, 3, 4, k13, brickStairBlock, 4);
			if (IntMath.mod(k13, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, 3, 5, k13, brickWallBlock, brickWallMeta);
		}
		for (i13 = 2; i13 >= -2; --i13) {
			setBlockAndMetadata(world, i13, 4, 9, brickStairBlock, 7);
			if (IntMath.mod(i13, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, i13, 5, 9, brickWallBlock, brickWallMeta);
		}
		for (k13 = 9; k13 >= 5; --k13) {
			setBlockAndMetadata(world, -3, 4, k13, brickStairBlock, 5);
			if (IntMath.mod(k13, 2) != 1) {
				continue;
			}
			setBlockAndMetadata(world, -3, 5, k13, brickWallBlock, brickWallMeta);
		}
		for (i13 = -4; i13 >= -6; --i13) {
			setBlockAndMetadata(world, i13, 4, 5, brickStairBlock, 7);
		}
		setBlockAndMetadata(world, -7, 4, 5, brickBlock, brickMeta);
		setBlockAndMetadata(world, -8, 4, 5, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -8, 4, 4, brickBlock, brickMeta);
		for (k13 = 3; k13 >= 1; --k13) {
			setBlockAndMetadata(world, -8, 4, k13, brickStairBlock, 5);
		}
		setBlockAndMetadata(world, -8, 4, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -8, 4, -1, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -7, 4, -1, brickBlock, brickMeta);
		for (int i18 = -6; i18 <= -4; ++i18) {
			setBlockAndMetadata(world, i18, 4, -1, brickStairBlock, 6);
		}
		setBlockAndMetadata(world, -3, 4, -1, brickBlock, brickMeta);
		for (int k14 : new int[]{0, 4, 8}) {
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 4, k14, brickBlock, brickMeta);
				if (k14 != 0 && k14 != 8) {
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k14, plankBlock, plankMeta);
				setBlockAndMetadata(world, i1, 6, k14, plankBlock, plankMeta);
				setBlockAndMetadata(world, i1, 7, k14, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		int[] i18 = {-2, 2};
		j1 = i18.length;
		for (fillBlock22 = 0; fillBlock22 < j1; ++fillBlock22) {
			int i15 = i18[fillBlock22];
			for (int k122 = 1; k122 <= 3; ++k122) {
				setBlockAndMetadata(world, i15, 4, k122, brickBlock, brickMeta);
				setBlockAndMetadata(world, i15, 5, k122, plankBlock, plankMeta);
				setBlockAndMetadata(world, i15, 6, k122, plankBlock, plankMeta);
				setBlockAndMetadata(world, i15, 7, k122, woodBeamBlock, woodBeamMeta | 8);
			}
			for (k12 = 5; k12 <= 7; ++k12) {
				setBlockAndMetadata(world, i15, 4, k12, brickBlock, brickMeta);
				setBlockAndMetadata(world, i15, 5, k12, plankBlock, plankMeta);
				setBlockAndMetadata(world, i15, 6, k12, plankBlock, plankMeta);
				setBlockAndMetadata(world, i15, 7, k12, woodBeamBlock, woodBeamMeta | 8);
			}
		}
		for (int i15 = -1; i15 <= 1; ++i15) {
			for (k1 = 1; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i15, 4, k1, plankBlock, plankMeta);
			}
			for (k1 = 5; k1 <= 7; ++k1) {
				setBlockAndMetadata(world, i15, 4, k1, plankBlock, plankMeta);
			}
		}
		setBlockAndMetadata(world, -5, 2, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 6, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 6, 2, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 6, 6, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 6, 8, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 6, 6, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 6, 4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -7, 6, 2, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 6, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 7, -1, brickStairBlock, 6);
		setBlockAndMetadata(world, 2, 7, -1, brickStairBlock, 6);
		setBlockAndMetadata(world, 3, 7, 0, brickStairBlock, 4);
		setBlockAndMetadata(world, 3, 7, 4, brickStairBlock, 4);
		setBlockAndMetadata(world, 3, 7, 8, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 7, 9, brickStairBlock, 7);
		setBlockAndMetadata(world, -2, 7, 9, brickStairBlock, 7);
		setBlockAndMetadata(world, -3, 7, 8, brickStairBlock, 5);
		for (i14 = -8; i14 <= 3; ++i14) {
			for (k1 = -1; k1 <= 9; ++k1) {
				if (i14 < -3 && k1 > 5) {
					continue;
				}
				setBlockAndMetadata(world, i14, 8, k1, brickBlock, brickMeta);
			}
		}
		setBlockAndMetadata(world, -8, 8, -1, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -8, 8, 5, brickSlabBlock, brickSlabMeta | 8);
		for (i14 = -8; i14 <= 3; ++i14) {
			setBlockAndMetadata(world, i14, 9, -1, roofStairBlock, 2);
			setBlockAndMetadata(world, i14, 10, 0, roofStairBlock, 2);
			setBlockAndMetadata(world, i14, 11, 1, roofStairBlock, 2);
			setBlockAndMetadata(world, i14, 11, 2, roofBlock, roofMeta);
			if (i14 != 0) {
				setBlockAndMetadata(world, i14, 11, 3, roofStairBlock, 3);
			}
			if (i14 <= -2 || i14 >= 2) {
				setBlockAndMetadata(world, i14, 10, 4, roofStairBlock, 3);
			}
			if (i14 > -3 && i14 < 3) {
				continue;
			}
			setBlockAndMetadata(world, i14, 9, 5, roofStairBlock, 3);
		}
		for (int k17 = 3; k17 <= 9; ++k17) {
			if (k17 >= 6) {
				setBlockAndMetadata(world, -3, 9, k17, roofStairBlock, 1);
				setBlockAndMetadata(world, 3, 9, k17, roofStairBlock, 0);
			}
			if (k17 >= 5) {
				setBlockAndMetadata(world, -2, 10, k17, roofStairBlock, 1);
				setBlockAndMetadata(world, 2, 10, k17, roofStairBlock, 0);
			}
			if (k17 >= 4) {
				setBlockAndMetadata(world, -1, 11, k17, roofStairBlock, 1);
				setBlockAndMetadata(world, 1, 11, k17, roofStairBlock, 0);
			}
			setBlockAndMetadata(world, 0, 11, k17, roofBlock, roofMeta);
		}
		for (int i15 : new int[]{-7, 2}) {
			for (k12 = 0; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, i15, 9, k12, brickBlock, brickMeta);
			}
			for (k12 = 1; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, i15, 10, k12, brickBlock, brickMeta);
			}
		}
		for (int i15 : new int[]{-8, 3}) {
			setBlockAndMetadata(world, i15, 9, 0, roofStairBlock, 7);
			setBlockAndMetadata(world, i15, 10, 1, roofStairBlock, 7);
			setBlockAndMetadata(world, i15, 10, 3, roofStairBlock, 6);
			setBlockAndMetadata(world, i15, 9, 4, roofStairBlock, 6);
		}
		for (int i122 = -2; i122 <= 2; ++i122) {
			setBlockAndMetadata(world, i122, 9, 8, brickBlock, brickMeta);
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, 10, 8, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -2, 9, 9, roofStairBlock, 4);
		setBlockAndMetadata(world, -1, 10, 9, roofStairBlock, 4);
		setBlockAndMetadata(world, 1, 10, 9, roofStairBlock, 5);
		setBlockAndMetadata(world, 2, 9, 9, roofStairBlock, 5);
		setBlockAndMetadata(world, 0, 12, 2, LOTRMod.hearth, 0);
		setBlockAndMetadata(world, 0, 13, 2, Blocks.fire, 0);
		setBlockAndMetadata(world, 0, 11, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 12, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 12, 2, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 12, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 13, 1, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -1, 13, 2, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 1, 13, 2, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 0, 13, 3, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, 0, 14, 1, roofStairBlock, 2);
		setBlockAndMetadata(world, -1, 14, 2, roofStairBlock, 1);
		setBlockAndMetadata(world, 1, 14, 2, roofStairBlock, 0);
		setBlockAndMetadata(world, 0, 14, 3, roofStairBlock, 3);
		setBlockAndMetadata(world, 0, 14, 2, roofBlock, roofMeta);
		for (int j16 = 1; j16 <= 7; ++j16) {
			setBlockAndMetadata(world, -5, j16, 2, woodBeamBlock, woodBeamMeta);
		}
		setBlockAndMetadata(world, -4, 3, 1, Blocks.torch, 1);
		setBlockAndMetadata(world, -6, 7, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 7, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, -5, 1, 1, brickStairBlock, 0);
		setBlockAndMetadata(world, -6, 1, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -6, 1, 2, brickStairBlock, 7);
		setBlockAndMetadata(world, -6, 2, 2, brickStairBlock, 2);
		setBlockAndMetadata(world, -6, 2, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, -5, 2, 3, brickStairBlock, 4);
		setBlockAndMetadata(world, -5, 3, 3, brickStairBlock, 1);
		setBlockAndMetadata(world, -4, 3, 3, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 3, 2, brickStairBlock, 6);
		setBlockAndMetadata(world, -4, 4, 2, brickStairBlock, 3);
		setBlockAndMetadata(world, -4, 4, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -5, 4, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -5, 5, 1, fenceBlock, fenceMeta);
		for (int k18 = 1; k18 <= 3; ++k18) {
			setBlockAndMetadata(world, -3, 4, k18, woodBeamBlock, woodBeamMeta | 8);
			setBlockAndMetadata(world, -2, 5, k18, brickBlock, brickMeta);
			setBlockAndMetadata(world, -2, 6, k18, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, -2, 5, 2, doorBlock, 2);
		setBlockAndMetadata(world, -2, 6, 2, doorBlock, 8);
		setBlockAndMetadata(world, 0, 7, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, -1, 7, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 7, 3, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 7, 5, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 5, 1, LOTRMod.strawBed, 1);
		setBlockAndMetadata(world, 1, 5, 1, LOTRMod.strawBed, 9);
		setBlockAndMetadata(world, 1, 5, 2, woodBeamBlock, woodBeamMeta);
		placeMug(world, random, 1, 6, 2, 1, LOTRFoods.DALE_DRINK);
		placeChest(world, random, 1, 5, 3, 5, LOTRChestContents.DALE_HOUSE);
		spawnItemFrame(world, 2, 7, 1, 3, new ItemStack(Items.clock));
		setBlockAndMetadata(world, 1, 5, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 6, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 7, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 7, 4, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 7, 4, brickStairBlock, 4);
		setBlockAndMetadata(world, 1, 5, 5, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 1, 5, 6, Blocks.furnace, 5);
		setBlockAndMetadata(world, 1, 5, 7, brickBlock, brickMeta);
		placePlateWithCertainty(world, random, 1, 6, 7, plateBlock, LOTRFoods.DALE);
		setBlockAndMetadata(world, 0, 5, 7, Blocks.cauldron, 3);
		setBlockAndMetadata(world, -1, 5, 7, LOTRMod.daleTable, 0);
		setBlockAndMetadata(world, -1, 7, 7, brickStairBlock, 2);
		setBlockAndMetadata(world, 0, 7, 7, brickStairBlock, 2);
		setBlockAndMetadata(world, 1, 7, 7, brickBlock, brickMeta);
		setBlockAndMetadata(world, 1, 7, 6, brickStairBlock, 1);
		setBlockAndMetadata(world, 1, 7, 5, brickStairBlock, 1);
		setBlockAndMetadata(world, -3, 10, 2, LOTRMod.chandelier, 0);
		setBlockAndMetadata(world, 0, 10, 5, LOTRMod.chandelier, 0);
		if (random.nextInt(3) == 0) {
			i12 = MathHelper.getRandomIntegerInRange(random, -6, 1);
			k1 = MathHelper.getRandomIntegerInRange(random, 0, 4);
			int chestDir = Direction.directionToFacing[random.nextInt(4)];
			placeChest(world, random, i12, 9, k1, chestDir, LOTRChestContents.DALE_HOUSE_TREASURE);
		}
		LOTREntityDaleMan daleMan = new LOTREntityDaleMan(world);
		spawnNPCAndSetHome(daleMan, world, -1, 5, 2, 16);
		return true;
	}
}
