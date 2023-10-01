package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class LOTRBlockChandelier extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] chandelierIcons;
	public String[] chandelierNames = {"bronze", "iron", "silver", "gold", "mithril", "mallornSilver", "woodElven", "orc", "dwarven", "uruk", "highElven", "blueDwarven", "morgul", "mallornBlue", "mallornGold", "mallornGreen"};

	public LOTRBlockChandelier() {
		super(Material.circuits);
		setCreativeTab(LOTRCreativeTabs.tabDeco);
		setHardness(0.0f);
		setResistance(2.0f);
		setStepSound(Block.soundTypeMetal);
		setLightLevel(0.9375f);
		setBlockBounds(0.0625f, 0.1875f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j + 1, k);
		int meta = world.getBlockMetadata(i, j + 1, k);
		if (block instanceof BlockFence || block instanceof BlockWall) {
			return true;
		}
		if (block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 8) == 0) {
			return true;
		}
		if (block instanceof BlockStairs && (meta & 4) == 0 || block instanceof LOTRBlockOrcChain) {
			return true;
		}
		return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= chandelierNames.length) {
			j = 0;
		}
		return chandelierIcons[j];
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < chandelierNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k);
		double d = 0.13;
		double d1 = 1.0 - d;
		double d2 = 0.6875;
		spawnChandelierParticles(world, i + d, j + d2, k + d, random, meta);
		spawnChandelierParticles(world, i + d1, j + d2, k + d1, random, meta);
		spawnChandelierParticles(world, i + d, j + d2, k + d1, random, meta);
		spawnChandelierParticles(world, i + d1, j + d2, k + d, random, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		chandelierIcons = new IIcon[chandelierNames.length];
		for (int i = 0; i < chandelierNames.length; ++i) {
			chandelierIcons[i] = iconregister.registerIcon(getTextureName() + "_" + chandelierNames[i]);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public void spawnChandelierParticles(World world, double d, double d1, double d2, Random random, int meta) {
		switch (meta) {
			case 5:
			case 13:
			case 14:
			case 15: {
				LOTRBlockTorch torchBlock = null;
				switch (meta) {
					case 5:
						torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchSilver;
						break;
					case 13:
						torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchBlue;
						break;
					case 14:
						torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchGold;
						break;
					case 15:
						torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchGreen;
						break;
					default:
						break;
				}
				LOTRBlockTorch.TorchParticle particle = torchBlock.createTorchParticle(random);
				if (particle != null) {
					particle.spawn(d, d1, d2);
				}
				break;
			}
			case 6: {
				String s = "leafRed_" + (10 + random.nextInt(20));
				double d3 = -0.005 + random.nextFloat() * 0.01f;
				double d4 = -0.005 + random.nextFloat() * 0.01f;
				double d5 = -0.005 + random.nextFloat() * 0.01f;
				LOTRMod.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
				break;
			}
			case 10:
				LOTRMod.proxy.spawnParticle("elvenGlow", d, d1, d2, 0.0, 0.0, 0.0);
				break;
			case 12: {
				double d3 = -0.05 + random.nextFloat() * 0.1;
				double d4 = 0.1 + random.nextFloat() * 0.1;
				double d5 = -0.05 + random.nextFloat() * 0.1;
				LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
				break;
			}
			default:
				world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
				world.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
				break;
		}
	}
}
