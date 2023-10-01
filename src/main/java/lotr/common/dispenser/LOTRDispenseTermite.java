package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityThrownTermite;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseTermite extends BehaviorProjectileDispense {
	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new LOTREntityThrownTermite(world, position.getX(), position.getY(), position.getZ());
	}
}
