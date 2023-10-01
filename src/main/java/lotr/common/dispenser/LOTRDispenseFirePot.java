package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityFirePot;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseFirePot extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new LOTREntityFirePot(world, position.getX(), position.getY(), position.getZ());
	}
}
