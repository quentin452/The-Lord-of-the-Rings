package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.dispenser.LOTRDispenseRhunFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class LOTRItemRhunFireJar extends ItemBlock {
	public LOTRItemRhunFireJar(Block block) {
		super(block);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseRhunFireJar());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		list.add(StatCollector.translateToLocal("tile.lotr.rhunFire.warning"));
	}
}
