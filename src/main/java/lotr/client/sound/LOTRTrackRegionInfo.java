package lotr.client.sound;

import lotr.common.world.biome.LOTRMusicRegion;

import java.util.ArrayList;
import java.util.List;

public class LOTRTrackRegionInfo {
	public LOTRMusicRegion region;
	public List<String> subregions = new ArrayList<>();
	public double weight;
	public List<LOTRMusicCategory> categories = new ArrayList<>();

	public LOTRTrackRegionInfo(LOTRMusicRegion r) {
		region = r;
		weight = 1.0;
	}

	public void addAllCategories() {
		for (LOTRMusicCategory cat : LOTRMusicCategory.values()) {
			addCategory(cat);
		}
	}

	public void addAllSubregions() {
		List<String> allSubs = region.getAllSubregions();
		if (!allSubs.isEmpty()) {
			for (String sub : allSubs) {
				addSubregion(sub);
			}
		}
	}

	public void addCategory(LOTRMusicCategory cat) {
		if (!categories.contains(cat)) {
			categories.add(cat);
		}
	}

	public void addSubregion(String sub) {
		if (!subregions.contains(sub)) {
			subregions.add(sub);
		}
	}

	public List<LOTRMusicCategory> getCategories() {
		return categories;
	}

	public List<String> getSubregions() {
		return subregions;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double d) {
		weight = d;
	}
}
