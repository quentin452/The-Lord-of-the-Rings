package lotr.common.fellowship;

import com.mojang.authlib.GameProfile;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.StatCollector;

import java.util.Locale;
import java.util.UUID;

public class LOTRFellowshipProfile extends GameProfile {
	public static String fellowshipPrefix = "f/";
	public String fellowshipName;

	public LOTRFellowshipProfile(LOTREntityBanner banner, UUID fsID, String fsName) {
		super(fsID, fsName);
		fellowshipName = fsName;
	}

	public static String addFellowshipCode(String s) {
		return fellowshipPrefix + s;
	}

	public static String getFellowshipCodeHint() {
		return StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.fellowshipHint", fellowshipPrefix);
	}

	public static boolean hasFellowshipCode(String s) {
		return s.toLowerCase(Locale.ROOT).startsWith(fellowshipPrefix.toLowerCase(Locale.ROOT));
	}

	public static String stripFellowshipCode(String s) {
		return s.substring(fellowshipPrefix.length());
	}

	public LOTRFellowship getFellowship() {
		return LOTRFellowshipData.getActiveFellowship(getId());
	}

	public LOTRFellowshipClient getFellowshipClient() {
		return LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer()).getClientFellowshipByName(fellowshipName);
	}

	@Override
	public String getName() {
		return addFellowshipCode(super.getName());
	}
}
