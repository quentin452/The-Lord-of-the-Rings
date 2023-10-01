package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.world.structure.LOTRStructures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class LOTRItemStructureSpawner extends Item {
	public static int lastStructureSpawnTick;
	@SideOnly(Side.CLIENT)
	public IIcon iconBase;
	@SideOnly(Side.CLIENT)
	public IIcon iconOverlay;
	@SideOnly(Side.CLIENT)
	public IIcon iconVillageBase;
	@SideOnly(Side.CLIENT)
	public IIcon iconVillageOverlay;

	public LOTRItemStructureSpawner() {
		setHasSubtypes(true);
		setCreativeTab(LOTRCreativeTabs.tabSpawn);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(itemstack.getItemDamage());
		if (info != null) {
			if (pass == 0) {
				return info.colorBackground;
			}
			return info.colorForeground;
		}
		return 16777215;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int i, int pass) {
		LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(i);
		if (info != null) {
			if (info.isVillage) {
				if (pass == 0) {
					return iconVillageBase;
				}
				return iconVillageOverlay;
			}
			if (pass == 0) {
				return iconBase;
			}
			return iconOverlay;
		}
		return iconBase;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		StringBuilder s = new StringBuilder().append((StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim());
		String structureName = LOTRStructures.getNameFromID(itemstack.getItemDamage());
		if (structureName != null) {
			s.append(" ").append(StatCollector.translateToLocal("lotr.structure." + structureName + ".name"));
		}
		return s.toString();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (LOTRStructures.StructureColorInfo info : LOTRStructures.structureItemSpawners.values()) {
			if (info.isHidden) {
				continue;
			}
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (world.isRemote) {
			return true;
		}
		if (LOTRLevelData.structuresBanned()) {
			entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.disabled"));
			return false;
		}
		if (LOTRLevelData.isPlayerBannedForStructures(entityplayer)) {
			entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.banned"));
			return false;
		}
		if (lastStructureSpawnTick > 0) {
			entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.wait", lastStructureSpawnTick / 20.0));
			return false;
		}
		if (spawnStructure(entityplayer, world, itemstack.getItemDamage(), i + Facing.offsetsXForSide[side], j + Facing.offsetsYForSide[side], k + Facing.offsetsZForSide[side]) && !entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		iconBase = iconregister.registerIcon(getIconString() + "_base");
		iconOverlay = iconregister.registerIcon(getIconString() + "_overlay");
		iconVillageBase = iconregister.registerIcon(getIconString() + "_village_base");
		iconVillageOverlay = iconregister.registerIcon(getIconString() + "_village_overlay");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public boolean spawnStructure(EntityPlayer entityplayer, World world, int id, int i, int j, int k) {
		if (!LOTRStructures.structureItemSpawners.containsKey(id)) {
			return false;
		}
		LOTRStructures.IStructureProvider strProvider = LOTRStructures.getStructureForID(id);
		if (strProvider != null) {
			boolean generated = strProvider.generateStructure(world, entityplayer, i, j, k);
			if (generated) {
				lastStructureSpawnTick = 20;
				world.playSoundAtEntity(entityplayer, "lotr:item.structureSpawner", 1.0f, 1.0f);
			}
			return generated;
		}
		return false;
	}
}
