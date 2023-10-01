package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBottlePoison;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;

public class LOTRBlockMug extends BlockContainer {
	public static float MUG_SCALE = 0.75f;

	public LOTRBlockMug() {
		this(3.0f, 8.0f);
	}

	public LOTRBlockMug(float f, float f1) {
		super(Material.circuits);
		f /= 16.0f;
		f1 /= 16.0f;
		setBlockBounds(0.5f - (f *= 0.75f), 0.0f, 0.5f - f, 0.5f + f, f1 * 0.75f, 0.5f + f);
		setHardness(0.0f);
		setStepSound(Block.soundTypeWood);
	}

	public static ItemStack getMugItem(IBlockAccess world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityMug) {
			LOTRTileEntityMug mug = (LOTRTileEntityMug) tileentity;
			return mug.getMugItem();
		}
		return new ItemStack(LOTRMod.mug);
	}

	public static void setMugItem(IBlockAccess world, int i, int j, int k, ItemStack itemstack, LOTRItemMug.Vessel vessel) {
		TileEntity te = world.getTileEntity(i, j, k);
		if (te instanceof LOTRTileEntityMug) {
			LOTRTileEntityMug mug = (LOTRTileEntityMug) te;
			mug.setMugItem(itemstack);
			mug.setVessel(vessel);
		}
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j - 1, k);
		return block.canPlaceTorchOnTop(world, i, j - 1, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityMug();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if ((meta & 4) == 0) {
			ItemStack itemstack = getMugItem(world, i, j, k);
			LOTRTileEntityMug mug = (LOTRTileEntityMug) world.getTileEntity(i, j, k);
			if (mug != null) {
				drops.add(itemstack);
			}
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		return getMugItem(world, i, j, k);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityMug) {
			LOTRTileEntityMug mug = (LOTRTileEntityMug) tileentity;
			ItemStack mugItem = mug.getMugItem();
			if (!mug.isEmpty() && LOTRItemMug.isItemEmptyDrink(itemstack)) {
				ItemStack takenDrink = mugItem.copy();
				LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
				LOTRItemMug.setVessel(takenDrink, v, true);
				if (entityplayer.capabilities.isCreativeMode) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
				} else {
					--itemstack.stackSize;
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
					} else if (!entityplayer.inventory.addItemStackToInventory(takenDrink)) {
						entityplayer.dropPlayerItemWithRandomChoice(takenDrink, false);
					}
				}
				mug.setEmpty();
				world.playSoundAtEntity(entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				return true;
			}
			if (mug.isEmpty() && LOTRItemMug.isItemFullDrink(itemstack)) {
				ItemStack emptyMug = LOTRItemMug.getVessel(itemstack).getEmptyVessel();
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
				ItemStack mugFill = itemstack.copy();
				mugFill.stackSize = 1;
				mug.setMugItem(mugFill);
				world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				return true;
			}
			if (!mug.isEmpty()) {
				if (itemstack != null && itemstack.getItem() instanceof LOTRItemBottlePoison && mug.canPoisonMug()) {
					if (!world.isRemote) {
						mug.poisonMug(entityplayer);
						if (!entityplayer.capabilities.isCreativeMode) {
							ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
							entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
						}
						entityplayer.openContainer.detectAndSendChanges();
						((EntityPlayerMP) entityplayer).sendContainerToPlayer(entityplayer.openContainer);
					}
					return true;
				}
				ItemStack equivalentDrink = LOTRItemMug.getEquivalentDrink(mugItem);
				Item eqItem = equivalentDrink.getItem();
				boolean canDrink = false;
				if (eqItem instanceof LOTRItemMug) {
					canDrink = ((LOTRItemMug) eqItem).canPlayerDrink(entityplayer);
				}
				if (canDrink) {
					ItemStack mugItemResult = mugItem.onFoodEaten(world, entityplayer);
					ForgeEventFactory.onItemUseFinish(entityplayer, mugItem, mugItem.getMaxItemUseDuration(), mugItemResult);
					mug.setEmpty();
					world.markBlockForUpdate(i, j, k);
					world.playSoundAtEntity(entityplayer, "random.drink", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 4, 4);
		}
		dropBlockAsItem(world, i, j, k, meta, 0);
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
