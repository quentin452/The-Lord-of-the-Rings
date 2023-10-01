package lotr.common.block;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRBlockOre extends Block {
	public LOTRBlockOre() {
		super(Material.rock);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(3.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
		super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
		if (getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock(this)) {
			int amountXp = 0;
			if (this == LOTRMod.oreNaurite) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
			}
			if (this == LOTRMod.oreQuendite) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
			}
			if (this == LOTRMod.oreGlowstone) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 2, 4);
			}
			if (this == LOTRMod.oreGulduril) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 2, 5);
			}
			if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter) {
				amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
			}
			dropXpOnBlockBreak(world, i, j, k, amountXp);
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		if (this == LOTRMod.oreNaurite) {
			return LOTRMod.nauriteGem;
		}
		if (this == LOTRMod.oreQuendite) {
			return LOTRMod.quenditeCrystal;
		}
		if (this == LOTRMod.oreGlowstone) {
			return Items.glowstone_dust;
		}
		if (this == LOTRMod.oreGulduril) {
			return LOTRMod.guldurilCrystal;
		}
		if (this == LOTRMod.oreSulfur) {
			return LOTRMod.sulfur;
		}
		if (this == LOTRMod.oreSaltpeter) {
			return LOTRMod.saltpeter;
		}
		return Item.getItemFromBlock(this);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
		if (!world.isRemote) {
			if (this == LOTRMod.oreMithril) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineMithril);
			}
			if (this == LOTRMod.oreQuendite) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineQuendite);
			}
			if (this == LOTRMod.oreGlowstone) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGlowstone);
			}
			if (this == LOTRMod.oreNaurite) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineNaurite);
			}
			if (this == LOTRMod.oreGulduril) {
				LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineGulduril);
			}
		}
	}

	@Override
	public int quantityDropped(Random random) {
		if (this == LOTRMod.oreNaurite) {
			return 1 + random.nextInt(2);
		}
		if (this == LOTRMod.oreGlowstone) {
			return 2 + random.nextInt(4);
		}
		if (this == LOTRMod.oreSulfur || this == LOTRMod.oreSaltpeter) {
			return 1 + random.nextInt(2);
		}
		return 1;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		if (i > 0 && Item.getItemFromBlock(this) != getItemDropped(0, random, i)) {
			int factor = random.nextInt(i + 2) - 1;
			factor = Math.max(factor, 0);
			int drops = quantityDropped(random) * (factor + 1);
			if (this == LOTRMod.oreGlowstone) {
				drops = Math.min(drops, 8);
			}
			return drops;
		}
		return quantityDropped(random);
	}
}
