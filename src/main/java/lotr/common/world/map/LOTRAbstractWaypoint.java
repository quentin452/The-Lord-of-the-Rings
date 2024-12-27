package lotr.common.world.map;

import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public interface LOTRAbstractWaypoint {
	String getCodeName();

	String getDisplayName();

	int getID();

	WaypointLockState getLockState(EntityPlayer var1);

	String getLoreText(EntityPlayer var1);

	double getX();

	int getXCoord();

	double getY();

	int getYCoord(World var1, int var2, int var3);

	int getYCoordSaved();

	int getZCoord();

	boolean hasPlayerUnlocked(EntityPlayer var1);

    boolean hasPlayerUnlockedProxy(EntityPlayer entityplayer, Map<LOTRFaction, List<LOTRConquestZone>> facConquestGrids);

	boolean isHidden();

	enum WaypointLockState {
		STANDARD_LOCKED(0, 200), STANDARD_UNLOCKED(4, 200), STANDARD_UNLOCKED_CONQUEST(8, 200), CUSTOM_LOCKED(0, 204), CUSTOM_UNLOCKED(4, 204), SHARED_CUSTOM_LOCKED(0, 208), SHARED_CUSTOM_UNLOCKED(4, 208);

		public int iconU;
		public int iconV;

		WaypointLockState(int u, int v) {
			iconU = u;
			iconV = v;
		}
	}

}
