package lotr.common.item;

import lotr.common.block.LOTRBlockFallenLeaves;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRItemFallenLeaves extends LOTRItemBlockMetadata {
	public LOTRItemFallenLeaves(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		Object[] obj = ((LOTRBlockFallenLeaves) field_150939_a).leafBlockMetaFromFallenMeta(itemstack.getItemDamage());
		ItemStack leaves = new ItemStack((Block) obj[0], 1, (Integer) obj[1]);
		String name = leaves.getDisplayName();
		return StatCollector.translateToLocalFormatted("tile.lotr.fallenLeaves", name);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return LOTRItemWaterPlant.tryPlaceWaterPlant(this, itemstack, world, entityplayer, getMovingObjectPositionFromPlayer(world, entityplayer, true));
	}
}
