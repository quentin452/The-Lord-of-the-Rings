package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityScorpion extends EntityMob implements LOTRMobSpawnerCondition {
	@SuppressWarnings("Convert2Lambda")
	public static IEntitySelector noWraiths = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return !(entity instanceof LOTREntityHaradPyramidWraith);
		}
	};
	public float scorpionWidth = -1.0f;
	public float scorpionHeight;
	public boolean spawningFromSpawner;

	protected LOTREntityScorpion(World world) {
		super(world);
		setSize(1.2f, 0.9f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.2, false));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.05f));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 0, true, false, noWraiths));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + getScorpionScale() * 6.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - getScorpionScale() * 0.05);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + getScorpionScale());
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			int difficulty;
			int duration;
			if (!worldObj.isRemote) {
				setStrikeTime(20);
			}
			if (entity instanceof EntityLivingBase && (duration = (difficulty = worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
			}
			return true;
		}
		return false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		int k = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int j = 0; j < k; ++j) {
			dropItem(Items.rotten_flesh, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) getRandomScorpionScale());
		dataWatcher.addObject(19, 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("mob.spider.step", 0.15f, 1.0f);
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		if (spawningFromSpawner) {
			return 0.0f;
		}
		return super.getBlockPathWeight(i, j, k);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public String getDeathSound() {
		return "mob.spider.death";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		int i = getScorpionScale();
		return 2 + i + rand.nextInt(i + 2);
	}

	@Override
	public String getHurtSound() {
		return "mob.spider.say";
	}

	@Override
	public String getLivingSound() {
		return "mob.spider.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	public int getRandomScorpionScale() {
		return rand.nextInt(3);
	}

	public int getScorpionScale() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public float getScorpionScaleAmount() {
		return 0.5f + getScorpionScale() / 2.0f;
	}

	public int getStrikeTime() {
		return dataWatcher.getWatchableObjectInt(19);
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (itemstack != null && itemstack.getItem() == Items.glass_bottle) {
			--itemstack.stackSize;
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LOTRMod.bottlePoison));
			} else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
				entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(LOTRMod.bottlePoison), false);
			}
			return true;
		}
		return super.interact(entityplayer);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		if (effect.getPotionID() == Potion.poison.id) {
			return false;
		}
		return super.isPotionApplicable(effect);
	}

	@Override
	public void onLivingUpdate() {
		int i;
		super.onLivingUpdate();
		rescaleScorpion(getScorpionScaleAmount());
		if (!worldObj.isRemote && (i = getStrikeTime()) > 0) {
			setStrikeTime(i - 1);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setScorpionScale(nbt.getByte("ScorpionScale"));
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + getScorpionScale());
	}

	public void rescaleScorpion(float f) {
		super.setSize(scorpionWidth * f, scorpionHeight * f);
	}

	public void setScorpionScale(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	@Override
	public void setSize(float f, float f1) {
		boolean flag = scorpionWidth > 0.0f;
		scorpionWidth = f;
		scorpionHeight = f1;
		if (!flag) {
			rescaleScorpion(1.0f);
		}
	}

	@Override
	public void setSpawningFromMobSpawner(boolean flag) {
		spawningFromSpawner = flag;
	}

	public void setStrikeTime(int i) {
		dataWatcher.updateObject(19, i);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("ScorpionScale", (byte) getScorpionScale());
	}

}
