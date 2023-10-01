package lotr.common.entity.animal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTRAmbientSpawnChecks {
	public static boolean canSpawn(EntityLiving entity, int xzRange, int yRange, int attempts, int required, Material... materials) {
		int k;
		int j;
		World world = entity.worldObj;
		Random rand = entity.getRNG();
		int i = MathHelper.floor_double(entity.posX);
		Block below = world.getBlock(i, (j = MathHelper.floor_double(entity.posY)) - 1, k = MathHelper.floor_double(entity.posZ));
		if (below == world.getBiomeGenForCoords(i, k).topBlock) {
			int light = world.getBlockLightValue(i, j, k);
			if (j >= 62 && light >= rand.nextInt(8) || light >= 8) {
				List<Material> validMaterials = Arrays.asList(materials);
				int counted = 0;
				for (int l = 0; l < attempts; ++l) {
					int i1 = i + rand.nextInt(xzRange) - rand.nextInt(xzRange);
					int k1 = k + rand.nextInt(xzRange) - rand.nextInt(xzRange);
					int j1 = j + rand.nextInt(yRange) - rand.nextInt(yRange);
					if (!world.blockExists(i1, j1, k1) || !validMaterials.contains(world.getBlock(i1, j1, k1).getMaterial()) || ++counted <= required) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}
}
