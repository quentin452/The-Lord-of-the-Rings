package lotr.common.recipe;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRInventoryPouch;
import lotr.common.item.*;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesPouch implements IRecipe {
	public int overrideColor;
	public boolean hasOverrideColor;

	public LOTRRecipesPouch() {
		this(-1, false);
	}

	public LOTRRecipesPouch(int color, boolean flag) {
		overrideColor = color;
		hasOverrideColor = flag;
	}

	public LOTRRecipesPouch(LOTRFaction f) {
		this(f.getFactionColor(), true);
	}

	public int getCombinedMeta(Iterable<ItemStack> pouches) {
		int size = 0;
		for (ItemStack pouch : pouches) {
			size += pouch.getItemDamage() + 1;
		}
		return size - 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack pouch;
		List<ItemStack> pouches = new ArrayList<>();
		int[] rgb = new int[3];
		int totalColor = 0;
		int coloredItems = 0;
		boolean anyDye = false;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() instanceof LOTRItemPouch) {
				pouches.add(itemstack);
				int pouchColor = LOTRItemPouch.getPouchColor(itemstack);
				float r = (pouchColor >> 16 & 0xFF) / 255.0f;
				float g = (pouchColor >> 8 & 0xFF) / 255.0f;
				float b = (pouchColor & 0xFF) / 255.0f;
				totalColor = (int) (totalColor + Math.max(r, Math.max(g, b)) * 255.0f);
				rgb[0] = (int) (rgb[0] + r * 255.0f);
				rgb[1] = (int) (rgb[1] + g * 255.0f);
				rgb[2] = (int) (rgb[2] + b * 255.0f);
				++coloredItems;
				if (!LOTRItemPouch.isPouchDyed(itemstack)) {
					continue;
				}
				anyDye = true;
				continue;
			}
			int dye = LOTRItemDye.isItemDye(itemstack);
			if (dye == -1) {
				return null;
			}
			float[] dyeColors = EntitySheep.fleeceColorTable[BlockColored.func_150031_c(dye)];
			int r = (int) (dyeColors[0] * 255.0f);
			int g = (int) (dyeColors[1] * 255.0f);
			int b = (int) (dyeColors[2] * 255.0f);
			totalColor += Math.max(r, Math.max(g, b));
			rgb[0] = rgb[0] + r;
			rgb[1] = rgb[1] + g;
			rgb[2] = rgb[2] + b;
			++coloredItems;
			anyDye = true;
		}
		if (pouches.isEmpty()) {
			return null;
		}
		if (pouches.size() == 1) {
			pouch = pouches.get(0).copy();
		} else {
			int meta = getCombinedMeta(pouches);
			pouch = new ItemStack(LOTRMod.pouch);
			pouch.stackSize = 1;
			pouch.setItemDamage(meta);
			IInventory pouchInv = new LOTRInventoryPouch(pouch);
			int slot = 0;
			for (ItemStack craftingPouch : pouches) {
				IInventory craftingPouchInv = new LOTRInventoryPouch(craftingPouch);
				for (int i = 0; i < craftingPouchInv.getSizeInventory(); ++i) {
					ItemStack slotItem = craftingPouchInv.getStackInSlot(i);
					if (slotItem == null) {
						continue;
					}
					pouchInv.setInventorySlotContents(slot, slotItem.copy());
					++slot;
				}
			}
		}
		if (hasOverrideColor) {
			LOTRItemPouch.setPouchColor(pouch, overrideColor);
		} else if (anyDye && coloredItems > 0) {
			int r = rgb[0] / coloredItems;
			int g = rgb[1] / coloredItems;
			int b = rgb[2] / coloredItems;
			float averageColor = (float) totalColor / coloredItems;
			float maxColor = Math.max(r, Math.max(g, b));
			r = (int) (r * averageColor / maxColor);
			g = (int) (g * averageColor / maxColor);
			b = (int) (b * averageColor / maxColor);
			int color = (r << 16) + (g << 8) + b;
			LOTRItemPouch.setPouchColor(pouch, color);
		}
		return pouch;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		Collection<ItemStack> pouches = new ArrayList<>();
		Collection<ItemStack> dyes = new ArrayList<>();
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() instanceof LOTRItemPouch) {
				pouches.add(itemstack);
				continue;
			}
			if (LOTRItemDye.isItemDye(itemstack) == -1) {
				return false;
			}
			dyes.add(itemstack);
		}
		if (pouches.isEmpty()) {
			return false;
		}
		if (pouches.size() == 1) {
			return hasOverrideColor || !dyes.isEmpty();
		}
		int meta = getCombinedMeta(pouches);
		return LOTRItemPouch.getCapacityForMeta(meta) <= LOTRItemPouch.getMaxPouchCapacity();
	}
}
