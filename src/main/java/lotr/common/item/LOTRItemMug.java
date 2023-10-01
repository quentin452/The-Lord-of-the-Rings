package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRDrinkIcons;
import lotr.common.*;
import lotr.common.block.LOTRBlockMug;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LOTRItemMug extends Item {
	public static String[] strengthNames = {"weak", "light", "moderate", "strong", "potent"};
	public static float[] strengths = {0.25f, 0.5f, 1.0f, 2.0f, 3.0f};
	public static float[] foodStrengths = {0.5f, 0.75f, 1.0f, 1.25f, 1.5f};
	public static int vesselMeta = 100;
	@SideOnly(Side.CLIENT)
	public static IIcon barrelGui_emptyBucketSlotIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon barrelGui_emptyMugSlotIcon;
	@SideOnly(Side.CLIENT)
	public IIcon[] drinkIcons;
	@SideOnly(Side.CLIENT)
	public IIcon liquidIcon;
	public boolean isFullMug;
	public boolean isFoodDrink;
	public boolean isBrewable;
	public float alcoholicity;
	public int foodHealAmount;
	public float foodSaturationAmount;
	public Collection<PotionEffect> potionEffects = new ArrayList<>();
	public int damageAmount;
	public boolean curesEffects;

	public LOTRItemMug(boolean full, boolean food) {
		this(full, food, false, 0.0f);
	}

	public LOTRItemMug(boolean full, boolean food, boolean brew, float alc) {
		if (full) {
			setMaxStackSize(1);
			setHasSubtypes(true);
			setMaxDamage(0);
		} else {
			setMaxStackSize(64);
		}
		setCreativeTab(LOTRCreativeTabs.tabFood);
		isFullMug = full;
		isFoodDrink = food;
		isBrewable = brew;
		alcoholicity = alc;
	}

	public LOTRItemMug(float alc) {
		this(true, false, true, alc);
	}

	public static void addPotionEffectsToTooltip(ItemStack itemstack, EntityPlayer entityplayer, Collection list, boolean flag, Collection itemEffects) {
		if (!itemEffects.isEmpty()) {
			ItemStack potionEquivalent = new ItemStack(Items.potionitem);
			potionEquivalent.setItemDamage(69);
			NBTTagList effectsData = new NBTTagList();
			for (Object itemEffect : itemEffects) {
				PotionEffect effect = (PotionEffect) itemEffect;
				NBTTagCompound nbt = new NBTTagCompound();
				effect.writeCustomPotionEffectToNBT(nbt);
				effectsData.appendTag(nbt);
			}
			potionEquivalent.setTagCompound(new NBTTagCompound());
			potionEquivalent.getTagCompound().setTag("CustomPotionEffects", effectsData);
			List effectTooltips = new ArrayList();
			potionEquivalent.getItem().addInformation(potionEquivalent, entityplayer, effectTooltips, flag);
			list.addAll(effectTooltips);
		}
	}

	public static ItemStack getEquivalentDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof LOTRItemMug) {
				return itemstack;
			}
			if (item == Items.potionitem && itemstack.getItemDamage() == 0) {
				ItemStack water = itemstack.copy();
				water.func_150996_a(LOTRMod.mugWater);
				setVessel(water, Vessel.BOTTLE, false);
				return water;
			}
		}
		return itemstack;
	}

	public static float getFoodStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return foodStrengths[i];
		}
		return 1.0f;
	}

	public static ItemStack getRealDrink(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() == LOTRMod.mugWater && getVessel(itemstack) == Vessel.BOTTLE) {
			ItemStack water = itemstack.copy();
			water.func_150996_a(Items.potionitem);
			water.setItemDamage(0);
			return water;
		}
		return itemstack;
	}

	public static float getStrength(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return strengths[i];
		}
		return 1.0f;
	}

	public static int getStrengthMeta(int damage) {
		int i = damage % vesselMeta;
		if (i < 0 || i >= strengths.length) {
			i = 0;
		}
		return i;
	}

	public static int getStrengthMeta(ItemStack itemstack) {
		return getStrengthMeta(itemstack.getItemDamage());
	}

	public static String getStrengthSubtitle(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
			int i = getStrengthMeta(itemstack);
			return StatCollector.translateToLocal("item.lotr.drink." + strengthNames[i]);
		}
		return null;
	}

	public static Vessel getVessel(int damage) {
		int i = damage / vesselMeta;
		return Vessel.forMeta(i);
	}

	public static Vessel getVessel(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemMug) {
			LOTRItemMug itemMug = (LOTRItemMug) item;
			if (itemMug.isFullMug) {
				return getVessel(itemstack.getItemDamage());
			}
			return itemMug.getEmptyVesselType();
		}
		if (item == Items.glass_bottle || item == Items.potionitem && itemstack.getItemDamage() == 0) {
			return Vessel.BOTTLE;
		}
		return null;
	}

	public static boolean isItemEmptyDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof LOTRItemMug) {
				return !((LOTRItemMug) item).isFullMug;
			}
			return item == Items.glass_bottle;
		}
		return false;
	}

	public static boolean isItemFullDrink(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof LOTRItemMug) {
				return ((LOTRItemMug) item).isFullMug;
			}
			return item == Items.potionitem && itemstack.getItemDamage() == 0;
		}
		return false;
	}

	public static void setStrengthMeta(ItemStack itemstack, int i) {
		Vessel v = getVessel(itemstack);
		itemstack.setItemDamage(i);
		setVessel(itemstack, v, true);
	}

	public static void setVessel(ItemStack itemstack, Vessel v, boolean correctItem) {
		if (correctItem && itemstack.getItem() == Items.potionitem && itemstack.getItemDamage() == 0) {
			itemstack.func_150996_a(LOTRMod.mugWater);
			itemstack.setItemDamage(0);
		}
		int i = itemstack.getItemDamage();
		itemstack.setItemDamage(v.id * vesselMeta + i % vesselMeta);
		if (correctItem && itemstack.getItem() == LOTRMod.mugWater && v == Vessel.BOTTLE) {
			itemstack.func_150996_a(Items.potionitem);
			itemstack.setItemDamage(0);
		}
	}

	public static boolean tryPlaceMug(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side) {
		Vessel vessel = getVessel(itemstack);
		if (vessel == null || !vessel.canPlace) {
			return false;
		}
		Block mugBlock = vessel.getBlock();
		Block block = world.getBlock(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]);
		if (block != null && !block.isReplaceable(world, i, j, k) || block.getMaterial() == Material.water) {
			return false;
		}
		if (entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
			if (!mugBlock.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
			world.setBlock(i, j, k, mugBlock, l, 3);
			ItemStack mugFill = itemstack.copy();
			mugFill.stackSize = 1;
			LOTRBlockMug.setMugItem(world, i, j, k, mugFill, vessel);
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, mugBlock.stepSound.func_150496_b(), (mugBlock.stepSound.getVolume() + 1.0f) / 2.0f, mugBlock.stepSound.getPitch() * 0.8f);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isBrewable) {
			float strength = getStrength(itemstack);
			list.add(getStrengthSubtitle(itemstack));
			if (alcoholicity > 0.0f) {
				EnumChatFormatting c;
				float f = alcoholicity * strength * 10.0f;
				c = f < 2.0f ? EnumChatFormatting.GREEN : f < 5.0f ? EnumChatFormatting.YELLOW : f < 10.0f ? EnumChatFormatting.GOLD : f < 20.0f ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED;
				list.add(c + StatCollector.translateToLocal("item.lotr.drink.alcoholicity") + ": " + String.format("%.2f", f) + "%");
			}
			addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, convertPotionEffectsForStrength(strength));
		}
	}

	public LOTRItemMug addPotionEffect(int i, int j) {
		potionEffects.add(new PotionEffect(i, j * 20));
		return this;
	}

	public void applyToNPC(LOTREntityNPC npc, ItemStack itemstack) {
		float strength = getStrength(itemstack);
		npc.heal(foodHealAmount * strength);
		List<PotionEffect> effects = convertPotionEffectsForStrength(strength);
		for (PotionEffect effect : effects) {
			npc.addPotionEffect(effect);
		}
		if (damageAmount > 0) {
			npc.attackEntityFrom(DamageSource.magic, damageAmount * strength);
		}
		if (curesEffects) {
			npc.curePotionEffects(new ItemStack(Items.milk_bucket));
		}
	}

	public boolean canPlayerDrink(EntityPlayer entityplayer) {
		if (!isFullMug) {
			return false;
		}
		return !isFoodDrink || entityplayer.canEat(false);
	}

	public List<PotionEffect> convertPotionEffectsForStrength(float strength) {
		List<PotionEffect> list = new ArrayList<>();
		for (PotionEffect base : potionEffects) {
			PotionEffect modified = new PotionEffect(base.getPotionID(), (int) (base.getDuration() * strength));
			list.add(modified);
		}
		return list;
	}

	public Vessel getEmptyVesselType() {
		for (Vessel v : Vessel.values()) {
			if (v.getEmptyVesselItem() != this) {
				continue;
			}
			return v;
		}
		return Vessel.MUG;
	}

	@Override
	public IIcon getIconFromDamage(int i) {
		if (isFullMug) {
			if (i == -1) {
				return liquidIcon;
			}
			int vessel = getVessel(i).id;
			return drinkIcons[vessel];
		}
		return super.getIconFromDamage(i);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		if (LOTRMod.isAprilFools() && isFullMug) {
			return "Hooch";
		}
		if (itemstack.getItem() == LOTRMod.mugWater && getVessel(itemstack) == Vessel.BOTTLE) {
			return "\u00c2\u00a7cMY DUDE YOU DONE MESSED UP";
		}
		return super.getItemStackDisplayName(itemstack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.drink;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 32;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		if (isFullMug) {
			Vessel[] vesselTypes = {Vessel.MUG};
			if (tab == null || tab.hasSearchBar()) {
				vesselTypes = Vessel.values();
			}
			for (Vessel v : vesselTypes) {
				if (isBrewable) {
					for (int str = 0; str < strengths.length; ++str) {
						ItemStack drink = new ItemStack(item);
						setStrengthMeta(drink, str);
						setVessel(drink, v, true);
						if (drink.getItem() != item) {
							continue;
						}
						list.add(drink);
					}
					continue;
				}
				ItemStack drink = new ItemStack(item);
				setVessel(drink, v, true);
				if (drink.getItem() != item) {
					continue;
				}
				list.add(drink);
			}
		} else {
			super.getSubItems(item, tab, list);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		Vessel vessel = getVessel(itemstack);
		float strength = getStrength(itemstack);
		float foodStrength = getFoodStrength(itemstack);
		if (entityplayer.canEat(false)) {
			entityplayer.getFoodStats().addStats(Math.round(foodHealAmount * foodStrength), foodSaturationAmount * foodStrength);
		}
		if (alcoholicity > 0.0f) {
			int duration;
			float alcoholPower = alcoholicity * strength;
			int tolerance = LOTRLevelData.getData(entityplayer).getAlcoholTolerance();
			if (tolerance > 0) {
				float f = (float) Math.pow(0.99, tolerance);
				alcoholPower *= f;
			}
			if (!world.isRemote && itemRand.nextFloat() < alcoholPower && (duration = (int) (60.0f * (1.0f + itemRand.nextFloat() * 0.5f) * alcoholPower)) >= 1) {
				int durationTicks = duration * 20;
				entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, durationTicks));
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getDrunk);
				int toleranceAdd = Math.round(duration / 20.0f);
				LOTRLevelData.getData(entityplayer).setAlcoholTolerance(tolerance + toleranceAdd);
			}
		}
		if (!world.isRemote && shouldApplyPotionEffects(itemstack, entityplayer)) {
			List<PotionEffect> effects = convertPotionEffectsForStrength(strength);
			for (PotionEffect effect : effects) {
				entityplayer.addPotionEffect(effect);
			}
		}
		if (damageAmount > 0) {
			entityplayer.attackEntityFrom(DamageSource.magic, damageAmount * strength);
		}
		if (!world.isRemote && curesEffects) {
			entityplayer.curePotionEffects(new ItemStack(Items.milk_bucket));
		}
		if (!world.isRemote) {
			if (vessel == Vessel.SKULL) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkSkull);
			}
			if (this == LOTRMod.mugMangoJuice) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkMangoJuice);
			}
			if (this == LOTRMod.mugOrcDraught) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkOrcDraught);
			}
			if (this == LOTRMod.mugAthelasBrew) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkAthelasBrew);
				for (Potion potion : Potion.potionTypes) {
					if (potion == null || !LOTRReflection.isBadEffect(potion)) {
						continue;
					}
					entityplayer.removePotionEffect(potion.id);
				}
			}
			if (this == LOTRMod.mugRedWine || this == LOTRMod.mugWhiteWine) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkWine);
			}
			if (this == LOTRMod.mugDwarvenTonic) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkDwarvenTonic);
			}
		}
		return !entityplayer.capabilities.isCreativeMode ? new ItemStack(vessel.getEmptyVesselItem()) : itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!isFullMug) {
			ItemStack filled = new ItemStack(LOTRMod.mugWater);
			setVessel(filled, getEmptyVesselType(), true);
			MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);
			if (m == null) {
				return itemstack;
			}
			if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = m.blockX;
				int j = m.blockY;
				int k = m.blockZ;
				if (!world.canMineBlock(entityplayer, i, j, k) || !entityplayer.canPlayerEdit(i, j, k, m.sideHit, itemstack)) {
					return itemstack;
				}
				if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0) {
					--itemstack.stackSize;
					if (itemstack.stackSize <= 0) {
						world.playSoundAtEntity(entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
						return filled.copy();
					}
					if (!entityplayer.inventory.addItemStackToInventory(filled.copy())) {
						entityplayer.dropPlayerItemWithRandomChoice(filled.copy(), false);
					}
					world.playSoundAtEntity(entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
				}
			}
			return itemstack;
		}
		if (canPlayerDrink(entityplayer)) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		return tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
	}

	@Override
	public void registerIcons(IIconRegister iconregister) {
		if (isFullMug) {
			drinkIcons = new IIcon[Vessel.values().length];
			for (int i = 0; i < Vessel.values().length; ++i) {
				drinkIcons[i] = LOTRDrinkIcons.registerDrinkIcon(iconregister, this, getIconString(), Vessel.values()[i].name);
			}
			liquidIcon = LOTRDrinkIcons.registerLiquidIcon(iconregister, this, getIconString());
			barrelGui_emptyBucketSlotIcon = iconregister.registerIcon("lotr:barrel_emptyBucketSlot");
			barrelGui_emptyMugSlotIcon = iconregister.registerIcon("lotr:barrel_emptyMugSlot");
		} else {
			super.registerIcons(iconregister);
		}
	}

	public LOTRItemMug setCuresEffects() {
		curesEffects = true;
		return this;
	}

	public LOTRItemMug setDamageAmount(int i) {
		damageAmount = i;
		return this;
	}

	public LOTRItemMug setDrinkStats(int i, float f) {
		foodHealAmount = i;
		foodSaturationAmount = f;
		return this;
	}

	public boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
		return true;
	}

	public enum Vessel {
		MUG(0, "mug", true, 0), MUG_CLAY(1, "clay", true, 1), GOBLET_GOLD(2, "gobletGold", true, 10), GOBLET_SILVER(3, "gobletSilver", true, 8), GOBLET_COPPER(4, "gobletCopper", true, 5), GOBLET_WOOD(5, "gobletWood", true, 0), SKULL(6, "skull", true, 3), GLASS(7, "glass", true, 3), BOTTLE(8, "bottle", true, 2), SKIN(9, "skin", false, 0), HORN(10, "horn", true, 5), HORN_GOLD(11, "hornGold", true, 8);

		public String name;
		public int id;
		public boolean canPlace;
		public int extraPrice;

		Vessel(int i, String s, boolean flag, int p) {
			id = i;
			name = s;
			canPlace = flag;
			extraPrice = p;
		}

		public static Vessel forMeta(int i) {
			for (Vessel v : values()) {
				if (v.id != i) {
					continue;
				}
				return v;
			}
			return MUG;
		}

		public Block getBlock() {
			if (this == MUG) {
				return LOTRMod.mugBlock;
			}
			if (this == MUG_CLAY) {
				return LOTRMod.ceramicMugBlock;
			}
			if (this == GOBLET_GOLD) {
				return LOTRMod.gobletGoldBlock;
			}
			if (this == GOBLET_SILVER) {
				return LOTRMod.gobletSilverBlock;
			}
			if (this == GOBLET_COPPER) {
				return LOTRMod.gobletCopperBlock;
			}
			if (this == GOBLET_WOOD) {
				return LOTRMod.gobletWoodBlock;
			}
			if (this == SKULL) {
				return LOTRMod.skullCupBlock;
			}
			if (this == GLASS) {
				return LOTRMod.wineGlassBlock;
			}
			if (this == BOTTLE) {
				return LOTRMod.glassBottleBlock;
			}
			if (this == SKIN) {
				return null;
			}
			if (this == HORN) {
				return LOTRMod.aleHornBlock;
			}
			if (this == HORN_GOLD) {
				return LOTRMod.aleHornGoldBlock;
			}
			return LOTRMod.mugBlock;
		}

		public ItemStack getEmptyVessel() {
			return new ItemStack(getEmptyVesselItem());
		}

		public Item getEmptyVesselItem() {
			if (this == MUG) {
				return LOTRMod.mug;
			}
			if (this == MUG_CLAY) {
				return LOTRMod.ceramicMug;
			}
			if (this == GOBLET_GOLD) {
				return LOTRMod.gobletGold;
			}
			if (this == GOBLET_SILVER) {
				return LOTRMod.gobletSilver;
			}
			if (this == GOBLET_COPPER) {
				return LOTRMod.gobletCopper;
			}
			if (this == GOBLET_WOOD) {
				return LOTRMod.gobletWood;
			}
			if (this == SKULL) {
				return LOTRMod.skullCup;
			}
			if (this == GLASS) {
				return LOTRMod.wineGlass;
			}
			if (this == BOTTLE) {
				return Items.glass_bottle;
			}
			if (this == SKIN) {
				return LOTRMod.waterskin;
			}
			if (this == HORN) {
				return LOTRMod.aleHorn;
			}
			if (this == HORN_GOLD) {
				return LOTRMod.aleHornGold;
			}
			return LOTRMod.mug;
		}
	}

}
