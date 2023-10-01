package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

public class LOTRBlockGrass extends BlockBush implements IShearable {
	public boolean isSandy;

	public LOTRBlockGrass() {
		super(Material.vine);
		setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setHardness(0.0f);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		if (isSandy && below.getMaterial() == Material.sand && below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
			return true;
		}
		return below.canSustainPlant(world, i, j, k, ForgeDirection.UP, this);
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}

	@Override
	public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
		return Blocks.tallgrass.getDrops(world, i, j, k, meta, fortune);
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getGrassRenderID();
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
		return list;
	}

	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		return Blocks.tallgrass.quantityDroppedWithBonus(i, random);
	}

	public LOTRBlockGrass setSandy() {
		isSandy = true;
		return this;
	}
}
