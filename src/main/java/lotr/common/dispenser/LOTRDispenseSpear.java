package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntitySpear;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LOTRDispenseSpear extends BehaviorDefaultDispenseItem {
	@Override
	public ItemStack dispenseStack(IBlockSource dispenser, ItemStack itemstack) {
		World world = dispenser.getWorld();
		IPosition iposition = BlockDispenser.func_149939_a(dispenser);
		EnumFacing enumfacing = BlockDispenser.func_149937_b(dispenser.getBlockMetadata());
		LOTREntitySpear spear = new LOTREntitySpear(world, itemstack.copy(), iposition.getX(), iposition.getY(), iposition.getZ());
		spear.setThrowableHeading(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY() + 0.1f, enumfacing.getFrontOffsetZ(), 1.1f, 6.0f);
		spear.canBePickedUp = 1;
		world.spawnEntityInWorld(spear);
		itemstack.splitStack(1);
		return itemstack;
	}

	@Override
	public void playDispenseSound(IBlockSource dispenser) {
		dispenser.getWorld().playAuxSFX(1002, dispenser.getXInt(), dispenser.getYInt(), dispenser.getZInt(), 0);
	}
}
