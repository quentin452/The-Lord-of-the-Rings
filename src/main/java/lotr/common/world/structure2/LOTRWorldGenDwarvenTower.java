package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDwarfAxeThrower;
import lotr.common.entity.npc.LOTREntityDwarfCommander;
import lotr.common.entity.npc.LOTREntityDwarfWarrior;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenDwarvenTower extends LOTRWorldGenStructureBase2 {
	public Block brickBlock = LOTRMod.brick;
	public int brickMeta = 6;
	public Block brickSlabBlock = LOTRMod.slabSingle;
	public int brickSlabMeta = 7;
	public Block brickStairBlock = LOTRMod.stairsDwarvenBrick;
	public Block brickWallBlock = LOTRMod.wall;
	public int brickWallMeta = 7;
	public Block pillarBlock = LOTRMod.pillar;
	public int pillarMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block barsBlock = LOTRMod.dwarfBars;
	public Block gateBlock = LOTRMod.gateDwarven;
	public Block tableBlock = LOTRMod.dwarvenTable;
	public Block forgeBlock = LOTRMod.dwarvenForge;
	public Block glowBrickBlock = LOTRMod.brick3;
	public int glowBrickMeta = 12;
	public Block plateBlock;
	public LOTRItemBanner.BannerType bannerType = LOTRItemBanner.BannerType.DWARF;
	public LOTRChestContents chestContents = LOTRChestContents.DWARVEN_TOWER;
	public boolean ruined;

	public LOTRWorldGenDwarvenTower(boolean flag) {
		super(flag);
	}

	public void generateCornerPillars(World world, Random random, int i, int j, int k) {
		for (int i1 = i - 1; i1 <= i; ++i1) {
			for (int k1 = k - 1; k1 <= k; ++k1) {
				for (int j1 = j; (j1 == 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (j1 == j - 2) {
						setBlockAndMetadata(world, i1, j1, k1, glowBrickBlock, glowBrickMeta);
						continue;
					}
					placePillar(world, random, i1, j1, k1);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int j12;
		int k1;
		int k12;
		LOTREntityNPC commander;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		int sections = 5 + random.nextInt(3);
		if (restrictions) {
			for (i1 = -6; i1 <= 6; ++i1) {
				for (k1 = -6; k1 <= 6; ++k1) {
					j1 = getTopBlock(world, i1, k1);
					Block block = getBlock(world, i1, j1 - 1, k1);
					if (block == Blocks.grass || block == Blocks.stone || block == Blocks.snow) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				for (j1 = 0; (j1 == 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					placeBrick(world, random, i1, j1, k1);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				boolean flag = true;
				if (ruined) {
					flag = random.nextInt(12) != 0;
				}
				if (!flag) {
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, plankBlock, plankMeta);
			}
		}
		for (int l = 0; l <= sections; ++l) {
			int k13;
			int j13;
			int k14;
			int i12;
			int j14;
			int i13;
			int k2;
			int sectionBase = l * 5;
			for (i13 = -4; i13 <= 4; ++i13) {
				for (j14 = sectionBase + 1; j14 <= sectionBase + 5; ++j14) {
					for (k13 = -4; k13 <= 4; ++k13) {
						setAir(world, i13, j14, k13);
						setAir(world, i13, j14, k13);
					}
				}
			}
			for (j1 = sectionBase + 1; j1 <= sectionBase + 5; ++j1) {
				boolean flag;
				for (int i14 = -5; i14 <= 5; ++i14) {
					for (int k15 : new int[]{-5, 5}) {
						flag = true;
						if (ruined) {
							flag = random.nextInt(20) != 0;
						}
						if (!flag) {
							continue;
						}
						placeBrick(world, random, i14, j1, k15);
					}
				}
				for (k14 = -4; k14 <= 4; ++k14) {
					for (int i15 : new int[]{-5, 5}) {
						flag = true;
						if (ruined) {
							flag = random.nextInt(20) != 0;
						}
						if (!flag) {
							continue;
						}
						placeBrick(world, random, i15, j1, k14);
					}
				}
			}
			placePillar(world, random, -4, sectionBase + 1, -4);
			placePillar(world, random, -4, sectionBase + 2, -4);
			setBlockAndMetadata(world, -4, sectionBase + 3, -4, glowBrickBlock, glowBrickMeta);
			placePillar(world, random, -4, sectionBase + 4, -4);
			placePillar(world, random, -4, sectionBase + 1, 4);
			placePillar(world, random, -4, sectionBase + 2, 4);
			setBlockAndMetadata(world, -4, sectionBase + 3, 4, glowBrickBlock, glowBrickMeta);
			placePillar(world, random, -4, sectionBase + 4, 4);
			placePillar(world, random, 4, sectionBase + 1, -4);
			placePillar(world, random, 4, sectionBase + 2, -4);
			setBlockAndMetadata(world, 4, sectionBase + 3, -4, glowBrickBlock, glowBrickMeta);
			placePillar(world, random, 4, sectionBase + 4, -4);
			placePillar(world, random, 4, sectionBase + 1, 4);
			placePillar(world, random, 4, sectionBase + 2, 4);
			setBlockAndMetadata(world, 4, sectionBase + 3, 4, glowBrickBlock, glowBrickMeta);
			placePillar(world, random, 4, sectionBase + 4, 4);
			for (i13 = -4; i13 <= 4; ++i13) {
				for (k14 = -4; k14 <= 4; ++k14) {
					boolean flag = true;
					if (ruined) {
						flag = random.nextInt(12) != 0;
					}
					if (!flag) {
						continue;
					}
					setBlockAndMetadata(world, i13, sectionBase + 5, k14, plankBlock, plankMeta);
				}
			}
			for (k12 = -2; k12 <= 2; ++k12) {
				for (j14 = sectionBase + 1; j14 <= sectionBase + 4; ++j14) {
					if (Math.abs(k12) < 2 && (j14 == sectionBase + 2 || j14 == sectionBase + 3)) {
						setBlockAndMetadata(world, -5, j14, k12, barsBlock, 0);
						setBlockAndMetadata(world, 5, j14, k12, barsBlock, 0);
						continue;
					}
					placePillar(world, random, -5, j14, k12);
					placePillar(world, random, 5, j14, k12);
				}
			}
			int randomFeature = random.nextInt(5);
			if (l % 2 == 0) {
				for (k14 = -1; k14 <= 4; ++k14) {
					for (i12 = 1; i12 <= 2; ++i12) {
						setAir(world, i12, sectionBase + 5, k14);
						k2 = k14 + 1;
						for (j13 = sectionBase + 1; j13 <= sectionBase + k2; ++j13) {
							placeBrick(world, random, i12, j13, k14);
						}
						if (k2 >= 5) {
							continue;
						}
						placeBrickStair(world, random, i12, sectionBase + k2 + 1, k14, 2);
					}
				}
				placeRandomFeature(world, random, -2, sectionBase + 1, 4, randomFeature, false);
				placeRandomFeature(world, random, -1, sectionBase + 1, 4, randomFeature, false);
				setBlockAndMetadata(world, 0, sectionBase + 1, 4, plankBlock, plankMeta);
				setBlockAndMetadata(world, -3, sectionBase + 1, 4, plankBlock, plankMeta);
				setBlockAndMetadata(world, 0, sectionBase + 2, 4, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, -3, sectionBase + 2, 4, plankSlabBlock, plankSlabMeta);
			} else {
				for (k14 = -4; k14 <= 1; ++k14) {
					for (i12 = -2; i12 <= -1; ++i12) {
						setAir(world, i12, sectionBase + 5, k14);
						k2 = 5 - (k14 + 4);
						for (j13 = sectionBase + 1; j13 <= sectionBase + k2; ++j13) {
							placeBrick(world, random, i12, j13, k14);
						}
						if (k2 >= 5) {
							continue;
						}
						placeBrickStair(world, random, i12, sectionBase + k2 + 1, k14, 3);
					}
				}
				placeRandomFeature(world, random, 2, sectionBase + 1, -4, randomFeature, true);
				placeRandomFeature(world, random, 1, sectionBase + 1, -4, randomFeature, true);
				setBlockAndMetadata(world, 0, sectionBase + 1, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, 3, sectionBase + 1, -4, plankBlock, plankMeta);
				setBlockAndMetadata(world, 0, sectionBase + 2, -4, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, 3, sectionBase + 2, -4, plankSlabBlock, plankSlabMeta);
			}
			if (ruined) {
				continue;
			}
			LOTREntityDwarfWarrior dwarf = random.nextInt(3) == 0 ? new LOTREntityDwarfAxeThrower(world) : new LOTREntityDwarfWarrior(world);
			spawnNPCAndSetHome(dwarf, world, 0, sectionBase + 1, 0, 12);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (j12 = 1; j12 <= 2; ++j12) {
				for (k12 = -4; k12 <= 4; ++k12) {
					setAir(world, i1, (sections + 1) * 5 + j12, k12);
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, -5);
			placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, 5);
		}
		for (int k16 = -4; k16 <= 4; ++k16) {
			placeBrickWall(world, random, -5, (sections + 1) * 5 + 1, k16);
			placeBrickWall(world, random, 5, (sections + 1) * 5 + 1, k16);
		}
		generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, -5);
		generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, 6);
		generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, -5);
		generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, 6);
		for (i1 = -1; i1 <= 1; ++i1) {
			placePillar(world, random, i1, 0, -5);
			for (j12 = 1; j12 <= 4; ++j12) {
				setBlockAndMetadata(world, i1, j12, -5, gateBlock, 2);
			}
		}
		for (int i14 : new int[]{-2, 2}) {
			int j15 = 4;
			while (!isOpaque(world, i14, j15, -6) && getY(j15) >= 0) {
				if (j15 == 3) {
					setBlockAndMetadata(world, i14, 3, -6, glowBrickBlock, glowBrickMeta);
				} else {
					placePillar(world, random, i14, j15, -6);
				}
				setGrassToDirt(world, i14, j15 - 1, -6);
				--j15;
			}
		}
		for (int i16 = -2; i16 <= 2; ++i16) {
			placeBrickSlab(world, random, i16, 5, -6, false);
		}
		if (bannerType != null) {
			placeWallBanner(world, -2, 7, -5, bannerType, 2);
			placeWallBanner(world, 0, 8, -5, bannerType, 2);
			placeWallBanner(world, 2, 7, -5, bannerType, 2);
		}
		commander = getCommanderNPC(world);
		if (commander != null) {
			spawnNPCAndSetHome(commander, world, 0, (sections + 1) * 5 + 1, 0, 16);
			if (sections % 2 == 0) {
				setBlockAndMetadata(world, -3, (sections + 1) * 5 + 1, -3, LOTRMod.commandTable, 0);
			} else {
				setBlockAndMetadata(world, 3, (sections + 1) * 5 + 1, 3, LOTRMod.commandTable, 0);
			}
		}
		placePillar(world, random, -4, (sections + 1) * 5 + 1, 0);
		placePillar(world, random, -4, (sections + 1) * 5 + 2, 0);
		placePillar(world, random, 4, (sections + 1) * 5 + 1, 0);
		placePillar(world, random, 4, (sections + 1) * 5 + 2, 0);
		if (bannerType != null) {
			placeBrick(world, random, -4, (sections + 1) * 5 + 1, 0);
			placeBanner(world, -4, (sections + 1) * 5 + 1, 0, bannerType, 1);
			placeBrick(world, random, 4, (sections + 1) * 5 + 1, 0);
			placeBanner(world, 4, (sections + 1) * 5 + 1, 0, bannerType, 3);
		}
		if (!ruined) {
			LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
			respawner.setSpawnClasses(LOTREntityDwarfWarrior.class, LOTREntityDwarfAxeThrower.class);
			respawner.setCheckRanges(12, -8, 42, 16);
			respawner.setSpawnRanges(4, 1, 41, 12);
			placeNPCRespawner(respawner, world, 0, 0, 0);
		}
		return true;
	}

	public LOTREntityNPC getCommanderNPC(World world) {
		return new LOTREntityDwarfCommander(world);
	}

	public void placeBrick(World world, Random random, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, brickBlock, brickMeta);
	}

	public void placeBrickSlab(World world, Random random, int i, int j, int k, boolean flip) {
		setBlockAndMetadata(world, i, j, k, brickSlabBlock, brickSlabMeta | (flip ? 8 : 0));
	}

	public void placeBrickStair(World world, Random random, int i, int j, int k, int meta) {
		setBlockAndMetadata(world, i, j, k, brickStairBlock, meta);
	}

	public void placeBrickWall(World world, Random random, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, brickWallBlock, brickWallMeta);
	}

	public void placePillar(World world, Random random, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, pillarBlock, pillarMeta);
	}

	public void placeRandomFeature(World world, Random random, int i, int j, int k, int randomFeature, boolean flip) {
		switch (randomFeature) {
			case 0:
				setBlockAndMetadata(world, i, j, k, tableBlock, 0);
				break;
			case 1: {
				setBlockAndMetadata(world, i, j, k, forgeBlock, flip ? 3 : 2);
				TileEntity tileentity = getTileEntity(world, i, j, k);
				if (tileentity instanceof LOTRTileEntityAlloyForge) {
					((IInventory) tileentity).setInventorySlotContents(12, new ItemStack(Items.coal, 1 + random.nextInt(4)));
				}
				break;
			}
			case 2:
				setBlockAndMetadata(world, i, j, k, plankSlabBlock, plankSlabMeta | 8);
				placeChest(world, random, i, j + 1, k, flip ? 3 : 2, chestContents);
				break;
			case 3:
				setBlockAndMetadata(world, i, j, k, plankSlabBlock, plankSlabMeta | 8);
				if (!ruined) {
					placePlateWithCertainty(world, random, i, j + 1, k, plateBlock, LOTRFoods.DWARF);
				}
				break;
			case 4:
				setBlockAndMetadata(world, i, j, k, plankSlabBlock, plankSlabMeta | 8);
				if (!ruined) {
					placeBarrel(world, random, i, j + 1, k, flip ? 3 : 2, LOTRFoods.DWARF_DRINK);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomWood = random.nextInt(4);
		switch (randomWood) {
			case 0:
				plankBlock = Blocks.planks;
				plankMeta = 1;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 1;
				break;
			case 1:
				plankBlock = LOTRMod.planks;
				plankMeta = 13;
				plankSlabBlock = LOTRMod.woodSlabSingle2;
				plankSlabMeta = 5;
				break;
			case 2:
				plankBlock = LOTRMod.planks2;
				plankMeta = 4;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 4;
				break;
			case 3:
				plankBlock = LOTRMod.planks2;
				plankMeta = 3;
				plankSlabBlock = LOTRMod.woodSlabSingle3;
				plankSlabMeta = 3;
				break;
			default:
				break;
		}
		plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
	}
}
