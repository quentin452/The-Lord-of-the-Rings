package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenNearHaradDesertCamp extends LOTRWorldGenStructureBase2 {
	public LOTRWorldGenNearHaradDesertCamp(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		int highestHeight = 0;
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int k1 = -1; k1 <= 1; ++k1) {
				int j1 = getTopBlock(world, i1, k1);
				Block block = getBlock(world, i1, j1 - 1, k1);
				if (block != Blocks.sand && block != Blocks.dirt && block != Blocks.grass) {
					return false;
				}
				if (j1 <= highestHeight) {
					continue;
				}
				highestHeight = j1;
			}
		}
		boolean flag;
		int x = -2 + random.nextInt(5);
		int z = 4 + random.nextInt(4);
		int y = getTopBlock(world, x, z);
		flag = new LOTRWorldGenNearHaradTent(notifyChanges).generateWithSetRotation(world, random, getX(x, z), getY(y), getZ(x, z), getRotationMode());
		x = -2 + random.nextInt(5);
		z = -4 - random.nextInt(4);
		y = getTopBlock(world, x, z);
		flag = new LOTRWorldGenNearHaradTent(notifyChanges).generateWithSetRotation(world, random, getX(x, z), getY(y), getZ(x, z), (getRotationMode() + 2) % 4) || flag;
		if (!flag) {
			return false;
		}
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int k1 = -1; k1 <= 1; ++k1) {
				int j1 = highestHeight - 1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.sandstone, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
			setBlockAndMetadata(world, i1, highestHeight, -1, LOTRMod.stairsNearHaradBrick, 2);
			setBlockAndMetadata(world, i1, highestHeight, 1, LOTRMod.stairsNearHaradBrick, 3);
		}
		setBlockAndMetadata(world, -1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 1);
		setBlockAndMetadata(world, 1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 0);
		setBlockAndMetadata(world, 0, highestHeight, 0, Blocks.water, 0);
		int camels = 1 + random.nextInt(4);
		for (int l = 0; l < camels; ++l) {
			int camelY;
			int camelZ;
			LOTREntityCamel camel = new LOTREntityCamel(world);
			int camelX = random.nextBoolean() ? -3 - random.nextInt(3) : 3 + random.nextInt(3);
			if (getBlock(world, camelX, (camelY = getTopBlock(world, camelX, camelZ = -3 + random.nextInt(7))) - 1, camelZ) != Blocks.sand || getBlock(world, camelX, camelY, camelZ) != Blocks.air || getBlock(world, camelX, camelY + 1, camelZ) != Blocks.air) {
				continue;
			}
			setBlockAndMetadata(world, camelX, camelY, camelZ, LOTRMod.fence2, 2);
			setBlockAndMetadata(world, camelX, camelY + 1, camelZ, LOTRMod.fence2, 2);
			spawnNPCAndSetHome(camel, world, camelX, camelY, camelZ, 0);
			camel.detachHome();
			camel.saddleMountForWorldGen();
			if (random.nextBoolean()) {
				camel.setChestedForWorldGen();
			}
			EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, getX(camelX, camelZ), getY(camelY), getZ(camelX, camelZ));
			camel.setLeashedToEntity(leash, true);
		}
		LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
		respawner.setSpawnClass(LOTREntityHarnedorWarrior.class);
		respawner.setCheckRanges(20, -12, 12, 4);
		respawner.setSpawnRanges(8, -4, 4, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}
