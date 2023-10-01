package lotr.common.recipe;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LOTREntJarRecipes {
	public static Map<ItemStack, ItemStack> recipes = new HashMap<>();

	public static void addDraughtRecipe(ItemStack ingredient, ItemStack result) {
		recipes.put(ingredient, result);
	}

	public static void createDraughtRecipes() {
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 0), new ItemStack(LOTRMod.entDraught, 1, 0));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 1), new ItemStack(LOTRMod.entDraught, 1, 1));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 2), new ItemStack(LOTRMod.entDraught, 1, 2));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 3), new ItemStack(LOTRMod.entDraught, 1, 3));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 4), new ItemStack(LOTRMod.entDraught, 1, 4));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 5), new ItemStack(LOTRMod.entDraught, 1, 5));
		addDraughtRecipe(new ItemStack(LOTRMod.fangornRiverweed), new ItemStack(LOTRMod.entDraught, 1, 6));
	}

	public static ItemStack findMatchingRecipe(ItemStack input) {
		if (input == null) {
			return null;
		}
		for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet()) {
			if (!LOTRRecipes.checkItemEquals(entry.getKey(), input)) {
				continue;
			}
			return entry.getValue().copy();
		}
		return null;
	}
}
