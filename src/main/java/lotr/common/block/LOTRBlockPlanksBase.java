package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockPlanksBase extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] plankIcons;
	public String[] plankTypes;

	protected LOTRBlockPlanksBase() {
		super(Material.wood);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= plankTypes.length) {
			j = 0;
		}
		return plankIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < plankTypes.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		plankIcons = new IIcon[plankTypes.length];
		for (int i = 0; i < plankTypes.length; ++i) {
			plankIcons[i] = iconregister.registerIcon(getTextureName() + "_" + plankTypes[i]);
		}
	}

	public void setPlankTypes(String... types) {
		plankTypes = types;
	}
}
