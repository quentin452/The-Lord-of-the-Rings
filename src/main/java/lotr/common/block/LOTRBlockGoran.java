package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockGoran extends Block {
	public static String[] goranNames = {"", "rock"};
	public static String[] displayNames = {"Goran", "Cargoran"};
	@SideOnly(Side.CLIENT)
	public IIcon[] goranIcons;

	public LOTRBlockGoran() {
		super(Material.rock);
		setCreativeTab(null);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (j >= goranNames.length) {
			j = 0;
		}
		return goranIcons[j];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < goranNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile())) {
				return false;
			}
			for (int i1 = i - 32; i1 <= i + 32; ++i1) {
				for (int j1 = j - 32; j1 <= j + 32; ++j1) {
					for (int k1 = k - 32; k1 <= k + 32; ++k1) {
						if (!world.blockExists(i1, j1, k1) || !world.isAirBlock(i1, j1, k1)) {
							continue;
						}
						world.setBlock(i1, j1, k1, Blocks.water);
					}
				}
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		goranIcons = new IIcon[goranNames.length];
		for (int i = 0; i < goranNames.length; ++i) {
			StringBuilder iconName = new StringBuilder().append(getTextureName());
			if (!"".equals(goranNames[i])) {
				iconName.append("_").append(goranNames[i]);
			}
			goranIcons[i] = iconregister.registerIcon(iconName.toString());
		}
	}
}
