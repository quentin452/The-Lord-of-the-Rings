package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRMaterial;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityHalfTroll extends LOTREntityNPC {
	public LOTREntityHalfTroll(World world) {
		super(world);
		setSize(1.0f, 2.4f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, createHalfTrollAttackAI());
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new LOTREntityAIEat(this, LOTRFoods.HALF_TROLL, 6000));
		tasks.addTask(5, new LOTREntityAIDrink(this, LOTRFoods.HALF_TROLL_DRINK, 6000));
		tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(8, new EntityAILookIdle(this));
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 1000, false));
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(6.0);
		getEntityAttribute(horseAttackSpeed).setBaseValue(1.5);
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		block3:
		{
			block2:
			{
				switch (slot) {
					case 0:
					case 2:
					case 1:
						break block2;
					default:
						break;
				}
				if (slot != 3) {
					break block3;
				}
			}
			return itemstack != null && itemstack.getItem() instanceof ItemArmor && ((ItemArmor) itemstack.getItem()).getArmorMaterial() == LOTRMaterial.HALF_TROLL.toArmorMaterial();
		}
		return super.canReEquipHired(slot, itemstack);
	}

	public EntityAIBase createHalfTrollAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.HALF_TROLL.createQuest(this);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int flesh = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < flesh; ++l) {
			dropItem(Items.rotten_flesh, 1);
		}
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.trollBone, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.HALF_TROLL;
	}

	@Override
	public String getDeathSound() {
		return "lotr:halfTroll.death";
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.HALF_TROLL;
	}

	public boolean getHalfTrollModelFlag(int part) {
		byte i = dataWatcher.getWatchableObjectByte(17);
		return (i & 1 << part) != 0;
	}

	@Override
	public String getHurtSound() {
		return "lotr:halfTroll.hurt";
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killHalfTroll;
	}

	@Override
	public String getLivingSound() {
		return "lotr:halfTroll.say";
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "halfTroll/halfTroll/hired";
			}
			return "halfTroll/halfTroll/friendly";
		}
		return "halfTroll/halfTroll/hostile";
	}

	public boolean hasFullHorns() {
		return getHalfTrollModelFlag(3);
	}

	public boolean hasHorns() {
		return getHalfTrollModelFlag(2);
	}

	public boolean hasMohawk() {
		return getHalfTrollModelFlag(1);
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
		setHasMohawk(rand.nextBoolean());
		if (rand.nextBoolean()) {
			setHasHorns(true);
			setHasFullHorns(rand.nextBoolean());
		}
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setHasMohawk(nbt.getBoolean("Mohawk"));
		setHasHorns(nbt.getBoolean("Horns"));
		setHasFullHorns(nbt.getBoolean("HornsFull"));
		if (nbt.hasKey("HalfTrollName")) {
			familyInfo.setName(nbt.getString("HalfTrollName"));
		}
	}

	public void setHalfTrollModelFlag(int part, boolean flag) {
		int i = dataWatcher.getWatchableObjectByte(17);
		int pow2 = 1 << part;
		i = flag ? i | pow2 : i & ~pow2;
		dataWatcher.updateObject(17, (byte) i);
	}

	public void setHasFullHorns(boolean flag) {
		setHalfTrollModelFlag(3, flag);
	}

	public void setHasHorns(boolean flag) {
		setHalfTrollModelFlag(2, flag);
	}

	public void setHasMohawk(boolean flag) {
		setHalfTrollModelFlag(1, flag);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getOrcName(rand));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("Mohawk", hasMohawk());
		nbt.setBoolean("Horns", hasHorns());
		nbt.setBoolean("HornsFull", hasFullHorns());
	}
}
