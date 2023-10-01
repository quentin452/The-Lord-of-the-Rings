package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRItemWaterPlant extends ItemBlock {
	public LOTRItemWaterPlant(Block block) {
		super(block);
	}

	public static ItemStack tryPlaceWaterPlant(ItemBlock itemblock, ItemStack itemstack, World world, EntityPlayer entityplayer, MovingObjectPosition targetBlock) {
		if (targetBlock == null) {
			return itemstack;
		}
		if (targetBlock.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = targetBlock.blockX;
			int j = targetBlock.blockY;
			int k = targetBlock.blockZ;
			if (!world.canMineBlock(entityplayer, i, j, k) || !entityplayer.canPlayerEdit(i, j, k, targetBlock.sideHit, itemstack)) {
				return itemstack;
			}
			Block block = itemblock.field_150939_a;
			int meta = itemblock.getMetadata(itemstack.getItemDamage());
			if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k) && block.canPlaceBlockAt(world, i, j + 1, k) && itemblock.placeBlockAt(itemstack, entityplayer, world, i, j + 1, k, 1, 0.5f, 0.5f, 0.5f, meta)) {
				Block.SoundType stepsound = block.stepSound;
				world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, stepsound.func_150496_b(), (stepsound.getVolume() + 1.0f) / 2.0f, stepsound.getPitch() * 0.8f);
				if (!entityplayer.capabilities.isCreativeMode) {
					--itemstack.stackSize;
				}
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return tryPlaceWaterPlant(this, itemstack, world, entityplayer, getMovingObjectPositionFromPlayer(world, entityplayer, true));
	}
}
