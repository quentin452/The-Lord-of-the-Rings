package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockRope extends LOTRBlockLadder {
	public boolean canRetract;

	public LOTRBlockRope(boolean flag) {
		setHardness(0.4f);
		setStepSound(Block.soundTypeCloth);
		canRetract = flag;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return canPlaceBlockAt(world, i, j, k);
	}

	public boolean canExtendRopeWithMetadata(World world, int i, int j, int k, int meta) {
		if (world.getBlock(i, j + 1, k) == this) {
			return true;
		}
		switch (meta) {
			case 2:
				return world.isSideSolid(i, j, k + 1, ForgeDirection.NORTH);
			case 3:
				return world.isSideSolid(i, j, k - 1, ForgeDirection.SOUTH);
			case 4:
				return world.isSideSolid(i + 1, j, k, ForgeDirection.WEST);
			case 5:
				return world.isSideSolid(i - 1, j, k, ForgeDirection.EAST);
			default:
				break;
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return world.getBlock(i, j + 1, k) == this || super.canPlaceBlockAt(world, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getRopeRenderID();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean lookingUpOrDown = entityplayer.rotationPitch <= 0.0f;
		int lookDir = lookingUpOrDown ? 1 : -1;
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(this)) {
			int j1;
			Block block;
			//noinspection StatementWithEmptyBody
			for (j1 = j; j1 >= 0 && j1 < world.getHeight() && world.getBlock(i, j1, k) == this; j1 += lookDir) {
			}
			if (j1 >= 0 && j1 < world.getHeight()) {
				int thisMeta;
				block = world.getBlock(i, j1, k);
				if (canPlaceBlockOnSide(world, i, j1, k, side) && block.isReplaceable(world, i, j1, k) && !block.getMaterial().isLiquid() && canExtendRopeWithMetadata(world, i, j1, k, thisMeta = world.getBlockMetadata(i, j, k))) {
					world.setBlock(i, j1, k, this, thisMeta, 3);
					world.playSoundEffect(i + 0.5f, j1 + 0.5f, k + 0.5f, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
					}
					return true;
				}
			}
		} else if (!entityplayer.isOnLadder() && canRetract) {
			if (!world.isRemote) {
				boolean invAdded = false;
				for (int j1 = j; j1 >= 0 && j1 < world.getHeight(); j1 += lookDir) {
					Block block = world.getBlock(i, j1, k);
					int meta = world.getBlockMetadata(i, j1, k);
					if (block != this) {
						break;
					}
					if (!entityplayer.capabilities.isCreativeMode) {
						Iterable<ItemStack> drops = block.getDrops(world, i, j1, k, meta, 0);
						for (ItemStack drop : drops) {
							if (entityplayer.inventory.addItemStackToInventory(drop)) {
								invAdded = true;
								continue;
							}
							entityplayer.dropPlayerItemWithRandomChoice(drop, false);
						}
					}
					world.setBlockToAir(i, j1, k);
					world.playSoundEffect(i + 0.5f, j1 + 0.5f, k + 0.5f, stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
				}
				if (invAdded) {
					entityplayer.openContainer.detectAndSendChanges();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int onBlockPlaced(World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ, int meta) {
		int placeMeta = super.onBlockPlaced(world, i, j, k, side, hitX, hitY, hitZ, meta);
		if (placeMeta == 0 && world.getBlock(i, j + 1, k) == this) {
			placeMeta = world.getBlockMetadata(i, j + 1, k);
		}
		return placeMeta;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (world.getBlock(i, j + 1, k) != this) {
			super.onNeighborBlockChange(world, i, j, k, block);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
		if (side == 0 || side == 1) {
			Block block = world.getBlock(i, j, k);
			return block != this && !block.isOpaqueCube();
		}
		return true;
	}
}
