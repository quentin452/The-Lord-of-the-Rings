package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.dispenser.LOTRDispenseRhunFireJar;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

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
