package lotr.common.quest;

import java.util.*;

import lotr.client.LOTRKeyHandler;
import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestWelcome extends LOTRMiniQuest {
	public static String SPEECHBANK = "char/gandalf/quest";
	public static int STAGE_GET_ITEMS = 1;
	public static int STAGE_READ_BOOK = 2;
	public static int STAGE_EXPLAIN_BOOK = 3;
	public static int STAGE_EXPLAIN_MAP = 4;
	public static int STAGE_OPEN_MAP = 5;
	public static int STAGE_EXPLAIN_FACTIONS = 6;
	public static int STAGE_EXPLAIN_ALIGNMENT = 7;
	public static int STAGE_CYCLE_ALIGNMENT = 8;
	public static int STAGE_CYCLE_REGIONS = 9;
	public static int STAGE_EXPLAIN_FACTION_GUIDE = 10;
	public static int STAGE_OPEN_FACTIONS = 11;
	public static int STAGE_TALK_ADVENTURES = 12;
	public static int STAGE_GET_POUCHES = 13;
	public static int STAGE_TALK_FINAL = 14;
	public static int STAGE_COMPLETE = 15;
	public static int NUM_STAGES = 15;
	public int stage;
	public boolean movedOn;

	public LOTRMiniQuestWelcome(LOTRPlayerData pd) {
		super(pd);
	}

	public LOTRMiniQuestWelcome(LOTRPlayerData pd, LOTREntityGandalf gandalf) {
		this(pd);
		setNPCInfo(gandalf);
		speechBankStart = "";
		speechBankProgress = "";
		speechBankComplete = "";
		speechBankTooMany = "";
		quoteStart = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 2);
		quoteComplete = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 12);
	}

	@Override
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		return !pd.hasAnyGWQuest();
	}

	@Override
	public boolean canRewardVariousExtraItems() {
		return false;
	}

	@Override
	public void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
		super.complete(entityplayer, npc);
		updateGreyWanderer();
		LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.doGreyQuest);
	}

	@Override
	public float getAlignmentBonus() {
		return 0.0f;
	}

	@Override
	public int getCoinBonus() {
		return 0;
	}

	@Override
	public float getCompletionFactor() {
		return stage / 15.0f;
	}

	@Override
	public String getFactionSubtitle() {
		return "";
	}

	@Override
	public String getObjectiveInSpeech() {
		return "OBJECTIVE_SPEECH";
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return "OBJECTIVE_SPEECH_PROGRESSED";
	}

	@Override
	public String getQuestFailure() {
		if (movedOn) {
			return StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.movedOn", entityName);
		}
		return super.getQuestFailure();
	}

	@Override
	public String getQuestFailureShorthand() {
		if (movedOn) {
			return StatCollector.translateToLocal("lotr.gui.redBook.mq.movedOn");
		}
		return super.getQuestFailureShorthand();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(LOTRMod.redBook);
	}

	@Override
	public String getQuestObjective() {
		switch (stage) {
			case 2:
				return StatCollector.translateToLocal("lotr.miniquest.welcome.book");
			case 5: {
				KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
				return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.map", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
			}
			case 8: {
				KeyBinding keyLeft = LOTRKeyHandler.keyBindingAlignmentCycleLeft;
				KeyBinding keyRight = LOTRKeyHandler.keyBindingAlignmentCycleRight;
				return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.align", GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()));
			}
			case 9: {
				KeyBinding keyUp = LOTRKeyHandler.keyBindingAlignmentGroupPrev;
				KeyBinding keyDown = LOTRKeyHandler.keyBindingAlignmentGroupNext;
				return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.alignRegions", GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()));
			}
			case 11: {
				KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
				return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.factions", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
			}
			default:
				break;
		}
		return StatCollector.translateToLocal("lotr.miniquest.welcome.speak");
	}

	@Override
	public String getQuestProgress() {
		return getQuestProgressShorthand();
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", stage, 15);
	}

	@Override
	public void handleEvent(LOTRMiniQuestEvent event) {
		if (event instanceof LOTRMiniQuestEvent.OpenRedBook && stage == 2) {
			stage = 3;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof LOTRMiniQuestEvent.ViewMap && stage == 5) {
			stage = 6;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof LOTRMiniQuestEvent.CycleAlignment && stage == 8) {
			stage = 9;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof LOTRMiniQuestEvent.CycleAlignmentRegion && stage == 9) {
			stage = 10;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof LOTRMiniQuestEvent.ViewFactions && stage == 11) {
			stage = 12;
			updateQuest();
			updateGreyWanderer();
		}
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || movedOn;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
		updateGreyWanderer();
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		switch (stage) {
			case 1: {
				Collection<ItemStack> dropItems = new ArrayList<>();
				dropItems.add(new ItemStack(LOTRMod.redBook));
				npc.dropItemList(dropItems);
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 4);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 2;
				updateQuest();
				break;
			}
			case 2: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 4);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			}
			case 3: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 5);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 4;
				updateQuest();
				break;
			}
			case 4: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 6);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 5;
				updateQuest();
				break;
			}
			case 5: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 6);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			}
			case 6: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 7);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 7;
				updateQuest();
				break;
			}
			case 7: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 8);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 8;
				updateQuest();
				break;
			}
			case 8:
			case 9: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 8);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			}
			case 10: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 9);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 11;
				updateQuest();
				break;
			}
			case 11: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 9);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			}
			case 12: {
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 10);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 13;
				updateQuest();
				break;
			}
			case 13: {
				Collection<ItemStack> dropItems = new ArrayList<>();
				if (!pd.getQuestData().getGivenFirstPouches()) {
					dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
					dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
					dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
					pd.getQuestData().setGivenFirstPouches(true);
				}
				npc.dropItemList(dropItems);
				String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 11);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 14;
				updateQuest();
				break;
			}
			case 14:
				stage = 15;
				updateQuest();
				complete(entityplayer, npc);
				break;
			default:
				break;
		}
	}

	@Override
	public void onPlayerTick(EntityPlayer entityplayer) {
		if (!LOTRGreyWandererTracker.isWandererActive(entityUUID)) {
			movedOn = true;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		stage = nbt.getByte("WStage");
		movedOn = nbt.getBoolean("WMovedOn");
	}

	@Override
	public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
		super.start(entityplayer, npc);
		String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 3);
		sendQuoteSpeech(entityplayer, npc, line);
		quotesStages.add(line);
		stage = 1;
		updateQuest();
		updateGreyWanderer();
	}

	public void updateGreyWanderer() {
		LOTRGreyWandererTracker.setWandererActive(entityUUID);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("WStage", (byte) stage);
		nbt.setBoolean("WMovedOn", movedOn);
	}

	public static boolean[] forceMenu_Map_Factions(EntityPlayer entityplayer) {
		boolean[] flags = {false, false};
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		List<LOTRMiniQuest> activeQuests = pd.getActiveMiniQuests();
		for (LOTRMiniQuest quest : activeQuests) {
			if (!(quest instanceof LOTRMiniQuestWelcome)) {
				continue;
			}
			LOTRMiniQuestWelcome qw = (LOTRMiniQuestWelcome) quest;
			if (qw.stage == 5) {
				flags[0] = true;
				break;
			}
			if (qw.stage != 11) {
				continue;
			}
			flags[1] = true;
			break;
		}
		return flags;
	}
}
