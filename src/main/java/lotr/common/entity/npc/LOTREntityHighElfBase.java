package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityHighElfBase extends LOTREntityElf {
	protected LOTREntityHighElfBase(World world) {
		super(world);
	}

	@Override
	public boolean canElfSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return j > 62 && worldObj.getBlock(i, j - 1, k) == Blocks.grass;
	}

	@Override
	public void dropElfItems(boolean flag, int i) {
		super.dropElfItems(flag, i);
		if (flag) {
			int dropChance = 20 - i * 4;
			if (rand.nextInt(Math.max(dropChance, 1)) == 0) {
				ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
				elfDrink.setItemDamage(1 + rand.nextInt(3));
				LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(rand), true);
				entityDropItem(elfDrink, 0.0f);
			}
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.HIGH_ELF;
	}

}
