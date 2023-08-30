package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDunlending extends LOTREntityMan {
	public LOTREntityDunlending(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, getDunlendingAttackAI());
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
		tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.DUNLAND.createQuest(this);
	}

	public void dropDunlendingItems(boolean flag, int i) {
		if (rand.nextInt(5) == 0) {
			dropChestContents(LOTRChestContents.DUNLENDING_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropDunlendingItems(flag, i);
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenDunland || biome instanceof LOTRBiomeGenAdornland) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.DUNLAND;
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
			BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
			return j > 62 && worldObj.getBlock(i, j - 1, k) == biome.topBlock;
		}
		return false;
	}

	public EntityAIBase getDunlendingAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, false);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.DUNLAND;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killDunlending;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isDrunkard()) {
			return "dunlending/drunkard/neutral";
		}
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "dunlending/dunlending/hired";
			}
			return "dunlending/dunlending/friendly";
		}
		return "dunlending/dunlending/hostile";
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
		int i = rand.nextInt(9);
		switch (i) {
			case 0:
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingClub));
				break;
			case 2:
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingTrident));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.wooden_sword));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_sword));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_axe));
				break;
			case 7:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_hoe));
				break;
			case 8:
				npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearStone));
				break;
			default:
				break;
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(4) == 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetFur));
		}
		if (rand.nextInt(10000) == 0) {
			LOTREntityUrukWargBombardier warg = new LOTREntityUrukWargBombardier(worldObj);
			warg.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0f);
			warg.onSpawnWithEgg(null);
			warg.isNPCPersistent = isNPCPersistent;
			worldObj.spawnEntityInWorld(warg);
			mountEntity(warg);
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.orcBomb));
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcBomb));
			npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		}
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("DunlendingName")) {
			familyInfo.setName(nbt.getString("DunlendingName"));
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getDunlendingName(rand, familyInfo.isMale()));
	}
}
