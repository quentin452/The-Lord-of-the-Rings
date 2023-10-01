package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRBlockHearth extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] blockIcons;

	public LOTRBlockHearth() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return blockIcons[0];
		}
		if (i == 1) {
			return blockIcons[1];
		}
		return blockIcons[2];
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlock(i, j + 1, k) == Blocks.fire) {
			int smokeHeight = 5;
			for (int j1 = j + 1; j1 <= j + smokeHeight && !world.getBlock(i, j1, k).getMaterial().isSolid(); ++j1) {
				for (int l = 0; l < 3; ++l) {
					float f = i + random.nextFloat();
					float f1 = j1 + random.nextFloat();
					float f2 = k + random.nextFloat();
					world.spawnParticle("largesmoke", f, f1, f2, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcons = new IIcon[3];
		blockIcons[0] = iconregister.registerIcon(getTextureName() + "_bottom");
		blockIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
		blockIcons[2] = iconregister.registerIcon(getTextureName() + "_side");
	}
}
