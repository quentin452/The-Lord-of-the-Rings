package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class LOTRWorldGenHobbitWindmill extends LOTRWorldGenStructureBase2 {
	public Block plankBlock;
	public int plankMeta;
	public Block woodBlock;
	public int woodMeta;
	public Block doorBlock;

	public LOTRWorldGenHobbitWindmill(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		TileEntity te;
		int i1;
		int j1;
		int j12;
		Block fillBlock;
		int k1;
		int j13;
		int i2;
		int fillMeta;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -4; i1 <= 4; ++i1) {
				for (k1 = -4; k1 <= 4; ++k1) {
					j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 >= 3 && k2 > 3 || k2 >= 3 && i2 > 3) {
					continue;
				}
				fillMeta = 0;
				if (i2 == 3 && k2 == 3) {
					fillBlock = plankBlock;
					fillMeta = plankMeta;
				} else if (i2 == 4 && k2 == 2 || i2 == 2 && k2 == 4) {
					fillBlock = woodBlock;
					fillMeta = woodMeta;
				} else if (i2 == 4 || k2 == 4) {
					fillBlock = plankBlock;
					fillMeta = plankMeta;
				} else {
					fillBlock = Blocks.air;
				}
				for (j13 = 4; (j13 >= 0 || !isOpaque(world, i1, j13, k1)) && getY(j13) >= 0; --j13) {
					if (fillBlock == Blocks.air) {
						if (j13 == 4 || j13 <= 0) {
							setBlockAndMetadata(world, i1, j13, k1, plankBlock, plankMeta);
							setGrassToDirt(world, i1, j13 - 1, k1);
							continue;
						}
						setAir(world, i1, j13, k1);
						continue;
					}
					setBlockAndMetadata(world, i1, j13, k1, fillBlock, fillMeta);
					setGrassToDirt(world, i1, j13 - 1, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					continue;
				}
				fillMeta = 0;
				if (i2 == 3 && k2 == 2 || i2 == 2 && k2 == 3) {
					fillBlock = woodBlock;
					fillMeta = woodMeta;
				} else if (i2 == 3 || k2 == 3) {
					fillBlock = plankBlock;
					fillMeta = plankMeta;
				} else {
					fillBlock = Blocks.air;
				}
				for (j13 = 5; j13 <= 8; ++j13) {
					if (fillBlock == Blocks.air) {
						setAir(world, i1, j13, k1);
						continue;
					}
					setBlockAndMetadata(world, i1, j13, k1, fillBlock, fillMeta);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				for (j1 = 9; j1 <= 12; ++j1) {
					if (i2 == 2 && k2 == 2) {
						setBlockAndMetadata(world, i1, j1, k1, woodBlock, woodMeta);
						continue;
					}
					if (i2 == 2 || k2 == 2) {
						setBlockAndMetadata(world, i1, j1, k1, plankBlock, plankMeta);
						continue;
					}
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				for (j12 = 11; j12 <= 12; ++j12) {
					setBlockAndMetadata(world, i1, j12, k1, plankBlock, plankMeta);
				}
			}
		}
		setBlockAndMetadata(world, 0, 10, 0, LOTRMod.chandelier, 2);
		int originX = 0;
		int originY = 13;
		int originZ = 0;
		int radius = 4;
		for (int i12 = originX - radius; i12 <= originX + radius; ++i12) {
			for (int j14 = originY - radius; j14 <= originY + radius; ++j14) {
				for (int k12 = originZ - radius; k12 <= originZ + radius; ++k12) {
					int i22 = i12 - originX;
					int j2 = j14 - originY;
					int k22 = k12 - originZ;
					int dist = i22 * i22 + j2 * j2 + k22 * k22;
					if (dist >= radius * radius || j14 < originY) {
						continue;
					}
					setBlockAndMetadata(world, i12, j14, k12, LOTRMod.clayTileDyed, 13);
				}
			}
		}
		setBlockAndMetadata(world, -3, 6, 0, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 3, 6, 0, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 0, 6, -3, LOTRMod.glassPane, 0);
		setBlockAndMetadata(world, 0, 6, 3, LOTRMod.glassPane, 0);
		placeFenceTorch(world, -2, 2, -3);
		placeFenceTorch(world, -2, 2, 3);
		placeFenceTorch(world, 2, 2, -3);
		placeFenceTorch(world, 2, 2, 3);
		placeFenceTorch(world, -3, 2, -2);
		placeFenceTorch(world, 3, 2, -2);
		placeFenceTorch(world, -3, 2, 2);
		placeFenceTorch(world, 3, 2, 2);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, -3, 1, -1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 1, 0, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -2, 1, 0, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 1, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -2, 1, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 2, 1, Blocks.hay_block, 0);
		for (j1 = 1; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, 0, j1, 2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 0, j1, 1, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 1, 5, -2, Blocks.bed, 1);
		setBlockAndMetadata(world, 2, 5, -2, Blocks.bed, 9);
		setBlockAndMetadata(world, -2, 5, -2, Blocks.bookshelf, 0);
		setBlockAndMetadata(world, -1, 5, -2, Blocks.bookshelf, 0);
		setBlockAndMetadata(world, -2, 6, -2, Blocks.bookshelf, 0);
		setBlockAndMetadata(world, -1, 6, -2, Blocks.bookshelf, 0);
		setBlockAndMetadata(world, -2, 5, -1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -2, 5, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 5, 2, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 6, 1, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
		placeBarrel(world, random, -2, 6, 2, 4, LOTRFoods.HOBBIT_DRINK);
		setBlockAndMetadata(world, 2, 5, 1, LOTRMod.hobbitOven, 5);
		setBlockAndMetadata(world, 2, 5, 2, LOTRMod.hobbitOven, 5);
		placeChest(world, random, -2, 5, 0, 4, LOTRChestContents.HOBBIT_HOLE_STUDY);
		placeChest(world, random, 2, 5, 0, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
		if (random.nextInt(20) == 0 && (te = getTileEntity(world, 2, 5, 0)) instanceof IInventory) {
			IInventory chest = (IInventory) te;
			ItemStack hooch = new ItemStack(LOTRMod.mugLemonLiqueur);
			LOTRItemMug.setStrengthMeta(hooch, 1);
			LOTRItemMug.setVessel(hooch, LOTRItemMug.Vessel.MUG, true);
			hooch.setStackDisplayName("Bad Windmill Hooch");
			NBTTagList loreTags = hooch.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
			loreTags.appendTag(new NBTTagString("Really nothing compared to the Spoons Hooch."));
			hooch.getTagCompound().getCompoundTag("display").setTag("Lore", loreTags);
			int slot = random.nextInt(chest.getSizeInventory());
			chest.setInventorySlotContents(slot, hooch);
		}
		setBlockAndMetadata(world, 0, 10, -3, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 10, -4, Blocks.wool, 15);
		for (j1 = 7; j1 <= 13; ++j1) {
			for (int i13 = -3; i13 <= 3; ++i13) {
				int j2 = Math.abs(j1 - 10);
				if (j2 != Math.abs(i13) || j2 == 0) {
					continue;
				}
				setBlockAndMetadata(world, i13, j1, -4, Blocks.wool, 0);
			}
		}
		LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
		spawnNPCAndSetHome(hobbit, world, 0, 1, 0, 8);
		return true;
	}

	public void placeFenceTorch(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, Blocks.fence, 0);
		setBlockAndMetadata(world, i, j + 1, k, Blocks.torch, 5);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		if (random.nextBoolean()) {
			woodBlock = Blocks.log;
			woodMeta = 0;
			plankBlock = Blocks.planks;
			plankMeta = 0;
			doorBlock = Blocks.wooden_door;
		} else {
			woodBlock = LOTRMod.wood;
			woodMeta = 0;
			plankBlock = LOTRMod.planks;
			plankMeta = 0;
			doorBlock = LOTRMod.doorShirePine;
		}
	}
}
