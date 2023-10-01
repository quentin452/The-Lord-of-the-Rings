package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import java.util.List;
import java.util.Random;

public class LOTRTileEntityCorruptMallorn extends TileEntity {
	@Override
	public void updateEntity() {
		super.updateEntity();
		Random rand = worldObj.rand;
		if (!worldObj.isRemote && LOTRMod.canSpawnMobs(worldObj) && rand.nextInt(40) == 0 && worldObj.getBiomeGenForCoords(xCoord, zCoord) instanceof LOTRBiomeGenFangorn) {
			int checkRange = 24;
			int spawnRange = 20;
			List nearbyEnts = worldObj.getEntitiesWithinAABB(LOTREntityEnt.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).expand(checkRange, checkRange, checkRange));
			if (nearbyEnts.isEmpty()) {
				LOTREntityEnt ent = new LOTREntityEnt(worldObj);
				for (int l = 0; l < 16; ++l) {
					int k;
					int j;
					int i = xCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange);
					if (!worldObj.getBlock(i, (j = yCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange)) - 1, k = zCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange)).isNormalCube() || worldObj.getBlock(i, j, k).isNormalCube() || worldObj.getBlock(i, j + 1, k).isNormalCube()) {
						continue;
					}
					ent.setLocationAndAngles(i + 0.5, j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
					ent.liftSpawnRestrictions = false;
					if (!ent.getCanSpawnHere()) {
						continue;
					}
					ent.onSpawnWithEgg(null);
					ent.isNPCPersistent = false;
					worldObj.spawnEntityInWorld(ent);
					break;
				}
			}
		}
	}
}
