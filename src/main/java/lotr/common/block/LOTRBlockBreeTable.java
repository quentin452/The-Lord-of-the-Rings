package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockBreeTable extends LOTRBlockCraftingTable {
	@SideOnly(Side.CLIENT)
	public IIcon[] tableIcons;

	public LOTRBlockBreeTable() {
		super(Material.wood, LOTRFaction.BREE, 62);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[2];
		}
		if (i == 0) {
			return LOTRMod.planks.getIcon(0, 9);
		}
		return i == 4 || i == 5 ? tableIcons[0] : tableIcons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		tableIcons = new IIcon[3];
		tableIcons[0] = iconregister.registerIcon(getTextureName() + "_side0");
		tableIcons[1] = iconregister.registerIcon(getTextureName() + "_side1");
		tableIcons[2] = iconregister.registerIcon(getTextureName() + "_top");
	}
}
