package lotr.common.item;

import lotr.common.block.LOTRBlockGate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemGate extends ItemBlock {
	public LOTRBlockGate gateBlock;

	public LOTRItemGate(Block block) {
		super(block);
		gateBlock = (LOTRBlockGate) block;
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int meta) {
		int yaw = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
		float horizontalAngle = 40.0f;
		boolean lookingUp = entityplayer.rotationPitch < -horizontalAngle;
		boolean lookingDown = entityplayer.rotationPitch > horizontalAngle;
		boolean fullBlock = gateBlock.fullBlockGate;
		if (side == 0 || side == 1) {
			meta = Direction.directionToFacing[yaw];
		} else if (lookingUp || lookingDown) {
			meta = fullBlock ? entityplayer.rotationPitch > 0.0f ? 0 : 1 : f1 > 0.5f ? 0 : 1;
		} else {
			int dir = Direction.facingToDirection[side];
			meta = fullBlock ? Direction.directionToFacing[Direction.rotateOpposite[dir]] : Direction.directionToFacing[Direction.rotateLeft[dir]];
		}
		return super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, meta);
	}
}
