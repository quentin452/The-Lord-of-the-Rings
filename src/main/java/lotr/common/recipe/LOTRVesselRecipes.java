package lotr.common.recipe;

import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRVesselRecipes {
	public static void addRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
		List<IRecipe> recipes = generateRecipes(result, drinkBase, ingredients);
		for (IRecipe r : recipes) {
			GameRegistry.addRecipe(r);
		}
	}

	public static void addRecipes(ItemStack result, Object[] ingredients) {
		addRecipes(result, null, ingredients);
	}

	public static List<IRecipe> generateRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
		List<IRecipe> recipes = new ArrayList<>();
		for (LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
			Collection<Object> vIngredients = new ArrayList<>();
			ItemStack vBase = v.getEmptyVessel();
			if (drinkBase != null) {
				vBase = new ItemStack(drinkBase);
				LOTRItemMug.setVessel(vBase, v, true);
			}
			vIngredients.add(vBase);
			vIngredients.addAll(Arrays.asList(ingredients));
			ItemStack vResult = result.copy();
			LOTRItemMug.setVessel(vResult, v, true);
			IRecipe recipe = new ShapelessOreRecipe(vResult, vIngredients.toArray());
			recipes.add(recipe);
		}
		return recipes;
	}

	public static List<IRecipe> generateRecipes(ItemStack result, Object[] ingredients) {
		return generateRecipes(result, null, ingredients);
	}
}
