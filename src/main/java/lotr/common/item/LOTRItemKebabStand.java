package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class LOTRItemKebabStand extends ItemBlock {
	public LOTRItemKebabStand(Block block) {
		super(block);
	}

	public static NBTTagCompound getKebabData(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRKebabData")) {
			return itemstack.getTagCompound().getCompoundTag("LOTRKebabData");
		}
		return null;
	}

	public static void loadKebabData(ItemStack itemstack, LOTRTileEntityKebabStand kebabStand) {
		NBTTagCompound kebabData = getKebabData(itemstack);
		if (kebabData != null) {
			kebabStand.readKebabStandFromNBT(kebabData);
		}
	}

	public static void setKebabData(ItemStack itemstack, LOTRTileEntityKebabStand kebabStand) {
		if (kebabStand.shouldSaveBlockData()) {
			NBTTagCompound kebabData = new NBTTagCompound();
			kebabStand.writeKebabStandToNBT(kebabData);
			setKebabData(itemstack, kebabData);
		}
	}

	public static void setKebabData(ItemStack itemstack, NBTTagCompound kebabData) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setTag("LOTRKebabData", kebabData);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound kebabData = getKebabData(itemstack);
		if (kebabData != null) {
			LOTRTileEntityKebabStand kebabStand = new LOTRTileEntityKebabStand();
			kebabStand.readKebabStandFromNBT(kebabData);
			int meats = kebabStand.getMeatAmount();
			list.add(StatCollector.translateToLocalFormatted("tile.lotr.kebabStand.meats", meats));
			if (kebabStand.isCooked()) {
				list.add(StatCollector.translateToLocal("tile.lotr.kebabStand.cooked"));
			}
		}
	}
}
