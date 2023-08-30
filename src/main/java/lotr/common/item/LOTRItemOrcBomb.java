package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.dispenser.LOTRDispenseOrcBomb;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class LOTRItemOrcBomb extends ItemBlock {
	public LOTRItemOrcBomb(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseOrcBomb());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		int meta = itemstack.getItemDamage();
		int strength = LOTRBlockOrcBomb.getBombStrengthLevel(meta);
		if (strength == 1) {
			list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.doubleStrength"));
		}
		if (strength == 2) {
			list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.tripleStrength"));
		}
		if (LOTRBlockOrcBomb.isFireBomb(meta)) {
			list.add(StatCollector.translateToLocal("tile.lotr.orcBomb.fire"));
		}
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}
