package lotr.common;

import net.minecraft.util.ResourceLocation;

public class LOTRCapes {
	public static ResourceLocation GONDOR = forName("gondor");
	public static ResourceLocation TOWER_GUARD = forName("gondorTowerGuard");
	public static ResourceLocation RANGER = forName("ranger");
	public static ResourceLocation RANGER_ITHILIEN = forName("ranger_ithilien");
	public static ResourceLocation LOSSARNACH = forName("lossarnach");
	public static ResourceLocation PELARGIR = forName("pelargir");
	public static ResourceLocation BLACKROOT = forName("blackroot");
	public static ResourceLocation PINNATH_GELIN = forName("pinnathGelin");
	public static ResourceLocation LAMEDON = forName("lamedon");
	public static ResourceLocation ROHAN = forName("rohan");
	public static ResourceLocation DALE = forName("dale");
	public static ResourceLocation DUNLENDING_BERSERKER = forName("dunlendingBerserker");
	public static ResourceLocation GALADHRIM = forName("galadhrim");
	public static ResourceLocation GALADHRIM_TRADER = forName("galadhrimTrader");
	public static ResourceLocation WOOD_ELF = forName("woodElf");
	public static ResourceLocation HIGH_ELF = forName("highElf");
	public static ResourceLocation RIVENDELL = forName("rivendell");
	public static ResourceLocation RIVENDELL_TRADER = forName("rivendellTrader");
	public static ResourceLocation NEAR_HARAD = forName("nearHarad");
	public static ResourceLocation SOUTHRON_CHAMPION = forName("haradChampion");
	public static ResourceLocation GULF_HARAD = forName("gulf");
	public static ResourceLocation TAURETHRIM = forName("taurethrim");
	public static ResourceLocation GALADHRIM_SMITH = forName("galadhrimSmith");
	public static ResourceLocation DORWINION_CAPTAIN = forName("dorwinionCaptain");
	public static ResourceLocation DORWINION_ELF_CAPTAIN = forName("dorwinionElfCaptain");
	public static ResourceLocation GANDALF = forName("gandalf");
	public static ResourceLocation GANDALF_SANTA = forName("santa");

	public static ResourceLocation forName(String s) {
		return new ResourceLocation("lotr:cape/" + s + ".png");
	}
}
