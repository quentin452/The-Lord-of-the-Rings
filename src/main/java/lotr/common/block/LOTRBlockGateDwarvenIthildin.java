package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LOTRBlockGateDwarvenIthildin extends LOTRBlockGateDwarven {
	public boolean areDwarfDoorsMatching(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		Block otherBlock = world.getBlock(i1, j1, k1);
		int otherMeta = world.getBlockMetadata(i1, j1, k1);
		int dir = LOTRBlockGate.getGateDirection(meta);
		int otherDir = LOTRBlockGate.getGateDirection(otherMeta);
		return block == this && block == otherBlock && ((LOTRBlockGate) block).directionsMatch(dir, otherDir) && ((LOTRBlockGate) otherBlock).directionsMatch(dir, otherDir);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor) world.getTileEntity(i, j, k);
		if (door != null) {
			DoorSize doorSize = door.getDoorSize();
			if (doorSize != null) {
				int dir = getGateDirection(meta);
				if (dir != 0 && dir != 1) {
					int xzFactorX = dir == 2 ? 1 : dir == 3 ? -1 : 0;
					int xzFactorZ = dir == 4 ? -1 : dir == 5 ? 1 : 0;
					int width = doorSize.width;
					int height = doorSize.height;
					int rangeXZ = width - 1;
					int rangeY = height - 1;
					for (int y = -rangeY; y <= rangeY; y++) {
						for (int xz = -rangeXZ; xz <= rangeXZ; xz++) {
							int j1 = j + y;
							int i1 = i + xz * xzFactorX;
							int k1 = k + xz * xzFactorZ;
							if (i1 != i || j1 != j || k1 != k) {
								TileEntity te = world.getTileEntity(i1, j1, k1);
								if (te instanceof LOTRTileEntityDwarvenDoor) {
									LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor) te;
									if (otherDoor.isSameDoor(door)) {
										otherDoor.setDoorSizeAndPos(null, 0, 0);
									}
								}
							}
						}
					}
				}
			}
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new LOTRTileEntityDwarvenDoor();
	}

	@Override
	public boolean directionsMatch(int dir1, int dir2) {
		return dir1 == dir2;
	}

	public IIcon getGlowIcon(IBlockAccess world, int i, int j, int k, int side) {
		TileEntity te;
		int meta = world.getBlockMetadata(i, j, k);
		int dir = LOTRBlockGate.getGateDirection(meta);
		boolean open = LOTRBlockGate.isGateOpen(world, i, j, k);
		if (!open && dir == Facing.oppositeSide[side] && (te = world.getTileEntity(i, j, k)) instanceof LOTRTileEntityDwarvenDoor) {
			LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor) te;
			return door.getDoorSize().icons[door.getDoorPosX()][door.getDoorPosY()];
		}
		return null;
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@SuppressWarnings("ComparatorResultComparison")
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		super.onBlockPlacedBy(world, i, j, k, entity, itemstack);
		LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor) world.getTileEntity(i, j, k);
		if (door != null) {
			int meta = world.getBlockMetadata(i, j, k);
			int dir = getGateDirection(meta);
			door.setDoorSizeAndPos(null, 0, 0);
			door.setDoorBasePos(i, j, k);
			if (dir != 0 && dir != 1) {
				int xzFactorX = dir == 2 ? 1 : dir == 3 ? -1 : 0;
				int xzFactorZ = dir == 4 ? -1 : dir == 5 ? 1 : 0;
				for (DoorSize doorSize : DoorSize.orderedSizes) {
					int width = doorSize.width;
					int height = doorSize.height;
					int rangeXZ = width - 1;
					int rangeY = height - 1;
					for (int y = -rangeY; y <= 0; y++) {
						for (int xz = -rangeXZ; xz <= 0; xz++) {
							int j1 = j + y;
							int i1 = i + xz * xzFactorX;
							int k1 = k + xz * xzFactorZ;
							boolean connected = true;
							boolean canReplaceSize = true;
							int y1;
							label75:
							for (y1 = 0; y1 <= rangeY; y1++) {
								for (int xz1 = 0; xz1 <= rangeXZ; xz1++) {
									int j2 = j1 + y1;
									int i2 = i1 + xz1 * xzFactorX;
									int k2 = k1 + xz1 * xzFactorZ;
									if (i2 != i || j2 != j || k2 != k) {
										if (!areDwarfDoorsMatching(world, i, j, k, i2, j2, k2)) {
											connected = false;
											break label75;
										}
										TileEntity te = world.getTileEntity(i2, j2, k2);
										if (te instanceof LOTRTileEntityDwarvenDoor) {
											LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor) te;
											DoorSize otherSize = otherDoor.getDoorSize();
											if (DoorSize.compareLarger.compare(otherSize, doorSize) != 1) {
												canReplaceSize = false;
												break label75;
											}
										}
									}
								}
							}
							if (connected && canReplaceSize) {
								door.setDoorSizeAndPos(doorSize, -xz, -y);
								door.setDoorBasePos(i, j, k);
								for (y1 = 0; y1 <= rangeY; y1++) {
									for (int xz1 = 0; xz1 <= rangeXZ; xz1++) {
										int j2 = j1 + y1;
										int i2 = i1 + xz1 * xzFactorX;
										int k2 = k1 + xz1 * xzFactorZ;
										if (i2 != i || j2 != j || k2 != k) {
											TileEntity te = world.getTileEntity(i2, j2, k2);
											if (te instanceof LOTRTileEntityDwarvenDoor) {
												LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor) te;
												otherDoor.setDoorSizeAndPos(doorSize, xz1, y1);
												otherDoor.setDoorBasePos(i, j, k);
											}
										}
									}
								}
								// Byte code: goto -> 570
							}
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		for (DoorSize s : DoorSize.values()) {
			for (int i = 0; i < s.width; ++i) {
				for (int j = 0; j < s.height; ++j) {
					s.icons[i][j] = iconregister.registerIcon(getTextureName() + "_glow_" + s.doorName + "_" + i + "_" + j);
				}
			}
		}
	}

	public enum DoorSize {
		_1x1("1x1", 1, 1), _1x2("1x2", 1, 2), _2x2("2x2", 2, 2), _2x3("2x3", 2, 3), _3x4("3x4", 3, 4);

		public static Comparator<DoorSize> compareLarger;
		public static List<DoorSize> orderedSizes;

		static {
			compareLarger = (s1, s2) -> {
				if (s1 == s2) {
					return 0;
				}
				if (s1.area != s2.area) {
					return -Integer.compare(s1.area, s2.area);
				}
				if (s1.height != s2.height) {
					return -Integer.compare(s1.height, s2.height);
				}
				return -Integer.compare(s1.width, s2.width);
			};
			orderedSizes = new ArrayList<>();
			Collections.addAll(orderedSizes, values());
			orderedSizes.sort(compareLarger);
		}

		public String doorName;
		public int width;
		public int height;
		public int area;

		public IIcon[][] icons;

		DoorSize(String s, int i, int j) {
			doorName = s;
			width = i;
			height = j;
			area = width * height;
			icons = new IIcon[width][height];
		}

		public static DoorSize forName(String s) {
			for (DoorSize size : values()) {
				if (!size.doorName.equals(s)) {
					continue;
				}
				return size;
			}
			return null;
		}

	}

}