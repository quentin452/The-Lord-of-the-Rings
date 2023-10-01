package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenMeadHall extends LOTRWorldGenRohanStructure {
	public String[] meadHallName;
	public String[] meadNameSign;

	public LOTRWorldGenMeadHall(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int i12;
		int i13;
		int j12;
		int step;
		int k1;
		int i2;
		int j13;
		int j14;
		int k12;
		int k13;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -8; i12 <= 8; ++i12) {
				for (int k14 = 0; k14 <= 28; ++k14) {
					j14 = getTopBlock(world, i12, k14) - 1;
					if (!isSurface(world, i12, j14, k14)) {
						return false;
					}
					if (j14 < minHeight) {
						minHeight = j14;
					}
					if (j14 > maxHeight) {
						maxHeight = j14;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (i13 = -8; i13 <= 8; ++i13) {
			for (k1 = 0; k1 <= 28; ++k1) {
				boolean stairSide;
				for (j13 = 1; j13 <= 11; ++j13) {
					setAir(world, i13, j13, k1);
				}
				boolean corner = Math.abs(i13) == 8 && (k1 == 0 || k1 == 28);
				stairSide = Math.abs(i13) == 3 && k1 == 0;
				if (corner || stairSide) {
					for (j14 = 1; (j14 >= 1 || !isOpaque(world, i13, j14, k1)) && getY(j14) >= 0; --j14) {
						setBlockAndMetadata(world, i13, j14, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						setGrassToDirt(world, i13, j14 - 1, k1);
					}
					if (!corner) {
						continue;
					}
					setBlockAndMetadata(world, i13, 2, k1, rockSlabBlock, rockSlabMeta);
					continue;
				}
				for (j14 = 1; (j14 >= 1 || !isOpaque(world, i13, j14, k1)) && getY(j14) >= 0; --j14) {
					setBlockAndMetadata(world, i13, j14, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i13, j14 - 1, k1);
				}
				if (Math.abs(i13) > 4 || k1 < 4 || k1 > 21 || random.nextInt(4) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, 2, k1, LOTRMod.thatchFloor, 0);
			}
		}
		for (i13 = -7; i13 <= 7; ++i13) {
			i2 = Math.abs(i13);
			if (i2 <= 2) {
				setBlockAndMetadata(world, i13, 1, 0, brickStairBlock, 2);
			}
			if (i2 == 3) {
				setBlockAndMetadata(world, i13, 2, 0, rockWallBlock, rockWallMeta);
			}
			if (i2 < 4 || i2 > 7) {
				continue;
			}
			setBlockAndMetadata(world, i13, 2, 0, fenceBlock, fenceMeta);
		}
		for (i13 = -7; i13 <= 7; ++i13) {
			for (k1 = 2; k1 <= 26; ++k1) {
				int i22 = Math.abs(i13);
				if (i22 == 5 && (k1 == 2 || k1 == 26)) {
					for (j12 = 2; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i13, j12, k1, woodBeamRohanBlock, woodBeamRohanMeta);
					}
				} else {
					if (i22 == 5 && k1 >= 3) {
						setBlockAndMetadata(world, i13, 2, k1, brickBlock, brickMeta);
						setBlockAndMetadata(world, i13, 3, k1, plank2Block, plank2Meta);
						setBlockAndMetadata(world, i13, 4, k1, plankBlock, plankMeta);
					}
					if (i22 <= 4 && k1 == 3) {
						for (j12 = 2; j12 <= 4; ++j12) {
							setBlockAndMetadata(world, i13, j12, 3, plankBlock, plankMeta);
						}
						setBlockAndMetadata(world, i13, 5, 3, woodBeamBlock, woodBeamMeta | 4);
					}
					if (i22 <= 4 && k1 == 25) {
						setBlockAndMetadata(world, i13, 2, 25, brickBlock, brickMeta);
						setBlockAndMetadata(world, i13, 3, 25, plank2Block, plank2Meta);
						setBlockAndMetadata(world, i13, 4, 25, plankBlock, plankMeta);
						setBlockAndMetadata(world, i13, 5, 25, woodBeamBlock, woodBeamMeta | 4);
					}
				}
				if (k1 < 3 || k1 > 25) {
					continue;
				}
				if (i22 == 6) {
					if (k1 % 6 == 2) {
						for (j12 = 2; j12 <= 4; ++j12) {
							setBlockAndMetadata(world, i13, j12, k1, woodBeamBlock, woodBeamMeta);
						}
					} else {
						if (k1 == 3 || k1 == 25) {
							setBlockAndMetadata(world, i13, 2, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						} else {
							setBlockAndMetadata(world, i13, 2, k1, rockSlabBlock, rockSlabMeta | 8);
						}
						if (k1 % 6 == 3 || k1 % 6 == 1) {
							setBlockAndMetadata(world, i13, 4, k1, plank2SlabBlock, plank2SlabMeta | 8);
						}
						if (random.nextInt(5) == 0) {
							placeFlowerPot(world, i13, 3, k1, getRandomFlower(world, random));
						}
					}
				}
				if (i22 != 5 || k1 % 6 != 5) {
					continue;
				}
				setBlockAndMetadata(world, i13, 3, k1, plank2StairBlock, i13 > 0 ? 0 : 1);
				setBlockAndMetadata(world, i13, 4, k1, fenceBlock, fenceMeta);
			}
		}
		for (k12 = 3; k12 <= 25; ++k12) {
			for (step = 0; step <= 5; ++step) {
				i12 = 1 + step;
				j12 = 7 - step / 2;
				Block block = roofSlabBlock;
				int meta = roofSlabMeta;
				if (k12 == 3 || k12 == 25) {
					block = plankSlabBlock;
					meta = plankSlabMeta;
				}
				if (step % 2 == 0) {
					meta |= 8;
				}
				setBlockAndMetadata(world, -i12, j12, k12, block, meta);
				setBlockAndMetadata(world, i12, j12, k12, block, meta);
			}
		}
		for (k12 = 2; k12 <= 26; ++k12) {
			setBlockAndMetadata(world, 0, 7, k12, logBlock, logMeta | 8);
			setBlockAndMetadata(world, 0, 8, k12, plank2SlabBlock, plank2SlabMeta);
			if (k12 % 12 != 2) {
				continue;
			}
			for (step = 0; step <= 6; ++step) {
				i12 = 1 + step;
				j12 = 8 - (step + 1) / 2;
				for (int i23 : new int[]{-i12, i12}) {
					if (step % 2 == 0) {
						setBlockAndMetadata(world, i23, j12, k12, plank2SlabBlock, plank2SlabMeta);
						setBlockAndMetadata(world, i23, j12 - 1, k12, plank2SlabBlock, plank2SlabMeta | 8);
						continue;
					}
					setBlockAndMetadata(world, i23, j12, k12, plank2Block, plank2Meta);
				}
			}
			setBlockAndMetadata(world, 0, 8, k12, plank2Block, plank2Meta);
			setBlockAndMetadata(world, 0, 9, k12, plank2SlabBlock, plank2SlabMeta);
			setBlockAndMetadata(world, -1, 9, k12, plank2StairBlock, 5);
			setBlockAndMetadata(world, 1, 9, k12, plank2StairBlock, 4);
			for (j1 = 2; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, -7, j1, k12, fence2Block, fence2Meta);
				setBlockAndMetadata(world, 7, j1, k12, fence2Block, fence2Meta);
			}
			if (k12 != 2 && k12 != 26) {
				continue;
			}
			setBlockAndMetadata(world, -6, 4, k12, fence2Block, fence2Meta);
			setBlockAndMetadata(world, 6, 4, k12, fence2Block, fence2Meta);
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			i2 = Math.abs(i13);
			if (i2 == 5) {
				setBlockAndMetadata(world, i13, 5, 3, plankBlock, plankMeta);
			}
			if (i2 >= 2 && i2 <= 3) {
				setBlockAndMetadata(world, i13, 6, 3, plankBlock, plankMeta);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i13, 6, 3, plankStairBlock, i13 > 0 ? 5 : 4);
			}
			if (i2 != 1) {
				continue;
			}
			setBlockAndMetadata(world, i13, 7, 3, plankBlock, plankMeta);
		}
		for (i13 = -5; i13 <= 5; ++i13) {
			i2 = Math.abs(i13);
			if (i2 == 5) {
				setBlockAndMetadata(world, i13, 5, 25, plankBlock, plankMeta);
			}
			if (i2 == 3) {
				setBlockAndMetadata(world, i13, 6, 25, plankBlock, plankMeta);
			}
			if (i2 == 2) {
				for (j13 = 2; j13 <= 6; ++j13) {
					setBlockAndMetadata(world, i13, j13, 25, woodBeamRohanBlock, woodBeamRohanMeta);
				}
			}
			if (i2 <= 1) {
				for (j13 = 2; j13 <= 8; ++j13) {
					if (j13 == 5) {
						continue;
					}
					setBlockAndMetadata(world, i13, j13, 25, brickBlock, brickMeta);
				}
			}
			if (i2 == 0) {
				for (j13 = 9; j13 <= 11; ++j13) {
					setBlockAndMetadata(world, i13, j13, 25, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i13, 12, 25, rockSlabBlock, rockSlabMeta);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i13, 9, 25, brickStairBlock, i13 > 0 ? 0 : 1);
			}
			if (i2 <= 2) {
				for (int k15 = 23; k15 <= 24; ++k15) {
					for (j12 = 2; j12 <= 6; ++j12) {
						setBlockAndMetadata(world, i13, j12, k15, brickBlock, brickMeta);
					}
				}
			}
			if (i2 <= 1) {
				setBlockAndMetadata(world, i13, 7, 24, brickBlock, brickMeta);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i13, 8, 24, brickStairBlock, i13 > 0 ? 0 : 1);
			}
			if (i2 == 0) {
				setBlockAndMetadata(world, i13, 8, 24, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, 9, 24, brickStairBlock, 2);
			}
			if (i2 >= 3 && i2 <= 4) {
				for (j13 = 2; j13 <= 5; ++j13) {
					setBlockAndMetadata(world, i13, j13, 24, brickBlock, brickMeta);
				}
			}
			if (i2 <= 1) {
				setBlockAndMetadata(world, i13, 2, 26, brickBlock, brickMeta);
			}
			if (i2 == 1) {
				setBlockAndMetadata(world, i13, 3, 26, brickStairBlock, i13 > 0 ? 0 : 1);
			}
			if (i2 != 0) {
				continue;
			}
			setBlockAndMetadata(world, i13, 3, 26, brickBlock, brickMeta);
		}
		for (int k14 : new int[]{2, 26}) {
			for (int i14 = -5; i14 <= 5; ++i14) {
				int i24 = Math.abs(i14);
				if (i24 == 2 || i24 == 5) {
					setBlockAndMetadata(world, i14, 5, k14, woodBeamRohanBlock, woodBeamRohanMeta | 8);
				}
				if (i24 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i14, 5, k14, plank2SlabBlock, plank2SlabMeta | 8);
			}
		}
		for (int i15 : new int[]{-4, 3}) {
			setBlockAndMetadata(world, i15, 2, 2, plank2StairBlock, 4);
			setBlockAndMetadata(world, i15 + 1, 2, 2, plank2StairBlock, 5);
			for (int i25 = i15; i25 <= i15 + 1; ++i25) {
				if (!random.nextBoolean()) {
					continue;
				}
				placeFlowerPot(world, i25, 3, 2, getRandomFlower(world, random));
			}
		}
		setBlockAndMetadata(world, 0, 2, 3, doorBlock, 1);
		setBlockAndMetadata(world, 0, 3, 3, doorBlock, 8);
		setBlockAndMetadata(world, -1, 3, 2, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, 2, Blocks.torch, 4);
		placeSign(world, 0, 4, 2, Blocks.wall_sign, 2, meadNameSign);
		for (int i15 : new int[]{-2, 2}) {
			for (j14 = 2; j14 <= 4; ++j14) {
				setBlockAndMetadata(world, i15, j14, 3, woodBeamRohanGoldBlock, woodBeamRohanGoldMeta);
			}
		}
		for (int i15 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, i15, 3, 3, plankStairBlock, 2);
			setBlockAndMetadata(world, i15, 4, 3, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 0, 4, 4, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 5, 4, woodBeamBlock, woodBeamMeta | 8);
		setBlockAndMetadata(world, 2, 5, 4, woodBeamBlock, woodBeamMeta | 8);
		for (k13 = 4; k13 <= 24; ++k13) {
			setBlockAndMetadata(world, -5, 5, k13, roofBlock, roofMeta);
			setBlockAndMetadata(world, 5, 5, k13, roofBlock, roofMeta);
			if (k13 > 23) {
				continue;
			}
			if (k13 % 6 == 2) {
				for (j1 = 2; j1 <= 5; ++j1) {
					setBlockAndMetadata(world, -4, j1, k13, woodBeamRohanBlock, woodBeamRohanMeta);
					setBlockAndMetadata(world, 4, j1, k13, woodBeamRohanBlock, woodBeamRohanMeta);
				}
				setBlockAndMetadata(world, -3, 5, k13, plank2SlabBlock, plank2SlabMeta | 8);
				setBlockAndMetadata(world, 3, 5, k13, plank2SlabBlock, plank2SlabMeta | 8);
				setBlockAndMetadata(world, -2, 6, k13, plank2SlabBlock, plank2SlabMeta);
				setBlockAndMetadata(world, 2, 6, k13, plank2SlabBlock, plank2SlabMeta);
				setBlockAndMetadata(world, -1, 6, k13, plank2SlabBlock, plank2SlabMeta | 8);
				setBlockAndMetadata(world, 1, 6, k13, plank2SlabBlock, plank2SlabMeta | 8);
				setBlockAndMetadata(world, 0, 6, k13, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, 0, 5, k13, LOTRMod.chandelier, 1);
			} else {
				setBlockAndMetadata(world, -4, 5, k13, plankSlabBlock, plankSlabMeta);
			}
			if (k13 % 6 == 4 || k13 % 6 == 0) {
				setBlockAndMetadata(world, -4, 4, k13, Blocks.torch, 2);
				setBlockAndMetadata(world, 4, 4, k13, Blocks.torch, 1);
			}
			if (k13 % 6 != 1 && k13 % 6 != 3) {
				continue;
			}
			spawnItemFrame(world, -5, 3, k13, 1, getRohanFramedItem(random));
			spawnItemFrame(world, 5, 3, k13, 3, getRohanFramedItem(random));
		}
		for (k13 = 9; k13 <= 19; ++k13) {
			for (int i16 = -1; i16 <= 1; ++i16) {
				if (k13 % 5 == 4 && Math.abs(i16) == 1) {
					setBlockAndMetadata(world, i16, 2, k13, plank2Block, plank2Meta);
				} else {
					setBlockAndMetadata(world, i16, 2, k13, plank2SlabBlock, plank2SlabMeta | 8);
				}
				if (i16 == 0 && random.nextBoolean()) {
					if (random.nextBoolean()) {
						placeBarrel(world, random, 0, 3, k13, random.nextBoolean() ? 4 : 5, LOTRFoods.ROHAN_DRINK);
						continue;
					}
					setBlockAndMetadata(world, 0, 3, k13, getRandomCakeBlock(random), 0);
					continue;
				}
				if (random.nextInt(3) == 0) {
					placeMug(world, random, i16, 3, k13, random.nextInt(4), LOTRFoods.ROHAN_DRINK);
					continue;
				}
				placePlate(world, random, i16, 3, k13, plateBlock, LOTRFoods.ROHAN);
			}
		}
		for (k13 = 8; k13 <= 20; ++k13) {
			if (k13 % 2 != 0) {
				continue;
			}
			setBlockAndMetadata(world, -3, 2, k13, plankStairBlock, 0);
			setBlockAndMetadata(world, 3, 2, k13, plankStairBlock, 1);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = 6; k1 <= 7; ++k1) {
				setBlockAndMetadata(world, i1, 2, k1, carpetBlock, carpetMeta);
			}
		}
		setBlockAndMetadata(world, -2, 2, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 2, 4, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -4, 2, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, -4, 2, 5, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -4, 2, 6, plankBlock, plankMeta);
		placeMug(world, random, -2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
		placeBarrel(world, random, -3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
		placeBarrel(world, random, -4, 3, 5, 4, LOTRFoods.ROHAN_DRINK);
		placeMug(world, random, -4, 3, 6, 3, LOTRFoods.ROHAN_DRINK);
		setBlockAndMetadata(world, 2, 2, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 2, 4, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 4, 2, 4, plankBlock, plankMeta);
		setBlockAndMetadata(world, 4, 2, 5, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 4, 2, 6, plankBlock, plankMeta);
		placeMug(world, random, 2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
		placeBarrel(world, random, 3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
		placeBarrel(world, random, 4, 3, 5, 5, LOTRFoods.ROHAN_DRINK);
		placeMug(world, random, 4, 3, 6, 1, LOTRFoods.ROHAN_DRINK);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, 24, LOTRMod.hearth, 0);
			setBlockAndMetadata(world, i1, 2, 24, Blocks.fire, 0);
			for (j1 = 3; j1 <= 4; ++j1) {
				setAir(world, i1, j1, 24);
			}
			for (j1 = 2; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, i1, j1, 23, barsBlock, 0);
			}
		}
		setBlockAndMetadata(world, -3, 6, 24, roofBlock, roofMeta);
		setBlockAndMetadata(world, 3, 6, 24, roofBlock, roofMeta);
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = 22; k1 <= 23; ++k1) {
				setBlockAndMetadata(world, i1, 1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
			}
		}
		setBlockAndMetadata(world, -3, 4, 23, Blocks.torch, 4);
		setBlockAndMetadata(world, 3, 4, 23, Blocks.torch, 4);
		placeWallBanner(world, -2, 5, 23, bannerType, 2);
		placeWallBanner(world, 2, 5, 23, bannerType, 2);
		setBlockAndMetadata(world, -1, 5, 23, brickCarvedBlock, brickCarvedMeta);
		setBlockAndMetadata(world, 1, 5, 23, brickCarvedBlock, brickCarvedMeta);
		placeWeaponRack(world, 0, 5, 22, 6, getRandomRohanWeapon(random));
		LOTREntityRohanMeadhost meadhost = new LOTREntityRohanMeadhost(world);
		spawnNPCAndSetHome(meadhost, world, 0, 2, 21, 8);
		int men = 5 + random.nextInt(5);
		for (int l = 0; l < men; ++l) {
			int i15;
			i15 = random.nextBoolean() ? -2 : 2;
			j14 = 2;
			int k16 = MathHelper.getRandomIntegerInRange(random, 8, 20);
			LOTREntityRohanMan rohirrim = random.nextBoolean() ? new LOTREntityRohanMan(world) : new LOTREntityRohirrimWarrior(world);
			spawnNPCAndSetHome(rohirrim, world, i15, j14, k16, 16);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityRohanShieldmaiden.class);
		respawner.setCheckRanges(32, -12, 12, 2);
		respawner.setSpawnRanges(4, -2, 4, 16);
		respawner.setSpawnIntervalMinutes(5);
		respawner.setNoPlayerRange(8);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	@Override
	public ItemStack getRohanFramedItem(Random random) {
		if (random.nextBoolean()) {
			return LOTRFoods.ROHAN_DRINK.getRandomVessel(random).getEmptyVessel();
		}
		return super.getRohanFramedItem(random);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		meadHallName = LOTRNames.getRohanMeadHallName(random);
		meadNameSign = new String[]{"", meadHallName[0], meadHallName[1], ""};
	}
}
