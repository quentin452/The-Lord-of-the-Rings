package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityMysteryWeb;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseMysteryWeb extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new LOTREntityMysteryWeb(world, position.getX(), position.getY(), position.getZ());
	}
}
