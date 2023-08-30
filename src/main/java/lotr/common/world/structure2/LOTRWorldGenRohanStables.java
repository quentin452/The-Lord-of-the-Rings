package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanStables extends LOTRWorldGenRohanStructure {
	public LOTRWorldGenRohanStables(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int i12;
		int k1;
		int i2;
		int k2;
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -8; i12 <= 8; ++i12) {
				for (int k12 = -1; k12 <= 26; ++k12) {
					j1 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j1, k12)) {
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
		for (int i13 = -7; i13 <= 7; ++i13) {
			for (k1 = 0; k1 <= 26; ++k1) {
				int dz;
				int hayDist;
				int dx;
				i2 = Math.abs(i13);
				k2 = IntMath.mod(k1, 4);
				if (k1 <= 12) {
					for (j1 = 0; (j1 >= 0 || !isOpaque(world, i13, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i13, j1, k1, brickBlock, brickMeta);
						setGrassToDirt(world, i13, j1 - 1, k1);
					}
					for (j1 = 1; j1 <= 7; ++j1) {
						setAir(world, i13, j1, k1);
					}
				} else {
					setBlockAndMetadata(world, i13, 0, k1, Blocks.grass, 0);
					j1 = -1;
					while (!isOpaque(world, i13, j1, k1) && getY(j1) >= 0) {
						int randomGround = random.nextInt(4);
						switch (randomGround) {
							case 0:
								setBlockAndMetadata(world, i13, j1, k1, Blocks.dirt, 0);
								break;
							case 1:
								setBlockAndMetadata(world, i13, j1, k1, Blocks.gravel, 0);
								break;
							case 2:
								setBlockAndMetadata(world, i13, j1, k1, cobbleBlock, cobbleMeta);
								break;
							case 3:
								setBlockAndMetadata(world, i13, j1, k1, rockBlock, rockMeta);
								break;
							default:
								break;
						}
						setGrassToDirt(world, i13, j1 - 1, k1);
						--j1;
					}
					for (j1 = 1; j1 <= 6; ++j1) {
						setAir(world, i13, j1, k1);
					}
				}
				if (k1 >= 0 && k1 <= 12) {
					if (k1 >= 1 && k1 <= 11) {
						if (i2 >= 1 && i2 <= 2) {
							setBlockAndMetadata(world, i13, 0, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
						}
						if (i2 <= 2 && random.nextBoolean()) {
							setBlockAndMetadata(world, i13, 1, k1, LOTRMod.thatchFloor, 0);
						}
					}
					if (i2 == 7 && k2 != 0) {
						setBlockAndMetadata(world, i13, 1, k1, plankBlock, plankMeta);
						setBlockAndMetadata(world, i13, 2, k1, fenceBlock, fenceMeta);
						if (k2 == 2) {
							setBlockAndMetadata(world, i13, 3, k1, brickSlabBlock, brickSlabMeta | 8);
						} else {
							setBlockAndMetadata(world, i13, 3, k1, brickBlock, brickMeta);
						}
					} else if ((k1 == 0 || k1 == 12) && i2 >= 4 && i2 <= 6) {
						setBlockAndMetadata(world, i13, 1, k1, plankBlock, plankMeta);
						setBlockAndMetadata(world, i13, 2, k1, fenceBlock, fenceMeta);
						if (i2 == 5) {
							setBlockAndMetadata(world, i13, 3, k1, brickSlabBlock, brickSlabMeta | 8);
						} else {
							setBlockAndMetadata(world, i13, 3, k1, brickBlock, brickMeta);
						}
					}
					if (i2 >= 3 && i2 <= 6 && k1 >= 1 && k1 <= 11) {
						if (k2 == 0) {
							if (i2 >= 4) {
								setBlockAndMetadata(world, i13, 1, k1, plankBlock, plankMeta);
								setBlockAndMetadata(world, i13, 2, k1, fenceBlock, fenceMeta);
							}
						} else {
							if (i2 >= 4) {
								setBlockAndMetadata(world, i13, 0, k1, Blocks.dirt, 1);
								if (i2 == 6) {
									if (k2 == 3) {
										setBlockAndMetadata(world, i13, 1, k1, Blocks.cauldron, 3);
									} else {
										setBlockAndMetadata(world, i13, 1, k1, Blocks.hay_block, 0);
									}
								} else {
									setBlockAndMetadata(world, i13, 1, k1, LOTRMod.thatchFloor, 0);
								}
							}
							if (i2 == 3) {
								if (k2 == 1) {
									setBlockAndMetadata(world, i13, 1, k1, fenceBlock, fenceMeta);
								} else {
									setBlockAndMetadata(world, i13, 1, k1, fenceGateBlock, i13 > 0 ? 3 : 1);
								}
								if (k2 == 2) {
									setBlockAndMetadata(world, i13, 4, k1, brickSlabBlock, brickSlabMeta);
								} else {
									setBlockAndMetadata(world, i13, 3, k1, brickSlabBlock, brickSlabMeta | 8);
								}
							}
						}
						if (k2 == 2 && i2 == 5) {
							LOTREntityHorse horse = new LOTREntityHorse(world);
							spawnNPCAndSetHome(horse, world, i13, 1, k1, 0);
							horse.setHorseType(0);
							horse.saddleMountForWorldGen();
							horse.detachHome();
						}
					}
					if (k1 == 0 && i2 >= 1 && i2 <= 2) {
						for (j1 = 1; j1 <= 3; ++j1) {
							setBlockAndMetadata(world, i13, j1, 0, gateBlock, 2);
						}
						setBlockAndMetadata(world, i13, 4, 0, plank2SlabBlock, plank2SlabMeta);
					}
					if (k1 != 12 || i2 < 1 || i2 > 2) {
						continue;
					}
					setBlockAndMetadata(world, i13, 1, k1, fenceGateBlock, 0);
					setBlockAndMetadata(world, i13, 3, k1, brickSlabBlock, brickSlabMeta | 8);
					setBlockAndMetadata(world, i13, 4, k1, plank2SlabBlock, plank2SlabMeta);
					continue;
				}
				if (i2 == 7 || k1 == 26) {
					boolean beam = k1 == 19;
					if (k1 == 26 && i2 % 7 == 0) {
						beam = true;
					}
					if (beam) {
						for (int j12 = 1; j12 <= 2; ++j12) {
							setBlockAndMetadata(world, i13, j12, k1, woodBeamBlock, woodBeamMeta);
						}
						setGrassToDirt(world, i13, 0, k1);
						setBlockAndMetadata(world, i13, 3, k1, fenceBlock, fenceMeta);
						setBlockAndMetadata(world, i13, 4, k1, Blocks.torch, 5);
						continue;
					}
					setBlockAndMetadata(world, i13, 1, k1, fenceBlock, fenceMeta);
					continue;
				}
				if (random.nextInt(3) == 0) {
					plantTallGrass(world, random, i13, 1, k1);
				}
				dx = i13;
				dz = k1 - 20;
				hayDist = 1 + random.nextInt(3);
				if (dx * dx + dz * dz < hayDist * hayDist && random.nextInt(3) != 0) {
					int hayHeight = 1 + random.nextInt(3);
					for (int j13 = 1; j13 <= hayHeight; ++j13) {
						setBlockAndMetadata(world, i13, j13, k1, Blocks.hay_block, 0);
					}
					setGrassToDirt(world, i13, 0, k1);
				}
				if (i2 != 4 || k1 != 23) {
					continue;
				}
				int horses = 2 + random.nextInt(3);
				for (int l = 0; l < horses; ++l) {
					LOTREntityHorse horse = new LOTREntityHorse(world);
					spawnNPCAndSetHome(horse, world, i13, 1, k1, 0);
					horse.setHorseType(0);
					horse.setGrowingAge(0);
					horse.detachHome();
				}
			}
		}
		for (int k13 = 0; k13 <= 12; ++k13) {
			for (int step = 0; step <= 7; ++step) {
				i12 = 8 - step;
				int j14 = 4 + step / 2;
				Block block = roofSlabBlock;
				int meta = roofSlabMeta;
				if (k13 == 6) {
					block = plank2SlabBlock;
					meta = plank2SlabMeta;
				}
				if (step % 2 == 1) {
					meta |= 8;
				}
				setBlockAndMetadata(world, -i12, j14, k13, block, meta);
				setBlockAndMetadata(world, i12, j14, k13, block, meta);
				if (step < 2) {
					continue;
				}
				block = plankSlabBlock;
				meta = plankSlabMeta;
				if (step % 2 == 1) {
					meta |= 8;
				}
				setBlockAndMetadata(world, -i12, j14 - 1, k13, block, meta);
				setBlockAndMetadata(world, i12, j14 - 1, k13, block, meta);
			}
		}
		for (int k12 : new int[]{-1, 13}) {
			for (int step = 0; step <= 7; ++step) {
				int i14 = 8 - step;
				int j15 = 4 + step / 2;
				if (step % 2 == 0) {
					setBlockAndMetadata(world, -i14, j15, k12, plank2SlabBlock, plank2SlabMeta);
					setBlockAndMetadata(world, -i14, j15 - 1, k12, plank2SlabBlock, plank2SlabMeta | 8);
					setBlockAndMetadata(world, i14, j15, k12, plank2SlabBlock, plank2SlabMeta);
					setBlockAndMetadata(world, i14, j15 - 1, k12, plank2SlabBlock, plank2SlabMeta | 8);
					continue;
				}
				setBlockAndMetadata(world, -i14, j15, k12, plank2Block, plank2Meta);
				setBlockAndMetadata(world, i14, j15, k12, plank2Block, plank2Meta);
			}
		}
		for (int k14 = -2; k14 <= 14; ++k14) {
			setBlockAndMetadata(world, 0, 7, k14, logBlock, logMeta | 8);
			setBlockAndMetadata(world, 0, 8, k14, plank2SlabBlock, plank2SlabMeta);
		}
		for (int k12 : new int[]{-1, 6, 13}) {
			setBlockAndMetadata(world, -1, 8, k12, plank2StairBlock, 5);
			setBlockAndMetadata(world, 1, 8, k12, plank2StairBlock, 4);
		}
		for (int k12 : new int[]{0, 12}) {
			setBlockAndMetadata(world, -5, 4, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -4, 4, k12, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 4, 4, k12, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 5, 4, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -2, 5, k12, plankStairBlock, 4);
			setBlockAndMetadata(world, -1, 5, k12, plankStairBlock, 5);
			setBlockAndMetadata(world, -2, 6, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 6, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 1, 5, k12, plankStairBlock, 4);
			setBlockAndMetadata(world, 2, 5, k12, plankStairBlock, 5);
			setBlockAndMetadata(world, 1, 6, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 2, 6, k12, plankBlock, plankMeta);
		}
		for (i1 = -7; i1 <= 7; ++i1) {
			for (k1 = 0; k1 <= 12; ++k1) {
				i2 = Math.abs(i1);
				k2 = IntMath.mod(k1, 4);
				if ((i2 == 0 || i2 == 3 || i2 == 7) && k2 == 0) {
					if (i2 == 0 && (k1 == 4 || k1 == 8)) {
						for (j1 = 1; j1 <= 2; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, rockWallBlock, rockWallMeta);
						}
					} else {
						for (j1 = 1; j1 <= 2; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
						}
					}
					setBlockAndMetadata(world, i1, 3, k1, brickCarvedBlock, brickCarvedMeta);
					if (i2 == 3) {
						for (j1 = 4; j1 <= 5; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
						}
					}
					if (i2 == 0) {
						for (j1 = 4; j1 <= 6; ++j1) {
							setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
						}
					}
				}
				if (k1 < 1 || k1 > 11 || (i2 != 0 || k2 == 0) && (i2 < 1 || i2 > 2 || k2 != 0)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k1, plank2SlabBlock, plank2SlabMeta | 8);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			if (IntMath.mod(i1, 3) != 0) {
				continue;
			}
			setBlockAndMetadata(world, i1, 3, -1, Blocks.torch, 4);
			setBlockAndMetadata(world, i1, 3, 13, Blocks.torch, 3);
		}
		for (int k12 : new int[]{4, 8}) {
			setBlockAndMetadata(world, -1, 3, k12, Blocks.torch, 1);
			setBlockAndMetadata(world, 1, 3, k12, Blocks.torch, 2);
		}
		LOTREntityRohanStablemaster stablemaster = new LOTREntityRohanStablemaster(world);
		spawnNPCAndSetHome(stablemaster, world, 0, 1, 6, 8);
		int men = 1 + random.nextInt(3);
		for (int l = 0; l < men; ++l) {
			LOTREntityRohanMan stabler = random.nextBoolean() ? new LOTREntityRohirrimWarrior(world) : new LOTREntityRohanMan(world);
			spawnNPCAndSetHome(stabler, world, 0, 1, 6, 16);
		}
		return true;
	}
}
