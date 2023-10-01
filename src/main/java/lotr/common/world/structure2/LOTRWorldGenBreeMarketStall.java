package lotr.common.world.structure2;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class LOTRWorldGenBreeMarketStall extends LOTRWorldGenBreeStructure {
	public static Class[] allStallTypes = {Baker.class, Butcher.class, Brewer.class, Mason.class, Lumber.class, Smith.class, Florist.class, Farmer.class};
	public Block wool1Block;
	public int wool1Meta;
	public Block wool2Block;
	public int wool2Meta;

	protected LOTRWorldGenBreeMarketStall(boolean flag) {
		super(flag);
	}

	public static LOTRWorldGenBreeMarketStall getRandomStall(Random random, boolean flag) {
		try {
			Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return (LOTRWorldGenBreeMarketStall) cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LOTRWorldGenBreeMarketStall[] getRandomStalls(Random random, boolean flag, int num) {
		List<Class> types = Arrays.asList(Arrays.copyOf(allStallTypes, allStallTypes.length));
		Collections.shuffle(types, random);
		LOTRWorldGenBreeMarketStall[] ret = new LOTRWorldGenBreeMarketStall[num];
		for (int i = 0; i < ret.length; ++i) {
			int listIndex = i % types.size();
			Class cls = types.get(listIndex);
			try {
				ret[i] = (LOTRWorldGenBreeMarketStall) cls.getConstructor(Boolean.TYPE).newInstance(flag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public abstract LOTREntityNPC createTrader(World var1, Random var2);

	public abstract void decorateStall(World var1, Random var2);

	@Override
	public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int k1;
		int i2;
		int j1;
		int i1;
		int step;
		int i12;
		int j2;
		int k12;
		int j12;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i1 = -3; i1 <= 3; ++i1) {
				for (int k13 = -3; k13 <= 3; ++k13) {
					j12 = getTopBlock(world, i1, k13) - 1;
					if (!isSurface(world, i1, j12, k13)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k12 = -2; k12 <= 2; ++k12) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k12);
				if (i2 == 2 && k2 == 2) {
					for (j12 = 3; (j12 >= 0 || !isOpaque(world, i12, j12, k12)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i12, j12, k12, beamBlock, beamMeta);
						setGrassToDirt(world, i12, j12 - 1, k12);
					}
					continue;
				}
				placeRandomFloor(world, random, i12, 0, k12);
				setGrassToDirt(world, i12, -1, k12);
				j12 = -1;
				while (!isOpaque(world, i12, j12, k12) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i12, j12, k12, Blocks.dirt, 0);
					setGrassToDirt(world, i12, j12 - 1, k12);
					--j12;
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setAir(world, i12, j12, k12);
				}
				if ((i2 != 2 || k2 > 1) && (k2 != 2 || i2 > 1)) {
					continue;
				}
				setBlockAndMetadata(world, i12, 3, k12, fenceBlock, fenceMeta);
			}
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k12 = -3; k12 <= 3; ++k12) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k12);
				if (i2 == 3 && k2 >= 1 && k2 <= 2 || k2 == 3 && i2 >= 1 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k12, wool1Block, wool1Meta);
				}
				if (i2 + k2 == 3 || i2 + k2 == 4) {
					if (i2 == 2 && k2 == 2) {
						setBlockAndMetadata(world, i12, 4, k12, wool2Block, wool2Meta);
					} else {
						setBlockAndMetadata(world, i12, 4, k12, wool1Block, wool1Meta);
					}
				}
				if (i2 + k2 > 2) {
					continue;
				}
				if (i2 == k2) {
					setBlockAndMetadata(world, i12, 5, k12, wool2Block, wool2Meta);
					continue;
				}
				setBlockAndMetadata(world, i12, 5, k12, wool1Block, wool1Meta);
			}
		}
		setBlockAndMetadata(world, -1, 1, -2, plankStairBlock, 4);
		setBlockAndMetadata(world, 0, 1, -2, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, 1, 1, -2, plankStairBlock, 5);
		setBlockAndMetadata(world, 2, 1, -1, fenceGateBlock, 3);
		setBlockAndMetadata(world, 2, 1, 0, plankStairBlock, 7);
		setBlockAndMetadata(world, 2, 1, 1, plankStairBlock, 6);
		setBlockAndMetadata(world, 1, 1, 2, plankStairBlock, 5);
		setBlockAndMetadata(world, 0, 1, 2, plankStairBlock, 4);
		setBlockAndMetadata(world, -1, 1, 2, fenceGateBlock, 0);
		setBlockAndMetadata(world, -2, 1, 1, plankStairBlock, 6);
		setBlockAndMetadata(world, -2, 1, 0, plankSlabBlock, plankSlabMeta | 8);
		setBlockAndMetadata(world, -2, 1, -1, plankStairBlock, 7);
		for (i12 = -1; i12 <= 1; ++i12) {
			setBlockAndMetadata(world, i12, 1, -3, trapdoorBlock, 12);
		}
		for (int k14 = -1; k14 <= 1; ++k14) {
			setBlockAndMetadata(world, -3, 1, k14, trapdoorBlock, 15);
		}
		if (random.nextBoolean()) {
			setBlockAndMetadata(world, 1, 1, 1, Blocks.chest, 2);
		} else {
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.chestBasket, 2);
		}
		for (step = 0; step < 12 && !isOpaque(world, i1 = 3 + step, j1 = -step, k1 = -1); ++step) {
			placeRandomFloor(world, random, i1, j1, k1);
			setGrassToDirt(world, i1, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		for (step = 0; step < 12 && !isOpaque(world, i1 = -1, j1 = -step, k1 = 3 + step); ++step) {
			placeRandomFloor(world, random, i1, j1, k1);
			setGrassToDirt(world, i1, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		decorateStall(world, random);
		LOTREntityNPC trader = createTrader(world, random);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 1);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		wool1Block = Blocks.wool;
		wool1Meta = 14;
		wool2Block = Blocks.wool;
		wool2Meta = 0;
	}

	public static class Baker extends LOTRWorldGenBreeMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return random.nextBoolean() ? new LOTREntityBreeHobbitBaker(world) : new LOTREntityBreeBaker(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placePlate_item(world, random, -1, 2, -2, LOTRMod.woodPlateBlock, getRandomBakeryItem(random), true);
			placePlate_item(world, random, 1, 2, 2, LOTRMod.ceramicPlateBlock, getRandomBakeryItem(random), true);
			placePlate_item(world, random, -2, 2, 1, LOTRMod.plateBlock, getRandomBakeryItem(random), true);
			setBlockAndMetadata(world, 1, 2, -2, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
			setBlockAndMetadata(world, -2, 2, -1, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
			setBlockAndMetadata(world, 2, 2, 1, LOTRWorldGenBreeStructure.getRandomPieBlock(random), 0);
		}

		public ItemStack getRandomBakeryItem(Random random) {
			ItemStack[] foods = {new ItemStack(Items.bread), new ItemStack(LOTRMod.cornBread), new ItemStack(LOTRMod.hobbitPancake), new ItemStack(LOTRMod.hobbitPancakeMapleSyrup)};
			ItemStack ret = foods[random.nextInt(foods.length)].copy();
			ret.stackSize = 1 + random.nextInt(3);
			return ret;
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 4;
		}
	}

	public static class Brewer extends LOTRWorldGenBreeMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return random.nextBoolean() ? new LOTREntityBreeHobbitBrewer(world) : new LOTREntityBreeBrewer(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placeMug(world, random, -1, 2, -2, 0, LOTRFoods.BREE_DRINK);
			placeMug(world, random, 1, 2, -2, 0, LOTRFoods.BREE_DRINK);
			placeMug(world, random, 1, 2, 2, 2, LOTRFoods.BREE_DRINK);
			setBlockAndMetadata(world, -1, 1, -1, LOTRMod.barrel, 3);
			setBlockAndMetadata(world, -2, 2, 1, LOTRMod.barrel, 2);
			setBlockAndMetadata(world, 2, 2, 1, LOTRMod.barrel, 5);
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 1;
		}
	}

	public static class Butcher extends LOTRWorldGenBreeMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return random.nextBoolean() ? new LOTREntityBreeHobbitButcher(world) : new LOTREntityBreeButcher(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placePlate_item(world, random, -1, 2, -2, LOTRMod.plateBlock, getRandomButcherItem(random), true);
			placePlate_item(world, random, 1, 2, -2, LOTRMod.ceramicPlateBlock, getRandomButcherItem(random), true);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, getRandomButcherItem(random), true);
			placePlate_item(world, random, 2, 2, 1, LOTRMod.plateBlock, getRandomButcherItem(random), true);
			placePlate_item(world, random, 0, 2, 2, LOTRMod.ceramicPlateBlock, getRandomButcherItem(random), true);
		}

		public ItemStack getRandomButcherItem(Random random) {
			ItemStack[] foods = {new ItemStack(Items.beef), new ItemStack(Items.porkchop), new ItemStack(LOTRMod.gammon), new ItemStack(Items.chicken), new ItemStack(LOTRMod.muttonRaw), new ItemStack(LOTRMod.rabbitRaw), new ItemStack(LOTRMod.deerRaw)};
			ItemStack ret = foods[random.nextInt(foods.length)].copy();
			ret.stackSize = 1 + random.nextInt(3);
			return ret;
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 14;
		}
	}

	public static class Farmer extends LOTRWorldGenBreeMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return new LOTREntityBreeFarmer(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placePlate_item(world, random, -1, 2, -2, LOTRMod.plateBlock, getRandomFarmerItem(random), true);
			placePlate_item(world, random, 0, 2, -2, LOTRMod.woodPlateBlock, getRandomFarmerItem(random), true);
			placePlate_item(world, random, -2, 2, -1, LOTRMod.woodPlateBlock, getRandomFarmerItem(random), true);
			placePlate_item(world, random, -2, 2, 1, LOTRMod.ceramicPlateBlock, getRandomFarmerItem(random), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, getRandomFarmerItem(random), true);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.pumpkin, 3);
			setGrassToDirt(world, -1, 0, -1);
		}

		public ItemStack getRandomFarmerItem(Random random) {
			ItemStack[] foods = {new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip), new ItemStack(LOTRMod.leek), new ItemStack(Items.apple), new ItemStack(LOTRMod.appleGreen), new ItemStack(LOTRMod.pear), new ItemStack(LOTRMod.plum)};
			ItemStack ret = foods[random.nextInt(foods.length)].copy();
			ret.stackSize = 1 + random.nextInt(3);
			return ret;
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 13;
		}
	}

	public static class Florist extends LOTRWorldGenBreeMarketStall {
		public Florist(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return random.nextBoolean() ? new LOTREntityBreeHobbitFlorist(world) : new LOTREntityBreeFlorist(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placeRandomFlowerPot(world, random, -1, 2, -2);
			placeRandomFlowerPot(world, random, 1, 2, -2);
			placeRandomFlowerPot(world, random, -2, 2, 0);
			placeRandomFlowerPot(world, random, 2, 2, 1);
			placeRandomFlowerPot(world, random, 0, 2, 2);
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 10;
		}
	}

	public static class Lumber extends LOTRWorldGenBreeMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return new LOTREntityBreeLumberman(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			setBlockAndMetadata(world, 0, 1, 1, Blocks.log, 0);
			setGrassToDirt(world, 0, 0, 1);
			setBlockAndMetadata(world, -1, 1, -1, LOTRMod.wood5, 4);
			setGrassToDirt(world, -1, 0, -1);
			placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(Items.iron_axe));
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 12;
		}
	}

	public static class Mason extends LOTRWorldGenBreeMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return new LOTREntityBreeMason(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			setBlockAndMetadata(world, 0, 1, 1, brickBlock, brickMeta);
			setBlockAndMetadata(world, 0, 2, 1, brickBlock, brickMeta);
			setGrassToDirt(world, 0, 0, 1);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.cobblestone, 0);
			setGrassToDirt(world, -1, 0, -1);
			placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(Items.iron_pickaxe));
			placeWeaponRack(world, -2, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 8;
		}
	}

	public static class Smith extends LOTRWorldGenBreeMarketStall {
		public Smith(boolean flag) {
			super(flag);
		}

		@Override
		public LOTREntityNPC createTrader(World world, Random random) {
			return new LOTREntityBreeBlacksmith(world);
		}

		@Override
		public void decorateStall(World world, Random random) {
			placeWeaponRack(world, 1, 2, -2, 7, new ItemStack(LOTRMod.ironCrossbow));
			placeWeaponRack(world, -2, 2, 1, 3, new ItemStack(LOTRMod.battleaxeIron));
			placeWeaponRack(world, 2, 2, 1, 1, new ItemStack(Items.iron_sword));
			LOTREntityBreeGuard armorGuard = new LOTREntityBreeGuard(world);
			armorGuard.onSpawnWithEgg(null);
			placeArmorStand(world, 0, 1, 1, 0, new ItemStack[]{armorGuard.getEquipmentInSlot(4), armorGuard.getEquipmentInSlot(3), null, null});
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			wool1Block = Blocks.wool;
			wool1Meta = 7;
		}
	}

}
