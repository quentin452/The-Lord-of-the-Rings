package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public abstract class LOTRBlockWoodBase extends BlockLog {
	@SideOnly(Side.CLIENT)
	public IIcon[][] woodIcons;
	public String[] woodNames;

	protected LOTRBlockWoodBase() {
		setHardness(2.0f);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j & 0xC;
		j &= 3;
		if (j >= woodNames.length) {
			j = 0;
		}
		if (j1 == 0 && (i == 0 || i == 1) || j1 == 4 && (i == 4 || i == 5) || j1 == 8 && (i == 2 || i == 3)) {
			return woodIcons[j][0];
		}
		return woodIcons[j][1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < woodNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		woodIcons = new IIcon[woodNames.length][2];
		for (int i = 0; i < woodNames.length; ++i) {
			woodIcons[i][0] = iconregister.registerIcon(getTextureName() + "_" + woodNames[i] + "_top");
			woodIcons[i][1] = iconregister.registerIcon(getTextureName() + "_" + woodNames[i] + "_side");
		}
	}

	public void setWoodNames(String... s) {
		woodNames = s;
	}
}
