package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRWorldGenWargPitBase extends LOTRWorldGenStructureBase2 {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block beamBlock;
	public int beamMeta;
	public Block doorBlock;
	public Block woolBlock;
	public int woolMeta;
	public Block carpetBlock;
	public int carpetMeta;
	public Block barsBlock;
	public Block gateOrcBlock;
	public Block gateMetalBlock;
	public Block tableBlock;
	public Block bedBlock;
	public LOTRItemBanner.BannerType banner;
	public LOTRChestContents chestContents;

	protected LOTRWorldGenWargPitBase(boolean flag) {
		super(flag);
	}

	public void associateGroundBlocks() {
		addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 4, LOTRMod.dirtPath, 0);
		addBlockMetaAliasOption("GROUND", 4, Blocks.gravel, 0);
		addBlockMetaAliasOption("GROUND", 4, Blocks.cobblestone, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 1);
		addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, Blocks.stone_slab, 3);
		addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.thatchFloor, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int step;
		int j12;
		int j2;
		int k1;
		int i12;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 8, -10);
		originY -= 4;
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -13; i12 <= 12; ++i12) {
				for (k12 = -12; k12 <= 14; ++k12) {
					j12 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 <= maxHeight) {
						continue;
					}
					maxHeight = j12;
				}
			}
			if (maxHeight - minHeight > 12) {
				return false;
			}
		}
		int radius = 8;
		for (int i13 = -radius; i13 <= radius; ++i13) {
			for (int k13 = -radius; k13 <= radius; ++k13) {
				if (i13 * i13 + k13 * k13 >= radius * radius) {
					continue;
				}
				for (int j13 = 0; j13 <= 12; ++j13) {
					setAir(world, i13, j13, k13);
				}
			}
		}
		int r2 = 12;
		for (i12 = -r2; i12 <= r2; ++i12) {
			for (k12 = -r2; k12 <= r2; ++k12) {
				if (i12 * i12 + k12 * k12 >= r2 * r2 || k12 < -4 || i12 > 4) {
					continue;
				}
				for (j12 = 0; j12 <= 12; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		for (i12 = -12; i12 <= -8; ++i12) {
			for (k12 = -7; k12 <= -4; ++k12) {
				if (k12 == -7 && (i12 == -12 || i12 == -8)) {
					continue;
				}
				for (j12 = 5; j12 <= 12; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k12 = 8; k12 <= 12; ++k12) {
				for (j12 = 7; j12 <= 11; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			for (k12 = -11; k12 <= -6; ++k12) {
				for (j12 = 0; j12 <= 3; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		for (i12 = 6; i12 <= 11; ++i12) {
			for (k12 = -1; k12 <= 1; ++k12) {
				for (j12 = 0; j12 <= 3; ++j12) {
					setAir(world, i12, j12, k12);
				}
			}
		}
		loadStrScan("warg_pit");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("WOOL", woolBlock, woolMeta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateGroundBlocks();
		associateBlockMetaAlias("BARS", barsBlock, 0);
		associateBlockAlias("GATE_ORC", gateOrcBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -7, 5, 0, banner, 1);
		placeWallBanner(world, 7, 5, 0, banner, 3);
		placeWallBanner(world, 0, 5, -7, banner, 0);
		placeWallBanner(world, 0, 5, 7, banner, 2);
		placeOrcTorch(world, 2, 4, -5);
		placeOrcTorch(world, -2, 4, -5);
		placeOrcTorch(world, 5, 4, -2);
		placeOrcTorch(world, -5, 4, -2);
		placeOrcTorch(world, 5, 4, 2);
		placeOrcTorch(world, -5, 4, 2);
		placeOrcTorch(world, 2, 4, 5);
		placeOrcTorch(world, -2, 4, 5);
		placeOrcTorch(world, 1, 7, 8);
		placeOrcTorch(world, -1, 7, 8);
		placeOrcTorch(world, 4, 8, -4);
		placeOrcTorch(world, -4, 8, -4);
		placeOrcTorch(world, 4, 8, 4);
		placeOrcTorch(world, -4, 8, 4);
		placeOrcTorch(world, -8, 10, -4);
		placeOrcTorch(world, -12, 10, -4);
		placeChest(world, random, -7, 1, 0, 4, chestContents);
		placeChest(world, random, 1, 7, 12, 2, chestContents);
		setBlockAndMetadata(world, -2, 7, 9, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 9, bedBlock, 11);
		setBlockAndMetadata(world, -2, 7, 11, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 11, bedBlock, 11);
		placeBarrel(world, random, 3, 8, 11, 5, LOTRFoods.ORC_DRINK);
		placeMug(world, random, 3, 8, 10, 1, LOTRFoods.ORC_DRINK);
		placePlateWithCertainty(world, random, 3, 8, 9, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
		int maxStep = 12;
		for (i1 = -1; i1 <= 1; ++i1) {
			for (step = 0; step < 2 && !isSideSolid(world, i1, j1 = 5 - step, k1 = -9 - step, ForgeDirection.UP); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, brickStairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isSideSolid(world, i1, j2, k1, ForgeDirection.UP) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (step = 0; step < maxStep && !isOpaque(world, i1, j1 = 3 - step, k1 = -13 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, brickStairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		int wargs = 2 + random.nextInt(5);
		for (int l = 0; l < wargs; ++l) {
			LOTREntityNPC warg = getWarg(world);
			spawnNPCAndSetHome(warg, world, 0, 1, 0, 8);
		}
		LOTREntityNPC orc = getOrc(world);
		spawnNPCAndSetHome(orc, world, 0, 1, 0, 24);
		LOTREntityNPCRespawner wargSpawner = new LOTREntityNPCRespawner(world);
		setWargSpawner(wargSpawner);
		wargSpawner.setCheckRanges(12, -8, 16, 8);
		wargSpawner.setSpawnRanges(4, -4, 4, 24);
		placeNPCRespawner(wargSpawner, world, 0, 0, 0);
		LOTREntityNPCRespawner orcSpawner = new LOTREntityNPCRespawner(world);
		setOrcSpawner(orcSpawner);
		orcSpawner.setCheckRanges(32, -12, 20, 16);
		orcSpawner.setSpawnRanges(16, -4, 8, 16);
		placeNPCRespawner(orcSpawner, world, 0, 0, 0);
		return true;
	}

	public abstract LOTREntityNPC getOrc(World var1);

	public abstract LOTREntityNPC getWarg(World var1);

	public abstract void setOrcSpawner(LOTREntityNPCRespawner var1);

	@Override
	public void setupRandomBlocks(Random random) {
		plankBlock = LOTRMod.planks;
		plankMeta = 3;
		plankSlabBlock = LOTRMod.woodSlabSingle;
		plankSlabMeta = 3;
		plankStairBlock = LOTRMod.stairsCharred;
		fenceBlock = LOTRMod.fence;
		fenceMeta = 3;
		beamBlock = LOTRMod.woodBeam1;
		beamMeta = 3;
		doorBlock = LOTRMod.doorCharred;
		woolBlock = Blocks.wool;
		woolMeta = 12;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		barsBlock = LOTRMod.orcSteelBars;
		gateOrcBlock = LOTRMod.gateOrc;
		gateMetalBlock = LOTRMod.gateBronzeBars;
		bedBlock = LOTRMod.orcBed;
	}

	public abstract void setWargSpawner(LOTREntityNPCRespawner var1);
}
