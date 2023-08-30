package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeMarket extends LOTRWorldGenBreeStructure {
	public LOTRWorldGenBreeMarketStall[] presetStalls;
	public boolean frontStepsOnly;

	public LOTRWorldGenBreeMarket(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int step;
		int i1;
		int j2;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 13);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -12; i1 <= 12; ++i1) {
				for (k1 = -12; k1 <= 12; ++k1) {
					int j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -12; i1 <= 12; ++i1) {
			for (k1 = -12; k1 <= 12; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				boolean marketBounds = false;
				if (i2 <= 1 && k2 <= 12 || k2 <= 1 && i2 <= 12) {
					marketBounds = true;
				} else if (i2 <= 4 && k2 <= 11 || k2 <= 4 && i2 <= 11) {
					marketBounds = true;
				} else if (i2 <= 6 && k2 <= 10 || k2 <= 6 && i2 <= 10) {
					marketBounds = true;
				} else if (i2 <= 7 && k2 <= 9 || k2 <= 7 && i2 <= 9) {
					marketBounds = true;
				} else if (i2 <= 8 && k2 == 8) {
					marketBounds = true;
				}
				if (!marketBounds) {
					continue;
				}
				for (int j13 = 1; j13 <= 8; ++j13) {
					setAir(world, i1, j13, k1);
				}
			}
		}
		loadStrScan("bree_market");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", Blocks.stone_slab, 5);
		associateBlockMetaAlias("COBBLE", Blocks.cobblestone, 0);
		associateBlockMetaAlias("COBBLE_SLAB", Blocks.stone_slab, 3);
		associateBlockAlias("COBBLE_STAIR", Blocks.stone_stairs);
		associateBlockMetaAlias("COBBLE_WALL", Blocks.cobblestone_wall, 0);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		addBlockMetaAliasOption("GROUND", 1, Blocks.gravel, 0);
		addBlockMetaAliasOption("GROUND", 1, Blocks.grass, 0);
		addBlockMetaAliasOption("GROUND", 1, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 1, LOTRMod.dirtPath, 0);
		addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
		setBlockAliasChance("THATCH_FLOOR", 0.15f);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		associateBlockMetaAlias("LEAF_FLOOR", LOTRMod.fallenLeaves, 0);
		setBlockAliasChance("LEAF_FLOOR", 0.5f);
		generateStrScan(world, random, 0, 0, 0);
		for (int i12 = -1; i12 <= 1; ++i12) {
			int k12;
			for (step = 0; step < 12 && !isOpaque(world, i12, j1 = -1 - step, k12 = -13 - step); ++step) {
				placeRandomFloor(world, random, i12, j1, k12);
				setGrassToDirt(world, i12, j1 - 1, k12);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.dirt, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
			if (frontStepsOnly) {
				continue;
			}
			for (step = 0; step < 12 && !isOpaque(world, i12, j1 = -1 - step, k12 = 13 + step); ++step) {
				placeRandomFloor(world, random, i12, j1, k12);
				setGrassToDirt(world, i12, j1 - 1, k12);
				j2 = j1 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.dirt, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
		}
		if (!frontStepsOnly) {
			for (k1 = -1; k1 <= 1; ++k1) {
				int i13;
				for (step = 0; step < 12 && !isOpaque(world, i13 = -13 - step, j1 = -1 - step, k1); ++step) {
					placeRandomFloor(world, random, i13, j1, k1);
					setGrassToDirt(world, i13, j1 - 1, k1);
					j2 = j1 - 1;
					while (!isOpaque(world, i13, j2, k1) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i13, j2, k1, Blocks.dirt, 0);
						setGrassToDirt(world, i13, j2 - 1, k1);
						--j2;
					}
				}
				for (step = 0; step < 12 && !isOpaque(world, i13 = 13 + step, j1 = -1 - step, k1); ++step) {
					placeRandomFloor(world, random, i13, j1, k1);
					setGrassToDirt(world, i13, j1 - 1, k1);
					j2 = j1 - 1;
					while (!isOpaque(world, i13, j2, k1) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i13, j2, k1, Blocks.dirt, 0);
						setGrassToDirt(world, i13, j2 - 1, k1);
						--j2;
					}
				}
			}
		}
		placeWallBanner(world, 0, 4, 0, LOTRItemBanner.BannerType.BREE, 2);
		placeAnimalJar(world, -3, 1, 4, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeAnimalJar(world, 3, 2, -8, LOTRMod.birdCage, 1, new LOTREntityBird(world));
		placeAnimalJar(world, -1, 4, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
		placeAnimalJar(world, 0, 2, 1, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
		LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
		armorGuard.onSpawnWithEgg(null);
		placeArmorStand(world, 2, 1, 0, 3, new ItemStack[]{armorGuard.getEquipmentInSlot(4), armorGuard.getEquipmentInSlot(3), null, null});
		LOTRWorldGenBreeMarketStall[] stalls = presetStalls;
		if (stalls == null) {
			stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, notifyChanges, 4);
		}
		generateSubstructureWithRestrictionFlag(stalls[0], world, random, 6, 1, 3, 0, false);
		generateSubstructureWithRestrictionFlag(stalls[1], world, random, 3, 1, -6, 1, false);
		generateSubstructureWithRestrictionFlag(stalls[2], world, random, -6, 1, -3, 2, false);
		generateSubstructureWithRestrictionFlag(stalls[3], world, random, -3, 1, 6, 3, false);
		return true;
	}

	public LOTRWorldGenBreeMarket setFrontStepsOnly(boolean flag) {
		frontStepsOnly = flag;
		return this;
	}

	public LOTRWorldGenBreeMarket setStalls(LOTRWorldGenBreeMarketStall... stalls) {
		if (stalls.length != 4) {
			throw new IllegalArgumentException("Error: Market must have 4 stalls, but " + stalls.length + " supplied");
		}
		presetStalls = stalls;
		return this;
	}
}
