package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityStoneTroll extends Entity implements LOTRBannerProtectable {
	public float trollHealth = 40.0f;
	public boolean placedByPlayer;
	public int entityAge;

	public LOTREntityStoneTroll(World world) {
		super(world);
		setSize(1.6f, 3.2f);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && !isDead) {
			EntityPlayer entityplayer;
			if (placedByPlayer) {
				if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
					worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
					worldObj.setEntityState(this, (byte) 17);
					setDead();
					EntityPlayer entityplayer2 = (EntityPlayer) damagesource.getSourceOfDamage();
					if (!entityplayer2.capabilities.isCreativeMode) {
						dropAsStatue();
					}
					return true;
				}
				return false;
			}
			boolean drops = true;
			boolean dropStatue = false;
			if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
				if (entityplayer.capabilities.isCreativeMode) {
					drops = false;
					f = trollHealth;
				} else {
					drops = true;
					ItemStack itemstack = entityplayer.inventory.getCurrentItem();
					if (itemstack != null && itemstack.getItem() instanceof ItemPickaxe) {
						dropStatue = true;
						f = 1.0f + (float) entityplayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					} else {
						dropStatue = false;
						f = 1.0f;
					}
					if (itemstack != null) {
						itemstack.damageItem(1, entityplayer);
						if (itemstack.stackSize <= 0) {
							entityplayer.destroyCurrentEquippedItem();
						}
					}
				}
			}
			trollHealth -= f;
			if (trollHealth <= 0.0f) {
				worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
				worldObj.setEntityState(this, (byte) 17);
				if (drops) {
					if (dropStatue) {
						if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
							entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
							LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getTrollStatue);
						}
						dropAsStatue();
					} else {
						int stone = 6 + rand.nextInt(7);
						for (int l = 0; l < stone; ++l) {
							dropItem(Item.getItemFromBlock(Blocks.cobblestone), 1);
						}
					}
				}
				setDead();
			} else {
				worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.5f);
				worldObj.setEntityState(this, (byte) 16);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public void dropAsStatue() {
		entityDropItem(getStatueItem(), 0.0f);
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(16, (byte) 0);
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getStatueItem();
	}

	public ItemStack getStatueItem() {
		ItemStack itemstack = new ItemStack(LOTRMod.trollStatue);
		itemstack.setItemDamage(getTrollOutfit());
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setBoolean("TwoHeads", hasTwoHeads());
		return itemstack;
	}

	public int getTrollOutfit() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	public void setTrollOutfit(int i) {
		dataWatcher.updateObject(16, (byte) i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int l = 0; l < 16; ++l) {
				worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		} else if (b == 17) {
			for (int l = 0; l < 64; ++l) {
				worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	public boolean hasTwoHeads() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033;
		func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98f;
		if (onGround) {
			f = 0.58800006f;
			Block i = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
			if (i.getMaterial() != Material.air) {
				f = i.slipperiness * 0.98f;
			}
		}
		motionX *= f;
		motionY *= 0.98;
		motionZ *= f;
		if (onGround) {
			motionY *= -0.5;
		}
		if (!worldObj.isRemote && !placedByPlayer) {
			++entityAge;
			EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, -1.0);
			if (entityplayer != null) {
				double d = entityplayer.posX - posX;
				double d1 = entityplayer.posY - posY;
				double d2 = entityplayer.posZ - posZ;
				double distanceSq = d * d + d1 * d1 + d2 * d2;
				if (distanceSq > 16384.0) {
					setDead();
				}
				if (entityAge > 600 && rand.nextInt(800) == 0 && distanceSq > 1024.0) {
					setDead();
				} else if (distanceSq < 1024.0) {
					entityAge = 0;
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		trollHealth = nbt.getFloat("TrollHealth");
		setTrollOutfit(nbt.getByte("TrollOutfit"));
		placedByPlayer = nbt.getBoolean("PlacedByPlayer");
		setHasTwoHeads(nbt.getBoolean("TwoHeads"));
	}

	public void setHasTwoHeads(boolean flag) {
		dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("TrollHealth", trollHealth);
		nbt.setByte("TrollOutfit", (byte) getTrollOutfit());
		nbt.setBoolean("PlacedByPlayer", placedByPlayer);
		nbt.setBoolean("TwoHeads", hasTwoHeads());
	}
}
