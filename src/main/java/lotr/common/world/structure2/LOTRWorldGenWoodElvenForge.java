package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityWoodElfSmith;
import net.minecraft.world.World;

public class LOTRWorldGenWoodElvenForge extends LOTRWorldGenElvenForge {
	public LOTRWorldGenWoodElvenForge(boolean flag) {
		super(flag);
		brickBlock = LOTRMod.brick3;
		brickMeta = 5;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 12;
		slabBlock = LOTRMod.slabSingle6;
		slabMeta = 2;
		carvedBrickBlock = LOTRMod.brick2;
		carvedBrickMeta = 14;
		wallBlock = LOTRMod.wall3;
		wallMeta = 0;
		stairBlock = LOTRMod.stairsWoodElvenBrick;
		torchBlock = LOTRMod.woodElvenTorch;
		tableBlock = LOTRMod.woodElvenTable;
		barsBlock = LOTRMod.woodElfBars;
		woodBarsBlock = LOTRMod.woodElfWoodBars;
		roofBlock = LOTRMod.clayTileDyed;
		roofMeta = 13;
		roofStairBlock = LOTRMod.stairsClayTileDyedGreen;
	}

	@Override
	public LOTREntityElf getElf(World world) {
		return new LOTREntityWoodElfSmith(world);
	}
}
