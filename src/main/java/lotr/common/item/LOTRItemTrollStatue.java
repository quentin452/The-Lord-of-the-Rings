package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityStoneTroll;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemTrollStatue extends Item {
	public LOTRItemTrollStatue() {
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("TwoHeads")) {
			list.add(StatCollector.translateToLocal("item.lotr.trollStatue.twoHeads"));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j <= 2; ++j) {
			ItemStack statue = new ItemStack(item, 1, j);
			list.add(statue);
			statue = statue.copy();
			statue.setTagCompound(new NBTTagCompound());
			statue.getTagCompound().setBoolean("TwoHeads", true);
			list.add(statue);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
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
			LOTREntityStoneTroll trollStatue = new LOTREntityStoneTroll(world);
			trollStatue.setLocationAndAngles((double) i + f, j, (double) k + f2, 180.0f - entityplayer.rotationYaw % 360.0f, 0.0f);
			if (world.checkNoEntityCollision(trollStatue.boundingBox) && world.getCollidingBoundingBoxes(trollStatue, trollStatue.boundingBox).isEmpty() && !world.isAnyLiquid(trollStatue.boundingBox)) {
				trollStatue.setTrollOutfit(itemstack.getItemDamage());
				if (itemstack.hasTagCompound()) {
					trollStatue.setHasTwoHeads(itemstack.getTagCompound().getBoolean("TwoHeads"));
				}
				trollStatue.placedByPlayer = true;
				world.spawnEntityInWorld(trollStatue);
				world.playSoundAtEntity(trollStatue, Blocks.stone.stepSound.func_150496_b(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
				--itemstack.stackSize;
				return true;
			}
			trollStatue.setDead();
		}
		return false;
	}
}
