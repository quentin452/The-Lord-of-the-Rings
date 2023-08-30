package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDwarf extends LOTREntityNPC {
	public LOTREntityDwarf(World world) {
		super(world);
		setSize(0.5f, 1.5f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, new LOTREntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
		tasks.addTask(3, getDwarfAttackAI());
		tasks.addTask(4, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(5, new LOTREntityAINPCMarry(this, 1.3));
		tasks.addTask(6, new LOTREntityAINPCMate(this, 1.3));
		tasks.addTask(7, new LOTREntityAINPCFollowParent(this, 1.4));
		tasks.addTask(8, new LOTREntityAINPCFollowSpouse(this, 1.1));
		tasks.addTask(9, new EntityAIOpenDoor(this, true));
		tasks.addTask(10, new EntityAIWander(this, 1.0));
		tasks.addTask(11, new LOTREntityAIEat(this, getDwarfFoods(), 6000));
		tasks.addTask(11, new LOTREntityAIDrink(this, LOTRFoods.DWARF_DRINK, 6000));
		tasks.addTask(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(13, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(14, new EntityAILookIdle(this));
		addTargetTasks(true);
		familyInfo.marriageEntityClass = LOTREntityDwarf.class;
		familyInfo.marriageRing = LOTRMod.dwarvenRing;
		familyInfo.marriageAlignmentRequired = 200.0f;
		familyInfo.marriageAchievement = LOTRAchievement.marryDwarf;
		familyInfo.potentialMaxChildren = 3;
		familyInfo.timeToMature = 72000;
		familyInfo.breedingDelay = 48000;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	public boolean canDwarfSpawnAboveGround() {
		return true;
	}

	public boolean canDwarfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		if (rand.nextInt(200) == 0) {
			return canDwarfSpawnAboveGround();
		}
		return j < 60 && worldObj.getBlock(i, j - 1, k).getMaterial() == Material.rock && !worldObj.canBlockSeeTheSky(i, j, k) && worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) >= 10;
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.DURIN.createQuest(this);
	}

	@Override
	public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
		familyInfo.setName(LOTRNames.getDwarfChildNameForParent(rand, familyInfo.isMale(), (LOTREntityDwarf) maleParent));
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.dwarfBone, 1);
		}
		if (rand.nextInt(4) == 0) {
			dropChestContents(getLarderDrops(), 1, 2 + i);
		}
		if (rand.nextInt(8) == 0) {
			dropChestContents(getGenericDrops(), 1, 2 + i);
		}
		if (flag) {
			int rareDropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(rareDropChance, 1)) == 0) {
				int randDrop = rand.nextInt(4);
				switch (randDrop) {
					case 0: {
						entityDropItem(new ItemStack(Items.iron_ingot), 0.0f);
						break;
					}
					case 1: {
						entityDropItem(new ItemStack(getDwarfSteelDrop()), 0.0f);
						break;
					}
					case 2: {
						entityDropItem(new ItemStack(Items.gold_nugget, 1 + rand.nextInt(3)), 0.0f);
						break;
					}
					case 3: {
						entityDropItem(new ItemStack(LOTRMod.silverNugget, 1 + rand.nextInt(3)), 0.0f);
					}
				}
			}
			int mithrilBookChance = 40 - i * 5;
			if (rand.nextInt(Math.max(mithrilBookChance, 1)) == 0) {
				entityDropItem(new ItemStack(LOTRMod.mithrilBook), 0.0f);
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public String getAttackSound() {
		return "lotr:dwarf.attack";
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenIronHills || biome instanceof LOTRBiomeGenErebor || biome instanceof LOTRBiomeGenBlueMountains || biome instanceof LOTRBiomeGenRedMountains) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.DURIN;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			return canDwarfSpawnHere();
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "lotr:dwarf.hurt";
	}

	public EntityAIBase getDwarfAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	public LOTRFoods getDwarfFoods() {
		return LOTRFoods.DWARF;
	}

	public Item getDwarfSteelDrop() {
		return LOTRMod.dwarfSteel;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DURINS_FOLK;
	}

	public LOTRChestContents getGenericDrops() {
		return LOTRChestContents.DWARVEN_TOWER;
	}

	@Override
	public String getHurtSound() {
		return "lotr:dwarf.hurt";
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killDwarf;
	}

	public LOTRChestContents getLarderDrops() {
		return LOTRChestContents.DWARF_HOUSE_LARDER;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public float getSoundPitch() {
		float f = super.getSoundPitch();
		if (!familyInfo.isMale()) {
			f *= 1.4f;
		}
		return f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "dwarf/dwarf/hired";
			}
			return isChild() ? "dwarf/child/friendly" : "dwarf/dwarf/friendly";
		}
		return isChild() ? "dwarf/child/hostile" : "dwarf/dwarf/hostile";
	}

	@Override
	public LOTRAchievement getTalkAchievement() {
		if (!familyInfo.isMale()) {
			return LOTRAchievement.talkDwarfWoman;
		}
		return super.getTalkAchievement();
	}

	@Override
	public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
		data = super.initCreatureForHire(data);
		data = onSpawnWithEgg(data);
		if (getClass() == familyInfo.marriageEntityClass && rand.nextInt(3) == 0) {
			familyInfo.setMale(false);
			setupNPCName();
		}
		return data;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (familyInfo.interact(entityplayer)) {
			return true;
		}
		return super.interact(entityplayer);
	}

	@Override
	public void onArtificalSpawn() {
		if (getClass() == familyInfo.marriageEntityClass) {
			if (rand.nextInt(3) == 0) {
				familyInfo.setMale(false);
				setupNPCName();
			}
			if (rand.nextInt(20) == 0) {
				familyInfo.setChild();
			}
		}
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
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		playSound("lotr:dwarf.kill", getSoundVolume(), getSoundPitch());
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDwarven));
		npcItemsInv.setIdleItem(null);
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("DwarfName")) {
			familyInfo.setName(nbt.getString("DwarfName"));
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getDwarfName(rand, familyInfo.isMale()));
	}
}
