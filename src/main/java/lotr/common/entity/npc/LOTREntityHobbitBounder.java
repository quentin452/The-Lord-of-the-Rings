package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitBounder extends LOTREntityHobbit {
	public EntityAIBase rangedAttackAI = createHobbitRangedAttackAI();
	public EntityAIBase meleeAttackAI = createHobbitMeleeAttackAI();

	public LOTREntityHobbitBounder(World world) {
		super(world);
		tasks.taskEntries.clear();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(8, new EntityAILookIdle(this));
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new LOTREntityAIHobbitTargetRuffian(this, LOTREntityBreeRuffian.class, 0, true));
		spawnRidingHorse = rand.nextInt(3) == 0;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		EntityArrow template = new EntityArrow(worldObj, this, target, 1.0f, 0.5f);
		LOTREntityPebble pebble = new LOTREntityPebble(worldObj, this).setSling();
		pebble.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
		pebble.motionX = template.motionX;
		pebble.motionY = template.motionY;
		pebble.motionZ = template.motionZ;
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(pebble);
	}

	public EntityAIBase createHobbitMeleeAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.5, false);
	}

	public EntityAIBase createHobbitRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.5, 20, 40, 12.0f);
	}

	@Override
	public LOTRNPCMount createMountToRide() {
		return new LOTREntityShirePony(worldObj);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int dropChance = 10 - i * 2;
		if (dropChance < 1) {
			dropChance = 1;
		}
		if (rand.nextInt(dropChance) == 0) {
			dropItem(LOTRMod.pebble, 1 + rand.nextInt(3) + rand.nextInt(i + 1));
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 2 + rand.nextInt(3);
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendlyAndAligned(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "hobbit/bounder/hired";
			}
			return "hobbit/bounder/friendly";
		}
		return "hobbit/bounder/hostile";
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (mode == LOTREntityNPC.AttackMode.IDLE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		}
		if (mode == LOTREntityNPC.AttackMode.MELEE) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, meleeAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
		if (mode == LOTREntityNPC.AttackMode.RANGED) {
			tasks.removeTask(meleeAttackAI);
			tasks.removeTask(rangedAttackAI);
			tasks.addTask(2, rangedAttackAI);
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(3);
		if (i == 0 || i == 1) {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
		}
		npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.sling));
		npcItemsInv.setIdleItem(null);
		ItemStack hat = new ItemStack(LOTRMod.leatherHat);
		LOTRItemLeatherHat.setHatColor(hat, 6834742);
		LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
		setCurrentItemOrArmor(4, hat);
		return data;
	}
}
