package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRBlockOrcPlating extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] blockIcons;
	public String[] blockNames = {"iron", "rust"};

	public LOTRBlockOrcPlating() {
		super(Material.iron);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(3.0f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeMetal);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (j >= blockNames.length) {
			j = 0;
		}
		return blockIcons[j];
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getOrcPlatingRenderID();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < blockNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcons = new IIcon[blockNames.length];
		for (int i = 0; i < blockNames.length; ++i) {
			blockIcons[i] = iconregister.registerIcon(getTextureName() + "_" + blockNames[i]);
		}
	}
}
