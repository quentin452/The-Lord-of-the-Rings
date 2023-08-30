package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class LOTREntityMirkTroll extends LOTREntityTroll {
	public LOTREntityMirkTroll(World world) {
		super(world);
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
		trollImmuneToSun = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0);
		getEntityAttribute(npcAttackDamage).setBaseValue(6.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int duration;
			if (entity instanceof EntityLivingBase && (duration = worldObj.difficultySetting.getDifficultyId() * 3 - 1) > 0) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void dropTrollItems(boolean flag, int i) {
		if (flag) {
			int rareDropChance = 8 - i;
			if (rareDropChance < 1) {
				rareDropChance = 1;
			}
			if (rand.nextInt(rareDropChance) == 0) {
				int drops = 1 + rand.nextInt(2) + rand.nextInt(i + 1);
				for (int j = 0; j < drops; ++j) {
					dropItem(LOTRMod.orcSteel, 1);
				}
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 4.0f;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 4 + rand.nextInt(7);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DOL_GULDUR;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killMirkTroll;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		return null;
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
	}

	@Override
	public float getTrollScale() {
		return 1.2f;
	}

	@Override
	public boolean hasTrollName() {
		return false;
	}
}
