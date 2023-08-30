package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBreeGatehouse extends LOTRWorldGenBreeStructure {
	public String villageName = "Village";

	public LOTRWorldGenBreeGatehouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		int i12;
		int k12;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i12 = -10; i12 <= 5; ++i12) {
				for (k12 = -5; k12 <= 10; ++k12) {
					j1 = getTopBlock(world, i12, k12) - 1;
					if (isSurface(world, i12, j1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k12 = -1; k12 <= 1; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i12, j1, k12);
				}
			}
		}
		for (i12 = -9; i12 <= -4; ++i12) {
			for (k12 = 4; k12 <= 9; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i12, j1, k12);
				}
			}
		}
		for (int k13 = 6; k13 <= 7; ++k13) {
			int i13 = -3;
			for (j1 = 1; j1 <= 7; ++j1) {
				setAir(world, i13, j1, k13);
			}
		}
		loadStrScan("bree_gatehouse");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("COBBLE", Blocks.cobblestone, 0);
		associateBlockAlias("COBBLE_STAIR", Blocks.stone_stairs);
		associateBlockMetaAlias("COBBLE_WALL", Blocks.cobblestone_wall, 0);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
		setBlockAliasChance("THATCH_FLOOR", 0.4f);
		addBlockMetaAliasOption("PATH", 5, LOTRMod.dirtPath, 0);
		addBlockMetaAliasOption("PATH", 3, Blocks.cobblestone, 0);
		addBlockMetaAliasOption("PATH", 2, Blocks.gravel, 0);
		generateStrScan(world, random, 0, 0, 0);
		int maxSteps = 12;
		for (int step = 0; step < maxSteps && !isOpaque(world, i1 = -3, j12 = -Math.max(0, step - 2), k1 = 6 + step); ++step) {
			if (step < 2) {
				setBlockAndMetadata(world, i1, j12, k1, Blocks.cobblestone, 0);
			} else {
				setBlockAndMetadata(world, i1, j12, k1, Blocks.stone_stairs, 3);
			}
			setGrassToDirt(world, i1, j12 - 1, k1);
			int j2 = j12 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i1, j2, k1, Blocks.cobblestone, 0);
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		placeChest(world, random, -5, 2, 8, 5, LOTRChestContents.BREE_HOUSE);
		placeMug(world, random, -7, 3, 5, 2, LOTRFoods.BREE_DRINK);
		placePlateWithCertainty(world, random, -8, 3, 5, LOTRMod.plateBlock, LOTRFoods.BREE);
		setBlockAndMetadata(world, -7, 2, 8, bedBlock, 3);
		setBlockAndMetadata(world, -8, 2, 8, bedBlock, 11);
		spawnItemFrame(world, -7, 4, 4, 0, new ItemStack(Items.clock));
		LOTREntityBreeGuard guard = new LOTREntityBreeGuard(world);
		spawnNPCAndSetHome(guard, world, -7, 2, 6, 8);
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityBreeGuard.class);
		respawner.setCheckRanges(20, -12, 12, 1);
		respawner.setSpawnRanges(4, -2, 2, 8);
		placeNPCRespawner(respawner, world, -7, 2, 6);
		placeSign(world, -4, 3, -5, Blocks.wall_sign, 2, new String[]{"", "Welcome to", villageName, ""});
		placeWallBanner(world, -4, 6, -1, LOTRItemBanner.BannerType.BREE, 2);
		placeWallBanner(world, 4, 6, -1, LOTRItemBanner.BannerType.BREE, 2);
		placeWallBanner(world, 4, 6, 1, LOTRItemBanner.BannerType.BREE, 0);
		placeWallBanner(world, -4, 6, 6, LOTRItemBanner.BannerType.BREE, 1);
		return true;
	}

	public LOTRWorldGenBreeGatehouse setName(String name) {
		villageName = name;
		return this;
	}
}
