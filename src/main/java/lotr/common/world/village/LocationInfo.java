package lotr.common.world.village;

import lotr.common.world.map.LOTRWaypoint;

public class LocationInfo {
	public static LocationInfo RANDOM_GEN_HERE = new LocationInfo(0, 0, 0, "RANDOM_GEN");
	public static LocationInfo SPAWNED_BY_PLAYER = new LocationInfo(0, 0, 0, "PLAYER_SPAWNED");
	public static LocationInfo NONE_HERE = new LocationInfo(0, 0, 0, "NONE") {

		@Override
		public boolean isPresent() {
			return false;
		}
	};
	public int posX;
	public int posZ;
	public int rotation;
	public String name;
	public boolean isFixedLocation;
	public LOTRWaypoint associatedWaypoint;

	public LocationInfo(int x, int z, int r, String s) {
		posX = x;
		posZ = z;
		rotation = r;
		name = s;
	}

	public LOTRWaypoint getAssociatedWaypoint() {
		return associatedWaypoint;
	}

	public boolean isFixedLocation() {
		return isFixedLocation;
	}

	public boolean isPresent() {
		return true;
	}

	public LocationInfo setFixedLocation(LOTRWaypoint wp) {
		isFixedLocation = true;
		associatedWaypoint = wp;
		return this;
	}

}
