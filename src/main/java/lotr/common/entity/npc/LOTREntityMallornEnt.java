package lotr.common.entity.npc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMallornEntHeal;
import lotr.common.network.LOTRPacketMallornEntSummon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class LOTREntityMallornEnt extends LOTREntityEnt implements LOTRBoss {
	public static float BOSS_SCALE = 1.5f;
	public static int SPAWN_TIME = 150;
	public static int MAX_LEAF_HEALINGS = 5;
	public LeafHealInfo[] leafHealings;
	public EntityAIBase meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 2.0, false);
	public EntityAIBase rangedAttackAI = new LOTREntityAIRangedAttack(this, 1.5, 30, 50, 24.0f);

	public LOTREntityMallornEnt(World world) {
		super(world);
		setSize(npcWidth * BOSS_SCALE, npcHeight * BOSS_SCALE);
		tasks.taskEntries.clear();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(0, new LOTREntityAIBossJumpAttack(this, 1.5, 0.02f));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0f, 0.02f));
		tasks.addTask(5, new EntityAILookIdle(this));
		resetLeafHealings();
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(8.0);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (getEntSpawnTick() < SPAWN_TIME) {
			return false;
		}
		if (LOTRMod.getDamagingPlayerIncludingUnits(damagesource) == null && f > 1.0f) {
			f = 1.0f;
		}
		if (!isTreeEffectiveDamage(damagesource)) {
			f *= 0.5f;
		}
		if (isWeaponShieldActive() && !damagesource.isFireDamage()) {
			f = 0.0f;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		LOTREntityMallornLeafBomb leaves = new LOTREntityMallornLeafBomb(worldObj, this, target);
		leaves.leavesDamage = 6.0f;
		worldObj.spawnEntityInWorld(leaves);
		playSound("lotr:ent.mallorn.leafAttack", getSoundVolume(), getSoundPitch());
		swingItem();
	}

	@Override
	public void damageEntity(DamageSource damagesource, float f) {
		super.damageEntity(damagesource, f);
		if (!worldObj.isRemote && !hasWeaponShield() && getHealth() <= 0.0f) {
			setHasWeaponShield(true);
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
			setHealth(getMaxHealth());
			sendEntBossSpeech("shield");
		}
	}

	@Override
	public boolean doTreeDamageCalculation() {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int dropped;
		int dropped2;
		super.dropFewItems(flag, i);
		for (int wood = MathHelper.getRandomIntegerInRange(rand, 20, 30 + i * 20); wood > 0; wood -= dropped2) {
			dropped2 = Math.min(20, wood);
			entityDropItem(new ItemStack(LOTRMod.wood, dropped2, 1), 0.0f);
		}
		for (int sticks = MathHelper.getRandomIntegerInRange(rand, 30, 40 + i * 20); sticks > 0; sticks -= dropped) {
			dropped = Math.min(20, sticks);
			entityDropItem(new ItemStack(LOTRMod.mallornStick, dropped), 0.0f);
		}
		entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MALLORN_ENT.trophyID), 0.0f);
		float maceChance = 0.3f;
		maceChance += i * 0.1f;
		if (rand.nextFloat() < maceChance) {
			dropItem(LOTRMod.maceMallornCharred, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(22, (short) 0);
		dataWatcher.addObject(23, (byte) 0);
	}

	@Override
	public float getAlignmentBonus() {
		return 50.0f;
	}

	@Override
	public float getBaseChanceModifier() {
		return bossInfo.getHealthChanceModifier();
	}

	@Override
	public LOTRAchievement getBossKillAchievement() {
		return LOTRAchievement.killMallornEnt;
	}

	public int getEntSpawnTick() {
		return dataWatcher.getWatchableObjectShort(22);
	}

	public void setEntSpawnTick(int i) {
		dataWatcher.updateObject(22, (short) i);
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 100;
	}

	@Override
	public int getExtraHeadBranches() {
		if (hasWeaponShield()) {
			return 0;
		}
		int max = 8;
		float healthR = getHealth() / getMaxHealth();
		int branches = MathHelper.ceiling_float_int(healthR * max);
		return MathHelper.clamp_int(branches, 1, max);
	}

	@Override
	public double getMeleeRange() {
		return 12.0;
	}

	public float getSpawningOffset(float f) {
		float f1 = (getEntSpawnTick() + f) / SPAWN_TIME;
		f1 = Math.min(f1, 1.0f);
		return (1.0f - f1) * -5.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public LOTRAchievement getTalkAchievement() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		int i;
		if (b == 20) {
			for (i = 0; i < 360; i += 2) {
				float angle = (float) Math.toRadians(i);
				double distance = 2.0;
				double d = distance * MathHelper.sin(angle);
				double d1 = distance * MathHelper.cos(angle);
				LOTRMod.proxy.spawnParticle("mEntJumpSmash", posX + d, boundingBox.minY + 0.1, posZ + d1, d * 0.2, 0.2, d1 * 0.2);
			}
		}
		if (b == 21) {
			for (i = 0; i < 200; ++i) {
				double d = posX;
				double d1 = posY + height * 0.5f;
				double d2 = posZ;
				double d3 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				double d4 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				double d5 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				int time = 40 + rand.nextInt(30);
				LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	public boolean hasWeaponShield() {
		return dataWatcher.getWatchableObjectByte(23) == 1;
	}

	@Override
	public boolean isMovementBlocked() {
		if (getEntSpawnTick() < SPAWN_TIME) {
			return true;
		}
		return super.isMovementBlocked();
	}

	public boolean isWeaponShieldActive() {
		return hasWeaponShield() && !isBurning();
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(1, meleeAttackAI);
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(1, meleeAttackAI);
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(1, rangedAttackAI);
		}
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		if (!worldObj.isRemote) {
			worldObj.setEntityState(this, (byte) 21);
			int fireRange = 12;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			for (int i1 = i - fireRange; i1 <= i + fireRange; ++i1) {
				for (int j1 = j - fireRange; j1 <= j + fireRange; ++j1) {
					for (int k1 = k - fireRange; k1 <= k + fireRange; ++k1) {
						Block block = worldObj.getBlock(i1, j1, k1);
						if (!(block instanceof BlockFire)) {
							continue;
						}
						worldObj.setBlockToAir(i1, j1, k1);
					}
				}
			}
		}
		super.onDeath(damagesource);
	}

	@Override
	public void onJumpAttackFall() {
		worldObj.setEntityState(this, (byte) 20);
		playSound("lotr:troll.rockSmash", 1.5f, 0.75f);
	}

	@Override
	public void onLivingUpdate() {
		double d2;
		double d1;
		super.onLivingUpdate();
		if (getEntSpawnTick() < SPAWN_TIME) {
			if (worldObj.isRemote) {
				for (int l = 0; l < 16; ++l) {
					double d = posX + rand.nextGaussian() * width * 0.5;
					d1 = posY + rand.nextDouble() * height + getSpawningOffset(0.0f);
					d2 = posZ + rand.nextGaussian() * width * 0.5;
					LOTRMod.proxy.spawnParticle("mEntSpawn", d, d1, d2, 0.0, 0.0, 0.0);
				}
				int leaves = 8;
				for (int l = 0; l < leaves; ++l) {
					int leafR = (int) ((float) l / leaves);
					float argBase = (float) getEntSpawnTick() + leafR;
					double r = 3.5;
					double up = 0.5;
					for (float extra : new float[]{0.0f, 3.1415927f}) {
						float arg = argBase + extra;
						double x = posX + r * MathHelper.cos(arg);
						double z = posZ + r * MathHelper.sin(arg);
						double y = posY + leafR * up;
						LOTRMod.proxy.spawnParticle("leafGold_40", x, y, z, 0.0, up, 0.0);
					}
				}
			} else {
				setEntSpawnTick(getEntSpawnTick() + 1);
				if (getEntSpawnTick() == SPAWN_TIME) {
					bossInfo.doJumpAttack(1.5);
				}
			}
		}
		if (!worldObj.isRemote) {
			float f = getBaseChanceModifier();
			f *= 0.05f;
			if (rand.nextFloat() < f) {
				bossInfo.doTargetedJumpAttack(1.5);
			}
		}
		if (!worldObj.isRemote && getHealth() < getMaxHealth()) {
			block3:
			for (LeafHealInfo healing : leafHealings) {
				if (healing.active) {
					continue;
				}
				float f = getBaseChanceModifier();
				f *= 0.02f;
				if (rand.nextFloat() >= f) {
					continue;
				}
				int range = 16;
				int i = MathHelper.floor_double(posX);
				int j = MathHelper.floor_double(posY);
				int k = MathHelper.floor_double(posZ);
				for (int l = 0; l < 30; ++l) {
					int k1;
					int j1;
					int i1 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
					Block block = worldObj.getBlock(i1, j1 = j + MathHelper.getRandomIntegerInRange(rand, -range, range), k1 = k + MathHelper.getRandomIntegerInRange(rand, -range, range));
					if (!(block instanceof BlockLeavesBase)) {
						continue;
					}
					healing.active = true;
					healing.leafX = i1;
					healing.leafY = j1;
					healing.leafZ = k1;
					healing.healTime = 15 + rand.nextInt(15);
					sendHealInfoToWatchers(healing);
					continue block3;
				}
			}
		}
		for (LeafHealInfo healing : leafHealings) {
			if (!healing.active) {
				continue;
			}
			int leafX = healing.leafX;
			int leafY = healing.leafY;
			int leafZ = healing.leafZ;
			Block block = worldObj.getBlock(leafX, leafY, leafZ);
			int meta = worldObj.getBlockMetadata(leafX, leafY, leafZ);
			if (block instanceof BlockLeavesBase) {
				if (!worldObj.isRemote) {
					if (ticksExisted % 20 != 0) {
						continue;
					}
					heal(2.0f);
					healing.healTime--;
					if (getHealth() < getMaxHealth() && healing.healTime > 0) {
						continue;
					}
					healing.active = false;
					sendHealInfoToWatchers(healing);
					continue;
				}
				double d = leafX + 0.5f;
				double d12 = leafY + 0.5f;
				double d22 = leafZ + 0.5f;
				double d3 = posX - d;
				double d4 = posY + height * 0.9 - d12;
				double d5 = posZ - d22;
				LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(block) + "_" + meta, d, d12, d22, d3 / 25.0, d4 / 25.0, d5 / 25.0);
				continue;
			}
			if (worldObj.isRemote) {
				continue;
			}
			healing.active = false;
			sendHealInfoToWatchers(healing);
		}
		if (!worldObj.isRemote) {
			if (getHealth() < getMaxHealth() && rand.nextInt(50) == 0) {
				trySummonEnts();
			}
		} else if (getEntSpawnTick() >= SPAWN_TIME) {
			for (int i = 0; i < 2; ++i) {
				double d = posX + (rand.nextDouble() - 0.5) * width;
				d1 = posY + height + rand.nextDouble() * height * 0.5;
				d2 = posZ + (rand.nextDouble() - 0.5) * width;
				double d3 = MathHelper.getRandomDoubleInRange(rand, -0.2, 0.2);
				double d4 = MathHelper.getRandomDoubleInRange(rand, -0.2, 0.0);
				double d5 = MathHelper.getRandomDoubleInRange(rand, -0.2, 0.2);
				int time = 30 + rand.nextInt(30);
				LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
			}
		}
	}

	@Override
	public void onPlayerStartTracking(EntityPlayerMP entityplayer) {
		super.onPlayerStartTracking(entityplayer);
		for (LeafHealInfo healing : leafHealings) {
			healing.sendData(entityplayer);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		resetLeafHealings();
		NBTTagList leafHealingTags = nbt.getTagList("LeafHealings", 10);
		for (int i = 0; i < leafHealingTags.tagCount(); ++i) {
			NBTTagCompound healTag = leafHealingTags.getCompoundTagAt(i);
			byte slot = healTag.getByte("Slot");
			if (slot < 0 || slot >= leafHealings.length) {
				continue;
			}
			LeafHealInfo healing = leafHealings[slot];
			healing.readFromNBT(healTag);
		}
		setEntSpawnTick(nbt.getInteger("EntSpawnTick"));
		setHasWeaponShield(nbt.getBoolean("EntWeaponShield"));
	}

	public void receiveClientHealing(NBTTagCompound data) {
		LeafHealInfo healing = new LeafHealInfo(this, 0);
		healing.receiveData(data);
		leafHealings[healing.slot] = healing;
	}

	public void resetLeafHealings() {
		leafHealings = new LeafHealInfo[MAX_LEAF_HEALINGS];
		for (int i = 0; i < MAX_LEAF_HEALINGS; ++i) {
			leafHealings[i] = new LeafHealInfo(this, i);
		}
	}

	public void sendEntBossSpeech(String speechBank) {
		List players = worldObj.playerEntities;
		double range = 64.0;
		for (Object obj : players) {
			EntityPlayer entityplayer = (EntityPlayer) obj;
			if (getDistanceSqToEntity(entityplayer) > range * range) {
				continue;
			}
			sendSpeechBank(entityplayer, "ent/mallornEnt/" + speechBank);
		}
	}

	public void sendEntSummon(LOTREntityTree tree) {
		IMessage packet = new LOTRPacketMallornEntSummon(getEntityId(), tree.getEntityId());
		LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(tree, 64.0));
	}

	public void sendHealInfoToWatchers(LeafHealInfo healing) {
		int x = MathHelper.floor_double(posX) >> 4;
		int z = MathHelper.floor_double(posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) worldObj).getPlayerManager();
		List players = worldObj.playerEntities;
		for (Object obj : players) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
				continue;
			}
			healing.sendData(entityplayer);
		}
	}

	public void setHasWeaponShield(boolean flag) {
		dataWatcher.updateObject(23, flag ? (byte) 1 : 0);
	}

	@Override
	public boolean shouldBurningPanic() {
		return false;
	}

	public void spawnEntSummonParticles(LOTREntityTree tree) {
		int l;
		int type = tree.getTreeType();
		Block leafBlock = LOTREntityTree.LEAF_BLOCKS[type];
		int leafMeta = LOTREntityTree.LEAF_META[type];
		int particles = 60;
		for (l = 0; l < particles; ++l) {
			float t = (float) l / particles;
			LOTRMod.proxy.spawnParticle("mEntSummon_" + getEntityId() + "_" + tree.getEntityId() + "_" + t + "_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		}
		for (l = 0; l < 120; ++l) {
			double d = tree.posX + (rand.nextDouble() - 0.5) * tree.width;
			double d1 = tree.posY + tree.height * 0.5;
			double d2 = tree.posZ + (rand.nextDouble() - 0.5) * tree.width;
			double d3 = MathHelper.getRandomDoubleInRange(rand, -0.4, 0.4);
			double d4 = MathHelper.getRandomDoubleInRange(rand, -0.4, 0.4);
			double d5 = MathHelper.getRandomDoubleInRange(rand, -0.4, 0.4);
			LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, d, d1, d2, d3, d4, d5);
		}
	}

	public void trySummonEnts() {
		float f = getBaseChanceModifier();
		f *= 0.5f;
		List nearbyTrees = worldObj.getEntitiesWithinAABB(LOTREntityTree.class, boundingBox.expand(24.0, 8.0, 24.0));
		int maxNearbyTrees = 6;
		float nearbyModifier = (float) (maxNearbyTrees - nearbyTrees.size()) / maxNearbyTrees;
		f *= nearbyModifier;
		if (rand.nextFloat() < f) {
			LOTREntityTree tree = rand.nextInt(3) == 0 ? new LOTREntityHuorn(worldObj) : new LOTREntityEnt(worldObj);
			int range = 12;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			for (int l = 0; l < 30; ++l) {
				int j1;
				int k1;
				int i1 = i + MathHelper.getRandomIntegerInRange(rand, -range, range);
				if (!worldObj.getBlock(i1, (j1 = j + MathHelper.getRandomIntegerInRange(rand, -range, range)) - 1, k1 = k + MathHelper.getRandomIntegerInRange(rand, -range, range)).isNormalCube() || worldObj.getBlock(i1, j1, k1).isNormalCube() || worldObj.getBlock(i1, j1 + 1, k1).isNormalCube()) {
					continue;
				}
				tree.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, rand.nextFloat() * 360.0f, 0.0f);
				tree.liftSpawnRestrictions = true;
				if (!tree.getCanSpawnHere()) {
					continue;
				}
				tree.liftSpawnRestrictions = false;
				tree.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(tree);
				sendEntSummon(tree);
				worldObj.playSoundAtEntity(tree, "lotr:ent.mallorn.summonEnt", getSoundVolume(), getSoundPitch());
				break;
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagList leafHealingTags = new NBTTagList();
		for (LeafHealInfo healing : leafHealings) {
			NBTTagCompound healTag = new NBTTagCompound();
			healing.writeToNBT(healTag);
			leafHealingTags.appendTag(healTag);
		}
		nbt.setTag("LeafHealings", leafHealingTags);
		nbt.setInteger("EntSpawnTick", getEntSpawnTick());
		nbt.setBoolean("EntWeaponShield", hasWeaponShield());
	}

	public static class LeafHealInfo {
		public LOTREntityMallornEnt theEnt;
		public int slot;
		public boolean active;
		public int leafX;
		public int leafY;
		public int leafZ;
		public int healTime;

		public LeafHealInfo(LOTREntityMallornEnt ent, int i) {
			theEnt = ent;
			slot = i;
		}

		public void readFromNBT(NBTTagCompound nbt) {
			slot = nbt.getByte("Slot");
			active = nbt.getBoolean("Active");
			leafX = nbt.getInteger("X");
			leafY = nbt.getInteger("Y");
			leafZ = nbt.getInteger("Z");
			healTime = nbt.getShort("healTime");
		}

		public void receiveData(NBTTagCompound nbt) {
			readFromNBT(nbt);
		}

		public void sendData(EntityPlayerMP entityplayer) {
			NBTTagCompound nbt = new NBTTagCompound();
			writeToNBT(nbt);
			IMessage packet = new LOTRPacketMallornEntHeal(theEnt.getEntityId(), nbt);
			LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		}

		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setByte("Slot", (byte) slot);
			nbt.setBoolean("Active", active);
			nbt.setInteger("X", leafX);
			nbt.setInteger("Y", leafY);
			nbt.setInteger("Z", leafZ);
			nbt.setShort("Time", (short) healTime);
		}
	}

}
