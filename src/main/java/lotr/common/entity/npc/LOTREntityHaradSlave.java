package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityHaradSlave extends LOTREntityMan implements LOTRFarmhand {
	public Item seedsItem;

	public LOTREntityHaradSlave(World world) {
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
		tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.HARAD_SLAVE, 12000));
		tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.HARAD_SLAVE_DRINK, 8000));
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
		if (!LOTRMod.getNPCFaction(attacker).isBadRelation(getHiringFaction())) {
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
	public LOTRFaction getFaction() {
		return getSlaveType().faction;
	}

	@Override
	public LOTRFaction getHiringFaction() {
		return LOTRFaction.NEAR_HARAD;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	public SlaveType getSlaveType() {
		int i = dataWatcher.getWatchableObjectByte(20);
		i = MathHelper.clamp_int(i, 0, SlaveType.values().length);
		return SlaveType.values()[i];
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
			return "nearHarad/slave/hired";
		}
		return "nearHarad/slave/neutral";
	}

	@Override
	public IPlantable getUnhiredSeeds() {
		if (seedsItem == null) {
			return (IPlantable) Items.wheat_seeds;
		}
		return (IPlantable) seedsItem;
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
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeBronze));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		Item item;
		SlaveType type;
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("SlaveType") && (type = SlaveType.forName(nbt.getString("SlaveType"))) != null) {
			setSlaveType(type);
		}
		if (nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
			seedsItem = item;
		}
	}

	public void setSlaveType(SlaveType t) {
		int i = t.ordinal();
		dataWatcher.updateObject(20, (byte) i);
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void setupNPCName() {
		dataWatcher.addObject(20, (byte) 0);
		float f = rand.nextFloat();
		if (f < 0.05f) {
			setSlaveType(SlaveType.TAURETHRIM);
		} else if (f < 0.2f) {
			setSlaveType(SlaveType.MORWAITH);
		} else if (f < 0.7f) {
			setSlaveType(SlaveType.NEAR_HARAD);
		} else {
			setSlaveType(SlaveType.GONDOR);
		}
		SlaveType type = getSlaveType();
		if (type == SlaveType.GONDOR) {
			familyInfo.setName(LOTRNames.getGondorName(rand, familyInfo.isMale()));
		} else if (type == SlaveType.NEAR_HARAD) {
			if (rand.nextBoolean()) {
				familyInfo.setName(LOTRNames.getHarnennorName(rand, familyInfo.isMale()));
			} else {
				familyInfo.setName(LOTRNames.getNomadName(rand, familyInfo.isMale()));
			}
		} else if (type == SlaveType.MORWAITH) {
			familyInfo.setName(LOTRNames.getMoredainName(rand, familyInfo.isMale()));
		} else if (type == SlaveType.TAURETHRIM) {
			familyInfo.setName(LOTRNames.getTauredainName(rand, familyInfo.isMale()));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("SlaveType", getSlaveType().saveName());
		if (seedsItem != null) {
			nbt.setInteger("SeedsID", Item.getIdFromItem(seedsItem));
		}
	}

	public enum SlaveType {
		GONDOR(LOTRFaction.GONDOR, "gondor"), NEAR_HARAD(LOTRFaction.NEAR_HARAD, "nearHarad"), MORWAITH(LOTRFaction.MORWAITH, "morwaith"), TAURETHRIM(LOTRFaction.TAURETHRIM, "taurethrim");

		public LOTRFaction faction;
		public String skinDir;

		SlaveType(LOTRFaction f, String s) {
			faction = f;
			skinDir = s;
		}

		public String saveName() {
			return name();
		}

		public static SlaveType forName(String s) {
			for (SlaveType type : values()) {
				if (!type.saveName().equals(s)) {
					continue;
				}
				return type;
			}
			return null;
		}
	}

}
