package lotr.common;

import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LOTRFoods {
	public static LOTRFoods HOBBIT = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(Items.bread), new ItemStack(LOTRMod.cornBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.leek), new ItemStack(LOTRMod.leekSoup), new ItemStack(Items.mushroom_stew), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(Items.pumpkin_pie), new ItemStack(LOTRMod.mushroomPie), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.cherry), new ItemStack(LOTRMod.plum), new ItemStack(Items.cookie), new ItemStack(LOTRMod.hobbitPancake), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.elderberry), new ItemStack(LOTRMod.chestnutRoast), new ItemStack(LOTRMod.cornCooked), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate)});
	public static LOTRFoods HOBBIT_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugCherryLiqueur), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
	public static LOTRFoods BREE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.rabbitStew), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.leek), new ItemStack(LOTRMod.leekSoup), new ItemStack(Items.mushroom_stew), new ItemStack(LOTRMod.mushroomPie)});
	public static LOTRFoods BREE_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD);
	public static LOTRFoods RANGER = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry), new ItemStack(LOTRMod.raspberry), new ItemStack(LOTRMod.elderberry)});
	public static LOTRFoods RANGER_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods ROHAN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry)});
	public static LOTRFoods ROHAN_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN, LOTRItemMug.Vessel.HORN_GOLD);
	public static LOTRFoods GONDOR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.blueberry), new ItemStack(LOTRMod.blackberry), new ItemStack(LOTRMod.cranberry)});
	public static LOTRFoods GONDOR_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods DALE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate)});
	public static LOTRFoods DALE_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugPerry), new ItemStack(LOTRMod.mugVodka), new ItemStack(LOTRMod.mugPlumKvass), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods DWARF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.bread), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram), new ItemStack(LOTRMod.cram)});
	public static LOTRFoods BLUE_DWARF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.bread)});
	public static LOTRFoods DWARF_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenAle), new ItemStack(LOTRMod.mugDwarvenTonic)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods DUNLENDING = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.chestnutRoast)});
	public static LOTRFoods DUNLENDING_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugRum)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods RHUDAUR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(Items.bread), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(Items.rotten_flesh)});
	public static LOTRFoods RHUDAUR_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugMead), new ItemStack(LOTRMod.mugOrcDraught)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.HORN);
	public static LOTRFoods ELF = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.lembas), new ItemStack(LOTRMod.chestnutRoast)});
	public static LOTRFoods ELF_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugMiruvor), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
	public static LOTRFoods WOOD_ELF_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugAppleJuice), new ItemStack(LOTRMod.mugBlueberryJuice), new ItemStack(LOTRMod.mugBlackberryJuice), new ItemStack(LOTRMod.mugElderberryJuice), new ItemStack(LOTRMod.mugRaspberryJuice), new ItemStack(LOTRMod.mugCranberryJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE);
	public static LOTRFoods DORWINION = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.marzipan), new ItemStack(LOTRMod.marzipanChocolate), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins)});
	public static LOTRFoods DORWINION_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice)}).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE);
	public static LOTRFoods RHUN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.carrot), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.turnipCooked), new ItemStack(LOTRMod.rabbitCooked), new ItemStack(LOTRMod.deerCooked), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(LOTRMod.almond), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_porkchop), new ItemStack(LOTRMod.grapeRed), new ItemStack(LOTRMod.grapeWhite), new ItemStack(LOTRMod.raisins), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.pomegranate)});
	public static LOTRFoods RHUN_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugSourMilk), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugRedWine), new ItemStack(LOTRMod.mugWhiteWine), new ItemStack(LOTRMod.mugRedGrapeJuice), new ItemStack(LOTRMod.mugWhiteGrapeJuice), new ItemStack(LOTRMod.mugPomegranateWine), new ItemStack(LOTRMod.mugPomegranateJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_SILVER, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GLASS, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods SOUTHRON = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lime), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.almond), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked)});
	public static LOTRFoods SOUTHRON_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugLemonLiqueur), new ItemStack(LOTRMod.mugLemonade), new ItemStack(LOTRMod.mugLimeLiqueur), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods HARNEDOR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked)});
	public static LOTRFoods HARNEDOR_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods CORSAIR = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_fished), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab)});
	public static LOTRFoods CORSAIR_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugRum)}).setDrinkVessels(LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.BOTTLE, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL);
	public static LOTRFoods NOMAD = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(LOTRMod.date), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh)});
	public static LOTRFoods NOMAD_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugAle)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods GULF_HARAD = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.oliveBread), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.olive), new ItemStack(LOTRMod.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.orange), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(LOTRMod.muttonCooked), new ItemStack(LOTRMod.kebab), new ItemStack(LOTRMod.shishKebab), new ItemStack(LOTRMod.camelCooked), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh), new ItemStack(LOTRMod.saltedFlesh)});
	public static LOTRFoods GULF_HARAD_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugAraq), new ItemStack(LOTRMod.mugCactusLiqueur), new ItemStack(LOTRMod.mugOrangeJuice), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMangoJuice)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_COPPER, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.SKIN, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKULL);
	public static LOTRFoods HARAD_SLAVE = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.date), new ItemStack(LOTRMod.kebab)});
	public static LOTRFoods HARAD_SLAVE_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater)}).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods MOREDAIN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.melon), new ItemStack(LOTRMod.lionCooked), new ItemStack(LOTRMod.zebraCooked), new ItemStack(LOTRMod.rhinoCooked), new ItemStack(LOTRMod.yamRoast)});
	public static LOTRFoods MOREDAIN_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.MUG_CLAY, LOTRItemMug.Vessel.GOBLET_WOOD, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods TAUREDAIN = new LOTRFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(LOTRMod.bananaBread), new ItemStack(LOTRMod.cornBread), new ItemStack(LOTRMod.corn), new ItemStack(LOTRMod.cornCooked), new ItemStack(Items.baked_potato), new ItemStack(LOTRMod.banana), new ItemStack(LOTRMod.mango), new ItemStack(Items.melon), new ItemStack(LOTRMod.melonSoup), new ItemStack(Items.cooked_fished)});
	public static LOTRFoods TAUREDAIN_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugChocolate), new ItemStack(LOTRMod.mugMangoJuice), new ItemStack(LOTRMod.mugBananaBeer), new ItemStack(LOTRMod.mugMelonLiqueur), new ItemStack(LOTRMod.mugCornLiquor)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.GOBLET_GOLD, LOTRItemMug.Vessel.GOBLET_WOOD);
	public static LOTRFoods ORC = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread)});
	public static LOTRFoods ORC_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugOrcDraught)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods NURN_SLAVE = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread)});
	public static LOTRFoods NURN_SLAVE_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugWater), new ItemStack(LOTRMod.mugOrcDraught)}).setDrinkVessels(LOTRItemMug.Vessel.SKIN);
	public static LOTRFoods HALF_TROLL = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.maggotyBread), new ItemStack(Items.rotten_flesh), new ItemStack(LOTRMod.lionRaw), new ItemStack(LOTRMod.zebraRaw), new ItemStack(LOTRMod.rhinoRaw), new ItemStack(LOTRMod.torogStew)});
	public static LOTRFoods HALF_TROLL_DRINK = new LOTRFoods(new ItemStack[]{new ItemStack(LOTRMod.mugTorogDraught)}).setDrinkVessels(LOTRItemMug.Vessel.MUG, LOTRItemMug.Vessel.SKULL, LOTRItemMug.Vessel.SKIN);
	public ItemStack[] foodList;
	public LOTRItemMug.Vessel[] drinkVessels;
	public LOTRItemMug.Vessel[] drinkVesselsPlaceable;

	public LOTRFoods(ItemStack[] items) {
		foodList = items;
	}

	public LOTRItemMug.Vessel[] getDrinkVessels() {
		return drinkVessels;
	}

	public LOTRFoods setDrinkVessels(LOTRItemMug.Vessel... vessels) {
		drinkVessels = vessels;
		List<LOTRItemMug.Vessel> placeable = new ArrayList<>();
		for (LOTRItemMug.Vessel v : drinkVessels) {
			if (v.canPlace) {
				placeable.add(v);
			}
		}
		if (placeable.isEmpty()) {
			drinkVesselsPlaceable = new LOTRItemMug.Vessel[]{LOTRItemMug.Vessel.MUG};
		} else {
			drinkVesselsPlaceable = placeable.toArray(new LOTRItemMug.Vessel[0]);
		}
		return this;
	}

	public LOTRItemMug.Vessel[] getPlaceableDrinkVessels() {
		return drinkVesselsPlaceable;
	}

	public ItemStack getRandomBrewableDrink(Random random) {
		ArrayList<ItemStack> alcohols = new ArrayList<>();
		for (ItemStack itemstack : foodList) {
			Item item = itemstack.getItem();
			if (!(item instanceof LOTRItemMug) || !((LOTRItemMug) item).isBrewable) {
				continue;
			}
			alcohols.add(itemstack.copy());
		}
		ItemStack drink = alcohols.get(random.nextInt(alcohols.size()));
		setDrinkVessel(drink, random, false);
		return drink;
	}

	public ItemStack getRandomFood(Random random) {
		ItemStack food = foodList[random.nextInt(foodList.length)].copy();
		setDrinkVessel(food, random, false);
		return food;
	}

	public ItemStack getRandomFoodForPlate(Random random) {
		ArrayList<ItemStack> foodsNoContainer = new ArrayList<>();
		for (ItemStack itemstack : foodList) {
			Item item = itemstack.getItem();
			if (item.hasContainerItem(itemstack)) {
				continue;
			}
			foodsNoContainer.add(itemstack.copy());
		}
		return foodsNoContainer.get(random.nextInt(foodsNoContainer.size()));
	}

	public ItemStack getRandomPlaceableDrink(Random random) {
		ItemStack food = foodList[random.nextInt(foodList.length)].copy();
		setDrinkVessel(food, random, true);
		return food;
	}

	public LOTRItemMug.Vessel getRandomPlaceableVessel(Random random) {
		return drinkVesselsPlaceable[random.nextInt(drinkVesselsPlaceable.length)];
	}

	public LOTRItemMug.Vessel getRandomVessel(Random random) {
		return drinkVessels[random.nextInt(drinkVessels.length)];
	}

	public void setDrinkVessel(ItemStack itemstack, Random random, boolean requirePlaceable) {
		Item item = itemstack.getItem();
		if (item instanceof LOTRItemMug && ((LOTRItemMug) item).isFullMug) {
			LOTRItemMug.Vessel v = requirePlaceable ? getRandomPlaceableVessel(random) : getRandomVessel(random);
			LOTRItemMug.setVessel(itemstack, v, true);
		}
	}
}
