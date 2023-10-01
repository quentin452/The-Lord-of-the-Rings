package lotr.common.entity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemConquestHorn;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketInvasionWatch;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class LOTREntityInvasionSpawner extends Entity {
	public static int MAX_INVASION_SIZE = 10000;
	public static double INVASION_FOLLOW_RANGE = 40.0;
	public float spawnerSpin;
	public float prevSpawnerSpin;
	public int invasionSize;
	public int invasionRemaining;
	public int successiveFailedSpawns;
	public int timeSincePlayerProgress;
	public Map<UUID, Integer> recentPlayerContributors = new HashMap<>();
	public boolean isWarhorn;
	public boolean spawnsPersistent = true;
	public Collection<LOTRFaction> bonusFactions = new ArrayList<>();

	public LOTREntityInvasionSpawner(World world) {
		super(world);
		setSize(1.5f, 1.5f);
		renderDistanceWeight = 4.0;
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	@SuppressWarnings("Convert2Lambda")
	public static LOTREntityInvasionSpawner locateInvasionNearby(Entity seeker, UUID id) {
		World world = seeker.worldObj;
		double search = 256.0;
		List invasions = world.selectEntitiesWithinAABB(LOTREntityInvasionSpawner.class, seeker.boundingBox.expand(search, search, search), new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity e) {
				return e.getUniqueID().equals(id);
			}
		});
		if (!invasions.isEmpty()) {
			return (LOTREntityInvasionSpawner) invasions.get(0);
		}
		return null;
	}

	public void addPlayerKill(EntityPlayer entityplayer) {
		--invasionRemaining;
		timeSincePlayerProgress = 0;
		recentPlayerContributors.put(entityplayer.getUniqueID(), 2400);
	}

	public void announceInvasionTo(ICommandSender entityplayer) {
		entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.invasion.start", getInvasionType().invasionName()));
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				endInvasion(false);
			}
			return true;
		}
		return false;
	}

	public boolean attemptSpawnMob(LOTREntityNPC npc) {
		for (int at = 0; at < 40; ++at) {
			int i = MathHelper.floor_double(posX) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int k = MathHelper.floor_double(posZ) + MathHelper.getRandomIntegerInRange(rand, -6, 6);
			int j = MathHelper.floor_double(posY) + MathHelper.getRandomIntegerInRange(rand, -8, 4);
			if (!worldObj.getBlock(i, j - 1, k).isSideSolid(worldObj, i, j - 1, k, ForgeDirection.UP)) {
				continue;
			}
			npc.setLocationAndAngles(i + 0.5, j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
			npc.liftSpawnRestrictions = true;
			Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(npc, worldObj, (float) npc.posX, (float) npc.posY, (float) npc.posZ);
			if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !npc.getCanSpawnHere())) {
				continue;
			}
			npc.liftSpawnRestrictions = false;
			npc.onSpawnWithEgg(null);
			npc.isNPCPersistent = spawnsPersistent;
			npc.setInvasionID(getInvasionID());
			npc.killBonusFactions.addAll(bonusFactions);
			worldObj.spawnEntityInWorld(npc);
			IAttributeInstance followRangeAttrib = npc.getEntityAttribute(SharedMonsterAttributes.followRange);
			double followRange = followRangeAttrib.getBaseValue();
			followRange = Math.max(followRange, INVASION_FOLLOW_RANGE);
			followRangeAttrib.setBaseValue(followRange);
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean canInvasionSpawnHere() {
		if (LOTRBannerProtection.isProtected(worldObj, this, LOTRBannerProtection.forInvasionSpawner(this), false) || LOTREntityNPCRespawner.isSpawnBlocked(this, getInvasionType().invasionFaction)) {
			return false;
		}
		return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}

	public void endInvasion(boolean completed) {
		if (completed) {
			LOTRFaction invasionFac = getInvasionType().invasionFaction;
			Collection<EntityPlayer> achievementPlayers = new HashSet<>();
			Collection<EntityPlayer> conqRewardPlayers = new HashSet<>();
			for (UUID player : recentPlayerContributors.keySet()) {
				LOTRFaction pledged;
				EntityPlayer entityplayer = worldObj.func_152378_a(player);
				if (entityplayer == null) {
					continue;
				}
				double range = 100.0;
				if (entityplayer.dimension != dimension || entityplayer.getDistanceSqToEntity(this) >= range * range) {
					continue;
				}
				LOTRPlayerData pd = LOTRLevelData.getData(player);
				if (pd.getAlignment(invasionFac) <= 0.0f) {
					achievementPlayers.add(entityplayer);
				}
				pledged = pd.getPledgeFaction();
				if (pledged == null || !pledged.isBadRelation(invasionFac)) {
					continue;
				}
				conqRewardPlayers.add(entityplayer);
			}
			for (EntityPlayer entityplayer : achievementPlayers) {
				LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
				pd.addAchievement(LOTRAchievement.defeatInvasion);
			}
			if (!conqRewardPlayers.isEmpty()) {
				float boostPerPlayer = 50.0f / conqRewardPlayers.size();
				for (EntityPlayer entityplayer : conqRewardPlayers) {
					LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
					pd.givePureConquestBonus(entityplayer, pd.getPledgeFaction(), getInvasionType().invasionFaction, boostPerPlayer, "lotr.alignment.invasionDefeat", posX, posY, posZ);
				}
			}
		}
		worldObj.createExplosion(this, posX, posY + height / 2.0, posZ, 0.0f, false);
		setDead();
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(20, (byte) 0);
		dataWatcher.addObject(21, (short) 0);
		dataWatcher.addObject(22, (short) 0);
	}

	public float getInvasionHealthStatus() {
		return (float) invasionRemaining / invasionSize;
	}

	public UUID getInvasionID() {
		return getUniqueID();
	}

	public ItemStack getInvasionItem() {
		return getInvasionType().getInvasionIcon();
	}

	public int getInvasionSize() {
		return invasionSize;
	}

	public LOTRInvasions getInvasionType() {
		byte i = dataWatcher.getWatchableObjectByte(20);
		LOTRInvasions type = LOTRInvasions.forID(i);
		if (type != null) {
			return type;
		}
		return LOTRInvasions.HOBBIT;
	}

	public void setInvasionType(LOTRInvasions type) {
		dataWatcher.updateObject(20, (byte) type.ordinal());
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		LOTRInvasions invasionType = getInvasionType();
		if (invasionType != null) {
			return LOTRItemConquestHorn.createHorn(invasionType);
		}
		return null;
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		if (entity instanceof EntityPlayer) {
			return attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entity), 0.0f);
		}
		return false;
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (!worldObj.isRemote && entityplayer.capabilities.isCreativeMode && !bonusFactions.isEmpty()) {
			IChatComponent message = new ChatComponentText("");
			for (LOTRFaction f : bonusFactions) {
				if (!message.getSiblings().isEmpty()) {
					message.appendSibling(new ChatComponentText(", "));
				}
				message.appendSibling(new ChatComponentTranslation(f.factionName()));
			}
			entityplayer.addChatMessage(message);
			return true;
		}
		return false;
	}

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			endInvasion(false);
			return;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevSpawnerSpin = spawnerSpin;
		spawnerSpin += 6.0f;
		prevSpawnerSpin = MathHelper.wrapAngleTo180_float(prevSpawnerSpin);
		spawnerSpin = MathHelper.wrapAngleTo180_float(spawnerSpin);
		motionX = 0.0;
		motionY = 0.0;
		motionZ = 0.0;
		moveEntity(motionX, motionY, motionZ);
		if (!worldObj.isRemote) {
			if (invasionRemaining > 0) {
				++timeSincePlayerProgress;
				if (LOTRConfig.invasionProgressReverts && timeSincePlayerProgress >= 6000 && !isWarhorn && timeSincePlayerProgress % 1200 == 0) {
					++invasionRemaining;
					invasionRemaining = Math.min(invasionRemaining, invasionSize);
				}
				if (!recentPlayerContributors.isEmpty()) {
					Collection<UUID> removes = new HashSet<>();
					for (Map.Entry<UUID, Integer> e : recentPlayerContributors.entrySet()) {
						UUID player = e.getKey();
						int time = e.getValue();
						time--;
						e.setValue(time);
						if (time > 0) {
							continue;
						}
						removes.add(player);
					}
					for (UUID player : removes) {
						recentPlayerContributors.remove(player);
					}
				}
			} else {
				endInvasion(true);
			}
		}
		if (!worldObj.isRemote && LOTRMod.canSpawnMobs(worldObj)) {
			double nearbySearch;
			LOTRInvasions invasionType = getInvasionType();
			EntityPlayer closePlayer = worldObj.getClosestPlayer(posX, posY, posZ, 80.0);
			if (closePlayer != null && invasionRemaining > 0 && worldObj.selectEntitiesWithinAABB(LOTREntityNPC.class, boundingBox.expand(nearbySearch = INVASION_FOLLOW_RANGE * 2.0, nearbySearch, nearbySearch), selectThisInvasionMobs()).size() < 16 && rand.nextInt(160) == 0) {
				int spawnAttempts = MathHelper.getRandomIntegerInRange(rand, 1, 6);
				spawnAttempts = Math.min(spawnAttempts, invasionRemaining);
				boolean spawnedAnyMobs = false;
				for (int l = 0; l < spawnAttempts; ++l) {
					LOTRInvasions.InvasionSpawnEntry entry = (LOTRInvasions.InvasionSpawnEntry) WeightedRandom.getRandomItem(rand, invasionType.invasionMobs);
					Class entityClass = entry.getEntityClass();
					String entityName = LOTREntities.getStringFromClass(entityClass);
					LOTREntityNPC npc = (LOTREntityNPC) EntityList.createEntityByName(entityName, worldObj);
					if (!attemptSpawnMob(npc)) {
						continue;
					}
					spawnedAnyMobs = true;
				}
				if (spawnedAnyMobs) {
					successiveFailedSpawns = 0;
					playHorn();
				} else {
					++successiveFailedSpawns;
					if (successiveFailedSpawns >= 16) {
						endInvasion(false);
					}
				}
			}
		} else {
			String particle = rand.nextBoolean() ? "smoke" : "flame";
			worldObj.spawnParticle(particle, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
		}
		updateWatchedInvasionValues();
	}

	public void playHorn() {
		worldObj.playSoundAtEntity(this, "lotr:item.horn", 4.0f, 0.65f + rand.nextFloat() * 0.1f);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		LOTRInvasions type = LOTRInvasions.forName(nbt.getString("InvasionType"));
		if (type == null && nbt.hasKey("Faction")) {
			String factionName = nbt.getString("Faction");
			type = LOTRInvasions.forName(factionName);
		}
		if (type == null || type.invasionMobs.isEmpty()) {
			setDead();
		} else {
			int i;
			setInvasionType(type);
			if (nbt.hasKey("MobsRemaining")) {
				invasionSize = invasionRemaining = nbt.getInteger("MobsRemaining");
			} else {
				invasionSize = nbt.getInteger("InvasionSize");
				invasionRemaining = nbt.hasKey("InvasionRemaining") ? nbt.getInteger("InvasionRemaining") : invasionSize;
			}
			successiveFailedSpawns = nbt.getInteger("SuccessiveFailedSpawns");
			timeSincePlayerProgress = nbt.getInteger("TimeSinceProgress");
			recentPlayerContributors.clear();
			if (nbt.hasKey("RecentPlayers")) {
				NBTTagList recentTags = nbt.getTagList("RecentPlayers", 10);
				for (i = 0; i < recentTags.tagCount(); ++i) {
					NBTTagCompound playerData = recentTags.getCompoundTagAt(i);
					String playerS = playerData.getString("Player");
					try {
						UUID player = UUID.fromString(playerS);
						short time = playerData.getShort("Time");
						recentPlayerContributors.put(player, (int) time);
					} catch (IllegalArgumentException e) {
						FMLLog.warning("LOTR: Error loading invasion recent players - %s is not a valid UUID", playerS);
						e.printStackTrace();
					}
				}
			}
			if (nbt.hasKey("Warhorn")) {
				isWarhorn = nbt.getBoolean("Warhorn");
			}
			if (nbt.hasKey("NPCPersistent")) {
				spawnsPersistent = nbt.getBoolean("NPCPersistent");
			}
			if (nbt.hasKey("BonusFactions")) {
				NBTTagList bonusTags = nbt.getTagList("BonusFactions", 8);
				for (i = 0; i < bonusTags.tagCount(); ++i) {
					String fName = bonusTags.getStringTagAt(i);
					LOTRFaction f = LOTRFaction.forName(fName);
					if (f == null) {
						continue;
					}
					bonusFactions.add(f);
				}
			}
		}
	}

	public void selectAppropriateBonusFactions() {
		if (LOTRFaction.controlZonesEnabled(worldObj)) {
			LOTRFaction invasionFaction = getInvasionType().invasionFaction;
			for (LOTRFaction faction : invasionFaction.getBonusesForKilling()) {
				if (faction.isolationist || !faction.inDefinedControlZone(worldObj, posX, posY, posZ, 50)) {
					continue;
				}
				bonusFactions.add(faction);
			}
			if (bonusFactions.isEmpty()) {
				int nearestRange = 150;
				LOTRFaction nearest = null;
				double nearestDist = Double.MAX_VALUE;
				for (LOTRFaction faction : invasionFaction.getBonusesForKilling()) {
					double dist;
					if (faction.isolationist || (dist = faction.distanceToNearestControlZoneInRange(worldObj, posX, posY, posZ, nearestRange)) < 0.0 || nearest != null && dist >= nearestDist) {
						continue;
					}
					nearest = faction;
					nearestDist = dist;
				}
				if (nearest != null) {
					bonusFactions.add(nearest);
				}
			}
		}
	}

	@SuppressWarnings("Convert2Lambda")
	public IEntitySelector selectThisInvasionMobs() {
		return new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				if (entity.isEntityAlive() && entity instanceof LOTREntityNPC) {
					LOTREntityNPC npc = (LOTREntityNPC) entity;
					return npc.isInvasionSpawned() && npc.getInvasionID().equals(getInvasionID());
				}
				return false;
			}
		};
	}

	public void setWatchingInvasion(EntityPlayerMP entityplayer, boolean overrideAlreadyWatched) {
		IMessage pkt = new LOTRPacketInvasionWatch(this, overrideAlreadyWatched);
		LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
	}

	public void startInvasion() {
		startInvasion(null);
	}

	public void startInvasion(EntityPlayer announcePlayer) {
		startInvasion(announcePlayer, -1);
	}

	public void startInvasion(EntityPlayer announcePlayer, int size) {
		if (size < 0) {
			size = MathHelper.getRandomIntegerInRange(rand, 30, 70);
		}
		invasionRemaining = invasionSize = size;
		playHorn();
		double announceRange = INVASION_FOLLOW_RANGE * 2.0;
		List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(announceRange, announceRange, announceRange));
		if (announcePlayer != null && !nearbyPlayers.contains(announcePlayer)) {
			nearbyPlayers.add(announcePlayer);
		}
		for (EntityPlayer entityplayer : nearbyPlayers) {
			boolean announce;
			announce = LOTRLevelData.getData(entityplayer).getAlignment(getInvasionType().invasionFaction) < 0.0f;
			if (entityplayer == announcePlayer) {
				announce = true;
			}
			if (!announce) {
				continue;
			}
			announceInvasionTo(entityplayer);
			setWatchingInvasion((EntityPlayerMP) entityplayer, false);
		}
	}

	public void updateWatchedInvasionValues() {
		if (worldObj.isRemote) {
			invasionSize = dataWatcher.getWatchableObjectShort(21);
			invasionRemaining = dataWatcher.getWatchableObjectShort(22);
		} else {
			dataWatcher.updateObject(21, (short) invasionSize);
			dataWatcher.updateObject(22, (short) invasionRemaining);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setString("InvasionType", getInvasionType().codeName());
		nbt.setInteger("InvasionSize", invasionSize);
		nbt.setInteger("InvasionRemaining", invasionRemaining);
		nbt.setInteger("SuccessiveFailedSpawns", successiveFailedSpawns);
		nbt.setInteger("TimeSinceProgress", timeSincePlayerProgress);
		if (!recentPlayerContributors.isEmpty()) {
			NBTTagList recentTags = new NBTTagList();
			for (Map.Entry<UUID, Integer> e : recentPlayerContributors.entrySet()) {
				UUID player = e.getKey();
				int time = e.getValue();
				NBTTagCompound playerData = new NBTTagCompound();
				playerData.setString("Player", player.toString());
				playerData.setShort("Time", (short) time);
				recentTags.appendTag(playerData);
			}
			nbt.setTag("RecentPlayers", recentTags);
		}
		nbt.setBoolean("Warhorn", isWarhorn);
		nbt.setBoolean("NPCPersistent", spawnsPersistent);
		if (!bonusFactions.isEmpty()) {
			NBTTagList bonusTags = new NBTTagList();
			for (LOTRFaction f : bonusFactions) {
				String fName = f.codeName();
				bonusTags.appendTag(new NBTTagString(fName));
			}
			nbt.setTag("BonusFactions", bonusTags);
		}
	}

}
