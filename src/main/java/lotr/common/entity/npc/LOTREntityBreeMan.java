package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenBreeland;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityBreeMan extends LOTREntityMan implements IPickpocketable {
	public static String CARROT_EATER_NAME = "Peter Jackson";
	public static ItemStack[] weapons = {new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.axeBronze), new ItemStack(Items.stone_axe)};

	public LOTREntityBreeMan(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		int p = addBreeAttackAI(2);
		addBreeHiringAI(p + 1);
		tasks.addTask(p + 2, new EntityAIOpenDoor(this, true));
		addBreeAvoidAI(p + 3);
		tasks.addTask(p + 4, new EntityAIWander(this, 1.0));
		tasks.addTask(p + 5, new LOTREntityAIBreeEat(this, LOTRFoods.BREE, 8000));
		tasks.addTask(p + 5, new LOTREntityAIDrink(this, LOTRFoods.BREE_DRINK, 8000));
		tasks.addTask(p + 5, new LOTREntityAIHobbitSmoke(this, 12000));
		tasks.addTask(p + 6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(p + 6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(p + 7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(p + 8, new EntityAILookIdle(this));
		addTargetTasks(false);
	}

	public int addBreeAttackAI(int prio) {
		tasks.addTask(prio, new LOTREntityAIAttackOnCollide(this, 1.3, false));
		return prio;
	}

	public void addBreeAvoidAI(int prio) {
		tasks.addTask(prio, new EntityAIAvoidEntity(this, LOTREntityRuffianBrute.class, 8.0f, 1.0, 1.5));
	}

	public void addBreeHiringAI(int prio) {
		tasks.addTask(prio, new LOTREntityAIFollowHiringPlayer(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	@Override
	public boolean canPickpocket() {
		return true;
	}

	@Override
	public LOTRMiniQuest createMiniQuest() {
		return LOTRMiniQuestFactory.BREE.createQuest(this);
	}

	@Override
	public ItemStack createPickpocketItem() {
		return LOTRChestContents.BREE_PICKPOCKET.getOneItem(rand, true);
	}

	public void dropBreeItems(boolean flag, int i) {
		if (rand.nextInt(6) == 0) {
			dropChestContents(LOTRChestContents.BREE_HOUSE, 1, 2 + i);
		}
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(Items.bone, 1);
		}
		dropBreeItems(flag, i);
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof LOTRBiomeGenBreeland) {
			f += 20.0f;
		}
		return f;
	}

	@Override
	public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
		return LOTRMiniQuestFactory.BREE;
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
	public LOTRFaction getFaction() {
		return LOTRFaction.BREE;
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killBreelander;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			return "bree/man/friendly";
		}
		return "bree/man/hostile";
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
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(null);
		if (familyInfo.isMale() && rand.nextInt(2000) == 0) {
			familyInfo.setName(CARROT_EATER_NAME);
			npcItemsInv.setIdleItem(new ItemStack(Items.carrot));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getBreeName(rand, familyInfo.isMale()));
	}
}
