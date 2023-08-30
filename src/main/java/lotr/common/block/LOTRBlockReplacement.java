package lotr.common.block;

import java.lang.reflect.*;
import java.util.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.registry.RegistryDelegate;
import io.gitlab.dwarfyassassin.lotrucp.core.hooks.GenericModHooks;
import lotr.common.LOTRReflection;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.*;

public class LOTRBlockReplacement {
	public static boolean initForgeHooks;

	public static void injectReplacementItem(ItemStack itemstack, Item newItem) {
		NBTTagCompound nbt = new NBTTagCompound();
		itemstack.writeToNBT(nbt);
		itemstack.readFromNBT(nbt);
	}

	public static void replaceBlockStats(int id, Block newBlock, ItemBlock itemblock) {
		replaceStat(id, StatList.mineBlockStatArray, new StatCrafting("stat.mineBlock." + id, new ChatComponentTranslation("stat.mineBlock", new ItemStack(newBlock).func_151000_E()), itemblock));
		replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(itemblock).func_151000_E()), itemblock));
		replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(itemblock).func_151000_E()), itemblock));
	}

	public static void replaceItemStats(int id, Item newItem) {
		replaceStat(id, StatList.objectUseStats, new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(newItem).func_151000_E()), newItem));
		replaceStat(id, StatList.objectCraftStats, new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(newItem).func_151000_E()), newItem));
		if (newItem.isDamageable()) {
			replaceStat(id, StatList.objectBreakStats, new StatCrafting("stat.breakItem." + id, new ChatComponentTranslation("stat.breakItem", new ItemStack(newItem).func_151000_E()), newItem));
		}
	}

	public static void replaceRecipesEtc(Item newItem) {
		String newItemName = newItem.getUnlocalizedName();
		List craftingRecipes = CraftingManager.getInstance().getRecipeList();
		for (Object obj : craftingRecipes) {
			if (obj instanceof ShapedRecipes) {
				IRecipe recipe = (IRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapelessRecipes) {
				IRecipe recipe = (IRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapedOreRecipe) {
				IRecipe recipe = (IRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
			if (obj instanceof ShapelessOreRecipe) {
				IRecipe recipe = (IRecipe) obj;
				ItemStack output = recipe.getRecipeOutput();
				if (output != null && output.getItem() != null && output.getItem().getUnlocalizedName().equals(newItemName)) {
					injectReplacementItem(output, newItem);
				}
			}
		}
		for (Object obj : AchievementList.achievementList) {
			Achievement a = (Achievement) obj;
			ItemStack icon = a.theItemStack;
			if (icon.getItem().getUnlocalizedName().equals(newItem.getUnlocalizedName())) {
				injectReplacementItem(icon, newItem);
			}
		}
	}

	public static void replaceStat(int id, StatBase[] stats, StatBase newStat) {
		StatBase oldStat = stats[id];
		if (oldStat != null && oldStat.statId.equals(newStat.statId)) {
			for (int i = 0; i < stats.length; ++i) {
				StatBase otherOldStat = stats[i];
				if (otherOldStat == null || !otherOldStat.statId.equals(oldStat.statId)) {
					continue;
				}
				StatList.allStats.remove(otherOldStat);
				StatList.objectMineStats.remove(otherOldStat);
				StatList.itemStats.remove(otherOldStat);
				StatList.generalStats.remove(otherOldStat);
				Reflect.getOneShotStats().remove(otherOldStat.statId);
				stats[i] = newStat;
			}
			newStat.registerStat();
		}
	}

	public static void replaceVanillaBlock(Block oldBlock, Block newBlock, Class<? extends ItemBlock> itemClass) {
		try {
			Item oldItem = Item.getItemFromBlock(oldBlock);
			int id = Block.blockRegistry.getIDForObject(oldBlock);
			String blockName = Reflect.getBlockName(oldBlock);
			String registryName = Block.blockRegistry.getNameForObject(oldBlock);
			String itemblockName = blockName;
			if (oldItem != null) {
				itemblockName = Reflect.getItemName(oldItem);
			}
			GenericModHooks.removeBlockFromOreDictionary(oldBlock);
			newBlock.setBlockName(blockName);
			Reflect.overwriteBlockList(oldBlock, newBlock);
			Reflect.setDelegateName(newBlock.delegate, oldBlock.delegate.name());
			Reflect.getUnderlyingIntMap(Block.blockRegistry).func_148746_a(newBlock, id);
			Reflect.getUnderlyingObjMap(Block.blockRegistry).put(registryName, newBlock);
			if (!initForgeHooks) {
				ForgeHooks.isToolEffective(new ItemStack(Items.iron_shovel), Blocks.dirt, 0);
				initForgeHooks = true;
			}
			for (int meta = 0; meta <= 15; ++meta) {
				newBlock.setHarvestLevel(oldBlock.getHarvestTool(meta), oldBlock.getHarvestLevel(meta), meta);
			}
			if (itemClass != null) {
				Constructor<?> itemCtor = null;
				for (Constructor<?> ct : itemClass.getConstructors()) {
					Class<?>[] params = ct.getParameterTypes();
					if (params.length != 1 || !Block.class.isAssignableFrom(params[0])) {
						continue;
					}
					itemCtor = ct;
					break;
				}
				ItemBlock itemblock = ((ItemBlock) itemCtor.newInstance(newBlock)).setUnlocalizedName(itemblockName);
				Reflect.setDelegateName(itemblock.delegate, oldItem.delegate.name());
				Reflect.getUnderlyingIntMap(Item.itemRegistry).func_148746_a(itemblock, id);
				Reflect.getUnderlyingObjMap(Item.itemRegistry).put(registryName, itemblock);
				replaceBlockStats(id, newBlock, itemblock);
				replaceRecipesEtc(itemblock);
			}
		} catch (Exception e) {
			FMLLog.severe("Failed to replace vanilla block %s", oldBlock.getUnlocalizedName());
			throw new RuntimeException(e);
		}
	}

	public static void replaceVanillaItem(Item oldItem, Item newItem) {
		try {
			int id = Item.itemRegistry.getIDForObject(oldItem);
			String itemName = Reflect.getItemName(oldItem);
			String registryName = Item.itemRegistry.getNameForObject(oldItem);
			GenericModHooks.removeItemFromOreDictionary(oldItem);
			newItem.setUnlocalizedName(itemName);
			Reflect.overwriteItemList(oldItem, newItem);
			Reflect.setDelegateName(newItem.delegate, oldItem.delegate.name());
			Reflect.getUnderlyingIntMap(Item.itemRegistry).func_148746_a(newItem, id);
			Reflect.getUnderlyingObjMap(Item.itemRegistry).put(registryName, newItem);
			replaceItemStats(id, newItem);
			replaceRecipesEtc(newItem);
		} catch (Exception e) {
			FMLLog.severe("Failed to replace vanilla item %s", oldItem.getUnlocalizedName());
			throw new RuntimeException(e);
		}
	}

	public static class Reflect {

		public static String getBlockName(Block block) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(Block.class, block, "unlocalizedName", "field_149770_b");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static String getItemName(Item item) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "unlocalizedName", "field_77774_bZ");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static Map getOneShotStats() {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(StatList.class, null, "oneShotStats", "field_75942_a");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static ObjectIntIdentityMap getUnderlyingIntMap(RegistryNamespaced registry) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(RegistryNamespaced.class, registry, "underlyingIntegerMap", "field_148759_a");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static Map getUnderlyingObjMap(RegistryNamespaced registry) {
			try {
				return ObfuscationReflectionHelper.getPrivateValue(RegistrySimple.class, registry, "registryObjects", "field_82596_a");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
				return null;
			}
		}

		public static void overwriteBlockList(Block oldBlock, Block newBlock) {
			try {
				Field field = null;
				for (Field f : Blocks.class.getDeclaredFields()) {
					LOTRReflection.unlockFinalField(f);
					if (f.get(null) != oldBlock) {
						continue;
					}
					field = f;
					break;
				}
				LOTRReflection.setFinalField(Blocks.class, null, newBlock, field);
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
			}
		}

		public static void overwriteItemList(Item oldItem, Item newItem) {
			try {
				Field field = null;
				for (Field f : Items.class.getDeclaredFields()) {
					LOTRReflection.unlockFinalField(f);
					if (f.get(null) != oldItem) {
						continue;
					}
					field = f;
					break;
				}
				LOTRReflection.setFinalField(Items.class, null, newItem, field);
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
			}
		}

		public static void setDelegateName(RegistryDelegate rd, String name) {
			RegistryDelegate.Delegate delegate = (RegistryDelegate.Delegate) rd;
			try {
				ObfuscationReflectionHelper.setPrivateValue(RegistryDelegate.Delegate.class, delegate, (Object) name, "name");
			} catch (Exception e) {
				LOTRReflection.logFailure(e);
			}
		}
	}

}
