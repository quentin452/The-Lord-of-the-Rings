package lotr.common.entity.npc;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.quest.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGandalf extends LOTREntityNPC {
	public LOTREntityGandalf(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.8, false));
		tasks.addTask(2, new EntityAIOpenDoor(this, true));
		tasks.addTask(3, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new LOTREntityAIGandalfSmoke(this, 3000));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.05f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAILookIdle(this));
		int target = addTargetTasks(false);
		targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityBalrog.class, 0, true));
		targetTasks.addTask(target + 2, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntitySaruman.class, 0, true));
		npcCape = LOTRCapes.GANDALF;
	}

	public boolean addMQOfferFor(EntityPlayer entityplayer) {
		LOTRMiniQuestWelcome quest;
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		if (pd.getMiniQuestsForEntity(this, true).isEmpty() && ((LOTRMiniQuest) (quest = new LOTRMiniQuestWelcome(null, this))).canPlayerAccept(entityplayer)) {
			questInfo.setPlayerSpecificOffer(entityplayer, quest);
			return true;
		}
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
	}

	public void arriveAt(EntityPlayer entityplayer) {
		Collection<EntityPlayer> msgPlayers = new ArrayList<>();
		if (entityplayer != null) {
			msgPlayers.add(entityplayer);
		}
		List worldPlayers = worldObj.playerEntities;
		for (Object obj : worldPlayers) {
			EntityPlayer player = (EntityPlayer) obj;
			if (msgPlayers.contains(player)) {
				continue;
			}
			double d = 64.0;
			double dSq = d * d;
			if (getDistanceSqToEntity(player) >= dSq) {
				continue;
			}
			msgPlayers.add(player);
		}
		for (EntityPlayer player : msgPlayers) {
			LOTRSpeech.sendSpeechAndChatMessage(player, this, "char/gandalf/arrive");
		}
		doGandalfFX();
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			return super.attackEntityFrom(damagesource, f);
		}
		f = 0.0f;
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
		return false;
	}

	public void depart() {
		Collection<EntityPlayer> msgPlayers = new ArrayList<>();
		List worldPlayers = worldObj.playerEntities;
		for (Object obj : worldPlayers) {
			EntityPlayer player = (EntityPlayer) obj;
			if (msgPlayers.contains(player)) {
				continue;
			}
			double d = 64.0;
			double dSq = d * d;
			if (getDistanceSqToEntity(player) >= dSq) {
				continue;
			}
			msgPlayers.add(player);
		}
		for (EntityPlayer player : msgPlayers) {
			LOTRSpeech.sendSpeechAndChatMessage(player, this, "char/gandalf/depart");
		}
		doGandalfFX();
		setDead();
	}

	public void doGandalfFX() {
		playSound("random.pop", 2.0f, 0.5f + rand.nextFloat() * 0.5f);
		worldObj.setEntityState(this, (byte) 16);
	}

	@Override
	public ItemStack getHeldItemLeft() {
		ItemStack heldItem = getHeldItem();
		if (heldItem != null && heldItem.getItem() == LOTRMod.glamdring) {
			return new ItemStack(LOTRMod.gandalfStaffGrey);
		}
		return super.getHeldItemLeft();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "char/gandalf/friendly";
		}
		return "char/gandalf/hostile";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int i = 0; i < 20; ++i) {
				double d0 = posX + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * width;
				double d1 = posY + MathHelper.randomFloatClamp(rand, 0.0f, 1.0f) * height;
				double d2 = posZ + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * width;
				double d3 = rand.nextGaussian() * 0.02;
				double d4 = 0.05 + rand.nextGaussian() * 0.02;
				double d5 = rand.nextGaussian() * 0.02;
				worldObj.spawnParticle("explode", d0, d1, d2, d3, d4, d5);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public void onArtificalSpawn() {
		LOTRGreyWandererTracker.addNewWanderer(getUniqueID());
		arriveAt(null);
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && !LOTRGreyWandererTracker.isWandererActive(getUniqueID()) && getAttackTarget() == null) {
			depart();
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.glamdring));
		npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gandalfStaffGrey));
		return data;
	}

	@Override
	public boolean speakTo(EntityPlayer entityplayer) {
		if (LOTRGreyWandererTracker.isWandererActive(getUniqueID())) {
			if (questInfo.getOfferFor(entityplayer) != null) {
				return super.speakTo(entityplayer);
			}
			if (addMQOfferFor(entityplayer)) {
				LOTRGreyWandererTracker.setWandererActive(getUniqueID());
				String speechBank = "char/gandalf/welcome";
				sendSpeechBank(entityplayer, speechBank);
				return true;
			}
		}
		return super.speakTo(entityplayer);
	}
}
