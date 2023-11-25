package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class LOTRTeleporter extends Teleporter {
    public WorldServer world;
    public boolean makeRingPortal;

    public LOTRTeleporter(WorldServer worldserver, boolean flag) {
        super(worldserver);
        world = worldserver;
        makeRingPortal = flag;
    }

    @Override
    public void placeInPortal(Entity entity, double d, double d1, double d2, float f) {
        if (world == null || world.provider == null) {
            return;
        }

        int i, j, k;
        if (world.provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            i = 0;
            k = 0;
            j = LOTRMod.getTrueTopBlock(world, i, k);
        } else {
            i = LOTRLevelData.overworldPortalX;
            k = LOTRLevelData.overworldPortalZ;
            j = LOTRLevelData.overworldPortalY;
        }

        if (entity == null) {return;
        }

        entity.setLocationAndAngles(i + 0.5, j + 1.0, k + 0.5, entity.rotationYaw, 0.0f);

        if (world.provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID && LOTRLevelData.madeMiddleEarthPortal == 0) {
            LOTRLevelData.setMadeMiddleEarthPortal(1);
            if (makeRingPortal && world.provider instanceof LOTRWorldProviderMiddleEarth) {
                ((LOTRWorldProviderMiddleEarth) world.provider).setRingPortalLocation(i, j, k);
                LOTREntityPortal portal = new LOTREntityPortal(world);
                portal.setLocationAndAngles(i + 0.5, j + 3.5, k + 0.5, 0.0f, 0.0f);
                world.spawnEntityInWorld(portal);
            }
        }
    }
}
