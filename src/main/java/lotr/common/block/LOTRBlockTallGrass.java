package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDamage;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LOTRBlockTallGrass extends LOTRBlockGrass {
	public static String[] grassNames = {"short", "flower", "wheat", "thistle", "nettle", "fernsprout"};
	public static boolean[] grassOverlay = {false, true, true, true, false, false};
	@SideOnly(Side.CLIENT)
	public IIcon[] grassIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] overlayIcons;

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor() {
		return Blocks.tallgrass.getBlockColor();
	}

	@Override
	public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
		if (meta == 3) {
			ArrayList<ItemStack> thistles = new ArrayList<>();
			thistles.add(new ItemStack(this, 1, 3));
			return thistles;
		}
		return super.getDrops(world, i, j, k, meta, fortune);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= grassNames.length) {
			j = 0;
		}
		if (i == -1) {
			return overlayIcons[j];
		}
		return grassIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int meta) {
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < grassNames.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 3 && entity.isSprinting() || meta == 4 && entity instanceof EntityPlayer) {
			EntityLivingBase living;
			boolean bootsLegs = entity instanceof EntityLivingBase && (living = (EntityLivingBase) entity).getEquipmentInSlot(1) != null && living.getEquipmentInSlot(2) != null;
			if (!bootsLegs) {
				entity.attackEntityFrom(LOTRDamage.plantHurt, 0.25f);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		grassIcons = new IIcon[grassNames.length];
		overlayIcons = new IIcon[grassNames.length];
		for (int i = 0; i < grassNames.length; ++i) {
			grassIcons[i] = iconregister.registerIcon(getTextureName() + "_" + grassNames[i]);
			if (!grassOverlay[i]) {
				continue;
			}
			overlayIcons[i] = iconregister.registerIcon(getTextureName() + "_" + grassNames[i] + "_overlay");
		}
	}
}
