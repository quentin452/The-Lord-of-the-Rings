package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenRivendellHouse extends LOTRWorldGenHighElfHouse {
	public LOTRWorldGenRivendellHouse(boolean flag) {
		super(flag);
	}

	@Override
	public LOTREntityElf createElf(World world) {
		return new LOTREntityRivendellElf(world);
	}

	@Override
	public ItemStack getElfFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(LOTRMod.helmetRivendell), new ItemStack(LOTRMod.bodyRivendell), new ItemStack(LOTRMod.legsRivendell), new ItemStack(LOTRMod.bootsRivendell), new ItemStack(LOTRMod.daggerRivendell), new ItemStack(LOTRMod.swordRivendell), new ItemStack(LOTRMod.spearRivendell), new ItemStack(LOTRMod.longspearRivendell), new ItemStack(LOTRMod.rivendellBow), new ItemStack(Items.arrow), new ItemStack(Items.feather), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tableBlock = LOTRMod.rivendellTable;
		bannerType = LOTRItemBanner.BannerType.RIVENDELL;
		chestContents = LOTRChestContents.RIVENDELL_HALL;
	}
}
