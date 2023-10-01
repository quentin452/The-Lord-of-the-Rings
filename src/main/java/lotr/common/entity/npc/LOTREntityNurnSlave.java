package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IPlantable;

public class LOTREntityNurnSlave extends LOTREntityMan implements LOTRFarmhand {
	public boolean isFree;

	public LOTREntityNurnSlave(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.3, false));
		tasks.addTask(2, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(3, new LOTREntityAIFarm(this, 1.0, 1.0f));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.NURN_SLAVE, 12000));
		tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.NURN_SLAVE_DRINK, 12000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		targetTasks.taskEntries.clear();
		targetTasks.addTask(1, new LOTREntityAINPCHurtByTarget(this, false));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
		if (!isFree && !LOTRMod.getNPCFaction(attacker).isBadRelation(getHiringFaction())) {
			return false;
		}
		return super.canBeFreelyTargetedBy(attacker);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (!isFree && biome instanceof LOTRBiomeGenNurn) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.GONDOR;
	}

	@Override
	public LOTRFaction getHiringFaction() {
		if (!isFree) {
			return LOTRFaction.MORDOR;
		}
		return super.getHiringFaction();
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFree) {
			if (isFriendly(entityplayer)) {
				if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
					return "mordor/nurnSlave/free_hired";
				}
				return "mordor/nurnSlave/free_friendly";
			}
			return "mordor/nurnSlave/free_hostile";
		}
		if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
			return "mordor/nurnSlave/hired";
		}
		return "mordor/nurnSlave/neutral";
	}

	@Override
	public IPlantable getUnhiredSeeds() {
		return (IPlantable) Items.wheat_seeds;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeOrc));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getGondorName(rand, familyInfo.isMale()));
	}
}
