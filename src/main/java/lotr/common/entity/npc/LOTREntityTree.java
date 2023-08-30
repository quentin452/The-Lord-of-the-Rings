package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeHooks;

public abstract class LOTREntityTree extends LOTREntityNPC {
	public static Block[] WOOD_BLOCKS = {Blocks.log, LOTRMod.wood2, Blocks.log};
	public static Block[] LEAF_BLOCKS = {Blocks.leaves, LOTRMod.leaves2, Blocks.leaves};
	public static Block[] SAPLING_BLOCKS = {Blocks.sapling, LOTRMod.sapling2, Blocks.sapling};
	public static int[] WOOD_META = {0, 1, 2};
	public static int[] LEAF_META = {0, 1, 2};
	public static int[] SAPLING_META = {0, 1, 2};
	public static String[] TYPES = {"oak", "beech", "birch"};

	protected LOTREntityTree(World world) {
		super(world);
	}

	@Override
	public void addPotionEffect(PotionEffect effect) {
		if (effect.getPotionID() == Potion.poison.id) {
			return;
		}
		super.addPotionEffect(effect);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (doTreeDamageCalculation() && !isTreeEffectiveDamage(damagesource)) {
			f /= 3.0f;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public boolean canDropRares() {
		return false;
	}

	@Override
	public boolean canReEquipHired(int slot, ItemStack itemstack) {
		return false;
	}

	public boolean doTreeDamageCalculation() {
		return true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int logs = MathHelper.getRandomIntegerInRange(rand, 3, 10) + rand.nextInt(4 * (i + 1));
		for (int l = 0; l < logs; ++l) {
			int treeType = getTreeType();
			entityDropItem(new ItemStack(WOOD_BLOCKS[treeType], 1, WOOD_META[treeType]), 0.0f);
		}
		int sticks = MathHelper.getRandomIntegerInRange(rand, 6, 16) + rand.nextInt(5 * (i + 1));
		for (int l = 0; l < sticks; ++l) {
			dropItem(Items.stick, 1);
		}
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
		if (rand.nextInt(9) == 0) {
			setTreeType(2);
		} else if (rand.nextInt(3) == 0) {
			setTreeType(1);
		} else {
			setTreeType(0);
		}
	}

	@Override
	public float getBlockPathWeight(int i, int j, int k) {
		float f = 0.0f;
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (isTreeHomeBiome(biome)) {
			f += 20.0f;
		}
		return f;
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
			Block block = worldObj.getBlock(i, j - 1, k);
			worldObj.getBlockMetadata(i, j - 1, k);
			return j > 62 && (block == Blocks.grass || block == Blocks.dirt);
		}
		return false;
	}

	public int getTreeType() {
		byte i = dataWatcher.getWatchableObjectByte(16);
		if (i < 0 || i >= TYPES.length) {
			i = 0;
		}
		return i;
	}

	public boolean isTreeEffectiveDamage(DamageSource damagesource) {
		ItemStack itemstack;
		if (damagesource.isFireDamage()) {
			return true;
		}
		return damagesource.getEntity() instanceof EntityLivingBase && damagesource.getSourceOfDamage() == damagesource.getEntity() && (itemstack = ((EntityLivingBase) damagesource.getEntity()).getHeldItem()) != null && ForgeHooks.canToolHarvestBlock(Blocks.log, 0, itemstack);
	}

	public boolean isTreeHomeBiome(BiomeGenBase biome) {
		return biome instanceof LOTRBiomeGenFangorn;
	}

	@Override
	public void knockBack(Entity entity, float f, double d, double d1) {
		super.knockBack(entity, f, d, d1);
		motionX /= 2.0;
		motionY /= 2.0;
		motionZ /= 2.0;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setTreeType(nbt.getByte("EntType"));
	}

	@Override
	public void setAttackTarget(EntityLivingBase target, boolean speak) {
		if (target instanceof LOTREntityTree) {
			return;
		}
		super.setAttackTarget(target, speak);
	}

	public void setTreeType(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("EntType", (byte) getTreeType());
	}
}
