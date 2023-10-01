package lotr.common.world.biome.variant;

import java.util.ArrayList;
import java.util.Collection;

public class LOTRBiomeVariantList {
	public float totalWeight;
	public Collection<VariantBucket> variantList = new ArrayList<>();

	public void add(LOTRBiomeVariant v, float f) {
		variantList.add(new VariantBucket(v, totalWeight, totalWeight + f));
		totalWeight += f;
	}

	public void clear() {
		totalWeight = 0.0f;
		variantList.clear();
	}

	public LOTRBiomeVariant get(float index) {
		if (index < 0.0f) {
			index = 0.0f;
		}
		if (index >= 1.0f) {
			index = 0.9999f;
		}
		float f = index * totalWeight;
		for (VariantBucket bucket : variantList) {
			if (f < bucket.min || f >= bucket.max) {
				continue;
			}
			return bucket.variant;
		}
		return null;
	}

	public boolean isEmpty() {
		return totalWeight == 0.0f;
	}

	public static class VariantBucket {
		public LOTRBiomeVariant variant;
		public float min;
		public float max;

		public VariantBucket(LOTRBiomeVariant v, float f0, float f1) {
			variant = v;
			min = f0;
			max = f1;
		}
	}

}
