package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarBazaar extends LOTRWorldGenSouthronBazaar {
	public static Class[] stallsUmbar = {Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class};

	public LOTRWorldGenUmbarBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public Class[] getStallClasses() {
		return stallsUmbar;
	}

	@Override
	public boolean isUmbar() {
		return true;
	}

	public static class Baker extends LOTRWorldGenStructureBase2 {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
			placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, getRandomFlower(world, random));
			LOTREntityUmbarBaker trader = new LOTREntityUmbarBaker(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Blacksmith extends LOTRWorldGenStructureBase2 {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
			placeArmorStand(world, 1, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null});
			placeWeaponRack(world, -2, 2, 0, 1, new LOTRWorldGenUmbarBazaar(false).getRandomHaradWeapon(random));
			placeWeaponRack(world, 2, 2, 0, 3, new LOTRWorldGenUmbarBazaar(false).getRandomHaradWeapon(random));
			LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Brewer extends LOTRWorldGenStructureBase2 {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
			placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
			placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
			LOTREntityUmbarBrewer trader = new LOTREntityUmbarBrewer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Butcher extends LOTRWorldGenStructureBase2 {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
			LOTREntityUmbarButcher trader = new LOTREntityUmbarButcher(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Farmer extends LOTRWorldGenStructureBase2 {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
			LOTREntityUmbarFarmer trader = new LOTREntityUmbarFarmer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Fish extends LOTRWorldGenStructureBase2 {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
			placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			LOTREntityUmbarFishmonger trader = new LOTREntityUmbarFishmonger(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Florist extends LOTRWorldGenStructureBase2 {
		public Florist(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, -2, 2, 0, getRandomFlower(world, random));
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			setBlockAndMetadata(world, -1, 0, 1, Blocks.grass, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.grass, 0);
			plantFlower(world, random, 1, 2, 1);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
			LOTREntityUmbarFlorist trader = new LOTREntityUmbarFlorist(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Goldsmith extends LOTRWorldGenStructureBase2 {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
			LOTREntityUmbarGoldsmith trader = new LOTREntityUmbarGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Lumber extends LOTRWorldGenStructureBase2 {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
			setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
			placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
			placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
			LOTREntityUmbarLumberman trader = new LOTREntityUmbarLumberman(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Mason extends LOTRWorldGenStructureBase2 {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick6, 6);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle13, 2);
			setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick6, 9);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.iron_pickaxe));
			LOTREntityUmbarMason trader = new LOTREntityUmbarMason(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Miner extends LOTRWorldGenStructureBase2 {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
			setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.iron_ore, 0);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.iron_pickaxe));
			LOTREntityUmbarMiner trader = new LOTREntityUmbarMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
