package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.block.Block;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispensePlate extends BehaviorProjectileDispense {
	public Block plateBlock;

	public LOTRDispensePlate(Block block) {
		plateBlock = block;
	}

	@Override
	public IProjectile getProjectileEntity(World world, IPosition position) {
		return new LOTREntityPlate(world, plateBlock, position.getX(), position.getY(), position.getZ());
	}
}
