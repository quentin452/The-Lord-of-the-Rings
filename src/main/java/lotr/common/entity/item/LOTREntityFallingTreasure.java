package lotr.common.entity.item;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityFallingTreasure extends Entity implements IEntityAdditionalSpawnData {
	public Block theBlock;
	public int theBlockMeta;
	public int ticksFalling;

	public LOTREntityFallingTreasure(World world) {
		super(world);
	}

	public LOTREntityFallingTreasure(World world, double d, double d1, double d2, Block block) {
		this(world, d, d1, d2, block, 0);
	}

	public LOTREntityFallingTreasure(World world, double d, double d1, double d2, Block block, int meta) {
		super(world);
		blockMetaConstructor(d, d1, d2, block, meta);
	}

	public void blockMetaConstructor(double d, double d1, double d2, Block block, int meta) {
		theBlock = block;
		theBlockMeta = meta;
		preventEntitySpawning = true;
		setSize(0.98f, 0.98f);
		yOffset = height / 2.0f;
		setPosition(d, d1, d2);
		motionX = 0.0;
		motionY = 0.0;
		motionZ = 0.0;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getShadowSize() {
		return 0.0f;
	}

	@Override
	public void onUpdate() {
		if (theBlock.getMaterial() == Material.air) {
			setDead();
		} else {
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			++ticksFalling;
			motionY -= 0.04;
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.98;
			motionY *= 0.98;
			motionZ *= 0.98;
			if (!worldObj.isRemote) {
				int i = MathHelper.floor_double(posX);
				int j = MathHelper.floor_double(posY);
				int k = MathHelper.floor_double(posZ);
				Block block = worldObj.getBlock(i, j, k);
				int meta = worldObj.getBlockMetadata(i, j, k);
				if (ticksFalling == 1) {
					if (block != theBlock) {
						setDead();
						return;
					}
					worldObj.setBlockToAir(i, j, k);
				}
				if (onGround) {
					motionX *= 0.7;
					motionZ *= 0.7;
					motionY *= -0.5;
					if (block != Blocks.piston_extension) {
						setDead();
						boolean placedTreasure = false;
						if (block == theBlock && meta < 7) {
							while (theBlockMeta >= 0 && meta < 7) {
								--theBlockMeta;
								++meta;
							}
							worldObj.setBlockMetadataWithNotify(i, j, k, meta, 3);
							placedTreasure = true;
							++j;
						}
						if (theBlockMeta >= 0) {
							if (worldObj.canPlaceEntityOnSide(theBlock, i, j, k, true, 1, null, null) && worldObj.setBlock(i, j, k, theBlock, theBlockMeta, 3)) {
								placedTreasure = true;
							} else {
								entityDropItem(new ItemStack(theBlock, theBlock.quantityDropped(theBlockMeta, 0, rand), theBlock.damageDropped(theBlockMeta)), 0.0f);
							}
						}
						if (placedTreasure) {
							Block.SoundType stepSound = theBlock.stepSound;
							worldObj.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
						}
					}
				} else if (ticksFalling > 100 && !worldObj.isRemote && (j < 1 || j > 256 || ticksFalling > 600)) {
					entityDropItem(new ItemStack(theBlock, theBlock.quantityDropped(theBlockMeta, 0, rand), theBlock.damageDropped(theBlockMeta)), 0.0f);
					setDead();
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		theBlock = Block.getBlockById(nbt.getInteger("TileID"));
		theBlockMeta = nbt.getByte("Data") & 0xFF;
		ticksFalling = nbt.getByte("Time") & 0xFF;
		if (theBlock.getMaterial() == Material.air) {
			theBlock = Blocks.sand;
		}
	}

	@Override
	public void readSpawnData(ByteBuf data) {
		double x = data.readDouble();
		double y = data.readDouble();
		double z = data.readDouble();
		Block block = Block.getBlockById(data.readInt());
		byte meta = data.readByte();
		blockMetaConstructor(x, y, z, block, meta);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("TileID", Block.getIdFromBlock(theBlock));
		nbt.setByte("Data", (byte) theBlockMeta);
		nbt.setByte("Time", (byte) ticksFalling);
	}

	@Override
	public void writeSpawnData(ByteBuf data) {
		data.writeDouble(prevPosX);
		data.writeDouble(prevPosY);
		data.writeDouble(prevPosZ);
		data.writeInt(Block.getIdFromBlock(theBlock));
		data.writeByte(theBlockMeta);
	}
}
