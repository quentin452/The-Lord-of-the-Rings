package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenUmbarTower extends LOTRWorldGenSouthronTower {
	public LOTRWorldGenUmbarTower(boolean flag) {
		super(flag);
	}

	@Override
	public LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
		return random.nextInt(3) == 0 ? new LOTREntityUmbarArcher(world) : new LOTREntityUmbarWarrior(world);
	}

	@Override
	public boolean isUmbar() {
		return true;
	}

	@Override
	public void setSpawnClasses(LOTREntityNPCRespawner spawner) {
		spawner.setSpawnClasses(LOTREntityUmbarWarrior.class, LOTREntityUmbarArcher.class);
	}
}
