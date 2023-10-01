package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGundabadOrcMercenaryCaptain;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRuinedDwarvenTower extends LOTRWorldGenDwarvenTower {
	public boolean isGundabad;

	public LOTRWorldGenRuinedDwarvenTower(boolean flag) {
		super(flag);
		ruined = true;
		glowBrickBlock = brickBlock;
		glowBrickMeta = brickMeta;
	}

	@Override
	public LOTREntityNPC getCommanderNPC(World world) {
		if (isGundabad) {
			return new LOTREntityGundabadOrcMercenaryCaptain(world);
		}
		return null;
	}

	@Override
	public void placeBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 5);
		} else {
			super.placeBrick(world, random, i, j, k);
		}
	}

	@Override
	public void placeBrickSlab(World world, Random random, int i, int j, int k, boolean flip) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 6 | (flip ? 8 : 0));
		} else {
			super.placeBrickSlab(world, random, i, j, k, flip);
		}
	}

	@Override
	public void placeBrickStair(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDwarvenBrickCracked, meta);
		} else {
			super.placeBrickStair(world, random, i, j, k, meta);
		}
	}

	@Override
	public void placeBrickWall(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 5);
		} else {
			super.placeBrickWall(world, random, i, j, k);
		}
	}

	@Override
	public void placePillar(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, LOTRMod.pillar2, 0);
		} else {
			super.placePillar(world, random, i, j, k);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (random.nextInt(3) == 0) {
			plankBlock = LOTRMod.planks;
			plankMeta = 3;
			plankSlabBlock = LOTRMod.woodSlabSingle;
			plankSlabMeta = 3;
		}
		if (random.nextInt(4) == 0) {
			barsBlock = Blocks.air;
		} else {
			int randomBars = random.nextInt(4);
			switch (randomBars) {
				case 0:
					barsBlock = LOTRMod.dwarfBars;
					break;
				case 1:
					barsBlock = LOTRMod.orcSteelBars;
					break;
				case 2:
					barsBlock = Blocks.iron_bars;
					break;
				case 3:
					barsBlock = LOTRMod.bronzeBars;
					break;
				default:
					break;
			}
		}
		isGundabad = random.nextInt(3) == 0;
		if (isGundabad) {
			gateBlock = LOTRMod.gateOrc;
			tableBlock = LOTRMod.gundabadTable;
			forgeBlock = LOTRMod.orcForge;
			bannerType = LOTRItemBanner.BannerType.GUNDABAD;
			chestContents = LOTRChestContents.GUNDABAD_TENT;
		} else {
			gateBlock = LOTRMod.gateDwarven;
			tableBlock = LOTRMod.dwarvenTable;
			forgeBlock = LOTRMod.dwarvenForge;
			bannerType = null;
			chestContents = LOTRChestContents.DWARVEN_TOWER;
		}
	}
}
