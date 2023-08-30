package lotr.common.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesTreasurePile implements IRecipe {
	public Block treasureBlock;
	public Item ingotItem;

	public LOTRRecipesTreasurePile(Block block, Item item) {
		treasureBlock = block;
		ingotItem = item;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		int ingredientCount = 0;
		int ingredientTotalSize = 0;
		int resultCount = 0;
		int resultMeta = 0;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() == Item.getItemFromBlock(treasureBlock)) {
				++ingredientCount;
				int meta = itemstack.getItemDamage();
				ingredientTotalSize += meta + 1;
				continue;
			}
			return null;
		}
		if (ingredientCount > 0) {
			if (ingredientCount == 1) {
				if (ingredientTotalSize > 1) {
					resultCount = ingredientTotalSize;
					resultMeta = 0;
				}
			} else {
				resultCount = 1;
				resultMeta = ingredientTotalSize - 1;
			}
		}
		if (resultCount == 0 || resultMeta > 7) {
			return null;
		}
		if (ingredientCount == 1 && ingredientTotalSize == 8) {
			return new ItemStack(ingotItem, 4);
		}
		return new ItemStack(treasureBlock, resultCount, resultMeta);
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
		return getCraftingResult(inv) != null;
	}
}
