package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityFlamingo extends EntityAnimal {
	public static int NECK_TIME = 20;
	public static int FISHING_TIME = 160;
	public static int FISHING_TIME_TOTAL = 200;
	public boolean field_753_a;
	public float field_752_b;
	public float destPos;
	public float field_757_d;
	public float field_756_e;
	public float field_755_h = 5.0f;

	public LOTREntityFlamingo(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.3));
		tasks.addTask(2, new EntityAIMate(this, 1.0));
		tasks.addTask(3, new EntityAITempt(this, 1.2, Items.fish, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.2));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float f) {
		boolean flag = super.attackEntityFrom(source, f);
		if (flag && !worldObj.isRemote && getFishingTickCur() > 20) {
			setFishingTick(20, 20);
		}
		return flag;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity) {
		return new LOTREntityFlamingo(worldObj);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public String getDeathSound() {
		return "lotr:flamingo.death";
	}

	@Override
	public Item getDropItem() {
		return Items.feather;
	}

	public int getFishingTick() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public int getFishingTickCur() {
		return getFishingTick() & 0xFFFF;
	}

	public int getFishingTickPre() {
		return getFishingTick() >> 16;
	}

	@Override
	public String getHurtSound() {
		return "lotr:flamingo.hurt";
	}

	@Override
	public String getLivingSound() {
		return "lotr:flamingo.say";
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean isBreedingItem(ItemStack itemstack) {
		return itemstack.getItem() == Items.fish;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		field_756_e = field_752_b;
		field_757_d = destPos;
		destPos = (float) (destPos + (onGround || inWater ? -1 : 4) * 0.3);
		if (destPos < 0.0f) {
			destPos = 0.0f;
		}
		if (destPos > 1.0f) {
			destPos = 1.0f;
		}
		if (!onGround && !inWater && field_755_h < 1.0f) {
			field_755_h = 1.0f;
		}
		field_755_h = (float) (field_755_h * 0.9);
		if (!onGround && !inWater && motionY < 0.0) {
			motionY *= 0.6;
		}
		field_752_b += field_755_h * 2.0f;
		if (!worldObj.isRemote && !isChild() && !isInLove() && getFishingTickCur() == 0 && rand.nextInt(600) == 0 && worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY), MathHelper.floor_double(posZ)) == Blocks.water) {
			setFishingTick(200, 200);
		}
		if (getFishingTickCur() > 0) {
			if (worldObj.isRemote) {
				for (int i = 0; i < 3; ++i) {
					double d = posX + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					double d1 = boundingBox.minY + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					double d2 = posZ + MathHelper.getRandomDoubleInRange(rand, -0.3, 0.3);
					worldObj.spawnParticle("bubble", d, d1, d2, 0.0, 0.0, 0.0);
				}
			} else {
				int cur = getFishingTickCur();
				setFishingTick(cur, cur - 1);
			}
		}
		if (!worldObj.isRemote && isInLove() && getFishingTickCur() > 20) {
			setFishingTick(20, 20);
		}
	}

	public void setFishingTick(int pre, int cur) {
		int i = pre << 16 | cur & 0xFFFF;
		dataWatcher.updateObject(16, i);
	}
}
