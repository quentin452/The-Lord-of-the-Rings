package lotr.common.world.spawning;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRGollumSpawner {
	public static void performSpawning(World world) {
		if (LOTRLevelData.gollumSpawned()) {
			return;
		}
		if (world.getTotalWorldTime() % 20L == 0L) {
			int checkRange;
			int k;
			int j;
			LOTRWaypoint home = LOTRWaypoint.HIGH_PASS;
			int x = home.getXCoord();
			int z = home.getZCoord();
			int homeRange = 128;
			int i = MathHelper.getRandomIntegerInRange(world.rand, x - homeRange, x + homeRange);
			if (world.checkChunksExist(i - (checkRange = 16), (j = MathHelper.getRandomIntegerInRange(world.rand, 16, 32)) - checkRange, (k = MathHelper.getRandomIntegerInRange(world.rand, z - homeRange, z + homeRange)) - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
				if (world.getEntitiesWithinAABB(EntityPlayer.class, aabb.expand(checkRange, checkRange, checkRange)).isEmpty()) {
					Block block = world.getBlock(i, j, k);
					Block below = world.getBlock(i, j - 1, k);
					Block above = world.getBlock(i, j + 1, k);
					if (below.isNormalCube() && !block.isNormalCube() && !above.isNormalCube()) {
						LOTREntityGollum gollum = new LOTREntityGollum(world);
						gollum.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
						if (gollum.getCanSpawnHere()) {
							gollum.onSpawnWithEgg(null);
							world.spawnEntityInWorld(gollum);
							LOTRLevelData.setGollumSpawned(true);
						}
					}
				}
			}
		}
	}
}
