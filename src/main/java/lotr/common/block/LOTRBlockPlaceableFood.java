package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockPlaceableFood extends Block {
	public static int MAX_EATS = 6;
	@SideOnly(Side.CLIENT)
	public IIcon iconBottom;
	@SideOnly(Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(Side.CLIENT)
	public IIcon iconSide;
	@SideOnly(Side.CLIENT)
	public IIcon iconEaten;
	public Item foodItem;
	public float foodHalfWidth;
	public float foodHeight;
	public int healAmount;
	public float saturationAmount;

	public LOTRBlockPlaceableFood() {
		this(0.4375f, 0.5f);
	}

	public LOTRBlockPlaceableFood(float f, float f1) {
		super(Material.cake);
		foodHalfWidth = f;
		foodHeight = f1;
		setHardness(0.5f);
		setStepSound(Block.soundTypeCloth);
		setTickRandomly(true);
		setFoodStats(2, 0.1f);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canBlockStay(world, i, j, k);
	}

	public void eatCake(World world, int i, int j, int k, EntityPlayer entityplayer) {
		if (!world.isRemote && entityplayer.canEat(false)) {
			entityplayer.getFoodStats().addStats(healAmount, saturationAmount);
			entityplayer.playSound("random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
			int meta = world.getBlockMetadata(i, j, k);
			if (++meta >= MAX_EATS) {
				world.setBlockToAir(i, j, k);
			} else {
				world.setBlockMetadataWithNotify(i, j, k, meta, 3);
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.5f - foodHalfWidth;
		float f1 = 0.5f + foodHalfWidth;
		float f2 = f + (f1 - f) * ((float) world.getBlockMetadata(i, j, k) / MAX_EATS);
		return AxisAlignedBB.getBoundingBox(i + f2, j, k + f, i + f1, j + foodHeight, k + f1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if (meta == 0) {
			if (foodItem != null) {
				drops.add(new ItemStack(foodItem));
			} else {
				drops.add(new ItemStack(this, 1, 0));
			}
		}
		return drops;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return iconBottom;
		}
		if (i == 1) {
			return iconTop;
		}
		if (j > 0 && i == 4) {
			return iconEaten;
		}
		return iconSide;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		if (foodItem != null) {
			return foodItem;
		}
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		return getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		eatCake(world, i, j, k, entityplayer);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		iconBottom = iconregister.registerIcon(getTextureName() + "_bottom");
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconSide = iconregister.registerIcon(getTextureName() + "_side");
		iconEaten = iconregister.registerIcon(getTextureName() + "_inner");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		world.getBlockMetadata(i, j, k);
		float f = 0.5f - foodHalfWidth;
		float f1 = 0.5f + foodHalfWidth;
		float f2 = f + (f1 - f) * ((float) world.getBlockMetadata(i, j, k) / MAX_EATS);
		setBlockBounds(f2, 0.0f, f, f1, foodHeight, f1);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.5f - foodHalfWidth;
		float f1 = 0.5f + foodHalfWidth;
		setBlockBounds(f, 0.0f, f, f1, foodHeight, f1);
	}

	public LOTRBlockPlaceableFood setFoodStats(int i, float f) {
		healAmount = i;
		saturationAmount = f;
		return this;
	}
}
