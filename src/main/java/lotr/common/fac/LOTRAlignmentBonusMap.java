package lotr.common.fac;

import java.util.*;

public class LOTRAlignmentBonusMap extends HashMap<LOTRFaction, Float> {
	public Set<LOTRFaction> getChangedFactions() {
		Set<LOTRFaction> changed = EnumSet.noneOf(LOTRFaction.class);
		for (LOTRFaction fac : keySet()) {
			float bonus = get(fac);
			if (bonus == 0.0f) {
				continue;
			}
			changed.add(fac);
		}
		return changed;
	}
}
