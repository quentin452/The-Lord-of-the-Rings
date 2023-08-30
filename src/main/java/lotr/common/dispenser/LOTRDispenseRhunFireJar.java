package lotr.common.dispenser;

import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.block.*;
import net.minecraft.dispenser.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LOTRDispenseRhunFireJar extends BehaviorDefaultDispenseItem {
	public IBehaviorDispenseItem dispenseDefault = new BehaviorDefaultDispenseItem();

	@Override
	public ItemStack dispenseStack(IBlockSource dispenser, ItemStack itemstack) {
		int k;
		int j;
		int i;
		EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
		World world = dispenser.getWorld();
		if (world.getBlock(i = dispenser.getXInt() + enumfacing.getFrontOffsetX(), j = dispenser.getYInt() + enumfacing.getFrontOffsetY(), k = dispenser.getZInt() + enumfacing.getFrontOffsetZ()).isReplaceable(world, i, j, k)) {
			LOTRBlockRhunFireJar.explodeOnAdded = false;
			world.setBlock(i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage(), 3);
			LOTRBlockRhunFireJar.explodeOnAdded = true;
			--itemstack.stackSize;
			return itemstack;
		}
		return dispenseDefault.dispense(dispenser, itemstack);
	}
}
