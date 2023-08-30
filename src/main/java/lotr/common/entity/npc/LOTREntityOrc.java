package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTREntityOrc extends LOTREntityNPC {
	public boolean isWeakOrc = true;
	public int orcSkirmishTick;
	public EntityLivingBase currentRevengeTarget;

	protected LOTREntityOrc(World world) {
		super(world);
		setSize(0.5f, 1.55f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(2, new EntityAIAvoidEntity(this, LOTREntityOrcBomb.class, 12.0f, 1.5, 2.0));
		tasks.addTask(3, new LOTREntityAIOrcAvoidGoodPlayer(this, 8.0f, 1.5));
		tasks.addTask(4, createOrcAttackAI());
		tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(6, new EntityAIOpenDoor(this, true));
		tasks.addTask(7, new EntityAIWander(this, 1.0));
		tasks.addTask(8, new LOTREntityAIEat(this, LOTRFoods.ORC, 6000));
		tasks.addTask(8, new LOTREntityAIDrink(this, LOTRFoods.ORC_DRINK, 6000));
		tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.05f));
		tasks.addTask(9, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(11, new EntityAILookIdle(this));
		int target = addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
		targetTasks.addTask(target + 1, new LOTREntityAIOrcSkirmish(this, true));
		if (!isOrcBombardier()) {
			targetTasks.addTask(target + 2, new LOTREntityAINearestAttackableTargetOrc(this, LOTREntityRabbit.class, 2000, false));
		}
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
	}

	public boolean canOrcSkirmish() {
		return !questInfo.anyActiveQuestPlayers();
	}

	public abstract EntityAIBase createOrcAttackAI();

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int flesh = rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < flesh; ++l) {
			dropItem(Items.rotten_flesh, 1);
		}
		int bones = rand.nextInt(2) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.orcBone, 1);
		}
		if (rand.nextInt(10) == 0) {
			int breads = 1 + rand.nextInt(2) + rand.nextInt(i + 1);
			for (int l = 0; l < breads; ++l) {
				dropItem(LOTRMod.maggotyBread, 1);
			}
		}
		if (flag) {
			int rareDropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(rareDropChance, 1)) == 0) {
				int dropType = rand.nextInt(2);
				if (dropType == 0) {
					ItemStack orcDrink = new ItemStack(LOTRMod.mugOrcDraught);
					orcDrink.setItemDamage(1 + rand.nextInt(3));
					LOTRItemMug.setVessel(orcDrink, LOTRFoods.ORC_DRINK.getRandomVessel(rand), true);
					entityDropItem(orcDrink, 0.0f);
				} else {
					int ingots = 1 + rand.nextInt(2) + rand.nextInt(i + 1);
					for (int l = 0; l < ingots; ++l) {
						if (this instanceof LOTREntityUrukHai || this instanceof LOTREntityGundabadUruk) {
							dropItem(LOTRMod.urukSteel, 1);
							continue;
						}
						if (this instanceof LOTREntityBlackUruk) {
							dropItem(LOTRMod.blackUrukSteel, 1);
							continue;
						}
						dropItem(LOTRMod.orcSteel, 1);
					}
				}
			}
		}
		dropOrcItems(flag, i);
	}

	public void dropOrcItems(boolean flag, int i) {
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) -1);
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
			if (biome instanceof LOTRBiome && ((LOTRBiome) biome).isDwarvenBiome(worldObj)) {
				return worldObj.getBlock(i, j - 1, k) == biome.topBlock;
			}
			return true;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "lotr:orc.death";
	}

	@Override
	public ItemStack getHeldItemLeft() {
		if (isOrcBombardier() && npcItemsInv.getBomb() != null) {
			return npcItemsInv.getBomb();
		}
		return super.getHeldItemLeft();
	}

	@Override
	public String getHurtSound() {
		return "lotr:orc.hurt";
	}

	@Override
	public String getLivingSound() {
		return "lotr:orc.say";
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	public String getOrcSkirmishSpeech() {
		return "";
	}

	@Override
	public float getPoisonedArrowChance() {
		return 0.06666667f;
	}

	@Override
	public int getTotalArmorValue() {
		if (isWeakOrc) {
			return MathHelper.floor_double(super.getTotalArmorValue() * 0.75);
		}
		return super.getTotalArmorValue();
	}

	public boolean isOrcBombardier() {
		return false;
	}

	public boolean isOrcSkirmishing() {
		return orcSkirmishTick > 0;
	}

	@Override
	public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
		if (npcItemsInv.getBomb() != null) {
			setCurrentItemOrArmor(0, npcItemsInv.getBombingItem());
		} else if (mode == LOTREntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && getAttackTarget() == null) {
			currentRevengeTarget = null;
		}
		if (!worldObj.isRemote && isWeakOrc) {
			boolean flag;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
			flag = worldObj.isDaytime() && worldObj.canBlockSeeTheSky(i, j, k);
			if (biome instanceof LOTRBiome && ((LOTRBiome) biome).canSpawnHostilesInDay()) {
				flag = false;
			}
			if (flag && ticksExisted % 20 == 0) {
				addPotionEffect(new PotionEffect(Potion.resistance.id, 200, -1));
				addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200));
			}
		}
		if (!worldObj.isRemote && isOrcSkirmishing()) {
			if (!LOTRConfig.enableOrcSkirmish) {
				orcSkirmishTick = 0;
			} else if (!(getAttackTarget() instanceof LOTREntityOrc)) {
				--orcSkirmishTick;
			}
		}
		if (isOrcBombardier()) {
			if (worldObj.isRemote) {
				byte meta = dataWatcher.getWatchableObjectByte(17);
				if (meta == -1) {
					npcItemsInv.setBomb(null);
				} else {
					npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, meta));
				}
			} else {
				ItemStack bomb = npcItemsInv.getBomb();
				int meta = -1;
				if (bomb != null && Block.getBlockFromItem(bomb.getItem()) instanceof LOTRBlockOrcBomb) {
					meta = bomb.getItemDamage();
				}
				dataWatcher.updateObject(17, (byte) meta);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		return super.onSpawnWithEgg(data);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("OrcName")) {
			familyInfo.setName(nbt.getString("OrcName"));
		}
		orcSkirmishTick = nbt.getInteger("OrcSkirmish");
	}

	public void setOrcSkirmishing() {
		int prevSkirmishTick = orcSkirmishTick;
		orcSkirmishTick = 160;
		if (!worldObj.isRemote && prevSkirmishTick == 0) {
			List nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(24.0, 24.0, 24.0));
			for (Object nearbyPlayer : nearbyPlayers) {
				EntityPlayer entityplayer = (EntityPlayer) nearbyPlayer;
				LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, getOrcSkirmishSpeech(), entityplayer));
			}
		}
	}

	@Override
	public void setRevengeTarget(EntityLivingBase entity) {
		super.setRevengeTarget(entity);
		if (entity != null) {
			currentRevengeTarget = entity;
		}
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getOrcName(rand));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("OrcSkirmish", orcSkirmishTick);
	}
}
