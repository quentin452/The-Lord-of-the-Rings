package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class LOTRTeleporterUtumno extends Teleporter {
	public WorldServer worldObj;
	public int targetX;
	public int targetZ;

	public LOTRTeleporterUtumno(WorldServer world) {
		super(world);
		worldObj = world;
	}

	public static LOTRTeleporterUtumno newTeleporter(int dimension) {
		WorldServer world = DimensionManager.getWorld(dimension);
		if (world == null) {
			DimensionManager.initDimension(dimension);
			world = DimensionManager.getWorld(dimension);
		}
		return new LOTRTeleporterUtumno(world);
	}

	@Override
	public void placeInPortal(Entity entity, double d, double d1, double d2, float f) {
		int k;
		int j;
		int i;
		block4:
		{
			i = 0;
			j = 256;
			k = 0;
			if (worldObj.provider.dimensionId == LOTRDimension.UTUMNO.dimensionID) {
				for (int l = 0; l < 10000; ++l) {
					int x = targetX;
					int z = targetZ;
					int y = LOTRUtumnoLevel.ICE.corridorBaseLevels[LOTRUtumnoLevel.ICE.corridorBaseLevels.length - 1];
					int targetFuzz = 32;
					int x1 = MathHelper.getRandomIntegerInRange(worldObj.rand, x - targetFuzz, x + targetFuzz);
					int z1 = MathHelper.getRandomIntegerInRange(worldObj.rand, z - targetFuzz, z + targetFuzz);
					int yFuzz = 3;
					for (int j1 = -yFuzz; j1 <= yFuzz; ++j1) {
						int y1 = y + j1;
						if (!worldObj.getBlock(x1, y1 - 1, z1).isOpaqueCube() || !worldObj.isAirBlock(x1, y1, z1) || !worldObj.isAirBlock(x1, y1, z1)) {
							continue;
						}
						i = x1;
						j = y1;
						k = z1;
						break block4;
					}
				}
			} else {
				double randomDistance = MathHelper.getRandomDoubleInRange(worldObj.rand, 40.0, 80.0);
				float angle = worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
				i = LOTRFixedStructures.UTUMNO_ENTRANCE.xCoord + (int) (randomDistance * MathHelper.sin(angle));
				k = LOTRFixedStructures.UTUMNO_ENTRANCE.zCoord + (int) (randomDistance * MathHelper.cos(angle));
				j = worldObj.getTopSolidOrLiquidBlock(i, k);
			}
		}
		setEntityAndMountLocation(entity, i + 0.5, j + 1.0, k + 0.5);
	}

	public void setEntityAndMountLocation(Entity entity, double x, double y, double z) {
		entity.setLocationAndAngles(x, y, z, entity.rotationYaw, 0.0f);
		entity.fallDistance = 0.0f;
		if (entity.ridingEntity != null) {
			setEntityAndMountLocation(entity.ridingEntity, x, y, z);
		}
	}

	public void setTargetCoords(int x, int z) {
		targetX = x;
		targetZ = z;
	}
}
