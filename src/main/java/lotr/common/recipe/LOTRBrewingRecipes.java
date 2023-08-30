package lotr.common.recipe;

import java.util.ArrayList;
import java.util.Collection;

import lotr.common.LOTRMod;
import net.minecraft.init.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRBrewingRecipes {
	public static Collection<ShapelessOreRecipe> recipes = new ArrayList<>();
	public static int BARREL_CAPACITY = 16;

	public static void addBrewingRecipe(ItemStack result, Object... ingredients) {
		if (ingredients.length != 6) {
			throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
		}
		recipes.add(new ShapelessOreRecipe(result, ingredients));
	}

	public static void createBrewingRecipes() {
		addBrewingRecipe(new ItemStack(LOTRMod.mugAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
		addBrewingRecipe(new ItemStack(LOTRMod.mugMiruvor, BARREL_CAPACITY), LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.elanor, LOTRMod.niphredil, Items.sugar);
		addBrewingRecipe(new ItemStack(LOTRMod.mugOrcDraught, BARREL_CAPACITY), LOTRMod.morgulShroom, LOTRMod.morgulShroom, LOTRMod.morgulShroom, "bone", "bone", "bone");
		addBrewingRecipe(new ItemStack(LOTRMod.mugMead, BARREL_CAPACITY), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
		addBrewingRecipe(new ItemStack(LOTRMod.mugCider, BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
		addBrewingRecipe(new ItemStack(LOTRMod.mugPerry, BARREL_CAPACITY), LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear);
		addBrewingRecipe(new ItemStack(LOTRMod.mugCherryLiqueur, BARREL_CAPACITY), LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry);
		addBrewingRecipe(new ItemStack(LOTRMod.mugRum, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
		addBrewingRecipe(new ItemStack(LOTRMod.mugAthelasBrew, BARREL_CAPACITY), LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas);
		addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenTonic, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb, LOTRMod.mithrilNugget);
		addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb);
		addBrewingRecipe(new ItemStack(LOTRMod.mugVodka, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
		addBrewingRecipe(new ItemStack(LOTRMod.mugMapleBeer, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.mapleSyrup, LOTRMod.mapleSyrup);
		addBrewingRecipe(new ItemStack(LOTRMod.mugAraq, BARREL_CAPACITY), LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date);
		addBrewingRecipe(new ItemStack(LOTRMod.mugCarrotWine, BARREL_CAPACITY), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
		addBrewingRecipe(new ItemStack(LOTRMod.mugBananaBeer, BARREL_CAPACITY), LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana);
		addBrewingRecipe(new ItemStack(LOTRMod.mugMelonLiqueur, BARREL_CAPACITY), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
		addBrewingRecipe(new ItemStack(LOTRMod.mugCactusLiqueur, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
		addBrewingRecipe(new ItemStack(LOTRMod.mugTorogDraught, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.rotten_flesh, Items.rotten_flesh, Blocks.dirt, LOTRMod.rhinoHorn);
		addBrewingRecipe(new ItemStack(LOTRMod.mugLemonLiqueur, BARREL_CAPACITY), LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon);
		addBrewingRecipe(new ItemStack(LOTRMod.mugLimeLiqueur, BARREL_CAPACITY), LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime);
		addBrewingRecipe(new ItemStack(LOTRMod.mugCornLiquor, BARREL_CAPACITY), LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn);
		addBrewingRecipe(new ItemStack(LOTRMod.mugRedWine, BARREL_CAPACITY), LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed);
		addBrewingRecipe(new ItemStack(LOTRMod.mugWhiteWine, BARREL_CAPACITY), LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite);
		addBrewingRecipe(new ItemStack(LOTRMod.mugMorgulDraught, BARREL_CAPACITY), LOTRMod.morgulFlower, LOTRMod.morgulFlower, LOTRMod.morgulFlower, "bone", "bone", "bone");
		addBrewingRecipe(new ItemStack(LOTRMod.mugPlumKvass, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.plum, LOTRMod.plum, LOTRMod.plum);
		addBrewingRecipe(new ItemStack(LOTRMod.mugTermiteTequila, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, LOTRMod.termite);
		addBrewingRecipe(new ItemStack(LOTRMod.mugSourMilk, BARREL_CAPACITY), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
		addBrewingRecipe(new ItemStack(LOTRMod.mugPomegranateWine, BARREL_CAPACITY), LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate);
	}

	public static ItemStack findMatchingRecipe(IInventory barrel) {
		for (int i = 6; i < 9; ++i) {
			ItemStack itemstack = barrel.getStackInSlot(i);
			if (isWaterSource(itemstack)) {
				continue;
			}
			return null;
		}
		block1:
		for (ShapelessOreRecipe recipe : recipes) {
			Collection<Object> ingredients = new ArrayList<>(recipe.getInput());
			for (int i = 0; i < 6; ++i) {
				ItemStack itemstack = barrel.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				boolean inRecipe = false;
				for (Object next : ingredients) {
					boolean match = false;
					if (next instanceof ItemStack) {
						match = LOTRRecipes.checkItemEquals((ItemStack) next, itemstack);
					} else if (next instanceof ArrayList) {
						for (ItemStack item : (Iterable<ItemStack>) next) {
							match = match || LOTRRecipes.checkItemEquals(item, itemstack);
						}
					}
					if (!match) {
						continue;
					}
					inRecipe = true;
					ingredients.remove(next);
					break;
				}
				if (!inRecipe) {
					continue block1;
				}
			}
			if (!ingredients.isEmpty()) {
				continue;
			}
			return recipe.getRecipeOutput().copy();
		}
		return null;
	}

	public static boolean isWaterSource(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.water_bucket;
	}
}
