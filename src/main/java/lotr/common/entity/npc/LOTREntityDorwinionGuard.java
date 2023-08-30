package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.*;
import lotr.common.world.*;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityDorwinionGuard extends LOTREntityDorwinionMan {
	public static ItemStack[] guardWeapons = {new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron)};
	public static int MAX_GRAPE_ALERT = 3;
	public int grapeAlert;

	public LOTREntityDorwinionGuard(World world) {
		super(world);
		addTargetTasks(true);
		npcShield = LOTRShields.ALIGNMENT_DORWINION;
	}

	@Override
	public EntityAIBase createDorwinionAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.4, true);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "dorwinion/guard/hired";
			}
			return "dorwinion/guard/friendly";
		}
		return "dorwinion/guard/hostile";
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			if (grapeAlert >= 3) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.stealDorwinionGrapes);
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && grapeAlert > 0 && ticksExisted % 600 == 0) {
			--grapeAlert;
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(guardWeapons.length);
		npcItemsInv.setMeleeWeapon(guardWeapons[i].copy());
		if (rand.nextInt(8) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinion));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinion));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinion));
		if (rand.nextInt(4) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDorwinion));
		}
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		grapeAlert = nbt.getInteger("GrapeAlert");
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("GrapeAlert", grapeAlert);
	}

	public static void defendGrapevines(EntityPlayer entityplayer, World world, int i, int j, int k) {
		if (!entityplayer.capabilities.isCreativeMode) {
			LOTRBiomeVariant variant;
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			variant = world.provider instanceof LOTRWorldProvider ? ((LOTRWorldChunkManager) world.provider.worldChunkMgr).getBiomeVariantAt(i, k) : null;
			if (biome instanceof LOTRBiomeGenDorwinion && variant == LOTRBiomeVariant.VINEYARD) {
				LOTREntityDorwinionGuard guard;
				float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DORWINION);
				boolean evil = alignment < 0.0f;
				float limit = 2000.0f;
				float chance = (limit - alignment) / limit;
				chance = Math.max(chance, 0.0f);
				chance = Math.min(chance, 1.0f);
				chance *= chance;
				if ((evil || world.rand.nextFloat() < chance) && world.rand.nextInt(4) == 0) {
					int nearbyGuards = 0;
					int spawnRange = 8;
					List guardList = world.getEntitiesWithinAABB(LOTREntityDorwinionGuard.class, entityplayer.boundingBox.expand(spawnRange, spawnRange, spawnRange));
					for (Object obj : guardList) {
						guard = (LOTREntityDorwinionGuard) obj;
						if (guard.hiredNPCInfo.isActive) {
							continue;
						}
						++nearbyGuards;
					}
					if (nearbyGuards < 8) {
						int guardSpawns = 1 + world.rand.nextInt(6);
						block1:
						for (int l = 0; l < guardSpawns; ++l) {
							guard = new LOTREntityDorwinionGuard(world);
							if (world.rand.nextBoolean()) {
								guard = new LOTREntityDorwinionCrossbower(world);
							}
							int attempts = 16;
							for (int a = 0; a < attempts; ++a) {
								int i1 = i + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
								int j1 = j + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange / 2, spawnRange / 2);
								int k1 = k + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
								Block belowBlock = world.getBlock(i1, j1 - 1, k1);
								world.getBlockMetadata(i1, j1 - 1, k1);
								boolean belowSolid = belowBlock.isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP);
								if (!belowSolid || world.getBlock(i1, j1, k1).isNormalCube() || world.getBlock(i1, j1 + 1, k1).isNormalCube()) {
									continue;
								}
								guard.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
								guard.liftSpawnRestrictions = true;
								if (!guard.getCanSpawnHere()) {
									continue;
								}
								guard.liftSpawnRestrictions = false;
								world.spawnEntityInWorld(guard);
								guard.spawnRidingHorse = false;
								guard.onSpawnWithEgg(null);
								continue block1;
							}
						}
					}
				}
				int range = 16;
				List guardList = world.getEntitiesWithinAABB(LOTREntityDorwinionGuard.class, entityplayer.boundingBox.expand(range, range, range));
				boolean anyAlert = false;
				for (Object obj : guardList) {
					guard = (LOTREntityDorwinionGuard) obj;
					if (guard.hiredNPCInfo.isActive) {
						continue;
					}
					if (evil) {
						guard.setAttackTarget(entityplayer);
						guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
						guard.grapeAlert = 3;
						anyAlert = true;
						continue;
					}
					if (world.rand.nextFloat() >= chance) {
						continue;
					}
					++guard.grapeAlert;
					if (guard.grapeAlert >= 3) {
						guard.setAttackTarget(entityplayer);
						guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
					} else {
						guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeWarn");
					}
					anyAlert = true;
				}
				if (anyAlert && alignment >= 0.0f) {
					LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.VINEYARD_STEAL_PENALTY, LOTRFaction.DORWINION, i + 0.5, j + 0.5, k + 0.5);
				}
			}
		}
	}
}
