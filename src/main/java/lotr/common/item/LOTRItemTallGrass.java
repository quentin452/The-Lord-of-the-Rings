package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemTallGrass extends LOTRItemBlockMetadata {
	public LOTRItemTallGrass(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass > 0) {
			return 16777215;
		}
		return super.getColorFromItemStack(itemstack, pass);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if (pass > 0) {
			return LOTRMod.tallGrass.getIcon(-1, meta);
		}
		return super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	public int getRenderPasses(int meta) {
		return LOTRBlockTallGrass.grassOverlay[meta] ? 2 : 1;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
