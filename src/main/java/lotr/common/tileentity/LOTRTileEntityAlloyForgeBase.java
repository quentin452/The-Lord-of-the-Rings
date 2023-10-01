package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public abstract class LOTRTileEntityAlloyForgeBase extends LOTRTileEntityForgeBase {
	@Override
	public boolean canDoSmelting() {
		for (int i = 4; i < 8; ++i) {
			if (!canSmelt(i)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canMachineInsertInput(ItemStack itemstack) {
		return itemstack != null && getSmeltingResult(itemstack) != null;
	}

	public boolean canSmelt(int i) {
		int resultSize;
		ItemStack alloyResult;
		ItemStack result;
		if (inventory[i] == null) {
			return false;
		}
		if (inventory[i - 4] != null && (alloyResult = getAlloySmeltingResult(inventory[i], inventory[i - 4])) != null) {
			if (inventory[i + 4] == null) {
				return true;
			}
			resultSize = inventory[i + 4].stackSize + alloyResult.stackSize;
			if (inventory[i + 4].isItemEqual(alloyResult) && resultSize <= getInventoryStackLimit() && resultSize <= alloyResult.getMaxStackSize()) {
				return true;
			}
		}
		result = getSmeltingResult(inventory[i]);
		if (result == null) {
			return false;
		}
		if (inventory[i + 4] == null) {
			return true;
		}
		if (!inventory[i + 4].isItemEqual(result)) {
			return false;
		}
		resultSize = inventory[i + 4].stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
	}

	@Override
	public void doSmelt() {
		for (int i = 4; i < 8; ++i) {
			smeltItemInSlot(i);
		}
	}

	public ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
		if (isCopper(itemstack) && isTin(alloyItem) || isTin(itemstack) && isCopper(alloyItem)) {
			return new ItemStack(LOTRMod.bronze, 2);
		}
		return null;
	}

	@Override
	public int getForgeInvSize() {
		return 13;
	}

	@Override
	public int getSmeltingDuration() {
		return 200;
	}

	public ItemStack getSmeltingResult(ItemStack itemstack) {
		boolean isStoneMaterial = false;
		Item item = itemstack.getItem();
		Block block = Block.getBlockFromItem(item);
		if (block != null && block != Blocks.air) {
			Material material = block.getMaterial();
			if (material == Material.rock || material == Material.sand || material == Material.clay) {
				isStoneMaterial = true;
			}
		} else if (item == Items.clay_ball || item == LOTRMod.redClayBall || item == LOTRMod.clayMug || item == LOTRMod.clayPlate || item == LOTRMod.ceramicPlate) {
			isStoneMaterial = true;
		}
		if (isStoneMaterial || isWood(itemstack)) {
			return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
		}
		return null;
	}

	public boolean isCoal(ItemStack itemstack) {
		return itemstack.getItem() == Items.coal;
	}

	public boolean isCopper(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "oreCopper") || LOTRMod.isOreNameEqual(itemstack, "ingotCopper");
	}

	public boolean isGold(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "oreGold") || LOTRMod.isOreNameEqual(itemstack, "ingotGold");
	}

	public boolean isGoldNugget(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "nuggetGold");
	}

	public boolean isIron(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "oreIron") || LOTRMod.isOreNameEqual(itemstack, "ingotIron");
	}

	public boolean isMithril(ItemStack itemstack) {
		return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMithril) || itemstack.getItem() == LOTRMod.mithril;
	}

	public boolean isMithrilNugget(ItemStack itemstack) {
		return itemstack.getItem() == LOTRMod.mithrilNugget;
	}

	public boolean isOrcSteel(ItemStack itemstack) {
		return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMorgulIron) || itemstack.getItem() == LOTRMod.orcSteel;
	}

	public boolean isSilver(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "oreSilver") || LOTRMod.isOreNameEqual(itemstack, "ingotSilver");
	}

	public boolean isSilverNugget(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "nuggetSilver");
	}

	public boolean isTin(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "oreTin") || LOTRMod.isOreNameEqual(itemstack, "ingotTin");
	}

	public boolean isWood(ItemStack itemstack) {
		return LOTRMod.isOreNameEqual(itemstack, "logWood");
	}

	@Override
	public void setupForgeSlots() {
		inputSlots = new int[]{4, 5, 6, 7};
		outputSlots = new int[]{8, 9, 10, 11};
		fuelSlot = 12;
	}

	public void smeltItemInSlot(int i) {
		if (canSmelt(i)) {
			ItemStack alloyResult;
			boolean smeltedAlloyItem = false;
			if (inventory[i - 4] != null && (alloyResult = getAlloySmeltingResult(inventory[i], inventory[i - 4])) != null && (inventory[i + 4] == null || inventory[i + 4].isItemEqual(alloyResult))) {
				if (inventory[i + 4] == null) {
					inventory[i + 4] = alloyResult.copy();
				} else if (inventory[i + 4].isItemEqual(alloyResult)) {
					inventory[i + 4].stackSize += alloyResult.stackSize;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize <= 0) {
					inventory[i] = null;
				}
				--inventory[i - 4].stackSize;
				if (inventory[i - 4].stackSize <= 0) {
					inventory[i - 4] = null;
				}
				smeltedAlloyItem = true;
			}
			if (!smeltedAlloyItem) {
				ItemStack result = getSmeltingResult(inventory[i]);
				if (inventory[i + 4] == null) {
					inventory[i + 4] = result.copy();
				} else if (inventory[i + 4].isItemEqual(result)) {
					inventory[i + 4].stackSize += result.stackSize;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize <= 0) {
					inventory[i] = null;
				}
			}
		}
	}
}
