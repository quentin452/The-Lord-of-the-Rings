package lotr.common.inventory;

import java.util.*;

import net.minecraft.command.ICommandSender;
import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import lotr.common.entity.npc.*;
import lotr.common.item.*;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRContainerAnvil extends Container {
	public static int maxReforgeTime = 40;
	public IInventory invOutput;
	public IInventory invInput;
	public EntityPlayer thePlayer;
	public World theWorld;
	public boolean isTrader;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public LOTREntityNPC theNPC;
	public LOTRTradeable theTrader;
	public int materialCost;
	public int reforgeCost;
	public int engraveOwnerCost;
	public String repairedItemName;
	public long lastReforgeTime = -1L;
	public int clientReforgeTime;
	public boolean doneMischief;
	public boolean isSmithScrollCombine;

	public LOTRContainerAnvil(EntityPlayer entityplayer, boolean trader) {
		thePlayer = entityplayer;
		theWorld = entityplayer.worldObj;
		isTrader = trader;
		invOutput = new InventoryCraftResult();
		invInput = new InventoryBasic("Repair", true, isTrader ? 2 : 3) {

			@Override
			public void markDirty() {
				super.markDirty();
				onCraftMatrixChanged(this);
			}
		};
		addSlotToContainer(new Slot(invInput, 0, 27, 58));
		addSlotToContainer(new Slot(invInput, 1, 76, 47));
		if (!isTrader) {
			addSlotToContainer(new Slot(invInput, 2, 76, 70));
		}
		addSlotToContainer(new LOTRSlotAnvilOutput(this, invOutput, 0, 134, 58));
		for (int j1 = 0; j1 < 3; ++j1) {
			for (int i1 = 0; i1 < 9; ++i1) {
				addSlotToContainer(new Slot(entityplayer.inventory, i1 + j1 * 9 + 9, 8 + i1 * 18, 116 + j1 * 18));
			}
		}
		for (int i1 = 0; i1 < 9; ++i1) {
			addSlotToContainer(new Slot(entityplayer.inventory, i1, 8 + i1 * 18, 174));
		}
	}

	public LOTRContainerAnvil(EntityPlayer entityplayer, int i, int j, int k) {
		this(entityplayer, false);
		xCoord = i;
		yCoord = j;
		zCoord = k;
	}

	public LOTRContainerAnvil(EntityPlayer entityplayer, LOTREntityNPC npc) {
		this(entityplayer, true);
		theNPC = npc;
		theTrader = (LOTRTradeable) npc;
	}

	public boolean applyMischief(ItemStack itemstack) {
		boolean changed = false;
		Random rand = theWorld.rand;
		if (rand.nextFloat() < 0.8f) {
			String name = itemstack.getDisplayName();
			if ((name = OddmentCollectorNameMischief.garbleName(name, rand)).equals(itemstack.getItem().getItemStackDisplayName(itemstack))) {
				itemstack.func_135074_t();
			} else {
				itemstack.setStackDisplayName(name);
			}
			changed = true;
		}
		if (rand.nextFloat() < 0.2f) {
			LOTREnchantmentHelper.applyRandomEnchantments(itemstack, rand, false, true);
			changed = true;
		}
		return changed;
	}

	public boolean canEngraveNewOwner(ItemStack itemstack, ICommandSender entityplayer) {
		String currentOwner = LOTRItemOwnership.getCurrentOwner(itemstack);
		if (currentOwner == null) {
			return true;
		}
		return !currentOwner.equals(entityplayer.getCommandSenderName());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (isTrader) {
			return theNPC != null && entityplayer.getDistanceToEntity(theNPC) <= 12.0 && theNPC.isEntityAlive() && theNPC.getAttackTarget() == null && theTrader.canTradeWith(entityplayer);
		}
		return theWorld.getBlock(xCoord, yCoord, zCoord) == Blocks.anvil && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			crafting.sendProgressBarUpdate(this, 0, materialCost);
			crafting.sendProgressBarUpdate(this, 1, reforgeCost);
			crafting.sendProgressBarUpdate(this, 3, engraveOwnerCost);
		}
	}

	public void engraveOwnership() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		if (inputItem != null && engraveOwnerCost > 0 && hasMaterialOrCoinAmount(engraveOwnerCost)) {
			int cost = engraveOwnerCost;
			LOTRItemOwnership.setCurrentOwner(inputItem, thePlayer.getCommandSenderName());
			if (isTrader && theNPC instanceof LOTREntityScrapTrader && applyMischief(inputItem)) {
				doneMischief = true;
			}
			invInput.setInventorySlotContents(0, inputItem);
			takeMaterialOrCoinAmount(cost);
			playAnvilSound();
			LOTRLevelData.getData(thePlayer).addAchievement(LOTRAchievement.engraveOwnership);
		}
	}

	public List<EnumChatFormatting> getActiveItemNameFormatting() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		ItemStack resultItem = invOutput.getStackInSlot(0);
		if (resultItem != null) {
			return getAppliedFormattingCodes(resultItem.getDisplayName());
		}
		if (inputItem != null) {
			return getAppliedFormattingCodes(inputItem.getDisplayName());
		}
		return new ArrayList<>();
	}

	public float getTraderMaterialPrice(ItemStack inputItem) {
		float materialPrice = 0.0f;
		LOTRTradeEntry[] sellTrades = theNPC.traderNPCInfo.getSellTrades();
		if (sellTrades != null) {
			for (LOTRTradeEntry trade : sellTrades) {
				ItemStack tradeItem = trade.createTradeItem();
				if (!isRepairMaterial(inputItem, tradeItem)) {
					continue;
				}
				materialPrice = (float) trade.getCost() / trade.createTradeItem().stackSize;
				break;
			}
		}
		if (materialPrice <= 0.0f) {
			LOTRTradeEntries sellPool = theTrader.getSellPool();
			for (LOTRTradeEntry trade : sellPool.tradeEntries) {
				ItemStack tradeItem = trade.createTradeItem();
				if (!isRepairMaterial(inputItem, tradeItem)) {
					continue;
				}
				materialPrice = (float) trade.getCost() / trade.createTradeItem().stackSize;
				break;
			}
		}
		if (materialPrice <= 0.0f && (isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithril)) || isRepairMaterial(inputItem, new ItemStack(LOTRMod.mithrilMail))) && theTrader instanceof LOTREntityDwarf) {
			materialPrice = 200.0f;
		}
		return materialPrice;
	}

	public boolean hasMaterialOrCoinAmount(int cost) {
		if (isTrader) {
			return LOTRItemCoin.getInventoryValue(thePlayer, false) >= cost;
		}
		ItemStack inputItem = invInput.getStackInSlot(0);
		ItemStack materialItem = invInput.getStackInSlot(2);
		if (materialItem != null) {
			return isRepairMaterial(inputItem, materialItem) && materialItem.stackSize >= cost;
		}
		return false;
	}

	public boolean isRepairMaterial(ItemStack inputItem, ItemStack materialItem) {
		if (inputItem.getItem().getIsRepairable(inputItem, materialItem)) {
			return true;
		}
		Item item = inputItem.getItem();
		if (item == Items.bow && LOTRMod.rohanBow.getIsRepairable(inputItem, materialItem) || item instanceof ItemFishingRod && materialItem.getItem() == Items.string) {
			return true;
		}
		if (item instanceof ItemShears && materialItem.getItem() == Items.iron_ingot || item instanceof LOTRItemChisel && materialItem.getItem() == Items.iron_ingot) {
			return true;
		}
		if (item instanceof ItemEnchantedBook && materialItem.getItem() == Items.paper) {
			return true;
		}
		Item.ToolMaterial material = null;
		if (item instanceof ItemTool) {
			material = Item.ToolMaterial.valueOf(((ItemTool) item).getToolMaterialName());
		} else if (item instanceof ItemSword) {
			material = Item.ToolMaterial.valueOf(((ItemSword) item).getToolMaterialName());
		}
		if (material == Item.ToolMaterial.WOOD || material == LOTRMaterial.MOREDAIN_WOOD.toToolMaterial()) {
			return LOTRMod.isOreNameEqual(materialItem, "plankWood");
		}
		if (material == LOTRMaterial.MALLORN.toToolMaterial()) {
			return materialItem.getItem() == Item.getItemFromBlock(LOTRMod.planks) && materialItem.getItemDamage() == 1;
		}
		if (material == LOTRMaterial.MALLORN_MACE.toToolMaterial()) {
			return materialItem.getItem() == Item.getItemFromBlock(LOTRMod.wood) && materialItem.getItemDamage() == 1;
		}
		if (item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial() == LOTRMaterial.BONE.toArmorMaterial()) {
			return LOTRMod.isOreNameEqual(materialItem, "bone");
		}
		return false;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theWorld.isRemote) {
			for (int i = 0; i < invInput.getSizeInventory(); ++i) {
				ItemStack itemstack = invInput.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
			if (doneMischief && isTrader && theNPC instanceof LOTREntityScrapTrader) {
				theNPC.sendSpeechBank(entityplayer, ((LOTREntityScrapTrader) theNPC).getSmithSpeechBank());
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		super.onCraftMatrixChanged(inv);
		if (inv == invInput) {
			updateRepairOutput();
		}
	}

	public void playAnvilSound() {
		if (!theWorld.isRemote) {
			int i;
			int j;
			int k;
			if (isTrader) {
				i = MathHelper.floor_double(theNPC.posX);
				j = MathHelper.floor_double(theNPC.posY);
				k = MathHelper.floor_double(theNPC.posZ);
			} else {
				i = xCoord;
				j = yCoord;
				k = zCoord;
			}
			theWorld.playAuxSFX(1021, i, j, k, 0);
		}
	}

	public void reforgeItem() {
		ItemStack inputItem;
		long curTime = System.currentTimeMillis();
		if ((lastReforgeTime < 0L || curTime - lastReforgeTime >= 2000L) && (inputItem = invInput.getStackInSlot(0)) != null && reforgeCost > 0 && hasMaterialOrCoinAmount(reforgeCost)) {
			int cost = reforgeCost;
			if (inputItem.isItemStackDamageable()) {
				inputItem.setItemDamage(0);
			}
			LOTREnchantmentHelper.applyRandomEnchantments(inputItem, theWorld.rand, true, true);
			LOTREnchantmentHelper.setAnvilCost(inputItem, 0);
			if (isTrader && theNPC instanceof LOTREntityScrapTrader && applyMischief(inputItem)) {
				doneMischief = true;
			}
			invInput.setInventorySlotContents(0, inputItem);
			takeMaterialOrCoinAmount(cost);
			playAnvilSound();
			lastReforgeTime = curTime;
			((ICrafting) thePlayer).sendProgressBarUpdate(this, 2, 0);
			if (!isTrader) {
				LOTRLevelData.getData(thePlayer).addAchievement(LOTRAchievement.reforge);
			}
		}
	}

	@Override
	public ItemStack slotClick(int slotNo, int j, int k, EntityPlayer entityplayer) {
		ItemStack resultCopy;
		ItemStack resultItem = invOutput.getStackInSlot(0);
		resultItem = ItemStack.copyItemStack(resultItem);
		boolean changed = false;
		if (resultItem != null && slotNo == getSlotFromInventory(invOutput, 0).slotNumber && !theWorld.isRemote && isTrader && theNPC instanceof LOTREntityScrapTrader && (changed = applyMischief(resultCopy = resultItem.copy()))) {
			invOutput.setInventorySlotContents(0, resultCopy);
		}
		ItemStack slotClickResult = super.slotClick(slotNo, j, k, entityplayer);
		if (changed) {
			doneMischief = true;
			if (invOutput.getStackInSlot(0) != null) {
				invOutput.setInventorySlotContents(0, resultItem.copy());
			}
		}
		return slotClickResult;
	}

	public void takeMaterialOrCoinAmount(int cost) {
		if (isTrader) {
			if (!theWorld.isRemote) {
				LOTRItemCoin.takeCoins(cost, thePlayer);
				detectAndSendChanges();
				theNPC.playTradeSound();
			}
		} else {
			ItemStack materialItem = invInput.getStackInSlot(2);
			if (materialItem != null) {
				materialItem.stackSize -= cost;
				if (materialItem.stackSize <= 0) {
					invInput.setInventorySlotContents(2, null);
				} else {
					invInput.setInventorySlotContents(2, materialItem);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int inputSize = invInput.getSizeInventory();
			if (i == inputSize) {
				if (!mergeItemStack(itemstack1, inputSize + 1, inputSize + 37, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i >= inputSize + 1 ? i >= inputSize + 1 && i < inputSize + 37 && !mergeItemStack(itemstack1, 0, inputSize, false) : !mergeItemStack(itemstack1, inputSize + 1, inputSize + 37, false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(entityplayer, itemstack1);
		}
		return itemstack;
	}

	public void updateItemName(String name) {
		List<EnumChatFormatting> colors = getAppliedFormattingCodes(name);
		name = stripFormattingCodes(name);
		repairedItemName = ChatAllowedCharacters.filerAllowedCharacters(name);
		ItemStack itemstack = invOutput.getStackInSlot(0);
		if (itemstack != null) {
			if (StringUtils.isBlank(repairedItemName)) {
				itemstack.func_135074_t();
			} else {
				itemstack.setStackDisplayName(repairedItemName);
			}
			if (!colors.isEmpty()) {
				itemstack.setStackDisplayName(applyFormattingCodes(itemstack.getDisplayName(), colors));
			}
		}
		updateRepairOutput();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			materialCost = j;
		}
		if (i == 1) {
			reforgeCost = j;
		}
		if (i == 2) {
			clientReforgeTime = 40;
		}
		if (i == 3) {
			engraveOwnerCost = j;
		}
	}

	public void updateRepairOutput() {
		ItemStack inputItem = invInput.getStackInSlot(0);
		materialCost = 0;
		reforgeCost = 0;
		engraveOwnerCost = 0;
		isSmithScrollCombine = false;
		int baseAnvilCost;
		int repairCost = 0;
		int combineCost = 0;
		int renameCost = 0;
		if (inputItem == null) {
			invOutput.setInventorySlotContents(0, null);
			materialCost = 0;
		} else {
			int oneItemRepair;
			LOTREnchantmentCombining.CombineRecipe scrollCombine;
			boolean repairing;
			ItemStack inputCopy = inputItem.copy();
			ItemStack combinerItem = invInput.getStackInSlot(1);
			ItemStack materialItem = isTrader ? null : invInput.getStackInSlot(2);
			Map inputEnchants = EnchantmentHelper.getEnchantments(inputCopy);
			boolean enchantingWithBook = false;
			List<LOTREnchantment> inputModifiers = LOTREnchantmentHelper.getEnchantList(inputCopy);
			baseAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem) + (combinerItem == null ? 0 : LOTREnchantmentHelper.getAnvilCost(combinerItem));
			materialCost = 0;
			String previousDisplayName = inputCopy.getDisplayName();
			String defaultItemName = inputCopy.getItem().getItemStackDisplayName(inputCopy);
			String formattedNameToApply = repairedItemName;
			Collection<EnumChatFormatting> colorsToApply = new ArrayList<>(getAppliedFormattingCodes(inputCopy.getDisplayName()));
			boolean alteringNameColor = false;
			if (costsToRename(inputItem) && combinerItem != null) {
				if (combinerItem.getItem() instanceof AnvilNameColorProvider) {
					boolean isDifferentColor;
					AnvilNameColorProvider nameColorProvider = (AnvilNameColorProvider) combinerItem.getItem();
					EnumChatFormatting newColor = nameColorProvider.getAnvilNameColor();
					isDifferentColor = !colorsToApply.contains(newColor);
					if (isDifferentColor) {
						for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
							if (!ecf.isColor()) {
								continue;
							}
							while (colorsToApply.contains(ecf)) {
								colorsToApply.remove(ecf);
							}
						}
						colorsToApply.add(newColor);
						alteringNameColor = true;
					}
				} else if (combinerItem.getItem() == Items.flint && !colorsToApply.isEmpty()) {
					colorsToApply.clear();
					alteringNameColor = true;
				}
				if (alteringNameColor) {
					++renameCost;
				}
			}
			if (!colorsToApply.isEmpty()) {
				if (StringUtils.isBlank(formattedNameToApply)) {
					formattedNameToApply = defaultItemName;
				}
				formattedNameToApply = applyFormattingCodes(formattedNameToApply, colorsToApply);
			}
			boolean nameChange = false;
			if (formattedNameToApply != null && !formattedNameToApply.equals(previousDisplayName)) {
				if (StringUtils.isBlank(formattedNameToApply) || formattedNameToApply.equals(defaultItemName)) {
					if (inputCopy.hasDisplayName()) {
						inputCopy.func_135074_t();
						if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
							nameChange = true;
						}
					}
				} else {
					inputCopy.setStackDisplayName(formattedNameToApply);
					if (!stripFormattingCodes(previousDisplayName).equals(stripFormattingCodes(formattedNameToApply))) {
						nameChange = true;
					}
				}
			}
			if (nameChange && costsToRename(inputItem)) {
				++renameCost;
			}
			if (isTrader && (scrollCombine = LOTREnchantmentCombining.getCombinationResult(inputItem, combinerItem)) != null) {
				invOutput.setInventorySlotContents(0, scrollCombine.createOutputItem());
				materialCost = scrollCombine.cost;
				reforgeCost = 0;
				engraveOwnerCost = 0;
				isSmithScrollCombine = true;
				return;
			}
			boolean combining = false;
			if (combinerItem != null) {
				enchantingWithBook = combinerItem.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(combinerItem).tagCount() > 0;
				if (enchantingWithBook && !LOTRConfig.enchantingVanilla) {
					invOutput.setInventorySlotContents(0, null);
					materialCost = 0;
					return;
				}
				LOTREnchantment combinerItemEnchant = null;
				if (combinerItem.getItem() instanceof LOTRItemEnchantment) {
					combinerItemEnchant = ((LOTRItemEnchantment) combinerItem.getItem()).theEnchant;
				} else if (combinerItem.getItem() instanceof LOTRItemModifierTemplate) {
					combinerItemEnchant = LOTRItemModifierTemplate.getModifier(combinerItem);
				}
				if (!enchantingWithBook && combinerItemEnchant == null) {
					if (inputCopy.isItemStackDamageable() && inputCopy.getItem() == combinerItem.getItem()) {
						int inputUseLeft = inputItem.getMaxDamage() - inputItem.getItemDamageForDisplay();
						int combinerUseLeft = combinerItem.getMaxDamage() - combinerItem.getItemDamageForDisplay();
						int restoredUses = combinerUseLeft + inputCopy.getMaxDamage() * 12 / 100;
						int newUsesLeft = inputUseLeft + restoredUses;
						int newDamage = inputCopy.getMaxDamage() - newUsesLeft;
						newDamage = Math.max(newDamage, 0);
						if (newDamage < inputCopy.getItemDamage()) {
							inputCopy.setItemDamage(newDamage);
							int restoredUses1 = inputCopy.getMaxDamage() - inputUseLeft;
							int restoredUses2 = inputCopy.getMaxDamage() - combinerUseLeft;
							combineCost += Math.max(0, Math.min(restoredUses1, restoredUses2) / 100);
						}
						combining = true;
					} else if (!alteringNameColor) {
						invOutput.setInventorySlotContents(0, null);
						materialCost = 0;
						return;
					}
				}
				HashMap outputEnchants = new HashMap(inputEnchants);
				if (LOTRConfig.enchantingVanilla) {
					Map combinerEnchants = EnchantmentHelper.getEnchantments(combinerItem);
					for (Object obj : combinerEnchants.keySet()) {
						int combinerEnchLevel;
						int combinerEnchID = (Integer) obj;
						Enchantment combinerEnch = Enchantment.enchantmentsList[combinerEnchID];
						int inputEnchLevel = 0;
						if (outputEnchants.containsKey(combinerEnchID)) {
							inputEnchLevel = (Integer) outputEnchants.get(combinerEnchID);
						}
						combinerEnchLevel = inputEnchLevel == (combinerEnchLevel = (Integer) combinerEnchants.get(combinerEnchID)) ? ++combinerEnchLevel : Math.max(combinerEnchLevel, inputEnchLevel);
						int levelsAdded = combinerEnchLevel - inputEnchLevel;
						boolean canApply = combinerEnch.canApply(inputItem);
						if (thePlayer.capabilities.isCreativeMode || inputItem.getItem() == Items.enchanted_book) {
							canApply = true;
						}
						for (Object objIn : outputEnchants.keySet()) {
							int inputEnchID = (Integer) objIn;
							Enchantment inputEnch = Enchantment.enchantmentsList[inputEnchID];
							if (inputEnchID == combinerEnchID || combinerEnch.canApplyTogether(inputEnch) && inputEnch.canApplyTogether(combinerEnch)) {
								continue;
							}
							canApply = false;
							combineCost += levelsAdded;
						}
						if (!canApply) {
							continue;
						}
						combinerEnchLevel = Math.min(combinerEnchLevel, combinerEnch.getMaxLevel());
						outputEnchants.put(combinerEnchID, combinerEnchLevel);
						int costPerLevel = 0;
						int enchWeight = combinerEnch.getWeight();
						switch (enchWeight) {
							case 1:
								costPerLevel = 8;
								break;
							case 2:
								costPerLevel = 4;
								break;
							case 5:
								costPerLevel = 2;
								break;
							case 10:
								costPerLevel = 1;
								break;
							default:
								break;
						}
						combineCost += costPerLevel * levelsAdded;
					}
				} else {
					outputEnchants.clear();
				}
				EnchantmentHelper.setEnchantments(outputEnchants, inputCopy);
				int maxMods = 3;
				Collection<LOTREnchantment> outputMods = new ArrayList<>(inputModifiers);
				List<LOTREnchantment> combinerMods = LOTREnchantmentHelper.getEnchantList(combinerItem);
				if (combinerItemEnchant != null) {
					Item item;
					combinerMods.add(combinerItemEnchant);
					if (combinerItemEnchant == LOTREnchantment.fire && LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item = inputCopy.getItem())) {
						Item unpoisoned = LOTRRecipePoisonWeapon.poisonedToInput.get(item);
						inputCopy.func_150996_a(unpoisoned);
					}
				}
				for (LOTREnchantment combinerMod : combinerMods) {
					boolean canApply = combinerMod.canApply(inputItem, false);
					if (canApply) {
						for (LOTREnchantment mod : outputMods) {
							if (mod.isCompatibleWith(combinerMod) && combinerMod.isCompatibleWith(mod)) {
								continue;
							}
							canApply = false;
						}
					}
					int numOutputMods = 0;
					for (LOTREnchantment mod : outputMods) {
						if (mod.bypassAnvilLimit()) {
							continue;
						}
						++numOutputMods;
					}
					if (!combinerMod.bypassAnvilLimit() && numOutputMods >= maxMods) {
						canApply = false;
					}
					if (!canApply) {
						continue;
					}
					outputMods.add(combinerMod);
					if (!combinerMod.isBeneficial()) {
						continue;
					}
					combineCost += Math.max(1, (int) combinerMod.getValueModifier());
				}
				LOTREnchantmentHelper.setEnchantList(inputCopy, outputMods);
			}
			if (combineCost > 0) {
				combining = true;
			}
			int numEnchants = 0;
			for (Object obj : inputEnchants.keySet()) {
				int enchID = (Integer) obj;
				Enchantment ench = Enchantment.enchantmentsList[enchID];
				int enchLevel = (Integer) inputEnchants.get(enchID);
				++numEnchants;
				int costPerLevel = 0;
				int enchWeight = ench.getWeight();
				switch (enchWeight) {
					case 1:
						costPerLevel = 8;
						break;
					case 2:
						costPerLevel = 4;
						break;
					case 5:
						costPerLevel = 2;
						break;
					case 10:
						costPerLevel = 1;
						break;
					default:
						break;
				}
				baseAnvilCost += numEnchants + enchLevel * costPerLevel;
			}
			if (enchantingWithBook && !inputCopy.getItem().isBookEnchantable(inputCopy, combinerItem)) {
				inputCopy = null;
			}
			for (LOTREnchantment mod : inputModifiers) {
				if (!mod.isBeneficial()) {
					continue;
				}
				baseAnvilCost += Math.max(1, (int) mod.getValueModifier());
			}
			if (inputCopy.isItemStackDamageable()) {
				boolean canRepair;
				int availableMaterials = 0;
				if (isTrader) {
					canRepair = getTraderMaterialPrice(inputItem) > 0.0f;
					availableMaterials = Integer.MAX_VALUE;
				} else {
					canRepair = materialItem != null && isRepairMaterial(inputItem, materialItem);
					if (materialItem != null) {
						availableMaterials = materialItem.stackSize - combineCost - renameCost;
					}
				}
				oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4);
				if (canRepair && availableMaterials > 0 && oneItemRepair > 0) {
					availableMaterials -= baseAnvilCost;
					if (availableMaterials > 0) {
						int usedMaterials;
						for (usedMaterials = 0; oneItemRepair > 0 && usedMaterials < availableMaterials; ++usedMaterials) {
							int newDamage = inputCopy.getItemDamageForDisplay() - oneItemRepair;
							inputCopy.setItemDamage(newDamage);
							oneItemRepair = Math.min(inputCopy.getItemDamageForDisplay(), inputCopy.getMaxDamage() / 4);
						}
						repairCost += usedMaterials;
					} else if (!nameChange && !combining) {
						repairCost = 1;
						int newDamage = inputCopy.getItemDamageForDisplay() - oneItemRepair;
						inputCopy.setItemDamage(newDamage);
					}
				}
			}
			repairing = repairCost > 0;
			if (combining || repairing) {
				materialCost = baseAnvilCost;
				materialCost += combineCost + repairCost;
			} else {
				materialCost = 0;
			}
			materialCost += renameCost;
			int nextAnvilCost = LOTREnchantmentHelper.getAnvilCost(inputItem);
			if (combinerItem != null) {
				int combinerAnvilCost = LOTREnchantmentHelper.getAnvilCost(combinerItem);
				nextAnvilCost = Math.max(nextAnvilCost, combinerAnvilCost);
			}
			if (combining) {
				nextAnvilCost += 2;
			} else if (repairing) {
				++nextAnvilCost;
			}
			nextAnvilCost = Math.max(nextAnvilCost, 0);
			if (nextAnvilCost > 0) {
				LOTREnchantmentHelper.setAnvilCost(inputCopy, nextAnvilCost);
			}
			if (LOTREnchantmentHelper.isReforgeable(inputItem)) {
				ItemStack reforgeCopy;
				reforgeCost = 2;
				if (inputItem.getItem() instanceof ItemArmor) {
					reforgeCost = 3;
				}
				if (inputItem.isItemStackDamageable() && (oneItemRepair = Math.min((reforgeCopy = inputItem.copy()).getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4)) > 0) {
					int usedMaterials = 0;
					while (oneItemRepair > 0) {
						int newDamage = reforgeCopy.getItemDamageForDisplay() - oneItemRepair;
						reforgeCopy.setItemDamage(newDamage);
						oneItemRepair = Math.min(reforgeCopy.getItemDamageForDisplay(), reforgeCopy.getMaxDamage() / 4);
						++usedMaterials;
					}
					reforgeCost += usedMaterials;
				}
				engraveOwnerCost = 2;
			} else {
				reforgeCost = 0;
				engraveOwnerCost = 0;
			}
			if (isRepairMaterial(inputItem, new ItemStack(Items.string))) {
				int stringFactor = 3;
				materialCost *= stringFactor;
				reforgeCost *= stringFactor;
				engraveOwnerCost *= stringFactor;
			}
			if (isTrader) {
				boolean isCommonRenameOnly = nameChange && materialCost == 0;
				float materialPrice = getTraderMaterialPrice(inputItem);
				if (materialPrice > 0.0f) {
					materialCost = Math.round(materialCost * materialPrice);
					materialCost = Math.max(materialCost, 1);
					reforgeCost = Math.round(reforgeCost * materialPrice);
					reforgeCost = Math.max(reforgeCost, 1);
					engraveOwnerCost = Math.round(engraveOwnerCost * materialPrice);
					engraveOwnerCost = Math.max(engraveOwnerCost, 1);
					if (theTrader instanceof LOTREntityScrapTrader) {
						materialCost = MathHelper.ceiling_float_int(materialCost * 0.5f);
						materialCost = Math.max(materialCost, 1);
						reforgeCost = MathHelper.ceiling_float_int(reforgeCost * 0.5f);
						reforgeCost = Math.max(reforgeCost, 1);
						engraveOwnerCost = MathHelper.ceiling_float_int(engraveOwnerCost * 0.5f);
						engraveOwnerCost = Math.max(engraveOwnerCost, 1);
					}
				} else if (!isCommonRenameOnly) {
					invOutput.setInventorySlotContents(0, null);
					materialCost = 0;
					reforgeCost = 0;
					engraveOwnerCost = 0;
					return;
				}
			}
			if (combining || repairing || nameChange || alteringNameColor) {
				invOutput.setInventorySlotContents(0, inputCopy);
			} else {
				invOutput.setInventorySlotContents(0, null);
				materialCost = 0;
			}
			detectAndSendChanges();
		}
	}

	public static String applyFormattingCodes(String name, Iterable<EnumChatFormatting> colors) {
		StringBuilder nameBuilder = new StringBuilder(name);
		for (EnumChatFormatting color : colors) {
			nameBuilder.insert(0, color);
		}
		name = nameBuilder.toString();
		return name;
	}

	public static boolean costsToRename(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemArmor && ((ItemArmor) item).damageReduceAmount > 0) {
			return true;
		}
		return item instanceof ItemBow || item instanceof LOTRItemThrowingAxe || item instanceof LOTRItemBlowgun;
	}

	public static List<EnumChatFormatting> getAppliedFormattingCodes(String name) {
		List<EnumChatFormatting> colors = new ArrayList<>();
		for (EnumChatFormatting color : EnumChatFormatting.values()) {
			String formatCode = color.toString();
			if (!name.startsWith(formatCode)) {
				continue;
			}
			colors.add(color);
		}
		return colors;
	}

	public static String stripFormattingCodes(String name) {
		for (EnumChatFormatting color : EnumChatFormatting.values()) {
			String formatCode = color.toString();
			if (!name.startsWith(formatCode)) {
				continue;
			}
			name = name.substring(formatCode.length());
		}
		return name;
	}

}
