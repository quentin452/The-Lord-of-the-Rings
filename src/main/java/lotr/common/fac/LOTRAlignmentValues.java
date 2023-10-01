package lotr.common.fac;

import lotr.common.util.LOTRLog;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class LOTRAlignmentValues {
	public static float MAX_ALIGNMENT = 10000.0f;
	public static AlignmentBonus MARRIAGE_BONUS = new AlignmentBonus(5.0f, "lotr.alignment.marriage");
	public static AlignmentBonus FANGORN_TREE_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.cutFangornTree");
	public static AlignmentBonus ROHAN_HORSE_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.killRohanHorse");
	public static AlignmentBonus VINEYARD_STEAL_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.vineyardSteal");
	public static AlignmentBonus PICKPOCKET_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.pickpocket");
	public static DecimalFormat alignFormat = new DecimalFormat(",##0.0");
	public static DecimalFormat conqFormat = new DecimalFormat(",##0.00");
	public static DecimalFormatSymbols alignFormatSymbols = new DecimalFormatSymbols();

	public static AlignmentBonus createMiniquestBonus(float alignment) {
		return new AlignmentBonus(alignment, "lotr.alignment.miniQuest");
	}

	public static AlignmentBonus createPledgePenalty(float alignment) {
		return new AlignmentBonus(alignment, "lotr.alignment.breakPledge");
	}

	public static String formatAlignForDisplay(float alignment) {
		return formatAlignForDisplay(alignment, alignFormat, true);
	}

	public static String formatAlignForDisplay(float alignment, DecimalFormat dFormat, boolean prefixPlus) {
		setupDecimalFormat(dFormat);
		String s = dFormat.format(alignment);
		if (prefixPlus && !(!s.isEmpty() && s.charAt(0) == '-')) {
			s = "+" + s;
		}
		return s;
	}

	public static String formatConqForDisplay(float conq, boolean prefixPlus) {
		return formatAlignForDisplay(conq, conqFormat, prefixPlus);
	}

	public static void notifyAlignmentNotHighEnough(ICommandSender entityplayer, float alignmentRequired, LOTRFaction faction) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment", componentAlignReq, faction.factionName()));
	}

	public static void notifyAlignmentNotHighEnough(ICommandSender entityplayer, float alignmentRequired, LOTRFaction faction1, LOTRFaction faction2) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment2", componentAlignReq, faction1.factionName(), faction2.factionName()));
	}

	public static void notifyAlignmentNotHighEnough(ICommandSender entityplayer, float alignmentRequired, LOTRFaction faction1, LOTRFaction faction2, LOTRFaction faction3) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment3", componentAlignReq, faction1.factionName(), faction2.factionName(), faction3.factionName()));
	}

	public static void notifyMiniQuestsNeeded(ICommandSender entityplayer, LOTRFaction faction) {
		entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.requireMiniQuest", faction.factionName()));
	}

	public static float parseDisplayedAlign(String alignmentText) {
		DecimalFormat dFormat = alignFormat;
		setupDecimalFormat(dFormat);
		if (!alignmentText.isEmpty() && alignmentText.charAt(0) == '+') {
			alignmentText = alignmentText.substring("+".length());
		}
		try {
			return dFormat.parse(alignmentText).floatValue();
		} catch (ParseException e) {
			LOTRLog.logger.error("Could not parse alignment value from display string " + alignmentText);
			e.printStackTrace();
			return 0.0f;
		}
	}

	public static void setupDecimalFormat(DecimalFormat dFormat) {
		String groupSeparator;
		char decimalSeparatorChar = '.';
		char groupSeparatorChar = ',';
		String decimalSeparator = StatCollector.translateToLocal("lotr.alignment.decimal_separator_char");
		if (decimalSeparator.length() == 1) {
			decimalSeparatorChar = decimalSeparator.charAt(0);
		}
		if ((groupSeparator = StatCollector.translateToLocal("lotr.alignment.group_separator_char")).length() == 1) {
			groupSeparatorChar = groupSeparator.charAt(0);
		}
		alignFormatSymbols.setDecimalSeparator(decimalSeparatorChar);
		alignFormatSymbols.setGroupingSeparator(groupSeparatorChar);
		dFormat.setDecimalFormatSymbols(alignFormatSymbols);
	}

	public static class AlignmentBonus {
		public float bonus;
		public String name;
		public boolean needsTranslation = true;
		public boolean isKill;
		public boolean killByHiredUnit;
		public boolean isCivilianKill;

		public AlignmentBonus(float f, String s) {
			bonus = f;
			name = s;
		}

		public static float scalePenalty(float penalty, float alignment) {
			if (alignment > 0.0f && penalty < 0.0f) {
				float factor = alignment / 50.0f;
				factor = MathHelper.clamp_float(factor, 1.0f, 20.0f);
				penalty *= factor;
			}
			return penalty;
		}
	}

	public static class Bonuses {
		public static float HOBBIT = 1.0f;
		public static float HOBBIT_BOUNDER = 2.0f;
		public static float HOBBIT_SHIRRIFF = 5.0f;
		public static float HOBBIT_BARTENDER = 2.0f;
		public static float HOBBIT_ORCHARDER = 2.0f;
		public static float HOBBIT_FARMER = 2.0f;
		public static float DARK_HUORN = 1.0f;
		public static float BREE_MAN = 1.0f;
		public static float BREE_GUARD = 2.0f;
		public static float BREE_CAPTAIN = 5.0f;
		public static float BREE_BLACKSMITH = 2.0f;
		public static float BREE_INNKEEPER = 2.0f;
		public static float BREE_HOBBIT = 1.0f;
		public static float BREE_MARKET_TRADER = 2.0f;
		public static float BREE_FARMER = 2.0f;
		public static float DUNEDAIN = 1.0f;
		public static float RANGER_NORTH = 2.0f;
		public static float RANGER_NORTH_CAPTAIN = 5.0f;
		public static float DUNEDAIN_BLACKSMITH = 2.0f;
		public static float BLUE_DWARF = 1.0f;
		public static float BLUE_DWARF_WARRIOR = 2.0f;
		public static float BLUE_DWARF_COMMANDER = 5.0f;
		public static float BLUE_DWARF_MINER = 2.0f;
		public static float BLUE_DWARF_MERCHANT = 2.0f;
		public static float BLUE_DWARF_SMITH = 2.0f;
		public static float HIGH_ELF = 1.0f;
		public static float HIGH_ELF_WARRIOR = 2.0f;
		public static float HIGH_ELF_LORD = 5.0f;
		public static float HIGH_ELF_SMITH = 2.0f;
		public static float RIVENDELL_TRADER = 2.0f;
		public static float GUNDABAD_ORC = 1.0f;
		public static float GUNDABAD_ORC_MERCENARY_CAPTAIN = 5.0f;
		public static float GUNDABAD_WARG = 2.0f;
		public static float GUNDABAD_URUK = 2.0f;
		public static float GUNDABAD_ORC_TRADER = 2.0f;
		public static float ANGMAR_ORC = 1.0f;
		public static float ANGMAR_ORC_WARRIOR = 2.0f;
		public static float ANGMAR_ORC_MERCENARY_CAPTAIN = 5.0f;
		public static float ANGMAR_WARG = 2.0f;
		public static float ANGMAR_ORC_TRADER = 2.0f;
		public static float ANGMAR_HILLMAN = 1.0f;
		public static float ANGMAR_HILLMAN_WARRIOR = 2.0f;
		public static float ANGMAR_HILLMAN_CHIEFTAIN = 5.0f;
		public static float TROLL = 3.0f;
		public static float MOUNTAIN_TROLL = 4.0f;
		public static float SNOW_TROLL = 3.0f;
		public static float BARROW_WIGHT = 2.0f;
		public static float WOOD_ELF = 1.0f;
		public static float WOOD_ELF_WARRIOR = 2.0f;
		public static float WOOD_ELF_CAPTAIN = 5.0f;
		public static float WOOD_ELF_SMITH = 2.0f;
		public static float MIRKWOOD_SPIDER = 1.0f;
		public static float DOL_GULDUR_ORC = 1.0f;
		public static float DOL_GULDUR_CAPTAIN = 5.0f;
		public static float MIRK_TROLL = 4.0f;
		public static float DOL_GULDUR_ORC_TRADER = 2.0f;
		public static float DALE_MAN = 1.0f;
		public static float DALE_MILITIA = 2.0f;
		public static float DALE_SOLDIER = 2.0f;
		public static float DALE_CAPTAIN = 5.0f;
		public static float DALE_BLACKSMITH = 2.0f;
		public static float DALE_BAKER = 2.0f;
		public static float DALE_MERCHANT = 2.0f;
		public static float DWARF = 1.0f;
		public static float DWARF_WARRIOR = 2.0f;
		public static float DWARF_COMMANDER = 5.0f;
		public static float DWARF_MINER = 2.0f;
		public static float DWARF_MERCHANT = 2.0f;
		public static float DWARF_SMITH = 2.0f;
		public static float GALADHRIM = 1.0f;
		public static float GALADHRIM_WARRIOR = 2.0f;
		public static float GALADHRIM_LORD = 5.0f;
		public static float GALADHRIM_TRADER = 2.0f;
		public static float GALADHRIM_SMITH = 2.0f;
		public static float DUNLENDING = 1.0f;
		public static float DUNLENDING_WARRIOR = 2.0f;
		public static float DUNLENDING_WARLORD = 5.0f;
		public static float DUNLENDING_BARTENDER = 2.0f;
		public static float ENT = 3.0f;
		public static float HUORN = 2.0f;
		public static float ROHIRRIM = 1.0f;
		public static float ROHIRRIM_WARRIOR = 2.0f;
		public static float ROHIRRIM_MARSHAL = 5.0f;
		public static float ROHAN_BLACKSMITH = 2.0f;
		public static float ROHAN_MEADHOST = 2.0f;
		public static float ROHAN_FARMER = 2.0f;
		public static float ROHAN_MARKET_TRADER = 2.0f;
		public static float ISENGARD_SNAGA = 1.0f;
		public static float URUK_HAI = 2.0f;
		public static float URUK_HAI_MERCENARY_CAPTAIN = 5.0f;
		public static float URUK_HAI_TRADER = 2.0f;
		public static float URUK_WARG = 2.0f;
		public static float GONDOR_MAN = 1.0f;
		public static float GONDOR_MILITIA = 2.0f;
		public static float GONDOR_SOLDIER = 2.0f;
		public static float GONDOR_CAPTAIN = 5.0f;
		public static float GONDOR_BLACKSMITH = 2.0f;
		public static float RANGER_ITHILIEN = 2.0f;
		public static float RANGER_ITHILIEN_CAPTAIN = 5.0f;
		public static float SWAN_KNIGHT = 2.0f;
		public static float DOL_AMROTH_CAPTAIN = 5.0f;
		public static float GONDOR_FARMER = 2.0f;
		public static float GONDOR_BARTENDER = 2.0f;
		public static float GONDOR_MARKET_TRADER = 2.0f;
		public static float MORDOR_ORC = 1.0f;
		public static float MORDOR_ORC_MERCENARY_CAPTAIN = 5.0f;
		public static float BLACK_URUK_CAPTAIN = 5.0f;
		public static float MORDOR_ORC_TRADER = 2.0f;
		public static float MORDOR_ORC_SLAVER = 2.0f;
		public static float MORDOR_ORC_SPIDER_KEEPER = 5.0f;
		public static float MORDOR_WARG = 2.0f;
		public static float OLOG_HAI = 4.0f;
		public static float MORDOR_SPIDER = 1.0f;
		public static float WICKED_DWARF = 2.0f;
		public static float BLACK_URUK = 2.0f;
		public static float DORWINION_MAN = 1.0f;
		public static float DORWINION_GUARD = 2.0f;
		public static float DORWINION_CAPTAIN = 5.0f;
		public static float DORWINION_ELF = 2.0f;
		public static float DORWINION_ELF_WARRIOR = 3.0f;
		public static float DORWINION_ELF_CAPTAIN = 5.0f;
		public static float DORWINION_ELF_VINTNER = 2.0f;
		public static float DORWINION_FARMER = 2.0f;
		public static float DORWINION_MERCHANT = 2.0f;
		public static float EASTERLING = 1.0f;
		public static float EASTERLING_WARRIOR = 2.0f;
		public static float EASTERLING_BLACKSMITH = 2.0f;
		public static float EASTERLING_WARLORD = 5.0f;
		public static float EASTERLING_MARKET_TRADER = 2.0f;
		public static float EASTERLING_BARTENDER = 2.0f;
		public static float EASTERLING_FARMER = 2.0f;
		public static float NEAR_HARADRIM = 1.0f;
		public static float NEAR_HARADRIM_WARRIOR = 2.0f;
		public static float NEAR_HARADRIM_WARLORD = 5.0f;
		public static float NEAR_HARADRIM_TRADER = 2.0f;
		public static float NEAR_HARADRIM_BARTENDER = 2.0f;
		public static float NEAR_HARADRIM_BLACKSMITH = 2.0f;
		public static float NEAR_HARADRIM_FARMER = 2.0f;
		public static float MOREDAIN = 1.0f;
		public static float MOREDAIN_WARRIOR = 2.0f;
		public static float MOREDAIN_CHIEFTAIN = 5.0f;
		public static float MOREDAIN_TRADER = 2.0f;
		public static float TAUREDAIN = 1.0f;
		public static float TAUREDAIN_WARRIOR = 2.0f;
		public static float TAUREDAIN_CHIEFTAIN = 5.0f;
		public static float TAUREDAIN_TRADER = 2.0f;
		public static float TAUREDAIN_FARMER = 2.0f;
		public static float HALF_TROLL = 1.0f;
		public static float HALF_TROLL_WARRIOR = 2.0f;
		public static float HALF_TROLL_WARLORD = 5.0f;
		public static float HALF_TROLL_SCAVENGER = 2.0f;
		public static float MOUNTAIN_TROLL_CHIEFTAIN = 50.0f;
		public static float MALLORN_ENT = 50.0f;
	}

	public static class Levels {
		public static float USE_TABLE = 1.0f;
		public static float USE_PORTAL = 1.0f;
		public static float CONQUEST_HORN = 1500.0f;
		public static float HOBBIT_MARRY = 100.0f;
		public static float HOBBIT_CHILD_FOLLOW = 200.0f;
		public static float HOBBIT_SHIRRIFF_TRADE = 50.0f;
		public static float HOBBIT_FLEE = -100.0f;
		public static float HOBBIT_FARMER_TRADE;
		public static float BREE_CAPTAIN_TRADE = 100.0f;
		public static float BREE_BLACKSMITH_TRADE = 50.0f;
		public static float BREE_MARKET_TRADE;
		public static float BREE_FARMER_TRADE;
		public static float RANGER_NORTH_CAPTAIN_TRADE = 300.0f;
		public static float DUNEDAIN_BLACKSMITH_TRADE = 50.0f;
		public static float BLUE_DWARF_MINER_TRADE = 100.0f;
		public static float BLUE_DWARF_COMMANDER_TRADE = 200.0f;
		public static float BLUE_DWARF_MERCHANT_TRADE;
		public static float BLUE_DWARF_SMITH_TRADE = 100.0f;
		public static float HIGH_ELF_LORD_TRADE = 300.0f;
		public static float HIGH_ELF_SMITH_TRADE = 100.0f;
		public static float RIVENDELL_TRADER_TRADE = 75.0f;
		public static float TROLL_TRUST = 100.0f;
		public static float WOOD_ELF_CAPTAIN_TRADE = 250.0f;
		public static float WOOD_ELF_SMITH_TRADE = 100.0f;
		public static float DALE_CAPTAIN_TRADE = 100.0f;
		public static float DALE_BLACKSMITH_TRADE = 50.0f;
		public static float DALE_BAKER_TRADE;
		public static float DALE_MERCHANT_TRADE;
		public static float DWARF_MINER_TRADE = 100.0f;
		public static float DWARF_COMMANDER_TRADE = 200.0f;
		public static float DWARF_MARRY = 200.0f;
		public static float DWARF_MERCHANT_TRADE;
		public static float DWARF_SMITH_TRADE = 100.0f;
		public static float GALADHRIM_TRADER_TRADE = 75.0f;
		public static float GALADHRIM_LORD_TRADE = 300.0f;
		public static float GALADHRIM_SMITH_TRADE = 100.0f;
		public static float ROHIRRIM_MARSHAL_TRADE = 150.0f;
		public static float ROHAN_BLACKSMITH_TRADE = 50.0f;
		public static float ROHAN_SHIELDMAIDEN = 150.0f;
		public static float ROHAN_FARMER_TRADE;
		public static float ROHAN_MARKET_TRADE;
		public static float ROHAN_STABLE_TRADE = 50.0f;
		public static float DUNLENDING_WARLORD_TRADE = 100.0f;
		public static float SPAWN_HUORN = 500.0f;
		public static float GONDOR_BLACKSMITH_TRADE = 50.0f;
		public static float GONDORIAN_CAPTAIN_TRADE = 200.0f;
		public static float RANGER_ITHILIEN_CAPTAIN_TRADE = 300.0f;
		public static float DOL_AMROTH_CAPTAIN_TRADE = 200.0f;
		public static float LOSSARNACH_CAPTAIN_TRADE = 150.0f;
		public static float PELARGIR_CAPTAIN_TRADE = 200.0f;
		public static float PINNATH_GELIN_CPTAIN_TRADE = 200.0f;
		public static float BLACKROOT_CAPTAIN_TRADE = 150.0f;
		public static float GONDOR_FARMER_TRADE;
		public static float LEBENNIN_CAPTAIN_TRADE = 150.0f;
		public static float GONDOR_MARKET_TRADE;
		public static float LAMEDON_CAPTAIN_TRADE = 200.0f;
		public static float ORC_FLEE = -500.0f;
		public static float ORC_FRIENDLY = 100.0f;
		public static float MORDOR_TRUST = 100.0f;
		public static float MORDOR_ORC_TRADER_TRADE = 100.0f;
		public static float MORDOR_ORC_MERCENARY_CAPTAIN_TRADE = 150.0f;
		public static float BLACK_URUK_CAPTAIN_TRADE = 400.0f;
		public static float MORDOR_SPIDER_KEEPER_TRADE = 250.0f;
		public static float MORDOR_ORC_SLAVER_TRADE = 200.0f;
		public static float MORGUL_FLOWERS = 250.0f;
		public static float WICKED_DWARF_TRADE = 100.0f;
		public static float ANGMAR_ORC_MERCENARY_CAPTAIN_TRADE = 150.0f;
		public static float ANGMAR_ORC_TRADER_TRADE = 100.0f;
		public static float ANGMAR_HILLMAN_CHIEFTAIN_TRADE = 100.0f;
		public static float GUNDABAD_ORC_MERCENARY_CAPTAIN_TRADE = 100.0f;
		public static float GUNDABAD_ORC_TRADER_TRADE = 50.0f;
		public static float URUK_HAI_TRADER_TRADE = 100.0f;
		public static float URUK_HAI_MERCENARY_CAPTAIN_TRADE = 150.0f;
		public static float WARG_RIDE = 50.0f;
		public static float SPIDER_RIDE = 50.0f;
		public static float DOL_GULDUR_CAPTAIN_TRADE = 150.0f;
		public static float DOL_GULDUR_ORC_TRADER_TRADE = 100.0f;
		public static float DORWINION_CAPTAIN_TRADE = 150.0f;
		public static float DORWINION_ELF_CAPTAIN_TRADE = 250.0f;
		public static float DORWINION_ELF_VINTNER_TRADE = 50.0f;
		public static float DORWINION_VINEKEEPER_TRADE;
		public static float DORWINION_VINEYARD_ALLOW = 2000.0f;
		public static float DORWINION_MERCHANT_TRADE;
		public static float EASTERLING_BLACKSMITH_TRADE = 50.0f;
		public static float EASTERLING_WARLORD_TRADE = 150.0f;
		public static float EASTERLING_MARKET_TRADE;
		public static float EASTERLING_FARMER_TRADE;
		public static float NEAR_HARADRIM_WARLORD_TRADE = 150.0f;
		public static float HARNEDOR_WARLORD_TRADE = 150.0f;
		public static float UMBAR_CAPTAIN_TRADE = 150.0f;
		public static float CORSAIR_CAPTAIN_TRADE = 150.0f;
		public static float CORSAIR_SLAVER_TRADE;
		public static float NOMAD_WARLORD_TRADE = 150.0f;
		public static float GULF_WARLORD_TRADE = 150.0f;
		public static float NEAR_HARAD_MERCHANT_TRADE;
		public static float NOMAD_MERCHANT_TRADE;
		public static float NEAR_HARAD_BAZAAR_TRADE;
		public static float NEAR_HARAD_BLACKSMITH_TRADE = 50.0f;
		public static float MOREDAIN_MERCENARY_TRADE;
		public static float GONDOR_RENEGADE = 50.0f;
		public static float NEAR_HARAD_FARMER_TRADE;
		public static float MOREDAIN_CHIEFTAIN_TRADE = 150.0f;
		public static float MOREDAIN_VILLAGE_TRADE;
		public static float TAUREDAIN_CHIEFTAIN_TRADE = 200.0f;
		public static float TAUREDAIN_SHAMAN_TRADE = 100.0f;
		public static float TAUREDAIN_FARMER_TRADE;
		public static float TAUREDAIN_SMITH_TRADE = 50.0f;
		public static float HALF_TROLL_WARLORD_TRADE = 200.0f;
		public static float HALF_TROLL_SCAVENGER_TRADE = 50.0f;
	}

}
