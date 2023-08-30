package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRItemRugBase extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon[] rugIcons;
	public String[] rugNames;

	protected LOTRItemRugBase(String... names) {
		rugNames = names;
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public abstract LOTREntityRugBase createRug(World var1, ItemStack var2);

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		if (i >= rugIcons.length) {
			i = 0;
		}
		return rugIcons[i];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < rugNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		Block block = world.getBlock(i, j, k);
		if (block == Blocks.snow_layer) {
			l = 1;
		} else if (!block.isReplaceable(world, i, j, k)) {
			if (l == 0) {
				--j;
			}
			if (l == 1) {
				++j;
			}
			if (l == 2) {
				--k;
			}
			if (l == 3) {
				++k;
			}
			if (l == 4) {
				--i;
			}
			if (l == 5) {
				++i;
			}
		}
		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
			return false;
		}
		if (world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.isRemote) {
			LOTREntityRugBase rug = createRug(world, itemstack);
			rug.setLocationAndAngles(i + f, j, k + f2, 180.0f - entityplayer.rotationYaw % 360.0f, 0.0f);
			if (world.checkNoEntityCollision(rug.boundingBox) && world.getCollidingBoundingBoxes(rug, rug.boundingBox).isEmpty() && !world.isAnyLiquid(rug.boundingBox)) {
				world.spawnEntityInWorld(rug);
				world.playSoundAtEntity(rug, Blocks.wool.stepSound.func_150496_b(), (Blocks.wool.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.wool.stepSound.getPitch() * 0.8f);
				--itemstack.stackSize;
				return true;
			}
			rug.setDead();
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		rugIcons = new IIcon[rugNames.length];
		for (int i = 0; i < rugIcons.length; ++i) {
			rugIcons[i] = iconregister.registerIcon(getIconString() + "_" + rugNames[i]);
		}
	}
}
