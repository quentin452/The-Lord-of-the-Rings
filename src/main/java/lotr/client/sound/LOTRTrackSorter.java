package lotr.client.sound;

import java.util.*;

import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRTrackSorter {
	public static Filter forAny() {
		return track -> true;
	}

	public static Filter forRegionAndCategory(LOTRMusicRegion reg, LOTRMusicCategory cat) {
		return track -> track.getRegionInfo(reg).getCategories().contains(cat);
	}

	public static List<LOTRMusicTrack> sortTracks(Iterable<LOTRMusicTrack> tracks, Filter filter) {
		List<LOTRMusicTrack> sorted = new ArrayList<>();
		for (LOTRMusicTrack track : tracks) {
			if (!filter.accept(track)) {
				continue;
			}
			sorted.add(track);
		}
		return sorted;
	}

	public interface Filter {
		boolean accept(LOTRMusicTrack var1);
	}

}
