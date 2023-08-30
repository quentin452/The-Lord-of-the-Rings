package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntitySauron extends LOTREntityNPC {
	public LOTREntitySauron(World world) {
		super(world);
		setSize(0.8f, 2.2f);
		isImmuneToFire = true;
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAISauronUseMace(this));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
		tasks.addTask(3, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f, 0.02f));
		tasks.addTask(5, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void addPotionEffect(PotionEffect effect) {
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18);
		getEntityAttribute(npcAttackDamage).setBaseValue(8.0);
	}

	@Override
	public int decreaseAirSupply(int i) {
		return i;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.MORDOR;
	}

	public boolean getIsUsingMace() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public int getTotalArmorValue() {
		return 20;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote) {
			worldObj.createExplosion(this, posX, posY, posZ, 3.0f, false);
			setDead();
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && getHealth() < getMaxHealth() && ticksExisted % 10 == 0) {
			heal(2.0f);
		}
		if (getIsUsingMace() && worldObj.isRemote) {
			for (int i = 0; i < 6; ++i) {
				double d = posX - 2.0 + rand.nextFloat() * 4.0f;
				double d1 = posY + rand.nextFloat() * 3.0f;
				double d2 = posZ - 2.0 + rand.nextFloat() * 4.0f;
				double d3 = (posX - d) / 8.0;
				double d4 = (posY + 0.5 - d1) / 8.0;
				double d5 = (posZ - d2) / 8.0;
				double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				double d7 = 1.0 - d6;
				double d8 = 0.0;
				double d9 = 0.0;
				double d10 = 0.0;
				if (d7 > 0.0) {
					d7 *= d7;
					d8 += d3 / d6 * d7 * 0.2;
					d9 += d4 / d6 * d7 * 0.2;
					d10 += d5 / d6 * d7 * 0.2;
				}
				worldObj.spawnParticle("smoke", d, d1, d2, d8, d9, d10);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.sauronMace));
		return data;
	}

	public void setIsUsingMace(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}
}
