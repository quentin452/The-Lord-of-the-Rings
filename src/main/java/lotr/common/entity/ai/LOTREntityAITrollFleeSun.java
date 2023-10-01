package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class LOTREntityAITrollFleeSun extends EntityAIBase {
	public LOTREntityTroll theTroll;
	public double xPosition;
	public double yPosition;
	public double zPosition;
	public double moveSpeed;
	public World theWorld;

	public LOTREntityAITrollFleeSun(LOTREntityTroll troll, double d) {
		theTroll = troll;
		moveSpeed = d;
		theWorld = troll.worldObj;
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return !theTroll.getNavigator().noPath();
	}

	public Vec3 findPossibleShelter() {
		Random random = theTroll.getRNG();
		for (int l = 0; l < 32; ++l) {
			int j;
			int k;
			int i = MathHelper.floor_double(theTroll.posX) - 24 + random.nextInt(49);
			if (theWorld.canBlockSeeTheSky(i, j = MathHelper.floor_double(theTroll.boundingBox.minY) - 12 + random.nextInt(25), k = MathHelper.floor_double(theTroll.posZ) - 24 + random.nextInt(49)) || theTroll.getBlockPathWeight(i, j, k) >= 0.0f) {
				continue;
			}
			return Vec3.createVectorHelper(i, j, k);
		}
		return null;
	}

	@Override
	public boolean shouldExecute() {
		Vec3 vec3;
		if (!theWorld.isDaytime() || !theWorld.canBlockSeeTheSky(MathHelper.floor_double(theTroll.posX), (int) theTroll.boundingBox.minY, MathHelper.floor_double(theTroll.posZ)) || theTroll.trollImmuneToSun) {
			return false;
		}
		BiomeGenBase biome = theWorld.getBiomeGenForCoords(MathHelper.floor_double(theTroll.posX), MathHelper.floor_double(theTroll.posZ));
		if (biome instanceof LOTRBiome && ((LOTRBiome) biome).canSpawnHostilesInDay()) {
			return false;
		}
		if (theTroll.getTrollBurnTime() == -1) {
			theTroll.setTrollBurnTime(300);
		}
		vec3 = findPossibleShelter();
		if (vec3 == null && (vec3 = RandomPositionGenerator.findRandomTarget(theTroll, 12, 6)) == null) {
			return false;
		}
		xPosition = vec3.xCoord;
		yPosition = vec3.yCoord;
		zPosition = vec3.zCoord;
		return true;
	}

	@Override
	public void startExecuting() {
		theTroll.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, moveSpeed);
	}
}
