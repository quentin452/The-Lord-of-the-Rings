package lotr.common.world.biome;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public enum LOTRMusicRegion {
	MENU("menu"), SEA("sea"), SHIRE("shire"), OLD_FOREST("oldForest"), LINDON("lindon"), BARROW_DOWNS("barrowDowns"), BREE("bree"), ERIADOR("eriador"), RIVENDELL("rivendell"), ANGMAR("angmar"), EREGION("eregion"), ENEDWAITH("enedwaith"), DUNLAND("dunland"), PUKEL("pukel"), MISTY_MOUNTAINS("mistyMountains"), FORODWAITH("forodwaith"), GREY_MOUNTAINS("greyMountains"), RHOVANION("rhovanion"), MIRKWOOD("mirkwood"), WOODLAND_REALM("woodlandRealm"), DALE("dale"), DWARVEN("dwarven"), LOTHLORIEN("lothlorien"), FANGORN("fangorn"), ROHAN("rohan"), ISENGARD("isengard"), GONDOR("gondor"), BROWN_LANDS("brownLands"), DEAD_MARSHES("deadMarshes"), MORDOR("mordor"), DORWINION("dorwinion"), RHUN("rhun"), NEAR_HARAD("nearHarad"), FAR_HARAD("farHarad"), FAR_HARAD_JUNGLE("farHaradJungle"), PERDOROGWAITH("pertorogwaith"), UTUMNO("utumno");

	public static String allRegionCode = "all";
	public String regionName;
	public List<String> subregions = new ArrayList<>();

	LOTRMusicRegion(String s) {
		regionName = s;
	}

	public List<String> getAllSubregions() {
		return subregions;
	}

	public Sub getSubregion(String s) {
		if (s != null && !subregions.contains(s)) {
			subregions.add(s);
		}
		return new Sub(this, s);
	}

	public Sub getWithoutSub() {
		return new Sub(this, null);
	}

	public boolean hasNoSubregions() {
		return subregions.isEmpty();
	}

	public boolean hasSubregion(String s) {
		return subregions.contains(s);
	}

	public static LOTRMusicRegion forName(String s) {
		for (LOTRMusicRegion r : values()) {
			if (!s.equalsIgnoreCase(r.regionName)) {
				continue;
			}
			return r;
		}
		return null;
	}

	public static class Sub extends Pair<LOTRMusicRegion, String> {
		public LOTRMusicRegion region;
		public String subregion;

		public Sub(LOTRMusicRegion r, String s) {
			region = r;
			subregion = s;
		}

		@Override
		public LOTRMusicRegion getLeft() {
			return region;
		}

		@Override
		public String getRight() {
			return subregion;
		}

		@Override
		public String setValue(String value) {
			throw new IllegalArgumentException("Value is final");
		}
	}

}
