package lotr.common;

import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class LOTRAchievementRank extends LOTRAchievement {
	public LOTRFactionRank theRank;
	public LOTRFaction theFac;

	public LOTRAchievementRank(LOTRFactionRank rank) {
		super(rank.fac.getAchieveCategory(), rank.fac.getAchieveCategory().getNextRankAchID(), LOTRMod.goldRing, "alignment_" + rank.fac.codeName() + "_" + rank.alignment);
		theRank = rank;
		theFac = theRank.fac;
		setRequiresAlly(theFac);
		setSpecial();
	}

	@Override
	public boolean canPlayerEarn(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		float align = pd.getAlignment(theFac);
		if (align < 0.0f) {
			return false;
		}
		return !requiresPledge() || pd.isPledgedTo(theFac);
	}

	@Override
	public String getDescription(EntityPlayer entityplayer) {
		String suffix = requiresPledge() ? "achieveRankPledge" : "achieveRank";
		return StatCollector.translateToLocalFormatted("lotr.faction." + theFac.codeName() + "." + suffix, LOTRAlignmentValues.formatAlignForDisplay(theRank.alignment));
	}

	@Override
	public String getUntranslatedTitle(EntityPlayer entityplayer) {
		return theRank.getCodeFullNameWithGender(LOTRLevelData.getData(entityplayer));
	}

	public boolean isPlayerRequiredRank(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		float align = pd.getAlignment(theFac);
		float rankAlign = theRank.alignment;
		if (requiresPledge() && !pd.isPledgedTo(theFac)) {
			return false;
		}
		return align >= rankAlign;
	}

	public boolean requiresPledge() {
		return theRank.isAbovePledgeRank();
	}
}
