package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRDispenseCrossbowBolt extends BehaviorProjectileDispense {
	public Item theBoltItem;

	public LOTRDispenseCrossbowBolt(Item item) {
		theBoltItem = item;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition iposition) {
		ItemStack itemstack = new ItemStack(theBoltItem);
		LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
		bolt.canBePickedUp = 1;
		return bolt;
	}

	@Override
	public void playDispenseSound(IBlockSource source) {
		source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "lotr:item.crossbow", 1.0f, 1.2f);
	}
}
