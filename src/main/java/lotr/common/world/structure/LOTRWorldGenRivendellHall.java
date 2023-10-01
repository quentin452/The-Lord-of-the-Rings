package lotr.common.world.structure;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityRivendellLord;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRivendellHall extends LOTRWorldGenHighElvenHall {
	public LOTRWorldGenRivendellHall(boolean flag) {
		super(flag);
		tableBlock = LOTRMod.rivendellTable;
		bannerType = LOTRItemBanner.BannerType.RIVENDELL;
		chestContents = LOTRChestContents.RIVENDELL_HALL;
	}

	@Override
	public LOTREntityElf createElf(World world) {
		return new LOTREntityRivendellElf(world);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		if (super.generate(world, random, i, j, k)) {
			LOTREntityRivendellLord elfLord = new LOTREntityRivendellLord(world);
			elfLord.setLocationAndAngles(i + 6, j + 6, k + 6, 0.0f, 0.0f);
			elfLord.spawnRidingHorse = false;
			((LOTREntityNPC) elfLord).onSpawnWithEgg(null);
			elfLord.isNPCPersistent = true;
			world.spawnEntityInWorld(elfLord);
			elfLord.setHomeArea(i + 7, j + 3, k + 7, 16);
		}
		return false;
	}
}
