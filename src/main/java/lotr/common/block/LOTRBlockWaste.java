package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class LOTRBlockWaste extends Block {
	public static Random wasteRand = new Random();
	@SideOnly(Side.CLIENT)
	public IIcon[] randomIcons;

	public LOTRBlockWaste() {
		super(Material.ground);
		setHardness(0.5f);
		setStepSound(Block.soundTypeSand);
		setCreativeTab(LOTRCreativeTabs.tabBlock);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.125f;
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1 - f, k + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int hash = i * 25799626 ^ k * 6879038 ^ j;
		wasteRand.setSeed(hash + side);
		wasteRand.setSeed(wasteRand.nextLong());
		return randomIcons[wasteRand.nextInt(randomIcons.length)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		int hash = i * 334224425 ^ i;
		hash = hash * hash * 245256 + hash * 113549945;
		wasteRand.setSeed(hash);
		wasteRand.setSeed(wasteRand.nextLong());
		return randomIcons[wasteRand.nextInt(randomIcons.length)];
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getWasteRenderID();
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		double slow = 0.4;
		entity.motionX *= slow;
		entity.motionZ *= slow;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		randomIcons = new IIcon[8];
		for (int l = 0; l < randomIcons.length; ++l) {
			randomIcons[l] = iconregister.registerIcon(getTextureName() + "_var" + l);
		}
	}
}
