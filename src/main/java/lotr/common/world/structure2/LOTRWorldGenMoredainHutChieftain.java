package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMoredainChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenMoredainHutChieftain extends LOTRWorldGenMoredainHut {
	public LOTRWorldGenMoredainHutChieftain(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int k2;
		int i1;
		int i2;
		int i12;
		if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k1 = -5; k1 <= 5; ++k1) {
				int j12;
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 <= 4 && k2 <= 4) {
					layFoundation(world, i12, k1);
					for (j12 = 1; j12 <= 9; ++j12) {
						setAir(world, i12, j12, k1);
					}
				}
				if (i2 == 4 && k2 <= 4 || k2 == 4 && i2 <= 4) {
					setBlockAndMetadata(world, i12, 1, k1, clayBlock, clayMeta);
				}
				if (i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
					for (j12 = 2; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i12, j12, k1, brickBlock, brickMeta);
					}
				}
				if (i2 == 4 && k2 == 4) {
					for (j12 = 2; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i12, j12, k1, fenceBlock, fenceMeta);
					}
				}
				if (k1 == 5 && i2 <= 1) {
					layFoundation(world, i12, 5);
					setBlockAndMetadata(world, i12, 1, 5, clayBlock, clayMeta);
					for (j12 = 2; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i12, j12, 5, brickBlock, brickMeta);
					}
					if (i2 == 0) {
						setBlockAndMetadata(world, i12, 5, 5, plankBlock, plankMeta);
					} else {
						setBlockAndMetadata(world, i12, 5, 5, plankSlabBlock, plankSlabMeta);
					}
				}
				if (i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 5, k1, stainedClayBlock, stainedClayMeta);
					if (i2 == 0 || k2 == 0) {
						setBlockAndMetadata(world, i12, 6, k1, plankSlabBlock, plankSlabMeta);
					}
				}
				if (i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
					setBlockAndMetadata(world, i12, 5, k1, plankSlabBlock, plankSlabMeta);
				}
				if (i2 == 3 && k2 == 3) {
					setBlockAndMetadata(world, i12, 1, k1, plankBlock, plankMeta);
					for (j12 = 2; j12 <= 4; ++j12) {
						setBlockAndMetadata(world, i12, j12, k1, fenceBlock, fenceMeta);
					}
					setBlockAndMetadata(world, i12, 5, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 6, k1, fenceBlock, fenceMeta);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					if (i2 == 0 || k2 == 0) {
						setBlockAndMetadata(world, i12, 6, k1, plankSlabBlock, plankSlabMeta);
					} else {
						setBlockAndMetadata(world, i12, 6, k1, plankBlock, plankMeta);
					}
					setBlockAndMetadata(world, i12, 7, k1, plankBlock, plankMeta);
				}
				if (i2 == 3 && k2 == 2 || k2 == 3 && i2 == 2) {
					setBlockAndMetadata(world, i12, 5, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i12, 6, k1, fenceBlock, fenceMeta);
				}
				if (k2 == 0 && i2 == 4) {
					setBlockAndMetadata(world, i12, 2, k1, plankSlabBlock, plankSlabMeta);
					setBlockAndMetadata(world, i12, 3, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				if (k2 > 1 || i2 != 5) {
					continue;
				}
				if (k2 == 0) {
					setBlockAndMetadata(world, i12, 4, k1, thatchSlabBlock, thatchSlabMeta);
					setBlockAndMetadata(world, i12, 5, k1, plankSlabBlock, plankSlabMeta | 8);
					continue;
				}
				setBlockAndMetadata(world, i12, 3, k1, thatchSlabBlock, thatchSlabMeta | 8);
				setBlockAndMetadata(world, i12, 5, k1, plankSlabBlock, plankSlabMeta);
				dropFence(world, i12, 2, k1);
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 7, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 == 3 && k2 == 3) {
					setBlockAndMetadata(world, i12, 7, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 == 4 && k2 == 0 || k2 == 4 && i2 == 0) {
					setBlockAndMetadata(world, i12, 8, k1, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i12, 9, k1, fenceBlock, fenceMeta);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i12, 7, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 8, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 == 3 && k2 == 0 || k2 == 3 && i2 == 0) {
					setBlockAndMetadata(world, i12, 8, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 8, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 + k2 == 2) {
					setBlockAndMetadata(world, i12, 9, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 + k2 == 1) {
					setBlockAndMetadata(world, i12, 9, k1, thatchBlock, thatchMeta);
				}
				if (i2 <= 2 && k2 <= 2 && i2 + k2 >= 3) {
					setBlockAndMetadata(world, i12, 8, k1, thatchBlock, thatchMeta);
				}
				if (i2 != 1 || k2 != 1) {
					continue;
				}
				setBlockAndMetadata(world, i12, 8, k1, thatchSlabBlock, thatchSlabMeta | 8);
			}
		}
		int[] i13 = {-1, 1};
		k1 = i13.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int f = i13[i2];
			layFoundation(world, 2 * f, -5);
			setBlockAndMetadata(world, 2 * f, 1, -5, clayBlock, clayMeta);
			setBlockAndMetadata(world, 2 * f, 2, -5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2 * f, 3, -5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2 * f, 2, -4, stainedClayBlock, stainedClayMeta);
			setBlockAndMetadata(world, 2 * f, 3, -4, stainedClayBlock, stainedClayMeta);
			setAir(world, f, 1, -4);
			setAir(world, f, 2, -4);
			setBlockAndMetadata(world, f, 3, -4, stainedClayBlock, stainedClayMeta);
			setBlockAndMetadata(world, f, 4, -4, stainedClayBlock, stainedClayMeta);
			setAir(world, 0, 1, -4);
			setAir(world, 0, 2, -4);
			setAir(world, 0, 3, -4);
			setBlockAndMetadata(world, 0, 4, -4, stainedClayBlock, stainedClayMeta);
			setBlockAndMetadata(world, 2 * f, 4, -5, thatchSlabBlock, thatchSlabMeta);
			setBlockAndMetadata(world, f, 4, -5, thatchSlabBlock, thatchSlabMeta | 8);
			setBlockAndMetadata(world, 0, 5, -5, thatchSlabBlock, thatchSlabMeta);
			setBlockAndMetadata(world, 2 * f, 3, -3, thatchSlabBlock, thatchSlabMeta | 8);
			setBlockAndMetadata(world, f, 4, -3, thatchSlabBlock, thatchSlabMeta);
			setBlockAndMetadata(world, 0, 4, -3, thatchSlabBlock, thatchSlabMeta | 8);
		}
		placeWallBanner(world, 0, 4, -4, LOTRItemBanner.BannerType.MOREDAIN, 2);
		placeWallBanner(world, -4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
		placeWallBanner(world, 4, 5, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
		placeWallBanner(world, -2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 1);
		placeWallBanner(world, 0, 8, -2, LOTRItemBanner.BannerType.MOREDAIN, 0);
		placeWallBanner(world, 2, 8, 0, LOTRItemBanner.BannerType.MOREDAIN, 3);
		placeWallBanner(world, 0, 8, 2, LOTRItemBanner.BannerType.MOREDAIN, 2);
		setBlockAndMetadata(world, -2, 1, -2, LOTRMod.lionBed, 3);
		setBlockAndMetadata(world, -3, 1, -2, LOTRMod.lionBed, 11);
		setBlockAndMetadata(world, -2, 1, 0, LOTRMod.lionBed, 3);
		setBlockAndMetadata(world, -3, 1, 0, LOTRMod.lionBed, 11);
		setBlockAndMetadata(world, -3, 1, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 1, 2, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 3, 1, 2, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 3, 1, 1, Blocks.crafting_table, 0);
		placeChest(world, random, 3, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.MOREDAIN_HUT);
		setBlockAndMetadata(world, 3, 1, -1, LOTRMod.moredainTable, 0);
		setBlockAndMetadata(world, 3, 1, -2, plankSlabBlock, plankSlabMeta | 8);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (j1 = 1; j1 <= 4; ++j1) {
				setBlockAndMetadata(world, i1, j1, 4, stainedClayBlock, stainedClayMeta);
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (j1 = 1; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, i1, j1, 3, plankBlock, plankMeta);
			}
		}
		setBlockAndMetadata(world, -1, 3, 3, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, 0, 3, 3, plankBlock, plankMeta);
		setBlockAndMetadata(world, 1, 3, 3, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, -1, 1, 2, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 1, 2, thatchSlabBlock, thatchSlabMeta);
		setBlockAndMetadata(world, 1, 1, 2, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 2, 3, LOTRMod.goldBars, 0);
		for (int f : new int[]{-1, 1}) {
			for (int k12 = 2; k12 <= 3; ++k12) {
				setBlockAndMetadata(world, 2 * f, 3, k12, thatchSlabBlock, thatchSlabMeta | 8);
				setBlockAndMetadata(world, f, 4, k12, thatchSlabBlock, thatchSlabMeta);
				setBlockAndMetadata(world, 0, 4, k12, thatchSlabBlock, thatchSlabMeta | 8);
			}
		}
		setBlockAndMetadata(world, -3, 3, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, 3, 3, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 3, -1, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 3, 1, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 3, -1, Blocks.torch, 1);
		setBlockAndMetadata(world, 3, 3, 1, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 0, -1, LOTRMod.commandTable, 0);
		LOTREntityMoredainChieftain chieftain = new LOTREntityMoredainChieftain(world);
		chieftain.spawnRidingHorse = false;
		spawnNPCAndSetHome(chieftain, world, 0, 1, 0, 8);
		return true;
	}

	@Override
	public int getOffset() {
		return 5;
	}
}
