package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LOTRBlockLeaves4 extends LOTRBlockLeavesBase {
	public LOTRBlockLeaves4() {
		setLeafNames("chestnut", "baobab", "cedar", "fir");
	}

	@Override
	public void addSpecialLeafDrops(List drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 0 && world.rand.nextInt(calcFortuneModifiedDropChance(20, fortune)) == 0) {
			drops.add(new ItemStack(LOTRMod.chestnut));
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = super.getDrops(world, i, j, k, meta, fortune);
		if ((meta & 3) == 3 && LOTRMod.isChristmas()) {
			for (ItemStack itemstack : drops) {
				if (world.rand.nextInt(3) != 0 || itemstack.getItem() != Item.getItemFromBlock(LOTRMod.sapling4)) {
					continue;
				}
				itemstack.setStackDisplayName("Christmas Tree");
			}
		}
		return drops;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(LOTRMod.sapling4);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		super.updateTick(world, i, j, k, random);
		if (!world.isRemote && world.getBlock(i, j, k) == this) {
			boolean playerPlaced;
			int meta = world.getBlockMetadata(i, j, k);
			int leafType = meta & 3;
			playerPlaced = (meta & 4) != 0;
			if (leafType == 0 && !playerPlaced && world.isAirBlock(i, j - 1, k) && random.nextInt(300) == 0) {
				double d = i + random.nextDouble();
				double d1 = j - 0.2;
				double d2 = k + random.nextDouble();
				EntityItem conker = new EntityItem(world, d, d1, d2, new ItemStack(LOTRMod.chestnut));
				conker.delayBeforeCanPickup = 10;
				conker.motionZ = 0.0;
				conker.motionY = 0.0;
				conker.motionX = 0.0;
				world.spawnEntityInWorld(conker);
			}
		}
	}
}
