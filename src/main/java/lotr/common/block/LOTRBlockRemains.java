package lotr.common.block;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;

public class LOTRBlockRemains extends Block {
	public LOTRBlockRemains() {
		super(LOTRMaterialRemains.remains);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
		setHardness(3.0f);
		setStepSound(Block.soundTypeGravel);
	}

	@Override
	public ArrayList getDrops(World world, int i, int j, int k, int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		int dropCount = MathHelper.getRandomIntegerInRange(world.rand, 1, 3) + world.rand.nextInt(1 + fortune * 2);
		if (world.rand.nextBoolean()) {
			++dropCount;
		}
		IInventory dropInv = new InventoryBasic("remains", true, dropCount * 5);
		LOTRChestContents.fillInventory(dropInv, world.rand, LOTRChestContents.MARSH_REMAINS, dropCount);
		for (int l = 0; l < dropInv.getSizeInventory(); ++l) {
			ItemStack drop = dropInv.getStackInSlot(l);
			if (drop == null) {
				continue;
			}
			drops.add(drop);
		}
		return drops;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
		super.harvestBlock(world, entityplayer, i, j, k, l);
		if (!world.isRemote) {
			LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineRemains);
		}
	}
}
