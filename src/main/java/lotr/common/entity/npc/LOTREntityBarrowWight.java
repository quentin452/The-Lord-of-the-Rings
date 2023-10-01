package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityBarrowWight extends LOTREntityNPC {
	public static Potion[] attackEffects = {Potion.moveSlowdown, Potion.digSlowdown, Potion.wither};

	public LOTREntityBarrowWight(World world) {
		super(world);
		setSize(0.8f, 2.5f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, getWightAttackAI());
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true);
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(6.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				for (Potion effect : attackEffects) {
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(effect.id, duration * 20, 0));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canDropRares() {
		return true;
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		if (rand.nextBoolean()) {
			dropChestContents(LOTRChestContents.BARROW_DOWNS, 1, 2 + i + 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, -1);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public String getDeathSound() {
		return "lotr:wight.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 4 + rand.nextInt(5);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.HOSTILE;
	}

	@Override
	public String getHurtSound() {
		return "lotr:wight.hurt";
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killBarrowWight;
	}

	public int getTargetEntityID() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public void setTargetEntityID(Entity entity) {
		dataWatcher.updateObject(16, entity == null ? -1 : entity.getEntityId());
	}

	public EntityAIBase getWightAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, false);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			for (int l = 0; l < 1; ++l) {
				double d = posX + width * MathHelper.getRandomDoubleInRange(rand, -0.5, 0.5);
				double d1 = posY + height * MathHelper.getRandomDoubleInRange(rand, 0.4, 0.8);
				double d2 = posZ + width * MathHelper.getRandomDoubleInRange(rand, -0.5, 0.5);
				double d3 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				double d4 = MathHelper.getRandomDoubleInRange(rand, -0.2, -0.05);
				double d5 = MathHelper.getRandomDoubleInRange(rand, -0.1, 0.1);
				if (rand.nextBoolean()) {
					LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
					continue;
				}
				worldObj.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
			}
		}
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		super.setAttackTarget(target, speak);
		if (!worldObj.isRemote) {
			setTargetEntityID(target);
		}
	}
}
