package lotr.common.entity.npc;

import java.util.List;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityBreeRuffian extends LOTREntityBreeMan {
	public static ItemStack[] ruffianWeapons = {new ItemStack(Items.iron_sword), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.battleaxeIron)};
	public int ruffianAngerTick;

	@SuppressWarnings("Convert2Lambda")
	protected LOTREntityBreeRuffian(World world) {
		super(world);
		int target = addTargetTasks(false);
		targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, EntityPlayer.class, 0, true, new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				EntityPlayer player = (EntityPlayer) entity;
				return canRuffianTarget(player);
			}
		}));
	}

	@Override
	public void addBreeAvoidAI(int prio) {
		tasks.addTask(prio, new EntityAIAvoidEntity(this, LOTREntityRanger.class, 12.0f, 1.0, 1.5));
		tasks.addTask(prio, new EntityAIAvoidEntity(this, LOTREntityBreeGuard.class, 12.0f, 1.0, 1.5));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (super.attackEntityFrom(damagesource, f)) {
			if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) damagesource.getEntity();
				ruffianAngerTick += 100;
				double range = ruffianAngerTick / 25.0;
				range = Math.min(range, 24.0);
				List nearbyRuffians = worldObj.getEntitiesWithinAABB(LOTREntityBreeRuffian.class, boundingBox.expand(range, range, range));
				for (Object o : nearbyRuffians) {
					LOTREntityBreeRuffian ruffian = (LOTREntityBreeRuffian) o;
					if (!ruffian.isEntityAlive() || ruffian.hiredNPCInfo.isActive && ruffian.hiredNPCInfo.getHiringPlayer() == attacker || ruffian.getAttackTarget() != null) {
						continue;
					}
					ruffian.setAttackTarget(attacker);
					if (!(attacker instanceof EntityPlayer)) {
						continue;
					}
					EntityPlayer player = (EntityPlayer) attacker;
					String speech = ruffian.getSpeechBank(player);
					ruffian.sendSpeechBank(player, speech);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canPickpocket() {
		return false;
	}

	public boolean canRuffianTarget(EntityPlayer player) {
		PotionEffect nausea = player.getActivePotionEffect(Potion.confusion);
		if (nausea != null) {
			int nauseaTime = nausea.getDuration() / 20;
			int minNauseaTime = 20;
			int fullNauseaTime = 120;
			float chance = (float) (nauseaTime - minNauseaTime) / (fullNauseaTime - minNauseaTime);
			return rand.nextFloat() < chance * 0.05f;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int coins = 2 + rand.nextInt(3) + rand.nextInt((i + 1) * 3);
		for (int l = 0; l < coins; ++l) {
			dropItem(LOTRMod.silverCoin, 1);
		}
		if (rand.nextInt(5) == 0) {
			entityDropItem(LOTRItemMug.Vessel.SKULL.getEmptyVessel(), 0.0f);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 0.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.RUFFIAN;
	}

	@Override
	public LOTRFaction getInfluenceZoneFaction() {
		return LOTRFaction.ISENGARD;
	}

	@Override
	public int getMiniquestColor() {
		return LOTRFaction.ISENGARD.getFactionColor();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "bree/ruffian/hired";
			}
			return "bree/ruffian/friendly";
		}
		return "bree/ruffian/hostile";
	}

	@Override
	public boolean isCivilianNPC() {
		return false;
	}

	@Override
	public boolean lootsExtraCoins() {
		return true;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && ruffianAngerTick > 0) {
			--ruffianAngerTick;
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(ruffianWeapons.length);
		npcItemsInv.setMeleeWeapon(ruffianWeapons[i].copy());
		if (rand.nextInt(4) == 0) {
			ItemStack hat = new ItemStack(LOTRMod.leatherHat);
			if (rand.nextBoolean()) {
				LOTRItemLeatherHat.setHatColor(hat, 0);
			} else {
				LOTRItemLeatherHat.setHatColor(hat, 6834742);
			}
			LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
			setCurrentItemOrArmor(4, hat);
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void setupNPCName() {
		boolean flag = rand.nextBoolean();
		if (flag) {
			familyInfo.setName(LOTRNames.getDunlendingName(rand, familyInfo.isMale()));
		} else {
			familyInfo.setName(LOTRNames.getBreeName(rand, familyInfo.isMale()));
		}
	}

}
