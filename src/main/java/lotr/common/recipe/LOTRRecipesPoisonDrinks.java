package lotr.common.recipe;

import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class LOTRRecipesPoisonDrinks implements IRecipe {
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		EntityPlayer craftingPlayer;
		ItemStack result;
		block12:
		{
			ItemStack drink = null;
			ItemStack poison = null;
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				if (LOTRPoisonedDrinks.canPoison(itemstack)) {
					if (drink != null) {
						return null;
					}
					drink = itemstack.copy();
					continue;
				}
				if (LOTRMod.isOreNameEqual(itemstack, "poison")) {
					if (poison != null) {
						return null;
					}
					poison = itemstack.copy();
					continue;
				}
				return null;
			}
			if (drink == null || poison == null) {
				return null;
			}
			result = drink.copy();
			LOTRPoisonedDrinks.setDrinkPoisoned(result, true);
			craftingPlayer = null;
			try {
				Container cwb = null;
				for (Field f : inv.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					Object obj = f.get(inv);
					if (!(obj instanceof Container)) {
						continue;
					}
					cwb = (Container) obj;
					break;
				}
				if (cwb == null) {
					break block12;
				}
				for (Object obj : cwb.inventorySlots) {
					Slot slot = (Slot) obj;
					IInventory slotInv = slot.inventory;
					if (!(slotInv instanceof InventoryPlayer)) {
						continue;
					}
					InventoryPlayer playerInv = (InventoryPlayer) slotInv;
					craftingPlayer = playerInv.player;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (craftingPlayer != null) {
			LOTRPoisonedDrinks.setPoisonerPlayer(result, craftingPlayer);
		} else {
			FMLLog.bigWarning("LOTR Warning! Poisoned drink was crafted, player could not be found!");
		}
		return result;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		ItemStack drink = null;
		ItemStack poison = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (LOTRPoisonedDrinks.canPoison(itemstack)) {
				if (drink != null) {
					return false;
				}
				drink = itemstack;
				continue;
			}
			if (LOTRMod.isOreNameEqual(itemstack, "poison")) {
				if (poison != null) {
					return false;
				}
				poison = itemstack;
				continue;
			}
			return false;
		}
		return drink != null && poison != null;
	}
}
