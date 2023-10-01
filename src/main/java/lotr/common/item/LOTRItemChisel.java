package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class LOTRItemChisel extends Item {
	public Block signBlock;

	public LOTRItemChisel(Block block) {
		signBlock = block;
		setCreativeTab(LOTRCreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(100);
		setFull3D();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (side == 0 || side == 1) {
			return false;
		}
		Block block = world.getBlock(i, j, k);
		Material mt = block.getMaterial();
		if (block.isOpaqueCube() && (mt == Material.rock || mt == Material.wood || mt == Material.iron)) {
			if (!entityplayer.canPlayerEdit(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side], side, itemstack) || !signBlock.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			if (!world.isRemote) {
				world.setBlock(i, j, k, signBlock, side, 3);
				itemstack.damageItem(1, entityplayer);
				LOTRTileEntitySign sign = (LOTRTileEntitySign) world.getTileEntity(i, j, k);
				if (sign != null) {
					sign.openEditGUI((EntityPlayerMP) entityplayer);
				}
			}
			return true;
		}
		return false;
	}
}
