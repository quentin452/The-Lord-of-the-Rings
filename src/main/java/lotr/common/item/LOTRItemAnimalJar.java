package lotr.common.item;

import lotr.common.*;
import lotr.common.block.LOTRBlockAnimalJar;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTRItemAnimalJar extends LOTRItemBlockMetadata {
	public LOTRItemAnimalJar(Block block) {
		super(block);
		setMaxStackSize(1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
		itemstack = entityplayer.getCurrentEquippedItem();
		World world = entityplayer.worldObj;
		LOTRBlockAnimalJar jarBlock = (LOTRBlockAnimalJar) field_150939_a;
		if (jarBlock.canCapture(entity) && getEntityData(itemstack) == null) {
			NBTTagCompound nbt;
			if (!world.isRemote && entity.writeToNBTOptional(nbt = new NBTTagCompound())) {
				setEntityData(itemstack, nbt);
				entity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
				entity.setDead();
				if (entity instanceof LOTREntityButterfly) {
					LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.catchButterfly);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		Entity jarEntity;
		if (!world.isRemote && (jarEntity = getItemJarEntity(itemstack, world)) != null) {
			double x = entityplayer.posX;
			double y = entityplayer.boundingBox.minY + entityplayer.getEyeHeight();
			double z = entityplayer.posZ;
			Vec3 look = entityplayer.getLookVec();
			float length = 2.0f;
			jarEntity.setLocationAndAngles(x + look.xCoord * length, y + look.yCoord * length, z + look.zCoord * length, world.rand.nextFloat(), 0.0f);
			world.spawnEntityInWorld(jarEntity);
			jarEntity.playSound("random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
			setEntityData(itemstack, null);
		}
		return itemstack;
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
		if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof LOTRTileEntityAnimalJar) {
				LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar) tileentity;
				jar.setEntityData(getEntityData(itemstack));
			}
			return true;
		}
		return false;
	}

	public static NBTTagCompound getEntityData(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			NBTTagCompound nbt;
			if (itemstack.getTagCompound().hasKey("LOTRButterfly")) {
				nbt = itemstack.getTagCompound().getCompoundTag("LOTRButterfly");
				if (!nbt.hasNoTags()) {
					nbt.setString("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
					setEntityData(itemstack, (NBTTagCompound) nbt.copy());
				}
				itemstack.getTagCompound().removeTag("LOTRButterfly");
			}
			if (itemstack.getTagCompound().hasKey("JarEntity") && !(nbt = itemstack.getTagCompound().getCompoundTag("JarEntity")).hasNoTags()) {
				return nbt;
			}
		}
		return null;
	}

	public static Entity getItemJarEntity(ItemStack itemstack, World world) {
		NBTTagCompound nbt = getEntityData(itemstack);
		if (nbt != null) {
			return EntityList.createEntityFromNBT(nbt, world);
		}
		return null;
	}

	public static void setEntityData(ItemStack itemstack, NBTTagCompound nbt) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		if (nbt == null) {
			itemstack.getTagCompound().removeTag("JarEntity");
		} else {
			itemstack.getTagCompound().setTag("JarEntity", nbt);
		}
	}
}
