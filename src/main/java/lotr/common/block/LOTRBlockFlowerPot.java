package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityFlowerPot;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockFlowerPot extends BlockFlowerPot {
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new LOTRTileEntityFlowerPot();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ItemStack itemstack;
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(Items.flower_pot));
		if ((meta & 8) == 0 && (itemstack = getPlant(world, i, j, k)) != null && world.getTileEntity(i, j, k) != null) {
			drops.add(itemstack);
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return Blocks.flower_pot.getIcon(i, j);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		return getPlant(world, i, j, k);
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getFlowerPotRenderID();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
		}
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (getPlant(world, i, j, k) != null && getPlant(world, i, j, k).getItem() == Item.getItemFromBlock(LOTRMod.pipeweedPlant)) {
			double d = i + 0.2 + random.nextFloat() * 0.6f;
			double d1 = j + 0.625 + random.nextFloat() * 0.1875f;
			double d2 = k + 0.2 + random.nextFloat() * 0.6f;
			world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
		}
		if (getPlant(world, i, j, k) != null && getPlant(world, i, j, k).getItem() == Item.getItemFromBlock(LOTRMod.corruptMallorn)) {
			LOTRMod.corruptMallorn.randomDisplayTick(world, i, j, k, random);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	public static boolean canAcceptPlant(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock) {
			Block block = ((ItemBlock) item).field_150939_a;
			return block instanceof LOTRBlockFlower;
		}
		return false;
	}

	public static ItemStack getPlant(IBlockAccess world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityFlowerPot) {
			LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot) tileentity;
			if (flowerPot.item == null) {
				return null;
			}
			return new ItemStack(flowerPot.item, 1, flowerPot.meta);
		}
		return null;
	}

	public static void setPlant(World world, int i, int j, int k, ItemStack itemstack) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof LOTRTileEntityFlowerPot) {
			LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot) tileentity;
			flowerPot.item = itemstack.getItem();
			flowerPot.meta = itemstack.getItemDamage();
			world.markBlockForUpdate(i, j, k);
		}
	}
}
