package lotr.common;

import lotr.common.fac.LOTRFaction;
import lotr.common.playerdetails.ExclusiveGroup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum LOTRShields {
	ALIGNMENT_BREE(LOTRFaction.BREE), ALIGNMENT_RANGER(LOTRFaction.RANGER_NORTH), ALIGNMENT_BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS), ALIGNMENT_HIGH_ELF(LOTRFaction.HIGH_ELF), ALIGNMENT_RIVENDELL(LOTRFaction.HIGH_ELF), ALIGNMENT_GUNDABAD(LOTRFaction.GUNDABAD), ALIGNMENT_ANGMAR(LOTRFaction.ANGMAR), ALIGNMENT_WOOD_ELF(LOTRFaction.WOOD_ELF), ALIGNMENT_DOL_GULDUR(LOTRFaction.DOL_GULDUR), ALIGNMENT_DALE(LOTRFaction.DALE), ALIGNMENT_ESGAROTH(LOTRFaction.DALE), ALIGNMENT_DWARF(LOTRFaction.DURINS_FOLK), ALIGNMENT_GALADHRIM(LOTRFaction.LOTHLORIEN), ALIGNMENT_DUNLAND(LOTRFaction.DUNLAND), ALIGNMENT_URUK_HAI(LOTRFaction.ISENGARD), ALIGNMENT_ROHAN(LOTRFaction.ROHAN), ALIGNMENT_GONDOR(LOTRFaction.GONDOR), ALIGNMENT_DOL_AMROTH(LOTRFaction.GONDOR), ALIGNMENT_LOSSARNACH(LOTRFaction.GONDOR), ALIGNMENT_LEBENNIN(LOTRFaction.GONDOR), ALIGNMENT_PELARGIR(LOTRFaction.GONDOR), ALIGNMENT_BLACKROOT_VALE(LOTRFaction.GONDOR), ALIGNMENT_PINNATH_GELIN(LOTRFaction.GONDOR), ALIGNMENT_LAMEDON(LOTRFaction.GONDOR), ALIGNMENT_MORDOR(LOTRFaction.MORDOR), ALIGNMENT_MINAS_MORGUL(LOTRFaction.MORDOR), ALIGNMENT_BLACK_URUK(LOTRFaction.MORDOR), ALIGNMENT_DORWINION(LOTRFaction.DORWINION), ALIGNMENT_DORWINION_ELF(LOTRFaction.DORWINION), ALIGNMENT_RHUN(LOTRFaction.RHUDEL), ALIGNMENT_HARNEDOR(LOTRFaction.NEAR_HARAD), ALIGNMENT_NEAR_HARAD(LOTRFaction.NEAR_HARAD), ALIGNMENT_UMBAR(LOTRFaction.NEAR_HARAD), ALIGNMENT_CORSAIR(LOTRFaction.NEAR_HARAD), ALIGNMENT_GULF(LOTRFaction.NEAR_HARAD), ALIGNMENT_MOREDAIN(LOTRFaction.MORWAITH), ALIGNMENT_TAUREDAIN(LOTRFaction.TAURETHRIM), ALIGNMENT_HALF_TROLL(LOTRFaction.HALF_TROLL), ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_MITHRIL, ALCOHOLIC, DEFEAT_MTC, DEFEAT_MALLORN_ENT, ELVEN_CONTEST(ExclusiveGroup.BUILD_CONTEST_1_ELVEN), EVIL_CONTEST(ExclusiveGroup.BUILD_CONTEST_2_EVIL), SHIRE_CONTEST(ExclusiveGroup.BUILD_CONTEST_3_SHIRE), GONDOR_CONTEST(ExclusiveGroup.BUILD_CONTEST_4_GONDOR), HARAD_CONTEST(ExclusiveGroup.BUILD_CONTEST_5_HARAD), RHUN_CONTEST(ExclusiveGroup.BUILD_CONTEST_6_RHUN), STRUCTURE_CONTEST(ExclusiveGroup.STRUCTURE_CONTEST_1), RENEWED_CONTEST(ExclusiveGroup.BUILD_CONTEST_7_RENEWED_WINNER), MOD(ExclusiveGroup.MOD_TEAM), GRUK(true, ExclusiveGroup.GRUK), BOYD(true, ExclusiveGroup.BOYD), ELECTRICIAN(true, ExclusiveGroup.ELECTRICIAN), OGRE(ExclusiveGroup.OGRE_FINDER), TRANSLATOR(ExclusiveGroup.TRANSLATOR), PATRON(ExclusiveGroup.PATRON), LOREMASTER_2013(false, ExclusiveGroup.LOREMASTER_2013), LOREMASTER_2014(false, ExclusiveGroup.LOREMASTER_2014), LOREMASTER_2015(false, ExclusiveGroup.LOREMASTER_2015), LOREMASTER_2016(false, ExclusiveGroup.LOREMASTER_2016);

	public ShieldType shieldType;
	public int shieldID;
	public ResourceLocation shieldTexture;
	public boolean isHidden;
	public LOTRFaction alignmentFaction;
	public ExclusiveGroup exclusiveGroup;

	LOTRShields() {
		this(ShieldType.ACHIEVABLE, false, null, null);
	}

	LOTRShields(boolean hidden, ExclusiveGroup group) {
		this(ShieldType.EXCLUSIVE, hidden, null, group);
	}

	LOTRShields(ExclusiveGroup group) {
		this(false, group);
	}

	LOTRShields(LOTRFaction faction) {
		this(ShieldType.ALIGNMENT, false, faction, null);
	}

	LOTRShields(ShieldType type, boolean hidden, LOTRFaction faction, ExclusiveGroup group) {
		shieldType = type;
		shieldID = shieldType.list.size();
		shieldType.list.add(this);
		shieldTexture = new ResourceLocation("lotr:shield/" + name().toLowerCase(Locale.ROOT) + ".png");
		isHidden = hidden;
		alignmentFaction = faction;
		exclusiveGroup = group;
	}

	@SuppressWarnings("all")
	public static void forceClassLoad() {
	}

	public static LOTRShields shieldForName(String shieldName) {
		for (LOTRShields shield : values()) {
			if (!shield.name().equals(shieldName)) {
				continue;
			}
			return shield;
		}
		return null;
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (shieldType == ShieldType.ALIGNMENT) {
			return LOTRLevelData.getData(entityplayer).getAlignment(alignmentFaction) >= 1000.0f;
		}
		if (shieldType == ShieldType.EXCLUSIVE) {
			return LOTRMod.playerDetailsCache.getPlayerDetails(entityplayer).hasExclusiveGroup(exclusiveGroup);
		}
		if (this == ACHIEVEMENT_BRONZE) {
			return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 25;
		}
		if (this == ACHIEVEMENT_SILVER) {
			return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 50;
		}
		if (this == ACHIEVEMENT_GOLD) {
			return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 100;
		}
		if (this == ACHIEVEMENT_MITHRIL) {
			return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 200;
		}
		if (this == ALCOHOLIC) {
			return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.gainHighAlcoholTolerance);
		}
		if (this == DEFEAT_MTC) {
			return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.killMountainTrollChieftain);
		}
		if (this == DEFEAT_MALLORN_ENT) {
			return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.killMallornEnt);
		}
		return false;
	}

	public String getShieldDesc() {
		return StatCollector.translateToLocal("lotr.shields." + name() + ".desc");
	}

	public int getShieldId() {
		return shieldID;
	}

	public String getShieldName() {
		return StatCollector.translateToLocal("lotr.shields." + name() + ".name");
	}

	public ShieldType getShieldType() {
		return shieldType;
	}

	public ResourceLocation getTexture() {
		return shieldTexture;
	}

	public enum ShieldType {
		ALIGNMENT, ACHIEVABLE, EXCLUSIVE;

		public List<LOTRShields> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("lotr.shields.category." + name());
		}
	}

}
