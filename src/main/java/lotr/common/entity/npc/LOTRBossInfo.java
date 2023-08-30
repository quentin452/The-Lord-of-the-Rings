package lotr.common.entity.npc;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import lotr.common.*;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class LOTRBossInfo {
	public static int PLAYER_HURT_COOLDOWN = 600;
	public static float PLAYER_DAMAGE_THRESHOLD = 40.0f;
	public LOTREntityNPC theNPC;
	public LOTRBoss theBoss;
	public EntityPlayer lastAttackingPlayer;
	public Map<UUID, Pair<Integer, Float>> playerHurtTimes = new HashMap<>();
	public boolean jumpAttack;

	public LOTRBossInfo(LOTRBoss boss) {
		theBoss = boss;
		theNPC = (LOTREntityNPC) theBoss;
	}

	public void clearSurroundingBlocks() {
		if (LOTRMod.canGrief(theNPC.worldObj)) {
			int xzRange = MathHelper.ceiling_float_int(theNPC.width / 2.0f * 1.5f);
			int yRange = MathHelper.ceiling_float_int(theNPC.height * 1.5f);
			int xzDist = xzRange * xzRange + xzRange * xzRange;
			int i = MathHelper.floor_double(theNPC.posX);
			int j = MathHelper.floor_double(theNPC.boundingBox.minY);
			int k = MathHelper.floor_double(theNPC.posZ);
			for (int i1 = i - xzRange; i1 <= i + xzRange; ++i1) {
				for (int j1 = j; j1 <= j + yRange; ++j1) {
					for (int k1 = k - xzRange; k1 <= k + xzRange; ++k1) {
						float resistance;
						Block block;
						int i2 = i1 - i;
						int k2 = k1 - k;
						int dist = i2 * i2 + k2 * k2;
						if (dist >= xzDist || (block = theNPC.worldObj.getBlock(i1, j1, k1)) == null || block.getMaterial().isLiquid() || (resistance = block.getExplosionResistance(theNPC, theNPC.worldObj, i1, j1, k1, theNPC.posX, theNPC.boundingBox.minY + theNPC.height / 2.0f, theNPC.posZ)) >= 2000.0f) {
							continue;
						}
						block.dropBlockAsItemWithChance(theNPC.worldObj, i1, j1, k1, theNPC.worldObj.getBlockMetadata(i1, j1, k1), resistance / 100.0f, 0);
						theNPC.worldObj.setBlockToAir(i1, j1, k1);
					}
				}
			}
		}
	}

	public void doJumpAttack(double jumpSpeed) {
		jumpAttack = true;
		theNPC.motionY = jumpSpeed;
	}

	public void doTargetedJumpAttack(double jumpSpeed) {
		if (!theNPC.worldObj.isRemote && lastAttackingPlayer != null && (lastAttackingPlayer.posY - theNPC.posY > 10.0 || theNPC.getDistanceSqToEntity(lastAttackingPlayer) > 400.0) && theNPC.onGround) {
			doJumpAttack(jumpSpeed);
			theNPC.motionX = lastAttackingPlayer.posX - theNPC.posX;
			theNPC.motionY = lastAttackingPlayer.posY - theNPC.posY;
			theNPC.motionZ = lastAttackingPlayer.posZ - theNPC.posZ;
			theNPC.motionX /= 10.0;
			theNPC.motionY /= 10.0;
			theNPC.motionZ /= 10.0;
			if (theNPC.motionY < jumpSpeed) {
				theNPC.motionY = jumpSpeed;
			}
			theNPC.getLookHelper().setLookPositionWithEntity(lastAttackingPlayer, 100.0f, 100.0f);
			theNPC.getLookHelper().onUpdateLook();
			theNPC.rotationYaw = theNPC.rotationYawHead;
		}
	}

	public float getHealthChanceModifier() {
		float f = 1.0f - theNPC.getHealth() / theNPC.getMaxHealth();
		return MathHelper.sqrt_float(f);
	}

	public List getNearbyEnemies() {
		List<EntityPlayer> enemies = new ArrayList<>();
		List players = theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theNPC.boundingBox.expand(12.0, 6.0, 12.0));
		for (Object player : players) {
			EntityPlayer entityplayer = (EntityPlayer) player;
			if (entityplayer.capabilities.isCreativeMode || LOTRLevelData.getData(entityplayer).getAlignment(theNPC.getFaction()) >= 0.0f) {
				continue;
			}
			enemies.add(entityplayer);
		}
		enemies.addAll(theNPC.worldObj.selectEntitiesWithinAABB(EntityLiving.class, theNPC.boundingBox.expand(12.0, 6.0, 12.0), new LOTRNPCTargetSelector(theNPC)));
		return enemies;
	}

	public void onDeath(DamageSource damagesource) {
		onHurt(damagesource, 0.0f);
		if (!theNPC.worldObj.isRemote) {
			for (Map.Entry<UUID, Pair<Integer, Float>> e : playerHurtTimes.entrySet()) {
				UUID player = e.getKey();
				Pair<Integer, Float> pair = e.getValue();
				float damage = pair.getRight();
				if (damage < PLAYER_DAMAGE_THRESHOLD) {
					continue;
				}
				LOTRLevelData.getData(player).addAchievement(theBoss.getBossKillAchievement());
			}
		}
	}

	public float onFall(float fall) {
		if (!theNPC.worldObj.isRemote && jumpAttack) {
			fall = 0.0f;
			jumpAttack = false;
			List enemies = getNearbyEnemies();
			float attackDamage = (float) theNPC.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			for (Object enemie : enemies) {
				EntityLivingBase entity = (EntityLivingBase) enemie;
				float strength = 12.0f - theNPC.getDistanceToEntity(entity) / 3.0f;
				entity.attackEntityFrom(DamageSource.causeMobDamage(theNPC), (strength /= 12.0f) * attackDamage * 3.0f);
				float knockback = strength * 3.0f;
				entity.addVelocity(-MathHelper.sin(theNPC.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f, 0.25 * knockback, MathHelper.cos(theNPC.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f);
			}
			theBoss.onJumpAttackFall();
		}
		return fall;
	}

	public void onHurt(DamageSource damagesource, float f) {
		if (!theNPC.worldObj.isRemote) {
			EntityPlayer playerSource;
			if (damagesource.getEntity() instanceof EntityPlayer) {
				EntityPlayer attackingPlayer = (EntityPlayer) damagesource.getEntity();
				if (!attackingPlayer.capabilities.isCreativeMode) {
					lastAttackingPlayer = attackingPlayer;
				}
			}
			playerSource = LOTRMod.getDamagingPlayerIncludingUnits(damagesource);
			if (playerSource != null) {
				UUID player = playerSource.getUniqueID();
				int time = PLAYER_HURT_COOLDOWN;
				float totalDamage = f;
				if (playerHurtTimes.containsKey(player)) {
					Pair<Integer, Float> pair = playerHurtTimes.get(player);
					totalDamage += pair.getRight();
				}
				playerHurtTimes.put(player, Pair.of(time, totalDamage));
			}
		}
	}

	public void onUpdate() {
		if (lastAttackingPlayer != null && (!lastAttackingPlayer.isEntityAlive() || lastAttackingPlayer.capabilities.isCreativeMode)) {
			lastAttackingPlayer = null;
		}
		if (!theNPC.worldObj.isRemote) {
			Map<UUID, Pair<Integer, Float>> playerHurtTimes_new = new HashMap<>();
			for (Map.Entry<UUID, Pair<Integer, Float>> entry : playerHurtTimes.entrySet()) {
				UUID player = entry.getKey();
				int time = entry.getValue().getLeft();
				float damage = entry.getValue().getRight();
				time--;
				if (time <= 0) {
					continue;
				}
				playerHurtTimes_new.put(player, Pair.of(time, damage));
			}
			playerHurtTimes = playerHurtTimes_new;
		}
		if (!theNPC.worldObj.isRemote && jumpAttack && theNPC.ticksExisted % 5 == 0) {
			clearSurroundingBlocks();
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag("NPCBossInfo");
		if (data != null) {
			NBTTagList playerHurtTags = data.getTagList("PlayerHurtTimes", 10);
			for (int i = 0; i < playerHurtTags.tagCount(); ++i) {
				NBTTagCompound playerTag = playerHurtTags.getCompoundTagAt(i);
				UUID player = UUID.fromString(playerTag.getString("UUID"));
				int time = playerTag.getInteger("Time");
				float damage = playerTag.getFloat("Damage");
				playerHurtTimes.put(player, Pair.of(time, damage));
			}
			jumpAttack = data.getBoolean("JumpAttack");
		}
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		NBTTagList playerHurtTags = new NBTTagList();
		for (Map.Entry<UUID, Pair<Integer, Float>> entry : playerHurtTimes.entrySet()) {
			UUID player = entry.getKey();
			Pair<Integer, Float> pair = entry.getValue();
			int time = pair.getLeft();
			float damage = pair.getRight();
			NBTTagCompound playerTag = new NBTTagCompound();
			playerTag.setString("UUID", player.toString());
			playerTag.setInteger("Time", time);
			playerTag.setFloat("Damage", damage);
			playerHurtTags.appendTag(playerTag);
		}
		data.setTag("PlayerHurtTimes", playerHurtTags);
		data.setBoolean("JumpAttack", jumpAttack);
		nbt.setTag("NPCBossInfo", data);
	}
}
