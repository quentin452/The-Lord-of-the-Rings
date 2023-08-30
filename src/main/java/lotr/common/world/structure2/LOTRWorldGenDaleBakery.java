package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.world.World;

public class LOTRWorldGenDaleBakery extends LOTRWorldGenDaleStructure {
	public LOTRWorldGenDaleBakery(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k17;
		int j1;
		int i12;
		int i2;
		int k12;
		int i13;
		int k13;
		int k14;
		int k15;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -7; i14 <= 7; ++i14) {
				for (k17 = -4; k17 <= 15; ++k17) {
					int j12 = getTopBlock(world, i14, k17) - 1;
					Object block = getBlock(world, i14, j12, k17);
					if (block != Blocks.grass) {
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
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k12 = 0; k12 <= 13; ++k12) {
				j1 = 0;
				while (!isOpaque(world, i12, j1, k12) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i12, j1, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j1 - 1, k12);
					--j1;
				}
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i12, j1, k12);
				}
				if (Math.abs(i12) == 5 || k12 == 0 || k12 == 13) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i12, j1, k12, brickBlock, brickMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i12, 0, k12, floorBlock, floorMeta);
			}
		}
		for (i12 = -6; i12 <= 6; ++i12) {
			for (k12 = -1; k12 <= 14; ++k12) {
				if ((Math.abs(i12) != 6 || k12 != -1 && k12 != 14) && (Math.abs(i12) != 1 || k12 != -1)) {
					continue;
				}
				for (j1 = 4; (j1 >= 1 || !isOpaque(world, i12, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i12, j1, k12, woodBeamBlock, woodBeamMeta);
					setGrassToDirt(world, i12, j1 - 1, k12);
				}
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			if (Math.abs(i12) == 1) {
				k12 = -2;
				for (j1 = 2; (j1 >= 1 || !isOpaque(world, i12, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i12, j1, k12, fenceBlock, fenceMeta);
				}
				continue;
			}
			if (i12 != 0) {
				continue;
			}
			k12 = -1;
			for (j1 = 0; (j1 >= 0 || !isOpaque(world, i12, j1, k12)) && getY(j1) >= 0; --j1) {
				setBlockAndMetadata(world, i12, j1, k12, floorBlock, floorMeta);
				setGrassToDirt(world, i12, j1 - 1, k12);
			}
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			setBlockAndMetadata(world, i12, 4, -1, brickBlock, brickMeta);
			setBlockAndMetadata(world, i12, 4, 14, brickBlock, brickMeta);
		}
		for (k13 = 0; k13 <= 13; ++k13) {
			setBlockAndMetadata(world, -6, 4, k13, brickBlock, brickMeta);
			setBlockAndMetadata(world, 6, 4, k13, brickBlock, brickMeta);
		}
		for (i12 = -7; i12 <= 7; ++i12) {
			setBlockAndMetadata(world, i12, 4, -2, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i12, 4, 15, brickSlabBlock, brickSlabMeta | 8);
		}
		for (k13 = -2; k13 <= 15; ++k13) {
			setBlockAndMetadata(world, -7, 4, k13, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, 7, 4, k13, brickSlabBlock, brickSlabMeta | 8);
		}
		for (int i15 : new int[]{-5, 2}) {
			setBlockAndMetadata(world, i15, 2, -1, trapdoorBlock, 12);
			setBlockAndMetadata(world, i15, 3, -1, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i15, 4, -2, brickBlock, brickMeta);
			setBlockAndMetadata(world, i15 + 1, 2, 0, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15 + 1, 3, -1, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i15 + 2, 2, 0, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15 + 2, 3, -1, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i15 + 3, 2, -1, trapdoorBlock, 12);
			setBlockAndMetadata(world, i15 + 3, 3, -1, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i15 + 3, 4, -2, brickBlock, brickMeta);
		}
		int[] k16 = {-5, 2};
		k12 = k16.length;
		for (j1 = 0; j1 < k12; ++j1) {
			int i15;
			i15 = k16[j1];
			setBlockAndMetadata(world, i15, 2, 14, trapdoorBlock, 13);
			setBlockAndMetadata(world, i15, 3, 14, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i15, 4, 15, brickBlock, brickMeta);
			setBlockAndMetadata(world, i15 + 1, 2, 13, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15 + 1, 3, 14, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i15 + 2, 2, 13, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15 + 2, 3, 14, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i15 + 3, 2, 14, trapdoorBlock, 13);
			setBlockAndMetadata(world, i15 + 3, 3, 14, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i15 + 3, 4, 15, brickBlock, brickMeta);
		}
		for (k14 = 0; k14 <= 13; ++k14) {
			if (k14 == 0 || k14 == 3 || k14 == 6 || k14 == 8 || k14 == 13) {
				setBlockAndMetadata(world, -6, 2, k14, trapdoorBlock, 15);
				setBlockAndMetadata(world, -6, 3, k14, brickSlabBlock, brickSlabMeta | 8);
				setBlockAndMetadata(world, -7, 4, k14, brickBlock, brickMeta);
				setBlockAndMetadata(world, 6, 2, k14, trapdoorBlock, 14);
				setBlockAndMetadata(world, 6, 3, k14, brickSlabBlock, brickSlabMeta | 8);
				setBlockAndMetadata(world, 7, 4, k14, brickBlock, brickMeta);
			}
			if (k14 != 1 && k14 != 2 && k14 != 4 && k14 != 5 && (k14 < 9 || k14 > 12)) {
				continue;
			}
			setBlockAndMetadata(world, -5, 2, k14, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -6, 3, k14, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 5, 2, k14, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 6, 3, k14, plankSlabBlock, plankSlabMeta);
		}
		for (k14 = -2; k14 <= -1; ++k14) {
			setBlockAndMetadata(world, -1, 3, k14, Blocks.wool, 14);
			setBlockAndMetadata(world, 0, 3, k14, Blocks.wool, 0);
			setBlockAndMetadata(world, 1, 3, k14, Blocks.wool, 14);
		}
		setBlockAndMetadata(world, 0, 1, -1, doorBlock, 3);
		setBlockAndMetadata(world, 0, 2, -1, doorBlock, 8);
		setBlockAndMetadata(world, 0, 0, 0, floorBlock, floorMeta);
		setAir(world, 0, 1, 0);
		setAir(world, 0, 2, 0);
		for (k14 = 0; k14 <= 13; ++k14) {
			for (i13 = -5; i13 <= 5; ++i13) {
				i2 = Math.abs(i13);
				if (i2 >= 1 && i2 <= 3 && (k14 >= 2 && k14 <= 6 || k14 >= 8 && k14 <= 11)) {
					setBlockAndMetadata(world, i13, 4, k14, plankSlabBlock, plankSlabMeta | 8);
					continue;
				}
				setBlockAndMetadata(world, i13, 4, k14, plankBlock, plankMeta);
			}
			for (i13 = -6; i13 <= 6; ++i13) {
				setBlockAndMetadata(world, i13, 5, k14, roofBlock, roofMeta);
			}
			for (i13 = -5; i13 <= 5; ++i13) {
				setBlockAndMetadata(world, i13, 6, k14, roofBlock, roofMeta);
			}
		}
		for (int k171 : new int[]{-1, 14}) {
			int i16;
			for (i16 = -6; i16 <= 6; ++i16) {
				setBlockAndMetadata(world, i16, 5, k171, plankBlock, plankMeta);
			}
			for (i16 = -5; i16 <= 5; ++i16) {
				setBlockAndMetadata(world, i16, 6, k171, plankBlock, plankMeta);
			}
		}
		int[] k18 = {-2, 15};
		i13 = k18.length;
		for (i2 = 0; i2 < i13; ++i2) {
			k17 = k18[i2];
			setBlockAndMetadata(world, -6, 5, k17, roofStairBlock, 4);
			setBlockAndMetadata(world, -5, 6, k17, roofStairBlock, 4);
			setBlockAndMetadata(world, 5, 6, k17, roofStairBlock, 5);
			setBlockAndMetadata(world, 6, 5, k17, roofStairBlock, 5);
			for (int i17 : new int[]{-3, 0, 3}) {
				for (int j13 = 5; j13 <= 6; ++j13) {
					setBlockAndMetadata(world, i17, j13, k17, brickBlock, brickMeta);
				}
			}
		}
		for (int k19 = -2; k19 <= 15; ++k19) {
			setBlockAndMetadata(world, -7, 5, k19, roofStairBlock, 1);
			setBlockAndMetadata(world, -6, 6, k19, roofStairBlock, 1);
			setBlockAndMetadata(world, -5, 7, k19, roofStairBlock, 1);
			for (i13 = -4; i13 <= 4; ++i13) {
				setBlockAndMetadata(world, i13, 7, k19, roofBlock, roofMeta);
			}
			for (i13 = -2; i13 <= 2; ++i13) {
				setBlockAndMetadata(world, i13, 8, k19, roofSlabBlock, roofSlabMeta);
			}
			setBlockAndMetadata(world, 5, 7, k19, roofStairBlock, 0);
			setBlockAndMetadata(world, 6, 6, k19, roofStairBlock, 0);
			setBlockAndMetadata(world, 7, 5, k19, roofStairBlock, 0);
		}
		for (int j14 = 1; j14 <= 9; ++j14) {
			setBlockAndMetadata(world, 0, j14, 11, Blocks.brick_block, 0);
			setBlockAndMetadata(world, -1, j14, 12, Blocks.brick_block, 0);
			setAir(world, 0, j14, 12);
			setBlockAndMetadata(world, 1, j14, 12, Blocks.brick_block, 0);
			setBlockAndMetadata(world, 0, j14, 13, Blocks.brick_block, 0);
		}
		for (int j15 : new int[]{1, 8}) {
			setBlockAndMetadata(world, 0, j15, 12, LOTRMod.hearth, 0);
			setBlockAndMetadata(world, 0, j15 + 1, 12, Blocks.fire, 0);
		}
		setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, -4, 3, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 4, 3, 3, Blocks.torch, 1);
		setBlockAndMetadata(world, -4, 3, 10, Blocks.torch, 2);
		setBlockAndMetadata(world, 4, 3, 10, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 3, 12, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, 12, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 4, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
		setBlockAndMetadata(world, 2, 4, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);
		for (i1 = -4; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 1, 7, brickBlock, brickMeta);
			setBlockAndMetadata(world, i1, 3, 7, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -1, 1, 7, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 1, 7, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 1, 1, 7, brickStairBlock, 5);
		setBlockAndMetadata(world, 3, 1, 7, fenceGateBlock, 0);
		setBlockAndMetadata(world, -1, 1, 11, Blocks.furnace, 2);
		setBlockAndMetadata(world, 0, 1, 11, Blocks.brick_stairs, 2);
		setBlockAndMetadata(world, 1, 1, 11, Blocks.furnace, 2);
		setBlockAndMetadata(world, -1, 2, 11, Blocks.furnace, 2);
		setBlockAndMetadata(world, 0, 2, 11, barsBlock, 0);
		setBlockAndMetadata(world, 1, 2, 11, Blocks.furnace, 2);
		setBlockAndMetadata(world, -1, 3, 11, Blocks.brick_block, 0);
		setBlockAndMetadata(world, 1, 3, 11, Blocks.brick_block, 0);
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k12 = 10; k12 <= 12; ++k12) {
				setBlockAndMetadata(world, i1, 4, k12, Blocks.brick_block, 0);
			}
		}
		setBlockAndMetadata(world, -2, 1, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 1, 1, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -4, 1, 1, plankBlock, plankMeta);
		for (k15 = 2; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, -4, 1, k15, plankSlabBlock, plankSlabMeta | 8);
		}
		setBlockAndMetadata(world, -4, 1, 6, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 1, 6, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -2, 1, 6, plankBlock, plankMeta);
		for (k15 = 1; k15 <= 6; ++k15) {
			placeRandomCake(world, random, -4, 2, k15);
		}
		for (i1 = -3; i1 <= -2; ++i1) {
			placeRandomCake(world, random, i1, 2, 1);
			placeRandomCake(world, random, i1, 2, 6);
		}
		setBlockAndMetadata(world, 2, 1, 3, plankStairBlock, 7);
		setBlockAndMetadata(world, 2, 1, 4, plankStairBlock, 6);
		placeRandomCake(world, random, 2, 2, 3);
		placeRandomCake(world, random, 2, 2, 4);
		setBlockAndMetadata(world, 4, 1, 1, plankBlock, plankMeta);
		for (k15 = 2; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, 4, 1, k15, plankSlabBlock, plankSlabMeta | 8);
		}
		setBlockAndMetadata(world, 4, 1, 6, plankBlock, plankMeta);
		for (k15 = 1; k15 <= 6; ++k15) {
			placeRandomCake(world, random, 4, 2, k15);
		}
		for (i1 = -4; i1 <= -3; ++i1) {
			setBlockAndMetadata(world, i1, 1, 8, plankBlock, plankMeta);
			for (k12 = 9; k12 <= 11; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, plankSlabBlock, plankSlabMeta | 8);
			}
			setBlockAndMetadata(world, i1, 1, 12, plankBlock, plankMeta);
			for (k12 = 8; k12 <= 12; ++k12) {
				placeRandomCake(world, random, i1, 2, k12);
			}
		}
		setBlockAndMetadata(world, 4, 1, 8, plankBlock, plankMeta);
		placeRandomCake(world, random, 4, 2, 8);
		setBlockAndMetadata(world, 4, 1, 9, Blocks.cauldron, 3);
		setBlockAndMetadata(world, 4, 1, 10, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 4, 1, 11, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 4, 1, 12, plankBlock, plankMeta);
		placeRandomCake(world, random, 4, 2, 12);
		setBlockAndMetadata(world, 3, 1, 12, plankSlabBlock, plankSlabMeta | 8);
		placeRandomCake(world, random, 3, 2, 12);
		setBlockAndMetadata(world, 2, 1, 12, plankBlock, plankMeta);
		placeBarrel(world, random, 2, 2, 12, 2, LOTRFoods.DALE_DRINK);
		spawnItemFrame(world, 5, 3, 9, 3, new ItemStack(Items.clock));
		LOTREntityDaleBaker baker = new LOTREntityDaleBaker(world);
		spawnNPCAndSetHome(baker, world, 0, 1, 8, 8);
		setBlockAndMetadata(world, 0, 5, -3, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 0, 6, -3, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, 0, 6, -4, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, 0, 5, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, -4, plankBlock, plankMeta);
		String[] bakeryName = LOTRNames.getDaleBakeryName(random, baker.getNPCName());
		baker.setSpecificLocationName(bakeryName[0] + " " + bakeryName[1]);
		setBlockAndMetadata(world, -1, 4, -4, Blocks.wall_sign, 5);
		setBlockAndMetadata(world, 1, 4, -4, Blocks.wall_sign, 4);
		for (int i18 : new int[]{-1, 1}) {
			TileEntity te = getTileEntity(world, i18, 4, -4);
			if (!(te instanceof TileEntitySign)) {
				continue;
			}
			TileEntitySign sign = (TileEntitySign) te;
			sign.signText[1] = bakeryName[0];
			sign.signText[2] = bakeryName[1];
		}
		return true;
	}

	public void placeRandomCake(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			Block cakeBlock = null;
			if (random.nextBoolean()) {
				cakeBlock = LOTRMod.dalishPastry;
			} else {
				int randomCake = random.nextInt(4);
				switch (randomCake) {
					case 0:
						cakeBlock = Blocks.cake;
						break;
					case 1:
						cakeBlock = LOTRMod.appleCrumble;
						break;
					case 2:
						cakeBlock = LOTRMod.berryPie;
						break;
					case 3:
						cakeBlock = LOTRMod.marzipanBlock;
						break;
					default:
						break;
				}
			}
			setBlockAndMetadata(world, i, j, k, cakeBlock, 0);
		}
	}
}
