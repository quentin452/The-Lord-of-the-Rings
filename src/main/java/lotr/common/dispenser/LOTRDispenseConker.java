package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityConker;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseConker extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new LOTREntityConker(world, position.getX(), position.getY(), position.getZ());
	}
}
