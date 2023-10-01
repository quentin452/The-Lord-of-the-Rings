package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemArmorStand extends Item {
	public LOTRItemArmorStand() {
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (world.isRemote) {
			return true;
		}
		Block block = LOTRMod.armorStand;
		Block block1 = world.getBlock(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]);
		Block block2 = world.getBlock(i, j + 1, k);
		if (block1 != null && !block1.isReplaceable(world, i, j, k) || block2 != null && !block2.isReplaceable(world, i, j + 1, k)) {
			return false;
		}
		if (entityplayer.canPlayerEdit(i, j, k, side, itemstack) && entityplayer.canPlayerEdit(i, j + 1, k, side, itemstack)) {
			if (!block.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
			world.setBlock(i, j, k, block, l, 3);
			world.setBlock(i, j + 1, k, block, l | 4, 3);
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
