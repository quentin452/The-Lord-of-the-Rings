package lotr.common.item;

import lotr.common.recipe.LOTRRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LOTRValuableItems {
	public static List<ItemStack> toolMaterials = new ArrayList<>();
	public static boolean initTools;

	public static boolean canMagpieSteal(ItemStack itemstack) {
		registerToolMaterials();
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemCoin || item instanceof LOTRItemRing || item instanceof LOTRItemGem) {
			return true;
		}
		for (ItemStack listItem : toolMaterials) {
			if (!LOTRRecipes.checkItemEquals(listItem, itemstack)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static List<ItemStack> getToolMaterials() {
		registerToolMaterials();
		return toolMaterials;
	}

	public static void registerToolMaterials() {
		if (!initTools) {
			toolMaterials.clear();

			for (Item.ToolMaterial material : Item.ToolMaterial.values()) {
				try {
					ItemStack repair = material.getRepairItemStack();

					if (repair == null || repair.getItem() == null) {
						continue;
					}

					if (material.getHarvestLevel() < 2) {
						continue;
					}

					toolMaterials.add(repair.copy());
				} catch (Throwable t) {
					System.err.println("[LOTRValuableItems] Skipping broken ToolMaterial: " + material.name());
					continue;
				}
			}

			initTools = true;
		}
	}
}
