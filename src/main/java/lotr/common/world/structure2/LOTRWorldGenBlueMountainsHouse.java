package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenBlueMountainsHouse extends LOTRWorldGenDwarfHouse {
	public LOTRWorldGenBlueMountainsHouse(boolean flag) {
		super(flag);
	}

	@Override
	public LOTREntityDwarf createDwarf(World world) {
		return new LOTREntityBlueDwarf(world);
	}

	@Override
	public ItemStack getRandomOtherItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetBlueDwarven), new ItemStack(LOTRMod.bodyBlueDwarven), new ItemStack(LOTRMod.legsBlueDwarven), new ItemStack(LOTRMod.bootsBlueDwarven), new ItemStack(LOTRMod.blueDwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.iron_ingot), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_nugget)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public ItemStack getRandomWeaponItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.swordBlueDwarven), new ItemStack(LOTRMod.daggerBlueDwarven), new ItemStack(LOTRMod.hammerBlueDwarven), new ItemStack(LOTRMod.battleaxeBlueDwarven), new ItemStack(LOTRMod.pickaxeBlueDwarven), new ItemStack(LOTRMod.mattockBlueDwarven), new ItemStack(LOTRMod.throwingAxeBlueDwarven), new ItemStack(LOTRMod.pikeBlueDwarven)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		stoneBlock = Blocks.stone;
		stoneMeta = 0;
		fillerBlock = LOTRMod.rock;
		fillerMeta = 3;
		topBlock = LOTRMod.rock;
		topMeta = 3;
		brick2Block = LOTRMod.brick;
		brick2Meta = 14;
		pillarBlock = LOTRMod.pillar;
		pillarMeta = 3;
		chandelierBlock = LOTRMod.chandelier;
		chandelierMeta = 11;
		tableBlock = LOTRMod.blueDwarvenTable;
		barsBlock = LOTRMod.blueDwarfBars;
		larderContents = LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
		personalContents = LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
		plateFoods = LOTRFoods.BLUE_DWARF;
		drinkFoods = LOTRFoods.DWARF_DRINK;
	}
}
