package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenShire;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHobbit extends LOTREntityMan {
	public LOTREntityHobbit(World world) {
		super(world);
		setSize(0.45f, 1.2f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityOrc.class, 12.0f, 1.5, 1.8));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityWarg.class, 12.0f, 1.5, 1.8));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityTroll.class, 12.0f, 1.5, 1.8));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntitySpiderBase.class, 12.0f, 1.5, 1.8));
		tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityRuffianBrute.class, 8.0f, 1.0, 1.5));
		tasks.addTask(1, new LOTREntityAIAvoidHuorn(this, 12.0f, 1.5, 1.8));
		tasks.addTask(2, new EntityAIPanic(this, 1.6));
		tasks.addTask(3, new LOTREntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
		tasks.addTask(4, new LOTREntityAIHobbitChildFollowGoodPlayer(this, 12.0f, 1.5));
		tasks.addTask(5, new LOTREntityAINPCMarry(this, 1.3));
		tasks.addTask(6, new LOTREntityAINPCMate(this, 1.3));
		tasks.addTask(7, new LOTREntityAINPCFollowParent(this, 1.4));
		tasks.addTask(8, new LOTREntityAINPCFollowSpouse(this, 1.1));
		tasks.addTask(9, new EntityAIOpenDoor(this, true));
		tasks.addTask(10, new EntityAIWander(this, 1.1));
		tasks.addTask(11, new LOTREntityAIEat(this, getHobbitFoods(), 3000));
		tasks.addTask(11, new LOTREntityAIDrink(this, getHobbitDrinks(), 3000));
		tasks.addTask(11, new LOTREntityAIHobbitSmoke(this, 4000));
		tasks.addTask(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.05f));
		tasks.addTask(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
		tasks.addTask(13, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(14, new EntityAILookIdle(this));
		familyInfo.marriageEntityClass = LOTREntityHobbit.class;
		familyInfo.marriageRing = LOTRMod.hobbitRing;
		familyInfo.marriageAlignmentRequired = 100.0f;
		familyInfo.marriageAchievement = LOTRAchievement.marryHobbit;
		familyInfo.potentialMaxChildren = 4;
		familyInfo.timeToMature = 48000;
		familyInfo.breedingDelay = 24000;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
		if (familyInfo.isMale()) {
			LOTRNames.changeHobbitSurnameForMarriage(this, (LOTREntityHobbit) spouse);
		} else if (spouse.familyInfo.isMale()) {
			LOTRNames.changeHobbitSurnameForMarriage((LOTREntityHobbit) spouse, this);
		}
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.HOBBIT.createQuest(this);
	}

	@Override
	public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
		familyInfo.setName(LOTRNames.getHobbitChildNameForParent(rand, familyInfo.isMale(), (LOTREntityHobbit) maleParent));
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.hobbitBone, 1);
		}
		dropHobbitItems(flag, i);
	}

	public void dropHobbitItems(boolean flag, int i) {
		if (rand.nextInt(8) == 0) {
			dropChestContents(LOTRChestContents.HOBBIT_HOLE_STUDY, 1, 1 + i);
		}
		if (rand.nextInt(4) == 0) {
			dropChestContents(LOTRChestContents.HOBBIT_HOLE_LARDER, 1, 2 + i);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenShire) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.HOBBIT;
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
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 1 + rand.nextInt(3);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.HOBBIT;
	}

	public LOTRFoods getHobbitDrinks() {
		return LOTRFoods.HOBBIT_DRINK;
	}

	public LOTRFoods getHobbitFoods() {
		return LOTRFoods.HOBBIT;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killHobbit;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isDrunkard()) {
			return "hobbit/drunkard/neutral";
		}
		if (isFriendlyAndAligned(entityplayer)) {
			return isChild() ? "hobbit/child/friendly" : "hobbit/hobbit/friendly";
		}
		return isChild() ? "hobbit/child/hostile" : "hobbit/hobbit/hostile";
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
		if (getClass() == familyInfo.marriageEntityClass && rand.nextInt(10) == 0) {
			familyInfo.setChild();
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
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getHobbitName(rand, familyInfo.isMale()));
	}

	@Override
	public boolean speakTo(EntityPlayer entityplayer) {
		boolean flag = super.speakTo(entityplayer);
		if (flag && isDrunkard() && entityplayer.isPotionActive(Potion.confusion.id)) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.speakToDrunkard);
		}
		return flag;
	}
}
