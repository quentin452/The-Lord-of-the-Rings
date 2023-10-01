package lotr.common.quest;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LOTRMiniQuestBounty extends LOTRMiniQuest {
	public UUID targetID;
	public String targetName;
	public boolean killed;
	public float alignmentBonus;
	public int coinBonus;
	public boolean bountyClaimedByOther;
	public boolean killedByBounty;

	public LOTRMiniQuestBounty(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		if (super.canPlayerAccept(entityplayer) && !targetID.equals(entityplayer.getUniqueID()) && LOTRLevelData.getData(entityplayer).getAlignment(entityFaction) >= 100.0f) {
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			List<LOTRMiniQuest> active = pd.getActiveMiniQuests();
			for (LOTRMiniQuest quest : active) {
				if (!(quest instanceof LOTRMiniQuestBounty) || !((LOTRMiniQuestBounty) quest).targetID.equals(targetID)) {
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
		boolean specialReward;
		LOTRPlayerData pd = getPlayerData();
		pd.addCompletedBountyQuest();
		int bComplete = pd.getCompletedBountyQuests();
		specialReward = bComplete > 0 && bComplete % 5 == 0;
		if (specialReward) {
			ItemStack trophy = new ItemStack(LOTRMod.bountyTrophy);
			rewardItemTable.add(trophy);
		}
		super.complete(entityplayer, npc);
		pd.addAchievement(LOTRAchievement.doMiniquestHunter);
		if (specialReward) {
			pd.addAchievement(LOTRAchievement.doMiniquestHunter5);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return alignmentBonus;
	}

	@Override
	public int getCoinBonus() {
		return coinBonus;
	}

	@Override
	public float getCompletionFactor() {
		return killed ? 1.0f : 0.0f;
	}

	public float getKilledAlignmentPenalty() {
		return -alignmentBonus * 2.0f;
	}

	@Override
	public String getObjectiveInSpeech() {
		return targetName;
	}

	public LOTRFaction getPledgeOrHighestAlignmentFaction(EntityPlayer entityplayer, float min) {
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		if (pd.getPledgeFaction() != null) {
			return pd.getPledgeFaction();
		}
		ArrayList<LOTRFaction> highestFactions = new ArrayList<>();
		float highestAlignment = min;
		for (LOTRFaction f : LOTRFaction.getPlayableAlignmentFactions()) {
			float alignment = pd.getAlignment(f);
			if (alignment <= min) {
				continue;
			}
			if (alignment > highestAlignment) {
				highestFactions.clear();
				highestFactions.add(f);
				highestAlignment = alignment;
				continue;
			}
			if (alignment != highestAlignment) {
				continue;
			}
			highestFactions.add(f);
		}
		if (!highestFactions.isEmpty()) {
			Random rand = entityplayer.getRNG();
			return highestFactions.get(rand.nextInt(highestFactions.size()));
		}
		return null;
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return targetName;
	}

	@Override
	public String getQuestFailure() {
		if (killedByBounty) {
			return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty.killedBy", targetName);
		}
		if (bountyClaimedByOther) {
			return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty.claimed", targetName);
		}
		return super.getQuestFailure();
	}

	@Override
	public String getQuestFailureShorthand() {
		if (killedByBounty) {
			return StatCollector.translateToLocal("lotr.miniquest.bounty.killedBy.short");
		}
		if (bountyClaimedByOther) {
			return StatCollector.translateToLocal("lotr.miniquest.bounty.claimed.short");
		}
		return super.getQuestFailureShorthand();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(Items.iron_sword);
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.bounty", targetName);
	}

	@Override
	public String getQuestProgress() {
		if (killed) {
			return StatCollector.translateToLocal("lotr.miniquest.bounty.progress.slain");
		}
		return StatCollector.translateToLocal("lotr.miniquest.bounty.progress.notSlain");
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", killed ? 1 : 0, 1);
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || bountyClaimedByOther || killedByBounty;
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && targetID != null;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
		if (killed) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (!killed && !isFailed() && entity instanceof EntityPlayer && entity.getUniqueID().equals(targetID)) {
			EntityPlayer slainPlayer = (EntityPlayer) entity;
			LOTRPlayerData slainPlayerData = LOTRLevelData.getData(slainPlayer);
			killed = true;
			LOTRFactionBounties.forFaction(entityFaction).forPlayer(slainPlayer).recordBountyKilled();
			updateQuest();
			LOTRFaction highestFaction = getPledgeOrHighestAlignmentFaction(slainPlayer, 100.0f);
			if (highestFaction != null) {
				float alignmentLoss;
				float curAlignment = slainPlayerData.getAlignment(highestFaction);
				alignmentLoss = getKilledAlignmentPenalty();
				if (curAlignment + alignmentLoss < 100.0f) {
					alignmentLoss = -(curAlignment - 100.0f);
				}
				LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.bountyKilled");
				slainPlayerData.addAlignment(slainPlayer, source, highestFaction, entityplayer);
				IChatComponent slainMsg1 = new ChatComponentTranslation("chat.lotr.bountyKilled1", entityplayer.getCommandSenderName(), entityFaction.factionName());
				IChatComponent slainMsg2 = new ChatComponentTranslation("chat.lotr.bountyKilled2", highestFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainPlayer.addChatMessage(slainMsg1);
				slainPlayer.addChatMessage(slainMsg2);
			}
			IChatComponent announceMsg = new ChatComponentTranslation("chat.lotr.bountyKill", entityplayer.getCommandSenderName(), slainPlayer.getCommandSenderName(), entityFaction.factionName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				ICommandSender otherPlayer = (ICommandSender) obj;
				if (otherPlayer == slainPlayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
		if (!killed && !isFailed() && killer.getUniqueID().equals(targetID)) {
			float curAlignment;
			LOTRPlayerData pd;
			LOTRPlayerData killerData = LOTRLevelData.getData(killer);
			LOTRFaction killerHighestFaction = getPledgeOrHighestAlignmentFaction(killer, 0.0f);
			if (killerHighestFaction != null) {
				float killerBonus = alignmentBonus;
				LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(killerBonus, "lotr.alignment.killedHunter");
				killerData.addAlignment(killer, source, killerHighestFaction, entityplayer);
			}
			curAlignment = (pd = getPlayerData()).getAlignment(entityFaction);
			if (curAlignment > 100.0f) {
				float alignmentLoss = getKilledAlignmentPenalty();
				if (curAlignment + alignmentLoss < 100.0f) {
					alignmentLoss = -(curAlignment - 100.0f);
				}
				LOTRAlignmentValues.AlignmentBonus source = new LOTRAlignmentValues.AlignmentBonus(alignmentLoss, "lotr.alignment.killedByBounty");
				pd.addAlignment(entityplayer, source, entityFaction, killer);
				IChatComponent slainMsg1 = new ChatComponentTranslation("chat.lotr.killedByBounty1", killer.getCommandSenderName());
				IChatComponent slainMsg2 = new ChatComponentTranslation("chat.lotr.killedByBounty2", entityFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(slainMsg1);
				entityplayer.addChatMessage(slainMsg2);
			}
			killedByBounty = true;
			updateQuest();
			killerData.addAchievement(LOTRAchievement.killHuntingPlayer);
			IChatComponent announceMsg = new ChatComponentTranslation("chat.lotr.killedByBounty", entityplayer.getCommandSenderName(), killer.getCommandSenderName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				ICommandSender otherPlayer = (ICommandSender) obj;
				if (otherPlayer == entityplayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onPlayerTick(EntityPlayer entityplayer) {
		super.onPlayerTick(entityplayer);
		if (isActive() && !killed && !bountyClaimedByOther && LOTRFactionBounties.forFaction(entityFaction).forPlayer(targetID).recentlyBountyKilled()) {
			bountyClaimedByOther = true;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("TargetID")) {
			String s = nbt.getString("TargetID");
			targetID = UUID.fromString(s);
		}
		if (nbt.hasKey("TargetName")) {
			targetName = nbt.getString("TargetName");
		}
		killed = nbt.getBoolean("Killed");
		alignmentBonus = nbt.hasKey("Alignment") ? nbt.getInteger("Alignment") : nbt.getFloat("AlignF");
		coinBonus = nbt.getInteger("Coins");
		bountyClaimedByOther = nbt.getBoolean("BountyClaimed");
		killedByBounty = nbt.getBoolean("KilledBy");
	}

	@Override
	public boolean shouldRandomiseCoinReward() {
		return false;
	}

	@Override
	public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
		super.start(entityplayer, npc);
		LOTRLevelData.getData(targetID).placeBountyFor(npc.getFaction());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (targetID != null) {
			nbt.setString("TargetID", targetID.toString());
		}
		if (targetName != null) {
			nbt.setString("TargetName", targetName);
		}
		nbt.setBoolean("Killed", killed);
		nbt.setFloat("AlignF", alignmentBonus);
		nbt.setInteger("Coins", coinBonus);
		nbt.setBoolean("BountyClaimed", bountyClaimedByOther);
		nbt.setBoolean("KilledBy", killedByBounty);
	}

	public enum BountyHelp {
		BIOME("biome"), WAYPOINT("wp");

		public String speechName;

		BountyHelp(String s) {
			speechName = s;
		}

		public static BountyHelp getRandomHelpType(Random random) {
			return values()[random.nextInt(values().length)];
		}
	}

	public static class QFBounty<Q extends LOTRMiniQuestBounty> extends LOTRMiniQuest.QuestFactoryBase<Q> {
		public QFBounty(String name) {
			super(name);
		}

		@Override
		public Q createQuest(LOTREntityNPC npc, Random rand) {
			if (!LOTRConfig.allowBountyQuests) {
				return null;
			}
			Q quest = super.createQuest(npc, rand);
			LOTRFaction faction = npc.getFaction();
			LOTRFactionBounties bounties = LOTRFactionBounties.forFaction(faction);
			List<LOTRFactionBounties.PlayerData> players = bounties.findBountyTargets(25);
			if (players.isEmpty()) {
				return null;
			}
			LOTRFactionBounties.PlayerData targetData = players.get(rand.nextInt(players.size()));
			int alignment = (int) (float) targetData.getNumKills();
			alignment = MathHelper.clamp_int(alignment, 1, 50);
			int coins = (int) (targetData.getNumKills() * 10.0f * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
			coins = MathHelper.clamp_int(coins, 1, 1000);
			quest.targetID = targetData.playerID;
			String username = targetData.findUsername();
			if (StringUtils.isBlank(username)) {
				username = quest.targetID.toString();
			}
			quest.targetName = username;
			quest.alignmentBonus = alignment;
			quest.coinBonus = coins;
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return LOTRMiniQuestBounty.class;
		}
	}

}
