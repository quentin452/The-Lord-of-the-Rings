package lotr.common.playerdetails;

public class ExclusiveGroup {
	public static ExclusiveGroup MOD_TEAM = of("mod_team");
	public static ExclusiveGroup CREATOR = of("creator");
	public static ExclusiveGroup VICEGERENT = of("vicegerent");
	public static ExclusiveGroup GRUK = of("gruk");
	public static ExclusiveGroup BOYD = of("boyd");
	public static ExclusiveGroup BAT = of("bat");
	public static ExclusiveGroup ELECTRICIAN = of("electrician");
	public static ExclusiveGroup OGRE_FINDER = of("ogre_finder");
	public static ExclusiveGroup TRANSLATOR = of("translator");
	public static ExclusiveGroup PATRON = of("patron");
	public static ExclusiveGroup BOWING_ELVES = of("bowing_elves");
	public static ExclusiveGroup BUILD_CONTEST_1_ELVEN = of("build_contest_1_elven");
	public static ExclusiveGroup BUILD_CONTEST_2_EVIL = of("build_contest_2_evil");
	public static ExclusiveGroup BUILD_CONTEST_3_SHIRE = of("build_contest_3_shire");
	public static ExclusiveGroup BUILD_CONTEST_4_GONDOR = of("build_contest_4_gondor");
	public static ExclusiveGroup BUILD_CONTEST_5_HARAD = of("build_contest_5_harad");
	public static ExclusiveGroup BUILD_CONTEST_6_RHUN = of("build_contest_6_rhun");
	public static ExclusiveGroup BUILD_CONTEST_7_RENEWED_ENTRANT = of("build_contest_7_renewed_entrant");
	public static ExclusiveGroup BUILD_CONTEST_7_RENEWED_WINNER = of("build_contest_7_renewed_winner");
	public static ExclusiveGroup STRUCTURE_CONTEST_1 = of("structure_contest_1");
	public static ExclusiveGroup LOREMASTER_2013 = of("loremaster_2013");
	public static ExclusiveGroup LOREMASTER_2014 = of("loremaster_2014");
	public static ExclusiveGroup LOREMASTER_2015 = of("loremaster_2015");
	public static ExclusiveGroup LOREMASTER_2016 = of("loremaster_2016");
	public String name;

	public ExclusiveGroup(String name) {
		this.name = name;
	}

	public static ExclusiveGroup of(String name) {
		return new ExclusiveGroup(name);
	}

	public String getName() {
		return name;
	}
}
