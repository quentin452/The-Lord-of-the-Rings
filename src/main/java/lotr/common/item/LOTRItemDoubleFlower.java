package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.IIcon;

public class LOTRItemDoubleFlower extends LOTRItemBlockMetadata {
	public LOTRItemDoubleFlower(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		return ((BlockDoublePlant) field_150939_a).func_149888_a(true, i);
	}
}
