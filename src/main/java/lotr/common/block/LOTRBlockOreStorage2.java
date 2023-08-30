package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockOreStorage2 extends LOTRBlockOreStorageBase {
	public LOTRBlockOreStorage2() {
		setOreStorageNames("blackUrukSteel", "elfSteel", "gildedIron", "salt");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
	}
}
