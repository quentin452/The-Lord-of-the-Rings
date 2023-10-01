package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityTroll extends LOTREntityNPC {
	public int sneeze;
	public int sniffTime;
	public boolean trollImmuneToSun;

	public LOTREntityTroll(World world) {
		super(world);
		float f = getTrollScale();
		setSize(1.6f * f, 3.2f * f);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIRestrictSun(this));
		tasks.addTask(2, new LOTREntityAIHiredRemainStill(this));
		tasks.addTask(3, new LOTREntityAITrollFleeSun(this, 2.5));
		tasks.addTask(4, getTrollAttackAI());
		tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(6, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
		tasks.addTask(9, new EntityAILookIdle(this));
		addTargetTasks(true, LOTREntityAINearestAttackableTargetTroll.class);
		spawnsInDarkness = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(5.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			float attackDamage = (float) getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
			float knockbackModifier = 0.25f * attackDamage;
			entity.addVelocity(-MathHelper.sin(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
			return true;
		}
		return false;
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	public boolean canTrollBeTickled(EntityPlayer entityplayer) {
		return canNPCTalk() && isFriendly(entityplayer) && LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f && getAttackTarget() == null && getTrollBurnTime() == -1;
	}

	@Override
	public boolean conquestSpawnIgnoresDarkness() {
		return trollImmuneToSun;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int bones = 2 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < bones; ++l) {
			dropItem(LOTRMod.trollBone, 1);
		}
		dropTrollItems(flag, i);
	}

	public void dropTrollItems(boolean flag, int i) {
		if (rand.nextInt(3) == 0) {
			int j = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
			for (int k = 0; k < j; ++k) {
				dropItem(Items.slime_ball, 1);
			}
		}
		int animalDrops = 1 + rand.nextInt(3) + rand.nextInt(i + 1);
		for (int l = 0; l < animalDrops; ++l) {
			int drop = rand.nextInt(10);
			switch (drop) {
				case 0: {
					entityDropItem(new ItemStack(Items.leather, 1 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 1: {
					entityDropItem(new ItemStack(Items.beef, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 2: {
					entityDropItem(new ItemStack(Items.chicken, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 3: {
					entityDropItem(new ItemStack(Items.feather, 1 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 4: {
					entityDropItem(new ItemStack(Items.porkchop, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 5: {
					entityDropItem(new ItemStack(Blocks.wool, 1 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 6: {
					entityDropItem(new ItemStack(Items.rotten_flesh, 1 + rand.nextInt(3)), 0.0f);
					continue;
				}
				case 7: {
					entityDropItem(new ItemStack(LOTRMod.rabbitRaw, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 8: {
					entityDropItem(new ItemStack(LOTRMod.muttonRaw, 1 + rand.nextInt(2)), 0.0f);
					continue;
				}
				case 9: {
					entityDropItem(new ItemStack(LOTRMod.deerRaw, 1 + rand.nextInt(2)), 0.0f);
				}
			}
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) rand.nextInt(3));
		dataWatcher.addObject(17, (short) -1);
		dataWatcher.addObject(18, (byte) 0);
		dataWatcher.addObject(19, (byte) 0);
	}

	@Override
	public void func_145780_a(int i, int j, int k, Block block) {
		playSound("lotr:troll.step", 0.75f, getSoundPitch());
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public String getDeathSound() {
		return "lotr:troll.say";
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 4 + rand.nextInt(5);
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.ANGMAR;
	}

	@Override
	public String getHurtSound() {
		return "lotr:troll.say";
	}

	@Override
	public LOTRAchievement getKillAchievement() {
		return LOTRAchievement.killTroll;
	}

	@Override
	public String getLivingSound() {
		return "lotr:troll.say";
	}

	@Override
	public String getNPCName() {
		if (hasTrollName()) {
			return familyInfo.getName();
		}
		return super.getNPCName();
	}

	public int getSneezingTime() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public void setSneezingTime(int i) {
		dataWatcher.updateObject(18, (byte) i);
	}

	@Override
	public float getSoundVolume() {
		return 1.5f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (getTrollBurnTime() >= 0) {
			return null;
		}
		if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= 100.0f && isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "troll/hired";
			}
			return "troll/friendly";
		}
		return "troll/hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 8;
	}

	public EntityAIBase getTrollAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, false);
	}

	public int getTrollBurnTime() {
		return dataWatcher.getWatchableObjectShort(17);
	}

	public void setTrollBurnTime(int i) {
		dataWatcher.updateObject(17, (short) i);
	}

	public int getTrollOutfit() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	public void setTrollOutfit(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	public float getTrollScale() {
		return 1.0f;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			spawnExplosionParticle();
		} else if (b == 16) {
			sniffTime = 16;
		} else {
			super.handleHealthUpdate(b);
		}
	}

	public boolean hasTrollName() {
		return true;
	}

	public boolean hasTwoHeads() {
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack itemstack;
		if (!worldObj.isRemote && canTrollBeTickled(entityplayer) && (itemstack = entityplayer.inventory.getCurrentItem()) != null && LOTRMod.isOreNameEqual(itemstack, "feather") && getSneezingTime() == 0) {
			if (rand.nextBoolean()) {
				++sneeze;
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
			if (itemstack.stackSize <= 0) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
			npcTalkTick = getNPCTalkInterval() / 2;
			if (sneeze >= 3) {
				setSneezingTime(16);
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.makeTrollSneeze);
			} else {
				LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "troll/tickle", entityplayer));
				worldObj.playSoundAtEntity(this, "lotr:troll.sniff", getSoundVolume(), getSoundPitch());
				worldObj.setEntityState(this, (byte) 16);
			}
		}
		return super.interact(entityplayer);
	}

	@Override
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 2.0;
		motionY /= 2.0;
		motionZ /= 2.0;
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && getTrollBurnTime() >= 0) {
			LOTRLevelData.getData((EntityPlayer) damagesource.getEntity()).addAchievement(LOTRAchievement.killTrollFleeingSun);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getTrollBurnTime() >= 0 && isEntityAlive()) {
			if (worldObj.isRemote) {
				worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			} else {
				BiomeGenBase biome = worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
				if (trollImmuneToSun || biome instanceof LOTRBiome && ((LOTRBiome) biome).canSpawnHostilesInDay() || !worldObj.isDaytime() || !worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), (int) boundingBox.minY, MathHelper.floor_double(posZ))) {
					setTrollBurnTime(-1);
				} else {
					setTrollBurnTime(getTrollBurnTime() - 1);
					if (getTrollBurnTime() == 0) {
						onTrollDeathBySun();
						if (hiredNPCInfo.isActive && hiredNPCInfo.getHiringPlayer() != null) {
							hiredNPCInfo.getHiringPlayer().addChatMessage(new ChatComponentTranslation("lotr.hiredNPC.trollStone", getCommandSenderName()));
						}
					}
				}
			}
		}
		if (sniffTime > 0) {
			--sniffTime;
		}
		if (!worldObj.isRemote && getSneezingTime() > 0) {
			setSneezingTime(getSneezingTime() - 1);
			if (getSneezingTime() == 8) {
				worldObj.playSoundAtEntity(this, "lotr:troll.sneeze", getSoundVolume() * 1.5f, getSoundPitch());
			}
			if (getSneezingTime() == 4) {
				int slimes = 2 + rand.nextInt(3);
				for (int i = 0; i < slimes; ++i) {
					EntityItem entityitem = new EntityItem(worldObj, posX, posY + getEyeHeight(), posZ, new ItemStack(Items.slime_ball));
					entityitem.delayBeforeCanPickup = 40;
					float f = 1.0f;
					entityitem.motionX = -MathHelper.sin(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * f;
					entityitem.motionZ = MathHelper.cos(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * f;
					entityitem.motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
					f = 0.02f;
					float f1 = rand.nextFloat() * 3.1415927f * 2.0f;
					entityitem.motionX += Math.cos(f1) * (f *= rand.nextFloat());
					entityitem.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1f;
					entityitem.motionZ += Math.sin(f1) * f;
					worldObj.spawnEntityInWorld(entityitem);
				}
			}
			if (getSneezingTime() == 0) {
				sneeze = 0;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(10) == 0) {
			setHasTwoHeads(true);
			double maxHealth = getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth * 1.5);
			setHealth(getMaxHealth());
			double attack = getEntityAttribute(npcAttackDamage).getBaseValue();
			getEntityAttribute(npcAttackDamage).setBaseValue(attack + 3.0);
			double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed * 1.4);
		}
		return data;
	}

	public void onTrollDeathBySun() {
		worldObj.playSoundAtEntity(this, "lotr:troll.transform", getSoundVolume(), getSoundPitch());
		worldObj.setEntityState(this, (byte) 15);
		setDead();
		LOTREntityStoneTroll stoneTroll = new LOTREntityStoneTroll(worldObj);
		stoneTroll.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0f);
		stoneTroll.setTrollOutfit(getTrollOutfit());
		stoneTroll.setHasTwoHeads(hasTwoHeads());
		worldObj.spawnEntityInWorld(stoneTroll);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setTrollOutfit(nbt.getByte("TrollOutfit"));
		setTrollBurnTime(nbt.getInteger("TrollBurnTime"));
		sneeze = nbt.getInteger("Sneeze");
		setSneezingTime(nbt.getInteger("SneezeTime"));
		trollImmuneToSun = nbt.getBoolean("ImmuneToSun");
		setHasTwoHeads(nbt.getBoolean("TwoHeads"));
		if (nbt.hasKey("TrollName")) {
			familyInfo.setName(nbt.getString("TrollName"));
		}
	}

	public void setHasTwoHeads(boolean flag) {
		dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(LOTRNames.getTrollName(rand));
	}

	public boolean shouldRenderHeadHurt() {
		return hurtTime > 0 || getSneezingTime() > 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("TrollOutfit", (byte) getTrollOutfit());
		nbt.setInteger("TrollBurnTime", getTrollBurnTime());
		nbt.setInteger("Sneeze", sneeze);
		nbt.setInteger("SneezeTime", getSneezingTime());
		nbt.setBoolean("ImmuneToSun", trollImmuneToSun);
		nbt.setBoolean("TwoHeads", hasTwoHeads());
	}
}
