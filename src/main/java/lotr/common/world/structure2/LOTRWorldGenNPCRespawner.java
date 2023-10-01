package lotr.common.world.structure2;

import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.world.World;

import java.util.Random;

public abstract class LOTRWorldGenNPCRespawner extends LOTRWorldGenStructureBase2 {
	protected LOTRWorldGenNPCRespawner(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		LOTREntityNPCRespawner spawner = new LOTREntityNPCRespawner(world);
		setupRespawner(spawner);
		placeNPCRespawner(spawner, world, 0, 1, 0);
		return true;
	}

	public abstract void setupRespawner(LOTREntityNPCRespawner var1);
}
