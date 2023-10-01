package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class LOTRBlockOreMordorVariant extends LOTRBlockOre {
	@SideOnly(Side.CLIENT)
	public IIcon mordorIcon;
	public boolean dropsBlock;

	public LOTRBlockOreMordorVariant(boolean flag) {
		dropsBlock = flag;
	}

	@Override
	public int damageDropped(int i) {
		if (dropsBlock) {
			return i;
		}
		return super.damageDropped(i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 1) {
			return mordorIcon;
		}
		return super.getIcon(i, j);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
		return new ItemStack(this, 1, world.getBlockMetadata(i, j, k));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j <= 1; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		mordorIcon = iconregister.registerIcon(getTextureName() + "_mordor");
	}
}
