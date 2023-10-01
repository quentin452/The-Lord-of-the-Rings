package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBlueMountainsSmith;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsSmithy extends LOTRWorldGenDwarfSmithy {
	public LOTRWorldGenBlueMountainsSmithy(boolean flag) {
		super(flag);
		baseBrickBlock = LOTRMod.brick;
		baseBrickMeta = 14;
		carvedBrickBlock = LOTRMod.brick3;
		carvedBrickMeta = 0;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 3;
		tableBlock = LOTRMod.blueDwarvenTable;
		barsBlock = LOTRMod.blueDwarfBars;
	}

	@Override
	public LOTREntityDwarf createSmith(World world) {
		return new LOTREntityBlueMountainsSmith(world);
	}

	@Override
	public LOTRChestContents getChestContents() {
		return LOTRChestContents.BLUE_MOUNTAINS_SMITHY;
	}
}
