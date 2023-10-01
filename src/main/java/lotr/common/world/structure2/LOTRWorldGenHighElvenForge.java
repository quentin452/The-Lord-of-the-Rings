package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityHighElfSmith;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenForge extends LOTRWorldGenElvenForge {
	public LOTRWorldGenHighElvenForge(boolean flag) {
		super(flag);
		brickBlock = LOTRMod.brick3;
		brickMeta = 2;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 10;
		slabBlock = LOTRMod.slabSingle5;
		slabMeta = 5;
		carvedBrickBlock = LOTRMod.brick2;
		carvedBrickMeta = 13;
		wallBlock = LOTRMod.wall2;
		wallMeta = 11;
		stairBlock = LOTRMod.stairsHighElvenBrick;
		torchBlock = LOTRMod.highElvenTorch;
		tableBlock = LOTRMod.highElvenTable;
		barsBlock = LOTRMod.highElfBars;
		woodBarsBlock = LOTRMod.highElfWoodBars;
		roofBlock = LOTRMod.clayTileDyed;
		roofMeta = 3;
		roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
	}

	@Override
	public LOTREntityElf getElf(World world) {
		return new LOTREntityHighElfSmith(world);
	}
}
