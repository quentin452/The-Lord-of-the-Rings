package lotr.common.entity.animal;

import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;

public class LOTREntityDesertScorpion extends LOTREntityScorpion implements LOTRBiomeGenNearHarad.ImmuneToHeat {
	public boolean pyramidSpawned;

	public LOTREntityDesertScorpion(World world) {
		super(world);
		isImmuneToFire = true;
	}

	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && (spawningFromSpawner || pyramidSpawned || posY < 60.0 || rand.nextInt(500) == 0);
	}

	@Override
	public int getRandomScorpionScale() {
		return rand.nextInt(2);
	}

	@Override
	public boolean isValidLightLevel() {
		return spawningFromSpawner || pyramidSpawned || super.isValidLightLevel();
	}
}
