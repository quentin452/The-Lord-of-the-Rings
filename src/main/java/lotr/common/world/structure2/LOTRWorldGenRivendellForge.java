package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityRivendellSmith;
import net.minecraft.world.World;

public class LOTRWorldGenRivendellForge extends LOTRWorldGenHighElvenForge {
	public LOTRWorldGenRivendellForge(boolean flag) {
		super(flag);
		roofBlock = LOTRMod.clayTileDyed;
		roofMeta = 9;
		roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
		tableBlock = LOTRMod.rivendellTable;
	}

	@Override
	public LOTREntityElf getElf(World world) {
		return new LOTREntityRivendellSmith(world);
	}
}
