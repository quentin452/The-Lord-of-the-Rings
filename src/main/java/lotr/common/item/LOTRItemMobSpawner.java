package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class LOTRItemMobSpawner extends ItemBlock {
	public LOTRItemMobSpawner(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		String entityName = LOTREntities.getStringFromID(itemstack.getItemDamage());
		list.add(entityName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (LOTREntities.SpawnEggInfo info : LOTREntities.spawnEggs.values()) {
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
		if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof LOTRTileEntityMobSpawner) {
				((LOTRTileEntityMobSpawner) tileentity).setEntityClassID(itemstack.getItemDamage());
				((LOTRTileEntityMobSpawner) tileentity).spawnsPersistentNPCs = true;
			}
			return true;
		}
		return false;
	}
}
