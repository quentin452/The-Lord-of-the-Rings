package lotr.client.sound;

import lotr.common.world.biome.LOTRMusicRegion;

import java.util.*;

public class LOTRRegionTrackPool {
	public LOTRMusicRegion region;
	public Collection<LOTRMusicTrack> trackList = new ArrayList<>();

	public LOTRRegionTrackPool(LOTRMusicRegion r, String s) {
		region = r;
	}

	public void addTrack(LOTRMusicTrack track) {
		trackList.add(track);
	}

	public LOTRMusicTrack getRandomTrack(Random rand, LOTRTrackSorter.Filter filter) {
		List<LOTRMusicTrack> sortedTracks = LOTRTrackSorter.sortTracks(trackList, filter);
		double totalWeight = 0.0;
		for (LOTRMusicTrack track : sortedTracks) {
			double weight2 = track.getRegionInfo(region).getWeight();
			totalWeight += weight2;
		}
		double randWeight = rand.nextDouble();
		randWeight *= totalWeight;
		Iterator<LOTRMusicTrack> it = sortedTracks.iterator();
		LOTRMusicTrack track = null;
		do {
			if (it.hasNext()) {
				continue;
			}
			return track;
		} while ((randWeight -= (track = it.next()).getRegionInfo(region).getWeight()) >= 0.0);
		return track;
	}

	public boolean isEmpty() {
		return trackList.isEmpty();
	}
}
