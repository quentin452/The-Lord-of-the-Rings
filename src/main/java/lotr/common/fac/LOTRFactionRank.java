package lotr.common.fac;

import lotr.common.*;
import net.minecraft.util.StatCollector;

public class LOTRFactionRank implements Comparable<LOTRFactionRank> {
	public static LOTRFactionRank RANK_NEUTRAL = new Dummy("lotr.faction.rank.neutral");
	public static LOTRFactionRank RANK_ENEMY = new Dummy("lotr.faction.rank.enemy");
	public LOTRFaction fac;
	public float alignment;
	public String name;
	public LOTRAchievementRank rankAchievement;
	public boolean isGendered;
	public LOTRTitle rankTitle;
	public LOTRTitle rankTitleMasc;
	public LOTRTitle rankTitleFem;

	public LOTRFactionRank(LOTRFaction f, float al, String s, boolean gend) {
		fac = f;
		alignment = al;
		name = s;
		isGendered = gend;
	}

	@Override
	public int compareTo(LOTRFactionRank other) {
		if (fac != other.fac) {
			throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
		}
		float al1 = alignment;
		float al2 = other.alignment;
		if (al1 == al2) {
			throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
		}
		return -Float.compare(al1, al2);
	}

	public String getCodeFullName() {
		return getCodeName() + ".f";
	}

	public String getCodeFullNameFem() {
		return getCodeNameFem() + ".f";
	}

	public String getCodeFullNameWithGender(LOTRPlayerData pd) {
		if (isGendered && pd.useFeminineRanks()) {
			return getCodeFullNameFem();
		}
		return getCodeFullName();
	}

	public String getCodeName() {
		return "lotr.faction." + fac.codeName() + ".rank." + name;
	}

	public String getCodeNameFem() {
		return getCodeName() + "_fm";
	}

	public String getDisplayFullName() {
		return StatCollector.translateToLocal(getCodeFullName());
	}

	public String getDisplayFullNameFem() {
		return StatCollector.translateToLocal(getCodeFullNameFem());
	}

	public String getDisplayName() {
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayNameFem() {
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getFullNameWithGender(LOTRPlayerData pd) {
		if (isGendered && pd.useFeminineRanks()) {
			return getDisplayFullNameFem();
		}
		return getDisplayFullName();
	}

	public LOTRAchievementRank getRankAchievement() {
		return rankAchievement;
	}

	public String getShortNameWithGender(LOTRPlayerData pd) {
		if (isGendered && pd.useFeminineRanks()) {
			return getDisplayNameFem();
		}
		return getDisplayName();
	}

	public boolean isAbovePledgeRank() {
		return alignment > fac.getPledgeAlignment();
	}

	public boolean isDummyRank() {
		return false;
	}

	public boolean isGendered() {
		return isGendered;
	}

	public boolean isPledgeRank() {
		return this == fac.getPledgeRank();
	}

	public LOTRFactionRank makeAchievement() {
		rankAchievement = new LOTRAchievementRank(this);
		return this;
	}

	public LOTRFactionRank makeTitle() {
		if (isGendered) {
			rankTitleMasc = new LOTRTitle(this, false);
			rankTitleFem = new LOTRTitle(this, true);
			return this;
		}
		rankTitle = new LOTRTitle(this, false);
		return this;
	}

	public LOTRFactionRank setPledgeRank() {
		fac.setPledgeRank(this);
		return this;
	}

	public static class Dummy extends LOTRFactionRank {
		public Dummy(String s) {
			super(null, 0.0f, s, false);
		}

		@Override
		public String getCodeName() {
			return name;
		}

		@Override
		public String getDisplayFullName() {
			return getDisplayName();
		}

		@Override
		public String getDisplayName() {
			return StatCollector.translateToLocal(name);
		}

		@Override
		public boolean isDummyRank() {
			return true;
		}
	}

}
