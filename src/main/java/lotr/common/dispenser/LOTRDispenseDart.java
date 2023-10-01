package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.LOTRItemDart;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRDispenseDart extends BehaviorProjectileDispense {
	public LOTRItemDart theDartItem;

	public LOTRDispenseDart(LOTRItemDart item) {
		theDartItem = item;
	}

	@Override
	public float func_82500_b() {
		return 1.5f;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition iposition) {
		ItemStack itemstack = new ItemStack(theDartItem);
		LOTREntityDart dart = theDartItem.createDart(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
		dart.canBePickedUp = 1;
		return dart;
	}

	@Override
	public void playDispenseSound(IBlockSource source) {
		source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "lotr:item.dart", 1.0f, 1.2f);
	}
}
