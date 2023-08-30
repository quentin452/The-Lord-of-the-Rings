package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRCreativeTabs;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockGate extends Block implements LOTRConnectedBlock {
	public static int MAX_GATE_RANGE = 16;
	public boolean hasConnectedTextures;
	public boolean fullBlockGate;

	public LOTRBlockGate(Material material, boolean ct) {
		super(material);
		hasConnectedTextures = ct;
		setCreativeTab(LOTRCreativeTabs.tabUtil);
	}

	public void activateGate(World world, int i, int j, int k) {
		boolean wasOpen = isGateOpen(world, i, j, k);
		boolean isOpen = !wasOpen;
		List<ChunkCoordinates> gates = getConnectedGates(world, i, j, k);
		for (ChunkCoordinates coords : gates) {
			setGateOpen(world, coords.posX, coords.posY, coords.posZ, isOpen);
		}
		String soundEffect;
		boolean stone = getMaterial() == Material.rock;
		if (stone) {
			soundEffect = isOpen ? "lotr:block.gate.stone_open" : "lotr:block.gate.stone_close";
		} else {
			soundEffect = isOpen ? "lotr:block.gate.open" : "lotr:block.gate.close";
		}
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, soundEffect, 1.0F, 0.8F + world.rand.nextFloat() * 0.4F);
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		int meta = world.getBlockMetadata(i, j, k);
		Block otherBlock = world.getBlock(i1, j1, k1);
		int otherMeta = world.getBlockMetadata(i1, j1, k1);
		int dir = getGateDirection(meta);
		boolean open = isGateOpen(meta);
		int otherDir = getGateDirection(otherMeta);
		boolean otherOpen = isGateOpen(otherMeta);
		if ((dir == 0 || dir == 1) && j1 != j) {
			return false;
		}
		if ((dir == 2 || dir == 3) && k1 != k) {
			return false;
		}
		if ((dir == 4 || dir == 5) && i1 != i) {
			return false;
		}
		if (open && j1 == j - 1 && !(otherBlock instanceof LOTRBlockGate)) {
			return true;
		}
		boolean connected = open ? otherBlock instanceof LOTRBlockGate : otherBlock == this;
		return connected && directionsMatch(dir, otherDir) && ((LOTRBlockGate) otherBlock).directionsMatch(dir, otherDir) && open == otherOpen;
	}

	public boolean directionsMatch(int dir1, int dir2) {
		switch (dir1) {
			case 0:
			case 1:
				return dir1 == dir2;
			case 2:
			case 3:
				return dir2 == 2 || dir2 == 3;
			case 4:
			case 5:
				return dir2 == 4 || dir2 == 5;
			default:
				break;
		}
		return false;
	}

	public void gatherAdjacentGate(IBlockAccess world, int i, int j, int k, int dir, boolean open, Collection<ChunkCoordinates> allCoords, Collection<ChunkCoordinates> currentDepthCoords) {
		ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
		if (allCoords.contains(coords)) {
			return;
		}
		Block otherBlock = world.getBlock(i, j, k);
		if (otherBlock instanceof LOTRBlockGate) {
			boolean otherOpen = isGateOpen(world, i, j, k);
			int otherDir = getGateDirection(world, i, j, k);
			if (otherOpen == open && directionsMatch(dir, otherDir) && ((LOTRBlockGate) otherBlock).directionsMatch(dir, otherDir)) {
				allCoords.add(coords);
				currentDepthCoords.add(coords);
			}
		}
	}

	public void gatherAdjacentGates(IBlockAccess world, int i, int j, int k, int dir, boolean open, Collection<ChunkCoordinates> allCoords, Collection<ChunkCoordinates> currentDepthCoords) {
		if (dir != 0 && dir != 1) {
			gatherAdjacentGate(world, i, j - 1, k, dir, open, allCoords, currentDepthCoords);
			gatherAdjacentGate(world, i, j + 1, k, dir, open, allCoords, currentDepthCoords);
		}
		if (dir != 2 && dir != 3) {
			gatherAdjacentGate(world, i, j, k - 1, dir, open, allCoords, currentDepthCoords);
			gatherAdjacentGate(world, i, j, k + 1, dir, open, allCoords, currentDepthCoords);
		}
		if (dir != 4 && dir != 5) {
			gatherAdjacentGate(world, i - 1, j, k, dir, open, allCoords, currentDepthCoords);
			gatherAdjacentGate(world, i + 1, j, k, dir, open, allCoords, currentDepthCoords);
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess world, int i, int j, int k) {
		return isGateOpen(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		if (isGateOpen(world, i, j, k)) {
			return null;
		}
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.getCollisionBoundingBoxFromPool(world, i, j, k);
	}

	public List<ChunkCoordinates> getConnectedGates(IBlockAccess world, int i, int j, int k) {
		boolean open = isGateOpen(world, i, j, k);
		int dir = getGateDirection(world, i, j, k);
		Set<ChunkCoordinates> allCoords = new HashSet<>();
		Collection<ChunkCoordinates> lastDepthCoords = new HashSet<>();
		Collection<ChunkCoordinates> currentDepthCoords = new HashSet<>();
		for (int depth = 0; depth <= 16; depth++) {
			if (depth == 0) {
				allCoords.add(new ChunkCoordinates(i, j, k));
				currentDepthCoords.add(new ChunkCoordinates(i, j, k));
			} else {
				for (ChunkCoordinates coords : lastDepthCoords) {
					gatherAdjacentGates(world, coords.posX, coords.posY, coords.posZ, dir, open, allCoords, currentDepthCoords);
				}
			}
			lastDepthCoords.clear();
			lastDepthCoords.addAll(currentDepthCoords);
			currentDepthCoords.clear();
		}
		return new ArrayList<>(allCoords);
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		boolean open = isGateOpen(world, i, j, k);
		if (hasConnectedTextures) {
			return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, open);
		}
		if (open) {
			return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (hasConnectedTextures) {
			return LOTRConnectedTextures.getConnectedIconItem(this, j);
		}
		return blockIcon;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (Block.getBlockFromItem(item) instanceof LOTRBlockGate || LOTRWeaponStats.isRangedWeapon(itemstack)) {
				return false;
			}
		}
		if (!world.isRemote) {
			activateGate(world, i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!world.isRemote && !(block instanceof LOTRBlockGate)) {
			boolean open = isGateOpen(world, i, j, k);
			boolean powered = false;
			List<ChunkCoordinates> gates = getConnectedGates(world, i, j, k);
			for (ChunkCoordinates coords : gates) {
				int i1 = coords.posX;
				int j1 = coords.posY;
				int k1 = coords.posZ;
				if (world.isBlockIndirectlyGettingPowered(i1, j1, k1)) {
					powered = true;
					break;
				}
			}
			if ((powered || block.canProvidePower()) && powered ^ open) {
				activateGate(world, i, j, k);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		if (hasConnectedTextures) {
			LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 0, true);
		} else {
			super.registerBlockIcons(iconregister);
			LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
		int dir = getGateDirection(world, i, j, k);
		setBlockBoundsForDirection(dir);
	}

	public void setBlockBoundsForDirection(int dir) {
		if (fullBlockGate) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			float width = 0.25F;
			float halfWidth = width / 2.0F;
			switch (dir) {
				case 0:
					setBlockBounds(0.0F, 1.0F - width, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 1:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, width, 1.0F);
					break;
				case 2:
				case 3:
					setBlockBounds(0.0F, 0.0F, 0.5F - halfWidth, 1.0F, 1.0F, 0.5F + halfWidth);
					break;
				case 4:
				case 5:
					setBlockBounds(0.5F - halfWidth, 0.0F, 0.0F, 0.5F + halfWidth, 1.0F, 1.0F);
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBoundsForDirection(4);
	}

	public LOTRBlockGate setFullBlock() {
		fullBlockGate = true;
		lightOpacity = 255;
		useNeighborBrightness = true;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
		int i1 = i - Facing.offsetsXForSide[side];
		int j1 = j - Facing.offsetsYForSide[side];
		int k1 = k - Facing.offsetsZForSide[side];
		Block otherBlock = world.getBlock(i, j, k);
		if (otherBlock instanceof LOTRBlockGate) {
			int metaThis = world.getBlockMetadata(i1, j1, k1);
			int metaOther = world.getBlockMetadata(i, j, k);
			int dirThis = getGateDirection(metaThis);
			boolean openThis = isGateOpen(metaThis);
			int dirOther = getGateDirection(metaOther);
			boolean openOther = isGateOpen(metaOther);
			if (!fullBlockGate || openThis) {
				boolean connect = !directionsMatch(dirThis, side);
				if (connect) {
					return openThis != openOther || !directionsMatch(dirThis, dirOther) || !((LOTRBlockGate) otherBlock).directionsMatch(dirThis, dirOther);
				}
			}
		}
		return super.shouldSideBeRendered(world, i, j, k, side);
	}

	public static LOTRBlockGate createMetal(boolean ct) {
		LOTRBlockGate block = new LOTRBlockGate(Material.iron, ct);
		block.setHardness(4.0F);
		block.setResistance(10.0F);
		block.setStepSound(Block.soundTypeMetal);
		return block;
	}

	public static LOTRBlockGate createStone(boolean ct) {
		LOTRBlockGate block = new LOTRBlockGate(Material.rock, ct);
		block.setHardness(4.0F);
		block.setResistance(10.0F);
		block.setStepSound(Block.soundTypeStone);
		return block;
	}

	public static LOTRBlockGate createWooden(boolean ct) {
		LOTRBlockGate block = new LOTRBlockGate(Material.wood, ct);
		block.setHardness(4.0F);
		block.setResistance(5.0F);
		block.setStepSound(Block.soundTypeWood);
		return block;
	}

	public static int getGateDirection(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		return getGateDirection(meta);
	}

	public static int getGateDirection(int meta) {
		return meta & 0x7;
	}

	public static boolean isGateOpen(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		return isGateOpen(meta);
	}

	public static boolean isGateOpen(int meta) {
		return (meta & 0x8) != 0;
	}

	public static void setGateOpen(World world, int i, int j, int k, boolean flag) {
		int meta = world.getBlockMetadata(i, j, k);
		if (flag) {
			meta |= 0x8;
		} else {
			meta &= 0x7;
		}
		world.setBlockMetadataWithNotify(i, j, k, meta, 3);
	}
}