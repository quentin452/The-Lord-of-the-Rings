package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercTent extends LOTRWorldGenStructureBase2 {
	public Block fenceBlock;
	public int fenceMeta;
	public Block tentBlock;
	public int tentMeta;
	public Block tent2Block;
	public int tent2Meta;
	public Block tableBlock;
	public LOTRChestContents chestContents;
	public LOTRItemBanner.BannerType bannerType;

	public LOTRWorldGenMoredainMercTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					int randomGround = random.nextInt(3);
					switch (randomGround) {
						case 0:
							if (j1 == 0) {
								setBiomeTop(world, i1, 0, k1);
							} else {
								setBiomeFiller(world, i1, j1, k1);
							}
							break;
						case 1:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
							break;
						case 2:
							setBlockAndMetadata(world, i1, j1, k1, Blocks.sandstone, 0);
							break;
						default:
							break;
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (int k12 = -3; k12 <= 3; ++k12) {
			boolean tent2 = IntMath.mod(k12, 2) == 0;
			Block block = tent2 ? tent2Block : tentBlock;
			int meta = tent2 ? tent2Meta : tentMeta;
			for (int i12 : new int[]{-2, 2}) {
				for (int j12 = 1; j12 <= 2; ++j12) {
					setBlockAndMetadata(world, i12, j12, k12, block, meta);
				}
				setGrassToDirt(world, i12, 0, k12);
			}
			setBlockAndMetadata(world, -1, 3, k12, block, meta);
			setBlockAndMetadata(world, 1, 3, k12, block, meta);
			setBlockAndMetadata(world, 0, 4, k12, block, meta);
			if (Math.abs(k12) != 3) {
				continue;
			}
			setBlockAndMetadata(world, 0, 5, k12, block, meta);
		}
		for (int j13 = 1; j13 <= 3; ++j13) {
			setBlockAndMetadata(world, 0, j13, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 0, j13, 3, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
		if (random.nextBoolean()) {
			placeChest(world, random, -1, 1, 0, 4, chestContents);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
			setGrassToDirt(world, -1, 0, -1);
			setBlockAndMetadata(world, -1, 1, 1, tableBlock, 0);
			setGrassToDirt(world, -1, 0, 1);
		} else {
			placeChest(world, random, 1, 1, 0, 5, chestContents);
			setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
			setGrassToDirt(world, 1, 0, -1);
			setBlockAndMetadata(world, 1, 1, 1, tableBlock, 0);
			setGrassToDirt(world, 1, 0, 1);
		}
		placeWallBanner(world, 0, 5, -3, bannerType, 2);
		placeWallBanner(world, 0, 5, 3, bannerType, 0);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		fenceBlock = LOTRMod.fence2;
		fenceMeta = 2;
		int randomWool = random.nextInt(3);
		switch (randomWool) {
			case 0:
				tentBlock = Blocks.wool;
				tentMeta = 14;
				break;
			case 1:
				tentBlock = Blocks.wool;
				tentMeta = 12;
				break;
			case 2:
				tentBlock = Blocks.wool;
				tentMeta = 1;
				break;
			default:
				break;
		}
		tent2Block = Blocks.wool;
		tent2Meta = 15;
		chestContents = LOTRChestContents.MOREDAIN_MERC_TENT;
		if (random.nextBoolean()) {
			tableBlock = LOTRMod.nearHaradTable;
			bannerType = LOTRItemBanner.BannerType.NEAR_HARAD;
		} else {
			tableBlock = LOTRMod.moredainTable;
			bannerType = LOTRItemBanner.BannerType.MOREDAIN;
		}
	}
}
