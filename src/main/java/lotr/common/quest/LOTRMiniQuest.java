package lotr.common.quest;

import java.awt.Color;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.FMLLog;
import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.fac.*;
import lotr.common.item.*;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRMiniQuest {
	public static Map<String, Class<? extends LOTRMiniQuest>> nameToQuestMapping = new HashMap<>();
	public static Map<Class<? extends LOTRMiniQuest>, String> questToNameMapping = new HashMap<>();
	public static int MAX_MINIQUESTS_PER_FACTION;
	public static double RENDER_HEAD_DISTANCE;
	public static float defaultRewardFactor = 1.0f;

	static {
		registerQuestType("Collect", LOTRMiniQuestCollect.class);
		registerQuestType("KillFaction", LOTRMiniQuestKillFaction.class);
		registerQuestType("KillEntity", LOTRMiniQuestKillEntity.class);
		registerQuestType("Bounty", LOTRMiniQuestBounty.class);
		registerQuestType("Welcome", LOTRMiniQuestWelcome.class);
		registerQuestType("Pickpocket", LOTRMiniQuestPickpocket.class);
		MAX_MINIQUESTS_PER_FACTION = 5;
		RENDER_HEAD_DISTANCE = 12.0;
	}

	public LOTRMiniQuestFactory questGroup;
	public LOTRPlayerData playerData;
	public UUID questUUID;
	public UUID entityUUID;
	public String entityName;
	public String entityNameFull;
	public LOTRFaction entityFaction;
	public int questColor;
	public int dateGiven;
	public LOTRBiome biomeGiven;
	public float rewardFactor = 1.0f;
	public boolean willHire;
	public float hiringAlignment;
	public List<ItemStack> rewardItemTable = new ArrayList<>();
	public boolean completed;
	public int dateCompleted;
	public int coinsRewarded;
	public float alignmentRewarded;
	public boolean wasHired;
	public Collection<ItemStack> itemsRewarded = new ArrayList<>();
	public boolean entityDead;
	public Pair<ChunkCoordinates, Integer> lastLocation;
	public String speechBankStart;
	public String speechBankProgress;
	public String speechBankComplete;
	public String speechBankTooMany;
	public String quoteStart;
	public String quoteComplete;

	public List<String> quotesStages = new ArrayList<>();

	protected LOTRMiniQuest(LOTRPlayerData pd) {
		playerData = pd;
		questUUID = UUID.randomUUID();
	}

	public boolean anyRewardsGiven() {
		return alignmentRewarded > 0.0f || coinsRewarded > 0 || !itemsRewarded.isEmpty();
	}

	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		return true;
	}

	public boolean canRewardVariousExtraItems() {
		return true;
	}

	public void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
		int coins;
		LOTRAchievement achievement;
		completed = true;
		dateCompleted = LOTRDate.ShireReckoning.currentDay;
		Random rand = npc.getRNG();
		List<ItemStack> dropItems = new ArrayList<>();
		float alignment = getAlignmentBonus();
		if (alignment != 0.0f) {
			alignment *= MathHelper.randomFloatClamp(rand, 0.75f, 1.25f);
			alignment = Math.max(alignment, 1.0f);
			LOTRAlignmentValues.AlignmentBonus bonus = LOTRAlignmentValues.createMiniquestBonus(alignment);
			LOTRFaction rewardFaction = getAlignmentRewardFaction();
			if (!questGroup.isNoAlignRewardForEnemy() || playerData.getAlignment(rewardFaction) >= 0.0f) {
				LOTRAlignmentBonusMap alignmentMap = playerData.addAlignment(entityplayer, bonus, rewardFaction, npc);
				alignmentRewarded = alignmentMap.get(rewardFaction);
			}
		}
		coins = getCoinBonus();
		if (coins != 0) {
			if (shouldRandomiseCoinReward()) {
				coins = Math.round(coins * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
				if (rand.nextInt(12) == 0) {
					coins *= MathHelper.getRandomIntegerInRange(rand, 2, 5);
				}
			}
			coinsRewarded = coins = Math.max(coins, 1);
			int coinsRemain = coins;
			for (int l = LOTRItemCoin.values.length - 1; l >= 0; --l) {
				int coinValue = LOTRItemCoin.values[l];
				if (coinsRemain < coinValue) {
					continue;
				}
				int numCoins = coinsRemain / coinValue;
				coinsRemain -= numCoins * coinValue;
				while (numCoins > 64) {
					numCoins -= 64;
					dropItems.add(new ItemStack(LOTRMod.silverCoin, 64, l));
				}
				dropItems.add(new ItemStack(LOTRMod.silverCoin, numCoins, l));
			}
		}
		if (!rewardItemTable.isEmpty()) {
			ItemStack item = rewardItemTable.get(rand.nextInt(rewardItemTable.size()));
			dropItems.add(item.copy());
			itemsRewarded.add(item.copy());
		}
		if (canRewardVariousExtraItems()) {
			LOTRLore lore;
			if (rand.nextInt(10) == 0 && questGroup != null && !questGroup.getLoreCategories().isEmpty() && (lore = LOTRLore.getMultiRandomLore(questGroup.getLoreCategories(), rand, true)) != null) {
				ItemStack loreBook = lore.createLoreBook(rand);
				dropItems.add(loreBook.copy());
				itemsRewarded.add(loreBook.copy());
			}
			if (rand.nextInt(15) == 0) {
				ItemStack modItem = LOTRItemModifierTemplate.getRandomCommonTemplate(rand);
				dropItems.add(modItem.copy());
				itemsRewarded.add(modItem.copy());
			}
			if (npc instanceof LOTREntityDwarf && rand.nextInt(10) == 0) {
				ItemStack mithrilBook = new ItemStack(LOTRMod.mithrilBook);
				dropItems.add(mithrilBook.copy());
				itemsRewarded.add(mithrilBook.copy());
			}
		}
		if (!dropItems.isEmpty()) {
			boolean givePouch;
			givePouch = canRewardVariousExtraItems() && rand.nextInt(10) == 0;
			if (givePouch) {
				ItemStack pouch = npc.createNPCPouchDrop();
				npc.fillPouchFromListAndRetainUnfilled(pouch, dropItems);
				npc.entityDropItem(pouch, 0.0f);
				ItemStack pouchCopy = pouch.copy();
				pouchCopy.setTagCompound(null);
				itemsRewarded.add(pouchCopy);
			}
			npc.dropItemList(dropItems);
		}
		if (willHire) {
			LOTRUnitTradeEntry tradeEntry = new LOTRUnitTradeEntry(npc.getClass(), 0, hiringAlignment);
			tradeEntry.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
			npc.hiredNPCInfo.hireUnit(entityplayer, false, entityFaction, tradeEntry, null, npc.ridingEntity);
			wasHired = true;
		}
		updateQuest();
		playerData.completeMiniQuest(this);
		sendCompletedSpeech(entityplayer, npc);
		if (questGroup != null && (achievement = questGroup.getAchievement()) != null) {
			playerData.addAchievement(achievement);
		}
	}

	public abstract float getAlignmentBonus();

	public LOTRFaction getAlignmentRewardFaction() {
		return questGroup.checkAlignmentRewardFaction(entityFaction);
	}

	public abstract int getCoinBonus();

	public abstract float getCompletionFactor();

	public String getFactionSubtitle() {
		if (entityFaction.isPlayableAlignmentFaction()) {
			return entityFaction.factionName();
		}
		return "";
	}

	public ChunkCoordinates getLastLocation() {
		return lastLocation == null ? null : lastLocation.getLeft();
	}

	public abstract String getObjectiveInSpeech();

	public LOTRPlayerData getPlayerData() {
		return playerData;
	}

	public abstract String getProgressedObjectiveInSpeech();

	public int getQuestColor() {
		return questColor;
	}

	public float[] getQuestColorComponents() {
		return new Color(questColor).getColorComponents(null);
	}

	public String getQuestFailure() {
		return StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.dead", entityName);
	}

	public String getQuestFailureShorthand() {
		return StatCollector.translateToLocal("lotr.gui.redBook.mq.dead");
	}

	public abstract ItemStack getQuestIcon();

	public abstract String getQuestObjective();

	public abstract String getQuestProgress();

	public abstract String getQuestProgressShorthand();

	public void handleEvent(LOTRMiniQuestEvent event) {
	}

	public boolean isActive() {
		return !completed && !isFailed();
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isFailed() {
		return entityDead;
	}

	public boolean isValidQuest() {
		return entityUUID != null && entityFaction != null;
	}

	public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
	}

	public boolean onInteractOther(EntityPlayer entityplayer, LOTREntityNPC npc) {
		return false;
	}

	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
	}

	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
	}

	public void onPlayerTick(EntityPlayer entityplayer) {
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound itemData;
		UUID u;
		ItemStack item;
		LOTRMiniQuestFactory factory;
		NBTTagList itemTags;
		String recovery;
		if (nbt.hasKey("QuestGroup") && (factory = LOTRMiniQuestFactory.forName(nbt.getString("QuestGroup"))) != null) {
			questGroup = factory;
		}
		//noinspection ConstantValue
		if (nbt.hasKey("QuestUUID") && (u = UUID.fromString(nbt.getString("QuestUUID"))) != null) {
			questUUID = u;
		}
		entityUUID = nbt.hasKey("UUIDMost") && nbt.hasKey("UUIDLeast") ? new UUID(nbt.getLong("UUIDMost"), nbt.getLong("UUIDLeast")) : UUID.fromString(nbt.getString("EntityUUID"));
		entityName = nbt.getString("Owner");
		entityNameFull = nbt.hasKey("OwnerFull") ? nbt.getString("OwnerFull") : entityName;
		entityFaction = LOTRFaction.forName(nbt.getString("Faction"));
		questColor = nbt.hasKey("Color") ? nbt.getInteger("Color") : entityFaction.getFactionColor();
		dateGiven = nbt.getInteger("DateGiven");
		if (nbt.hasKey("BiomeID")) {
			int biomeID = nbt.getByte("BiomeID") & 0xFF;
			String biomeDimName = nbt.getString("BiomeDim");
			LOTRDimension biomeDim = LOTRDimension.forName(biomeDimName);
			if (biomeDim != null) {
				biomeGiven = biomeDim.biomeList[biomeID];
			}
		}
		rewardFactor = nbt.hasKey("RewardFactor") ? nbt.getFloat("RewardFactor") : 1.0f;
		willHire = nbt.getBoolean("WillHire");
		hiringAlignment = nbt.hasKey("HiringAlignment") ? nbt.getInteger("HiringAlignment") : nbt.getFloat("HiringAlignF");
		rewardItemTable.clear();
		if (nbt.hasKey("RewardItemTable")) {
			itemTags = nbt.getTagList("RewardItemTable", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				rewardItemTable.add(item);
			}
		}
		completed = nbt.getBoolean("Completed");
		dateCompleted = nbt.getInteger("DateCompleted");
		coinsRewarded = nbt.getShort("CoinReward");
		alignmentRewarded = nbt.hasKey("AlignmentReward") ? nbt.getShort("AlignmentReward") : nbt.getFloat("AlignRewardF");
		wasHired = nbt.getBoolean("WasHired");
		itemsRewarded.clear();
		if (nbt.hasKey("ItemRewards")) {
			itemTags = nbt.getTagList("ItemRewards", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				itemsRewarded.add(item);
			}
		}
		entityDead = nbt.getBoolean("OwnerDead");
		if (nbt.hasKey("Dimension")) {
			ChunkCoordinates coords = new ChunkCoordinates(nbt.getInteger("XPos"), nbt.getInteger("YPos"), nbt.getInteger("ZPos"));
			int dimension = nbt.getInteger("Dimension");
			lastLocation = Pair.of(coords, dimension);
		}
		speechBankStart = nbt.getString("SpeechStart");
		speechBankProgress = nbt.getString("SpeechProgress");
		speechBankComplete = nbt.getString("SpeechComplete");
		speechBankTooMany = nbt.getString("SpeechTooMany");
		quoteStart = nbt.getString("QuoteStart");
		quoteComplete = nbt.getString("QuoteComplete");
		quotesStages.clear();
		if (nbt.hasKey("QuotesStages")) {
			NBTTagList stageTags = nbt.getTagList("QuotesStages", 8);
			for (int l = 0; l < stageTags.tagCount(); ++l) {
				String s = stageTags.getStringTagAt(l);
				quotesStages.add(s);
			}
		}
		if (questGroup == null && (recovery = speechBankStart) != null) {
			LOTRMiniQuestFactory factory2;
			int i1 = recovery.indexOf('/');
			int i2 = recovery.indexOf('/', i1 + 1);
			if (i1 >= 0 && i2 >= 0 && (factory2 = LOTRMiniQuestFactory.forName(recovery.substring(i1 + 1, i2))) != null) {
				questGroup = factory2;
			}
		}
	}

	public void sendCompletedSpeech(EntityPlayer entityplayer, LOTREntityNPC npc) {
		sendQuoteSpeech(entityplayer, npc, quoteComplete);
	}

	public void sendProgressSpeechbank(EntityPlayer entityplayer, LOTREntityNPC npc) {
		npc.sendSpeechBank(entityplayer, speechBankProgress, this);
	}

	public void sendQuoteSpeech(EntityPlayer entityplayer, LOTREntityNPC npc, String quote) {
		LOTRSpeech.sendSpeech(entityplayer, npc, LOTRSpeech.formatSpeech(quote, entityplayer, null, getObjectiveInSpeech()));
		npc.markNPCSpoken();
	}

	public void setEntityDead() {
		entityDead = true;
		updateQuest();
	}

	public void setNPCInfo(LOTREntityNPC npc) {
		entityUUID = npc.getUniqueID();
		entityName = npc.getNPCName();
		entityNameFull = npc.getCommandSenderName();
		entityFaction = npc.getFaction();
		questColor = npc.getMiniquestColor();
	}

	public void setPlayerData(LOTRPlayerData pd) {
		playerData = pd;
	}

	public boolean shouldRandomiseCoinReward() {
		return true;
	}

	public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
		setNPCInfo(npc);
		dateGiven = LOTRDate.ShireReckoning.currentDay;
		int i = MathHelper.floor_double(entityplayer.posX);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = entityplayer.worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiome) {
			biomeGiven = (LOTRBiome) biome;
		}
		playerData.addMiniQuest(this);
		npc.questInfo.addActiveQuestPlayer(entityplayer);
		playerData.setTrackingMiniQuest(this);
	}

	public void updateLocation(LOTREntityNPC npc) {
		int i = MathHelper.floor_double(npc.posX);
		int j = MathHelper.floor_double(npc.posY);
		int k = MathHelper.floor_double(npc.posZ);
		ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
		int dim = npc.dimension;
		ChunkCoordinates prevCoords = null;
		if (lastLocation != null) {
			prevCoords = lastLocation.getLeft();
		}
		lastLocation = Pair.of(coords, dim);
		boolean sendUpdate;
		if (prevCoords == null) {
			sendUpdate = true;
		} else {
			sendUpdate = coords.getDistanceSquaredToChunkCoordinates(prevCoords) > 256.0;
		}
		if (sendUpdate) {
			updateQuest();
		}
	}

	public void updateQuest() {
		playerData.updateMiniQuest(this);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList itemTags;
		NBTTagCompound itemData;
		nbt.setString("QuestType", questToNameMapping.get(getClass()));
		if (questGroup != null) {
			nbt.setString("QuestGroup", questGroup.getBaseName());
		}
		nbt.setString("QuestUUID", questUUID.toString());
		nbt.setString("EntityUUID", entityUUID.toString());
		nbt.setString("Owner", entityName);
		nbt.setString("OwnerFull", entityNameFull);
		nbt.setString("Faction", entityFaction.codeName());
		nbt.setInteger("Color", questColor);
		nbt.setInteger("DateGiven", dateGiven);
		if (biomeGiven != null) {
			nbt.setByte("BiomeID", (byte) biomeGiven.biomeID);
			nbt.setString("BiomeDim", biomeGiven.biomeDimension.dimensionName);
		}
		nbt.setFloat("RewardFactor", rewardFactor);
		nbt.setBoolean("WillHire", willHire);
		nbt.setFloat("HiringAlignF", hiringAlignment);
		if (!rewardItemTable.isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : rewardItemTable) {
				itemData = new NBTTagCompound();
				item.writeToNBT(itemData);
				itemTags.appendTag(itemData);
			}
			nbt.setTag("RewardItemTable", itemTags);
		}
		nbt.setBoolean("Completed", completed);
		nbt.setInteger("DateCompleted", dateCompleted);
		nbt.setShort("CoinReward", (short) coinsRewarded);
		nbt.setFloat("AlignRewardF", alignmentRewarded);
		nbt.setBoolean("WasHired", wasHired);
		if (!itemsRewarded.isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : itemsRewarded) {
				itemData = new NBTTagCompound();
				item.writeToNBT(itemData);
				itemTags.appendTag(itemData);
			}
			nbt.setTag("ItemRewards", itemTags);
		}
		nbt.setBoolean("OwnerDead", entityDead);
		if (lastLocation != null) {
			ChunkCoordinates coords = lastLocation.getLeft();
			nbt.setInteger("XPos", coords.posX);
			nbt.setInteger("YPos", coords.posY);
			nbt.setInteger("ZPos", coords.posZ);
			nbt.setInteger("Dimension", lastLocation.getRight());
		}
		nbt.setString("SpeechStart", speechBankStart);
		nbt.setString("SpeechProgress", speechBankProgress);
		nbt.setString("SpeechComplete", speechBankComplete);
		nbt.setString("SpeechTooMany", speechBankTooMany);
		nbt.setString("QuoteStart", quoteStart);
		nbt.setString("QuoteComplete", quoteComplete);
		if (!quotesStages.isEmpty()) {
			NBTTagList stageTags = new NBTTagList();
			for (String s : quotesStages) {
				stageTags.appendTag(new NBTTagString(s));
			}
			nbt.setTag("QuotesStages", stageTags);
		}
	}

	public static LOTRMiniQuest loadQuestFromNBT(NBTTagCompound nbt, LOTRPlayerData playerData) {
		String questTypeName = nbt.getString("QuestType");
		Class<? extends LOTRMiniQuest> questType = nameToQuestMapping.get(questTypeName);
		if (questType == null) {
			FMLLog.severe("Could not instantiate miniquest of type " + questTypeName);
			return null;
		}
		LOTRMiniQuest quest = newQuestInstance(questType, playerData);
		quest.readFromNBT(nbt);
		if (quest.isValidQuest()) {
			return quest;
		}
		FMLLog.severe("Loaded an invalid LOTR miniquest " + quest.speechBankStart);
		return null;
	}

	public static <Q extends LOTRMiniQuest> Q newQuestInstance(Class<Q> questType, LOTRPlayerData playerData) {
		try {
			return questType.getConstructor(LOTRPlayerData.class).newInstance(playerData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void registerQuestType(String name, Class<? extends LOTRMiniQuest> questType) {
		nameToQuestMapping.put(name, questType);
		questToNameMapping.put(questType, name);
	}

	public abstract static class QuestFactoryBase<Q extends LOTRMiniQuest> {
		public LOTRMiniQuestFactory questFactoryGroup;
		public String questName;
		public float rewardFactor = 1.0f;
		public boolean willHire;
		public float hiringAlignment;
		public List<ItemStack> rewardItems;

		protected QuestFactoryBase(String name) {
			questName = name;
		}

		public Q createQuest(LOTREntityNPC npc, Random rand) {
			Q quest = newQuestInstance(getQuestClass(), null);
			quest.questGroup = questFactoryGroup;
			String pathName = "miniquest/" + questFactoryGroup.getBaseName() + "/";
			String pathNameBaseSpeech = "miniquest/" + questFactoryGroup.getBaseSpeechGroup().getBaseName() + "/";
			String questPathName = pathName + questName + "_";
			quest.speechBankStart = questPathName + "start";
			quest.speechBankProgress = questPathName + "progress";
			quest.speechBankComplete = questPathName + "complete";
			quest.speechBankTooMany = pathNameBaseSpeech + "_tooMany";
			quest.quoteStart = LOTRSpeech.getRandomSpeech(quest.speechBankStart);
			quest.quoteComplete = LOTRSpeech.getRandomSpeech(quest.speechBankComplete);
			quest.setNPCInfo(npc);
			quest.rewardFactor = rewardFactor;
			quest.willHire = willHire;
			quest.hiringAlignment = hiringAlignment;
			if (rewardItems != null) {
				quest.rewardItemTable.addAll(rewardItems);
			}
			return quest;
		}

		public LOTRMiniQuestFactory getFactoryGroup() {
			return questFactoryGroup;
		}

		public abstract Class<Q> getQuestClass();

		public void setFactoryGroup(LOTRMiniQuestFactory factory) {
			questFactoryGroup = factory;
		}

		public QuestFactoryBase setHiring(float f) {
			willHire = true;
			hiringAlignment = f;
			return this;
		}

		public QuestFactoryBase setRewardFactor(float f) {
			rewardFactor = f;
			return this;
		}

		public QuestFactoryBase setRewardItems(ItemStack[] items) {
			rewardItems = Arrays.asList(items);
			return this;
		}
	}

	public static class SorterAlphabetical implements Comparator<LOTRMiniQuest> {
		@Override
		public int compare(LOTRMiniQuest q1, LOTRMiniQuest q2) {
			if (!q2.isActive() && q1.isActive()) {
				return 1;
			}
			if (!q1.isActive() && q2.isActive()) {
				return -1;
			}
			if (q1.entityFaction == q2.entityFaction) {
				return q1.entityName.compareTo(q2.entityName);
			}
			return Integer.compare(q1.entityFaction.ordinal(), q2.entityFaction.ordinal());
		}
	}

}
