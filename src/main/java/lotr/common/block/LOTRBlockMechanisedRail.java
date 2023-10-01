package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class LOTRBlockMechanisedRail extends BlockRailBase {
	@SideOnly(Side.CLIENT)
	public IIcon iconOn;
	@SideOnly(Side.CLIENT)
	public IIcon iconOff;
	public boolean defaultPower;

	public LOTRBlockMechanisedRail(boolean power) {
		super(true);
		setHardness(0.7f);
		setStepSound(soundTypeMetal);
		defaultPower = power;
		setCreativeTab(null);
	}

	@Override
	public void func_150048_a(World world, int i, int j, int k, int meta, int dir, Block block) {
		boolean powered = world.isBlockIndirectlyGettingPowered(i, j, k);
		powered = powered || isPoweredRailAdjacent(world, i, j, k, meta, true, 0) || isPoweredRailAdjacent(world, i, j, k, meta, false, 0);
		boolean powerToggled = false;
		if (powered && (meta & 8) == 0) {
			world.setBlockMetadataWithNotify(i, j, k, dir | 8, 3);
			powerToggled = true;
		} else if (!powered && (meta & 8) != 0) {
			world.setBlockMetadataWithNotify(i, j, k, dir, 3);
			powerToggled = true;
		}
		if (powerToggled) {
			world.notifyBlocksOfNeighborChange(i, j - 1, k, this);
			if (dir == 2 || dir == 3 || dir == 4 || dir == 5) {
				world.notifyBlocksOfNeighborChange(i, j + 1, k, this);
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(Blocks.rail));
		drops.add(new ItemStack(LOTRMod.mechanism));
		return drops;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return isPowerOn(meta) ? iconOn : iconOff;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(LOTRMod.mechanism);
	}

	public boolean isPoweredRailAdjacent(World world, int i, int j, int k, int meta, boolean forwardOrBack, int recursion) {
		if (recursion >= 8) {
			return false;
		}
		int dir = meta & 7;
		boolean flat = true;
		int axis = 0;
		switch (dir) {
			case 0: {
				k = forwardOrBack ? ++k : --k;
				axis = 0;
				break;
			}
			case 1: {
				i = forwardOrBack ? --i : ++i;
				axis = 0;
				break;
			}
			case 2: {
				if (forwardOrBack) {
					--i;
				} else {
					++i;
					++j;
					flat = false;
				}
				axis = 1;
				break;
			}
			case 3: {
				if (forwardOrBack) {
					--i;
					++j;
					flat = false;
				} else {
					++i;
				}
				axis = 1;
				break;
			}
			case 4: {
				if (forwardOrBack) {
					++k;
				} else {
					--k;
					++j;
					flat = false;
				}
				axis = 0;
				break;
			}
			case 5: {
				if (forwardOrBack) {
					++k;
					++j;
					flat = false;
				} else {
					--k;
				}
				axis = 0;
			}
		}
		return isPoweredRailAt(world, i, j, k, forwardOrBack, recursion, axis) || flat && isPoweredRailAt(world, i, j - 1, k, forwardOrBack, recursion, axis);
	}

	public boolean isPoweredRailAt(World world, int i, int j, int k, boolean forwardOrBack, int recursion, int axis) {
		Block block = world.getBlock(i, j, k);
		if (block == this) {
			int j1 = world.getBlockMetadata(i, j, k);
			int k1 = j1 & 7;
			if (axis == 1 && (k1 == 0 || k1 == 4 || k1 == 5)) {
				return false;
			}
			if (axis == 0 && (k1 == 1 || k1 == 2 || k1 == 3)) {
				return false;
			}
			if ((j1 & 8) != 0) {
				if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
					return true;
				}
				return isPoweredRailAdjacent(world, i, j, k, j1, forwardOrBack, recursion + 1);
			}
		}
		return false;
	}

	public boolean isPowerOn(int meta) {
		return (meta & 8) == 0 == defaultPower;
	}

	public Vec3 lerp(Vec3 vec1, Vec3 vec2, float t) {
		return Vec3.createVectorHelper(vec1.xCoord + (vec2.xCoord - vec1.xCoord) * t, vec1.yCoord + (vec2.yCoord - vec1.yCoord) * t, vec1.zCoord + (vec2.zCoord - vec1.zCoord) * t);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack curItem = entityplayer.getHeldItem();
		if (curItem != null && curItem.getItem() instanceof ItemMinecart) {
			return false;
		}
		Block setBlock = this == LOTRMod.mechanisedRailOff ? LOTRMod.mechanisedRailOn : LOTRMod.mechanisedRailOff;
		int setMeta = world.getBlockMetadata(i, j, k);
		world.setBlock(i, j, k, setBlock, setMeta, 3);
		boolean isNowPowered = ((LOTRBlockMechanisedRail) setBlock).isPowerOn(setMeta);
		world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, "random.click", 0.3f, isNowPowered ? 0.6f : 0.5f);
		return true;
	}

	public boolean onShiftClickActivateFirst(World world, int i, int j, int k, EntityPlayer entityplayer, int side) {
		ItemStack curItem = entityplayer.getHeldItem();
		if (curItem != null && curItem.getItem() instanceof ItemMinecart) {
			return false;
		}
		Block setBlock = Blocks.rail;
		world.setBlock(i, j, k, setBlock, getBasicRailMetadata(world, null, i, j, k), 3);
		Block.SoundType sound = setBlock.stepSound;
		world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, sound.func_150496_b(), (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
		if (!world.isRemote) {
			dropBlockAsItem(world, i, j, k, new ItemStack(LOTRMod.mechanism));
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random rand) {
		int meta = world.getBlockMetadata(i, j, k);
		boolean power = isPowerOn(meta);
		if (power) {
			int dir = meta & 7;
			Vec3 corner1 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
			Vec3 corner2 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
			Vec3 corner3 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
			Vec3 corner4 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
			switch (dir) {
				case 0:
					corner1 = Vec3.createVectorHelper(-0.4, 0.0, -0.5);
					corner2 = Vec3.createVectorHelper(-0.4, 0.0, 0.5);
					corner3 = Vec3.createVectorHelper(0.4, 0.0, -0.5);
					corner4 = Vec3.createVectorHelper(0.4, 0.0, 0.5);
					break;
				case 1:
					corner1 = Vec3.createVectorHelper(-0.5, 0.0, -0.4);
					corner2 = Vec3.createVectorHelper(0.5, 0.0, -0.4);
					corner3 = Vec3.createVectorHelper(-0.5, 0.0, 0.4);
					corner4 = Vec3.createVectorHelper(0.5, 0.0, 0.4);
					break;
				case 2:
					corner1 = Vec3.createVectorHelper(-0.5, 0.0, -0.4);
					corner2 = Vec3.createVectorHelper(0.5, 1.0, -0.4);
					corner3 = Vec3.createVectorHelper(-0.5, 0.0, 0.4);
					corner4 = Vec3.createVectorHelper(0.5, 1.0, 0.4);
					break;
				case 3:
					corner1 = Vec3.createVectorHelper(-0.5, 1.0, -0.4);
					corner2 = Vec3.createVectorHelper(0.5, 0.0, -0.4);
					corner3 = Vec3.createVectorHelper(-0.5, 1.0, 0.4);
					corner4 = Vec3.createVectorHelper(0.5, 0.0, 0.4);
					break;
				case 4:
					corner1 = Vec3.createVectorHelper(-0.4, 1.0, -0.5);
					corner2 = Vec3.createVectorHelper(-0.4, 0.0, 0.5);
					corner3 = Vec3.createVectorHelper(0.4, 1.0, -0.5);
					corner4 = Vec3.createVectorHelper(0.4, 0.0, 0.5);
					break;
				case 5:
					corner1 = Vec3.createVectorHelper(-0.4, 0.0, -0.5);
					corner2 = Vec3.createVectorHelper(-0.4, 1.0, 0.5);
					corner3 = Vec3.createVectorHelper(0.4, 0.0, -0.5);
					corner4 = Vec3.createVectorHelper(0.4, 1.0, 0.5);
					break;
				default:
					break;
			}
			for (int l = 0; l < 1; ++l) {
				float t1 = rand.nextFloat();
				float t2 = rand.nextFloat();
				Vec3 edge1 = lerp(corner1, corner2, t1).addVector(i + 0.5, j, k + 0.5);
				Vec3 edge2 = lerp(corner3, corner4, t2).addVector(i + 0.5, j, k + 0.5);
				world.spawnParticle("smoke", edge1.xCoord, edge1.yCoord, edge1.zCoord, 0.0, 0.1, 0.0);
				world.spawnParticle("smoke", edge2.xCoord, edge2.yCoord, edge2.zCoord, 0.0, 0.1, 0.0);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		iconOn = register.registerIcon(getTextureName() + "_on");
		blockIcon = iconOff = register.registerIcon(getTextureName() + "_off");
	}
}
