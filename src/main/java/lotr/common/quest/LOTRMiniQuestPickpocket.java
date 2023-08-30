package lotr.common.quest;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.*;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class LOTRMiniQuestPickpocket extends LOTRMiniQuestCollectBase {
	public LOTRFaction pickpocketFaction;
	public Collection<UUID> pickpocketedEntityIDs = new HashSet<>();

	public LOTRMiniQuestPickpocket(LOTRPlayerData pd) {
		super(pd);
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getAlignmentBonus() * 5.0f);
	}

	@Override
	public String getObjectiveInSpeech() {
		return pickpocketFaction.factionEntityName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return collectTarget - amountGiven + " " + pickpocketFaction.factionEntityName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return createPickpocketIcon();
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.pickpocket", collectTarget, pickpocketFaction.factionEntityName());
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("lotr.miniquest.pickpocket.progress", amountGiven, collectTarget);
	}

	public boolean isEntityWatching(EntityLiving watcher, EntityLivingBase target) {
		Vec3 look = watcher.getLookVec();
		Vec3 watcherEyes = Vec3.createVectorHelper(watcher.posX, watcher.boundingBox.minY + watcher.getEyeHeight(), watcher.posZ);
		Vec3 targetEyes = Vec3.createVectorHelper(target.posX, target.boundingBox.minY + target.getEyeHeight(), target.posZ);
		Vec3 disp = Vec3.createVectorHelper(targetEyes.xCoord - watcherEyes.xCoord, targetEyes.yCoord - watcherEyes.yCoord, targetEyes.zCoord - watcherEyes.zCoord);
		double dot = disp.normalize().dotProduct(look.normalize());
		if (dot >= MathHelper.cos(2.2689280275926285f / 2.0f)) {
			return watcher.getEntitySenses().canSee(target);
		}
		return false;
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		return IPickpocketable.Helper.isPickpocketed(itemstack) && entityUUID.equals(IPickpocketable.Helper.getWanterID(itemstack));
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && pickpocketFaction != null;
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public boolean onInteractOther(EntityPlayer entityplayer, LOTREntityNPC npc) {
		if (entityplayer.isSneaking() && entityplayer.getHeldItem() == null && npc.getFaction() == pickpocketFaction && npc instanceof IPickpocketable) {
			IPickpocketable ppable = (IPickpocketable) npc;
			UUID id = npc.getPersistentID();
			if (ppable.canPickpocket() && !pickpocketedEntityIDs.contains(id)) {
				boolean noticed;
				boolean success;
				if (npc.getAttackTarget() != null) {
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.inCombat"));
					return true;
				}
				if (isEntityWatching(npc, entityplayer)) {
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.watched"));
					return true;
				}
				Random rand = npc.getRNG();
				success = rand.nextInt(3) == 0;
				boolean anyoneNoticed = noticed = success ? rand.nextInt(3) == 0 : rand.nextInt(4) == 0;
				if (success) {
					ItemStack picked = ppable.createPickpocketItem();
					IPickpocketable.Helper.setPickpocketData(picked, npc.getNPCName(), entityNameFull, entityUUID);
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, picked);
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.success", picked.stackSize, picked.getDisplayName(), npc.getNPCName()));
					npc.playSound("lotr:event.trade", 0.5f, 1.0f + (rand.nextFloat() - rand.nextFloat()) * 0.1f);
					npc.playSound("mob.horse.leather", 0.5f, 1.0f);
					spawnPickingFX("pickpocket", 1.0, npc);
					pickpocketedEntityIDs.add(id);
					updateQuest();
					LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.pickpocket);
				} else {
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.missed", npc.getNPCName()));
					npc.playSound(Blocks.wool.stepSound.getBreakSound(), 0.5f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
					spawnPickingFX("pickpocketFail", 0.4, npc);
				}
				if (noticed) {
					entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.noticed", npc.getNPCName()));
					npc.setAttackTarget(entityplayer, true);
					npc.setRevengeTarget(entityplayer);
					spawnAngryFX(npc);
				}
				if (!noticed || rand.nextFloat() < 0.5f) {
					List nearbyFriends = npc.worldObj.selectEntitiesWithinAABB(LOTREntityNPC.class, npc.boundingBox.expand(16.0, 16.0, 16.0), new IEntitySelector() {

						@Override
						public boolean isEntityApplicable(Entity entity) {
							LOTREntityNPC otherNPC = (LOTREntityNPC) entity;
							if (otherNPC.isEntityAlive() && otherNPC.getFaction().isGoodRelation(npc.getFaction())) {
								return otherNPC.hiredNPCInfo.getHiringPlayer() != entityplayer;
							}
							return false;
						}
					});
					for (Object o : nearbyFriends) {
						double maxRange;
						LOTREntityNPC otherNPC = (LOTREntityNPC) o;
						if (otherNPC == npc) {
							continue;
						}
						boolean civilian = otherNPC.isCivilianNPC();
						maxRange = civilian ? 8.0 : 16.0;
						double dist = otherNPC.getDistanceToEntity(npc);
						if (dist > maxRange || otherNPC.getAttackTarget() != null || !isEntityWatching(otherNPC, entityplayer)) {
							continue;
						}
						float distFactor = 1.0f - (float) ((dist - 4.0) / (maxRange - 4.0));
						float chance = 0.5f + distFactor * 0.5f;
						if (civilian) {
							chance *= 0.25f;
						}
						if (rand.nextFloat() >= chance) {
							continue;
						}
						entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.pickpocket.otherNoticed", otherNPC.getEntityClassName()));
						otherNPC.setAttackTarget(entityplayer, true);
						otherNPC.setRevengeTarget(entityplayer);
						spawnAngryFX(otherNPC);
						anyoneNoticed = true;
					}
				}
				if (anyoneNoticed) {
					LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.PICKPOCKET_PENALTY, npc.getFaction(), npc);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		pickpocketFaction = LOTRFaction.forName(nbt.getString("PickpocketFaction"));
		pickpocketedEntityIDs.clear();
		NBTTagList ids = nbt.getTagList("PickpocketedIDs", 8);
		for (int i = 0; i < ids.tagCount(); ++i) {
			UUID id = UUID.fromString(ids.getStringTagAt(i));
			pickpocketedEntityIDs.add(id);
		}
	}

	public void spawnAngryFX(EntityLivingBase npc) {
		LOTRMod.proxy.spawnParticle("angry", npc.posX, npc.boundingBox.minY + npc.height * 2.0f, npc.posZ, npc.motionX, Math.max(0.0, npc.motionY), npc.motionZ);
	}

	public void spawnPickingFX(String particle, double upSpeed, EntityLivingBase npc) {
		Random rand = npc.getRNG();
		int particles = 3 + rand.nextInt(8);
		for (int p = 0; p < particles; ++p) {
			double x = npc.posX;
			double y = npc.boundingBox.minY + npc.height * 0.5f;
			double z = npc.posZ;
			float w = npc.width * 0.1f;
			float ang = rand.nextFloat() * 3.1415927f * 2.0f;
			double hSpeed = MathHelper.getRandomDoubleInRange(rand, 0.05, 0.08);
			double vx = MathHelper.cos(ang) * hSpeed;
			double vz = MathHelper.sin(ang) * hSpeed;
			double vy = MathHelper.getRandomDoubleInRange(rand, 0.1, 0.25) * upSpeed;
			LOTRMod.proxy.spawnParticle(particle, x + MathHelper.cos(ang) * w, y, z + MathHelper.sin(ang) * w, vx, vy, vz);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("PickpocketFaction", pickpocketFaction.codeName());
		NBTTagList ids = new NBTTagList();
		for (UUID id : pickpocketedEntityIDs) {
			ids.appendTag(new NBTTagString(id.toString()));
		}
		nbt.setTag("PickpocketedIDs", ids);
	}

	public static ItemStack createPickpocketIcon() {
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		LOTRItemLeatherHat.setHatColor(hat, 0);
		LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
		return hat;
	}

	public static class QFPickpocket<Q extends LOTRMiniQuestPickpocket> extends LOTRMiniQuest.QuestFactoryBase<Q> {
		public LOTRFaction pickpocketFaction;
		public int minTarget;
		public int maxTarget;

		public QFPickpocket(String name) {
			super(name);
		}

		@Override
		public Q createQuest(LOTREntityNPC npc, Random rand) {
			Q quest = super.createQuest(npc, rand);
			quest.pickpocketFaction = pickpocketFaction;
			quest.collectTarget = MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget);
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return LOTRMiniQuestPickpocket.class;
		}

		public QFPickpocket setPickpocketFaction(LOTRFaction f, int min, int max) {
			pickpocketFaction = f;
			minTarget = min;
			maxTarget = max;
			return this;
		}
	}

}
